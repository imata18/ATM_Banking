import java.awt.Color;

import javax.swing.*;


public class AddNewAccountPage {

	
	private JButton addNewChecking;
    private JButton addNewSavings;
    private JButton addNewSecurities;
    private JButton addNewLoans;
    private ButtonGroup bg;  
    private JLabel amountToOpenLabel;
    private JTextField amountToOpenWith;
    private JLabel incorrectInput;
    private JPanel panel;
    private JComboBox currencySelection;
    
    private UserHomePage home;
    private Customer user;
    
    public AddNewAccountPage(UserHomePage homepage, Customer inputUser) {
    	
		user = inputUser;
		home = homepage;
		panel = new JPanel();
		panel.setLayout(null);
    	
		addNewChecking = new JButton("Create New Checking Account");  
		addNewChecking.setBounds(70, 150, 250, 30);
		if(inputUser.getChecking()!=null)
			addNewChecking.setVisible(false);
		
		addNewSavings = new JButton("Create New Savings Account");  
		addNewSavings.setBounds(70, 200, 250, 30);
		if(inputUser.getSavings()!=null)
			addNewSavings.setVisible(false);
		
		addNewSecurities = new JButton("Create New Securities Account");  
		addNewSecurities.setBounds(70, 250, 250, 30); 
		addNewSecurities.setVisible(false);
		if(inputUser.getSecurities()==null && inputUser.getSavings()!=null && inputUser.getSavings().getBalance()>=CurrencyType.USD.convertTo(user.getSavings().getCurrencyType(), 5000))
			addNewSecurities.setVisible(true);
		
		addNewLoans = new JButton("Create New Loans Account");  
		addNewLoans.setBounds(70, 300, 250, 30); 
		if(inputUser.getLoans()!=null)
			addNewLoans.setVisible(false);
		
    	bg = new ButtonGroup();
    	bg.add(addNewChecking);
    	bg.add(addNewSavings);
    	bg.add(addNewSecurities);
    	bg.add(addNewLoans);
		
		amountToOpenLabel = new JLabel("Amount to iniate account with:");
		amountToOpenLabel.setBounds(370, 150, 300, 30);
		
		amountToOpenWith = new JTextField("");
		amountToOpenWith.setBounds(370, 200, 100, 30);
		
    	
    	incorrectInput = new JLabel("Please enter an integer");
        incorrectInput.setForeground(Color.RED);
        incorrectInput.setBounds(370, 250, 200, 30);
        incorrectInput.setVisible(false);
        
        String currencies[]={"USD","GBP","EURO","JPY","CN"};
        currencySelection = new JComboBox(currencies);
        currencySelection.setBounds(70, 80,90,20); 
        panel.add(currencySelection);
        
		addNewChecking.addActionListener(e -> {
			try {
				String amountToOpen = amountToOpenWith.getText();
				String currencyToUse = currencySelection.getItemAt(currencySelection.getSelectedIndex()).toString();  
				CurrencyType currency = getCurrencyType(currencyToUse);
				if(amountToOpen == "" || Double.valueOf(amountToOpen)<CurrencyType.USD.convertTo(currency, 5.1))
					incorrectInput.setVisible(true);
				else {
					double amount = Double.valueOf(amountToOpen);
					user.setChecking(AccountFactory.createAccount(AccountType.CHECKING, user, "CHECKING"));
					user.getChecking().setCurrencyType(currency);
					user.getChecking().deposit(amount, currencyToUse);
					user.getChecking().withdraw(CurrencyType.USD.convertTo(currency, 5), "Account creation fee");
					incorrectInput.setVisible(false);
					home.update();
				}
			} catch (Exception exception) {
                incorrectInput.setVisible(true);
            }	
		});
		
		addNewSavings.addActionListener(e -> {
			try {
				String amountToOpen = amountToOpenWith.getText();
				String currencyToUse = currencySelection.getItemAt(currencySelection.getSelectedIndex()).toString();  
				CurrencyType currency = getCurrencyType(currencyToUse);
				if(amountToOpen == "" || Double.valueOf(amountToOpen)<CurrencyType.USD.convertTo(currency, 5.1))
					incorrectInput.setVisible(true);
				else {
					double amount = Double.valueOf(amountToOpen);
					user.setSavings(AccountFactory.createAccount(AccountType.SAVINGS, user, "SAVINGS"));
					user.getSavings().setCurrencyType(currency);
					user.getSavings().deposit(amount, currencyToUse);
					user.getSavings().withdraw(CurrencyType.USD.convertTo(currency, 5), "Account creation fee");
					incorrectInput.setVisible(false);
					home.update();
				}
			} catch (Exception exception) {
                incorrectInput.setVisible(true);
            }	
		});
		
		addNewSecurities.addActionListener(e -> {
			try {
				String amountToOpen = amountToOpenWith.getText();
				double amount = Double.valueOf(amountToOpen);
				String currencyToUse = currencySelection.getItemAt(currencySelection.getSelectedIndex()).toString();  
				CurrencyType currency = getCurrencyType(currencyToUse);
				if(amountToOpen == "" || amount<CurrencyType.USD.convertTo(currency, 1000) || user.getSavings().getBalance()-amount<CurrencyType.USD.convertTo(user.getSavings().getCurrencyType(), 2505.1))
					incorrectInput.setVisible(true);
				else {
					user.setSecurities(AccountFactory.createAccount(AccountType.SECURITIES, user, "SECURITIES"));
					user.getSavings().withdraw(currency.convertTo(user.getSavings().getCurrencyType(), amount)+CurrencyType.USD.convertTo(user.getSavings().getCurrencyType(), 5), "Account creation fee");
					user.getSecurities().setCurrencyType(currency);
					user.getSecurities().deposit(amount, currencyToUse);
					incorrectInput.setVisible(false);
					home.update();
				}
			} catch (Exception exception) {
                incorrectInput.setVisible(true);
            }	
		});
		
		addNewLoans.addActionListener(e -> {
				user.setLoans(AccountFactory.createAccount(AccountType.LOANS, user, "LOANS"));
				incorrectInput.setVisible(false);
				home.update();
		});
		
		panel.add(addNewChecking);
		panel.add(addNewSavings);
		panel.add(addNewSecurities);
		panel.add(addNewLoans);
		panel.add(amountToOpenLabel);
		panel.add(amountToOpenWith);
		panel.add(incorrectInput);
    }
    
    public JPanel getPanel() {
    	return panel;
    }
    
    private CurrencyType getCurrencyType(String type) {
    	if(type == "USD")
    		return CurrencyType.USD;
    	else if(type == "GBP")
    		return CurrencyType.GBP;
    	else if(type == "EURO")
    		return CurrencyType.EURO;
    	else if(type == "JPY")
    		return CurrencyType.JPY;
    	else
    		return CurrencyType.CN;
    }
}
