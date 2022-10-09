package br.com.subgerentepro.bo;

import br.com.subgerentepro.dao.BancoEmpresaDAO;
import br.com.subgerentepro.dto.BancoEmpresaDTO;
import br.com.subgerentepro.exceptions.ValidacaoException;
import javax.swing.JOptionPane;

/**
 *
 * @author DaTorres
 */
public class BancoEmpresaBO {

    BancoEmpresaDTO bancoEmpresaDTO = new BancoEmpresaDTO();
    BancoEmpresaDAO bancoEmpresaDAO = new BancoEmpresaDAO();

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void EditarBO(BancoEmpresaDTO bancoEmpresaDTO) {

        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        BancoEmpresaDAO bancoEmpresaDAO;
        try {
            bancoEmpresaDAO = new BancoEmpresaDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            bancoEmpresaDAO.atualizar(bancoEmpresaDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:\n" + e.getMessage());
        }
    }

    
    
    
    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void cadastrarBO(BancoEmpresaDTO bancoEmpresaDTO) {
        try {
            /**
             * 1º Vamos criar um objeto de PessoaDAO
             */
            BancoEmpresaDAO bancoEmpresaDAO = new BancoEmpresaDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */
            bancoEmpresaDAO.inserir(bancoEmpresaDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: Método cadastrarBO()" + e.getMessage());
        }
    }

    
    
    
    
    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirBO(BancoEmpresaDTO bancoEmpresaDTO) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        BancoEmpresaDAO bancoEmpresaDAO;
        try {
            bancoEmpresaDAO = new BancoEmpresaDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            bancoEmpresaDAO.deletar(bancoEmpresaDTO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }

    /**
     * Como esse método é um método que verifica algo então terá um retorno
     * boolean verdadeiro ou false
     */
    public boolean validaCodigoBanco(BancoEmpresaDTO bancoEmpresaDTO) throws ValidacaoException {

        boolean ehValido = true;
        /**
         * setar 0(zero) no valor default no Banco de Dados
         */
        if (bancoEmpresaDTO.getIdBancoDto() == 0) {

            ehValido = false;
            throw new ValidacaoException("Campo Codigo Banco e Obrigatorio");

        }

        return ehValido;
    }

    public boolean validaCodigoEmpresa(BancoEmpresaDTO bancoEmpresaDTO) throws ValidacaoException {

        boolean ehValido = true;
        /**
         * setar 0(zero) no valor default no Banco de Dados
         */
        if (bancoEmpresaDTO.getFkEmpresaDto() == 0) {

            ehValido = false;
            throw new ValidacaoException("Campo Codigo Empresa e Obrigatorio");

        }

        return ehValido;
    }

    public boolean validaAgencia(BancoEmpresaDTO bancoEmpresaDTO) throws ValidacaoException {

        boolean ehValido = true;
        /**
         * setar 0(zero) no valor default no Banco de Dados
         */
        if (bancoEmpresaDTO.getAgenciaDto().equals("    - ")) {

            ehValido = false;
            throw new ValidacaoException("Campo Agencia e Obrigatorio");

        }

        return ehValido;
    }
 public boolean validaContaCorrente(BancoEmpresaDTO bancoEmpresaDTO) throws ValidacaoException {

        boolean ehValido = true;
        /**
         * setar 0(zero) no valor default no Banco de Dados
         */
        if (bancoEmpresaDTO.getContaCorrenteDto().equals(" - ")//2-x se selecionado [2]
                ||bancoEmpresaDTO.getContaCorrenteDto().equals("  - ") //32-x se selecionado [3]
                ||bancoEmpresaDTO.getContaCorrenteDto().equals("   - ") //332-x se selecionado [4]
                ||bancoEmpresaDTO.getContaCorrenteDto().equals("    - ") //3332-x se selecionado [5]
                ||bancoEmpresaDTO.getContaCorrenteDto().equals("     - ") //33332-x se selecionado [6]
                ||bancoEmpresaDTO.getContaCorrenteDto().equals("      - ") //333332-x se selecionado [7]
                ||bancoEmpresaDTO.getContaCorrenteDto().equals("       - ") //3333332-x se selecionado [8]
                ||bancoEmpresaDTO.getContaCorrenteDto().equals("        - ") //33333332-x se selecionado [9]
                ||bancoEmpresaDTO.getContaCorrenteDto().equals("         - ") //333333332-x se selecionado [10]
                ) {

            ehValido = false;
            throw new ValidacaoException("Campo Conta e Obrigatorio");

        }

        return ehValido;
    }

public boolean valavoidaFrecido(BancoEmpresaDTO bancoEmpresaDTO) throws ValidacaoException{

        boolean ehValido = true;
        /**
         * setar 0(zero) no valor default no Banco de Dados
         */
        if (bancoEmpresaDTO.getFavorecidoDto().equals("")|| bancoEmpresaDTO.getFavorecidoDto()==null) {

            ehValido = false;
            throw new ValidacaoException("Campo Favorecido e Obrigatorio");

        }

        return ehValido;







}
    
}
