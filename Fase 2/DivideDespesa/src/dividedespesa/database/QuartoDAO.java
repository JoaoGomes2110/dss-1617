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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author João
 */
public class QuartoDAO {
    
    public void toDB(Quarto quarto) throws SQLException{
        Connection c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO quarto VALUES(?,?)");
        
        st.setInt(1,quarto.getNumQuarto());
        st.setDouble(2,quarto.getPreco());
                
        st.executeUpdate();
        c.close();
    }
    
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
        } catch (SQLException e) {}
        return r;
    }


    public boolean containsValue(Object value) {
        //return containsKey()
        return false;
    }

    public Quarto get(int key) {
        Quarto c = null;
        Connection con = null;
        try {
            con = Connect.connect();
            
            //OBTEM DADOS DO MORADOR QUE ESTA NAQUELE QUARTO
            PreparedStatement ps_quarto = con.prepareStatement("SELECT morador FROM moradorquarto WHERE quarto = ?");
            ps_quarto.setInt(1, key);
            ResultSet rs_quarto = ps_quarto.executeQuery();
            Set<String> listamorador = new HashSet<>();
            

            while(rs_quarto.next())
                listamorador.add(rs_quarto.getString("morador"));
            

                
            PreparedStatement ps = con.prepareStatement("select * from quarto where id = ?");
            ps.setInt(1,key);
            ResultSet rs = ps.executeQuery();


            if (rs.next())
                return new Quarto(rs.getInt("id"),rs.getInt("preco"),listamorador);

            
            con.close();
        } catch (SQLException e) {}
        return c;
    }

    public Quarto put(Integer key, Quarto value) {
        try {
            this.toDB(value);
        } catch (SQLException ex) {
            return null;
        }
        return value;
    }

    public Quarto remove(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    public void putAll(Map<? extends Integer, ? extends Quarto> m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Set<Integer> keySet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Collection<Quarto> values() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
