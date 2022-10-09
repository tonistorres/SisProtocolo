package br.com.subgerentepro.dao;

import br.com.subgerentepro.dto.BancoEmpresaDTO;
import br.com.subgerentepro.dto.EmpresaDTO;
import br.com.subgerentepro.dto.ModeloEmpresaBancoDTO;
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
public class ModeloEmpresaBancoDAO {
    
    public List<ModeloEmpresaBancoDTO> listarTodos() throws PersistenciaException {
        
        List<ModeloEmpresaBancoDTO> listarEmpresaBancoDTO = new ArrayList<>();
        
        BancoEmpresaDTO bancoDTO = new BancoEmpresaDTO();
        EmpresaDTO empresaDTO = new EmpresaDTO();
        ModeloEmpresaBancoDTO modeloEmpresaBancoDTO = new ModeloEmpresaBancoDTO();
        
        try {
            
            Connection connection = ConexaoUtil.getInstance().getConnection();
            /**
             * INNER JOIN retorna linhas quando houver pelo menos uma
             * correspondencia entre ambas as tabelas
             */

            //String sql = "SELECT * FROM tbvendas INNER JOIN tbclientes ON tbvendas.fk_cliente=tbclientes.id"; 
            String sql = "SELECT * FROM tbbancoempresa INNER JOIN tbempresa ON tbbancoempresa.fk_id_empresa=tbempresa.idEmpresa";
            
            PreparedStatement statement = connection.prepareStatement(sql);
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                
                bancoDTO = new BancoEmpresaDTO();
                empresaDTO = new EmpresaDTO();
                modeloEmpresaBancoDTO = new ModeloEmpresaBancoDTO();

                //BancoEmpresa
                bancoDTO.setIdBancoDto(resultSet.getInt("idBanco"));
                bancoDTO.setFkEmpresaDto(resultSet.getInt("fk_id_empresa"));
                bancoDTO.setBancoDto(resultSet.getString("Banco"));
                bancoDTO.setAgenciaDto(resultSet.getString("Agencia"));
                bancoDTO.setContaCorrenteDto(resultSet.getString("Conta"));
                bancoDTO.setFavorecidoDto(resultSet.getString("favorecido"));

                //EmpresaDTO
                empresaDTO.setIdEmpresaDto(resultSet.getInt("idEmpresa"));
                empresaDTO.setEmpresaDto(resultSet.getString("Empresa"));
                
                modeloEmpresaBancoDTO.setModeloBancoEmpresaDto(bancoDTO);
                modeloEmpresaBancoDTO.setModeloEmpresaDto(empresaDTO);
                listarEmpresaBancoDTO.add(modeloEmpresaBancoDTO);
                
            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return listarEmpresaBancoDTO;
        
    }
    
    public List<ModeloEmpresaBancoDTO> filtrarUsuarioPesqRapida(String pesquisar) throws PersistenciaException {
        
        List<ModeloEmpresaBancoDTO> listarEmpresaBancoDTO = new ArrayList<>();
        
        BancoEmpresaDTO bancoDTO = new BancoEmpresaDTO();
        EmpresaDTO empresaDTO = new EmpresaDTO();
        ModeloEmpresaBancoDTO modeloEmpresaBancoDTO = new ModeloEmpresaBancoDTO();
        
        try {
            
            Connection connection = ConexaoUtil.getInstance().getConnection();
            /**
             * INNER JOIN retorna linhas quando houver pelo menos uma
             * correspondencia entre ambas as tabelas
             */

            //String sql = "SELECT * FROM tbvendas INNER JOIN tbclientes ON tbvendas.fk_cliente=tbclientes.id"; 
            String sql = "SELECT * FROM tbbancoempresa INNER JOIN tbempresa ON tbbancoempresa.fk_id_empresa=tbempresa.idEmpresa WHERE Empresa LIKE '%" + pesquisar + "%'";
            
            PreparedStatement statement = connection.prepareStatement(sql);
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                
                bancoDTO = new BancoEmpresaDTO();
                empresaDTO = new EmpresaDTO();
                modeloEmpresaBancoDTO = new ModeloEmpresaBancoDTO();

                //BancoEmpresa
                bancoDTO.setIdBancoDto(resultSet.getInt("idBanco"));
                bancoDTO.setFkEmpresaDto(resultSet.getInt("fk_id_empresa"));
                bancoDTO.setBancoDto(resultSet.getString("Banco"));
                bancoDTO.setAgenciaDto(resultSet.getString("Agencia"));
                bancoDTO.setContaCorrenteDto(resultSet.getString("Conta"));
                bancoDTO.setFavorecidoDto(resultSet.getString("favorecido"));

                //EmpresaDTO
                empresaDTO.setIdEmpresaDto(resultSet.getInt("idEmpresa"));
                // empresaDTO.setCnpjDto(resultSet.getString("CNPJ"));
                empresaDTO.setEmpresaDto(resultSet.getString("Empresa"));
                //  empresaDTO.setFkEstadoDto(resultSet.getInt("fk_estado"));
                //  empresaDTO.setFkCidadeDto(resultSet.getInt("fk_cidade"));
                //  empresaDTO.setFkBancoEmpresaDto(resultSet.getInt("fk_bancoEmpresa"));
                // empresaDTO.setBairroDto(resultSet.getString("bairro"));
                // empresaDTO.setEmpresaDto(resultSet.getString("endereco"));
                // empresaDTO.setComplementoDto(resultSet.getString("complemento"));
                // empresaDTO.setNumeroDto(resultSet.getString("numero"));
                //empresaDTO.setFoneComercialDto(resultSet.getString("foneComercial"));
                //empresaDTO.setCelularDto(resultSet.getString("celular"));
                //empresaDTO.setContatoDto(resultSet.getString("contato"));
                
                modeloEmpresaBancoDTO.setModeloBancoEmpresaDto(bancoDTO);
                modeloEmpresaBancoDTO.setModeloEmpresaDto(empresaDTO);
                listarEmpresaBancoDTO.add(modeloEmpresaBancoDTO);
                
            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return listarEmpresaBancoDTO;
        
    }
    
    public List<ModeloEmpresaBancoDTO> filtrarPorCodigo(int codigo) throws PersistenciaException {
        
        List<ModeloEmpresaBancoDTO> listarEmpresaBancoDTO = new ArrayList<>();
        
        BancoEmpresaDTO bancoDTO = new BancoEmpresaDTO();
        EmpresaDTO empresaDTO = new EmpresaDTO();
        ModeloEmpresaBancoDTO modeloEmpresaBancoDTO = new ModeloEmpresaBancoDTO();
        
        try {
            
            Connection connection = ConexaoUtil.getInstance().getConnection();
            /**
             * INNER JOIN retorna linhas quando houver pelo menos uma
             * correspondencia entre ambas as tabelas
             */
            String sql = "SELECT * FROM tbbancoempresa INNER JOIN tbempresa ON tbbancoempresa.fk_id_empresa=tbempresa.idEmpresa WHERE idBanco LIKE '%" + codigo + "%'";
            
            PreparedStatement statement = connection.prepareStatement(sql);
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                
                bancoDTO = new BancoEmpresaDTO();
                empresaDTO = new EmpresaDTO();
                modeloEmpresaBancoDTO = new ModeloEmpresaBancoDTO();

                
           
            
                //BancoEmpresa
                bancoDTO.setIdBancoDto(resultSet.getInt("idBanco"));
                bancoDTO.setFkEmpresaDto(resultSet.getInt("fk_id_empresa"));
                bancoDTO.setBancoDto(resultSet.getString("Banco"));
                bancoDTO.setAgenciaDto(resultSet.getString("Agencia"));
                bancoDTO.setContaCorrenteDto(resultSet.getString("Conta"));
                bancoDTO.setFavorecidoDto(resultSet.getString("favorecido"));
                bancoDTO.setTipoContaDto(resultSet.getString("TipoConta"));

                //EmpresaDTO
                empresaDTO.setIdEmpresaDto(resultSet.getInt("idEmpresa"));
                // empresaDTO.setCnpjDto(resultSet.getString("CNPJ"));
                empresaDTO.setEmpresaDto(resultSet.getString("Empresa"));
                //  empresaDTO.setFkEstadoDto(resultSet.getInt("fk_estado"));
                //  empresaDTO.setFkCidadeDto(resultSet.getInt("fk_cidade"));
                //  empresaDTO.setFkBancoEmpresaDto(resultSet.getInt("fk_bancoEmpresa"));
                // empresaDTO.setBairroDto(resultSet.getString("bairro"));
                // empresaDTO.setEmpresaDto(resultSet.getString("endereco"));
                // empresaDTO.setComplementoDto(resultSet.getString("complemento"));
                // empresaDTO.setNumeroDto(resultSet.getString("numero"));
                //empresaDTO.setFoneComercialDto(resultSet.getString("foneComercial"));
                //empresaDTO.setCelularDto(resultSet.getString("celular"));
                //empresaDTO.setContatoDto(resultSet.getString("contato"));
                
                modeloEmpresaBancoDTO.setModeloBancoEmpresaDto(bancoDTO);
                modeloEmpresaBancoDTO.setModeloEmpresaDto(empresaDTO);
                listarEmpresaBancoDTO.add(modeloEmpresaBancoDTO);
                
            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return listarEmpresaBancoDTO;
        
    }
    
}
