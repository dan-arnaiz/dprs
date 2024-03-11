private void addVisitUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                     
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
            pstmt.setString(2, reasonCreateText.getText());  // Assuming reasonCreateText is the JTextField for the reason
            pstmt.setString(3, priceCreateText.getText());  // Assuming priceCreateText is the JTextField for the price
            pstmt.setString(4, dateCreateText.getText());  // Assuming dateCreateText is the JTextField for the date
            pstmt.executeUpdate();
        }

        // Add the new visit to the patientHistoryTable
        String dataVisit[] = {String.valueOf(currentId), reasonCreateText.getText(), priceCreateText.getText(), dateCreateText.getText()};
        DefaultTableModel model = (DefaultTableModel) patientHistoryTable.getModel();
        model.addRow(dataVisit);

        JOptionPane.showMessageDialog(this, "Visit Added Successfully!");

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
} 