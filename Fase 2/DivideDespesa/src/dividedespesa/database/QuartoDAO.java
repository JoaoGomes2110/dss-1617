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
    
    public void toDB(int id, double preco) throws SQLException {
        //try{
            Connection c = Connect.connect();
            PreparedStatement st = c.prepareStatement("INSERT INTO quarto VALUES(?,?)");

            st.setInt(1,id);
            st.setDouble(2,preco);

            st.executeUpdate();
            c.close();
        /*} catch (SQLException e) {
            System.out.println("Erro SQL! " + e.toString());
        }*/
    }
    
    public int size() throws SQLException {
        int size = -1;
        //try{
            Connection con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("select count(id) from quarto");
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                size = rs.getInt(1);

            con.close();
        //} catch (Exception e){};

        return size;
    }

    public boolean isEmpty() throws SQLException {
        boolean empty = true;
        //try{
            Connection con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("select count(id) from quarto");
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                empty = false;

            con.close();
        //} catch (Exception e){};

        return empty;
    }

    public boolean containsKey(Object key) throws SQLException {
        boolean r = false;
        Connection con = null;
        //try {
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("select * from quarto where id = ?");
            ps.setInt(1, Integer.parseInt(key.toString()));
            ResultSet rs = ps.executeQuery();
            if(rs.isBeforeFirst()) {
                r = true;
            }
     
            con.close();
        //} catch (SQLException e) {}
        return r;
    }



    public void updateRenda(int key, double preco) throws SQLException {
        //try{
            Connection con = Connect.connect();

            PreparedStatement ps = con.prepareStatement("UPDATE quarto SET preco = ? WHERE id = ?");
            ps.setDouble(1,preco);
            ps.setInt(2,key);
            ps.executeUpdate();
            con.close();
        //} catch (SQLException e) {System.out.println("Erro SQL! " + e.toString());}
        
    }
    
    public int getNumMorador(int key) throws SQLException {
        Quarto c = null;
        Connection con = null;
        int numMoradores = 0;
        //try {
            con = Connect.connect();
            
            //OBTEM DADOS DO MORADOR QUE ESTA NAQUELE QUARTO
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM moradorquarto WHERE quarto = ?");
            ps.setInt(1, key);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                numMoradores = rs.getInt(1); 
            
            con.close();
        //} catch (SQLException e) {}
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
               
        //try {
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM quarto");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                double preco = rs.getDouble("preco");
                
                preco = preco / (this.getNumMorador(rs.getInt("id")) + 1);
                StringBuilder sb = new StringBuilder();
                System.out.println("Chegou");
                 
                sb.append(rs.getInt("id")).append(" - ").append(preco);
                
                
                ret[i] = sb.toString();
                i++;
            }
        //} catch (SQLException e) {System.out.println("Erro SQL! " + e.toString());}
        
        return ret;
        
    }
}
