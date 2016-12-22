/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Carlos Pereira, João Gomes, João Pires Barreira, João Reis
 */
public class Quarto {
    
    // Variáveis de instância
    
    private int numQuarto; // número do quarto
    private int numMoradores;
    private double preco;
    private Map<String, Morador> moradores; // username - Morador
    
    // Construtores
    
    public Quarto (int numQuarto, int numMoradores, double preco) {
        this.numQuarto = numQuarto;
        this.numMoradores = numMoradores;
        this.preco = preco;
    }
    
    
    public Quarto (Quarto q) {
        this.numQuarto = q.getNumQuarto();
        this.numMoradores = q.getNumMoradores();
        this.preco = q.getPreco();
    }
    
    // Métodos de instância
    
    
    
    // Getters e Setters
    
    public int getNumQuarto() {
        return numQuarto;
    }
    
    public int getNumMoradores() {
        return numMoradores;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public void setNumQuarto(int numQuarto) {
        this.numQuarto = numQuarto;
    }
    
    public void setNumMoradores(int numMoradores) {
        this.numMoradores = numMoradores;
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
        
        return (q.getNumQuarto() == numQuarto && q.getNumMoradores() == numMoradores && q.getPreco() == preco);
    }
    
    @Override
    public Quarto clone() {
        return new Quarto(this);
    }
}
