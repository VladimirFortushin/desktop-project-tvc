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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.fortushin.tvc.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoginPageController implements Initializable {
    @Value("classpath:/templates/registration-page.fxml")
    private Resource registrationPageResource;
    @Value("classpath:/templates/table.fxml")
    private Resource tablePageResource;
    private final ApplicationContext applicationContext;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public LoginPageController(ApplicationContext applicationContext, PasswordEncoder passwordEncoder, UserService userService) {
        this.applicationContext = applicationContext;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;

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
        registrationButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                try {
                    FXMLLoader loader = new FXMLLoader(registrationPageResource.getURL());
                    loader.setControllerFactory(applicationContext::getBean);
                    Parent parent = loader.load();
                    Scene nextPageScene = new Scene(parent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(nextPageScene);
                    window.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String login = loginField.getText();
                String password = passwordField.getText();
                if(passwordEncoder.matches(password, userService.loadUserByUsername(login).getPassword())){
                    try {
                        FXMLLoader loader = new FXMLLoader(tablePageResource.getURL());
                        loader.setControllerFactory(applicationContext::getBean);
                        Parent parent = loader.load();
                        Scene nextPageScene = new Scene(parent);
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(nextPageScene);
                        window.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    errorLabel.setText("Invalid username or password");
                }
            }
        });
    }
}
