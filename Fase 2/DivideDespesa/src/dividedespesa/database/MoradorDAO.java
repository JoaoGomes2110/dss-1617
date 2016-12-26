/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.database;

import dividedespesa.Conta;
import dividedespesa.Despesa;
import dividedespesa.Morador;
import dividedespesa.Quarto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author João
 */
public class MoradorDAO {
    
    public void put(Morador morador, Quarto quarto) throws SQLException{
        Connection c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO morador VALUES(?,?,?,?,?,?)");
        
        java.sql.Date dataEntrada = new java.sql.Date(morador.getDataEntrada().getTime());
        java.sql.Date dataSaida = null;
        if (morador.getDataSaida() != null)
            dataSaida = new java.sql.Date(morador.getDataSaida().getTime());
        
        st.setString(1,morador.getUsername());
        st.setString(2,morador.getPassword());
        st.setString(3,morador.getNome());
        st.setDate(4,dataEntrada);
        st.setDate(5,dataSaida); 
        st.setDouble(6,morador.getContaCorrente().getSaldo());
        
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
    
    public Morador get(String username) {
        Morador m = null;
        Connection con = null;
        
        try {
            con = Connect.connect();

            // OBTEM DESPESAS
         
            PreparedStatement ps_dPagas, ps_dPorPagar;
            ResultSet rs_dPagas, rs_dPorPagar;
            Map<Integer,Despesa> mapPagas = new HashMap<>(),
                                 mapPorPagar = new HashMap<>();
            Date dataEmissao, dataLimite, dataPagamento;
            Despesa dPagas, dPorPagar;

            
            // OBTEM DESPESAS PAGAS  
            
            ps_dPagas = con.prepareStatement("SELECT * FROM despesa WHERE data_pagamento IS NOT NULL AND morador = ?");
            
            ps_dPagas.setString(1, username);
            rs_dPagas = ps_dPagas.executeQuery();
            
            while(rs_dPagas.next()){
                    dataEmissao = rs_dPagas.getDate("data_emissao");
                    dataLimite = rs_dPagas.getDate("data_limite");
                    dataPagamento = rs_dPagas.getDate("data_pagamento");
                    dPagas = new Despesa(rs_dPagas.getInt("id"), rs_dPagas.getString("info"), rs_dPagas.getDouble("valor"), rs_dPagas.getString("tipo"),dataEmissao, dataLimite, dataPagamento);
                    mapPagas.put(dPagas.getId(),dPagas);
            }
            
            // OBTEM DESPESAS POR PAGAR
            
            ps_dPorPagar = con.prepareStatement("SELECT *" +
                                                    "FROM despesa" +
                                                "WHERE data_pagamento IS NULL AND morador = ?");
            ps_dPorPagar.setString(1, username);
            rs_dPorPagar = ps_dPagas.executeQuery();

            while(rs_dPorPagar.next()){
                dataEmissao = rs_dPorPagar.getDate("data_emissao");
                dataLimite = rs_dPorPagar.getDate("data_limite");
                dataPagamento = rs_dPorPagar.getDate("data_pagamento");
                dPagas = new Despesa(rs_dPorPagar.getInt("id"), rs_dPorPagar.getString("info"), rs_dPorPagar.getDouble("valor"), rs_dPorPagar.getString("tipo"),dataEmissao, dataLimite, dataPagamento);
                mapPorPagar.put(dPagas.getId(),dPagas);
            }
            
            // OBTEM UTILIZADOR
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM morador WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                Conta conta = new Conta(rs.getDouble("saldo"));
                m = new Morador(rs.getString("username"), rs.getString("password"), rs.getString("nome"), conta, mapPorPagar, mapPagas, rs.getDate("data_chegada"), rs.getDate("data_saida"));
               
            }
            
            con.close();
        } catch (SQLException e) {}
        return m;
    }
    
    public void updateSaldo (String username, Morador m) throws SQLException{
        //ainda nao suportado
    }
    
    public void updateSaida (String username, Date saida) throws SQLException{
        Connection con = Connect.connect();
        
        PreparedStatement ps = con.prepareStatement("UPDATE morador SET data_saida = ? WHERE username = ?");
        ps.setDate(1,saida);
        ps.setString(2,username);
        ps.executeUpdate();
        con.close();
    } 
    
    public void updateSaldo (String username, double saldo) throws SQLException{
        Connection con = Connect.connect();
        
        PreparedStatement ps = con.prepareStatement("UPDATE morador SET saldo = ? WHERE username = ?");
        ps.setDouble(1,saldo);
        ps.setString(2,username);
        ps.executeUpdate();
        con.close();
    }
}
