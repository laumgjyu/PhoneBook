package com.lmy;

import com.lmy.config.SpringConfig;
import com.lmy.config.StartupConfig;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by lmy on 2018/2/8.
 */

public class Run extends Application {

    @Override
    public void start(Stage primaryStage) {

        Platform.setImplicitExit(true);

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        StartupConfig screens = context.getBean(StartupConfig.class);

        screens.setPrimaryStage(primaryStage);
        screens.startApp();
        screens.startup();
    }

    public static void main(String[] args) {
        launch(args);
    }

}