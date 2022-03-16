import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAccount {
	private static Customer customer = new Customer("ccaparanga", "Cyril", "0000");
	private static Customer rich = new Customer("ccaparanga", "Cyril", "0000");

	@BeforeClass
	public static void before() {
		rich.setSavings(AccountFactory.createAccount(AccountType.SAVINGS, rich, "SAVING"));
		rich.getSavings().deposit(10000);
	}

	@Test
	public void testTransactions() {
		Account account = AccountFactory.createAccount(AccountType.CHECKING, customer, "CHECKING");
		Assert.assertNotNull(account);
		account.deposit(5.00);
		Assert.assertEquals(5.00, account.getBalance(), 0);
		double withdrawn = account.withdraw(2.00);
		Assert.assertEquals(2.00, withdrawn, 0);
		Assert.assertEquals(2.9, account.getBalance(), 0);
	}

	@Test
	public void testSecuritiesPoor() {
		Account account = AccountFactory.createAccount(AccountType.SECURITIES, customer, "SECURITIES");
		Assert.assertNull(account);
	}

	@Test
	public void testSecuritiesRich() {
		Account account = AccountFactory.createAccount(AccountType.SECURITIES, rich, "SECURITIES");
		Assert.assertNotNull(account);
	}

	@Test
	public void testLoans() {
		// account created
		AccountLoans account = (AccountLoans) AccountFactory.createAccount(AccountType.LOANS, rich, "LOAN");
		Assert.assertNotNull(account);

		// loan created
		account.requestLoan(1000, 0.05);
		Assert.assertNotNull(account.getLoans().get(0));
		Assert.assertEquals(1000, account.getLoans().get(0).getBalance(), 0);

		// check interest
		account.applyInterest(1);
		Assert.assertEquals(1050, account.getLoans().get(0).getBalance(), 0);
	}

	@Test
	public void testSavings() {
		AccountSavings saving =
				(AccountSavings) AccountFactory.createAccount(AccountType.SAVINGS, customer, "SAVING");

		Assert.assertNotNull(saving);
		saving.deposit(1000);
		saving.applyInterest(1);
		Assert.assertEquals(1010, saving.getBalance(), 0);

		System.out.println(saving.getStatement());
	}

	@Test
	public void testTransfer() {
		Account checking = AccountFactory.createAccount(AccountType.CHECKING, customer, "CHECKING");
		Account saving = AccountFactory.createAccount(AccountType.SAVINGS, customer, "SAVING");

		Assert.assertNotNull(checking);
		Assert.assertNotNull(saving);

		checking.deposit(1000);
		saving.deposit(2000);

		checking.transfer(100, saving);

		Assert.assertEquals(899.9, checking.getBalance(), 0);
		Assert.assertEquals(2100, saving.getBalance(), 0);

		System.out.println(checking.getStatement());
		System.out.println(saving.getStatement());
	}

	@Test
	public void testTransferCurrencies() {
		Account usdAccount = AccountFactory.createAccount(AccountType.SAVINGS, customer, "USD", CurrencyType.USD);
		Account euroAccount =
				AccountFactory.createAccount(AccountType.SAVINGS, customer, "EURO", CurrencyType.EURO);

		Assert.assertNotNull(usdAccount);
		Assert.assertNotNull(euroAccount);

		usdAccount.deposit(2000);
		euroAccount.deposit(1000);

		usdAccount.transfer(200, euroAccount);

		Assert.assertEquals(1799.99, usdAccount.getBalance(), 0);
		Assert.assertEquals(1000 + 200 * CurrencyType.EURO.getFromUsd(), euroAccount.getBalance(), 0);

		System.out.println(usdAccount.getStatement());
		System.out.println(euroAccount.getStatement());
	}

	@Test
	public void testTransferCustomers() {
		Customer customer = new Customer("Cyril", "Cyril", "0000");
		Customer otherCustomer = new Customer("Other", "Other", "0000");

		Account savings = AccountFactory.createAccount(AccountType.SAVINGS, customer, "THIS", CurrencyType.USD);
		Account otherSavings = AccountFactory.createAccount(AccountType.SAVINGS, customer, "OTHER", CurrencyType.USD);

		Assert.assertNotNull(savings);
		Assert.assertNotNull(otherSavings);

		customer.addAccount(savings);
		otherCustomer.addAccount(otherSavings);

		savings.deposit(1000);
		otherSavings.deposit(2000);

		savings.transfer(100, otherSavings);

		Assert.assertEquals(899.99, customer.getAccounts().get(savings.getAccountId()).getBalance(), 0);
		Assert.assertEquals(2100, otherCustomer.getAccounts().get(otherSavings.getAccountId()).getBalance(), 0);

		System.out.println(customer.getAccounts());
		System.out.println(otherCustomer.getAccounts());
	}
}
