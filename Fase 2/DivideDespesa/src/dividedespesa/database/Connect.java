/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.database;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João
 */
public class Connect{
        
    /**
     * Estabelece connecção com a base de dados e devolve ponto de acesso.
     * @return Connection correspondente à ligação à base de dados.
     * @throws SQLException 
     */
    public static Connection connect() throws SQLException{
        Connection connect = null; 
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/divide_despesas", "root", "qweqwe");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        
        return connect;
      }
      
    
    /**
     * Recebe uma coneção à base de dados e fecha-a.
     * @param c Conecção
     * @throws SQLException 
     */
    public static void close(java.sql.Connection c) throws SQLException {
        if(c!=null && !c.isClosed())
            c.close();
    }
}
        
