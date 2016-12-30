package dividedespesa.database;

import dividedespesa.Senhorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Carlos Pereira
 * @author João Barreira
 * @author João Gomes
 * @author João Reis
 * 
 */
public class SenhorioDAO {
    
    /**
     * Coloca na base de dados um senhorio.
     * @param senhorio Senhorio
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
    public void toDB(Senhorio senhorio) throws SQLException {
        Connection c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO senhorio VALUES(?,?,?)");

        st.setString(1,senhorio.getUsername());
        st.setString(2,senhorio.getPassword());
        st.setString(3,senhorio.getNome());

        st.executeUpdate();
        c.close();
    }
    
    /**
     * Constroi um morador dada o seu username.
     * @param key Username do senhorio
     * @return Senhorio
     */
    public Senhorio get(Object key) {
        Senhorio c = null;
        Connection con = null;
        try {
            con = Connect.connect();
            
            // OBTEM DADOS DO QUARTO
            PreparedStatement ps = con.prepareStatement("select * from senhorio where username = ?");
            ps.setString(1, key.toString());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
                c = new Senhorio(rs.getString("username"),rs.getString("password"),rs.getString("nome"));
            
            con.close();
        } catch (SQLException e) {}
        return c;
    }
    
    /**
     * Consulta a base de dados de modo conferir se os dados de um senhorio estão correctos.
     * @param username Username do senhorio.
     * @param password Password do senhorio.
     * @return true se está correcto, false se não.
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
    public boolean exists(String username, String password) throws SQLException {
        boolean exists = false;
        Connection con = null;
  
        con = Connect.connect();

        PreparedStatement ps = con.prepareStatement("select * from senhorio where username = ? and password = ?");
        ps.setString(1, username);
        ps.setString(2, password);


        ResultSet rs = ps.executeQuery();

        if(rs.next())
            exists = true;

        con.close();
        
        return exists;
    }
     
    /**
     * Consulta base de dados de modo conferir se existe algum senhorio.
     * Caso não exista, pode-se adicionar um apartamento.
     * @return
     * @throws SQLException 
     */
    public boolean existeDB() throws SQLException{
        boolean ret = false;
       
        Connection con = Connect.connect();
        PreparedStatement ps = con.prepareStatement("select username from senhorio");
       
        ResultSet rs = ps.executeQuery();
       
        if(rs.next())
            ret = true;
       
        con.close();
        
        return ret;
    }
}
