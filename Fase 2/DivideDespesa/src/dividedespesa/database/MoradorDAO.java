/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.database;

import dividedespesa.Morador;
import dividedespesa.Quarto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author João
 */
public class MoradorDAO {
    
    public void put(Morador morador, Quarto quarto) throws SQLException{
        Connection c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO morador VALUES(?,?,?,?,?)");
        
        java.sql.Date dataEntrada = new java.sql.Date(morador.getDataEntrada().getTimeInMillis());
        java.sql.Date dataSaida = new java.sql.Date(morador.getDataSaida().getTimeInMillis());
        
        st.setString(1,morador.getUsername());
        st.setString(2,morador.getPassword());
        st.setString(3,morador.getNome());
        st.setDate(4,dataEntrada);
        st.setDate(5,dataSaida);        
        st.executeUpdate();
        
        st = c.prepareStatement("INSERT INTO moradorquarto VALUES(?,?)");
        st.setInt(1,quarto.getNumQuarto());
        st.setString(2, morador.getUsername());
        st.executeUpdate();
        
        c.close();
    }
    
    public int size() {
        int size = -1;
        try{
            Connection con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("select count(id) from morador");
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                size = rs.getInt(1);

            con.close();
        } catch (Exception e){};

        return size;
    }
    
    public void get(String username) throws SQLException{
        Connection c = Connect.connect();
        PreparedStatement st_morador = c.prepareStatement("INSERT INTO morador VALUES(?,?,?,?,?)");
        PreparedStatment st_despesas = c.prepareStatment("SELECT")
                +
        
        Morador m 
        
        c.close();
    }
    
}
