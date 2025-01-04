package dat.nx.sanbongdaquoc.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.services.*;

public class UserPageController {

    @FXML
    private TextField txtUserID, txtFullName, txtEmail, txtPhoneNumber, txtCreatedAt, searchField;

    @FXML
    private TableView<UserDTO> userTable;
    @FXML
    private TableColumn<UserDTO, Integer> sttColumn;
    @FXML
    private TableColumn<UserDTO, String> nameColumn, emailColumn, phoneColumn;
    @FXML
    private TableColumn<UserDTO, Timestamp> createdAtColumn;

    private ObservableList<UserDTO> userList = FXCollections.observableArrayList();
    private UserBLL userBLL = new UserBLL();

    @FXML
    private void initialize() {
        setupTableColumns();
        userTable.setItems(userList);
        setupUserSearchByName();
    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        
        sttColumn.setCellValueFactory(cellData -> {
            int index = userTable.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleIntegerProperty(index).asObject();
        });
        loadUserData();
        userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtUserID.setText(newValue.getUserID());
                txtFullName.setText(newValue.getFullName());
                txtEmail.setText(newValue.getEmail());
                txtPhoneNumber.setText(newValue.getPhoneNumber());
                if (newValue.getCreatedAt() != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-HH-dd HH:mm:ss");
                    txtCreatedAt.setText(newValue.getCreatedAt().toLocalDateTime().format(formatter));
                } else {
                    txtCreatedAt.setText("");
                }
            }
        });
    }

    private void setupUserSearchByName() {
        FilteredList<UserDTO> filterUserList =
            new FilteredList<>(userList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterUserList.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return user.getFullName().toLowerCase().contains(lowerCaseFilter);
            });
        });
        userTable.setItems(filterUserList);
    }

    private void loadUserData() {
        List<UserDTO> userDTOs = userBLL.getAllUsers();
        userList.setAll(userDTOs);
    }

    @FXML
    private void handleAddUser() {
        String userID = txtUserID.getText();
        String fullName = txtFullName.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtPhoneNumber.getText();

        if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin", Alert.AlertType.ERROR);
            return;
        }

        UserDTO newUser = new UserDTO();
        newUser.setUserID(userID);
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        if (userBLL.addUser(newUser)) {
            userList.add(newUser);
            userTable.refresh();
            clearInputsFields();
            showAlert("Thành công", "Thêm người dùng thành công", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Thất bại", "Không thể thêm người dùng", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDeleteUser() {
        int selectedIndex = userTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            showAlert("Cảnh báo", "Vui lòng chọn người dùng để xóa", Alert.AlertType.WARNING);
            return;
        }

        UserDTO selectedUser = userList.get(selectedIndex);

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn muốn xóa người dùng này không?",
                ButtonType.YES, ButtonType.NO);
        confirmAlert.setTitle("Xác nhận xóa người dùng");
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                boolean result = userBLL.deleteUser(selectedUser);
                if (result) {
                    userList.remove(selectedIndex);
                    userTable.refresh();
                    showAlert("Thành công", "Xóa người dùng thành công", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Lỗi", "Không thể xóa người dùng", Alert.AlertType.ERROR);
                }
            }
        });
    }
    
  //Phương thức cập nhật thông tin người dùng
  		@FXML
  		private void updateUser() {
  			//Lấy người dùng được chọn từ table view
  			UserDTO selectedUser = userTable.getSelectionModel().getSelectedItem();
  			
  			//Kiểm tra xem đã lấy người dùng để chuẩn bị cập nhật
  			if(selectedUser == null) {
  				showAlert("Cảnh báo", "Vui lòng chọn người dùng để xóa", AlertType.WARNING);
  			}
  			
  			//Hiển thị hộp thoại xác nhận
  			Alert confirmAlert = new Alert(AlertType.WARNING,"Bạn có muốn cập nhật thông tin người dùng "
  					+ "này hay không",ButtonType.YES,ButtonType.NO);
  			confirmAlert.setTitle("Xác nhận cập nhật");
  			confirmAlert.showAndWait().ifPresent(response -> {
  				if(response == ButtonType.YES) {
  					
  					//Nhận dữ liệu mới từ các TextField
  					String newFullName = txtFullName.getText();
  					String newEmail = txtEmail.getText();
  					String newPhoneNumber = txtPhoneNumber.getText();
  					
  					//Gán dữ liệu mới vào user
  					selectedUser.setFullName(newFullName);
  					selectedUser.setEmail(newEmail);
  					selectedUser.setPhoneNumber(newPhoneNumber);
  					
  					//Gọi BLL để xử lý
  					boolean result = userBLL.updateUser(selectedUser);
  					
  					if(result) {
  						//Cập nhật danh sách để hiển thị
  						userTable.refresh();
  						showAlert("Thông báo","Cập nhật người dùng thành công",AlertType.INFORMATION);
  					} else {
  						showAlert("Lỗi", "Cập nhật người dùng thất bại", AlertType.ERROR);
  					}
  				}
  			});
  		}

    @FXML
    private void clearInputsFields() {
        txtUserID.clear();
        txtFullName.clear();
        txtEmail.clear();
        txtPhoneNumber.clear();
        txtCreatedAt.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
