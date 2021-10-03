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

    LocalChat localChat;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final int PORT = 8181;

    private boolean isAuthorized;

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
            String focusNick = localChat.getFocusNick();
            String msg = focusNick.equals("all") ? textField.getText():"/w "+focusNick+" "+textField.getText();
            out.writeUTF(msg);
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendEndSocket() {
        try {
            out.writeUTF("/end");
            if (localChat != null){
                localChat.writeToStream();
            }
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
    public void filterMsg() {
       localChat.setFocusNick(clientsList.getFocusModel().getFocusedItem());
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
                    String oldFocus = localChat.getFocusNick();
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
                        localChat.filterMsgAll();
                    }
                });
            } else {
                localChat.addLineMessage(str);
            }
        }
    }

    public void readNotAuthMsg() throws IOException {
        while (true) {
            String str = in.readUTF();
            if (!isAuthorized) {
                if (str.startsWith("/authok")) {
                    setAuthorized(true);
                    this.nick = str.replace("/authok ", "");
                    localChat = new LocalChat(this.nick,"all",VBoxMsg);
                    localChat.addLineMessage("Сервер: Авторизация успешна");
                    break;
                }
            }
        }
    }
}