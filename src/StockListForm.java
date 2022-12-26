import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class StockListForm extends JDialog {
    private JTextField slSearch;
    private JButton btnOK;
    private JTable slTable;
    private JButton btnClear;
    private JPanel listPanel;
    private JButton btnList;
    private JTextField slName;
    private JTextField slQty;
    private JTextField slBarcode;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JTextField slID;
    private JScrollPane jTable1;

    Connection con;


    public StockListForm(JFrame parent) throws SQLException {
        super(parent);
        setTitle("STOCK LIST");
        setContentPane(listPanel);
        setMinimumSize(new Dimension(700, 474));
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

                String name = slSearch.getText();

                String sql = "SELECT * FROM `product` WHERE name = '" + name + "'";
                try {
                    PreparedStatement pst = con.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) slTable.getModel();
                    model.setRowCount(0);
                    slTable.clearSelection();
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
                String sql = "SELECT * FROM `product`";
                try {
                    PreparedStatement pst = con.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) slTable.getModel();
                    model.setRowCount(0);
                    slTable.clearSelection();
                    while (rs.next()) {
                        model.addRow(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        slTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {


                try {
                    int row = slTable.getSelectedRow();
                    String Table_click = (slTable.getModel().getValueAt(row, 0).toString());
                    String sql = "SELECT * FROM product WHERE id = " + Table_click + "";
                    PreparedStatement pst = con.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        String add1 = rs.getString("id");
                        slID.setText(String.valueOf(add1));
                        String add2 = rs.getString("name");
                        slName.setText(add2);
                        String add3 = rs.getString("qty");
                        slQty.setText(add3);
                        String add4 = rs.getString("barcode");
                        slBarcode.setText(add4);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }


            }


        });


        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int row = slTable.getSelectedRow();
                String Table_click = (slTable.getModel().getValueAt(row, 0).toString());

                String name = slName.getText();
                String qty = slQty.getText();
                String barcode = slBarcode.getText();

                //String sql = "UPDATE `product` SET id="+id+",name='"+name+"',qty="+qty+",barcode= "+barcode+" WHERE id="+id+"";
                String sql = "UPDATE `product` SET `id`='" + Table_click + "',`name`='" + name + "',`qty`='" + qty + "',`barcode`='" + barcode + "' WHERE id=" +Table_click + "";
                try {
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Updated");
                    System.out.println(sql);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int row = slTable.getSelectedRow();
                String Table_click = (slTable.getModel().getValueAt(row, 0).toString());
                String sql = "DELETE FROM product WHERE id =" +Table_click + "";
                try {
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Deleted");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
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
        slTable.setModel(new DefaultTableModel(
                null,
                new String[]{"id", "Name", "Product QTY", "Barcode"}
        ));
    }


    public Product product;

    public static void main(String[] args) {
        try {
            StockListForm myForm = new StockListForm(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
