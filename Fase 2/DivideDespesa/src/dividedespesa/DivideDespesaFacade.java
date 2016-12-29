/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Carlos Pereira
 */
public class DivideDespesaFacade extends Observable {
    
    private DivideDespesa dd;
    private List<Double> precos;
    
    public DivideDespesaFacade() {
        
        dd = new DivideDespesa();
        precos = new ArrayList<>();
    }
    
    
    public boolean validaSenhorio(String nome, String user, String pass) {
        Senhorio senhorio = new Senhorio(nome, user, pass);
        
        return senhorio.validaSenhorio();
    }
    
    public boolean validaAdministrador(String user, String pass) {
        Administrador admin = new Administrador(user, pass);
        
        return admin.validaAdministrador();
    }
    
    public boolean validaMorador(String nome, String user, String pass) {
        Morador morador = new Morador(user, pass, nome);
        
        return morador.validaMorador();
    }
    
    public boolean fieldSize(String username, String password, String nome) {
        
        return (username.length() == 0 || password.length() == 0 ||
                nome.length() == 0);
    }
    
    public boolean addMorador(String nome, String username, String password, 
                           List<Integer> numQuartos) {
        boolean ret = false;
        
        try {
            dd.addMorador(username, password, nome, numQuartos);
        } catch (MoradorExistenteException e) {
            ret = true;
        }
        
        return ret;
    }
    
    public void setUtilizador(String username, String password) {
        Utilizador login = new Utilizador(username, password);
        dd.setUtilizador(login);
    }
    
    public String[] getQuartos() {
        return dd.getQuartosString();
    }
    
    
    public String[] getMoradores() {
        return dd.getMoradoresString();
    }

   
    public String parseString(String str) {
        String[] parts = str.split(" ");
        return parts[0];
    }
    
    
    public void addToPrecos(String str) {
        double preco = Double.valueOf(str);
        this.precos.add(preco);
        this.setChanged();
        this.notifyObservers();
    }
    
    public void removeQuarto() {
        this.precos.remove(precos.size() - 1);
        this.setChanged();
        this.notifyObservers();
    }
    
    
    public int getNumQuartos() {
        return precos.size();
    }
    
    
    public void registaApartamento(String usernameSenhorio, String usernameAdmin,
                                    String nomeSenhorio, String passSenhorio,
                                    String passAdmin) {
        
        Senhorio senhorio = new Senhorio(nomeSenhorio, usernameSenhorio, passSenhorio);
        Administrador admin = new Administrador(usernameAdmin, passAdmin);
        
        dd.registaApartamento(senhorio, admin, precos);
    }
    
    public boolean isSenhorio(String username, String password) {
        return dd.isSenhorio(username, password);
    }
    
    public boolean isAdministrador(String username, String password) {
        return dd.isAdministrador(username, password);
    }
    
    public boolean isMorador(String username, String password) {
        return dd.isMorador(username, password);
    }
    
    public void alterarPasswordMorador(String username, String password) {
        dd.alterarPasswordMorador(username, password);
    }
    
    public void alterarQuartosMorador(String username, List<Integer> quartos) {
        dd.alterarQuartosMorador(username, quartos);
    }
    
    public Set<String> getSetMoradores(){
        return new HashSet<>(Arrays.asList(dd.getMoradoresString()));
    }
   
    public Collection<Despesa> despesasPagas(String morador){
        Collection<Despesa> despesas = new TreeSet<>();
        despesas = dd.verDespesasPagas(morador);
        return despesas;
    }
   
    public Collection<Despesa> despesasPorPagar(String morador){
        Collection<Despesa> despesas = new TreeSet<>();
        despesas = dd.verDespesasPorPagar(morador);
        return despesas;
    }
   
    public void remover(String username){
        dd.removerUtilizador(username);
    }
    
    public void carregar(String username, Double valor){
        dd.updateSaldo(username, valor);
    }
        
    public double consultar(String username){
        return dd.consultaSaldo(username);
    }
   
    public String[] getDadosDespesa(String user){
        Collection<Despesa> temp = despesasPorPagar(user);
        String[] despesas = new String[temp.size()];
        int i = 0;
        for(Despesa d: temp){
            StringBuilder sb = new StringBuilder();
            sb.append(d.getId()).append("-").append(d.getInfo()).append("-").
               append(d.getValor());
            despesas[i] = sb.toString();
            i++;
        }
        return despesas;
    }
   
    public boolean pagar(String user,int idDespesa){
        return dd.pagaDespesa(user, idDespesa);
    }
    
    public String getUsername() {
        return dd.getUtilizador().getUsername();
    }
}
