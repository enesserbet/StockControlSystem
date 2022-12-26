import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserListForm extends JDialog {
    private JPanel listPanel;
    private JTextField ulSearch;
    private JButton btnOK;
    private JButton btnClear;
    private JTable ulTable;
    private JButton btnList;

    Connection con;


    public UserListForm(JFrame parent) throws SQLException {
        super(parent);
        setTitle("USER LIST");
        setContentPane(listPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //setVisible(true);
        createTable();

        final String DB_URL = "jdbc:mysql://localhost/MyStore?servetTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";


        try {
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                String email = ulSearch.getText();

                String sql = "SELECT * FROM `users` WHERE email = '" + email + "'";
                try {
                    PreparedStatement pst = con.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) ulTable.getModel();
                    model.setRowCount(0);
                    ulTable.clearSelection();
                    while (rs.next()) {
                        model.addRow(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String sql = "SELECT * FROM `users`";
                try {
                    PreparedStatement pst = con.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) ulTable.getModel();
                    model.setRowCount(0);
                    ulTable.clearSelection();
                    while (rs.next()) {
                        model.addRow(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6)});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    WelcomingForm myForm = new WelcomingForm(null);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        setVisible(true);

    }

    private void createTable() {
        ulTable.setModel(new DefaultTableModel(
                null,
                new String[]{"id", "Name", "Email", "Phone", "Address"}
        ));
    }


    public Product product;

    public static void main(String[] args) {
        try {
            UserListForm myForm = new UserListForm(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

