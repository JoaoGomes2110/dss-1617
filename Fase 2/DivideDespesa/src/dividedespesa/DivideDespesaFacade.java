/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Carlos Pereira
 */
public class DivideDespesaFacade extends Observable {
    DivideDespesa dd;
    private List<Double> precos;
    
    public DivideDespesaFacade() {
        
        dd = new DivideDespesa();
        precos = new ArrayList<>();
    }
    
    
    public boolean validaSenhorio(String nome, String user, String pass) {
        Senhorio senhorio = new Senhorio(nome, user, pass);
        
        return senhorio.validaSenhorio();
    }
    
    public boolean validaAdministrador(String user, String pass) {
        Administrador admin = new Administrador(user, pass);
        
        return admin.validaAdministrador();
    }
    
    public boolean fieldSize(String usernameSenhorio, String usernameAdmin, 
                             String nomeSenhorio, String pswrdSenhorio,
                             String pswrdAdmin, String descApartamento) {
        
        return (usernameAdmin.length() == 0 || usernameSenhorio.length() == 0 ||
                nomeSenhorio.length() == 0 || pswrdSenhorio.length() == 0 ||
                pswrdAdmin.length() == 0 || descApartamento.length() == 0);
    }
    
    public void addToPrecos(String str) {
        double preco = Double.valueOf(str);
        this.precos.add(preco);
        this.setChanged();
        this.notifyObservers();
    }
    
    public void removeQuarto() {
        this.precos.remove(0);
        this.setChanged();
        this.notifyObservers();
    }
    
    
    public int getNumQuartos() {
        return precos.size();
    }
    
    
    public void registarApartamento(String usernameSenhorio, String usernameAdmin,
                                    String nomeSenhorio, String passSenhorio,
                                    String passAdmin, String descApartamento) {
        
        
        
        
    }
    
}
