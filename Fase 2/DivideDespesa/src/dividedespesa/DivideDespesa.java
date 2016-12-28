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
    
    private Apartamento apartamento; // apartamento registado no sistema
    private Utilizador utilizador;   // utilizar com sessão iniciada
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
                               List<Integer> qrts) throws MoradorExistenteException {
        apartamento.addMorador(nome, username, password, qrts);
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
                                   Double> precoQuartos) {
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
    
    public void alterarPasswordMorador(String username, String password) {
        apartamento.getMoradores().get(username).setPassword(password);
    }
    
    public void alterarQuartosMorador(String username, List<Integer> quartos) {
        apartamento.getQuartos().forEach((i, q) -> {
                                          if(quartos.contains(i)) {
                                              q.addMorador(username);
                                          } else {
                                              q.takeMorador(username);
                                          }
                                        });
    }
    
    public boolean isMorador(String username, String password) {
        boolean ret = false;
        Utilizador login = new Utilizador(username, password);
        
        for(Morador m : apartamento.getMoradores().values()) {
            if(m.isMorador(login)) {
                ret = true;
                break;
            }
        }
        
        return ret;
    }
    
    public boolean isSenhorio(String username, String password) {
        Utilizador login = new Utilizador(username, password);
        
        return apartamento.getSenhorio().isSenhorio(login);
    }
    
    public boolean isAdministrador(String username, String password) {
        Utilizador login = new Utilizador(username, password);
        
        return apartamento.getAdministrador().isAdministrador(login);
    }
    
    
    public String[] getQuartos() {
        return apartamento.getQuartosString();
    }
    
    public String[] getMoradores() {
        return apartamento.getMoradoresString();
    }
    
    // Getters e setters
    
    public Apartamento getApartamento() {
        return apartamento.clone(); 
    }
  
    public Utilizador getUtilizador() {
        return utilizador.clone();
    }
    
    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }
    
    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }
    
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */

  
    public static void main(String[] args) throws SQLException {
       despesas = new DespesaDAO();
       morador_dao = new MoradorDAO();
       quartos_dao = new QuartoDAO();
       
       /*
       Quarto q = new Quarto(1,50, new HashSet<>());
       quartos_dao.put(q.getNumQuarto(),q);
       
       Morador m = new Morador("gomes","gay","paneleiro");
       morador_dao.put(m, q);
       
       Despesa d = new Despesa(1,"gay porn",69,"extra", new Date(), new Date(), new Date());
       despesas.put(d, m.getUsername());
       
       morador_dao.updateSaldo(m.getUsername(), 6999);
       */
       
       System.out.println(quartos_dao.get(1).getPreco());

       
       //System.out.println(m.toString());
     
    }
}
