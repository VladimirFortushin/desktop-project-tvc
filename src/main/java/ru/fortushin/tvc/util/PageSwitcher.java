package ru.fortushin.tvc.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PageSwitcher {
    private final ApplicationContext applicationContext;

    @Autowired
    public PageSwitcher(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void goTo(ActionEvent event, Resource resource) {
        try {
            FXMLLoader loader = new FXMLLoader(resource.getURL());
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
}
