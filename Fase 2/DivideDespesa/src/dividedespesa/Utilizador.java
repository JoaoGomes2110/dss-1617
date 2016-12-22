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
    

    public void validaUsername(String user) throws CarateresEspeciaisException {
        Pattern ptrn = Pattern.compile(" /#$%&><-,");
        Matcher mtchr = ptrn.matcher(user);
        
        if (mtchr.find()) {
            throw new CarateresEspeciaisException();
        }
    }
    
    
    public void validaPassword(String pswrd) throws CarateresEspeciaisException {
        Pattern ptrn = Pattern.compile(" /#$%&><-,");
        Matcher mtchr = ptrn.matcher(pswrd);
        
        if (mtchr.find()) {
            throw new CarateresEspeciaisException();
        }
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
    
    public boolean equalsUsername(Object o) {        
        return username.equals(o);
    }
    
    // Métodos complementares comuns
    
    public Utilizador clone() {
        return new Utilizador(this);
    }
}
