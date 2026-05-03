import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminLogin {

    JFrame frame;
    JTextField emailField;
    JPasswordField passField;
    JButton loginButton;

    public AdminLogin() {

        frame = new JFrame("Admin Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel title = new JLabel("ADMIN LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(120, 20, 200, 30);
        panel.add(title);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(60, 80, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(140, 80, 180, 25);
        panel.add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(60, 120, 80, 25);
        panel.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(140, 120, 180, 25);
        panel.add(passField);

        loginButton = new JButton("LOGIN");
        loginButton.setBounds(140, 170, 100, 30);
        panel.add(loginButton);

        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String email = emailField.getText();
                String password = new String(passField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter Email and Password");
                    return;
                }

                try {
                    String sql = "SELECT * FROM admin WHERE email='" 
                               + email + "' AND password='" + password + "'";

                    ResultSet rs = DBLoader.executeSql(sql);

                    if (rs != null && rs.next()) {

                        Global.adminemail = email;

                        JOptionPane.showMessageDialog(frame, "Login Successful");

                        new AdminHome().setVisible(true);
                        frame.dispose(); 

                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Email or Password");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}