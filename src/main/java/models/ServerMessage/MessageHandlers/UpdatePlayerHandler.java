package models.ServerMessage.MessageHandlers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import interfaces.PlayerDatabaseInterface;
import interfaces.Sender;
import java.io.IOException;
import models.Player;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import models.ServerMessage.UpdatePlayerMessageBody;
import models.ServerMessage.UpdatePlayerSuccessMessageBody;
import services.JWTService;

public class UpdatePlayerHandler implements Runnable{
    
    UpdatePlayerMessageBody msg;
    Sender sender;

    public UpdatePlayerHandler(UpdatePlayerMessageBody msg, Sender sender){
        this.msg = msg;
        this.sender = sender;
    }

    @Override
    public void run() {
        if(JWTService.validate(msg.getJwt())){
            DecodedJWT decodedJwt = JWTService.decode(msg.getJwt());
            Player player = new Gson().fromJson(decodedJwt.getClaim("player").asString(), Player.class);
            
            if(msg.getColor() != null){
                player.setColor(msg.getColor().getColor());
            }

            if(msg.getName() != null){
                player.setName(msg.getName());
            }

            if(msg.getShape() != null){
                player.setShape(msg.getShape());
            }

            PlayerDatabaseInterface.getInstance().setPlayer(player);

            String newJwt = JWTService.create(player);
            UpdatePlayerSuccessMessageBody body = new UpdatePlayerSuccessMessageBody(newJwt, player);
            Message message = new Message(body, MessageType.UPDATE_PLAYER_SUCCESS);
            try {
                this.sender.send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
