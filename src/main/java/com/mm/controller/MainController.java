package com.mm.controller;

import com.mm.config.StartupConfig;
import com.mm.model.Province;
import com.mm.model.User;
import com.mm.service.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.xml.soap.Text;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Created by lmy on 2018/2/8.
 */
@Controller
@Log4j
public class MainController extends BaseController implements Initializable {

    @Resource
    private LoginService loginService;

    @FXML
    private TextArea consoleContent;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputNumber;

    @FXML
    private SplitMenuButton inputProvince;

    @FXML
    private TextField inputAddress;

    @FXML
    private Button addButton;

    @FXML
    private TextField search;

    @FXML
    private Button searchButton;

    @FXML
    private Tab addTab;

    @FXML
    private Tab searchTab;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, Integer> numberColumn;

    @FXML
    private TableColumn<User, Province> provinceColumn;

    @FXML
    private TableColumn<User, String> addressColumn;

    @FXML
    private TableColumn<User, LocalDateTime> timeColumn;

    private TableColumn<User, String> deleteColumn;

    @FXML
    private TableColumn<User, String> modifyColumn;

    public MainController(StartupConfig config) {

        super(config);
    }

    public void initialize(URL location, ResourceBundle resources) {

        // TODO (don't really need to do anything here).
    }

}
