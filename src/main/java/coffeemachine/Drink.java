package coffeemachine;

public enum Drink {
    CHOCOLATE("H",50, true), TEA("T", 40, true), COFFEE("C", 60, true), ORANGE("O", 60, false);

    private final String code;
    private final boolean allowExtraHot;
    private int price;

    Drink(String code, int price, boolean allowExtraHot) {
        this.code = code;
        this.price = price;
        this.allowExtraHot = allowExtraHot;
    }

    public String getCode() {
        return code;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAllowExtraHot() {
        return allowExtraHot;
    }
}
