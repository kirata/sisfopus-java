/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisfopus;

import java.awt.event.KeyEvent;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cosnors
 */
public class FormMasterBuku extends javax.swing.JFrame {
    KoneksiDB kon = new KoneksiDB();
    private Object [] [] dataBuku=null;
    private String [] label = {"Kode Buku","Judul Buku","Penerbit Buku","Penulis Buku","Stok Buku"};
    /**
     * Creates new form FormBuku
     */
    public FormMasterBuku() {
        initComponents();
        kon.setKoneksi();
        BacaTabelBuku();
        nonaktif();
    }
    public String generateKodeBuku() {
        String urutan = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        String uniqdate = sdf.format(date);
        try {
            kon.rs = kon.st.executeQuery(
                    "SELECT RIGHT(kode_buku,3)+1 FROM buku as nomor "
                    + "WHERE kode_buku LIKE '"+uniqdate+"%' "
                    + "ORDER BY kode_buku DESC");
            if (kon.rs.next()) {
                urutan=kon.rs.getString(1);
                while (urutan.length()<3) {
                    urutan="00"+urutan;
                    urutan="BK"+sdf.format(date)+urutan;
                    
                }
            }
            else {
                urutan = "BK"+sdf.format(date)+"001";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        return urutan;
    }
    private void BacaTabelBuku()
    {
        try{
            String sql="SELECT * FROM buku ORDER BY kode_buku";
            kon.rs=kon.st.executeQuery(sql);
            ResultSetMetaData m=kon.rs.getMetaData();
            int kolom=m.getColumnCount();
            int baris=0;
            while(kon.rs.next()){
                baris=kon.rs.getRow();
            }
            dataBuku=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
                dataBuku[x][0]=kon.rs.getString("kode_buku");
                dataBuku[x][1]=kon.rs.getString("judul_buku");
                dataBuku[x][2]=kon.rs.getString("penerbit_buku");
                dataBuku[x][3]=kon.rs.getString("penulis_buku");
                dataBuku[x][4]=kon.rs.getString("stok_buku");
                x++;
            }
            tblBuku.setModel(new DefaultTableModel(dataBuku,label));


        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
        private void BacaTabelBukuCari()
        {
            try{
                String sql="SELECT * FROM buku WHERE judul_buku LIKE '%"+tCari.getText()+"%'";
                kon.rs=kon.st.executeQuery(sql);
                ResultSetMetaData m=kon.rs.getMetaData();
                int kolom=m.getColumnCount();
                int baris=0;
                while(kon.rs.next()){
                    baris=kon.rs.getRow();
            }
                dataBuku=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
                dataBuku[x][0]=kon.rs.getString("kode_buku");
                dataBuku[x][1]=kon.rs.getString("judul_buku");
                dataBuku[x][2]=kon.rs.getString("penerbit_buku");
                dataBuku[x][3]=kon.rs.getString("penulis_buku");
                dataBuku[x][4]=kon.rs.getString("stok_buku");
                x++;
        }
            tblBuku.setModel(new DefaultTableModel(dataBuku,label));
    }
            catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
        private void SetTabel()
        {
            int row=tblBuku.getSelectedRow();
            tKdBuku.setText((String)tblBuku.getValueAt(row, 0));
            tJudulBuku.setText((String)tblBuku.getValueAt(row, 1));
            tPenerbitBuku.setText((String)tblBuku.getValueAt(row, 2));
            tPenulisBuku.setText((String)tblBuku.getValueAt(row, 3));
            tStokBuku.setText((String)tblBuku.getValueAt(row, 4));
        }
        private void Bersih()
        {
            tKdBuku.setText("");
            tJudulBuku.setText("");
            tPenerbitBuku.setText("");
            tPenulisBuku.setText("");
            tStokBuku.setText("");
        }
        private void aktif()
        {
            tKdBuku.setEnabled(true);
            tJudulBuku.setEnabled(true);
            tPenerbitBuku.setEnabled(true);
            tPenulisBuku.setEnabled(true);
            tStokBuku.setEnabled(true);
        }
        private void nonaktif()
        {
//            tKdBuku.setEnabled(false);
            tJudulBuku.setEnabled(false);
            tPenerbitBuku.setEnabled(false);
            tPenulisBuku.setEnabled(false);
            tStokBuku.setEnabled(false);
        }
        private void SimpanData()
        {
            try{
                String sql=" INSERT INTO buku(kode_buku,judul_buku,penerbit_buku,penulis_buku,stok_buku) "
                        + "VALUES('"+tKdBuku.getText()+"',"
                        +"'"+tJudulBuku.getText()+"','"
                        + ""+tPenerbitBuku.getText()+"',"
                        + "'"+tPenulisBuku.getText()+"',"
                        +"'"+tStokBuku.getText()+"')";
                kon.st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data Buku Berhasil Disimpan");
                Bersih();
                BacaTabelBuku();
//                System.out.println(sql);
            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        private void UpdateData()
        {
            try{
                String sql = "UPDATE buku SET judul_buku='"+tJudulBuku.getText()+"',"
                        +"penerbit_buku='"+tPenerbitBuku.getText()+"',"
                        +"penulis_buku='"+tPenulisBuku.getText()+"',"
                        +"stok_buku='"+tStokBuku.getText()+"' WHERE kode_buku='"+tKdBuku.getText()+"'";
                kon.st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null,"Data Buku Berhasil Di Update");
                Bersih();
                BacaTabelBuku();
            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        private void HapusData()
        {
            try{
                String sql="DELETE FROM buku WHERE kode_buku = '"+tKdBuku.getText()+"'";
                kon.st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data Buku Berhasil Dihapus");
                Bersih();
                BacaTabelBuku();
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(null, e);
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

        jLabel3 = new javax.swing.JLabel();
        btKeluar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tKdBuku = new javax.swing.JTextField();
        tJudulBuku = new javax.swing.JTextField();
        tPenerbitBuku = new javax.swing.JTextField();
        tPenulisBuku = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tStokBuku = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBuku = new javax.swing.JTable();
        btTambah = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("INPUT DATA BUKU");

        btKeluar.setText("KELUAR");
        btKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKeluarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATA BUKU", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 11))); // NOI18N

        jLabel4.setText("Kode Buku");

        jLabel5.setText("Judul Buku");

        jLabel6.setText("Penerbit Buku");

        jLabel7.setText("Penulis Buku");

        tKdBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tKdBukuKeyPressed(evt);
            }
        });

        tJudulBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tJudulBukuKeyPressed(evt);
            }
        });

        tPenerbitBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tPenerbitBukuKeyPressed(evt);
            }
        });

        jLabel8.setText("Stok Buku");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tStokBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tPenulisBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tPenerbitBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tKdBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tKdBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tPenerbitBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tPenulisBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tStokBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Data Buku");

        jLabel2.setText("Cari Buku");

        tCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tCariKeyTyped(evt);
            }
        });

        tblBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBukuMouseClicked(evt);
            }
        });
        tblBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblBukuKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblBuku);

        btTambah.setText("TAMBAH");
        btTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(btTambah)
                                            .addGap(432, 432, 432)
                                            .addComponent(btKeluar)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(235, 235, 235)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(283, 283, 283)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btTambah)
                    .addComponent(btKeluar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKeluarActionPerformed
        if(btKeluar.getText().equals("KELUAR"))
        {
            dispose();
        }
        else if(btKeluar.getText().equals("BATAL"))
        {
            Bersih();
            nonaktif();
            btKeluar.setText("KELUAR");
            btTambah.setText("TAMBAH");
        }

    }//GEN-LAST:event_btKeluarActionPerformed

    private void tKdBukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tKdBukuKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            try{
                String sql ="SELECT * FROM buku WHERE kode_buku='"+tKdBuku.getText()+"'";
                kon.rs=kon.st.executeQuery(sql);
                if (kon.rs.next()){
                    tKdBuku.setEnabled(false);
                    tKdBuku.setText(kon.rs.getString("kode_buku"));
                    tJudulBuku.setText(kon.rs.getString("judul_buku"));
                    tPenerbitBuku.setText(kon.rs.getString("penerbit_buku"));
                    tPenulisBuku.setText(kon.rs.getString("penulis_buku"));
                    tStokBuku.setText(kon.rs.getString("stok_buku"));
                    aktif();
                    btTambah.setText("UPDATE");
                }else{
                    tJudulBuku.requestFocus();
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_tKdBukuKeyPressed

    private void tJudulBukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tJudulBukuKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tPenerbitBuku.requestFocus();
        }
    }//GEN-LAST:event_tJudulBukuKeyPressed

    private void tPenerbitBukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tPenerbitBukuKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tPenulisBuku.requestFocus();
        }
    }//GEN-LAST:event_tPenerbitBukuKeyPressed

    private void tCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tCariKeyTyped
        // TODO add your handling code here:
        kon.setKoneksi();
        BacaTabelBukuCari();
    }//GEN-LAST:event_tCariKeyTyped

    private void tblBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBukuMouseClicked
        SetTabel();
    }//GEN-LAST:event_tblBukuMouseClicked

    private void tblBukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblBukuKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            HapusData();
        }
    }//GEN-LAST:event_tblBukuKeyPressed

    private void btTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTambahActionPerformed
        if(btTambah.getText().equals("TAMBAH"))
        {
            btTambah.setText("SIMPAN");
            btKeluar.setText("BATAL");
            Bersih();
            aktif();
            tKdBuku.setText(generateKodeBuku());
            tKdBuku.setEnabled(false);
        }
        else if(btTambah.getText().equals("SIMPAN"))
        {
            SimpanData();
            BacaTabelBuku();
            btTambah.setText("TAMBAH");
            btKeluar.setText("KELUAR");
            Bersih();
            nonaktif();
        }
        else if(btTambah.getText().equals("UPDATE"))
        {
            UpdateData();
            BacaTabelBuku();
            btTambah.setText("TAMBAH");
            btKeluar.setText("KELUAR");
            Bersih();
            nonaktif();
        }
    }//GEN-LAST:event_btTambahActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormMasterBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMasterBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMasterBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMasterBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMasterBuku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btKeluar;
    private javax.swing.JButton btTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tCari;
    private javax.swing.JTextField tJudulBuku;
    private javax.swing.JTextField tKdBuku;
    private javax.swing.JTextField tPenerbitBuku;
    private javax.swing.JTextField tPenulisBuku;
    private javax.swing.JTextField tStokBuku;
    private javax.swing.JTable tblBuku;
    // End of variables declaration//GEN-END:variables
}
