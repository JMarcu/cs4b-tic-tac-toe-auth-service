package models.ServerMessage;

public class RequestPlayerMessageBody {

    private String playerId;

    public RequestPlayerMessageBody(String playerId){
        this.playerId = playerId;
    }

    public String getPlayerId(){ return playerId; }
}