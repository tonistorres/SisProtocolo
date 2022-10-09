package br.com.subgerentepro.dao;

import br.com.subgerentepro.dto.PacienteDTO;
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

public class PacienteDAO implements GenericDAO<PacienteDTO> {
    
    @Override
    public void inserir(PacienteDTO pacienteDTO) throws PersistenciaException {
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
            String sql = "INSERT INTO tbtfd(nomepaciente,cpfpaciente,sexo,fk_estado,fk_cidade,fk_bairro,ruapaciente,numerocartaosus,vinculo,cadastro) VALUES(?,?,?,?,?,?,?,?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, pacienteDTO.getNomePacienteDto());
            statement.setString(2, pacienteDTO.getCpfPacienteDto());
            statement.setString(3, pacienteDTO.getSexoDto());
            statement.setInt(4, pacienteDTO.getFkEstadoDto());
            statement.setInt(5, pacienteDTO.getFkCidadeDto());
            statement.setInt(6, pacienteDTO.getFkBairroDto());
            statement.setString(7, pacienteDTO.getRuaPacienteDto());
            statement.setString(8, pacienteDTO.getNumeroCartaoSusDto());
            statement.setString(9, pacienteDTO.getVinculoDto());
            statement.setDate(10, null);
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();
            
        } catch (Exception ex) {
            //robo conectado servidor google 
            erroViaEmail(ex.getMessage(), "inserir(PacienteDTo pacienteDTO) - Camada DAO \n"
                    + "erro ao tentar inserir um paiciente");
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
        
    }
    
    @Override
    public void atualizar(PacienteDTO pacienteDTO) throws PersistenciaException {
        try {
            
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
             * Esse recurso das interrogações na estrutura do código SQL é utilizada para
             * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
             * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
             * SQL
             */
            //,,,,,,,
            String sql = "UPDATE tbtfd SET nomepaciente=?,cpfpaciente=?,sexo=?,fk_estado=?,fk_cidade=?,fk_bairro=?,ruapaciente=?,numerocartaosus=?,vinculo=? WHERE idpaciente=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, pacienteDTO.getNomePacienteDto());
            statement.setString(2, pacienteDTO.getCpfPacienteDto());
            statement.setString(3, pacienteDTO.getSexoDto());
            statement.setInt(4, pacienteDTO.getFkEstadoDto());
            statement.setInt(5, pacienteDTO.getFkCidadeDto());
            statement.setInt(6, pacienteDTO.getFkBairroDto());
            statement.setString(7, pacienteDTO.getRuaPacienteDto());
            statement.setString(8, pacienteDTO.getNumeroCartaoSusDto());
            statement.setString(9, pacienteDTO.getVinculoDto());
            
            statement.setInt(10, pacienteDTO.getIdPacienteDto());
            /**
             * executar o statement
             */
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();
            
        } catch (Exception ex) {
            //robo conectado servidor google 
            erroViaEmail(ex.getMessage(), "Camada  GUI - Método de atualização Erro ");
            
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public void deletar(PacienteDTO pacienteDTO) throws PersistenciaException {
        
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            
            String sql = "DELETE FROM tbtfd WHERE idpaciente=?";
            
            PreparedStatement statement;
            
            statement = connection.prepareStatement(sql);
            
            statement.setInt(1, pacienteDTO.getIdPacienteDto());
            /**
             * A estamos disparando por meio do método execute a minhha strig
             * sql devidamente setada
             */
            statement.execute();
            JOptionPane.showMessageDialog(null, "Dado Deletado com Sucesso!!");
            /**
             * Fecha Conexão
             */
            connection.close();
            
        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "deletar(PacienteDTO pacienteDTo)- CamadaDAO\n"
                    + "erro ao tentar deletar um paciente ");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro na Deleção do Dado\nErro:" + ex.getMessage());
            
        }
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
    public List<PacienteDTO> listarTodos() throws PersistenciaException {
        List<PacienteDTO> listaDePaciente = new ArrayList<>();
        
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            
            String sql = "SELECT *FROM tbtfd order by nomepaciente";
            
            PreparedStatement statement = connection.prepareStatement(sql);
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                
                PacienteDTO pacienteDTO = new PacienteDTO();
                
                pacienteDTO.setIdPacienteDto(resultSet.getInt("idpaciente"));
                pacienteDTO.setNomePacienteDto(resultSet.getString("nomepaciente"));
                pacienteDTO.setCpfPacienteDto(resultSet.getString("cpfpaciente"));
                pacienteDTO.setFkEstadoDto(resultSet.getInt("fk_estado"));
                pacienteDTO.setFkCidadeDto(resultSet.getInt("fk_cidade"));
                pacienteDTO.setFkBairroDto(resultSet.getInt("fk_bairro"));
                pacienteDTO.setRuaPacienteDto(resultSet.getString("ruapaciente"));
                pacienteDTO.setNumeroCartaoSusDto(resultSet.getString("numerocartaosus"));
                pacienteDTO.setCadastroTimeStampDto(resultSet.getDate("cadastro"));
                pacienteDTO.setSexoDto(resultSet.getString("sexo"));

                /**
                 * Adiciona na listaDePaciente todos os dados capturado pelo
                 * laço e adicionado no objeto pacienteDTO
                 */
                listaDePaciente.add(pacienteDTO);
                
            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "Camada DAO - listarTodos()\n"
                    + "PacienteDAO");
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return listaDePaciente;
    }
    
