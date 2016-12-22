/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.database;

import com.mysql.jdbc.Connection;
import dividedespesa.Administrador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author João
 */
public class AdministradorDAO {
 
    public void toDB(Administrador administrador) throws SQLException{
        Connection c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO administrador VALUES(?,?,?)");
        
        st.setString(1,administrador.getUsername());
        st.setString(2,administrador.getPassword());
                
        st.executeUpdate();
        c.close();
    }
    
     public Administrador get(Object key) {
        Administrador c = null;
        Connection con = null;
        try {
            con = Connect.connect();
            
            // OBTEM DADOS DO QUARTO
            PreparedStatement ps = con.prepareStatement("select * from administrador where username = ?");
            ps.setInt(1, Integer.parseInt(key.toString()));
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
                c = new Administrador(rs.getString("username"),rs.getString("password"));
            
            con.close();
        } catch (SQLException e) {}
        return c;
    }
    
}
