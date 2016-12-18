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

public class Apartamento {
    
    //Variáveis de instância
    private Map<Integer, Quarto> quartos;
    private String info;
    
    
    //Construtores
    
    public Apartamento () {
        quartos = new HashMap<>();
        info = "";
    }
    
    
    public Apartamento (Apartamento apt) {
        quartos = new HashMap<> (apt.getQuartos());
        info = apt.getInfo();
    }
    

    //Métodos de instância
    
    public void criaQuarto(int num, double preco) {
        quartos.put(quartos.size(), new Quarto(num, preco));
    }
    
    
    public void adicionarMorador(int num_qrt, String nome,
                                 String username, String password) {
        Morador morador = new Morador(nome, username, password);
    
        quartos.put(num_qrt, morador);
        
        
    }
    
    
    //Gets e Sets
    
    public Map<Integer, Quarto> getQuartos() {
        return quartos;
    }

    
    public String getInfo() {
        return info;
    }
    
    public void setQuartos(Map<Integer, Quarto> quartos) {
        this.quartos = quartos;
    }
    
    public void setInfo(String info) {
        this.info = info;
    }
    
    
    //Métodos complementares comuns

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
      
}
