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
import java.util.TreeMap;

/**
 *
 * @author João
 */
public class QuartoDAO {
    
    public void toDB(int id, double preco) throws SQLException {

        Connection c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO quarto VALUES(?,?)");

        st.setInt(1,id);
        st.setDouble(2,preco);

        st.executeUpdate();
        c.close();
    }
    
    public int size() throws SQLException {
        int size = -1;

        Connection con = Connect.connect();
        PreparedStatement ps = con.prepareStatement("select count(id) from quarto");
        ResultSet rs = ps.executeQuery();

        if(rs.next())
            size = rs.getInt(1);

        con.close();

        return size;
    }

    public boolean isEmpty() throws SQLException {
        boolean empty = true;
        
        Connection con = Connect.connect();
        PreparedStatement ps = con.prepareStatement("select count(id) from quarto");
        ResultSet rs = ps.executeQuery();

        if(rs.next())
            empty = false;

        con.close();

        return empty;
    }

    public boolean containsKey(Object key) throws SQLException {
        boolean r = false;
        Connection con = null;

        con = Connect.connect();
        PreparedStatement ps = con.prepareStatement("select * from quarto where id = ?");
        ps.setInt(1, Integer.parseInt(key.toString()));
        ResultSet rs = ps.executeQuery();
        if(rs.isBeforeFirst()) {
            r = true;
        }

        con.close();
        
        return r;
    }



    public void updateRenda(int key, double preco) throws SQLException {
        Connection con = Connect.connect();

        PreparedStatement ps = con.prepareStatement("UPDATE quarto SET preco = ? WHERE id = ?");
        ps.setDouble(1,preco);
        ps.setInt(2,key);
        ps.executeUpdate();
        con.close();
    }
    
    public int getNumMorador(int key) throws SQLException {
        Quarto c = null;
        Connection con = null;
        int numMoradores = 0;

        con = Connect.connect();

        //OBTEM DADOS DO MORADOR QUE ESTA NAQUELE QUARTO
        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM moradorquarto WHERE quarto = ?");
        ps.setInt(1, key);
        ResultSet rs = ps.executeQuery();

        if(rs.next())
            numMoradores = rs.getInt(1); 

        con.close();

        return numMoradores;
    }
    
    public Quarto get(int key) throws SQLException {
        Quarto c = null;
        Connection con = null;

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
            
        return c;
    }

        
    public String[] getAll() throws SQLException {
        String[] ret = new String[this.size()];
        Connection con = null;
        int i=0;          

        con = Connect.connect();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM quarto");
        ResultSet rs = ps.executeQuery();

        while(rs.next()){

            double preco = rs.getDouble("preco");

            preco = preco / (this.getNumMorador(rs.getInt("id")) + 1);
            StringBuilder sb = new StringBuilder();

            sb.append(rs.getInt("id")).append(" - ").append(preco);

            ret[i] = sb.toString();
            i++;
        }
        
        con.close();
        
        return ret;        
    }

    public Map<String, Double> getUsernamesPrecos() throws SQLException {
        
        Map<String, Double> usernames = new TreeMap<>();
        
        Connection c = null;
        
        c = Connect.connect();
        
        
        PreparedStatement st = c.prepareStatement("SELECT morador, preco, Q.id FROM moradorquarto AS MQ " +
                                                            "INNER JOIN quarto AS Q" +
                                                  " ON Q.id = MQ.quarto");
        ResultSet rs = st.executeQuery();

        while(rs.next()){
            String username = rs.getString("morador");
            
            if (usernames.containsKey(username)){
                double tmp = usernames.get(username);
                
                usernames.put(username, tmp + rs.getDouble("preco")/getNumMorador(rs.getInt("id")));
            } else {
                usernames.put(username, rs.getDouble("preco")/getNumMorador(rs.getInt("id")));
            }
        }

        c.close();
        
        return usernames;
    }


}
