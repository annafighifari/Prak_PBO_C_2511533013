package pekan1;

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

    if (input.hasNextDouble()) {

        double saldo = input.nextDouble();

        akunAktif = new Rekening(no, nama, saldo);
        daftarRekening.add(akunAktif);

    } else {

        System.out.println(
                "Error: Saldo awal harus berupa angka!");

        input.next();
    }

    break;

                case 2:

                    if (akunAktif == null) {

                        System.out.println(
                                "Error: Anda belum memiliki rekening!");

                    } else {

                        System.out.print("Masukkan nominal setor: ");

                        if (input.hasNextDouble()) {

                            double setor = input.nextDouble();
                            akunAktif.setorTunai(setor);

                        } else {

                            System.out.println(
                                    "Error: Nominal harus berupa angka!");

                            input.next(); // membersihkan input yang salah
                        }
                    }

                    break;

                case 3:

                    if (akunAktif == null) {

                        System.out.println(
                                "Error: Anda belum memiliki rekening!");

                    } else {

                        System.out.print("Masukkan nominal tarik: ");

                        if (input.hasNextDouble()) {

                            double tarik = input.nextDouble();
                            akunAktif.tarikTunai(tarik);

                        } else {

                            System.out.println(
                                    "Error: Nominal harus berupa angka!");

                            input.next(); // membersihkan input yang salah
                        }
                    }

                    break;

                case 4:

                    if (akunAktif == null) {

                        System.out.println(
                                "Error: Anda belum membuka rekening!");

                    } else {

                        akunAktif.cekInformasi();
                    }

                    break;

                case 5:

    System.out.print("Masukkan nomor rekening: ");
    String nomorCari = input.nextLine();

    boolean ditemukan = false;

    for (Rekening rekening : daftarRekening) {

        if (rekening.nomorRekening.equals(nomorCari)) {

            akunAktif = rekening;
            ditemukan = true;

            System.out.println(
                "Berhasil mengganti akun ke rekening " +
                akunAktif.namaPemilik
            );

            break;
        }
    }

    if (!ditemukan) {
        System.out.println("Rekening tidak ditemukan!");
    }

    break;

                case 0:

                    isRunning = false;

                    System.out.println(
                            "Sistem ditutup. Terima Kasih!");

                    break;

                // =========================
                // MENU TIDAK VALID
                // =========================
                default:

                    System.out.println(
                            "Pilihan menu tidak tersedia!");

                    break;
            }
        }

        input.close();
    }
}