package interfaces;

import models.Player;
import java.sql.*;
import java.util.UUID;

import org.postgresql.Driver;

import javafx.scene.paint.Color;

import java.net.URI;
import java.net.URISyntaxException;
import models.MarkerShape;
import models.SerializeableColor;

public class PlayerDatabaseInterface {

    private static PlayerDatabaseInterface instance = null; // singleton pattern

    private PlayerDatabaseInterface()
    {
        // constructor code
        try{
        Connection conn = getConn();
        Statement stmt = conn.createStatement();

        //create Players table
        String sql = "CREATE TABLE IF NOT EXISTS Players (playerId VARCHAR(255) not NULL, markerShape VARCHAR(255) not NULL, markerColor VARCHAR(255) not NULL, username VARCHAR(255) not NULL, PRIMARY KEY (playerId))";
        stmt.executeUpdate(sql);

         //create LoginCredentials / RefreshTokens table
        sql = "CREATE TABLE IF NOT EXISTS LoginCredentials (username VARCHAR(255) not NULL, password VARCHAR(255) not NULL, refreshToken VARCHAR(255) not NULL, playerId VARCHAR(255) not NULL, PRIMARY KEY (username), FOREIGN KEY (playerId) REFERENCES Players(playerId))";
        stmt.executeUpdate(sql);

        //close everything
        stmt.close();
        conn.close();

        }
        catch(Exception ex)
        {
            System.out.print("Error in PlayerDatabaseInterface");
        }
    }

    public static PlayerDatabaseInterface getInstance()
    {
        if (instance == null)
            instance = new PlayerDatabaseInterface();
        return instance;
    }

    private Connection getConn() throws URISyntaxException, SQLException
    {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        return DriverManager.getConnection(dbUrl);
    }

