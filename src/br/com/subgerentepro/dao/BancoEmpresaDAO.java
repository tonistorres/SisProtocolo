package br.com.subgerentepro.dao;

import br.com.subgerentepro.bo.EmpresaBO;
import br.com.subgerentepro.dto.BancoEmpresaDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class BancoEmpresaDAO {

    BancoEmpresaDTO bancoEmpresaDTO = new BancoEmpresaDTO();

    public void inserir(BancoEmpresaDTO bancoEmpresaDTO) throws PersistenciaException {
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
            String sql = "INSERT INTO tbbancoempresa(idBanco,fk_id_empresa,Banco,Agencia,Conta,favorecido,perfilCliente,cadastro,TipoConta) VALUES(?,?,?,?,?,?,?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, bancoEmpresaDTO.getIdBancoDto());
            statement.setInt(2, bancoEmpresaDTO.getFkEmpresaDto());
            statement.setString(3, bancoEmpresaDTO.getBancoDto());
            statement.setString(4, bancoEmpresaDTO.getAgenciaDto());
            statement.setString(5, bancoEmpresaDTO.getContaCorrenteDto());
            statement.setString(6, bancoEmpresaDTO.getFavorecidoDto());
            statement.setString(7, bancoEmpresaDTO.getPerfilClienteDto());
            statement.setDate(8, null);
            statement.setString(9, bancoEmpresaDTO.getTipoContaDto());
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    public void atualizar(BancoEmpresaDTO bancoEmpresaDTO) throws PersistenciaException {

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
			 * Esse recurso das interrogações na estrutura do código SQL é utilizada para
			 * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
			 * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
			 * SQL
             */
            String sql = "UPDATE tbbancoempresa SET fk_id_empresa=?,Banco=?,Agencia=?,Conta=?,favorecido=?,TipoConta=? WHERE idBanco=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bancoEmpresaDTO.getFkEmpresaDto());
            statement.setString(2, bancoEmpresaDTO.getBancoDto());
            statement.setString(3, bancoEmpresaDTO.getAgenciaDto());
            statement.setString(4, bancoEmpresaDTO.getContaCorrenteDto());
            statement.setString(5, bancoEmpresaDTO.getFavorecidoDto());
            statement.setString(6, bancoEmpresaDTO.getTipoContaDto());

            statement.setInt(7, bancoEmpresaDTO.getIdBancoDto());
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

    public void deletar(BancoEmpresaDTO bancoEmpresaDTO) throws PersistenciaException {
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
            String sql = "DELETE FROM tbbancoempresa WHERE idBanco=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, bancoEmpresaDTO.getIdBancoDto());

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
            String sql = "SELECT MAX(idBanco) FROM tbbancoempresa";
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

    public boolean verificaDuplicidade(BancoEmpresaDTO bancoEmpresaDTO) throws PersistenciaException {

        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false, a qual
         * irá fazer a verificação se o estado digitado pelo usuário se encontra
         */
        boolean contaDuplicada = false;

        try {
            //criando conexão utilizando padtra singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbbancoempresa where Conta='" + bancoEmpresaDTO.getContaCorrenteDto() + "'";
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
    
   
}
