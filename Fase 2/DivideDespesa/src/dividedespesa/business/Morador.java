/*
 * @brief Classe Morador. Contém métodos para criar e validar um objecto
 *        desta classe.
 *
 * @author Carlos Pereira   - A61887
 * @author João Barreira    - A73831
 * @author João Gomes       - A74033
 * @author João Reis        - A75372
 */
package dividedespesa.business;

import java.util.Date;

public class Morador extends Utilizador {
    
    // Variáveis de instância
    
    private String nome;
    private Conta contaCorrente;
    private Date dataEntrada;
    private Date dataSaida; // data da saída do Morador do apt (null se não saiu)

    // Construtores
    
    /**
     * Construtor por parâmetros.
     * 
     * @param username username do morador
     * @param password password do morador
     * @param nome     nome do morador
     */    
    public Morador(String username, String password, String nome) {
        super(username, password);
        this.setNome(nome);
        contaCorrente = new Conta();
        dataEntrada = new Date(); // data atual
        dataSaida = null;
    }
    
     /**
     * Construtor por parâmetros.
     * 
     * @param username    username do morador
     * @param password    password do morador
     * @param nome        nome do morador
     * @param Conta       conta corrente
     * @param dataEntrada data de entrada
     * @param dataSaida   data de saída
     */    
    public Morador(String username, String password, String nome, Conta contaCorrente,
                   Date dataEntrada, Date dataSaida) {
        super(username, password);
        this.setNome(nome);
        this.contaCorrente = contaCorrente.clone();
        this.setDataEntrada(dataEntrada);
        this.setDataSaida(dataSaida);
    }

    /**
     * Construtor através de um objeto Morador.
     * 
     * @param m Morador
     */
    public Morador(Morador m) {
        super(m.getUsername(), m.getPassword());
        nome = m.getNome();
        contaCorrente = m.getContaCorrente();
        dataEntrada = m.getDataEntrada();
        dataSaida = m.getDataSaida();
    }
    
    // Métodos de instância
    
    /**
     * Faz a validação do nome, username e password de um Morador.
     * Para isso verifica se estes parâmetros contêm carateres especiais.
     * 
     * @return true caso seja válido
     *         false caso contrário
     */    
    public boolean validaMorador() {
        String [] special = { "/", "#", "$", "%", "&", ">", "<", "-", ","};
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
     * são iguais aos do morador.
     * 
     * @param u Utilizador a verificar
     * @return  true se for morador
     *          false caso contrário
     */   
    public boolean isMorador (Utilizador u) {
        return this.getUsername().equals(u.getUsername()) &&
               this.getPassword().equals(u.getPassword());
    }
    
    // Getters e setters
    
    /**
     * Devolve o nome do Morador.
     * 
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Devolve a conta corrente do Morador.
     * 
     * @return contaCorrente
     */    
    public Conta getContaCorrente() {
        return contaCorrente.clone();
    }

    /**
     * Devolve a data de entrada do Morador no apartamento.
     * 
     * @return dataEntrada
     */
    public Date getDataEntrada() { 
        return dataEntrada; 
    }

    /**
     * Devolve a data da saída do Morador do apartamento.
     * 
     * @return dataSaida
     */
    public Date getDataSaida() { 
        return dataSaida; 
    }
    
    /**
     * Faz o set ao nome do Morador.
     * 
     * @param nome
     */    
    public void setNome(String nome) { 
        this.nome = nome;
    }
    
    /**
     * Faz o set à conta corrente do Morador.
     * 
     * @param contaCorrente Clone da conta
     */    
    public void setContaCorrente(Conta contaCorrente) {
        this.contaCorrente = contaCorrente.clone();
    }

    /**
     * Faz o set à data de entrada do Morador.
     * 
     * @param dataEntrada 
     */    
    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    /**
     * Faz o set à data de saída do Morador.
     * 
     * @param dataSaida
     */        
    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }
    
    // Métodos complementares comuns

    
    /**
     * Faz o clone do objeto Morador.
     * 
     * @return Clone do objeto
     */    
    @Override
    public Morador clone() {
        return new Morador(this);
    }
    
}
