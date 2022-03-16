import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class TransferPage extends JPanel{

    private UserHomePage home;
    private JRadioButton checking, savings, securities;
    private ButtonGroup bg; 
    private JButton confirm;
    private JTextField amount;
    private JTextField recipient;
    private JPanel panel;
    private JLabel incorrectInput;
    
    public TransferPage(UserHomePage homepage, Customer user, BankManager bankUserList) {
    	home = homepage;
    	panel = new JPanel();
    	panel.setLayout(null);
    	
    	JLabel fromAccountLabel = new JLabel("Transfer from which account?");
    	fromAccountLabel.setBounds(100, 10, 200, 30);
    	panel.add(fromAccountLabel);
    	
    	JLabel recipientLabel = new JLabel("Recipient's UserID: ");
    	recipientLabel.setBounds(400, 10, 200, 30);
    	panel.add(recipientLabel);
    	
    	recipient = new JTextField("");
    	recipient.setBounds(400, 50, 150, 30);
    	panel.add(recipient);
    	
    	checking = new JRadioButton("Checkings Account");    
    	checking.setBounds(100,50,190,30);      
    	
    	savings = new JRadioButton("Savings Account");    
    	savings.setBounds(100,100,190,30);  
    	
    	securities = new JRadioButton("Securities Account");
    	securities.setBounds(100,150,190,30);   
    	if(user.getAccountByType(AccountType.SECURITIES) == null)
    		securities.setVisible(false);
    	
    	bg = new ButtonGroup();
    	bg.add(checking);
    	bg.add(savings);
    	bg.add(securities);
    	
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
            	String userID = recipient.getText();
            	Customer recipientCustomer = bankUserList.getCustomer(userID);
            	if(recipientCustomer == null) {
            		incorrectInput.setText("Please enter a valid User ID");
            		incorrectInput.setVisible(true);
            	}
            	else {
	            	if(checking.isSelected() && amount <= user.getChecking().getBalance()) {
	            		user.getChecking().transfer(amount, recipientCustomer.getChecking());    
	            		incorrectInput.setVisible(false);
	            	}
	            	else if(savings.isSelected() && amount <= user.getSavings().getBalance()) {
	            		user.getSavings().transfer(amount, recipientCustomer.getSavings());
	            		incorrectInput.setVisible(false);
	            	}
	            	else if(securities.isSelected() && amount <= user.getSecurities().getBalance()) {
	            		user.getSecurities().transfer(amount, recipientCustomer.getSecurities());      
	            		incorrectInput.setVisible(false);
	            	}
	            	else {
	            		incorrectInput.setText("Please enter a valid amount");
	            		incorrectInput.setVisible(true);
	            	}
	            	homepage.update();
            	}
            } catch (Exception exception) {
                incorrectInput.setVisible(true);
            }
        });
	    
	    panel.add(checking);
	    panel.add(savings);
	    panel.add(securities);
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
}
