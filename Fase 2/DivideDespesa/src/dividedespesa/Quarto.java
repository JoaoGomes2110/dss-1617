/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;


import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author Carlos Pereira, João Gomes, João Pires Barreira, João Reis
 */
public class Quarto {
    
    // Variáveis de instância
    
    private int numQuarto; // número do quarto
    private double preco;


    // Construtores
    
    public Quarto (int numQuarto, double preco) {

        this.numQuarto = numQuarto;
        this.preco = preco;
    }
    
    
    public Quarto (Quarto q) {
        this.numQuarto = q.getNumQuarto();
        this.preco = q.getPreco();

    }
    
     // Getters e Setters
    
    public int getNumQuarto() {
        return numQuarto;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public void setNumQuarto(int numQuarto) {
        this.numQuarto = numQuarto;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    //Métodos complementares comuns
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || (this.getClass() != o.getClass())) {
            return true;
        }
        
        Quarto q = (Quarto) o;
        
        return (q.getNumQuarto() == numQuarto && q.getPreco() == preco);
    }
    
    @Override
    public Quarto clone() {
        return new Quarto(this);
    }
}
