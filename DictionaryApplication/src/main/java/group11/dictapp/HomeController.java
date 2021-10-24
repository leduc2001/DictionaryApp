package group11.dictapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static group11.dictapp.DictionaryApplication.dict;

public class HomeController implements Initializable {
    @FXML
    private TextField tfInput;

    @FXML
    private TextArea taMeaning;

    @FXML
    private Button btSearchOnl;

    @FXML
    private Button btAdd;

    @FXML
    private Button btModify;

    @FXML
    private Button btDelete;

    @FXML
    private Button btSpeak;

    @FXML
    private TableView<Word> tableWord;

    @FXML
    private TableColumn<Word,String> clWordTarget;

    @FXML
    private TableColumn<Word,String> clWordMeaning;

    public static ObservableList<Word> wordList;

    public void changeInsertScene(ActionEvent event) throws IOException { //Pop-up cửa sổ thêm từ mới
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InsertPage.fxml"));
        Scene scene = new Scene(loader.load(),470,250);
        InsertController controller = loader.getController();
        if (taMeaning.getText().equals("Không tìm thấy")) {
            controller.setTfWordTarget(tfInput.getText());
        }
        stage.setTitle("Thêm từ mới");
        stage.setScene(scene);
        stage.show();
    }

    public void changeModifyScene(ActionEvent event) throws IOException { //Pop-up cửa sổ sửa từ
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPage.fxml"));
        Scene scene = new Scene(loader.load(),470,250);
        ModifyController controller = loader.getController();
        if (!taMeaning.getText().equals("Không tìm thấy")) {
            controller.setTfWordTarget(tfInput.getText());
            controller.setTaWordExplain(taMeaning.getText());
        }
        stage.setTitle("Sửa từ");
        stage.setScene(scene);
        stage.show();
        taMeaning.clear();
    }

    public void changeDeleteScene(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DeletePage.fxml"));
        Scene scene = new Scene(loader.load(),470,250);
        DeteleController controller = loader.getController();
        if (!taMeaning.getText().equals("Không tìm thấy")) {
            controller.setTfWordTarget(tfInput.getText());
            controller.setTaWordExplain(taMeaning.getText());
        }
        stage.setTitle("Xoá từ");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wordList = FXCollections.observableArrayList(dict.load());
        clWordTarget.setCellValueFactory(new PropertyValueFactory<Word,String>("word_target"));
        clWordMeaning.setCellValueFactory(new PropertyValueFactory<Word,String>("word_explain"));
        tableWord.setItems(wordList);

        tableWord.setOnMouseClicked(mouseEvent -> {
            Word selected = tableWord.getSelectionModel().getSelectedItem();
            if(selected != null) {
                taMeaning.setText(selected.getWord_target() + "\n\n" + selected.getWord_explain());
            }
        });

        FilteredList<Word> filterData = new FilteredList<>(wordList, b -> true);

        tfInput.textProperty().addListener((observableValue, oldValue, newValue) ->{
            filterData.setPredicate(word -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String res = newValue.toLowerCase();
                return word.getWord_target().startsWith(res);
            });
        });

        SortedList<Word> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tableWord.comparatorProperty());
        tableWord.setItems(sortedData);

        tfInput.setOnKeyTyped(event -> {
            String input = tfInput.getText();
            if (input.isEmpty()) {
                taMeaning.setText("Nhập từ để tra cứu");
            } else if (dict.dictionaryLookup(input) == null){
                taMeaning.setText("Không tìm thấy");
            } else {
                taMeaning.setText(input + "\n\n" + dict.dictionaryLookup(input));
            }
        });

        btSpeak.setOnMouseClicked(mouseEvent -> {
            textToSpeech speech = new textToSpeech();
            speech.speakWord(tfInput.getText());
        });
    }
}