/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.database;

import dividedespesa.Conta;
import dividedespesa.Despesa;
import dividedespesa.Morador;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author João
 */
public class MoradorDAO {
    
    public void put(Morador morador, List<Integer> quartos) throws SQLException {
        Connection c = null;
 
        c = Connect.connect();
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

        for(Integer q: quartos){
            st = c.prepareStatement("INSERT INTO moradorquarto VALUES(?,?)");
            st.setInt(1,q);
            st.setString(2, morador.getUsername());
            st.executeUpdate();
        }

        c.close();
    }
    
    public void updateMoradorQuarto (Morador morador, List<Integer> quartos) throws SQLException {
        Connection c = null;
        c = Connect.connect();
        PreparedStatement st = c.prepareStatement("DELETE FROM moradorquarto WHERE username = ?");
        st.setString(1, morador.getUsername());
        st.executeUpdate();
        c.close();

        put(morador, quartos);
    }
    

    public Map<String, Double> getUsernamesPrecos()  throws SQLException{
        
        Map<String, Double> usernames = new TreeMap<>();
        Connection c = null;
        
        c = Connect.connect();
        PreparedStatement st = c.prepareStatement("SELECT morador, preco FROM moradorquarto AS MQ" +
                                                            "INNER JOIN quarto AS Q" +
                                                  "ON Q.id = MQ.quarto");
        ResultSet rs = st.executeQuery();

        while(rs.next()){
            String username = rs.getString("username");

            if (usernames.containsKey(username)){
                double tmp = usernames.get(username);
                usernames.put(username, tmp + rs.getDouble("preco"));
            } else {
                usernames.put(username, rs.getDouble("preco"));
            }
        }

        return usernames;
    }
    
    
    
    public int size()  throws SQLException {
        int size = -1;

        Connection con = Connect.connect();
        PreparedStatement ps = con.prepareStatement("SELECT COUNT(username) FROM morador WHERE data_saida IS NULL");
        ResultSet rs = ps.executeQuery();

        if(rs.next())
            size = rs.getInt(1);

        con.close();

        return size;
    }
    
    public boolean containsKey (String username) throws SQLException {
        boolean exists = false;
        Connection con = null;

        con = Connect.connect();

        // OBTEM UTILIZADOR

        PreparedStatement ps = con.prepareStatement("SELECT * FROM morador WHERE username = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            exists = true;
        }

        con.close();
        
        return exists;
    }
    
    public Morador get(String username)  throws SQLException {
        Morador m = null;
        Connection con = null;
        
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

        return m;
    }
    

    public boolean exists(String username, String password) throws SQLException {
        boolean exists = false;
        Connection con = null;

        con = Connect.connect();

        // OBTEM DADOS DO QUARTO
        PreparedStatement ps = con.prepareStatement("select * from morador where username = ? and password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if(rs.next())
            exists = true;

        con.close();

        return exists;
    }
    
    public void updateSaida (String username, java.util.Date saida) throws SQLException {
                
        Connection con = Connect.connect();

        System.out.println("Entrou no updateSaida");

        PreparedStatement ps = con.prepareStatement("UPDATE morador SET data_saida = ? WHERE username = ?");
        java.sql.Date d_saida = new java.sql.Date(saida.getTime());
        ps.setDate(1, d_saida);


        ps.setString(2, username);
        ps.executeUpdate();
        con.close();
    } 
    
    public void updateSaldo (String username, double saldo) throws SQLException {

        Connection con = Connect.connect();

        PreparedStatement ps = con.prepareStatement("UPDATE morador SET saldo = ? WHERE username = ?");
        ps.setDouble(1,saldo);
        ps.setString(2,username);
        ps.executeUpdate();
        con.close();
    }

    public void updatePassword(String username, String password) throws SQLException {
        Connection con = Connect.connect();

        PreparedStatement ps = con.prepareStatement("UPDATE morador SET password = ? WHERE username = ?");
        ps.setString(1,password);
        ps.setString(2,username);
        ps.executeUpdate();
        con.close();
    }
    
    public String[] getAll() throws SQLException {
        String[] ret = new String[this.size()];
        Connection con = null;
        int i = 0;

        con = Connect.connect();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM morador WHERE data_saida IS NULL");
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            StringBuilder sb = new StringBuilder();
            sb.append(rs.getString("username")).append(" - ").append(rs.getString("nome"));
            ret[i] = sb.toString();
            i++;
        }
        
        con.close();
        
        return ret;
        
    }
  
}
