package models.ServerMessage.MessageHandlers;

import interfaces.Sender;
import models.Player;
import models.ServerMessage.MessageType;
import services.JWTService;

public class LoginHandler implements Runnable{

    private String userName;
    private Sender sender;

    public LoginHandler(String userName, Sender sender){
        this.userName = userName;
        this.sender = sender;
    }

    @Override
    public void run() {
        String password = getPassword(userName);

        if(password != null){
           Player player = getPlayer(userName);

           String token = JWTService.create();

           setRefreshToken(player.getUuid(), token);

           sender.send(new Message(true, MessageType.LOGIN_SUCCESS));
        }
        else{
            sender.send(new Message(true, MessageType.LOGIN_FAIL));
        }

        // TODO Auto-generated method stub
        
    }
}
