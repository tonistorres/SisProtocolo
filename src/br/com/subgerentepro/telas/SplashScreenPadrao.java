package br.com.subgerentepro.telas;

import br.com.subgerentepro.dao.InfoControleConexaoDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class SplashScreenPadrao extends javax.swing.JFrame {

    //*********************************************************   
    //TESTE DE CONECTIVIDADE COM BANCO DE DADOS [[[[[PRIMERIO CRONOMETRO]]]]
    //Documentação:https://www.youtube.com/watch?v=LoKQvAQpL3w
    //************************************************************
    // inicializando variáveis do tipo int e estáticas
    //    static int milissegundos = 0; 
    //    static int segundos = 0;
    //    static int minutos = 0;
    //    static int horas = 0;
    //    static boolean estado = true;
    //*********************************************************   
    //TESTE DE CONECTIVIDADE COM BANCO DE DADOS [[[[[SEGUNDO CRONOMETRO DE SAIDA]]]]
    //Documentação:https://www.youtube.com/watch?v=LoKQvAQpL3w
    //************************************************************
    //    static int milissegundos1 = 0;
    //    static int segundos1 = 0;
    //    static int minutos1 = 0;
    //    static int horas1 = 0;
    //    static boolean estado1 = true;
    //----------------------------------------------------------------------------------------------------- //
    // 6º PASSO CRIAR UM OBJETO DO TIPO SplashScreenPadrao TAMBÉM NA TELA DE SplashScreenPadrão              //
    //--------------------------------------------------------------------------------------------------- //
    //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 08                        //
    // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555380?start=0 //
    //------------------------------------------------------------------------------------------------ //
    SplashScreenPadrao splash = this;

    InfoControleConexaoDAO controle = new InfoControleConexaoDAO();

    public SplashScreenPadrao() {
        initComponents();
        startThread();

        //Tive que desabilitar esse método pois o uso do mesmo desta forma acarreta em sobre carga
        //no numero de conexões lançadas no banco causando incosistencia no sistema:
        //Lentidão , e derrubando o mesmo quando excede esse limite 
        //    metodoStartTesteConexao();
    }

    //----------------------------------------------------------------------------------------------------- //
    // 7º PASSO MÉTODO PARA INICIAR O PROGRESSO COM SPLASH                                                 //
    //--------------------------------------------------------------------------------------------------- //
    //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 08                        //
    // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555380?start=0 //
    //------------------------------------------------------------------------------------------------ //
    void startThread() {
        /**
         * Função de Inicialização da Barra de Progresso
         */
        Thread hi = new Thread(new Runnable() {
            @Override
            //----------------------------------------------------------------------------------------------------- //
            // 8º PASSO CHAMAR O FORMULARIO DE LOGIN                                                               //
            //--------------------------------------------------------------------------------------------------- //
            //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 09                        //
            // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555384?start=0 //
            //------------------------------------------------------------------------------------------------ //
            public void run() {

                Login login;
                try {
                    login = new Login(splash);
                    login.setLocationRelativeTo(null);
                    login.setVisible(true);
                    dispose();

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SplashScreenPadrao.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        hi.start();//inicie minha barra de progresso 
    }

    //----------------------------------------------------------------------------------------------------- //
    // 4º PASSO CRIAR O MÉTODO  getJProgressBar() E getJLabel()                                            //
    //--------------------------------------------------------------------------------------------------- //
    //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 06                        //
    // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555374?start=0 //
    //------------------------------------------------------------------------------------------------ //
    public JProgressBar getJProgressBar() {
        return Progresso;
    }

    /**
     * segue a mesma lógica do getProgressBar só que criamos o getJLabel e
     * retornamos nosso label que está associado a nossa brra de progresso
     */
    public JLabel getJLabel() {
        return lblInfoCarregamento;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelProgress = new javax.swing.JPanel();
        lblInfoCarregamento = new javax.swing.JLabel();
        Progresso = new javax.swing.JProgressBar();
        lblSaindo = new javax.swing.JLabel();
        painelInfoLogoEmpresa = new javax.swing.JPanel();
        lblInformacoesSistema = new javax.swing.JLabel();
        lblInformacoesDesenvolvedor = new javax.swing.JLabel();
        painelTecnologiasRodaPe = new javax.swing.JPanel();
        lblTecnologiasMySQL = new javax.swing.JLabel();
        lblTecnologiasJava = new javax.swing.JLabel();
        lblTecnologiasIReport = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ponto de Vendas");
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelProgress.setBackground(new java.awt.Color(255, 255, 255));

        lblInfoCarregamento.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblInfoCarregamento.setForeground(new java.awt.Color(51, 0, 102));
        lblInfoCarregamento.setText("Carregando Sistema  Version 1.0 Beta");
        lblInfoCarregamento.setName("lblInfoCarregamento"); // NOI18N
        lblInfoCarregamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInfoCarregamentoMouseClicked(evt);
            }
        });

        Progresso.setBackground(new java.awt.Color(255, 255, 255));
        Progresso.setName("Progresso"); // NOI18N
        Progresso.setOpaque(true);

        lblSaindo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblSaindo.setForeground(new java.awt.Color(204, 153, 0));
        lblSaindo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout painelProgressLayout = new javax.swing.GroupLayout(painelProgress);
        painelProgress.setLayout(painelProgressLayout);
        painelProgressLayout.setHorizontalGroup(
            painelProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProgressLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(painelProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInfoCarregamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(painelProgressLayout.createSequentialGroup()
                        .addGroup(painelProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelProgressLayout.createSequentialGroup()
                                .addGap(154, 154, 154)
                                .addComponent(lblSaindo, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Progresso, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        painelProgressLayout.setVerticalGroup(
            painelProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProgressLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblInfoCarregamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Progresso, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(lblSaindo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(painelProgress, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 340, 130));

        painelInfoLogoEmpresa.setBackground(new java.awt.Color(255, 255, 255));
        painelInfoLogoEmpresa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInformacoesSistema.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblInformacoesSistema.setForeground(new java.awt.Color(204, 153, 0));
        lblInformacoesSistema.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacoesSistema.setText("Sistema de Protocolo J");
        painelInfoLogoEmpresa.add(lblInformacoesSistema, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 340, 30));

        lblInformacoesDesenvolvedor.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblInformacoesDesenvolvedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacoesDesenvolvedor.setText("Desenvolvedor: Tonis A. Torres Ferreira ");
        painelInfoLogoEmpresa.add(lblInformacoesDesenvolvedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 20));

        getContentPane().add(painelInfoLogoEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 130));

        painelTecnologiasRodaPe.setBackground(java.awt.Color.white);
        painelTecnologiasRodaPe.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tecnologias:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Quattrocento Sans", 1, 13), new java.awt.Color(0, 153, 204))); // NOI18N
        painelTecnologiasRodaPe.setForeground(new java.awt.Color(0, 153, 204));
        painelTecnologiasRodaPe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        painelTecnologiasRodaPe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTecnologiasMySQL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTecnologiasMySQL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/Banco_MySQL.png"))); // NOI18N
        painelTecnologiasRodaPe.add(lblTecnologiasMySQL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 160, -1));

        lblTecnologiasJava.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTecnologiasJava.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/javalOGIN.png"))); // NOI18N
        painelTecnologiasRodaPe.add(lblTecnologiasJava, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 160, -1));

        lblTecnologiasIReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTecnologiasIReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/IReportLogin.jpg"))); // NOI18N
        painelTecnologiasRodaPe.add(lblTecnologiasIReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 160, -1));

        getContentPane().add(painelTecnologiasRodaPe, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 160, 260));

        setSize(new java.awt.Dimension(501, 264));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblInfoCarregamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInfoCarregamentoMouseClicked
        Login form;
        try {
            form = new Login();
            form.setLocationRelativeTo(form);
            form.toFront();
            form.setVisible(true);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }


    }//GEN-LAST:event_lblInfoCarregamentoMouseClicked


    public static void main(String args[]) {

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
            java.util.logging.Logger.getLogger(SplashScreenPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SplashScreenPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SplashScreenPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SplashScreenPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /**
         * Contribuição:Hugo
         * Vasconcelos:https://www.udemy.com/curso-de-java-design/learn/v4/t/lecture/10503330?start=0
         * Dessa forma abrirá centralizada em qualquer monitor com qualquer
         * configuração
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SplashScreenPadrao tela = new SplashScreenPadrao();//Aqui utilizamos orientação objeto: criamo um objetio do tipo tela
                tela.setLocationRelativeTo(null);//setamos uma atributo a esse objeto do tipo setLocationRelativeTo 
                tela.setVisible(true);//Em seguida outro abritubo para torná-la visivel 

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar Progresso;
    private javax.swing.JLabel lblInfoCarregamento;
    private javax.swing.JLabel lblInformacoesDesenvolvedor;
    private javax.swing.JLabel lblInformacoesSistema;
    private javax.swing.JLabel lblSaindo;
    private javax.swing.JLabel lblTecnologiasIReport;
    private javax.swing.JLabel lblTecnologiasJava;
    private javax.swing.JLabel lblTecnologiasMySQL;
    private javax.swing.JPanel painelInfoLogoEmpresa;
    private javax.swing.JPanel painelProgress;
    private javax.swing.JPanel painelTecnologiasRodaPe;
    // End of variables declaration//GEN-END:variables
}
