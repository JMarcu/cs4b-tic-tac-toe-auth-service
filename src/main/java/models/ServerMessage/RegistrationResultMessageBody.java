package models.ServerMessage;

public class RegistrationResultMessageBody {
    
    private RegistrationResultType result;

    public RegistrationResultMessageBody(RegistrationResultType result){
        this.result = result;
    }

    public RegistrationResultType getResult(){ return result; }
}
