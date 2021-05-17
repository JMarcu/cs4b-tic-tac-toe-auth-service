package models.ServerMessage;

import java.util.UUID;

public class RegisterMessageBody {
    private String password;
    private String userName;

    public RegisterMessageBody(String password, UUID playerId, String userName){
        this.password = password;
        this.userName = userName;
    }

    public String getPassword(){ return password; }
    public String getUserName() { return userName;}
}
