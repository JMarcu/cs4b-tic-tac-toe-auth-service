
package models.ServerMessage;

public class RefreshTokenMessageBody {
    private String refreshToken;
    private String userName;

    public RefreshTokenMessageBody(String refreshToken, String userName){
        this.refreshToken = refreshToken;
        this.userName = userName;
    }

    public String getRefreshToken(){ return refreshToken; }
    public String getUsername(){return userName;}
}
