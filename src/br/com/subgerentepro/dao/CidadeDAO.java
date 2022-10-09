package br.com.subgerentepro.dao;

import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.dto.CidadeDTO;
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
public class CidadeDAO implements GenericDAO<CidadeDTO> {

    /*
     * OBSERVAÇÃO IMPORTANTE: Os MÉTODOS INSERIR, DELETAR,LISTAR,ATUALIZAR E BUSCAR
     * POR ID são métodos sobreposto, ou seja, são métodos implementados da Classe
     * GenericDao, pois, são métodos comuns rotineiros que poderão ser implementados
     * por outras classe em outra parte da aplicação. De forma que métodos
     * rotineiros é interessante colocarmos na camada Generic deixando nosso código
     * mais limpo e bem mais organizado
     */
    @Override
    public void inserir(CidadeDTO cidadeDTO) throws PersistenciaException {
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
            String sql = "INSERT INTO cidades(nome_cidade,fk_id_estado) VALUES(?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, cidadeDTO.getNomeCidadeDto());
            statement.setInt(2, cidadeDTO.getChaveEstrangeiraIdEstadoDto());
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    @Override
    public void atualizar(CidadeDTO cidadeDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
             * Esse recurso das interrogações na estrutura do código SQL é utilizada para
             * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
             * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
             * SQL
             */
            String sql = "UPDATE cidades SET nome_cidade=?,fk_id_estado=? WHERE id_cidade=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, cidadeDTO.getNomeCidadeDto());
            statement.setInt(2, cidadeDTO.getChaveEstrangeiraIdEstadoDto());
            statement.setInt(3, cidadeDTO.getCodCidadeDto());
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
    public void deletar(CidadeDTO cidadeDTO) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "DELETE FROM cidades WHERE id_cidade=?";

            PreparedStatement statement;
            statement = connection.prepareStatement(sql);
            statement.setInt(1, cidadeDTO.getCodCidadeDto());
            statement.execute();

            JOptionPane.showMessageDialog(null, "Dado Deletado com Sucesso!!");

            connection.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO:O Registro não pode ser DELETADO por um dos motivos:\n"
                    + "1º)Falha da comunicação entre o Banco de Dados e Aplicação;"
                    + "\n2º)A cidade selecionada pode conter registro de bairros relacionadas a ela."
            );

        }
    }

    public List<CidadeDTO> listarTodosComFiltro(int estadoFiltro) throws PersistenciaException {

        List<CidadeDTO> listaDeCidades = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            //Canal Youtube INNER JOIN: https://www.youtube.com/watch?v=4nbECYDlAwc

           // String sql = "SELECT *FROM cidades WHERE nome_cidade LIKE '%" + pesquisarCidades + "%'";
            String sql = "SELECT cidades.id_cidade,cidades.nome_cidade,estados.sigla_estado FROM cidades INNER JOIN estados ON fk_id_estado=id_estado WHERE fk_id_estado LIKE '%" + estadoFiltro + "%'";
            //  String sql = "SELECT cidades.id_cidade,cidades.nome_cidade,estados.sigla_estado FROM cidades INNER JOIN estados ON fk_id_estado=id_estado ORDER BY nome_cidade";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                CidadeDTO cidadeDTO = new CidadeDTO();

                cidadeDTO.setCodCidadeDto(resultSet.getInt("id_cidade"));
                cidadeDTO.setNomeCidadeDto(resultSet.getString("nome_cidade"));
                cidadeDTO.setSiglaEstadoRecuperarTable(resultSet.getString("sigla_estado"));

                listaDeCidades.add(cidadeDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeCidades;

    }

    @Override
    public List<CidadeDTO> listarTodos() throws PersistenciaException {

        List<CidadeDTO> listaDeCidades = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            //Canal Youtube INNER JOIN: https://www.youtube.com/watch?v=4nbECYDlAwc

            //String sql = "SELECT cidades.id_cidade,cidades.nome_cidade,estados.sigla_estado FROM cidades INNER JOIN estados ON fk_id_estado=id_estado WHERE nome_cidade LIKE '%" + pesquisarCidades + "%'AND fk_id_estado LIKE'%"+estadoFiltro+"%'";  
            String sql = "SELECT cidades.id_cidade,cidades.nome_cidade,estados.sigla_estado FROM cidades INNER JOIN estados ON fk_id_estado=id_estado ORDER BY nome_cidade";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                CidadeDTO cidadeDTO = new CidadeDTO();

                cidadeDTO.setCodCidadeDto(resultSet.getInt("id_cidade"));
                cidadeDTO.setNomeCidadeDto(resultSet.getString("nome_cidade"));
                cidadeDTO.setSiglaEstadoRecuperarTable(resultSet.getString("sigla_estado"));

                listaDeCidades.add(cidadeDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeCidades;

    }

    @Override
    public CidadeDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(CidadeDTO cidadeDTO) throws PersistenciaException {

        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false, a qual
         * irá fazer a verificação se o estado digitado pelo usuário se encontra
         */
        boolean cidadeDuplicada = false;

        try {
            //criando conexão utilizando padtra singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM cidades where nome_cidade='" + cidadeDTO.getNomeCidadeDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                cidadeDuplicada = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return cidadeDuplicada;
            }

            //estado já cadastrado
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return cidadeDuplicada;

    }

    @Override
    public CidadeDTO buscarPor(CidadeDTO cidadeDTO) throws Exception {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        CidadeDTO cidadeDtoCyteNExiste = null;

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
            String sql = "SELECT *FROM estados WHERE nome_estado LIKE '%" + cidadeDTO.getPesquisaDto() + "%'";

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
                cidadeDTO = new CidadeDTO();

                cidadeDTO.setCodCidadeDto(resultSet.getInt("id_cidade"));
                cidadeDTO.setNomeCidadeDto(resultSet.getString("nome_cidade"));
                cidadeDTO.setChaveEstrangeiraIdEstadoDto(resultSet.getInt("fk_id_estado"));

                return cidadeDTO;
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro metodo novo" + e.getMessage());
        }

        return null;

    }

    public CidadeDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        CidadeDTO cidadeDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM cidades where id_cidade=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                cidadeDTO = new CidadeDTO();
                cidadeDTO.setCodCidadeDto(resultSet.getInt("id_cidade"));
                cidadeDTO.setNomeCidadeDto(resultSet.getString("nome_cidade"));
                cidadeDTO.setChaveEstrangeiraIdEstadoDto(resultSet.getInt("fk_id_estado"));
                return cidadeDTO;
            }

            connection.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }

        return null;

    }

    public CidadeDTO ComboCidadeSetarParaEviarBanco(CidadeDTO cidadeDTO) {

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM cidades where nome_cidade='" + cidadeDTO.getNomeCidadeDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */
//order by
            if (resultSet.next()) {

                //EstadoDTO estadoDTO = new EstadoDTO();
                cidadeDTO.setCodCidadeDto(resultSet.getInt("id_cidade"));
                cidadeDTO.setNomeCidadeDto(resultSet.getString("nome_cidade"));
                //estadoDTO.setSiglaEstadoDto(resultSet.getString("sigla_estado"));

                //  listaDeEstados.add(estadoDTO);
                return cidadeDTO;

            }

            connection.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro: Método ComboCidadeSetarParaEviarBanco(CidadeDTO cidadeDTO)\n"
                    + "Classe EstadoDAO camada de Persistencia\n" + e.getMessage());
        }

        return null;

    }

    
    
    
     public List<CidadeDTO> filtrarCidadesPesqRapidaUmFiltroApenas(String pesquisarCidades) throws PersistenciaException {

        List<CidadeDTO> listaDeCidades = new ArrayList<CidadeDTO>();
      
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            
            String sql = "SELECT cidades.id_cidade,cidades.nome_cidade,estados.sigla_estado FROM cidades INNER JOIN estados ON fk_id_estado=id_estado WHERE nome_cidade LIKE '%" + pesquisarCidades +"'";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                CidadeDTO cidadeDTO = new CidadeDTO();

                cidadeDTO.setCodCidadeDto(resultSet.getInt("id_cidade"));
                cidadeDTO.setNomeCidadeDto(resultSet.getString("nome_cidade"));
                cidadeDTO.setSiglaEstadoRecuperarTable(resultSet.getString("sigla_estado"));

                listaDeCidades.add(cidadeDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeCidades;
        //To change body of generated methods, choose Tools | Templates.
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
    
    public List<CidadeDTO> filtrarCidadesPesqRapida(String pesquisarCidades, int estadoFiltro) throws PersistenciaException {

        List<CidadeDTO> listaDeCidades = new ArrayList<CidadeDTO>();
      
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            
            String sql = "SELECT cidades.id_cidade,cidades.nome_cidade,estados.sigla_estado FROM cidades INNER JOIN estados ON fk_id_estado=id_estado WHERE nome_cidade LIKE '%" + pesquisarCidades + "%'AND fk_id_estado='" + estadoFiltro + "'";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                CidadeDTO cidadeDTO = new CidadeDTO();

                cidadeDTO.setCodCidadeDto(resultSet.getInt("id_cidade"));
                cidadeDTO.setNomeCidadeDto(resultSet.getString("nome_cidade"));
                cidadeDTO.setSiglaEstadoRecuperarTable(resultSet.getString("sigla_estado"));

                listaDeCidades.add(cidadeDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeCidades;
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CidadeDTO filtrarAoClicar(CidadeDTO modelo) throws PersistenciaException {

        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        CidadeDTO cidadeDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM cidades where nome_cidade='" + modelo.getNomeCidadeDto() + "'";

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
                /*
                 * Dentro do loop não se esquecer de inicializar o objeto como abaixo, caso
                 * contrário erro de exception
                 */
                cidadeDTO = new CidadeDTO();
                cidadeDTO.setCodCidadeDto(resultSet.getInt("id_cidade"));
                cidadeDTO.setNomeCidadeDto(resultSet.getString("nome_cidade"));
                cidadeDTO.setChaveEstrangeiraIdEstadoDto(resultSet.getInt("fk_id_estado"));
                return cidadeDTO;
            }

            connection.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro camada DAO" + e.getMessage());
        }

        return null;

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
    public boolean inserirControlEmail(CidadeDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(CidadeDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(CidadeDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(CidadeDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
