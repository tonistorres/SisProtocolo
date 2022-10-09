
package br.com.subgerentepro.dao;

import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.dto.BairroDTO;
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
public class BairroDAO implements GenericDAO<BairroDTO>{

    @Override
    public void inserir(BairroDTO bairroDTO) throws PersistenciaException {
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
            String sql = "INSERT INTO bairros(nome_bairro,fk_cidade) VALUES(?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, bairroDTO.getNomeBairroDto());
            statement.setInt(2, bairroDTO.getChaveEstrangeiraIdCidadeDto());
            
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    @Override
    public void atualizar(BairroDTO bairroDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
			 * Esse recurso das interrogações na estrutura do código SQL é utilizada para
			 * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
			 * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
			 * SQL
             */
            String sql = "UPDATE bairros SET nome_bairro=?,fk_cidade=? WHERE id_bairro=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, bairroDTO.getNomeBairroDto());
            statement.setInt(2, bairroDTO.getChaveEstrangeiraIdCidadeDto());
            statement.setInt(3, bairroDTO.getIdBairroDto());
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
    public void deletar(BairroDTO bairroDTO) throws PersistenciaException {
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
            String sql = "DELETE FROM bairros WHERE id_bairro=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, bairroDTO.getIdBairroDto());
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
    public List<BairroDTO> listarTodos() throws PersistenciaException {
      
          List<BairroDTO> listaDeBairros = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            //Canal Youtube INNER JOIN: https://www.youtube.com/watch?v=4nbECYDlAwc
            String sql = "SELECT bairros.id_bairro,bairros.nome_bairro,cidades.nome_cidade FROM bairros INNER JOIN cidades ON fk_cidade=id_cidade ORDER BY nome_bairro";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                BairroDTO bairroDTO = new BairroDTO();

                bairroDTO.setIdBairroDto(resultSet.getInt("id_bairro"));
                bairroDTO.setNomeBairroDto(resultSet.getString("nome_bairro"));
                bairroDTO.setNomeCidadeRecuperarDto(resultSet.getString("nome_cidade"));

                listaDeBairros.add(bairroDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeBairros;
      
         
        
    }

    @Override
    public BairroDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BairroDTO buscarPor(BairroDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(BairroDTO bairroDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false, a qual
         * irá fazer a verificação se o estado digitado pelo usuário se encontra
         */
        boolean bairroDuplicado = false;

        try {
            //criando conexão utilizando padtra singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM bairros where nome_bairro='" + bairroDTO.getNomeBairroDto()+ "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                bairroDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return bairroDuplicado;
            }

            //estado já cadastrado
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return bairroDuplicado;

    }

    @Override
    public BairroDTO filtrarAoClicar(BairroDTO modelo) throws PersistenciaException {
         /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        BairroDTO bairroeDTO = null;
        

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM bairros WHERE nome_bairro LIKE'%" + modelo.getNomeBairroDto() + "%'";
            
           
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
                bairroeDTO = new BairroDTO();
                bairroeDTO.setIdBairroDto(resultSet.getInt("id_bairro"));
                bairroeDTO.setNomeBairroDto(resultSet.getString("nome_bairro"));
                bairroeDTO.setChaveEstrangeiraIdCidadeDto(resultSet.getInt("fk_cidade"));
                return bairroeDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

       return null;

    }
    
    
    public List<CidadeDTO> listarComboCidades() throws PersistenciaException {
      
          List<CidadeDTO> listaCidades = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            //Canal Youtube INNER JOIN: https://www.youtube.com/watch?v=4nbECYDlAwc
            String sql = "select *from cidades order by nome_cidade";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                CidadeDTO cidadeDTO = new CidadeDTO();

                cidadeDTO.setCodCidadeDto(resultSet.getInt("id_cidade"));
                cidadeDTO.setNomeCidadeDto(resultSet.getString("nome_cidade"));
               
                listaCidades.add(cidadeDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaCidades;
      
         
        
    }
        
    public List<BairroDTO> filtrarBairrosPesqRapidaComFiltroCidade(String pesquisarBairros, int filtroCidade) throws PersistenciaException {

        List<BairroDTO> listaDeBairros = new ArrayList<BairroDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

       
            String sql = "SELECT bairros.id_bairro,bairros.nome_bairro,cidades.nome_cidade FROM bairros INNER JOIN cidades ON fk_cidade=id_cidade WHERE nome_bairro LIKE '%" + pesquisarBairros + "%' AND fk_cidade='"+filtroCidade+"'";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                BairroDTO bairroDTO = new BairroDTO();

                bairroDTO.setIdBairroDto(resultSet.getInt("id_bairro"));
                bairroDTO.setNomeBairroDto(resultSet.getString("nome_bairro"));
                bairroDTO.setNomeCidadeRecuperarDto(resultSet.getString("nome_cidade"));


                listaDeBairros.add(bairroDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeBairros;
        //To change body of generated methods, choose Tools | Templates.
    }

    public List<BairroDTO> filtrarBairrosPesqRapida(String pesquisarBairros) throws PersistenciaException {

        List<BairroDTO> listaDeBairros = new ArrayList<BairroDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

                                                                                                                            //String sql = "SELECT *FROM bairros WHERE nome_bairro LIKE'%" + modelo.getNomeBairroDto() + "%'";
            String sql = "SELECT bairros.id_bairro,bairros.nome_bairro,cidades.nome_cidade FROM bairros INNER JOIN cidades ON fk_cidade=id_cidade WHERE nome_bairro LIKE '%" + pesquisarBairros + "%'";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                BairroDTO bairroDTO = new BairroDTO();

                bairroDTO.setIdBairroDto(resultSet.getInt("id_bairro"));
                bairroDTO.setNomeBairroDto(resultSet.getString("nome_bairro"));
                bairroDTO.setNomeCidadeRecuperarDto(resultSet.getString("nome_cidade"));


                listaDeBairros.add(bairroDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeBairros;
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
    public boolean inserirControlEmail(BairroDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(BairroDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(BairroDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(BairroDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
