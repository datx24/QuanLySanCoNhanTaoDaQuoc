package dat.nx.sanbongdaquoc.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class DashBoardController {
	@FXML
    private Button btnUserPage; // Nút chuyển trang danh sách người dùng
	@FXML
	private Button btnFieldPage; //Nút chuyển trang danh sách sân
	@FXML
	private Button btnBookingPage; //Nút chuyển trang danh sách đặt sân
	@FXML
	private Button btnInvoicePage; //Nút chuyển trang danh sách hóa đơn
	@FXML
	private Button btnMaintenancePage; //Nút chuyển trang danh sách bảo trì sân
    @FXML
    private AnchorPane paneToShowUsers; // Vùng AnchorPane hiển thị danh sách người dùng
    @FXML
    private AnchorPane paneToShowFields; // Vùng AnchorPane hiển thị danh sách sân
    @FXML
    private AnchorPane paneToShowBookings; // Vùng AnchorPane hiển thị danh sách đặt sân
    @FXML
    private AnchorPane paneToShowInvoices; // Vùng AnchorPane hiển thị danh sách hóa đơn
    @FXML
    private AnchorPane paneToShowMaintenances; // Vùng AnchorPane hiển thị danh sách bảo trì sân
    
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
}
