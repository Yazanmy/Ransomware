/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import static javafxapplication3.RSAUtil.privateKey;

public class Decryption extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {

        Button btn = new Button();
        Label label = new Label();
        TextField textArea1 = new TextField("Insert Encrypted Key Here");
        label.setText("Decryption For The Key");
        label.setFont(new Font(30.0));

        btn.setText("Decrypt Key");

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String decryptedString = null;
                try {
                    decryptedString = RSAUtil.decrypt(textArea1.getText(), privateKey);
                } catch (IllegalBlockSizeException ex) {
                    Logger.getLogger(Decryption.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(Decryption.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadPaddingException ex) {
                    Logger.getLogger(Decryption.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Decryption.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(Decryption.class.getName()).log(Level.SEVERE, null, ex);
                }

                TextArea textArea = new TextArea(decryptedString);
                textArea.setEditable(false);
                textArea.setWrapText(true);
                GridPane gridPane = new GridPane();
                gridPane.setMaxWidth(Double.MAX_VALUE);
                gridPane.add(textArea, 0, 0);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Look, Decryption Key :");
                alert.getDialogPane().setContent(gridPane);
                alert.showAndWait();

            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        root.getChildren().add(textArea1);
        root.getChildren().add(label);
        Scene scene = new Scene(root, 400, 400);
        label.setTranslateY(-100);
        textArea1.setTranslateY(-50);
        btn.setTranslateY(30);
        primaryStage.setTitle("Decryption");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }

}
