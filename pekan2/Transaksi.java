package pekan2;

public class Transaksi {
    String kodeTransaksi;
    String jenisTransaksi;
    double nominal;

    public Transaksi(String kodeTransaksi, String jenisTransaksi, double nominal) {
        this.kodeTransaksi = kodeTransaksi;
        this.jenisTransaksi = jenisTransaksi;
        this.nominal = nominal;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public double getNominal() {
        return nominal;
    }

    public void cetakDetail() {
        System.out.println(kodeTransaksi + " | " + jenisTransaksi + " | Rp " + nominal);
    }
}