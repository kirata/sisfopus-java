/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisfopus;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HERU
 */
public class FormTransaksiPeminjaman extends javax.swing.JFrame {
    KoneksiDB kon = new KoneksiDB();
    private Object [] [] dataPeminjaman=null;
    private String [] label = {"Kode Peminjaman","Kode Member","Buku","Mulai Pinjam","Akhir Masa Pinjam","Status Pinjam"};
    
    
    /**
     * Creates new form Peminjaman
     */
    public FormTransaksiPeminjaman() {
        initComponents();
        kon.setKoneksi();
        BacaTabelPeminjaman();
        nonaktif();
       
    }
    
    public String KodeBuku;
    public String JudulBuku;
    public String PenulisBuku;
    public String PenerbitBuku;
    /**
     * @return the KodeBuku
     */
    public String getKodeBuku() {
        return KodeBuku;
    }

    /**
     * @return the JudulBuku
     */
    public String getJudulBuku() {
        return JudulBuku;
    }

    /**
     * @return the PenulisBuku
     */
    public String getPenulisBuku() {
        return PenulisBuku;
    }

    /**
     * @return the PenerbitBuku
     */
    public String getPenerbitBuku() {
        return PenerbitBuku;
    }
    
    public String generateKodePeminjaman() {
        String urutan = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        String uniqdate = sdf.format(date);
        try {
            kon.rs = kon.st.executeQuery(
                    "SELECT RIGHT(kode_peminjaman,3)+1 FROM peminjaman_pengembalian as nomor "
                    + "WHERE kode_peminjaman LIKE '"+uniqdate+"%' "
                    + "ORDER BY kode_peminjaman DESC");
            if (kon.rs.next()) {
                urutan=kon.rs.getString(1);
                while (urutan.length()<3) {
                    urutan="00"+urutan;
                    urutan="TRANS"+sdf.format(date)+urutan;
                    
                }
            }
            else {
                urutan = "TRANS"+sdf.format(date)+"001";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        return urutan;
    }
    private void bersih()
    {
        tKdPinjam.setText("");
        tNim.setText("");
//        tKdMember.setText("");
        tNamaMember.setText("");
        tKdBuku.setText("");
        tJudulBuku.setText("");
        tPenulisBuku.setText("");
        tPenerbitBuku.setText("");
        tTglPinjam.setText("");
        tTglKembali.setText("");
        
    }
    private void nonaktif()
    {
        tKdPinjam.setEnabled(false);
        tNim.setEnabled(false);
        tKdMember.setEnabled(false);
        tNamaMember.setEnabled(false);
        tKdBuku.setEnabled(false);
        tJudulBuku.setEnabled(false);
        tPenulisBuku.setEnabled(false);
        tPenerbitBuku.setEnabled(false);
        tTglPinjam.setEnabled(false);
        tTglKembali.setEnabled(false);
    }
    private void aktif()
    {
        tKdMember.setEnabled(true);
    }
    
    
    
    private void BacaTabelPeminjaman()
    {
        try{
            String sql="SELECT * FROM peminjaman_pengembalian ORDER BY kode_peminjaman DESC";
            kon.rs=kon.st.executeQuery(sql);
            ResultSetMetaData m=kon.rs.getMetaData();
            int kolom=m.getColumnCount();
            int baris=0;
            while(kon.rs.next()){
                baris=kon.rs.getRow();
            }
            dataPeminjaman=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
                dataPeminjaman[x][0]=kon.rs.getString("kode_peminjaman");
                dataPeminjaman[x][1]=kon.rs.getString("kode_member");
                dataPeminjaman[x][2]=kon.rs.getString("kode_buku");
                dataPeminjaman[x][3]=kon.rs.getString("waktu_mulai_peminjaman");
                dataPeminjaman[x][4]=kon.rs.getString("waktu_akhir_peminjaman");
                dataPeminjaman[x][5]=kon.rs.getString("status_pinjam");
                x++;
            }
            tbPeminjaman.setModel(new DefaultTableModel(dataPeminjaman,label));


        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void SetFormPinjam(){
        try{
            String sql="SELECT * FROM member"
                    + " WHERE kode_member = '"+tKdMember.getText()+"'"
                    + " ORDER BY kode_member DESC"
                    + " LIMIT 1";
            kon.rs=kon.st.executeQuery(sql);
            ResultSetMetaData m=kon.rs.getMetaData();
            int kolom=m.getColumnCount();
            int baris=0;
            while(kon.rs.next()){
                baris=kon.rs.getRow();
            }
            dataPeminjaman=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
                dataPeminjaman[x][0]=kon.rs.getString("kode_member");
                dataPeminjaman[x][1]=kon.rs.getString("nama_member");
                dataPeminjaman[x][2]=kon.rs.getString("nim_member");
                dataPeminjaman[x][3]=kon.rs.getString("telepon_member");
                x++;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uniq = sdf.format(new Date());
            
            String namaMember = (String) dataPeminjaman[0][1];
            String nimMember = (String) dataPeminjaman[0][2];
            String tanggalPinjam = uniq;
            String tanggalKembali = "";
            
            tNamaMember.setText(namaMember);
            tNim.setText(nimMember);
            tTglPinjam.setText(tanggalPinjam);
            tTglKembali.setText(tanggalKembali);
            
//            tNim.setText(dataPeminjaman[0][1]);


        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
        private void SimpanData()
        {
            try{
                String sql=" INSERT INTO buku(kode_buku,judul_buku,penerbit_buku,penulis_buku,stok_buku) "
                        + "VALUES('"+tKdBuku.getText()+"',"
                        +"'"+tJudulBuku.getText()+"','"
                        + ""+tPenerbitBuku.getText()+"',"
                        + "'"+tPenulisBuku.getText()+"',"
                        +"'"+tPenulisBuku.getText()+"')";
                kon.st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data Buku Berhasil Disimpan");
                bersih();
                BacaTabelPeminjaman();
//                System.out.println(sql);
            }
            catch(SQLException e)
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        tKdBuku = new javax.swing.JTextField();
        btBrowseBuku = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        tJudulBuku = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tPenulisBuku = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tPenerbitBuku = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        tNamaMember = new javax.swing.JTextField();
        tKdMember = new javax.swing.JTextField();
        tKdPinjam = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tTglPinjam = new javax.swing.JTextField();
        tTglKembali = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tNim = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPeminjaman = new javax.swing.JTable();
        btProsesPeminjaman = new javax.swing.JButton();
        btReset = new javax.swing.JButton();
        btKeluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Form Peminjaman");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Buku", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel7.setText("Kode Buku");

        btBrowseBuku.setText("...");
        btBrowseBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBrowseBukuActionPerformed(evt);
            }
        });

        jLabel8.setText("Judul Buku");

        jLabel10.setText("Penulis Buku");

        jLabel11.setText("Penerbit Buku");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tKdBuku)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btBrowseBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(44, 44, 44)
                        .addComponent(tPenulisBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tPenerbitBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(tPenulisBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(tKdBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btBrowseBuku)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(tPenerbitBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(tJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Peminjam", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tKdMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tKdMemberActionPerformed(evt);
            }
        });

        jLabel2.setText("Nomor Transaksi Peminjaman");

        jLabel3.setText("Kode Member");

        jLabel4.setText("Nama Member");

        jLabel6.setText("Tgl Kembali");

        jLabel5.setText("Tgl Pinjam");

        jLabel9.setText("NIM");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tNamaMember)
                            .addComponent(tKdMember))
                        .addGap(36, 36, 36))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tKdPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tTglPinjam)
                    .addComponent(tTglKembali, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                    .addComponent(tNim))
                .addGap(27, 27, 27))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(tKdPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(tNim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tKdMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(tTglPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tNamaMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(tTglKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tabel Peminjaman", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tbPeminjaman.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbPeminjaman);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(343, 343, 343))
        );

        btProsesPeminjaman.setText("TAMBAH");
        btProsesPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProsesPeminjamanActionPerformed(evt);
            }
        });

        btReset.setText("RESET");
        btReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btResetActionPerformed(evt);
            }
        });

        btKeluar.setText("KELUAR");
        btKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btProsesPeminjaman)
                                .addGap(18, 18, 18)
                                .addComponent(btReset)
                                .addGap(18, 18, 18)
                                .addComponent(btKeluar))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btProsesPeminjaman)
                    .addComponent(btReset)
                    .addComponent(btKeluar))
                .addGap(17, 17, 17)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btProsesPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btProsesPeminjamanActionPerformed
        // TODO add your handling code here:
        if(btProsesPeminjaman.getText().equals("TAMBAH"))
        {
            btProsesPeminjaman.setText("PROSES");
            btKeluar.setText("BATAL");
            bersih();
            aktif();
            tKdPinjam.setText(generateKodePeminjaman());
            tKdMember.setEnabled(true);
            tKdMember.requestFocus();
        }
        else if(btProsesPeminjaman.getText().equals("PROSES"))
        {
            SimpanData();
            BacaTabelPeminjaman();
            btProsesPeminjaman.setText("TAMBAH");
            btKeluar.setText("KELUAR");
            bersih();
            nonaktif();
        }
    }//GEN-LAST:event_btProsesPeminjamanActionPerformed

    private void btResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btResetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btResetActionPerformed

    private void btBrowseBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBrowseBukuActionPerformed
        // TODO add your handling code here:
        boolean closable = true;
        DialogBuku browseBuku = new DialogBuku(null, closable);
        browseBuku.setVisible(true);
        browseBuku.setResizable(true);
        
        tKdBuku.setText(KodeBuku);
        tJudulBuku.setText(JudulBuku);
        tPenulisBuku.setText(PenulisBuku);
        tPenerbitBuku.setText(PenerbitBuku);
        
    }//GEN-LAST:event_btBrowseBukuActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        nonaktif();
    }//GEN-LAST:event_formWindowActivated

    private void tKdMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tKdMemberActionPerformed
        // TODO add your handling code here:
        SetFormPinjam();
//        tKdMember.setEnabled(false);

    }//GEN-LAST:event_tKdMemberActionPerformed

    private void btKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btKeluarActionPerformed

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
            java.util.logging.Logger.getLogger(FormTransaksiPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTransaksiPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTransaksiPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTransaksiPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTransaksiPeminjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBrowseBuku;
    private javax.swing.JButton btKeluar;
    private javax.swing.JButton btProsesPeminjaman;
    private javax.swing.JButton btReset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tJudulBuku;
    private javax.swing.JTextField tKdBuku;
    private javax.swing.JTextField tKdMember;
    private javax.swing.JTextField tKdPinjam;
    private javax.swing.JTextField tNamaMember;
    private javax.swing.JTextField tNim;
    private javax.swing.JTextField tPenerbitBuku;
    private javax.swing.JTextField tPenulisBuku;
    private javax.swing.JTextField tTglKembali;
    private javax.swing.JTextField tTglPinjam;
    private javax.swing.JTable tbPeminjaman;
    // End of variables declaration//GEN-END:variables

    
}
