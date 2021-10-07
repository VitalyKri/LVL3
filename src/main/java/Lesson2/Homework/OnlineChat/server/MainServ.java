package Lesson2.Homework.OnlineChat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MainServ {
    private PoolClientHandler<String,ClientHandler> poolClientHandler;

    public MainServ() {
        poolClientHandler = new PoolClientHandler();
        ServerSocket server = null;
        Socket socket = null;
        addScanner();

        try {
            server = new ServerSocket(8181);
            System.out.println("Сервер запущен!");
            while (true) {
                socket = server.accept();
                poolClientHandler.addThread(new ClientHandler(this, socket));
                ;
                System.out.println("Клиент подключился!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                poolClientHandler.getService().shutdown();
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
        poolClientHandler.addClient(client.getNick(), client);
        broadcastClientsList();
    }

    public boolean isNickBusy(String nick) {
        return poolClientHandler.getClient(nick) != null;
    }

    public void upsubscribe(String nick) {
        poolClientHandler.deleteClient(nick);
        broadcastClientsList();
    }

    public void broadcastMsg(String msg) {
        Iterator<String> iterator = poolClientHandler.getIterator();
        while (iterator.hasNext()) {
            poolClientHandler.getClient(iterator.next()).sendMsg(msg);
        }
    }

    public void sendMsg(String msg) {
        try {
            Iterator<String> iterator = poolClientHandler.getIterator();
            if (iterator.hasNext()) {
                poolClientHandler.getClient(iterator.next()).sendMsg(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPersonalMsg(ClientHandler from, String toNick, String msg) {
        try {
            ClientHandler client = poolClientHandler.getClient(toNick);
            client.sendMsg(from.getNick() + ": " + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void broadcastClientsList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientslist all ");
        Set<String> nicks = poolClientHandler.getClients().keySet();
        for (String nick : nicks
        ) {
            sb.append(nick + " ");
        }
        String out = sb.toString();
        for (String nick : nicks
        ) {
            poolClientHandler.getClient(nick).sendMsg(out);
        }
    }
}