    public PacienteDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {
        
        PacienteDTO pacienteDTO = null;
        
        try {
            
            Connection connection = ConexaoUtil.getInstance().getConnection();
            
            String sql = "SELECT \n"
                    + "\n"
                    + "T.idpaciente,\n"
                    + "T.nomepaciente,\n"
                    + "T.cpfpaciente,\n"
                    + "T.sexo,\n"
                    + "T.fk_estado,\n"
                    + "T.fk_cidade,\n"
                    + "T.fk_bairro,\n"
                    + "T.ruapaciente,\n"
                    + "T.numerocartaosus,\n"
                    + "T.cadastro,\n"
                    + "E.nome_estado,\n"
                    + "C.nome_cidade,\n"
                    + "B.nome_bairro\n"
                    + "\n"
                    + "FROM\n"
                    + "tbtfd AS T\n"
                    + "\n"
                    + "INNER JOIN estados AS E \n"
                    + "ON T.fk_estado=E.id_estado\n"
                    + "\n"
                    + "INNER JOIN cidades AS C\n"
                    + "ON  T.fk_cidade=C.id_cidade\n"
                    + "\n"
                    + "INNER JOIN bairros AS B\n"
                    + "ON T.fk_bairro=B.id_bairro\n"
                    + "\n"
                    + "WHERE T.idpaciente=" + codigo;
            
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                
                pacienteDTO = new PacienteDTO();
                
                pacienteDTO.setIdPacienteDto(resultSet.getInt("idpaciente"));
                pacienteDTO.setNomePacienteDto(resultSet.getString("nomepaciente"));
                pacienteDTO.setCpfPacienteDto(resultSet.getString("cpfpaciente"));
                pacienteDTO.setFkEstadoDto(resultSet.getInt("fk_estado"));
                pacienteDTO.setFkCidadeDto(resultSet.getInt("fk_cidade"));
                pacienteDTO.setFkBairroDto(resultSet.getInt("fk_bairro"));
                pacienteDTO.setEstadoDto(resultSet.getString("nome_estado"));
                pacienteDTO.setCidadeDto(resultSet.getString("nome_cidade"));
                pacienteDTO.setBairroDto(resultSet.getString("nome_bairro"));
                pacienteDTO.setRuaPacienteDto(resultSet.getString("ruapaciente"));
                pacienteDTO.setNumeroCartaoSusDto(resultSet.getString("numerocartaosus"));
                pacienteDTO.setCadastroTimeStampDto(resultSet.getDate("cadastro"));
                pacienteDTO.setSexoDto(resultSet.getString("sexo"));
                return pacienteDTO;
            }
            
            connection.close();
            
        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "buscarPorIdTblConsultaList(int codigo)\n"
                    + "Camada DAO - setar valores em capos\n"
                    + " para alteração TelaTFD.java");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }
        
        return null;
        
    }
    
   @Override
    public PacienteDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public PacienteDTO buscarPor(PacienteDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public PacienteDTO buscarPeloCPF(PacienteDTO paciente) throws PersistenciaException {

        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        PacienteDTO pacienteDTO = null;
        
        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbtfd where cpfpaciente='" + paciente.getCpfPacienteDto() + "'";

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
                paciente = new PacienteDTO();
                
                paciente.setNomePacienteDto(resultSet.getString("nomepaciente"));
                paciente.setSexoDto(resultSet.getString("sexo"));
                paciente.setRuaPacienteDto(resultSet.getString("ruapaciente"));
                paciente.setNumeroCartaoSusDto(resultSet.getString("numerocartaosus"));
                paciente.setCpfPacienteDto(resultSet.getString("cpfpaciente"));
                
                return paciente;
            }
            
            connection.close();
            
        } catch (Exception ex) {
    
            erroViaEmail(ex.getMessage(), "buscarPeloCPF()-Camada DAO - Class PacienteDAO");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo buscarPeloCPF Camada DAO\n" + ex.getMessage());

        }
        
        return null;
        
    }
    
    @Override
    public boolean verificaDuplicidade(PacienteDTO pacienteDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna usuarioDuplicado=true(Verdadeiro). Caso contrário
         * usuarioDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */
        
        boolean pacienteDuplicado = false;
        
        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbtfd where cpfpaciente='" + pacienteDTO.getCpfPacienteDto() + "'";
            
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                
                pacienteDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return pacienteDuplicado;
            }
            
            connection.close();
            
        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "verificaDuplicidade()-Camada DAO - Class PacienteDAO");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }
        
        return pacienteDuplicado;
        
    }
    
   
    @Override
    public PacienteDTO filtrarAoClicar(PacienteDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        PacienteDTO pacienteDTO = null;
        
        try {
            
            Connection connection = ConexaoUtil.getInstance().getConnection();
            
            String sql = "SELECT *FROM tbtfd WHERE nomepaciente LIKE'%" + modelo.getNomePacienteDto() + "%'";

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
                pacienteDTO = new PacienteDTO();
                
                pacienteDTO.setIdPacienteDto(resultSet.getInt("idpaciente"));
                pacienteDTO.setNomePacienteDto(resultSet.getString("nomepaciente"));
                pacienteDTO.setCpfPacienteDto(resultSet.getString("cpfpaciente"));
                pacienteDTO.setFkEstadoDto(resultSet.getInt("fk_estado"));
                pacienteDTO.setFkCidadeDto(resultSet.getInt("fk_cidade"));
                pacienteDTO.setFkBairroDto(resultSet.getInt("fk_bairro "));
                pacienteDTO.setRuaPacienteDto(resultSet.getString("ruapaciente"));
                pacienteDTO.setCadastroTimeStampDto(resultSet.getDate("cadastro"));
                
                return pacienteDTO;
            }
            
            connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }
        
        return null;
        
    }
    
    public List<PacienteDTO> filtrarUsuarioPesqRapida(String pesquisarPaciente) throws PersistenciaException {
        
        List<PacienteDTO> listaPaciente = new ArrayList<PacienteDTO>();
        
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            
            String sql = "SELECT *FROM tbtfd WHERE nomepaciente LIKE '%" + pesquisarPaciente + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                
                PacienteDTO pacienteDTO = new PacienteDTO();
                pacienteDTO.setIdPacienteDto(resultSet.getInt("idpaciente"));
                pacienteDTO.setNomePacienteDto(resultSet.getString("nomepaciente"));
                pacienteDTO.setCpfPacienteDto(resultSet.getString("cpfpaciente"));
                pacienteDTO.setFkEstadoDto(resultSet.getInt("fk_estado"));
                pacienteDTO.setFkCidadeDto(resultSet.getInt("fk_cidade"));
                pacienteDTO.setFkBairroDto(resultSet.getInt("fk_bairro"));
                pacienteDTO.setRuaPacienteDto(resultSet.getString("ruapaciente"));
                pacienteDTO.setCadastroTimeStampDto(resultSet.getDate("cadastro"));
                pacienteDTO.setNumeroCartaoSusDto(resultSet.getString("numerocartaosus"));
                
                listaPaciente.add(pacienteDTO);
                
            }
            connection.close();
        } catch (Exception e) {
    
            erroViaEmail(e.getMessage(), "filtrarUsuarioPesqRapida(String pesquisarPaciente)\n"
                    + "Camada DAO- pesquisar por nome do paciente\n"
                    + "PacienteDAO.java");
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return listaPaciente;
        
    }
    
    public boolean verificaDuplicidadeCartaoSUS(PacienteDTO pacienteDTO) throws PersistenciaException {
        
        boolean pacienteDuplicado = false;
        
        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbtfd where numerocartaosus='" + pacienteDTO.getNumeroCartaoSusDto() + "'";
            
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                
                pacienteDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return pacienteDuplicado;
            }
            
            connection.close();
            
        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "Verificar Duplicidade Cartão SUS\n"
                    + "Camada DAO - PacienteDAO");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }
        
        return pacienteDuplicado;
        
    }

    @Override
    public boolean inserirControlEmail(PacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(PacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(PacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(PacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
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
