public class Security {
	private final String symbol;
	private String name;
	private String price;
	private int shares;
	private double averagePrice;

	public Security(String symbol) {
		this.symbol = symbol;
	}

	/*
	getter/setter
	 */

	public String getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

	public int getShares() {
		return shares;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	/*
	buy/sell
	 */

	public void buy(int amount, double currentPrice) {
		averagePrice = (amount * currentPrice + shares * averagePrice) / (shares + amount);
		shares += amount;
	}

	public double sell(int amount, double currentPrice) {
		shares -= amount;
		return amount * currentPrice;
	}

	/*
	process methods
	 */

	public double getValue() {
		return shares * averagePrice;
	}

	public double getProfit(double currentPrice) {
		return shares * (currentPrice - averagePrice);
	}

	@Override
	public String toString() {
		return "Security{" +
				"symbol='" + symbol + '\'' +
				", name='" + name + '\'' +
				", price='" + price + '\'' +
				", shares=" + shares +
				", averagePrice=" + averagePrice +
				'}';
	}
}
