package com.example.lab1chahat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    public TextField ipaintentName;
    public TextField iphone;
    public TextField ipayment;

    @FXML
    private TableView<dental_appoint> tableView;
    @FXML
    private TableColumn<dental_appoint, Integer> id;
    @FXML
    private TableColumn<dental_appoint, String> paintentName;
    @FXML
    private TableColumn<dental_appoint, Integer> phone;
    @FXML
    private TableColumn<dental_appoint, Integer> payment;

    ObservableList<dental_appoint> list = FXCollections.observableArrayList();

    private final String jdbcUrl = "jdbc:mysql://localhost:3306/lab1chahat";
    private final String dbUser = "root";
    private final String dbPassword = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        paintentName.setCellValueFactory(new PropertyValueFactory<>("paintentName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        tableView.setItems(list);
    }

    @FXML
    protected void onHelloButtonClick() {
        populateTable();
    }

    public void populateTable() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM dental_appoint";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            list.clear(); // Clear the current table before populating
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String paintentName = resultSet.getString("paintentName");
                int phone = resultSet.getInt("phone");
                int payment = resultSet.getInt("payment");
                tableView.getItems().add(new dental_appoint(id, paintentName, phone, payment));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert Data into Database
    public void insertdata(ActionEvent actionEvent) {
        String name = ipaintentName.getText();
        String phone = iphone.getText();
        String payment = ipayment.getText();

        if (name.isEmpty() || phone.isEmpty() || payment.isEmpty()) {
            System.out.println("Please fill all the fields.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO dental_appoint (paintentName, phone, payment) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, Integer.parseInt(phone));
            preparedStatement.setInt(3, Integer.parseInt(payment));

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new record was inserted successfully!");
                tableView.getItems().add(new dental_appoint(0, name, Integer.parseInt(phone), Integer.parseInt(payment)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Data in the Database
    public void updatedata(ActionEvent actionEvent) {
        // Get selected item from the table
        dental_appoint selectedRecord = tableView.getSelectionModel().getSelectedItem();

        if (selectedRecord == null) {
            System.out.println("Please select a record to update.");
            return;
        }

        String name = ipaintentName.getText();
        String phone = iphone.getText();
        String payment = ipayment.getText();

        if (name.isEmpty() || phone.isEmpty() || payment.isEmpty()) {
            System.out.println("Please fill all the fields.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE dental_appoint SET paintentName = ?, phone = ?, payment = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, Integer.parseInt(phone));
            preparedStatement.setInt(3, Integer.parseInt(payment));
            preparedStatement.setInt(4, selectedRecord.getId()); // Use the id of the selected record

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Record updated successfully!");

                // Update the table view with new data
                selectedRecord.setPaintentName(name);
                selectedRecord.setPhone(Integer.parseInt(phone));
                selectedRecord.setPayment(Integer.parseInt(payment));
                tableView.refresh();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Data from Database
    public void deletedata(ActionEvent actionEvent) {
        // Get selected item from the table
        dental_appoint selectedRecord = tableView.getSelectionModel().getSelectedItem();

        if (selectedRecord == null) {
            System.out.println("Please select a record to delete.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM dental_appoint WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, selectedRecord.getId());

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Record deleted successfully!");

                // Remove the deleted record from the table view
                tableView.getItems().remove(selectedRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loaddata(ActionEvent actionEvent) {
    }
}
