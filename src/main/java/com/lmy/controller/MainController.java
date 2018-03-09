package com.lmy.controller;

import com.lmy.config.StartupConfig;
import com.lmy.model.Province;
import com.lmy.model.User;
import com.lmy.service.ProvinceService;
import com.lmy.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Created by lmy on 2018/2/8.
 */
@Controller
@Log4j
public class MainController extends BaseController implements Initializable {

    @Resource
    private UserService userService;

    @Resource
    private ProvinceService provinceService;

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

    @FXML
    private TableColumn<User, String> deleteColumn;

    @FXML
    private TableColumn<User, String> modifyColumn;


    public MainController(StartupConfig config) {
        super(config);
    }

    public void initialize(URL location, ResourceBundle resources) {


        this.showTable(getUsers());

        this.addItemsListener();

        this.initialSplitMenuButton();

        this.initialAddTab();
    }

    private boolean checkAddParams() {
        if (StringUtils.isBlank(inputName.getText()) || StringUtils.isBlank(inputNumber.getText()) ||
                StringUtils.isBlank(inputAddress.getText()) || StringUtils.equals("选择你的省份", inputProvince.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "请出入正确的参数！");
            alert.show();
            return false;
        } else if (!checkPhoneNumber()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "电话号码格式不正确！");
            alert.show();
            return false;
        }
        return true;
    }

    private boolean checkPhoneNumber() {
        String phoneNumber = inputNumber.getText();
        System.out.println(phoneNumber);

        String pattern = "^1\\d{10}";
        return Pattern.matches(pattern, phoneNumber);
    }

    @FXML
    public void addUser() {

        //TODO 添加用户

        if (checkAddParams()) {
            String name = inputName.getText();
            String phoneNumber = inputNumber.getText();
            String province = inputProvince.getText();
            String address = inputAddress.getText();

            User user = new User(name, phoneNumber, new Province(province), address);
            this.table.getItems().add(user);

            Alert success = new Alert(Alert.AlertType.INFORMATION, "添加成功！");
            success.show();
        }


    }

    private void initialSplitMenuButton() {
        this.inputProvince.getItems().clear();

        List<Province> provinces = provinceService.getAllProvince();

        for (int i = 0; i < provinces.size(); i++) {

            MenuItem item = new MenuItem();
            item.setText(provinces.get(i).getName());
            item.setOnAction((event -> this.inputProvince.setText(item.getText())));
            this.inputProvince.getItems().add(item);
        }
    }

    private void initialAddTab() {
        addTab.setOnSelectionChanged((event -> {
            if (addTab.isSelected()) {
                this.showTable(getUsers());
            } else {
                this.table.getItems().clear();
            }

        }));
    }

    private void addItemsListener() {
        this.table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            String display = "";

            if (newSelection != null)
                display = "用户详情： \t\n" +
                        "姓名：" + newSelection.getName() + "\t\n" +
                        "电话：" + newSelection.getPhoneNumber() + "\t\n" +
                        "省份：" + newSelection.getProvince() + "\t\n" +
                        "地址：" + newSelection.getAddress() + "\t\n";

            consoleContent.setText(display);

        });
    }

    private ObservableList<User> getUsers() {

        //TODO 获取用户信息

        User user1 = new User("user1", "1", new Province("a"), "中华人名共和国中央人民政府今天成立了！中华人名共和国中央人民政府今天成立了！");
        User user2 = new User("user2", "2", new Province("b"), "hhhhh");
        User user3 = new User("user3", "3", new Province("c"), "hhhhh");
        User user4 = new User("user4", "4", new Province("d"), "hhhhh");
        User user5 = new User("user5", "5", new Province("e"), "hhhhh");
        User user6 = new User("user6", "6", new Province("f"), "hhhhh");

        User user7 = new User("user7", "1", new Province("a"), "中华人名共和国中央人民政府今天成立了！中华人名共和国中央人民政府今天成立了！");
        User user8 = new User("user8", "2", new Province("b"), "hhhhh");
        User user9 = new User("user9", "3", new Province("c"), "hhhhh");
        User user10 = new User("user10", "4", new Province("d"), "hhhhh");
        User user11 = new User("user11", "5", new Province("e"), "hhhhh");
        User user12 = new User("user12", "6", new Province("f"), "hhhhh");

        ObservableList<User> list = FXCollections.observableArrayList(user1, user2, user3, user4, user5, user6,
                user7, user8, user9, user10, user11, user12, user1, user2, user3, user4, user5, user6);
//        return list;
        return FXCollections.observableArrayList(userService.getAllUsers());
    }

    private void showTable(ObservableList<User> users) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        timeColumn.setCellFactory((column) -> {
            TableCell<User, LocalDateTime> cell = new TableCell<User, LocalDateTime>() {
                @Override
                public void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(LocalDateTime.now(), empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        this.setText(LocalDateTime.now().toString());
                    }
                }
            };
            return cell;
        });

        deleteColumn.setCellFactory((column) -> {
            TableCell<User, String> cell = new TableCell<User, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        Button delBtn = new Button("删除");
                        delBtn.setPrefWidth(100);
                        this.setGraphic(delBtn);
                        delBtn.setOnMouseClicked((me) -> {

                            User user = this.getTableView().getItems().get(this.getIndex());

                            table.getItems().remove(user);

                            //TODO 删除用户

                            System.out.println("删除 ");
                        });
                    }
                }
            };
            return cell;
        });

        modifyColumn.setCellFactory((column) -> {
            TableCell<User, String> cell = new TableCell<User, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        Button delBtn = new Button("修改");
                        delBtn.setPrefWidth(100);
                        this.setGraphic(delBtn);
                        delBtn.setOnMouseClicked((me) -> {

                            User user = this.getTableView().getItems().get(this.getIndex());

                            //TODO 修改用户

                            System.out.println("修改");
                        });
                    }
                }
            };
            return cell;
        });

        table.setItems(users);
    }

}
