package Lesson2.Homework.OnlineChat.client;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.*;

public class LocalChat /*implements SavebleLocalFile */{

    @FXML
    private VBox VBoxMsg;
    private String nick;
    private String focusNick;
    public LocalChat(String nick,String focusNick,VBox VBoxMsg) {
        this.VBoxMsg = VBoxMsg;
        this.nick = nick;
        this.focusNick = focusNick;
    }

    public String getFocusNick() {
        return focusNick;
    }

    public void setFocusNick(String focusNick) {
        this.focusNick = focusNick;
        for (Node line : VBoxMsg.getChildren()
        ) {
            line.setVisible(focusNick.equals("all") || line.getId().contains(nick) || line.getId().contains(focusNick));
            line.setManaged(focusNick.equals("all") || line.getId().contains(nick) || line.getId().contains(focusNick));
        }
    }

    public ObservableList<Node> getLines() {
        return VBoxMsg.getChildren();
    }

    public void filterMsgAll() {
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

    public void addLineMessage(String mgs) {
        Platform.runLater(() -> {
            HBox hBox = getLineMsg(mgs);
            VBoxMsg.getChildren().add(hBox);
        });
    }

    public HBox getLineMsg(String msg) {
        Font fontNick = new Font("System Bold", 12.0);
        Font fontText = new Font(14.0);
        Insets panePadding = new Insets(0, 10, 40, 10);

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


        HBoxWithComment hBox = new HBoxWithComment();
        hBox.setId("message_" + token[0]);
        hBox.setAlignment((token.length == 1 || token[0].equals(this.nick)) ? Pos.TOP_RIGHT : Pos.TOP_LEFT);
        hBox.getChildren().add(pane);
        filterMsg(hBox);
        hBox.setComment(msg);
        return hBox;
    }

//    @Override
//    public BufferedOutputStream writeToStream() {
//
//        try (BufferedOutputStream bos = new BufferedOutputStream
//                (new FileOutputStream("history_"+nick+".txt"))){
//             int sizeChat = VBoxMsg.getChildren().size();
//            for (int i = 0; i < 100; i++) {
//                String msg = ((HBoxWithComment) VBoxMsg.getChildren().get(sizeChat-i)).getComment();
//            }
//
//        }catch (IOException e){e.printStackTrace();}
//
//
//        return null;
//    }
//
//    @Override
//    public void readingTheStream(BufferedInputStream bis) {
//
//    }
}
