public class Loan {
	private double principal;
	private double interest;
	private double balance;

	public Loan(double principal, double interest) {
		this.principal = principal;
		this.interest = interest;
		this.balance = principal;

	}

	public double getBalance() {
		return balance;
	}

	public void applyInterest(long days) {
		balance += (balance * interest * days);
	}

	public void pay(double amount) {
		balance -= amount;
	}
}
