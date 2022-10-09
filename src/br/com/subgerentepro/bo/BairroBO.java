
package br.com.subgerentepro.bo;

import br.com.subgerentepro.dao.BairroDAO;
import br.com.subgerentepro.dto.BairroDTO;
import javax.swing.JOptionPane;

public class BairroBO {

 /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void cadastrarBO(BairroDTO bairroDTO) {
        try {
            /**
             * 1º Vamos criar um objeto de PessoaDAO
             */
            BairroDAO bairroDAO = new BairroDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */
            bairroDAO.inserir(bairroDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: Método cadastrarBO()" + e.getMessage());
        }
    }
    
    
    
     /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void EditarBO(BairroDTO bairroDTO) {

      
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        BairroDAO bairroDAO;
        try {
            bairroDAO= new BairroDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            bairroDAO.atualizar(bairroDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:\n" + e.getMessage());
        }
    }
    
     /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirBO(BairroDTO bairroDTO) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        BairroDAO bairroDAO;
        try {
            bairroDAO = new BairroDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            bairroDAO.deletar(bairroDTO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }


     /**
     * Criando Um métod de validação para Nome, como esse método vai verificar
     * alguma coisa vai ter um retorno boolean e vai lançar exceções do tipo
     * ValidacaoException
     */
    public boolean validaNome(String nome) {
        /*
		 * iniciamos a flag ehValido como true Testado a Primeira condição quando o
		 * campo nome atender as regras de validação
         */
        boolean ehValido = true;

        /* Se o nome é nulo ou o nome é vazio */
        if (nome == null || nome.equals("")) {
            JOptionPane.showMessageDialog(null, "Preenchimento campo nome é obrigatorio.");
            /*
			 * ehValido é false, quando o campo NOME não atender as Regras de Validação
             */
            ehValido = false;
            //   JOptionPane.showMessageDialog(null, "OBSERVAÇÃO:\nCampo nome eh Obrigatório xx");

        } else if (nome.length() > 50) {

            JOptionPane.showMessageDialog(null, "Excedeu os 50 Caracter");

            /*
			 * ehValido é false, quando o campo NOME não atender as Regras de Validação
             */
            ehValido = false;

        }
        /* no fim retornamos o método como false ou vazio dependendo do teste feito */
        return ehValido;
    }

    
}
