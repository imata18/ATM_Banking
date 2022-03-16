import java.util.HashMap;
import java.util.Map;

public class AccountSecurities extends Account {
    private final Map<String, Security> securities = new HashMap<>();
    private double realizedGains = 0;

    public AccountSecurities(String accountId, String customerId, String name, double balance, double fee) {
        super(accountId, customerId, name, AccountType.SECURITIES, balance, fee);
    }

    public Map<String, Security> getSecurities() {
        return securities;
    }

    public double getRealizedGains() {
        return realizedGains;
    }

    public double getUnrealizedGains() {
        double gains = 0;
        for (Security security : securities.values()) {
            // TODO: how to get current price
            double currentPrice = StockController.getInstance().getPrice(security.getSymbol());
            gains += security.getProfit(currentPrice);
        }
        return gains;
    }

    public void buy(String symbol, int amount) {
        // TODO: check balance, get current price
        double currentPrice = StockController.getInstance().getPrice(symbol);
        double cost = amount * currentPrice;

        if (cost < getBalance()) {
            if (!securities.containsKey(symbol)) {
                securities.put(symbol, new Security(symbol));
            }

            Security security = securities.get(symbol);
            security.buy(amount, currentPrice);
            withdraw(cost, "BUY " + amount + " " + symbol);
        }
    }

    public void sell(String symbol, int amount) {
        // TODO: update realized gains, get current price
        if (securities.containsKey(symbol)) {
            double currentPrice = StockController.getInstance().getPrice(symbol);
            Security security = securities.get(symbol);

            double gains = security.sell(amount, currentPrice);
            double intialCost = security.getAveragePrice() * amount;
            realizedGains += gains - intialCost;
            deposit(gains, "SELL " + amount + " " + symbol);
        }
    }
}
