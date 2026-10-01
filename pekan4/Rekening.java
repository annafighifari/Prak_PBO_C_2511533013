package pekan4;

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

    public String getNomorRekening() {
        return nomorRekening;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public double getSaldo() {
        return saldo;
    }

    public ArrayList<Transaksi> getRiwayatTransaksi() {
        return riwayatTransaksi;
    }

    public boolean otentikasi(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public boolean setor(double nominal) {
        if (nominal <= 0) {
            System.out.println("Nominal setor harus lebih dari 0.");
            return false;
        }
        this.saldo += nominal;
        String id = "TRX" + (riwayatTransaksi.size() + 1);
        riwayatTransaksi.add(new Transaksi(id, "Setor", nominal));
        System.out.println("Setor Rp" + nominal + " berhasil. Saldo sekarang: Rp" + saldo);
        return true;
    }

    public boolean tarik(double nominal) {
        if (nominal <= 0) {
            System.out.println("Nominal tarik harus lebih dari 0.");
            return false;
        }
        if (nominal > saldo) {
            System.out.println("Saldo tidak cukup.");
            return false;
        }
        this.saldo -= nominal;
        String id = "TRX" + (riwayatTransaksi.size() + 1);
        riwayatTransaksi.add(new Transaksi(id, "Tarik", nominal));
        System.out.println("Tarik Rp" + nominal + " berhasil. Saldo sekarang: Rp" + saldo);
        return true;
    }

    public void cetakInfo() {
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Pemilik      : " + namaPemilik);
        System.out.println("Saldo        : Rp" + saldo);
    }
}