/*
 *
 */
package calcfx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;

/**
 * コントロールクラス
 */
public class CalcController implements Initializable {

    @FXML private TextField inputView;
    private String inputBefore;
    private boolean reset;
    private BiFunction<String, String, String> calc;
    /**
     * 初期化処理
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.inputView.setText("0");
        this.inputBefore = null;
        this.calc = null;
        this.reset = true;
    }

    /**
     * クリアボタンのアクション
     * @param event イベント
     */
    @FXML public void onClearBtnAction(ActionEvent event) {
        initialize(null, null);
    }
    /**
     * 数字ボタンのアクション
     * @param event イベント
     */
    @FXML public void onNumberBtnAction(ActionEvent event) {
        String text = this.reset ? "0" : this.inputView.getText();
        if (!validMaxLength(text)) {
            return;
        }
        String input = ((Labeled)event.getSource()).getText();
        if (".".equals(input)) {
            if (text.indexOf(".") < 0) {
                this.inputView.setText(text + input);
            }
        } else if ("0".equals(text)) {
            if (input.indexOf("0") < 0) {
                this.inputView.setText(input);
            }
        } else {
            this.inputView.setText(text + input);
        }
        this.reset = false;
    }
    private static int MAX_INPUT_LENGTH = 15;
    private boolean validMaxLength(String text) {
        if (text.indexOf(".") < 0) {
            return text.length() < MAX_INPUT_LENGTH;
        } else {
            return text.length() <= MAX_INPUT_LENGTH;
        }
    }

    /**
     * 「/」ボタンのアクション
     * @param event イベント
     */
    @FXML public void onDivisionBtnAction(ActionEvent event) {
        if (!reset) {
            this.doCalc();
        }
        this.calc = (before, input) -> {
            if (input.matches("[0¥¥.]+")) {
                return "0";
            }
            return new BigDecimal(before).divide(new BigDecimal(input), MAX_INPUT_LENGTH, RoundingMode.HALF_UP).toPlainString();
        };
    }
    /**
     * 「*」ボタンのアクション
     * @param event イベント
     */
    @FXML public void onMultiplicationBtnAction(ActionEvent event) {
        if (!reset) {
            this.doCalc();
        }
        this.calc = (before, input) -> {
            return new BigDecimal(before).multiply(new BigDecimal(input)).toPlainString();
        };
    }
    /**
     * 「-」ボタンのアクション
     * @param event イベント
     */
    @FXML public void onMinusBtnAction(ActionEvent event) {
        if (!reset) {
            this.doCalc();
        }
        this.calc = (before, input) -> {
            return new BigDecimal(before).subtract(new BigDecimal(input)).toPlainString();
        };
    }
    /**
     * 「+」ボタンのアクション
     * @param event イベント
     */
    @FXML public void onPlusBtnAction(ActionEvent event) {
        if (!reset) {
            this.doCalc();
        }
        this.calc = (before, input) -> {
            return new BigDecimal(before).add(new BigDecimal(input)).toPlainString();
        };
    }
    /**
     * 「=」ボタンのアクション
     * @param event イベント
     */
    @FXML public void onEqualBtnAction(ActionEvent event) {
        this.doCalc();
        this.inputBefore = null;
        this.calc = null;
    }

    private void doCalc() {
        if (this.inputBefore == null) {
            this.inputBefore = this.inputView.getText();
        } else {
            this.inputBefore = this.calc.apply(this.inputBefore, this.inputView.getText());
        }
        this.inputView.setText(this.inputBefore);
        this.reset = true;
    }
}
