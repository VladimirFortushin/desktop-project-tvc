package ru.fortushin.tvc.javaFx;



import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.fortushin.tvc.JavaFxAppStarter.StageReadyEvent;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    @Value("classpath:/templates/login-page.fxml")
    private Resource tableResource;
    private final String applicationTitle;
    private final ApplicationContext applicationContext;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle, ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(tableResource.getURL());
            loader.setControllerFactory(applicationContext::getBean);
            Parent parent = loader.load();
            Stage stage = event.getStage();
            Scene scene = new Scene(parent, 400, 300);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.setTitle(applicationTitle);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
