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
 * @author Carlos Pereira
 */
public class Quarto {
    
    //Variáveis de instância
    
    private Map<Integer, Morador> moradores;
    private double preco;
    private int num;

    
    //Construtores
    
    public Quarto (int num, double preco) {
        this.preco = preco;
        this.num = num;
        this.moradores = new HashMap<>();
    }
    
    
    public Quarto (Quarto qrt) {
        this.preco = qrt.getPreco();
        this.num = qrt.getNum();
        this.moradores = qrt.getMoradores();
    }
    
    //Métodos de instância
    
    
    
    //Gets e Sets
    
    public int getNum() {
        return num;
    }
    
    
    public double getPreco() {
        return preco;
    }
    
    
    public Map<Integer, Morador> getMoradores() {
        return new HashMap<>(moradores);
    }
    
    public void setNum(int num) {
        this.num = num;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public void setMoradores(Map<Integer, Morador> m) {
        moradores = m;
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
        
        return (q.getNum() == num);
    }
}
