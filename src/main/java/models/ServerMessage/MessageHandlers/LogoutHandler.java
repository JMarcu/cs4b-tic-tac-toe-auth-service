package models.ServerMessage.MessageHandlers;

import java.io.IOException;
import java.util.UUID;

import interfaces.PlayerDatabaseInterface;
import interfaces.Sender;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import services.JWTService;

public class LogoutHandler implements Runnable{
    
    private UUID playerId;
    private String token;
    private Sender sender;
    
    public LogoutHandler(UUID playerId, String token, Sender sender){
        this.playerId = playerId;
        this.token = token;
        this.sender = sender;
    }

    @Override
    public void run() {
        String tokenFromDB = PlayerDatabaseInterface.getInstance().getRefreshToken(playerId);
        
        if(tokenFromDB.equals(token)){
            PlayerDatabaseInterface.getInstance().deleteRefreshToken(playerId);
            try {
                sender.send(new Message(null, MessageType.LOGOUT_SUCCESS));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            try {
                sender.send(new Message(null, MessageType.LOGOUT_FAIL));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
