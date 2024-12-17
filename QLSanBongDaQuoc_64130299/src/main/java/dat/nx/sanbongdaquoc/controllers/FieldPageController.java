package dat.nx.sanbongdaquoc.controllers;

import java.math.BigDecimal;
import java.util.List;

import dat.nx.sanbongdaquoc.enums.FieldStatus;
import dat.nx.sanbongdaquoc.models.entities.FieldDTO;
import dat.nx.sanbongdaquoc.repositories.FieldDAL;
import dat.nx.sanbongdaquoc.services.FieldBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FieldPageController {

	// Khung giao diện
	@FXML
	private VBox mainLayout;

	// Tiêu đề
	@FXML
	private Label titleLabel;

	// Phần lọc thông tin sân
	@FXML
	private ToggleGroup statusGroup ;
	@FXML
	private RadioButton allStatusRadio,availableRadio,maintenanceRadio,bookedRadio;
	@FXML
	private Button applyButton;

	// Phần nhập thông tin sân
	@FXML
	private GridPane inputGrid;
	@FXML
	private TextField fieldIDField,fieldNameField,pricePerHourField,descriptionField;
	@FXML
	private ComboBox<FieldStatus> fieldStatusComboBox;

	// Các button xử lý
	@FXML
	private HBox actionButtons;
	@FXML
	private Button addButton,deleteButton,editButton,resetButton;

	// Phần bảng sân
	@FXML
	private TableView<FieldDTO> fieldTable;
	@FXML
	private TableColumn<FieldDTO, Integer> colFieldID;
	@FXML
	private TableColumn<FieldDTO, String> colFieldName,colFieldStatus,colDescription;
	@FXML
	private TableColumn<FieldDTO, BigDecimal> colPricePerHour;
	
	//Gọi lớp BLL để xử lý
	private FieldBLL fieldBLL = new FieldBLL(new FieldDAL());
	
	//Gán dữ liệu cho table view
	private ObservableList<FieldDTO> fieldList = FXCollections.observableArrayList();
	
	@FXML
	public void initialize() {
		//Cấu hình các cột table view
		setupTableColumn();
		
		//Gọi dữ liệu ban đầu từ BLL
		loadAllFields();
		
		//Khởi tạo trạng thái sân
		loadComboBox();
	}

	private void loadComboBox() {
	    fieldStatusComboBox.setItems(FXCollections.observableArrayList(FieldStatus.values()));
	    // Thiết lập cho ComboBox để hiển thị giá trị status dưới dạng chuỗi
	    fieldStatusComboBox.setCellFactory(param -> new javafx.scene.control.ListCell<FieldStatus>() {
	        @Override
	        protected void updateItem(FieldStatus item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || item == null) {
	                setText(null);
	            } else {
	                setText(item.getStatus());
	            }
	        }
	    });
	    fieldStatusComboBox.setButtonCell(fieldStatusComboBox.getCellFactory().call(null));
	}


	private void loadAllFields() {
		colFieldID.setCellValueFactory(new PropertyValueFactory<>("fieldID"));
		colFieldName.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
		colFieldStatus.setCellValueFactory(new PropertyValueFactory<>("statusString"));
		colPricePerHour.setCellValueFactory(new PropertyValueFactory<>("pricePerHour"));
		colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
	}

	private void setupTableColumn() {
		// Lấy danh sách sân bóng từ BLL
		List<FieldDTO> fields = fieldBLL.getAllFields();
		
		// Làm mới dữ liệu trong table view
		fieldList.setAll(fields);
		fieldTable.setItems(fieldList);
	}
	
}
