/*
 * @brief Classe Administrador. Contém métodos para criar e validar um objecto
 *        desta classe.
 *
 * @author Carlos Pereira   - A61887
 * @author João Barreira    - A73831
 * @author João Gomes       - A74033
 * @author João Reis        - A75372
 */

package dividedespesa.business;

public class Administrador extends Utilizador {
    
    //Construtores
    
    /**
     * Construtor por parâmetros.
     * 
     * @param username  Username do administrador
     * @param password  Password do administrador
     */
    public Administrador(String username, String password) {
        super(username, password);
    }
    
    /**
     * Construtor a partir do mesmo objeto.
     * 
     * @param admin Administrador
     */
    public Administrador(Administrador admin) {
        super(admin.getUsername(), admin.getPassword());
    }
    
    
    //Métodos de instância
    
    /**
     * Valida o username e a password de um administrador.
     * 
     * @return true se forem válidos
     *         false caso contrário
     */
    public boolean validaAdministrador() {
        return this.validaUsername() && this.validaPassword();
    }
    
    /**
     * Verifica se o username e a password de um utilizador recebido
     * são iguais aos do administrador.
     * 
     * @param u Utilizador a verificar
     * @return  true se for administrador
     *          false caso contrário
     */
    public boolean isAdministrador (Utilizador u) {
        return this.getUsername().equals(u.getUsername()) &&
               this.getPassword().equals(u.getPassword());
    }
}
