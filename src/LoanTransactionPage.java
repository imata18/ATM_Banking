import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class LoanTransactionPage {

	private JTabbedPane tabbedPane;
    private JTable tableOfAccountTypes;
    private JScrollPane sp;
    private UserHomePage home;
    private Customer user;
    private Account loans;
    private List<Transaction> statement;
    
    public LoanTransactionPage(Customer inputUser, UserHomePage homepage) {
    	home = homepage;
    	user = inputUser;
    	
    	tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
    	
    	loans = user.getLoans();
    	
    	if(loans != null) {
	    	statement = loans.getStatement();
	    	
	    	ArrayList<String[]> accountInfo = new ArrayList<String[]>();
	    	
	    	for(Transaction transaction: statement) {
	    		String[] info = {String.format("%.2f",transaction.getAmount()), transaction.getMessage(), 
	    				transaction.getDate()};
	    		accountInfo.add(info);
	    	}
	    	
	    	String[][] accounts = accountInfo.toArray(new String[accountInfo.size()][]);
	    	
	    	String[] columnIDs = {"Amount","Message","Date"};  
	    	tableOfAccountTypes = new JTable(accounts,columnIDs);    
	    	tableOfAccountTypes.setBounds(30,40,200,300);          
		    sp = new JScrollPane(tableOfAccountTypes); 
		    
		    tabbedPane.add(sp); 
    	}
    	else {
    		JLabel temp = new JLabel("You do not have any loans");
    		temp.setBounds(50,50, 250, 30 );
    		sp = new JScrollPane(temp);
    	}
    }
    
    public JTabbedPane getTabbedPane() {
    	return tabbedPane;
    }
    
    public JScrollPane getScrollPane() {
    	return sp;
    }
}