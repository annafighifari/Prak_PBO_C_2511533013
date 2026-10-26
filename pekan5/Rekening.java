package pekan5;

import java.util.ArrayList;

public class Rekening {
    private String nomorRekening;
    private String namaPemilik;
    private String pin;

    protected double saldo; 
    protected ArrayList<Transaksi> riwayatTransaksi;

    public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
        this.nomorRekening = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal;

        if (pinAwal.length() == 6) {
            this.pin = pinAwal;
        } else {
            System.out.println("Peringatan: PIN harus 6 digit! Menggunakan PIN default 123456");
            this.pin = "123456";
        }
        this.riwayatTransaksi = new ArrayList<>();
        System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat.");
    }

    public String getNomorRekening() { return nomorRekening; }
    public String getNamaPemilik() { return namaPemilik; }
    public double getSaldo() { return saldo; }
    public ArrayList<Transaksi> getRiwayatTransaksi() { return riwayatTransaksi; }

    public boolean otentikasi(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public boolean setorTunai(double nominal) {
        if (nominal <= 0) {
            System.out.println("Nominal setor harus lebih dari 0.");
            return false;
        }
        this.saldo += nominal;
        String id = "TRX" + (riwayatTransaksi.size() + 1);
        riwayatTransaksi.add(new Transaksi(id, "Setor Tunai", nominal));
        System.out.println("Setor Rp" + nominal + " berhasil. Saldo sekarang: Rp" + saldo);
        return true;
    }

    public boolean tarikTunai(double nominal) {
        if (nominal <= 0) {
            System.out.println("Gagal: Nominal tarik harus lebih dari 0!");
            return false;
        }
        if (nominal > saldo) {
            System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
            return false;
        }
        this.saldo -= nominal;
        String id = "TRX" + (riwayatTransaksi.size() + 1);
        riwayatTransaksi.add(new Transaksi(id, "Tarik Tunai", nominal));
        System.out.println("Tarik tunai Rp" + nominal + " berhasil. Sisa saldo: Rp" + saldo);
        return true;
    }

    public void transfer(Rekening tujuan, double nominal, String pinInput) {
        if (!this.otentikasi(pinInput)) {
            System.out.println("Transfer Gagal: PIN salah!");
            return;
        }
        if (this.nomorRekening.equals(tujuan.getNomorRekening())) {
            System.out.println("Transfer Gagal: Tidak dapat mentransfer ke rekening sendiri!");
            return;
        }

        System.out.println("Memproses transfer sebesar Rp" + nominal + " ke " + tujuan.getNamaPemilik() + " (" + tujuan.getNomorRekening() + ")...");
        
        boolean statusTarik = this.tarikTunai(nominal);

        if (statusTarik) {
            tujuan.setorTunai(nominal);
            
            String idSender = "TRX" + (riwayatTransaksi.size() + 1);
            riwayatTransaksi.add(new Transaksi(idSender, "Transfer Keluar ke " + tujuan.getNomorRekening(), nominal));
            
            String idReceiver = "TRX" + (tujuan.getRiwayatTransaksi().size() + 1);
            tujuan.getRiwayatTransaksi().add(new Transaksi(idReceiver, "Transfer Masuk dari " + this.nomorRekening, nominal));
            
            System.out.println("Transfer BERHASIL dilakukan!");
        } else {
            System.out.println("Transfer Gagal diproses.");
        }
    }

    public void cetakInfo() {
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Pemilik      : " + namaPemilik);
        System.out.println("Saldo        : Rp" + saldo);
    }
}