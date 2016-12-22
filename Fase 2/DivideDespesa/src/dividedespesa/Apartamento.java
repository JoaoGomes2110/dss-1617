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
import java.util.List;
import static java.util.stream.Collectors.toMap;

/**
 * @author Carlos Pereira, João Gomes, João Pires Barreira, João Reis
 */

public class Apartamento {
    
    // Variáveis de instância
    private String info;
    private Senhorio senhorio;
    private Administrador admin;
    private Map<Integer, Quarto> quartos;
    private Map<String, Morador> moradores;
    private Map<String, Morador> moradoresAntigos;

    
    // Construtores
    
    public Apartamento (String info, Senhorio senhorio, Administrador admin,
                        Map<Integer, Quarto> quartos) {
        this.info = info;
        this.senhorio = senhorio.clone();
        this.admin = admin.clone();
        this.setQuartos(quartos);
        moradores = new HashMap<>();
        moradoresAntigos = new HashMap<>();
    }
    
    public Apartamento (Apartamento apt) {
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
                quartos.get(i).addMorador(username);
            }
        }
    }
    
    public void adicionarDespesaPorPagar(String username, String info, double valor,
                                         String tipo, SimpleDateFormat dataEmissao,
                                         SimpleDateFormat dataLimite) {
        
        moradores.get(username).adicionarDespesaPorPagar(info, valor, tipo, dataEmissao,
                                                         dataLimite);
        
    }
    
    public void cobrarRenda() {
        SimpleDateFormat date = new SimpleDateFormat();
        
        for (String user : moradores.keySet()) {
            for (Quarto qrt : quartos.values()) {
                if (qrt.containsUsername(user)) {
                    moradores.get(user).cobrarRenda(qrt.getPreco(),
                                                    qrt.getNumQuarto(),
                                                    qrt.getNumMoradores(),
                                                    date);
                }
            }
        }    
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
        return senhorio.clone();
    }
    
    public Administrador getAdministrador() {
        return admin.clone();
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
