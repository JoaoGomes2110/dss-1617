/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.business.exceptions;

/**
 *
 * @author win8
 */
public class SaldoInsuficienteException extends Exception {

    public SaldoInsuficienteException() {
       super();
    }
  
    public SaldoInsuficienteException(String mensagem) {
       super(mensagem);
    }      
}
