/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;


import java.util.Arrays;
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
    private Administrador administrador;
    private Administrador admin;
    private Map<Integer, Quarto> quartos;
    private Map<String, Morador> moradores;
    private Map<String, Morador> moradoresAntigos;

    // Construtores
    
    public Apartamento (String info, Administrador administrador, Administrador admin,
                        Map<Integer, Quarto> quartos) {
        this.info = info;
        this.administrador = administrador.clone();
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
                           List<Integer> numQuartos) {
        Morador novo = new Morador(username, password, nome);
        
        moradores.put(username, novo);
        
        for (Integer i : numQuartos) {
            quartos.get(i).addMorador(novo);
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

    public Map<Integer, Morador> getMoradores() {
        Map<Integer, Morador> temp = new HashMap<>();
        
        for (Morador m : moradores.values()) {
            temp.put(m.hashCode(), m.clone());
        }
        
        return temp;
    }
    
    public Map<Integer, Morador> getMoradoresAntigos() {
        Map<Integer, Morador> temp = new HashMap<>();
        
        for (Morador m : moradoresAntigos.values()) {
            temp.put(m.hashCode(), m.clone());
        }
        
        return temp;
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
