import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;

public class StartUpPage extends JFrame {
	
    private Container c;
    private CardLayout cards;
    private JButton logoutButton;
    private JButton closeAccount;
    private BankManager bankUserList;

    public StartUpPage(BankManager temp) {
        JFrame frame = new JFrame();
        bankUserList = temp;
        

        frame.setTitle("Bank");
        frame.setSize(700, 700);
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        c = frame.getContentPane();
        cards = new CardLayout();

        c.setLayout(cards);

        addLogin();
        addRegister();

        frame.setVisible(true);
    }

    private void addLogin() {
        LoginPage login = new LoginPage();

        login.getLoginButton().addActionListener(e -> {
            String[] usernameAndPin = {login.getUsernameFieldString(), login.getPasswordFieldString()};
            if (bankUserList.contains(usernameAndPin[0], usernameAndPin[1])) {
            	if(usernameAndPin[0].contains("admin")) {
            		Customer user = bankUserList.login(usernameAndPin[0], usernameAndPin[1]);
    	            BankManagerHomePage bankHome = new BankManagerHomePage(user, bankUserList);
    	            c.add(bankHome.getTabbedPane(), "BankHome");
    	            addLogout(bankHome);
    	            addCloseAccount(bankHome);
                    cards.show(c, "BankHome");
                    bankHome.getTable().addMouseListener(new MouseAdapter() {
                	    public void mouseClicked(final MouseEvent e) {
                	        if (e.getClickCount() == 1) {
                	            JTable target = (JTable)e.getSource();
                	            int row = target.getSelectedRow();
                	            int column = target.getSelectedColumn();
                	            String userId = target.getValueAt(row, column).toString();
                	            Customer selectedCustomer = bankUserList.getCustomer(userId);
                	            if(selectedCustomer != null) {
	                	            UserHomePage home = new UserHomePage(selectedCustomer, bankUserList);
	                	            c.add(home.getTabbedPane(), "Home");
	                	            addLogoutManager(home);
	                	            addCloseAccountManager(home);
	                                cards.show(c, "Home");
                	            }
                	        }
                	    }
                	});
            	}
            	else {
	                Customer user = bankUserList.login(usernameAndPin[0], usernameAndPin[1]);
		            UserHomePage home = new UserHomePage(user, bankUserList);
		            c.add(home.getTabbedPane(), "Home");
		            addLogout(home);
		            addCloseAccount(home);
	                cards.show(c, "Home");
            	}
            } 
            else {
            	login.notValidUser().setVisible(true);
            }
        });

        login.getCreateUserButton().addActionListener(e -> cards.show(c, "Register"));

        c.add(login.getFrame(), "Login");
    }

    private void addRegister() {
        NewUserPage register = new NewUserPage(bankUserList);

        register.getRegisterButton().addActionListener(e -> {
            if(!register.getAdminCreate().isSelected() && register.userCreated()) {
            	Customer user = register.getCreatedCustomer();
            	UserHomePage home = new UserHomePage(user, bankUserList);
            	c.add(home.getTabbedPane(), "Home");
            	addLogout(home);
            	addCloseAccount(home);
                cards.show(c, "Home");
            } 
            else if(register.getAdminCreate().isSelected() && register.userCreated()) {
            	cards.show(c, "Login");
            }
            else {
                register.getErrorLabel().setVisible(true);
            }
        });

        register.getGoToLoginButton().addActionListener(e -> cards.show(c, "Login"));

        c.add(register.getPanel(), "Register");
    }
    
    private void addLogout(UserHomePage h) {
    	logoutButton = h.getLogoutButton();
        logoutButton.addActionListener(e -> cards.show(c, "Login"));
    }
    
    private void addCloseAccount(UserHomePage h) {
    	closeAccount = h.getCloseAccountButton();
    	closeAccount.addActionListener(e -> {
    		cards.show(c, "Login");
    		bankUserList.removeCustomer(h.getCurrentUser());
    	});
    }
    
    private void addLogout(BankManagerHomePage h) {
    	logoutButton = h.getLogoutButton();
        logoutButton.addActionListener(e -> cards.show(c, "Login"));
    }
    
    private void addCloseAccount(BankManagerHomePage h) {
    	closeAccount = h.getCloseAccountButton();
    	closeAccount.addActionListener(e -> {
    		cards.show(c, "Login");
    		bankUserList.removeCustomer(h.getCurrentUser());
    	});
    }
    
    private void addLogoutManager(UserHomePage h) {
    	logoutButton = h.getLogoutButton();
        logoutButton.addActionListener(e -> cards.show(c, "BankHome"));
    }
    
    private void addCloseAccountManager(UserHomePage h) {
    	closeAccount = h.getCloseAccountButton();
    	closeAccount.addActionListener(e -> {
    		cards.show(c, "BankHome");
    		bankUserList.removeCustomer(h.getCurrentUser());
    	});
    }
    
}
