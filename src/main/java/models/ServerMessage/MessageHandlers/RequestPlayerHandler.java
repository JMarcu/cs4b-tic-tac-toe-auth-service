package models.ServerMessage.MessageHandlers;

import java.io.IOException;
import java.util.UUID;
import interfaces.PlayerDatabaseInterface;
import interfaces.Sender;
import models.Player;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;

public class RequestPlayerHandler implements Runnable{

    private UUID playerId;
    private Sender sender;

    public RequestPlayerHandler(UUID playerId, Sender sender){
        this.playerId = playerId;
        this.sender = sender;
    }

    @Override
    public void run() {
        try {
            Player player = PlayerDatabaseInterface.getInstance().getPlayer(playerId);

            System.out.println();
            System.out.println("Player name: " + player.getName());
            System.out.println("Player id: " + player.getUuid());
            System.out.println();
            
            sender.send(new Message(player, MessageType.REQUESTED_PLAYER));
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }    
}
