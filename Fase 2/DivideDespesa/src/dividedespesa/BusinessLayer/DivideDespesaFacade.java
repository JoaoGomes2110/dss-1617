/*
 * @brief Facade da classe DivideDespesa. Recebe os campos lidos pela
 *        interface de utilizador, envia para os métodos correspondentes
 *        dessa classe. Também apanha as excepções previstas e contém métodos
 *        que fazem o parse dos campos lidos.
 *        Envia para a IU os resultados dos métodos.
 *
 * @author Carlos Pereira   - A61887
 * @author João Barreira    - A73831
 * @author João Gomes       - A74033
 * @author João Reis        - A75372
 */

package dividedespesa.BusinessLayer;

import dividedespesa.Exceptions.MoradorExistenteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;


public class DivideDespesaFacade extends Observable {
    
    //Variáveis de instância
    
    private DivideDespesa dd;       //Objeto da classe DivideDespesa
    
    private List<Double> precos;    //Lista auxiliar em que a posição
                                    //corresponde ao número do quarto
                                    //e o valor ao preço do quarto.
    
    
    //Contrutores
    
    /**
     * Construtor vazio.
     */
    public DivideDespesaFacade() {
        dd = new DivideDespesa();
        precos = new ArrayList<>();
    }

    
    //Métodos de instância
    
    /**
     * Determina se os dados do senhorio são válidos, nomeadamente verificar
     * se contêm carateres especiais.
     * Cria um objeto Senhorio e chama o método nessa classe que faz a 
     * validação necessária.
     * 
     * @param nome  Nome
     * @param user  Username
     * @param pass  Password
     * @return      true se os dados forem válidos
     *              false caso contrário
     */
    public boolean validaSenhorio(String nome, String user, String pass) {
        Senhorio senhorio = new Senhorio(nome, user, pass);
        
        return senhorio.validaSenhorio();
    }
    
    /**
     * Determina se os dados do administrador são válidos, nomeadamente verificar
     * se contêm carateres especiais.
     * Cria um objeto Administrador e chama o método nessa classe que faz a 
     * validação necessária.
     * 
     * @param user  Username
     * @param pass  Password
     * @return      true se os dados forem válidos
     *              false caso contrário
     */
    public boolean validaAdministrador(String user, String pass) {
        Administrador admin = new Administrador(user, pass);
        
        return admin.validaAdministrador();
    }
    
    /**
     * Determina se os dados do morador são válidos, nomeadamente verificar
     * se contêm carateres especiais.
     * Cria um objeto Morador e chama o método nessa classe que faz a 
     * validação necessária
     * 
     * @param nome  Nome
     * @param user  Username
     * @param pass  Password
     * @return      true se os dados forem válidos
     *              false caso contrário
     */
    public boolean validaMorador(String nome, String user, String pass) {
        Morador morador = new Morador(user, pass, nome);
        
        return morador.validaMorador();
    }
    
    /**
     * Verifica se nenhum dos campos recebidos está vazio.
     * É utilizado para verificar se todos os campos foram preenchidos numa
     * janela da interface de utilizador.
     * 
     * @param username  Campo correspondente ao username
     * @param password  Campo correspondente à password
     * @param nome      Campo correspondente ao nome
     * @return          true caso exista pelo menos um campo vazio
     *                  false caso contrário
     */
    public boolean fieldSize(String username, String password, String nome) {
        return (username.length() == 0 || password.length() == 0 ||
                nome.length() == 0);
    }
    
    /**
     * Adiciona um morador aos quartos correspondentes.
     * Recebe os campos introduzidos na IU que correspondem aos dados de um
     * morador e o número de quartos a que este foi associado. 
     * 
     * @param nome Nome
     * @param username Username
     * @param password Password
     * @param numQuartos Lista com o número dos quartos associados
     * @return Mensagem a indicar se houve sucesso a adicionar o morador
     */
    public String addMorador(String nome, String username, String password, 
                              List<Integer> numQuartos) {
        String ret;
        
        try {
            dd.addMorador(username, password, nome, numQuartos);
            ret = "Morador adicionado.";
        } catch (SQLException e) {
            ret = "Não foi possível ligar à Base de Dados.";
        } catch (MoradorExistenteException ex) {
            ret = "O morador já existe no apartamento.";
        }
        
        return ret;
    }
    
