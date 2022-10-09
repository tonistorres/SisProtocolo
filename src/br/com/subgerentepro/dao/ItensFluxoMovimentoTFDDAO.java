
package br.com.subgerentepro.dao;

import br.com.subgerentepro.dto.ItensFluxoMovimentoTFDDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;


public class ItensFluxoMovimentoTFDDAO implements GenericDAO<ItensFluxoMovimentoTFDDTO>  {

    @Override
    public void inserir(ItensFluxoMovimentoTFDDTO itensFluxoMovimento) throws PersistenciaException {
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
            String sql = "INSERT INTO tbItensFluxoMovimentoTFD(fk_id_com_data,login_usuario,perfil,reposit_HD,reposit_CPU,statusMovimento,recusadoCausa,destino,interessado_destino,dataRegistrada) VALUES(?,?,?,?,?,?,?,?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, itensFluxoMovimento.getFkIDCustomDto());//fk_id_com_data
            statement.setString(2, itensFluxoMovimento.getLoginUsuarioDto());
            statement.setString(3, itensFluxoMovimento.getPerfilDto());
            statement.setString(4, itensFluxoMovimento.getRepositHDDto());
            statement.setString(5, itensFluxoMovimento.getRepositCPUDto());
            statement.setString(6, itensFluxoMovimento.getStatusMovimentoDto());
            statement.setString(7, itensFluxoMovimento.getRecusadoCausaDto());
            statement.setString(8, itensFluxoMovimento.getDepartamentoDestinoDto());
            statement.setString(9, itensFluxoMovimento.getInteressadoDestinoDto());
            statement.setDate(10, null);
             
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "inserir()-Camada DAO  ItensFluxoMovimentoDAO");
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public boolean inserirControlEmail(ItensFluxoMovimentoTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizar(ItensFluxoMovimentoTFDDTO itensFluxoDTo) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
			 * Esse recurso das interrogações na estrutura do código SQL é utilizada para
			 * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
			 * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
			 * SQL
             */
            String sql = "UPDATE tbItensFluxoMovimentoTFD SET lancamento=? WHERE id=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, itensFluxoDTo.getLancamentoDto());
            statement.setInt(2, itensFluxoDTo.getIdDto());
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

    @Override
    public boolean atualizarControlEmail(ItensFluxoMovimentoTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletar(ItensFluxoMovimentoTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(ItensFluxoMovimentoTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarTudo() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(ItensFluxoMovimentoTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarPorCodigoTabela(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ItensFluxoMovimentoTFDDTO> listarTodos() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<ItensFluxoMovimentoTFDDTO> listarTodosParametro(String idCustomizado)throws PersistenciaException{
     
        List<ItensFluxoMovimentoTFDDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbItensFluxoMovimentoTFD  where fk_id_com_data='"+idCustomizado+"' ORDER BY id ASC";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ItensFluxoMovimentoTFDDTO itensFluxoDTO = new ItensFluxoMovimentoTFDDTO();

                itensFluxoDTO.setIdDto(resultSet.getInt("id"));
                itensFluxoDTO.setFkIDCustomDto(resultSet.getString("fk_id_com_data"));
                itensFluxoDTO.setLoginUsuarioDto(resultSet.getString("login_usuario"));
                itensFluxoDTO.setPerfilDto(resultSet.getString("perfil"));
                itensFluxoDTO.setRepositHDDto(resultSet.getString("reposit_HD"));
                itensFluxoDTO.setRepositCPUDto(resultSet.getString("reposit_CPU"));
                itensFluxoDTO.setStatusMovimentoDto(resultSet.getString("statusMovimento"));
                itensFluxoDTO.setRecusadoCausaDto(resultSet.getString("recusadoCausa"));
                itensFluxoDTO.setDepartamentoDestinoDto(resultSet.getString("destino"));
                itensFluxoDTO.setInteressadoDestinoDto(resultSet.getString("interessado_destino"));
                itensFluxoDTO.setDataRegistradaDto(resultSet.getDate("dataRegistrada"));
                itensFluxoDTO.setLancamentoDto(resultSet.getString("lancamento"));
                
                lista.add(itensFluxoDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "listarTodos - Camada DAO:\n"
                    + "tem por finalidade adicionar dados na tabela\n"
                    + "do formulario ViewCnsRegProtocolados.java");
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;
        
    }

    @Override
    public ItensFluxoMovimentoTFDDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItensFluxoMovimentoTFDDTO buscarPor(ItensFluxoMovimentoTFDDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(ItensFluxoMovimentoTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItensFluxoMovimentoTFDDTO filtrarAoClicar(ItensFluxoMovimentoTFDDTO modelo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
