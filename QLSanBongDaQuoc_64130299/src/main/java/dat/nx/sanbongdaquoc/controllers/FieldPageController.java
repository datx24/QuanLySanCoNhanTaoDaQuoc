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
		
		// Lắng nghe sự kiện khi chọn một hàng trong TableView
	    fieldTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	        if (newValue != null) {
	            // Hiển thị thông tin của sân được chọn lên các trường nhập liệu
	            displayFieldInfo(newValue);
	        }
	    });
	}
	
	private void displayFieldInfo(FieldDTO field) {
	    // Điền thông tin sân vào các TextField và ComboBox
	    fieldIDField.setText(String.valueOf(field.getFieldID()));
	    fieldNameField.setText(field.getFieldName());
	    pricePerHourField.setText(field.getPricePerHour().toString());
	    descriptionField.setText(field.getDescription());
	    fieldStatusComboBox.setValue(field.getStatus());  // ComboBox sẽ nhận giá trị FieldStatus
	}

	//Phương thức thêm sân
	@FXML
	public void handleAddField() {
	    try {
	        // Lấy dữ liệu từ các TextField và ComboBox
	        String fieldName = fieldNameField.getText();
	        BigDecimal pricePerHour = new BigDecimal(pricePerHourField.getText());
	        String description = descriptionField.getText();
	        FieldStatus status = fieldStatusComboBox.getValue();

	        // Tạo đối tượng FieldDTO mới
	        FieldDTO newField = new FieldDTO(0, fieldName, status, pricePerHour, description);

	        // Gọi lớp BLL để thêm sân
	        boolean success = fieldBLL.addField(newField);

	        if (success) {
	            // Thêm đối tượng vào danh sách hiển thị TableView
	            fieldList.add(newField);
	            resetFields(); // Reset form nhập
	            System.out.println("Thêm sân thành công!");
	        } else {
	            System.out.println("Thêm sân thất bại!");
	        }
	    } catch (Exception e) {
	        System.out.println("Lỗi khi thêm sân: " + e.getMessage());
	    }
	}
	
	@FXML
	public void handleDeleteField() {
	    try {
	        // Lấy sân đang được chọn trong TableView
	        FieldDTO selectedField = fieldTable.getSelectionModel().getSelectedItem();

	        if (selectedField != null) {
	            // Gọi BLL để xóa sân
	            boolean success = fieldBLL.deleteField(selectedField.getFieldID());

	            if (success) {
	                fieldList.remove(selectedField);  // Xóa trực tiếp khỏi ObservableList
	                System.out.println("Xóa sân thành công!");
	                resetFields();  // Reset form nhập liệu sau khi xóa
	            } else {
	                System.out.println("Xóa sân thất bại!");
	            }
	        } else {
	            System.out.println("Vui lòng chọn một sân để xóa.");
	        }
	    } catch (Exception e) {
	    	 // In chi tiết lỗi (stack trace) ra màn hình
	        e.printStackTrace();
	        System.out.println("Lỗi khi xóa sân: " + e.getMessage());
	    }
	}
	
	@FXML
	public void handleEditField() {
	    try {
	        // Lấy dữ liệu từ các trường nhập liệu
	        int fieldID = Integer.parseInt(fieldIDField.getText());
	        String fieldName = fieldNameField.getText();
	        BigDecimal pricePerHour = new BigDecimal(pricePerHourField.getText());
	        String description = descriptionField.getText();
	        FieldStatus status = fieldStatusComboBox.getValue();

	        // Tạo đối tượng FieldDTO mới với thông tin đã chỉnh sửa
	        FieldDTO updatedField = new FieldDTO(fieldID, fieldName, status, pricePerHour, description);

	        // Gọi BLL để cập nhật thông tin sân
	        boolean success = fieldBLL.updateField(updatedField);

	        if (success) {
	            // Cập nhật lại danh sách trong TableView
	            int index = fieldList.indexOf(fieldTable.getSelectionModel().getSelectedItem());
	            fieldList.set(index, updatedField);  // Thay thế đối tượng trong list
	            fieldTable.refresh();  // Làm mới TableView
	            resetFields();  // Reset form nhập liệu
	            System.out.println("Cập nhật sân thành công!");
	        } else {
	            System.out.println("Cập nhật sân thất bại!");
	        }
	    } catch (Exception e) {
	        System.out.println("Lỗi khi cập nhật sân: " + e.getMessage());
	    }
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
	
	@FXML
	public void handleResetFields() {
	    resetFields();
	}

	private void resetFields() {
	    fieldIDField.clear();
	    fieldNameField.clear();
	    pricePerHourField.clear();
	    descriptionField.clear();
	    fieldStatusComboBox.getSelectionModel().clearSelection();
	    fieldTable.getSelectionModel().clearSelection();
	}
	
}
