/*
 *
 */
package calcfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * アプリケーションメイン
 */
public class CalcFxApp extends Application {


    /**
     * アプリケーションをスタートさせる。
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage stage) throws Exception {
        // レイアウトの読み込み
        Parent root = FXMLLoader.load(getClass().getResource("/calcfx.fxml"));
        // Senceの作成
        Scene scene = new Scene(root);
        // Senceの設定
        stage.setScene(scene);
        // 画面の表示
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
