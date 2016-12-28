/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import Interface.AdicionarMorador;
import Interface.AlterarDadosMorador;
import Interface.JanelaInicial;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;


/**
 *
 * @author Carlos Pereira
 */
public class main {
    
    static DivideDespesaFacade facade;
    
    public static void main(String[] args) {
        facade = new DivideDespesaFacade();
        
        /*
        
        for(int i = 0; i < 10; i++) {
            facade.addToPrecos(Integer.toString(i*2 + 1));
        }
        */
        facade.registaApartamento("senhorio", "usernameAdmin",
                                   "senhorio", "senhorio", "passAdmin",
                                   "descApartamento");
        
        /*
        
        
        List<Integer> aux = new ArrayList<>();
        
        aux.add(1);
        
        facade.addMorador("nome", "username", "password", aux);
        
        aux.add(2); aux.add(4);
        facade.addMorador("Carlos", "userCarlos", "password", aux);
        facade.addMorador("Carlos", "user", "password", aux);
        facade.addMorador("Carlos", "carlos", "password", aux);
        */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new JanelaInicial(facade).setVisible(true);
                //new AlterarDadosMorador(new javax.swing.JFrame(), true, facade).setVisible(true);
            }
        });
    }
}
