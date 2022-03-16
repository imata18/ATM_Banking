public class AccountSavings extends Account implements AccountInterest {
    private final double interestRate;

    public AccountSavings(String accountId, String customerId, String name, double balance, double fee) {
        super(accountId, customerId, name, AccountType.SAVINGS, balance, fee);

        interestRate = 0.01;
    }

    @Override
    public void applyInterest(long days) {
        if (getBalance() >= 1000) {
            deposit(getBalance() * interestRate * days, "INTEREST");
        }
    }
}
