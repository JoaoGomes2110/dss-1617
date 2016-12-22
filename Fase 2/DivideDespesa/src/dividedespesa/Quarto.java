/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Carlos Pereira, João Gomes, João Pires Barreira, João Reis
 */
public class Quarto {
    
    // Variáveis de instância
    
    private int numQuarto;          // número do quarto
    private double preco;
    private List<String> moradores; // lista dos usernames dos moradores do quarto
    
    // Construtores
    
    public Quarto (int numQuarto, double preco, List<String> moradores) {
        this.numQuarto = numQuarto;
        this.preco = preco;
        this.moradores = new ArrayList<>(moradores);
    }
    
    
    public Quarto (Quarto q) {
        numQuarto = q.getNumQuarto();
        preco = q.getPreco();
        moradores = q.getMoradores();
    }
    
    // Métodos de instância
    
    public void addMorador() {
        
    }
    
    // Getters e Setters
    
    public int getNumQuarto() {
        return numQuarto;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public List<String> getMoradores() {
        return new ArrayList<>(moradores);
    }
    
    public void setNumQuarto(int numQuarto) {
        this.numQuarto = numQuarto;
    }
   
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public void setMoradores(List<Morador> moradores) {
        this.moradores = moradores.stream().map(Morador::clone).collect(Collectors.toList());
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
