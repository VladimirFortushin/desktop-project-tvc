package ru.fortushin.tvc.javaFx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.fortushin.tvc.service.UserService;
import ru.fortushin.tvc.util.PageSwitcher;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoginPageController implements Initializable {
    @Value("classpath:/templates/registration-page.fxml")
    private Resource registrationPageResource;
    @Value("classpath:/templates/table.fxml")
    private Resource tablePageResource;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final PageSwitcher pageSwitcher;

    @Autowired
    public LoginPageController(PasswordEncoder passwordEncoder, UserService userService, PageSwitcher pageSwitcher) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.pageSwitcher = pageSwitcher;
    }
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;
    @FXML
    private Button registrationButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Label authLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label userNameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registrationButton.setOnAction(event ->  {
                pageSwitcher.goTo(event, registrationPageResource);
        });

        loginButton.setOnAction(event ->  {

                String login = loginField.getText();
                String password = passwordField.getText();
                if(passwordEncoder.matches(password, userService.loadUserByUsername(login).getPassword())
                        && userService.loadUserByUsername(login).getUsername().equals(login)){
                    pageSwitcher.goTo(event, tablePageResource);
                }else{
                    errorLabel.setText("Invalid username or password");
                }
        });
    }


}
