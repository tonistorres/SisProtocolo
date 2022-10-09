package br.com.subgerentepro.bo;

import br.com.subgerentepro.dao.PacienteDAO;
import br.com.subgerentepro.dto.PacienteDTO;
import br.com.subgerentepro.exceptions.NegocioException;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.exceptions.ValidacaoException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

public class PacienteBO {

    public void atualizarBO(PacienteDTO pacienteDTO) {

        try {

            PacienteDAO pacienteDAO = new PacienteDAO();

            pacienteDAO.atualizar(pacienteDTO);

        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "atualizarBO CamadaBO");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }

    public void cadastrarBO(PacienteDTO pacienteDTO) throws NegocioException {

        try {

            PacienteDAO pacienteDAO = new PacienteDAO();

            pacienteDAO.inserir(pacienteDTO);

        } catch (Exception e) {
            //robo conectado servidor google 
            erroViaEmail(e.getMessage(), "Camada BO - Erro ao tentar cadastrar paciente \n"
                    + "PacienteBO");
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }

    }

    public boolean duplicidade(PacienteDTO pacienteDTO) throws PersistenciaException {

        PacienteDAO pacienteDAO = new PacienteDAO();

        //a verificação de Duplicidade será feita a partir do CPF do Paciente
        boolean verificaDuplicidade = pacienteDAO.verificaDuplicidade(pacienteDTO);

        //retorna o valor boleano com true ou false
        return verificaDuplicidade;
    }

    //VALIDAÇÃO INDEXADA
    public boolean validacaoBOdosCamposForm(PacienteDTO pacienteDTO) throws ValidacaoException {

        boolean ehValido = true;

        if (pacienteDTO.getNomePacienteDto() == null
                || pacienteDTO.getNomePacienteDto().equals("")
                || pacienteDTO.getNomePacienteDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo paciente Obrigatorio");

        }

        if (pacienteDTO.getNomePacienteDto().length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo paciente aceita no MAX 49 chars");

        }

        //campo cpf
        if (pacienteDTO.getCpfPacienteDto().equals("   .   .   -  ")) {

            ehValido = false;
            throw new ValidacaoException("Campo cpf Obrigatorio");

        }

        if (pacienteDTO.getCpfPacienteDto().length() > 14) {

            ehValido = false;
            throw new ValidacaoException("Campo cpf aceita no MAX 14 chars");

        }

        //campo estado 
        if (pacienteDTO.getEstadoDto() == null
                || pacienteDTO.getEstadoDto().equals("")
                || pacienteDTO.getEstadoDto().toString().isEmpty()) {

            ehValido = false;

            throw new ValidacaoException("Campo estado Obrigatorio");

        }

        if (pacienteDTO.getEstadoDto().length() > 40) {

            ehValido = false;
            throw new ValidacaoException("Campo estado aceita no MAX 40 chars");

        }

        //campo cidade 
        if (pacienteDTO.getCidadeDto() == null
                || pacienteDTO.getCidadeDto().equals("")
                || pacienteDTO.getCidadeDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo cidade Obrigatorio");

        }

        if (pacienteDTO.getCidadeDto().length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo cidade aceita no MAX 49 chars");

        }

        //campo bairro
        if (pacienteDTO.getBairroDto() == null
                || pacienteDTO.getBairroDto().equals("")
                || pacienteDTO.getBairroDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo bairro Obrigatorio");

        }

        if (pacienteDTO.getBairroDto().length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo bairro aceita no MAX 49 chars");

        }

        //campo rua 
        if (pacienteDTO.getRuaPacienteDto() == null
                || pacienteDTO.getRuaPacienteDto().equals("")
                || pacienteDTO.getRuaPacienteDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo rua Obrigatorio");

        }

        if (pacienteDTO.getRuaPacienteDto().length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo rua aceita no MAX 49 chars");

        }

        //campo NCartao SUS 
        if (pacienteDTO.getNumeroCartaoSusDto() == null
                || pacienteDTO.getNumeroCartaoSusDto().equals("")
                || pacienteDTO.getNumeroCartaoSusDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo Numero Cartao SUS Obrigatorio");

        }

          //campo cpf
        if (pacienteDTO.getNumeroCartaoSusDto().equals("   .    .    .    ")) {

            ehValido = false;
            throw new ValidacaoException("Campo Numero Cartao SUS  Obrigatorio");

        }

        return ehValido;
    }

    private void erroViaEmail(String mensagemErro, String metodo) {

        String meuEmail = "sisprotocoloj@gmail.com";
        String minhaSenha = "gerlande2111791020";

        //agora iremos utilizar os objetos das Bibliotecas referente Email
        //commons-email-1.5.jar e mail-1.4.7.jar
        SimpleEmail email = new SimpleEmail();
        //Agora podemos utilizar o objeto email do Tipo SimplesEmail
        //a primeira cois a fazer é acessar o host abaixo estarei usando
        //o host do google para enviar as informações
        email.setHostName("smtp.gmail.com");
        //O proximo método abaixo é para configurar a porta desse host
        email.setSmtpPort(465);
        //esse método vai autenticar a conexão o envio desse email
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        //esse método vai ativar a conexao de forma segura 
        email.setSSLOnConnect(true);

        //agora um try porque os outro métodos podem gerar erros daí devem está dentro de um bloco de exceções
        try {
            //configura de quem é esse email de onde ele está vindo 
            email.setFrom(meuEmail);
            //configurar o assunto 
            email.setSubject("Metodo:" + metodo);
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Mensagem:" + mensagemErro + "\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            erroViaEmail(mensagemErro, metodo);

            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Erro:" + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            e.printStackTrace();
        }

    }

}
