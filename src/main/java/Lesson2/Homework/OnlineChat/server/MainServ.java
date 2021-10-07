package Lesson2.Homework.OnlineChat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServ {
    private Map<String, ClientHandler> clients;

    ExecutorService service;
    public MainServ() {
        clients = new HashMap<>();
        ServerSocket server = null;
        Socket socket = null;
        service = Executors.newCachedThreadPool();
        addScanner();

        try {

            server = new ServerSocket(8181);
            System.out.println("Сервер запущен!");
            while (true) {
                socket = server.accept();
                addThread(new ClientHandler(this, socket));
                System.out.println("Клиент подключился!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                service.shutdown();
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

    public void addThread(ClientHandler clientHandler){
        service.execute(()->{
            try {
                clientHandler.readMessages();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clientHandler.closeConnect();
            }
            this.upsubscribe(clientHandler);
        });
    }
}


