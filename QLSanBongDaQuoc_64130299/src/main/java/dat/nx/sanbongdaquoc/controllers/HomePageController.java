package dat.nx.sanbongdaquoc.controllers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.controlsfx.control.textfield.TextFields;

import dat.nx.sanbongdaquoc.models.entities.BookingDTO;
import dat.nx.sanbongdaquoc.repositories.BookingDAL;
import dat.nx.sanbongdaquoc.repositories.FieldDAL;
import dat.nx.sanbongdaquoc.repositories.InvoiceDAL;
import dat.nx.sanbongdaquoc.services.BookingBLL;
import dat.nx.sanbongdaquoc.services.FieldBLL;
import dat.nx.sanbongdaquoc.services.InvoiceBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class HomePageController {
	//Cài đặt các biến hiên thị danh sách đặt sân chưa duyệt
	@FXML
    private TableView<BookingDTO> bookingTable;
    @FXML
    private TableColumn<BookingDTO, String> colBookingID,colUserName,colEmail,colPhoneNumber,colFieldName,colTimeDetail;

    //Cài đặt các biến hiển thị vùng thêm 1 đơn đặt sân mới
    @FXML
    private Pane bookingPane;
    @FXML
    private TextField userIDField,txtFieldName;
    @FXML
    private DatePicker bookingDatePicker;
    @FXML
    private Spinner<Integer> startHourSpinner,startMinuteSpinner,endHourSpinner,endMinuteSpinner;
    //Cài đặt các biến để hiển thị các thống kê
    @FXML
    private Label bookingCountLabel;
    @FXML
    private Label totalFieldsLabel;
    @FXML
    private Label totalRevenueLabel;
    //Gọi lớp BLL để cài đặt
    private BookingBLL bookingBLL = new BookingBLL(new BookingDAL());
    private FieldBLL fieldBLL = new FieldBLL(new FieldDAL());
    private InvoiceBLL invoiceBLL = new InvoiceBLL(new InvoiceDAL());
    
    @FXML
    public void initialize() {
    	bookingPane.setVisible(false);
    	
    	// Cấu hình các cột trong TableView
        setupTableColumn();

        // Lấy dữ liệu từ BLL và hiển thị lên TableView
        loadAllBookings();
        
        updateBookingCountLabel();
        updateTotalFieldsLabel();
        updateTotalRevenueLabel();
        
        setupFieldNameAutoCompletion();
    }
    
    private void setupFieldNameAutoCompletion() {
        // Lấy danh sách tên sân từ cơ sở dữ liệu
		List<String> fieldNames = fieldBLL.getAllFieldNames();

		// Cài đặt auto-completion cho TextField
		TextFields.bindAutoCompletion(txtFieldName, fieldNames);
    }
    
	@FXML
	private void handleDeleteBooking() {
	    // Lấy bản ghi được chọn
	    BookingDTO selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
	    if (selectedBooking == null) {
	        showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn một bản ghi để xóa.");
	        return;
	    }
	
	    // Hiển thị hộp thoại xác nhận
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle("Xác nhận xóa");
	    alert.setHeaderText("Bạn có chắc chắn muốn xóa?");
	    alert.setContentText("Hành động này không thể hoàn tác.");
	
	    // Xử lý kết quả từ hộp thoại
	    Optional<ButtonType> result = alert.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.OK) {
	        // Gọi BLL để xóa
	        boolean success = bookingBLL.deleteBookingById(selectedBooking.getBookingID());
	        if (success) {
	            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa đặt chỗ thành công.");
	            bookingTable.getItems().remove(selectedBooking); // Cập nhật giao diện
	        } else {
	            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa đặt chỗ.");
	        }
	    }
	}
    
    // Phương thức cập nhật số lượng sân bóng
    public void updateTotalFieldsLabel() {
        int totalFields = fieldBLL.getTotalFields(); // Lấy tổng số sân bóng từ BLL
        totalFieldsLabel.setText("" + totalFields);
    }
    
    //Phương thức cập nhật sô đơn đặt trong ngày
    public void updateBookingCountLabel() {
        int totalBookings = bookingBLL.getTotalBookingsToday(); // Lấy tổng số lượng đặt sân hôm nay từ BLL
        bookingCountLabel.setText(""+ totalBookings);
    }
    
    // Phương thức cập nhật doanh thu trong ngày
    public void updateTotalRevenueLabel() {
        BigDecimal totalRevenue = invoiceBLL.getTotalRevenueToday(); // Lấy tổng doanh thu từ BLL
        totalRevenueLabel.setText(totalRevenue + " VND");
    }
    
    @FXML
    public void addBooking() {
        try {
            // Lấy dữ liệu từ giao diện
            String userID = userIDField.getText();

            String fieldIDText = txtFieldName.getText();
            String fieldName = txtFieldName.getText(); 

            LocalDate bookingDate = bookingDatePicker.getValue();

            // Lấy giá trị từ Spinner (Spinner<Integer>)
            int startHour = startHourSpinner.getValue(); // Không cần ép kiểu
            int startMinute = startMinuteSpinner.getValue();
            int endHour = endHourSpinner.getValue();
            int endMinute = endMinuteSpinner.getValue();

            // Chuyển đổi giờ phút thành LocalTime
            LocalTime startTime = LocalTime.of(startHour, startMinute);
            LocalTime endTime = LocalTime.of(endHour, endMinute);

            // Gọi BLL để thêm đặt chỗ
            boolean success = bookingBLL.addBooking("BOOK" + System.currentTimeMillis(), userID, fieldName, bookingDate, startTime, endTime);

            // Hiển thị thông báo
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đặt chỗ thành công!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Sân đang bảo trì hoặc thời gian đặt chỗ bị trùng.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Dữ liệu không hợp lệ: " + e.getMessage());
            e.printStackTrace(); // In lỗi ra console để debug
        }
        loadAllBookings();
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void handleShowBookingPane() {
    	bookingPane.setVisible(true);
    }
    
    @FXML
    private void handleCloseBookingPane() {
    	bookingPane.setVisible(false);
    }

	private void loadAllBookings() {
		// Lấy danh sách các đơn đặt sân chưa duyệt từ BLL
        List<BookingDTO> pendingBookings = bookingBLL.getPendingBookings();

        // Chuyển đổi danh sách thành ObservableList để hiển thị trong TableView
        ObservableList<BookingDTO> bookingList = FXCollections.observableArrayList(pendingBookings);

        // Đưa dữ liệu vào TableView
        bookingTable.setItems(bookingList);	
	}

	// Cấu hình các cột trong TableView
    private void setupTableColumn() {
        colBookingID.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colFieldName.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
        colTimeDetail.setCellValueFactory(new PropertyValueFactory<>("timeDetails"));
    }
}
