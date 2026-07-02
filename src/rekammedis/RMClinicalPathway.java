/*
 * By Pandanano
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.WarnaTableAspekCP;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPegawai;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMClinicalPathway extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeAspek, tabModeVariasi;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs,rs2,rs3;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private DlgCariPegawai pegawaivariasi=new DlgCariPegawai(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private MasterCariBerkasClinicalPathway berkascp=new MasterCariBerkasClinicalPathway(null,false);
    private String finger=""; 
    private StringBuilder htmlContent;
    private boolean ignoreDocumentEvent = false;
    
    /** Creates new form RMClinicalPathway
     * @param parent
     * @param modal */
    public RMClinicalPathway(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Tgl.Lahir","Kode Diagnosa","Diagnosa","Lama Dirawat","Tgl.Asuhan","Judul Berkas","No.Revisi","Tgl.Berlaku","Catatan Khusus",
            "Kd.Dokter","Dokter","Kd.Perawat","Perawat","Kd.Petugas","Petugas Verifikasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbClinicalPathway.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbClinicalPathway.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbClinicalPathway.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbClinicalPathway.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setPreferredWidth(0);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(60);
            }else if(i==9){
                column.setPreferredWidth(300);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(60);
            }else if(i==12){
                column.setPreferredWidth(300);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(120);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else if(i==16){
                column.setPreferredWidth(120);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==18){
                column.setPreferredWidth(120);
            }
        }
        tbClinicalPathway.setDefaultRenderer(Object.class, new WarnaTable());

        Object[] rowAspek = {"No.Urut","Lvl.List","Kosongi?","No","","Aspek Pelayanan","H-1"};
        tabModeAspek=new DefaultTableModel(null,rowAspek){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                String kosongi = String.valueOf(getValueAt(rowIndex, 2));

                if(colIndex >= 6 && kosongi.equals("0")) {
                    return true;
                }
                
                return false;
            }
        };
        tbAspekPelayanan.setModel(tabModeAspek);
        
        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbAspekPelayanan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbAspekPelayanan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < tbAspekPelayanan.getColumnCount(); i++) {
            TableColumn columnAspek = tbAspekPelayanan.getColumnModel().getColumn(i);
            if (i <= 2) {
                columnAspek.setMinWidth(0);
                columnAspek.setMaxWidth(0);
                columnAspek.setPreferredWidth(0);
            } else if (i == 3 || i == 4) {
                columnAspek.setPreferredWidth(25);
            } else if (i == 5) {
                columnAspek.setPreferredWidth(500);
            } else {
                columnAspek.setPreferredWidth(40);
            }
        }
        
        tbAspekPelayanan.setDefaultRenderer(Object.class, new WarnaTableAspekCP());
        
        Object[] rowVariasi={"","No.Rawat","No.Berkas","Variasi Pelayanan","Tanggal","Alasan","Petugas","Tanda Tangan"};
        tabModeVariasi=new DefaultTableModel(null,rowVariasi){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return Boolean.class;
                    default: return Object.class;
                }
            }
            
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex){
                switch (colIndex){
                    case 0: return true;
                    default: return false;
                }
            }
        };
        tbVarian.setModel(tabModeVariasi);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbVarian.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbVarian.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < tbVarian.getColumnCount(); i++) {
            TableColumn columnVarian = tbVarian.getColumnModel().getColumn(i);
            if(i == 0){
                columnVarian.setPreferredWidth(30);
            }else if (i == 1 || i == 2) {
                columnVarian.setMinWidth(0);
                columnVarian.setMaxWidth(0);
                columnVarian.setPreferredWidth(0);
            } else if (i == 3) {
                columnVarian.setPreferredWidth(300);
            } else if (i == 4) {
                columnVarian.setPreferredWidth(65);
            } else if (i == 5){
                columnVarian.setPreferredWidth(210);
            } else if (i == 6){
                columnVarian.setMinWidth(0);
                columnVarian.setMaxWidth(0);
                columnVarian.setPreferredWidth(0);
            } else if (i == 7){
                columnVarian.setPreferredWidth(175);
            }
        }
        
        tbVarian.setAutoCreateRowSorter(true);
        tbVarian.setDefaultRenderer(Object.class, new WarnaTable());
        aktifkanSorting();
        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        LamaDirawat.setDocument(new batasInput((int)1).getKata(LamaDirawat));
        
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
                    KdDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());   
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    KdPerawat.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPerawat.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());   
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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){ 
                    KdPelaksana.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    NmPelaksana.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());   
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
        
        pegawaivariasi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawaivariasi.getTable().getSelectedRow()!= -1){ 
                    TKdPetugasVariasi.setText(pegawaivariasi.getTable().getValueAt(pegawaivariasi.getTable().getSelectedRow(),0).toString());
                    TNmPetugasVariasi.setText(pegawaivariasi.getTable().getValueAt(pegawaivariasi.getTable().getSelectedRow(),1).toString());   
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
        
        berkascp.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(berkascp.getTable().getSelectedRow()!= -1){ 
                    TKdDiagnosaCP.setText(berkascp.getTable().getValueAt(berkascp.getTable().getSelectedRow(),0).toString());
                    TDiagnosaCP.setText(berkascp.getTable().getValueAt(berkascp.getTable().getSelectedRow(),1).toString());
                    TJudulBerkas.setText(berkascp.getTable().getValueAt(berkascp.getTable().getSelectedRow(),2).toString());
                    TNoRevisiBerkas.setText(berkascp.getTable().getValueAt(berkascp.getTable().getSelectedRow(),3).toString());
                    TTglBerlakuBerkas.setText(berkascp.getTable().getValueAt(berkascp.getTable().getSelectedRow(),4).toString());
                    TCatatan.setText(berkascp.getTable().getValueAt(berkascp.getTable().getSelectedRow(),5).toString());
                    generateKolom();
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
        
        LamaDirawat.getDocument().addDocumentListener(
        new javax.swing.event.DocumentListener() {

        private void update() {
            if (ignoreDocumentEvent) {
                return;
            }
            
            SwingUtilities.invokeLater(() -> generateKolom());
        }

        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            update();
        }

        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            update();
        }

        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            update();
        }
    });
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        DlgVariasiPelayanan = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa1 = new widget.PanelBiasa();
        label22 = new widget.Label();
        TVariasiPelayanan = new widget.TextBox();
        label23 = new widget.Label();
        TAlasan = new widget.TextBox();
        TglVariasi = new widget.Tanggal();
        label24 = new widget.Label();
        label25 = new widget.Label();
        TKdPetugasVariasi = new widget.TextBox();
        TNmPetugasVariasi = new widget.TextBox();
        BtnPetugasVariasi = new widget.Button();
        BtnSimpanVariasi = new widget.Button();
        BtnKeluarVariasi = new widget.Button();
        jSeparator3 = new javax.swing.JSeparator();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        cetakCP = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDPJP = new widget.TextBox();
        NmDPJP = new widget.TextBox();
        BtnDPJP = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jSeparator1 = new javax.swing.JSeparator();
        label15 = new widget.Label();
        TDiagnosaCP = new widget.TextBox();
        BtnDiagnosaCP = new widget.Button();
        LamaDirawat = new widget.TextBox();
        label12 = new widget.Label();
        label17 = new widget.Label();
        label16 = new widget.Label();
        label18 = new widget.Label();
        KdPerawat = new widget.TextBox();
        NmPerawat = new widget.TextBox();
        BtnPerawat = new widget.Button();
        label19 = new widget.Label();
        KdPelaksana = new widget.TextBox();
        NmPelaksana = new widget.TextBox();
        BtnPelaksana = new widget.Button();
        label20 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        TCatatan = new widget.TextArea();
        scrollPane2 = new widget.ScrollPane();
        tbAspekPelayanan = new widget.Table();
        label21 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        scrollPane3 = new widget.ScrollPane();
        tbVarian = new widget.Table();
        BtnTambahVarian = new widget.Button();
        BtnHapusVarian = new widget.Button();
        JnsKelamin = new widget.TextBox();
        TKdDiagnosaCP = new widget.TextBox();
        TJudulBerkas = new widget.TextBox();
        label26 = new widget.Label();
        label27 = new widget.Label();
        label28 = new widget.Label();
        TNoRevisiBerkas = new widget.TextBox();
        label29 = new widget.Label();
        TTglBerlakuBerkas = new widget.TextBox();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbClinicalPathway = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        DlgVariasiPelayanan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgVariasiPelayanan.setMinimumSize(new java.awt.Dimension(760, 210));
        DlgVariasiPelayanan.setName("DlgVariasiPelayanan"); // NOI18N
        DlgVariasiPelayanan.setUndecorated(true);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Form Variasi Pelayanan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout());

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setLayout(null);

        label22.setText("Variasi Pelayanan :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        panelBiasa1.add(label22);
        label22.setBounds(0, 10, 105, 23);

        TVariasiPelayanan.setHighlighter(null);
        TVariasiPelayanan.setName("TVariasiPelayanan"); // NOI18N
        TVariasiPelayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TVariasiPelayananKeyPressed(evt);
            }
        });
        panelBiasa1.add(TVariasiPelayanan);
        TVariasiPelayanan.setBounds(110, 10, 610, 23);

        label23.setText("Alasan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        panelBiasa1.add(label23);
        label23.setBounds(0, 40, 105, 23);

        TAlasan.setHighlighter(null);
        TAlasan.setName("TAlasan"); // NOI18N
        TAlasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlasanKeyPressed(evt);
            }
        });
        panelBiasa1.add(TAlasan);
        TAlasan.setBounds(110, 40, 610, 23);

        TglVariasi.setForeground(new java.awt.Color(50, 70, 50));
        TglVariasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-06-2026" }));
        TglVariasi.setDisplayFormat("dd-MM-yyyy");
        TglVariasi.setName("TglVariasi"); // NOI18N
        TglVariasi.setOpaque(false);
        TglVariasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglVariasiKeyPressed(evt);
            }
        });
        panelBiasa1.add(TglVariasi);
        TglVariasi.setBounds(630, 70, 90, 23);

        label24.setText("Tanggal :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        panelBiasa1.add(label24);
        label24.setBounds(570, 70, 55, 23);

        label25.setText("Petugas Pelayanan :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        panelBiasa1.add(label25);
        label25.setBounds(0, 70, 105, 23);

        TKdPetugasVariasi.setEditable(false);
        TKdPetugasVariasi.setHighlighter(null);
        TKdPetugasVariasi.setName("TKdPetugasVariasi"); // NOI18N
        TKdPetugasVariasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPetugasVariasiKeyPressed(evt);
            }
        });
        panelBiasa1.add(TKdPetugasVariasi);
        TKdPetugasVariasi.setBounds(110, 70, 120, 23);

        TNmPetugasVariasi.setEditable(false);
        TNmPetugasVariasi.setHighlighter(null);
        TNmPetugasVariasi.setName("TNmPetugasVariasi"); // NOI18N
        TNmPetugasVariasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmPetugasVariasiKeyPressed(evt);
            }
        });
        panelBiasa1.add(TNmPetugasVariasi);
        TNmPetugasVariasi.setBounds(235, 70, 290, 23);

        BtnPetugasVariasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugasVariasi.setMnemonic('2');
        BtnPetugasVariasi.setToolTipText("Alt+2");
        BtnPetugasVariasi.setName("BtnPetugasVariasi"); // NOI18N
        BtnPetugasVariasi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugasVariasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasVariasiActionPerformed(evt);
            }
        });
        BtnPetugasVariasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasVariasiKeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnPetugasVariasi);
        BtnPetugasVariasi.setBounds(530, 70, 28, 23);

        BtnSimpanVariasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanVariasi.setMnemonic('S');
        BtnSimpanVariasi.setText("Simpan");
        BtnSimpanVariasi.setToolTipText("");
        BtnSimpanVariasi.setName("BtnSimpanVariasi"); // NOI18N
        BtnSimpanVariasi.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpanVariasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanVariasiActionPerformed(evt);
            }
        });
        BtnSimpanVariasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanVariasiKeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnSimpanVariasi);
        BtnSimpanVariasi.setBounds(500, 120, 100, 30);

        BtnKeluarVariasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluarVariasi.setMnemonic('K');
        BtnKeluarVariasi.setText("Tutup");
        BtnKeluarVariasi.setToolTipText("");
        BtnKeluarVariasi.setName("BtnKeluarVariasi"); // NOI18N
        BtnKeluarVariasi.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarVariasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarVariasiActionPerformed(evt);
            }
        });
        BtnKeluarVariasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarVariasiKeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnKeluarVariasi);
        BtnKeluarVariasi.setBounds(620, 120, 100, 30);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        panelBiasa1.add(jSeparator3);
        jSeparator3.setBounds(0, 105, 730, 1);

        internalFrame4.add(panelBiasa1, java.awt.BorderLayout.CENTER);

        DlgVariasiPelayanan.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        cetakCP.setBackground(new java.awt.Color(255, 255, 254));
        cetakCP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        cetakCP.setForeground(new java.awt.Color(50, 50, 50));
        cetakCP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        cetakCP.setText("Cetak Clinical Pathway");
        cetakCP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cetakCP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cetakCP.setName("MnCetakCP"); // NOI18N
        cetakCP.setPreferredSize(new java.awt.Dimension(200, 26));
        cetakCP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cetakCPMouseClicked(evt);
            }
        });
        cetakCP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakCPActionPerformed(evt);
            }
        });
        jPopupMenu1.add(cetakCP);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Clinical Pathway ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnAll);

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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1200));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(75, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(315, 10, 355, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(210, 10, 100, 23);

        label14.setText("Lama Dirawat :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(520, 40, 90, 23);

        KdDPJP.setEditable(false);
        KdDPJP.setName("KdDPJP"); // NOI18N
        KdDPJP.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDPJP);
        KdDPJP.setBounds(120, 160, 145, 23);

        NmDPJP.setEditable(false);
        NmDPJP.setName("NmDPJP"); // NOI18N
        NmDPJP.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDPJP);
        NmDPJP.setBounds(270, 160, 300, 23);

        BtnDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDPJP.setMnemonic('2');
        BtnDPJP.setToolTipText("Alt+2");
        BtnDPJP.setName("BtnDPJP"); // NOI18N
        BtnDPJP.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDPJPActionPerformed(evt);
            }
        });
        BtnDPJP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDPJPKeyPressed(evt);
            }
        });
        FormInput.add(BtnDPJP);
        BtnDPJP.setBounds(580, 160, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(710, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(780, 10, 90, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        label11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label11.setText("hari");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(670, 40, 30, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-06-2026" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(780, 40, 90, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 250, 880, 1);

        label15.setText("Penanggung jawab pasien :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(0, 140, 160, 23);

        TDiagnosaCP.setEditable(false);
        TDiagnosaCP.setName("TDiagnosaCP"); // NOI18N
        TDiagnosaCP.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(TDiagnosaCP);
        TDiagnosaCP.setBounds(210, 40, 270, 23);

        BtnDiagnosaCP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDiagnosaCP.setMnemonic('2');
        BtnDiagnosaCP.setToolTipText("Alt+2");
        BtnDiagnosaCP.setName("BtnDiagnosaCP"); // NOI18N
        BtnDiagnosaCP.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDiagnosaCP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDiagnosaCPActionPerformed(evt);
            }
        });
        BtnDiagnosaCP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDiagnosaCPKeyPressed(evt);
            }
        });
        FormInput.add(BtnDiagnosaCP);
        BtnDiagnosaCP.setBounds(490, 40, 28, 23);

        LamaDirawat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        LamaDirawat.setText("1");
        LamaDirawat.setHighlighter(null);
        LamaDirawat.setName("LamaDirawat"); // NOI18N
        FormInput.add(LamaDirawat);
        LamaDirawat.setBounds(620, 40, 50, 23);

        label12.setText("Tanggal :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(720, 40, 50, 23);

        label17.setText("Diagnosa :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(0, 40, 70, 23);

        label16.setText("Perawat :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(0, 188, 110, 23);

        label18.setText("Dokter :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(0, 160, 110, 23);

        KdPerawat.setEditable(false);
        KdPerawat.setName("KdPerawat"); // NOI18N
        KdPerawat.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPerawat);
        KdPerawat.setBounds(120, 188, 145, 23);

        NmPerawat.setEditable(false);
        NmPerawat.setName("NmPerawat"); // NOI18N
        NmPerawat.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPerawat);
        NmPerawat.setBounds(270, 188, 300, 23);

        BtnPerawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPerawat.setMnemonic('2');
        BtnPerawat.setToolTipText("Alt+2");
        BtnPerawat.setName("BtnPerawat"); // NOI18N
        BtnPerawat.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPerawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerawatActionPerformed(evt);
            }
        });
        BtnPerawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerawatKeyPressed(evt);
            }
        });
        FormInput.add(BtnPerawat);
        BtnPerawat.setBounds(580, 188, 28, 23);

        label19.setText("Pelaksana Verifikasi :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(0, 215, 110, 23);

        KdPelaksana.setEditable(false);
        KdPelaksana.setName("KdPelaksana"); // NOI18N
        KdPelaksana.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPelaksana);
        KdPelaksana.setBounds(120, 215, 145, 23);

        NmPelaksana.setEditable(false);
        NmPelaksana.setName("NmPelaksana"); // NOI18N
        NmPelaksana.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPelaksana);
        NmPelaksana.setBounds(270, 215, 300, 23);

        BtnPelaksana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPelaksana.setMnemonic('2');
        BtnPelaksana.setToolTipText("Alt+2");
        BtnPelaksana.setName("BtnPelaksana"); // NOI18N
        BtnPelaksana.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPelaksana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPelaksanaActionPerformed(evt);
            }
        });
        BtnPelaksana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPelaksanaKeyPressed(evt);
            }
        });
        FormInput.add(BtnPelaksana);
        BtnPelaksana.setBounds(580, 215, 28, 23);

        label20.setText("Catatan Khusus :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(605, 65, 90, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        TCatatan.setEditable(false);
        TCatatan.setColumns(20);
        TCatatan.setRows(5);
        TCatatan.setName("TCatatan"); // NOI18N
        scrollPane1.setViewportView(TCatatan);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(620, 85, 250, 155);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        tbAspekPelayanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAspekPelayanan.setName("tbAspekPelayanan"); // NOI18N
        scrollPane2.setViewportView(tbAspekPelayanan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(10, 260, 860, 700);

        label21.setText("Varian :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(0, 990, 70, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 980, 880, 1);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        tbVarian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbVarian.setName("tbVarian"); // NOI18N
        scrollPane3.setViewportView(tbVarian);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(80, 990, 790, 150);

        BtnTambahVarian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahVarian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnTambahVarian.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BtnTambahVarian.setName("BtnTambahVarian"); // NOI18N
        BtnTambahVarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahVarianActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahVarian);
        BtnTambahVarian.setBounds(40, 1020, 30, 30);

        BtnHapusVarian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusVarian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnHapusVarian.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BtnHapusVarian.setName("BtnHapusVarian"); // NOI18N
        BtnHapusVarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusVarianActionPerformed(evt);
            }
        });
        FormInput.add(BtnHapusVarian);
        BtnHapusVarian.setBounds(40, 1060, 30, 30);

        JnsKelamin.setEditable(false);
        JnsKelamin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JnsKelamin.setHighlighter(null);
        JnsKelamin.setName("JnsKelamin"); // NOI18N
        FormInput.add(JnsKelamin);
        JnsKelamin.setBounds(675, 10, 30, 23);

        TKdDiagnosaCP.setEditable(false);
        TKdDiagnosaCP.setName("TKdDiagnosaCP"); // NOI18N
        TKdDiagnosaCP.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(TKdDiagnosaCP);
        TKdDiagnosaCP.setBounds(75, 40, 130, 23);

        TJudulBerkas.setEditable(false);
        TJudulBerkas.setName("TJudulBerkas"); // NOI18N
        TJudulBerkas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(TJudulBerkas);
        TJudulBerkas.setBounds(120, 85, 490, 23);

        label26.setText("Judul Berkas :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(0, 85, 110, 23);

        label27.setText("Detail Berkas Clinical Pathway :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(0, 65, 160, 23);

        label28.setText("No.Revisi Berkas :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(0, 112, 110, 23);

        TNoRevisiBerkas.setEditable(false);
        TNoRevisiBerkas.setName("TNoRevisiBerkas"); // NOI18N
        TNoRevisiBerkas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(TNoRevisiBerkas);
        TNoRevisiBerkas.setBounds(120, 112, 260, 23);

        label29.setText("Tgl.Berlaku Berkas :");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(400, 112, 100, 23);

        TTglBerlakuBerkas.setEditable(false);
        TTglBerlakuBerkas.setName("TTglBerlakuBerkas"); // NOI18N
        TTglBerlakuBerkas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(TTglBerlakuBerkas);
        TTglBerlakuBerkas.setBounds(510, 112, 100, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Clinical Pathway", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbClinicalPathway.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbClinicalPathway.setComponentPopupMenu(jPopupMenu1);
        tbClinicalPathway.setName("tbClinicalPathway"); // NOI18N
        tbClinicalPathway.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbClinicalPathwayMouseClicked(evt);
            }
        });
        tbClinicalPathway.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbClinicalPathwayKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbClinicalPathway);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-06-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-06-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Clinical Pathway", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
    if(TNoRw.getText().equals("")){
        Valid.textKosong(TNoRw, "No Rawat / Pasien");
    }else if(TKdDiagnosaCP.getText().equals("")){
        Valid.textKosong(TKdDiagnosaCP, "Diagnosa Clinical Pathway");
    }else if(LamaDirawat.getText().equals("") || LamaDirawat.getText().equals("0")){
        Valid.textKosong(LamaDirawat, "Lama Dirawat");
    }else if((TglAsuhan.getSelectedItem()+"").equals("")){
        Valid.textKosong(TglAsuhan, "Tanggal Asuhan");
    }else if(KdDPJP.getText().equals("")){
        Valid.textKosong(KdDPJP, "Dokter");
    }else if(KdPerawat.getText().equals("")){
        Valid.textKosong(KdPerawat, "Perawat");
    }else if(KdPelaksana.getText().equals("")){
        Valid.textKosong(KdPelaksana, "Pelaksana Verifikasi");
    }else{
        if(Sequel.menyimpantf("clinical_pathway","?,?,?,?,?,?,?,?,?,?,?,?","Data",12,new String[]{ TNoRw.getText(),TKdDiagnosaCP.getText(),LamaDirawat.getText(),
            Valid.SetTgl(TglAsuhan.getSelectedItem()+""),TDiagnosaCP.getText(),TJudulBerkas.getText(),TNoRevisiBerkas.getText(),TTglBerlakuBerkas.getText(),TCatatan.getText(),
            KdDPJP.getText(),KdPerawat.getText(),KdPelaksana.getText() })==true){
            
            for(int rw = 0;rw < tabModeAspek.getRowCount();rw++){
                String qValue = "'"+TNoRw.getText()+"','"+TKdDiagnosaCP.getText()+"','"+tabModeAspek.getValueAt(rw, 0)+"','"+tabModeAspek.getValueAt(rw, 1)+"','"+tabModeAspek.getValueAt(rw, 2)+"','"
                        +tabModeAspek.getValueAt(rw, 3)+"','"+tabModeAspek.getValueAt(rw, 4)+"','"+tabModeAspek.getValueAt(rw, 5).toString().trim()+"'";
                for(int col = 6; col < 15; col++) {
                    if(col < tabModeAspek.getColumnCount()) {
                        Object val = tabModeAspek.getValueAt(rw, col);

                        if(val == null || val.toString().trim().isEmpty()) {
                            qValue += ",null";
                        } else {
                            qValue += ",'"+val.toString().trim()+"'";
                        }
                    } else {
                        qValue += ",null";
                    }
                    
                }
                Sequel.menyimpantf("clinical_pathway_aspek", qValue, "Data Aspek Klinis");
            }
            
            if(tabModeVariasi.getRowCount() > 0){
                for(int rwv = 0; rwv < tabModeVariasi.getRowCount();rwv ++){
                    Sequel.menyimpantf("clinical_pathway_varian", "'"+tabModeVariasi.getValueAt(rwv, 1)+"','"+tabModeVariasi.getValueAt(rwv, 2)+"','"+tabModeVariasi.getValueAt(rwv, 3)+"','"
                            +Valid.SetTgl(tabModeVariasi.getValueAt(rwv, 4)+"")+"','"+tabModeVariasi.getValueAt(rwv, 5)+"','"+tabModeVariasi.getValueAt(rwv, 6)+"'", "Data Varian");
                }
            }
            
            JOptionPane.showMessageDialog(null, "Berhasil menyimpan data Clinical Pathway!!!");
            tampil();emptTeks();
            LCount.setText(""+tabMode.getRowCount());
            }  
    }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnPelaksana,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbClinicalPathway.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdPelaksana.getText().equals(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),18).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
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
    if(TNoRw.getText().equals("")){
        Valid.textKosong(TNoRw, "No Rawat / Pasien");
    }else if(TKdDiagnosaCP.getText().equals("")){
        Valid.textKosong(TKdDiagnosaCP, "Diagnosa Clinical Pathway");
    }else if(LamaDirawat.getText().equals("") || LamaDirawat.getText().equals("0")){
        Valid.textKosong(LamaDirawat, "Lama Dirawat");
    }else if((TglAsuhan.getSelectedItem()+"").equals("")){
        Valid.textKosong(TglAsuhan, "Tanggal Asuhan");
    }else if(KdDPJP.getText().equals("")){
        Valid.textKosong(KdDPJP, "Dokter");
    }else if(KdPerawat.getText().equals("")){
        Valid.textKosong(KdPerawat, "Perawat");
    }else if(KdPelaksana.getText().equals("")){
        Valid.textKosong(KdPelaksana, "Pelaksana Verifikasi");
    }else{
        if(tbClinicalPathway.getSelectedRow()!= -1){
            if(akses.getkode().equals("Admin Utama")){
                edit();
            }else{
                if(KdPelaksana.getText().equals(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),18).toString())){
                    edit();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa diedit oleh petugas pelaksana yang bersangkutan..!!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih data yang ingin diubah terlebih dahulu!!!");
        }
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
            try{
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Diagnosa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Lama Dirawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Asuhan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Judul Berkas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Revisi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Berlaku</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Catatan Khusus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kd.Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kd.Perawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Perawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kd.Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Verifikasi</b></td>"+
                    "</tr>"
                );
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,0).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,1).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,2).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,3).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,4).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,5).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,6).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,7).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,8).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,9).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,10).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,11).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,12).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,13).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,14).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,15).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,16).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,17).toString()+"</td>"+
                            "<td valign='top'>"+tbClinicalPathway.getValueAt(i,18).toString()+"</td>"+
                        "</tr>");
                }

                LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                    ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                    ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                    ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                    ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                );
                bg.close();

                File f = new File("DataPenilaianTerapiWicara.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA CLINICAL PATHWAY<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());

            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbClinicalPathwayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbClinicalPathwayMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbClinicalPathway.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbClinicalPathwayMouseClicked

    private void tbClinicalPathwayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbClinicalPathwayKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbClinicalPathwayKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void BtnDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDPJPKeyPressed
        //Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_BtnDPJPKeyPressed

    private void BtnDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDPJPActionPerformed
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDPJPActionPerformed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnDPJP);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void BtnDiagnosaCPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDiagnosaCPActionPerformed
        berkascp.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        berkascp.setLocationRelativeTo(internalFrame1);
        berkascp.setAlwaysOnTop(false);
        berkascp.setVisible(true);
    }//GEN-LAST:event_BtnDiagnosaCPActionPerformed

    private void BtnDiagnosaCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDiagnosaCPKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnDiagnosaCPActionPerformed(null);
        }else{
            Valid.pindah(evt, LamaDirawat, TglAsuhan);
        }
    }//GEN-LAST:event_BtnDiagnosaCPKeyPressed

    private void BtnPerawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerawatActionPerformed
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPerawatActionPerformed

    private void BtnPerawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPerawatKeyPressed

    private void BtnPelaksanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPelaksanaActionPerformed
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnPelaksanaActionPerformed

    private void BtnPelaksanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPelaksanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPelaksanaKeyPressed

    private void TVariasiPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TVariasiPelayananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TVariasiPelayananKeyPressed

    private void TAlasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlasanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TAlasanKeyPressed

    private void TglVariasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglVariasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglVariasiKeyPressed

    private void TKdPetugasVariasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPetugasVariasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKdPetugasVariasiKeyPressed

    private void TNmPetugasVariasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmPetugasVariasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNmPetugasVariasiKeyPressed

    private void BtnPetugasVariasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasVariasiActionPerformed
        // TODO add your handling code here:
        pegawaivariasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawaivariasi.setLocationRelativeTo(internalFrame1);
        pegawaivariasi.setAlwaysOnTop(false);
        pegawaivariasi.setVisible(true);
    }//GEN-LAST:event_BtnPetugasVariasiActionPerformed

    private void BtnPetugasVariasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasVariasiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPetugasVariasiActionPerformed(null);
        }else{
            Valid.pindah(evt, TglVariasi, BtnSimpanVariasi);
        }
    }//GEN-LAST:event_BtnPetugasVariasiKeyPressed

    private void BtnSimpanVariasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanVariasiActionPerformed
        if(TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pilih terlebih dahulu pasien yang mau ditambahkan Variasi Pelayanannya...!");
        }else if(TVariasiPelayanan.getText().equals("")){
            Valid.textKosong(TVariasiPelayanan, "Variasi Pelayanan");
        }else if(TAlasan.getText().equals("")){
            Valid.textKosong(TAlasan, "Alasan Variasi Pelayanan");
        }else if(TKdPetugasVariasi.getText().equals("")){
            Valid.textKosong(TKdPetugasVariasi, "Petugas Variasi");
        }else {
            tabModeVariasi.addRow(new Object[]{false,TNoRw.getText().trim(),TKdDiagnosaCP.getText().trim(),TVariasiPelayanan.getText(),
                TglVariasi.getSelectedItem()+"",TAlasan.getText(),TKdPetugasVariasi.getText(),TKdPetugasVariasi.getText()+" "+TNmPetugasVariasi.getText()});
            TVariasiPelayanan.setText("");
            TAlasan.setText("");
            TKdPetugasVariasi.setText("");
            TNmPetugasVariasi.setText("");
            BtnKeluarVariasiActionPerformed(null);
        }
    }//GEN-LAST:event_BtnSimpanVariasiActionPerformed

    private void BtnSimpanVariasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanVariasiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanVariasiActionPerformed(null);
        }else{
            Valid.pindah(evt, TglVariasi, BtnKeluarVariasi);
        }
    }//GEN-LAST:event_BtnSimpanVariasiKeyPressed

    private void BtnKeluarVariasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarVariasiActionPerformed
        // TODO add your handling code here:
        DlgVariasiPelayanan.dispose();
    }//GEN-LAST:event_BtnKeluarVariasiActionPerformed

    private void BtnKeluarVariasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarVariasiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarVariasiKeyPressed(null);
        }else{
            Valid.pindah(evt, BtnSimpanVariasi, TVariasiPelayanan);
        }
    }//GEN-LAST:event_BtnKeluarVariasiKeyPressed

    private void BtnTambahVarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahVarianActionPerformed
        // TODO add your handling code here:
        if(TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pilih terlebih dahulu pasien yang mau ditambahkan Variasi Pelayanannya...!");
        }else{
            //emptFormVariasi();
            DlgVariasiPelayanan.setSize(760, 225);
            DlgVariasiPelayanan.setLocationRelativeTo(internalFrame1);
            DlgVariasiPelayanan.setVisible(true);
        }
    }//GEN-LAST:event_BtnTambahVarianActionPerformed

    private void BtnHapusVarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusVarianActionPerformed
        for (int i = tabModeVariasi.getRowCount() - 1; i >= 0; i--) {
            Boolean checked = (Boolean) tabModeVariasi.getValueAt(i, 0);

            if (checked != null && checked) {
                tabModeVariasi.removeRow(i);
            }
        }
    }//GEN-LAST:event_BtnHapusVarianActionPerformed

    private void cetakCPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cetakCPMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cetakCPMouseClicked

    private void cetakCPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakCPActionPerformed
        if(tbClinicalPathway.getSelectedRow()!= -1){
            try {
                htmlContent = new StringBuilder();
                rs2=koneksi.prepareStatement(
                        "select clinical_pathway.*, dokter.nm_dokter, petugas.nama as nm_perawat, pegawai.nama as nm_pelaksana "+
                        "from clinical_pathway inner join reg_periksa on reg_periksa.no_rawat=clinical_pathway.no_rawat "+
                        "inner join master_berkas_cp on master_berkas_cp.no_berkas=clinical_pathway.no_berkas "+
                        "inner join dokter on dokter.kd_dokter=clinical_pathway.dokter "+
                        "inner join petugas on petugas.nip=clinical_pathway.perawat "+
                        "inner join pegawai on pegawai.nik=clinical_pathway.pelaksana "+
                        "where clinical_pathway.no_rawat='"+tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),0).toString()+"'").executeQuery();
                if(rs2.next()){
                    rs2.beforeFirst();
                    if(rs2.next()){
                        htmlContent.append(
                            "<tr>"+
                                "<td valign='top'>"+
                                    "<table width='100%' border='1' align='center' cellpadding='3px' cellspacing='0' class='isi'>"+
                                        "<tr class='isi4 head1'>"+
                                            "<td valign='top' colspan='3' align='center' bgcolor='#FFFAF8'>ALUR KLINIS (CLINICAL PATHWAY)<br>"+rs2.getString("judul_cp").toUpperCase()+"</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                            "<td valign='top' width='14%'>Nomor CP</td>"+
                                            "<td valign='top' width='1%'>:</td>"+
                                            "<td valign='top' width='85%'>"+rs2.getString("no_berkas")+" ("+rs2.getString("diagnosa_cp")+")</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                            "<td valign='top' width='14%'>Tgl Asuhan</td>"+
                                            "<td valign='top' width='1%'>:</td>"+
                                            "<td valign='top' width='85%'>"+rs2.getString("tgl_asuhan")+"</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                            "<td valign='top' width='14%'>Tgl Berlaku</td>"+
                                            "<td valign='top' width='1%'>:</td>"+
                                            "<td valign='top' width='85%'>"+rs2.getString("tgl_berlaku_cp")+"</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                            "<td valign='top' width='14%'>Nomor Revisi</td>"+
                                            "<td valign='top' width='1%'>:</td>"+
                                            "<td valign='top' width='85%'>"+rs2.getString("no_revisi_cp")+"</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                            "<td valign='top' width='14%'>Catatan Khusus</td>"+
                                            "<td valign='top' width='1%'>:</td>"+
                                            "<td valign='top' width='85%'>"+rs2.getString("catatan_khusus_cp")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                            "</tr>"+
                            "<tr>"+
                                "<td valign='top'>"+
                                    "<table width='100%' border='1' align='center' cellpadding='3px' cellspacing='0' class='isi'>"+
                                        "<tr align='center' class='head1'>"+
                                            "<td valign='middle' colspan='3' rowspan='2' bgcolor='#FFFAF8'>Aspek Klinis</td>"+
                                            "<td valign='middle' colspan='"+rs2.getString("lama_rawat")+"' bgcolor='#FFFAF8'>Hari</td>"+
                                        "</tr>"+
                                        "<tr align='center' class='head1'>");
                                            for(int hari=1;hari<=rs2.getInt("lama_rawat");hari++){
                                                htmlContent.append("<td valign='top' bgcolor='#FFFAF8'>").append(hari).append("</td>");
                                            }
                    htmlContent.append("</tr>");
                                    try {
                                        rs3=koneksi.prepareStatement("select * from clinical_pathway_aspek where no_rawat='"+rs2.getString("no_rawat")+"' and no_berkas='"+rs2.getString("no_berkas")+"' order by no_urut asc").executeQuery();
                                        while(rs3.next()){
                                            htmlContent.append(
                                                   "<tr>").append(
                                                       "<td valign='top' align='center' width='3%' ").append(rs3.getString("no_list").equals("")?">":"bgcolor='#F0F0F0'>").append(rs3.getString("no_list")).append("</td>");
                                                       if(rs3.getString("lvl_list").equals("0")){
                                                           htmlContent.append("<td valign='top' colspan='2' bgcolor='#F0F0F0'>").append(rs3.getString("aspek_pelayanan")).append("</td>");
                                                       } else {
                                                           htmlContent.append("<td valign='top' align='center' width='3%'>").append(rs3.getString("sub_no_list")).append("</td>")
                                                                   .append("<td valign='top'>").append(rs3.getString("aspek_pelayanan")).append("</td>");
                                                       }

                                                       for(int hari=1;hari<=rs2.getInt("lama_rawat");hari++){
                                                            htmlContent.append("<td valign='top' align='center' width='7%' ").append(rs3.getString("kosongi").equals("0")?">":"bgcolor='#F0F0F0'>").append((rs3.getObject("h"+hari) == null?"":rs3.getObject("h"+hari).toString())).append("</td>");
                                                       }
                                            htmlContent.append(
                                                   "</tr>");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                    }

                    htmlContent.append("</table>"+
                                "</td>"+
                            "</tr>"+
                            "<tr>"+
                                "<td valign='top'>"+
                                    "<table width='100%' border='1' align='center' cellpadding='3px' cellspacing='0' class='isi'>"+
                                        "<tr class='head1'>"+
                                            "<td align='center' colspan='4' bgcolor='#FFFAF8'>Varian</td>"+
                                        "</tr>"+
                                        "<tr align='center' class='head1'>"+
                                            "<td valign='top' width='37%' bgcolor='#FFFAF8'>Variasi Pelayanan yang Diberikan</td>"+
                                            "<td valign='top' width='8%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                            "<td valign='top' width='35%' bgcolor='#FFFAF8'>Alasan</td>"+
                                            "<td valign='top' width='20%' bgcolor='#FFFAF8'>Tanda Tangan</td>"+
                                        "</tr>");
                                    try {
                                        rs3=koneksi.prepareStatement("select clinical_pathway_varian.*, pegawai.nama from clinical_pathway_varian inner join pegawai on clinical_pathway_varian.petugas=pegawai.nik "+
                                                "where clinical_pathway_varian.no_rawat='"+rs2.getString("no_rawat")+"' and clinical_pathway_varian.no_berkas='"+rs2.getString("no_berkas")+"' order by clinical_pathway_varian.tgl_variasi asc").executeQuery();
                                        while(rs3.next()){
                                            htmlContent.append(
                                                "<tr>").append(
                                                    "<td valign='top'>").append(rs3.getString("variasi_pelayanan")).append("</td>").append(
                                                    "<td valign='top' align='center'>").append(rs3.getString("tgl_variasi")).append("</td>").append(
                                                    "<td valign='top'>").append(rs3.getString("alasan")).append("</td>").append(
                                                    "<td valign='top'>").append(rs3.getString("petugas")+" "+rs3.getString("nama")).append("</td>").append(
                                                "</tr>");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                    }
                htmlContent.append("</table>"+
                                "</td>"+
                            "</tr>");
                                    try {
                                        rs3=koneksi.prepareStatement("select k.tgl_masuk, k.tgl_keluar, resume_pasien_ranap.* from " +
                                            "(select no_rawat, min(tgl_masuk) as tgl_masuk, max(tgl_keluar) as tgl_keluar from kamar_inap " +
                                            "where no_rawat='" + rs2.getString("no_rawat") + "' group by no_rawat) k " +
                                            "left join resume_pasien_ranap on resume_pasien_ranap.no_rawat=k.no_rawat").executeQuery();
                                        if(rs3.next()){
                                            htmlContent.append("<tr>"+
                                            "<td valign='top'>"+
                                                "<table width='100%' border='1' align='center' cellpadding='3px' cellspacing='0' class='isi'>"+
                                                    "<tr class='head1'>"+
                                                        "<td align='center' colspan='4' bgcolor='#FFFAF8'>Penutup</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                        "<td valign='top' width='15%' bgcolor='#FFFAF8' class='head1'>Tanggal Masuk</td>"+
                                                        "<td valign='top' width='35%'>"+rs3.getString("tgl_masuk")+"</td>"+
                                                        "<td valign='top' width='15%' bgcolor='#FFFAF8' class='head1'>Tanggal Keluar</td>"+
                                                        "<td valign='top' width='35%'>"+rs3.getString("tgl_keluar")+"</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                        "<td valign='top' width='15%' bgcolor='#FFFAF8' class='head1'>Diagnosis Utama</td>"+
                                                        "<td valign='top' width='35%'>"+(rs3.getString("diagnosa_utama").equals("")?"":rs3.getString("diagnosa_utama"))+"</td>"+
                                                        "<td valign='top' width='15%' bgcolor='#FFFAF8' class='head1'>Kode ICD 10</td>"+
                                                        "<td valign='top' width='35%'>"+(rs3.getString("kd_diagnosa_utama").equals("")?"":rs3.getString("kd_diagnosa_utama"))+"</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                        "<td valign='top' width='15%' bgcolor='#FFFAF8' class='head1'>Diagnosis Penyerta</td>"+
                                                        "<td valign='top' width='35%'>"+(rs3.getString("diagnosa_sekunder").equals("")?"":rs3.getString("diagnosa_sekunder"))+"</td>"+
                                                        "<td valign='top' width='15%' bgcolor='#FFFAF8' class='head1'>Kode ICD 10</td>"+
                                                        "<td valign='top' width='35%'>"+(rs3.getString("kd_diagnosa_sekunder").equals("")?"":rs3.getString("kd_diagnosa_sekunder"))+"</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                        "<td valign='top' width='15%' bgcolor='#FFFAF8' class='head1'>Komplikasi</td>"+
                                                        "<td valign='top' width='35%'>"+
                                                            (rs3.getString("diagnosa_sekunder2").equals("")?"":rs3.getString("diagnosa_sekunder2")+",")+(rs3.getString("diagnosa_sekunder3").equals("")?"":rs3.getString("diagnosa_sekunder3")+",")+
                                                            (rs3.getString("diagnosa_sekunder4").equals("")?"":rs3.getString("diagnosa_sekunder4")+",")+(rs3.getString("diagnosa_sekunder5").equals("")?"":rs3.getString("diagnosa_sekunder5")+",")+
                                                            (rs3.getString("diagnosa_sekunder6").equals("")?"":rs3.getString("diagnosa_sekunder6")+",")+(rs3.getString("diagnosa_sekunder7").equals("")?"":rs3.getString("diagnosa_sekunder7"))+
                                                        "</td>"+
                                                        "<td valign='top' width='15%' bgcolor='#FFFAF8' class='head1'>Kode ICD 10</td>"+
                                                        "<td valign='top' width='35%'>"+
                                                            (rs3.getString("kd_diagnosa_sekunder2").equals("")?"":rs3.getString("kd_diagnosa_sekunder2")+",")+(rs3.getString("kd_diagnosa_sekunder3").equals("")?"":rs3.getString("kd_diagnosa_sekunder3")+",")+
                                                            (rs3.getString("kd_diagnosa_sekunder4").equals("")?"":rs3.getString("kd_diagnosa_sekunder4")+",")+(rs3.getString("kd_diagnosa_sekunder5").equals("")?"":rs3.getString("kd_diagnosa_sekunder5")+",")+
                                                            (rs3.getString("kd_diagnosa_sekunder6").equals("")?"":rs3.getString("kd_diagnosa_sekunder6")+",")+(rs3.getString("kd_diagnosa_sekunder7").equals("")?"":rs3.getString("kd_diagnosa_sekunder7"))+
                                                        "</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                        "<td valign='top' width='15%' bgcolor='#FFFAF8' class='head1'>Tindakan Utama</td>"+
                                                        "<td valign='top' width='35%'>"+(rs3.getString("prosedur_utama").equals("")?"":rs3.getString("prosedur_utama"))+"</td>"+
                                                        "<td valign='top' width='15%' bgcolor='#FFFAF8' class='head1'>Kode ICD 9</td>"+
                                                        "<td valign='top' width='35%'>"+(rs3.getString("kd_prosedur_utama").equals("")?"":rs3.getString("kd_prosedur_utama"))+"</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                        "<td valign='top' width='15%' bgcolor='#FFFAF8' class='head1'>Tindakan Lain</td>"+
                                                        "<td valign='top' width='35%'>"+(rs3.getString("prosedur_sekunder").equals("")?"":rs3.getString("prosedur_sekunder")+",")+
                                                            (rs3.getString("prosedur_sekunder2").equals("")?"":rs3.getString("prosedur_sekunder2")+",")+(rs3.getString("prosedur_sekunder3").equals("")?"":rs3.getString("prosedur_sekunder3")+",")+
                                                            (rs3.getString("prosedur_sekunder4").equals("")?"":rs3.getString("prosedur_sekunder4")+",")+(rs3.getString("prosedur_sekunder5").equals("")?"":rs3.getString("prosedur_sekunder5")+",")+
                                                            (rs3.getString("prosedur_sekunder6").equals("")?"":rs3.getString("prosedur_sekunder6")+",")+(rs3.getString("prosedur_sekunder7").equals("")?"":rs3.getString("prosedur_sekunder7"))+
                                                        "</td>"+
                                                        "<td valign='top' width='15%' bgcolor='#FFFAF8' class='head1'>Kode ICD 9</td>"+
                                                        "<td valign='top' width='35%'>"+(rs3.getString("kd_prosedur_sekunder").equals("")?"":rs3.getString("kd_prosedur_sekunder")+",")+
                                                            (rs3.getString("kd_prosedur_sekunder2").equals("")?"":rs3.getString("kd_prosedur_sekunder2")+",")+(rs3.getString("kd_prosedur_sekunder3").equals("")?"":rs3.getString("kd_prosedur_sekunder3")+",")+
                                                            (rs3.getString("kd_prosedur_sekunder4").equals("")?"":rs3.getString("kd_prosedur_sekunder4")+",")+(rs3.getString("kd_prosedur_sekunder5").equals("")?"":rs3.getString("kd_prosedur_sekunder5")+",")+
                                                            (rs3.getString("kd_prosedur_sekunder6").equals("")?"":rs3.getString("kd_prosedur_sekunder6")+",")+(rs3.getString("kd_prosedur_sekunder7").equals("")?"":rs3.getString("kd_prosedur_sekunder7"))+
                                                        "</td>"+
                                                    "</tr>"+
                                                "</table>"+
                                            "</td>"+
                                        "</tr>");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                    }
         htmlContent.append("<tr>"+
                                "<td valign='top'>"+
                                    "<table width='100%' border='1' align='center' cellpadding='3px' cellspacing='0' class='isi'>"+
                                        "<tr align='center' class='head1'>"+
                                            "<td valign='top' width='33%' bgcolor='#FFFAF8'>Dokter Penanggung Jawab Pasien</td>"+
                                            "<td valign='top' width='33%' bgcolor='#FFFAF8'>Perawat Penanggung Jawab</td>"+
                                            "<td valign='top' width='33%' bgcolor='#FFFAF8'>Pelaksana Verifikasi</td>"+
                                        "</tr>"+
                                        "<tr align='center'>"+
                                            "<td valign='top' width='33%'><br>( "+rs2.getString("dokter")+" "+rs2.getString("nm_dokter")+" )<br>&nbsp;</td>"+
                                            "<td valign='top' width='33%'><br>( "+rs2.getString("perawat")+" "+rs2.getString("nm_perawat")+" )<br>&nbsp;</td>"+
                                            "<td valign='top' width='33%'><br>( "+rs2.getString("pelaksana")+" "+rs2.getString("nm_pelaksana")+" )<br>&nbsp;</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                            "</tr>");
                    }
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_all'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                    ".isi td{border: 1px solid #8C8C8C;font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                    ".isi3 td{border-right: 1px solid #8C8C8C;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:14px;background: #ffffff;color:#323232;}"+
                    ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                    ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                    ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                    ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                    ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"+
                    ".head1,.head1 td{background: #FFFAF8 !important;} .tbl_all table{border-collapse: collapse;}"
                );
                bg.close();

                File f = new File("CetakClinicalPathway_"+tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),0).toString().replaceAll("/", "")+".html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+   
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());
                g.deleteOnExit();
                f.deleteOnExit();
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } 
        } else {
            JOptionPane.showMessageDialog(null,"Data belum dipilih, silahkan pilih data Clinical Pathway yang akan di cetak...!!!!");
        }
    }//GEN-LAST:event_cetakCPActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMClinicalPathway dialog = new RMClinicalPathway(new javax.swing.JFrame(), true);
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
    private widget.Button BtnDPJP;
    private widget.Button BtnDiagnosaCP;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnHapusVarian;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarVariasi;
    private widget.Button BtnPelaksana;
    private widget.Button BtnPerawat;
    private widget.Button BtnPetugasVariasi;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanVariasi;
    private widget.Button BtnTambahVarian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JDialog DlgVariasiPelayanan;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JnsKelamin;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdPelaksana;
    private widget.TextBox KdPerawat;
    private widget.Label LCount;
    private widget.TextBox LamaDirawat;
    private widget.editorpane LoadHTML;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmPelaksana;
    private widget.TextBox NmPerawat;
    private widget.ScrollPane Scroll;
    private widget.TextBox TAlasan;
    private widget.TextBox TCari;
    private widget.TextArea TCatatan;
    private widget.TextBox TDiagnosaCP;
    private widget.TextBox TJudulBerkas;
    private widget.TextBox TKdDiagnosaCP;
    private widget.TextBox TKdPetugasVariasi;
    private widget.TextBox TNmPetugasVariasi;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRevisiBerkas;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TTglBerlakuBerkas;
    private widget.TextBox TVariasiPelayanan;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglVariasi;
    private javax.swing.JMenuItem cetakCP;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label29;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.Table tbAspekPelayanan;
    private widget.Table tbClinicalPathway;
    private widget.Table tbVarian;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select clinical_pathway.*, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, dokter.nm_dokter, petugas.nama as nm_petugas, pegawai.nama as nm_pegawai "+
                        "from clinical_pathway inner join reg_periksa on reg_periksa.no_rawat=clinical_pathway.no_rawat "+
                        "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                        "inner join dokter on dokter.kd_dokter=clinical_pathway.dokter "+
                        "inner join petugas on petugas.nip=clinical_pathway.perawat "+
                        "inner join pegawai on pegawai.nik=clinical_pathway.pelaksana "+
                        "where clinical_pathway.tgl_asuhan between ? and ? order by clinical_pathway.tgl_asuhan asc");
            }else{
                ps=koneksi.prepareStatement(
                        "select clinical_pathway.*, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, dokter.nm_dokter, petugas.nama as nm_petugas, pegawai.nama as nm_pegawai "+
                        "from clinical_pathway inner join reg_periksa on reg_periksa.no_rawat=clinical_pathway.no_rawat "+
                        "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                        "inner join dokter on dokter.kd_dokter=clinical_pathway.dokter "+
                        "inner join petugas on petugas.nip=clinical_pathway.perawat "+
                        "inner join pegawai on pegawai.nik=clinical_pathway.pelaksana "+
                        "where (clinical_pathway.tgl_asuhan between ? and ?) and (clinical_pathway.no_rawat like ? or clinical_pathway.catatan_khusus_cp like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or clinical_pathway.diagnosa_cp like ? or clinical_pathway.judul_cp like ? or clinical_pathway.dokter like ? or "+
                        "clinical_pathway.perawat like ? or clinical_pathway.pelaksana like ? or dokter.nm_dokter like ? or petugas.nama like ? or pegawai.nama like ?) "+
                        "order by clinical_pathway.tgl_asuhan asc");
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,"%"+TCari.getText()+"%");
                    ps.setString(14,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("tgl_lahir"),
                        rs.getString("no_berkas"),rs.getString("diagnosa_cp"),rs.getString("lama_rawat"),rs.getString("tgl_asuhan"),rs.getString("judul_cp"),rs.getString("no_revisi_cp"),rs.getString("tgl_berlaku_cp"),rs.getString("catatan_khusus_cp"),
                        rs.getString("dokter"),rs.getString("nm_dokter"),rs.getString("perawat"),rs.getString("nm_petugas"),rs.getString("pelaksana"),rs.getString("nm_pegawai")
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
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    private void tampilAspek(){
        Valid.tabelKosong(tabModeAspek);
        try{
            ps=koneksi.prepareStatement("select * from aspek_berkas_cp where no_berkas=? order by no_urut asc");
            try{
                ps.setString(1, TKdDiagnosaCP.getText().trim());
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeAspek.addRow(new String[]{
                        rs.getString("no_urut"),rs.getString("lvl_list"),rs.getString("kosongi"),
                        rs.getString("lvl_list").equals("0")?rs.getString("no_list"):"",rs.getString("lvl_list").equals("0")?"":rs.getString("no_list"),
                        rs.getString("isi_aspek")
                    });
                }
            } catch(Exception e){
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilAspek(String no_rawat, String kd_diagnosa){
        if(!TKdDiagnosaCP.getText().equals("") && !LamaDirawat.getText().equals("")){
            DefaultTableModel model = (DefaultTableModel) tbAspekPelayanan.getModel();
            int jumlah = Valid.SetInteger(LamaDirawat.getText());
            model.setColumnCount(6);

            for (int i = 1; i <= jumlah; i++) {
                model.addColumn("H-"+i);
            }
            
            Valid.tabelKosong(tabModeAspek);
            try{
                ps=koneksi.prepareStatement("select * from clinical_pathway_aspek where no_rawat=? and no_berkas=? order by no_urut asc");
                try{
                    ps.setString(1, no_rawat);
                    ps.setString(2, kd_diagnosa);
                    rs=ps.executeQuery();
                    while(rs.next()){
                        ArrayList<String> row = new ArrayList<>();

                        row.add(rs.getString("no_urut"));
                        row.add(rs.getString("lvl_list"));
                        row.add(rs.getString("kosongi"));
                        row.add(rs.getString("lvl_list").equals("0") ? rs.getString("no_list") : "");
                        row.add(rs.getString("lvl_list").equals("0") ? "" : rs.getString("sub_no_list"));
                        row.add(rs.getString("aspek_pelayanan"));

                        for(int col = 9; col <= (jumlah+9); col++){
                            row.add(rs.getString(col) == null ? "" : rs.getString(col));
                        }

                        tabModeAspek.addRow(row.toArray(new String[0]));
                    }
                } catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
            aturKolom();
            setComboKolom();
        }
    }
    
    private void tampilVarian(String no_rawat, String kd_diagnosa){
        Valid.tabelKosong(tabModeVariasi);
        try {
            ps=koneksi.prepareStatement(
                    "select clinical_pathway_varian.*, pegawai.nama "+
                    "from clinical_pathway_varian inner join pegawai on clinical_pathway_varian.petugas=pegawai.nik "+
                    "where clinical_pathway_varian.no_rawat=? and clinical_pathway_varian.no_berkas=? order by clinical_pathway_varian.tgl_variasi asc");
            try {
                ps.setString(1,no_rawat);
                ps.setString(2,kd_diagnosa);
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeVariasi.addRow(new Object[]{
                        false,rs.getString("no_rawat"),rs.getString("no_berkas"),rs.getString("variasi_pelayanan"),
                        Valid.SetTgl3(rs.getString("tgl_variasi")+""),rs.getString("alasan"),rs.getString("petugas"),rs.getString("petugas")+" "+rs.getString("nama")
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    public void emptTeks() {
        TglAsuhan.setDate(new Date());
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        JnsKelamin.setText("");
        TglLahir.setText("");
        TKdDiagnosaCP.setText("");
        TDiagnosaCP.setText("");
        LamaDirawat.setText("1");
        TCatatan.setText("");
        TJudulBerkas.setText("");
        TNoRevisiBerkas.setText("");
        TTglBerlakuBerkas.setText("");
        KdDPJP.setText("");
        NmDPJP.setText("");
        KdPerawat.setText("");
        NmPerawat.setText("");
        KdPelaksana.setText("");
        NmPelaksana.setText("");
        Valid.tabelKosong(tabModeAspek);
        Valid.tabelKosong(tabModeVariasi);
    }

    private void getData() {
        if(tbClinicalPathway.getSelectedRow()!= -1){
            ignoreDocumentEvent=true;
            TNoRw.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),0).toString());
            isRawat();
            TKdDiagnosaCP.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),5).toString());
            TDiagnosaCP.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),6).toString());
            LamaDirawat.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),7).toString());
            Valid.SetTgl(TglAsuhan,tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),8).toString());
            TJudulBerkas.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),9).toString());
            TNoRevisiBerkas.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),10).toString());
            TTglBerlakuBerkas.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),11).toString());
            TCatatan.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),12).toString());
            KdDPJP.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),13).toString());
            NmDPJP.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),14).toString());
            KdPerawat.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),15).toString());
            NmPerawat.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),16).toString());
            KdPelaksana.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),17).toString());
            NmPelaksana.setText(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),18).toString());
            tampilAspek(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),0).toString(), tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),5).toString());
            tampilVarian(tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),0).toString(), tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),5).toString());
            ignoreDocumentEvent=false;
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    JnsKelamin.setText(rs.getString("jk"));
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl2);
        isRawat(); 
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getclinicalpathway());
        BtnHapus.setEnabled(akses.getclinicalpathway());
        BtnEdit.setEnabled(akses.getclinicalpathway());
        if(akses.getjml2()>=1){
            KdPelaksana.setEditable(false);
            BtnPelaksana.setEnabled(false);
            KdPelaksana.setText(akses.getkode());
            NmPelaksana.setText(pegawai.tampil3(KdPelaksana.getText()));
            if(NmPelaksana.getText().equals("")){
                KdPelaksana.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }
    
    private void hapus() {
        if(Sequel.queryu2tf("delete from clinical_pathway where no_rawat=? and no_berkas=?",2,new String[]{
            tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),0).toString(),tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),5).toString()
        })==true){
            Sequel.queryu2tf("delete from clinical_pathway_aspek where no_rawat=? and no_berkas=?",2,new String[]{
                tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),0).toString(),tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),5).toString()
            });
            Sequel.queryu2tf("delete from clinical_pathway_varian where no_rawat=? and no_berkas=?",2,new String[]{
                tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),0).toString(),tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),5).toString()
            });
            tampil();emptTeks();
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void edit() {
        if(Sequel.mengedittf("clinical_pathway","no_rawat=?","no_rawat=?, no_berkas=?, lama_rawat=?, tgl_asuhan=?, diagnosa_cp=?, judul_cp=?, no_revisi_cp=?, tgl_berlaku_cp=?, catatan_khusus_cp=?, dokter=?, perawat=?, pelaksana=?",13,new String[]{ 
            TNoRw.getText(),TKdDiagnosaCP.getText(),LamaDirawat.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+""),
            TDiagnosaCP.getText(),TJudulBerkas.getText(),TNoRevisiBerkas.getText(),TTglBerlakuBerkas.getText(),TCatatan.getText(),
            KdDPJP.getText(),KdPerawat.getText(),KdPelaksana.getText(),tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),0).toString() })==true){

            Sequel.meghapus("clinical_pathway_aspek","no_rawat","no_berkas",tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),0).toString(), tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),5).toString());
            for(int rw = 0;rw < tabModeAspek.getRowCount();rw++){
                String qValue = "'"+TNoRw.getText()+"','"+TKdDiagnosaCP.getText()+"','"+tabModeAspek.getValueAt(rw, 0)+"','"+tabModeAspek.getValueAt(rw, 1)+"','"+tabModeAspek.getValueAt(rw, 2)+"','"
                        +tabModeAspek.getValueAt(rw, 3)+"','"+tabModeAspek.getValueAt(rw, 4)+"','"+tabModeAspek.getValueAt(rw, 5).toString().trim()+"'";
                for(int col = 6; col < 15; col++) {
                    if(col < tabModeAspek.getColumnCount()) {
                        Object val = tabModeAspek.getValueAt(rw, col);

                        if(val == null || val.toString().trim().isEmpty()) {
                            qValue += ",null";
                        } else {
                            qValue += ",'"+val.toString().trim()+"'";
                        }
                    } else {
                        qValue += ",null";
                    }
                }
                Sequel.menyimpantf("clinical_pathway_aspek", qValue, "Data Aspek Klinis");
            }

            Sequel.meghapus("clinical_pathway_varian","no_rawat","no_berkas",tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),0).toString(), tbClinicalPathway.getValueAt(tbClinicalPathway.getSelectedRow(),5).toString());
            if(tabModeVariasi.getRowCount() > 0){
                for(int rwv = 0; rwv < tabModeVariasi.getRowCount();rwv ++){
                    Sequel.menyimpantf("clinical_pathway_varian", "'"+tabModeVariasi.getValueAt(rwv, 1)+"','"+tabModeVariasi.getValueAt(rwv, 2)+"','"+tabModeVariasi.getValueAt(rwv, 3)+"','"
                            +Valid.SetTgl(tabModeVariasi.getValueAt(rwv, 4)+"")+"','"+tabModeVariasi.getValueAt(rwv, 5)+"','"+tabModeVariasi.getValueAt(rwv, 6)+"'", "Data Varian");
                }
            }

            JOptionPane.showMessageDialog(null, "Berhasil mengubah data Clinical Pathway!!!");
            tampil();emptTeks();
            LCount.setText(""+tabMode.getRowCount());
        }
    }
    
    private void generateKolom() {
        if(!TKdDiagnosaCP.getText().equals("") && !LamaDirawat.getText().equals("")){
            DefaultTableModel model = (DefaultTableModel) tbAspekPelayanan.getModel();
            int jumlah = Valid.SetInteger(LamaDirawat.getText());
            model.setColumnCount(6);

            for (int i = 1; i <= jumlah; i++) {
                model.addColumn("H-"+i);
            }
            
            tampilAspek();
            aturKolom();
            setComboKolom();
        }
    }
    
    private void aturKolom() {
        for (int i = 0;i < tbAspekPelayanan.getColumnCount();i++) {
            TableColumn column =tbAspekPelayanan.getColumnModel().getColumn(i);

            if (i <= 2) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setPreferredWidth(0);
            } else if (i == 3 || i == 4) {
                column.setPreferredWidth(25);
            } else if (i == 5) {
                column.setPreferredWidth(500);
            } else {
                column.setPreferredWidth(40);
            }
        }
    }
    
    private void setComboKolom() {
        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("");
        combo.addItem("Y");
        combo.addItem("-");
        
        for (int i = 6; i < tbAspekPelayanan.getColumnCount(); i++) {
            tbAspekPelayanan.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(combo));
        }
    }
    
    private void aktifkanSorting() {
        TableRowSorter<DefaultTableModel> sorter =
            new TableRowSorter<>(
                (DefaultTableModel) tbVarian.getModel()
            );

        sorter.setComparator(4, (o1, o2) -> {

            SimpleDateFormat sdf =
                new SimpleDateFormat("dd-MM-yyyy");

            try {
                Date d1 = sdf.parse(o1.toString());
                Date d2 = sdf.parse(o2.toString());

                return d1.compareTo(d2);

            } catch (Exception e) {
                return 0;
            }
        });

        tbVarian.setRowSorter(sorter);

        sorter.setSortKeys(
            java.util.Arrays.asList(
                new RowSorter.SortKey(
                    4,
                    SortOrder.ASCENDING
                )
            )
        );
    }
}
