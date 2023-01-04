
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jessicaliu
 */
public class Waitlist {
    private static Connection connection;
    private static ArrayList<WaitlistEntry> waitlist = new ArrayList<WaitlistEntry>();
    private static PreparedStatement addWaitlistEntry;
    private static PreparedStatement deleteWaitlistEntry;
    private static PreparedStatement getWaitlistByDate;
    private static PreparedStatement getWaitlistByFaculty;
    private static PreparedStatement getAllWaitlist;
    private static ResultSet resultSet;
    
    public static void addWaitlistEntry(String faculty, Date date, int seats)
    {
        java.sql.Timestamp current = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        connection = DBConnection.getConnection();
        try
        {
            addWaitlistEntry = connection.prepareStatement("insert into waitlist (faculty,date,seats,timestamp) values(?,?,?,?)");
            addWaitlistEntry.setString(1, faculty);
            addWaitlistEntry.setDate(2,date);
            addWaitlistEntry.setInt(3, seats);
            addWaitlistEntry.setTimestamp(4, current);
            addWaitlistEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

    
    public static ArrayList<WaitlistEntry> getWaitlistByDate(Date date)
    {
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> waitlist = new ArrayList<>();
        try
        {
            getWaitlistByDate = connection.prepareStatement("select faculty, seats, timestamp from waitlist where date = ?");
            getWaitlistByDate.setDate(1, date);
            resultSet = getWaitlistByDate.executeQuery();
            
            while(resultSet.next())
            {
                WaitlistEntry wait = new WaitlistEntry(resultSet.getString(1),date, resultSet.getInt(2), resultSet.getTimestamp(3));
                waitlist.add(wait);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlist;
    }
    
    public static ArrayList<WaitlistEntry> getWaitlistByFaculty(String faculty)
    {
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> waitlist = new ArrayList<>();
        try
        {
            getWaitlistByFaculty = connection.prepareStatement("select date, seats, timestamp from waitlist where faculty = ?");
            getWaitlistByFaculty.setString(1,faculty);
            resultSet = getWaitlistByFaculty.executeQuery();
            
            while(resultSet.next())
            {
                WaitlistEntry wait = new WaitlistEntry(faculty, resultSet.getDate(1), resultSet.getInt(2), resultSet.getTimestamp(3));
                waitlist.add(wait);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlist;
    }
    
    public static ArrayList<WaitlistEntry> getAllWaitlist(){
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> waitlist = new ArrayList<>();
        try{
            getAllWaitlist = connection.prepareStatement("select faculty, date, seats, timestamp from waitlist order by date");
            resultSet = getAllWaitlist.executeQuery();
            
            while (resultSet.next()){
                WaitlistEntry wait = new WaitlistEntry(resultSet.getString(1),resultSet.getDate(2),resultSet.getInt(3),resultSet.getTimestamp(4));
                waitlist.add(wait);
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return waitlist;
    }
    
    public static void deleteWaitlist(String faculty, Date date){
        connection = DBConnection.getConnection();
        try{
            deleteWaitlistEntry = connection.prepareStatement("delete from waitlist where faculty = ? and date = ?");
            deleteWaitlistEntry.setString(1, faculty);
            deleteWaitlistEntry.setDate(2, date);
            deleteWaitlistEntry.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
