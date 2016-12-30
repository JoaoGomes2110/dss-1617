/*
 * @brief Classe principal do projeto DivideDespesa que constrói a
 *        Facade e chama a interface de utilizador.
 *
 * @author Carlos Pereira   - A61887
 * @author João Barreira    - A73831
 * @author João Gomes       - A74033
 * @author João Reis        - A75372
 */

package dividedespesa;

import Interface.JanelaInicial;

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
