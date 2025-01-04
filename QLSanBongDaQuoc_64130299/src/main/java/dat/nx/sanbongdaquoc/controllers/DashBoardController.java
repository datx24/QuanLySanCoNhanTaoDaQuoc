package dat.nx.sanbongdaquoc.controllers;

import java.io.IOException;
import java.net.ResponseCache;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dat.nx.sanbongdaquoc.enums.FieldStatus;
import dat.nx.sanbongdaquoc.models.entities.FieldDTO;
import dat.nx.sanbongdaquoc.models.entities.UserDTO;
import dat.nx.sanbongdaquoc.services.UserBLL;
import dat.nx.sanbongdaquoc.utils.SceneManager;
import dat.nx.sanbongdaquoc.utils.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashBoardController {
	@FXML
	private Label lblName;
	@FXML
	private Button logoutButton;
	//Tạo các biến BLL để giao tiếp với controller
	@FXML
    private AnchorPane contentArea; // Content Area để load các page con
    UserBLL userBLL = new UserBLL();
	
	// Hàm này sẽ được gọi khi DashboardController được load
    @FXML
    public void initialize() {
        // Lấy thông tin người dùng từ SessionManager
        UserDTO currentUser = SessionManager.getCurrentUser();
        
        if (currentUser != null) {
            // Cập nhật label với tên người dùng
            lblName.setText(currentUser.getFullName());
        } else {
            // Nếu không có thông tin người dùng (người dùng chưa đăng nhập), bạn có thể hiển thị thông báo
            lblName.setText("Chưa đăng nhập");
        }
    }
    //Load DashBoard Page
    @FXML
    private void loadDashBoardPage() throws IOException {
    	loadPage("HomePage.fxml");
    }

    // Load User Page
    @FXML
    private void loadUserPage() throws IOException {
        loadPage("UserPage.fxml");
    }

    // Load Field Page
    @FXML
    private void loadFieldPage() throws IOException {
        loadPage("FieldPage.fxml");
    }

    // Load Booking Page
    @FXML
    private void loadBookingPage() throws IOException {
        loadPage("BookingPage.fxml");
    }
    
    // Load Invoice Page
    @FXML
    private void loadInvoicePage() throws IOException {
        loadPage("InvoicePage.fxml");
    }
    
 // Load Maintenance Page
    @FXML
    private void loadMaintenancePage() throws IOException {
        loadPage("MaintenancePage.fxml");
    }
    
    @FXML
    private void handleLogout() {
        if (userBLL.checkLogout()) {
            // Hiển thị thông báo đăng xuất thành công
            showAlert("Thông báo", "Đăng xuất thành công!", Alert.AlertType.INFORMATION);

            try {
                // Load LoginRegisterView
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dat/nx/sanbongdaquoc/fxml/LoginRegisterView.fxml"));
                Parent root = loader.load();

                // Tạo Stage mới
                Stage loginStage = new Stage();
                loginStage.setTitle("Login/Register");
                loginStage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(root);
                loginStage.setScene(scene);

                // Hiển thị form trước để kích thước Stage được tính toán đầy đủ
                loginStage.show();

                // Lấy thông tin kích thước màn hình
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();

                // Tính toán vị trí trung tâm
                double stageWidth = loginStage.getWidth();
                double stageHeight = loginStage.getHeight();

                double centerX = (bounds.getWidth() - stageWidth) / 2;
                double centerY = (bounds.getHeight() - stageHeight) / 2;

                // Cập nhật vị trí của Stage
                loginStage.setX(centerX);
                loginStage.setY(centerY);

                // Đóng cửa sổ hiện tại
                Stage currentStage = (Stage) logoutButton.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Lỗi", "Không thể chuyển sang màn hình đăng nhập.", Alert.AlertType.ERROR);
            }
        } else {
            // Hiển thị thông báo lỗi nếu đăng xuất thất bại
            showAlert("Lỗi", "Đăng xuất thất bại!", Alert.AlertType.ERROR);
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

    // Hàm tiện ích để load FXML
    private void loadPage(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dat/nx/sanbongdaquoc/fxml/" + fxmlFile));
        AnchorPane page = loader.load();

        contentArea.getChildren().clear();
        contentArea.getChildren().add(page);

        AnchorPane.setTopAnchor(page, 0.0);
        AnchorPane.setBottomAnchor(page, 0.0);
        AnchorPane.setLeftAnchor(page, 0.0);
        AnchorPane.setRightAnchor(page, 0.0);
}
    
    // Thu nhỏ cửa sổ
    public void minimizeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    // Đóng cửa sổ
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

