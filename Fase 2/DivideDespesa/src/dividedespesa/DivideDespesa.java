/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import dividedespesa.database.*;
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
    private Utilizador utilizador;
    private static AdministradorDAO adminDAO;
    private static SenhorioDAO senhorioDAO;
    private static QuartoDAO quartosDAO;
    private static MoradorDAO moradoresDAO;
    private static DespesaDAO despesasDAO;
    
    // Construtores
    
    public DivideDespesa() {
        utilizador = null;
        adminDAO = new AdministradorDAO();
        senhorioDAO = new SenhorioDAO();
        quartosDAO = new QuartoDAO();
        moradoresDAO = new MoradorDAO();
        despesasDAO = new DespesaDAO();
        
    }
    
    public DivideDespesa(DivideDespesa dd) {
        //apartamento = dd.getApartamento();
        //utilizador = dd.getUtilizador();
    }
    
    // Métodos de instância
    
    public void addMorador(String username, String password, String nome,
                               List<Integer> qrts) throws MoradorExistenteException {
        if (moradoresDAO.containsKey(username)) {
            throw new MoradorExistenteException();
        } else {
            Morador novo = new Morador(username, password, nome);
            moradoresDAO.put(novo, qrts);
        }
    }
    
    public void adicionarDespesa(String username, Despesa d) {
        despesasDAO.put(d, username);
    }    
    
    public Collection<Despesa> verDespesasPorPagar(String username) {
        return despesasDAO.userPorPagar(username);
    }
    
    public Collection<Despesa> verDespesasPagas(String username) {
        return despesasDAO.userPagas(username);
    }

    public void cobrarRendaDAO() {
        Date date = new Date();
        
        Map<String,Double> moradoresPreco = moradoresDAO.getUsernamesPrecos();
        
        moradoresPreco.keySet().stream().forEach((user) -> {
            this.adicionarDespesa(user,
                                  new Despesa(despesasDAO.size(), "Renda", moradoresPreco.get(user), "Renda", new Date(), new Date(), null)); //TENHO DE ALTERAR
        });
        }
    
    public void alteraRendaQuarto(int numQuarto, double valor) {
        quartosDAO.updateRenda(numQuarto, valor);
    }
    
    public void alterarPasswordMorador(String username, String password) {
       moradoresDAO.updatePassword(username,password);
    }
    
    public void alterarQuartosMorador(String username, List<Integer> quartos) {
         moradoresDAO.updateMoradorQuarto(moradoresDAO.get(username), quartos); 
    }
    
    public boolean isMorador(String username, String password) {
        return moradoresDAO.exists(username,password);
    }
    
    public boolean isSenhorio(String username, String password) {      
        return senhorioDAO.exists(username,password);
    }
    
    public boolean isAdministrador(String username, String password) {
        return adminDAO.exists(username,password);
    }
    
    
    public String[] getQuartosString() {
        return quartosDAO.getAll();
    }
    
    public String[] getMoradoresString() {
        return moradoresDAO.getAll();
    }
    
    public double consultaSaldo(String username){
        return moradoresDAO.get(username).getContaCorrente().getSaldo();
    }
    
    public void updateSaldo (String username, double valor) {
        moradoresDAO.updateSaldo(username, valor + consultaSaldo(username));
    }
    
    public void removerUtilizador(String username){
        moradoresDAO.updateSaida(username, new Date());
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }
    
    public boolean pagaDespesa(String username, int idDespesa){
        double saldo = consultaSaldo(username),
               valor = despesasDAO.get(idDespesa).getValor();
        
        if(saldo < valor)
            return false;
        
        updateSaldo(username,saldo-valor);
        return true;
    }
    
    void registaApartamento(Senhorio senhorio, Administrador admin, List<Double> precosQuartos) {
        senhorioDAO.toDB(senhorio);
        adminDAO.toDB(admin);
        for(int i=0;i<precosQuartos.size();i++)
            quartosDAO.toDB(i,precosQuartos.get(i));
        
        
    }
    
    
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */

  
    public static void main(String[] args) throws SQLException {
       
       /*
       Quarto q = new Quarto(1,50, new HashSet<>());
       quartos_dao.put(q.getNumQuarto(),q);
       
       Morador m = new Morador("gomes","gay","paneleiro");
       morador_dao.put(m, q);
       
       Despesa d = new Despesa(1,"gay porn",69,"extra", new Date(), new Date(), new Date());
       despesas.put(d, m.getUsername());
       
       morador_dao.updateSaldo(m.getUsername(), 6999);
       */
       
       //System.out.println(m.toString());
     
    }


    
}

