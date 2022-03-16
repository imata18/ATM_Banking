public class AccountChecking extends Account {

    public AccountChecking(String accountId, String customerId, String name, double balance, double fee) {
        super(accountId, customerId, name, AccountType.CHECKING, balance, fee);
    }
}
