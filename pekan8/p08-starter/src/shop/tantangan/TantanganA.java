package shop.tantangan;

import shop.discount.NoDiscount;
import shop.exception.PaymentFailedException;
import shop.model.Address;
import shop.model.Order;
import shop.model.PhysicalProduct;
import shop.payment.QrisPayment;
import shop.service.CheckoutService;
import shop.service.InvoicePrinter;
import shop.service.Notifier;
import shop.service.OrderCalculator;
import shop.shipping.SameDayRate;

public class TantanganA {
    public static void main(String[] args) throws PaymentFailedException {
        PhysicalProduct beras = new PhysicalProduct("BRS", "Beras Solok 4kg", 60_000, 4000);
        Order order = new Order("ORD-SD1", new Address("Annafi", "Padang")).add(beras, 1);

        CheckoutService checkout = new CheckoutService(
                new OrderCalculator(new SameDayRate()),
                new InvoicePrinter(),
                new Notifier()
        );

        checkout.checkout(order, new NoDiscount(), new QrisPayment());
    }
}