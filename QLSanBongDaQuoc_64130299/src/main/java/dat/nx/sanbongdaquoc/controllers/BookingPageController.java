package dat.nx.sanbongdaquoc.controllers;

import java.time.LocalDate;
import java.util.List;

import dat.nx.sanbongdaquoc.models.entities.BookingDTO;
import dat.nx.sanbongdaquoc.models.entities.FieldDTO;
import dat.nx.sanbongdaquoc.repositories.BookingDAL;
import dat.nx.sanbongdaquoc.repositories.FieldDAL;
import dat.nx.sanbongdaquoc.services.BookingBLL;
import dat.nx.sanbongdaquoc.services.FieldBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookingPageController {
	//Phần bảng đặt sân
	@FXML
	private TableView<BookingDTO> bookingTable;
	@FXML
	private TableColumn<BookingDTO, String> bookingIDCol, bookingTimeCol, 
	statusCol, customerCol, fieldNameCol, paymentStatusCol;
	//Phần lọc danh sách đặt sân
	@FXML
	private ComboBox<String> fieldComboBox;
	@FXML
	private DatePicker bookingDatePicker;
	@FXML
	private Button searchButton;
	
	//Gọi lớp BLL để xử lý
	private BookingBLL bookingBLL = new BookingBLL(new BookingDAL());
	private FieldBLL fieldBLL = new FieldBLL(new FieldDAL());
	
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
		//Lấy danh sách đặt sân từ lớp BLL
		List<BookingDTO> bookings = bookingBLL.getAllBookings();
		
		//Làm mới dữ liệu trong bảng
		bookingList.setAll(bookings);
		bookingTable.setItems(bookingList);
	}
	
}
