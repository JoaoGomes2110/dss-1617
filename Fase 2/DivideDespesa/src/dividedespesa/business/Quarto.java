/*
 * @brief Classe Quarto. Contém métodos para criar um objecto desta
 *        classe.
 *
 * @author Carlos Pereira   - A61887
 * @author João Barreira    - A73831
 * @author João Gomes       - A74033
 * @author João Reis        - A75372
 */

package dividedespesa.business;

public class Quarto {
    
    // Variáveis de instância
    
    private int idQuarto;   // id do quarto
    private double preco;   // preço do quarto
    
    
    // Construtores
    
    /**
     * Construtor por parâmetros.
     * 
     * @param idQuarto id do quarto
     * @param preco     preço do quarto
     */
    public Quarto (int idQuarto, double preco) {
        this.idQuarto = idQuarto;
        this.preco = preco;
    }
    
    /**
     * Construtor através de um objeto Quarto.
     * 
     * @param q Quarto
     */
    public Quarto (Quarto q) {
        this.idQuarto = q.getidQuarto();
        this.preco = q.getPreco();

    }
    
    
    // Getters e Setters
    
    /**
     * Devolve o id do quarto.
     * 
     * @return idQuarto
     */
    public int getidQuarto() {
        return idQuarto;
    }
    
    /**
     * Devolve o preço do quarto.
     * 
     * @return preço do quarto
     */
    public double getPreco() {
        return preco;
    }
    
    /**
     * Faz o set ao id do quarto.
     * 
     * @param idQuarto 
     */
    public void setidQuarto(int numQuarto) {
        this.idQuarto = numQuarto;
    }
    
    /**
     * Faz o set ao preço do quarto.
     * 
     * @param preco preço do quarto
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }
}
