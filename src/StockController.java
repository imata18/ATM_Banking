import java.util.HashMap;
import java.util.Map;

public class StockController {
    private static StockController stockController;
    private final Map<String, Double> stockPrices = new HashMap<>();

    private StockController() {

    }

    public static StockController getInstance() {
        if (stockController == null) {
            stockController = new StockController();
        }
        return stockController;
    }

    public void setPrice(String symbol, double price) {
        stockPrices.put(symbol, price);
    }

    public double getPrice(String symbol) {
        // TODO: get real price
        if (!stockPrices.containsKey(symbol)) {
            setPrice(symbol, 1);
        }
        return stockPrices.get(symbol);
    }

    public void clearPrices() {
        stockPrices.clear();
    }
}
