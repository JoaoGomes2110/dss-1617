/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.Arrays;
import java.util.GregorianCalendar;

/**
 *
 * @author Carlos Pereira
 */



public class Despesa {
    
    // Variáveis de instância
    
    private String info;
    private double valor;
    private TipoDespesa tipo;
    private GregorianCalendar dataEmissao;
    private GregorianCalendar dataLimite;
    private GregorianCalendar dataPagamento; // null para despesas por pagar
    
    private enum TipoDespesa {
        RECORRENTE, EXTRAORDINARIA
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
    
    public boolean equalsTipo (TipoDespesa t) {
        return tipo == t;
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
    
    public void setDataPagamento(GregorianCalendar g) { // confirmar gregorian
        dataPagamento = g;
    }
    
    
    //Métodos complementares comuns
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {info, valor, tipo, dataEmissao, dataLimite, dataPagamento});
    }
        
    @Override
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
    
    public Despesa clone() {
        return new Despesa(this);
    }
} 

