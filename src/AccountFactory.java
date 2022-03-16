import java.util.UUID;

public class AccountFactory {

	public static Account createAccount(AccountType type, Customer customer, String name) {
		String accountId = UUID.randomUUID().toString();

		switch (type) {
			case CHECKING:
				return new AccountChecking(accountId, customer.getCustomerId(), name, 0, 0.10);
			case SAVINGS:
				return new AccountSavings(accountId, customer.getCustomerId(), name, 0, 0.01);
			case LOANS:
				return new AccountLoans(accountId, customer.getCustomerId(), name, 0, 0);
			case SECURITIES:
				if (customer.getSavings() != null) {
					if (customer.getSavings().getBalance() >= 5000) {
						return new AccountSecurities(accountId, customer.getCustomerId(), name, 0, 0);
					}
				}
				break;
		}

		return null;
	}

	public static Account createAccount(AccountType type, Customer customer, String name, CurrencyType currencyType) {
		Account account = createAccount(type, customer, name);
		if (account != null) {
			account.setCurrencyType(currencyType);
		}
		return account;
	}
}
