package models.ServerMessage.MessageHandlers;

import java.util.UUID;
import interfaces.PlayerDatabaseInterface;
import interfaces.Sender;

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

        PlayerDatabaseInterface.getInstance().getPlayer(playerId);
    }    
}
