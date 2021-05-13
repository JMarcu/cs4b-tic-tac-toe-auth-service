
package models.ServerMessage;

import java.util.UUID;

public class RefreshTokenMessageBody {
    private String refreshToken;
    private UUID playerId;

    public RefreshTokenMessageBody(String refreshToken, UUID playerId){
        this.refreshToken = refreshToken;
        this.playerId = playerId;
    }

    public String getRefreshToken(){ return refreshToken; }
    public UUID getPlayerId(){return playerId;}
}
