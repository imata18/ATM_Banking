import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBankManager {
    BankManager bankManager = new BankManager();

    @BeforeClass
    public static void setUp() {

    }

    @Test
    public void testLogin() {
        String customerId = "ccaparanga";
        String pin = "0000";
        Customer customer = bankManager.login(customerId, pin);
        Assert.assertNull(customer);

        customer = bankManager.createAccount(customerId, "Cyril", pin);
        Assert.assertEquals(customerId, customer.getCustomerId());

        customer = bankManager.login(customerId, pin);
        Assert.assertEquals(customerId, customer.getCustomerId());
    }

    @Test
    public void testStatements() {
        String customerId = "ccaparanga";
        String pin = "0000";

        Customer customer = bankManager.createAccount(customerId, "Cyril", pin);
        Account checking = AccountFactory.createAccount(AccountType.CHECKING, customer, "CHECKING");
        Assert.assertNotNull(checking);
        checking.deposit(1000);
        customer.setChecking(checking);
        Account saving = AccountFactory.createAccount(AccountType.SAVINGS, customer, "SAVING");
        Assert.assertNotNull(saving);
        checking.deposit(2000);
        customer.setSavings(saving);

        Customer customer1 = bankManager.createAccount("C1", "C1", pin);
        Account account1 = AccountFactory.createAccount(AccountType.CHECKING, customer1, "CHECKING");
        Assert.assertNotNull(account1);
        account1.deposit(500);
        customer1.setChecking(account1);

        Customer customer2 = bankManager.createAccount("C2", "C2", pin);
        Account account2 = AccountFactory.createAccount(AccountType.CHECKING, customer2, "CHECKING");
        Assert.assertNotNull(account2);
        account2.deposit(200);
        customer2.setChecking(account2);

        bankManager.printReport();
    }
}
