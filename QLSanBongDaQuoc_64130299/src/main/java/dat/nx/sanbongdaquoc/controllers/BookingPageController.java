package dat.nx.sanbongdaquoc.controllers;
import dat.nx.sanbongdaquoc.controllers.ButtonCellFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import dat.nx.sanbongdaquoc.enums.BookingStatus;
import dat.nx.sanbongdaquoc.enums.PaymentMethod;
import dat.nx.sanbongdaquoc.enums.PaymentStatus;
import dat.nx.sanbongdaquoc.models.entities.BookingDTO;
import dat.nx.sanbongdaquoc.models.entities.FieldDTO;
import dat.nx.sanbongdaquoc.models.entities.InvoiceDTO;
import dat.nx.sanbongdaquoc.repositories.BookingDAL;
import dat.nx.sanbongdaquoc.repositories.FieldDAL;
import dat.nx.sanbongdaquoc.repositories.InvoiceDAL;
import dat.nx.sanbongdaquoc.services.BookingBLL;
import dat.nx.sanbongdaquoc.services.FieldBLL;
import dat.nx.sanbongdaquoc.services.InvoiceBLL;
import dat.nx.sanbongdaquoc.utils.CommonUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


public class BookingPageController {
	//Phần bảng đặt sân
	@FXML
	private TableView<BookingDTO> bookingTable;
	@FXML
	private TableColumn<BookingDTO, String> bookingIDCol, bookingTimeCol, 
	statusCol, customerCol, fieldNameCol, paymentStatusCol, actionColumn;
	//Phần lọc danh sách đặt sân
	@FXML
	private ComboBox<String> fieldComboBox;
	@FXML
	private DatePicker bookingDatePicker;
	@FXML
	private Button searchButton;
	//Phần thanh công cụ làm mới,thống kê,xác nhận,hủy
	@FXML
	private Button refreshButton, reportButton, confirmButton, cancelButton;
	//Bảng các label hiển thị chi tiết 1 nội dung đặt sân
	@FXML
	private Label customerDetailsLabel,fieldDetailsLabel,bookingDetailsLabel,statusDetailsLabel,paymentStatusLabel;
	
	//Gọi lớp BLL để xử lý
	private BookingBLL bookingBLL = new BookingBLL(new BookingDAL());
	private FieldBLL fieldBLL = new FieldBLL(new FieldDAL());
	private InvoiceBLL invoiceBLL = new InvoiceBLL(new InvoiceDAL());
	
	//Gán dữ liệu cho table booking
	private ObservableList<BookingDTO> bookingList = FXCollections.observableArrayList();
	
	@FXML
	public void initialize () {
		//Cấu hình các cột booking table
		setupTableColumn();
		
		//Gọi dữ liệu từ BLL
		loadAllBookings();
		
		// Gán dữ liệu vào combobox
        loadFieldsToComboBox();

        // Xử lý sự kiện tìm kiếm
        setupSearchButton();
        
        // Xử lý sự kiện làm mới
        setupRefreshButton();
        
        // Gán sự kiện cho nút xuất báo cáo
        setupReportButton();
        
        // Gán sự kiện cho nút xác nhận
        setupConfirmButton();

        // Gán sự kiện cho nút hủy
        setupCancelButton();
	}

	// Phương thức xử lý sự kiện nút Hủy
    private void setupCancelButton() {
        cancelButton.setOnAction(event -> {
            BookingDTO selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
            if (selectedBooking != null) {
                // Thay đổi trạng thái thành "Đã hủy"
                selectedBooking.setStatus(BookingStatus.CANCELLED);
                bookingBLL.updateBookingStatus(selectedBooking);
                showAlert(AlertType.INFORMATION, "Thông báo", "Đơn đặt sân đã bị hủy.");
                loadAllBookings(); // Làm mới danh sách
            }
        });
    }

