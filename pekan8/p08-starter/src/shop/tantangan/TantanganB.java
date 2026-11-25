package shop.tantangan;

import java.util.List;
import shop.discount.PlatinumMemberDiscount;
import shop.exception.PaymentFailedException;
import shop.model.Address;
import shop.model.Order;
import shop.model.PhysicalProduct;
import shop.notify.SmsChannel;
import shop.notify.WhatsAppChannel;
import shop.payment.QrisPayment;
import shop.service.CheckoutService;
import shop.service.InvoicePrinter;
import shop.service.Notifier;
import shop.service.OrderCalculator;

public class TantanganB {
    public static void main(String[] args) throws PaymentFailedException {
        PhysicalProduct kopi = new PhysicalProduct("KOPI", "Kopi Gayo 250g", 85_000, 300);
        Order order = new Order("ORD-WA1", new Address("Annafi", "Padang")).add(kopi, 2);

        Notifier multiNotifier = new Notifier(List.of(new SmsChannel(), new WhatsAppChannel()));
        CheckoutService checkout = new CheckoutService(
                new OrderCalculator(10_000),
                new InvoicePrinter(),
                multiNotifier
        );

        checkout.checkout(order, new PlatinumMemberDiscount(), new QrisPayment());
    }
}