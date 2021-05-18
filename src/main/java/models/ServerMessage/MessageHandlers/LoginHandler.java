package models.ServerMessage.MessageHandlers;

import java.io.IOException;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import interfaces.PlayerDatabaseInterface;
import interfaces.Sender;
import models.Player;
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
        boolean isPasswordValid = PlayerDatabaseInterface.getInstance().validatePassword(userName, password);

        if(isPasswordValid){
            Player player = PlayerDatabaseInterface.getInstance().getPlayer(userName);

            String jwt = JWTService.create(player);
            UUID refreshToken = UUID.randomUUID();
           
            PlayerDatabaseInterface.getInstance().setRefreshToken(player.getUuid(), refreshToken.toString());

            LoginSuccessMessageBody body = new LoginSuccessMessageBody(jwt, refreshToken.toString(), player);
            Message msg = new Message(body, MessageType.LOGIN_SUCCESS);
            try {
                sender.send(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            try {
                sender.send(new Message(null, MessageType.LOGIN_FAIL));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }        
    }
}
