package ru.fortushin.tvc.javaFx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fortushin.tvc.model.Employee;
import ru.fortushin.tvc.repository.EmployeeRepository;

import java.net.URL;
import java.util.*;

@Component
@FxmlView
public class TableController implements Initializable {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public TableController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @FXML
    public TableView<Employee> tableView;
    @FXML
    public Label searchLabel;
    @FXML
    public TextField searchField;
    @FXML
    public ObservableList<Employee> observableList = FXCollections.observableArrayList();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        building.setCellValueFactory(new PropertyValueFactory<>("building"));
        floor.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("floorNumber"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        room.setCellValueFactory(new PropertyValueFactory<>("room"));
        place.setCellValueFactory(new PropertyValueFactory<>("place"));
        jobTitle.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        List<Employee> employeeList = employeeRepository.findAll();
        observableList.addAll(employeeList);
        tableView.setItems(observableList);

        FilteredList<Employee> filteredList = new FilteredList<>(observableList, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(employeeSearchModel -> {
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchValue = newValue.toLowerCase();

                if(employeeSearchModel.getName().toLowerCase().indexOf(searchValue) > -1){
                    return true;
                }else if(employeeSearchModel.getBuilding().toLowerCase().indexOf(searchValue) > -1){
                    return true;
                }else if(employeeSearchModel.getEmail().toLowerCase().indexOf(searchValue) > -1){
                    return true;
                }else if(employeeSearchModel.getJobTitle().toLowerCase().indexOf(searchValue) > -1){
                    return true;
                }else if(employeeSearchModel.getRoom().toLowerCase().indexOf(searchValue) > -1){
                    return true;
                }else if(employeeSearchModel.getPlace().toLowerCase().indexOf(searchValue) > -1){
                    return true;
                }else if(employeeSearchModel.getDepartment().toLowerCase().indexOf(searchValue) > -1){
                    return true;
                }else {
                    return false;
                }
            });
        });

        SortedList<Employee> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);


    }
}
