package br.com.subgerentepro.dao;

import br.com.subgerentepro.dto.TutorDTO;
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

public class TutorDAO implements GenericDAO<TutorDTO> {

    @Override
    public void inserir(TutorDTO tutorDTO) throws PersistenciaException {
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
            
            
            String sql = "INSERT INTO tbtutortfd(nometutor,cpftutor,fk_estado,fk_cidade,fk_bairro,ruatutor,cadastro) VALUES(?,?,?,?,?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, tutorDTO.getNomeDto());
            statement.setString(2, tutorDTO.getCpfDto());
            statement.setInt(3, tutorDTO.getFkEstadoDto());
            statement.setInt(4, tutorDTO.getFkCidadeDto());
            statement.setInt(5, tutorDTO.getFkBairroDto());
            statement.setString(6, tutorDTO.getRuaDto());
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
    public void atualizar(TutorDTO tutorDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
             * Esse recurso das interrogações na estrutura do código SQL é utilizada para
             * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
             * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
             * SQL
             */
            //,,,,,,,
            String sql = "UPDATE tbtutortfd SET nometutor=?,cpftutor=?,fk_estado=?,fk_cidade=?,fk_bairro=?,ruatutor=? WHERE codtutor=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, tutorDTO.getNomeDto());
            statement.setString(2, tutorDTO.getCpfDto());
            statement.setInt(3, tutorDTO.getFkEstadoDto());
            statement.setInt(4, tutorDTO.getFkCidadeDto());
            statement.setInt(5, tutorDTO.getFkBairroDto());
            statement.setString(6, tutorDTO.getRuaDto());
            

            statement.setInt(7, tutorDTO.getIdDto());
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
    public void deletar(TutorDTO tutorDTO) throws PersistenciaException {

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "DELETE FROM tbtutortfd WHERE codtutor=?";

            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, tutorDTO.getIdDto());
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
    public List<TutorDTO> listarTodos() throws PersistenciaException {
        List<TutorDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbtutortfd order by nometutor";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                TutorDTO tutorDTO = new TutorDTO();

         
                tutorDTO.setIdDto(resultSet.getInt("codtutor"));
                tutorDTO.setNomeDto(resultSet.getString("nometutor"));
                tutorDTO.setCpfDto(resultSet.getString("cpftutor"));
                tutorDTO.setFkEstadoDto(resultSet.getInt("fk_estado"));
                tutorDTO.setFkCidadeDto(resultSet.getInt("fk_cidade"));
                tutorDTO.setFkBairroDto(resultSet.getInt("fk_bairro"));
                tutorDTO.setRuaDto(resultSet.getString("ruatutor"));
                tutorDTO.setCadastroTimeStampDto(resultSet.getDate("cadastro"));

                /**
                 * Adiciona na lista todos os dados capturado pelo
                 * laço e adicionado no objeto tutorDTO
                 */
                lista.add(tutorDTO);

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

    public TutorDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        TutorDTO tutorDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT \n"
                    + "\n"
                    + "T.codtutor,\n"
                    + "T.nometutor,\n"
                    + "T.cpftutor,\n"
                    + "T.fk_estado,\n"
                    + "T.fk_cidade,\n"
                    + "T.fk_bairro,\n"
                    + "T.ruatutor,\n"
                    + "T.cadastro,\n"
                    + "E.nome_estado,\n"
                    + "C.nome_cidade,\n"
                    + "B.nome_bairro\n"
                    + "\n"
                    + "FROM\n"
                    + "tbtutortfd AS T\n"
                    + "\n"
                    + "INNER JOIN estados AS E \n"
                    + "ON T.fk_estado=E.id_estado\n"
                    + "\n"
                    + "INNER JOIN cidades AS C\n"
                    + "ON  T.fk_cidade=C.id_cidade\n"
                    + "\n"
                    + "INNER JOIN bairros AS B\n"
                    + "ON T.fk_bairro=B.id_bairro\n"
                    + "\n"
                    + "WHERE T.codtutor=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                tutorDTO = new TutorDTO();

           
                tutorDTO.setIdDto(resultSet.getInt("codtutor"));
                tutorDTO.setNomeDto(resultSet.getString("nometutor"));
                tutorDTO.setCpfDto(resultSet.getString("cpftutor"));
                tutorDTO.setFkEstadoDto(resultSet.getInt("fk_estado"));
                tutorDTO.setFkCidadeDto(resultSet.getInt("fk_cidade"));
                tutorDTO.setFkBairroDto(resultSet.getInt("fk_bairro"));
                tutorDTO.setEstadoDto(resultSet.getString("nome_estado"));
                tutorDTO.setCidadeDto(resultSet.getString("nome_cidade"));
                tutorDTO.setBairroDto(resultSet.getString("nome_bairro"));
                tutorDTO.setRuaDto(resultSet.getString("ruatutor"));
                tutorDTO.setCadastroTimeStampDto(resultSet.getDate("cadastro"));

                return tutorDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }

        return null;

    }

    @Override
    public TutorDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TutorDTO buscarPor(TutorDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(TutorDTO tutorDTO) throws PersistenciaException {
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
            String sql = "SELECT *FROM tbtutortfd where cpftutor='" + tutorDTO.getCpfDto() + "'";

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

    
      public boolean verificaSeTitularCadastrado(TutorDTO tutorDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna usuarioDuplicado=true(Verdadeiro). Caso contrário
         * usuarioDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

       
        boolean titularDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbtutortfd where nometutor='" + tutorDTO.getNomeDto()+ "'";

            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                titularDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return titularDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return titularDuplicado;

    }
    
    
    @Override
    public TutorDTO filtrarAoClicar(TutorDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        TutorDTO tutorDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbtutortfd WHERE nometutor LIKE'%" + modelo.getNomeDto() + "%'";

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
                tutorDTO = new TutorDTO();

                tutorDTO.setIdDto(resultSet.getInt("codtutor"));
                tutorDTO.setNomeDto(resultSet.getString("nometutor"));
                tutorDTO.setCpfDto(resultSet.getString("cpftutor"));
                tutorDTO.setFkEstadoDto(resultSet.getInt("fk_estado"));
                tutorDTO.setFkCidadeDto(resultSet.getInt("fk_cidade"));
                tutorDTO.setFkBairroDto(resultSet.getInt("fk_bairro "));
                tutorDTO.setRuaDto(resultSet.getString("ruatutor"));
                tutorDTO.setCadastroTimeStampDto(resultSet.getDate("cadastro"));

                return tutorDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }

    public List<TutorDTO> filtrarUsuarioPesqRapida(String pesquisar) throws PersistenciaException {

        List<TutorDTO> lista = new ArrayList<TutorDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbtutortfd WHERE nometutor LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                TutorDTO tutorDTO = new TutorDTO();
                
                tutorDTO.setIdDto(resultSet.getInt("codtutor"));
                tutorDTO.setNomeDto(resultSet.getString("nometutor"));
                tutorDTO.setCpfDto(resultSet.getString("cpftutor"));
                tutorDTO.setFkEstadoDto(resultSet.getInt("fk_estado"));
                tutorDTO.setFkCidadeDto(resultSet.getInt("fk_cidade"));
                tutorDTO.setFkBairroDto(resultSet.getInt("fk_bairro"));
                tutorDTO.setRuaDto(resultSet.getString("ruatutor"));
                tutorDTO.setCadastroTimeStampDto(resultSet.getDate("cadastro"));
               

                lista.add(tutorDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;

    }

    @Override
    public boolean inserirControlEmail(TutorDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(TutorDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(TutorDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(TutorDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
