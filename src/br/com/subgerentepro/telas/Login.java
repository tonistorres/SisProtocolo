package br.com.subgerentepro.telas;

/**
 * trazer o módulo de conexão, que irá fazer a conexão entre o código java e o
 * Banco de Dados para Isso importar Aula:18
 */
import br.com.subgerentepro.bo.LoginBO;
import br.com.subgerentepro.bo.UsuarioBO;
import br.com.subgerentepro.dao.LoginDAO;
import br.com.subgerentepro.dao.UsuarioDAO;
import br.com.subgerentepro.dto.LoginDTO;
import br.com.subgerentepro.dto.ReconhecimentoDTO;
import br.com.subgerentepro.dto.UsuarioDTO;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import br.com.subgerentepro.metodosstatics.SerialUtils;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import static br.com.subgerentepro.telas.TelaPrincipal.barraProgresso;
import static br.com.subgerentepro.telas.TelaPrincipal.lblImagemUser;
import static br.com.subgerentepro.telas.TelaPrincipal.lblVerificacao;
import static br.com.subgerentepro.telas.TelaPrincipal.painelInferior;
import br.com.subgerentepro.thread.MinhaThredLoad;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import static java.lang.Thread.sleep;
/*Para evitar erros de conexão é sempre bom importar a Classe abaixo*/
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

//Esse método quando ele gera o formulário 
public class Login extends javax.swing.JFrame {

    //TRABALHANDO A THREAD QUE IRÁ DA O EFEITO 
    //DE PISCAR O CAMPO QUANDO RECEBER FOCO
    static int milissegundos = 0;
    static int segundos = 0;
    static int minutos = 0;
    static int horas = 0;
    //estado para controle de movimentos paineis txtLogin e txtSenha
    //bem essas flags já entram setada em true porque ja iniciam suas
    //tarefas no momento que o formulario é aberto 
    static boolean estado = true;

    /**
     * Dentro o public irei inicializar as chamadas
     */
    ConexaoUtil conecta = new ConexaoUtil();

    LoginBO loginBO = new LoginBO();
    LoginDTO loginDTO = new LoginDTO();
    LoginDAO loginDAO = new LoginDAO();

    UsuarioBO usuarioBO = new UsuarioBO();
    UsuarioDTO usuarioDTO = new UsuarioDTO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    br.com.subgerentepro.telas.TelaBancoTutor formBancoTutor;

    //----------------------------------------------------------------------------------------------------- //
    // 1º PASSO DENTRO DA CLASSE LOGIN CRIAR UM OBJETO DO TIPO SplashScreenPadrao                          //
    //--------------------------------------------------------------------------------------------------- //
    //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 04                        //
    // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555368?start=0 //
    //------------------------------------------------------------------------------------------------ //
    SplashScreenPadrao splash;

