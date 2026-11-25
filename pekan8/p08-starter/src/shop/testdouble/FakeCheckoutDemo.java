package shop.testdouble;

import shop.discount.NoDiscount;
import shop.exception.PaymentFailedException;
import shop.model.Address;
import shop.model.Order;
import shop.model.PhysicalProduct;
import shop.service.CheckoutService;
import shop.service.InvoicePrinter;
import shop.service.Notifier;
import shop.service.OrderCalculator;

public class FakeCheckoutDemo {
    public static void main(String[] args) throws PaymentFailedException {
        PhysicalProduct kopi = new PhysicalProduct("KOPI", "Kopi Gayo 250g", 85_000, 300);
        Order order = new Order("ORD-TEST", new Address("Tester", "Padang")).add(kopi, 1);

        FakePaymentMethod fakePayment = new FakePaymentMethod();
        CheckoutService service = new CheckoutService(
                new OrderCalculator(10_000),
                new InvoicePrinter(),
                new Notifier()
        );

        service.checkout(order, new NoDiscount(), fakePayment);

        boolean ok = fakePayment.getLastAmount() == 95_000 && fakePayment.getCallCount() == 1;
        System.out.println("Sesuai harapan (95000, 1 kali)? " + ok);
    }
}