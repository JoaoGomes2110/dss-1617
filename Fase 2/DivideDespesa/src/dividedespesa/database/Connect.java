/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.database;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author João
 */
public class Connect{
        
        
      public Connect(){
             
      }
      
    /**
     *
     * @return
     * @throws SQLException
     */
    public static Connection connect() throws SQLException{
        Connection connect = null;  
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://host/divide_despesas?user=root&password=password");
        } catch (ClassNotFoundException ex) {System.out.println("Base de dados inexistente");}
        
        return connect;
      }
      
      public void disconnect(Connection con) throws SQLException {
           con.close();
      }
}
        
