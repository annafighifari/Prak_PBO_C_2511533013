package shop.service;

import java.util.List;
import shop.model.Product;
import shop.payment.PaymentMethod;
import shop.payment.RefundablePayment;
import shop.shipping.Shippable;
import shop.shipping.ShippingService;

public class CheckoutService {

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
        long sub = subtotal(items);
        if (shipping == null) {
            return sub;
        }
        int grams = totalWeight(items);
        return sub + shipping.calculateCost(grams);
    }

    public boolean checkout(List<Product> items, ShippingService shipping, PaymentMethod payment) {
        if (items == null || items.isEmpty()) {
            return false;
        }
        long totalCost = total(items, shipping);
        return payment.pay(totalCost);
    }
    public boolean refund(RefundablePayment payment, long amount) {
    if (payment == null) {
        return false;
    }
    return payment.refund(amount);
 }
}