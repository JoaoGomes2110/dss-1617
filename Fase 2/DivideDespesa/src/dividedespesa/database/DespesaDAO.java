package dividedespesa.database;

import dividedespesa.Despesa;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Carlos Pereira
 * @author João Barreira
 * @author João Gomes
 * @author João Reis
 * 
 */

public class DespesaDAO {
    
    /**
     * Coloca na base de dados uma desesa relativa a um morador.
     * @param despesa Despesa
     * @param username Username do morador
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
    public void put(Despesa despesa, String username) throws SQLException {
        Connection c = null;

        c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO despesa VALUES(?,?,?,?,?,?,?,?)");


        java.sql.Date dataEmissao = new java.sql.Date(despesa.getDataEmissao().getTime());
        java.sql.Date dataLimite = new java.sql.Date(despesa.getDataLimite().getTime());
        java.sql.Date dataPagamento = null;
        
        if (despesa.getDataPagamento() != null)
            dataPagamento = new java.sql.Date(despesa.getDataPagamento().getTime());

        st.setInt(1,despesa.getId());
        st.setString(2,despesa.getInfo());
        st.setDouble(3,despesa.getValor());
        st.setDate(4,dataEmissao);
        st.setDate(5,dataLimite);
        st.setDate(6,dataPagamento);        
        st.setString(7,despesa.getTipoDespesaString());
        st.setString(8, username);
        st.executeUpdate();

        c.close();
    }
    
    /**
     * Dado o id, devolve uma despesa.
     * @param key Id da despesa
     * @return Despesa caso exista, de outra maneira, devolve null.
     * @throws SQLException  Atira exceção caso ocorram erros SQL
     */
    
    public Despesa get(Object key) throws SQLException {
        Despesa d = null;
        Connection con = null;

        con = Connect.connect();

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

        return d;
    }
    
    /**
     * Actuliza a data de pagamento de uma dada despesa.
     * @param idDespesa ID da despesa
     * @param data Data de pagamento
     * @throws SQLException  Atira exceção caso ocorram erros SQL
     */
    public void updateDespesa(int idDespesa, java.util.Date data)  throws SQLException {
               
        Connection con = Connect.connect();
 
        PreparedStatement ps = con.prepareStatement("UPDATE despesa SET data_pagamento = ? WHERE id = ?");
        java.sql.Date d_pagamento = new java.sql.Date(data.getTime());
        ps.setDate(1, d_pagamento);
        ps.setInt(2, idDespesa);
 
        ps.executeUpdate();
        con.close();
    }
    
    /**
     * Consulta na base de dados a ultima renda paga por um dado morador.
     * @param username Username
     * @return Data da ultima renda paga. Caso ainda não tenha sido cobrado qualquer renda, devolve null.
     * @throws SQLException  Atira exceção caso ocorram erros SQL.
     */
    public Date ultimaRenda(String username) throws SQLException {
        Connection con = Connect.connect();
 
        PreparedStatement ps = con.prepareStatement("SELECT * FROM despesa WHERE morador = ? AND tipo = 'RENDA' AND data_emissao IS NOT NULL ORDER BY data_emissao DESC");
        ps.setString(1, username);
 
        ResultSet rs = ps.executeQuery();
        
        Date date = null;
        if (rs.next()) {
            date = rs.getDate("data_emissao");
        }
        
        con.close();
        
        return date;
    }
    
    /**
     * Consulta na base dados as despesas por pagar de um dado morador.
     * @param username Username
     * @return Lista de despesas por pagar
     * @throws SQLException  Atira exceção caso ocorram erros SQL.
     */
    public List<Despesa> userPorPagar(String username) throws SQLException {
        List<Despesa> ret = new ArrayList<>();
        Connection con = null;
        
        con = Connect.connect();

        PreparedStatement ps_dPorPagar;
        ResultSet rs_dPorPagar;
        Date dataEmissao, dataLimite, dataPagamento;
        Despesa dPorPagar;

        ps_dPorPagar = con.prepareStatement("SELECT * FROM despesa WHERE data_pagamento IS NULL AND morador = ?");
        ps_dPorPagar.setString(1, username);
        rs_dPorPagar = ps_dPorPagar.executeQuery();

        while(rs_dPorPagar.next()){
                dataEmissao = rs_dPorPagar.getDate("data_emissao");
                dataLimite = rs_dPorPagar.getDate("data_limite");
                dataPagamento = rs_dPorPagar.getDate("data_pagamento");
                dPorPagar = new Despesa(rs_dPorPagar.getInt("id"), rs_dPorPagar.getString("info"), rs_dPorPagar.getDouble("valor"), rs_dPorPagar.getString("tipo"),dataEmissao, dataLimite, dataPagamento);
                ret.add(dPorPagar);
        }

        con.close();

        return ret;
    }
    
    /**
     * Consulta na base dados as despesas pagas de um dado morador.
     * @param username Username
     * @return Lista de despesas pagas
     * @throws SQLException  Atira exceção caso ocorram erros SQL.
     */
    public List<Despesa> userPagas(String username) throws SQLException {
        List<Despesa> ret = new ArrayList<>();
        Connection con = null;
        

        con = Connect.connect();


        PreparedStatement ps_dPagas;
        ResultSet rs_dPagas;
        Date dataEmissao, dataLimite, dataPagamento;
        Despesa dPagas;

        ps_dPagas = con.prepareStatement("SELECT * FROM despesa WHERE data_pagamento IS NOT NULL AND morador = ?");
        ps_dPagas.setString(1, username);
        rs_dPagas = ps_dPagas.executeQuery();
            
        while(rs_dPagas.next()){
                dataEmissao = rs_dPagas.getDate("data_emissao");
                dataLimite = rs_dPagas.getDate("data_limite");
                dataPagamento = rs_dPagas.getDate("data_pagamento");
                dPagas = new Despesa(rs_dPagas.getInt("id"), rs_dPagas.getString("info"),
                                     rs_dPagas.getDouble("valor"), rs_dPagas.getString("tipo"),
                                     dataEmissao, dataLimite, dataPagamento);
                ret.add(dPagas);
        }

        con.close();

        return ret;
    }
    
    /**
     * Consulta na base de dados a quantidade de despesas existentes.
     * @return numero de despesas existentes.
     * @throws SQLException 
     */
    public int size() throws SQLException {
        int size = -1;

        Connection con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("select count(id) from despesa");
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                size = rs.getInt(1);

            con.close();

        return size;
    }
}
