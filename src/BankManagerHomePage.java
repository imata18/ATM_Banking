import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.util.*;

public class BankManagerHomePage extends JPanel{
	
    private JTabbedPane tabbedPane;
    private JTable tableOfAccountTypes;
    private JScrollPane sp;
    private UserHomePage selectedUserHome;
    private SettingsPage managerSettingsPage;
	private Customer bankOwner;
	private BankManager userList;
	private Map<String, Customer> customersList;
	
    private JButton logoutButton;
    private JButton closeAccount;

	public BankManagerHomePage (Customer BankOwner, BankManager bankuserList) {
		
		bankOwner = BankOwner;
		userList = bankuserList;
		customersList = userList.getCustomerList();
		
		tabbedPane = new JTabbedPane();
		
		ArrayList<String[]> accountInfo = new ArrayList<String[]>();
		
		for (Map.Entry<String, Customer> item : customersList.entrySet()) {
			Customer currentCustomer = item.getValue();
			if(!currentCustomer.getCustomerId().contains("admin")) {
				String[] customerInfo = {currentCustomer.getCustomerId(), "No account", "No account", "No loans", "No account"};
				if(currentCustomer.getChecking()!=null) {
		    		String valueOfAccount = String.format("%.2f",currentCustomer.getChecking().getBalance());
		    		customerInfo[1] = valueOfAccount;
		    	}
		    	if(currentCustomer.getSavings()!=null) {
		    		String valueOfAccount = String.format("%.2f",currentCustomer.getSavings().getBalance());
		    		customerInfo[2] = valueOfAccount;
		    	}
		    	if(currentCustomer.getLoans()!=null) {
		    		String valueOfAccount = String.format("%.2f",currentCustomer.getLoans().getBalance());
		    		customerInfo[3] = valueOfAccount;
		    	}
		    	if(currentCustomer.getSecurities()!=null) {
		    		String valueOfAccount = String.format("%.2f",currentCustomer.getSecurities().getBalance());
		    		customerInfo[4] = valueOfAccount;
		    	}
		    	accountInfo.add(customerInfo);
				}
		}	
		
		String[][] accounts = accountInfo.toArray(new String[accountInfo.size()][]);
		String[] columnIDs = {"User ID","Checking","Savings","Loans","Securities"};  
    	tableOfAccountTypes = new JTable(accounts,columnIDs);    
    	tableOfAccountTypes.setBounds(30,40,200,300);   
    	tableOfAccountTypes.setCellSelectionEnabled(true);  
    	
	    sp = new JScrollPane(tableOfAccountTypes); 
	    
	    tabbedPane.addTab("Account Overview", sp);

	    managerSettingsPage = new SettingsPage(bankOwner);
		tabbedPane.addTab("Settings", managerSettingsPage.getPanel());
		
		add(tabbedPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
    
    public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
    
    public JTable getTable() {
    	return tableOfAccountTypes;
    }
    
    public Customer getCurrentUser() {
		return bankOwner;
	}
    
    public JButton getLogoutButton() {
    	return managerSettingsPage.getLogoutButton();
    }
    
    public JButton getCloseAccountButton() {
    	return managerSettingsPage.getCloseAccountButton();
    }
}
