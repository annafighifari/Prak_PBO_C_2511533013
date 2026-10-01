package pekan4;

public class RekeningGiro extends Rekening {
    private double batasOverdraft;

    public RekeningGiro(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft) {
        // Memanggil konstruktor dari superclass (Rekening)
        super(nomor, nama, saldoAwal, pinAwal);
        this.batasOverdraft = batasOverdraft;
    }
    
    public double getBatasOverdraft() {
        return batasOverdraft;
    }

    // Menimpa (override) method tarik dari class induk khusus untuk Giro
    @Override
    public boolean tarik(double nominal) {
        if (nominal <= 0) {
            System.out.println("Nominal tarik harus lebih dari 0.");
            return false;
        }
        
        // Logika Overdraft: Boleh tarik melebihi saldo, 
        // asalkan nominal tidak melebihi (saldo + batasOverdraft)
        if (nominal > (saldo + batasOverdraft)) {
            System.out.println("Gagal: Saldo dan limit overdraft tidak cukup!");
            return false;
        }

        this.saldo -= nominal; 
        
        String id = "TRX" + (riwayatTransaksi.size() + 1);
        riwayatTransaksi.add(new Transaksi(id, "Tarik Giro", nominal));
        System.out.println("Tarik Rp" + nominal + " berhasil. Saldo sekarang: Rp" + saldo);
        return true;
    }
}