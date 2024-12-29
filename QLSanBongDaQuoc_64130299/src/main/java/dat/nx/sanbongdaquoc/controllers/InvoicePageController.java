package dat.nx.sanbongdaquoc.controllers;

import dat.nx.sanbongdaquoc.services.*;
import dat.nx.sanbongdaquoc.enums.PaymentMethod;
import dat.nx.sanbongdaquoc.models.entities.InvoiceDTO;
import dat.nx.sanbongdaquoc.repositories.InvoiceDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class InvoicePageController {
	//Biến để hiển thị danh sách sân
    @FXML
    private TableView<InvoiceDTO> invoiceTable;
    @FXML
    private TableColumn<InvoiceDTO, String> invoiceIDColumn,bookingIDColumn,amountColumn,
    										paymentMethodColumn,createdAtColumn;
    //Biến để tìm kiếm sân
    @FXML
    private TextField searchBookingID;
    @FXML
    private DatePicker startDatePicker,endDatePicker;
    @FXML
    private Button searchButton;
    //Biến để hiển thị thống kê sân
    @FXML
    private VBox statisticsPopup;
    @FXML
    private Label totalInvoicesLabel,totalAmountLabel;
    @FXML
    private Button statisticsButton1;

    private InvoiceBLL invoiceBLL = new InvoiceBLL(new InvoiceDAL());

    @FXML
    public void initialize() {
    	// Khởi tạo popup ẩn khi chưa bấm nút
        statisticsPopup.setVisible(false);
        
    	// Gọi phương thức setupTable để cấu hình bảng
        setupTable();

        // Gọi phương thức để hiển thị danh sách hóa đơn
        loadInvoices();
    }

    private void setupTable() {
        // Cấu hình các cột
        invoiceIDColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceID"));
        bookingIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    }

	private void loadInvoices() {
        // Lấy danh sách hóa đơn từ BLL
        List<InvoiceDTO> invoices = invoiceBLL.getAllInvoices();

        // Chuyển danh sách thành ObservableList
        ObservableList<InvoiceDTO> observableInvoices = FXCollections.observableArrayList(invoices);

        // Đặt dữ liệu cho TableView
        invoiceTable.setItems(observableInvoices);
    }
	
	@FXML
	private void handleSearch() {
	    String bookingID = searchBookingID.getText();  // Lấy bookingID từ TextField
	    LocalDate startDate = startDatePicker.getValue();  // Lấy ngày bắt đầu từ DatePicker
	    LocalDate endDate = endDatePicker.getValue();  // Lấy ngày kết thúc từ DatePicker

	    // Gọi phương thức tìm kiếm trong BLL
	    List<InvoiceDTO> results = invoiceBLL.searchInvoices(bookingID, startDate, endDate);

	    if (results.isEmpty()) {
	        // Hiển thị thông báo nếu không có kết quả
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Không có kết quả");
	        alert.setHeaderText("Không tìm thấy hóa đơn");
	        alert.setContentText("Không có hóa đơn nào phù hợp với tiêu chí tìm kiếm của bạn.");
	        alert.showAndWait();
	    } else {
	        // Cập nhật bảng với các kết quả tìm kiếm
	        invoiceTable.getItems().setAll(results);
	    }
	}

	@FXML
	private void handleRowSelection() {
	    // Lấy dòng được chọn trong bảng
	    InvoiceDTO selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
	    if (selectedInvoice == null) {
	        return; // Nếu không có bản ghi nào được chọn, thoát khỏi phương thức
	    }
	
	    // Hiển thị thông báo yêu cầu chọn phương thức thanh toán
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle("Chọn phương thức thanh toán");
	    alert.setHeaderText("Chọn phương thức thanh toán cho hóa đơn: " + selectedInvoice.getInvoiceID());
	    alert.setContentText("Chọn một phương thức thanh toán: Tiền mặt hoặc Chuyển khoản");
	
	    // Tạo hai nút cho Tiền mặt và Chuyển khoản
	    ButtonType cashButton = new ButtonType("Tiền mặt");
	    ButtonType onlineButton = new ButtonType("Chuyển khoản");
	    ButtonType cancelButton = new ButtonType("Hủy", ButtonData.CANCEL_CLOSE);
	
	    alert.getButtonTypes().setAll(cashButton, onlineButton, cancelButton);
	
	    // Xử lý lựa chọn của người dùng
	    Optional<ButtonType> result = alert.showAndWait();
	    if (result.isPresent() && result.get() == cashButton) {
	        // Cập nhật phương thức thanh toán thành Tiền mặt
	        if (invoiceBLL.updatePaymentMethod(selectedInvoice.getInvoiceID(), PaymentMethod.CASH)) {
	        	loadInvoices();
	            showSuccessMessage("Cập nhật phương thức thanh toán thành Tiền mặt thành công.");
	        } else {
	            showErrorMessage("Cập nhật phương thức thanh toán thất bại.");
	        }
	    } else if (result.isPresent() && result.get() == onlineButton) {
	        // Cập nhật phương thức thanh toán thành Chuyển khoản
	        if (invoiceBLL.updatePaymentMethod(selectedInvoice.getInvoiceID(), PaymentMethod.ONLINE)) {
	        	loadInvoices();
	            showSuccessMessage("Cập nhật phương thức thanh toán thành Chuyển khoản thành công.");
	        } else {
	            showErrorMessage("Cập nhật phương thức thanh toán thất bại.");
	        }
	    }
	}
	
	// Phương thức xóa hóa đơn
    @FXML
    private void handleDeleteInvoice() {
        // Lấy hóa đơn đã chọn trong bảng
        InvoiceDTO selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();

        if (selectedInvoice == null) {
            showErrorMessage("Vui lòng chọn một hóa đơn để xóa.");
            return;
        }

        // Hiển thị hộp thoại xác nhận
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa hóa đơn này?");
        alert.setContentText("Hóa đơn sẽ bị xóa vĩnh viễn!");

        ButtonType confirmButton = new ButtonType("Xóa");
        ButtonType cancelButton = new ButtonType("Hủy", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        // Xử lý kết quả xác nhận
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirmButton) {
            // Gọi phương thức từ BLL để xóa hóa đơn
            boolean success = invoiceBLL.deleteInvoice(selectedInvoice.getInvoiceID());
            if (success) {
                showSuccessMessage("Hóa đơn đã được xóa thành công.");
                // Cập nhật bảng sau khi xóa
                loadInvoices();
            } else {
                showErrorMessage("Xóa hóa đơn thất bại.");
            }
        }
    }
    
	// Phương thức hiển thị popup thống kê khi bấm vào statisticsButton1
    @FXML
    private void handleShowStatistics() {
    	Timestamp startDate = Timestamp.valueOf(startDatePicker.getValue().atStartOfDay());
        Timestamp endDate = Timestamp.valueOf(endDatePicker.getValue().atStartOfDay());

        // Lấy tổng số tiền và tổng số hóa đơn từ BLL
        BigDecimal totalAmount = invoiceBLL.calculateTotalAmountByDateRange(startDate, endDate);
        int totalInvoices = invoiceBLL.calculateTotalInvoicesByDateRange(startDate, endDate);
        // Định dạng số tiền theo chuẩn VND
        DecimalFormat decimalFormat = new DecimalFormat("#,###"); 

        // Chuyển số tiền thành chuỗi theo định dạng
        String formattedTotalAmount = decimalFormat.format(totalAmount);

        // Hiển thị kết quả lên giao diện
        totalInvoicesLabel.setText("Tổng hóa đơn: " + totalInvoices);
        totalAmountLabel.setText("Tổng số tiền: " + formattedTotalAmount + "VNĐ");
        statisticsPopup.setVisible(true);  // Hiển thị popup thống kê
    }

    // Phương thức ẩn popup thống kê khi bấm vào nút "Đóng"
    @FXML
    private void handleCloseStatistics() {
        statisticsPopup.setVisible(false);  // Ẩn popup thống kê
    }
	
	// Hiển thị thông báo thành công
	private void showSuccessMessage(String message) {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Thông báo");
	    alert.setHeaderText("Thành công");
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	
	// Hiển thị thông báo lỗi
	private void showErrorMessage(String message) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle("Thông báo lỗi");
	    alert.setHeaderText("Lỗi");
	    alert.setContentText(message);
	    alert.showAndWait();
	}

}
