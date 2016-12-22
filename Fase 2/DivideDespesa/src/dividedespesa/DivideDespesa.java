/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author Carlos Pereira, João Gomes, João Pires Barreira, João Reis
 */
public class DivideDespesa {
    
    // Variáveis de instância
    
    Apartamento apartamento; // apartamento registado no sistema
    Utilizador utilizador;   // utilizar com sessão iniciada
    
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
    
    public void registaMorador(String username, String password, String nome, List<Integer>) {
        
        Morador novo = new Morador(username, password, nome);
        
        if (apartamento.getMoradores().containsValue(novo)) {
            throw new UtilizadorExistenteException();
        }
        
        apartamento.put(novo.hashCode(), novo);
    }
    
    public void autenticarUtilizador(String username, String password) throws SemAutorizacaoException {
        
        if (apartamento.get)
        
        if (utilizadores.containsKey(username) && utilizadores.get(username).getPassword().equals(password)) {
            utilizador = utilizadores.get(username).clone();
        } else {
            throw new SemAutorizacaoException();
        }
    }
    
    public Collection<Despesa> verDespesasPorPagar(Morador m) {
        return apartamento.getMoradores().get(m.hashCode()).getDespesasPorPagar().values();
    }
    
    public Collection<Despesa> verDespesasPagas(Morador m) {
        return apartamento.getMoradores().get(m.hashCode()).getDespesasPagas().values();
    }
    
    public Utilizador registaApartamento(Apartamento apt) {
        if (utilizador != null && utilizador.getUsername().equals("root")) {
            apartamento = apt.clone();
        }
        
        // gerar credenciais para administrador
        String admin_usr = "admin12345";
        String admin_pwd = "admin12345";
        Administrador admin = new Administrador(admin_usr, admin_pwd);
        utilizadores.put(admin.getUsername(), admin);
        return admin;
    }
    
    public void adicionarMorador(Morador m, Quarto q) throws UtilizadorExistenteException {
        if (apartamento.getMoradores().containsKey(m.hashCode())) {
            throw new UtilizadorExistenteException();
        } else {
            
        }
    }
    
    public boolean cobrarRenda() {
        double total = 0;
        
        GregorianCalendar gc = new GregorianCalendar(); // data atual
                
        for (Morador m : apartamento.getMoradores().values()) {
            for ()) {
                
                total += (q.getPreco() / q.getNumMoradores());
            }
            
            
        }
    }
    
    // Getters e setters
    
    public Apartamento getApartamento() { return apartamento.clone(); }
  
    public Utilizador getUtilizador() { return utilizador.clone(); }
    
    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento.clone();
    }
    
    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador.clone();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    }
    
}
