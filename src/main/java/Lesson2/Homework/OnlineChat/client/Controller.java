package Lesson2.Homework.OnlineChat.client;

import javafx.fxml.FXML;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Controller {
    @FXML
    TextArea textArea;


    @FXML
    PasswordField passField;

    @FXML
    TextField textField, loginField, nickField;

    @FXML
    HBox bottomPanel, upperBox, enemy_msg, ally_msg;

    @FXML
    ListView<String> clientsList;

    @FXML
    CheckBox checkReg;

    @FXML
    Button buttomReg;

    @FXML
    VBox VBoxMsg;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final int PORT = 8181;

    private boolean isAuthorized;

    Font fontNick = new Font("System Bold", 12.0);
    Font fontText = new Font(14.0);
    Insets panePadding = new Insets(0, 10, 40, 10);

    String focusNick;
    String nick;

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        upperBox.setVisible(!isAuthorized);
        upperBox.setManaged(!isAuthorized);
        bottomPanel.setVisible(isAuthorized);
        bottomPanel.setManaged(isAuthorized);
        clientsList.setVisible(true);
        clientsList.setManaged(true);

    }

    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            setAuthorized(false);


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    readMsg();
                }
            });
            thread.setDaemon(true);
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        try {

            String msg = focusNick.equals("all") ? textField.getText():"/w "+focusNick+" "+textField.getText();
            out.writeUTF(msg);
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filterMsg() {
       focusNick = clientsList.getFocusModel().getFocusedItem();
        Platform.runLater(() -> {
            for (Node line : VBoxMsg.getChildren()
            ) {
                line.setVisible(focusNick.equals("all") || line.getId().contains(nick) || line.getId().contains(focusNick));
                line.setManaged(focusNick.equals("all") || line.getId().contains(nick) || line.getId().contains(focusNick));
            }
        });


    }

    public void filterMsg(Node line) {
        Platform.runLater(() -> {
                line.setVisible(focusNick.equals("all")|| line.getId().contains(nick) || line.getId().contains(focusNick));
                line.setManaged(focusNick.equals("all") || line.getId().contains(nick) || line.getId().contains(focusNick));
        });
    }

    public void sendEndSocket() {
        try {
            out.writeUTF("/end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth() {

        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passField.getText());
            loginField.clear();
            passField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void checkReg() {
        boolean StatusCheck = checkReg.isSelected();
        buttomReg.setVisible(StatusCheck);
        buttomReg.setManaged(StatusCheck);
        nickField.setVisible(StatusCheck);
        nickField.setManaged(StatusCheck);
    }

    public void Registation() {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF("/reg " + loginField.getText() + " " + passField.getText()
                    + " " + nickField.getText());
            loginField.clear();
            passField.clear();
            nickField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readMsg() {
        try {
            readNotAuthMsg();
            readAuthMsg();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readAuthMsg() throws IOException {
        // цикл читающий сообщение, в зависимости от статуса читает разное сообщение
        while (true) {
            String str = in.readUTF();
            if (str.equals("/serverclosed")) {
                break;
            }
            if (str.startsWith("/clientslist ")) {
                String[] tokens = str.split(" ");
                Platform.runLater(() -> {
                    String oldFocus = focusNick;
                    FocusModel<String> fmClient = clientsList.getFocusModel();
                    boolean focusFind = false;
                    clientsList.getItems().clear();
                    for (int i = 1; i < tokens.length; i++) {
                        clientsList.getItems().add(tokens[i]);
                        if (tokens[i].equals(oldFocus)) {
                            fmClient.focus(i-1);
                            focusFind = true;
                        };
                    }
                    if (!focusFind){
                        fmClient.focus(0);
                        filterMsg();
                    }
                });
            } else {
                addLineMessage(str);
            }
        }
    }

    public void addLineMessage(String mgs) {
        Platform.runLater(() -> {
            HBox hBox = getLineMsg(mgs);
            VBoxMsg.getChildren().add(hBox);
        });
    }

    public void readNotAuthMsg() throws IOException {
        while (true) {
            String str = in.readUTF();
            if (!isAuthorized) {
                if (str.startsWith("/authok")) {
                    setAuthorized(true);
                    this.nick = str.replace("/authok ", "");
                    addLineMessage("Сервер: Авторизация успешна");
                    break;
                } else {
                    Platform.runLater(() -> {
                        VBoxMsg.getChildren().clear();
                    });
                    if (str.equals("/serverclosed")) {
                        break;
                    }
                    addLineMessage(str);
                }
            }
        }
    }


    public HBox getLineMsg(String msg) {

        String[] token = msg.split(": ");

        Label labelNick = new Label();
        labelNick.setLayoutX(10);
        labelNick.setMaxWidth(300);
        labelNick.setPrefHeight(30);
        labelNick.setText(token.length == 1 ? "" : token[0]);
        labelNick.setFont(fontNick);
        labelNick.setMaxWidth(300);

        Label labelText = new Label();
        labelText.setLayoutX(10);
        labelText.setLayoutY(30);
        labelText.setMaxWidth(300);
        labelText.setText(token.length == 1 ? token[0] : token[1]);
        labelText.setFont(fontText);
        labelText.setWrapText(true);

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: #FFFFFF;");
        vBox.getChildren().add(labelNick);
        vBox.getChildren().add(labelText);
        vBox.setPadding(panePadding);

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #FFFFFF;");
        pane.getChildren().add(vBox);


        HBox hBox = new HBox();
        hBox.setId("message_" + token[0]);
        hBox.setAlignment((token.length == 1 || token[0].equals(this.nick)) ? Pos.TOP_RIGHT : Pos.TOP_LEFT);
        hBox.getChildren().add(pane);
        filterMsg(hBox);
        return hBox;
    }
}