package models.ServerMessage.MessageHandlers;

import java.io.IOException;
import java.util.UUID;

import interfaces.Sender;
import models.Player;
import models.PlayerDatabaseInterface;
import models.ServerMessage.LoginSuccessMessageBody;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import services.JWTService;

public class LoginHandler implements Runnable{

    private String userName;
    private String password;
    private Sender sender;

    public LoginHandler(String userName, String password, Sender sender){
        this.userName = userName;
        this.password = password;
        this.sender = sender;
    }

    @Override
    public void run() {

        boolean isPasswordValid = PlayerDatabaseInterface.validatePassword(userName, password);

        if(isPasswordValid){
           Player player = PlayerDatabaseInterface.getPlayer(UUID.randomUUID());

           String jwt = JWTService.create();
           String refreshToken = JWTService.create();
           
           //player.getUuid()
          PlayerDatabaseInterface.setRefreshToken(userName, refreshToken);

        //    try {
        //        sender.send(new Message(new LoginSuccessMessageBody(jwt, refreshToken), MessageType.LOGIN_SUCCESS));
        //     } 
        //     catch (IOException e) {
        //         e.printStackTrace();
        //     }
        }
        else{
            // try {
            //     sender.send(new Message(null, MessageType.LOGIN_FAIL));
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
        }        
    }
}
