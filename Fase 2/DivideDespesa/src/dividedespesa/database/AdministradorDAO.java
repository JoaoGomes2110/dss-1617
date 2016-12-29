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
 
    public void toDB(Administrador administrador){
        try{
            Connection c = Connect.connect();
            PreparedStatement st = c.prepareStatement("INSERT INTO administrador VALUES(?,?,?)");

            st.setString(1,administrador.getUsername());
            st.setString(2,administrador.getPassword());

            st.executeUpdate();
            c.close();
        } catch (SQLException e) {System.out.println("Erro SQL! " + e.toString());}
    }
    
     public Administrador get(Object key) {
        Administrador c = null;
        Connection con = null;
        try {
            con = Connect.connect();
            
            // OBTEM DADOS DO QUARTO
            PreparedStatement ps = con.prepareStatement("select * from administrador where username = ?");
            ps.setString(1, key.toString());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
                c = new Administrador(rs.getString("username"),rs.getString("password"));
            
            con.close();
        } catch (SQLException e) {}
        return c;
    }
     public boolean exists(String username, String password) {
        boolean exists = false;
        Connection con = null;
        try {
            con = Connect.connect();
            
            // OBTEM DADOS DO QUARTO
            PreparedStatement ps = con.prepareStatement("select * from administrador where username = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
                exists = true;
            
            con.close();
        } catch (SQLException e) {}
        return exists;
    }
    
}
