/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.WarnaTable5;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;


/**
 *
 * @author perpustakaan
 */
public final class RMKlasifikasiRobson extends javax.swing.JDialog {
    private final DefaultTableModel tabMode, tabModeRobson;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private DlgCariDokter dokter = new DlgCariDokter(null,false);
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMKlasifikasiRobson(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Tanggal","HPHT","HPL","Diagnosa","Populasi Bumil","Kesimpulan","NIP","Pemeriksa"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 11; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(160);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(120);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRobson = new DefaultTableModel(null,new Object[]{
            "Kelompok","Populasi Ibu Hamil","Checklist"
        }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    boolean a = false;
                    if (colIndex==2) {
                        a=true;
                    }
                    return a;
             }
            Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.Boolean.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbKlasifikasi.setModel(tabModeRobson);
        tbKlasifikasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbKlasifikasi.getColumnModel()
             .getColumn(0)
             .setCellRenderer(centerRenderer);
        
        for (int i = 0; i < 3; i++) {
            TableColumn column = tbKlasifikasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(60);
            }else if(i==1){
                column.setPreferredWidth(690);
            }else if(i==2){
                column.setPreferredWidth(50);
            }
        }
        
        tbKlasifikasi.setRowHeight(30);
        tbKlasifikasi.setDefaultRenderer(Object.class, new WarnaTable5());
        tabModeRobson.addRow(new Object[]{"1","Wanita Nullipara, janin tunggal, presentasi kepala, usia kehamilan ≥ 37 minggu, dalam persalinan spontan",false});
        tabModeRobson.addRow(new Object[]{"2","Wanita Nullipara, janin tunggal, presentasi kepala, usia kehamilan ≥ 37 minggu dengan induksi persalinan atau dilakukan SC sebelum dalam persalinan (emergensi)",false});
        tabModeRobson.addRow(new Object[]{"3","Wanita Multipara, tanpa riwayat operasi uterus sebelumnya, janin tunggal, presentasi kepala, usia kehamilan ≥ 37 minggu, dalam persalinan spontan",false});
        tabModeRobson.addRow(new Object[]{"4","Wanita Multipara, dengan riwayat operasi uterus sebelumnya, janin tunggal, presentasi kepala, usia kehamilan ≥ 37 minggu dengan induksi persalinan atau dilakukan SC sebelum dalam persalinan (emergensi)",false});
        tabModeRobson.addRow(new Object[]{"5","Wanita Multipara, dengan riwayat operasi uterus sebelumnya, janin tunggal, presentasi kepala, usia kehamilan ≥ 37 minggu",false});
        tabModeRobson.addRow(new Object[]{"6","Semua Wanita Nullipara dengan janin tunggal sungsang",false});
        tabModeRobson.addRow(new Object[]{"7","Semua Wanita Multipara dengan janin tunggal sungsang",false});
        tabModeRobson.addRow(new Object[]{"8","Semua Wanita hamil dengan janin Multipel",false});
        tabModeRobson.addRow(new Object[]{"9","Semua Wanita hamil dengan janin tunggal, dengan posisi janin oblik atau melintang",false});
        tabModeRobson.addRow(new Object[]{"10","Semua Wanita hamil dengan janin tunggal, presentasi kepala, usia kehamilan ≤ 36 minggu",false});
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                   
                    KdPemeriksa.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    TNmPemeriksa.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }   
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        ChkInput.setSelected(true);
        isForm();
        BtnPrint.setVisible(false);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        mnCetak = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel24 = new widget.Label();
        TglHPHT = new widget.Tanggal();
        jLabel25 = new widget.Label();
        TglHPL = new widget.Tanggal();
        jLabel17 = new widget.Label();
        TNmPemeriksa = new widget.TextBox();
        jLabel18 = new widget.Label();
        TDiagnosa = new widget.TextBox();
        KdPemeriksa = new widget.TextBox();
        BtnCariPemeriksa = new widget.Button();
        ScrollRobson = new widget.ScrollPane();
        tbKlasifikasi = new widget.Table();
        jLabel20 = new widget.Label();
        TKesimpulan = new widget.TextBox();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setForeground(new java.awt.Color(50, 50, 50));
        jPopupMenu1.setAutoscrolls(true);
        jPopupMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPopupMenu1.setFocusTraversalPolicyProvider(true);
        jPopupMenu1.setName("jPopupMenu1"); // NOI18N
        jPopupMenu1.setPreferredSize(new java.awt.Dimension(202, 557));

        mnCetak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        mnCetak.setForeground(new java.awt.Color(50, 50, 50));
        mnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        mnCetak.setText("Cetak Klasifikasi Robson");
        mnCetak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnCetak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnCetak.setName("mnCetak"); // NOI18N
        mnCetak.setPreferredSize(new java.awt.Dimension(200, 26));
        mnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCetakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnCetak);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Klasifikasi ROBSON ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 100));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-02-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-02-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 500));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 400));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 110, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(120, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(380, 10, 420, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-02-2026" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalActionPerformed(evt);
            }
        });
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(120, 40, 110, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(265, 10, 112, 23);

        jLabel16.setText("Pemeriksa :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(390, 70, 80, 23);

        jLabel24.setText("HPL :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(460, 40, 30, 23);

        TglHPHT.setForeground(new java.awt.Color(50, 70, 50));
        TglHPHT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-02-2026" }));
        TglHPHT.setDisplayFormat("dd-MM-yyyy");
        TglHPHT.setName("TglHPHT"); // NOI18N
        TglHPHT.setOpaque(false);
        TglHPHT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglHPHTKeyPressed(evt);
            }
        });
        FormInput.add(TglHPHT);
        TglHPHT.setBounds(320, 40, 110, 23);

        jLabel25.setText("HPHT :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(270, 40, 40, 23);

        TglHPL.setForeground(new java.awt.Color(50, 70, 50));
        TglHPL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-02-2026" }));
        TglHPL.setDisplayFormat("dd-MM-yyyy");
        TglHPL.setName("TglHPL"); // NOI18N
        TglHPL.setOpaque(false);
        TglHPL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglHPLKeyPressed(evt);
            }
        });
        FormInput.add(TglHPL);
        TglHPL.setBounds(500, 40, 110, 23);

        jLabel17.setText("Tanggal  Periksa :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 40, 110, 23);

        TNmPemeriksa.setEditable(false);
        TNmPemeriksa.setHighlighter(null);
        TNmPemeriksa.setName("TNmPemeriksa"); // NOI18N
        TNmPemeriksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmPemeriksaKeyPressed(evt);
            }
        });
        FormInput.add(TNmPemeriksa);
        TNmPemeriksa.setBounds(595, 70, 170, 23);

        jLabel18.setText("Diagnosa :");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel18);
        jLabel18.setBounds(30, 70, 80, 23);

        TDiagnosa.setHighlighter(null);
        TDiagnosa.setName("TDiagnosa"); // NOI18N
        TDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(TDiagnosa);
        TDiagnosa.setBounds(120, 70, 260, 23);

        KdPemeriksa.setEditable(false);
        KdPemeriksa.setHighlighter(null);
        KdPemeriksa.setName("KdPemeriksa"); // NOI18N
        KdPemeriksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPemeriksaKeyPressed(evt);
            }
        });
        FormInput.add(KdPemeriksa);
        KdPemeriksa.setBounds(480, 70, 110, 23);

        BtnCariPemeriksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCariPemeriksa.setName("BtnCariPemeriksa"); // NOI18N
        BtnCariPemeriksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPemeriksaActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPemeriksa);
        BtnCariPemeriksa.setBounds(770, 70, 30, 22);

        ScrollRobson.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150)));
        ScrollRobson.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollRobson.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        ScrollRobson.setHorizontalScrollBar(null);
        ScrollRobson.setName("ScrollRobson"); // NOI18N

        tbKlasifikasi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbKlasifikasi.setAutoscrolls(false);
        tbKlasifikasi.setGridColor(new java.awt.Color(150, 150, 150));
        tbKlasifikasi.setName("tbKlasifikasi"); // NOI18N
        tbKlasifikasi.setRowHeight(150);
        tbKlasifikasi.getTableHeader().setResizingAllowed(false);
        tbKlasifikasi.getTableHeader().setReorderingAllowed(false);
        ScrollRobson.setViewportView(tbKlasifikasi);

        FormInput.add(ScrollRobson);
        ScrollRobson.setBounds(10, 100, 800, 320);

        jLabel20.setText("Kesimpulan dari Klasifikasi Robson :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel20);
        jLabel20.setBounds(10, 440, 180, 23);

        TKesimpulan.setHighlighter(null);
        TKesimpulan.setName("TKesimpulan"); // NOI18N
        TKesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKesimpulanKeyPressed(evt);
            }
        });
        FormInput.add(TKesimpulan);
        TKesimpulan.setBounds(200, 440, 600, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        int checkrobson = 0;
        int posisiRobson = -1;
        for(int x = 0; x<10; x++){
            if(tbKlasifikasi.getValueAt(x, 2).equals(true)){
                checkrobson = checkrobson + 1;
                posisiRobson = x;
            }
        }
        if(TNoRw.getText().trim().equals("")||TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(!Tanggal.isValid()){
            Valid.textKosong(Tanggal, "Tanggal Periksa");
        }else if(!TglHPHT.isValid()){
            Valid.textKosong(TglHPHT, "Tanggal HPHT");
        }else if(!TglHPL.isValid()){
            Valid.textKosong(TglHPL, "Tanggal HPL");
        }else if(TDiagnosa.getText().trim().equals("")){
            Valid.textKosong(TDiagnosa, "Diagnosa");
        }else if(KdPemeriksa.getText().trim().equals("") || TNmPemeriksa.getText().trim().equals("")){
            Valid.textKosong(KdPemeriksa, "Petugas Pemeriksa");
        }else if(checkrobson == 0){
            JOptionPane.showMessageDialog(null,"Kelompok Populasi Ibu hamil belum dipilih...!!!");
        }else if(checkrobson > 1){
            JOptionPane.showMessageDialog(null,"Kelompok Populasi Ibu hamil silahkan pilih salah satu saja...!!!");
        }else{
            if(Sequel.menyimpantf("klasifikasi_robson", "'"+TNoRw.getText()+"','"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"','"+Valid.SetTgl(TglHPHT.getSelectedItem()+"")+"','"+
                Valid.SetTgl(TglHPL.getSelectedItem()+"")+"','"+TDiagnosa.getText()+"','"+tbKlasifikasi.getValueAt(posisiRobson, 0).toString()+"','"+TKesimpulan.getText()+"','"+
                KdPemeriksa.getText()+"'", "Data / No Rawat")==true){
                tampil();
                emptTeks();
            }   
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            if(Sequel.queryu2tf("delete from klasifikasi_robson where no_rawat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tabMode.removeRow(tbObat.getSelectedRow());
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
            }
        }            
            
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        int checkrobson = 0;
        int posisiRobson = -1;
        for(int x = 0; x<10; x++){
            if(tbKlasifikasi.getValueAt(x, 2).equals(true)){
                checkrobson = checkrobson + 1;
                posisiRobson = x;
            }
        }
        if(TNoRw.getText().trim().equals("")||TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(!Tanggal.isValid()){
            Valid.textKosong(Tanggal, "Tanggal Periksa");
        }else if(!TglHPHT.isValid()){
            Valid.textKosong(TglHPHT, "Tanggal HPHT");
        }else if(!TglHPL.isValid()){
            Valid.textKosong(TglHPL, "Tanggal HPL");
        }else if(TDiagnosa.getText().trim().equals("")){
            Valid.textKosong(TDiagnosa, "Diagnosa");
        }else if(KdPemeriksa.getText().trim().equals("") || TNmPemeriksa.getText().trim().equals("")){
            Valid.textKosong(KdPemeriksa, "Petugas Pemeriksa");
        }else if(checkrobson == 0){
            JOptionPane.showMessageDialog(null,"Kelompok Populasi Ibu hamil belum dipilih...!!!");
        }else if(checkrobson > 1){
            JOptionPane.showMessageDialog(null,"Kelompok Populasi Ibu hamil silahkan pilih salah satu saja...!!!");
        }else{
            Valid.editTable(tabMode, "klasifikasi_robson", "no_rawat", TNoRw, "tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")
                    +"', hpht='"+Valid.SetTgl(TglHPHT.getSelectedItem()+"")
                    +"', hpl='"+Valid.SetTgl(TglHPL.getSelectedItem()+"")
                    +"', diagnosa='"+TDiagnosa.getText()
                    +"', populasi_bumil='"+tbKlasifikasi.getValueAt(posisiRobson, 0).toString()
                    +"', kesimpulan='"+TKesimpulan.getText()
                    +"', nip='"+KdPemeriksa.getText()+"'");
            if(tabMode.getRowCount()!=0){tampil();}
            emptTeks();
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            if(TCari.getText().equals("")){ 
                Valid.MyReportqry("rptDataHemodialisa.jasper","report","::[ Data Hemodialis ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,hemodialisa.tanggal,hemodialisa.lama,hemodialisa.akses,hemodialisa.dialist,hemodialisa.transfusi,hemodialisa.penarikan, "+
                    "hemodialisa.qb,hemodialisa.qd,hemodialisa.ureum,hemodialisa.hb,hemodialisa.hbsag,creatinin,hemodialisa.hiv,hemodialisa.hcv,hemodialisa.lain, "+
                    "hemodialisa.kd_dokter,dokter.nm_dokter,hemodialisa.kd_penyakit,penyakit.nm_penyakit "+
                    "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on hemodialisa.kd_dokter=dokter.kd_dokter "+
                    "inner join penyakit on hemodialisa.kd_penyakit=penyakit.kd_penyakit where "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' order by hemodialisa.tanggal ",param);
            }else{
                Valid.MyReportqry("rptDataHemodialisa.jasper","report","::[ Data Hemodialis ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,hemodialisa.tanggal,hemodialisa.lama,hemodialisa.akses,hemodialisa.dialist,hemodialisa.transfusi,hemodialisa.penarikan, "+
                    "hemodialisa.qb,hemodialisa.qd,hemodialisa.ureum,hemodialisa.hb,hemodialisa.hbsag,creatinin,hemodialisa.hiv,hemodialisa.hcv,hemodialisa.lain, "+
                    "hemodialisa.kd_dokter,dokter.nm_dokter,hemodialisa.kd_penyakit,penyakit.nm_penyakit "+
                    "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on hemodialisa.kd_dokter=dokter.kd_dokter "+
                    "inner join penyakit on hemodialisa.kd_penyakit=penyakit.kd_penyakit where "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and hemodialisa.akses like '%"+TCari.getText().trim()+"%' or "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and hemodialisa.dialist like '%"+TCari.getText().trim()+"%' or "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and hemodialisa.lain like '%"+TCari.getText().trim()+"%' or "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' "+
                    "order by hemodialisa.tanggal ",param);
            }  
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        
}//GEN-LAST:event_TanggalKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                emptTeks();
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    emptTeks();
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TglHPHTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglHPHTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglHPHTKeyPressed

    private void TanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalActionPerformed

    private void TglHPLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglHPLKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglHPLKeyPressed

    private void TNmPemeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmPemeriksaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNmPemeriksaKeyPressed

    private void TDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDiagnosaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDiagnosaKeyPressed

    private void KdPemeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPemeriksaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPemeriksaKeyPressed

    private void BtnCariPemeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnCariPemeriksaActionPerformed

    private void TKesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKesimpulanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKesimpulanKeyPressed

    private void mnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCetakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnCetakActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMKlasifikasiRobson dialog = new RMKlasifikasiRobson(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariPemeriksa;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdPemeriksa;
    private widget.Label LCount;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane ScrollRobson;
    private widget.TextBox TCari;
    private widget.TextBox TDiagnosa;
    private widget.TextBox TKesimpulan;
    private widget.TextBox TNmPemeriksa;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TglHPHT;
    private widget.Tanggal TglHPL;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JMenuItem mnCetak;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbKlasifikasi;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien, klasifikasi_robson.*, dokter.nm_dokter "+
                    "from klasifikasi_robson inner join reg_periksa on klasifikasi_robson.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on klasifikasi_robson.nip=dokter.kd_dokter where "+
                    "klasifikasi_robson.tanggal between ? and ? order by klasifikasi_robson.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien, klasifikasi_robson.*, dokter.nm_dokter "+
                    "from klasifikasi_robson inner join reg_periksa on klasifikasi_robson.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on klasifikasi_robson.nip=dokter.kd_dokter where "+
                    "klasifikasi_robson.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                    "klasifikasi_robson.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                    "klasifikasi_robson.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                    "klasifikasi_robson.tanggal between ? and ? and klasifikasi_robson.diagnosa like ? or "+
                    "klasifikasi_robson.tanggal between ? and ? and klasifikasi_robson.populasi_bumil like ? or "+
                    "klasifikasi_robson.tanggal between ? and ? and klasifikasi_robson.kesimpulan like ? or "+
                    "klasifikasi_robson.tanggal between ? and ? and dokter.nm_dokter like ? "+
                    "order by klasifikasi_robson.tanggal ");
            }
                
            try {
                if(TCari.getText().toString().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()));
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()));
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem().toString()));
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem().toString()));
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem().toString()));
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem().toString()));
                    ps.setString(15,"%"+TCari.getText()+"%");
                    ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem().toString()));
                    ps.setString(18,"%"+TCari.getText()+"%");
                    ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem().toString()));
                    ps.setString(21,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tanggal"),
                        rs.getString("hpht"),rs.getString("hpl"),rs.getString("diagnosa"),rs.getString("populasi_bumil"),
                        rs.getString("kesimpulan"),rs.getString("nip"),rs.getString("nm_dokter")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
        Tanggal.setDate(new Date());
        TglHPHT.setDate(new Date());
        TglHPL.setDate(new Date());
        TDiagnosa.setText("");
        TKesimpulan.setText("");
        KdPemeriksa.setText("");
        TNmPemeriksa.setText("");
        for(int v=0 ; v<10 ; v++){
            tbKlasifikasi.setValueAt(false, v, 2);
        }
        Tanggal.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Valid.SetTgl(TglHPHT,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Valid.SetTgl(TglHPL,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            TDiagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            tbKlasifikasi.setValueAt(true, Integer.parseInt(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString())-1, 2);
            TKesimpulan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            KdPemeriksa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            TNmPemeriksa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            ChkInput.setSelected(true);
            isForm();
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }
    
    public void setNoRm(String norwt, Date tgl) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl);
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,500));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getklasifikasi_robson());
        BtnHapus.setEnabled(akses.getklasifikasi_robson());
        BtnEdit.setEnabled(akses.getklasifikasi_robson());
        BtnPrint.setEnabled(akses.getklasifikasi_robson()); 
        if(Sequel.cariIsiBoolean("select kd_dokter from dokter where kd_dokter='"+akses.getkode()+"' ")){
            KdPemeriksa.setText(akses.getkode());
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter='"+akses.getkode()+"' ", TNmPemeriksa);
            BtnCariPemeriksa.setVisible(false);
        } else {
            BtnCariPemeriksa.setVisible(true);
        }
    }
    
}
