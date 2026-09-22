package pekan2;

import java.util.ArrayList;

public class Rekening {
    private String nomorRekening;
    private double saldoAwal;
    private ArrayList<Transaksi> riwayatTransaksi;

    public Rekening(String nomorRekening, double saldoAwal) {
        this.nomorRekening = nomorRekening;
        this.saldoAwal = saldoAwal;
        this.riwayatTransaksi = new ArrayList<>();
    }

    public boolean tarikTunai(double nominal) {
        if (nominal > 0 && nominal <= 500000 && nominal <= getSaldo()) {
            String kodeTransaksi = "TRX-T-" + (riwayatTransaksi.size() + 1);
            Transaksi trx = new Transaksi(kodeTransaksi, "Debit", nominal);
            riwayatTransaksi.add(trx);
            return true;
        } else {
            return false;
        }
    }

    public boolean setorTunai(double nominal) {
        if (nominal > 0 && nominal <= 500000) {
            String kodeTransaksi = "TRX-S-" + (riwayatTransaksi.size() + 1);
            Transaksi trx = new Transaksi(kodeTransaksi, "Kredit", nominal);
            riwayatTransaksi.add(trx);
            return true;
        } else {
            return false;
        }
    }

    public void cetakMutasi() {
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi pada rekening ini");
        } else {
            for (Transaksi trx : riwayatTransaksi) {
                trx.cetakDetail();
            }
        }
    }

    public void cetakInformasi() {
        double totalSetor = 0;
        double totalTarik = 0;

        for (Transaksi trx : riwayatTransaksi) {
            if (trx.getJenisTransaksi().equalsIgnoreCase("Kredit")) {
                totalSetor += trx.getNominal();
            } else if (trx.getJenisTransaksi().equalsIgnoreCase("Debit")) {
                totalTarik += trx.getNominal();
            }
        }

        double akumulasi = totalSetor - totalTarik;
        double saldoAkhir = saldoAwal + akumulasi;

        System.out.println("\n--- INFO TRANSAKSI ---");
        System.out.println("Total Setor : Rp " + totalSetor);
        System.out.println("Total Tarik : Rp " + totalTarik);
        System.out.println("Akumulasi   : Rp " + akumulasi);
        System.out.println("Saldo : Rp " + saldoAkhir);
    }

    public double getSaldo() {
        double akumulasi = 0;
        for (Transaksi trx : riwayatTransaksi) {
            if (trx.getJenisTransaksi().equalsIgnoreCase("Kredit")) {
                akumulasi += trx.getNominal();
            } else if (trx.getJenisTransaksi().equalsIgnoreCase("Debit")) {
                akumulasi -= trx.getNominal();
            }
        }
        return saldoAwal + akumulasi;
    }

    public String getNomorRekening() {
        return nomorRekening;
    }
}