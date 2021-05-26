package routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import interfaces.Sender;
import java.io.IOException;
import java.lang.reflect.Modifier;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import models.ServerMessage.AuthenticationRequestMessageBody;
import models.ServerMessage.AuthenticationResultMessageBody;
import models.ServerMessage.LoginFailMessageBody;
import models.ServerMessage.LoginMessageBody;
import models.ServerMessage.LoginSuccessMessageBody;
import models.ServerMessage.LogoutMessageBody;
import models.ServerMessage.Message;
import models.ServerMessage.MessageExecutor;
import models.ServerMessage.MessageType;
import models.ServerMessage.PlayerPropertiesMessageBody;
import models.ServerMessage.RefreshFailMessageBody;
import models.ServerMessage.RefreshSuccessMessageBody;
import models.ServerMessage.RefreshTokenMessageBody;
import models.ServerMessage.RegisterMessageBody;
import models.ServerMessage.RegistrationResultMessageBody;
import models.ServerMessage.RegistrationResultType;
import models.ServerMessage.RequestPlayerMessageBody;
import models.ServerMessage.RequestedPlayerMessageBody;
import models.ServerMessage.MessageHandlers.AuthenticationRequestHandler;
import models.ServerMessage.MessageHandlers.AuthenticationResultHandler;
import models.ServerMessage.MessageHandlers.LoginHandler;
import models.ServerMessage.MessageHandlers.LogoutHandler;
import models.ServerMessage.MessageHandlers.RefreshTokenHandler;
import models.ServerMessage.MessageHandlers.RegisterHandler;
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

////////////////
        System.out.print("Message Received");
/////////////////////

        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
        Message message = gson.fromJson(messageString, Message.class);
        switch(message.getType()){
            case LOGIN:
                LoginMessageBody loginBody = gson.fromJson(message.getBody(), LoginMessageBody.class);
                handler = new LoginHandler(loginBody.getUsername(), loginBody.getPassword(), this);
                break;
            case LOGIN_FAIL:
                LoginFailMessageBody loginFailBody = gson.fromJson(message.getBody(), LoginFailMessageBody.class);
           
                try {
                    send(new Message(loginFailBody, MessageType.LOGIN_FAIL));
                } catch (IOException e) {
                    System.out.println("LOGIN FAIL");
                    e.printStackTrace();
                }
              
                break;
            case LOGIN_SUCCESS:
                LoginSuccessMessageBody loginSuccessBody = gson.fromJson(message.getBody(), LoginSuccessMessageBody.class);
           
                try {

                    //////////////////////
                    System.out.print("Message Received: Login Success");
                    ///////////////////////
                    
                    send(new Message(loginSuccessBody, MessageType.LOGIN_SUCCESS));
                } catch (IOException e) {
                    System.out.println("LOGIN SUCCESS");
                    e.printStackTrace();
                }
                break;
            case LOGOUT:
                LogoutMessageBody logoutBody = gson.fromJson(message.getBody(), LogoutMessageBody.class);
           
                handler = new LogoutHandler(logoutBody.getPlayerId(), logoutBody.getRefreshToken(), this);
                break;
            case LOGOUT_FAIL:
                try {
                    send(new Message(null, MessageType.LOGIN_FAIL));
                } catch (IOException e) {
                    System.out.println("LOGOUT_FAIL");
                    e.printStackTrace();
                }
                break;
            case LOGOUT_SUCCESS:
                try {
                    send(new Message(null, MessageType.LOGIN_SUCCESS));
                } catch (IOException e) {
                    System.out.println("LOGOUT_SUCCESS");
                    e.printStackTrace();
                }
              
                break;
            case PLAYER_PROPERTIES:
               // PlayerPropertiesMessageBody playerPropertiesBody = gson.fromJson(message.getBody(), PlayerPropertiesMessageBody.class);

                // try {
                //    send(new Message(, MessageType.PLAYER_PROPERTIES));
                // } catch (IOException e) {
                //     System.out.println("PLAYER PROPERTIES");
                //     e.printStackTrace();
                // }
                break;
            case REGISTER:
                System.out.println("Register Message Received: " + message.getBody());
                RegisterMessageBody registerBody = gson.fromJson(message.getBody(), RegisterMessageBody.class);
                handler = new RegisterHandler(registerBody.getUserName(), registerBody.getPassword(), this);
                break;
            case REGISTRATION_RESULT:
                RegistrationResultMessageBody registResultBody = gson.fromJson(message.getBody(), RegistrationResultMessageBody.class);
                if(registResultBody.getResult() == RegistrationResultType.SUCCESS){
                    System.out.print("SUCCESS");
                }
                else if (registResultBody.getResult() == RegistrationResultType.PASSWORD_FAILS_REQUIREMENTS){
                    System.out.print("PASSWORD_FAILS_REQUIREMENTS");
                }
                else if (registResultBody.getResult() == RegistrationResultType.USERNAME_ALREADY_EXISTS){
                    System.out.print("USERNAME_ALREADY_EXISTS");
                }
                else{
                    System.out.print("UNKNOWN_ERROR");
                }
                break;
            case REFRESH_FAIL:
               RefreshFailMessageBody refreshFailBody = gson.fromJson(message.getBody(), RefreshFailMessageBody.class);

                try {
                 send(new Message(refreshFailBody.getJWT(), MessageType.REFRESH_FAIL));
                } 
                catch (IOException e) {
                    System.out.println("REFRESH FAIL");
                    e.printStackTrace();
                }

                break;
            case REFRESH_SUCCESS:
                RefreshSuccessMessageBody refreshSuccessBody = gson.fromJson(message.getBody(), RefreshSuccessMessageBody.class);

                try {
                    send(new Message(refreshSuccessBody.getJWT(), MessageType.REFRESH_SUCCESS));
                } catch (IOException e) {
                    System.out.println("REFRESH SUCCESS");
                    e.printStackTrace();
                }

                break;
            case REFRESH_TOKEN:
                RefreshTokenMessageBody refreshTokenBody = gson.fromJson(message.getBody(), RefreshTokenMessageBody.class);

                handler = new RefreshTokenHandler(refreshTokenBody.getPlayerId(), refreshTokenBody.getRefreshToken(), this);
                break;
            case REQUEST_PLAYER:
                RequestPlayerMessageBody requestPlayerBody = gson.fromJson(message.getBody(), RequestPlayerMessageBody.class);

                handler = new RequestPlayerHandler(requestPlayerBody.getPlayerId(), this);
                break;  
            case REQUESTED_PLAYER:
                RequestedPlayerMessageBody requestedPlayerBody = gson.fromJson(message.getBody(), RequestedPlayerMessageBody.class);

                System.out.println();
                System.out.println();
                System.out.println("Player name: " + requestedPlayerBody.getPlayer().getName());
                System.out.println("Player id: " + requestedPlayerBody.getPlayer().getUuid());
                System.out.println();
                System.out.println();

                try {
                   send(new Message(requestedPlayerBody.getPlayer(), MessageType.REQUESTED_PLAYER));
                } catch (IOException e) {
                    System.out.println("PLAYER PROPERTIES");
                    e.printStackTrace();
                }
               
                break;  
            default:
                break;
        }
        MessageExecutor.getInstance().queueMessageHandler(handler);
    }

    public void send(Message message) throws IOException{
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
        session.getBasicRemote().sendText(gson.toJson(message));
    }
}
