package dat.nx.sanbongdaquoc.controllers;


import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dat.nx.sanbongdaquoc.models.entities.UserDTO;
import dat.nx.sanbongdaquoc.services.UserBLL;
import dat.nx.sanbongdaquoc.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class DashBoardController {
	@FXML
	private TextField txtUserID,txtFullName,txtEmail,txtPhoneNumber,txtCreatedAt;
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
    private TableView<UserDTO> userTable; //Bảng danh sách người dùng
    @FXML
    private TableColumn<UserDTO, Integer> sttColumn; //Cột số thứ tự người dùng
    @FXML
    private TableColumn<UserDTO, String> nameColumn; //Cột tên người dùng
    @FXML
    private TableColumn<UserDTO, String> emailColumn; //Cột email người dùng
    @FXML
    private TableColumn<UserDTO, String> phoneColumn; //Cột số diện thoại người dùng
    @FXML
    private TableColumn<UserDTO, Timestamp> createdAtColumn; //Cột thời gian tạo
    
    //Tạo các biến BLL để giao tiếp với controller
    UserBLL userBLL = new UserBLL();
    
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
		userTable.getItems().setAll(userDTOs);
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
}
