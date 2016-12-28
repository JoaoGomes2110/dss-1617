/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;



import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import static java.util.stream.Collectors.toMap;

/**
 * @author Carlos Pereira, João Gomes, João Pires Barreira, João Reis
 */

public class Apartamento {
    
    // Variáveis de instância
    private String info;
    private Senhorio senhorio;
    private Administrador administrador;
    private Map<Integer, Quarto> quartos;
    private Map<String, Morador> moradores;
    private Map<String, Morador> moradoresAntigos;

    
    // Construtores
    
    public Apartamento(String info, Senhorio senhorio, Administrador administrador,
                       Map<Integer, Quarto> quartos) {
        this.info = info;
        System.out.println("A construir senhorio.");
        this.senhorio = senhorio.clone();
        
        System.out.println("Senhorio construido " + this.senhorio.getUsername());
        this.administrador = administrador.clone();
        this.setQuartos(quartos);
        moradores = new HashMap<>();
        moradoresAntigos = new HashMap<>();
    }
    
    public Apartamento(Apartamento apt) {
        info = apt.getInfo();
        quartos = apt.getQuartos();
        moradores = apt.getMoradores();
        moradoresAntigos = apt.getMoradoresAntigos();
    }

    
    // Métodos de instância
    
    public void addMorador(String nome, String username, String password, 
                           List<Integer> numQuartos) throws MoradorExistenteException {
        
        if (moradores.containsKey(username)) {
            throw new MoradorExistenteException();
        } else {
            Morador novo = new Morador(username, password, nome);
            moradores.put(username, novo);
        
            for (Integer i : numQuartos) {
                quartos.get(i - 1).addMorador(username);
            }
        }
    }
    
    public void adicionarDespesaPorPagar(String username, String info, double valor,
                                         String tipo, Date dataEmissao,
                                         Date dataLimite) {
        
        moradores.get(username).adicionarDespesaPorPagar(info, valor, tipo, dataEmissao,
                                                         dataLimite);
        
    }
    
    public void cobrarRenda() {
        Date date = new Date(); // data atual
        
        for (String user : moradores.keySet()) {
            for (Quarto qrt : quartos.values()) {
                if (qrt.getMoradores().contains(user)) {
                    moradores.get(user).cobrarRenda(qrt.getPreco(),
                                                    qrt.getNumQuarto(),
                                                    qrt.getNumMoradores(),
                                                    date);
                }
            }
        }    
    }

    public void removerMorador(Morador m) {
        String aux_user = m.getUsername();
        
        if (moradores.containsKey(aux_user)) {
            Morador aux = moradores.get(aux_user);
            moradores.remove(aux_user);
            moradoresAntigos.put(aux_user, aux);
        }
    }
    
    public void alterarQuartosMorador(Morador m, List<Integer> numQuartos) {
        // remove morador de todos os quartos
        for (Quarto q : quartos.values()) {
            if (q.getMoradores().contains(m)) {
                q.getMoradores().remove(m);
            }
        }
        
        // adiciona morador aos quartos pretendidos
        for (Integer i : numQuartos) {
            quartos.get(i).addMorador(m.getUsername());
        }
    }
    
    public void alteraRendaQuarto(int numQuarto, double valor) {
        quartos.get(numQuarto).setPreco(valor);
    }
    
    public boolean existeUser(String user) {
        return moradores.containsKey(user) || moradoresAntigos.containsKey(user);
    }
    
    
    public String[] getQuartosString() {
        String[] quartosStr = new String[quartos.size()];
        
        for(Integer i : quartos.keySet()) {
            StringBuilder sb = new StringBuilder();
            double conta = quartos.get(i).getPreco()/(quartos.get(i).getNumMoradores() + 1);
            
            sb.append((i + 1) + " - Preço: " + conta + "€");

            quartosStr[i] = sb.toString();
        }
        
        return quartosStr;
    }
    
    public String[] getMoradoresString() {
        String[] moradoresStr = new String[moradores.size()];
        int i = 0;
        
        for(Morador m : moradores.values()) {
            StringBuilder sb = new StringBuilder();
            sb.append(m.getUsername() + " - " + m.getNome());
            moradoresStr[i] = sb.toString();
            i++;
        }

        return moradoresStr;
    }

    
    // Getters e Setters
    
    public String getInfo() { 
        return info;
    }    
    
    public Map<Integer, Quarto> getQuartos() {
        Map<Integer, Quarto> temp = new HashMap<>();
        
        for (Quarto q : quartos.values()) {
            temp.put(q.hashCode(), q.clone());
        }
        
        return temp;
    }

    public Map<String, Morador> getMoradores() {
        Map<String, Morador> temp = new HashMap<>();
        
        for (Morador m : moradores.values()) {
            temp.put(m.getUsername(), m.clone());
        }
        
        return temp;
    }
    
    public Map<String, Morador> getMoradoresAntigos() {
        Map<String, Morador> temp = new HashMap<>();
        
        for (Morador m : moradoresAntigos.values()) {
            temp.put(m.getUsername(), m.clone());
        }
        
        return temp;
    }
    
    
    public Senhorio getSenhorio() {
        return senhorio;
    }
    
    public Administrador getAdministrador() {
        return administrador;
    }
   
    public void setInfo(String info) {
        this.info = info;
    }    
    
    public void setQuartos(Map<Integer, Quarto> quartos) {
        this.quartos = quartos.values()
                              .stream()
                              .collect(toMap(Quarto::getNumQuarto, Quarto::clone));  
    }
    
    public void setMoradores(Map<Integer, Morador> Moradores) {
        this.moradores = moradores.values()
                                  .stream()
                                  .collect(toMap(Morador::getUsername, Morador::clone));  
    }
    
    public void setMoradoresAntigos(Map<Integer, Morador> moradoresAntigos) {
        this.moradoresAntigos = moradoresAntigos.values()
                                                .stream()
                                                .collect(toMap(Morador::getUsername, Morador::clone));
    }
    
    //Métodos complementares comuns

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {info, quartos, moradores, moradoresAntigos});
    }
    
    @Override
    public boolean equals(Object o) {
        
        if (this == o) {
            return true;
        }
        
        if (o == null || (this.getClass() != o.getClass())) {
            return false;
        }
    
        Apartamento apt = (Apartamento) o; 

        return (quartos.equals(apt.getQuartos()) &&
                info.equals(apt.getInfo()));
    }
    
    @Override
    public Apartamento clone() {
        return new Apartamento(this);
    }
      
}
