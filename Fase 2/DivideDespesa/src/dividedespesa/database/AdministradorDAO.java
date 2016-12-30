package dividedespesa.database;

import com.mysql.jdbc.Connection;
import dividedespesa.Administrador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Carlos Pereira
 * @author João Barreira
 * @author João Gomes
 * @author João Reis
 * 
 */

public class AdministradorDAO {
 
    /**
     * Coloca um administrador na base de dados.
     * @param administrador Administrador
     * @throws SQLException  Atira a exceção caso exista erros SQL
     */
    public void toDB(Administrador administrador) throws SQLException {
        Connection c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO administrador VALUES(?,?)");

        st.setString(1,administrador.getUsername());
        st.setString(2,administrador.getPassword());

        st.executeUpdate();
        c.close();
    }
    
    /**
     * Constroi um Administrador dado o seu username
     * @param key Username
     * @return Administrador
     * @throws java.sql.SQLException Atira exceção caso existam problemas SQL
     */
    public Administrador get(Object key) throws SQLException {
        Administrador c = null;
        Connection con = Connect.connect();
            
        PreparedStatement ps = con.prepareStatement("select * from administrador where username = ?");
        ps.setString(1, key.toString());
        ResultSet rs = ps.executeQuery();

        if(rs.next())
            c = new Administrador(rs.getString("username"),rs.getString("password"));

        con.close();
            
        return c;
    }
     /**
      * Verifica se o username e password do administrador estão correctos.
      * @param username Username
      * @param password Password
      * @return boolean a indicar se estão correctos.
     * @throws java.sql.SQLException
      */
    public boolean exists(String username, String password) throws SQLException {
        boolean exists = false;
        Connection con = null;

        con = Connect.connect();
            
        PreparedStatement ps = con.prepareStatement("select * from administrador where username = ? and password = ?");
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if(rs.next())
            exists = true;

        con.close();

        return exists;
    }
    
}
