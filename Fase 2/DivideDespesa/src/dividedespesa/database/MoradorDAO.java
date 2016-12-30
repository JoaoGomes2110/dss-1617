/*
 * @author Carlos Pereira   - A61887
 * @author João Barreira    - A73831
 * @author João Gomes       - A74033
 * @author João Reis        - A75372
 */

package dividedespesa.database;

import dividedespesa.BusinessLayer.Conta;
import dividedespesa.BusinessLayer.Morador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class MoradorDAO {
    
    /**
     * Coloca na base dados um morador assim como a lista de quartos a que lhe corresponde.
     * @param morador Morador
     * @param quartos Lista de quartos
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
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
    
    /**
     * Actualiza na base de dados a lista de quartos a ele associado.
     * @param morador Morador
     * @param quartos Lista de quartos
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
    public void updateMoradorQuarto (Morador morador, List<Integer> quartos) throws SQLException {
        Connection c = null;
        c = Connect.connect();
        PreparedStatement st = c.prepareStatement("DELETE FROM moradorquarto WHERE morador = ?");
        st.setString(1, morador.getUsername());
        st.executeUpdate();
        
        for(Integer q: quartos){
            st = c.prepareStatement("INSERT INTO moradorquarto VALUES(?,?)");
            st.setInt(1, q);
            st.setString(2, morador.getUsername());
            st.executeUpdate();
        }
        
        c.close();
    }
        
    /**
     * Consulta a base de dados de modo a calcular o numero de moradores existes.
     * @return Número de moradores
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
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
    
    /**
     * Consulta a base dados de modo a conferir se um determinado morador existe.
     * @param username Username do morador
     * @return true se existe, false se não.
     * @throws SQLException Atira exceção caso ocorram erros SQL. 
     */
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
    
    /**
     * Controi um Morador dado um username.
     * @param username Username do moradores
     * @return Morador contruido.
     * @throws SQLException Atira exceção caso ocorram erros SQL.
     */
    public Morador get(String username)  throws SQLException {
        Morador m = null;
        Connection con = null;
        
        con = Connect.connect();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM morador WHERE username = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            Conta conta = new Conta(rs.getDouble("saldo"));
            m = new Morador(rs.getString("username"), rs.getString("password"), rs.getString("nome"), conta, rs.getDate("data_chegada"), rs.getDate("data_saida"));

        }

        con.close();

        return m;
    }
    
    /**
     * Consulta a base de dados de modo conferir se os dados de um morador estão correctos.
     * @param username Username do morador.
     * @param password Password do morador.
     * @return true se está correcto, false se não.
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
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
    
    /**
     * Actuliza na base dados a saída de um morador de um apartamento.
     * @param username Username do morador
     * @param saida Data de saída
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
    public void updateSaida (String username, java.util.Date saida) throws SQLException {
                
        Connection con = Connect.connect();

        PreparedStatement ps = con.prepareStatement("UPDATE morador SET data_saida = ? WHERE username = ?");
        java.sql.Date d_saida = new java.sql.Date(saida.getTime());
        ps.setDate(1, d_saida);

        ps.setString(2, username);
        ps.executeUpdate();
        con.close();
    } 
    
    /**
     * Actualiza na base de dados o saldo de um morador.
     * @param username Username do morador.
     * @param saldo Novo saldo
     * @throws SQLException Atira exceção caso ocorram erros SQL.
     */
    public void updateSaldo (String username, double saldo) throws SQLException {

        Connection con = Connect.connect();

        PreparedStatement ps = con.prepareStatement("UPDATE morador SET saldo = ? WHERE username = ?");
        ps.setDouble(1,saldo);
        ps.setString(2,username);
        ps.executeUpdate();
        con.close();
    }
    
    /**
     * Actualiza na base dados a password de um morador.
     * @param username Username do morador.
     * @param password Password do morador.
     * @throws SQLException Atira exceção caso ocorram erros SQL.
     */
    public void updatePassword(String username, String password) throws SQLException {
        Connection con = Connect.connect();

        PreparedStatement ps = con.prepareStatement("UPDATE morador SET password = ? WHERE username = ?");
        ps.setString(1,password);
        ps.setString(2,username);
        ps.executeUpdate();
        con.close();
    }
    
    /**
     * Consulta na base de dados todos moradores.
     * @return Array com o nome de e username de cada morador no aparamento.
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
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
