package Lesson2.Homework.OnlineChat.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolClientHandler<K, T extends ClientHandler> {
    private Map<K, T> clients;
    private ExecutorService service;

    public PoolClientHandler() {
        clients = new HashMap<>();
        service = Executors.newCachedThreadPool();
    }

    public ExecutorService getService() {
        return service;
    }

    public Map<K, T> getClients() {
        return clients;
    }

    public void addClient(K nick, T clientHandler) {
        clients.put(nick, clientHandler);
    }
    public void deleteClient(K nick) {
        clients.remove(nick);
    }

    public T getClient(K nick) {
        return clients.get(nick);
    }

    public Iterator<K> getIterator() {
        return this.clients.keySet().iterator();
    }

    public void addThread(T clientHandler) {
        service.submit(()->{
            try {
                clientHandler.readMessages();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clientHandler.closeConnect();
            }
        });

    }

}
