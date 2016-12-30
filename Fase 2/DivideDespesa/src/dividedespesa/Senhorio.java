/*
 * @brief Classe Senhorio. Contém métodos para criar e validar um objecto
 *        desta classe.
 *
 * @author Carlos Pereira   - A61887
 * @author João Barreira    - A73831
 * @author João Gomes       - A74033
 * @author João Reis        - A75372
 */

package dividedespesa;

public class Senhorio extends Utilizador {
    
    // Variáveis de instância
    
    private String nome;

    
    // Construtores
    
    /**
     * Construtor por parâmetros.
     * 
     * @param nome      Nome do senhorio
     * @param username  Username do senhorio
     * @param password  Password do senhorio
     */
    public Senhorio(String nome, String username, String password) {
        super(username, password);
        this.nome = nome;
    }
    
    /**
     * Construtor a partir de um objeto Senhorio.
     * 
     * @param s Senhorio
     */
    public Senhorio(Senhorio s) {
        super(s.getUsername(), s.getPassword());
        nome = s.getNome();
    }
    
    
    // Métodos de instância

    /**
     * Faz a validação do nome, username e password de um Senhorio.
     * Para isso verifica se estes parâmetros contêm carateres especiais.
     * 
     * @return true caso seja válido
     *         false caso contrário
     */
    public boolean validaSenhorio() {
        String [] special = {"/", "#", "$", "%", "&", ">", "<", "-", ","};
        boolean ret = true;

        for(String s : special) {
            if(nome.contains(s)) {
                ret = false;
                break;
            }
        }
        
        return ret && this.validaUsername() && this.validaPassword();
    }
    
    /**
     * Verifica se o username e a password de um utilizador recebido
     * são iguais aos do senhorio.
     * 
     * @param u Utilizador a verificar
     * @return  true se for senhorio
     *          false caso contrário
     */
    public boolean isSenhorio (Utilizador u) {
        System.out.println("CHEGOU AO isSenhorio");
        return this.getUsername().equals(u.getUsername()) &&
               this.getPassword().equals(u.getPassword());
    }
    
   
    // Getters
    
    /**
     * Devolve o parâmetro 'nome' de um Senhorio.
     * 
     * @return Parâmetro 'nome'
     */
    public String getNome() {
        return nome;
    }
}
