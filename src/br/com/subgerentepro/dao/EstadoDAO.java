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
package br.com.subgerentepro.dao;


import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.dto.EstadoDTO;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 *
 */
public class EstadoDAO implements GenericDAO<EstadoDTO> {

    /*
	 * OBSERVAÇÃO IMPORTANTE: Os MÉTODOS INSERIR, DELETAR,LISTAR,ATUALIZAR E BUSCAR
	 * POR ID são métodos sobreposto, ou seja, são métodos implementados da Classe
	 * GenericDao, pois, são métodos comuns rotineiros que poderão ser implementados
	 * por outras classe em outra parte da aplicação. De forma que métodos
	 * rotineiros é interessante colocarmos na camada Generic deixando nosso código
	 * mais limpo e bem mais organizado
     */
    @Override
    public void inserir(EstadoDTO estadoDTO) throws PersistenciaException {
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
            String sql = "INSERT INTO estados(nome_estado,sigla_estado) VALUES(?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, estadoDTO.getNomeEstadoDto());
            statement.setString(2, estadoDTO.getSiglaEstadoDto());
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    @Override
    public void atualizar(EstadoDTO estadoDTO) throws PersistenciaException {

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
			 * Esse recurso das interrogações na estrutura do código SQL é utilizada para
			 * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
			 * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
			 * SQL
             */
            String sql = "UPDATE estados SET nome_estado=?,sigla_estado=? WHERE id_estado=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, estadoDTO.getNomeEstadoDto());
            statement.setString(2, estadoDTO.getSiglaEstadoDto());
            statement.setInt(3, estadoDTO.getIdEtadoDto());
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
    public void deletar(EstadoDTO estadoDTO) throws PersistenciaException {

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
            String sql = "DELETE FROM estados WHERE id_estado=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, estadoDTO.getIdEtadoDto());
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
            //  ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO:O Registro não pode ser DELETADO por um dos motivos:\n"
                    + "1º)Falha da comunicação entre o Banco de Dados e Aplicação;"
                    + "\n2º)Ou Estado selecionado pode conter Cidades relacionadas a ele."
            );

        }
    }

    @Override
    public List<EstadoDTO> listarTodos() throws PersistenciaException {

        List<EstadoDTO> listaDeEstados = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM estados order by nome_estado";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                EstadoDTO estadoDTO = new EstadoDTO();

                estadoDTO.setIdEtadoDto(resultSet.getInt("id_estado"));
                estadoDTO.setNomeEstadoDto(resultSet.getString("nome_estado"));
                estadoDTO.setSiglaEstadoDto(resultSet.getString("sigla_estado"));

                listaDeEstados.add(estadoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeEstados;
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EstadoDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public EstadoDTO buscarPor(EstadoDTO estado) throws Exception {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        EstadoDTO estadoDTO = null;

        try {
            /*
			 * Logo abaixo teremos um objeto do Tipo Connection que é o connection, e que
			 * irá receber da Classe ConexaoUtil o retorno da conexão da aplicação com o
			 * Banco de Dados MYSQL
             */

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
            String sql = "SELECT *FROM estados WHERE nome_estado LIKE '%" + estado.getPesquisaDto() + "%'";

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
                estadoDTO = new EstadoDTO();

                estadoDTO.setIdEtadoDto(resultSet.getInt("id_estado"));
                estadoDTO.setNomeEstadoDto(resultSet.getString("nome_estado"));
                estadoDTO.setSiglaEstadoDto(resultSet.getString("sigla_estado"));

                return estadoDTO;
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro metodo novo" + e.getMessage());
        }

        return null;
    }

    public EstadoDTO filtrarAoClicar(EstadoDTO modelo) {

        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        EstadoDTO estadoDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM estados where nome_estado='" + modelo.getNomeEstadoDto() + "'";

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
                estadoDTO = new EstadoDTO();
                estadoDTO.setIdEtadoDto(resultSet.getInt("id_estado"));
                estadoDTO.setNomeEstadoDto(resultSet.getString("nome_estado"));
                estadoDTO.setSiglaEstadoDto(resultSet.getString("sigla_estado"));
                return estadoDTO;
            }

            connection.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro metodo novo" + e.getMessage());
        }

        return null;

    }

    @Override
    public boolean verificaDuplicidade(EstadoDTO estadoDTO) throws PersistenciaException {

        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false, a qual
         * irá fazer a verificação se o estado digitado pelo usuário se encontra
         */
        boolean estadoDuplicado = false;

        try {
            //criando conexão utilizando padtra singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM estados where nome_estado='" + estadoDTO.getNomeEstadoDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                estadoDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return estadoDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return estadoDuplicado;

    }

    public List<EstadoDTO> listaParaFormLocalize() throws PersistenciaException {

        List<EstadoDTO> listaDeEstados = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM estados";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                EstadoDTO estadoDTO = new EstadoDTO();

                estadoDTO.setIdEtadoDto(resultSet.getInt("id_estado"));
                estadoDTO.setNomeEstadoDto(resultSet.getString("nome_estado"));
                estadoDTO.setSiglaEstadoDto(resultSet.getString("sigla_estado"));

                listaDeEstados.add(estadoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeEstados;
        //To change body of generated methods, choose Tools | Templates.
    }

    public EstadoDTO ComboCidadeSetarParaEviarBanco(EstadoDTO estadoDTO) {

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM estados where nome_estado='" + estadoDTO.getNomeEstadoDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                //EstadoDTO estadoDTO = new EstadoDTO();
                estadoDTO.setIdEtadoDto(resultSet.getInt("id_estado"));
                estadoDTO.setNomeEstadoDto(resultSet.getString("nome_estado"));
                estadoDTO.setSiglaEstadoDto(resultSet.getString("sigla_estado"));

                //  listaDeEstados.add(estadoDTO);
                return estadoDTO;

            }

            connection.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro: Método ComboCidadeSetarParaEviarBanco(EstadoDTO estadoDTO)\n"
                    + "Classe EstadoDAO camada de Persistencia\n" + e.getMessage());
        }

        return null;

    }

    
    public List<EstadoDTO> filtrarEstadosPesqRapida(String pesquisarEstados) throws PersistenciaException {

        List<EstadoDTO> listaDeEstados = new ArrayList<EstadoDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM estados WHERE nome_estado LIKE '%" + pesquisarEstados + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                EstadoDTO estadoDTO = new EstadoDTO();

                estadoDTO.setIdEtadoDto(resultSet.getInt("id_estado"));
                estadoDTO.setNomeEstadoDto(resultSet.getString("nome_estado"));
                estadoDTO.setSiglaEstadoDto(resultSet.getString("sigla_estado"));

                listaDeEstados.add(estadoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeEstados;
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarPorCodigoTabela(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public void deletarTudo() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean inserirControlEmail(EstadoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(EstadoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(EstadoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(EstadoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
