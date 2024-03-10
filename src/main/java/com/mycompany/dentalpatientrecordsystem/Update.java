private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
    int selectedRowIndex = patientRecordTable.getSelectedRow();

    if (selectedRowIndex != -1) {
        // Get the data from the text fields
        String id = idDetailsText.getText();
        String givenName = givenNameDetailsText.getText();
        String middleName = middleNameDetailsText.getText();
        String lastName = lastNameDetailsText.getText();
        String age = ageDetailsText.getText();
        String civilStatus = civilStatusDetailsText.getText();
        String occupation = occupationDetailsText.getText();
        String contactNo = contactNumDetailsText.getText();
        String address = addressDetailsText.getText();

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
                        pstmt.setInt(7, Integer.parseInt(contactNo));
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
}