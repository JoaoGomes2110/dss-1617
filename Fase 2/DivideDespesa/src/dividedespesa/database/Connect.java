package dividedespesa.database;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Carlos Pereira
 * @author João Barreira
 * @author João Gomes
 * @author João Reis
 * 
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
        
