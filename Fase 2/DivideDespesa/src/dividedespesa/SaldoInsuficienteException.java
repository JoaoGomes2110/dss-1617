/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

/**
 *
 * @author win8
 */
class SaldoInsuficienteException extends Exception {

    public SaldoInsuficienteException() {
       super();
    }
  
    public SaldoInsuficienteException(String mensagem) {
       super(mensagem);
    }      
}
