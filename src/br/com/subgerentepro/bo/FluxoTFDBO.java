package br.com.subgerentepro.bo;

import br.com.subgerentepro.dao.FluxoTFDDAO;
import br.com.subgerentepro.dao.PacienteDAO;
import br.com.subgerentepro.dao.ProtocoloTFDDAO;
import br.com.subgerentepro.dto.FluxoTFDDTO;

import br.com.subgerentepro.exceptions.NegocioException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

public class FluxoTFDBO {


    public void cadastrar(FluxoTFDDTO fluxoTFDDTO) throws NegocioException {

        try {

           FluxoTFDDAO fluxoTFDDAO = new FluxoTFDDAO();

            fluxoTFDDAO.inserir(fluxoTFDDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }

    }

    
        public boolean cadastrarRetorMsgEmail(FluxoTFDDTO fluxoTFDDTO) throws NegocioException {

            //flag de controle de email 
            boolean retornaControleEmail=false;
            
        try {

           FluxoTFDDAO fluxoTFDDAO = new FluxoTFDDAO();

            retornaControleEmail=fluxoTFDDAO.inserirControlEmail(fluxoTFDDTO);

            return retornaControleEmail;
            
        } catch (Exception e) {
            erroViaEmail("Camada BO FluxoBO:cadastrarRetorMsgEmail", "NºPROT.:"+fluxoTFDDTO.getIdCustomDto()+"\n"+e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }
        return retornaControleEmail;

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
            email.setSubject("Erro Camada DAO -" + metodo);
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Erro:" + mensagemErro + "\n"
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
