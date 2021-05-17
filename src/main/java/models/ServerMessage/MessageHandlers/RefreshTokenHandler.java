package models.ServerMessage.MessageHandlers;

import java.io.IOException;
import java.util.UUID;

import interfaces.Sender;
import models.PlayerDatabaseInterface;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import services.JWTService;

public class RefreshTokenHandler implements Runnable{
    
    private String userName;
    private String jwt;
    private Sender sender;
        
    public RefreshTokenHandler(String userName, String jwt, Sender sender){
        this.userName = userName;
        this.jwt = jwt;
        this.sender = sender;
    }

    @Override
    public void run() {
        
        String reFreshToken = PlayerDatabaseInterface.getRefreshToken(userName);
        
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
