package ru.fortushin.tvc.javaFx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.fortushin.tvc.model.User;
import ru.fortushin.tvc.repository.UserRepository;
import ru.fortushin.tvc.service.RegistrationService;
import ru.fortushin.tvc.util.PageSwitcher;
import ru.fortushin.tvc.util.UserNameToLoginConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView
public class RegistrationPageController implements Initializable {
    @Value("classpath:/templates/login-page.fxml")
    private Resource loginPageResource;
    private final RegistrationService registrationService;
    private final UserRepository userRepository;
    private final PageSwitcher pageSwitcher;
    private final UserNameToLoginConverter userNameToLoginConverter;

    @Autowired
    public RegistrationPageController(RegistrationService registrationService, UserRepository userRepository,
                                      PageSwitcher pageSwitcher, UserNameToLoginConverter userNameToLoginConverter) {
        this.registrationService = registrationService;
        this.userRepository = userRepository;
        this.pageSwitcher = pageSwitcher;

        this.userNameToLoginConverter = userNameToLoginConverter;
    }


    @FXML
    private ChoiceBox<String> choiceBox;


    @FXML
    private TextField emailField;

    @FXML
    private Label emailLabel;

    @FXML
    private Label errorLabel;
    @FXML
    private Label successLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Label jobTitleLabel;
    @FXML
    private TextField jobTitleField;

    @FXML
    private Label nameLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button registerButton;

    @FXML
    private Button returnButton;

    @FXML
    private Label registrationLabel;

    @FXML
    private Label role;

    private String getUserRole(String choice){
        return switch(choice) {
            case "Администратор" -> "ROLE_ADMIN";
            case "Администратор БД" -> "ROLE_ADB";
            case "Пользователь" -> "ROLE_USER";
            default -> "ROLE_USER";
        };
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        choiceBox.getItems().add("Администратор");
        choiceBox.getItems().add("Администратор БД");
        choiceBox.getItems().add("Пользователь");

        registerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                errorLabel.setText("");
                StringBuilder errorText = new StringBuilder();
                if (!isValidEmail(emailField.getText())) {
                    errorText.append("Невалидный email. Пожалуйста, введите действительный адрес почты.\n");
                }
                if (!isValidName(nameField.getText())) {
                    errorText.append("Введите 'Имя Фамилию Отчество' кириллицей через пробел.\n");
                }
                if (userRepository.findUserByEmail(emailField.getText()).isPresent()) {
                    errorText.append("Пользователь с таким email уже существует\n");
                }
                String login = userNameToLoginConverter.mapUserNameToLogin(nameField.getText());
                if (errorText.isEmpty()) {
                    User user = new User(
                            login,
                            nameField.getText(),
                            getUserRole(choiceBox.getValue()),
                            jobTitleField.getText(),
                            passwordField.getText(),
                            emailField.getText());
                    registrationService.register(user);
                    successLabel.setText("Пользователь был успешно создан, логин: " + login);

                } else {
                    errorLabel.setText(errorText.toString());
                }
            }
        });

        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    pageSwitcher.goTo(event, loginPageResource);
                }
        });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        return email.matches(emailPattern);
    }

    private boolean isValidName(String name) {
        String namePattern = "^[А-ЯЁ][а-яё]+([-][А-ЯЁ][а-яё]+)?(\\s+[А-ЯЁ][а-яё]+([-][А-ЯЁ][а-яё]+)?){0,2}$";
        return name.matches(namePattern);
    }
}
