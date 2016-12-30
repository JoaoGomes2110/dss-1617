/*
 * @author Carlos Pereira   - A61887
 * @author João Barreira    - A73831
 * @author João Gomes       - A74033
 * @author João Reis        - A75372
 */

package dividedespesa.database;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
        
    /**
     * Estabelece conexão com a base de dados e devolve ponto de acesso.
     * 
     * @return Conexão correspondente à ligação à base de dados.
     * @throws SQLException 
     */
    public static Connection connect() throws SQLException {
        Connection connect = null; 
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/divide_despesas", "root", "qweqwe");
        } catch (ClassNotFoundException e) {
            throw new SQLException();
        }
        
        return connect;
      }
      
    
    /**
     * Recebe uma conexão à base de dados e fecha-a.
     *
     * @param c Conexão
     * @throws SQLException 
     */
    public static void close(java.sql.Connection c) throws SQLException {
        if(c!=null && !c.isClosed())
            c.close();
    }
}
        
