package br.com.subgerentepro.dao;

import br.com.subgerentepro.dto.SetorTramiteInternoDTO;
import br.com.subgerentepro.dto.SetorTramiteInternoDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.dto.UsuarioDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SetorTramiteInternoDAO implements GenericDAO<SetorTramiteInternoDTO> {

    @Override
    public void inserir(SetorTramiteInternoDTO setorTramiteInternoDTO) throws PersistenciaException {
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
             *
             */
            String sql = "INSERT INTO tbSetoresTramiteInterno(nome,cadastro) VALUES(?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, setorTramiteInternoDTO.getNomeDto());
            statement.setDate(2, null);
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void atualizar(SetorTramiteInternoDTO setorTramiteInternoDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
             * Esse recurso das interrogações na estrutura do código SQL é utilizada para
             * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
             * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
             * SQL
             */
            //,,,,,,,
            String sql = "UPDATE tbSetoresTramiteInterno SET nome=? WHERE id=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, setorTramiteInternoDTO.getNomeDto());
            statement.setInt(2, setorTramiteInternoDTO.getIdDto());
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
    public void deletar(SetorTramiteInternoDTO setorTramiteInternoDTO) throws PersistenciaException {

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "DELETE FROM tbSetoresTramiteInterno WHERE id=?";

            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, setorTramiteInternoDTO.getIdDto());
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
    public List<SetorTramiteInternoDTO> listarTodos() throws PersistenciaException {
        List<SetorTramiteInternoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbSetoresTramiteInterno order by nome";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                SetorTramiteInternoDTO setorTramiteInternoDTO = new SetorTramiteInternoDTO();

                setorTramiteInternoDTO.setIdDto(resultSet.getInt("id"));
                setorTramiteInternoDTO.setNomeDto(resultSet.getString("nome"));

                /**
                 * Adiciona na lista todos os dados capturado pelo laço e
                 * adicionado no objeto setorTramiteInternoDTO
                 */
                lista.add(setorTramiteInternoDTO);

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

    public SetorTramiteInternoDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        SetorTramiteInternoDTO setorTramiteInternoDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT * FROM tbSetoresTramiteInterno WHERE id=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                setorTramiteInternoDTO = new SetorTramiteInternoDTO();

                setorTramiteInternoDTO.setIdDto(resultSet.getInt("id"));
                setorTramiteInternoDTO.setNomeDto(resultSet.getString("nome"));

                return setorTramiteInternoDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }

        return null;

    }

    public SetorTramiteInternoDTO buscarPorPerfil(String perfil) throws PersistenciaException {

        SetorTramiteInternoDTO setorTramiteInternoDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            JOptionPane.showMessageDialog(null, "Perfil:" + perfil);
            String sql = "SELECT * FROM tbSetoresTramiteInterno WHERE nome LIKE '%"+perfil+"%'";
            //  String sql = "SELECT *FROM tbtfd WHERE nomepaciente LIKE '%" + pesquisarPaciente + "%'";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                setorTramiteInternoDTO = new SetorTramiteInternoDTO();

                setorTramiteInternoDTO.setIdDto(resultSet.getInt("id"));
                setorTramiteInternoDTO.setNomeDto(resultSet.getString("nome"));

                return setorTramiteInternoDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }

        return null;

    }

    @Override
    public SetorTramiteInternoDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SetorTramiteInternoDTO buscarPor(SetorTramiteInternoDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(SetorTramiteInternoDTO setorTramiteInternoDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna usuarioDuplicado=true(Verdadeiro). Caso contrário
         * usuarioDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean pacienteDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbSetoresTramiteInterno where nome='" + setorTramiteInternoDTO.getNomeDto() + "'";

            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                pacienteDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return pacienteDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return pacienteDuplicado;

    }

    @Override
    public SetorTramiteInternoDTO filtrarAoClicar(SetorTramiteInternoDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        SetorTramiteInternoDTO setorTramiteInternoDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbSetoresTramiteInterno WHERE nome LIKE'%" + modelo.getNomeDto() + "%'";

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
                setorTramiteInternoDTO = new SetorTramiteInternoDTO();

                setorTramiteInternoDTO.setIdDto(resultSet.getInt("id"));
                setorTramiteInternoDTO.setNomeDto(resultSet.getString("nome"));

                return setorTramiteInternoDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }

    public List<SetorTramiteInternoDTO> filtrarUsuarioPesqRapida(String pesquisar) throws PersistenciaException {

        List<SetorTramiteInternoDTO> lista = new ArrayList<SetorTramiteInternoDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbSetoresTramiteInterno WHERE nome LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                SetorTramiteInternoDTO setorTramiteInternoDTO = new SetorTramiteInternoDTO();

                setorTramiteInternoDTO.setIdDto(resultSet.getInt("id"));
                setorTramiteInternoDTO.setNomeDto(resultSet.getString("nome"));

                lista.add(setorTramiteInternoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;

    }

    @Override
    public boolean inserirControlEmail(SetorTramiteInternoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(SetorTramiteInternoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(SetorTramiteInternoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(SetorTramiteInternoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
