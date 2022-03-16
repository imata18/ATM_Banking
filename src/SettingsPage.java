import java.util.ArrayList;
import javax.swing.*;  


public class SettingsPage extends JPanel {

    private JTabbedPane tabbedPane;
    
    private JButton logoutButton;
    private JButton closeAccount;
    
    private UserHomePage home;
    private Customer user;
    
    private JPanel panel;
    
    public SettingsPage(Customer inputUser) {
    	
		user = inputUser;
		panel = new JPanel();
		panel.setLayout(null);
		
		tabbedPane = new JTabbedPane();
    	
		logoutButton = new JButton("Logout");  
		logoutButton.setBounds(100, 150, 80, 30); 
		
		closeAccount = new JButton("Close Account");  
		closeAccount.setBounds(100, 200, 120, 30); 
		
		panel.add(logoutButton);
		panel.add(closeAccount);
		
    }
    
    public JButton getLogoutButton() {
		return logoutButton;
	}
    
    public JButton getCloseAccountButton() {
		return closeAccount;
	}
    
    public JPanel getPanel() {
    	return panel;
    }
}
