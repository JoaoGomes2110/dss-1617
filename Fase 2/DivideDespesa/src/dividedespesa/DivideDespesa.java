/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import dividedespesa.database.DespesaDAO;
import dividedespesa.database.MoradorDAO;
import dividedespesa.database.QuartoDAO;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos Pereira, João Gomes, João Pires Barreira, João Reis
 */
public class DivideDespesa {
    
    // Variáveis de instância
    
    Apartamento apartamento; // apartamento registado no sistema
    Utilizador utilizador;   // utilizar com sessão iniciada
    private static QuartoDAO quartos_dao;
    private static MoradorDAO morador_dao;
    private static DespesaDAO despesas;
    
    // Construtores
    
    public DivideDespesa() {
        apartamento = null;
        utilizador = new Utilizador("root", "root");
    }
    
    public DivideDespesa(DivideDespesa dd) {
        apartamento = dd.getApartamento();
        utilizador = dd.getUtilizador();
    }
    
    // Métodos de instância
    
    public void addMorador(String username, String password, String nome,
                               List<Integer> qrts) {
        
        try {
            apartamento.addMorador(nome, username, password, qrts);
        } catch (MoradorExistenteException e) {
            
        }

    }
    
    public void autenticarUtilizador(String username, String password) throws SemAutorizacaoException {
        Utilizador u = new Utilizador(username, password);
        
        if (u.login(apartamento.getSenhorio())) {
            utilizador = u;
        } else {
            if (u.login(apartamento.getAdministrador())) {
                utilizador = u;
            } else {
                if (apartamento.getMoradores().containsKey(username)) {
                    if (apartamento.getMoradores().get(username).getPassword().equals(password)) {
                       utilizador = u; 
                    }
                }
            }
        }
        
        throw new SemAutorizacaoException();
    }
    
    public Collection<Despesa> verDespesasPorPagar(Morador m) {
        return apartamento.getMoradores().get(m.getUsername()).getDespesasPorPagar().values();
    }
    
    public Collection<Despesa> verDespesasPagas(Morador m) {
        return apartamento.getMoradores().get(m.getUsername()).getDespesasPagas().values();
    }
    
    public void registaApartamento(String info, Senhorio senhorio,
                                   Administrador admin, Map<Integer, 
                                   Double> precoQuartos ) {
        Map<Integer, Quarto> quartos = new HashMap<>();
        
        for (Integer i : precoQuartos.keySet()) {
            Quarto novo = new Quarto(i, precoQuartos.get(i), new HashSet<>());
                                   
            quartos.put(i, novo);
        }
        
        apartamento = new Apartamento(info, senhorio, admin, quartos);
    }
    
    public void cobrarRenda() {
        apartamento.cobrarRenda();
    }
    
    public void alteraRendaQuarto(int numQuarto, double valor) {
        apartamento.alteraRendaQuarto(numQuarto, valor);
    }
    
    // Getters e setters
    
    public Apartamento getApartamento() {
        return apartamento.clone(); 
    }
  
    public Utilizador getUtilizador() {
        return utilizador.clone();
    }
    
    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento.clone();
    }
    
    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador.clone();
    }
    
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */

  
    public static void main(String[] args) throws SQLException {
       despesas = new DespesaDAO();
       morador_dao = new MoradorDAO();
       
       if (morador_dao.get("gomes") == null)
           System.out.println("null");
       
       //System.out.println(m.toString());
     
    }
}
