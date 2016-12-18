/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dividedespesa;



/**
 *
 * @author Carlos Pereira
 */
public class Senhorio extends Utilizador {
    private String nome;

    /**
     *
     * @param nome
     * @param username
     * @param password
     */
    public Senhorio(String nome, String username, String password) {
        super(username, password);
        this.nome = nome;
    }



}
