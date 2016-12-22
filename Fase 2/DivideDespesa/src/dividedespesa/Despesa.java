/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.Arrays;
import java.text.SimpleDateFormat;

/**
 *
 * @author Carlos Pereira
 */

    

public class Despesa {
    
    // Variáveis de classe
    
    public enum TipoDespesa {
        RECORRENTE, EXTRAORDINARIA, RENDA        
    };    

    
    // Variáveis de instância
    
    private String info;
    private double valor;
    private TipoDespesa tipo;
    private SimpleDateFormat dataEmissao;
    private SimpleDateFormat dataLimite;
    private SimpleDateFormat dataPagamento; // null para despesas por pagar
    
    
    //Construtores
    
    public Despesa (Despesa desp) {
        this.info = desp.getInfo();
        this.valor = desp.getValor();
        this.tipo = desp.getTipoDespesa();
        this.dataEmissao = desp.getDataEmissao();
        this.dataLimite = desp.getDataLimite();
    }
    
    public Despesa (String info, double valor, String t,
                    SimpleDateFormat dataEmissao,
                    SimpleDateFormat dataLimite) {
        
        
        this.info = new String(info);
        this.valor = valor;
        this.tipo = returnTipo(t);
        this.dataEmissao = dataEmissao;
        this.dataLimite = dataLimite;
        dataPagamento = null;
    }
    

    
    
    //Métodos de instância
    
    public boolean equalsTipo(TipoDespesa t) {
        return tipo == t;
    }  
    
    
    public TipoDespesa returnTipo(String t) {
        TipoDespesa tipo;
        
        switch (t) {
            case "recorrente":  tipo = TipoDespesa.RECORRENTE;
                                break;
            case "extra":       tipo = TipoDespesa.EXTRAORDINARIA;
                                break;
            default :           tipo = TipoDespesa.RENDA;
                                break;
        }
        
        return tipo;
    }
    
    //Getters e Setters
    
    public String getInfo() {
        return new String(info);
    }
    
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
    
    public SimpleDateFormat getDataEmissao() {
        return dataEmissao;
    }
    
    public SimpleDateFormat getDataLimite() {
        return dataLimite;
    }
    
    public void setDataPagamento(SimpleDateFormat g) { // confirmar gregorian
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

