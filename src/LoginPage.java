import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;  

public class LoginPage extends JFrame {
	
	private JPanel f;
	private JTextField usernameInput;
	private JPasswordField passwordInput;
	private JButton loginButton;
	private JLabel notValid;
	private JButton createUserButton;
	
	private String username = "";
	private String password = "";


	public LoginPage () {  
	    f = new JPanel(); 
	    f.setLayout(null);
	    
	    JLabel usernameLabel = new JLabel("Username:");
	    usernameInput = new JTextField(""); 
	    usernameLabel.setBounds(20, 100, 80, 30);    
	    usernameInput.setBounds(100, 100, 100, 30);
	    
	    f.add(usernameLabel);
	    f.add(usernameInput);
	       
	    JLabel passwordLabel = new JLabel("Password:");
	    passwordInput = new JPasswordField();
	    passwordLabel.setBounds(20, 150, 80, 30);    
	    passwordInput.setBounds(100, 150, 100, 30);  
	    
        f.add(passwordLabel);  
        f.add(passwordInput);
        
        loginButton = new JButton("Login");  
        loginButton.setBounds(100, 250, 80, 30); 
        
        f.add(loginButton);
        
        createUserButton = new JButton("Create New User");  
        createUserButton.setBounds(100, 300, 150, 30); 
        
        f.add(createUserButton);
        
        notValid = new JLabel("Incorrect username or password");
		notValid.setForeground(Color.RED);
		notValid.setBounds(100, 200, 200, 30);
		notValid.setVisible(false);
		f.add(notValid);
   
	    f.setSize(400,400);  
	    f.setLayout(null); 
	}
	
	public void display(boolean turnOn) {
		if(turnOn) {
			f.setVisible(true);
		}
		else
			f.setVisible(false);
	}
	
	public JTextField getUsernameField() {
		return usernameInput;
	}
	
	public String getUsernameFieldString() {
		return usernameInput.getText();
	}
	
	public JPasswordField getPasswordField() {
		return passwordInput;
	}
	
	public String getPasswordFieldString() {
		return new String(passwordInput.getPassword());
	}
	
	public JLabel notValidUser() {
		return notValid;
	}
	
	public JPanel getFrame() {
		return f;
	}
	
	public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getCreateUserButton() {
        return createUserButton;
    }

    public JLabel getNotValid() {
        return notValid;
    }

}
