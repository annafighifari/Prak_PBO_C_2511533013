package review;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import shop.exception.CatalogLoadException;
import shop.exception.PaymentFailedException;
import shop.payment.PaymentMethod;

public class FragileShop {

    public static void payOrder(PaymentMethod method, long amount) throws PaymentFailedException {
        method.pay(amount);
    }

    public static String readCatalogHeader(Path path) throws CatalogLoadException {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return reader.readLine();
        } catch (IOException e) {
            throw new CatalogLoadException("Gagal membaca header katalog: " + path, e);
        }
    }
}