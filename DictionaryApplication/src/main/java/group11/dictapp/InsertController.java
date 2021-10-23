package group11.dictapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static group11.dictapp.DictionaryApplication.dict;
import static group11.dictapp.HomeController.wordList;

public class InsertController implements Initializable{
    @FXML
    private TextField tfWordTarget;

    @FXML
    private TextField tfWordExplain;

    @FXML
    private Button btInsert;

    @FXML
    private Button btCancel;

    public void setTfWordTarget(String text) {
        tfWordTarget.setText(text);
    }

    public void btCancelOnClick(ActionEvent event) {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfWordTarget.setOnKeyTyped(event -> {
            String input = tfWordTarget.getText();
            tfWordExplain.setText(dict.dictionaryLookup(input));
        });

        btInsert.setOnMouseClicked(mouseEvent -> {
            String targ = tfWordTarget.getText();
            String expl = tfWordExplain.getText();

            if (!targ.isEmpty() && expl != null) {
                int index = dict.checkDuplicate(targ);
                if(index == -1) { //Thêm từ lên table
                    wordList.add(new Word(targ,expl));
                    dict.dictionaryInsert(targ,expl);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thêm thành công");
                    alert.setHeaderText("Đã thêm từ '" + targ + "'");
                    alert.show();

                    tfWordTarget.clear();
                    tfWordExplain.clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Từ đã tồn tại");
                    alert.setHeaderText("Từ '" + targ + "' đã có trong từ điển.");
                    alert.setContentText("Nếu vẫn thêm sẽ thay thế nghĩa cũ.");

                    ButtonType add = new ButtonType("Vẫn thêm!", ButtonBar.ButtonData.YES);
                    ButtonType cancel = new ButtonType("Quay lại", ButtonBar.ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(add,cancel);
                    Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() == add) {
                        wordList.get(index).setWord_explain(expl);

                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Thêm thành công");
                        alert1.setHeaderText("Đã thêm từ '" + targ + "'");
                        alert1.show();
                    }
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
