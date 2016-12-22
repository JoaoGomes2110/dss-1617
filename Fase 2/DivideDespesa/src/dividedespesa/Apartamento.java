/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;


import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import static java.util.stream.Collectors.toMap;

/**
 * @author Carlos Pereira, João Gomes, João Pires Barreira, João Reis
 */

public class Apartamento {
    
    // Variáveis de instância
    private String info;
    private Map<Integer, Quarto> quartos;
    private Map<Integer, Morador> moradores;

    // Construtores
    
    public Apartamento () {
        info = "";
        quartos = new HashMap<>();
        moradores = new HashMap<>();
    }
    
    public Apartamento (Apartamento apt) {
        info = apt.getInfo();
        quartos = apt.getQuartos();
        moradores = apt.getMoradores();
    }

    // Métodos de instância
    
    
    // Getters e Setters
    
    public String getInfo() { return info; }    
    
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
   
    public void setInfo(String info) { this.info = info; }    
    
    public void setQuartos(Map<Integer, Quarto> quartos) {
        this.quartos = quartos.values()
                              .stream()
                              .collect(toMap(Quarto::hashCode, Quarto::clone));  
    }
    
    public void setMoradores(Map<Integer, Morador> Moradores) {
        this.moradores = moradores.values()
                                  .stream()
                                  .collect(toMap(Morador::hashCode, Morador::clone));  
    }    
   
    
    //Métodos complementares comuns

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {info, quartos, moradores});
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