    // Phương thức xử lý sự kiện nút Xác Nhận
    private void setupConfirmButton() {
        confirmButton.setOnAction(event -> {
            BookingDTO selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
            if (selectedBooking != null) {
                // Cập nhật trạng thái thành "Đã xác nhận"
                selectedBooking.setStatus(BookingStatus.CONFIRMED);
                bookingBLL.updateBookingStatus(selectedBooking);
                
                // Hiển thị thông báo thành công
                showAlert(AlertType.INFORMATION, "Thành công", "Đơn đặt sân đã được xác nhận.");
                
                // Làm mới danh sách
                loadAllBookings();
            } else {
                // Hiển thị thông báo nếu không có đơn đặt sân nào được chọn
                showAlert(AlertType.WARNING, "Cảnh báo", "Vui lòng chọn đơn đặt sân để xác nhận.");
            }
        });
    }
    
    // Phương thức hiển thị thông báo Alert
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	// Phương thức được gọi khi chọn một bản ghi trong bảng
    @FXML
    private void onBookingSelected() {
        BookingDTO selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            BookingDTO bookingDetails = bookingBLL.getBookingById(selectedBooking.getBookingID());
            if (bookingDetails != null) {
                // Gán dữ liệu lên các Label
                customerDetailsLabel.setText("Tên khách hàng: " + bookingDetails.getUserName());
                fieldDetailsLabel.setText("Sân: " + bookingDetails.getFieldName());
                bookingDetailsLabel.setText("Thời gian yêu cầu: " + bookingDetails.getTimeDetails());
                statusDetailsLabel.setText("Trạng thái: " + bookingDetails.getStatus().getStatus());
                paymentStatusLabel.setText("Trạng thái thanh toán: " + bookingDetails.getPaymentStatus().getStatus());
            }
        }
    }
	@FXML
	private void setupReportButton() {
	    reportButton.setOnAction(event -> {
	        // Lấy thông tin lọc từ ComboBox và DatePicker
	        String selectedField = fieldComboBox.getValue();
	        LocalDate selectedDate = bookingDatePicker.getValue();
	        
	        // Lọc danh sách booking dựa trên tiêu chí tìm kiếm
	        List<BookingDTO> filteredBookings = bookingBLL.searchBookings(selectedField, selectedDate);

	        // Chỉ định đường dẫn file để lưu
	        String filePath = "E:\\64CNTT_CLC2\\QuanLySanCoNhanTaoDaQuoc\\QLSanBongDaQuoc_64130299\\BaoCaoQLSanBong.xlsx";

	        // Gọi phương thức xuất báo cáo
	        CommonUtils.exportToExcel(filteredBookings, filePath);
	    });
	}

	private void setupRefreshButton() {
		refreshButton.setOnAction(event -> {
	        // Reset ComboBox
	        fieldComboBox.setValue(null);

	        // Reset DatePicker
	        bookingDatePicker.setValue(null);

	        // Load lại toàn bộ danh sách đặt sân
	        setupTableColumn();
	    });
	}

	private void setupSearchButton() {
        searchButton.setOnAction(event -> {
            String selectedField = fieldComboBox.getValue();
            LocalDate selectedDate = bookingDatePicker.getValue();
            
            // Kiểm tra điều kiện lọc
            List<BookingDTO> filteredBookings = bookingBLL.searchBookings(selectedField, selectedDate);

            // Cập nhật lại bảng với dữ liệu lọc
            bookingTable.setItems(FXCollections.observableArrayList(filteredBookings));
        });
    }

	private void loadFieldsToComboBox() {
		// Lấy danh sách sân từ FieldBLL
        List<FieldDTO> fieldList = fieldBLL.getAllFields();
        ObservableList<String> fieldNames = FXCollections.observableArrayList();
        for (FieldDTO field : fieldList) {
            fieldNames.add(field.getFieldName());
        }
        fieldComboBox.setItems(fieldNames);
	}

	private void loadAllBookings() {
		bookingIDCol.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
		bookingTimeCol.setCellValueFactory(new PropertyValueFactory<>("timeDetails"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		paymentStatusCol.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
		customerCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
		fieldNameCol.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
	}

	private void setupTableColumn() {
	    // Lấy danh sách đặt sân từ lớp BLL
	    List<BookingDTO> bookings = bookingBLL.getAllBookings();

	    // Làm mới dữ liệu trong bảng
	    bookingList.setAll(bookings);
	    bookingTable.setItems(bookingList);

	    // Khởi tạo cột "Hành động" với TableCell chứa Button
	    actionColumn.setCellFactory(new Callback<TableColumn<BookingDTO, String>, TableCell<BookingDTO, String>>() {
	        @Override
	        public TableCell<BookingDTO, String> call(TableColumn<BookingDTO, String> param) {
	            // Tạo TableCell chứa Button
	            return new TableCell<BookingDTO, String>() {
	                private final Button paymentButton = new Button("Thanh toán");

	                {
	                    paymentButton.setOnAction(event -> {
	                        BookingDTO selectedBooking = getTableRow().getItem();
	                        handlePaymentAction(selectedBooking);
	                    });
	                }

	                @Override
	                protected void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty) {
	                        setGraphic(null);
	                    } else {
	                        setGraphic(paymentButton);
	                    }
	                }
	            };
	        }
	    });
	}
	
	private void handlePaymentAction(BookingDTO booking) {
	    if (booking == null) {
	        showAlert(AlertType.WARNING, "Cảnh báo", "Vui lòng chọn một đơn đặt sân để thực hiện thanh toán.");
	        return;
	    }

	    // Kiểm tra trạng thái thanh toán hiện tại
	    if (booking.getPaymentStatus() == PaymentStatus.PAID) {
	        showAlert(AlertType.INFORMATION, "Thông báo", "Đơn đặt sân này đã được thanh toán.");
	        return;
	    }

	    // Tạo một Alert để lựa chọn phương thức thanh toán
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Chọn phương thức thanh toán");
	    alert.setHeaderText("Vui lòng chọn phương thức thanh toán:");

	    // Thêm các ButtonType cho các phương thức thanh toán
	    ButtonType cashButton = new ButtonType("Tiền mặt");
	    ButtonType onlineButton = new ButtonType("Chuyển khoản");
	    ButtonType cancelButton = new ButtonType("Hủy", ButtonData.CANCEL_CLOSE);

	    alert.getButtonTypes().setAll(cashButton, onlineButton, cancelButton);

	    // Hiển thị alert và chờ người dùng chọn
	    Optional<ButtonType> result = alert.showAndWait();

	    // Kiểm tra nếu người dùng chọn một trong các phương thức thanh toán
	    if (result.isPresent()) {
	        InvoiceDTO invoice = null;

	        if (result.get() == cashButton) {
	            // Tạo hóa đơn cho phương thức thanh toán tiền mặt
	            invoice = invoiceBLL.createInvoice(booking, PaymentMethod.CASH);
	            showAlert(AlertType.INFORMATION, "Thành công", "Thanh toán bằng tiền mặt thành công.");
	        } else if (result.get() == onlineButton) {
	            // Tạo hóa đơn cho phương thức thanh toán chuyển khoản
	            invoice = invoiceBLL.createInvoice(booking, PaymentMethod.ONLINE);
	            showAlert(AlertType.INFORMATION, "Thành công", "Thanh toán chuyển khoản thành công.");
	        } else if (result.get() == cancelButton) {
	            // Nếu người dùng chọn hủy thì không làm gì cả
	            return;
	        }

	        if (invoice != null) {
	            // Cập nhật trạng thái thanh toán cho đơn đặt sân
	            booking.setPaymentStatus(PaymentStatus.PAID);
	            bookingBLL.processPayment(booking);

	            // Làm mới danh sách đặt sân
	            loadAllBookings();
	        }
	    }
	}

}
