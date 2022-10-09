package br.com.subgerentepro.dao;

import br.com.subgerentepro.dto.BancoTutorDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * AULA 14: 3 CAMADA DAO(Data Access Object)O padrão de projeto DAO surgiu com a
 * necessidade de separarmos a lógica de negócios da lógica de persistência de
 * dados. Este padrão permite que possamos mudar a forma de persistência sem que
 * isso influencie em nada na lógica de negócio, além de tornar nossas classes
 * mais legíveis. Classes DAO são responsáveis por trocar informações com o SGBD
 * e fornecer operações CRUD e de pesquisas, elas devem ser capazes de buscar
 * dados no banco e transformar esses em objetos ou lista de objetos, fazendo
 * uso de listas genéricas (BOX 3), também deverão receber os objetos, converter
 * em instruções SQL e mandar para o banco de dados.Toda interação com a base de
 * dados se dará através destas classes, nunca das classes de negócio, muito
 * menos de formulários.Se aplicarmos este padrão corretamente ele vai abstrair
 * completamente o modo de busca e gravação dos dados, tornando isso
 * transparente para aplicação e facilitando muito na hora de fazermos
 * manutenção na aplicação ou migrarmos de banco de dados.
 *
 * Também conseguimos centralizar a troca de dados com o SGBD (Sistema
 * Gerenciador de Banco de Dados), teremos um ponto único de acesso a dados,
 * tendo assim nossa aplicação um ótimo design orientado a objeto.
 */
public class BancoTutorDAO {

    BancoTutorDTO bancoTutorDTO = new BancoTutorDTO();

