public class Bank {
    private final BankManager bankManager;

    public Bank() {
        bankManager = new BankManager();
    }

    public void start() {
        StartUpPage test = new StartUpPage(bankManager);
    }
}
