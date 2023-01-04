
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jessicaliu
 */
public class Room 
{
    private static Connection connection;
    private static PreparedStatement getAllPossibleRooms;
    private static PreparedStatement addRoom;
    private static PreparedStatement dropRoom;
    private static ResultSet resultSet;
    
    public static ArrayList<RoomEntry> getAllPossibleRooms()
    {
        connection = DBConnection.getConnection();
        ArrayList<RoomEntry> rooms = new ArrayList<RoomEntry>();
        try
        {
            getAllPossibleRooms = connection.prepareStatement("select name, seats from rooms order by seats");
            resultSet = getAllPossibleRooms.executeQuery();
            
            while(resultSet.next())
            {
                RoomEntry room = new RoomEntry(resultSet.getString(1), resultSet.getInt(2));
                rooms.add(room);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return rooms;
    }
    
    public static void addRoom(String name, int seat)
    {
        connection = DBConnection.getConnection();
        try
        {
            addRoom = connection.prepareStatement("insert into rooms (name, seats) values (?,?)");
            addRoom.setString(1, name);
            addRoom.setInt(2, seat);
            addRoom.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void dropRoom(String name){
        connection = DBConnection.getConnection();
        try{
            dropRoom = connection.prepareStatement("delete from rooms where name = ?");
            dropRoom.setString(1,name);
            dropRoom.executeUpdate();
    }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
