package br.com.subgerentepro.backup;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Contribuição: Canal Yotube:https://www.youtube.com/watch?v=fq1alQY9iLk
 *
 */
public class SistemaBackup extends javax.swing.JInternalFrame {
    
    public SistemaBackup() {
        initComponents();
        aoCarregar();
    }
    
    private void aoCarregar() {
        
        lblInfo.setVisible(false);

        //dados conexão banco local 
        txtUsuario.setText("inovec87_torres");
        passSenha.setText("pmaa1@2@");
        txtBanco.setText("inovec87_infoq");

        //barra de progresso 
        barraProgresso.setVisible(false);
        lblVerificacao.setVisible(false);

        //comando interligados
        cbLocalBanco.setSelectedItem("Banco Local");
        String selecionaLocalBanco = (String) cbLocalBanco.getSelectedItem();
        personalizarURL(selecionaLocalBanco);
        
    }
    
    private void personalizarURL(String dataBaseHopedado) {

        //JOptionPane.showMessageDialog(null, "dataBaseHospedado"+dataBaseHopedado);
        if (dataBaseHopedado.equalsIgnoreCase("Banco Local")) {

            //  JOptionPane.showMessageDialog(null, "Local");
            Font f = new Font("Tahoma", Font.BOLD, 26);
            
            txtURL.setVisible(false);
            lblURL.setText("Banco MySQL Local");
            lblURL.setForeground(Color.WHITE);
            lblURL.setFont(f);
            PainelInfoConexao.setBackground(Color.red);
        }
        if (dataBaseHopedado.equalsIgnoreCase("Banco Nuvem")) {

            //  JOptionPane.showMessageDialog(null, "Nuvem");
            txtURL.setVisible(true);
            lblURL.setText("URL:");
            PainelInfoConexao.setBackground(Color.red);
        }
        
    }
    
    String path = null;
    String filename;
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PainelPrincipal = new javax.swing.JPanel();
        PainelInformativo = new javax.swing.JPanel();
        lblInfo = new javax.swing.JLabel();
        lblCaminho = new javax.swing.JLabel();
        btnSelecionarPasta = new javax.swing.JButton();
        btnExecutaBackup = new javax.swing.JButton();
        lblVerificacao = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();
        painelConexao = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        passSenha = new javax.swing.JPasswordField();
        lblBancoDados = new javax.swing.JLabel();
        txtBanco = new javax.swing.JTextField();
        cbLocalBanco = new javax.swing.JComboBox();
        PainelInfoConexao = new javax.swing.JPanel();
        lblURL = new javax.swing.JLabel();
        txtURL = new javax.swing.JTextField();

        setClosable(true);

