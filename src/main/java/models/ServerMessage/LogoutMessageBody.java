package models.ServerMessage;

public class LogoutMessageBody {
    private String refreshToken;
    private String userName;

    public LogoutMessageBody(String refreshToken, String userName){
        this.refreshToken = refreshToken;
        this.userName = userName;
    }

    public String getRefreshToken(){ return refreshToken; }
    public String getUsername(){return userName;}
}