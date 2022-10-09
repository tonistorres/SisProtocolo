package br.com.subgerentepro.dao;

import br.com.subgerentepro.dto.ProtocoloTFDDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

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
public class ProtocoloTFDDAO implements GenericDAO<ProtocoloTFDDTO> {

    @Override
    public void inserir(ProtocoloTFDDTO protocoloTFD) throws PersistenciaException {

        boolean flagControleLanacaEmail = false;

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
             *
             */
            String sql = "INSERT INTO tbProtocoloTFD(4id_com_data,date_protocolada,departamento_origem,interessado_origem,departamento_destino,interessado_destino,grau_relevancia,login_usuario,perfil,usuario,reposit_HD,reposit_CPU,reposit_placa_mae,data_transferecia,comoseraviagem,cpf) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, protocoloTFD.getIdCustomDto());//id_com_data
            statement.setString(2, protocoloTFD.getDataProtocoloDto());//date_protocolada
            statement.setString(3, protocoloTFD.getDepOrigemDto());//departamento_origem
            statement.setString(4, protocoloTFD.getInteressadoOrigemDto());//interessado_origem
            statement.setString(5, protocoloTFD.getDepDestinoDto());//departamento_destino
            statement.setString(6, protocoloTFD.getInteressadoDestinoDto());//interessado_destino
            statement.setString(7, protocoloTFD.getGrauRelevanciaDto());//grau_relevancia
            statement.setString(8, protocoloTFD.getLoginDto());//login_usuario
            statement.setString(9, protocoloTFD.getPerfilDto());//perfil
            statement.setString(10, protocoloTFD.getUsuarioDto());//usuario
            statement.setString(11, protocoloTFD.getHdDto());//reposit_HP
            statement.setString(12, protocoloTFD.getCpuDto());//reposit_CP
            statement.setString(13, protocoloTFD.getPlacamaeDto());//reposit_placa_mae
            statement.setString(14, protocoloTFD.getDataTransferenciaDto());//data_transferencia
            statement.setString(15, protocoloTFD.getComoSeraViagemDto());//comoseraviagem
            statement.setString(16, protocoloTFD.getCpfDto());//cpf
            statement.execute();

            // importantíssimo fechar sempre a conexão
            connection.close();

            //flag de controle de lancamento de email recebe true 
            flagControleLanacaEmail = true;
        } catch (Exception ex) {

            //caso haja uma exception ela recebe false
            flagControleLanacaEmail = false;
            erroViaEmail(ex.getMessage(),"inserir()");
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    
    @Override
    public void atualizar(ProtocoloTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletar(ProtocoloTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarTudo() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarPorCodigoTabela(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProtocoloTFDDTO> listarTodos() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProtocoloTFDDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProtocoloTFDDTO buscarPor(ProtocoloTFDDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(ProtocoloTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProtocoloTFDDTO filtrarAoClicar(ProtocoloTFDDTO modelo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean inserirControlEmail(ProtocoloTFDDTO protocoloTFD) throws PersistenciaException {

        boolean flagControleLanacaEmail = false;

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
             *
             */
            String sql = "INSERT INTO tbProtocoloTFD(id_com_data,date_protocolada,departamento_origem,interessado_origem,departamento_destino,interessado_destino,grau_relevancia,login_usuario,perfil,usuario,reposit_HD,reposit_CPU,reposit_placa_mae,data_transferecia,comoseraviagem,cpf) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, protocoloTFD.getIdCustomDto());//id_com_data
            statement.setString(2, protocoloTFD.getDataProtocoloDto());//date_protocolada
            statement.setString(3, protocoloTFD.getDepOrigemDto());//departamento_origem
            statement.setString(4, protocoloTFD.getInteressadoOrigemDto());//interessado_origem
            statement.setString(5, protocoloTFD.getDepDestinoDto());//departamento_destino
            statement.setString(6, protocoloTFD.getInteressadoDestinoDto());//interessado_destino
            statement.setString(7, protocoloTFD.getGrauRelevanciaDto());//grau_relevancia
            statement.setString(8, protocoloTFD.getLoginDto());//login_usuario
            statement.setString(9, protocoloTFD.getPerfilDto());//perfil
            statement.setString(10, protocoloTFD.getUsuarioDto());//usuario
            statement.setString(11, protocoloTFD.getHdDto());//reposit_HP
            statement.setString(12, protocoloTFD.getCpuDto());//reposit_CP
            statement.setString(13, protocoloTFD.getPlacamaeDto());//reposit_placa_mae
            statement.setString(14, protocoloTFD.getDataTransferenciaDto());//data_transferencia
            statement.setString(15, protocoloTFD.getComoSeraViagemDto());//comoseraviagem
            statement.setString(16, protocoloTFD.getCpfDto());//cpf
            statement.execute();

            // importantíssimo fechar sempre a conexão
            connection.close();

            //flag de controle de lancamento de email recebe true 
            flagControleLanacaEmail = true;
        } catch (Exception ex) {

            //caso haja uma exception ela recebe false
            flagControleLanacaEmail = false;
            erroViaEmail(ex.getMessage(),"inserirControlEmai()");
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
        
        return flagControleLanacaEmail;
    }

    @Override
    public boolean atualizarControlEmail(ProtocoloTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(ProtocoloTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(ProtocoloTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void erroViaEmail(String mensagemErro,String metodo) {

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
            email.setSubject("Erro Camada DAO -"+metodo);
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Erro:" + mensagemErro + "\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "" + "\n Camada DAO:\n"
                    + "ProtocoloTFDDAO" + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            e.printStackTrace();
        }

    }

    
    
}
