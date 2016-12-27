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
public class DivideDespesaFacade {
    DivideDespesa dd;
    
    public void DivideDespesaFacade() {
        dd = new DivideDespesa();
    }
    
    
    public boolean validaDadosLogin(String username, String password) {
        
        Utilizador aux = new Utilizador(username, password);
        boolean ret = false;
        
        try {
            aux.validaUsername();
            aux.validaPassword();
            
            ret = true;
        } catch (CarateresEspeciaisException e) {
            //ret = false;
        }
        
        return ret; 
    }
    
    public boolean fieldSize(String usernameSenhorio, String usernameAdmin, 
                             String nomeSenhorio, String nomeAdmin,
                             String pswrdSenhorio, String pswrdAdmin,
                             String descApartamento) {
        
        return (usernameAdmin.length() == 0 || usernameSenhorio.length() == 0 ||
                nomeSenhorio.length() == 0 || nomeAdmin.length() == 0 ||
                pswrdSenhorio.length() == 0 || pswrdAdmin.length() == 0 ||
                descApartamento.length() == 0);
    }
    
    
    
    
}
