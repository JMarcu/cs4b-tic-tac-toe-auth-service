import java.io.*;
import java.net.http.WebSocket;
import java.nio.channels.FileChannel;
import java.util.UUID;

import javax.websocket.Session;

import com.google.gson.Gson;

import org.apache.catalina.startup.Tomcat;

import models.ServerMessage.LoginMessageBody;
import models.ServerMessage.LogoutMessageBody;
import models.ServerMessage.Message;
import models.ServerMessage.MessageType;
import models.ServerMessage.RefreshTokenMessageBody;
import models.ServerMessage.RegisterMessageBody;
import models.ServerMessage.RequestPlayerMessageBody;
import routes.WebsocketEndpoint;

public class App {
    private final String SERVICE_NAME = "auth-service";
    
    public static void main(String[] args) throws Exception {
    //    final App app = new App();
    //     app.launchServer();

        WebsocketEndpoint webSocket = new WebsocketEndpoint();

        // LoginMessageBody body = new LoginMessageBody("Grant", "password");
        // String message = new Gson().toJson(new Message(body, MessageType.LOGIN ));

        // LogoutMessageBody body = new LogoutMessageBody("Grant", "Grant");
        // String message = new Gson().toJson(new Message(body, MessageType.LOGOUT ));

        RegisterMessageBody body = new RegisterMessageBody("Grant", "password");
        String message = new Gson().toJson(new Message(body, MessageType.REGISTER ));

        // RefreshTokenMessageBody body = new RefreshTokenMessageBody("Grant", "Grant");
        // String message = new Gson().toJson(new Message(body, MessageType.REFRESH_TOKEN ));

        // RequestPlayerMessageBody body = new RequestPlayerMessageBody(UUID.randomUUID(), "Grant");
        // String message = new Gson().toJson(new Message(body, MessageType.REQUEST_PLAYER ));

        webSocket.onMessage(message); 
        RefreshTokenMessageBody body1 = new RefreshTokenMessageBody("Grant", "Grant");
        message = new Gson().toJson(new Message(body1, MessageType.REFRESH_TOKEN ));

        webSocket.onMessage(message);

        System.out.println("end");
    }

    private void launchServer() {
        //This is an embedded Tomcat webserver which will host the service.
        Tomcat tomcat = new Tomcat();

        /*Bind to proper port. Heroku will inject this through a system variable,
          but we use a default value for local development. */
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "4205";
        }
        tomcat.setPort(Integer.parseInt(webPort));

        try {
            //Get a reference to the service's jar file.
            File jar = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

            //Create a destination directory to run the jar file from, inside of the WEB-INF folder.
            File targetFolder = new File(SERVICE_NAME + File.separator + "WEB-INF" + File.separator +"lib");
            File destination = new File(targetFolder.getPath() + File.separator + SERVICE_NAME + ".jar");
            targetFolder.mkdirs();
    
            //Copy the service's jar file to the WEB-INF directory.
            FileChannel sourceChannel = new FileInputStream(jar).getChannel();
            FileChannel destChannel = new FileOutputStream(destination).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
            sourceChannel.close();
            destChannel.close();

            //Add the webapp context to the embedded Tomcat instance.
            tomcat.addWebapp("/", new File(SERVICE_NAME).getAbsolutePath());

            //Start the server.
            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
