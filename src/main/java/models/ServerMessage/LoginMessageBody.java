package models.ServerMessage;

public class LoginMessageBody {
    private String password;
    private String username;
    private String playerId;

    public LoginMessageBody(String username, String password, String playerId){
        this.username = username;
        this.password = password;
        this.playerId = playerId;
    }

    public String getPassword(){ return password; }
    public String getUsername(){ return username; }
    public String getPlayerId(){return playerId;}
}