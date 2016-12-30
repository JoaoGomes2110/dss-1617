/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.Arrays;
import java.util.Date;

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
    
    private int id;
    private String info;
    private double valor;
    private TipoDespesa tipo;
    private Date dataEmissao;
    private Date dataLimite;
    private Date dataPagamento; // null para despesas por pagar
    
    
    //Construtores
    
    public Despesa (Despesa desp) {
        this.id = desp.getId();
        this.info = desp.getInfo();
        this.valor = desp.getValor();
        this.tipo = desp.getTipoDespesa();
        this.dataEmissao = desp.getDataEmissao();
        this.dataLimite = desp.getDataLimite();
    }
    
    public Despesa (int id, String info, double valor, String t,
                    Date dataEmissao, Date dataLimite,
                    Date dataPagamento) {
        
        this.id = id;
        this.info = new String(info);
        this.valor = valor;
        this.tipo = returnTipo(t);
        this.dataEmissao = dataEmissao;
        this.dataLimite = dataLimite;
        this.dataPagamento = dataPagamento;
    }
    
    //Métodos de instância
    
    public boolean equalsTipo(TipoDespesa t) {
        return tipo == t;
    }  

    public TipoDespesa returnTipo(String t) {
        TipoDespesa tipo;
        
        switch (t) {
            case "Recorrente":      tipo = TipoDespesa.RECORRENTE;
                                    break;
            case "Extraordinária":  tipo = TipoDespesa.EXTRAORDINARIA;
                                    break;
            default :               tipo = TipoDespesa.RENDA;
                                    break;
        }
        
        return tipo;
    }
    
    //Getters e Setters
    
    public int getId() {
        return id;
    }
    
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
            case RECORRENTE:        ret = new String("recorrente");
                                    break;
            case EXTRAORDINARIA:    ret = new String("extraordinaria");
                                    break;
            default:                ret = new String("renda");
                                    break;
        }
    
        return tipo.toString();
    }
    
    public Date getDataEmissao() {
        return dataEmissao;
    }
    
    public Date getDataLimite() {
        return dataLimite;
    }
    
    public Date getDataPagamento() {
        return dataPagamento;
    }
    
    public void setDataPagamento(Date g) {
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
        
        return (id == desp.getId() && dataLimite.equals(desp.getDataLimite()) &&
                dataEmissao.equals(desp.getDataEmissao()) &&
                valor == desp.getValor() && this.equalsTipo(desp.getTipoDespesa()));
    }
    
    public Despesa clone() {
        return new Despesa(this);
    }
} 

