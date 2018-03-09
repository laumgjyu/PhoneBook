package com.lmy.config;

import com.lmy.controller.BaseController;
import com.lmy.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by lmy on 2018/2/13.
 */
@Configuration
@Lazy
@CommonsLog
public class StartupConfig implements Observer {

    private static final String ON_START_FILE = "/view/Main.fxml";
    private static final String PRIMARY_TITLE = "电话簿";

    private AnchorPane root;
    private Stage stage;
    private Scene scene;

    public void startApp() {
        root = new AnchorPane();

        scene = new Scene(root);

        stage.setTitle(PRIMARY_TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setHeight(800);
        stage.setWidth(1030);

        stage.show();
    }


    private void setNode(Node node) {
        root.getChildren().setAll(node);
    }

    private void setNodeOnTop(Node node) {
        root.getChildren().add(node);
    }

    public void removeNode(Node node) {
        root.getChildren().remove(node);
    }

    private Node getNode(final BaseController controller, URL location) {
        FXMLLoader loader = new FXMLLoader(location);
        loader.setControllerFactory(aClass -> controller);

        try {
            return (Node) loader.load();
        } catch (Exception e) {
            log.error("Error casting node", e);
            return null;
        }
    }

    public Stage getPrimaryStage() {
        return stage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void startup() {
        setNode(getNode(loginController(), getClass().getResource(ON_START_FILE)));
    }

    @Override
    public void update(Observable o, Object arg) {
        startup();
    }

    @Bean
    @Scope("prototype")
    MainController loginController() {
        return new MainController(this);
    }
}
