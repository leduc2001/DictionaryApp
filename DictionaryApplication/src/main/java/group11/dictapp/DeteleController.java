package group11.dictapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static group11.dictapp.DictionaryApplication.dict;
import static group11.dictapp.HomeController.wordList;

public class DeteleController implements Initializable{
    @FXML
    private TextField tfWordTarget;

    @FXML
    private TextArea taWordExplain;

    @FXML
    private Button btDelete;

    @FXML
    private Button btCancel;

    public void setTfWordTarget(String text) {
        tfWordTarget.setText(text);
    }

    public void setTaWordExplain(String text) {
        taWordExplain.setText(text);
    }

    public void btCancelOnClick(ActionEvent event) {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfWordTarget.setOnKeyTyped(event -> {
            String input = tfWordTarget.getText();
            if (dict.dictionaryLookup(input) != null) {
                taWordExplain.setText(dict.dictionaryLookup(input));
            } else {
                taWordExplain.setText("Không tìm thấy");
            }
        });

        btDelete.setOnMouseClicked(mouseEvent -> {
            String text = tfWordTarget.getText();

            if (!text.isEmpty()) {
                int index = dict.checkDuplicate(text);
                if(index != -1) {
                    wordList.remove(index);
                    dict.dictionaryDelete(text);
                    tfWordTarget.clear();
                    taWordExplain.clear();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Xoá thành công");
                    alert.setHeaderText("Đã xoá từ '" + text + "'");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Không tìm thấy từ cần xoá");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Không được để trống ô 'Từ'!");
                alert.show();
            }
        });
    }
}
