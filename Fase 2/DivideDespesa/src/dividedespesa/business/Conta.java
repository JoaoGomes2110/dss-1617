/*
 * @brief Classe Conta. Contém métodos para criar um objecto desta classe.
 *
 * @author Carlos Pereira   - A61887
 * @author João Barreira    - A73831
 * @author João Gomes       - A74033
 * @author João Reis        - A75372
 */

package dividedespesa.business;

public class Conta {
    
    //Variáveis de instância
    
    private double saldo;
    
    
    //Construtores
    
    /**
     * Construtor vazio.
     */
    public Conta() {
        saldo = 0;
    }
    
    /**
     * Construtor através de um objeto Conta.
     * @param c Conta
     */
    public Conta(Conta c) {
        saldo = c.getSaldo();
    }
    
    /**
     * Construtor por parâmetros.
     * @param saldo Saldo na conta
     */
    public Conta(Double saldo){
        this.saldo = saldo;
    }

    
    // Getters e Setters
    
    /**
     * Devolve o saldo na conta.
     * 
     * @return Saldo na conta
     */
    public double getSaldo() {
        return this.saldo;
    }
    
    /**
     * Faz o set no saldo da conta.
     * 
     * @param valor Novo valor do saldo 
     */
    public void setSaldo(double valor) {
        saldo = valor;
    }
    
    
    //Métodos complementares comuns
    
    /**
     * Faz o clone do objeto Conta.
     * 
     * @return Clone do objeto
     */
    public Conta clone() {
        return new Conta(this);
    }
}
