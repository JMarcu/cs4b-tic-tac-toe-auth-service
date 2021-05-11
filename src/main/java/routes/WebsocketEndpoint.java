package routes;

import com.google.gson.Gson;
import interfaces.Sender;
import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;

import models.Player;
import models.ServerMessage.AuthenticationRequestMessageBody;
import models.ServerMessage.AuthenticationResultMessageBody;
import models.ServerMessage.ConnectionMessageBody;
import models.ServerMessage.LoginMessageBody;
import models.ServerMessage.LoginSuccessMessageBody;
import models.ServerMessage.LogoutMessageBody;
import models.ServerMessage.Message;
import models.ServerMessage.MessageExecutor;
import models.ServerMessage.MessageType;
import models.ServerMessage.RegistrationResultType;
import models.ServerMessage.PlayerPropertiesMessageBody;
import models.ServerMessage.RefreshSuccessMessageBody;
import models.ServerMessage.RefreshTokenMessageBody;
import models.ServerMessage.RegisterMessageBody;
import models.ServerMessage.RegistrationResultMessageBody;
import models.ServerMessage.RequestPlayerMessageBody;
import models.ServerMessage.MessageHandlers.AuthenticationRequestHandler;
import models.ServerMessage.MessageHandlers.AuthenticationResultHandler;
import models.ServerMessage.MessageHandlers.LoginHandler;
import models.ServerMessage.MessageHandlers.LogoutHandler;
import models.ServerMessage.MessageHandlers.PlayerPropertiesHandler;
import models.ServerMessage.MessageHandlers.RefreshTokenHandler;
import models.ServerMessage.MessageHandlers.RegisterHandler;
import models.ServerMessage.MessageHandlers.RegistrationResultHandler;
import models.ServerMessage.MessageHandlers.RequestPlayerHandler;

@ServerEndpoint(value = "/ws")
public class WebsocketEndpoint implements Sender {
    Session session;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("open");
        this.session = session;
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("close");
    }

    @OnMessage
    public void onMessage(String messageString) throws InterruptedException {
        Runnable handler = null;

        Gson gson = new Gson();
        Message message = gson.fromJson(messageString, Message.class);
        switch(message.getType()){
            case AUTHENTICATION_REQUEST:
                AuthenticationRequestMessageBody authRequestBody = gson.fromJson(message.getBody(), AuthenticationRequestMessageBody.class);
 
                handler = new AuthenticationRequestHandler(authRequestBody, this);
                break;
            case AUTHENTICATION_RESULT:
                AuthenticationResultMessageBody authResultBody = gson.fromJson(message.getBody(), AuthenticationResultMessageBody.class);
    
                handler = new AuthenticationResultHandler(authResultBody.getSuccess());
                break;
            case LOGIN:
                LoginMessageBody loginBody = gson.fromJson(message.getBody(), LoginMessageBody.class);

                handler = new LoginHandler(loginBody.getUsername(), this);
                break;
            case LOGOUT:
                LogoutMessageBody logoutBody = gson.fromJson(message.getBody(), LogoutMessageBody.class);

                handler = new LogoutHandler(logoutBody.getPlayerId(), logoutBody.getRefreshToken(), this);
                break;
            case PLAYER_PROPERTIES:
                PlayerPropertiesMessageBody playerPropertiesBody = gson.fromJson(message.getBody(), PlayerPropertiesMessageBody.class);

                handler = new PlayerPropertiesHandler(playerPropertiesBody.getPlayer(), this);
                break;
            case REGISTER:
                RegisterMessageBody registerBody = gson.fromJson(message.getBody(), RegisterMessageBody.class);

                handler = new RegisterHandler(registerBody.getUsername(), this);
                break;
            case REGISTRATION_RESULT:
                RegistrationResultMessageBody registResultBody = gson.fromJson(message.getBody(), RegistrationResultMessageBody.class);

                handler = new RegistrationResultHandler(registResultBody.getResult(), this);
                break;
            case REFRESH_TOKEN:
                RefreshTokenMessageBody refreshTokenBody = gson.fromJson(message.getBody(), RefreshTokenMessageBody.class);

                handler = new RefreshTokenHandler(refreshTokenBody.getPlayerId(), refreshTokenBody.getRefreshToken(), this);
                break;
            case REFRESH_RESULT:
                RefreshSuccessMessageBody refreshSuccessBody = gson.fromJson(message.getBody(), RefreshSuccessMessageBody.class);

                break;
            case REQUEST_PLAYER:
                RequestPlayerMessageBody requestPlayerBody = gson.fromJson(message.getBody(), RequestPlayerMessageBody.class);

                handler = new RequestPlayerHandler(requestPlayerBody.getPlayerId(), this);
                break;  
            default:
                break;
        }

        MessageExecutor.getInstance().queueMessageHandler(handler);
    }

    public void send(Message message) throws IOException{
        session.getBasicRemote().sendText(new Gson().toJson(message));
    }
}
