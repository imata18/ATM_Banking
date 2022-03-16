import java.awt.Color;
import javax.swing.*;

public class NewUserPage extends JFrame {

	private JPanel panel;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JButton registerButton;
    private JLabel errorLabel;
    private JRadioButton admin;
    private JButton goToLoginMenuButton;
    
    private BankManager customerList;
    private Customer customer;

    public NewUserPage(BankManager usersList) {
        panel = new JPanel();
        panel.setLayout(null);
        customerList = usersList;
        customer = null;

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(200, 200, 80, 25);
        panel.add(userLabel);

        usernameInput = new JTextField(20);
        usernameInput.setBounds(300, 200, 165, 25);
        panel.add(usernameInput);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(200, 250, 80, 25);
        panel.add(passwordLabel);
        
        admin = new JRadioButton("Create admin account login?");
        admin.setBounds(200, 140, 280, 40);
        panel.add(admin);

        passwordInput = new JPasswordField(20);
        passwordInput.setBounds(300, 250, 165, 25);
        panel.add(passwordInput);

        registerButton = new JButton("Register");
        registerButton.setBounds(270, 310, 90, 40);
        panel.add(registerButton);

        goToLoginMenuButton = new JButton("Already have an account? \nLogin.");
        goToLoginMenuButton.setBounds(170, 370, 280, 40);
        panel.add(goToLoginMenuButton);

        errorLabel = new JLabel("User already exists. Please login.");
        errorLabel.setBounds(210, 420, 280, 40);
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        panel.add(errorLabel);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getGoToLoginButton() {
        return goToLoginMenuButton;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }
    
    public JRadioButton getAdminCreate() {
    	return admin;
    }

    public boolean userCreated() {
        String username = usernameInput.getText();
        String password = new String(passwordInput.getPassword());
        if(!customerList.contains(username, password)) {
        	customer = customerList.createAccount(username, username, password);
    		return true;
        }
        else
        	return false;
    }
    
    public Customer getCreatedCustomer() {
    	return customer;
    }
}
