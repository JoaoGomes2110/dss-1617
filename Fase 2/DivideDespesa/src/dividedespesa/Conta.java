/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

/**
 *
 * @author Carlos Pereira
 */
public class Conta {
    
    //Variáveis de instância
    private double valor;
    
    //Construtores
    
    public Conta() {
        valor = 0;
    }
    
    public Conta(Conta c) {
        valor = c.getValor();
    }
    
    //Métodos de instância
    
    
    
    
    //Gets e Sets
    
    public double getValor() {
        return this.valor;
    }
    
    public void setValor(double v) {
        valor = v;
    }
    
    //Métodos complementares comuns
    
    
}
