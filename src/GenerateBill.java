
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Shivam mahajan
 */
public class GenerateBill extends javax.swing.JFrame {

    int grandTotal = 0;
    ArrayList<CartBean> al = new ArrayList<>();

    /**
     * Creates new form GenerateBill
     */
    public GenerateBill() {
        initComponents();
        showCategories();
        setLocationRelativeTo(null);
    }

    void showCategories() {
        int count = 0;
        try {

            ResultSet rs = DBLoader.executeSql("select * from pos.categories");
            while (rs.next()) {

                String name = rs.getString("name");
                String photo = rs.getString("photo");

                JButton bt1 = new JButton();
                bt1.setText(name);

                try {
                    BufferedImage bi = ImageIO.read(new File(photo));

                    bi = scale(bi, 140, 80);

                    bt1.setIcon(new ImageIcon(bi));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                bt1.setHorizontalTextPosition(SwingConstants.CENTER);
                bt1.setVerticalTextPosition(SwingConstants.BOTTOM);

                bt1.setBounds(10, 140 * count, 105, 120);

                jPanel1.add(bt1);
                jPanel1.revalidate();
                jPanel1.repaint();

                bt1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showProducts(name);
                    }
                });

                count++;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        jPanel1.setPreferredSize(new Dimension(120, 135 * count));
    }

    public static BufferedImage scale(BufferedImage src, int w, int h) {
        BufferedImage img
                = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++) {
            ys[y] = y * hh / h;
        }
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

    void showProducts(String catname) {
        int count = 0;

        try {

            jPanel2.removeAll();

            ResultSet rs = DBLoader.executeSql(
                    "select * from pos.products where cat = '" + catname + "'"
            );

            while (rs.next()) {

                String productname = rs.getString("name");
                String photo = rs.getString("photo1");

                JButton bt2 = new JButton();
                bt2.setText(productname);

                try {
                    BufferedImage bi = ImageIO.read(new File(photo));
                    bi = scale(bi, 140, 80);
                    bt2.setIcon(new ImageIcon(bi));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                bt2.setHorizontalTextPosition(SwingConstants.CENTER);
                bt2.setVerticalTextPosition(SwingConstants.BOTTOM);

                jPanel2.add(bt2);

                bt2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addToCart(productname);
                    }
                });

                count++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        jPanel2.setPreferredSize(new Dimension(120, 135 * count));
        jPanel2.revalidate();
        jPanel2.repaint();
    }

    void addToCart(String productname) {
        String ans = JOptionPane.showInputDialog(this, "Enter Quantity");

        if (ans == null) {
            // user pressed Cancel
            return;
        }
        ans = ans.trim();
        if (ans.isEmpty()) {
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(ans);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Enter valid quantity");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid number");
            return;
        }
        try {
            ResultSet rs = DBLoader.executeSql(
                    "select * from pos.products where name = '" + productname + "' "
            );

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Product not found");
                return;
            }

            int databaseQuantity = rs.getInt("qty");

            if (databaseQuantity == 0) {
                JOptionPane.showMessageDialog(this, "Out of stock");
                return;
            }

            if (databaseQuantity < quantity) {
                JOptionPane.showMessageDialog(this, "Quantity exceeds available stock");
                return;
            }

            // update DB
            rs.updateInt("qty", databaseQuantity - quantity);
            rs.updateRow();

            String catname = rs.getString("cat");
            int price = rs.getInt("price");

            int perTotal = quantity * price;

            grandTotal += perTotal;
            jLabel1.setText("Grand Total: ₹" + grandTotal);

            Global.gtotal = grandTotal;

            al.add(new CartBean(
                    productname,
                    catname,
                    quantity + "",
                    price + "",
                    perTotal + ""
            ));

            javax.swing.table.DefaultTableModel model
                    = (javax.swing.table.DefaultTableModel) jTable1.getModel();

            model.addRow(new Object[]{
                productname,
                catname,
                quantity,
                price,
                perTotal
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product", "Category", "Qty", "Price", "Total"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setViewportView(jPanel1);

        jScrollPane3.setViewportView(jPanel2);

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Generate Bill");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jButton2))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new FillDetails(al).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        AdminHome ah = new AdminHome();
        ah.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GenerateBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GenerateBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GenerateBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GenerateBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GenerateBill().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
