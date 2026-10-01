package pekan4;

public class Transaksi {

    private String idTransaksi;
    private String jenisTransaksi;
    private double nominal;

    public Transaksi(String idTransaksi, String jenisTransaksi, double nominal) {
        this.idTransaksi = idTransaksi;
        this.jenisTransaksi = jenisTransaksi;
        this.nominal = nominal;
    }

    @Override
    public String toString() {
        return "[" + idTransaksi + "] " + jenisTransaksi + " sebesar Rp" + nominal;
    }
}