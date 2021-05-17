package models.ServerMessage.MessageHandlers;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import interfaces.Sender;
import models.Player;
import models.PlayerDatabaseInterface;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import models.ServerMessage.RegistrationResultMessageBody;
import models.ServerMessage.RegistrationResultType;
import services.JWTService;

public class RegisterHandler implements Runnable {
    
    private Sender sender;
    private String userName;
    private String password;


    public RegisterHandler(String userName, String password, Sender sender){
        this.userName = userName;
        this.password = password;
        this.sender = sender;
    }

    @Override
    public void run() {
        System.out.println("Running Register MessageHandler.");
        try {
            
            if(PlayerDatabaseInterface.userNameIsUnique(userName)){
                System.out.println("Name is Unique");

                if(PlayerDatabaseInterface.setPassword(userName, password)){
                    System.out.println("Password Set");
                    
                    Player player = new Player();
                    player.setName(userName);
                    PlayerDatabaseInterface.setplayer(player);

                    String jwt = JWTService.create();
                    UUID refreshToken = UUID.randomUUID(); //change this maybe

                    PlayerDatabaseInterface.setRefreshToken(userName, refreshToken.toString());

                    RegistrationResultMessageBody body = new RegistrationResultMessageBody(RegistrationResultType.SUCCESS);
                    sender.send(new Message(body, MessageType.REGISTRATION_RESULT)); 
                } else{
                    sender.send(new Message(RegistrationResultType.PASSWORD_FAILS_REQUIREMENTS, MessageType.REGISTRATION_RESULT)); 
                }
            } else{
                sender.send(new Message(RegistrationResultType.USERNAME_ALREADY_EXISTS, MessageType.REGISTRATION_RESULT));
            }     
              
        } catch (IOException e) {
            e.printStackTrace();

            try {
                sender.send(new Message(RegistrationResultType.UNKNOWN_ERROR, MessageType.REGISTRATION_RESULT));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }   
    }

}
