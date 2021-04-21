package models.ServerMessage.MessageHandlers;

import interfaces.Sender;

public class AuthenticationAcknowlegedHandler {

    private Sender sender;
   // private AuthenticationAcknowlegedMessageBody msg;

    public AuthenticationAcknowlegedHandler(/*AuthenticationAcknowlegedMessageBody msg,*/ Sender sender){
     //   this.msg = msg;
        this.sender = sender;
    }
    
    public void run(){
        

        //sender.send(new Message);
    }
    
}
