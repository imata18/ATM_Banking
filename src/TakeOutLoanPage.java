import java.awt.Color;
import javax.swing.*;

public class TakeOutLoanPage extends JPanel{
	
    private UserHomePage home;
    private JRadioButton checking, savings, securities;
    private ButtonGroup bg;   
    private JButton confirm;
    private JTextField amount;
    private JPanel panel;
    private JLabel incorrectInput;
    
    public TakeOutLoanPage(UserHomePage homepage, Customer user) {
    	home = homepage;
    	panel = new JPanel();
    	panel.setLayout(null);
    	
    	checking = new JRadioButton("Checkings Account");    
    	checking.setBounds(100,50,190,30);  
    	if(user.getAccountByType(AccountType.CHECKING) == null)
    		checking.setVisible(false);
    	
    	savings = new JRadioButton("Savings Account");    
    	savings.setBounds(100,100,190,30);  
    	if(user.getAccountByType(AccountType.SAVINGS) == null)
    		savings.setVisible(false);
    	
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
            	if(checking.isSelected()) {
            		user.getChecking().deposit(amount, "Loans");
            		user.getLoans().withdraw(amount, "Loans");
            	}
            	else if(savings.isSelected()) {
            		user.getSavings().deposit(amount, "Loans");
            		user.getLoans().withdraw(amount, "Loans");
            	}
            	else if(securities.isSelected()) {
            		user.getSecurities().deposit(amount, "Loans");
            		user.getLoans().withdraw(amount, "Loans");
            	}
            	homepage.update();
            	incorrectInput.setVisible(false);
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