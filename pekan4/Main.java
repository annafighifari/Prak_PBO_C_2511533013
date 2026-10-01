package pekan4;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        ArrayList<Rekening> daftarRekening = new ArrayList<>();
        Rekening akunAktif = null;

        boolean isRunning = true;

        System.out.println("=== SISTEM PERBANKAN MINI ===");

        while (isRunning) {

            System.out.println("\nMenu Utama:");
            System.out.println("1. Buka Rekening Baru");
            System.out.println("2. Setor Tunai");
            System.out.println("3. Tarik Tunai");
            System.out.println("4. Cek Informasi Rekening");
            System.out.println("5. Ganti Akun");
            System.out.println("7. Simulasi Akhir Bulan (Khusus Tabungan)");
            System.out.println("8. Simulasi Akhir Tahun (Khusus Tabungan)"); // Menu Baru
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            int pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {

                case 1:

                    System.out.print("Masukkan No Rekening: ");
                    String no = input.nextLine();

                    System.out.print("Masukkan Nama Pemilik: ");
                    String nama = input.nextLine();

                    System.out.print("Masukkan Saldo Awal: ");

                    if (!input.hasNextDouble()) {
                        System.out.println("Error: Saldo awal harus berupa angka!");
                        input.next();
                        break;
                    }

                    double saldoAwal = input.nextDouble();
                    input.nextLine();

                    System.out.print("Masukkan PIN: ");
                    String pinAwal = input.nextLine();

                    System.out.println("Pilih Produk: 1. Tabungan Umum | 2. Giro Bisnis");
                    System.out.print("Pilihan: ");
                    int produk = input.nextInt();
                    input.nextLine();

                    Rekening rekeningBaru = null;

                    if (produk == 1) {
                        System.out.print("Masukkan Suku Bunga (%): ");
                        double sukuBunga = input.nextDouble();
                        input.nextLine();
                        rekeningBaru = new RekeningTabungan(no, nama, saldoAwal, pinAwal, sukuBunga);

                    } else if (produk == 2) {
                        System.out.print("Masukkan Batas Overdraft: ");
                        double batasOverdraft = input.nextDouble();
                        input.nextLine();
                        rekeningBaru = new RekeningGiro(no, nama, saldoAwal, pinAwal, batasOverdraft);

                    } else {
                        System.out.println("Produk tidak valid, rekening tidak dibuat.");
                        break;
                    }

                    // Upcasting: RekeningTabungan / RekeningGiro otomatis dikenali sebagai Rekening
                    daftarRekening.add(rekeningBaru);
                    akunAktif = rekeningBaru;
                    break;

                case 2:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memiliki rekening!");
                    } else {
                        System.out.print("Masukkan nominal setor: ");
                        if (input.hasNextDouble()) {
                            double setor = input.nextDouble();
                            akunAktif.setor(setor); 
                        } else {
                            System.out.println("Error: Nominal harus berupa angka!");
                            input.next();
                        }
                    }
                    break;

                case 3:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memiliki rekening!");
                    } else {
                        System.out.print("Masukkan nominal tarik: ");
                        if (input.hasNextDouble()) {
                            double tarik = input.nextDouble();
                            akunAktif.tarik(tarik); 
                        } else {
                            System.out.println("Error: Nominal harus berupa angka!");
                            input.next();
                        }
                    }
                    break;

                case 4:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum membuka rekening!");
                    } else {
                        akunAktif.cetakInfo(); 
                    }
                    break;

                case 5:
                    System.out.print("Masukkan nomor rekening: ");
                    String nomorCari = input.nextLine();
                    boolean ditemukan = false;

                    for (Rekening rekening : daftarRekening) {
                        if (rekening.getNomorRekening().equals(nomorCari)) {
                            akunAktif = rekening;
                            ditemukan = true;
                            System.out.println("Berhasil mengganti akun ke rekening " + akunAktif.getNamaPemilik());
                            break;
                        }
                    }

                    if (!ditemukan) {
                        System.out.println("Rekening tidak ditemukan!");
                    }
                    break;

                case 7:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memiliki rekening aktif!");
                    } else if (akunAktif instanceof RekeningTabungan) {
                        // Downcasting: aman karena sudah dicek dengan instanceof
                        RekeningTabungan tab = (RekeningTabungan) akunAktif;
                        tab.tambahBungaAkhirBulan();
                    } else {
                        System.out.println("Gagal: Fitur bunga akhir bulan hanya berlaku untuk Rekening Tabungan.");
                    }
                    break;

                case 8: 
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memiliki rekening aktif!");
                    } else if (akunAktif instanceof RekeningTabungan) {
                        RekeningTabungan tab = (RekeningTabungan) akunAktif;
                        tab.tambahBungaAkhirTahun();
                    } else {
                        System.out.println("Gagal: Fitur bunga akhir tahun hanya berlaku untuk Rekening Tabungan.");
                    }
                    break;

                case 0:
                    isRunning = false;
                    System.out.println("Sistem ditutup. Terima Kasih!");
                    break;

                default:
                    System.out.println("Pilihan menu tidak tersedia!");
                    break;
            }
        }
        input.close();
    }
}