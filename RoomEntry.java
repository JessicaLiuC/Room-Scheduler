import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
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
public class RoomEntry {
    private String name;
    private int seats;
    
    public RoomEntry(String name, int seats){
        this.name = name;
        this.seats = seats;
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getSeats(){
        return this.seats;
    }
}
