package models;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import models.ServerMessage.Message;

public class ClientHandler implements Runnable{
    private Client client;
    private HashMap<UUID, Client> clientMap;
    private LinkedBlockingQueue<Message> msgQueue;
    
    public ClientHandler(Client client, HashMap<UUID, Client> clientMap){
        this.client = client;
        this.clientMap = clientMap;
        this.msgQueue = new LinkedBlockingQueue<Message>();
    }

    @Override
    public void run(){ }
}
