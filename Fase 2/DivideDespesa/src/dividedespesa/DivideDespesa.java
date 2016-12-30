/*
 * @brief Classe DivideDespesa. Contém métodos que acedem aos objetos da
 *        classes que fazem as ligações à base de dados, realizam operações
 *        na base de dados e fazem parse campos recebidos pela Facade
 *        antes de enviar aos métodos necessários.
 *
 * @author Carlos Pereira   - A61887
 * @author João Barreira    - A73831
 * @author João Gomes       - A74033
 * @author João Reis        - A75372
 */

package dividedespesa;

import dividedespesa.database.*;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DivideDespesa {
    
    // Variáveis de instância
    
    private Utilizador utilizador;
    private static AdministradorDAO adminDAO;
    private static SenhorioDAO senhorioDAO;
    private static QuartoDAO quartosDAO;
    private static MoradorDAO moradoresDAO;
    private static DespesaDAO despesasDAO;
    
    
    // Construtores
    
    /**
     * Construtor vazio.
     */
    public DivideDespesa() {
        utilizador = null;
        adminDAO = new AdministradorDAO();
        senhorioDAO = new SenhorioDAO();
        quartosDAO = new QuartoDAO();
        moradoresDAO = new MoradorDAO();
        despesasDAO = new DespesaDAO();
    }

    
    // Métodos de instância
    
    /**
     * Adiciona um morador ao sistema e associa-lhe uma lista de quartos.
     * 
     * @param username                      Username
     * @param password                      Password
     * @param nome                          Nome
     * @param qrts                          Lista com o número dos quartos
     * @throws MoradorExistenteException    Excepção atirada quando já
     *                                      existe um morador no sistema
     *                                      com o mesmo username
     * @throws SQLException                 Excepção atirada quando não é
     *                                      possível aceder à Base de Dados
     */
    public void addMorador(String username, String password, String nome,
                               List<Integer> qrts) throws MoradorExistenteException, SQLException {
        
        if (moradoresDAO.containsKey(username)) {
            throw new MoradorExistenteException();
        } else {
            Morador novo = new Morador(username, password, nome);
            moradoresDAO.put(novo, qrts);
        }
    }
    
    /**
     * Adiciona uma despesa a um morador.
     * 
     * @param nome                      Nome da despesa
     * @param valor                     Valor da despesa
     * @param tipo                      Tipo da despesa
     * @param dataLimite                Data limite de pagamento
     * @param username                  Username do morador
     * @throws SQLException             Excepção atirada quando não é
     *                                  possível aceder à Base de Dados
     * @throws NumberFormatException    Excepção atirada quando o campo
     *                                  'valor' recebido não está no formato
     *                                  esperado
     */
    public void adicionarDespesa(String nome, double valor, String tipo,
                                 Date dataLimite, String username) throws SQLException, NumberFormatException {
        Date now = new Date();

        int id = despesasDAO.size() + 1;

        Despesa d = new Despesa(id, nome, valor, tipo, new Date(), dataLimite, null);   
        
        despesasDAO.put(d, username);
    }    

    /**
     * Devolve uma colecção com as despesas por pagar de um dado morador.
     * 
     * @param username      Username do morador
     * @return              Despesas por pagar do morador
     * @throws SQLException Excepção que é atirada quando não é possível
     *                      aceder à Base de Dados
     */
    public Collection<Despesa> verDespesasPorPagarCollection(String username) throws SQLException {
        return despesasDAO.userPorPagar(username);
    
    }
    
    /**
     * Devolve uma matriz onde as colunas são diferentes campos de
     * informações sobre uma despesa por pagar e as linhas às despesas.
     * 
     * @param username      Username do morador
     * @return              Matriz com as informações das despesas
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public String[][] verDespesasPorPagar(String username) throws SQLException {
        List<Despesa> despesas = despesasDAO.userPorPagar(username);
        int i = 0;
        String [][] ret = new String[despesas.size()][5]; 
        
        for(Despesa d : despesas) {
            ret[i][0] = String.valueOf(d.getId());
            ret[i][1] = d.getInfo();
            ret[i][2] = String.valueOf(d.getValor());
            ret[i][3] = d.getDataEmissao().toString();
            ret[i][4] = d.getDataLimite().toString();
            i++;
        }
        
        return ret;
    }
    
    /**
     * Devolve uma matriz onde as colunas são diferentes campos de
     * informações sobre uma despesa paga e as linhas às despesas.
     * 
     * @param username      Username do morador
     * @return              Matriz com as informações das despesas
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public String[][] verDespesasPagas(String username) throws SQLException {
        List<Despesa> despesas = despesasDAO.userPagas(username);
        int i = 0;
        String [][] ret = new String[despesas.size()][6]; 
        
        for(Despesa d : despesas) {
            ret[i][0] = String.valueOf(d.getId());
            ret[i][1] = d.getInfo();
            ret[i][2] = String.valueOf(d.getValor());
            ret[i][3] = d.getDataEmissao().toString();
            ret[i][4] = d.getDataLimite().toString();
            ret[i][5] = d.getDataPagamento().toString();
            i++;
        }
        
        return ret;
    }
    
    /**
     * Adiciona a despesa renda a todos os moradores no apartamento.
     * Apenas adiciona a renda se ainda não tiver sido adicionado no mêss
     * atual.
     * 
     * @param dataLimite    Data limite de pagamento da renda
     * @throws SQLException Excepção atirada quando não é possível ligar à
     *                      Base de Dados
     */
    public void cobrarRenda(Date dataLimite) throws SQLException {
        Map<String, Double> usernameRendas = quartosDAO.getUsernamesPrecos();
        
        for(String username : usernameRendas.keySet()) {
            if (aindaNaoCobrouRenda(username)) {
                adicionarDespesa("Renda mensal", usernameRendas.get(username),
                                 "RENDA", dataLimite, username);
            }
        }
    }
    
    /**
     * Verifica se a renda do mês atual já foi cobrada ao morador.
     * 
     * @param username      Username do morador
     * @return              true caso ainda não tenha sido cobrada
     *                      false caso contrário
     * @throws SQLException Excepção atirada quando não é possível aceder à
     *                      Base de Dados
     */
    public boolean aindaNaoCobrouRenda(String username) throws SQLException {
        Date date = despesasDAO.ultimaRenda(username);  //Data da última renda
                                                        //cobrada ao morador
        boolean cobrar = false;
        
        if (date != null) {
            Calendar ultima = Calendar.getInstance();
            ultima.setTime(date);           //Data da última renda cobrada
            ultima.add(Calendar.MONTH, 1);  //Adiciona um mês à data da
                                            //última renda
            
            Calendar agora = Calendar.getInstance(); //Data atual
            
            if (ultima.before(agora)) {
                //Verifica a data após adicionar um mês à última renda é anterior
                //à data atual
                cobrar = true; //A renda deste mês ainda não foi cobrada
            }
            
        } else {
            //Caso seja null quer dizer que não tem nenhuma renda na sua
            //lista de despesas por pagar
            cobrar = true;
        }

        return cobrar;
    }
    
    /**
     * Altera a renda de um quarto.
     * 
     * @param numQuarto Número do quarto
     * @param valor     Novo valor da renda
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public void alteraRendaQuarto(int numQuarto, double valor) throws SQLException {
        quartosDAO.updateRenda(numQuarto, valor);
    }
    
    /**
     * Altera a password de um morador.
     * 
     * @param username      Username do morador
     * @param password      Nova password
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public void alterarPasswordMorador(String username, String password) throws SQLException {
       moradoresDAO.updatePassword(username,password);
    }
    
    /**
     * Verifica se já existe um apartamento registado no sistema.
     * Para isso verifica se existe um senhorio na Base de Dados.
     * 
     * @return              true se existir
     *                      false caso contrário
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public boolean existeApt() throws SQLException {
       return senhorioDAO.existeDB();
    }
    
    /**
     * Altera os quartos de um morador.
     * 
     * @param username      Username do morador
     * @param quartos       Nova lista de quartos a associar ao morador
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public void alterarQuartosMorador(String username, List<Integer> quartos) throws SQLException {
         moradoresDAO.updateMoradorQuarto(moradoresDAO.get(username), quartos); 
    }
    
    /**
     * Verifica se o username e a password pertencem a algum morador registado
     * no sistema.
     * 
     * @param username      Username
     * @param password      Password
     * @return              true caso exista
     *                      false caso contrário
     * @throws SQLException Excepção atirada quando não é possível ligar à Base
     *                      de Dados
     */
    public boolean isMorador(String username, String password) throws SQLException {
        return moradoresDAO.exists(username,password);
    }
    
    /**
     * Verifica se o username e a password pertencem ao senhorio registado
     * no sistema.
     * 
     * @param username      Username
     * @param password      Password
     * @return              true caso exista
     *                      false caso contrário
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public boolean isSenhorio(String username, String password) throws SQLException {      
        return senhorioDAO.exists(username,password);
    }
    
    /**
     * Verifica se o username e a password pertencem ao administrador
     * registado no sistema.
     * 
     * @param username      Username
     * @param password      Password
     * @return              true caso exista
     *                      false caso contrário
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public boolean isAdministrador(String username, String password) throws SQLException {
        return adminDAO.exists(username,password);
    }
    
    /**
     * Devolve um array com o número de cada quarto e o seu preço.
     * 
     * @return              Array de quartos
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public String[] getQuartosString() throws SQLException {
        return quartosDAO.getAll();
    }
    
    /**
     * Devolve um array com o username e o nome de todos os moradores.
     * 
     * @return              Array de moradores
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public String[] getMoradoresString() throws SQLException {
        return moradoresDAO.getAll();
    }
    
    /**
     * Consulta o saldo na conta de um utilizador autenticado.
     * 
     * @param username      Username do utilizador
     * @return              Valor do saldo
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public double consultaSaldo(String username) throws SQLException {
        return moradoresDAO.get(username).getContaCorrente().getSaldo();
    }
    
    /**
     * Altera o valor do saldo do utilizador autenticado na Base de Dados.
     * 
     * @param username      Username do utilizador
     * @param valor         Novo valor do saldo
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public void updateSaldo (String username, double valor) throws SQLException {
        moradoresDAO.updateSaldo(username, valor );
    }
    
    /**
     * Remove um morador do apartamento.
     * 
     * @param username      Username do morador a remover
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public void removerUtilizador(String username) throws SQLException {
        moradoresDAO.updateSaida(username, new Date());
    }

    /**
     * Set do utilizador autenticado.
     * 
     * @param utilizador Utilizador
     */
    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    /**
     * Get do utilizador autenticado.
     * 
     * @return utilizador autenticado
     */
    public Utilizador getUtilizador() {
        return utilizador;
    }
    
    /**
     * Faz o pagamento de uma despesa.
     * Verifica se é possível pagar a despesa com o saldo atual. Caso seja
     * possível, subtrai o valor da despesa ao saldo e faz o update do saldo.
     * 
     * @param username      Username do utilizador autenticado
     * @param idDespesa     Id da despesa a pagar
     * @return              Mensagem correspondente ao sucesso do pagamento
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public String pagaDespesa(String username, int idDespesa)  throws SQLException {
        
        double saldo = consultaSaldo(username),
               valor = despesasDAO.get(idDespesa).getValor();
        
        String ret;
        
        if(saldo < valor) {
            ret = "Valor da despesa superior ao saldo atual.";
        } else {
            updateSaldo(username, saldo - valor);
            despesasDAO.updateDespesa(idDespesa, new Date());
            ret = "Despesa paga.";
        }

        return ret;
    }
    
    /**
     * Regista um apartamento no sistema.
     * Recebe o senhorio e o administrador do apartamento, como também
     * uma lista com o preço dos quartos, onde a posição do preço na lista
     * corresponde ao id do quarto.
     * 
     * @param senhorio      Senhorio
     * @param admin         Administrador
     * @param precosQuartos Preços dos quartos
     * @throws SQLException Excepção atirada quando não é possível ligar
     *                      à Base de Dados
     */
    public void registaApartamento(Senhorio senhorio, Administrador admin,
                            List<Double> precosQuartos) throws SQLException {
        
        senhorioDAO.toDB(senhorio);
                
        adminDAO.toDB(admin);
        
        int i = 0;
        
        for(Double d : precosQuartos) {
            quartosDAO.toDB(i, d);
            i++;
        }
    }
}

