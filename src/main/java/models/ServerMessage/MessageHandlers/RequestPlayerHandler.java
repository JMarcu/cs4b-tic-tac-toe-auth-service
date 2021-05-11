package models.ServerMessage.MessageHandlers;

import interfaces.Sender;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;

public class RequestPlayerHandler implements Runnable{

    private String playerId;
    private Sender sender;

    public RequestPlayerHandler(String playerId, Sender sender){
        this.playerId = playerId;
        this.sender = sender;
    }

    @Override
    public void run() {
        sender.send(new Message(getPlayer(playerId), MessageType.PLAYER_PROPERTIES));
    }    
}