    public void inserir(BancoTutorDTO bancoTutorDTO) throws PersistenciaException {
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
            String sql = "INSERT INTO bancotutortfd(fk_id_tfd,Paciente,cpfPaciente,fk_id_tutor,Tutor, Banco,Agencia,Conta,favorecido,perfilCliente,cadastro,TipoConta) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, bancoTutorDTO.getFk_id_tfd());//fk_id_tfd
            statement.setString(2, bancoTutorDTO.getPacienteDto());//Paciente
            statement.setString(3, bancoTutorDTO.getCpfPacienteDto());//cpfPaciente
            statement.setInt(4, bancoTutorDTO.getFk_id_tutor()); //fk_id_tutor
            statement.setString(5, bancoTutorDTO.getTutorDto());//Tutor
            statement.setString(6, bancoTutorDTO.getBancoDto());
            statement.setString(7, bancoTutorDTO.getAgenciaDto());
            statement.setString(8, bancoTutorDTO.getContaCorrenteDto());
            statement.setString(9, bancoTutorDTO.getFavorecidoDto());
            statement.setString(10, bancoTutorDTO.getPerfilClienteDto());
            statement.setDate(11, null);
            statement.setString(12, bancoTutorDTO.getTipoContaDto());

            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    public void atualizar(BancoTutorDTO bancoTutorDTO) throws PersistenciaException {

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
             * Esse recurso das interrogações na estrutura do código SQL é utilizada para
             * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
             * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
             * SQL
             */
            String sql = "UPDATE bancotutortfd SET fk_id_tfd=?,Paciente=?,cpfPaciente=?,fk_id_tutor=?,Tutor=?, Banco=?,Agencia=?,Conta=?,favorecido=?,TipoConta=? WHERE idBanco=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, bancoTutorDTO.getFk_id_tfd());//fk_id_tfd
            statement.setString(2, bancoTutorDTO.getPacienteDto());//Paciente
            statement.setString(3, bancoTutorDTO.getCpfPacienteDto());//cpfPaciente
            statement.setInt(4, bancoTutorDTO.getFk_id_tutor());//fk_id_Tutor
            statement.setString(5, bancoTutorDTO.getTutorDto());//Tutor
            statement.setString(6, bancoTutorDTO.getBancoDto());
            statement.setString(7, bancoTutorDTO.getAgenciaDto());
            statement.setString(8, bancoTutorDTO.getContaCorrenteDto());
            statement.setString(9, bancoTutorDTO.getFavorecidoDto());
            statement.setString(10, bancoTutorDTO.getTipoContaDto());

            statement.setInt(11, bancoTutorDTO.getIdBancoDto());
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

    public void deletar(BancoTutorDTO bancoTutorDTO) throws PersistenciaException {
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
            String sql = "DELETE FROM bancotutortfd WHERE idBanco=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, bancoTutorDTO.getIdBancoDto());

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

    public int gerarCodigo() {
        int codigoMax = 0;
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT MAX(idBanco) FROM bancotutortfd";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                codigoMax = resultSet.getInt(1);

                return codigoMax;
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigoMax;
    }

    public boolean verificaDuplicidade(BancoTutorDTO bancoTutorDTO) throws PersistenciaException {

        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false, a qual
         * irá fazer a verificação se o estado digitado pelo usuário se encontra
         */
        boolean contaDuplicada = false;

        try {
            //criando conexão utilizando padtra singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM bancotutortfd where Conta='" + bancoTutorDTO.getContaCorrenteDto() + "'";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                contaDuplicada = true;
                //JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return contaDuplicada;
            }

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return contaDuplicada;

    }

    public List<BancoTutorDTO> filtrarUsuarioPesqRapida(String pesquisar) throws PersistenciaException {

        List<BancoTutorDTO> lista = new ArrayList<BancoTutorDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM bancotutortfd WHERE paciente LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                BancoTutorDTO bancoTutorDTO = new BancoTutorDTO();
                bancoTutorDTO.setIdBancoDto(resultSet.getInt("idBanco"));
                bancoTutorDTO.setFk_id_tfd(resultSet.getInt("fk_id_tfd"));
                bancoTutorDTO.setPacienteDto(resultSet.getString("Paciente"));
                bancoTutorDTO.setCpfPacienteDto(resultSet.getString("cpfPaciente"));
                bancoTutorDTO.setTutorDto(resultSet.getString("Tutor"));
                bancoTutorDTO.setBancoDto(resultSet.getString("Banco"));
                bancoTutorDTO.setAgenciaDto(resultSet.getString("Agencia"));
                bancoTutorDTO.setContaCorrenteDto(resultSet.getString("Conta"));

                lista.add(bancoTutorDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
    }

    public List<BancoTutorDTO> listarTodos() throws PersistenciaException {
        List<BancoTutorDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM bancotutortfd order by paciente";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                BancoTutorDTO bancoTutorDTO = new BancoTutorDTO();

                bancoTutorDTO.setIdBancoDto(resultSet.getInt("idBanco"));
                bancoTutorDTO.setFk_id_tfd(resultSet.getInt("fk_id_tfd"));
                bancoTutorDTO.setPacienteDto(resultSet.getString("Paciente"));
                bancoTutorDTO.setCpfPacienteDto(resultSet.getString("cpfPaciente"));
                bancoTutorDTO.setTutorDto(resultSet.getString("Tutor"));
                bancoTutorDTO.setBancoDto(resultSet.getString("Banco"));
                bancoTutorDTO.setAgenciaDto(resultSet.getString("Agencia"));
                bancoTutorDTO.setContaCorrenteDto(resultSet.getString("Conta"));

                /**
                 * Adiciona na lista todos os dados capturado pelo laço e
                 * adicionado no objeto tutorDTO
                 */
                lista.add(bancoTutorDTO);

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

     public List<BancoTutorDTO> filtrarPesqRapidaCPF(String pesquisar) throws PersistenciaException {

        List<BancoTutorDTO> lista = new ArrayList<BancoTutorDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM bancotutortfd WHERE cpfPaciente LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                BancoTutorDTO bancoTutorDTO = new BancoTutorDTO();
                bancoTutorDTO.setIdBancoDto(resultSet.getInt("idBanco"));
                bancoTutorDTO.setFk_id_tfd(resultSet.getInt("fk_id_tfd"));
                bancoTutorDTO.setPacienteDto(resultSet.getString("Paciente"));
                bancoTutorDTO.setCpfPacienteDto(resultSet.getString("cpfPaciente"));
                bancoTutorDTO.setTutorDto(resultSet.getString("Tutor"));
                bancoTutorDTO.setBancoDto(resultSet.getString("Banco"));
                bancoTutorDTO.setAgenciaDto(resultSet.getString("Agencia"));
                bancoTutorDTO.setContaCorrenteDto(resultSet.getString("Conta"));

                lista.add(bancoTutorDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
    }

    
}
