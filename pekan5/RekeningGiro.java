package pekan5;

public class RekeningGiro extends Rekening {
    private double batasOverdraft;

    public RekeningGiro(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft) {
        super(nomor, nama, saldoAwal, pinAwal);
        this.batasOverdraft = batasOverdraft;
    }
    
    public double getBatasOverdraft() { return batasOverdraft; }

    // Langkah 2: Overriding method tarikTunai dengan aturan Overdraft
    @Override
    public boolean tarikTunai(double nominal) {
        if (nominal <= 0) {
            System.out.println("Gagal: Nominal tarik Giro harus lebih dari 0!");
            return false;
        }
        if (nominal > (saldo + batasOverdraft)) {
            System.out.println("Transaksi Gagal: Melebihi batas overdraft! Maksimal penarikan: Rp" + (saldo + batasOverdraft));
            return false;
        }

        this.saldo -= nominal; 
        String id = "TRX" + (riwayatTransaksi.size() + 1);
        riwayatTransaksi.add(new Transaksi(id, "Tarik Giro (Overdraft)", nominal));
        System.out.println("Tarik tunai Giro Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
        return true;
    }
}