package br.com.subgerentepro.dao;

import br.com.subgerentepro.dto.BairroDTO;
import br.com.subgerentepro.dto.CidadeDTO;
import br.com.subgerentepro.dto.EmpresaDTO;
import br.com.subgerentepro.dto.EstadoDTO;
import br.com.subgerentepro.dto.ModeloEmpresaUfCidadeBairroDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author DaTorres
 */
public class EmpresaDAO {

    EmpresaDTO empresaDTO = new EmpresaDTO();
    BairroDTO bairroDTO = new BairroDTO();
    CidadeDTO cidadeDTO = new CidadeDTO();
    EstadoDTO estadoDTO = new EstadoDTO();
    ModeloEmpresaUfCidadeBairroDTO modeloEmpresaUfCidadeBairroDTO = new ModeloEmpresaUfCidadeBairroDTO();

    public void inserir(EmpresaDTO empresaDTO) throws PersistenciaException {
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
            String sql = "INSERT INTO tbempresa(idEmpresa,CNPJ,Empresa,fk_estado,fk_cidade,bairro,endereco,complemento, numero,foneComercial,celular,contato,vinculo,cadastro) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, empresaDTO.getIdEmpresaDto());//seta idEmpresa
            statement.setString(2, empresaDTO.getCnpjDto());//seta CNPJ
            statement.setString(3, empresaDTO.getEmpresaDto());//seta Empresa
            statement.setInt(4, empresaDTO.getFkEstadoDto());//seta fk_estado
            statement.setInt(5, empresaDTO.getFkCidadeDto());//seta fk_cidade
            statement.setString(6, empresaDTO.getBairroDto());//seta bairro
            statement.setString(7, empresaDTO.getEnderecoDto());//seta endereco
            statement.setString(8, empresaDTO.getComplementoDto());//seta complemento
            statement.setString(9, empresaDTO.getNumeroDto());//seta numero
            statement.setString(10, empresaDTO.getFoneComercialDto());//seta FoneComercial
            statement.setString(11, empresaDTO.getCelularDto());//seta celular
            statement.setString(12, empresaDTO.getContatoDto());//seta contato
            statement.setString(13, empresaDTO.getVinculoDto());//vinculo
            statement.setDate(14, null);
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    public void atualizar(EmpresaDTO empresaDTO) throws PersistenciaException {

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
			 * Esse recurso das interrogações na estrutura do código SQL é utilizada para
			 * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
			 * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
			 * SQL
             */
            String sql = "UPDATE tbempresa SET CNPJ=?,Empresa=?,fk_estado=?,fk_cidade=?,bairro=?,endereco=?,complemento=?,numero=?,foneComercial=?,celular=?,contato=?,vinculo=? WHERE idEmpresa=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, empresaDTO.getCnpjDto());//seta CNPJ
            statement.setString(2, empresaDTO.getEmpresaDto());//seta Empresa
            statement.setInt(3, empresaDTO.getFkEstadoDto());//seta fk_estado
            statement.setInt(4, empresaDTO.getFkCidadeDto());//seta fk_cidade
            statement.setString(5, empresaDTO.getBairroDto());//seta bairro
            statement.setString(6, empresaDTO.getEnderecoDto());//seta endereco
            statement.setString(7, empresaDTO.getComplementoDto());//seta complemento
            statement.setString(8, empresaDTO.getNumeroDto());//seta numero
            statement.setString(9, empresaDTO.getFoneComercialDto());//seta FoneComercial
            statement.setString(10, empresaDTO.getCelularDto());//seta celular
            statement.setString(11, empresaDTO.getContatoDto());//seta contato
            statement.setString(12, empresaDTO.getVinculoDto());//vinculo
            statement.setInt(13, empresaDTO.getIdEmpresaDto());//seta idEmpresa 
            
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

    public List<EmpresaDTO> listarTodos() throws PersistenciaException {

        List<EmpresaDTO> listaDeEmpresas = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

//JOptionPane.showMessageDialog(null,"executar agora sql select");
//Canal Youtube INNER JOIN: https://www.youtube.com/watch?v=4nbECYDlAwc
            String sql = "SELECT * from tbempresa";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                EmpresaDTO empresaDTO = new EmpresaDTO();

                empresaDTO.setIdEmpresaDto(resultSet.getInt("idEmpresa"));
                empresaDTO.setEmpresaDto(resultSet.getString("Empresa"));
                empresaDTO.setCnpjDto(resultSet.getString("CNPJ"));
                empresaDTO.setContatoDto(resultSet.getString("contato"));

                listaDeEmpresas.add(empresaDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeEmpresas;

    }

    public List<ModeloEmpresaUfCidadeBairroDTO> filtrarId(int codigo) throws PersistenciaException {

        List<ModeloEmpresaUfCidadeBairroDTO> listarEmpresaUfCidadeBairroDTO = new ArrayList<>();

        EmpresaDTO empresaDTO = new EmpresaDTO();
        BairroDTO bairroDTO = new BairroDTO();
        CidadeDTO cidadeDTO = new CidadeDTO();
        EstadoDTO estadoDTO = new EstadoDTO();
        ModeloEmpresaUfCidadeBairroDTO modeloEmpresaUfCidadeBairroDTO = new ModeloEmpresaUfCidadeBairroDTO();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            //**************************************************************************
            //INEER JOIN COM MAIS DE DUAS TABELAS    
            //Canal youtube Gustavo Guanabara:https://www.youtube.com/watch?v=jx2ne8iZMOA
            //******************************************************************************
            String sql = "select e.idEmpresa,e.CNPJ,e.Empresa,e.fk_estado,e.fk_cidade,e.bairro,e.endereco,e.complemento,e.numero,e.foneComercial,e.celular,e.contato,u.nome_estado,c.nome_cidade from tbempresa e join estados u on e.fk_estado=u.id_estado join  cidades c on e.fk_cidade=c.id_cidade WHERE e.idEmpresa=" + codigo;
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                empresaDTO = new EmpresaDTO();
                estadoDTO = new EstadoDTO();
                cidadeDTO = new CidadeDTO();
                bairroDTO = new BairroDTO();
                modeloEmpresaUfCidadeBairroDTO = new ModeloEmpresaUfCidadeBairroDTO();

                //Empresa
                empresaDTO.setIdEmpresaDto(resultSet.getInt("idEmpresa"));
                empresaDTO.setCnpjDto(resultSet.getString("CNPJ"));
                empresaDTO.setEmpresaDto(resultSet.getString("Empresa"));
                empresaDTO.setFkEstadoDto(resultSet.getInt("fk_estado"));
                empresaDTO.setFkCidadeDto(resultSet.getInt("fk_cidade"));
                empresaDTO.setBairroDto(resultSet.getString("bairro"));
                empresaDTO.setEnderecoDto(resultSet.getString("endereco"));
                empresaDTO.setComplementoDto(resultSet.getString("complemento"));
                empresaDTO.setNumeroDto(resultSet.getString("numero"));
                empresaDTO.setFoneComercialDto(resultSet.getString("foneComercial"));
                empresaDTO.setCelularDto(resultSet.getString("celular"));
                empresaDTO.setContatoDto(resultSet.getString("contato"));

                //Estado
                estadoDTO.setNomeEstadoDto(resultSet.getString("nome_estado"));

                //Cidade
                cidadeDTO.setNomeCidadeDto(resultSet.getString("nome_cidade"));

                modeloEmpresaUfCidadeBairroDTO.setEmpresaModeloDTO(empresaDTO);
                modeloEmpresaUfCidadeBairroDTO.setEstadoModeloDTO(estadoDTO);
                modeloEmpresaUfCidadeBairroDTO.setCidadeModeloDTO(cidadeDTO);

                listarEmpresaUfCidadeBairroDTO.add(modeloEmpresaUfCidadeBairroDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listarEmpresaUfCidadeBairroDTO;

    }

    
    //pode ser criado a partir desse métdo 
    
    public List<EmpresaDTO> filtrarPesquisaRapida(String pesquisar) throws PersistenciaException {
        List<EmpresaDTO> lista = new ArrayList<EmpresaDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbempresa WHERE Empresa LIKE '%" + pesquisar + "%' order by Empresa";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                EmpresaDTO empresaDTO = new EmpresaDTO();

                empresaDTO.setIdEmpresaDto(resultSet.getInt("idEmpresa"));
                empresaDTO.setEmpresaDto(resultSet.getString("Empresa"));
                empresaDTO.setCnpjDto(resultSet.getString("CNPJ"));
                empresaDTO.setContatoDto(resultSet.getString("contato"));

                lista.add(empresaDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    public int gerarCodigo() {
        int codigoMax = 0;
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT MAX(idEmpresa) FROM tbempresa";
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

    public void deletar(EmpresaDTO empresaDTO) throws PersistenciaException {
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
            String sql = "DELETE FROM tbempresa WHERE idEmpresa=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, empresaDTO.getIdEmpresaDto());

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

    
    
    public boolean verificaDuplicidadeString(String cnpj) throws PersistenciaException {

        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false, a qual
         * irá fazer a verificação se o estado digitado pelo usuário se encontra
         */
        boolean cnpjDuplicado = false;

        try {
            //criando conexão utilizando padtra singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbempresa where CNPJ='" + cnpj+ "'";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cnpjDuplicado = true;
                //JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return cnpjDuplicado;
            }

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return cnpjDuplicado;

    }

    
    
    
    
    
    public boolean verificaDuplicidade(EmpresaDTO empresaDTO) throws PersistenciaException {

        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false, a qual
         * irá fazer a verificação se o estado digitado pelo usuário se encontra
         */
        boolean cnpjDuplicado = false;

        try {
            //criando conexão utilizando padtra singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbempresa where CNPJ='" + empresaDTO.getCnpjDto() + "'";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cnpjDuplicado = true;
                //JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return cnpjDuplicado;
            }

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return cnpjDuplicado;

    }

}
