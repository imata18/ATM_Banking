import org.junit.*;

public class TestSecurities {
    private static final Customer rich = new Customer("ccaparanga", "Cyril", "0000");
    private static final StockController stockController = StockController.getInstance();
    private static AccountSecurities securities;

    @BeforeClass
    public static void before() {
        rich.setSavings(AccountFactory.createAccount(AccountType.SAVINGS, rich, "SAVING"));
        rich.getSavings().deposit(10000);
    }

    @Before
    public void beforeTest() {
        securities = (AccountSecurities) AccountFactory.createAccount(AccountType.SECURITIES, rich, "S1");
        Assert.assertNotNull(securities);
        securities.deposit(5000);

        stockController.clearPrices();
    }

    @AfterClass
    public static void after() {
        System.out.println(securities.getStatement());
    }

    @Test
    public void testBasic() {
        stockController.setPrice("AAPL", 1);
        stockController.setPrice("MSFT", 1);

        securities.buy("AAPL", 10);
        Assert.assertEquals(4990, securities.getBalance(), 0);

        securities.sell("AAPL", 5);
        Assert.assertEquals(4995, securities.getBalance(), 0);

        securities.buy("MSFT", 5);
        Assert.assertEquals(4990, securities.getBalance(), 0);
    }

    @Test
    public void testProfits() {
        stockController.setPrice("NFLX", 4);
        securities.buy("NFLX", 5);

        stockController.setPrice("NFLX", 6);
        securities.buy("NFLX", 5);
        Assert.assertEquals(4950, securities.getBalance(), 0);

        stockController.setPrice("NFLX", 6);

        securities.sell("NFLX", 10);
        Assert.assertEquals(5010, securities.getBalance(), 0);
        Assert.assertEquals(10, securities.getRealizedGains(), 0);
    }

    @Test
    public void testLoss() {
        stockController.setPrice("NFLX", 4);
        securities.buy("NFLX", 5);

        stockController.setPrice("NFLX", 6);
        securities.buy("NFLX", 5);
        Assert.assertEquals(4950, securities.getBalance(), 0);

        stockController.setPrice("NFLX", 3);

        securities.sell("NFLX", 10);
        Assert.assertEquals(4980, securities.getBalance(), 0);
        Assert.assertEquals(-20, securities.getRealizedGains(), 0);
    }
}
