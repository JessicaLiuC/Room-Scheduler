
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jessicaliu
 */
public class Reservations 
{
    private static Connection connection;
    private static PreparedStatement getReservationsByDate;
    private static PreparedStatement getReservationsByFaculty;
    private static PreparedStatement getRoomsReservedByDate;
    private static PreparedStatement getReservationByRoom;
    private static PreparedStatement cancelReservation;
    private static PreparedStatement addReservationEntry;
    private static PreparedStatement deleteReservation;
    private static ResultSet resultSet;
    
    public static ArrayList<RoomEntry> getRoomsReservedByDate(Date date)
    {
        connection = DBConnection.getConnection();
        ArrayList<RoomEntry> rooms = new ArrayList<>();
        try
        {
            //get the customers from the booking table
            getRoomsReservedByDate = connection.prepareStatement("select room, seats from reservations where date = ?");
            getRoomsReservedByDate.setDate(1, date);
            resultSet = getRoomsReservedByDate.executeQuery();
            
            while(resultSet.next())
            {
                RoomEntry room = new RoomEntry(resultSet.getString(1),resultSet.getInt(2));
                rooms.add(room);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return rooms;
        
    }
    
    public static ArrayList<ReservationEntry> getStatusReservationsByDate(Date date)
    {
        connection = DBConnection.getConnection();
        ArrayList<ReservationEntry> reservations = new ArrayList<>();
        try
        {
            getReservationsByDate = connection.prepareStatement("select faculty, room, seats, timestamp from reservations where date = ?");
            getReservationsByDate.setDate(1,date);
            resultSet = getReservationsByDate.executeQuery();
            
            while(resultSet.next()){
                ReservationEntry reserve = new ReservationEntry(resultSet.getString(1),resultSet.getString(2),date,resultSet.getInt(3),resultSet.getTimestamp(4));
                reservations.add(reserve);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return reservations;
    }
    
    public static ArrayList<ReservationEntry> getReservationByRoom(String room){
        connection = DBConnection.getConnection();
        ArrayList<ReservationEntry> reservations = new ArrayList<>();
        try{
            getReservationByRoom = connection.prepareStatement("select faculty, date, seats, timestamp from reservations where room =?");
            getReservationByRoom.setString(1, room);
            resultSet = getReservationByRoom.executeQuery();
            
            while(resultSet.next()){
                ReservationEntry reserve = new ReservationEntry(resultSet.getString(1),room, resultSet.getDate(2), resultSet.getInt(3), resultSet.getTimestamp(4));
                reservations.add(reserve);
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return reservations;
    }
    
    public static ArrayList<ReservationEntry> getReservationsByFaculty(String faculty)
    {
        connection = DBConnection.getConnection();
        ArrayList<ReservationEntry> reservations = new ArrayList<>();
        try
        {
            getReservationsByFaculty = connection.prepareStatement("select room, date, seats, timestamp from reservations where faculty = ?");
            getReservationsByFaculty.setString(1,faculty);
            resultSet = getReservationsByFaculty.executeQuery();
            
            while(resultSet.next()){
                ReservationEntry reserve = new ReservationEntry(faculty, resultSet.getString(1),resultSet.getDate(2),resultSet.getInt(3),resultSet.getTimestamp(4));
                reservations.add(reserve);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return reservations;
    
    }
    
    public static void addReservationEntry(ReservationEntry reservation)
    {
        connection = DBConnection.getConnection();
        try{
                addReservationEntry = connection.prepareStatement("insert into reservations (faculty, room, date, seats, timestamp) values (?,?,?,?,?)");
                addReservationEntry.setString(1, reservation.getFaculty());
                addReservationEntry.setString(2, reservation.getRoom());
                addReservationEntry.setDate(3, reservation.getDate());
                addReservationEntry.setInt(4, reservation.getSeats());
                addReservationEntry.setTimestamp(5, reservation.getTimestamp());
                addReservationEntry.executeUpdate();

            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
    }
    
    public static void deleteReservation(String room){
        connection = DBConnection.getConnection();
        try{
            deleteReservation = connection.prepareStatement("delete from reservations where room = ?");
            deleteReservation.setString(1, room);
            deleteReservation.executeUpdate();
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void cancelReservation(String faculty, Date date){
        connection = DBConnection.getConnection();
        try{
            cancelReservation = connection.prepareStatement("delete from reservations where faculty=? and date = ?");
            cancelReservation.setString(1, faculty);
            cancelReservation.setDate(2, date);
            cancelReservation.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
