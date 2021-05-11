package models.ServerMessage.MessageHandlers;

import java.io.IOException;
import java.util.Scanner;

import interfaces.Sender;
import models.Player;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import models.ServerMessage.RegistrationResultType;
import services.JWTService;

public class RegisterHandler implements Runnable {
    
    private Sender sender;
    private String userName;


    public RegisterHandler(String userName, Sender sender){
        this.userName = userName;
        this.sender = sender;
    }

    @Override
    public void run() {
        try {
            
            if(userNameIsUnique(userName)){
                Scanner input = new Scanner(System.in);
                Player player = new Player();
                System.out.println("Enter a password: ");
                String password = input.nextLine();
                addplayer(player);
                setPassword(userName, password);

                String token = JWTService.create();

                setRefreshToken(player.getUuid(), token);

                sender.send(new Message(RegistrationResultType.SUCCESS, MessageType.REGISTRATION_RESULT)); 
            }
            else{
                sender.send(new Message(RegistrationResultType.USERNAME_ALREADY_EXISTS, MessageType.REGISTRATION_RESULT)); 
            }     
              
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            sender.send(new Message(RegistrationResultType.UNKNOWN_ERROR, MessageType.REGISTRATION_RESULT));
        }   
    }

}