    /**
     * Adiciona uma despesa a uma lista de moradores.
     * Recebe os campos introduzidos na IU que correspondem aos dados da
     * despesa, como também uma lista com os moradores.
     * Antes de enviar para o método correspondente na classe DivideDespesa,
     * verifica se todos os campos foram preenchidos na IU e se a data limite
     * é depois da data atual.
     * Este método também faz o tratamento a excepções esperadas.
     * 
     * 
     * @param nome          Nome da despesa
     * @param valorTotal    Valor da despesa
     * @param tipo          Tipo da despesa
     * @param dataLimite    Data limite de pagamento da despesa
     * @param moradores     Lista do moradores para terem a despesa
     *                      adicionada
     * @return              Mensagem a indicar se houve sucesso na adição
     *                      da despesa
     */
    public String adicionarDespesa(String nome, String valorTotal, String tipo,
                                   Date dataLimite, List<String> moradores) {
        String ret;
        Date now = new Date();                  //Data atual
        int comp = dataLimite.compareTo(now);   //Comparação da data atual
                                                //à data limite
        

        if(nome.isEmpty() || valorTotal.isEmpty() || tipo.isEmpty() ||
           moradores.isEmpty()) {
            ret = "Todos os campos têm que estar preenchidos.";
        } else {
            if (comp <= 0) {
                ret = "A data limite tem que ser antes da data atual.";
            } else {
                try {            
                    //Divide o valor total da despesa pelo número de moradores
                    double valor = Double.valueOf(valorTotal)/(moradores.size());

                    for(String m : moradores) {
                        String username = this.parseString(m);
                        dd.adicionarDespesa(nome, valor, tipo, dataLimite, username);
                    }

                    ret = "Despesa adicionada.";
                } catch (SQLException e) {
                    ret = "Não foi possível ligar à Base de Dados.";
                } catch (NumberFormatException e) {
                    ret = "Introduza um valor válido para a despesa.";
                }
            }
        }
        
        return ret; //Devolve se houve sucesso a adicionar a despesa
    }
    
    /**
     * Indica se já existe um apartamento registado no sistema.
     * Este método é utilizado para informar ao utilizador se é necessário
     * registar um apartamento antes de fazer o login.
     * Também faz o tratamento de excepções.
     * 
     * @return true se existe um apartamento
     *         false caso contrário
     */
    public boolean existeApt() {
        boolean ret;
        
        try {
            ret = dd.existeApt();
        } catch (SQLException e) {
            ret = false;
        }

        return ret;
    }
    
    /**
     * Devolve um array com as informações de todos os quartos.
     * Caso não seja possível aceder aos quartos, devolve null que indica
     * ter havido uma falha.
     * 
     * @return Array com os quartos 
     */
    public String[] getQuartos() {
        String[] ret;
        
        try {
            //Chama o método na DivideDespesa que vai à base de dados
            //obter as informações
            ret = dd.getQuartosString();
        } catch (SQLException e) {
            ret = null;
        }
        
        return ret;
    }
    
    
    /**
     * Devolve um array com as informações de todos os quartos.
     * Caso não seja possível aceder aos quartos, devolve null que indica
     * ter havido uma falha.
     * 
     * @return Array com os quartos 
     */
    public String[] getQuartosPrecoTotal() {
        String[] ret;
        
        try {
            //Chama o método na DivideDespesa que vai à base de dados
            //obter as informações
            ret = dd.getQuartosPrecoTotal();
        } catch (SQLException e) {
            ret = null;
        }
        
        return ret;
    }
    
    /**
     * Devolve um array com as informações de todos os moradores.
     * Caso não seja possível aceder aos moradores, devolve null que indica
     * ter havido uma falha.
     * 
     * @return Array com os moradores
     */
    public String[] getMoradores() {
        String[] ret;
        
        try {
            //Chama o método na DivideDespesa que vai à base de dados
            //obter as informações
            ret = dd.getMoradoresString();
        } catch (SQLException e) {
            ret = null;
        }
        
        return ret;
    }

    /**
     * Faz o parse de uma String.
     * Devolve a primeira parte da String até encontrar um espaço em branco.
     * 
     * @param str   String a fazer o parse
     * @return      Parte da String orginal até ao primeiro espaço em branco
     */
    public String parseString(String str) {
        String[] parts = str.split(" ");
        return parts[0];
    }
    
