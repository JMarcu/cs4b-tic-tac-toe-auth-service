package models.ServerMessage.MessageHandlers;

import java.io.IOException;

import interfaces.Sender;
import models.ServerMessage.AuthenticationRequestMessageBody;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import services.JWTService;

/** 
 * This class handles validation of incoming JSON Web Tokens (JWTs) by calling the validate method
 * from the JWTService class. The class then sends the result of the validation through the sender object
 * and with a new message type of AUTHENTICATION_RESULT.
 */
public class AuthenticationRequestHandler implements Runnable{

    private Sender sender;
    private AuthenticationRequestMessageBody msg;

   /**
    * 
    * @param msg The message that carries the JWT to be validated 
    * @param sender The sender of this message.
    */
    public AuthenticationRequestHandler(AuthenticationRequestMessageBody msg, Sender sender){
        this.msg = msg;
        this.sender = sender;
    }
    

   /**
    *
    */
    public void run(){
        try {
            sender.send(new Message(JWTService.validate(msg.getToken()),MessageType.AUTHENTICATION_RESULT));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}