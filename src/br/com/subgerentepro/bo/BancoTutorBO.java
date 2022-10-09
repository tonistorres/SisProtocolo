package br.com.subgerentepro.bo;

import br.com.subgerentepro.dao.BancoTutorDAO;
import br.com.subgerentepro.dto.BancoTutorDTO;
import br.com.subgerentepro.exceptions.ValidacaoException;
import javax.swing.JOptionPane;

/**
 *
 * @author DaTorres
 */
public class BancoTutorBO {

    BancoTutorDTO bancoTutorDTO = new BancoTutorDTO();
    BancoTutorDAO bancoTutorDAO = new BancoTutorDAO();

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void EditarBO(BancoTutorDTO bancoTutorDTO) {

        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        BancoTutorDAO bancoTutorDAO;
        try {
            bancoTutorDAO = new BancoTutorDAO();
            /*
             * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
             * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            bancoTutorDAO.atualizar(bancoTutorDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:\n" + e.getMessage());
        }
    }

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void cadastrarBO(BancoTutorDTO bancoTutorDTO) {
        try {
            /**
             * 1º Vamos criar um objeto de PessoaDAO
             */
            BancoTutorDAO bancoTutorDAO = new BancoTutorDAO();
            /*
             * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
             * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */
            bancoTutorDAO.inserir(bancoTutorDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: Método cadastrarBO()" + e.getMessage());
        }
    }

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirBO(BancoTutorDTO bancoTutorDTO) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        BancoTutorDAO bancoTutorDAO;
        try {
            bancoTutorDAO = new BancoTutorDAO();
            /*
             * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
             * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            bancoTutorDAO.deletar(bancoTutorDTO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }

    public boolean validacaoIndexada(BancoTutorDTO bancoTutorDTO, int flag) throws ValidacaoException {
        
        boolean ehValido = true;
        
    //    JOptionPane.showMessageDialog(null, "fk_id_Tuturo:" + bancoTutorDTO.getFk_id_tutor() + "\nFlag:" + flag);
        
        if (flag == 2) {
            if (bancoTutorDTO.getIdBancoDto() == 0) {

                ehValido = false;
                throw new ValidacaoException("Campo Codigo Banco e Obrigatorio");

            }
        } else {

        //    JOptionPane.showMessageDialog(null, "fk_id_Tuturo:" + bancoTutorDTO.getFk_id_tutor() + "Flag:" + flag);
            //verificar o se esta relacionado com a tabela CODTUTOR
            if (bancoTutorDTO.getFk_id_tutor() == 0) {

                ehValido = false;
                throw new ValidacaoException("Campo Codigo Tutor e Obrigatorio");

            }

            //verificar o se esta relacionado com a tabela paciente CODTFD 
            if (bancoTutorDTO.getFk_id_tfd() == 0) {

                ehValido = false;
                throw new ValidacaoException("Campo Codigo Paciente e Obrigatorio");

            }

            //verificar se agencia é diferente de nulo ou da mascara que neste caso representa nulo
            if (bancoTutorDTO.getAgenciaDto().equals("    - ")) {

                ehValido = false;
                throw new ValidacaoException("Campo Agencia e Obrigatorio");

            }

            //verificar se o campo favorecido é nulo 
            if (bancoTutorDTO.getFavorecidoDto().equals("") || bancoTutorDTO.getFavorecidoDto() == null) {

                ehValido = false;
                throw new ValidacaoException("Campo Favorecido e Obrigatorio");

            }

        }
        return ehValido;

    }

    public boolean validaContaCorrente(BancoTutorDTO bancoTutorDTO) throws ValidacaoException {

        boolean ehValido = true;
        /**
         * setar 0(zero) no valor default no Banco de Dados
         */
        if (bancoTutorDTO.getContaCorrenteDto().equals(" - ")//2-x se selecionado [2]
                || bancoTutorDTO.getContaCorrenteDto().equals("  - ") //32-x se selecionado [3]
                || bancoTutorDTO.getContaCorrenteDto().equals("   - ") //332-x se selecionado [4]
                || bancoTutorDTO.getContaCorrenteDto().equals("    - ") //3332-x se selecionado [5]
                || bancoTutorDTO.getContaCorrenteDto().equals("     - ") //33332-x se selecionado [6]
                || bancoTutorDTO.getContaCorrenteDto().equals("      - ") //333332-x se selecionado [7]
                || bancoTutorDTO.getContaCorrenteDto().equals("       - ") //3333332-x se selecionado [8]
                || bancoTutorDTO.getContaCorrenteDto().equals("        - ") //33333332-x se selecionado [9]
                || bancoTutorDTO.getContaCorrenteDto().equals("         - ") //333333332-x se selecionado [10]
                ) {

            ehValido = false;
            
            throw new ValidacaoException("Campo Conta e Obrigatorio");

        }

        return ehValido;
    }

    public boolean valavoidaFrecido(BancoTutorDTO bancoTutorDTO) throws ValidacaoException {

        boolean ehValido = true;
        return ehValido;

    }

}
