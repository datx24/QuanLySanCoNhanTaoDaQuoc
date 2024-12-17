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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	//Tạo các biến BLL để giao tiếp với controller
    UserBLL userBLL = new UserBLL();
	@FXML
    private AnchorPane contentArea; // Content Area để load các page con

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
}