        PainelPrincipal.setBackground(java.awt.SystemColor.activeCaption);
        PainelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PainelInformativo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Executar Backup:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 14))); // NOI18N
        PainelInformativo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInfo.setFont(new java.awt.Font("Tahoma", 3, 16)); // NOI18N
        lblInfo.setText("mensagem informativa ");
        PainelInformativo.add(lblInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 500, 30));

        lblCaminho.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCaminho.setText("Caminho");
        PainelInformativo.add(lblCaminho, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 91, 400, 30));

        btnSelecionarPasta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/search-512.png"))); // NOI18N
        btnSelecionarPasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarPastaActionPerformed(evt);
            }
        });
        PainelInformativo.add(btnSelecionarPasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 40, 30));

        btnExecutaBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/save-512.png"))); // NOI18N
        btnExecutaBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecutaBackupActionPerformed(evt);
            }
        });
        PainelInformativo.add(btnExecutaBackup, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, 40, 30));

        lblVerificacao.setText("Verificacao");
        PainelInformativo.add(lblVerificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 240, -1));
        PainelInformativo.add(barraProgresso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 510, 20));

        PainelPrincipal.add(PainelInformativo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 550, 180));

        painelConexao.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados da Conexão:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lblUsuario.setText("USUÁRIO:");

        lblSenha.setText("SENHA:");

        lblBancoDados.setText("BANCO:");

        cbLocalBanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Banco Local", "Banco Nuvem" }));
        cbLocalBanco.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbLocalBancoItemStateChanged(evt);
            }
        });

        lblURL.setText("URL:");

        javax.swing.GroupLayout PainelInfoConexaoLayout = new javax.swing.GroupLayout(PainelInfoConexao);
        PainelInfoConexao.setLayout(PainelInfoConexaoLayout);
        PainelInfoConexaoLayout.setHorizontalGroup(
            PainelInfoConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelInfoConexaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblURL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtURL, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PainelInfoConexaoLayout.setVerticalGroup(
            PainelInfoConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelInfoConexaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelInfoConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblURL))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout painelConexaoLayout = new javax.swing.GroupLayout(painelConexao);
        painelConexao.setLayout(painelConexaoLayout);
        painelConexaoLayout.setHorizontalGroup(
            painelConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelConexaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PainelInfoConexao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelConexaoLayout.createSequentialGroup()
                        .addComponent(cbLocalBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelConexaoLayout.createSequentialGroup()
                        .addComponent(lblUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 11, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(painelConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelConexaoLayout.createSequentialGroup()
                        .addComponent(lblBancoDados)
                        .addGap(12, 12, 12)
                        .addComponent(txtBanco, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                    .addGroup(painelConexaoLayout.createSequentialGroup()
                        .addComponent(lblSenha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passSenha)))
                .addContainerGap())
        );
        painelConexaoLayout.setVerticalGroup(
            painelConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelConexaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbLocalBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(painelConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSenha)
                        .addComponent(lblUsuario)))
                .addGap(18, 18, 18)
                .addGroup(painelConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PainelInfoConexao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelConexaoLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(painelConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBancoDados)
                            .addComponent(txtBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        PainelPrincipal.add(painelConexao, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 550, 200));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PainelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );

        setBounds(0, 0, 594, 496);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelecionarPastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarPastaActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(this);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        
        try {
            File f = fc.getSelectedFile();
            path = f.getAbsolutePath();
            path = path.replace('\\', '/');
            path = path + "-" + date + ".sql";
            //  txtCaminho.setText(path);
            lblCaminho.setText(path);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSelecionarPastaActionPerformed
    
    private void salvarBK() {
        
        Process p = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            String usuario = txtUsuario.getText();
            String senha = (new String(passSenha.getPassword()));
            String database = txtBanco.getText();
            //  p = runtime.exec("C:/Program Files/MySQL/MySQL Server 5.5/bin/mysqldump.exe -uroot -p1020 --add-drop-database -B infoq -r" + path);
            p = runtime.exec("C:/Program Files/MySQL/MySQL Server 5.5/bin/mysqldump.exe -u" + usuario + " -p" + senha + " --add-drop-database -B " + database + " -r" + path);
            
            int processComplete = p.waitFor();
            if (processComplete == 0) {
                
                lblInfo.setForeground(Color.RED);
                lblInfo.setText("Backup Criado com Sucesso");
                lblInfo.setVisible(true);
            } else {
                
                lblInfo.setForeground(Color.RED);
                lblInfo.setText("Não é possível criar backup");
                lblInfo.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    

    private void btnExecutaBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecutaBackupActionPerformed
        lblInfo.setText("Iniciando Backup");
        barraProgresso.setVisible(true);
        lblVerificacao.setVisible(true);
        
        new Thread() {
            
            public void run() {
                
                for (int i = 0; i < 101; i++) {
                    
                    try {
                        
                        sleep(25);
                        barraProgresso.setValue(i);
                        
                        if (barraProgresso.getValue() <= 5) {
                            lblVerificacao.setText("Inicializando barra de progresso");
                            
                            barraProgresso.setVisible(true);
                            lblVerificacao.setVisible(true);
                            
                        } else if (barraProgresso.getValue() <= 15) {
                            lblVerificacao.setText("15% executando");
                            
                        } else if (barraProgresso.getValue() <= 25) {
                            
                            lblVerificacao.setText("25% executando");
                            
                        } else if (barraProgresso.getValue() <= 35) {
                            lblVerificacao.setText("35% executando");
                            
                        } else if (barraProgresso.getValue() <= 45) {
                            lblVerificacao.setText("45% executando");
                            
                        } else if (barraProgresso.getValue() <= 55) {
                            lblVerificacao.setText("55% executando");
                            
                        } else if (barraProgresso.getValue() <= 65) {
                            lblVerificacao.setText("65% executando");
                            
                        } else if (barraProgresso.getValue() <= 75) {
                            lblVerificacao.setText("75% executando");
                            
                        } else if (barraProgresso.getValue() <= 85) {
                            lblVerificacao.setText("85% executando");
                            
                        } else if (barraProgresso.getValue() <= 95) {
                            lblVerificacao.setText("95% executando");
                            
                        } else if (barraProgresso.getValue() <= 99) {
                            lblVerificacao.setText("99% executando");
                            
                        } else if (barraProgresso.getValue() <= 101) {
                            lblVerificacao.setText("100% concluido");
                            salvarBK();
                            PainelInformativo.setBackground(Color.WHITE);
                            painelConexao.setBackground(Color.WHITE);
                        }
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Desculpe! Erro ao Executar Backup\n" + e.getMessage());
                        
                    }
                    
                }
            }
        }.start();// iniciando a Thread


    }//GEN-LAST:event_btnExecutaBackupActionPerformed

    private void cbLocalBancoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbLocalBancoItemStateChanged
        
        String selecionaLocalBanco = (String) cbLocalBanco.getSelectedItem();
        
        personalizarURL(selecionaLocalBanco);

    }//GEN-LAST:event_cbLocalBancoItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelInfoConexao;
    private javax.swing.JPanel PainelInformativo;
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnExecutaBackup;
    private javax.swing.JButton btnSelecionarPasta;
    private javax.swing.JComboBox cbLocalBanco;
    private javax.swing.JLabel lblBancoDados;
    private javax.swing.JLabel lblCaminho;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblURL;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblVerificacao;
    private javax.swing.JPanel painelConexao;
    private javax.swing.JPasswordField passSenha;
    private javax.swing.JTextField txtBanco;
    private javax.swing.JTextField txtURL;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
