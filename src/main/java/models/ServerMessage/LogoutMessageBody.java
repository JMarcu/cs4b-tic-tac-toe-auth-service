package models.ServerMessage;

public class LogoutMessageBody {
    private String refreshToken;
    private String playerId;

    public LogoutMessageBody(String refreshToken, String playerId){
        this.refreshToken = refreshToken;
        this.playerId = playerId;
    }

    public String getRefreshToken(){ return refreshToken; }
    public String getPlayerId(){return playerId;}
}