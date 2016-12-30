/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa.BusinessLayer;

import java.util.Date;



/**
 *
 * @author Carlos Pereira, João Gomes, João Pires Barreira, João Reis
 */
public class Morador extends Utilizador {
    
    // Variáveis de instância
    
    private String nome;
    private Conta contaCorrente;
    private Date dataEntrada;
    private Date dataSaida;

    // Construtores
    
    public Morador(String username, String password, String nome) {
        super(username, password);
        this.setNome(nome);
        contaCorrente = new Conta();
        dataEntrada = new Date(); // data atual
        dataSaida = null;
    }
    
    public Morador(String username, String password, String nome, Conta contaCorrente,
                   Date dataEntrada, Date dataSaida) {
        super(username, password);
        this.setNome(nome);
        this.contaCorrente = contaCorrente.clone();
        this.setDataEntrada(dataEntrada);
        this.setDataSaida(dataSaida);
    }

    public Morador(Morador m) {
        super(m.getUsername(), m.getPassword());
        nome = m.getNome();
        contaCorrente = m.getContaCorrente();
        dataEntrada = m.getDataEntrada();
        dataSaida = m.getDataSaida();
    }
    
    // Métodos de instância
    
    public boolean validaMorador() {
        String [] special = { "/", "#", "$", "%", "&", ">", "<", "-", ","};
        boolean ret = true;

        for(String s : special) {
            if(nome.contains(s)) {
                ret = false;
                break;
            }
        }
        
        return ret && this.validaUsername() && this.validaPassword();
    }
    
    public boolean isMorador (Utilizador u) {
        return this.getUsername().equals(u.getUsername()) &&
               this.getPassword().equals(u.getPassword());
    }
    
    // Getters e setters
    
    public String getNome() {
        return nome;
    }
    
    public Conta getContaCorrente() {
        return contaCorrente.clone();
    }
   
    public Date getDataEntrada() { return dataEntrada; }

    public Date getDataSaida() { return dataSaida; }
    
    public void setNome(String nome) { this.nome = nome; }
    
    public void setContaCorrente(Conta contaCorrente) {
        this.contaCorrente = contaCorrente.clone();
    }
    
    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }
    
    // Métodos complementares comuns

    @Override
    public Morador clone() {
        return new Morador(this);
    }
    
}
