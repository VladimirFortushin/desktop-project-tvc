package ru.fortushin.tvc.javaFx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.fortushin.tvc.model.Employee;
import ru.fortushin.tvc.repository.EmployeeRepository;
import ru.fortushin.tvc.service.EmployeeService;
import ru.fortushin.tvc.service.UserService;
import ru.fortushin.tvc.util.PageSwitcher;
import ru.fortushin.tvc.util.TableFilter;

import java.net.URL;
import java.util.*;

@Component
@FxmlView
public class TableController implements Initializable {
    @Value("classpath:/templates/employee-data.fxml")
    private Resource employeeDataResource;
    private final EmployeeRepository employeeRepository;
    private final TableFilter tableFilter;
    private final EmployeeService employeeService;
    private final PageSwitcher pageSwitcher;
    private String role;
    private final CRUDEmployeeController CRUDcontroller;

    @Autowired
    public TableController(EmployeeRepository employeeRepository, TableFilter tableFilter, EmployeeService employeeService, PageSwitcher pageSwitcher, CRUDEmployeeController cruDcontroller) {
        this.employeeRepository = employeeRepository;
        this.tableFilter = tableFilter;
        this.employeeService = employeeService;
        this.pageSwitcher = pageSwitcher;
        this.CRUDcontroller = cruDcontroller;
    }
    @FXML
    public Button updateButton;
    @FXML
    public Button createButton;
    @FXML
    public Button deleteButton;
    @FXML
    public TableView<Employee> tableView;
    @FXML
    public Label searchLabel;
    @FXML
    public TextField searchField;

    @FXML
    public TableColumn<Employee, String> building;
    @FXML
    public TableColumn<Employee, Integer> floor;
    @FXML
    public TableColumn<Employee, String> department;
    @FXML
    public TableColumn<Employee, String> room;
    @FXML
    public TableColumn<Employee, String> place;
    @FXML
    public TableColumn<Employee, String> jobTitle;
    @FXML
    public TableColumn<Employee, String> name;
    @FXML
    public TableColumn<Employee, String> email;
    public void getAuthorities(String role){
        this.role = role;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateButton.setDisable(!role.contains("ROLE_ADMIN"));
        createButton.setDisable(!role.contains("ROLE_ADMIN"));
        deleteButton.setDisable(!role.contains("ROLE_ADMIN"));



        building.setCellValueFactory(new PropertyValueFactory<>("building"));
        floor.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("floorNumber"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        room.setCellValueFactory(new PropertyValueFactory<>("room"));
        place.setCellValueFactory(new PropertyValueFactory<>("place"));
        jobTitle.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        refreshTable();


        deleteButton.setOnAction(event -> {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            Employee employeeForDeletion = tableView.getItems().get(selectedIndex);
            employeeService.deleteEmployee(employeeForDeletion);
            refreshTable();
            tableView.refresh();
        });

        updateButton.setOnAction(event -> {
                int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
                Employee selectedEmployee = tableView.getItems().get(selectedIndex);
                CRUDcontroller.getEmployeeForUpdate(selectedEmployee);
                pageSwitcher.goTo(event, employeeDataResource);
        });

        createButton.setOnAction(event -> {
           pageSwitcher.goTo(event, employeeDataResource);
        });
    }
    private void refreshTable(){
        List<Employee> employeeList = employeeRepository.findAll();
        ObservableList<Employee> observableList = FXCollections.observableList(employeeList);

        SortedList<Employee> sortedList = new SortedList<>(tableFilter.getFilteredList(observableList, searchField));
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }




}
