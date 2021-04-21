package models.ServerMessage.MessageHandlers;

import interfaces.Sender;
import models.ServerMessage.AuthenticationRequestMessageBody;

public class AuthenticationRequestHandler implements Runnable{

    private Sender sender;
    private AuthenticationRequestMessageBody msg;

    public AuthenticationRequestHandler(AuthenticationRequestMessageBody msg, Sender sender){
        this.msg = msg;
        this.sender = sender;
    }
    
    public void run(){
        
        //Validate JWt 

        //sender.send(new Message);
    }
}