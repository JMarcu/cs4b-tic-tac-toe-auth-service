import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class App {
    
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = null; 
        
        try {
            serverSocket = new ServerSocket(4210);

            System.out.println("Server started and listening for new connections...");

            while(true){
                
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();            
            }            
            catch (Exception ex){ }        
        }
    }
}
