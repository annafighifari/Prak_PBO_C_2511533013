package pekan5;

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
            System.out.println("7. Simulasi Akhir Bulan (Tabungan)");
            System.out.println("8. Transfer ke Rekening Lain (Tugas Modul 5)"); // Menu 8 Sesuai PDF
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
                    double saldoAwal = input.nextDouble();
                    input.nextLine();
                    System.out.print("Masukkan PIN (6 digit): ");
                    String pinAwal = input.nextLine();

                    System.out.println("Pilih Produk: 1. Tabungan Umum | 2. Giro Bisnis");
                    int produk = input.nextInt();
                    input.nextLine();

                    Rekening rekeningBaru;
                    if (produk == 1) {
                        System.out.print("Masukkan Suku Bunga (%): ");
                        double sukuBunga = input.nextDouble();
                        input.nextLine();
                        rekeningBaru = new RekeningTabungan(no, nama, saldoAwal, pinAwal, sukuBunga);
                    } else {
                        System.out.print("Masukkan Batas Overdraft: ");
                        double overdraft = input.nextDouble();
                        input.nextLine();
                        rekeningBaru = new RekeningGiro(no, nama, saldoAwal, pinAwal, overdraft);
                    }
                    daftarRekening.add(rekeningBaru);
                    akunAktif = rekeningBaru;
                    break;

                case 2:
                    if (akunAktif == null) System.out.println("Error: Belum ada rekening aktif!");
                    else {
                        System.out.print("Masukkan nominal setor: ");
                        akunAktif.setorTunai(input.nextDouble());
                        input.nextLine();
                    }
                    break;

                case 3:
                    if (akunAktif == null) System.out.println("Error: Belum ada rekening aktif!");
                    else {
                        System.out.print("Masukkan nominal tarik: ");
                        akunAktif.tarikTunai(input.nextDouble()); // Dynamic Method Dispatch bekerja di sini!
                        input.nextLine();
                    }
                    break;

                case 4:
                    if (akunAktif == null) System.out.println("Error: Belum ada rekening aktif!");
                    else akunAktif.cetakInfo();
                    break;

                case 5:
                    System.out.print("Masukkan nomor rekening: ");
                    String nomorCari = input.nextLine();
                    boolean ditemukan = false;
                    for (Rekening r : daftarRekening) {
                        if (r.getNomorRekening().equals(nomorCari)) {
                            akunAktif = r;
                            ditemukan = true;
                            System.out.println("Berhasil mengganti akun ke " + akunAktif.getNamaPemilik());
                            break;
                        }
                    }
                    if (!ditemukan) System.out.println("Rekening tidak ditemukan!");
                    break;

                case 7:
                    if (akunAktif instanceof RekeningTabungan) {
                        ((RekeningTabungan) akunAktif).tambahBungaAkhirBulan();
                    } else {
                        System.out.println("Fitur khusus Rekening Tabungan.");
                    }
                    break;

                case 8: // Implementasi Tugas Modul 5: Menu 8 Transfer ke Rekening Lain
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum login/memiliki rekening aktif!");
                        break;
                    }
                    System.out.print("Masukkan Nomor Rekening Tujuan: ");
                    String noRekTujuan = input.nextLine();
                    
                    Rekening akunTujuan = null;
                    for (Rekening r : daftarRekening) {
                        if (r.getNomorRekening().equals(noRekTujuan)) {
                            akunTujuan = r;
                            break;
                        }
                    }

                    if (akunTujuan == null) {
                        System.out.println("Rekening tujuan tidak ditemukan!");
                    } else {
                        System.out.print("Masukkan Nominal Transfer: Rp");
                        double nominalTrf = input.nextDouble();
                        input.nextLine();
                        System.out.print("Masukkan PIN Akun Anda: ");
                        String pinTrf = input.nextLine();

                        // memanggil method transfer polimorfik
                        akunAktif.transfer(akunTujuan, nominalTrf, pinTrf);
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