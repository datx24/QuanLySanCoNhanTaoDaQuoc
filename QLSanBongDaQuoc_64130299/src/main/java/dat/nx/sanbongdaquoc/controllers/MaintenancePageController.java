package dat.nx.sanbongdaquoc.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.controlsfx.control.textfield.TextFields;

import dat.nx.sanbongdaquoc.models.entities.InvoiceDTO;
import dat.nx.sanbongdaquoc.models.entities.MaintenanceDTO;
import dat.nx.sanbongdaquoc.repositories.FieldDAL;
import dat.nx.sanbongdaquoc.repositories.MaintenanceDAL;
import dat.nx.sanbongdaquoc.services.FieldBLL;
import dat.nx.sanbongdaquoc.services.MaintenanceBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MaintenancePageController {
	//Biến fxml hiển thị bảng danh sách sân bảo trì
	@FXML
	private TableView<MaintenanceDTO> maintenanceTable;
	@FXML
	private TableColumn<MaintenanceDTO, String> maintenanceIDColumn,fieldNameColumn,startDateColumn,
												endDateColumn,descriptionColumn;
	//Biến fxml để tìm kiếm danh sách bảo trì dựa vào tên sân
	@FXML
	private TextField searchFieldName;
	//Biến fxml hiển thị vùng nhập liệu thông tin lịch bảo trì
	@FXML
	private TextField txtFieldName;
	@FXML
	private DatePicker startDatePicker, endDatePicker;
	@FXML
	private TextArea descriptionArea;
	//Biến fxml để thục hiện các chức năng thêm sửa xóa
	@FXML
	private Button addButton,editButton,deleteButton;
	//Khai báo lớp BLL
	private MaintenanceBLL maintenanceBLL = new MaintenanceBLL(new MaintenanceDAL());
	private FieldBLL fieldBLL = new FieldBLL(new FieldDAL());
	
	@FXML
	private void initialize() {
		// Gọi phương thức setupTable để cấu hình bảng
        setupTable();

        // Gọi phương thức để hiển thị danh sách bảo trì
        loadInvoices();
        
        // Lắng nghe sự thay đổi trong TextField để tìm kiếm theo tên sân
        searchFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            searchMaintenances(newValue);
        });
        
        // Lắng nghe sự kiện khi người dùng chọn một bảng ghi trong TableView
        maintenanceTable.setOnMouseClicked(this::onTableRowClicked);
        
        // Cài đặt tính năng AutoCompletion cho TextField tên sân
        setupFieldNameAutoCompletion();
        
        addButton.setOnAction(event -> handleAdd());
        editButton.setOnAction(event -> handleEdit());
        deleteButton.setOnAction(event -> handleDelete());
	}
	
	private void handleDelete() {
        MaintenanceDTO selectedMaintenance = maintenanceTable.getSelectionModel().getSelectedItem();

        if (selectedMaintenance == null) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng chọn lịch bảo trì để xóa!");
            return;
        }

        if (maintenanceBLL.deleteMaintenance(selectedMaintenance)) {
            showAlert(Alert.AlertType.INFORMATION, "Xóa lịch bảo trì thành công!");
            loadInvoices();
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Xóa lịch bảo trì thất bại!");
        }
    }
	
	private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtFieldName.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        descriptionArea.clear();
    }
	
	private void handleEdit() {
        MaintenanceDTO selectedMaintenance = maintenanceTable.getSelectionModel().getSelectedItem();

        if (selectedMaintenance == null) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng chọn lịch bảo trì để sửa!");
            return;
        }

        String fieldName = txtFieldName.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String description = descriptionArea.getText();

        if (fieldName.isEmpty() || startDate == null || endDate == null || description.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (maintenanceBLL.checkMaintenanceOverlap(fieldName, startDate, endDate)) {
            showAlert(Alert.AlertType.ERROR, "Lịch bảo trì trùng với lịch hiện có!");
        } else {
            selectedMaintenance.setFieldName(fieldName);
            selectedMaintenance.setStartDate(Date.valueOf(startDate));
            selectedMaintenance.setEndDate(Date.valueOf(endDate));
            selectedMaintenance.setDescription(description);

            if (maintenanceBLL.updateMaintenance(selectedMaintenance)) {
                showAlert(Alert.AlertType.INFORMATION, "Cập nhật lịch bảo trì thành công!");
                loadInvoices();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Cập nhật lịch bảo trì thất bại!");
            }
        }
    }

	private void handleAdd() {
	    String fieldName = txtFieldName.getText();
	    LocalDate startDate = startDatePicker.getValue();
	    LocalDate endDate = endDatePicker.getValue();
	    String description = descriptionArea.getText();
	    
	    // Kiểm tra thông tin đầu vào
	    System.out.println("FieldName: " + fieldName);
	    System.out.println("Start Date: " + startDate);
	    System.out.println("End Date: " + endDate);
	    System.out.println("Description: " + description);

	    if (fieldName.isEmpty() || startDate == null || endDate == null || description.isEmpty()) {
	        showAlert(Alert.AlertType.WARNING, "Vui lòng nhập đầy đủ thông tin!");
	        return;
	    }

	    if (maintenanceBLL.checkMaintenanceOverlap(fieldName, startDate, endDate)) {
	        showAlert(Alert.AlertType.ERROR, "Lịch bảo trì trùng với lịch hiện có!");
	    } else {
	        // Tạo một đối tượng MaintenanceDTO
	        MaintenanceDTO maintenance = new MaintenanceDTO();
	        maintenance.setFieldName(fieldName);
	        maintenance.setStartDate(Date.valueOf(startDate));
	        maintenance.setEndDate(Date.valueOf(endDate));
	        maintenance.setDescription(description);

	        // Gọi phương thức BLL để thêm lịch bảo trì
	        if (maintenanceBLL.addMaintenance(maintenance)) {
	            showAlert(Alert.AlertType.INFORMATION, "Thêm lịch bảo trì thành công!");
	            loadInvoices(); // Cập nhật danh sách hiển thị
	            clearFields();  // Xóa các trường nhập liệu    
	        }
	    }
	}

	// Cài đặt tính năng AutoCompletion cho TextField tên sân
    private void setupFieldNameAutoCompletion() {
        // Lấy danh sách tên sân từ lớp BLL
        List<String> fieldNames = fieldBLL.getAllFieldNames();

        // Chuyển danh sách thành ObservableList
        ObservableList<String> observableFieldNames = FXCollections.observableArrayList(fieldNames);

        // Cài đặt AutoCompletion cho TextField
        TextFields.bindAutoCompletion(txtFieldName, observableFieldNames);
    }

	// Sự kiện khi người dùng chọn một bảng ghi trong TableView
    private void onTableRowClicked(MouseEvent event) {
        // Lấy dòng đã chọn từ bảng
        MaintenanceDTO selectedMaintenance = maintenanceTable.getSelectionModel().getSelectedItem();

        if (selectedMaintenance != null) {
            // Điền dữ liệu vào các trường nhập liệu
            txtFieldName.setText(selectedMaintenance.getFieldName());
            startDatePicker.setValue(selectedMaintenance.getStartDate().toLocalDate());
            endDatePicker.setValue(selectedMaintenance.getEndDate().toLocalDate());
            descriptionArea.setText(selectedMaintenance.getDescription());
        }
    }

	// Tìm kiếm lịch bảo trì theo tên sân
    private void searchMaintenances(String fieldName) {
        if (fieldName == null || fieldName.trim().isEmpty()) {
            loadInvoices();  // Nếu không có tên sân thì hiển thị tất cả
        } else {
            // Gọi phương thức tìm kiếm của BLL
            List<MaintenanceDTO> maintenances = maintenanceBLL.getMaintenanceByFieldName(fieldName);

            // Chuyển danh sách thành ObservableList
            ObservableList<MaintenanceDTO> observableMaintenances = FXCollections.observableArrayList(maintenances);

            // Đặt dữ liệu cho TableView
            maintenanceTable.setItems(observableMaintenances);
        }
    }
    
	private void loadInvoices() {
		 // Lấy danh sách hóa đơn từ BLL
        List<MaintenanceDTO> maintenances = maintenanceBLL.getAllMaintenance();

        // Chuyển danh sách thành ObservableList
        ObservableList<MaintenanceDTO> observableMaintenances = FXCollections.observableArrayList(maintenances);

        // Đặt dữ liệu cho TableView
        maintenanceTable.setItems(observableMaintenances);
	}

	private void setupTable() {
		// Cấu hình các cột
		maintenanceIDColumn.setCellValueFactory(new PropertyValueFactory<>("maintenanceID"));
		fieldNameColumn.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
		startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
	}
}
