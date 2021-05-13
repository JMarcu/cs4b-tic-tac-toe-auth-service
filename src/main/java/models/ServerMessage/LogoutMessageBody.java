package models.ServerMessage;

import java.util.UUID;

public class LogoutMessageBody {
    private String refreshToken;
    private UUID playerId;

    public LogoutMessageBody(String refreshToken, UUID playerId){
        this.refreshToken = refreshToken;
        this.playerId = playerId;
    }

    public String getRefreshToken(){ return refreshToken; }
    public UUID getPlayerId(){return playerId;}
}