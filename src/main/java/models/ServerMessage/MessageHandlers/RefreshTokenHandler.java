package models.ServerMessage.MessageHandlers;

import java.io.IOException;
import java.util.UUID;

import interfaces.Sender;
import models.PlayerDatabaseInterface;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import services.JWTService;

public class RefreshTokenHandler implements Runnable{
    
    private UUID playerId;
    private String jwt;
    private Sender sender;
        
    public RefreshTokenHandler(UUID playerId, String jwt, Sender sender){
        this.playerId = playerId;
        this.jwt = jwt;
        this.sender = sender;
    }

    @Override
    public void run() {
        
        String reFreshToken = PlayerDatabaseInterface.getRefreshToken(playerId);
        
        reFreshToken =jwt;

        if(JWTService.validate(reFreshToken)){
            // try {
            //     sender.send(new Message(JWTService.validate(reFreshToken), MessageType.REFRESH_SUCCESS));
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }

            System.out.println("It worked REFRESH_SUCCESS");
        }
        else{
            // try {
            //     sender.send(new Message(JWTService.validate(jwt), MessageType.REFRESH_FAIL));
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
            System.out.println("It worked REFRESH_FAIL");
        }
    }
    
}
