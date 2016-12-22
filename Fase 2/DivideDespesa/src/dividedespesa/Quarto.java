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
    private Set<String> moradores; // lista dos usernames dos moradores do quarto


    // Construtores
    
    public Quarto (int numQuarto, double preco, Set<String> moradores) {

        this.numQuarto = numQuarto;
        this.preco = preco;
        this.moradores = new HashSet<>(moradores);
        this.moradores = moradores;
    }
    
    
    public Quarto (Quarto q) {
        this.numQuarto = q.getNumQuarto();
        this.preco = q.getPreco();
        this.moradores = q.getMoradores();
    }
    
    // Métodos de instância
    

    public void addMorador(String morador) {
        if (!moradores.contains(morador)) {
            moradores.add(morador);
        }
    }
    
    public boolean containsUsername(String user) {
        return moradores.contains(user);
    }
    
    public int getNumMoradores() {
        return moradores.size();
    }
    

    
    // Getters e Setters
    
    public int getNumQuarto() {
        return numQuarto;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public Set<String> getMoradores() {
        return new HashSet<>(moradores);
    }

    
    public void setNumQuarto(int numQuarto) {
        this.numQuarto = numQuarto;
    }


    public void setMoradores(Set<String> moradores) {
        this.moradores = new HashSet<>(moradores);    
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
