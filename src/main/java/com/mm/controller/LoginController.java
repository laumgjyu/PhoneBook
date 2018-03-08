package com.mm.controller;

import com.mm.config.StartupConfig;
import com.mm.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by lmy on 2018/2/8.
 */
@Controller
public class LoginController extends BaseController implements Initializable {

    @Resource
    private LoginService loginService;

    @FXML
    private Button loginButton;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    public LoginController(StartupConfig config) {
        super(config);
    }

    public void initialize(URL location, ResourceBundle resources) {

        // TODO (don't really need to do anything here).

    }

    public void login(ActionEvent event) {

        if (StringUtils.isBlank(username.getText()) || StringUtils.isBlank(password.getText())) {
            Alert warning = new Alert(Alert.AlertType.ERROR);
            warning.setTitle("警告");
            warning.setResizable(false);
            warning.setHeaderText("用户名或密码不能为空！");
            warning.setContentText("请重新输入！");
            warning.show();
        } else {
            loginService.login(username.getText(), password.getText());
        }
    }
}
