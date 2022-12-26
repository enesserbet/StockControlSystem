import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;

public class StockForm extends JDialog implements Prodcuttwo {
    private JTextField apQty;
    private JTextField apName;
    private JTextField apBarcode;
    private JButton btnClear;
    private JButton btnSave;
    private JPanel stockPanel;


    public StockForm(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(stockPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                stockAdd();
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

    public void stockAdd() {
        String name = apName.getText();
        String qty = apQty.getText();
        String barcode = apBarcode.getText();

        if (name.isEmpty() || qty.isEmpty() || barcode.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fileds",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        product = addProductToDatabase(name, qty, barcode);
        if (product == null) {
            JOptionPane.showMessageDialog(this,"Successful saving","Success",JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(this,
                    "Successful saving",
                    "Success",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void registerUser() {

    }


    public Product product;

    private Product addProductToDatabase (String name, String qty, String barcode) {
        Product product = null;
        final String DB_URL = "jdbc:mysql://localhost/MyStore?servetTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO product(name, qty, barcode)" +
                    "VALUES(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, qty);
            preparedStatement.setString(3, barcode);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                Product product1 = new Product();
                product.name = name;
                product.qty = qty;
                product.barcode = barcode;
                /*HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(product.name, name);
                hashMap.put(product.qty, qty);
                hashMap.put(product.barcode, barcode);

                 */

            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return product;
    }


    public static void main(String[] args) {
        StockForm myForm = new StockForm(null);
        Product product = myForm.product;
        if (product == null) {
            System.out.println("Successful saving" + product.name);
        } else {
            System.out.println("Saving cancelled");
        }
    }

}



