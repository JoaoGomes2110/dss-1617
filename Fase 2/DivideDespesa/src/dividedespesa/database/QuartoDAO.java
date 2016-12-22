/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.database;

import dividedespesa.Quarto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João
 */
public class QuartoDAO implements Map<Integer, Quarto>{
    
    public void toDB(Quarto quarto) throws SQLException{
        Connection c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO quarto VALUES(?,?)");
        
        st.setInt(1,quarto.getNumQuarto());
        st.setInt(2,quarto.getNumMoradores());
        st.setDouble(2,quarto.getPreco());
        
        st.executeUpdate();
        c.close();
    }
    
    @Override
    public int size() {
        int size = -1;
        try{
            Connection con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("select count(id) from quarto");
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                size = rs.getInt(1);

            con.close();
        } catch (Exception e){};

        return size;
    }

    @Override
    public boolean isEmpty() {
        boolean empty = true;
        try{
            Connection con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("select count(id) from quarto");
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                empty = false;

            con.close();
        } catch (Exception e){};

        return empty;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean r = false;
        Connection con = null;
        try {
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("select * from quarto where id = ?");
            ps.setInt(1, Integer.parseInt(key.toString()));
            ResultSet rs = ps.executeQuery();
            if(rs.isBeforeFirst()) {
                r = true;
            }
            
            con.close();
        } catch (SQLException | ClassNotFoundException e) {}
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        //return containsKey()
    }

    @Override
    public Quarto get(Object key) {
        Quarto c = null;
        Connection con = null;
        try {
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("select * from quarto where id = ?");
            ps.setInt(1, Integer.parseInt(key.toString()));
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
                c = new Quarto(rs.getInt("id"),rs.getInt("numero_moradores"),rs.getInt("preco"));
            
            con.close();
        } catch (SQLException |ClassNotFoundException ex) {}
        return c;
    }

    @Override
    public Quarto put(Integer key, Quarto value) {
        try {
            this.toDB(value);
        } catch (SQLException ex) {
            return null;
        }
        return value;
    }

    @Override
    public Quarto remove(Object key) {
        Connection con = Connect.connect();
        PreparedStatement ps = con.prepareStatement("DELETE FROM Morador WHERE username = ?")
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Quarto> m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Integer> keySet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Quarto> values() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Entry<Integer, Quarto>> entrySet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
