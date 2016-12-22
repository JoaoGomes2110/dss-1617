/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author Carlos Pereira, João Gomes, João Pires Barreira, João Reis
 */
public class Morador extends Utilizador {
    
    // Variáveis de instância
    
    private String nome;
    private Conta contaCorrente;
    private Map<Integer, Despesa> despesasPorPagar;
    private Map<Integer, Despesa> despesasPagas;
    private List<Quarto> quartos;
    private GregorianCalendar dataEntrada;
    private GregorianCalendar dataSaida;

    // Construtores
    
    public Morador(String username, String password, String nome, Conta contaCorrente, 
                   Map<Integer, Despesa> despesasPorPagar, Map<Integer, Despesa> despesasPagas, 
                   List<Quarto> quartos, GregorianCalendar dataEntrada, GregorianCalendar dataSaida) {
        super(username, password);
        this.setNome(nome);
        this.contaCorrente = contaCorrente.clone();
        this.setDespesasPorPagar(despesasPorPagar);
        this.setDespesasPagas(despesasPagas);
        this.setQuartos(quartos);
        this.setDataEntrada(dataEntrada);
        this.setDataSaida(dataSaida);
    }

    public Morador(Morador m) {
        super(m.getUsername(), m.getPassword());
        nome = m.getNome();
        contaCorrente = m.getContaCorrente();
        despesasPorPagar = m.getDespesasPorPagar();
        despesasPagas = m.getDespesasPagas();
        quartos = m.getQuartos();
        dataEntrada = m.getDataEntrada();
        dataSaida = m.getDataSaida();
    }
        
    public boolean validaMorador(Utilizador morador) {
        return true;
    }
    
    // Métodos de instância
    
    public void pagarDespesa(Despesa d) throws SaldoInsuficienteException {
        if (contaCorrente.debito(d.getValor())) {
            despesasPagas.put(d.hashCode(), d.clone());
            despesasPorPagar.remove(d.hashCode(), d.clone());    
        } else {
            throw new SaldoInsuficienteException();
        }     
    }
    
    public void adicionarDespesaPaga(Despesa d) {
        GregorianCalendar gc = new GregorianCalendar();            
        String ano = String.valueOf(gc.get(GregorianCalendar.YEAR));
        String mes = String.valueOf(gc.get(GregorianCalendar.MONTH));            
        String dia = String.valueOf(gc.get(GregorianCalendar.DAY_OF_MONTH));
        gc.set(Integer.getInteger(ano), Integer.getInteger(mes), Integer.getInteger(dia));
        
        d.setDataPagamento(gc);
        despesasPagas.put(d.hashCode(), d.clone());
        despesasPorPagar.remove(d.hashCode());
    }
    
    public void carregarConta(double valor) {
        contaCorrente.credito(valor);
    }
    
    // Getters e setters
    
    public String getNome() { return nome; }
    
    public Conta getContaCorrente() { return contaCorrente.clone(); }
    
    public Map<Integer, Despesa> getDespesasPorPagar() {
        Map<Integer, Despesa> temp = new HashMap<>();
        
        for (Despesa d : despesasPorPagar.values()) {
            temp.put(d.hashCode(), d.clone());
        }
        
        return temp;
    }
    
    public Map<Integer, Despesa> getDespesasPagas() {
        Map<Integer, Despesa> temp = new HashMap<>();
        
        for (Despesa d : despesasPagas.values()) {
            temp.put(d.hashCode(), d.clone());
        }
        
        return temp;
    }
    
    public List<Quarto> getQuartos() {
        return quartos.stream().map(Quarto::clone).collect(Collectors.toList());
    }
    
    public GregorianCalendar getDataEntrada() { return dataEntrada; }

    public GregorianCalendar getDataSaida() { return dataSaida; }
    
    public void setNome(String nome) { this.nome = nome; }
    
    public void setContaCorrente(Conta contaCorrente) {
        this.contaCorrente = contaCorrente.clone();
    }
    
    public void setDespesasPorPagar(Map<Integer, Despesa> despesasPorPagar) {
        this.despesasPorPagar = despesasPorPagar.values()
                                                .stream()
                                                .collect(toMap(Despesa::hashCode, Despesa::clone));        
    }
    
    public void setDespesasPagas(Map<Integer, Despesa> despesasPagas) {
        this.despesasPagas = despesasPagas.values()
                                          .stream()
                                          .collect(toMap(Despesa::hashCode, Despesa::clone));        
    }
    
    public void setQuartos(List<Quarto> quartos) {
        this.quartos = quartos.stream().map(Quarto::clone)
                                       .collect(Collectors.toList());
    }
    
    public void setDataEntrada(GregorianCalendar dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setDataSaida(GregorianCalendar dataSaida) {
        this.dataSaida = dataSaida;
    }
    
    // Métodos complementares comuns
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {nome, contaCorrente, despesasPorPagar, despesasPagas, quartos, dataEntrada, dataSaida});
    }
    
    @Override
    public Morador clone() {
        return new Morador(this);
    }
    
}
