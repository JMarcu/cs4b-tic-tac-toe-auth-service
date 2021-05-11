package models.ServerMessage.MessageHandlers;

import interfaces.Sender;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import services.JWTService;

public class LogoutHandler implements Runnable{
    
    private String playerId;
    private String token;
    private Sender sender;
    
    public LogoutHandler(String playerId, String token, Sender sender){
        this.playerId = playerId;
        this.token = token;
        this.sender = sender;
    }

    @Override
    public void run() {
        String refreshToken = getRefreshToken(playerId);
        
        if(JWTService.validate(playerId)){
            deleteRefreshToken(playerId, refreshToken);
            sender.send(new Message(null, MessageType.LOGOUT_SUCCESS));
        }
        else{
                
        }
    }
}
