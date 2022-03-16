import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountId;
    private String customerId;
    private String name;
    private AccountType type;
    private CurrencyType currencyType = CurrencyType.USD;
    private double balance;
    private double fee;
    private List<Transaction> statement;

    protected Account(String accountId, String customerId, String name, AccountType type,
                      double balance, double fee) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.fee = fee;

        statement = new ArrayList<>();
    }

    /*
    getter/setter
     */

    public String getAccountId() {
        return accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public AccountType getType() {
        return type;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public double getBalance() {
        return balance;
    }

    public double getFee() {
        return fee;
    }

    public List<Transaction> getStatement() {
        return statement;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    /*
    balance actions
     */

    public void deposit(double amount, String message) {
        statement.add(new Transaction(customerId, accountId, amount, message, Util.currentDate()));
        balance += amount;
        this.update();
    }

    public void deposit(double amount) {
        deposit(amount, "CASH");
        this.update();
    }

    public double withdraw(double amount, String message) {
        statement.add(new Transaction(customerId, accountId, -1 * amount, message, Util.currentDate()));
        balance -= amount;
        if (fee > 0) {
            statement.add(new Transaction(customerId, accountId, -1 * fee, "Withdrawl fee", Util.currentDate()));
            balance -= fee;
        }
        return amount;
    }

    public double withdraw(double amount) {
        withdraw(amount, "CASH");
        this.update();
        return amount;
    }

    public void update(){
        databaseManager.updateAccountAmount(accountId,balance);
    }
    /*
    from this account to other account
     */
    public void transfer(double amount, Account other, String message) {
        if (other == null) {
            return;
        }

        // remove amount from this account
        double withdrawl = withdraw(amount, message);
        double deposit = this.getCurrencyType().convertTo(other.getCurrencyType(), withdrawl);

        // other account
        other.deposit(deposit, getName());
    }

    public void transfer(double amount, Account other) {
        transfer(amount, other, "Transfer to " + other.getName());
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", currencyType=" + currencyType +
                ", balance=" + balance +
                '}';
    }
}
