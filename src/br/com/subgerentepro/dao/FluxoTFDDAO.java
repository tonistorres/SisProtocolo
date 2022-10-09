package br.com.subgerentepro.dao;

import br.com.subgerentepro.dto.FluxoTFDDTO;
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
public class FluxoTFDDAO implements GenericDAO<FluxoTFDDTO> {

    @Override
    public void inserir(FluxoTFDDTO fluxoTFD) throws PersistenciaException {
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
            String sql = "INSERT INTO tbFluxoMovimento(id_com_data,date_protocolada,departamento_origem,interessado_origem,departamento_destino,interessado_destino,grau_relevancia,login_usuario,perfil,usuario,reposit_HD,reposit_CPU,reposit_placa_mae,data_transferecia,comoseraviagem,cpf,paciente,paciente_sexo,paciente_cpf,paciente_rua,paciente_numeroSUS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, fluxoTFD.getIdCustomDto());//id_com_data
            statement.setString(2, fluxoTFD.getDataProtocoloDto());//date_protocolada
            statement.setString(3, fluxoTFD.getDepOrigemDto());//departamento_origem
            statement.setString(4, fluxoTFD.getInteressadoOrigemDto());//interessado_origem
            statement.setString(5, fluxoTFD.getDepDestinoDto());//departamento_destino
            statement.setString(6, fluxoTFD.getInteressadoDestinoDto());//interessado_destino
            statement.setString(7, fluxoTFD.getGrauRelevanciaDto());//grau_relevancia
            statement.setString(8, fluxoTFD.getLoginDto());//login_usuario
            statement.setString(9, fluxoTFD.getPerfilDto());//perfil
            statement.setString(10, fluxoTFD.getUsuarioDto());//usuario
            statement.setString(11, fluxoTFD.getHdDto());//reposit_HP
            statement.setString(12, fluxoTFD.getCpuDto());//reposit_CP
            statement.setString(13, fluxoTFD.getPlacamaeDto());//reposit_placa_mae
            statement.setString(14, fluxoTFD.getDataTransferenciaDto());//data_transferencia
            statement.setString(15, fluxoTFD.getComoSeraViagemDto());//comoseraviagem
            statement.setString(16, fluxoTFD.getCpfDto());//cpf
            statement.setString(17, fluxoTFD.getNome_pacienteDto());
            statement.setString(18, fluxoTFD.getSexo_pacienteDto());
            statement.setString(19, fluxoTFD.getCpf_pacienteDto());
            statement.setString(20, fluxoTFD.getRua_pacienteDto());
            statement.setString(21, fluxoTFD.getnCartaoSus_pacienteDto());
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "inserir()-Camada DAO FluxoDAO");
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void atualizar(FluxoTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletar(FluxoTFDDTO obj) throws PersistenciaException {
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
    public List<FluxoTFDDTO> listarTodos() throws PersistenciaException {

        List<FluxoTFDDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbFluxoMovimento order by id_com_data ASC";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                FluxoTFDDTO fluxoDTO = new FluxoTFDDTO();

                fluxoDTO.setIdCustomDto(resultSet.getString("id_com_data"));
                fluxoDTO.setInteressadoOrigemDto(resultSet.getString("interessado_origem"));
                fluxoDTO.setCpfDto(resultSet.getString("cpf"));
                /**
                 * Adiciona na listaDePaciente todos os dados capturado pelo
                 * laço e adicionado no objeto pacienteDTO
                 */
                lista.add(fluxoDTO);

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

    
    
     public void atualizarPagamentoStatusTransacao(FluxoTFDDTO fluxoTFD) throws PersistenciaException {
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
            String sql = "UPDATE tbFluxoMovimento SET statustransacao=?,datatransacao=? WHERE id_com_data=?";
   
            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, fluxoTFD.getStatutsTrasacaoDto());
            statement.setString(2, fluxoTFD.getDataTransacaoDto());
            statement.setString(3, fluxoTFD.getIdCustomDto());
            
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "atualizarPagamentoStatusTransacao()-Camada DAO FluxoDAO");
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }
    
    
       public FluxoTFDDTO buscarStatusTransacao(String idCustom) throws PersistenciaException {

          FluxoTFDDTO fluxoDTO = null;

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbFluxoMovimento WHERE id_com_data='"+idCustom+"'";
        

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
             fluxoDTO = new FluxoTFDDTO();
             
             
             
               fluxoDTO.setStatutsTrasacaoDto(resultSet.getString("statustransacao"));
             
             
             return fluxoDTO;
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
        return null;
    }

 
    
    
    
    
    @Override
    public FluxoTFDDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FluxoTFDDTO buscarPor(FluxoTFDDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(FluxoTFDDTO fluxoDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna usuarioDuplicado=true(Verdadeiro). Caso contrário
         * usuarioDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */
        
        boolean Duplicado = false;
        
        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            
            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbFluxoMovimento where id_com_data='" + fluxoDTO.getIdCustomDto() + "'";
            
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                
                Duplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return Duplicado;
            }
            
            connection.close();
            
        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "verificaDuplicidade()-Camada DAO - Class PacienteDAO");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }
        
