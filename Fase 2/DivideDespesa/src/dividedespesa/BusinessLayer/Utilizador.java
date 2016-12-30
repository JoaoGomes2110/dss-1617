/*
 * @brief Classe Utilizador. Contém métodos para criar e validar um objecto
 *        desta classe.
 *
 * @author Carlos Pereira   - A61887
 * @author João Barreira    - A73831
 * @author João Gomes       - A74033
 * @author João Reis        - A75372
 */

package dividedespesa.BusinessLayer;

public class Utilizador {
    
    // Variáveis de instância
    
    private String username;
    private String password;

    
    // Construtores
    
    /**
     * Construtor por parâmetros.
     * 
     * @param username  Username do utilizador
     * @param password  Password do utilizador
     */
    public Utilizador(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    /**
     * Construtor a partir de um objeto Utilizador.
     * 
     * @param u Utilizador
     */
    public Utilizador(Utilizador u) {
        username = u.getUsername();
        password = u.getPassword();
    }    
    
    
    // Métodos de instância
    
    /**
     * Faz a validação do username de um Utilizador.
     * Para isso verifica a existência de carateres especiais neste parâmetro.
     * 
     * @return true caso seja válido
     *         false caso contrário
     */
    public boolean validaUsername()  {
        String [] special = {"»", "«", ":", "*", " ", "/", "#", "$", "%", "&",
                             ">", "<", "-", ",", "=", "|", "\\", "!", "\"\""};
        boolean ret = true;

        for(String s : special) {
            if(username.contains(s)) {
                ret = false;
                break;
            }
        }
        
        return ret;
    }
    
    
    /**
     * Faz a validação da password de um Utilizador.
     * Para isso verifica a existência de carateres especiais neste parâmetro.
     * 
     * @return true caso seja válido
     *         false caso contrário
     */
    public boolean validaPassword() {
        String [] special = {"»", "«", ":", "*", " ", "/", "#", "$", "%", "&",
                             ">", "<", "-", ",", "=", "|", "\\", "!", "\"\""};
        boolean ret = true;

        for(String s : special) {
            if(password.contains(s)) {
                ret = false;
                break;
            }
        }
        
        return ret;
    }

    
    // Getters
    
    /**
     * Devolve o username do Utilizador.
     * 
     * @return username
     */
    public String getUsername() {
        return this.username;
    }
    
    /**
     * Devolve a password do Utilizador.
     * 
     * @return password
     */
    public String getPassword() {
        return this.password;
    }
}
