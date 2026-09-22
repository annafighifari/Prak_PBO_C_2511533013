package pekan2;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Rekening akun1 = new Rekening("123", 50000);
        Rekening akun2 = new Rekening("456", 100000);
        
        Rekening akunAktif = akun1;

        boolean berjalan = true;
        while (berjalan) {
            System.out.println("\n=== MENU UTAMA (Akun: " + akunAktif.getNomorRekening() + ") ===");
            System.out.println("1. Cek Saldo");
            System.out.println("2. Setor Tunai");
            System.out.println("3. Tarik Tunai");
            System.out.println("4. Cek Informasi Rekening");
            System.out.println("5. Ganti Akun");
            System.out.println("6. Cetak Mutasi (Riwayat)");
            System.out.println("7. Informasi Transaksi");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            
            int pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1:
                    System.out.println("Saldo Anda: " + akunAktif.getSaldo());
                    break;
                case 2:
                    System.out.print("Masukkan nominal setor: ");
                    double nominalSetor = scanner.nextDouble();
                    if (akunAktif.setorTunai(nominalSetor)) {
                        System.out.println("Setor tunai berhasil.");
                    } else {
                        System.out.println("Setor tunai gagal, Maksimal transaksi 500.000");
                    }
                    break;
                case 3:
                    System.out.print("Masukkan nominal tarik: ");
                    double nominalTarik = scanner.nextDouble();
                    if (akunAktif.tarikTunai(nominalTarik)) {
                        System.out.println("Penarikan berhasil.");
                    } else {
                        System.out.println("Penarikan gagal! Saldo tidak mencukupi atau melebihi batas transaksi 500.000.");
                    }
                    break;
                case 4:
                    System.out.println("\n--- INFORMASI REKENING ---");
                    System.out.println("Nomor Rekening : " + akunAktif.getNomorRekening());
                    System.out.println("Saldo          : " + akunAktif.getSaldo());
                    break;
                case 5:
                    if (akunAktif == akun1) {
                        akunAktif = akun2;
                    } else {
                        akunAktif = akun1;
                    }
                    System.out.println("Berhasil berganti akun! Akun aktif saat ini: " + akunAktif.getNomorRekening());
                    break;
                case 6:
                    akunAktif.cetakMutasi();
                    break;
                case 7:
                    akunAktif.cetakInformasi();
                    break;
                case 0:
                    berjalan = false;
                    System.out.println("Terima kasih telah menggunakan layanan kami.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
        scanner.close();
    }
}