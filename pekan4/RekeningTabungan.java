package pekan4;

public class RekeningTabungan extends Rekening {
    private double sukuBunga;

    public RekeningTabungan(String nomor, String nama, double saldoAwal, String pinAwal, double sukuBunga) {
        super(nomor, nama, saldoAwal, pinAwal);
        this.sukuBunga = sukuBunga;
    }

    public void tambahBungaAkhirBulan() {
        double nominalBunga = saldo * (sukuBunga / 100);
        saldo += nominalBunga;

        String idTrx = "TRX-B-" + System.currentTimeMillis();
        riwayatTransaksi.add(new Transaksi(idTrx, "Bunga Bulanan", nominalBunga));

        System.out.println("Bunga " + sukuBunga + "% berhasil ditambahkan: Rp" + nominalBunga);
    }

    public void tambahBungaAkhirTahun() {
        if (saldo > 10000000) {
            double nominalBunga = saldo * (sukuBunga / 100);
            saldo += nominalBunga;

            String idTrx = "TRX-T-" + System.currentTimeMillis();
            riwayatTransaksi.add(new Transaksi(idTrx, "Bunga Akhir Tahun", nominalBunga));

            System.out.println("Selamat! Anda mendapat bonus bunga akhir tahun " + sukuBunga + "%: Rp" + nominalBunga);
            System.out.println("Saldo Anda sekarang: Rp" + saldo);
        } else {
            System.out.println("Gagal: Saldo Anda (Rp" + saldo + ") tidak memenuhi syarat minimum > Rp10.000.000 untuk bunga akhir tahun.");
        }
    }
}