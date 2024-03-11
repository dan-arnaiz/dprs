private void createCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
    if(givenNameCreateText.getText().equals("")|ageCreateText.getText().equals("")|civilStatusCreateText.getText().equals("")|occupationCreateText.getText().equals("")|contactNumCreateText.getText().equals("")|addressCreateText.getText().equals("")){
    JOptionPane.showMessageDialog(this, "Please fill in all the information.");
    } else {
        DefaultTableModel ptnRecordsTable = (DefaultTableModel)patientRecordTable.getModel();
        DefaultTableModel ptnHistoryTable = (DefaultTableModel)patientHistoryTable.getModel();  // Assuming patientHistoryTable is the JTable for the patientvisit table
        int nextId = ptnRecordsTable.getRowCount() + 1; // Get the next ID
        String data[] = {String.valueOf(nextId), givenNameCreateText.getText(),middleNameCreateText.getText(), lastNameCreateText.getText(), ageCreateText.getText(), civilStatusCreateText.getText(), occupationCreateText.getText(), contactNumCreateText.getText(), addressCreateText.getText()};
        ptnRecordsTable.addRow(data);
        JOptionPane.showMessageDialog(this, "Record Added Successfully!");

        // Insert the new record into the patientrecords table in the database
        try {
            Database db = new Database();
            String query = "INSERT INTO patientrecords (id, given_name, middle_name, last_name, age, civil_status, occupation, contact_no, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, nextId);
                pstmt.setString(2, givenNameCreateText.getText());
                pstmt.setString(3, middleNameCreateText.getText());
                pstmt.setString(4, lastNameCreateText.getText());
                pstmt.setInt(5, Integer.parseInt(ageCreateText.getText()));
                pstmt.setString(6, civilStatusCreateText.getText());
                pstmt.setString(7, occupationCreateText.getText());
                pstmt.setInt(8, Integer.parseInt(contactNumCreateText.getText()));
                pstmt.setString(9, addressCreateText.getText());
                pstmt.executeUpdate();
            }

            // Insert the new visit into the patientvisit table in the database
            String queryVisit = "INSERT INTO patientvisit (patient_id, reason, price, date) VALUES (?, ?, ?, ?);";
            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(queryVisit)) {
                pstmt.setInt(1, nextId);
                pstmt.setString(2, reasonCreateText.getText());  // Assuming reasonCreateText is the JTextField for the reason
                pstmt.setString(3, priceCreateText.getText());  // Assuming priceCreateText is the JTextField for the price
                pstmt.setString(4, dateCreateText.getText());  // Assuming dateCreateText is the JTextField for the date
                pstmt.executeUpdate();
            }

            // Add the new visit to the patientHistoryTable
            String dataVisit[] = {String.valueOf(nextId), reasonCreateText.getText(), priceCreateText.getText(), dateCreateText.getText()};
            ptnHistoryTable.addRow(dataVisit);

            displayRecordList();
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

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}  