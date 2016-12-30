/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.business.exceptions;

/**
 *
 * @author Carlos Pereira
 */
public class RendaCobradaException extends Exception {

    /**
     * Creates a new instance of <code>RendaCobradaException</code> without
     * detail message.
     */
    public RendaCobradaException() {
        super();
    }

    /**
     * Constructs an instance of <code>RendaCobradaException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RendaCobradaException(String msg) {
        super(msg);
    }
}
