package dat.nx.sanbongdaquoc.controllers;

import java.net.ResponseCache;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dat.nx.sanbongdaquoc.enums.FieldStatus;
import dat.nx.sanbongdaquoc.models.entities.FieldDTO;
import dat.nx.sanbongdaquoc.models.entities.UserDTO;
import dat.nx.sanbongdaquoc.services.UserBLL;
import dat.nx.sanbongdaquoc.utils.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class DashBoardController {
	@FXML
	private TextField txtUserID,txtFullName,txtEmail,txtPhoneNumber,txtCreatedAt,searchField;
	@FXML
	private StackPane myStackPane;
	@FXML
	private AnchorPane anchorPaneTop,paneToShowUsers,paneToShowFields,paneToShowBookings,
	paneToShowInvoices,paneToShowMaintenances;
	@FXML
    private Button btnUserPage,btnFieldPage,btnBookingPage,btnInvoicePage,btnMaintenancePage;
	@FXML
	private Button btnLogout;
    @FXML
    private TableView<UserDTO> userTable,fieldTable;
    @FXML
    private TableColumn<UserDTO, Integer> sttColumn; //Cột số thứ tự người dùng
    @FXML
    private TableColumn<UserDTO, String> nameColumn,emailColumn,phoneColumn; //Cột tên,email,số điện thoại người dùng
    @FXML
    private TableColumn<UserDTO, Timestamp> createdAtColumn; //Cột thời gian tạo
    @FXML
    private TableColumn<FieldDTO, String> colFieldName,colFieldStatus,colDescription;
    @FXML
    private TableColumn<FieldDTO, Integer> colFieldID;
    @FXML
    private TableColumn<FieldDTO, Double> colPricePerHour;
    @FXML
    private Button btnAddField,btnEditField,btnDeleteField;
    @FXML
    private ComboBox<FieldStatus> fieldStatusCB; //trạng thái sân
    @FXML
    private TextField txtFieldName,txtPricePerHour; //tên và giá sân
    @FXML
    private TextArea txtAreaDescription;
    @FXML
    private RadioButton radioAvailable,radioMaintenance,radioBooked,radioAll;// nút lọc trạng thái sân
    @FXML
    private ToggleGroup fieldStatusToggleGroup;
    
    //Tạo các biến BLL để giao tiếp với controller
    UserBLL userBLL = new UserBLL();
    private ObservableList<UserDTO> userList = FXCollections.observableArrayList();
    
 // Phương thức xử lý sự kiện chuyển trang
    @FXML
    private void switchPage(ActionEvent event) {
    	if(event.getSource() == btnUserPage) {
    		paneToShowUsers.setVisible(true);
    		paneToShowFields.setVisible(false);
    		paneToShowBookings.setVisible(false);
    		paneToShowInvoices.setVisible(false);
    		paneToShowMaintenances.setVisible(false);
    	} else if(event.getSource() == btnFieldPage) {
    		paneToShowUsers.setVisible(false);
    		paneToShowFields.setVisible(true);
    		paneToShowBookings.setVisible(false);
    		paneToShowInvoices.setVisible(false);
    		paneToShowMaintenances.setVisible(false);
    	} else if(event.getSource() == btnBookingPage) {
    		paneToShowUsers.setVisible(false);
    		paneToShowFields.setVisible(false);
    		paneToShowBookings.setVisible(true);
    		paneToShowInvoices.setVisible(false);
    		paneToShowMaintenances.setVisible(false);
    	} else if(event.getSource() == btnInvoicePage) {
    		paneToShowUsers.setVisible(false);
    		paneToShowFields.setVisible(false);
    		paneToShowBookings.setVisible(false);
    		paneToShowInvoices.setVisible(true);
    		paneToShowMaintenances.setVisible(false);
    	} else if(event.getSource() == btnMaintenancePage) {
    		paneToShowUsers.setVisible(false);
    		paneToShowFields.setVisible(false);
    		paneToShowBookings.setVisible(false);
    		paneToShowInvoices.setVisible(false);
    		paneToShowMaintenances.setVisible(true);
    	}
    }
    
    @FXML
    private void initialize() {
    	Scene scene = myStackPane.getScene();
    	if(scene != null) {
    		myStackPane.prefWidthProperty().bind(scene.widthProperty());
    		myStackPane.prefHeightProperty().bind(scene.heightProperty());
    	}
    	setupTableColumns();
    	//Liên kết userList với bảng
    	userTable.setItems(userList);
      	setupUserSearchByName();
	}
    
    //Lọc danh sách người dùng theo tên
    private void setupUserSearchByName() {
		FilteredList<UserDTO> filterUserList = 
			new FilteredList<UserDTO>(userList, p -> true);//p -> true nghĩa là toàn bộ các p tử trong danh sách được hiển thị
		
		//Lắng nghe sự thay đổi trong ô Search để cập nhật bộ lọc
		searchField.textProperty().addListener((observable,olaValue,newValue) -> {
			filterUserList.setPredicate(user -> {
				//Nếu không nhập gì,hiển thị tất cả
				if(newValue == null || newValue.isEmpty()) {
					return true;	
				}
				//So sánh tên (không phân biệt hoa thường)
				String lowerCaseFilter = newValue.toLowerCase();
				return user.getFullName().toLowerCase().contains(lowerCaseFilter);
			});
		});
		userTable.setItems(filterUserList);
	}

	//Phương thức thiết lập dữ liệu các cột trong bảng danh sách người dùng
    private void setupTableColumns() {
    	//Gán thuộc tính cho từng cột
    
    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    	emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    	phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    	createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    	//Tạo số thứ tự
    	sttColumn.setCellValueFactory(cellData -> {
    		int index = userTable.getItems().indexOf(cellData.getValue()) + 1;
    		return new javafx.beans.property.SimpleIntegerProperty(index).asObject();
    	});
    	//Load dữ liệu
    	loadUserData();
    	//Xử lý sự kiện khi bấm vào 1 hàng
    	userTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
    		if(newValue != null) {
    			txtUserID.setText(newValue.getUserID());
    			txtFullName.setText(newValue.getFullName());
    			txtEmail.setText(newValue.getEmail());
    			txtPhoneNumber.setText(newValue.getPhoneNumber());
    			//Chuyển TimeStamp sang String trước khi hiển thị
    			if(newValue.getCreatedAt() != null) {
    				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-HH-dd HH:mm:ss");
    				txtCreatedAt.setText(newValue.getCreatedAt().toLocalDateTime().format(formatter));
    			} else {
    				txtCreatedAt.setText("");
    			}
    		}
    	});
    }

	private void loadUserData() {
		List<UserDTO> userDTOs = userBLL.getAllUsers();
		userList.setAll(userDTOs);
	}
	
	@FXML
	private void handleLogout() {
		if(userBLL.checkLogout()) {
			//Hiển thị thông báo đăng xuất thành công
    		showAlert("Thông báo", "Đăng nhập thành công!", AlertType.INFORMATION);
			SceneManager.changeScene("/dat/nx/sanbongdaquoc/fxml/LoginRegisterView.fxml");
		} else {
			System.out.println(Alert.AlertType.ERROR);	
		}
	}
	
	// Phương thức hiển thị thông báo
		private void showAlert(String title, String message, AlertType alertType) {
			Alert alert = new Alert(alertType);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.showAndWait();
		}
		
	 //Phương thức thêm người dùng
		@FXML
		private void handleAddUser(ActionEvent event) {
			//Lấy dữ liệu từ các trường nhập người dùng
			String userID = txtUserID.getText();
			String fullName = txtFullName.getText();
			String email = txtEmail.getText();
			String phoneNumber = txtPhoneNumber.getText();
			
			//Kiểm tra lỗi khi người dùng để trống trường nhập
			if(fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
				showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin", Alert.AlertType.ERROR);
			}
			
			//Tạo đối tượng UserDTO
			UserDTO newUser = new UserDTO();
			newUser.setUserID(userID);
			newUser.setFullName(fullName);
			newUser.setEmail(email);
			newUser.setPhoneNumber(phoneNumber);
			newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			
			//Gọi hàm thêm người dùng từ UserBLL
			if(userBLL.addUser(newUser)) {
				UserDTO savedUser = userBLL.getUserByEmail(email); // Giả sử email là duy nhất
				userList.add(newUser);
				userTable.refresh();
				clearInputsFields();
				showAlert("Thành công","Thêm người dùng thành công",Alert.AlertType.INFORMATION);
			} else {
				showAlert("Thất bại","Không thể thêm người dùng",Alert.AlertType.ERROR);
			}
		}
		
		@FXML
		private void clearInputsFields() {
			txtUserID.clear();
			txtFullName.clear();
			txtEmail.clear();
			txtPhoneNumber.clear();
			txtCreatedAt.clear();
			userTable.refresh();
		}
	//Phương thức xóa người dùng
		@FXML
		private void handleDeleteUser() {
		    // Lấy chỉ số của người dùng được chọn từ table view
		    int selectedIndex = userTable.getSelectionModel().getSelectedIndex();
		    
		    // Kiểm tra nếu người dùng chưa được chọn hoặc không có chỉ số hợp lệ
		    if (selectedIndex == -1) {
		        showAlert("Cảnh báo", "Vui lòng chọn người dùng để xóa", Alert.AlertType.WARNING);
		        return;
		    }

		    // Lấy người dùng từ chỉ số trong userList
		    UserDTO selectedUser = userList.get(selectedIndex);
		    
		    // Hiển thị hộp thoại xác nhận trước khi xóa
		    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn muốn xóa người dùng này không?", ButtonType.YES, ButtonType.NO);
		    confirmAlert.setTitle("Xác nhận xóa người dùng");
		    confirmAlert.showAndWait().ifPresent(response -> {
		        if (response == ButtonType.YES) {
		            // Gọi phương thức xóa từ BLL
		            boolean result = userBLL.deleteUser(selectedUser);
		            
		            if (result) {
		                // Xóa người dùng ra khỏi danh sách hiển thị và cập nhật TableView
		                userList.remove(selectedIndex);
		                userTable.setItems(userList);
		                showAlert("Thông báo", "Xóa người dùng thành công", Alert.AlertType.INFORMATION);
		            } else {
		                showAlert("Lỗi", "Xóa người dùng thất bại", Alert.AlertType.ERROR);
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
}

