/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.database;

import dividedespesa.Despesa;
import dividedespesa.Morador;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author João
 */
public class DespesaDAO {
    
    public void put(Despesa despesa, Morador m) throws SQLException{
        Connection c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO despesa VALUES(?,?,?,?,?,?,?)");
        

        java.sql.Date dataEmissao = new java.sql.Date(despesa.getDataEmissao().getTime());
        java.sql.Date dataLimite = new java.sql.Date(despesa.getDataLimite().getTime());
        java.sql.Date dataPagamento = new java.sql.Date(despesa.getDataPagamento().getTime());
        
        st.setInt(1,despesa.getId());
        st.setDouble(2,despesa.getValor());
        st.setDate(3,dataEmissao);
        st.setDate(4,dataLimite);
        st.setDate(5,dataPagamento);        
        st.setString(5,despesa.getTipoDespesaString()); // possivelmente mal
        st.setString(6, m.getUsername());
        st.executeUpdate();
        
        c.close();
    }
    
        public Despesa get(Object key) {
        Despesa d = null;
        Connection con = null;
        try {
            con = Connect.connect();
            
            // OBTEM DADOS DO QUARTO
            PreparedStatement ps = con.prepareStatement("select * from despesa where id = ?");
            ps.setInt(1, Integer.parseInt(key.toString()));
            ResultSet rs = ps.executeQuery();
            Date dataEmissao, dataLimite, dataPagamento;
            if(rs.next()){
                dataEmissao = rs.getDate("data_emissao");
                dataLimite = rs.getDate("data_limite");
                dataPagamento = rs.getDate("data_pagamento");
                d = new Despesa(rs.getInt("id"), rs.getString("info"), rs.getDouble("valor"), rs.getString("tipo"),dataEmissao, dataLimite, dataPagamento);
            }
            
            con.close();
        } catch (SQLException e) {}
        return d;
    }
    
}