    /**
     * Adiciona à variável de instância 'precos' o valor recebido da IU
     * que corresponde ao preço de um quarto adicionado.
     * Também informa ao observador na IU que foi adicionado um quarto e 
     * faz o tratamento de excepções esperadas.
     * 
     * @param str   Campo introduzido na IU
     * @return      true se foi adicionado a 'precos'
     *              false caso o campo introduzido não esteja no formato numérico
     */
    public boolean addToPrecos(String str) {
        boolean ret;
        
        try {
            double preco = Double.valueOf(str);
            this.precos.add(preco);
            this.setChanged();
            this.notifyObservers();
            ret = true;
        } catch (NumberFormatException e) {
            ret = false;
        }
        
        return ret;
    }
    
    /**
     * Remove o preço do último quarto adicionado a 'preco'.
     * Também faz o tratamento de excepções esperadas.
     * 
     * @return true caso tenha sido removido
     *         false caso não exista mais quartos a remover
     */
    public boolean removeQuarto() {
        boolean ret;
        
        try {
            this.precos.remove(precos.size() - 1);
            this.setChanged();
            this.notifyObservers();
            ret = true;
        } catch (IndexOutOfBoundsException e) {
            //Caso 'precos' não tenha quartos para remover.
            ret = false;
        }
        
        return ret;
    }
    
    /**
     * Devolve o número de quartos adicionados.
     * 
     * @return número de quartos
     */
    public int getNumQuartos() {
        return precos.size();
    }
    
    /**
     * Regista um apartamento no sistema.
     * Cria objetos das classes Senhorio e Admnistrador e chama o método
     * na classe DivideDespesa que regista um apartamento no sistema.
     * Faz o tratamento de excepções esperadas.
     * No fim indica se o apartamento foi registado.
     * 
     * @param nomeSenhorio      Nome do senhorio
     * @param usernameSenhorio  Username do senhorio
     * @param passSenhorio      Password do senhorio
     * @param usernameAdmin     Username do administrador
     * @param passAdmin         Password do administrador
     * @return                  true caso tenha sido registado
     *                          false caso contrário
     */
    public boolean registaApartamento(String nomeSenhorio, String usernameSenhorio, 
                                      String passSenhorio, String usernameAdmin,
                                      String passAdmin) {
        boolean ret;
        
        try {
            Senhorio senhorio = new Senhorio(nomeSenhorio, usernameSenhorio, passSenhorio);
            Administrador admin = new Administrador(usernameAdmin, passAdmin);
            dd.registaApartamento(senhorio, admin, precos);
            ret = true;
        } catch (SQLException e) {
            ret = false;
        }
        
        return ret;
    }
    
    /**
     * Verifica se um username e uma password correspondem aos do senhorio
     * registado.
     * 
     * @param username  Username
     * @param password  Password
     * @return          true caso correspondem
     *                  false caso contrário
     */
    public boolean isSenhorio(String username, String password) {
        boolean ret;
        
        try {
            ret = dd.isSenhorio(username, password);
        } catch (SQLException e) {
            ret = false;
        }
        
        return ret;
    }
    
    /**
     * Verifica se um username e uma password correspondem aos do
     * administrador registado.
     * 
     * @param username  Username
     * @param password  Password
     * @return          true caso correspondem
     *                  false caso contrário
     */
    public boolean isAdministrador(String username, String password) {
        boolean ret;
        
        try {
            ret = dd.isAdministrador(username, password);
        } catch (SQLException e) {
            ret = false;
        }
        
        return ret;
    }
    
    /**
     * Verifica se um username e uma password correspondem aos de de um
     * morador registado.
     * Também faz o tratamento de excepções.
     * Devolve uma mensagem que indica se é um morador registado ou não.
     * 
     * @param username  Username
     * @param password  Password
     * @return          Mensagem a indicar se é um morador ou não
     */
    public String isMorador(String username, String password) {
        String ret;
        
        try {
            if(dd.isMorador(username, password)) {
                ret = "morador";
            } else {
                ret = "Login inválido.";
            }
        } catch (SQLException ex) {
            ret = "Não foi possível ligar à Base de Dados.";
        }

        return ret;
    }
    
