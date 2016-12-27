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
public class Administrador extends Utilizador {
    
    
    //Construtores
    
    public Administrador(String username, String password) {
        super(username, password);
    }
    
    public Administrador(Administrador admin) {
        super(admin.getUsername(), admin.getPassword());
    }
    
    //Métodos de instância
    
    public boolean validaAdministrador() {
        return this.validaUsername() && this.validaPassword();
    }
    
    //Métodos complementares comuns

    
    public boolean equals(Object o) {
        
        if (this == o) {
            return true;
        }
        
        if (this.getClass() != o.getClass() || o == null) {
            return false;
        }
        
        Administrador admin = (Administrador) o;
        
        String user = this.getUsername();
        String pswd = this.getPassword();
        
        return user.equals(admin.getUsername()) &&
               pswd.equals(admin.getPassword());
    }
    
    public Administrador clone() {
        return new Administrador(this);
    }   
}
