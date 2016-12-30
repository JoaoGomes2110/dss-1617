/*
 * @author Carlos Pereira   - A61887
 * @author João Barreira    - A73831
 * @author João Gomes       - A74033
 * @author João Reis        - A75372
 */

package dividedespesa.data;

import dividedespesa.business.Quarto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class QuartoDAO {
    /**
     * Colo na base de dados um dado quarto
     * @param id ID do quarto
     * @param preco Preco do Quarto.
     * @throws SQLException 
     */
    public void toDB(int id, double preco) throws SQLException {

        Connection c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO quarto VALUES(?,?)");

        st.setInt(1,id);
        st.setDouble(2,preco);

        st.executeUpdate();
        c.close();
    }
    
    /**
     * Acede a base de dados de modo a calcular o numero de quartos que existe.
     * @return Numero de quartos que existe.
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
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
    
    /**
     * Acede a base de dados de modo a verificar se um determinado quarto existe
     * @param key ID do quarto
     * @return true se existe, false se não.
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
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


    /**
     * Actualiza na base de dados a renda de um determinado quarto.
     * @param key ID do quarto
     * @param preco Nova renda
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
    public void updateRenda(int key, double preco) throws SQLException {
        Connection con = Connect.connect();
        
        PreparedStatement ps = con.prepareStatement("UPDATE quarto SET preco = ? WHERE id = ?");
        ps.setDouble(1,preco);
        ps.setInt(2,key);
        ps.executeUpdate();
        con.close();
    }
    
    /**
     * Acede a base de dados de modo a calcular o numero de morador num determinado quarto.
     * @param key ID do quarto
     * @return numero de moradores
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
    public int getNumMorador(int key) throws SQLException {
        Quarto c = null;
        Connection con = null;
        int numMoradores = 0;

        con = Connect.connect();

        //OBTEM DADOS DO MORADOR QUE ESTÁ NAQUELE QUARTO
        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM moradorquarto WHERE quarto = ?");
        ps.setInt(1, key);
        ResultSet rs = ps.executeQuery();

        if(rs.next())
            numMoradores = rs.getInt(1); 

        con.close();

        return numMoradores;
    }
    
    /**
     * Controi um quarto, dado o seu ID
     * @param key ID do quarto
     * @return Quarto
     * @throws SQLException 
     */
    public Quarto get(int key) throws SQLException {
        Quarto c = null;
        Connection con = null;

        con = Connect.connect();

        PreparedStatement ps = con.prepareStatement("select * from quarto where id = ?");
        ps.setInt(1,key);
        ResultSet rs = ps.executeQuery();

        if (rs.next())
            return new Quarto(rs.getInt("id"),rs.getInt("preco"));

        con.close();
            
        return c;
    }

    /**
     * Consulta na base de dados os quartos existentes
     * @return Array com ID e preco de todos os quartos.
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
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
    /**
     * Acede à base de dados de modo a calcular, para todos moradores, a renda
     * que teriam de pagar caso fosse para o quarto.
     * 
     * @return Map com Username e a renda correspondente.
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
    public Map<String, Double> getUsernamesPrecos() throws SQLException {
        
        Map<String, Double> usernames = new TreeMap<>();
        
        Connection c = null;
        
        c = Connect.connect();
        
        String statement = "SELECT morador, preco, Q.id, data_saida FROM moradorquarto " + 
                           "AS MQ  INNER JOIN quarto AS Q ON Q.id = MQ.quarto " +
                           "INNER JOIN morador AS M ON MQ.morador = M.username " +
                           "WHERE data_saida IS NULL";
        PreparedStatement st = c.prepareStatement(statement);
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
    
    /**
     * Consulta na base de dados os quartos existentes
     * @return Array com ID e preco total de todos os quartos.
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
    public String[] getAllPrecoTotal() throws SQLException {
        String[] ret = new String[this.size()];
        Connection con = null;
        int i=0;          
 
        con = Connect.connect();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM quarto");
        ResultSet rs = ps.executeQuery();
 
        while(rs.next()){
            StringBuilder sb = new StringBuilder();
            sb.append(rs.getInt("id")).append(" - ").append(rs.getDouble("preco"));
 
            ret[i] = sb.toString();
            i++;
        }
       
        con.close();
       
        return ret;        
    }
}
