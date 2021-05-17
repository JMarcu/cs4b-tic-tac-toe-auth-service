package models.ServerMessage;

import models.Player;

public class RegistrationResultMessageBody {
    
    private Player player;
    private RegistrationResultType result;

    public RegistrationResultMessageBody(RegistrationResultType result){
        this.player = null;
        this.result = result;
    }

    public RegistrationResultMessageBody(RegistrationResultType result, Player player){
        this.player = player;
        this.result = result;
    }

    public Player getPlayer(){ return player; }
    public RegistrationResultType getResult(){ return result; }
}
