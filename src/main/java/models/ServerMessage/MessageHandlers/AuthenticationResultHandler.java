package models.ServerMessage.MessageHandlers;

import java.io.IOException;

import interfaces.Sender;
import jdk.tools.jlink.resources.plugins;
import models.Player;
import models.ServerMessage.AuthenticationResultMessageBody;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import services.JWTService;

/** 
 * 
 */
public class AuthenticationResultHandler implements Runnable{

   private boolean success;

   /**
    * 
    * @param msg The message that carries the JWT to be validated 
    * @param sender The sender of this message.
    */
    public AuthenticationResultHandler(boolean success){
        this.success = success;
    }
    

   /**
    *
    */
    public void run(){
        try {
            if(success){
                Player player = new Player();
                addPlayer(player);
                setPassword();
                setRefreshToken(player.getUuid(), );

            }
            else{

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}