    /**
     * Altera a password de um morador.
     * 
     * @param username  Username
     * @param password  Nova password
     * @return          true caso tenha sido alterada
     *                  false caso contrário
     */
    public boolean alterarPasswordMorador(String username, String password) {
        boolean ret;
        
        try {
            dd.alterarPasswordMorador(username, password);
            ret = true;
        } catch (SQLException ex) {
            ret = false;
        }

        return ret;
    }
    
    /**
     * Altera os números dos quartos associados a um morador.
     * 
     * @param username  Username
     * @param quartos   Lista com o novo número de quartos a associar
     * @return          true caso tenham sido alterados
     *                  false caso contrário
     */
    public boolean alterarQuartosMorador(String username, List<Integer> quartos) {
        boolean ret;
        
        try {
            dd.alterarQuartosMorador(username, quartos);
            ret = true;
        } catch (SQLException e) {
            ret = false;
        }

        return ret;
    }
    
    /**
     * Devolve um set com as o username e nome de um morador.
     * Também faz o tratamento de excepções esperadas. Devolve null caso
     * não tenha sido possível aceder aos moradores. 
     * 
     * @return Set de moradores
     */
    public Set<String> getSetMoradores()  {
        try {
            return new HashSet<>(Arrays.asList(dd.getMoradoresString()));
        } catch (SQLException e) {
            return null;
        }
    }
   
    /**
     * Devolve uma matriz onde as colunas são diferentes campos de
     * informações sobre uma despesa paga e as linhas às despesas.
     * Também faz o tratamento de excepções. Caso não tenha sido possível
     * aceder às despesas de um morador, devolve null.
     * 
     * @param morador   Username do morador a procurar
     * @return          matriz com as informações das despesas
     */
    public String[][] despesasPagas(String morador) {
        String[][] ret = null; 
        String username = parseString(morador);
        
        try {
            ret = dd.verDespesasPagas(username);
        } catch (SQLException e) {
            ret = null;
        }

        return ret;
    }
   
    /**
     * Devolve uma matriz onde as colunas são diferentes campos de
     * informações sobre uma despesa por pagar e as linhas as despesas.
     * Também faz o tratamento de excepções. Caso não tenha sido possível
     * aceder às despesas de um morador, devolve null.
     * 
     * @param morador   Username do morador a procurar
     * @return          matriz com as informações das despesas
     */
    public String[][] despesasPorPagar(String morador) {
        String[][] ret = null;
        String username = parseString(morador);
        
        try {
            ret = dd.verDespesasPorPagar(username);
        } catch (SQLException ex) {
            ret = null;
        }
        
        return ret;
    }
   
    /**
     * Altera a renda de um quarto.
     * Também verifica se todos os campos na IU foram introduzidos e faz
     * o tratamento de excepções esperadas. Nestes casos devolve a mensagem
     * adequada.
     * 
     * @param numQuarto     Número do quarto
     * @param valorRenda    Novo valor da renda
     * @return              Mensagem a indicar o sucesso da alteração
     */
    public String alterarRenda(String numQuarto, String valorRenda) {
        int quarto = Integer.valueOf(parseString(numQuarto));
        double renda = 0;
        String ret = "";
        
        if (numQuarto.isEmpty() || valorRenda.isEmpty()) {
            ret = "Todos os campos têm que estar preenchidos.";
        } else {
            try {
                renda = Double.valueOf(valorRenda);
                dd.alteraRendaQuarto(quarto, renda);  
                ret = " Valor da renda alterado.";
            } catch (NumberFormatException e) {
                ret = "Introduza um valor de renda válido.";
            } catch (SQLException e) {
                ret = "Não foi possível ligar à Base de Dados.";
            } 
        }
        
        return ret;
    }
    
    /**
     * Remove um morador do apartamento.
     * Também faz o tratamento a excepções esperadas. Neste caso devolve
     * uma mensagem adequada.
     * 
     * @param username  Username
     * @return          Mensagem a indicar se houve sucesso na remoção
     */
    public String remover(String username) {
        String ret;
        String username_parsed = parseString(username);
        
        try {
            dd.removerUtilizador(username_parsed);
            ret = "Morador removido.";
        } catch (SQLException ex) {
            ret = "Não foi possível ligar à Base de Dados.";
        }
        
        return ret;
    }
    
