package shop.shipping;

public abstract class ShippingService {
    private final String name;

    protected ShippingService(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    protected abstract long ratePerKg();

    public final long calculateCost(int weightGrams) {
        if (weightGrams <= 0) {
            return 0;
        }
        int kg = (weightGrams + 999) / 1000;
        return kg * ratePerKg();
    }
}