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
    
    
    //Variáveis de instância
    
    private double valor;
    private TipoDespesa tipo;
    private GregorianCalendar dataEmissao;
    private GregorianCalendar dataLimite;
    
    private enum TipoDespesa {
        RECORRENTE, EXTRAORDINARIA, RENDA
    }
    
    //Construtores
    
    public Despesa (Despesa desp) {
        this.valor = desp.getValor();
        this.tipo = desp.getTipoDespesa();
        this.dataEmissao = desp.getDataEmissao();
        this.dataLimite = desp.getDataLimite();
    }
    
    public Despesa (double valor, TipoDespesa tipo,
                    GregorianCalendar dataEmissao,
                    GregorianCalendar dataLimite) {
        
        this.valor = valor;
        this.tipo = tipo;
        this.dataEmissao = dataEmissao;
        this.dataLimite = dataLimite;
    }
    
    //Gets e Sets
    
    public double getValor() {
        return valor;
    }
    
    public TipoDespesa getTipoDespesa() {
        return tipo;
    }
    
    public String getTipoDespesaString() {
        String ret;
        
        switch(tipo) {
            case RECORRENTE:        ret = new String("Recorrente");
                                    break;
            case EXTRAORDINARIA:    ret = new String("Extraordinaria");
                                    break;
            default:                ret = new String("Renda");
                                    break;
        }
    
        return tipo.toString();
    }
    
    public GregorianCalendar getDataEmissao() {
        return dataEmissao;
    }
    
    public GregorianCalendar getDataLimite() {
        return dataLimite;
    }
    
    
    //Métodos complementares comuns
    
    
    public boolean equalsTipo (TipoDespesa t) {
        return tipo == t;
    }
    
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        
        Despesa desp = (Despesa) o;
        
        return (dataLimite.equals(desp.getDataLimite()) &&
                dataEmissao.equals(desp.getDataEmissao()) &&
                valor == desp.getValor() && this.equalsTipo(desp.getTipoDespesa()));
    }
} 

