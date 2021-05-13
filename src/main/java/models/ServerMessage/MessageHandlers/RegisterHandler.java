package models.ServerMessage.MessageHandlers;

import java.io.IOException;
import java.util.Scanner;

import interfaces.Sender;
import models.Player;
import models.PlayerDatabaseInterface;
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
            
            if(PlayerDatabaseInterface.userNameIsUnique(userName)){
                // System.out.println("made it");
               // Scanner input = new Scanner(System.in);
                Player player = new Player();
                System.out.println("Enter a password: ");
                String password = "password"; /*input.nextLine();*/

                if(PlayerDatabaseInterface.setPassword(userName, password)){
                    PlayerDatabaseInterface.setplayer(player);

                    String jwt = JWTService.create();
                    String refreshToken = JWTService.create(); //change this maybe

                    PlayerDatabaseInterface.setRefreshToken(player.getUuid(), refreshToken);
                    //input.close();
                    //sender.send(new Message(RegistrationResultType.SUCCESS, MessageType.REGISTRATION_RESULT)); 
                }
                else{
                    sender.send(new Message(RegistrationResultType.PASSWORD_FAILS_REQUIREMENTS, MessageType.REGISTRATION_RESULT)); 
                }
            }
            else{
                sender.send(new Message(RegistrationResultType.USERNAME_ALREADY_EXISTS, MessageType.REGISTRATION_RESULT));
            }     
              
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            try {
                sender.send(new Message(RegistrationResultType.UNKNOWN_ERROR, MessageType.REGISTRATION_RESULT));
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }   
    }

}
