import java.awt.Color;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class TransferAccountPage extends JPanel{

    private UserHomePage home;
    private ButtonGroup bg; 
    private JButton confirm;
    private JTextField amount;
    private JTextField recipient;
    private JPanel panel;
    private JLabel incorrectInput;
    private JComboBox accountFrom;
    private JComboBox accountTo;
    private Customer currentUser;
    
    public TransferAccountPage(UserHomePage homepage, Customer user) {
    	home = homepage;
    	currentUser = user;
    	panel = new JPanel();
    	panel.setLayout(null);
    	
    	JLabel fromAccountLabel = new JLabel("Transfer from which account?");
    	fromAccountLabel.setBounds(100, 10, 200, 30);
    	panel.add(fromAccountLabel);
    	
    	JLabel recipientLabel = new JLabel("to");
    	recipientLabel.setBounds(250, 75, 50, 30);
    	panel.add(recipientLabel);
    	
    	ArrayList<String> accounts = new ArrayList<String>();
    	if(user.getChecking()!=null)
    		accounts.add("Checking");
    	if(user.getSavings()!=null)
    		accounts.add("Savings");
    	if(user.getSecurities()!=null)
    		accounts.add("Securities");
    	
    	String accountsFromBox[] = accounts.toArray(new String[0]);
        
        accountFrom = new JComboBox(accountsFromBox);
        accountFrom.setBounds(70, 80,150,20); 
        panel.add(accountFrom);
        
        accountTo = new JComboBox(accountsFromBox);
        accountTo.setBounds(280, 80,150,20); 
        panel.add(accountTo);
        
    	
    	incorrectInput = new JLabel("Please enter an integer");
        incorrectInput.setForeground(Color.RED);
        incorrectInput.setBounds(100, 300, 200, 30);
        incorrectInput.setVisible(false);
		panel.add(incorrectInput);
		
	    JLabel amountLabel = new JLabel("Amount:");
	    amount = new JTextField(""); 
	    amountLabel.setBounds(20, 200, 80, 30);    
	    amount.setBounds(100, 200, 100, 30);
    	
    	confirm = new JButton("Confirm");  
        confirm.setBounds(100,250, 80,30); 
        confirm.addActionListener(e -> {
            try {
            	String amountToDeposit = amount.getText();
            	double amount = Double.valueOf(amountToDeposit); 
				String fromAccount = accountFrom.getItemAt(accountFrom.getSelectedIndex()).toString();  
            	AccountType fromAcc = getAccountType(fromAccount);
            	String toAccount = accountTo.getItemAt(accountTo.getSelectedIndex()).toString();  
            	AccountType toAcc = getAccountType(toAccount);
            	if(currentUser.getAccountByType(fromAcc).getBalance()-amount<0)
            		incorrectInput.setVisible(true);
            	else
            		currentUser.getAccountByType(fromAcc).transfer(amount, currentUser.getAccountByType(toAcc));
            	homepage.update();
            } catch (Exception exception) {
                incorrectInput.setVisible(true);
            }
        });
	    
	    panel.add(confirm);
	    panel.add(amountLabel);
	    panel.add(amount);
    }
    
    public JPanel getPanel() {
    	return panel;
    }
    
    public JButton getConfirmButton() {
    	return confirm;
    }
    
    public JTextField getAmountField() {
    	return amount;
    }
    
    public String getAmountFieldString() {
    	return amount.getText();
    }
    
    public JLabel incorrectInput() {
    	return incorrectInput;
    }
    
    private AccountType getAccountType(String type) {
    	if(type == "Checking")
    		return AccountType.CHECKING;
    	else if(type == "Savings")
    		return AccountType.SAVINGS;
    	else
    		return AccountType.SECURITIES;
    }
}
