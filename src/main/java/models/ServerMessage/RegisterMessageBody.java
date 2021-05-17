package models.ServerMessage;

import java.util.UUID;

public class RegisterMessageBody {
    private String password;
    private String username;

    public RegisterMessageBody(String password, UUID playerId, String username){
        this.password = password;
        this.username = username;
    }

    public String getPassword(){ return password; }
    public String getUserName() { return username;}
}
