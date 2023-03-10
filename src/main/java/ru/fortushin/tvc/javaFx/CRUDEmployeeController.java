package ru.fortushin.tvc.javaFx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fortushin.tvc.model.Employee;
import ru.fortushin.tvc.service.EmployeeService;
import ru.fortushin.tvc.util.EmployeeNotFoundException;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class CRUDEmployeeController implements Initializable {
    private final EmployeeService employeeService;
    private Employee employee;
    @Autowired
    public CRUDEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @FXML
    private Button backButton;

    @FXML
    private TextField buildingField;

    @FXML
    private Label buildingLabel;

    @FXML
    private TextField departmentField;

    @FXML
    private Label departmentLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField floorField;

    @FXML
    private Label floorNumberLabel;

    @FXML
    private TextField jobTitleField;

    @FXML
    private Label jobTitleLabel;

    @FXML
    private TextField lastNameField;

    @FXML
    private Label lastNameLabel;

    @FXML
    private TextField middleNameField;

    @FXML
    private Label middleNameLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField placeField;

    @FXML
    private Label placeLabel;

    @FXML
    private TextField roomField;

    @FXML
    private Label roomLabel;

    @FXML
    private Button saveButton;

    public void getEmployeeForUpdate(Employee employee){
        this.employee = employee;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        buildingField.setText(employee.getBuilding());
        floorField.setText(String.valueOf(employee.getFloorNumber()));
        departmentField.setText(employee.getDepartment());
        roomField.setText(employee.getRoom());
        placeField.setText(employee.getPlace());
        jobTitleField.setText(employee.getJobTitle());
        lastNameField.setText(employee.getName().split(" ")[0]);
        nameField.setText(employee.getName().split(" ")[1]);
        middleNameField.setText(employee.getName().split(" ")[2]);
        emailField.setText(employee.getEmail());


        saveButton.setOnAction(event -> {
            try {
                employeeService.getEmployeeById(employee.getId());
                employee.setBuilding(buildingField.getText());
                employee.setFloorNumber(Integer.parseInt(floorField.getText()));
                employee.setDepartment(departmentField.getText());
                employee.setRoom(roomField.getText());
                employee.setPlace(placeField.getText());
                employee.setJobTitle(jobTitleField.getText());
                employee.setName(lastNameField.getText() + " " + nameField.getText() + " " + middleNameField.getText());
                employee.setEmail(emailField.getText());
                employeeService.updateEmployee(employee.getId(), employee);
            }catch (EmployeeNotFoundException e){
                Employee newEmployee = new Employee(buildingField.getText(),
                        Integer.parseInt(floorField.getText()),
                        departmentField.getText(),
                        roomField.getText(),
                        placeField.getText(),
                        jobTitleField.getText(),
                        lastNameField.getText() + " " + nameField.getText() + " " + middleNameField.getText(),
                        emailField.getText());
                employeeService.createEmployee(newEmployee);
            }
        });
    }
}