    //----------------------------------------------------------------------------------------------------- //
    // 2º PASSO CRIAR UM CONSTRUTOR PASSANDO COMO PARÂMETRO UM OBJETO SplashScreenPadrao spl               //
    //--------------------------------------------------------------------------------------------------- //
    //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 04                        //
    // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555368?start=0 //
    //------------------------------------------------------------------------------------------------ //
    Login(SplashScreenPadrao splash) throws ClassNotFoundException {

        /**
         * O nosso objeto splash recebe ele mesmo o this significa neste
         * formulário
         */
        this.splash = splash;

        /**
         * O código abaixo faz inicialização dos componentes do form
         */
        initComponents();
        aoCarregarForm();
        personalizacaoFrontEnd();
        sisSegHospeda();

        /**
         * Setando valores ao carregar o formulário
         */
        txtLogin.setEditable(true);
        txtLogin.requestFocus();
        ConexaoUtil conecta = new ConexaoUtil();
        try {
            conecta.getInstance().getConnection();
            /**
             * Site Baixar icone: Contribuição do
             * Site:https://www.iconfinder.com
             */
            if (conecta != null) {
                //  lblStatusDaConexao.setText("Conectado");
                lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/Banco_MySQL.png")));
                lblStatusEspecificacao.setText("Banco Dado Conectado");
                lblChaveEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/chaveok.png")));
                /**
                 * pedimos que o método setProgress faça verificações na minha
                 * barra de progresso
                 */
                setProgress(0, "Carregando Componentes do Sistema");//barra em 0% mostrar esse texto na label
                setProgress(20, "Verificando conexao com MySQL");//barra em 20% 
                setProgress(40, "Carregando os Módulos");//barra em 40%
                setProgress(60, "Carregando interfaces");//barra em 60%
                setProgress(100, "Caso não exista criando caminho para relatório Unidade C:");//barra em 100%

                //robo conecatado servidor google
                criandoCaminhoRelatoriosEmC();

            } else {
                lblStatusEspecificacao.setText("Banco Dado Desconectado");
                lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/BancoDesconectado.png")));
                lblChaveEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/chaveDefeito.png")));
                txtSenha.setEnabled(false);
                txtLogin.setEnabled(false);
                btnLogin.setEnabled(false);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tratamento Erro Camada GUI: " + ex.getMessage());
            System.exit(0);
        }

    }

    private void criandoCaminhoRelatoriosEmC() {
//Dica:https://respostas.guj.com.br/5728-criar-pasta-com-mkdir
        String nomePasta = "C:" + File.separator + "ireport";
        File diretorio = new File(nomePasta);

        if (diretorio.exists()) {
            System.out.println("diretorio ja existe:" + diretorio.getPath());
            // erroViaEmail("Diretório ireport Criado com sucesso em C:", "criandoCaminhoRelatorioEMC()");

        } else {
            System.out.println("diretorio inexistente criando pasta");
            diretorio.mkdir();
            System.out.println("Pasta Criada:" + diretorio.getPath());

            //linha robo aviso
            erroViaEmail("Criando uma Pasta na Unidade C:\n"
                    + " que conterá os relatórios a serem \n"
                    + "exportadose transformados em pdf", "criandoCaminhoRelatorioEMC()\n"
                    + "" + diretorio.getPath());

        }

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

    private void aoCarregarForm() {
        txtLogin.requestFocus();
        lblLogin.setVisible(false);
        progressBarraPesquisa.setIndeterminate(true);

    }

    //----------------------------------------------------------------------------------------------------- //
    // 3º PASSO CRIAR O MÉTODO setProgress QUE IRÁ REALIZARA A INICIALIZAÇÃO DA BARRA DE PROGRESSO         //
    //--------------------------------------------------------------------------------------------------- //
    //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 05                        //
    // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555372?start=0 //
    //------------------------------------------------------------------------------------------------ //
    void setProgress(int percent, String informacao
    ) {

        /**
         * Captura os textos do setProgress e seta no label
         * (lblInfoCarregamento) na tela de SplashScreenPadrao
         */
        splash.getJLabel().setText(informacao);

        /**
         * Captura o valor em porcentagem do setProgress e seta o mesmo a Barra
         * de Progresso contida na Tela SplashScreenPadrao
         */
        splash.getJProgressBar().setValue(percent);

        //----------------------------------------------------------------------------------------------------- //
        // 5º PASSO CRIAR PROGRAMAR O Thread.sleep()                                            //
        //--------------------------------------------------------------------------------------------------- //
        //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 06                        //
        // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555374?start=0 //
        //------------------------------------------------------------------------------------------------ //
        try {

            Thread.sleep(1000);//o tempo que o meu ProgressBar vai demorar para percorrer toda barra
        } catch (InterruptedException e) {
            //no catch em caso de erro avisa para o usuário a causa do erro 
            JOptionPane.showMessageDialog(this, "Não foi possível carregar a inicialização\n" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        painelInformativo = new javax.swing.JPanel();
        lblLogoJava = new javax.swing.JLabel();
        lblSQL = new javax.swing.JLabel();
        lblIReport = new javax.swing.JLabel();
        lblNetbeans = new javax.swing.JLabel();
        jPanelStatus = new javax.swing.JPanel();
        lblStatusDaConexao = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        painelEfeitoTxtLogin = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        lblStatusEspecificacao = new javax.swing.JLabel();
        lblChaveEntrada = new javax.swing.JLabel();
        lblInfoUsuario = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        painelEfeitoTxtSenha = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblSenha = new javax.swing.JLabel();
        painelNuvem = new javax.swing.JPanel();
        lblImgNuvemPos6 = new javax.swing.JLabel();
        lblImgNuvemPos1 = new javax.swing.JLabel();
        lblImgNuvemPos2 = new javax.swing.JLabel();
        lblImgNuvemPos3 = new javax.swing.JLabel();
        lblImgNuvemPos4 = new javax.swing.JLabel();
        lblImgNuvemPos5 = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        painelColetadoInformaceos = new javax.swing.JPanel();
        txtRecuperaData = new javax.swing.JTextField();
        txtHDSerial = new javax.swing.JTextField();
        lblSerialHD = new javax.swing.JLabel();
        txtSerialCPU = new javax.swing.JTextField();
        lblSerialCPU = new javax.swing.JLabel();
        lblPlacaMae = new javax.swing.JLabel();
        txtPlacaMae = new javax.swing.JTextField();
        lblDataHora = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblId = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAInformacoesLocais = new javax.swing.JTextArea();
        barraProgresso = new javax.swing.JProgressBar();
        lblVerificacao = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setBackground(java.awt.Color.white);
        setForeground(java.awt.Color.white);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelInformativo.setBackground(new java.awt.Color(255, 255, 255));

        lblIReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout painelInformativoLayout = new javax.swing.GroupLayout(painelInformativo);
        painelInformativo.setLayout(painelInformativoLayout);
        painelInformativoLayout.setHorizontalGroup(
            painelInformativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformativoLayout.createSequentialGroup()
                .addGroup(painelInformativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblIReport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSQL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogoJava, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 32, Short.MAX_VALUE))
            .addGroup(painelInformativoLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lblNetbeans, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelInformativoLayout.setVerticalGroup(
            painelInformativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformativoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLogoJava)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSQL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIReport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNetbeans)
                .addContainerGap(316, Short.MAX_VALUE))
        );

        getContentPane().add(painelInformativo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 160, 350));

        jPanelStatus.setBackground(java.awt.Color.white);
        jPanelStatus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblStatusDaConexao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelStatus.add(lblStatusDaConexao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 46, 260, -1));

        txtLogin.setEditable(false);
        txtLogin.setBackground(java.awt.Color.white);
        txtLogin.setName("txtLogin"); // NOI18N
        txtLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLoginFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLoginFocusLost(evt);
            }
        });
        txtLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoginActionPerformed(evt);
            }
        });
        txtLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLoginKeyPressed(evt);
            }
        });
        jPanelStatus.add(txtLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 120, 30));

        painelEfeitoTxtLogin.setBackground(java.awt.Color.orange);

        javax.swing.GroupLayout painelEfeitoTxtLoginLayout = new javax.swing.GroupLayout(painelEfeitoTxtLogin);
        painelEfeitoTxtLogin.setLayout(painelEfeitoTxtLoginLayout);
        painelEfeitoTxtLoginLayout.setHorizontalGroup(
            painelEfeitoTxtLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        painelEfeitoTxtLoginLayout.setVerticalGroup(
            painelEfeitoTxtLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanelStatus.add(painelEfeitoTxtLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 140, 50));

        btnLogin.setBackground(new java.awt.Color(9, 81, 107));
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnLogin.setForeground(java.awt.Color.white);
        btnLogin.setText("Entrar");
        btnLogin.setToolTipText("");
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnLoginStateChanged(evt);
            }
        });
        btnLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnLoginFocusGained(evt);
            }
        });
        btnLogin.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                btnLoginComponentHidden(evt);
            }
        });
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        btnLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLoginKeyPressed(evt);
            }
        });
        jPanelStatus.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 120, 40));

        lblStatusEspecificacao.setBackground(java.awt.Color.white);
        lblStatusEspecificacao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStatusEspecificacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusEspecificacao.setText("mensagemProgramada");
        jPanelStatus.add(lblStatusEspecificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 112, 230, 30));

        lblChaveEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/chaveDefeito.png"))); // NOI18N
        jPanelStatus.add(lblChaveEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 300, 39, 40));

        lblInfoUsuario.setBackground(new java.awt.Color(255, 204, 204));
        lblInfoUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelStatus.add(lblInfoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 40));

        txtSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSenhaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSenhaFocusLost(evt);
            }
        });
        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });
        jPanelStatus.add(txtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 120, 30));

        painelEfeitoTxtSenha.setBackground(java.awt.Color.cyan);
        painelEfeitoTxtSenha.setPreferredSize(new java.awt.Dimension(210, 70));

        javax.swing.GroupLayout painelEfeitoTxtSenhaLayout = new javax.swing.GroupLayout(painelEfeitoTxtSenha);
        painelEfeitoTxtSenha.setLayout(painelEfeitoTxtSenhaLayout);
        painelEfeitoTxtSenhaLayout.setHorizontalGroup(
            painelEfeitoTxtSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        painelEfeitoTxtSenhaLayout.setVerticalGroup(
            painelEfeitoTxtSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanelStatus.add(painelEfeitoTxtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 140, 50));

        lblUsuario.setBackground(new java.awt.Color(51, 255, 0));
        lblUsuario.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanelStatus.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 50, 50));

        lblSenha.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanelStatus.add(lblSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 50, 50));

        painelNuvem.setBackground(java.awt.Color.white);
        painelNuvem.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuvem:"));
        painelNuvem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblImgNuvemPos6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 70, 50));

        lblImgNuvemPos1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 70, 50));

        lblImgNuvemPos2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 70, 50));

        lblImgNuvemPos3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 70, 50));

        lblImgNuvemPos4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 70, 50));

        lblImgNuvemPos5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 70, 50));

        jPanelStatus.add(painelNuvem, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 90, 350));

        lblLogin.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lblLogin.setForeground(java.awt.Color.red);
        lblLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogin.setText("0%");
        jPanelStatus.add(lblLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 180, 30));
        jPanelStatus.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 140, -1));

        getContentPane().add(jPanelStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 350));

        painelColetadoInformaceos.setBackground(java.awt.Color.white);
        painelColetadoInformaceos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Coletando Informações:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), java.awt.Color.white)); // NOI18N
        painelColetadoInformaceos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtRecuperaData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRecuperaData.setOpaque(false);
        txtRecuperaData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRecuperaDataActionPerformed(evt);
            }
        });
        painelColetadoInformaceos.add(txtRecuperaData, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 40, 120, 22));

        txtHDSerial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtHDSerial.setOpaque(false);
        painelColetadoInformaceos.add(txtHDSerial, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 120, 22));

        lblSerialHD.setText("Serial HD:");
        painelColetadoInformaceos.add(lblSerialHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        txtSerialCPU.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSerialCPU.setOpaque(false);
        painelColetadoInformaceos.add(txtSerialCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 120, 22));

        lblSerialCPU.setText("Serial CPU:");
        painelColetadoInformaceos.add(lblSerialCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        lblPlacaMae.setText("Placa Mãe:");
        painelColetadoInformaceos.add(lblPlacaMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        txtPlacaMae.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPlacaMae.setOpaque(false);
        painelColetadoInformaceos.add(txtPlacaMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 120, 22));

        lblDataHora.setText("DtRegistro:");
        painelColetadoInformaceos.add(lblDataHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 79, -1));
        painelColetadoInformaceos.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 30, 22));

        lblId.setText("ID:");
        painelColetadoInformaceos.add(lblId, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 18, -1, -1));

        getContentPane().add(painelColetadoInformaceos, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 350, 210, 160));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações Diversas:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        txtAInformacoesLocais.setColumns(20);
        txtAInformacoesLocais.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtAInformacoesLocais.setRows(5);
        txtAInformacoesLocais.setOpaque(false);
        jScrollPane1.setViewportView(txtAInformacoesLocais);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 359, 270, 140));

        barraProgresso.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(barraProgresso, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 480, 20));
        getContentPane().add(lblVerificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 270, 20));

        setSize(new java.awt.Dimension(496, 580));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void desabilitarCampos() {
        txtLogin.setEnabled(false);
        txtSenha.setEnabled(false);
    }

    private void desabilitarTodosBotoes() {
        btnLogin.setEnabled(false);
    }

    private void personalizacaoFrontEnd() {

        jPanelStatus.setBackground(Color.WHITE);

        painelColetadoInformaceos.setBackground(new Color(9, 81, 107));
        lblId.setForeground(Color.WHITE);
        lblDataHora.setForeground(Color.WHITE);
        lblSerialCPU.setForeground(Color.WHITE);
        lblSerialHD.setForeground(Color.WHITE);
        lblPlacaMae.setForeground(Color.WHITE);
        //botão entrar 
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/login.png")));
        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/userLogin.png")));
        lblSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/senhaLogin.png")));
        lblLogoJava.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/ireport/imagens/javalOGIN.png")));
        lblSQL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/ireport/imagens/Banco_MySQL.png")));
        lblIReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/ireport/imagens/IReportLogin.jpg")));
        lblNetbeans.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/ireport/imagens/NetBeans.png")));
        personalizarTxtCapturaInfoMaquina();
    }

    private void personalizarTxtCapturaInfoMaquina() {
        Font fonteTxtInformacaoMaquina = new Font("Tahoma", Font.ITALIC, 10);
        txtId.setFont(fonteTxtInformacaoMaquina);
        txtRecuperaData.setFont(fonteTxtInformacaoMaquina);
        txtHDSerial.setFont(fonteTxtInformacaoMaquina);
        txtSerialCPU.setFont(fonteTxtInformacaoMaquina);
        txtPlacaMae.setFont(fonteTxtInformacaoMaquina);
        Font fonteTxtInformacaoMaquinaTexArea = new Font("Tahoma", Font.ITALIC, 9);
        txtAInformacoesLocais.setFont(fonteTxtInformacaoMaquinaTexArea);

    }

    private void personalizarMaquinaReconhecida() {

        Font f = new Font("Tahoma", Font.BOLD, 15);
        lblInfoUsuario.setText("Maquina RECONHECIDA");
        //  lblInfoUsuario.setForeground(Color.WHITE);
        lblInfoUsuario.setForeground(new Color(9, 81, 107));
        lblInfoUsuario.setFont(f);
        txtLogin.setEnabled(true);
        txtSenha.setEnabled(true);

    }

    private void personalizarMaquinaBloqueada() {
        Font f = new Font("Tahoma", Font.BOLD, 15);
        lblInfoUsuario.setText("Maquina BLOQUEADA");
        //lblInfoUsuario.setForeground(Color.WHITE);
        lblInfoUsuario.setForeground(new Color(9, 81, 107));
        lblInfoUsuario.setFont(f);
        txtLogin.setEnabled(false);
        txtSenha.setEnabled(false);

    }


    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
