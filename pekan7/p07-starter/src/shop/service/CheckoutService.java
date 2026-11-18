package shop.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import shop.exception.OutOfStockException;
import shop.exception.PaymentFailedException;
import shop.model.Product;
import shop.payment.PaymentMethod;
import shop.payment.RefundablePayment;
import shop.shipping.Shippable;
import shop.shipping.ShippingService;

public class CheckoutService {
    private final Inventory inventory;

    public CheckoutService() {
        this(new Inventory());
    }

    public CheckoutService(Inventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory must not be null");
        }
        this.inventory = inventory;
    }

    public long subtotal(List<Product> items) {
        long total = 0;
        for (Product product : items) {
            total += product.getFinalPrice();
        }
        return total;
    }

    public int totalWeight(List<Product> items) {
        int grams = 0;
        for (Product product : items) {
            if (product instanceof Shippable shippable) {
                grams += shippable.getWeightGrams();
            }
        }
        return grams;
    }

    public long total(List<Product> items, ShippingService shipping) {
        return subtotal(items) + shipping.calculateCost(totalWeight(items));
    }

    public long checkout(List<Product> items, ShippingService shipping, PaymentMethod payment)
            throws OutOfStockException, PaymentFailedException {
        if (items == null || items.isEmpty()) {
            throw new IllegalStateException("Keranjang belanja kosong");
        }

        Map<String, Integer> qtyPerSku = new LinkedHashMap<>();
        for (Product item : items) {
            qtyPerSku.merge(item.getSku(), 1, Integer::sum);
        }

        Map<String, Integer> reserved = new LinkedHashMap<>();
        try {
            for (Map.Entry<String, Integer> entry : qtyPerSku.entrySet()) {
                inventory.reserve(entry.getKey(), entry.getValue());
                reserved.put(entry.getKey(), entry.getValue());
            }

            long grandTotal = total(items, shipping);
            payment.pay(grandTotal);
            return grandTotal;
        } catch (OutOfStockException | PaymentFailedException | RuntimeException e) {
            for (Map.Entry<String, Integer> entry : reserved.entrySet()) {
                inventory.release(entry.getKey(), entry.getValue());
            }
            throw e;
        }
    }

    public boolean refund(RefundablePayment payment, long amount) {
        return payment.refund(amount);
    }
}