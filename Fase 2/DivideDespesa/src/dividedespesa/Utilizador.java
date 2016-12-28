/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Carlos Pereira, João Gomes, João Pires Barreira, João Reis
 */
public class Utilizador {
    
    // Variávies de instância
    
    private String username;
    private String password;

    // Construtores
    
    public Utilizador(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public Utilizador(Utilizador u) {
        username = u.getUsername();
        password = u.getPassword();
    }    
    
    // Métodos de instância
    

    public boolean validaUsername()  {
        String [] special = {"»", "«", ":", "*", " ", "/", "#", "$", "%", "&",
                             ">", "<", "-", ",", "=", "|", "\\", "!", "\"\""};
        boolean ret = true;

        for(String s : special) {
            if(username.contains(s)) {
                ret = false;
                break;
            }
        }
        
        return ret;
    }
    
    
    public boolean validaPassword() {
        String [] special = {"»", "«", ":", "*", " ", "/", "#", "$", "%", "&",
                             ">", "<", "-", ",", "=", "|", "\\", "!", "\"\""};
        boolean ret = true;

        for(String s : special) {
            if(password.contains(s)) {
                ret = false;
                break;
            }
        }
        
        return ret;
    }
    
    public boolean login(Utilizador u) {
        return username.equals(u.getUsername()) &&
               password.equals(u.getPassword());
    }
    
    
    
    // Getters e setters
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setUsername(String usr) {
        username = usr;
    }
    
    public void setPassword(String pass) {
        password = pass;
    }
    
    
    // Métodos complementares comuns
    
    public boolean equalsUsername(Object o) {        
        return username.equals(o);
    }
    
    public Utilizador clone() {
        return new Utilizador(this);
    }
}
