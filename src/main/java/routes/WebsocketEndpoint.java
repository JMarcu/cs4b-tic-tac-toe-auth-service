package routes;

import com.google.gson.Gson;
import interfaces.Sender;
import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;

import models.ServerMessage.AuthenticationRequestMessageBody;
import models.ServerMessage.AuthenticationResultMessageBody;
import models.ServerMessage.ConnectionMessageBody;
import models.ServerMessage.Message;
import models.ServerMessage.MessageExecutor;
import models.ServerMessage.MessageType;
import models.ServerMessage.MessageHandlers.AuthenticationRequestHandler;

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
            case AUTHENTICATION_ACKNOWLEDGED:
                break;
            case AUTHENTICATION_REQUEST:
                AuthenticationRequestMessageBody authRequestBody = gson.fromJson(message.getBody(), AuthenticationRequestMessageBody.class);
                // boolean validToken = authRequestBody.getToken() != "";
                // AuthenticationResultMessageBody authResultResponse = new AuthenticationResultMessageBody(validToken);
                // try {
                //     send(new Message(authResultResponse, MessageType.AUTHENTICATION_RESULT));
                // } catch (IOException e) { e.printStackTrace(); }
                handler = new AuthenticationRequestHandler(authRequestBody, this);
                break;
            case AUTHENTICATION_RESULT:
                AuthenticationResultMessageBody authResultBody = gson.fromJson(message.getBody(), AuthenticationResultMessageBody.class);

               // handler = new AuthenticationResultHandler(authResultBody, this);
                break;
            case CHAT:
                //ChatMessageBody chatBody = gson.fromJson(message.getBody(), ChatMessageBody.class);

               // handler = new AuthenticationResultHandler(authResultBody, this);
                break;
            case CONNECTION:
                ConnectionMessageBody connectionBody = gson.fromJson(message.getBody(), ConnectionMessageBody.class);
            
              //  handler = new AuthenticationResultHandler(authResultBody, this);
                break;
            case CREATE_LOBBY:
                //CreateLobbyMessageBody createLobbyBody = gson.fromJson(message.getBody(), CreateLobbyMessageBody.class);
                
              //  handler = new AuthenticationResultHandler(authResultBody, this);
                break;
            case LOBBY_LIST:
                //LobbyListMessageBody lobbyListBody = gson.fromJson(message.getBody(), LobbyListMessageBody.class);

              //  handler = new AuthenticationResultHandler(authResultBody, this);
                break;
            case MOVE:
                //MoveMessageBody moveBody = gson.fromJson(message.getBody(), MoveMessageBody.class);

             //  handler = new AuthenticationResultHandler(authResultBody, this);
                break;
            case PLAYER_PROPERTIES:
                //PlayerPropertiesMessageBody playerPropertiesBody = gson.fromJson(message.getBody(), PlayerPropertiesMessageBody.class);
      
               // handler = new AuthenticationResultHandler(authResultBody, this);
                break;
            case REQUEST_PLAYER:
                //RequestPlayerMessageBody requestPlayerMessageBody = gson.fromJson(message.getBody(), RequestPlayerMessageBody.class);

               // handler = new AuthenticationResultHandler(authResultBody, this);
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