    /**
     * Carrega a conta de um morador com o valor recebido.
     * Caso o valor introduzido na IU não seja adequado ou não seja possível
     * aceder aos dados do morador, devolve uma mensagem adequada.
     * 
     * @param username  Username do utilizador
     * @param valorStr  Valor a carregar
     * @return          Mensagem a indicar o sucesso do carregamento 
     */
    public String carregar(String username, String valorStr) {
        String ret;
        
        try {
            double valor = Double.parseDouble(valorStr);
            dd.updateSaldo(username, valor);
            ret = "A conta foi carregada.";
        } catch (NumberFormatException e) {
            ret = "Introduza um valor a carregar válido.";
        } catch (SQLException ex) {
            ret = "Não foi possível ligar à Base de Dados.";
        }
        
        return ret;
    }
    
    /**
     * Devolve o valor do saldo na conta de um morador.
     * Também faz o tratamento de excepções esperada. Devolve uma mensagem
     * adequada a informar nesse caso.
     * 
     * @param username  Username
     * @return          Mensagem a indicar o saldo na conta
     */
    public String consultar(String username) {
        String ret;
        
        try {
            ret = Double.toString(dd.consultaSaldo(username));
        } catch (SQLException ex) {
            ret = "Não foi possível ligar à Base de Dados.";
        }
        
        return ret;
    }
    
    /**
     * 
     * @param user
     * @return 
     */
    public String[] getDadosDespesa(String user) {
        String[] despesas = null;
        
        try {
            Collection<Despesa> temp = dd.verDespesasPorPagarCollection(user);
        
            if(temp != null) {
                despesas = new String[temp.size()];

                int i = 0;

                for(Despesa d: temp){
                    StringBuilder sb = new StringBuilder();
                    sb.append(d.getId()).append(" - ").append(d.getInfo()).append(" - ").
                              append(d.getValor());
                    despesas[i] = sb.toString();
                    i++;
                }
            }
        } catch (SQLException e) {
            despesas = null;
        }
        
        return despesas;
    }

   
    public String pagar(String user, String despesaInfo) {
        String ret;
        int id;
        
        try {
            id = Integer.valueOf(this.parseString(despesaInfo));
            ret = dd.pagaDespesa(user, id);
        } catch (SQLException e) {
            ret = "Não foi possível ligar à Base de Dados.";
        } catch (NumberFormatException e) {
            ret = "Valor da despesa inválido.";
        }
        
        return ret;
    }
    
    /**
     * Cobra a renda do mês a todos os moradores no apartamento.
     * Chama o método na DivideDespesa que adiciona a renda do mês a todos
     * os moradores. Caso algum já tenha a renda cobrada, não cobra de novo.
     * Também faz o tratamento de excepções esperadas. Nesse caso devolve
     * uma mensagem adequada.
     * 
     * @param dataLimite    Data limite de pagamento da renda
     * @return              Mensagem a indicar o sucesso da operação
     */
    public String cobrarRenda(Date dataLimite) {
        String ret;
        Date now = new Date();
        int comp = dataLimite.compareTo(now);
        
        if (comp > 0) {
            try {
                dd.cobrarRenda(dataLimite);
                ret = "A renda do mês foi cobrada.";
            } catch (SQLException e) {
                ret = "Não foi possível ligar à Base de Dados.";
            }
        } else {
            ret = "A data limite da renda tem que ser depois do dia atual.";
        }
               
        return ret;
    }
    
    /**
     * Devolve o username do utilizador que fez a autenticação.
     * Caso não tenha sido possível aceder ao utilizador, devolve null.
     * 
     * @return  Username do utilizado autenticado
     */
    public String getUsername() {
        String ret;
        
        try {
            ret = dd.getUtilizador().getUsername();
        } catch (Exception e) {
            ret = null;
        }

        return ret;
    }
    
    /**
     * Faz o set do utilizador que fez a autenticação na classe
     * DivideDespesa.
     * 
     * @param username Username
     * @param password Password
     */
    public void setUtilizador(String username, String password) {
        Utilizador login = new Utilizador(username, password);
        dd.setUtilizador(login);
    }
}
