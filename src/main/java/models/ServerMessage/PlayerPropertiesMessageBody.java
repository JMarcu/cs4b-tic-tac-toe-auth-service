package models.ServerMessage;

import models.Player;

public class PlayerPropertiesMessageBody {
   
    private Player player;

    public PlayerPropertiesMessageBody(Player player) {
        this.player = player;
    }

    public Player getPlayer(){ return player;}
}