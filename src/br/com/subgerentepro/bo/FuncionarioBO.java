package br.com.subgerentepro.bo;

import br.com.subgerentepro.dao.FuncionarioDAO;
import br.com.subgerentepro.dto.FuncionarioDTO;
import br.com.subgerentepro.exceptions.NegocioException;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.exceptions.ValidacaoException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class FuncionarioBO {

    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    /**
     * Formatador de Datas
     */
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public void cadastrar(FuncionarioDTO funcionarioDTO) throws NegocioException {

        try {

            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

            funcionarioDAO.inserir(funcionarioDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }

    }

    
    //VALIDAÇÃO INDEXADA
    public boolean validacaoBOdosCamposForm(FuncionarioDTO funcionarioDTO) throws ValidacaoException {

        boolean ehValido = true;

        if (funcionarioDTO.getNomeDto()== null
                || funcionarioDTO.getNomeDto().equals("")
                || funcionarioDTO.getNomeDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo NOME Obrigatorio");

        }

        if (funcionarioDTO.getNomeDto().length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo NOME aceita no MAX 49 chars");

        }

        //campo cpf
       
        if (funcionarioDTO.getCpfDto().equals("   .   .   -  ")) {

            ehValido = false;
            throw new ValidacaoException("Campo cpf Obrigatorio");

        }

        if (funcionarioDTO.getCpfDto().length() > 14) {

            ehValido = false;
            throw new ValidacaoException("Campo cpf aceita no MAX 14 chars");

        }

        //campo estado 
        if (funcionarioDTO.getSexoDto()== null
                || funcionarioDTO.getSexoDto().equals("")
                || funcionarioDTO.getSexoDto().toString().isEmpty()) {

            ehValido = false;

            throw new ValidacaoException("Campo SEXO Obrigatorio");

        }


        //campo cidade 
         if (funcionarioDTO.getDepartamentoDto()== null
                 || funcionarioDTO.getDepartamentoDto().equals("")
                 || funcionarioDTO.getDepartamentoDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("DEPARTAMENTO OBRIATORIO");

        }
               
        return ehValido;
    }

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void UsuarioBO(FuncionarioDTO funcionarioDTO) {

        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        FuncionarioDAO funcionarioDAO;
        try {
            funcionarioDAO = new FuncionarioDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            funcionarioDAO.atualizar(funcionarioDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:\n" + e.getMessage());
        }
    }

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirPorCodigoBO(int codigo) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        FuncionarioDAO funcionarioDAO;

        try {

            funcionarioDAO = new FuncionarioDAO();
            funcionarioDAO.deletarPorCodigoTabela(codigo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirBO(FuncionarioDTO funcionarioDTO) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        FuncionarioDAO funcionarioDAO;
        try {
            funcionarioDAO = new FuncionarioDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            funcionarioDAO.deletar(funcionarioDTO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }

    public String ValidarForcaSenha(String senha) throws ValidacaoException {

        String strRetorno = null;

        if (senha == null || senha.equals("")) {

            strRetorno = "Campo Senha Obrigatório.";

            throw new ValidacaoException("Campo SENHA é obrigatório.");

        } else if (senha.length() != 8) {

            strRetorno = "Campo senha deve conter exatamente 8 caracter.";
            throw new ValidacaoException("Campo senha deve conter \nexatamente 8 caracter.");
        } else {

            char c;

            //criando dois cotadores e inicializando-os
            int ctalfa = 0;
            int ctnum = 0;
            int ctcarctEspecial = 0;

            //laço de repetição for agora 
            for (int i = 0; i < senha.length(); i++) {

                c = senha.charAt(i);//i-ésimo caractere da strin s

                // verificando se é um caractere alfabético
                if (((c >= 'A') && (c <= 'Z'))
                        || ((c >= 'a') && (c <= 'z'))) {
                    ctalfa = ctalfa + 1;
                    System.out.printf("%c ---> %da. letra\n", c, ctalfa);
                } // verificando se é um caractere numérico
                else if ((c >= '0') && (c <= '9')) {
                    ctnum = ctnum + 1;
                    System.out.printf("%c ---> %do. número\n", c, ctnum);
                } else {
                    ctcarctEspecial = ctcarctEspecial + 1;
                    System.out.printf("%c\n", c);
                }
            }

            if (ctalfa > 0 && ctnum > 0 && ctcarctEspecial > 0) {
                strRetorno = "Senha Forte";
                return strRetorno;
            } else if ((ctalfa > 0 && ctnum > 0) || (ctalfa > 0 && ctcarctEspecial > 0) || (ctnum > 0 && ctcarctEspecial > 0)) {
                strRetorno = "Senha Mediana";
                return strRetorno;
            } else {
                strRetorno = "Senha Fraca";
                return strRetorno;
            }

        }

    }

    

    public boolean duplicidadeUsuarios(FuncionarioDTO funcionarioDTO) throws PersistenciaException {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        boolean verificaDuplicidade = funcionarioDAO.verificaDuplicidade(funcionarioDTO);

        return verificaDuplicidade;
    }

    public List<FuncionarioDTO> listar() throws PersistenciaException {

        List<FuncionarioDTO> lista = new ArrayList<>();

        try {

            lista = funcionarioDAO.listarTodos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;

    }

    /**
     * Método validaNome() é um método público que tem como retorno uma boolean
     * e recebe como parâmetro uma String.Onde será analisado por este método sé
     * o nome é nulo ou tem um número de caracter superior ao permetido no banco
     * de dados.Método que fica alojado na camada BO(Busines Object, camada de
     * negocio)
     *
     */
    public boolean validaNome(FuncionarioDTO funcionarioDTO) throws ValidacaoException {

        boolean ehValido = true;

        if (funcionarioDTO.getNomeDto() == null || funcionarioDTO.getNomeDto().equals("")) {

            ehValido = false;
            throw new ValidacaoException("Campo nome Obrigatório");

        }
        if (funcionarioDTO.getNomeDto().length() > 50) {
            ehValido = false;
            throw new ValidacaoException("Campo nome aceita no MAX 50 chars");

        }

        return ehValido;
    }

    /**
     * Como esse método é um método que verifica algo então terá um retorno
     * boolean verdadeiro ou false
     */
    public boolean validaEndereco(String endereco) throws ValidacaoException {

        boolean ehValido = true;

        if (endereco == null || endereco.equals("")) {

            ehValido = false;
            throw new ValidacaoException("Campo endereco Obrigatório");

        }

        if (endereco.length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo endereco aceita no MAX 45 chars");

        }

        return ehValido;
    }

    /**
     * Como esse método é um método que verifica algo então terá um retorno
     * boolean verdadeiro ou false
     */
    public boolean validaDtNascimento(String dtNascimento) throws ValidacaoException {

        boolean ehValido = true;

        if (dtNascimento == null || dtNascimento.equals("")) {

            ehValido = false;
            throw new ValidacaoException("Campo Data Nascimento Obrigatório");

        } else {

            try {
                dateFormat.parse(dtNascimento);
                ehValido = true;
            } catch (ParseException ex) {
                ehValido = false;
                throw new ValidacaoException("Formato inválido de Data! \n Ex:01/01/2001");

            }
        }

        return ehValido;
    }

    public void removeTudo() throws ValidacaoException {

        try {

            funcionarioDAO.deletarTudo();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:" + e.getMessage());
            throw new ValidacaoException(e.getMessage());

        }

    }

    public void atualizarBO(FuncionarioDTO funcionarioDTO) {

        try {

            funcionarioDAO.atualizar(funcionarioDTO);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }
 
}//aqui fim da classe 
