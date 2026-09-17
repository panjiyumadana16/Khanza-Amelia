package rekammedis;
import fungsi.WarnaTable;
import fungsi.WarnaTableAspek;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class MasterBerkasClinicalPathway extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeAspek;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i;
    private boolean isInitializing = false;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public MasterBerkasClinicalPathway(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.Berkas","No.Revisi","Tgl Berlaku","Diagnosa CP","Judul CP","Catatan Khusus","Max.Hari Dirawat"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBerkas.setModel(tabMode);

        tbBerkas.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbBerkas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbBerkas.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(160);
            }else if(i==4){
                column.setPreferredWidth(160);
            }else if(i==5){
                column.setPreferredWidth(500);
            }else if(i==6){
                column.setPreferredWidth(80);
            }
        }
        tbBerkas.setDefaultRenderer(Object.class, new WarnaTable());
        
        Object[] rowAspek={"","No","Lvl.List","No.List","Isi Aspek","Kosongi?","Wajib Isi"};
        tabModeAspek=new DefaultTableModel(null,rowAspek){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return Boolean.class;
                    case 1: return Integer.class;
                    case 2: return Integer.class;
                    case 5: return Boolean.class;
                    default: return Object.class;
                }
            }
            
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                int lvl = Integer.parseInt(getValueAt(rowIndex, 2).toString());
                
                if(colIndex == 1) {return false;}
                if(lvl == 0) {return false;}
                
                return true;
            }
            
            @Override
            public void setValueAt(Object aValue, int row, int column) {
                if (isInitializing) {
                    super.setValueAt(aValue, row, column);
                    return;
                }
                
                if (column == 2) {
                    try {
                        int lvl = Integer.parseInt(aValue.toString());

                        if (lvl <= 0 || lvl >= 3) {
                            JOptionPane.showMessageDialog(null, "Masukan Level List antara 1 atau 2");
                            return; 
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Harus angka!");
                        return;
                    }
                }

                super.setValueAt(aValue, row, column);
            }
        };
        
        tbAspek.setModel(tabModeAspek);
        tbAspek.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbAspek.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 7; i++) {
            TableColumn columnAspek = tbAspek.getColumnModel().getColumn(i);
            if(i==0){
                columnAspek.setPreferredWidth(30);
            }else if(i==1){
                columnAspek.setMinWidth(0);
                columnAspek.setMaxWidth(0);
                columnAspek.setPreferredWidth(0);
            }else if(i==2){
                columnAspek.setPreferredWidth(50);
            }else if(i==3){
                columnAspek.setPreferredWidth(50);
            }else if(i==4){
                columnAspek.setPreferredWidth(400);
            }else if(i==5){
                columnAspek.setPreferredWidth(50);
            }else if(i==6){
                columnAspek.setPreferredWidth(80);
            }
        }
        tbAspek.setDefaultRenderer(Number.class, new WarnaTableAspek());
        tbAspek.setDefaultRenderer(Object.class, new WarnaTableAspek());
        
        tbAspek.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp");
        tbAspek.getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tbAspek.getSelectedRow();
                if (row > 0) {
                    Object current = tbAspek.getValueAt(row, 2);
                    Object target = tbAspek.getValueAt(row - 1, 2);

                    if ((current != null && Integer.parseInt(current.toString()) == 0) ||
                        (target != null && Integer.parseInt(target.toString()) == 0)) {
                        return;
                    }
                    swapRows(tabModeAspek, row, row - 1);
                    tbAspek.setRowSelectionInterval(row - 1, row - 1);
                }
            }
        });

        tbAspek.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        tbAspek.getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tbAspek.getSelectedRow();
                if (row < tbAspek.getRowCount() - 1) {
                    Object current = tbAspek.getValueAt(row, 2);
                    Object target = tbAspek.getValueAt(row + 1, 2);

                    if ((current != null && Integer.parseInt(current.toString()) == 0) ||
                        (target != null && Integer.parseInt(target.toString()) == 0)) {
                        return;
                    }
                    swapRows(tabModeAspek, row, row + 1);
                    tbAspek.setRowSelectionInterval(row + 1, row + 1);
                }
            }
        });
        
        clearAspek();
        TNoBerkas.setDocument(new batasInput(8).getKata(TNoBerkas));
        TDiagnosa.setDocument(new batasInput(64).getKata(TDiagnosa));
        TJudulBerkas.setDocument(new batasInput(128).getKata(TJudulBerkas));
        TMaxHariRawat.setDocument(new batasInput(1).getKata(TMaxHariRawat));
        TCari.setDocument(new batasInput(100).getKata(TCari));    
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
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        label12 = new widget.Label();
        TNoBerkas = new widget.TextBox();
        TJudulBerkas = new widget.TextBox();
        label20 = new widget.Label();
        label13 = new widget.Label();
        TDiagnosa = new widget.TextBox();
        label21 = new widget.Label();
        TNoRevisi = new widget.TextBox();
        TglBerlaku = new widget.Tanggal();
        label14 = new widget.Label();
        label22 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        TACatatan = new widget.TextArea();
        jSeparator1 = new javax.swing.JSeparator();
        label23 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        tbAspek = new widget.Table();
        panelBiasa1 = new widget.PanelBiasa();
        label15 = new widget.Label();
        cbNoAddAspek = new widget.ComboBox();
        BtnAddAspek = new widget.Button();
        BtnDelAspek = new widget.Button();
        BtnApplyAspek = new widget.Button();
        BtnClearAspek = new widget.Button();
        label24 = new widget.Label();
        TMaxHariRawat = new widget.TextBox();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBerkas = new widget.Table();
        panelGlass9 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Master Berkas Clinical Pathway ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(700, 850));
        FormInput.setLayout(null);

        label12.setText("Nomor Berkas :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label12);
        label12.setBounds(0, 10, 90, 23);

        TNoBerkas.setName("TNoBerkas"); // NOI18N
        TNoBerkas.setPreferredSize(new java.awt.Dimension(207, 23));
        TNoBerkas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoBerkasKeyPressed(evt);
            }
        });
        FormInput.add(TNoBerkas);
        TNoBerkas.setBounds(95, 10, 90, 23);

        TJudulBerkas.setName("TJudulBerkas"); // NOI18N
        TJudulBerkas.setPreferredSize(new java.awt.Dimension(207, 23));
        TJudulBerkas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TJudulBerkasKeyPressed(evt);
            }
        });
        FormInput.add(TJudulBerkas);
        TJudulBerkas.setBounds(95, 70, 590, 23);

        label20.setText("Judul Berkas CP :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label20);
        label20.setBounds(0, 70, 90, 23);

        label13.setText("Diagnosa CP :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label13);
        label13.setBounds(0, 40, 90, 23);

        TDiagnosa.setName("TDiagnosa"); // NOI18N
        TDiagnosa.setPreferredSize(new java.awt.Dimension(207, 23));
        TDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(TDiagnosa);
        TDiagnosa.setBounds(95, 40, 400, 23);

        label21.setText("Tanggal Berlaku :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label21);
        label21.setBounds(500, 10, 90, 23);

        TNoRevisi.setName("TNoRevisi"); // NOI18N
        TNoRevisi.setPreferredSize(new java.awt.Dimension(207, 23));
        TNoRevisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRevisiKeyPressed(evt);
            }
        });
        FormInput.add(TNoRevisi);
        TNoRevisi.setBounds(275, 10, 220, 23);

        TglBerlaku.setForeground(new java.awt.Color(50, 70, 50));
        TglBerlaku.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-09-2026" }));
        TglBerlaku.setDisplayFormat("dd-MM-yyyy");
        TglBerlaku.setName("TglBerlaku"); // NOI18N
        TglBerlaku.setOpaque(false);
        TglBerlaku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBerlakuKeyPressed(evt);
            }
        });
        FormInput.add(TglBerlaku);
        TglBerlaku.setBounds(595, 10, 90, 23);

        label14.setText("Nomor Revisi :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label14);
        label14.setBounds(190, 10, 80, 23);

        label22.setText("Catatan Khusus :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label22);
        label22.setBounds(0, 100, 90, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        TACatatan.setColumns(20);
        TACatatan.setRows(5);
        TACatatan.setName("TACatatan"); // NOI18N
        scrollPane1.setViewportView(TACatatan);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(95, 100, 590, 60);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 180, 690, 1);

        label23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        label23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label23.setText("ASPEK PELAYANAN");
        label23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label23);
        label23.setBounds(5, 190, 190, 50);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        tbAspek.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAspek.setToolTipText("Klik dahulu kemudian gunakan arah panah (Atas / Bawah) pada keyboard untuk memindah urutan, kolom wajib isi gunakan \"-\" untuk semua kolom, angka pisahkan dengan koma untuk wajib isi kolom tertentu");
        tbAspek.setName("tbAspek"); // NOI18N
        scrollPane2.setViewportView(tbAspek);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(5, 245, 680, 550);

        panelBiasa1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelBiasa1.setName("panelBiasa1"); // NOI18N

        label15.setText("No.Aspek Pelayanan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(75, 23));

        cbNoAddAspek.setMaximumRowCount(14);
        cbNoAddAspek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14" }));
        cbNoAddAspek.setToolTipText("");
        cbNoAddAspek.setName("cbNoAddAspek"); // NOI18N
        cbNoAddAspek.setPreferredSize(new java.awt.Dimension(72, 30));

        BtnAddAspek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnAddAspek.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnAddAspek.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BtnAddAspek.setName("BtnAddAspek"); // NOI18N
        BtnAddAspek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAddAspekActionPerformed(evt);
            }
        });

        BtnDelAspek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnDelAspek.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnDelAspek.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BtnDelAspek.setName("BtnDelAspek"); // NOI18N
        BtnDelAspek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDelAspekActionPerformed(evt);
            }
        });

        BtnApplyAspek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept_page.png"))); // NOI18N
        BtnApplyAspek.setText("Simpan");
        BtnApplyAspek.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnApplyAspek.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BtnApplyAspek.setName("BtnApplyAspek"); // NOI18N
        BtnApplyAspek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnApplyAspekActionPerformed(evt);
            }
        });

        BtnClearAspek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/New.png"))); // NOI18N
        BtnClearAspek.setText("Baru");
        BtnClearAspek.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnClearAspek.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BtnClearAspek.setName("BtnClearAspek"); // NOI18N
        BtnClearAspek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnClearAspekActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBiasa1Layout = new javax.swing.GroupLayout(panelBiasa1);
        panelBiasa1.setLayout(panelBiasa1Layout);
        panelBiasa1Layout.setHorizontalGroup(
            panelBiasa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBiasa1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label15, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbNoAddAspek, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnAddAspek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnDelAspek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(BtnApplyAspek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnClearAspek, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBiasa1Layout.setVerticalGroup(
            panelBiasa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBiasa1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelBiasa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBiasa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(BtnAddAspek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelBiasa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbNoAddAspek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(BtnDelAspek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BtnClearAspek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnApplyAspek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        FormInput.add(panelBiasa1);
        panelBiasa1.setBounds(200, 190, 485, 50);

        label24.setText("Max. Hari Dirawat :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label24);
        label24.setBounds(500, 40, 110, 23);

        TMaxHariRawat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TMaxHariRawat.setText("1");
        TMaxHariRawat.setName("TMaxHariRawat"); // NOI18N
        TMaxHariRawat.setPreferredSize(new java.awt.Dimension(207, 23));
        TMaxHariRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TMaxHariRawatActionPerformed(evt);
            }
        });
        TMaxHariRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TMaxHariRawatKeyPressed(evt);
            }
        });
        FormInput.add(TMaxHariRawat);
        TMaxHariRawat.setBounds(615, 40, 70, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Berkas", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbBerkas.setAutoCreateRowSorter(true);
        tbBerkas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbBerkas.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbBerkas.setName("tbBerkas"); // NOI18N
        tbBerkas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBerkasMouseClicked(evt);
            }
        });
        tbBerkas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbBerkasKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbBerkas);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(530, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Berkas", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
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

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(label10);

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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbBerkas.requestFocus();
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

    private void tbBerkasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBerkasMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbBerkas.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbBerkasMouseClicked

    private void tbBerkasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBerkasKeyPressed
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
}//GEN-LAST:event_tbBerkasKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(TNoBerkas.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbBerkas.requestFocus();
        }else{
            int tanya = JOptionPane.showConfirmDialog(rootPane, "Apakah anda yakin menghapus data Berkas Clinical Pathway Ini?","Kofirmasi!!!",JOptionPane.YES_NO_OPTION);
            if(tanya == JOptionPane.YES_OPTION){
                if(Valid.hapusTabletf(tabMode,TNoBerkas,"master_berkas_cp","no_berkas")==true){
                    Valid.hapusTabletf(tabMode,TNoBerkas,"aspek_berkas_cp","no_berkas");
                    if(tbBerkas.getSelectedRow()!= -1){
                        tabMode.removeRow(tbBerkas.getSelectedRow());
                        LCount.setText(""+tabMode.getRowCount());
                        JOptionPane.showMessageDialog(null,"Berhasil hapus Berkas Clinical Pathway...!!!");
                        emptTeks();
                    }
                }
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
        int aspekValid = 0;
        for(int i = 0; i < tabModeAspek.getRowCount();i++){
            if(tabModeAspek.getValueAt(i, 4).toString().equals("")){
                aspekValid++;
            }
        }
        
        if(TNoBerkas.getText().trim().equals("")){
            Valid.textKosong(TNoBerkas,"Nomor Berkas");
        }else if(TNoRevisi.getText().trim().equals("")){
            Valid.textKosong(TNoRevisi,"Nomor Revisi");
        }else if(TDiagnosa.getText().trim().equals("")){
            Valid.textKosong(TDiagnosa,"Diagnosa");
        }else if(TJudulBerkas.getText().trim().equals("")){
            Valid.textKosong(TJudulBerkas,"Judul Berkas");
        }else if(TACatatan.getText().trim().equals("")){
            Valid.textKosong(TACatatan,"Catatan Khusus");
        }else if(aspekValid > 0){
            JOptionPane.showMessageDialog(null,"Maaf, Template aspek masih ada "+aspekValid+" yang kosong...!!!");
        }else if(tabModeAspek.getRowCount() < 15){
            JOptionPane.showMessageDialog(null,"Maaf, Template aspek belum diisi...!!!");
        }else if(TMaxHariRawat.getText().equals("") || TMaxHariRawat.getText().equals("0")){
            Valid.textKosong(TMaxHariRawat,"Minimal Hari Dirawat");
        }else{
            if(Sequel.mengedittf("master_berkas_cp","no_berkas=?","no_berkas=?,no_revisi=?,tgl_berlaku=?,diagnosa_cp=?,judul_cp=?,catatan_khusus=?,max_hari_dirawat=?",8,new String[]{
                TNoBerkas.getText(),TNoRevisi.getText(),Valid.SetTgl(TglBerlaku.getSelectedItem()+""),TDiagnosa.getText(),TJudulBerkas.getText(),TACatatan.getText(),TMaxHariRawat.getText(),tabMode.getValueAt(tbBerkas.getSelectedRow(), 0).toString().trim()
            })==true){
                Sequel.meghapustf("aspek_berkas_cp","no_berkas",tabMode.getValueAt(tbBerkas.getSelectedRow(), 0).toString().trim());
                for(int rw = 0; rw < tabModeAspek.getRowCount();rw++){
                    Sequel.menyimpantf("aspek_berkas_cp","'"+TNoBerkas.getText().trim()+"',"+tabModeAspek.getValueAt(rw, 1)+",'"+tabModeAspek.getValueAt(rw, 2)+"','"+tabModeAspek.getValueAt(rw, 3).toString().trim()+"','"+tabModeAspek.getValueAt(rw, 4).toString().trim()+"',"+tabModeAspek.getValueAt(rw, 5)+",'"+tabModeAspek.getValueAt(rw, 6)+"'","Urutan Aspek");
                }
                JOptionPane.showMessageDialog(null,"Berhasil edit Berkas Clinical Pathway...!!!");
                tampil();
                emptTeks();
                LCount.setText(""+tabMode.getRowCount());
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
       dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        int aspekValid = 0;
        for(int i = 0; i < tabModeAspek.getRowCount();i++){
            if(tabModeAspek.getValueAt(i, 4).toString().equals("")){
                aspekValid++;
            }
        }
        
        if(TNoBerkas.getText().trim().equals("")){
            Valid.textKosong(TNoBerkas,"Nomor Berkas");
        }else if(TNoRevisi.getText().trim().equals("")){
            Valid.textKosong(TNoRevisi,"Nomor Revisi");
        }else if(TDiagnosa.getText().trim().equals("")){
            Valid.textKosong(TDiagnosa,"Diagnosa");
        }else if(TJudulBerkas.getText().trim().equals("")){
            Valid.textKosong(TJudulBerkas,"Judul Berkas");
        }else if(TACatatan.getText().trim().equals("")){
            Valid.textKosong(TACatatan,"Catatan Khusus");
        }else if(aspekValid > 0){
            JOptionPane.showMessageDialog(null,"Maaf, Template aspek masih ada "+aspekValid+" yang kosong...!!!");
        }else if(tabModeAspek.getRowCount() < 15){
            JOptionPane.showMessageDialog(null,"Maaf, Template aspek belum diisi...!!!");
        }else if(TMaxHariRawat.getText().equals("") || TMaxHariRawat.getText().equals("0")){
            Valid.textKosong(TMaxHariRawat,"Max Hari Dirawat");
        }else{
            if(Sequel.menyimpantf("master_berkas_cp","'"+TNoBerkas.getText().trim()+"','"+TNoRevisi.getText()+"','"+Valid.SetTgl(TglBerlaku.getSelectedItem()+"")+"','"+TDiagnosa.getText().trim()+"','"+TJudulBerkas.getText().trim()+"','"+TACatatan.getText()+"','"+TMaxHariRawat.getText()+"'","Nomor Berkas")==true){
                for(int rw = 0; rw < tabModeAspek.getRowCount();rw++){
                    Sequel.menyimpantf("aspek_berkas_cp","'"+TNoBerkas.getText().trim()+"',"+tabModeAspek.getValueAt(rw, 1)+",'"+tabModeAspek.getValueAt(rw, 2)+"','"+tabModeAspek.getValueAt(rw, 3).toString().trim()+"','"+tabModeAspek.getValueAt(rw, 4).toString().trim()+"',"+tabModeAspek.getValueAt(rw, 5)+",'"+tabModeAspek.getValueAt(rw, 6)+"'","Urutan Aspek");
                }
                JOptionPane.showMessageDialog(null,"Berhasil menyimpan Berkas Clinical Pathway...!!!");
                tampil();
                emptTeks();
                LCount.setText(""+tabMode.getRowCount());
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnSimpan,BtnBatal);
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
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TNoRevisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRevisiKeyPressed
       
    }//GEN-LAST:event_TNoRevisiKeyPressed

    private void TDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDiagnosaKeyPressed
        
    }//GEN-LAST:event_TDiagnosaKeyPressed

    private void TJudulBerkasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TJudulBerkasKeyPressed
        
    }//GEN-LAST:event_TJudulBerkasKeyPressed

    private void TNoBerkasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoBerkasKeyPressed
        
    }//GEN-LAST:event_TNoBerkasKeyPressed

    private void TglBerlakuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBerlakuKeyPressed
        //Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglBerlakuKeyPressed

    private void BtnAddAspekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAddAspekActionPerformed
        int noAspek = Integer.parseInt(cbNoAddAspek.getSelectedItem().toString());
        if (noAspek != 14) {
            noAspek = noAspek + 1;
            
            for (int row = 0; row < tabModeAspek.getRowCount(); row++) {
                String nolist = tabModeAspek.getValueAt(row, 3).toString();
                int lvl = Integer.parseInt(tabModeAspek.getValueAt(row, 2).toString());
                if (nolist != null && nolist.equals(String.valueOf(noAspek)) && lvl == 0) {
                    tabModeAspek.insertRow(row, new Object[]{false,null,1,"","",false,null});

                    break;
                }
            }
        } else {
            tabModeAspek.addRow(new Object[]{false,null,1,"","",false});
        }
        
        setNoUrut();
    }//GEN-LAST:event_BtnAddAspekActionPerformed

    private void BtnApplyAspekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnApplyAspekActionPerformed
        setNoUrut();
    }//GEN-LAST:event_BtnApplyAspekActionPerformed

    private void BtnClearAspekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnClearAspekActionPerformed
        clearAspek();
    }//GEN-LAST:event_BtnClearAspekActionPerformed

    private void BtnDelAspekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDelAspekActionPerformed
        for (int rowDel = tabModeAspek.getRowCount() - 1; rowDel >= 0; rowDel--) {
            if (Boolean.TRUE.equals(tabModeAspek.getValueAt(rowDel, 0))) {
                tabModeAspek.removeRow(rowDel);
            }
        }
        setNoUrut();
    }//GEN-LAST:event_BtnDelAspekActionPerformed

    private void TMaxHariRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TMaxHariRawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TMaxHariRawatKeyPressed

    private void TMaxHariRawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TMaxHariRawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TMaxHariRawatActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            MasterBerkasClinicalPathway dialog = new MasterBerkasClinicalPathway(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAddAspek;
    private widget.Button BtnAll;
    private widget.Button BtnApplyAspek;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnClearAspek;
    private widget.Button BtnDelAspek;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextArea TACatatan;
    private widget.TextBox TCari;
    private widget.TextBox TDiagnosa;
    private widget.TextBox TJudulBerkas;
    private widget.TextBox TMaxHariRawat;
    private widget.TextBox TNoBerkas;
    private widget.TextBox TNoRevisi;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglBerlaku;
    private widget.ComboBox cbNoAddAspek;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private javax.swing.JSeparator jSeparator1;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label9;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbAspek;
    private widget.Table tbBerkas;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        
        try{
            ps=koneksi.prepareStatement(
                    "select * from master_berkas_cp "+
                    "where no_berkas like ? or no_revisi like ? or tgl_berlaku like ? or diagnosa_cp like ? or judul_cp like ? or catatan_khusus like ? "+
                    "order by no_berkas");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_berkas"),rs.getString("no_revisi"),rs.getDate("tgl_berlaku"),
                        rs.getString("diagnosa_cp"),rs.getString("judul_cp"),rs.getString("catatan_khusus"),rs.getInt("max_hari_dirawat")
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
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

    public void emptTeks() {
        TNoBerkas.setText("");
        TNoRevisi.setText("");
        TDiagnosa.setText("");
        TJudulBerkas.setText("");
        TACatatan.setText("");
        TMaxHariRawat.setText("1");
        clearAspek();
    }

    private void getData() {
        if(tbBerkas.getSelectedRow()!= -1){
            TNoBerkas.setText(tbBerkas.getValueAt(tbBerkas.getSelectedRow(),0).toString());
            TNoRevisi.setText(tbBerkas.getValueAt(tbBerkas.getSelectedRow(),1).toString());
            Valid.SetTgl(TglBerlaku,tbBerkas.getValueAt(tbBerkas.getSelectedRow(),2).toString());
            TDiagnosa.setText(tbBerkas.getValueAt(tbBerkas.getSelectedRow(),3).toString());
            TJudulBerkas.setText(tbBerkas.getValueAt(tbBerkas.getSelectedRow(),4).toString());
            TACatatan.setText(tbBerkas.getValueAt(tbBerkas.getSelectedRow(),5).toString());
            TMaxHariRawat.setText(tbBerkas.getValueAt(tbBerkas.getSelectedRow(),6).toString());
            Valid.tabelKosong(tabModeAspek);
            try{
                ps=koneksi.prepareStatement("select * from aspek_berkas_cp where no_berkas = ? order by no_urut");
                try {
                    ps.setString(1,tbBerkas.getValueAt(tbBerkas.getSelectedRow(),0).toString().trim());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeAspek.addRow(new Object[]{
                            false,rs.getInt("no_urut"),rs.getString("lvl_list"),rs.getString("no_list"),rs.getString("isi_aspek"),rs.getBoolean("kosongi"),rs.getString("wajib_isi")
                        });
                    }
                    setNoUrut();
                } catch (Exception e) {
                    System.out.println(e);
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
    }
    
    private void clearAspek(){
        isInitializing = true;
        Valid.tabelKosong(tabModeAspek);
        
        tabModeAspek.addRow(new Object[]{false,null,0,"1","Penilaian dan Pemantauan Medis",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"2","Penilaian dan Pemantauan Keperawatan",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"3","Penilaian dan Pemantauan Gizi",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"4","Penilaian dan Pemantauan Farmasi",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"5","Pemeriksaan Penunjang Medis (lab, radiologi, dsb)",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"6","Tindakan Medis",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"7","Tindakan Keperawatan",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"8","Medikasi (Obat-obatan, cairan IV, transfusi, dsb)",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"9","Nutrisi (pembatasan konsumsi natrium, diet kaya sayuran, buah buahan,produk susu rendah lemak/bebas lemak, dsb)",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"10","Kegiatan Pasien",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"11","Konsultasi dan komunikasi Tim",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"12","Konseling Psikososial",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"13","Pendidikan dan komunikasi dengan pasien/keluarga (obat, diet, penggunaan alat, rehabilitasi, dsb)",true,null});
        tabModeAspek.addRow(new Object[]{false,null,0,"14","Outcome Pasien (penilaian outcome pasien yang harus dicapai sebelum pemulangan)",true,null});
        
        setNoUrut();
        isInitializing = false;
    }
    
    private void setNoUrut(){
        int j = tabModeAspek.getRowCount();
        for(int i = 1; i <= j; i++){
            tabModeAspek.setValueAt(i, (i-1), 1);
        }
    }
    
    private void swapRows(DefaultTableModel model, int row1, int row2) {
        int colCount = model.getColumnCount();

        for (int i = 0; i < colCount; i++) {
            Object temp = model.getValueAt(row1, i);
            model.setValueAt(model.getValueAt(row2, i), row1, i);
            model.setValueAt(temp, row2, i);
        }
        setNoUrut();
    }

    public JTable getTable(){
        return tbBerkas;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getmasterberkas_cp());
        BtnHapus.setEnabled(akses.getmasterberkas_cp());
        BtnEdit.setEnabled(akses.getmasterberkas_cp());
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }
}
