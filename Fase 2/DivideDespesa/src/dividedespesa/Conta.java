/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

/**
 *
 * @author Carlos Pereira, João Gomes, João Pires Barreira, João Reis
 */
public class Conta {
    
    // Variáveis de instância
    private double saldo;
     
    // Construtores
    
    public Conta() {
        saldo = 0;
    }
    
    public Conta(Conta c) {
        saldo = c.getSaldo();
    }
    
    public Conta(Double saldo){
        this.saldo = saldo;
    }
    
    // Métodos de instância
   
    public void debito(double valor) {
        saldo -= valor;
    }
    
    public void credito(double valor) {
        saldo += valor;
    }
    
    // Getters e Setters
    
    public double getSaldo() {
        return this.saldo;
    }
    
    public void setSaldo(double valor) {
        saldo = valor;
    }
    
    // Métodos complementares comuns
    
    public Conta clone() {
        return new Conta(this);
    }
    
}