//         
    }

    // Esse método quando inicializa o formulário     
    /**
     * Classe de inicialização Formulario Login Criado nos Estudos de Hugo Hugo
     * Vasconcelos
     * Contribuição:https://www.dropbox.com/sh/gbp9emvqjb4q88n/AADofh1m8UgXA9RWufgRgMwta?dl=0
     */
    public Login() throws ClassNotFoundException {

        initComponents();// inicialização dos componentes 

        ConexaoUtil conecta = new ConexaoUtil();

        try {
            conecta.getInstance().getConnection();
            /**
             * Site Baixar icone: Contribuição do
             * Site:https://www.iconfinder.com
             */
            if (conecta != null) {
                // lblStatusDaConexao.setText("Conectado");
                lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/Banco_MySQL.png")));
                lblStatusEspecificacao.setText("Banco Dado Conectado");
                lblChaveEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/chaveok.png")));
                /**
                 * pedimos que o método setProgress faça verificações na minha
                 * barra de progresso
                 */
                setProgress(0, "Carregando Componentes do Sistema");//barra em 0% mostrar esse texto na label
                setProgress(20, "Verificando conexao com MySQL");//barra em 20% 
                setProgress(40, "Carregando os Módulos");//barra em 40%
                setProgress(60, "Carregando interfaces");//barra em 60%
                setProgress(100, "Bem vindo ao sistema");//barra em 100%
            } else {
                lblStatusEspecificacao.setText("Banco Dado Desconectado");
                lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/BancoDesconectado.png")));
                lblChaveEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/chaveDefeito.png")));
                txtSenha.setEnabled(false);
                txtLogin.setEnabled(false);
                btnLogin.setEnabled(false);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tratamento Erro Camada GUI: " + ex.getMessage());
        }

    }//GEN-LAST:event_btnLoginActionPerformed

//METODO PARA LOGAR 
    public void login() {

        /**
         * Setando os valores digitados pelo usuario
         */
        //
        usuarioDTO.setLoginDto(txtLogin.getText());
        usuarioDTO.setSenhaDto(new String(txtSenha.getPassword()));

        try {

            UsuarioDTO resultado = usuarioBO.logarBO(usuarioDTO);

            String resultadoPerfil = resultado.getPerfilDto();

            String resultadoPolido = MetodoStaticosUtil.removerAcentosCaixAlta(resultadoPerfil);

            // PESQUISA TECNOLOGICA DA INFORMACAO
            if (resultadoPerfil.equalsIgnoreCase("PESQUISA TECNOLOGICA DA INFORMACAO")) {

                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

                this.dispose();

                TelaPrincipal.itmnUsuarios.setEnabled(true);
                TelaPrincipal.itmnEstados.setEnabled(true);
                TelaPrincipal.itmnCidades.setEnabled(true);
                TelaPrincipal.itmnBairros.setEnabled(true);

                TelaPrincipal.menuTFD.setEnabled(true);
                TelaPrincipal.itmnPessoasTFD.setEnabled(true);
                TelaPrincipal.itmnTutorTFD.setEnabled(true);
                TelaPrincipal.menuBancoTFD.setEnabled(true);
                TelaPrincipal.itmnProtocTFD.setEnabled(true);

                TelaPrincipal.menuEmpresas.setEnabled(true);
                TelaPrincipal.itmnCadastrarEmpresa.setEnabled(true);
                TelaPrincipal.itemBancoEmpresa.setEnabled(true);

                //MENU TESOURARIA 
                TelaPrincipal.mnTesouraria.setVisible(true);

                TelaPrincipal.itmnFuncionarios.setEnabled(true);
                TelaPrincipal.itmnOutros.setEnabled(true);
                TelaPrincipal.itmnDepartamentos.setEnabled(true);
                TelaPrincipal.itmnItensDoProtocolo.setEnabled(true);

                TelaPrincipal.itmnProtocEmpresas.setEnabled(true);

                TelaPrincipal.itmnProtocDepartamentos.setEnabled(true);
                TelaPrincipal.itmnProtocOutros.setEnabled(true);

                TelaPrincipal.itmnSetorTramiteInterno.setVisible(true);
                TelaPrincipal.itmnSetorTramiteInterno.setEnabled(true);

                //perfil
                Font f = new Font("Tahoma", Font.BOLD, 5);//label informativo 
                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());

                TelaPrincipal.lblPerfil.setFont(f);
                TelaPrincipal.lblUsuarioLogado.setText(resultado.getLoginDto().toUpperCase());
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(Color.PINK);

                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE MAQUINA 
                TelaPrincipal.lblRepositorioHD.setText(txtHDSerial.getText());
                TelaPrincipal.lblRepositorioCPU.setText(txtSerialCPU.getText());

                //SETAR IMAGEM DE USUARIO SE FEMININO OU MASCULINO 
                if (resultado.getSexoDto().equalsIgnoreCase("FEMININO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_2_1.png")));

                }

                if (resultado.getSexoDto().equalsIgnoreCase("MASCULINO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_1.png")));
                }

                this.dispose();

            }

            //adiministrador
            if (resultadoPolido.equalsIgnoreCase("admin")) {

                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

                this.dispose();

                TelaPrincipal.itmnUsuarios.setEnabled(true);
                TelaPrincipal.itmnEstados.setEnabled(true);
                TelaPrincipal.itmnCidades.setEnabled(true);
                TelaPrincipal.itmnBairros.setEnabled(true);

                TelaPrincipal.menuTFD.setEnabled(true);
                TelaPrincipal.itmnPessoasTFD.setEnabled(true);
                TelaPrincipal.itmnTutorTFD.setEnabled(true);
                TelaPrincipal.menuBancoTFD.setEnabled(true);
                TelaPrincipal.itmnProtocTFD.setEnabled(true);

                TelaPrincipal.menuEmpresas.setEnabled(true);
                TelaPrincipal.itmnCadastrarEmpresa.setEnabled(true);
                TelaPrincipal.itemBancoEmpresa.setEnabled(true);

                TelaPrincipal.itmnFuncionarios.setEnabled(true);
                TelaPrincipal.itmnOutros.setEnabled(true);
                TelaPrincipal.itmnDepartamentos.setEnabled(true);
                TelaPrincipal.itmnItensDoProtocolo.setEnabled(true);

                TelaPrincipal.itmnProtocEmpresas.setEnabled(true);

                TelaPrincipal.itmnProtocDepartamentos.setEnabled(true);
                TelaPrincipal.itmnProtocOutros.setEnabled(true);

                TelaPrincipal.itmnSetorTramiteInterno.setVisible(true);
                TelaPrincipal.itmnSetorTramiteInterno.setEnabled(true);

                //MENU TESOURARIA 
                TelaPrincipal.mnTesouraria.setVisible(true);

                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());
                TelaPrincipal.lblUsuarioLogado.setText(resultado.getLoginDto().toUpperCase());
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(Color.PINK);

                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE MAQUINA 
                TelaPrincipal.lblRepositorioHD.setText(txtHDSerial.getText());
                TelaPrincipal.lblRepositorioCPU.setText(txtSerialCPU.getText());

                //SETAR IMAGEM DE USUARIO SE FEMININO OU MASCULINO 
                if (resultado.getSexoDto().equalsIgnoreCase("FEMININO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_2_1.png")));

                }

                if (resultado.getSexoDto().equalsIgnoreCase("MASCULINO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_1.png")));
                }

                this.dispose();

            }

            //TESOURARIA 
            if (resultadoPolido.equalsIgnoreCase("TESOURARIA")) {

                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

                this.dispose();

                //Menu Cadastro itmnFuncionarios
                TelaPrincipal.mnCadastros.setVisible(false);
                TelaPrincipal.itmnUsuarios.setVisible(false);//cadastro de usuario
                TelaPrincipal.itmnEstados.setVisible(false);//cadastro de estados
                TelaPrincipal.itmnCidades.setVisible(false);//cadastro de cidades 
                TelaPrincipal.itmnBairros.setVisible(false);//cadastro de bairros

                //Menu Consulta 
                TelaPrincipal.mnConsultas.setVisible(false);
                TelaPrincipal.itmnFluxoProcessoGeneric.setVisible(false);
                TelaPrincipal.itmnFluxoProcessoGeneric.setEnabled(false);
                TelaPrincipal.itmRegistroProtocolados.setVisible(false);

                //CADASTRO -->>SUB MENU TFD
                TelaPrincipal.menuTFD.setVisible(false);
                TelaPrincipal.itmnPessoasTFD.setVisible(false);//paciente tfd
                TelaPrincipal.itmnTutorTFD.setVisible(false);//tutor paciente tfd
                TelaPrincipal.menuBancoTFD.setVisible(false);//banco do tutor

                //CADASTRO-->>SUB MENU EMPRESAS
                TelaPrincipal.menuEmpresas.setVisible(false);
                TelaPrincipal.itmnCadastrarEmpresa.setVisible(false);
                TelaPrincipal.itemBancoEmpresa.setVisible(false);

                //CADASTRO--->>ITENS DE MENU
                TelaPrincipal.itmnFuncionarios.setVisible(false);//cadastro de funcionarios
                TelaPrincipal.itmnFuncionarios.setEnabled(false);
                TelaPrincipal.itmnOutros.setVisible(false);//cadastro de pessoas externas a administração
                TelaPrincipal.itmnDepartamentos.setEnabled(false);//cadastro departamentos 
                TelaPrincipal.itmnDepartamentos.setVisible(false);//cadastro departamentos 
                TelaPrincipal.itmnItensDoProtocolo.setEnabled(false);//cadastro itens do protocolo 

                TelaPrincipal.itmnProtocEmpresas.setVisible(false);
                TelaPrincipal.itmnProtocDepartamentos.setEnabled(true);
                TelaPrincipal.itmnProtocOutros.setVisible(false);

                TelaPrincipal.itmnSetorTramiteInterno.setVisible(false);
                TelaPrincipal.itmnSetorTramiteInterno.setVisible(false);

                //MENU TESOURARIA 
                TelaPrincipal.mnTesouraria.setVisible(true);

                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());
                TelaPrincipal.lblUsuarioLogado.setText(resultado.getLoginDto().toUpperCase());
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(Color.PINK);

                //MENU PROTOCOLAR 
                TelaPrincipal.mnProtocolar.setVisible(false);

                //MENU RELATORIO
                TelaPrincipal.mnRelarorios.setVisible(false);

                //MENU FERRAMENTAS
                TelaPrincipal.mnFerramentas.setVisible(false);

                //***********************************************
                //FERRAMENTAS PARA TESOURARIA INVISIVEL TOTAL //
                //********************************************** 
                TelaPrincipal.BarraFerramentas.setVisible(true);
                TelaPrincipal.btnCadEmpresa.setEnabled(false);
                TelaPrincipal.btnCadTFD.setEnabled(false);
                TelaPrincipal.btnPessoasOutros.setEnabled(false);
                TelaPrincipal.btnProtocolarEmpresas.setEnabled(false);
                TelaPrincipal.btnProtocolarTFD.setEnabled(false);
                TelaPrincipal.btnProtocolarFuncionarios.setEnabled(false);
                TelaPrincipal.btnProtocolarPessoas.setEnabled(false);
                TelaPrincipal.btnCadFuncionario.setEnabled(false);

                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE MAQUINA 
                TelaPrincipal.lblRepositorioHD.setText(txtHDSerial.getText());
                TelaPrincipal.lblRepositorioCPU.setText(txtSerialCPU.getText());

                //SETAR IMAGEM DE USUARIO SE FEMININO OU MASCULINO 
                if (resultado.getSexoDto().equalsIgnoreCase("FEMININO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_2_1.png")));

                }

                if (resultado.getSexoDto().equalsIgnoreCase("MASCULINO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_1.png")));
                }
                this.dispose();

            }

            //CONTABILIDADE
            if (resultadoPolido.equalsIgnoreCase("contabilidade")) {

                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

                this.dispose();

                //Menu Cadastro itmnFuncionarios
                TelaPrincipal.mnCadastros.setVisible(true);
                TelaPrincipal.itmnUsuarios.setVisible(false);//cadastro de usuario
                TelaPrincipal.itmnEstados.setVisible(false);//cadastro de estados
                TelaPrincipal.itmnCidades.setVisible(false);//cadastro de cidades 
                TelaPrincipal.itmnBairros.setVisible(false);//cadastro de bairros

                //Menu Consulta
                TelaPrincipal.mnConsultas.setVisible(true);
                TelaPrincipal.itmnFluxoProcessoGeneric.setVisible(true);
                TelaPrincipal.itmnFluxoProcessoGeneric.setEnabled(true);
                TelaPrincipal.itmRegistroProtocolados.setVisible(false);

                //CADASTRO -->>SUB MENU TFD
                TelaPrincipal.menuTFD.setVisible(false);
                TelaPrincipal.itmnPessoasTFD.setVisible(false);//paciente tfd
                TelaPrincipal.itmnTutorTFD.setVisible(false);//tutor paciente tfd
                TelaPrincipal.menuBancoTFD.setVisible(false);//banco do tutor

                //CADASTRO-->>SUB MENU EMPRESAS
                TelaPrincipal.menuEmpresas.setVisible(false);
                TelaPrincipal.itmnCadastrarEmpresa.setVisible(false);
                TelaPrincipal.itemBancoEmpresa.setVisible(false);

                //CADASTRO--->>ITENS DE MENU
                TelaPrincipal.itmnFuncionarios.setVisible(false);//cadastro de funcionarios
                TelaPrincipal.itmnFuncionarios.setEnabled(false);
                TelaPrincipal.itmnOutros.setVisible(false);//cadastro de pessoas externas a administração
                TelaPrincipal.itmnDepartamentos.setEnabled(false);//cadastro departamentos 
                TelaPrincipal.itmnDepartamentos.setVisible(false);//cadastro departamentos 
                TelaPrincipal.itmnItensDoProtocolo.setEnabled(true);//cadastro itens do protocolo 

                TelaPrincipal.itmnProtocEmpresas.setVisible(false);
                TelaPrincipal.itmnProtocDepartamentos.setEnabled(true);
                TelaPrincipal.itmnProtocOutros.setVisible(false);

                TelaPrincipal.itmnSetorTramiteInterno.setVisible(false);
                TelaPrincipal.itmnSetorTramiteInterno.setVisible(false);

                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());
                TelaPrincipal.lblUsuarioLogado.setText(resultado.getLoginDto().toUpperCase());
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(Color.PINK);

                //MENU PROTOCOLAR 
                TelaPrincipal.mnProtocolar.setVisible(false);

                //MENU RELATORIO
                TelaPrincipal.mnRelarorios.setVisible(false);

                //MENU TESOURARIA 
                TelaPrincipal.mnTesouraria.setVisible(false);

                //FERRAMENTAS
                TelaPrincipal.mnFerramentas.setVisible(false);

                //barra de ferramentas
                TelaPrincipal.btnCadEmpresa.setEnabled(false);
                TelaPrincipal.btnCadTFD.setEnabled(false);
                TelaPrincipal.btnPessoasOutros.setEnabled(false);
                TelaPrincipal.btnProtocolarEmpresas.setEnabled(false);
                TelaPrincipal.btnProtocolarTFD.setEnabled(false);
                TelaPrincipal.btnProtocolarFuncionarios.setEnabled(false);
                TelaPrincipal.btnProtocolarPessoas.setEnabled(false);

                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE MAQUINA 
                TelaPrincipal.lblRepositorioHD.setText(txtHDSerial.getText());
                TelaPrincipal.lblRepositorioCPU.setText(txtSerialCPU.getText());

                //SETAR IMAGEM DE USUARIO SE FEMININO OU MASCULINO 
                if (resultado.getSexoDto().equalsIgnoreCase("FEMININO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_2_1.png")));

                }

                if (resultado.getSexoDto().equalsIgnoreCase("MASCULINO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_1.png")));
                }
                this.dispose();

            }

            //PROTOCOLO - SERÁ A PRIMEIRA ÁREA LIBERA A PARTE DE TFD PERSONALIZANDO ÁREA TFD
            if (resultadoPolido.equalsIgnoreCase("protocolo")) {

                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

                this.dispose();

                TelaPrincipal.itmnUsuarios.setVisible(false);
                TelaPrincipal.itmnEstados.setVisible(false);
                TelaPrincipal.itmnCidades.setVisible(false);
                TelaPrincipal.itmnBairros.setEnabled(true);

                //MENU TFD 
                TelaPrincipal.menuTFD.setVisible(true);
                TelaPrincipal.menuTFD.setEnabled(true);

                //MENU CONSULTAS
                TelaPrincipal.mnConsultas.setVisible(true);
                TelaPrincipal.itmRegistroProtocolados.setVisible(true);
                TelaPrincipal.itmRegistroProtocolados.setEnabled(true);
                TelaPrincipal.itmnFluxoProcessoGeneric.setEnabled(true);
                TelaPrincipal.itmnFluxoProcessoGeneric.setVisible(false);

                TelaPrincipal.itmnPessoasTFD.setVisible(true);
                TelaPrincipal.itmnPessoasTFD.setEnabled(true);

                TelaPrincipal.itmnTutorTFD.setVisible(false);
                TelaPrincipal.itmnTutorTFD.setEnabled(false);

                TelaPrincipal.menuBancoTFD.setVisible(false);
                TelaPrincipal.menuBancoTFD.setEnabled(false);

                TelaPrincipal.itmnProtocTFD.setVisible(true);
                TelaPrincipal.itmnProtocTFD.setEnabled(true);

                TelaPrincipal.menuEmpresas.setEnabled(false);
                TelaPrincipal.menuEmpresas.setVisible(false);
                TelaPrincipal.itmnCadastrarEmpresa.setEnabled(true);

                TelaPrincipal.itemBancoEmpresa.setVisible(false);
                TelaPrincipal.itemBancoEmpresa.setEnabled(false);

                TelaPrincipal.itmnFuncionarios.setVisible(false);
                TelaPrincipal.itmnOutros.setVisible(false);

                TelaPrincipal.itmnDepartamentos.setVisible(false);
                TelaPrincipal.itmnDepartamentos.setEnabled(false);

                TelaPrincipal.itmnItensDoProtocolo.setVisible(false);
                TelaPrincipal.itmnItensDoProtocolo.setEnabled(false);

                TelaPrincipal.itmnProtocEmpresas.setVisible(false);

                TelaPrincipal.itmnProtocDepartamentos.setVisible(false);
                TelaPrincipal.itmnProtocOutros.setVisible(false);

                TelaPrincipal.mnRelarorios.setVisible(false);
                TelaPrincipal.mnFerramentas.setVisible(false);
                TelaPrincipal.mnTesouraria.setVisible(false);

                TelaPrincipal.itmnSetorTramiteInterno.setVisible(false);

                //HABILITANDO E DESABILITANDO BOTOES BARRA DE FERRAMENTAS 
                TelaPrincipal.btnCadEmpresa.setEnabled(false);
                TelaPrincipal.btnCadFuncionario.setEnabled(false);
                TelaPrincipal.btnPessoasOutros.setEnabled(false);
                TelaPrincipal.btnProtocolarEmpresas.setEnabled(false);
                TelaPrincipal.btnProtocolarFuncionarios.setEnabled(false);
                TelaPrincipal.btnProtocolarPessoas.setEnabled(false);

                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE USUÁRIO 
                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());
                TelaPrincipal.lblUsuarioLogado.setText(resultado.getLoginDto().toUpperCase());
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(Color.PINK);

                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE MAQUINA 
                TelaPrincipal.lblRepositorioHD.setText(txtHDSerial.getText());
                TelaPrincipal.lblRepositorioCPU.setText(txtSerialCPU.getText());

                //SETAR IMAGEM DE USUARIO SE FEMININO OU MASCULINO 
                if (resultado.getSexoDto().equalsIgnoreCase("FEMININO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_2_1.png")));

                }

                if (resultado.getSexoDto().equalsIgnoreCase("MASCULINO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_1.png")));
                }
                this.dispose();

            }

            //SECRETARIA DE SAUDE 
            br.com.subgerentepro.telas.TelaTFD formTFD;
            if (resultadoPolido.equalsIgnoreCase("SECRETARIA DE SAUDE")) {

                /**
                 * 1-Primeiro irá abria a tela Principal do Sistema em seguida
                 * 2-Irá deixá-la visivel para usuário 3- Irá instanciar a tela
                 * de Paciente TFD e posicionar ela dentro do InternaFrame
                 * 4-Adicionar um título essa janela ou form aberto 5- e
                 * executar a função show deixando visivel para usuário 6- em
                 * seguida distroi a tela de Login e segue com as confi gurações
                 * de ambiente do sistema
                 */
                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

                formBancoTutor = new TelaBancoTutor();
                DeskTop.add(formBancoTutor).setLocation(1, 5);
                formBancoTutor.setTitle("Banco/Tutor");
                formBancoTutor.setVisible(true);

                this.dispose();

                //---------------
                //MENU CADASTRO//
                //---------------
                TelaPrincipal.mnCadastros.setVisible(true);
                TelaPrincipal.mnCadastros.setEnabled(true);

                TelaPrincipal.menuTFD.setVisible(true);
                TelaPrincipal.menuTFD.setEnabled(true);

                TelaPrincipal.itmnPessoasTFD.setVisible(false);
                TelaPrincipal.itmnPessoasTFD.setEnabled(false);

                TelaPrincipal.itmnTutorTFD.setVisible(false);
                TelaPrincipal.itmnTutorTFD.setEnabled(false);

                TelaPrincipal.menuBancoTFD.setVisible(true);
                TelaPrincipal.menuBancoTFD.setEnabled(true);
                //---------------------------------------------------

                TelaPrincipal.itmnUsuarios.setVisible(false);
                TelaPrincipal.itmnEstados.setVisible(false);
                TelaPrincipal.itmnCidades.setVisible(false);
                TelaPrincipal.itmnBairros.setVisible(false);
                TelaPrincipal.menuEmpresas.setVisible(false);
                TelaPrincipal.itmnFuncionarios.setVisible(false);
                TelaPrincipal.itmnOutros.setVisible(false);
                TelaPrincipal.itmnDepartamentos.setVisible(false);
                TelaPrincipal.itmnItensDoProtocolo.setVisible(false);
                TelaPrincipal.itmnSetorTramiteInterno.setVisible(false);

                //-----------
                //TESOURARIA//
                //------------
                TelaPrincipal.mnTesouraria.setVisible(false);

                //-----------
                //PROTOCOLAR//
                //------------
                TelaPrincipal.mnProtocolar.setVisible(false);

                //-----------
                //CONSULTAS //
                //------------
                TelaPrincipal.mnConsultas.setVisible(false);

                //-----------
                //RELATORIOS//
                //------------
                TelaPrincipal.mnRelarorios.setVisible(false);

                //-----------
                //FERRAMENTAS//
                //------------
                TelaPrincipal.mnFerramentas.setVisible(false);

                //---------------------------------
                //BOTOES DA BARRA DE FERRAMENTAS //
                //---------------------------------
                TelaPrincipal.BarraFerramentas.setVisible(false);
                // TelaPrincipal.btnCadEmpresa.setVisible(false);
                // TelaPrincipal.btnCadTFD.setVisible(false);
                // TelaPrincipal.btnCadFuncionario.setVisible(false);
                // TelaPrincipal.btnPessoasOutros.setVisible(false);
                // TelaPrincipal.btnProtocolarEmpresas.setVisible(false);
                // TelaPrincipal.btnProtocolarTFD.setVisible(false);
                // TelaPrincipal.btnProtocolarFuncionarios.setVisible(false);
                // TelaPrincipal.btnProtocolarPessoas.setVisible(false);

                Font f = new Font("Tahoma", Font.BOLD, 8);//label informativo     
                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());
                TelaPrincipal.lblPerfil.setFont(f);
                TelaPrincipal.lblUsuarioLogado.setText(resultado.getLoginDto().toUpperCase());
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(Color.PINK);

                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE MAQUINA 
                TelaPrincipal.lblRepositorioHD.setText(txtHDSerial.getText());
                TelaPrincipal.lblRepositorioCPU.setText(txtSerialCPU.getText());

                //SETAR IMAGEM DE USUARIO SE FEMININO OU MASCULINO 
                if (resultado.getSexoDto().equalsIgnoreCase("FEMININO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_2_1.png")));

                }

                if (resultado.getSexoDto().equalsIgnoreCase("MASCULINO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_1.png")));
                }
                this.dispose();

            }

            //USER 
            if (resultadoPolido.equals("user")) {

                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

                /**
                 * Distrói o form frmLogin| "Este Destrói"
                 */
                this.dispose();

                /**
                 * Áreas do Sistema Liberadas para administradores do sistema
                 */
                TelaPrincipal.itmnUsuarios.setEnabled(false);

                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());
                TelaPrincipal.lblUsuarioLogado.setText(resultado.getLoginDto().toUpperCase());
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(Color.PINK);
                TelaPrincipal.itmnSetorTramiteInterno.setVisible(false);
                TelaPrincipal.mnTesouraria.setVisible(false);
                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE MAQUINA 
                TelaPrincipal.lblRepositorioHD.setText(txtHDSerial.getText());
                TelaPrincipal.lblRepositorioCPU.setText(txtSerialCPU.getText());

                //SETAR IMAGEM DE USUARIO SE FEMININO OU MASCULINO 
                if (resultado.getSexoDto().equalsIgnoreCase("FEMININO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_2_1.png")));

                }

                if (resultado.getSexoDto().equalsIgnoreCase("MASCULINO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/user_1.png")));
                }
                this.dispose();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());

            if (ex.getMessage().equals("Login Obrigatorio!")) {

                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);
                txtLogin.requestFocus();
                txtLogin.setBackground(Color.RED);

            }

            if (ex.getMessage().equals("Senha Obrigatorio!")) {

                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);

                txtSenha.requestFocus();
                txtSenha.setBackground(Color.RED);
            }

            if (ex.getMessage().equals("Usuario ou Senha Incorretos")) {
                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);

                txtLogin.requestFocus();
                txtLogin.setText("");
                txtSenha.setText("");
                txtLogin.setBackground(Color.red);
                txtSenha.setBackground(Color.red);
            }
        }

    }

    private void desabilitarCamposInfo() {
        txtId.setEnabled(false);
        txtPlacaMae.setEnabled(false);
        txtRecuperaData.setEnabled(false);
        txtHDSerial.setEnabled(false);
        txtSerialCPU.setEnabled(false);
        txtAInformacoesLocais.setEnabled(false);
    }

    private void sisSegHospeda() {

        String recuperaPlaMae = SerialUtils.getMotherboardSerialWindows();

        try {

            ReconhecimentoDTO recebeComparacaoDto = loginDAO.comparaSereiMotherboard(recuperaPlaMae);

            if (recebeComparacaoDto.getSerial_placa_maeDto().trim().equals(recuperaPlaMae.trim()) && recebeComparacaoDto.getLiberado_bloqueadoDto().trim().equalsIgnoreCase("LIBERADO")) {

                txtId.setText(String.valueOf(recebeComparacaoDto.getId_reconhecimentoDto()));
                txtPlacaMae.setText(recebeComparacaoDto.getSerial_placa_maeDto());
                txtRecuperaData.setText(recebeComparacaoDto.getDt_hora_conectouDto());
                txtHDSerial.setText(recebeComparacaoDto.getSerialHdDto());
                txtSerialCPU.setText(recebeComparacaoDto.getSerialCPUDto());
                txtAInformacoesLocais.setText(recebeComparacaoDto.getInformacoes_diversasDto());

                desabilitarCamposInfo();
                personalizarMaquinaReconhecida();

            } else {

                txtId.setText(String.valueOf(recebeComparacaoDto.getId_reconhecimentoDto()));
                txtPlacaMae.setText(recebeComparacaoDto.getSerial_placa_maeDto());
                txtRecuperaData.setText(recebeComparacaoDto.getDt_hora_conectouDto());
                txtHDSerial.setText(recebeComparacaoDto.getSerialHdDto());
                txtSerialCPU.setText(recebeComparacaoDto.getSerialCPUDto());
                txtAInformacoesLocais.setText(recebeComparacaoDto.getInformacoes_diversasDto());

                desabilitarCamposInfo();
                personalizarMaquinaBloqueada();
                btnLogin.setEnabled(false);
                txtLogin.setEnabled(false);
                txtSenha.setEnabled(false);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Camada GUI/Método:"
                    + " \nsisSegHospeda Tela Login\n"
                    + "Falha de Comunicação com Banco de Dados\n"
                    + "" + e.getMessage());
            System.exit(0);
        }

    }

    private void txtLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            txtSenha.requestFocus();

        }

    }//GEN-LAST:event_txtLoginKeyPressed

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            btnLogin.requestFocus();

        }

    }//GEN-LAST:event_txtSenhaKeyPressed

    private void btnLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLoginKeyPressed

    }//GEN-LAST:event_btnLoginKeyPressed

    private void txtRecuperaDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecuperaDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRecuperaDataActionPerformed


    private void btnLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnLoginFocusGained
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        txtLogin.setEnabled(false);
        txtSenha.setEnabled(false);
        btnLogin.setEnabled(false);

        if (recebeConexao == true) {

            if (!txtLogin.getText().equals("") && !txtSenha.getText().equals("")) {

                login();
            }

            if (txtLogin.getText().equals("")) {

                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Digite o Usuario");
                txtLogin.requestFocus();
                txtLogin.setText("Usuario?");
            }

            if (txtSenha.getText().equals("")) {

                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Digite a SENHA");
                txtSenha.requestFocus();
                txtSenha.setText("");
            }

        } else {

            Font f = new Font("Tahoma", Font.ITALIC, 9);
            lblLogin.setVisible(true);
            lblLogin.setText("Sem Conexao Internet");
            lblLogin.setForeground(Color.RED);
            lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/BancoDesconectado.png")));
            lblStatusEspecificacao.setText("Falha na Comunicação");
            txtLogin.requestFocus();
        }

    }//GEN-LAST:event_btnLoginFocusGained

    private void txtLoginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusLost

    }//GEN-LAST:event_txtLoginFocusLost

    private void txtSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaFocusGained

        efeitoCampoTxtSenhaReceberFoco();
        txtLogin.setToolTipText("Digite a SENHA do Usuário");
        painelEfeitoTxtSenha.setBackground(Color.CYAN);


    }//GEN-LAST:event_txtSenhaFocusGained

    private void txtSenhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaFocusLost
        estado = false;
        txtSenha.setBackground(Color.WHITE);

    }//GEN-LAST:event_txtSenhaFocusLost

    private void efeitoCampoTxtSenhaReceberFoco() {

        estado = true;

        Thread t = new Thread() {
            public void run() {

                for (;;) {
                    if (estado == true) {

                        try {
                            sleep(1);

                            if (segundos % 2 == 0) {
                                painelEfeitoTxtSenha.setVisible(false);
                                txtSenha.setBackground(Color.ORANGE);

                            }

                            if (segundos % 2 != 0) {
                                painelEfeitoTxtSenha.setVisible(true);
                                lblImgNuvemPos1.setVisible(false);
                            }

                            //se segundos dividido por 2 igual a zero e menor que 10
                            //ou seja, os numeros pares menor igual a 10
                            //se par 
                            if (segundos % 2 == 0 && segundos <= 10) {
                                //                            System.out.println("PAR" + segundos);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(false);
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                            }

                            //se impar e menor que 10
                            if (segundos % 2 != 0 && segundos <= 10) {
                                //                          System.out.println("IMPAR" + segundos);
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 10 && segundos <= 20)) {
                                //                        System.out.println("PAR:" + segundos);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 10 && segundos <= 20)) {
                                //                      System.out.println("IMPAR:" + segundos);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(false);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                            }

                            if (segundos % 2 == 0 && (segundos > 20 && segundos <= 30)) {
            //                    System.out.println("PAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 20 && segundos <= 30)) {
              //                  System.out.println("IMPAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 30 && segundos <= 40)) {
                //                System.out.println("PAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                //boneco posicao 3 fica visivel estatico PAR 
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 30 && segundos <= 40)) {

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 3 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 40 && segundos <= 50)) {

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                //boneco posicao 3 fica visivel estatico PAR 
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);
                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 40 && segundos <= 50)) {

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 3 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);
                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 50 && segundos <= 54)) {

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                //boneco posicao 3 fica visivel estatico PAR 
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);
                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(true);
                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos6.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 50 && segundos <= 54)) {

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 3 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos6.setVisible(false);

                            }

                            if (segundos > 54 && segundos <= 60) {
                                new Thread() {

                                    public void run() {

                                        for (int i = segundos; i < 61; i++) {

                                            try {

                                                sleep(4000);
                                                barraProgresso.setValue(i);

                                                if (barraProgresso.getValue() == 55) {
                                                    lblVerificacao.setText("1% Inicializando barra de progresso");

                                                    // painelInferior.setVisible(true);
                                                    // barraProgresso.setVisible(true);
                                                    lblVerificacao.setVisible(true);

                                                } else if (barraProgresso.getValue() == 56) {
                                                    lblVerificacao.setText("45% Descarregado");

                                                } else if (barraProgresso.getValue() == 57) {

                                                    lblVerificacao.setText("65% Descarregado");

                                                } else if (barraProgresso.getValue() == 59) {

                                                    lblVerificacao.setText("85% Descarregado");
                                                } else {
                                                    lblVerificacao.setText("Encerrado com sucesso!");
                                                    System.exit(0);//sair do sistema
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                JOptionPane.showMessageDialog(null, "Desculpe! Erro no moduto de Descarregamento\n" + e.getMessage());

                                            }

                                        }
                                    }
                                }.start();// iniciando a Thread
                            }

                            if (milissegundos > 1000) {
                                milissegundos = 0;
                                segundos++;

                            }

                            if (segundos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos++;
                            }

                            if (minutos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos = 0;
                                horas++;
                            }

                            milissegundos++;

                        } catch (Exception e) {

                        }

                    } else {
                        break;
                    }
                }

            }

        };
        t.start();

    }

    //METODO CAMPO TXTLOGIN 
    private void efeitoCampoTxtLoinReceberFoco() {

        estado = true;

        Thread t = new Thread() {
            public void run() {

                for (;;) {
                    if (estado == true) {

                        try {
                            sleep(1);

                            if (segundos % 2 == 0) {
                                painelEfeitoTxtLogin.setVisible(false);
                                txtLogin.setBackground(Color.CYAN);

                            }

                            if (segundos % 2 != 0) {
                                painelEfeitoTxtLogin.setVisible(true);
                                lblImgNuvemPos1.setVisible(false);
                            }

                            //se segundos dividido por 2 igual a zero e menor que 10
                            //ou seja, os numeros pares menor igual a 10
                            //se par 
                            if (segundos % 2 == 0 && segundos <= 10) {
                                //                            System.out.println("PAR" + segundos);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(false);
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                            }

                            //se impar e menor que 10
                            if (segundos % 2 != 0 && segundos <= 10) {
                                //                          System.out.println("IMPAR" + segundos);
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 10 && segundos <= 20)) {
                                //                        System.out.println("PAR:" + segundos);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 10 && segundos <= 20)) {
                                //                      System.out.println("IMPAR:" + segundos);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(false);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                            }

                            if (segundos % 2 == 0 && (segundos > 20 && segundos <= 30)) {
            //                    System.out.println("PAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 20 && segundos <= 30)) {
              //                  System.out.println("IMPAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 30 && segundos <= 40)) {
                //                System.out.println("PAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                //boneco posicao 3 fica visivel estatico PAR 
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 30 && segundos <= 40)) {
                  //              System.out.println("IMPAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 3 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 40 && segundos <= 50)) {
                    //            System.out.println("PAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                //boneco posicao 3 fica visivel estatico PAR 
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);
                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 40 && segundos <= 50)) {
                      //          System.out.println("IMPAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 3 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);
                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 50 && segundos <= 54)) {
                        //        System.out.println("PAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                //boneco posicao 3 fica visivel estatico PAR 
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);
                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(true);
                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos6.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 50 && segundos <= 54)) {
                          //      System.out.println("IMPAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 3 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos6.setVisible(false);

                            }

                            if (segundos > 54 && segundos <= 60) {
                                new Thread() {

                                    public void run() {

                                        for (int i = segundos; i < 61; i++) {

                                            try {

                                                sleep(4000);
                                                barraProgresso.setValue(i);

                                                if (barraProgresso.getValue() == 55) {
                                                    lblVerificacao.setText("1% Inicializando barra de progresso");

                                                    // painelInferior.setVisible(true);
                                                    // barraProgresso.setVisible(true);
                                                    lblVerificacao.setVisible(true);

                                                } else if (barraProgresso.getValue() == 56) {
                                                    lblVerificacao.setText("45% Descarregado");

                                                } else if (barraProgresso.getValue() == 57) {

                                                    lblVerificacao.setText("65% Descarregado");

                                                } else if (barraProgresso.getValue() == 59) {

                                                    lblVerificacao.setText("85% Descarregado");
                                                } else {
                                                    lblVerificacao.setText("Encerrado com sucesso!");
                                                    System.exit(0);//sair do sistema
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                JOptionPane.showMessageDialog(null, "Desculpe! Erro no moduto de Descarregamento\n" + e.getMessage());

                                            }

                                        }
                                    }
                                }.start();// iniciando a Thread
                            }

                            if (milissegundos > 1000) {
                                milissegundos = 0;
                                segundos++;

                            }

                            if (segundos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos++;
                            }

                            if (minutos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos = 0;
                                horas++;
                            }

                            milissegundos++;

                        } catch (Exception e) {

                        }

                    } else {
                        break;
                    }
                }

            }

        };
        t.start();

    }


    private void txtLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusGained
        txtLogin.setToolTipText("Digite NOME do Usuário");
        efeitoCampoTxtLoinReceberFoco();

    }//GEN-LAST:event_txtLoginFocusGained

    private void txtLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoginActionPerformed

    private void btnLoginStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnLoginStateChanged


    }//GEN-LAST:event_btnLoginStateChanged

    private void btnLoginComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_btnLoginComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoginComponentHidden

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Login().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    ex.getMessage();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblChaveEntrada;
    private javax.swing.JLabel lblDataHora;
    private javax.swing.JLabel lblIReport;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblImgNuvemPos1;
    private javax.swing.JLabel lblImgNuvemPos2;
    private javax.swing.JLabel lblImgNuvemPos3;
    private javax.swing.JLabel lblImgNuvemPos4;
    private javax.swing.JLabel lblImgNuvemPos5;
    private javax.swing.JLabel lblImgNuvemPos6;
    private javax.swing.JLabel lblInfoUsuario;
    public static javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblLogoJava;
    private javax.swing.JLabel lblNetbeans;
    private javax.swing.JLabel lblPlacaMae;
    private javax.swing.JLabel lblSQL;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblSerialCPU;
    private javax.swing.JLabel lblSerialHD;
    private javax.swing.JLabel lblStatusDaConexao;
    private javax.swing.JLabel lblStatusEspecificacao;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblVerificacao;
    private javax.swing.JPanel painelColetadoInformaceos;
    private javax.swing.JPanel painelEfeitoTxtLogin;
    private javax.swing.JPanel painelEfeitoTxtSenha;
    private javax.swing.JPanel painelInformativo;
    private javax.swing.JPanel painelNuvem;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTextArea txtAInformacoesLocais;
    private javax.swing.JTextField txtHDSerial;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtPlacaMae;
    private javax.swing.JTextField txtRecuperaData;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtSerialCPU;
    // End of variables declaration//GEN-END:variables
}
