import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.SystemColor;
import java.awt.Color;
 
 
public class LoginFrame extends javax.swing.JDialog {
    private JButton connectToChatJB;
    private JPasswordField passwordTF;
    private JTextField usernameTF;
    private JTextField usernameJL;
    private JTextField passwordJL;
     
    public String getUsername() {
        return this.usernameTF.getText();
    }
     
    public String getPassword() {
        return this.passwordTF.getText();
    }
     
    public LoginFrame(JFrame frame) {
        super(frame);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        loginGUI();
    }
     
    private void loginGUI() {
        try {
            getContentPane().setLayout(null);
            {
                usernameTF = new JTextField();
                getContentPane().add(usernameTF);
                usernameTF.setBounds(197, 26, 149, 28);
                usernameTF.setText("");
}
{
    passwordTF = new JPasswordField();
    getContentPane().add(passwordTF);
    passwordTF.setBounds(197, 77, 149, 28);
    passwordTF.setText("");
}
{
    connectToChatJB = new JButton();
    connectToChatJB.setBackground(Color.WHITE);
    getContentPane().add(connectToChatJB);
    connectToChatJB.setText("Connect To Chat");
    connectToChatJB.setBounds(197, 134, 223, 22);
    connectToChatJB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
        	if(usernameTF == null){
        		System.out.println("Please Enter a username with a value of 1 or more");
        	}
        	if(passwordTF == null){
        		System.out.println("Please Enter a password with a value of 1 or more");
        	}
        	if((usernameTF) != null){
        	//return username.
            usernameTF.setText(usernameTF.getText());
        	}
        	if((passwordTF) != null){
            //return password.
            passwordTF.setText(passwordTF.getText());                      
        	}
        	
           //Hide the Frame once it is used.
            setVisible(false);
            
        }
    });
    {
    	usernameJL = new JTextField();
    	usernameJL.setEditable(false);
    	usernameJL.setText("Username >>");
    	usernameJL.setBounds(39, 28, 97, 24);
    	getContentPane().add(usernameJL);
    	usernameJL.setColumns(10);
    }
    {
    	passwordJL = new JTextField();
    	passwordJL.setEditable(false);
    	passwordJL.setText("Password >>");
                	passwordJL.setBounds(39, 79, 97, 24);
                	getContentPane().add(passwordJL);
                	passwordJL.setColumns(10);
                }
                
            }
            this.setSize(451, 208);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}