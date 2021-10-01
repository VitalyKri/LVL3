package Lesson2.Homework.OnlineChat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MainServ {
    private Map<String, ClientHandler> clients;

    public MainServ() {
        clients = new HashMap<>();
        ServerSocket server = null;
        Socket socket = null;

        addScanner();

        try {

            server = new ServerSocket(8181);
            System.out.println("Сервер запущен!");
            while (true) {
                socket = server.accept();
                new ClientHandler(this, socket);
                System.out.println("Клиент подключился!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void addScanner() {
        Scanner scanner = new Scanner(System.in);
        final String[] msg = new String[1];
        Thread thread = new Thread(() -> {
            while (true) {
                msg[0] = scanner.nextLine();
                this.sendMsg(msg[0]);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void subscribe(ClientHandler client) {
        clients.put(client.getNick(), client);
        broadcastClientsList();
    }

    public boolean isNickBusy(String nick) {
        return clients.get(nick) != null;
    }

    public void upsubscribe(ClientHandler client) {
        clients.remove(client.getNick());
        broadcastClientsList();
    }

    public void broadcastMsg(String msg) {
        Iterator<String> iterator = clients.keySet().iterator();
        while (iterator.hasNext()) {
            clients.get(iterator.next()).sendMsg(msg);
        }
    }

    /*public void sendMsg(String msg) {
        try {
            if (msg.startsWith("/w")){
                String[] token = msg.split(" ");
                ClientHandler client = clients.get(token[2]);
                msg = msg.replace("/w "+token[1]+" "+token[2],"/w "+token[1]+" :");
                client.sendMsg(msg);
            } else {
                Iterator<String> iterator = clients.keySet().iterator();
                if (iterator.hasNext()){
                    clients.get(iterator.next()).sendMsg(msg);
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public void sendMsg(String msg) {
        try {

            Iterator<String> iterator = clients.keySet().iterator();
            if (iterator.hasNext()) {
                clients.get(iterator.next()).sendMsg(msg);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPersonalMsg(ClientHandler from, String toNick, String msg) {
        try {
            ClientHandler client = clients.get(toNick);
            client.sendMsg(from.getNick() + ": " + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void broadcastClientsList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientslist all ");
        Set<String> keys = clients.keySet();
        for (String key : keys
        ) {
            sb.append(clients.get(key).getNick() + " ");
        }
        String out = sb.toString();
        for (String key : keys
        ) {
            clients.get(key).sendMsg(out);
        }
    }
}


