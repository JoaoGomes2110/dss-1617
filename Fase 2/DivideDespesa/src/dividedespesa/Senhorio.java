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
    
    public Senhorio(String nome, String username, String password) {
        super(username, password);
        this.nome = nome;
    }
    
    public Senhorio(Senhorio s) {
        super(s.getUsername(), s.getPassword());
        nome = s.getNome();
    }
    
    // Métodos de instância

    public boolean validaSenhorio() {
        String [] special = {"/", "#", "$", "%", "&", ">", "<", "-", ","};
        boolean ret = true;

        for(String s : special) {
            if(nome.contains(s)) {
                ret = false;
                break;
            }
        }
        
        return ret && this.validaUsername() && this.validaPassword();
    }
    
    public boolean isSenhorio (Utilizador u) {
        System.out.println("CHEGOU AO isSenhorio");
        return this.getUsername().equals(u.getUsername()) &&
               this.getPassword().equals(u.getPassword());
    }
    
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
    
    //Métodos complementares comuns
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (this.getClass() != o.getClass() || o == null) {
            return false;
        }
        
        Senhorio s = (Senhorio) o;
        
        return (s.getNome().equals(this.getNome()) &&
                s.getUsername().equals(this.getUsername()) &&
                s.getPassword().equals(this.getPassword()));
    }
    
    public Senhorio clone() {
        return new Senhorio(this);
    }


}
