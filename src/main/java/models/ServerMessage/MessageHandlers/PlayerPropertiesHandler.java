package models.ServerMessage.MessageHandlers;

import interfaces.Sender;
import models.Player;

public class PlayerPropertiesHandler implements Runnable{
 
    private Sender sender;
    private Player player;

    public PlayerPropertiesHandler(Player player, Sender sender){
        this.player = player;
        this.sender = sender;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }
}
