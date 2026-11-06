package shop.payment;

// contract for every payment method
public interface PaymentMethod {
    String getName();

    // @return true if the payment is successful
    boolean pay(long amount);
}
