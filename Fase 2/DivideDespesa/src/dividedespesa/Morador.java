/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;
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
    private SimpleDateFormat dataEntrada;
    private SimpleDateFormat dataSaida;

    // Construtores
    
    public Morador(String username, String password, String nome) {
        super(username, password);
        this.setNome(nome);
        contaCorrente = new Conta();
        despesasPorPagar = new HashMap<>();
        despesasPagas = new HashMap<>();
        dataEntrada = new SimpleDateFormat(); // data atual
        dataSaida = null;
    }
    
    public Morador(String username, String password, String nome, Conta contaCorrente, 
                   Map<Integer, Despesa> despesasPorPagar, Map<Integer, Despesa> despesasPagas, 
                   SimpleDateFormat dataEntrada, SimpleDateFormat dataSaida) {
        super(username, password);
        this.setNome(nome);
        this.contaCorrente = contaCorrente.clone();
        this.setDespesasPorPagar(despesasPorPagar);
        this.setDespesasPagas(despesasPagas);
        this.setDataEntrada(dataEntrada);
        this.setDataSaida(dataSaida);
    }

    public Morador(Morador m) {
        super(m.getUsername(), m.getPassword());
        nome = m.getNome();
        contaCorrente = m.getContaCorrente();
        despesasPorPagar = m.getDespesasPorPagar();
        despesasPagas = m.getDespesasPagas();
        dataEntrada = m.getDataEntrada();
        dataSaida = m.getDataSaida();
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
    
    public void adicionarDespesaPorPagar(String info, double valor, String tipo,
                                         SimpleDateFormat dataEmissao,
                                         SimpleDateFormat dataLimite) {
        int id = despesasPorPagar.size() + 1;
        Despesa desp = new Despesa(id, info, valor, tipo, dataEmissao, dataLimite, null);
        despesasPorPagar.put(desp.hashCode(), desp.clone());
    }
    
    public void adicionarDespesaPaga(Despesa d) {
        SimpleDateFormat gc = new SimpleDateFormat(); // data atual
        
        d.setDataPagamento(gc);
        despesasPagas.put(d.hashCode(), d.clone());
        despesasPorPagar.remove(d.hashCode());
    }
    
    public void carregarConta(double valor) {
        contaCorrente.credito(valor);
    }
    
    
    public void cobrarRenda(double preco, int numQrt, int numMrds,
                            SimpleDateFormat data) {
        String info = "RENDA mes ";
       
        double parte = preco/((double) numMrds);
        
        adicionarDespesaPorPagar(info, parte, "RENDA", data, data);
    }
    
    
    // Getters e setters
    
    public String getNome() {
        return nome;
    }
    
    public Conta getContaCorrente() {
        return contaCorrente.clone();
    }
    
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
    
    public SimpleDateFormat getDataEntrada() { return dataEntrada; }

    public SimpleDateFormat getDataSaida() { return dataSaida; }
    
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
    
    public void setDataEntrada(SimpleDateFormat dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setDataSaida(SimpleDateFormat dataSaida) {
        this.dataSaida = dataSaida;
    }
    
    // Métodos complementares comuns
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {nome, contaCorrente, despesasPorPagar, despesasPagas, dataEntrada, dataSaida});
    }
    
    @Override
    public Morador clone() {
        return new Morador(this);
    }
    
}
