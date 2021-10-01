package Lesson2.Homework.OnlineChat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ClientHandler implements Comparable {
    private MainServ serv;
    private Socket socket;
    DataInputStream in;
    DataOutputStream out;
    private String nick;
    private boolean isAuth;
    List<String> blacklist;
    private String login;
    public ClientHandler(MainServ serv, Socket socket) {
        try {
            this.blacklist = new ArrayList<>();
            this.serv = serv;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        readMessages();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        closeConnect();
                    }
                    serv.upsubscribe(ClientHandler.this);

                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void readMessages() throws IOException {
        while (true) {
            String str = in.readUTF();
            System.out.println("Client " + str);
            // если не авторизовался запускаем ветку, авторизация, регистрация, иначе можем отправлять сообщения
            if (!this.isAuth){
                if (str.startsWith("/auth")) {
                    authentication(str);
                }
            } else {
                if (str.equals("/end")) {
                    out.writeUTF("/serverclosed");
                    break;
                } else if (str.startsWith("/w")) {
                    serv.sendMsg(str.replace("/w","/w "+this.nick));
                    sendMsg(str);
                } else {
                    serv.broadcastMsg(nick + ": " + str);
                }

            }

        }
    }*/

    public boolean nickInBlackList(String nick ){
        return blacklist.contains(nick);
    }

    public void readMessages() throws IOException {
        while (true) {
            String str = in.readUTF();
            System.out.println("Client " + str);
            // если не авторизовался запускаем ветку, авторизация, регистрация, иначе можем отправлять сообщения
            if (!this.isAuth){
                if (str.startsWith("/auth")) {
                    authentication(str);
                }

                if (str.startsWith("/reg")) {
                    String[] token = str.split(" ", 4);
                    //AuthService.addUser(token[1],token[2],token[3]);
                    //sendMsg("Сервер: Регистрация пользователя ("+token[1]+") успешна");
                }

            }
            else {
                if (str.equals("/end")) {
                    sendMsg("/serverclosed");
                    break;
                } else if (str.startsWith("/w")) {

                    String[] token = str.split(" ", 3);
                    if (token[1].equals(token[2])){
                        serv.sendPersonalMsg(this,token[1],token[2]);
                    }
                    sendMsg(nick + ": Адресовано ("+token[1]+")."+token[2]);

                } else if (str.startsWith("/addBL")) {

                    String[] token = str.split(" ", 2);
                    String mgs = AuthService.addUsersInBL(this.getNick(),token[1]);
                    sendMsg("Сервер: "+mgs);

                }  else if (str.startsWith("/newnick")) {

                    String[] token = str.split(" ", 2);
                    //String mgs = AuthService.updateNick(this.login,token[1]);
                    //sendMsg("Сервер: "+mgs);

                }
                else if (str.startsWith("/subBL")) {
                    String[] token = str.split(" ", 2);
                    String mgs = AuthService.removeUsersInBL(this.getNick(),token[1]);
                    sendMsg("Сервер: "+mgs);
                }
                else {
                    serv.broadcastMsg(nick + ": " + str);
                }

            }

        }
    }

    public void authentication(String msg) throws IOException {

//        String[] token = msg.split(" ");
//        try {
//            String currentNick = AuthService.getNickByLoginAndPass(token[1], token[2]);
//            if (!serv.isNickBusy(currentNick)) {
//                this.nick = currentNick;
//                this.login = token[1];
//                this.isAuth = true;
//                sendMsg("/authok "+this.nick);
//                serv.subscribe(this);
//                serv.broadcastMsg("Сервер: пользователь (" + currentNick + ") в чате");
//            } else {
//                sendMsg("Под этим ником уже авторизовались");
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            sendMsg("Неверный логин/пароль");
//        }


    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }

    @Override
    public int compareTo(Object o) {
        ClientHandler another = (ClientHandler) o;
        if (this.nick.hashCode() > another.getNick().hashCode()) {
            return 1;
        }
        if (this.nick.hashCode() < another.getNick().hashCode()) {
            return -1;
        }
        return 0;
    }

    public void closeConnect() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
