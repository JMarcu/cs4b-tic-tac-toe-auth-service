
package models.ServerMessage;

public class RefreshTokenMessageBody {
    private String refreshToken;
    private String playerId;

    public RefreshTokenMessageBody(String refreshToken, String playerId){
        this.refreshToken = refreshToken;
        this.playerId = playerId;
    }

    public String getRefreshToken(){ return refreshToken; }
    public String getPlayerId(){return playerId;}
}
