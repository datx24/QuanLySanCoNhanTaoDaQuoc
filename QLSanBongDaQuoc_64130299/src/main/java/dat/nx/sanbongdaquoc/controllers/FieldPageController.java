package dat.nx.sanbongdaquoc.controllers;

import java.math.BigDecimal;

import dat.nx.sanbongdaquoc.enums.FieldStatus;
import dat.nx.sanbongdaquoc.models.entities.FieldDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
	private TableColumn<FieldDTO, String> colFieldID,colFieldName,colFieldStatus,colDescription;
	@FXML
	private TableColumn<FieldDTO, BigDecimal> colPricePerHour;


}
