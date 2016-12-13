/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.GregorianCalendar;

/**
 *
 * @author Carlos Pereira
 */



public class Despesa {
    private double valor;
    private GregorianCalendar dataEmissao;
    private GregorianCalendar dataLimite;
    private TipoDespesa tipo;
    
    
    private enum TipoDespesa {
        RECORRENTE, EXTRAORDINARIA, RENDA
    }
    
}

