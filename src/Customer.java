import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer {
    private String customerId;
    private String name;
    private String pin;
    private final Map<String, Account> accounts;
    private Account checking;
    private Account savings;
    private Account loans;
    private Account securities;

    public Customer(String customerId, String name, String pin) {
        this.customerId = customerId;
        this.name = name;
        this.pin = pin;
        checking = null;
        savings = null;
        loans = null;
        securities = null;
        accounts = new HashMap<>();
    }

    /*
    getter/setter
     */

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Account getChecking() {
        return checking;
    }

    public void setChecking(Account checking) {
        this.checking = checking;
        databaseManager.addSpecificAccountType(this, AccountType.CHECKING ,this.getChecking().getBalance(), "USD");

    }

    public Account getSavings() {

        return savings;
    }

    public void setSavings(Account savings) {
        this.savings = savings;
        databaseManager.addSpecificAccountType(this, AccountType.SAVINGS,this.getChecking().getBalance(), "USD");

    }

    public Account getLoans() {

        return loans;
    }

    public void setLoans(Account loans) {
        this.loans = loans;
        databaseManager.addSpecificAccountType(this, AccountType.LOANS,this.getChecking().getBalance(), "USD");

    }

    public Account getSecurities() {
        return securities;
    }

    public void setSecurities(Account securities) {
        this.securities = securities;
        databaseManager.addSpecificAccountType(this, AccountType.LOANS,this.getChecking().getBalance(), "USD");

    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public Account getAccountByType(AccountType type) {
        // TODO: get account -> else pull from database -> else error. Solved?




        if (type.equals(AccountType.CHECKING)) {
            if(checking==null){
                checking=databaseManager.getSpecificAccount(this.customerId, type.toString());
                if(checking==null){
                    //error
                }
            }
            return checking;
        }
        if (type.equals(AccountType.SAVINGS)) {
            if(savings==null){
                savings=databaseManager.getSpecificAccount(this.customerId, type.toString());
                if(savings==null){
                    //error
                }
            }
            return savings;
        }
        if (type.equals(AccountType.LOANS)) {
            if(loans==null){
                loans=databaseManager.getSpecificAccount(this.customerId, type.toString());
                if(loans==null){
                    //error
                }
            }
            return loans;
        }
        if (type.equals(AccountType.SECURITIES)) {
            if(securities==null){
                securities=databaseManager.getSpecificAccount(this.customerId, type.toString());
                if(securities==null){
                    //error
                }
            }
            return securities;
        }

        return null;
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public void chargeInterest() {
        for (AccountType type : AccountType.values()) {
            Account account = getAccountByType(type);
            if (account instanceof AccountInterest) {
                ((AccountInterest) account).applyInterest(1);
            }
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        for (AccountType type : AccountType.values()) {
            Account account = getAccountByType(type);
            if (account != null) {
                transactions.addAll(account.getStatement());
            }
        }
        return transactions;
    }
}
