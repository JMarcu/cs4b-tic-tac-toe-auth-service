package models.ServerMessage;

public class RefreshFailMessageBody {
    private String jwt;

    public RefreshFailMessageBody(String jwt){
        this.jwt = jwt;
    }

    public String getJWT(){ return jwt; }
}