    public Boolean validatePassword(String username, String password){
        Boolean isValid = false; // make it not 0 later
        //TODO
        try{
            Connection conn = getConn();
            PreparedStatement stmt = conn.prepareStatement("SELECT password FROM LoginCredentials WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();   
            String storedPassword = "";
            while (rs.next())
            {
                storedPassword = rs.getString(1);
            }
            //close everything
            rs.close();
            stmt.close();
            conn.close();

            if (password.equals(storedPassword))
            {
                isValid = true;
            }
    
            }
            catch(Exception ex)
            {
                System.out.print("Error in PlayerDatabaseInterface");
            }
       
        return isValid;
    }
    
    public Boolean setPassword(String username, String password, UUID playerId){
        Boolean isSet = false; // change
        //TODO
        //use sha1 or other hash generator to make hash
        //only allow to set password when making account
        //INSERT INTO player(playerNum, passwordHash)
        //Values(playerId, hash);
        try{
            Connection conn = getConn();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO LoginCredentials(username, password, refreshToken, playerId) VALUES (?,?,?,?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, "");
            stmt.setString(4, playerId.toString());
            stmt.executeUpdate();
            //close everything
            stmt.close();
            conn.close();

            isSet = true;
    
            }
            catch(Exception ex)
            {
                System.out.print("Error in PlayerDatabaseInterface :: " + ex.getMessage());
            }
       
            System.out.println("isSet: " + isSet);
        return isSet;
    }

    public String getRefreshToken(UUID playerId){
        //TODO
        //SELECT token FROM tokens WHERE playerNum = playerId;
        String storedToken = null;
        try{
            Connection conn = getConn();
            System.out.println("playerId: " + playerId);
            PreparedStatement stmt = conn.prepareStatement("SELECT refreshToken FROM LoginCredentials WHERE playerId = ?");
            stmt.setString(1, playerId.toString());
            ResultSet rs = stmt.executeQuery();   
            while (rs.next())
            {
                storedToken = rs.getString(1);
                System.out.println("StoredToken is: " + storedToken);
            }
            System.out.println("storedToken: " + storedToken);
            //close everything
            rs.close();
            stmt.close();
            conn.close();
    
        }
        catch(Exception ex)
        {
            System.out.println("Error in PlayerDatabaseInterface :: " + ex.getMessage());
        }
        return storedToken;
    }

    public void setRefreshToken(UUID playerId, String token){
        //TODO
        //INSERT INTO RefreshTokens
        //throw an error if it doesn't work
        try{
            Connection conn = getConn();
            PreparedStatement stmt = conn.prepareStatement("UPDATE LoginCredentials SET refreshToken = ? WHERE playerId = ?");
            stmt.setString(1, token);
            stmt.setString(2, playerId.toString());
            stmt.executeUpdate();
            //close everything
            stmt.close();
            conn.close();    
            }
            catch(Exception ex)
            {
                System.out.print("Error in PlayerDatabaseInterface");
            }
    }

    public void deleteRefreshToken(UUID playerId){
        //TODO
        try{
            Connection conn = getConn();
            PreparedStatement stmt = conn.prepareStatement("UPDATE LoginCredentials SET refreshToken = ? WHERE playerID = ?");
            stmt.setString(1, "");
            stmt.setString(2, playerId.toString());
            stmt.executeUpdate();
            //close everything
            stmt.close();
            conn.close();    
            }
            catch(Exception ex)
            {
                System.out.print("Error in PlayerDatabaseInterface");
            }
    }

    public Player getPlayer(UUID playerId){
        Player player;
        MarkerShape shape = MarkerShape.CAT;
        Color color = Color.BLACK;
        String username = "";


        try{
            Connection conn = getConn();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Players WHERE playerId = ?");
            stmt.setString(1, playerId.toString());
            ResultSet rs = stmt.executeQuery();   
            while (rs.next())
            {
                String id = rs.getString(1);
                shape = MarkerShape.valueOf(rs.getString(2));
                color = Color.valueOf(rs.getString(3));
                username = rs.getString(4);
            }
            //close everything
            rs.close();
            stmt.close();
            conn.close();
    
            }
            catch(Exception ex)
            {
                System.out.print("Error in PlayerDatabaseInterface");
            }

        player = new Player(color, playerId, username, shape); // CHANGE
        //TODO
        return player;
    }

    public Player getPlayer(String username){
        Player player;
        MarkerShape shape = MarkerShape.CAT;
        Color color = Color.BLACK;
        String usernameTwo = username;
        String playid = "";


        try{
            Connection conn = getConn();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Players WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();   
            while (rs.next())
            {
                playid = rs.getString(1);
                shape = MarkerShape.valueOf(rs.getString(2));
                color = Color.valueOf(rs.getString(3));
                usernameTwo = rs.getString(4);
            }
            //close everything
            rs.close();
            stmt.close();
            conn.close();
    
            }
            catch(Exception ex)
            {
                System.out.print("Error in PlayerDatabaseInterface");
            }

        player = new Player(color, UUID.fromString(playid), username, shape); // CHANGE
        //TODO
        return player;
    }

    public void setPlayer(Player player){
        //TODO
        try{
            Connection conn = getConn();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Players(playerId, markerShape, markerColor, username) VALUES (?,?,?,?) ON CONFLICT (playerId) DO UPDATE SET markerShape = ?, markerColor = ?, username = ?");
            stmt.setString(1, player.getUuid().toString());
            stmt.setString(2, player.getShape().toString());
            stmt.setString(3, player.getColor().toString());
            stmt.setString(4, player.getName());
            stmt.setString(5, player.getShape().toString());
            stmt.setString(6, player.getColor().toString());
            stmt.setString(7, player.getName());
            stmt.executeUpdate();
            //close everything
            stmt.close();
            conn.close();
    
            }
            catch(Exception ex)
            {
                System.out.println("Error in PlayerDatabaseInterface :: " + ex.getMessage());
            }
    }

    public boolean userNameIsUnique(String username){
        return true;
    }
}
