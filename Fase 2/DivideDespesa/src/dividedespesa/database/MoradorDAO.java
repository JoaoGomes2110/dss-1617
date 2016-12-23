/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.database;

import dividedespesa.Despesa;
import dividedespesa.Morador;
import dividedespesa.Quarto;
import java.sql.Connection;
import java.sql.Date;
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
        
        java.sql.Date dataEntrada = new java.sql.Date(morador.getDataEntrada().getTime());
        java.sql.Date dataSaida = new java.sql.Date(morador.getDataSaida().getTime());
        
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
    
    public Morador get(Object key) {
        Morador m = null;
        Connection con = null;
        try {
            con = Connect.connect();
            
            // OBTEM DADOS DO QUARTO
            PreparedStatement ps = con.prepareStatement("select * from morador where username = ?");
            
            ps.setString(1, key.toString());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                
                PreparedStatement ps_despesa = con.prepareStatement("select * from despesa where id = ?");
                ps.setString(1, key.toString());
                ResultSet rs_despesa = ps_despesa.executeQuery();
                Date dataEmissao, dataLimite, dataPagamento;
                while(rs_despesa.next()){
                    dataEmissao = rs_despesa.getDate("data_emissao");
                    dataLimite = rs_despesa.getDate("data_limite");
                    dataPagamento = rs_despesa.getDate("data_pagamento");
                    d = new Despesa(rs_despesa.getInt("id"), rs_despesa.getString("info"), rs_despesa.getDouble("valor"), rs_despesa.getString("tipo"),dataEmissao, dataLimite, dataPagamento);
                    
            }
                
                m = new Morador(rs.getInt("id"), rs.getString("info"), rs.getDouble("valor"), rs.getString("tipo"),dataEmissao, dataLimite, dataPagamento);
            }
            
            con.close();
        } catch (SQLException e) {}
        return d;
    }
    
}
