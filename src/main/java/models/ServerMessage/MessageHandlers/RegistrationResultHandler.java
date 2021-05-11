package models.ServerMessage.MessageHandlers;

import interfaces.Sender;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import models.ServerMessage.RegistrationResultType;

public class RegistrationResultHandler implements Runnable{

    private Sender sender;
    private RegistrationResultType result;


    public RegistrationResultHandler(RegistrationResultType result, Sender sender){
        this.result = result;
        this.sender = sender;
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        
        if(result == RegistrationResultType.SUCCESS){
            addPlayer(new Player());
            setPassword();
            setRefreshToken();

            sender.send(new Message(MessageType.LOGIN_RESULT));
        }

    }
    
}
