package shop.discount;

/** Aturan diskon yang bisa ditukar. */
public interface DiscountStrategy {
    /** Besar potongan (rupiah) untuk subtotal tertentu. */
    long discountFor(long subtotal);
}