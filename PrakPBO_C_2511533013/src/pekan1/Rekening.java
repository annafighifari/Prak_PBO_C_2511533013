package pekan1;

public class Rekening {

    String nomorRekening;
    String namaPemilik;
    double saldo;

    // Constructor
    public Rekening(String nomor, String nama, double saldoAwal) {

        nomorRekening = nomor;
        namaPemilik = nama;
        saldo = saldoAwal;

        System.out.println(
            "Rekening atas nama " + namaPemilik +
            " berhasil dibuat dengan saldo Rp" + saldo
        );
    }

public void setorTunai(double nominal) {

    if (nominal >= 10000) {

        saldo += nominal;

        System.out.println(
            "Setor tunai Rp" + nominal +
            " berhasil. Saldo saat ini: Rp" + saldo
        );

    } else {

        System.out.println(
            "Gagal, nominal setor minimal Rp10.000!"
        );
    }
}

    public void tarikTunai(double nominal) {

        if (nominal < 10000) {

            System.out.println(
                "Gagal, nominal tarik tunai minimal Rp10.000!"
            );

        } else if (nominal > saldo) {

            System.out.println(
                "Gagal, saldo tidak mencukupi, saldo Anda : Rp" + saldo
            );

        } else {

            saldo -= nominal;

            System.out.println(
                "Tarik tunai Rp" + nominal +
                " berhasil. Saldo saat ini: Rp" + saldo
            );
        }
    }

    // Method cek informasi rekening
    public void cekInformasi() {

        System.out.println("\n--- INFO REKENING ---");
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik : " + namaPemilik);
        System.out.println("Saldo Akhir  : Rp" + saldo);
        System.out.println("---------------------");
    }

    public void gantiAkun(String nomor, String nama, double saldoAwal) {
        
        nomorRekening = nomor;
        namaPemilik = nama;
        saldo = saldoAwal;

        System.out.println(
            "Rekening atas nama " + namaPemilik +
            " berhasil diganti dengan saldo Rp" + saldo
        );
    }
}