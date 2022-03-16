import javax.swing.*;  

public class UserHomePage extends JPanel{
	private JTabbedPane tabbedPane;
	private JTabbedPane accountTab;

	private Customer user;
	private BankManager bankUserList;
	
	private AccountsPage userAccPage;
	private CheckingTransactionPage checkPage;
	private SavingsTransactionPage savePage;
	private SecuritiesTransactionPage securePage;
	private LoanTransactionPage loanPage;
	
	private DepositPage userDepositPage;
	private WithdrawPage userWithdrawPage;
	private TransferPage userTransferPage;
	private TransferAccountPage userTransferAccountPage;
	
	private SettingsPage settingsPage;
	private AddNewAccountPage addAccountPage;
	
	private TakeOutLoanPage takeOutLoanPage;
	
	public UserHomePage(Customer inputUser, BankManager bankuserList) {
		user = inputUser;
		bankUserList = bankuserList;
		
		tabbedPane = new JTabbedPane();
		
		accountTab = new JTabbedPane();
		
		userAccPage = new AccountsPage(this, user);
		accountTab.add("Overview", userAccPage.getScrollPane());
		
		checkPage = new CheckingTransactionPage(user, this);
		accountTab.add("Checking", checkPage.getScrollPane());
		
		savePage = new SavingsTransactionPage(user, this);
		accountTab.add("Savings", savePage.getScrollPane());
		
		securePage = new SecuritiesTransactionPage(user, this);
		accountTab.add("Securities", securePage.getScrollPane());
		
		loanPage = new LoanTransactionPage(user, this);
		accountTab.add("Loans", loanPage.getScrollPane());
		
		tabbedPane.add("Accounts", accountTab);

		userDepositPage = new DepositPage(this, user);
		tabbedPane.addTab("Deposit", userDepositPage.getPanel());
		
		userWithdrawPage = new WithdrawPage(this, user);
		tabbedPane.addTab("Withdraw", userWithdrawPage.getPanel());
		
		userTransferPage = new TransferPage(this, user, bankUserList);
		tabbedPane.addTab("Transfer", userTransferPage.getPanel());
		
		settingsPage = new SettingsPage(user);
		tabbedPane.addTab("Settings", settingsPage.getPanel());
		
		addAccountPage = new AddNewAccountPage(this, user);
		tabbedPane.add("Add Account", addAccountPage.getPanel());
		
		takeOutLoanPage = new TakeOutLoanPage(this, user);
		tabbedPane.add("Get Loan", takeOutLoanPage.getPanel());
		
		userTransferAccountPage = new TransferAccountPage(this, user);
		tabbedPane.add("Transfer Account", userTransferAccountPage.getPanel());
		
		
		add(tabbedPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	
	
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	public JButton getLogoutButton() {
		return settingsPage.getLogoutButton();
	}
	
	public JButton getCloseAccountButton() {
		return settingsPage.getCloseAccountButton();
	}
	
	public Customer getCurrentUser() {
		return user;
	}
	
	public void update() {
    	userAccPage = new AccountsPage(this, user);
    	checkPage = new CheckingTransactionPage(user, this);
    	savePage = new SavingsTransactionPage(user, this);
    	securePage = new SecuritiesTransactionPage(user, this);
    	loanPage = new LoanTransactionPage(user,this);
    	accountTab.setComponentAt(0, userAccPage.getScrollPane());
    	accountTab.setComponentAt(1, checkPage.getScrollPane());
    	accountTab.setComponentAt(2, savePage.getScrollPane());
    	accountTab.setComponentAt(3, securePage.getScrollPane());
    	accountTab.setComponentAt(4, loanPage.getScrollPane());
    	tabbedPane.setComponentAt(0, accountTab);
    	
		userDepositPage = new DepositPage(this, user);
		tabbedPane.setComponentAt(1, userDepositPage.getPanel());
		userWithdrawPage = new WithdrawPage(this, user);
		tabbedPane.setComponentAt(2, userWithdrawPage.getPanel());
		userTransferPage = new TransferPage(this, user, bankUserList);
		tabbedPane.setComponentAt(3, userTransferPage.getPanel());
		addAccountPage = new AddNewAccountPage(this, user);
		tabbedPane.setComponentAt(5, addAccountPage.getPanel());
		takeOutLoanPage = new TakeOutLoanPage(this, user);
		tabbedPane.setComponentAt(6, takeOutLoanPage.getPanel());
		userTransferAccountPage = new TransferAccountPage(this, user);
		tabbedPane.setComponentAt(7, userTransferAccountPage.getPanel());
	}
	
}
