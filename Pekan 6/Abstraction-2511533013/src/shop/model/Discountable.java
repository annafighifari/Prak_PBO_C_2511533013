package shop.model;

public interface Discountable {
    int discountPercent();

    default long applyDiscount(long price) {
        return price - (price * discountPercent() / 100);
    }
}