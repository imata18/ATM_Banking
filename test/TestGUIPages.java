
public class TestGUIPages {

	public static void main(String[] args) {
		BankManager temp = new BankManager();
		Customer customer = temp.createAccount("John", "John", "123");
		

		customer.setSavings(AccountFactory.createAccount(AccountType.SAVINGS, customer, "SAVING"));
		customer.getSavings().deposit(10000);
		customer.setChecking(AccountFactory.createAccount(AccountType.CHECKING, customer, "CHECKING"));
		customer.getChecking().deposit(10000);
		customer.setLoans(AccountFactory.createAccount(AccountType.LOANS, customer, "LOANS"));
		customer.getLoans().deposit(10000);
//		customer.setSecurities(AccountFactory.createAccount(AccountType.SECURITIES, customer, "SECURITIES"));
//		customer.getSecurities().deposit(10000);
		
		StartUpPage test = new StartUpPage(temp);
	}

}
