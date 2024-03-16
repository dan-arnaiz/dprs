/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.dentalpatientrecordsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ryzen
 */
public class Database {
    private String url = "jdbc:mysql://localhost:3306/dprs?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private String user = "root";
    private String password = "";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void create(String givenName, String lastName, String middleName, int age, String civilStatus, String occupation, String contactNo, String address) throws SQLException {
        String query = "INSERT INTO patientrecords (given_name, last_name, middle_name, age, civil_status, occupation, contact_no, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, givenName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, middleName);
            pstmt.setInt(4, age);
            pstmt.setString(5, civilStatus);
            pstmt.setString(6, occupation);
            pstmt.setString(7, contactNo);
            pstmt.setString(8, address);
            pstmt.executeUpdate();
        }
    }

    public List<Object[]> read() throws SQLException {
    String query = "SELECT id, given_name, last_name, middle_name, age, civil_status, occupation, contact_no, address FROM patientrecords";
    List<Object[]> results = new ArrayList<>();
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            Object[] row = new Object[9];
            row[0] = rs.getInt("id");
            row[1] = rs.getString("given_name");
            row[2] = rs.getString("last_name");
            row[3] = rs.getString("middle_name");
            row[4] = rs.getInt("age");
            row[5] = rs.getString("civil_status");
            row[6] = rs.getString("occupation");
            row[7] = rs.getString("contact_no");
            row[8] = rs.getString("address");
            results.add(row);
        }
    }
    return results;
}

    public void update(int id, String givenName, String lastName, String middleName, int age, String civilStatus, String occupation, String contactNo, String address) throws SQLException {
        String query = "UPDATE patientrecords SET given_name = ?, last_name = ?, middle_name = ?, age = ?, civil_status = ?, occupation = ?, contact_no = ?, address = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, givenName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, middleName);
            pstmt.setInt(4, age);
            pstmt.setString(5, civilStatus);
            pstmt.setString(6, occupation);
            pstmt.setString(7, contactNo);
            pstmt.setString(8, address);
            pstmt.setInt(9, id);
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM patientrecords WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
    public ResultSet patientVisitHistory(int patientId) {
        ResultSet rs = null;
        String query = "SELECT id, reason, price, date FROM visits WHERE patient_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            rs = pstmt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }
    
    public void createUser(String accesslevel, String username, String password) throws SQLException {
        String query = "INSERT INTO usersdata (accessLevel, username, password) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, accesslevel);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
        }
    }
    public List<Object[]> readUsers() throws SQLException {
    String query = "SELECT usersID, accessLevel, username, password FROM usersdata";
    List<Object[]> usersResults = new ArrayList<>();
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            Object[] row = new Object[9];
            row[0] = rs.getInt("usersID");
            row[1] = rs.getString("accessLevel");
            row[2] = rs.getString("username");
            row[3] = rs.getString("password");
            usersResults.add(row);
        }
    }
    return usersResults;
    }
    
    public void updateUsers(int usersID, String accesslevel, String username, String password) throws SQLException {
        String query = "UPDATE usersdata SET accessLevel = ?, username = ?, password = ? WHERE usersID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, accesslevel);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
        }
    }
    public void deleteUsers(int usersID) throws SQLException {
        String query = "DELETE FROM usersdata WHERE usersID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, usersID);
            pstmt.executeUpdate();
        }
    }
}