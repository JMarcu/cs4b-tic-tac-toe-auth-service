package models.ServerMessage;

import java.util.UUID;

public class RequestPlayerMessageBody {

    private UUID playerId;
    private String userName;

    public RequestPlayerMessageBody(UUID playerId, String userName){
        this.playerId = playerId;
        this.userName = userName;
    }

    public UUID getPlayerId(){ return playerId; }
    public String getUsername(){ return userName; }
}