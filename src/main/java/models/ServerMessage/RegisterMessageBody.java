package models.ServerMessage;

import java.util.UUID;

public class RegisterMessageBody {
    private String password;
    private UUID playerId;
    private String userName;

    public RegisterMessageBody(String password, UUID playerId, String userName){
        this.password = password;
        this.playerId = playerId;
        this.userName = userName;
    }

    public String getPassword(){ return password; }
    public UUID getPlayerId(){ return playerId; }
    public String getUserName() { return userName;}
}
