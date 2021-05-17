package models.ServerMessage.MessageHandlers;

import java.io.IOException;
import java.util.UUID;

import interfaces.Sender;
import models.PlayerDatabaseInterface;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;

public class RequestPlayerHandler implements Runnable{

    private UUID playerId;
    private String userName;
    private Sender sender;

    public RequestPlayerHandler(UUID playerId ,String userName, Sender sender){
        this.playerId = playerId;
        this.userName = userName;
        this.sender = sender;
    }

    @Override
    public void run() {
        // try {
        //     sender.send(new Message(PlayerDatabaseInterface.getPlayer(playerId), MessageType.PLAYER_PROPERTIES));
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

        PlayerDatabaseInterface.getPlayer(playerId);
    }    
}
