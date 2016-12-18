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
public class Utilizador {
    private String username;
    private String password;

    public Utilizador(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public boolean equalsUsername(Object o) {        
        return username.equals(o);
    }
}
