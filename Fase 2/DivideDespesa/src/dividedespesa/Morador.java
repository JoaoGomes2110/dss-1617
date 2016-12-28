/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
    private Date dataEntrada;
    private Date dataSaida;

    // Construtores
    
    public Morador(String username, String password, String nome) {
        super(username, password);
        this.setNome(nome);
        contaCorrente = new Conta();
        despesasPorPagar = new HashMap<>();
        despesasPagas = new HashMap<>();
        dataEntrada = new Date(); // data atual
        dataSaida = null;
    }
    
    public Morador(String username, String password, String nome, Conta contaCorrente, 
                   Map<Integer, Despesa> despesasPorPagar, Map<Integer, Despesa> despesasPagas, 
                   Date dataEntrada, Date dataSaida) {
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
        if (contaCorrente.getSaldo() >= d.getValor()) {
            contaCorrente.debito(d.getValor());
            despesasPagas.put(d.getId(), d.clone());
            despesasPorPagar.remove(d.getId(), d.clone());    
        } else {
            throw new SaldoInsuficienteException();
        }     
    }
    
    public void adicionarDespesaPorPagar(String info, double valor, String tipo,
                                         Date dataEmissao,
                                         Date dataLimite) {
        int id = despesasPorPagar.size() + 1;
        Despesa desp = new Despesa(id, info, valor, tipo, dataEmissao, dataLimite, null);
        despesasPorPagar.put(desp.getId(), desp.clone());
    }
    
    public void adicionarDespesaPaga(Despesa d) {
        Date data = new Date(); // data atual
        
        d.setDataPagamento(data);
        despesasPagas.put(d.getId(), d.clone());
        despesasPorPagar.remove(d.getId());
    }
    
    public void carregarConta(double valor) {
        contaCorrente.credito(valor);
    }
    
    
    public void cobrarRenda(double preco, int numQrt, int numMoradores, Date data) {
        
        Calendar c1 = Calendar.getInstance(); // pois classe Calendar é abstrata (não pode ser instanciada)
        c1.setTime(data);
        int mes = c1.get(Calendar.MONTH);
        
        Calendar c2 = Calendar.getInstance();
        c2.setTime(dataEntrada);
        int mesEntrada = c2.get(Calendar.MONTH);
        
        double parte = preco/((double) numMoradores);
        double precoFinal;
        
        if (mes == mesEntrada) {
            int semana = c2.get(Calendar.WEEK_OF_MONTH);
            precoFinal = parte/semana;
        } else {
            precoFinal = parte;
        }
        
        String info = "RENDA mes " + mes;
        
        adicionarDespesaPorPagar(info, precoFinal, "RENDA", data, data);
    }
    
    public boolean validaMorador() {
        String [] special = {" ", "/", "#", "$", "%", "&", ">", "<", "-", ","};
        boolean ret = true;

        for(String s : special) {
            if(nome.contains(s)) {
                ret = false;
                break;
            }
        }
        
        return ret && this.validaUsername() && this.validaPassword();
    }
    
    public boolean isMorador (Utilizador u) {
        return this.getUsername().equals(u.getUsername()) &&
               this.getPassword().equals(u.getPassword());
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
    
    public Date getDataEntrada() { return dataEntrada; }

    public Date getDataSaida() { return dataSaida; }
    
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
    
    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setDataSaida(Date dataSaida) {
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
