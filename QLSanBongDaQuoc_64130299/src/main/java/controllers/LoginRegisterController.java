package controllers;

import BLL.UserBLL;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginRegisterController {
	@FXML
    private TextField usernameField; // TextField nhập tên đăng nhập
    @FXML
    private PasswordField passwordField; // PasswordField nhập mật khẩu
    @FXML
    private Button loginButton; // Nút đăng nhập
    @FXML
    private TextField fullNameField; // TextField nhập họ và tên (dành cho đăng ký)
    @FXML
    private TextField emailField; // TextField nhập email (dành cho đăng ký)
    @FXML
    private TextField phoneField; // TextField nhập số điện thoại (dành cho đăng ký)
    @FXML
    private PasswordField registerPasswordField; // PasswordField mật khẩu (dành cho đăng ký)
    @FXML
    private PasswordField confirmPasswordField; // Xác nhận mật khẩu (dành cho đăng ký)
    @FXML
    private Button registerButton; // Nút đăng ký
    
    private UserBLL userBLL = new UserBLL(); 
    
    // Xử lý đăng nhập
    @FXML
    public void handleLogin() {
    	
    }
    
    // Xử lý đăng kí
    @FXML
    public void handleRegister() {
    	
    }
    
}
