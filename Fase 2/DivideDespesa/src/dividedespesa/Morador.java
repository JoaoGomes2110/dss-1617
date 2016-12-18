/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Carlos Pereira
 */
public class Morador extends Utilizador {
    
    //Variáveis de instância
    
    private Map<Integer, Despesa> despesasPorPagar;
    private Map<Integer, Despesa> despesasPagas;
    private Conta contaCorrente;
    private String nome;

    //Construtores
    
    public Morador(String nome, String username, String password) {
        super(username, password);
        contaCorrente = new Conta();
        despesasPorPagar = new HashMap<>();
        despesasPagas = new HashMap<>();
        this.nome = nome;
    }

    public Morador(Morador m) {
        super(m.getUsername(), m.getPassword());
        contaCorrente = m.getContaCorrente();
        despesasPorPagar = m.getDespesasPorPagar();
        despesasPagas = m.getDespesasPagas();
        nome = m.getNome();
    }
    
    
    
    public boolean validaMorador(Utilizador morador) {
        return true;
    }
    
    //Gets e Sets
    
    public Map<Integer, Despesa> getDespesasPorPagar() {
        return new HashMap<>(despesasPorPagar);
    }
    
    public Map<Integer, Despesa> getDespesasPagas() {
        return new HashMap<>(despesasPagas);
    }
    
    public Conta getContaCorrente() {
        return new Conta (contaCorrente);
    }
    
    public String getNome() {
        return nome;
    }
    
    
    //Métodos complementares comuns
    
    
}
