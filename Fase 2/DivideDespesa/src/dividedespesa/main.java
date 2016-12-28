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

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new JanelaInicial(facade).setVisible(true);
            }
        });
    }
}
