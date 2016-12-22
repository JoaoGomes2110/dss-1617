/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.database;

import dividedespesa.Senhorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author João
 */
public class SenhorioDAO {
    
    public void toDB(Senhorio senhorio) throws SQLException{
        Connection c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO senhorio VALUES(?,?,?)");
        
        st.setString(1,senhorio.getUsername());
        st.setString(2,senhorio.getPassword());
        st.setString(2,senhorio.getNome());
                
        st.executeUpdate();
        c.close();
    }
    
     public Senhorio get(Object key) {
        Senhorio c = null;
        Connection con = null;
        try {
            con = Connect.connect();
            
            // OBTEM DADOS DO QUARTO
            PreparedStatement ps = con.prepareStatement("select * from senhorio where username = ?");
            ps.setInt(1, Integer.parseInt(key.toString()));
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
                c = new Senhorio(rs.getString("username"),rs.getString("password"),rs.getString("nome"));
            
            con.close();
        } catch (SQLException e) {}
        return c;
    }
    
    
}
