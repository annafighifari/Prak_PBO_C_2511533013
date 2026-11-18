package shop.app;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import shop.StudentInfo;
import shop.exception.CatalogLoadException;
import shop.exception.InsufficientBalanceException;
import shop.exception.OutOfStockException;
import shop.exception.PaymentFailedException;
import shop.io.CatalogReader;
import shop.model.Product;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.service.CheckoutService;
import shop.service.Inventory;
import shop.shipping.RegularShipping;
import shop.shipping.ShippingService;

public class ShopApp {

    public static void main(String[] args) {
        List<Product> catalog;
        try {
            CatalogReader reader = new CatalogReader();
            catalog = reader.load(Path.of("katalog.csv"));
        } catch (CatalogLoadException e) {
            System.out.println("Toko tidak bisa dibuka: " + e.getMessage());
            return;
        }

        Inventory inventory = new Inventory();
        for (Product product : catalog) {
            inventory.addStock(product.getSku(), StudentInfo.stockFor(product.getSku()));
        }

        System.out.println("== Toko Online UMKM ==");
        for (int i = 0; i < catalog.size(); i++) {
            Product p = catalog.get(i);
            System.out.println((i + 1) + ". " + p.getInfo() + " (stok: " + inventory.available(p.getSku()) + ")");
        }

        List<Product> cart = new ArrayList<>();
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.print("Nomor produk (0 = selesai): ");
                if (!in.hasNextLine()) {
                    break;
                }
                int choice;
                try {
                    choice = Integer.parseInt(in.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Input harus berupa angka.");
                    continue;
                }

                if (choice == 0) {
                    break;
                }
                if (choice < 1 || choice > catalog.size()) {
                    System.out.println("Nomor produk tidak ada.");
                    continue;
                }

                Product product = catalog.get(choice - 1);

                System.out.print("Jumlah: ");
                if (!in.hasNextLine()) {
                    break;
                }
                int quantity;
                try {
                    quantity = Integer.parseInt(in.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Input harus berupa angka.");
                    continue;
                }

                if (quantity <= 0) {
                    System.out.println("Jumlah harus lebih dari 0.");
                    continue;
                }

                for (int i = 0; i < quantity; i++) {
                    cart.add(product);
                }
                System.out.println("  + " + quantity + " x " + product.getName());
            }
        }

        if (cart.isEmpty()) {
            System.out.println("Keranjang kosong. Sampai jumpa!");
            return;
        }

        CheckoutService checkout = new CheckoutService(inventory);
        ShippingService regular = new RegularShipping();
        EWalletPayment wallet = new EWalletPayment("DompetKu", StudentInfo.walletBalance());
        System.out.println("\nKeranjang: " + cart.size() + " barang, total Reguler Rp"
                + checkout.total(cart, regular));
        System.out.println(buy(checkout, cart, regular, wallet));
        System.out.println("Sisa saldo: Rp" + wallet.getBalance());
    }

    public static String buy(CheckoutService service, List<Product> cart,
            ShippingService shipping, PaymentMethod payment) {
        try {
            long paid = service.checkout(cart, shipping, payment);
            return "[OK] Pembayaran berhasil: Rp" + paid;
        } catch (InsufficientBalanceException e) {
            return "[SALDO] Saldo/limit tidak cukup (kurang Rp" + e.getShortfall() + ")";
        } catch (PaymentFailedException e) {
            return "[BAYAR] Pembayaran gagal: " + e.getMessage();
        } catch (OutOfStockException e) {
            return "[STOK] Stok habis/kurang: " + e.getMessage();
        }
    }
}