package dat.nx.sanbongdaquoc.controllers;

import java.io.IOException;

import dat.nx.sanbongdaquoc.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginRegisterController {
	@FXML
	private TextField usernameField; // TextField nhập tên đăng nhập
	@FXML
	private TextField emailField;
	@FXML
	private PasswordField passwordField; // PasswordField nhập mật khẩu
	@FXML
	private Button loginButton; // Nút đăng nhập
	@FXML
	private TextField fullNameField; // TextField nhập họ và tên (dành cho đăng ký)
	@FXML
	private TextField emailRegisterField; // TextField nhập email (dành cho đăng ký)
	@FXML
	private TextField phoneField; // TextField nhập số điện thoại (dành cho đăng ký)
	@FXML
	private PasswordField passwordRegisterField; // PasswordField mật khẩu (dành cho đăng ký)
	@FXML
	private PasswordField confirmPasswordField; // Xác nhận mật khẩu (dành cho đăng ký)
	@FXML
	private Button registerButton; // Nút đăng ký
	@FXML
	private Pane registerPanel;
	@FXML
	private ImageView minimizeIcon,closeIcon;

	private UserBLL userBLL = new UserBLL();
	
	//Hiện form đăng kí
	@FXML
    public void showRegisterPanel() {
        registerPanel.setVisible(true);
    }
	
	@FXML
	//Đóng form đăng kí
	public void closeRegisterPanel() {
        registerPanel.setVisible(false);
    }
	
	// Xử lý đăng ký
    @FXML
    public void handleRegister() {
        String fullName = fullNameField.getText();
        String email = emailRegisterField.getText();
        String phone = phoneField.getText();
        String password = passwordRegisterField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin.",Alert.AlertType.ERROR);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Lỗi", "Mật khẩu và xác nhận mật khẩu không trùng khớp.",Alert.AlertType.ERROR);
            return;
        }

        // Gọi BLL để thêm người dùng mới vào cơ sở dữ liệu
        UserBLL userBLL = new UserBLL();
        boolean success = userBLL.registerUser(fullName, email, phone, password);

        if (success) {
            showAlert("Thành công", "Đăng ký thành công!",AlertType.INFORMATION);
            registerPanel.setVisible(false); // Ẩn panel đăng ký sau khi đăng ký thành công
            clearFields();
        } else {
            showAlert("Lỗi", "Đã có lỗi xảy ra, vui lòng thử lại.",AlertType.ERROR);
        }
    }

	// Xử lý đăng nhập
	@FXML
	public void handleLogin() {
	    String email = emailField.getText();
	    String password = passwordField.getText();

	    boolean loginSuccess = userBLL.checkLogin(email, password);

	    if (loginSuccess) {
	        // Hiển thị thông báo
	        showAlert("Thông báo", "Đăng nhập thành công!", AlertType.INFORMATION);

	        // Chuyển sang màn hình mới (DashBoard.fxml)
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dat/nx/sanbongdaquoc/fxml/DashBoard.fxml"));
	            Parent root = loader.load();

	            // Lấy thông tin Stage hiện tại
	            Stage stage = (Stage) emailField.getScene().getWindow();

	            // Thiết lập giao diện mới cho Stage
	            Scene scene = new Scene(root);
	            stage.setScene(scene);

	            // Tính toán để hiển thị Stage ở chính giữa màn hình desktop
	            stage.sizeToScene(); // Đảm bảo Stage có kích thước theo Scene
	            Screen screen = javafx.stage.Screen.getPrimary();
	            Rectangle2D bounds = screen.getVisualBounds();

	            double stageWidth = stage.getWidth();
	            double stageHeight = stage.getHeight();

	            double centerX = (bounds.getWidth() - stageWidth) / 2;
	            double centerY = (bounds.getHeight() - stageHeight) / 2;

	            stage.setX(centerX);
	            stage.setY(centerY);

	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	            showAlert("Lỗi", "Không thể chuyển sang màn hình mới.", Alert.AlertType.ERROR);
	        }
	    } else {
	        showAlert("Lỗi", "Đăng nhập thất bại!", AlertType.ERROR);
	    }
	}
	
	@FXML
	private void handleLogout() {
	    if (userBLL.checkLogout()) {
	        // Hiển thị thông báo đăng xuất thành công
	        showAlert("Thông báo", "Đăng xuất thành công!", AlertType.INFORMATION);

	        // Chuyển sang form LoginRegisterView
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dat/nx/sanbongdaquoc/fxml/LoginRegisterView.fxml"));
	            Parent root = loader.load();

	            // Tạo Stage mới cho LoginRegisterView
	            Stage stage = new Stage();
	            stage.setTitle("Login/Register");
	            Scene scene = new Scene(root);
	            stage.setScene(scene);

	            // Đặt vị trí trung tâm màn hình desktop
	            stage.sizeToScene();
	            Screen screen = Screen.getPrimary();
	            Rectangle2D bounds = screen.getVisualBounds();

	            double stageWidth = stage.getWidth();
	            double stageHeight = stage.getHeight();

	            double centerX = (bounds.getWidth() - stageWidth) / 2;
	            double centerY = (bounds.getHeight() - stageHeight) / 2;

	            stage.setX(centerX);
	            stage.setY(centerY);

	            // Hiển thị LoginRegisterView
	            stage.show();

	            // Đóng cửa sổ hiện tại (DashBoard)
	            Stage currentStage = (Stage) emailField.getScene().getWindow(); // Hoặc bất kỳ Node nào trong DashBoard
	            currentStage.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	            showAlert("Lỗi", "Không thể chuyển sang màn hình đăng nhập.", Alert.AlertType.ERROR);
	        }
	    } else {
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
	
	// Phương thức xóa các trường sau khi đăng ký thành công
    private void clearFields() {
        fullNameField.clear();
        emailRegisterField.clear();
        phoneField.clear();
        passwordRegisterField.clear();
        confirmPasswordField.clear();
    }
    
    @FXML
    private void minimizeWindow() {
        Stage stage = (Stage) minimizeIcon.getScene().getWindow();
        if (stage != null) {
            stage.setIconified(true); // Thu nhỏ cửa sổ
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        if (stage != null) {
            stage.close(); // Đóng cửa sổ
        }
    }
}
