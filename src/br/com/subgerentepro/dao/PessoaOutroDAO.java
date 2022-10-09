package br.com.subgerentepro.dao;

import br.com.subgerentepro.dto.PessoaOutroDTO;
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
public class PessoaOutroDAO implements GenericDAO<PessoaOutroDTO> {

    /**
     * Método Para listar todos os Usuários de uma tabela
     */
    @Override
    public List<PessoaOutroDTO> listarTodos() throws PersistenciaException {

        List<PessoaOutroDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbPessoasOutros order by nome";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                PessoaOutroDTO pessoaOutroDTO = new PessoaOutroDTO();

                pessoaOutroDTO.setIdDto(resultSet.getInt("id"));
                pessoaOutroDTO.setNomeDto(resultSet.getString("nome"));
                pessoaOutroDTO.setObservacaoDto(resultSet.getString("observacao"));
                pessoaOutroDTO.setSexoDto(resultSet.getString("sexo"));
                pessoaOutroDTO.setCelularDto(resultSet.getString("celular"));
               pessoaOutroDTO.setCpfDto(resultSet.getString("cpf"));

                /**
                 * Adiciona na lista todos os dados capturado pelo
                 * laço e adicionado no objeto pessoaOutroDTO
                 */
                lista.add(pessoaOutroDTO);

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

    public List<PessoaOutroDTO> filtrarPesquisaRapida(String pesquisar) throws PersistenciaException {

        List<PessoaOutroDTO> lista = new ArrayList<PessoaOutroDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbPessoasOutros WHERE nome LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                PessoaOutroDTO pessoaOutroDTO = new PessoaOutroDTO();

                pessoaOutroDTO.setIdDto(resultSet.getInt("id"));
                pessoaOutroDTO.setNomeDto(resultSet.getString("nome"));
                pessoaOutroDTO.setObservacaoDto(resultSet.getString("observacao"));
                pessoaOutroDTO.setSexoDto(resultSet.getString("sexo"));
                pessoaOutroDTO.setCelularDto(resultSet.getString("celular"));
               
                lista.add(pessoaOutroDTO);

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
     * retorno do tipo PessoaOutroDTO e que recebe como parâmetro um número inteiro
     * vindo de uma JTable
     */
    public PessoaOutroDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        PessoaOutroDTO pessoaOutroDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbPessoasOutros where id=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                pessoaOutroDTO = new PessoaOutroDTO();

                pessoaOutroDTO.setIdDto(resultSet.getInt("id"));
                pessoaOutroDTO.setNomeDto(resultSet.getString("nome"));
                pessoaOutroDTO.setObservacaoDto(resultSet.getString("observacao"));
                pessoaOutroDTO.setSexoDto(resultSet.getString("sexo"));
                pessoaOutroDTO.setCelularDto(resultSet.getString("celular"));
                pessoaOutroDTO.setDepartamentoDto(resultSet.getString("departamento"));
               
                return pessoaOutroDTO;
            }

            connection.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }

        return null;

    }

    @Override
    public void inserir(PessoaOutroDTO pessoaOutroDTO) throws PersistenciaException {
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
            String sql = "INSERT INTO tbPessoasOutros(nome,observacao,sexo,celular,departamento,cpf,cadastro) VALUES(?,?,?,?,?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, pessoaOutroDTO.getNomeDto());
            statement.setString(2, pessoaOutroDTO.getObservacaoDto());
            statement.setString(3, pessoaOutroDTO.getSexoDto());
            statement.setString(4, pessoaOutroDTO.getCelularDto());
            statement.setString(5, pessoaOutroDTO.getDepartamentoDto());
            statement.setString(6, pessoaOutroDTO.getCpfDto());
            statement.setDate(7, null);
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void atualizar(PessoaOutroDTO pessoaOutroDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
			 * Esse recurso das interrogações na estrutura do código SQL é utilizada para
			 * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
			 * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
			 * SQL
             */
            String sql = "UPDATE tbPessoasOutros SET nome=?,observacao=?,sexo=?,celular=?,departamento=?,cpf=? WHERE id=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, pessoaOutroDTO.getNomeDto());
            statement.setString(2, pessoaOutroDTO.getObservacaoDto());
            statement.setString(3, pessoaOutroDTO.getSexoDto());
            statement.setString(4, pessoaOutroDTO.getCelularDto());
            statement.setString(5, pessoaOutroDTO.getDepartamentoDto());
            statement.setString(6, pessoaOutroDTO.getCpfDto());
            statement.setInt(7, pessoaOutroDTO.getIdDto());
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
    public void deletar(PessoaOutroDTO pessoaOutroDTO) throws PersistenciaException {
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
            String sql = "DELETE FROM tbPessoasOutros WHERE id=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, pessoaOutroDTO.getIdDto());
            /**
             * A estamos disparando por meio do método execute a minhha strig
             * sql devidamente setada
             */
            statement.execute();
     //       JOptionPane.showMessageDialog(null, "Dado Deletado com Sucesso!!");
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
    public PessoaOutroDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PessoaOutroDTO buscarPor(PessoaOutroDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(PessoaOutroDTO pessoaOutroDTO) throws PersistenciaException {
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
            String sql = "SELECT *FROM tbPessoasOutros where nome='" + pessoaOutroDTO.getNomeDto()+ "'";

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
    
     public boolean verificaDuplicidadeCPF(PessoaOutroDTO pessoaOutroDTO) throws PersistenciaException {
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
            String sql = "SELECT *FROM tbPessoasOutros where cpf='" + pessoaOutroDTO.getCpfDto()+ "'";

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
    public PessoaOutroDTO filtrarAoClicar(PessoaOutroDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        PessoaOutroDTO pessoaOutroDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbPessoasOutros WHERE nome LIKE'%" + modelo.getNomeDto()+ "%'";

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
                pessoaOutroDTO = new PessoaOutroDTO();
                
                pessoaOutroDTO.setIdDto(resultSet.getInt("id"));
                pessoaOutroDTO.setNomeDto(resultSet.getString("nome"));
                pessoaOutroDTO.setObservacaoDto(resultSet.getString("observacao"));
                pessoaOutroDTO.setSexoDto(resultSet.getString("sexo"));
                pessoaOutroDTO.setCelularDto(resultSet.getString("celular"));
                
                return pessoaOutroDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }

    @Override
    public boolean inserirControlEmail(PessoaOutroDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(PessoaOutroDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(PessoaOutroDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(PessoaOutroDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  

}
