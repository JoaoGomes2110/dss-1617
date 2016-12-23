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
                
                PreparedStatement ps_dPagas = con.prepareStatement("SELECT *" +
                                                                     "FROM despesa" +
                                                                   "WHERE data_pagamento IS NOT NULL AND username = ?"),
                                  ps_dPorPagar = con.prepareStatement("SELECT *" +
                                                                        "FROM despesa" +
                                                                      "WHERE data_pagamento IS NULL AND username = ?");
                
                
                ps_dPagas.setString(1, key.toString());
                ps_dPorPagar.setString(1, key.toString());
                
                ResultSet rs_dPagas = ps_dPagas.executeQuery(),
                          rs_dPorPagar = ps_dPagas.executeQuery();
                
                Date dataEmissao, dataLimite, dataPagamento;
                Despesa dPagas, dPorPagar;
                
                Map<Integer,Despesa> mapPagas = new HashMap<>(),
                                     mapPorPagar = new HashMap<>();
                
                
                while(rs_dPagas.next()){
                    dataEmissao = rs_dPagas.getDate("data_emissao");
                    dataLimite = rs_dPagas.getDate("data_limite");
                    dataPagamento = rs_dPagas.getDate("data_pagamento");
                    dPagas = new Despesa(rs_dPagas.getInt("id"), rs_dPagas.getString("info"), rs_dPagas.getDouble("valor"), rs_dPagas.getString("tipo"),dataEmissao, dataLimite, dataPagamento);
                    mapPagas.put(dPagas.getId(),dPagas);
                }
                
                while(rs_dPorPagar.next()){
                    dataEmissao = rs_dPorPagar.getDate("data_emissao");
                    dataLimite = rs_dPorPagar.getDate("data_limite");
                    dataPagamento = rs_dPorPagar.getDate("data_pagamento");
                    dPagas = new Despesa(rs_dPorPagar.getInt("id"), rs_dPorPagar.getString("info"), rs_dPorPagar.getDouble("valor"), rs_dPorPagar.getString("tipo"),dataEmissao, dataLimite, dataPagamento);
                    mapPorPagar.put(dPagas.getId(),dPagas);
                }
                
                Conta conta = new Conta(rs.getDouble("saldo"));
                
                return new Morador(rs.getString("username"), rs.getString("pasword"), rs.getString("nome"), conta, mapPorPagar, mapPagas, rs.getDate("data_cheaga"), rs.getDate("data_saida"));
               
            }
            
            con.close();
        } catch (SQLException e) {}
        return m;
    }
    
}
