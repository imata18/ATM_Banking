import java.util.ArrayList;
import javax.swing.*;  

public class AccountsPage {

    private JTabbedPane tabbedPane;
    private JTable tableOfAccountTypes;
    private JScrollPane sp;
    private UserHomePage home;
    private Customer user;
    
    public AccountsPage(UserHomePage homepage, Customer inputUser) {
    	home = homepage;
    	user = inputUser;
    	
    	tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
    	
    	ArrayList<String[]> accountInfo = new ArrayList<String[]>();
    	
    	if(user.getChecking()!=null) {
    		String valueOfAccount = String.format("%.2f",user.getChecking().getBalance());
    		String[] checkingInfo = {"CHECKING", valueOfAccount};
    		accountInfo.add(checkingInfo);
    	}
    	if(user.getSavings()!=null) {
    		String valueOfAccount = String.format("%.2f",user.getSavings().getBalance());
    		String[] savingsInfo = {"SAVINGS", valueOfAccount};
    		accountInfo.add(savingsInfo);
    	}
    	if(user.getLoans()!=null) {
    		String valueOfAccount = String.format("%.2f",user.getLoans().getBalance());
    		String[] loansInfo = {"LOANS", valueOfAccount};
    		accountInfo.add(loansInfo);
    	}
    	if(user.getSecurities()!=null) {
    		String valueOfAccount = String.format("%.2f",user.getSecurities().getBalance());
    		String[] securitiesInfo = {"SECURITIES", valueOfAccount};
    		accountInfo.add(securitiesInfo);
    	}
    	
    	String[][] accounts = accountInfo.toArray(new String[accountInfo.size()][]);
    	
    	String[] columnIDs = {"Type of Account","Total Amount"};  
    	tableOfAccountTypes = new JTable(accounts,columnIDs);    
    	tableOfAccountTypes.setBounds(30,40,200,300);          
	    sp = new JScrollPane(tableOfAccountTypes); 
	    
	    tabbedPane.add(sp);        	
    }
    
    public JTabbedPane getTabbedPane() {
    	return tabbedPane;
    }
    
    public JScrollPane getScrollPane() {
    	return sp;
    }
    
}
