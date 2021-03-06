package com.lmy.controller;

import com.lmy.config.StartupConfig;
import com.lmy.core.cache.MemoryCache;
import com.lmy.core.hash.Node;
import com.lmy.model.Province;
import com.lmy.model.User;
import com.lmy.service.ProvinceService;
import com.lmy.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * Created by lmy on 2018/2/8.
 */
@Controller
@Log4j
public final class MainController extends BaseController implements Initializable {

    ExecutorService executorService = Executors.newFixedThreadPool(2);
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
        } else if (!checkPhoneNumber(inputNumber.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "电话号码格式不正确！");
            alert.show();
            return false;
        }
        return true;
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        String pattern = "^1\\d{10}";
        return Pattern.matches(pattern, phoneNumber);
    }

    @FXML
    public final void addUser() {

        if (checkAddParams()) {
            String name = inputName.getText();
            String phoneNumber = inputNumber.getText();
            String province = inputProvince.getText();
            String address = inputAddress.getText();

            User user = new User(name, phoneNumber, new Province(province), address);
            this.table.getItems().add(user);

            userService.addUser(user);

            Alert success = new Alert(Alert.AlertType.INFORMATION, "添加成功！");
            success.show();
            clearInput();
        }
    }

    @FXML
    public final void search() {
        consoleContent.clear();

        String searchParam = search.getText();
        this.table.getItems().clear();

        if (StringUtils.isBlank(searchParam)) {
            return;
        }

        conflictWithList(searchParam);

        conflictWithArray(searchParam);
        clearInput();
    }

    private void conflictWithList(String searchParam) {
        String consoleAppend = "用户：%s   " +
                "链地址法查找长度：%s \t\n";
        int searchCount = 1;
        if (checkPhoneNumber(searchParam)) {
            Node<String, User> node = MemoryCache.getInMapByPhoneNumber(searchParam);
            if (node != null) {
                this.table.getItems().add(node.getValue());

                consoleContent.appendText(String.format(consoleAppend, node.getValue().getName(), node.getSearchLength()));

                while ((node = node.getNext()) != null) {
                    searchCount++;
                    if (searchParam.equalsIgnoreCase(node.getValue().getPhoneNumber())) {
                        node.setSearchLength(searchCount);
                        this.table.getItems().add(node.getValue());

                        consoleContent.appendText(String.format(consoleAppend, node.getValue().getName(), node.getSearchLength()));
                    }
                }
            }
        } else {
            Node<String, User> node = MemoryCache.getInMapByName(searchParam);
            if (node != null) {
                this.table.getItems().add(node.getValue());

                consoleContent.appendText(String.format(consoleAppend, node.getValue().getName(), node.getSearchLength()));

                while ((node = node.getNext()) != null) {
                    searchCount++;
                    if (searchParam.equalsIgnoreCase(node.getValue().getName())) {
                        node.setSearchLength(searchCount);
                        this.table.getItems().add(node.getValue());

                        consoleContent.appendText(String.format(consoleAppend, node.getValue().getName(), node.getSearchLength()));
                    }
                }
            }
        }
    }

    private void conflictWithArray(String searchParam) {
        String consoleAppend = "用户：%s   " +
                "线性探测法查找长度：%s \t\n";

        if (checkPhoneNumber(searchParam)) {
            Node<String, User> node = MemoryCache.getInTableByPhoneNumber(searchParam);
            if (node != null)
                consoleContent.appendText(String.format(consoleAppend, node.getValue().getName(), node.getSearchLength()));
        } else {
            Node<String, User> node = MemoryCache.getInTableByName(searchParam);
            if (node != null)
                consoleContent.appendText(String.format(consoleAppend, node.getValue().getName(), node.getSearchLength()));
        }
    }

    private void clearInput() {
        inputName.setText("");
        inputNumber.setText("");
        inputAddress.setText("");
        inputProvince.setText("请选择省份");
        search.setText("");
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
                this.table.setItems(getUsers());
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

    private boolean confirmDelete() {
        Alert confirm = new Alert(Alert.AlertType.WARNING, "确定删除该条记录吗？", ButtonType.YES, ButtonType.CANCEL);
        Optional<ButtonType> buttonType = confirm.showAndWait();
        if (buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
            return true;
        } else {
            return false;
        }
    }

    private void modify(User user) {
        Button complete = new Button("完成");
        TextField name = new TextField(user.getName());
        TextField phoneNumber = new TextField(user.getPhoneNumber());
        TextArea address = new TextArea(user.getAddress());
        SplitMenuButton modifyProvince = new SplitMenuButton();

        {
            modifyProvince.getItems().clear();
            List<Province> provinces = provinceService.getAllProvince();

            for (Province province : provinces) {

                MenuItem item = new MenuItem();
                item.setText(province.getName());
                item.setOnAction((event -> modifyProvince.setText(item.getText())));
                modifyProvince.getItems().add(item);
            }
        }
        modifyProvince.setText(user.getProvince().getName());

        GridPane root = new GridPane();
        Scene scene = new Scene(root, 320, 480);
        Stage stage = new Stage();

        stage.setTitle("正在编辑用户：" + user.getName());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setHeight(380);
        stage.setWidth(320);
        stage.setAlwaysOnTop(true);

        root.setHgap(1);
        root.setVgap(4);
        root.add(name, 0, 0);
        root.add(phoneNumber, 0, 1);
        root.add(modifyProvince, 0, 2);
        root.add(address, 0, 3);
        root.add(complete, 0, 4);

        complete.setPrefWidth(310);

        complete.setOnMouseClicked((event -> {
            User user2Save = new User(name.getText(), phoneNumber.getText(), new Province(modifyProvince.getText()), address.getText());

            userService.deleteUser(user);
            userService.addUser(user2Save);
            if (addTab.isSelected())
                table.getItems().setAll(getUsers());
            else
                table.getItems().setAll(FXCollections.observableArrayList(user2Save));
            stage.hide();

        }));

        stage.show();
    }

    private ObservableList<User> getUsers() {

        return FXCollections.observableArrayList(userService.getAllUsers());
    }

    private void showTable(ObservableList<User> users) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("localDate"));

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

                            if (confirmDelete()) {
                                table.getItems().remove(user);
                                userService.deleteUser(user);
                            }

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

                            modify(user);
                        });
                    }
                }
            };
            return cell;
        });

        table.setItems(users);
    }

}
