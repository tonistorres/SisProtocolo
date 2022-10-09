package br.com.subgerentepro.dao;

import br.com.subgerentepro.dto.FuncionarioDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author DaTorres
 */
public class FuncionarioDAO implements GenericDAO<FuncionarioDTO> {

    /**
     * Método Para listar todos os Usuários de uma tabela
     */
    @Override
    public List<FuncionarioDTO> listarTodos() throws PersistenciaException {

        List<FuncionarioDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbFuncionarios order by departamento asc, nome asc";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                FuncionarioDTO funcionarioDTO = new FuncionarioDTO();

                funcionarioDTO.setIdDto(resultSet.getInt("id"));
                funcionarioDTO.setNomeDto(resultSet.getString("nome"));
                funcionarioDTO.setDepartamentoDto(resultSet.getString("departamento"));
                funcionarioDTO.setSexoDto(resultSet.getString("sexo"));
                funcionarioDTO.setCelularDto(resultSet.getString("celular"));
               

                /**
                 * Adiciona na lista todos os dados capturado pelo
                 * laço e adicionado no objeto funcionarioDTO
                 */
                lista.add(funcionarioDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

    //LISTAR TODOS OS FUNCIONÁRIOS DE UM DETERMINADO SETOR 
      public List<FuncionarioDTO> listarTodosDoDepartamentoEspecifico(String departamento) throws PersistenciaException {

        List<FuncionarioDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbFuncionarios WHERE nome="+departamento;

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                FuncionarioDTO funcionarioDTO = new FuncionarioDTO();

                funcionarioDTO.setIdDto(resultSet.getInt("id"));
                funcionarioDTO.setNomeDto(resultSet.getString("nome"));
                funcionarioDTO.setDepartamentoDto(resultSet.getString("departamento"));
                funcionarioDTO.setSexoDto(resultSet.getString("sexo"));
                funcionarioDTO.setCelularDto(resultSet.getString("celular"));
               

                /**
                 * Adiciona na lista todos os dados capturado pelo
                 * laço e adicionado no objeto funcionarioDTO
                 */
                lista.add(funcionarioDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

    
    
    public List<FuncionarioDTO> filtrarPesquisaRapida(String pesquisar) throws PersistenciaException {

        List<FuncionarioDTO> lista = new ArrayList<FuncionarioDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbFuncionarios WHERE nome LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                FuncionarioDTO funcionarioDTO = new FuncionarioDTO();

                funcionarioDTO.setIdDto(resultSet.getInt("id"));
                funcionarioDTO.setNomeDto(resultSet.getString("nome"));
                funcionarioDTO.setDepartamentoDto(resultSet.getString("departamento"));
                funcionarioDTO.setSexoDto(resultSet.getString("sexo"));
                funcionarioDTO.setCelularDto(resultSet.getString("celular"));
               
                lista.add(funcionarioDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Esse método que recebe como parâmetro um numero do tipo inteiro irá fazer
     * uma pesquisa no Banco de Dados Infoq na tabela de usuaios e em seguida
     * retorna o nome encontrado caso exista, pois, trata-se de um método com
     * retorno do tipo FuncionarioDTO e que recebe como parâmetro um número inteiro
     * vindo de uma JTable
     */
    public FuncionarioDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        FuncionarioDTO funcionarioDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbFuncionarios where id=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                funcionarioDTO = new FuncionarioDTO();

                funcionarioDTO.setIdDto(resultSet.getInt("id"));
                funcionarioDTO.setNomeDto(resultSet.getString("nome"));
                funcionarioDTO.setDepartamentoDto(resultSet.getString("departamento"));
                funcionarioDTO.setSexoDto(resultSet.getString("sexo"));
                funcionarioDTO.setCelularDto(resultSet.getString("celular"));
                funcionarioDTO.setCpfDto(resultSet.getString("CPF"));
                return funcionarioDTO;
            }

            connection.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }

        return null;

    }

    @Override
    public void inserir(FuncionarioDTO funcionarioDTO) throws PersistenciaException {
        try {

            /**
             * Dentro do bloco try catch temos um objeto do tipo Connection e
             * criamos um objeto connection que recebe da nossa Classe
             * ConexaoUtil um método getInstance do Padrão Singleton e
             * getConection que é nossa conexão de Fato
             */
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /**
             * Esse recurso das interrogações na estrutura do código SQL é
             * utilizada para facilitar o NAO uso do SQL Injection esse é um
             * recurso muito interessante do JDBC que facilitar dessa forma a
             * flexibilidade na montagem do nosso código SQL
             */
            String sql = "INSERT INTO tbFuncionarios(nome,departamento,sexo,celular,CPF,cadastro) VALUES(?,?,?,?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, funcionarioDTO.getNomeDto());
            statement.setString(2, funcionarioDTO.getDepartamentoDto());
            statement.setString(3, funcionarioDTO.getSexoDto());
            statement.setString(4, funcionarioDTO.getCelularDto());
            statement.setString(5, funcionarioDTO.getCpfDto());
            statement.setDate(6, null);
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void atualizar(FuncionarioDTO funcionarioDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
			 * Esse recurso das interrogações na estrutura do código SQL é utilizada para
			 * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
			 * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
			 * SQL
             */
            String sql = "UPDATE tbFuncionarios SET nome=?,departamento=?,sexo=?,celular=?,CPF=? WHERE id=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, funcionarioDTO.getNomeDto());
            statement.setString(2, funcionarioDTO.getDepartamentoDto());
            statement.setString(3, funcionarioDTO.getSexoDto());
            statement.setString(4, funcionarioDTO.getCelularDto());
            statement.setString(5, funcionarioDTO.getCpfDto());
            statement.setInt(6, funcionarioDTO.getIdDto());
            /**
             * executar o statement
             */
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deletar(FuncionarioDTO funcionarioDTO) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /**
             * O código abaixo é uma pesquisa montada na linguagem estruturada
             * de consulta que tem como funcionalidade buscar no campo
             * nome_medico o nome digitado pelo usuário no FormMedico campo
             * txtPesquisa que foi setado pelo metodo de encapsulamento
             * setPesquisa() contido na camada MedicoDTO e agora recuperado pelo
             * parametro medico que recupera o valor digitado pelo
             * getPesquisa(). OBSERVAÇÃO IMPORTANTE:O operador ILIKE é
             * específico do PostgreSQL e seu comportamento é semelhante ao
             * LIKE. A única diferença é que ele é case-insensitive, ou seja,
             * não diferencia maiúsculas de minúsculas.
             * Fontes:https://pt.stackoverflow.com/questions/96926/como-fazer-consulta-sql-que-ignora-mai%C3%BAsculas-min%C3%BAsculas-e-acentos
             */
            /**
             * 12min
             */
            String sql = "DELETE FROM tbFuncionarios WHERE id=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, funcionarioDTO.getIdDto());
            /**
             * A estamos disparando por meio do método execute a minhha strig
             * sql devidamente setada
             */
            statement.execute();
            JOptionPane.showMessageDialog(null, "Dado Deletado com Sucesso!!");
            /**
             * Fecha Conexão
             */
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro na Deleção do Dado\nErro:" + ex.getMessage());

        }
    }

    @Override
    public void deletarTudo() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarPorCodigoTabela(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FuncionarioDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FuncionarioDTO buscarPor(FuncionarioDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(FuncionarioDTO funcionarioDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna duplicado=true(Verdadeiro). Caso contrário
         * duplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean duplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbFuncionarios where nome='" + funcionarioDTO.getNomeDto()+ "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                duplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return duplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return duplicado;

    }

    
    public boolean verificaDuplicidadeCPF(FuncionarioDTO funcionarioDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna duplicado=true(Verdadeiro). Caso contrário
         * duplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean duplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbFuncionarios where CPF='" + funcionarioDTO.getCpfDto()+ "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                duplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return duplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return duplicado;

    }

    
    @Override
    public FuncionarioDTO filtrarAoClicar(FuncionarioDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        FuncionarioDTO funcionarioDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbFuncionarios WHERE nome LIKE'%" + modelo.getNomeDto()+ "%'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                /*
                 * Dentro do loop não se esquecer de inicializar o objeto como abaixo, caso
                 * contrário erro de exception
                 */
                funcionarioDTO = new FuncionarioDTO();
                
                funcionarioDTO.setIdDto(resultSet.getInt("id"));
                funcionarioDTO.setNomeDto(resultSet.getString("nome"));
                funcionarioDTO.setDepartamentoDto(resultSet.getString("departamento"));
                funcionarioDTO.setSexoDto(resultSet.getString("sexo"));
                funcionarioDTO.setCelularDto(resultSet.getString("celular"));
                
                return funcionarioDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }
  /**
     Analista de Sistemas: Tonis Alberto Torres Ferreira 
     * Método: filtrarCidadesPesqRapida() - Esse método tem por finalidade retornar uma lista de cidades em um objtodo do 
     * tipo CidadeDTO que contenha nela valores filtrados do ID, nome (da Cidade) e Sigla do Estado.
     * Adaptação: Utilizamos na linguagem SQL uma pesquisa do tipo Inner Join fazendo a conparação e pegando dados de duas 
     * tabelas cidades e estado e por fim passamos dois parametros um do tipo String pesquisarCidades e outro do tipo int
     * que é pra fazer o filtro trazendo somente as respectivas cidades relacionada ao filtro passado 
     * OBSERVAÇÃO: OLHAR COM BASTANTE CUIDADO QUANDO FIZER OUTRO FILTRO PARECIDO COM ESSE QUE O SEGREDO DOS FILTROS
     * EH NA HORA DE PASSAR OS PARAMETRO UM QUE É pesquisarCidades É PASSADO COM LIKE E % JÁ O OUTRO QUE EU QUERO PASSAR 
     * EXATAMENTE O NÚMERO INTEIRO E NÃO QUE INICIAL COLOCAMOS MESMO IGUAL E TIRAMOS O LIKE.
     */
    
    public List<FuncionarioDTO> filtrarPesqRapidaComParametro(String pesquisar, String filtro) throws PersistenciaException {

        List<FuncionarioDTO> lista = new ArrayList<FuncionarioDTO>();
      
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
    
            String sql = "SELECT *FROM tbFuncionarios WHERE nome LIKE '%" + pesquisar + "%'AND departamento='" + filtro + "'";
            //SELECT *FROM tbFuncionarios order by nome
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                FuncionarioDTO funcionarioDTO = new FuncionarioDTO();

                funcionarioDTO.setIdDto(resultSet.getInt("id"));
                funcionarioDTO.setNomeDto(resultSet.getString("nome"));
                

                lista.add(funcionarioDTO);
               
            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean inserirControlEmail(FuncionarioDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(FuncionarioDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(FuncionarioDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(FuncionarioDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
