import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class WelcomingForm extends JDialog {
    private JButton btnSAP;
    private JButton btnSLP;
    private JButton btnUAP;
    private JButton btnULP;
    private JButton btnExit;
    private JPanel welcomingPanel;

    public WelcomingForm(JFrame parent) throws SQLException {
        super(parent);
        setTitle("WELCOMING PAGE");
        setContentPane(welcomingPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //setVisible(true);
        btnSAP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                StockForm stockForm = new StockForm(null);
            }
        });
        btnSLP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    StockListForm myForm = new StockListForm(null);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                //stockListForm.setVisible(true);
            }
        });
        btnUAP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegistrationForm registrationForm = new RegistrationForm(null);
            }
        });
        btnULP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    UserListForm myForm = new UserListForm(null);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            WelcomingForm myForm = new WelcomingForm(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
