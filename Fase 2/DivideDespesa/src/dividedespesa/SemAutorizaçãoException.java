/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

/**
 *
 * @author JPB
 */
class SemAutorizacaoException extends Exception {
   
    public SemAutorizacaoException() {
       super();
   }
  
   public SemAutorizacaoException(String mensagem) {
       super(mensagem);
   }    
}
