import com.mysql.cj.log.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class LoginForm extends JDialog {
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel loginPanel;
    private JButton btnRegister;

    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAuthenticatedUser(email, password);

                if (user != null) {
                    dispose();
                    try {
                        WelcomingForm myForm = new WelcomingForm(null);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid",
                            "Try Again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegistrationForm myForm = new RegistrationForm(null);

            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public User user;

    private User getAuthenticatedUser(String email, String password) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost/MyStore?servetTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                ArrayList<String> users = new ArrayList<String>();

                //user.name = resultSet.getString("name");
                //user.email = resultSet.getString("email");
                //user.phone = resultSet.getString("phone");
                //user.address = resultSet.getString("address");
                //user.password = resultSet.getString("password");

                users.add(Integer.parseInt(user.name), resultSet.getString("name"));
                users.add(Integer.parseInt(user.phone), resultSet.getString("phone"));
                users.add(Integer.parseInt(user.email), resultSet.getString("email"));
                users.add(Integer.parseInt(user.address), resultSet.getString("address"));
                users.add(Integer.parseInt(user.password), resultSet.getString("password"));
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return user;
    }

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;

        if (user != null) {
            System.out.println("Successfull Authentication of:" + user.name);
            System.out.println("        Email:" + user.email);
            System.out.println("        Phone:" + user.phone);
            System.out.println("        Address" + user.address);
        } else {
            System.out.println("Authentication canceled");
        }
    }
}