        return Duplicado;
    
    }

    @Override
    public FluxoTFDDTO filtrarAoClicar(FluxoTFDDTO modelo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean inserirControlEmail(FluxoTFDDTO fluxoTFD) throws PersistenciaException {

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
            String sql = "INSERT INTO tbFluxoMovimento(id_com_data,date_protocolada,departamento_origem,interessado_origem,departamento_destino,interessado_destino,grau_relevancia,login_usuario,perfil,usuario,reposit_HD,reposit_CPU,reposit_placa_mae,data_transferecia,comoseraviagem,cpf,paciente,paciente_sexo,paciente_cpf,paciente_rua,paciente_numeroSUS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, fluxoTFD.getIdCustomDto());//id_com_data
            statement.setString(2, fluxoTFD.getDataProtocoloDto());//date_protocolada
            statement.setString(3, fluxoTFD.getDepOrigemDto());//departamento_origem
            statement.setString(4, fluxoTFD.getInteressadoOrigemDto());//interessado_origem
            statement.setString(5, fluxoTFD.getDepDestinoDto());//departamento_destino
            statement.setString(6, fluxoTFD.getInteressadoDestinoDto());//interessado_destino
            statement.setString(7, fluxoTFD.getGrauRelevanciaDto());//grau_relevancia
            statement.setString(8, fluxoTFD.getLoginDto());//login_usuario
            statement.setString(9, fluxoTFD.getPerfilDto());//perfil
            statement.setString(10, fluxoTFD.getUsuarioDto());//usuario
            statement.setString(11, fluxoTFD.getHdDto());//reposit_HP
            statement.setString(12, fluxoTFD.getCpuDto());//reposit_CP
            statement.setString(13, fluxoTFD.getPlacamaeDto());//reposit_placa_mae
            statement.setString(14, fluxoTFD.getDataTransferenciaDto());//data_transferencia
            statement.setString(15, fluxoTFD.getComoSeraViagemDto());//comoseraviagem
            statement.setString(16, fluxoTFD.getCpfDto());//cpf
            statement.setString(17, fluxoTFD.getNome_pacienteDto());
            statement.setString(18, fluxoTFD.getSexo_pacienteDto());
            statement.setString(19, fluxoTFD.getCpf_pacienteDto());
            statement.setString(20, fluxoTFD.getRua_pacienteDto());
            statement.setString(21, fluxoTFD.getnCartaoSus_pacienteDto());
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();
            //flag de controle de lancamento de email recebe true 
            flagControleLanacaEmail = true;
        } catch (Exception ex) {
            //caso haja uma exception ela recebe false
            flagControleLanacaEmail = false;
            //enviar mensagem de erro para meu email 
            erroViaEmail(ex.getMessage(), "inserirControlEmail()");
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
        return flagControleLanacaEmail;
    }

    @Override
    public boolean atualizarControlEmail(FluxoTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(FluxoTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(FluxoTFDDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public FluxoTFDDTO buscarIdCustomizado(String pesquisar) throws PersistenciaException {

        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        FluxoTFDDTO fluxoDTO = null;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbFluxoMovimento WHERE id_com_data LIKE '%" + pesquisar+ "%'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                /*
                 * Dentro do loop não se esquecer de inicializar o objeto como abaixo, caso
                 * contrário erro de exception
                 */
              FluxoTFDDTO  fluxo = new FluxoTFDDTO();

                
              fluxo.setIdCustomDto(resultSet.getString("id_com_data"));
              fluxo.setDataProtocoloDto(resultSet.getString("date_protocolada"));
              fluxo.setDepOrigemDto(resultSet.getString("departamento_origem"));
              fluxo.setInteressadoOrigemDto(resultSet.getString("interessado_origem"));
              fluxo.setInteressadoDestinoDto(resultSet.getString("interessado_destino"));
              fluxo.setDepDestinoDto(resultSet.getString("departamento_destino"));
              fluxo.setDataTransferenciaDto(resultSet.getString("data_Transferecia"));
            
              //Dados do Paciente setar no banco 
              fluxo.setNome_pacienteDto(resultSet.getString("paciente"));
              //fluxo.setRua_pacienteDto(resultSet.getString("paciente_rua"));
             // fluxo.setSexo_pacienteDto(resultSet.getString("paciente_sexo"));
              fluxo.setnCartaoSus_pacienteDto(resultSet.getString("paciente_numeroSUS"));
              fluxo.setCpf_pacienteDto(resultSet.getString("cpf"));
              
                
                        
                return fluxo;
            }

            connection.close();

        } catch (Exception ex) {

            erroViaEmail(ex.getMessage(), "buscarIdCustomizado(String pesquisar)-Camada DAO \n"
                    + "- Class FluxoTFDDAO");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo buscarPeloCPF Camada DAO\n" + ex.getMessage());

        }

        return null;

    }

    public List<FluxoTFDDTO> filtrarId(String pesquisar) throws PersistenciaException {

        List<FluxoTFDDTO> lista = new ArrayList<FluxoTFDDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbFluxoMovimento WHERE id_com_data LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                FluxoTFDDTO fluxoDTO = new FluxoTFDDTO();

                fluxoDTO.setIdCustomDto(resultSet.getString("id_com_data"));
                fluxoDTO.setCpfDto(resultSet.getString("cpf"));
                fluxoDTO.setInteressadoOrigemDto(resultSet.getString("interessado_origem"));
                lista.add(fluxoDTO);

            }
            connection.close();
        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "filtrarPesqRapida(String pesquisar)-camada DAO\n"
                    + "responsavél por fazer a pesquisa no banco e retornar\n "
                    + "o dado filtrado pelo idCustom para ser lançado na tabela\n"
                    + "onde será gerado o ReciboTFD e CapaTFD");
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;

    }

    public List<FluxoTFDDTO> filtrarPesqRapida(String pesquisar) throws PersistenciaException {

        List<FluxoTFDDTO> lista = new ArrayList<FluxoTFDDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbFluxoMovimento WHERE id_com_data LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                FluxoTFDDTO fluxoDTO = new FluxoTFDDTO();

                fluxoDTO.setIdCustomDto(resultSet.getString("id_com_data"));
                fluxoDTO.setCpfDto(resultSet.getString("cpf"));
                fluxoDTO.setInteressadoOrigemDto(resultSet.getString("interessado_origem"));
                lista.add(fluxoDTO);

            }
            connection.close();
        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "filtrarPesqRapida(String pesquisar)-camada DAO\n"
                    + "responsavél por fazer a pesquisa no banco e retornar\n "
                    + "o dado filtrado pelo idCustom para ser lançado na tabela\n"
                    + "onde será gerado o ReciboTFD e CapaTFD");
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;

    }

    public List<FluxoTFDDTO> filtrarPesqRapidaCPF(String pesquisar) throws PersistenciaException {

        List<FluxoTFDDTO> lista = new ArrayList<FluxoTFDDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbFluxoMovimento WHERE cpf LIKE '%" + pesquisar + "%'ORDER BY id_com_data ASC";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                FluxoTFDDTO fluxoDTO = new FluxoTFDDTO();

                fluxoDTO.setIdCustomDto(resultSet.getString("id_com_data"));
                fluxoDTO.setCpfDto(resultSet.getString("cpf"));
                fluxoDTO.setInteressadoOrigemDto(resultSet.getString("interessado_origem"));
                lista.add(fluxoDTO);

            }
            connection.close();
        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "filtrarPesqRapida(String pesquisar)-camada DAO\n"
                    + "responsavél por fazer a pesquisa no banco e retornar\n "
                    + "o dado filtrado pelo idCustom para ser lançado na tabela\n"
                    + "onde será gerado o ReciboTFD e CapaTFD");
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;

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
