package models.ServerMessage.MessageHandlers;

import java.io.IOException;

import interfaces.Sender;
import models.PlayerDatabaseInterface;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import services.JWTService;

public class LogoutHandler implements Runnable{
    
    private String userName;
    private String token;
    private Sender sender;
    
    public LogoutHandler(String userName, String token, Sender sender){
        this.userName = userName;
        this.token = token;
        this.sender = sender;
    }

    @Override
    public void run() {
        String refreshToken = PlayerDatabaseInterface.getRefreshToken(userName);
        
        if(JWTService.validate(token)){
            PlayerDatabaseInterface.deleteRefreshToken(userName);
            // try {
            //     sender.send(new Message(null, MessageType.LOGOUT_SUCCESS));
            // } catch (IOException e) {
            //     // TODO Auto-generated catch block
            //     e.printStackTrace();
            // }

            System.out.println("It worked true");
        }
        else{
            // try {
            //     sender.send(new Message(null, MessageType.LOGOUT_FAIL));
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }

            System.out.println("It worked false");
        }
    }
}
