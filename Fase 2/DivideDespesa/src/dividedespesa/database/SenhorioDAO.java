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
    
    public void toDB(Senhorio senhorio) throws SQLException {
        //try{
            Connection c = Connect.connect();
            PreparedStatement st = c.prepareStatement("INSERT INTO senhorio VALUES(?,?,?)");

            st.setString(1,senhorio.getUsername());
            st.setString(2,senhorio.getPassword());
            st.setString(3,senhorio.getNome());

            st.executeUpdate();
            c.close();
       // } catch (SQLException e) {
            //System.out.println("Erro SQL! " + e.toString());
        //}
    }
    
     public Senhorio get(Object key) {
        Senhorio c = null;
        Connection con = null;
        try {
            con = Connect.connect();
            
            // OBTEM DADOS DO QUARTO
            PreparedStatement ps = con.prepareStatement("select * from senhorio where username = ?");
            ps.setString(1, key.toString());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
                c = new Senhorio(rs.getString("username"),rs.getString("password"),rs.getString("nome"));
            
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
            PreparedStatement ps = con.prepareStatement("select * from senhorio where username = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
                exists = true;
            
            con.close();
        } catch (SQLException e) {}
        
        return exists;
    }
     
    public boolean existeDB() throws SQLException{
        boolean ret = false;
       
        Connection con = Connect.connect();
        PreparedStatement ps = con.prepareStatement("select username from senhorio");
       
        ResultSet rs = ps.executeQuery();
       
        if(rs.next())
            ret = true;
       
        con.close();
        
        return ret;
    }
}
