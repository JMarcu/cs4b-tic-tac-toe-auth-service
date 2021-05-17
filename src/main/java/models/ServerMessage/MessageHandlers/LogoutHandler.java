package models.ServerMessage.MessageHandlers;

import java.io.IOException;

import interfaces.PlayerDatabaseInterface;
import interfaces.Sender;
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
        String tokenFromDB = PlayerDatabaseInterface.getInstance().getRefreshToken(userName);
        
        if(tokenFromDB.equals(token)){
            PlayerDatabaseInterface.getInstance().deleteRefreshToken(userName);
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
