package br.com.subgerentepro.bo;

import br.com.subgerentepro.dao.EmpresaDAO;
import br.com.subgerentepro.dto.EmpresaDTO;
import br.com.subgerentepro.exceptions.ValidacaoException;
import javax.swing.JOptionPane;



public class EmpresaBO {

    EmpresaDTO empresaDTO = new EmpresaDTO();
    EmpresaDAO empresaDAO = new EmpresaDAO();
   
    
    
    
     /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void cadastrarBO(EmpresaDTO empresaDTO) {
        try {
            /**
             * 1º Vamos criar um objeto de PessoaDAO
             */
            EmpresaDAO empresaDAO = new EmpresaDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */
            empresaDAO.inserir(empresaDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: Método cadastrarBO()" + e.getMessage());
        }
    }
    
    public void EditarBO(EmpresaDTO empresaDTO) {

        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        EmpresaDAO empresaDAO;
        try {
            empresaDAO = new EmpresaDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            empresaDAO.atualizar(empresaDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:\n" + e.getMessage());
        }
    }

    
    
    
    public boolean validaCodigoEmpresa(EmpresaDTO empresaDTO) throws ValidacaoException {

        boolean ehValido = true;
        /**
         * setar 0(zero) no valor default no Banco de Dados
         */
        if (empresaDTO.getIdEmpresaDto()== 0) {

            ehValido = false;
            throw new ValidacaoException("Campo Codigo Empresa e Obrigatorio");

        }

        return ehValido;
    }

    public boolean validaCodigoEstado(EmpresaDTO empresaDTO) throws ValidacaoException {

        boolean ehValido = true;
        /**
         * setar 0(zero) no valor default no Banco de Dados
         */
        if (empresaDTO.getFkEstadoDto()== 0) {

            ehValido = false;
            throw new ValidacaoException("Campo Codigo Estado e Obrigatorio");

        }

        return ehValido;
    }
public boolean validaCodigoCidade(EmpresaDTO empresaDTO) throws ValidacaoException {

        boolean ehValido = true;
        /**
         * setar 0(zero) no valor default no Banco de Dados
         */
        if (empresaDTO.getFkCidadeDto()== 0) {

            ehValido = false;
            throw new ValidacaoException("Campo Codigo Cidade e Obrigatorio");

        }

        return ehValido;
    }
    
    
    public boolean validaNomeEmpresa(EmpresaDTO empresaDTO) {
        /*
		 * iniciamos a flag ehValido como true Testado a Primeira condição quando o
		 * campo nome atender as regras de validação
         */
        boolean ehValido = true;

        /* Se o nome é nulo ou o nome é vazio */
        if (empresaDTO.getEmpresaDto() == null || empresaDTO.getEmpresaDto().equals("")) {
            JOptionPane.showMessageDialog(null, "Preenchimento campo nome é obrigatorio.");
            /*
			 * ehValido é false, quando o campo NOME não atender as Regras de Validação
             */
            ehValido = false;
            //   JOptionPane.showMessageDialog(null, "OBSERVAÇÃO:\nCampo nome eh Obrigatório xx");

        } else if (empresaDTO.getEmpresaDto().length() > 99) {

            JOptionPane.showMessageDialog(null, "O campo Empresa Excedeu os 100 Caracteres");

            /*
			 * ehValido é false, quando o campo NOME não atender as Regras de Validação
             */
            ehValido = false;

        }
        /* no fim retornamos o método como false ou vazio dependendo do teste feito */
        return ehValido;
    }

    
    public boolean validaEndereco(EmpresaDTO empresaDTO) {
        /*
		 * iniciamos a flag ehValido como true Testado a Primeira condição quando o
		 * campo nome atender as regras de validação
         */
        boolean ehValido = true;

        /* Se o nome é nulo ou o nome é vazio */
        if (empresaDTO.getEnderecoDto()== null || empresaDTO.getEnderecoDto().equals("")) {
            JOptionPane.showMessageDialog(null, "Preenchimento campo Endereco e obrigatorio.");
            /*
			 * ehValido é false, quando o campo NOME não atender as Regras de Validação
             */
            ehValido = false;
            //   JOptionPane.showMessageDialog(null, "OBSERVAÇÃO:\nCampo nome eh Obrigatório xx");

        } else if (empresaDTO.getEnderecoDto().length() > 49) {

            JOptionPane.showMessageDialog(null, "O Campo Endereco Excedeu os 50 Caracteres");

            /*
			 * ehValido é false, quando o campo NOME não atender as Regras de Validação
             */
            ehValido = false;

        }
        /* no fim retornamos o método como false ou vazio dependendo do teste feito */
        return ehValido;
    }

    
    public boolean validaContato(EmpresaDTO empresaDTO) {
        /*
		 * iniciamos a flag ehValido como true Testado a Primeira condição quando o
		 * campo nome atender as regras de validação
         */
        boolean ehValido = true;

        /* Se o nome é nulo ou o nome é vazio */
        if (empresaDTO.getContatoDto()== null || empresaDTO.getContatoDto().equals("")) {
            JOptionPane.showMessageDialog(null, "Preenchimento campo Contato é obrigatorio.");
            /*
			 * ehValido é false, quando o campo NOME não atender as Regras de Validação
             */
            ehValido = false;
            //   JOptionPane.showMessageDialog(null, "OBSERVAÇÃO:\nCampo nome eh Obrigatório xx");

        } else if (empresaDTO.getContatoDto().length() > 49) {

            JOptionPane.showMessageDialog(null, "O campo Contato Excedeu os 50 Caracteres");

            /*
			 * ehValido é false, quando o campo NOME não atender as Regras de Validação
             */
            ehValido = false;

        }
        /* no fim retornamos o método como false ou vazio dependendo do teste feito */
        return ehValido;
    }
    
    
    public void ExcluirBO(EmpresaDTO empresaDTO) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        EmpresaDAO empresaDAO;
        try {
            empresaDAO = new EmpresaDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            empresaDAO.deletar(empresaDTO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }

}
