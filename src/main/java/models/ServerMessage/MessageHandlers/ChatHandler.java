package models.ServerMessage.MessageHandlers;

import interfaces.Sender;

public class ChatHandler {
    private Sender sender;
    //private ChatMessageBody msg;

    public ChatHandler(/*ChatMessageBody msg,*/ Sender sender){
        //this.msg = msg;
        this.sender = sender;
    }
    
    public void run(){
        
        //Validate JWt 

        //sender.send(new Message);
    }
}
