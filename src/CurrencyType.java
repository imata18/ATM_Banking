public enum CurrencyType {
    USD(1.00, 1.00),
    GBP(1.32, 0.76),
    EURO(1.13, 0.89),
    JPY(0.01, 113.58),
    CN(0.16, 6.37);

    private double toUsd;
    private double fromUsd;

    private CurrencyType(double toUsd, double fromUsd) {
        this.toUsd = toUsd;
        this.fromUsd = fromUsd;
    }

    public double getToUsd() {
        return toUsd;
    }

    public double getFromUsd() {
        return fromUsd;
    }

    public double convertTo(CurrencyType otherType, double amount) {
        double intermediate = amount * this.getToUsd();
        return intermediate * otherType.getFromUsd();
    }
}
