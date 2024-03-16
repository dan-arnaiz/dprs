/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.dentalpatientrecordsystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import com.mycompany.dentalpatientrecordsystem.Database;


/**
 *
 * @author Dan Arnaiz
 */
public class DashBoard extends javax.swing.JFrame {

    /**
     * Creates new form DashBoard
     */
    public DashBoard() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    public void updatePatientRecordTable() {
    try {
        // Create a new instance of the Database class
        Database db = new Database();

        // Get the table model
        DefaultTableModel model = (DefaultTableModel) patientRecordTable.getModel();

        if (model.getRowCount() > 0) {
            // Update the database based on the JTable data
            for (int i = 0; i < model.getRowCount(); i++) {
                int id = Integer.parseInt(model.getValueAt(i, 0).toString());
                String givenName = model.getValueAt(i, 1).toString();
                String lastName = model.getValueAt(i, 2).toString();
                String middleName = model.getValueAt(i, 3).toString();
                int age = Integer.parseInt(model.getValueAt(i, 4).toString());
                String civilStatus = model.getValueAt(i, 5).toString();
                String occupation = model.getValueAt(i, 6).toString();
                String contactNo = model.getValueAt(i, 7).toString();
                String address = model.getValueAt(i, 8).toString();

                String query = "UPDATE patientrecords SET given_name = ?, last_name = ?, middle_name = ?, age = ?, civil_status = ?, occupation = ?, contact_no = ?, address = ? WHERE id = ?";
                try (Connection conn = db.connect();
                     PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, givenName);
                    pstmt.setString(2, lastName);
                    pstmt.setString(3, middleName);
                    pstmt.setInt(4, age);
                    pstmt.setString(5, civilStatus);
                    pstmt.setString(6, occupation);
                    pstmt.setString(7, contactNo);
                    pstmt.setString(8, address);
                    pstmt.setInt(9, id); // Set id as int
                    pstmt.executeUpdate();
                }
            }
        } else {
            // Fetch the data from the database
            List<Object[]> data = db.read();

            // Clear the existing data in the table
            model.setRowCount(0);

            // Add the fetched data to the table model
            for (Object[] row : data) {
                model.addRow(row);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    
    String totalPatientCount = "Total Visits: " + patientRecordTable.getModel().getRowCount();
    totalPatients.setText(totalPatientCount);
    
    }
    
    public void refreshUsersTable(){
        
        try {
        // Create a new instance of the Database class
        Database db = new Database();

        // Get the table model
        DefaultTableModel model2 = (DefaultTableModel) usersInfoTable.getModel();
        model2.setRowCount(0);
        if (model2.getRowCount() > 0) {
            // Update the database based on the JTable data
            for (int i = 0; i < model2.getRowCount(); i++) {
                int usersID = Integer.parseInt(model2.getValueAt(i, 0).toString());
                String accesslevel = model2.getValueAt(i, 1).toString();
                String username = model2.getValueAt(i, 2).toString();
                String password = model2.getValueAt(i, 3).toString();
                
                String query = "UPDATE usersdata SET accessLevel = ?, username = ?, password = ? WHERE usersID = ?";
                try (Connection conn = db.connect();
                     PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, accesslevel);
                    pstmt.setString(2, username);
                    pstmt.setString(3, password);
                    pstmt.setInt(4, usersID); // Set id as int
                    pstmt.executeUpdate();
                }
            }
        } else {
            // Fetch the data from the database
            List<Object[]> userdata = db.readUsers();

            // Clear the existing data in the table
            model2.setRowCount(0);

            // Add the fetched data to the table model
            for (Object[] row : userdata) {
                model2.addRow(row);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    public void refreshVisitHistory() {                                                    
        // Get the currently selected ID from the text box
        int currentId = Integer.parseInt(patientIDDetailsText.getText());  // Assuming patientIDUpdateText is the JTextField for the current ID

        // Fetch the visit history from the patientvisit table in the database
        try {
            Database db = new Database();
            String queryVisit = "SELECT * FROM patientvisit WHERE patient_id = ?";
            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(queryVisit)) {
                pstmt.setInt(1, currentId);

                ResultSet rs = pstmt.executeQuery();

                // Clear the patientHistoryTable
                DefaultTableModel model = (DefaultTableModel) patientHistoryTable.getModel();
                model.setRowCount(0);

                // Add the visit history to the patientHistoryTable
                while (rs.next()) {
                    String dataVisit[] = {rs.getString("visit_id"), rs.getString("reason"), rs.getString("price"), rs.getString("date")};
                    model.addRow(dataVisit);
                }

                // Hide the visit_id column if it's not already hidden
                if (patientHistoryTable.getColumnCount() > 3) {
                    TableColumnModel columnModel = patientHistoryTable.getColumnModel();
                    TableColumn visitIdColumn = columnModel.getColumn(0);
                    patientHistoryTable.removeColumn(visitIdColumn);
                }
            }
        } catch (SQLException ex) {
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

        mainCardPanel = new javax.swing.JPanel();
        mainMenuPanel = new javax.swing.JPanel();
        mainHeader = new javax.swing.JLabel();
        displayDashBoardButton = new javax.swing.JButton();
        usersDashBoardButton = new javax.swing.JButton();
        logOutButton = new javax.swing.JButton();
        recordListPanel = new javax.swing.JPanel();
        mainHeader2 = new javax.swing.JLabel();
        recordListPrimaryPanel = new javax.swing.JPanel();
        editPatientRecordButton = new javax.swing.JButton();
        tablePanel = new javax.swing.JScrollPane();
        patientRecordTable = new javax.swing.JTable();
        searchPatientLabel = new javax.swing.JLabel();
        searchPatientText = new javax.swing.JTextField();
        totalPatients = new javax.swing.JLabel();
        deletePatientRecordButton = new javax.swing.JButton();
        addPatientRecordButton = new javax.swing.JButton();
        readPatientRecordButton = new javax.swing.JButton();
        backPatientRecordButton = new javax.swing.JButton();
        refreshPatientTable = new javax.swing.JButton();
        printPatientTable = new javax.swing.JButton();
        patientRecordsLabel1 = new javax.swing.JLabel();
        createPanel = new javax.swing.JPanel();
        mainHeader1 = new javax.swing.JLabel();
        primaryCreatePanel = new javax.swing.JPanel();
        mainCreateLabel = new javax.swing.JLabel();
        patientInfoCreateLabel = new javax.swing.JLabel();
        givenNameCreateLabel = new javax.swing.JLabel();
        givenNameCreateText = new javax.swing.JTextField();
        lastNameCreateLabel = new javax.swing.JLabel();
        lastNameCreateText = new javax.swing.JTextField();
        middleNameCreateLabel = new javax.swing.JLabel();
        middleNameCreateText = new javax.swing.JTextField();
        ageCreateLabel = new javax.swing.JLabel();
        ageCreateText = new javax.swing.JTextField();
        civilStatusCreateLabel = new javax.swing.JLabel();
        civilStatusCreateText = new javax.swing.JTextField();
        occupationCreateLabel = new javax.swing.JLabel();
        occupationCreateText = new javax.swing.JTextField();
        contactNumCreateLabel = new javax.swing.JLabel();
        contactNumCreateText = new javax.swing.JTextField();
        addressCreateLabel = new javax.swing.JLabel();
        addressCreateText = new javax.swing.JTextField();
        patientVisitsCreateLabel = new javax.swing.JLabel();
        createCreateButton = new javax.swing.JButton();
        reasonCreateLabel = new javax.swing.JLabel();
        reasonCreateText = new javax.swing.JTextField();
        backCreateButton = new javax.swing.JButton();
        priceCreateLabel = new javax.swing.JLabel();
        priceCreateText = new javax.swing.JTextField();
        DateCreateLabel = new javax.swing.JLabel();
        dateCreateText = new javax.swing.JTextField();
        clearCreateButton = new javax.swing.JButton();
        updatePanel = new javax.swing.JPanel();
        mainHeader3 = new javax.swing.JLabel();
        primaryUpdatePanel = new javax.swing.JPanel();
        mainUpdateLabel = new javax.swing.JLabel();
        patientInfoUpdateLabel = new javax.swing.JLabel();
        givenNameUpdateLabel = new javax.swing.JLabel();
        givenNameUpdateText = new javax.swing.JTextField();
        lastNameUpdateLabel = new javax.swing.JLabel();
        lastNameUpdateText = new javax.swing.JTextField();
        middleNameUpdateLabel = new javax.swing.JLabel();
        middleNameUpdateText = new javax.swing.JTextField();
        ageUpdateLabel = new javax.swing.JLabel();
        ageUpdateText = new javax.swing.JTextField();
        civilStatusUpdateLabel = new javax.swing.JLabel();
        civilStatusUpdateText = new javax.swing.JTextField();
        occupationUpdateLabel = new javax.swing.JLabel();
        occupationUpdateText = new javax.swing.JTextField();
        contactNumUpdateLabel = new javax.swing.JLabel();
        contactNumUpdateText = new javax.swing.JTextField();
        addressUpdateLabel = new javax.swing.JLabel();
        addressUpdateText = new javax.swing.JTextField();
        patientVisitsUpdateLabel = new javax.swing.JLabel();
        updateUpdateButton = new javax.swing.JButton();
        reasonUpdateLabel = new javax.swing.JLabel();
        reasonUpdateText = new javax.swing.JTextField();
        backUpdateButton = new javax.swing.JButton();
        priceUpdateLabel = new javax.swing.JLabel();
        priceUpdateText = new javax.swing.JTextField();
        DateUpdateLabel = new javax.swing.JLabel();
        dateUpdateText = new javax.swing.JTextField();
        addVisitUpdateButton = new javax.swing.JButton();
        patientIDUpdateLabel = new javax.swing.JLabel();
        patientIDUpdateText = new javax.swing.JTextField();
        detailedPanel = new javax.swing.JPanel();
        mainHeader4 = new javax.swing.JLabel();
        primaryDetailsPanel = new javax.swing.JPanel();
        mainDetailsLabel = new javax.swing.JLabel();
        patientName = new javax.swing.JLabel();
        givenNameDetailsLabel = new javax.swing.JLabel();
        givenNameDetailsText = new javax.swing.JTextField();
        lastNameDetailsLabel = new javax.swing.JLabel();
        lastNameDetailsText = new javax.swing.JTextField();
        middleNameDetailsLabel = new javax.swing.JLabel();
        middleNameDetailsText = new javax.swing.JTextField();
        ageDetailsLabel = new javax.swing.JLabel();
        ageDetailsText = new javax.swing.JTextField();
        civilStatusDetailsLabel = new javax.swing.JLabel();
        civilStatusDetailsText = new javax.swing.JTextField();
        occupationDetailsLabel = new javax.swing.JLabel();
        occupationDetailsText = new javax.swing.JTextField();
        contactNumDetailsLabel = new javax.swing.JLabel();
        contactNumDetailsText = new javax.swing.JTextField();
        addressDetailsLabel = new javax.swing.JLabel();
        addressDetailsText = new javax.swing.JTextField();
        updateDetailsButton = new javax.swing.JButton();
        backDetailsButton = new javax.swing.JButton();
        addVisitDetailsButton = new javax.swing.JButton();
        tablePanel1 = new javax.swing.JScrollPane();
        patientHistoryTable = new javax.swing.JTable();
        givenNameDetailsLabel1 = new javax.swing.JLabel();
        patientIDDetailsText = new javax.swing.JTextField();
        refreshVisitHistory = new javax.swing.JButton();
        printTable = new javax.swing.JButton();
        deleteVisit = new javax.swing.JButton();
        totalDentalCost = new javax.swing.JLabel();
        summaryVisits = new javax.swing.JLabel();
        totalVisits = new javax.swing.JLabel();
        usersPanel = new javax.swing.JPanel();
        mainHeader5 = new javax.swing.JLabel();
        primaryDetailsPanel1 = new javax.swing.JPanel();
        mainUsersLabel = new javax.swing.JLabel();
        userInfoUsersLabel = new javax.swing.JLabel();
        userNameUsersLabel = new javax.swing.JLabel();
        usernameUsersText = new javax.swing.JTextField();
        passwordUsersLabel = new javax.swing.JLabel();
        passwordUsersText = new javax.swing.JTextField();
        updateUsersButton = new javax.swing.JButton();
        backUsersButton = new javax.swing.JButton();
        addUsersButton = new javax.swing.JButton();
        tablePanel2 = new javax.swing.JScrollPane();
        usersInfoTable = new javax.swing.JTable();
        accessLevelUsersLabel = new javax.swing.JLabel();
        accessLevelUsersText = new javax.swing.JTextField();
        refreshUsers = new javax.swing.JButton();
        deleteUsers = new javax.swing.JButton();
        mainMenuBar = new javax.swing.JMenuBar();
        menuMenuBar = new javax.swing.JMenu();
        actionsMenuBar = new javax.swing.JMenu();
        searchActionMenuBar = new javax.swing.JMenuItem();
        createActionMenuBar = new javax.swing.JMenuItem();
        usersMenuBar = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(86, 119, 156));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(800, 500));
        setPreferredSize(new java.awt.Dimension(1920, 1080));
        setSize(new java.awt.Dimension(1920, 1080));

        mainCardPanel.setLayout(new java.awt.CardLayout());

        mainMenuPanel.setBackground(new java.awt.Color(64, 102, 143));
        mainMenuPanel.setMaximumSize(new java.awt.Dimension(1920, 1080));
        mainMenuPanel.setPreferredSize(new java.awt.Dimension(1920, 1080));

        mainHeader.setBackground(new java.awt.Color(77, 161, 190));
        mainHeader.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 48)); // NOI18N
        mainHeader.setForeground(new java.awt.Color(255, 255, 255));
        mainHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainHeader.setText("Smile Care Dental Clinic");
        mainHeader.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainHeader.setOpaque(true);

        displayDashBoardButton.setBackground(new java.awt.Color(77, 161, 190));
        displayDashBoardButton.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        displayDashBoardButton.setForeground(new java.awt.Color(255, 255, 255));
        displayDashBoardButton.setText("DISPLAY PATIENT RECORDS");
        displayDashBoardButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        displayDashBoardButton.setFocusable(false);
        displayDashBoardButton.setOpaque(true);
        displayDashBoardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayDashBoardButtonActionPerformed(evt);
            }
        });

        usersDashBoardButton.setBackground(new java.awt.Color(77, 161, 190));
        usersDashBoardButton.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        usersDashBoardButton.setForeground(new java.awt.Color(255, 255, 255));
        usersDashBoardButton.setText("  SYSTEM USER OPTIONS");
        usersDashBoardButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        usersDashBoardButton.setFocusable(false);
        usersDashBoardButton.setOpaque(true);
        usersDashBoardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersDashBoardButtonActionPerformed(evt);
            }
        });

        logOutButton.setBackground(new java.awt.Color(77, 161, 190));
        logOutButton.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        logOutButton.setForeground(new java.awt.Color(255, 255, 255));
        logOutButton.setText("LOG OUT");
        logOutButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        logOutButton.setFocusable(false);
        logOutButton.setOpaque(true);
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainMenuPanelLayout = new javax.swing.GroupLayout(mainMenuPanel);
        mainMenuPanel.setLayout(mainMenuPanelLayout);
        mainMenuPanelLayout.setHorizontalGroup(
            mainMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 1920, Short.MAX_VALUE)
            .addGroup(mainMenuPanelLayout.createSequentialGroup()
                .addGap(783, 783, 783)
                .addGroup(mainMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(displayDashBoardButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usersDashBoardButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainMenuPanelLayout.setVerticalGroup(
            mainMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainMenuPanelLayout.createSequentialGroup()
                .addComponent(mainHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(153, 153, 153)
                .addComponent(displayDashBoardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(usersDashBoardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(logOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainCardPanel.add(mainMenuPanel, "mainMenu");

        recordListPanel.setBackground(new java.awt.Color(64, 102, 143));
        recordListPanel.setPreferredSize(new java.awt.Dimension(1920, 1080));

        mainHeader2.setBackground(new java.awt.Color(77, 161, 190));
        mainHeader2.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 48)); // NOI18N
        mainHeader2.setForeground(new java.awt.Color(255, 255, 255));
        mainHeader2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainHeader2.setText("Smile Care Dental Clinic");
        mainHeader2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainHeader2.setOpaque(true);

        recordListPrimaryPanel.setBackground(new java.awt.Color(86, 119, 156));
        recordListPrimaryPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        editPatientRecordButton.setBackground(new java.awt.Color(77, 161, 190));
        editPatientRecordButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        editPatientRecordButton.setForeground(new java.awt.Color(255, 255, 255));
        editPatientRecordButton.setText("EDIT");
        editPatientRecordButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        editPatientRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPatientRecordButtonActionPerformed(evt);
            }
        });

        patientRecordTable.setBackground(new java.awt.Color(86, 119, 156));
        patientRecordTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        patientRecordTable.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 18)); // NOI18N
        patientRecordTable.setForeground(new java.awt.Color(255, 255, 255));
        patientRecordTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient ID", "Given Name", "Last Name", "Middle Name", "Age", "Civil Status", "Occupation", "Contact #", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        patientRecordTable.setToolTipText("");
        patientRecordTable.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        patientRecordTable.setRowHeight(30);
        patientRecordTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        patientRecordTable.setShowGrid(true);
        patientRecordTable.getTableHeader().setReorderingAllowed(false);
        patientRecordTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                patientRecordTableMousePressed(evt);
            }
        });
        tablePanel.setViewportView(patientRecordTable);
        deletePatientRecordButton.setVisible(false); // Initially hide the button

        patientRecordTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                if (patientRecordTable.getSelectedRow() != -1) {
                    // A row is selected, show the button
                    deletePatientRecordButton.setVisible(true);
                } else {
                    // No row is selected, hide the button
                    deletePatientRecordButton.setVisible(false);
                }
            }
        });

        searchPatientLabel.setBackground(new java.awt.Color(101, 191, 175));
        searchPatientLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        searchPatientLabel.setForeground(new java.awt.Color(255, 255, 255));
        searchPatientLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchPatientLabel.setText("Search Patient:");
        searchPatientLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        searchPatientLabel.setOpaque(true);

        searchPatientText.setBackground(new java.awt.Color(175, 237, 226));
        searchPatientText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        searchPatientText.setForeground(new java.awt.Color(64, 102, 143));
        searchPatientText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchPatientTextActionPerformed(evt);
            }
        });

        totalPatients.setBackground(new java.awt.Color(77, 161, 190));
        totalPatients.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        totalPatients.setForeground(new java.awt.Color(255, 255, 255));
        totalPatients.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalPatients.setText("Total Patients");
        totalPatients.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        totalPatients.setOpaque(true);

        deletePatientRecordButton.setBackground(new java.awt.Color(204, 0, 51));
        deletePatientRecordButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        deletePatientRecordButton.setForeground(new java.awt.Color(255, 255, 255));
        deletePatientRecordButton.setText("DELETE");
        deletePatientRecordButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 0, 0), null, null));
        deletePatientRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePatientRecordButtonActionPerformed(evt);
            }
        });

        addPatientRecordButton.setBackground(new java.awt.Color(77, 161, 190));
        addPatientRecordButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        addPatientRecordButton.setForeground(new java.awt.Color(255, 255, 255));
        addPatientRecordButton.setText("ADD NEW");
        addPatientRecordButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addPatientRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPatientRecordButtonActionPerformed(evt);
            }
        });

        readPatientRecordButton.setBackground(new java.awt.Color(77, 161, 190));
        readPatientRecordButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        readPatientRecordButton.setForeground(new java.awt.Color(255, 255, 255));
        readPatientRecordButton.setText("DETAILS");
        readPatientRecordButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        readPatientRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readPatientRecordButtonActionPerformed(evt);
            }
        });

        backPatientRecordButton.setBackground(new java.awt.Color(77, 161, 190));
        backPatientRecordButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        backPatientRecordButton.setForeground(new java.awt.Color(255, 255, 255));
        backPatientRecordButton.setText("BACK");
        backPatientRecordButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backPatientRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backPatientRecordButtonActionPerformed(evt);
            }
        });

        refreshPatientTable.setBackground(new java.awt.Color(77, 161, 190));
        refreshPatientTable.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        refreshPatientTable.setForeground(new java.awt.Color(255, 255, 255));
        refreshPatientTable.setText("REFRESH");
        refreshPatientTable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        refreshPatientTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshPatientTableActionPerformed(evt);
            }
        });

        printPatientTable.setBackground(new java.awt.Color(77, 161, 190));
        printPatientTable.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        printPatientTable.setForeground(new java.awt.Color(255, 255, 255));
        printPatientTable.setText("PRINT");
        printPatientTable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        printPatientTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printPatientTableActionPerformed(evt);
            }
        });

        patientRecordsLabel1.setBackground(new java.awt.Color(77, 161, 190));
        patientRecordsLabel1.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        patientRecordsLabel1.setForeground(new java.awt.Color(255, 255, 255));
        patientRecordsLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        patientRecordsLabel1.setText("All Patient Records");
        patientRecordsLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        patientRecordsLabel1.setOpaque(true);

        javax.swing.GroupLayout recordListPrimaryPanelLayout = new javax.swing.GroupLayout(recordListPrimaryPanel);
        recordListPrimaryPanel.setLayout(recordListPrimaryPanelLayout);
        recordListPrimaryPanelLayout.setHorizontalGroup(
            recordListPrimaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordListPrimaryPanelLayout.createSequentialGroup()
                .addGroup(recordListPrimaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(recordListPrimaryPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(totalPatients, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(recordListPrimaryPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(searchPatientLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchPatientText, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deletePatientRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(refreshPatientTable, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addPatientRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(readPatientRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editPatientRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(recordListPrimaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(backPatientRecordButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(printPatientTable, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                    .addGroup(recordListPrimaryPanelLayout.createSequentialGroup()
                        .addContainerGap(60, Short.MAX_VALUE)
                        .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1660, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
            .addGroup(recordListPrimaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(recordListPrimaryPanelLayout.createSequentialGroup()
                    .addGap(70, 70, 70)
                    .addComponent(patientRecordsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1410, Short.MAX_VALUE)))
        );
        recordListPrimaryPanelLayout.setVerticalGroup(
            recordListPrimaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordListPrimaryPanelLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(backPatientRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(recordListPrimaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchPatientLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchPatientText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(printPatientTable, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editPatientRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(readPatientRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addPatientRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshPatientTable, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletePatientRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(totalPatients)
                .addContainerGap(10, Short.MAX_VALUE))
            .addGroup(recordListPrimaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(recordListPrimaryPanelLayout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addComponent(patientRecordsLabel1)
                    .addContainerGap(688, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout recordListPanelLayout = new javax.swing.GroupLayout(recordListPanel);
        recordListPanel.setLayout(recordListPanelLayout);
        recordListPanelLayout.setHorizontalGroup(
            recordListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainHeader2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(recordListPanelLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(recordListPrimaryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );
        recordListPanelLayout.setVerticalGroup(
            recordListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordListPanelLayout.createSequentialGroup()
                .addComponent(mainHeader2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(recordListPrimaryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainCardPanel.add(recordListPanel, "recordList");

        createPanel.setBackground(new java.awt.Color(64, 102, 143));

        mainHeader1.setBackground(new java.awt.Color(77, 161, 190));
        mainHeader1.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 48)); // NOI18N
        mainHeader1.setForeground(new java.awt.Color(255, 255, 255));
        mainHeader1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainHeader1.setText("Smile Care Dental Clinic");
        mainHeader1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainHeader1.setOpaque(true);

        primaryCreatePanel.setBackground(new java.awt.Color(86, 119, 156));
        primaryCreatePanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        primaryCreatePanel.setMaximumSize(new java.awt.Dimension(1724, 848));

        mainCreateLabel.setBackground(new java.awt.Color(77, 161, 190));
        mainCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        mainCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        mainCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainCreateLabel.setText("Create New Patient Record");
        mainCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        mainCreateLabel.setOpaque(true);

        patientInfoCreateLabel.setBackground(new java.awt.Color(77, 161, 190));
        patientInfoCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        patientInfoCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        patientInfoCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        patientInfoCreateLabel.setText("Patient Information");
        patientInfoCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        patientInfoCreateLabel.setOpaque(true);

        givenNameCreateLabel.setBackground(new java.awt.Color(101, 191, 175));
        givenNameCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        givenNameCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        givenNameCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        givenNameCreateLabel.setText("Given Name:");
        givenNameCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        givenNameCreateLabel.setOpaque(true);

        givenNameCreateText.setBackground(new java.awt.Color(175, 237, 226));
        givenNameCreateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        givenNameCreateText.setForeground(new java.awt.Color(64, 102, 143));

        lastNameCreateLabel.setBackground(new java.awt.Color(101, 191, 175));
        lastNameCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        lastNameCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        lastNameCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lastNameCreateLabel.setText("Last Name:");
        lastNameCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        lastNameCreateLabel.setOpaque(true);

        lastNameCreateText.setBackground(new java.awt.Color(175, 237, 226));
        lastNameCreateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        lastNameCreateText.setForeground(new java.awt.Color(64, 102, 143));
        lastNameCreateText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameCreateTextActionPerformed(evt);
            }
        });

        middleNameCreateLabel.setBackground(new java.awt.Color(101, 191, 175));
        middleNameCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        middleNameCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        middleNameCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        middleNameCreateLabel.setText("Middle Name:");
        middleNameCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        middleNameCreateLabel.setOpaque(true);

        middleNameCreateText.setBackground(new java.awt.Color(175, 237, 226));
        middleNameCreateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        middleNameCreateText.setForeground(new java.awt.Color(64, 102, 143));
        middleNameCreateText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                middleNameCreateTextActionPerformed(evt);
            }
        });

        ageCreateLabel.setBackground(new java.awt.Color(101, 191, 175));
        ageCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        ageCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        ageCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ageCreateLabel.setText("Age:");
        ageCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        ageCreateLabel.setOpaque(true);

        ageCreateText.setBackground(new java.awt.Color(175, 237, 226));
        ageCreateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        ageCreateText.setForeground(new java.awt.Color(64, 102, 143));

        civilStatusCreateLabel.setBackground(new java.awt.Color(101, 191, 175));
        civilStatusCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        civilStatusCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        civilStatusCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        civilStatusCreateLabel.setText("Civil Status:");
        civilStatusCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        civilStatusCreateLabel.setOpaque(true);

        civilStatusCreateText.setBackground(new java.awt.Color(175, 237, 226));
        civilStatusCreateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        civilStatusCreateText.setForeground(new java.awt.Color(64, 102, 143));

        occupationCreateLabel.setBackground(new java.awt.Color(101, 191, 175));
        occupationCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        occupationCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        occupationCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        occupationCreateLabel.setText("Occupation:");
        occupationCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        occupationCreateLabel.setOpaque(true);

        occupationCreateText.setBackground(new java.awt.Color(175, 237, 226));
        occupationCreateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        occupationCreateText.setForeground(new java.awt.Color(64, 102, 143));

        contactNumCreateLabel.setBackground(new java.awt.Color(101, 191, 175));
        contactNumCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        contactNumCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        contactNumCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contactNumCreateLabel.setText("Contact #:");
        contactNumCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        contactNumCreateLabel.setOpaque(true);

        contactNumCreateText.setBackground(new java.awt.Color(175, 237, 226));
        contactNumCreateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        contactNumCreateText.setForeground(new java.awt.Color(64, 102, 143));

        addressCreateLabel.setBackground(new java.awt.Color(101, 191, 175));
        addressCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        addressCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        addressCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addressCreateLabel.setText("Address:");
        addressCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        addressCreateLabel.setOpaque(true);

        addressCreateText.setBackground(new java.awt.Color(175, 237, 226));
        addressCreateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        addressCreateText.setForeground(new java.awt.Color(64, 102, 143));

        patientVisitsCreateLabel.setBackground(new java.awt.Color(77, 161, 190));
        patientVisitsCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        patientVisitsCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        patientVisitsCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        patientVisitsCreateLabel.setText("Patient First Visit Details");
        patientVisitsCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        patientVisitsCreateLabel.setOpaque(true);

        createCreateButton.setBackground(new java.awt.Color(77, 161, 190));
        createCreateButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        createCreateButton.setForeground(new java.awt.Color(255, 255, 255));
        createCreateButton.setText("CREATE");
        createCreateButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        createCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCreateButtonActionPerformed(evt);
            }
        });

        reasonCreateLabel.setBackground(new java.awt.Color(101, 191, 175));
        reasonCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        reasonCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        reasonCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reasonCreateLabel.setText("Reason:");
        reasonCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        reasonCreateLabel.setOpaque(true);

        reasonCreateText.setBackground(new java.awt.Color(175, 237, 226));
        reasonCreateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        reasonCreateText.setForeground(new java.awt.Color(64, 102, 143));

        backCreateButton.setBackground(new java.awt.Color(77, 161, 190));
        backCreateButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        backCreateButton.setForeground(new java.awt.Color(255, 255, 255));
        backCreateButton.setText("BACK");
        backCreateButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backCreateButtonActionPerformed(evt);
            }
        });

        priceCreateLabel.setBackground(new java.awt.Color(101, 191, 175));
        priceCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        priceCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        priceCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        priceCreateLabel.setText("Price:");
        priceCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        priceCreateLabel.setOpaque(true);

        priceCreateText.setBackground(new java.awt.Color(175, 237, 226));
        priceCreateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        priceCreateText.setForeground(new java.awt.Color(64, 102, 143));

        DateCreateLabel.setBackground(new java.awt.Color(101, 191, 175));
        DateCreateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        DateCreateLabel.setForeground(new java.awt.Color(255, 255, 255));
        DateCreateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DateCreateLabel.setText("Date:");
        DateCreateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        DateCreateLabel.setOpaque(true);

        dateCreateText.setBackground(new java.awt.Color(175, 237, 226));
        dateCreateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        dateCreateText.setForeground(new java.awt.Color(64, 102, 143));

        clearCreateButton.setBackground(new java.awt.Color(77, 161, 190));
        clearCreateButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        clearCreateButton.setForeground(new java.awt.Color(255, 255, 255));
        clearCreateButton.setText("CLEAR");
        clearCreateButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        clearCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearCreateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout primaryCreatePanelLayout = new javax.swing.GroupLayout(primaryCreatePanel);
        primaryCreatePanel.setLayout(primaryCreatePanelLayout);
        primaryCreatePanelLayout.setHorizontalGroup(
            primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(mainCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                            .addComponent(addressCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(addressCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                            .addComponent(contactNumCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(contactNumCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                            .addComponent(occupationCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(occupationCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                            .addComponent(civilStatusCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(civilStatusCreateText))
                        .addComponent(patientInfoCreateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                        .addComponent(lastNameCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastNameCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                        .addComponent(givenNameCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(givenNameCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                        .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(middleNameCreateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                            .addComponent(ageCreateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ageCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, primaryCreatePanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(middleNameCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(220, 220, 220)
                .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                            .addComponent(DateCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(dateCreateText))
                        .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                            .addComponent(reasonCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(reasonCreateText))
                        .addComponent(patientVisitsCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                            .addComponent(priceCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(priceCreateText)))
                    .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                        .addComponent(clearCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(createCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 542, Short.MAX_VALUE))
        );
        primaryCreatePanelLayout.setVerticalGroup(
            primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(patientInfoCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patientVisitsCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(givenNameCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(givenNameCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reasonCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reasonCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNameCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(middleNameCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(middleNameCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DateCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ageCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ageCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(civilStatusCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(civilStatusCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(occupationCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(occupationCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(primaryCreatePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(contactNumCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactNumCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addressCreateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addressCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, primaryCreatePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addGroup(primaryCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(createCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(150, 150, 150))))
        );

        javax.swing.GroupLayout createPanelLayout = new javax.swing.GroupLayout(createPanel);
        createPanel.setLayout(createPanelLayout);
        createPanelLayout.setHorizontalGroup(
            createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainHeader1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(createPanelLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(primaryCreatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );
        createPanelLayout.setVerticalGroup(
            createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createPanelLayout.createSequentialGroup()
                .addComponent(mainHeader1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(primaryCreatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(181, Short.MAX_VALUE))
        );

        mainCardPanel.add(createPanel, "createPanel");

        updatePanel.setBackground(new java.awt.Color(64, 102, 143));

        mainHeader3.setBackground(new java.awt.Color(77, 161, 190));
        mainHeader3.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 48)); // NOI18N
        mainHeader3.setForeground(new java.awt.Color(255, 255, 255));
        mainHeader3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainHeader3.setText("Smile Care Dental Clinic");
        mainHeader3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainHeader3.setOpaque(true);

        primaryUpdatePanel.setBackground(new java.awt.Color(86, 119, 156));
        primaryUpdatePanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        primaryUpdatePanel.setMaximumSize(new java.awt.Dimension(1724, 848));

        mainUpdateLabel.setBackground(new java.awt.Color(77, 161, 190));
        mainUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        mainUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        mainUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainUpdateLabel.setText("Update Patient Record");
        mainUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        mainUpdateLabel.setOpaque(true);

        patientInfoUpdateLabel.setBackground(new java.awt.Color(77, 161, 190));
        patientInfoUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        patientInfoUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        patientInfoUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        patientInfoUpdateLabel.setText("Patient Information");
        patientInfoUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        patientInfoUpdateLabel.setOpaque(true);

        givenNameUpdateLabel.setBackground(new java.awt.Color(101, 191, 175));
        givenNameUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        givenNameUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        givenNameUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        givenNameUpdateLabel.setText("Given Name:");
        givenNameUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        givenNameUpdateLabel.setOpaque(true);

        givenNameUpdateText.setBackground(new java.awt.Color(175, 237, 226));
        givenNameUpdateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        givenNameUpdateText.setForeground(new java.awt.Color(64, 102, 143));

        lastNameUpdateLabel.setBackground(new java.awt.Color(101, 191, 175));
        lastNameUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        lastNameUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        lastNameUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lastNameUpdateLabel.setText("Last Name:");
        lastNameUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        lastNameUpdateLabel.setOpaque(true);

        lastNameUpdateText.setBackground(new java.awt.Color(175, 237, 226));
        lastNameUpdateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        lastNameUpdateText.setForeground(new java.awt.Color(64, 102, 143));

        middleNameUpdateLabel.setBackground(new java.awt.Color(101, 191, 175));
        middleNameUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        middleNameUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        middleNameUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        middleNameUpdateLabel.setText("Middle Name:");
        middleNameUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        middleNameUpdateLabel.setOpaque(true);

        middleNameUpdateText.setBackground(new java.awt.Color(175, 237, 226));
        middleNameUpdateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        middleNameUpdateText.setForeground(new java.awt.Color(64, 102, 143));

        ageUpdateLabel.setBackground(new java.awt.Color(101, 191, 175));
        ageUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        ageUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        ageUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ageUpdateLabel.setText("Age:");
        ageUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        ageUpdateLabel.setOpaque(true);

        ageUpdateText.setBackground(new java.awt.Color(175, 237, 226));
        ageUpdateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        ageUpdateText.setForeground(new java.awt.Color(64, 102, 143));

        civilStatusUpdateLabel.setBackground(new java.awt.Color(101, 191, 175));
        civilStatusUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        civilStatusUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        civilStatusUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        civilStatusUpdateLabel.setText("Civil Status:");
        civilStatusUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        civilStatusUpdateLabel.setOpaque(true);

        civilStatusUpdateText.setBackground(new java.awt.Color(175, 237, 226));
        civilStatusUpdateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        civilStatusUpdateText.setForeground(new java.awt.Color(64, 102, 143));

        occupationUpdateLabel.setBackground(new java.awt.Color(101, 191, 175));
        occupationUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        occupationUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        occupationUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        occupationUpdateLabel.setText("Occupation:");
        occupationUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        occupationUpdateLabel.setOpaque(true);

        occupationUpdateText.setBackground(new java.awt.Color(175, 237, 226));
        occupationUpdateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        occupationUpdateText.setForeground(new java.awt.Color(64, 102, 143));

        contactNumUpdateLabel.setBackground(new java.awt.Color(101, 191, 175));
        contactNumUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        contactNumUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        contactNumUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contactNumUpdateLabel.setText("Contact #:");
        contactNumUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        contactNumUpdateLabel.setOpaque(true);

        contactNumUpdateText.setBackground(new java.awt.Color(175, 237, 226));
        contactNumUpdateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        contactNumUpdateText.setForeground(new java.awt.Color(64, 102, 143));

        addressUpdateLabel.setBackground(new java.awt.Color(101, 191, 175));
        addressUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        addressUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        addressUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addressUpdateLabel.setText("Address:");
        addressUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        addressUpdateLabel.setOpaque(true);

        addressUpdateText.setBackground(new java.awt.Color(175, 237, 226));
        addressUpdateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        addressUpdateText.setForeground(new java.awt.Color(64, 102, 143));
        addressUpdateText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressUpdateTextActionPerformed(evt);
            }
        });

        patientVisitsUpdateLabel.setBackground(new java.awt.Color(77, 161, 190));
        patientVisitsUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        patientVisitsUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        patientVisitsUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        patientVisitsUpdateLabel.setText("Add Patient Visit ");
        patientVisitsUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        patientVisitsUpdateLabel.setOpaque(true);

        updateUpdateButton.setBackground(new java.awt.Color(77, 161, 190));
        updateUpdateButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        updateUpdateButton.setForeground(new java.awt.Color(255, 255, 255));
        updateUpdateButton.setText("UPDATE");
        updateUpdateButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        updateUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateUpdateButtonActionPerformed(evt);
            }
        });

        reasonUpdateLabel.setBackground(new java.awt.Color(101, 191, 175));
        reasonUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        reasonUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        reasonUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reasonUpdateLabel.setText("Reason:");
        reasonUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        reasonUpdateLabel.setOpaque(true);

        reasonUpdateText.setBackground(new java.awt.Color(175, 237, 226));
        reasonUpdateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        reasonUpdateText.setForeground(new java.awt.Color(64, 102, 143));

        backUpdateButton.setBackground(new java.awt.Color(77, 161, 190));
        backUpdateButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        backUpdateButton.setForeground(new java.awt.Color(255, 255, 255));
        backUpdateButton.setText("BACK");
        backUpdateButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backUpdateButtonActionPerformed(evt);
            }
        });

        priceUpdateLabel.setBackground(new java.awt.Color(101, 191, 175));
        priceUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        priceUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        priceUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        priceUpdateLabel.setText("Price:");
        priceUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        priceUpdateLabel.setOpaque(true);

        priceUpdateText.setBackground(new java.awt.Color(175, 237, 226));
        priceUpdateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        priceUpdateText.setForeground(new java.awt.Color(64, 102, 143));

        DateUpdateLabel.setBackground(new java.awt.Color(101, 191, 175));
        DateUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        DateUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        DateUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DateUpdateLabel.setText("Date:");
        DateUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        DateUpdateLabel.setOpaque(true);

        dateUpdateText.setBackground(new java.awt.Color(175, 237, 226));
        dateUpdateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        dateUpdateText.setForeground(new java.awt.Color(64, 102, 143));

        addVisitUpdateButton.setBackground(new java.awt.Color(77, 161, 190));
        addVisitUpdateButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        addVisitUpdateButton.setForeground(new java.awt.Color(255, 255, 255));
        addVisitUpdateButton.setText("ADD VISIT");
        addVisitUpdateButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addVisitUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVisitUpdateButtonActionPerformed(evt);
            }
        });

        patientIDUpdateLabel.setBackground(new java.awt.Color(101, 191, 175));
        patientIDUpdateLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        patientIDUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        patientIDUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        patientIDUpdateLabel.setText("Patient ID:");
        patientIDUpdateLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        patientIDUpdateLabel.setOpaque(true);

        patientIDUpdateText.setEditable(false);
        patientIDUpdateText.setBackground(new java.awt.Color(175, 237, 226));
        patientIDUpdateText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        patientIDUpdateText.setForeground(new java.awt.Color(64, 102, 143));

        javax.swing.GroupLayout primaryUpdatePanelLayout = new javax.swing.GroupLayout(primaryUpdatePanel);
        primaryUpdatePanel.setLayout(primaryUpdatePanelLayout);
        primaryUpdatePanelLayout.setHorizontalGroup(
            primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                        .addComponent(middleNameUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(middleNameUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                        .addComponent(givenNameUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(givenNameUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                        .addComponent(lastNameUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastNameUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                        .addComponent(ageUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ageUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                        .addComponent(civilStatusUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(civilStatusUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                        .addComponent(contactNumUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contactNumUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                        .addComponent(occupationUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(occupationUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                        .addComponent(addressUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addressUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                        .addComponent(patientIDUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(patientIDUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(patientInfoUpdateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(151, 151, 151)
                .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(patientVisitsUpdateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, primaryUpdatePanelLayout.createSequentialGroup()
                            .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(reasonUpdateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(priceUpdateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(DateUpdateLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(reasonUpdateText)
                                .addComponent(priceUpdateText)
                                .addComponent(dateUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                        .addComponent(updateUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addVisitUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(634, Short.MAX_VALUE))
            .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(mainUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        primaryUpdatePanelLayout.setVerticalGroup(
            primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                        .addComponent(patientInfoUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(patientIDUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(patientIDUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(givenNameUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(givenNameUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lastNameUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastNameUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(primaryUpdatePanelLayout.createSequentialGroup()
                        .addComponent(patientVisitsUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reasonUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reasonUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(priceUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(priceUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DateUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(middleNameUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(middleNameUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ageUpdateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ageUpdateText, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(civilStatusUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(civilStatusUpdateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(occupationUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(occupationUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contactNumUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactNumUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addVisitUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(updateUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addressUpdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addressUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(141, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout updatePanelLayout = new javax.swing.GroupLayout(updatePanel);
        updatePanel.setLayout(updatePanelLayout);
        updatePanelLayout.setHorizontalGroup(
            updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainHeader3, javax.swing.GroupLayout.DEFAULT_SIZE, 1920, Short.MAX_VALUE)
            .addGroup(updatePanelLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(primaryUpdatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        updatePanelLayout.setVerticalGroup(
            updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatePanelLayout.createSequentialGroup()
                .addComponent(mainHeader3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(primaryUpdatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        mainCardPanel.add(updatePanel, "updatePanel");

        detailedPanel.setBackground(new java.awt.Color(64, 102, 143));

        mainHeader4.setBackground(new java.awt.Color(77, 161, 190));
        mainHeader4.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 48)); // NOI18N
        mainHeader4.setForeground(new java.awt.Color(255, 255, 255));
        mainHeader4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainHeader4.setText("Smile Care Dental Clinic");
        mainHeader4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainHeader4.setOpaque(true);

        primaryDetailsPanel.setBackground(new java.awt.Color(86, 119, 156));
        primaryDetailsPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        primaryDetailsPanel.setMaximumSize(new java.awt.Dimension(1724, 848));

        mainDetailsLabel.setBackground(new java.awt.Color(77, 161, 190));
        mainDetailsLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        mainDetailsLabel.setForeground(new java.awt.Color(255, 255, 255));
        mainDetailsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainDetailsLabel.setText("Patient Details");
        mainDetailsLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        mainDetailsLabel.setOpaque(true);

        patientName.setBackground(new java.awt.Color(77, 161, 190));
        patientName.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        patientName.setForeground(new java.awt.Color(255, 255, 255));
        patientName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        String givenName = givenNameDetailsText.getText();
        String lastName = lastNameDetailsText.getText();
        patientName.setText("Patient Name");
        patientName.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        patientName.setOpaque(true);

        givenNameDetailsLabel.setBackground(new java.awt.Color(101, 191, 175));
        givenNameDetailsLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        givenNameDetailsLabel.setForeground(new java.awt.Color(255, 255, 255));
        givenNameDetailsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        givenNameDetailsLabel.setText("Given Name:");
        givenNameDetailsLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        givenNameDetailsLabel.setOpaque(true);

        givenNameDetailsText.setEditable(false);
        givenNameDetailsText.setBackground(new java.awt.Color(175, 237, 226));
        givenNameDetailsText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        givenNameDetailsText.setForeground(new java.awt.Color(64, 102, 143));

        lastNameDetailsLabel.setBackground(new java.awt.Color(101, 191, 175));
        lastNameDetailsLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        lastNameDetailsLabel.setForeground(new java.awt.Color(255, 255, 255));
        lastNameDetailsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lastNameDetailsLabel.setText("Last Name:");
        lastNameDetailsLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        lastNameDetailsLabel.setOpaque(true);

        lastNameDetailsText.setEditable(false);
        lastNameDetailsText.setBackground(new java.awt.Color(175, 237, 226));
        lastNameDetailsText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        lastNameDetailsText.setForeground(new java.awt.Color(64, 102, 143));

        middleNameDetailsLabel.setBackground(new java.awt.Color(101, 191, 175));
        middleNameDetailsLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        middleNameDetailsLabel.setForeground(new java.awt.Color(255, 255, 255));
        middleNameDetailsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        middleNameDetailsLabel.setText("Middle Name:");
        middleNameDetailsLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        middleNameDetailsLabel.setOpaque(true);

        middleNameDetailsText.setEditable(false);
        middleNameDetailsText.setBackground(new java.awt.Color(175, 237, 226));
        middleNameDetailsText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        middleNameDetailsText.setForeground(new java.awt.Color(64, 102, 143));
        middleNameDetailsText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                middleNameDetailsTextActionPerformed(evt);
            }
        });

        ageDetailsLabel.setBackground(new java.awt.Color(101, 191, 175));
        ageDetailsLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        ageDetailsLabel.setForeground(new java.awt.Color(255, 255, 255));
        ageDetailsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ageDetailsLabel.setText("Age:");
        ageDetailsLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        ageDetailsLabel.setOpaque(true);

        ageDetailsText.setEditable(false);
        ageDetailsText.setBackground(new java.awt.Color(175, 237, 226));
        ageDetailsText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        ageDetailsText.setForeground(new java.awt.Color(64, 102, 143));

        civilStatusDetailsLabel.setBackground(new java.awt.Color(101, 191, 175));
        civilStatusDetailsLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        civilStatusDetailsLabel.setForeground(new java.awt.Color(255, 255, 255));
        civilStatusDetailsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        civilStatusDetailsLabel.setText("Civil Status:");
        civilStatusDetailsLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        civilStatusDetailsLabel.setOpaque(true);

        civilStatusDetailsText.setEditable(false);
        civilStatusDetailsText.setBackground(new java.awt.Color(175, 237, 226));
        civilStatusDetailsText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        civilStatusDetailsText.setForeground(new java.awt.Color(64, 102, 143));

        occupationDetailsLabel.setBackground(new java.awt.Color(101, 191, 175));
        occupationDetailsLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        occupationDetailsLabel.setForeground(new java.awt.Color(255, 255, 255));
        occupationDetailsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        occupationDetailsLabel.setText("Occupation:");
        occupationDetailsLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        occupationDetailsLabel.setOpaque(true);

        occupationDetailsText.setEditable(false);
        occupationDetailsText.setBackground(new java.awt.Color(175, 237, 226));
        occupationDetailsText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        occupationDetailsText.setForeground(new java.awt.Color(64, 102, 143));

        contactNumDetailsLabel.setBackground(new java.awt.Color(101, 191, 175));
        contactNumDetailsLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        contactNumDetailsLabel.setForeground(new java.awt.Color(255, 255, 255));
        contactNumDetailsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contactNumDetailsLabel.setText("Contact #:");
        contactNumDetailsLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        contactNumDetailsLabel.setOpaque(true);

        contactNumDetailsText.setEditable(false);
        contactNumDetailsText.setBackground(new java.awt.Color(175, 237, 226));
        contactNumDetailsText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        contactNumDetailsText.setForeground(new java.awt.Color(64, 102, 143));

        addressDetailsLabel.setBackground(new java.awt.Color(101, 191, 175));
        addressDetailsLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        addressDetailsLabel.setForeground(new java.awt.Color(255, 255, 255));
        addressDetailsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addressDetailsLabel.setText("Address:");
        addressDetailsLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        addressDetailsLabel.setOpaque(true);

        addressDetailsText.setEditable(false);
        addressDetailsText.setBackground(new java.awt.Color(175, 237, 226));
        addressDetailsText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        addressDetailsText.setForeground(new java.awt.Color(64, 102, 143));

        updateDetailsButton.setBackground(new java.awt.Color(77, 161, 190));
        updateDetailsButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        updateDetailsButton.setForeground(new java.awt.Color(255, 255, 255));
        updateDetailsButton.setText("UPDATE");
        updateDetailsButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        updateDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateDetailsButtonActionPerformed(evt);
            }
        });

        backDetailsButton.setBackground(new java.awt.Color(77, 161, 190));
        backDetailsButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        backDetailsButton.setForeground(new java.awt.Color(255, 255, 255));
        backDetailsButton.setText("BACK");
        backDetailsButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backDetailsButtonActionPerformed(evt);
            }
        });

        addVisitDetailsButton.setBackground(new java.awt.Color(77, 161, 190));
        addVisitDetailsButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        addVisitDetailsButton.setForeground(new java.awt.Color(255, 255, 255));
        addVisitDetailsButton.setText("ADD VISIT");
        addVisitDetailsButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addVisitDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVisitDetailsButtonActionPerformed(evt);
            }
        });

        patientHistoryTable.setBackground(new java.awt.Color(86, 119, 156));
        patientHistoryTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        patientHistoryTable.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 12)); // NOI18N
        patientHistoryTable.setForeground(new java.awt.Color(255, 255, 255));
        patientHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "visit_id", "Visit Reason", "Price", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        patientHistoryTable.setRowHeight(30);
        patientHistoryTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        patientHistoryTable.setShowGrid(true);
        patientHistoryTable.getTableHeader().setReorderingAllowed(false);
        tablePanel1.setViewportView(patientHistoryTable);
        if (patientHistoryTable.getColumnModel().getColumnCount() > 0) {
            patientHistoryTable.getColumnModel().getColumn(0).setResizable(false);
        }
        deleteVisit.setVisible(false); // Initially hide the button

        patientHistoryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                if (patientHistoryTable.getSelectedRow() != -1) {
                    // A row is selected, show the button
                    deleteVisit.setVisible(true);
                } else {
                    // No row is selected, hide the button
                    deleteVisit.setVisible(false);
                }
            }
        });

        givenNameDetailsLabel1.setBackground(new java.awt.Color(101, 191, 175));
        givenNameDetailsLabel1.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        givenNameDetailsLabel1.setForeground(new java.awt.Color(255, 255, 255));
        givenNameDetailsLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        givenNameDetailsLabel1.setText("Patient ID:");
        givenNameDetailsLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        givenNameDetailsLabel1.setOpaque(true);

        patientIDDetailsText.setEditable(false);
        patientIDDetailsText.setBackground(new java.awt.Color(175, 237, 226));
        patientIDDetailsText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        patientIDDetailsText.setForeground(new java.awt.Color(64, 102, 143));
        patientIDDetailsText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientIDDetailsTextActionPerformed(evt);
            }
        });

        refreshVisitHistory.setBackground(new java.awt.Color(77, 161, 190));
        refreshVisitHistory.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        refreshVisitHistory.setForeground(new java.awt.Color(255, 255, 255));
        refreshVisitHistory.setText("REFRESH");
        refreshVisitHistory.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        refreshVisitHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshVisitHistoryActionPerformed(evt);
            }
        });

        printTable.setBackground(new java.awt.Color(77, 161, 190));
        printTable.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        printTable.setForeground(new java.awt.Color(255, 255, 255));
        printTable.setText("PRINT");
        printTable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        printTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printTableActionPerformed(evt);
            }
        });

        deleteVisit.setBackground(new java.awt.Color(153, 0, 0));
        deleteVisit.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        deleteVisit.setForeground(new java.awt.Color(255, 255, 255));
        deleteVisit.setText("DELETE");
        deleteVisit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deleteVisit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteVisitActionPerformed(evt);
            }
        });

        totalDentalCost.setBackground(new java.awt.Color(77, 161, 190));
        totalDentalCost.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        totalDentalCost.setForeground(new java.awt.Color(255, 255, 255));
        totalDentalCost.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalDentalCost.setText("Total Dental Cost");
        totalDentalCost.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        totalDentalCost.setOpaque(true);

        summaryVisits.setBackground(new java.awt.Color(77, 161, 190));
        summaryVisits.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        summaryVisits.setForeground(new java.awt.Color(255, 255, 255));
        summaryVisits.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        summaryVisits.setText("Summary");
        summaryVisits.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        summaryVisits.setOpaque(true);

        totalVisits.setBackground(new java.awt.Color(77, 161, 190));
        totalVisits.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        totalVisits.setForeground(new java.awt.Color(255, 255, 255));
        totalVisits.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalVisits.setText("Total Visits");
        totalVisits.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        totalVisits.setOpaque(true);

        javax.swing.GroupLayout primaryDetailsPanelLayout = new javax.swing.GroupLayout(primaryDetailsPanel);
        primaryDetailsPanel.setLayout(primaryDetailsPanelLayout);
        primaryDetailsPanelLayout.setHorizontalGroup(
            primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(mainDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                                .addComponent(addressDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addressDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                                .addComponent(middleNameDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(middleNameDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                                .addComponent(givenNameDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(givenNameDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                                .addComponent(lastNameDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lastNameDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                                .addComponent(ageDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ageDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                                .addComponent(civilStatusDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(civilStatusDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                                .addComponent(contactNumDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(contactNumDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                                .addComponent(occupationDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(occupationDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                                .addComponent(givenNameDetailsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(patientIDDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(patientName, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(updateDetailsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(23, 23, 23)
                .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                        .addComponent(backDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                        .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(totalDentalCost, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(summaryVisits, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                            .addComponent(totalVisits, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tablePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(refreshVisitHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addVisitDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(printTable, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteVisit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(129, 129, 129))))
        );
        primaryDetailsPanelLayout.setVerticalGroup(
            primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(mainDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(backDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                        .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                                .addComponent(patientName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(givenNameDetailsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(patientIDDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(givenNameDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(givenNameDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, primaryDetailsPanelLayout.createSequentialGroup()
                                .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(summaryVisits, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                                        .addGap(64, 64, 64)
                                        .addComponent(totalVisits, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(totalDentalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lastNameDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastNameDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(middleNameDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(middleNameDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ageDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ageDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(civilStatusDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(civilStatusDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(occupationDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(occupationDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(contactNumDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactNumDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(primaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addressDetailsText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addressDetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(primaryDetailsPanelLayout.createSequentialGroup()
                        .addComponent(addVisitDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(refreshVisitHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(printTable, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteVisit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(177, 177, 177))
                    .addComponent(tablePanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(updateDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout detailedPanelLayout = new javax.swing.GroupLayout(detailedPanel);
        detailedPanel.setLayout(detailedPanelLayout);
        detailedPanelLayout.setHorizontalGroup(
            detailedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainHeader4, javax.swing.GroupLayout.DEFAULT_SIZE, 1920, Short.MAX_VALUE)
            .addGroup(detailedPanelLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(primaryDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        detailedPanelLayout.setVerticalGroup(
            detailedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailedPanelLayout.createSequentialGroup()
                .addComponent(mainHeader4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(primaryDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        mainCardPanel.add(detailedPanel, "detailedPanel");

        usersPanel.setBackground(new java.awt.Color(64, 102, 143));

        mainHeader5.setBackground(new java.awt.Color(77, 161, 190));
        mainHeader5.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 48)); // NOI18N
        mainHeader5.setForeground(new java.awt.Color(255, 255, 255));
        mainHeader5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainHeader5.setText("Smile Care Dental Clinic");
        mainHeader5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainHeader5.setOpaque(true);

        primaryDetailsPanel1.setBackground(new java.awt.Color(86, 119, 156));
        primaryDetailsPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        primaryDetailsPanel1.setMaximumSize(new java.awt.Dimension(1724, 848));

        mainUsersLabel.setBackground(new java.awt.Color(77, 161, 190));
        mainUsersLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        mainUsersLabel.setForeground(new java.awt.Color(255, 255, 255));
        mainUsersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainUsersLabel.setText("User Credentials");
        mainUsersLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        mainUsersLabel.setOpaque(true);

        userInfoUsersLabel.setBackground(new java.awt.Color(77, 161, 190));
        userInfoUsersLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 18)); // NOI18N
        userInfoUsersLabel.setForeground(new java.awt.Color(255, 255, 255));
        userInfoUsersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userInfoUsersLabel.setText("User Information");
        userInfoUsersLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        userInfoUsersLabel.setOpaque(true);

        userNameUsersLabel.setBackground(new java.awt.Color(101, 191, 175));
        userNameUsersLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        userNameUsersLabel.setForeground(new java.awt.Color(255, 255, 255));
        userNameUsersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userNameUsersLabel.setText("Username:");
        userNameUsersLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        userNameUsersLabel.setOpaque(true);

        usernameUsersText.setBackground(new java.awt.Color(175, 237, 226));
        usernameUsersText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        usernameUsersText.setForeground(new java.awt.Color(64, 102, 143));

        passwordUsersLabel.setBackground(new java.awt.Color(101, 191, 175));
        passwordUsersLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        passwordUsersLabel.setForeground(new java.awt.Color(255, 255, 255));
        passwordUsersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        passwordUsersLabel.setText("Password:");
        passwordUsersLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        passwordUsersLabel.setOpaque(true);

        passwordUsersText.setBackground(new java.awt.Color(175, 237, 226));
        passwordUsersText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        passwordUsersText.setForeground(new java.awt.Color(64, 102, 143));

        updateUsersButton.setBackground(new java.awt.Color(77, 161, 190));
        updateUsersButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        updateUsersButton.setForeground(new java.awt.Color(255, 255, 255));
        updateUsersButton.setText("UPDATE");
        updateUsersButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        updateUsersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateUsersButtonActionPerformed(evt);
            }
        });

        backUsersButton.setBackground(new java.awt.Color(77, 161, 190));
        backUsersButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        backUsersButton.setForeground(new java.awt.Color(255, 255, 255));
        backUsersButton.setText("BACK");
        backUsersButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backUsersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backUsersButtonActionPerformed(evt);
            }
        });

        addUsersButton.setBackground(new java.awt.Color(77, 161, 190));
        addUsersButton.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        addUsersButton.setForeground(new java.awt.Color(255, 255, 255));
        addUsersButton.setText("ADD USER");
        addUsersButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addUsersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUsersButtonActionPerformed(evt);
            }
        });

        usersInfoTable.setBackground(new java.awt.Color(86, 119, 156));
        usersInfoTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        usersInfoTable.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 12)); // NOI18N
        usersInfoTable.setForeground(new java.awt.Color(255, 255, 255));
        usersInfoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "usersID", "Access Level", "Username", "Password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        usersInfoTable.setRowHeight(30);
        usersInfoTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        usersInfoTable.setShowGrid(true);
        usersInfoTable.getTableHeader().setReorderingAllowed(false);
        usersInfoTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usersInfoTableMouseClicked(evt);
            }
        });
        tablePanel2.setViewportView(usersInfoTable);
        if (usersInfoTable.getColumnModel().getColumnCount() > 0) {
            usersInfoTable.getColumnModel().getColumn(0).setResizable(false);
        }

        accessLevelUsersLabel.setBackground(new java.awt.Color(101, 191, 175));
        accessLevelUsersLabel.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 14)); // NOI18N
        accessLevelUsersLabel.setForeground(new java.awt.Color(255, 255, 255));
        accessLevelUsersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        accessLevelUsersLabel.setText("Access Level:");
        accessLevelUsersLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        accessLevelUsersLabel.setOpaque(true);

        accessLevelUsersText.setBackground(new java.awt.Color(175, 237, 226));
        accessLevelUsersText.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        accessLevelUsersText.setForeground(new java.awt.Color(64, 102, 143));
        accessLevelUsersText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accessLevelUsersTextActionPerformed(evt);
            }
        });

        refreshUsers.setBackground(new java.awt.Color(77, 161, 190));
        refreshUsers.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        refreshUsers.setForeground(new java.awt.Color(255, 255, 255));
        refreshUsers.setText("REFRESH");
        refreshUsers.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        refreshUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshUsersActionPerformed(evt);
            }
        });

        deleteUsers.setBackground(new java.awt.Color(153, 0, 0));
        deleteUsers.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 14)); // NOI18N
        deleteUsers.setForeground(new java.awt.Color(255, 255, 255));
        deleteUsers.setText("DELETE");
        deleteUsers.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deleteUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUsersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout primaryDetailsPanel1Layout = new javax.swing.GroupLayout(primaryDetailsPanel1);
        primaryDetailsPanel1.setLayout(primaryDetailsPanel1Layout);
        primaryDetailsPanel1Layout.setHorizontalGroup(
            primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                .addGroup(primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userInfoUsersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                                .addComponent(userNameUsersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(usernameUsersText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                                .addComponent(passwordUsersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(passwordUsersText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                                .addComponent(accessLevelUsersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(accessLevelUsersText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(mainUsersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, primaryDetailsPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(updateUsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addUsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(backUsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                        .addComponent(tablePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(deleteUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, primaryDetailsPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(refreshUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(516, Short.MAX_VALUE))
        );
        primaryDetailsPanel1Layout.setVerticalGroup(
            primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                .addGroup(primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(mainUsersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                        .addGroup(primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                                .addGap(107, 107, 107)
                                .addComponent(userInfoUsersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, primaryDetailsPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(backUsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                                .addGroup(primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(accessLevelUsersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(accessLevelUsersText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(userNameUsersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(usernameUsersText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(passwordUsersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(passwordUsersText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(primaryDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(addUsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(updateUsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(tablePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(primaryDetailsPanel1Layout.createSequentialGroup()
                                .addComponent(refreshUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(202, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout usersPanelLayout = new javax.swing.GroupLayout(usersPanel);
        usersPanel.setLayout(usersPanelLayout);
        usersPanelLayout.setHorizontalGroup(
            usersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainHeader5, javax.swing.GroupLayout.DEFAULT_SIZE, 1920, Short.MAX_VALUE)
            .addGroup(usersPanelLayout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(primaryDetailsPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        usersPanelLayout.setVerticalGroup(
            usersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usersPanelLayout.createSequentialGroup()
                .addComponent(mainHeader5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(primaryDetailsPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(124, Short.MAX_VALUE))
        );

        mainCardPanel.add(usersPanel, "usersPanel");

        mainMenuBar.setBackground(new java.awt.Color(64, 102, 143));
        mainMenuBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mainMenuBar.setForeground(new java.awt.Color(255, 255, 255));
        mainMenuBar.setOpaque(true);
        mainMenuBar.setPreferredSize(new java.awt.Dimension(103, 26));

        menuMenuBar.setText("Menu");
        menuMenuBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuMenuBarMouseClicked(evt);
            }
        });
        mainMenuBar.add(menuMenuBar);

        actionsMenuBar.setText("Actions");

        searchActionMenuBar.setText("Search Patient Record");
        searchActionMenuBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchActionMenuBarMouseClicked(evt);
            }
        });
        searchActionMenuBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionMenuBarActionPerformed(evt);
            }
        });
        actionsMenuBar.add(searchActionMenuBar);

        createActionMenuBar.setText("Create Patient Record");
        createActionMenuBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createActionMenuBarMouseClicked(evt);
            }
        });
        createActionMenuBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createActionMenuBarActionPerformed(evt);
            }
        });
        actionsMenuBar.add(createActionMenuBar);

        mainMenuBar.add(actionsMenuBar);

        usersMenuBar.setText("Users");
        usersMenuBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usersMenuBarMouseClicked(evt);
            }
        });
        mainMenuBar.add(usersMenuBar);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1920, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mainCardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1054, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mainCardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void displayDashBoardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayDashBoardButtonActionPerformed
        updatePatientRecordTable();
        displayRecordList();
        
    }//GEN-LAST:event_displayDashBoardButtonActionPerformed

    private void usersDashBoardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersDashBoardButtonActionPerformed
        refreshUsersTable();
        displayUsers();
    }//GEN-LAST:event_usersDashBoardButtonActionPerformed

    private void createCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCreateButtonActionPerformed
            if(givenNameCreateText.getText().equals("")|ageCreateText.getText().equals("")|civilStatusCreateText.getText().equals("")|occupationCreateText.getText().equals("")|contactNumCreateText.getText().equals("")|addressCreateText.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please fill in all the information.");
        } else {
            // Check if age is a valid integer
            int age;
            try {
                age = Integer.parseInt(ageCreateText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Age must be a number");
                return;
            }

            DefaultTableModel ptnRecordsTable = (DefaultTableModel)patientRecordTable.getModel();
            DefaultTableModel ptnHistoryTable = (DefaultTableModel)patientHistoryTable.getModel();
            int nextId = ptnRecordsTable.getRowCount() + 1;
            String data[] = {String.valueOf(nextId), givenNameCreateText.getText(),middleNameCreateText.getText(), lastNameCreateText.getText(), String.valueOf(age), civilStatusCreateText.getText(), occupationCreateText.getText(), contactNumCreateText.getText(), addressCreateText.getText()};
            ptnRecordsTable.addRow(data);
            JOptionPane.showMessageDialog(this, "Record Added Successfully!");

            updatePatientRecordTable();
            displayRecordList();

            try {
                Database db = new Database();
                String query = "INSERT INTO patientrecords (id, given_name, middle_name, last_name, age, civil_status, occupation, contact_no, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (Connection conn = db.connect();
                     PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, nextId);
                    pstmt.setString(2, givenNameCreateText.getText());
                    pstmt.setString(3, middleNameCreateText.getText());
                    pstmt.setString(4, lastNameCreateText.getText());
                    pstmt.setInt(5, age);
                    pstmt.setString(6, civilStatusCreateText.getText());
                    pstmt.setString(7, occupationCreateText.getText());
                    pstmt.setString(8, contactNumCreateText.getText());
                    pstmt.setString(9, addressCreateText.getText());
                    pstmt.executeUpdate();
                }

                String queryVisit = "INSERT INTO patientvisit (patient_id, reason, price, date) VALUES (?, ?, ?, ?);";
                try (Connection conn = db.connect();
                     PreparedStatement pstmt = conn.prepareStatement(queryVisit)) {
                    pstmt.setInt(1, nextId);
                    pstmt.setString(2, reasonCreateText.getText());
                    pstmt.setString(3, priceCreateText.getText());
                    pstmt.setString(4, dateCreateText.getText());
                    pstmt.executeUpdate();
                }

                String dataVisit[] = {String.valueOf(nextId), reasonCreateText.getText(), priceCreateText.getText(), dateCreateText.getText()};
                ptnHistoryTable.addRow(dataVisit);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            givenNameCreateText.setText("");
            middleNameCreateText.setText("");
            lastNameCreateText.setText("");
            ageCreateText.setText("");
            civilStatusCreateText.setText("");
            occupationCreateText.setText("");
            contactNumCreateText.setText("");
            addressCreateText.setText("");
            reasonCreateText.setText("");  
            priceCreateText.setText(""); 
            dateCreateText.setText("");
        }
    }//GEN-LAST:event_createCreateButtonActionPerformed

    private void editPatientRecordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPatientRecordButtonActionPerformed
        
        
        int selectedRowIndex = patientRecordTable.getSelectedRow();

        if (selectedRowIndex != -1) {
        // Get the data from the selected row
        String id = patientRecordTable.getValueAt(selectedRowIndex, 0).toString();
        String givenName = patientRecordTable.getValueAt(selectedRowIndex, 1).toString();
        String middleName = patientRecordTable.getValueAt(selectedRowIndex, 2).toString();
        String lastName = patientRecordTable.getValueAt(selectedRowIndex, 3).toString();
        String age = patientRecordTable.getValueAt(selectedRowIndex, 4).toString();
        String civilStatus = patientRecordTable.getValueAt(selectedRowIndex, 5).toString();
        String occupation = patientRecordTable.getValueAt(selectedRowIndex, 6).toString();
        String contactNo = patientRecordTable.getValueAt(selectedRowIndex, 7).toString();
        String address = patientRecordTable.getValueAt(selectedRowIndex, 8).toString();

        // Set the text of the corresponding text fields
        patientIDUpdateText.setText(id);
        givenNameUpdateText.setText(givenName);
        middleNameUpdateText.setText(middleName);
        lastNameUpdateText.setText(lastName);
        ageUpdateText.setText(age);
        civilStatusUpdateText.setText(civilStatus);
        occupationUpdateText.setText(occupation);
        contactNumUpdateText.setText(contactNo);
        addressUpdateText.setText(address);
        
        displayUpdate();
        } else {
        JOptionPane.showMessageDialog(this, "Please select a row.");
        }
    }//GEN-LAST:event_editPatientRecordButtonActionPerformed

    private void deletePatientRecordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePatientRecordButtonActionPerformed
            int selectedRowIndex = patientRecordTable.getSelectedRow();

        if (selectedRowIndex != -1) {
            // Show a confirmation dialog
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record? All associated visit records will also be deleted.", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Get the ID of the selected row
                int id = Integer.parseInt(patientRecordTable.getValueAt(selectedRowIndex, 0).toString());

                // Delete the row from the database
                Connection conn = null;
                try {
                    Database db = new Database();
                    conn = db.connect();

                    String query = "DELETE FROM patientrecords WHERE id = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                        pstmt.setInt(1, id);
                        pstmt.executeUpdate();
                    }

                    String queryVisit = "DELETE FROM patientvisit WHERE patient_id = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(queryVisit)) {
                        pstmt.setInt(1, id);
                        pstmt.executeUpdate();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                // Delete the row from the JTable
                DefaultTableModel model = (DefaultTableModel) patientRecordTable.getModel();
                model.removeRow(selectedRowIndex);

                // Delete the corresponding visits from the patientHistoryTable
                DefaultTableModel historyModel = (DefaultTableModel) patientHistoryTable.getModel();
                for (int i = historyModel.getRowCount() - 1; i >= 0; i--) {
                    if (Integer.parseInt(historyModel.getValueAt(i, 0).toString()) == id) {
                        historyModel.removeRow(i);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }
        updatePatientRecordTable();
    }//GEN-LAST:event_deletePatientRecordButtonActionPerformed

    private void addPatientRecordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPatientRecordButtonActionPerformed
        displayCreate();
    }//GEN-LAST:event_addPatientRecordButtonActionPerformed

    private void readPatientRecordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readPatientRecordButtonActionPerformed
       
        
        int selectedRowIndex = patientRecordTable.getSelectedRow();

        if (selectedRowIndex != -1) {
        // Get the data from the selected row
        String id = patientRecordTable.getValueAt(selectedRowIndex, 0).toString();
        String givenName = patientRecordTable.getValueAt(selectedRowIndex, 1).toString();
        String middleName = patientRecordTable.getValueAt(selectedRowIndex, 2).toString();
        String lastName = patientRecordTable.getValueAt(selectedRowIndex, 3).toString();
        String age = patientRecordTable.getValueAt(selectedRowIndex, 4).toString();
        String civilStatus = patientRecordTable.getValueAt(selectedRowIndex, 5).toString();
        String occupation = patientRecordTable.getValueAt(selectedRowIndex, 6).toString();
        String contactNo = patientRecordTable.getValueAt(selectedRowIndex, 7).toString();
        String address = patientRecordTable.getValueAt(selectedRowIndex, 8).toString();
        

        // Set the text of the corresponding text fields
        patientIDDetailsText.setText(id);
        givenNameDetailsText.setText(givenName);
        middleNameDetailsText.setText(middleName);
        lastNameDetailsText.setText(lastName);
        ageDetailsText.setText(age);
        civilStatusDetailsText.setText(civilStatus);
        occupationDetailsText.setText(occupation);
        contactNumDetailsText.setText(contactNo);
        addressDetailsText.setText(address);
        
        refreshVisitHistory();
        
        // Compute Total Cost
        DefaultTableModel model = (DefaultTableModel) patientHistoryTable.getModel();
        int totalCost = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String priceAsString = (String) model.getValueAt(i, 2);
            int price = Integer.parseInt(priceAsString);
            totalCost += price;
        }
        
        String patientNameCon = givenName + " " + lastName;
        String totalDentalPrice = "Total Dental Cost: " + totalCost;
        String totalVisitHistory = "Total Visits: " + patientHistoryTable.getModel().getRowCount();
        totalVisits.setText(totalVisitHistory);
        totalDentalCost.setText(totalDentalPrice);
        patientName.setText(patientNameCon);
        
        displayDetails();
        
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row.");
        }
    }//GEN-LAST:event_readPatientRecordButtonActionPerformed

    private void backPatientRecordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backPatientRecordButtonActionPerformed
        displayMenu();
    }//GEN-LAST:event_backPatientRecordButtonActionPerformed

    private void backCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backCreateButtonActionPerformed
        updatePatientRecordTable();
        displayRecordList();
    }//GEN-LAST:event_backCreateButtonActionPerformed

    private void clearCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearCreateButtonActionPerformed
        givenNameCreateText.setText("");
        middleNameCreateText.setText("");
        lastNameCreateText.setText("");
        ageCreateText.setText("");
        civilStatusCreateText.setText("");
        occupationCreateText.setText("");
        contactNumCreateText.setText("");
        addressCreateText.setText("");
        reasonCreateText.setText("");
        priceCreateText.setText("");
        dateCreateText.setText("");
    }//GEN-LAST:event_clearCreateButtonActionPerformed

    private void updateUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateUpdateButtonActionPerformed
        int selectedRowIndex = patientRecordTable.getSelectedRow();

        if (selectedRowIndex != -1) {
        // Get the data from the text fields
        String id = patientIDUpdateText.getText();
        String givenName = givenNameUpdateText.getText();
        String middleName = middleNameUpdateText.getText();
        String lastName = lastNameUpdateText.getText();
        String age = ageUpdateText.getText();
        String civilStatus = civilStatusUpdateText.getText();
        String occupation = occupationUpdateText.getText();
        String contactNo = contactNumUpdateText.getText();
        String address = addressUpdateText.getText();

        // Check if there are changes
        if (!id.equals(patientRecordTable.getValueAt(selectedRowIndex, 0).toString()) ||
            !givenName.equals(patientRecordTable.getValueAt(selectedRowIndex, 1).toString()) ||
            !middleName.equals(patientRecordTable.getValueAt(selectedRowIndex, 2).toString()) ||
            !lastName.equals(patientRecordTable.getValueAt(selectedRowIndex, 3).toString()) ||
            !age.equals(patientRecordTable.getValueAt(selectedRowIndex, 4).toString()) ||
            !civilStatus.equals(patientRecordTable.getValueAt(selectedRowIndex, 5).toString()) ||
            !occupation.equals(patientRecordTable.getValueAt(selectedRowIndex, 6).toString()) ||
            !contactNo.equals(patientRecordTable.getValueAt(selectedRowIndex, 7).toString()) ||
            !address.equals(patientRecordTable.getValueAt(selectedRowIndex, 8).toString())) {

            // Show a confirmation dialog
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to update this record?", "Confirm Update", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Update the row in the JTable
                patientRecordTable.setValueAt(id, selectedRowIndex, 0);
                patientRecordTable.setValueAt(givenName, selectedRowIndex, 1);
                patientRecordTable.setValueAt(middleName, selectedRowIndex, 2);
                patientRecordTable.setValueAt(lastName, selectedRowIndex, 3);
                patientRecordTable.setValueAt(age, selectedRowIndex, 4);
                patientRecordTable.setValueAt(civilStatus, selectedRowIndex, 5);
                patientRecordTable.setValueAt(occupation, selectedRowIndex, 6);
                patientRecordTable.setValueAt(contactNo, selectedRowIndex, 7);
                patientRecordTable.setValueAt(address, selectedRowIndex, 8);

                // Update the record in the database
                try {
                    Database db = new Database();
                    String query = "UPDATE patientrecords SET given_name = ?, middle_name = ?, last_name = ?, age = ?, civil_status = ?, occupation = ?, contact_no = ?, address = ? WHERE id = ?";
                    try (Connection conn = db.connect();
                         PreparedStatement pstmt = conn.prepareStatement(query)) {
                        pstmt.setString(1, givenName);
                        pstmt.setString(2, middleName);
                        pstmt.setString(3, lastName);
                        pstmt.setInt(4, Integer.parseInt(age));
                        pstmt.setString(5, civilStatus);
                        pstmt.setString(6, occupation);
                        pstmt.setString(7, contactNo);
                        pstmt.setString(8, address);
                        pstmt.setInt(9, Integer.parseInt(id));
                        pstmt.executeUpdate();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No changes made.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a row.");
    }
    }//GEN-LAST:event_updateUpdateButtonActionPerformed

    private void backUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backUpdateButtonActionPerformed
        updatePatientRecordTable();
        displayRecordList();
    }//GEN-LAST:event_backUpdateButtonActionPerformed

    private void addVisitUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVisitUpdateButtonActionPerformed
        // Get the currently selected ID from the text box
    int currentId = Integer.parseInt(patientIDUpdateText.getText());  // Assuming patientIDUpdateText is the JTextField for the current ID

    // Confirmation dialog
    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to add this visit?", "Confirm Add Visit", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        // Insert the new visit into the patientvisit table in the database
        try {
            Database db = new Database();
            String queryVisit = "INSERT INTO patientvisit (patient_id, reason, price, date) VALUES (?, ?, ?, ?)";
            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(queryVisit)) {
                pstmt.setInt(1, currentId);
                pstmt.setString(2, reasonUpdateText.getText());  // Assuming reasonCreateText is the JTextField for the reason
                pstmt.setString(3, priceUpdateText.getText());  // Assuming priceCreateText is the JTextField for the price
                pstmt.setString(4, dateUpdateText.getText());  // Assuming dateCreateText is the JTextField for the date
                pstmt.executeUpdate();
            }

            // Add the new visit to the patientHistoryTable
            String dataVisit[] = {String.valueOf(currentId), reasonUpdateText.getText(), priceUpdateText.getText(), dateUpdateText.getText()};
            DefaultTableModel model = (DefaultTableModel) patientHistoryTable.getModel();
            model.addRow(dataVisit);

            JOptionPane.showMessageDialog(this, "Visit Added Successfully!");
            
            reasonUpdateText.setText("");  
            priceUpdateText.setText(""); 
            dateUpdateText.setText("");
            refreshVisitHistory();
            displayDetails();
            
           
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    }//GEN-LAST:event_addVisitUpdateButtonActionPerformed

    private void updateDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateDetailsButtonActionPerformed
        int selectedRowIndex = patientRecordTable.getSelectedRow();

        if (selectedRowIndex != -1) {
        // Get the data from the selected row
        String id = patientRecordTable.getValueAt(selectedRowIndex, 0).toString();
        String givenName = patientRecordTable.getValueAt(selectedRowIndex, 1).toString();
        String middleName = patientRecordTable.getValueAt(selectedRowIndex, 2).toString();
        String lastName = patientRecordTable.getValueAt(selectedRowIndex, 3).toString();
        String age = patientRecordTable.getValueAt(selectedRowIndex, 4).toString();
        String civilStatus = patientRecordTable.getValueAt(selectedRowIndex, 5).toString();
        String occupation = patientRecordTable.getValueAt(selectedRowIndex, 6).toString();
        String contactNo = patientRecordTable.getValueAt(selectedRowIndex, 7).toString();
        String address = patientRecordTable.getValueAt(selectedRowIndex, 8).toString();

        // Set the text of the corresponding text fields
        patientIDUpdateText.setText(id);
        givenNameUpdateText.setText(givenName);
        middleNameUpdateText.setText(middleName);
        lastNameUpdateText.setText(lastName);
        ageUpdateText.setText(age);
        civilStatusUpdateText.setText(civilStatus);
        occupationUpdateText.setText(occupation);
        contactNumUpdateText.setText(contactNo);
        addressUpdateText.setText(address);
        
        displayUpdate();
        } else {
        JOptionPane.showMessageDialog(this, "Please select a row.");
        }
    }//GEN-LAST:event_updateDetailsButtonActionPerformed

    private void backDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backDetailsButtonActionPerformed
        updatePatientRecordTable();
        displayRecordList();
    }//GEN-LAST:event_backDetailsButtonActionPerformed

    private void addVisitDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVisitDetailsButtonActionPerformed
        int selectedRowIndex = patientRecordTable.getSelectedRow();

        if (selectedRowIndex != -1) {
        // Get the data from the selected row
        String id = patientRecordTable.getValueAt(selectedRowIndex, 0).toString();
        String givenName = patientRecordTable.getValueAt(selectedRowIndex, 1).toString();
        String middleName = patientRecordTable.getValueAt(selectedRowIndex, 2).toString();
        String lastName = patientRecordTable.getValueAt(selectedRowIndex, 3).toString();
        String age = patientRecordTable.getValueAt(selectedRowIndex, 4).toString();
        String civilStatus = patientRecordTable.getValueAt(selectedRowIndex, 5).toString();
        String occupation = patientRecordTable.getValueAt(selectedRowIndex, 6).toString();
        String contactNo = patientRecordTable.getValueAt(selectedRowIndex, 7).toString();
        String address = patientRecordTable.getValueAt(selectedRowIndex, 8).toString();

        // Set the text of the corresponding text fields
        patientIDUpdateText.setText(id);
        givenNameUpdateText.setText(givenName);
        middleNameUpdateText.setText(middleName);
        lastNameUpdateText.setText(lastName);
        ageUpdateText.setText(age);
        civilStatusUpdateText.setText(civilStatus);
        occupationUpdateText.setText(occupation);
        contactNumUpdateText.setText(contactNo);
        addressUpdateText.setText(address);
        
        displayUpdate();
        } else {
        JOptionPane.showMessageDialog(this, "Please select a row.");
        }
    }//GEN-LAST:event_addVisitDetailsButtonActionPerformed

    private void lastNameCreateTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameCreateTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameCreateTextActionPerformed

    private void middleNameCreateTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_middleNameCreateTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_middleNameCreateTextActionPerformed

    private void refreshPatientTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshPatientTableActionPerformed
    updatePatientRecordTable();
    }//GEN-LAST:event_refreshPatientTableActionPerformed

    private void patientIDDetailsTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientIDDetailsTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_patientIDDetailsTextActionPerformed

    private void addressUpdateTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressUpdateTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addressUpdateTextActionPerformed

    private void patientRecordTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_patientRecordTableMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_patientRecordTableMousePressed

    private void middleNameDetailsTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_middleNameDetailsTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_middleNameDetailsTextActionPerformed

    private void searchPatientTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchPatientTextActionPerformed
        // Add a document listener to the search text field
    searchPatientText.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            filter();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filter();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // Plain text components do not fire these events
        }

        private void filter() {
            String filter = searchPatientText.getText();
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>((DefaultTableModel) patientRecordTable.getModel());
            patientRecordTable.setRowSorter(trs);

            if (filter.trim().length() == 0) {
                trs.setRowFilter(null);
            } else {
                trs.setRowFilter(RowFilter.regexFilter("(?i)" + filter));
            }
        updatePatientRecordTable();
        }
    });
    }//GEN-LAST:event_searchPatientTextActionPerformed

    private void refreshVisitHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshVisitHistoryActionPerformed
        refreshVisitHistory();
    }//GEN-LAST:event_refreshVisitHistoryActionPerformed

    private void printTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printTableActionPerformed
        String firstName = givenNameDetailsText.getText(); // replace with your method to get the first name
        String lastName = lastNameDetailsText.getText(); // replace with your method to get the last name
        MessageFormat header = new MessageFormat(firstName + " " + lastName + " - Dental Records");
        MessageFormat footer = new MessageFormat("Page {0,number,integer}");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.PORTRAIT);
            patientHistoryTable.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, set, true); // changed to patientHistoryTable
            JOptionPane.showMessageDialog(null, "\n" + "Printed Successfully!");
        } catch (java.awt.print.PrinterException e) {
            JOptionPane.showMessageDialog(null, "\n" + "Failed"
                + "\n" + e);
        }
    }//GEN-LAST:event_printTableActionPerformed

    private void printPatientTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printPatientTableActionPerformed
        MessageFormat header = new MessageFormat("Patient Records");
        MessageFormat footer = new MessageFormat("Page {0,number,integer}");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            patientRecordTable.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, set, true);
            JOptionPane.showMessageDialog(null, "\n" + "Printed Successfully!");
        } catch (java.awt.print.PrinterException e) {
            JOptionPane.showMessageDialog(null, "\n" + "Failed"
                + "\n" + e);
        }
    }//GEN-LAST:event_printPatientTableActionPerformed

    private void deleteVisitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteVisitActionPerformed
        int selectedRow = patientHistoryTable.getSelectedRow();
        if (selectedRow != -1) {
            // Show confirmation dialog
            int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to delete the selected visit?", "Warning", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                // User confirmed deletion, get the ID of the selected row
                DefaultTableModel model = (DefaultTableModel) patientHistoryTable.getModel();
                int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

                // Delete from database
                try {
                    Database db = new Database();
                    String query = "DELETE FROM patientvisit WHERE visit_id = ?";
                    try (Connection conn = db.connect();
                         PreparedStatement pstmt = conn.prepareStatement(query)) {
                        pstmt.setInt(1, id);
                        pstmt.executeUpdate();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // Refresh the JTable
                refreshVisitHistory();
                JOptionPane.showMessageDialog(null, "Visit deleted successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to delete.");
        }
    }//GEN-LAST:event_deleteVisitActionPerformed

    private void updateUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateUsersButtonActionPerformed
    int selectedRowIndex = usersInfoTable.getSelectedRow();

        if (selectedRowIndex != -1) {
        // Get the data from the text fields
        
        String accesslevel = accessLevelUsersText.getText();
        String username = usernameUsersText.getText();
        String password = passwordUsersText.getText();
        String usersID = usersInfoTable.getValueAt(selectedRowIndex, 0).toString();
        

        // Check if there are changes
        if (!accesslevel.equals(usersInfoTable.getValueAt(selectedRowIndex, 1).toString()) ||
            !username.equals(usersInfoTable.getValueAt(selectedRowIndex, 2).toString()) ||
            !password.equals(usersInfoTable.getValueAt(selectedRowIndex, 3).toString())
            ){

            // Show a confirmation dialog
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to update this user?", "Confirm Update", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Update the row in the JTable
                usersInfoTable.setValueAt(accesslevel, selectedRowIndex, 1);
                usersInfoTable.setValueAt(username, selectedRowIndex, 2);
                usersInfoTable.setValueAt(password, selectedRowIndex, 3);
               

                // Update the record in the database
                try {
                    Database db = new Database();
                    String query = "UPDATE usersdata SET accessLevel = ?, username = ?, password = ? WHERE usersID = ?";
                    try (Connection conn = db.connect();
                         PreparedStatement pstmt = conn.prepareStatement(query)) {
                        pstmt.setInt(4, Integer.parseInt(usersID));
                        pstmt.setString(1, accesslevel);
                        pstmt.setString(2, username);
                        pstmt.setString(3, password);
                        pstmt.executeUpdate();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No changes made.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a row.");
    }
    }//GEN-LAST:event_updateUsersButtonActionPerformed

    private void backUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backUsersButtonActionPerformed
        displayMenu();
    }//GEN-LAST:event_backUsersButtonActionPerformed

    private void addUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUsersButtonActionPerformed
            // Get the currently selected ID from the text box
    //int currentId = Integer.parseInt(patientIDUpdateText.getText());  // Assuming patientIDUpdateText is the JTextField for the current ID

    // Confirmation dialog
    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to add this User?", "Confirm Add Visit", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        // Insert the new visit into the patientvisit table in the database
        try {
            Database db = new Database();
            String queryVisit = "INSERT INTO usersdata (accessLevel, username, password) VALUES (?, ?, ?)";
            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(queryVisit)) {
                pstmt.setString(1, accessLevelUsersText.getText());
                pstmt.setString(2, usernameUsersText.getText());  
                pstmt.setString(3, passwordUsersText.getText()); 
                pstmt.executeUpdate();
            }

            

            JOptionPane.showMessageDialog(this, "Visit Added Successfully!");
            
            accessLevelUsersText.setText("");  
            usernameUsersText.setText(""); 
            passwordUsersText.setText("");
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    refreshUsersTable();
    }//GEN-LAST:event_addUsersButtonActionPerformed

    private void accessLevelUsersTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accessLevelUsersTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accessLevelUsersTextActionPerformed

    private void refreshUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshUsersActionPerformed
        refreshUsersTable();
    }//GEN-LAST:event_refreshUsersActionPerformed

    private void deleteUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUsersActionPerformed
    int selectedRow = usersInfoTable.getSelectedRow();
        if (selectedRow != -1) {
            // Show confirmation dialog
            int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to delete the selected user?", "Warning", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                // User confirmed deletion, get the ID of the selected row
                DefaultTableModel model = (DefaultTableModel) usersInfoTable.getModel();
                int usersID = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

                // Delete from database
                try {
                    Database db = new Database();
                    String query = "DELETE FROM usersdata WHERE usersID = ?";
                    try (Connection conn = db.connect();
                         PreparedStatement pstmt = conn.prepareStatement(query)) {
                        pstmt.setInt(1, usersID);
                        pstmt.executeUpdate();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // Refresh the JTable
                refreshUsersTable();
                JOptionPane.showMessageDialog(null, "Visit deleted successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to delete.");
        }
        
    }//GEN-LAST:event_deleteUsersActionPerformed

    private void usersInfoTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usersInfoTableMouseClicked
    int selectedRowIndex = usersInfoTable.getSelectedRow();

        if (selectedRowIndex != -1) {
        // Get the data from the selected row
        
        String accesslevel = usersInfoTable.getValueAt(selectedRowIndex, 1).toString();
        String username = usersInfoTable.getValueAt(selectedRowIndex, 2).toString();
        String password = usersInfoTable.getValueAt(selectedRowIndex, 3).toString();
       
        // Set the text of the corresponding text fields
        
        accessLevelUsersText.setText(accesslevel);
        usernameUsersText.setText(username);
        passwordUsersText.setText(password);
        
        
        } else {
        JOptionPane.showMessageDialog(this, "Please select a row.");
        }
    }//GEN-LAST:event_usersInfoTableMouseClicked

    private void menuMenuBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMenuBarMouseClicked
        displayMenu();
    }//GEN-LAST:event_menuMenuBarMouseClicked

    private void searchActionMenuBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchActionMenuBarMouseClicked
        
    }//GEN-LAST:event_searchActionMenuBarMouseClicked

    private void createActionMenuBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createActionMenuBarMouseClicked
        
    }//GEN-LAST:event_createActionMenuBarMouseClicked

    private void usersMenuBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usersMenuBarMouseClicked
        displayUsers();
    }//GEN-LAST:event_usersMenuBarMouseClicked

    private void searchActionMenuBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionMenuBarActionPerformed
        displayRecordList();
    }//GEN-LAST:event_searchActionMenuBarActionPerformed

    private void createActionMenuBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createActionMenuBarActionPerformed
        displayCreate();
    }//GEN-LAST:event_createActionMenuBarActionPerformed

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        loginPopUp loginbutton = new loginPopUp();
        loginbutton.displayForm();
        
        this.dispose();
    }//GEN-LAST:event_logOutButtonActionPerformed

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
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DateCreateLabel;
    private javax.swing.JLabel DateUpdateLabel;
    private javax.swing.JLabel accessLevelUsersLabel;
    private javax.swing.JTextField accessLevelUsersText;
    private javax.swing.JMenu actionsMenuBar;
    private javax.swing.JButton addPatientRecordButton;
    private javax.swing.JButton addUsersButton;
    private javax.swing.JButton addVisitDetailsButton;
    private javax.swing.JButton addVisitUpdateButton;
    private javax.swing.JLabel addressCreateLabel;
    private javax.swing.JTextField addressCreateText;
    private javax.swing.JLabel addressDetailsLabel;
    private javax.swing.JTextField addressDetailsText;
    private javax.swing.JLabel addressUpdateLabel;
    private javax.swing.JTextField addressUpdateText;
    private javax.swing.JLabel ageCreateLabel;
    private javax.swing.JTextField ageCreateText;
    private javax.swing.JLabel ageDetailsLabel;
    private javax.swing.JTextField ageDetailsText;
    private javax.swing.JLabel ageUpdateLabel;
    private javax.swing.JTextField ageUpdateText;
    private javax.swing.JButton backCreateButton;
    private javax.swing.JButton backDetailsButton;
    private javax.swing.JButton backPatientRecordButton;
    private javax.swing.JButton backUpdateButton;
    private javax.swing.JButton backUsersButton;
    private javax.swing.JLabel civilStatusCreateLabel;
    private javax.swing.JTextField civilStatusCreateText;
    private javax.swing.JLabel civilStatusDetailsLabel;
    private javax.swing.JTextField civilStatusDetailsText;
    private javax.swing.JLabel civilStatusUpdateLabel;
    private javax.swing.JTextField civilStatusUpdateText;
    private javax.swing.JButton clearCreateButton;
    private javax.swing.JLabel contactNumCreateLabel;
    private javax.swing.JTextField contactNumCreateText;
    private javax.swing.JLabel contactNumDetailsLabel;
    private javax.swing.JTextField contactNumDetailsText;
    private javax.swing.JLabel contactNumUpdateLabel;
    private javax.swing.JTextField contactNumUpdateText;
    private javax.swing.JMenuItem createActionMenuBar;
    private javax.swing.JButton createCreateButton;
    private javax.swing.JPanel createPanel;
    private javax.swing.JTextField dateCreateText;
    private javax.swing.JTextField dateUpdateText;
    private javax.swing.JButton deletePatientRecordButton;
    private javax.swing.JButton deleteUsers;
    private javax.swing.JButton deleteVisit;
    private javax.swing.JPanel detailedPanel;
    private javax.swing.JButton displayDashBoardButton;
    private javax.swing.JButton editPatientRecordButton;
    private javax.swing.JLabel givenNameCreateLabel;
    private javax.swing.JTextField givenNameCreateText;
    private javax.swing.JLabel givenNameDetailsLabel;
    private javax.swing.JLabel givenNameDetailsLabel1;
    private javax.swing.JTextField givenNameDetailsText;
    private javax.swing.JLabel givenNameUpdateLabel;
    private javax.swing.JTextField givenNameUpdateText;
    private javax.swing.JLabel lastNameCreateLabel;
    private javax.swing.JTextField lastNameCreateText;
    private javax.swing.JLabel lastNameDetailsLabel;
    private javax.swing.JTextField lastNameDetailsText;
    private javax.swing.JLabel lastNameUpdateLabel;
    private javax.swing.JTextField lastNameUpdateText;
    private javax.swing.JButton logOutButton;
    private javax.swing.JPanel mainCardPanel;
    private javax.swing.JLabel mainCreateLabel;
    private javax.swing.JLabel mainDetailsLabel;
    private javax.swing.JLabel mainHeader;
    private javax.swing.JLabel mainHeader1;
    private javax.swing.JLabel mainHeader2;
    private javax.swing.JLabel mainHeader3;
    private javax.swing.JLabel mainHeader4;
    private javax.swing.JLabel mainHeader5;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JPanel mainMenuPanel;
    private javax.swing.JLabel mainUpdateLabel;
    private javax.swing.JLabel mainUsersLabel;
    private javax.swing.JMenu menuMenuBar;
    private javax.swing.JLabel middleNameCreateLabel;
    private javax.swing.JTextField middleNameCreateText;
    private javax.swing.JLabel middleNameDetailsLabel;
    private javax.swing.JTextField middleNameDetailsText;
    private javax.swing.JLabel middleNameUpdateLabel;
    private javax.swing.JTextField middleNameUpdateText;
    private javax.swing.JLabel occupationCreateLabel;
    private javax.swing.JTextField occupationCreateText;
    private javax.swing.JLabel occupationDetailsLabel;
    private javax.swing.JTextField occupationDetailsText;
    private javax.swing.JLabel occupationUpdateLabel;
    private javax.swing.JTextField occupationUpdateText;
    private javax.swing.JLabel passwordUsersLabel;
    private javax.swing.JTextField passwordUsersText;
    private javax.swing.JTable patientHistoryTable;
    private javax.swing.JTextField patientIDDetailsText;
    private javax.swing.JLabel patientIDUpdateLabel;
    private javax.swing.JTextField patientIDUpdateText;
    private javax.swing.JLabel patientInfoCreateLabel;
    private javax.swing.JLabel patientInfoUpdateLabel;
    private javax.swing.JLabel patientName;
    private javax.swing.JTable patientRecordTable;
    private javax.swing.JLabel patientRecordsLabel1;
    private javax.swing.JLabel patientVisitsCreateLabel;
    private javax.swing.JLabel patientVisitsUpdateLabel;
    private javax.swing.JLabel priceCreateLabel;
    private javax.swing.JTextField priceCreateText;
    private javax.swing.JLabel priceUpdateLabel;
    private javax.swing.JTextField priceUpdateText;
    private javax.swing.JPanel primaryCreatePanel;
    private javax.swing.JPanel primaryDetailsPanel;
    private javax.swing.JPanel primaryDetailsPanel1;
    private javax.swing.JPanel primaryUpdatePanel;
    private javax.swing.JButton printPatientTable;
    private javax.swing.JButton printTable;
    private javax.swing.JButton readPatientRecordButton;
    private javax.swing.JLabel reasonCreateLabel;
    private javax.swing.JTextField reasonCreateText;
    private javax.swing.JLabel reasonUpdateLabel;
    private javax.swing.JTextField reasonUpdateText;
    private javax.swing.JPanel recordListPanel;
    private javax.swing.JPanel recordListPrimaryPanel;
    private javax.swing.JButton refreshPatientTable;
    private javax.swing.JButton refreshUsers;
    private javax.swing.JButton refreshVisitHistory;
    private javax.swing.JMenuItem searchActionMenuBar;
    private javax.swing.JLabel searchPatientLabel;
    private javax.swing.JTextField searchPatientText;
    private javax.swing.JLabel summaryVisits;
    private javax.swing.JScrollPane tablePanel;
    private javax.swing.JScrollPane tablePanel1;
    private javax.swing.JScrollPane tablePanel2;
    private javax.swing.JLabel totalDentalCost;
    private javax.swing.JLabel totalPatients;
    private javax.swing.JLabel totalVisits;
    private javax.swing.JButton updateDetailsButton;
    private javax.swing.JPanel updatePanel;
    private javax.swing.JButton updateUpdateButton;
    private javax.swing.JButton updateUsersButton;
    private javax.swing.JLabel userInfoUsersLabel;
    private javax.swing.JLabel userNameUsersLabel;
    private javax.swing.JTextField usernameUsersText;
    private javax.swing.JButton usersDashBoardButton;
    private javax.swing.JTable usersInfoTable;
    private javax.swing.JMenu usersMenuBar;
    private javax.swing.JPanel usersPanel;
    // End of variables declaration//GEN-END:variables

    
    public void displayForm() {
        // Set the visibility of the form to true
        setVisible(true);
        
    }
    
    public void displayMenu(){
        CardLayout cl = (CardLayout)(mainCardPanel.getLayout());
            cl.show(mainCardPanel, "mainMenu");
    }
    public void displayRecordList(){
        CardLayout cl = (CardLayout)(mainCardPanel.getLayout());
            cl.show(mainCardPanel, "recordList");
    }
    public void displayCreate(){
        CardLayout cl = (CardLayout)(mainCardPanel.getLayout());
            cl.show(mainCardPanel, "createPanel");
    }
    public void displayUpdate(){
        CardLayout cl = (CardLayout)(mainCardPanel.getLayout());
            cl.show(mainCardPanel, "updatePanel");
    }
    public void displayDetails(){
        CardLayout cl = (CardLayout)(mainCardPanel.getLayout());
            cl.show(mainCardPanel, "detailedPanel");
    }
    public void displayUsers(){
        CardLayout cl = (CardLayout)(mainCardPanel.getLayout());
            cl.show(mainCardPanel, "usersPanel");
    }
}

