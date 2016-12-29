/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import Interface.JanelaInicial;



/**
 *
 * @author Carlos Pereira
 */
public class main {
    
    static DivideDespesaFacade facade;
    
    public static void main(String[] args) {
        facade = new DivideDespesaFacade();

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new JanelaInicial(facade).setVisible(true);
            }
        });
    }
}
