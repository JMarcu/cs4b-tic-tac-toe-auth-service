package models.ServerMessage.MessageHandlers;

import interfaces.Sender;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import services.JWTService;

public class RefreshTokenHandler implements Runnable{
    
    private String playerId;
    private String jwt;
    private Sender sender;
        
    public RefreshTokenHandler(String playerId, String token, Sender sender){
        this.playerId = playerId;
        this.jwt = jwt;
        this.sender = sender;
    }

    @Override
    public void run() {
        
        String token = getRefreshToken(playerId);
        sender.send(new Message(JWTService.validate(token), MessageType.REFRESH_RESULT));
    }
    
}
