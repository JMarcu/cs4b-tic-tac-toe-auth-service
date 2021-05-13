package models.ServerMessage;

public class LoginFailMessageBody {
    private boolean success;

    public LoginFailMessageBody(){
        this(false);
    }

    public LoginFailMessageBody(boolean success){
        this.success = success;
    }

    public boolean getSuccess(){
        return this.success;
    }
}
