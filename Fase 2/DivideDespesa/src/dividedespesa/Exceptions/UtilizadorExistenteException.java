/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.Exceptions;

/**
 *
 * @author win8
 */
class UtilizadorExistenteException extends Exception {

    public UtilizadorExistenteException() {
       super();
   }
  
   public UtilizadorExistenteException(String mensagem) {
       super(mensagem);
   } 
    
}
