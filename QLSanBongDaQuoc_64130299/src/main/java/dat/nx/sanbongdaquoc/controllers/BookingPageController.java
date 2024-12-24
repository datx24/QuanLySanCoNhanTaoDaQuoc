package dat.nx.sanbongdaquoc.controllers;

import java.util.List;

import dat.nx.sanbongdaquoc.models.entities.BookingDTO;
import dat.nx.sanbongdaquoc.repositories.BookingDAL;
import dat.nx.sanbongdaquoc.services.BookingBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
	
	//Gọi lớp BLL để xử lý
	private BookingBLL bookingBLL = new BookingBLL(new BookingDAL());
	
	//Gán dữ liệu cho table booking
	private ObservableList<BookingDTO> bookingList = FXCollections.observableArrayList();
	
	@FXML
	public void initialize () {
		//Cấu hình các cột booking table
		setupTableColumn();
		
		//Gọi dữ liệu từ BLL
		loadAllBookings();
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
