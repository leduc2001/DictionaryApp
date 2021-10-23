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

public class ModifyController implements Initializable {
    @FXML
    private TextField tfWordTarget;

    @FXML
    private TextArea taWordExplain;

    @FXML
    private Button btModify;

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
            taWordExplain.setText(dict.dictionaryLookup(input));
        });

        btModify.setOnMouseClicked(mouseEvent -> {
            String targ = tfWordTarget.getText();
            String expl = taWordExplain.getText();

            if (!targ.isEmpty() && expl != null) {
                int index = dict.checkDuplicate(targ);
                if (index != -1) {
                    wordList.get(index).setWord_explain(expl);
                    dict.update(targ,expl);
                    tfWordTarget.clear();
                    taWordExplain.clear();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sửa thành công");
                    alert.setHeaderText("Đã sửa từ '" + targ + "'");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Không tìm thấy từ cần sửa!");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Cần điền đầy đủ vào các ô!");
                alert.show();
            }
        });
    }
}
