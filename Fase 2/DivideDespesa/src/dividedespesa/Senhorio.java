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
public class Senhorio extends Utilizador {
    
    // Variáveis de instância
    
    private String nome;

    // Construtores
    
    public Senhorio() {
        
    }
    
    public Senhorio(String nome, String username, String password) {
        super(username, password);
        this.nome = nome;
    }
    
    // Métodos de instância
    
    public void adicionarMorador(Morador m, Quarto q) {
        
    }
    
    public void removeMorador(Morador m) {
        
    }    
    
    // e alterar quarto(s) de um morador?
    public void alteraDadosMorador(Morador m, String nome) {
        
    }
    
    public void registaApartamento(Apartamento apt) {
        
    }
    
    // o que acontece às rendas dos moradores desse apartamento?
    public void alteraRendaApartamento() {
        
    }
    
    // Getters e setters
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }


}
