package pekan3;

import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    static Rekening akunAktif = null;

    public static void main(String[] args) {
        int pilihan;

        do {
            tampilkanMenu();
            System.out.print("Pilih menu: ");
            pilihan = bacaAngka();

            switch (pilihan) {
                case 1:
                    bukaRekening();
                    break;
                case 2:
                    setorTunai();
                    break;
                case 3:
                    tarikTunai();
                    break;
                case 4:
                    cekSaldo();
                    break;
                case 5:
                    cetakInfoRekening();
                    break;
                case 6:
                    cetakMutasi();
                    break;
                case 7:
                    System.out.println("Terima kasih telah menggunakan layanan kami. Sampai jumpa!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }

            System.out.println();

        } while (pilihan != 7);

        input.close();
    }

    static void tampilkanMenu() {
        System.out.println("===== MENU BANK =====");
        System.out.println("1. Buka Rekening");
        System.out.println("2. Setor Tunai");
        System.out.println("3. Tarik Tunai");
        System.out.println("4. Cek Saldo");
        System.out.println("5. Info Rekening");
        System.out.println("6. Cetak Mutasi (Riwayat Transaksi)");
        System.out.println("7. Keluar");
    }

    static int bacaAngka() {
        while (!input.hasNextInt()) {
            System.out.print("Masukkan angka yang valid: ");
            input.next();
        }
        int angka = input.nextInt();
        input.nextLine(); // buang sisa newline
        return angka;
    }

    // 1. Buka Rekening — minta PIN sebelum instansiasi objek baru
    static void bukaRekening() {
        System.out.print("Masukkan nomor rekening: ");
        String nomor = input.nextLine();

        System.out.print("Masukkan nama pemilik: ");
        String nama = input.nextLine();

        System.out.print("Masukkan saldo awal: ");
        double saldoAwal = Double.parseDouble(bacaAngkaDesimal());

        String pin;
        while (true) {
            System.out.print("Masukkan PIN (6 digit angka): ");
            pin = input.nextLine();
            if (pin.length() == 6 && pin.chars().allMatch(Character::isDigit)) {
                break;
            }
            System.out.println("PIN tidak valid! PIN harus terdiri dari 6 digit angka.");
        }

        akunAktif = new Rekening(nomor, nama, saldoAwal, pin);
    }

    static String bacaAngkaDesimal() {
        while (!input.hasNextDouble()) {
            System.out.print("Masukkan angka yang valid: ");
            input.next();
        }
        String nilai = String.valueOf(input.nextDouble());
        input.nextLine(); // buang sisa newline
        return nilai;
    }

    // 2. Setor Tunai
    static void setorTunai() {
        if (!adaAkunAktif()) return;

        System.out.print("Masukkan nominal setor: ");
        double nominal = Double.parseDouble(bacaAngkaDesimal());
        akunAktif.setor(nominal);
    }

    // 3. Tarik Tunai — wajib otentikasi PIN dulu
    static void tarikTunai() {
        if (!adaAkunAktif()) return;

        System.out.print("Masukkan PIN Anda: ");
        String pinYangDiinput = input.nextLine();

        if (akunAktif.otentikasi(pinYangDiinput)) {
            System.out.print("Masukkan nominal tarik: ");
            double nominal = Double.parseDouble(bacaAngkaDesimal());
            akunAktif.tarik(nominal);
        } else {
            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
        }
    }

    // 4. Cek Saldo
    static void cekSaldo() {
        if (!adaAkunAktif()) return;
        System.out.println("Saldo saat ini: Rp" + akunAktif.getSaldo());
    }

    // 5. Info Rekening
    static void cetakInfoRekening() {
        if (!adaAkunAktif()) return;
        akunAktif.cetakInfo();
    }

    // 6. Cetak Mutasi — wajib otentikasi PIN dulu
    static void cetakMutasi() {
        if (!adaAkunAktif()) return;

        System.out.print("Masukkan PIN Anda: ");
        String pinYangDiinput = input.nextLine();

        if (akunAktif.otentikasi(pinYangDiinput)) {
            System.out.println("--- Riwayat Transaksi ---");
            if (akunAktif.getRiwayatTransaksi().isEmpty()) {
                System.out.println("Belum ada transaksi.");
            } else {
                for (Transaksi t : akunAktif.getRiwayatTransaksi()) {
                    t.cetakDetail();
                }
            }
        } else {
            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
        }
    }

    static boolean adaAkunAktif() {
        if (akunAktif == null) {
            System.out.println("Belum ada rekening aktif. Silakan buka rekening terlebih dahulu (Menu 1).");
            return false;
        }
        return true;
    }
}