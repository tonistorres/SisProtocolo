package br.com.subgerentepro.consultas;

import br.com.subgerentepro.dao.FluxoTFDDAO;
import br.com.subgerentepro.dto.FluxoTFDDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import br.com.subgerentepro.tabelas.TabelaNuvem;
import br.com.subgerentepro.telas.Render;
import br.com.subgerentepro.telas.TelaProtocDocsTFD;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

public class ViewGeradorDocsProtocolo extends javax.swing.JInternalFrame {

    /**
     * Creates new form ViewGeradorDocsProtocolo
     */
    FluxoTFDDTO fluxoDTO = new FluxoTFDDTO();
    FluxoTFDDAO fluxoDAO = new FluxoTFDDAO();
    TabelaNuvem t = new TabelaNuvem();

    public ViewGeradorDocsProtocolo() throws PersistenciaException {
        initComponents();

        //*********************************************************************
        //OBSERVAÇÃO IMPORTANTE: As 3(três) Linhas de códigos abaixo devem  //
        //esta na seguinte sequência abaixo respectivamente                // 
        //Iremos criar dois métodos que colocará estilo em nossas tabelas //***************************
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0//
        tabela.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 
        t.ver_tabela(tabela);                                                                        //
        //********************************************************************************************
        //********************************************************************************************
        //CANAL:MamaNs - Java Swing UI - Design jTable       
        //clicar e a tabela mudar a linha de cor indicando que a linha cliccada é aquela 
        //https://www.youtube.com/watch?v=RXhMdUPk12k
        //*******************************************************************************************
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(new Color(32, 136, 203));
        tabela.getTableHeader().setForeground(new Color(255, 255, 255));
        tabela.setRowHeight(25);

        barProgressProcedimentosBackEnd.setIndeterminate(true);
        txtIdCustomBuscar.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        btnBuscarIdCustom = new javax.swing.JButton();
        txtCPFBuscar = new javax.swing.JFormattedTextField();
        btnBuscarCPF = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        barProgressProcedimentosBackEnd = new javax.swing.JProgressBar();
        lblInfoProcessosBackEnd = new javax.swing.JLabel();
        lblLinhaInformativa = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboMascaraCustomizada = new javax.swing.JComboBox();
        lblConsultarProtocolo = new javax.swing.JLabel();
        txtIdCustomBuscar = new javax.swing.JFormattedTextField();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabela.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDCUSTOM", "PACIENTE", "CPF", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setFocusable(false);
        tabela.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabela.setRowHeight(25);
        tabela.setSelectionBackground(new java.awt.Color(32, 136, 203));
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setResizable(false);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(60);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(60);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 740, 290));

        btnBuscarIdCustom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnBuscarIdCustom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnBuscarIdCustomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnBuscarIdCustomFocusLost(evt);
            }
        });
        btnBuscarIdCustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarIdCustomActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarIdCustom, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 32, 32));

        try {
            txtCPFBuscar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCPFBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCPFBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCPFBuscarFocusLost(evt);
            }
        });
        txtCPFBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPFBuscarActionPerformed(evt);
            }
        });
        txtCPFBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCPFBuscarKeyPressed(evt);
            }
        });
        jPanel1.add(txtCPFBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 145, 33));

        btnBuscarCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnBuscarCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnBuscarCPFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnBuscarCPFFocusLost(evt);
            }
        });
        btnBuscarCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCPFActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 50, 32, 33));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 60, 40));
        jPanel1.add(barProgressProcedimentosBackEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 90, -1));

        lblInfoProcessosBackEnd.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        lblInfoProcessosBackEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfoProcessosBackEnd.setText("Back-End");
        jPanel1.add(lblInfoProcessosBackEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 90, 20));

        lblLinhaInformativa.setText("Info:");
        jPanel1.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 0, 610, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Protocolo:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 70, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("CPF:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, -1, -1));

        cboMascaraCustomizada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", " " }));
        cboMascaraCustomizada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMascaraCustomizadaActionPerformed(evt);
            }
        });
        jPanel1.add(cboMascaraCustomizada, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 40, 30));

        lblConsultarProtocolo.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblConsultarProtocolo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/mascaraBanco.png"))); // NOI18N
        jPanel1.add(lblConsultarProtocolo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 30, 30));

        try {
            txtIdCustomBuscar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtIdCustomBuscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIdCustomBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdCustomBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdCustomBuscarFocusLost(evt);
            }
        });
        txtIdCustomBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdCustomBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdCustomBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtIdCustomBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 150, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarCPFActionPerformed
    private void acaoBotaoPesquisar() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {
            acaoBTNPesquisar();

        } else {

            erroViaEmail("Sem Conexão com a Internet", "acaoBotaoPesquisar()- Camada GUI:\n"
                    + "ViewGeradorDocsProtocolo");
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }
    }

    private void pesquisarCPF() {

        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(txtCPFBuscar.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        tabela.setDefaultRenderer(Object.class, new Render());

        JButton btnReciboTFD = new JButton("Recibo");
        btnReciboTFD.setName("idReciboTFD");
        JButton btnCapaTFD = new JButton("capa");
        btnCapaTFD.setName("idCapaTFD");
        ArrayList<FluxoTFDDTO> list;

        try {

            list = (ArrayList<FluxoTFDDTO>) fluxoDAO.filtrarPesqRapidaCPF(pesquisar);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[5];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdCustomDto();
                rowData[1] = list.get(i).getInteressadoOrigemDto();
                rowData[2] = list.get(i).getCpfDto();
                /**
                 * Adicionamos mais dois campos na tabela tblListagem que serão
                 * responsável por assim dizer alocarem os espaços dos botões
                 * Editar e Deletar
                 */
                rowData[3] = btnReciboTFD;
                rowData[4] = btnCapaTFD;
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(100);

            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setForeground(Color.ORANGE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Pesquisa Realizada com Sucesso.");

        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "pesquisar()-Camada GUI- ViewGeradorDocsProtocolo.java\n"
                    + "pesquisar pelo idCustomizado para gerar o Recibo de Processo ");
            e.printStackTrace();

        }

    }

    private void acaoPesquisarCPF() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            int numeroLinhas = tabela.getRowCount();

            if (numeroLinhas > 0) {

                while (tabela.getModel().getRowCount() > 0) {
                    ((DefaultTableModel) tabela.getModel()).removeRow(0);

                }

                pesquisarCPF();

            } else {
                try {
                    t.ver_tabela(tabela);
                } catch (PersistenciaException ex) {
                    erroViaEmail(ex.getMessage(), "Método:acaoBTNPesquisar()-Camada GUI\n"
                            + "Responsável por fazer pesquisa no banco de dados \n"
                            + "e fazer a triagem na tabela ViewGeradorDocsProtocolo.java");
                    ex.printStackTrace();
                }
            }

        } else {

            erroViaEmail("Sem Conexão com a Internet", "acaoBTNPesquisar()-Camada GUI\n"
                    + "ViewGeradorDocsProtocolo");

            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }

    }

    private void acaoBotaoPesquisarCPF() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {
            acaoPesquisarCPF();

        } else {

            erroViaEmail("Sem Conexão com a Internet", "acaoBotaoPesquisar()- Camada GUI:\n"
                    + "ViewGeradorDocsProtocolo");
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }
    }

    private void acaoBTNPesquisar() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            int numeroLinhas = tabela.getRowCount();

            if (numeroLinhas > 0) {

                while (tabela.getModel().getRowCount() > 0) {
                    ((DefaultTableModel) tabela.getModel()).removeRow(0);

                }

                pesquisar();

            } else {
                try {
                    t.ver_tabela(tabela);
                } catch (PersistenciaException ex) {
                    erroViaEmail(ex.getMessage(), "Método:acaoBTNPesquisar()-Camada GUI\n"
                            + "Responsável por fazer pesquisa no banco de dados \n"
                            + "e fazer a triagem na tabela ViewGeradorDocsProtocolo.java");
                    ex.printStackTrace();
                }
            }

        } else {

            erroViaEmail("Sem Conexão com a Internet", "acaoBTNPesquisar()-Camada GUI\n"
                    + "ViewGeradorDocsProtocolo");

            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }

    }

    private void pesquisar() {

        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(txtIdCustomBuscar.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        tabela.setDefaultRenderer(Object.class, new Render());

        JButton btnReciboTFD = new JButton("Recibo");
        btnReciboTFD.setName("idReciboTFD");
        JButton btnCapaTFD = new JButton("capa");
        btnCapaTFD.setName("idCapaTFD");
        ArrayList<FluxoTFDDTO> list;

        try {

            list = (ArrayList<FluxoTFDDTO>) fluxoDAO.filtrarPesqRapida(pesquisar);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[5];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdCustomDto();
                rowData[1] = list.get(i).getInteressadoOrigemDto();
                rowData[2] = list.get(i).getCpfDto();
                /**
                 * Adicionamos mais dois campos na tabela tblListagem que serão
                 * responsável por assim dizer alocarem os espaços dos botões
                 * Editar e Deletar
                 */
                rowData[3] = btnReciboTFD;
                rowData[4] = btnCapaTFD;
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);

            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setForeground(Color.ORANGE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Pesquisa Realizada com Sucesso.");

        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "pesquisar()-Camada GUI- ViewGeradorDocsProtocolo.java\n"
                    + "pesquisar pelo idCustomizado para gerar o Recibo de Processo ");
            e.printStackTrace();

        }

    }


    private void btnBuscarIdCustomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarIdCustomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarIdCustomActionPerformed

    private void btnBuscarIdCustomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarIdCustomFocusGained

        btnBuscarIdCustom.setBackground(Color.YELLOW);

        //   JOptionPane.showMessageDialog(null, "camp IDCustom:"+txtIdCustomBuscar.getText());
        if (!txtIdCustomBuscar.getText().equals("  -TFD/    ")) {
            acaoBotaoPesquisar();
        } else {
            JOptionPane.showMessageDialog(null, "Campo Vazio");
            txtIdCustomBuscar.requestFocus();
        }

    }//GEN-LAST:event_btnBuscarIdCustomFocusGained


    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

        //
        //Canal Youtube (Contribuição:Daved Pacheco Jimez)
        //https://www.youtube.com/watch?v=jPfKFm2Yfow
        int coluna = tabela.getColumnModel().getColumnIndexAtX(evt.getX());
        int linha = evt.getY() / tabela.getRowHeight();
        if (linha < tabela.getRowCount() && linha >= 0 && coluna < tabela.getColumnCount() && coluna >= 0) {

            Object value = tabela.getValueAt(linha, coluna);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton button = (JButton) value;

                if (button.getName().equals("idReciboTFD")) {

                    String idCustomizado = (String) tabela.getValueAt(tabela.getSelectedRow(), 0);

                    JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                            + "Gerando ReciboTFD. \n"
                            + "Capturando idCustomizado\n"
                            + " Nº" + idCustomizado
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

                    //verificar se a pasta ireport existe na unidade C: do computador 
                    criandoCaminhoRelatoriosEmC();
                    //metodo para visualizar relatório reciboProcesso 
                    vizualizandoReciboProcesso(idCustomizado);
                }

                if (button.getName().equals("idCapaTFD")) {

                    String idCustomizado = (String) tabela.getValueAt(tabela.getSelectedRow(), 0);

                    JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                            + "Gerando Capa Processo TFD. \n"
                            + "Capturando idCustomizado\n"
                            + " Nº" + idCustomizado
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

                    criandoCaminhoRelatoriosEmC();

                    vizualizandoCapaDeProcesso(idCustomizado);
                }

            }

        }


    }//GEN-LAST:event_tabelaMouseClicked

    private void criandoCaminhoRelatoriosEmC() {

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


    private void btnBuscarIdCustomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarIdCustomFocusLost
        btnBuscarIdCustom.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnBuscarIdCustomFocusLost

    private void txtCPFBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFBuscarActionPerformed

    private void txtCPFBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFBuscarFocusGained
        txtCPFBuscar.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Informe o Nº do CPF a Ser Pesquisado.");
        lblLinhaInformativa.setForeground(Color.BLUE);

    }//GEN-LAST:event_txtCPFBuscarFocusGained

    private void txtCPFBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFBuscarFocusLost
        txtIdCustomBuscar.setBackground(Color.WHITE);
        lblLinhaInformativa.setText("");
    }//GEN-LAST:event_txtCPFBuscarFocusLost

    private void txtCPFBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFBuscarKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER ) {

            Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Iniciando Pesquisa no Banco de Dados...");

            btnBuscarCPF.requestFocus();

        }
    }//GEN-LAST:event_txtCPFBuscarKeyPressed

    private void btnBuscarCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarCPFFocusGained

        btnBuscarCPF.setBackground(Color.YELLOW);

        if (!txtCPFBuscar.getText().equals("   .   .   -  ")) {
            acaoBotaoPesquisarCPF();
        } else {
            JOptionPane.showMessageDialog(null, "Campo Vazio");
            txtCPFBuscar.requestFocus();
        }


    }//GEN-LAST:event_btnBuscarCPFFocusGained

    private void btnBuscarCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarCPFFocusLost
        btnBuscarCPF.setBackground(Color.WHITE);        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarCPFFocusLost

    private void cboMascaraCustomizadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMascaraCustomizadaActionPerformed

        int numeroEscolido = Integer.parseInt((String) cboMascaraCustomizada.getSelectedItem());

        switch (numeroEscolido) {
            case 1:

                MaskFormatter maskFormatter1;
                try {
                    txtIdCustomBuscar.setValue(null);
                    txtIdCustomBuscar.setEnabled(true);
                    maskFormatter1 = new MaskFormatter("#-TFD/20##");
                    txtIdCustomBuscar.setFormatterFactory(new DefaultFormatterFactory(maskFormatter1));
                    txtIdCustomBuscar.requestFocus();

                } catch (ParseException ex) {
                    erroViaEmail(ex.getMessage(), "GUI:ViewGeradorDocsProtocolo.java\n"
                            + "erro ao tentar setar a mascara \n"
                            + "na posição de 1 algarismo");
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 2:
                MaskFormatter maskFormatter2;
                try {
                    txtIdCustomBuscar.setValue(null);
                    txtIdCustomBuscar.setEnabled(true);
                    maskFormatter2 = new MaskFormatter("##-TFD/20##");
                    txtIdCustomBuscar.setFormatterFactory(new DefaultFormatterFactory(maskFormatter2));
                    txtIdCustomBuscar.requestFocus();
                } catch (ParseException ex) {

                    erroViaEmail(ex.getMessage(), "GUI:ViewGeradorDocsProtocolo.java\n"
                            + "erro ao tentar setar a mascara \n"
                            + "na posição de 2 algarismo");

                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 3:
                MaskFormatter maskFormatter3;
                try {
                    txtIdCustomBuscar.setValue(null);
                    txtIdCustomBuscar.setEnabled(true);
                    maskFormatter3 = new MaskFormatter("###-TFD/20##");
                    txtIdCustomBuscar.setFormatterFactory(new DefaultFormatterFactory(maskFormatter3));
                    txtIdCustomBuscar.requestFocus();
                } catch (ParseException ex) {

                    erroViaEmail(ex.getMessage(), "GUI:ViewGeradorDocsProtocolo.java\n"
                            + "erro ao tentar setar a mascara \n"
                            + "na posição de 3 algarismo");
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 4:
                MaskFormatter maskFormatter4;
                try {
                    txtIdCustomBuscar.setValue(null);
                    txtIdCustomBuscar.setEnabled(true);
                    maskFormatter4 = new MaskFormatter("####-TFD/20##");
                    txtIdCustomBuscar.setFormatterFactory(new DefaultFormatterFactory(maskFormatter4));
                    txtIdCustomBuscar.requestFocus();
                } catch (ParseException ex) {

                    erroViaEmail(ex.getMessage(), "GUI:ViewGeradorDocsProtocolo.java\n"
                            + "erro ao tentar setar a mascara \n"
                            + "na posição de 4 algarismo");
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 5:
                MaskFormatter maskFormatter5;
                try {
                    txtIdCustomBuscar.setValue(null);
                    txtIdCustomBuscar.setEnabled(true);
                    maskFormatter5 = new MaskFormatter("#####-TFD/20##");
                    txtIdCustomBuscar.setFormatterFactory(new DefaultFormatterFactory(maskFormatter5));
                    txtIdCustomBuscar.requestFocus();
                } catch (ParseException ex) {

                    erroViaEmail(ex.getMessage(), "GUI:ViewGeradorDocsProtocolo.java\n"
                            + "erro ao tentar setar a mascara \n"
                            + "na posição de 5 algarismo");
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;
            case 6:
                MaskFormatter maskFormatter6;
                try {
                    txtIdCustomBuscar.setValue(null);
                    txtIdCustomBuscar.setEnabled(true);
                    maskFormatter6 = new MaskFormatter("######-TFD/20##");
                    txtIdCustomBuscar.setFormatterFactory(new DefaultFormatterFactory(maskFormatter6));
                    txtIdCustomBuscar.requestFocus();

                } catch (ParseException ex) {

                    erroViaEmail(ex.getMessage(), "GUI:ViewGeradorDocsProtocolo.java\n"
                            + "erro ao tentar setar a mascara \n"
                            + "na posição de 6 algarismo");
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

        }

        System.out.println("Numero Esclido: " + numeroEscolido);
    }//GEN-LAST:event_cboMascaraCustomizadaActionPerformed

    private void txtIdCustomBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdCustomBuscarFocusGained
        txtIdCustomBuscar.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Informe o Nº do Processo a Ser Pesquisado.");
        lblLinhaInformativa.setForeground(Color.BLUE);
    }//GEN-LAST:event_txtIdCustomBuscarFocusGained

    private void txtIdCustomBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdCustomBuscarFocusLost
        txtIdCustomBuscar.setBackground(Color.WHITE);
        lblLinhaInformativa.setText("");
    }//GEN-LAST:event_txtIdCustomBuscarFocusLost

    private void txtIdCustomBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdCustomBuscarKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Iniciando Pesquisa no Banco de Dados");

            btnBuscarIdCustom.requestFocus();

        }
    }//GEN-LAST:event_txtIdCustomBuscarKeyPressed

    private void txtIdCustomBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdCustomBuscarKeyReleased

    }//GEN-LAST:event_txtIdCustomBuscarKeyReleased
    private void vizualizandoReciboProcesso(String id) {

        //criando um objeto de conexão do tipo ConexãoUtil
        ConexaoUtil conecta = new ConexaoUtil();

        try {//tratando os metódos abaixo dentro de um bloco try chatch

            //iremos setar o caminho de onde se encontra o arquivo xml por meio de um InputStream
            InputStream jrxmlStream = TelaProtocDocsTFD.class.getResourceAsStream("/ireport/reciboprocesso.xml");

            //JOptionPane.showMessageDialog(null, "setamos:/ireport/reciboprocesso.xml");
            //compilamos o arquivo xml capiturado acima pelo objeto do Tipo InputStream
            //e fazemos sua conpilaçao por meio do JasperCompilerManger
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            // JOptionPane.showMessageDialog(null, "Compilamos:/ireport/reciboprocesso.xml");
            //Esse relatório tem parâmetros daí criamos um HashMap 
            Map<String, Object> parametros = new HashMap<>();
            //passamos o parâmetro pro relatório ja compilado para fazer o filtro dos dados 
            parametros.put("CONDICAO_ID", id);

            //aqui preenchemos o relatório e passamos a conexao objeto conecta.getConnection()
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conecta.getConnection());
           // JOptionPane.showMessageDialog(null, "Preenchemos:/jasperPrint");

            //neste ponto já tenho meu relatório preenchido agora vamos salvar o arquivo JasperPrint 
            File file = new File("C:/ireport/reciboprocesso.jrprint");
            //  JOptionPane.showMessageDialog(null, "Salvamos:C:/ireport/reciboprocesso.jrprint");
            //agora iremos fazer uma verificação para ve se o arquivo existe ou não na pasta
            if (!file.exists()) {

                //iremos entao criá-lo
                file.createNewFile();
                JRSaver.saveObject(jasperPrint, file);

                //    JOptionPane.showMessageDialog(null, "N Existe:JRSaver.saveObject(jasperPrint,file)");
                //vamos exportar o arquivo no formato pdf
                JRPdfExporter exporterPdf = new JRPdfExporter();
                //aqui é o input que irá alimentar o arquivo pdf que no caso arqui jasperPrint
                //esse método abaixo é uma interface que encapsula o objeto jasperprint
                //preparando para exportação
                ExporterInput input = new SimpleExporterInput(jasperPrint);
                exporterPdf.setExporterInput(input);
                //agora iremos definir o caminho onde ficará o arquivo gerado
                //esse caminho é encapsulado pela classe SimpleOutputStreamExporterOutput 
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("C:/ireport/reciboProcesso.pdf")));

                //agora iremos chamar o método que gera o relatório propriamente dito 
                exporterPdf.exportReport();
                //  JOptionPane.showMessageDialog(null, "Exportamos:pdf)");

            } else {//caso contrário, ou seja, se não  existir 
                //JOptionPane.showMessageDialog(null, "Existe: Object reciboProcesso.jrprint");
                //vamos exportar o arquivo no formato pdf
                JRPdfExporter exporterPdf = new JRPdfExporter();
                //aqui é o input que irá alimentar o arquivo pdf que no caso arqui jasperPrint
                //esse método abaixo é uma interface que encapsula o objeto jasperprint
                //preparando para exportação
                ExporterInput input = new SimpleExporterInput(jasperPrint);
                exporterPdf.setExporterInput(input);
                //agora iremos definir o caminho onde ficará o arquivo gerado
                //esse caminho é encapsulado pela classe SimpleOutputStreamExporterOutput 
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("C:/ireport/reciboProcesso.pdf")));
                //JOptionPane.showMessageDialog(null, "Definimos caminho:C:/ireport/reciboProcesso.pdf");
                //agora iremos chamar o método que gera o relatório propriamente dito 
                exporterPdf.exportReport();
                // JOptionPane.showMessageDialog(null, "Exportamos:pdf)");
            }
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);

        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "vizualizandoReciboProcesso() - Camada GUI ViewGeradorDocsProtocolo");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tratamento Erro Camada GUI: " + ex.getMessage());
        }

    }

    private void vizualizandoCapaDeProcesso(String id) {

        ConexaoUtil conecta = new ConexaoUtil();
        try {

            InputStream jrxmlStream = TelaProtocDocsTFD.class.getResourceAsStream("/ireport/capaDeProcesso.xml");
            //compilamos o arquivo 
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            Map<String, Object> parametros = new HashMap<>();
            //preencher o Map de Parametros 
            parametros.put("CONDICAO_ID", id);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conecta.getConnection());

            //neste ponto já tenho meu relatório preenchido agora vamos salvar o arquivo JasperPrint 
            //C:/ireport/reciboprocesso.jrprint
            File file = new File("/ireport/capaprocesso.jrprint");

            //agora iremos fazer uma verificação para ve se o arquivo existe ou não na pasta
            if (!file.exists()) {

                //iremos entao criá-lo
                file.createNewFile();
                JRSaver.saveObject(jasperPrint, file);

                //vamos exportar o arquivo no formato pdf
                JRPdfExporter exporterPdf = new JRPdfExporter();
                //aqui é o input que irá alimentar o arquivo pdf que no caso arqui jasperPrint
                //esse método abaixo é uma interface que encapsula o objeto jasperprint
                //preparando para exportação
                ExporterInput input = new SimpleExporterInput(jasperPrint);
                exporterPdf.setExporterInput(input);
                //agora iremos definir o caminho onde ficará o arquivo gerado
                //esse caminho é encapsulado pela classe SimpleOutputStreamExporterOutput 
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("/ireport/capaProcesso.pdf")));

                //agora iremos chamar o método que gera o relatório propriamente dito 
                exporterPdf.exportReport();

            } else {//caso contrário, ou seja, se não  existir 
                //vamos exportar o arquivo no formato pdf
                JRPdfExporter exporterPdf = new JRPdfExporter();
                //aqui é o input que irá alimentar o arquivo pdf que no caso arqui jasperPrint
                //esse método abaixo é uma interface que encapsula o objeto jasperprint
                //preparando para exportação
                ExporterInput input = new SimpleExporterInput(jasperPrint);
                exporterPdf.setExporterInput(input);
                //agora iremos definir o caminho onde ficará o arquivo gerado
                //esse caminho é encapsulado pela classe SimpleOutputStreamExporterOutput 
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("/ireport/capaProcesso.pdf")));

                //agora iremos chamar o método que gera o relatório propriamente dito 
                exporterPdf.exportReport();
            }
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);

        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "VizualizandoCapaDeProcesso()");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tratamento Erro Camada GUI: " + ex.getMessage());
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barProgressProcedimentosBackEnd;
    private javax.swing.JButton btnBuscarCPF;
    private javax.swing.JButton btnBuscarIdCustom;
    private javax.swing.JComboBox cboMascaraCustomizada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblConsultarProtocolo;
    private javax.swing.JLabel lblInfoProcessosBackEnd;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JTable tabela;
    private javax.swing.JFormattedTextField txtCPFBuscar;
    private javax.swing.JFormattedTextField txtIdCustomBuscar;
    // End of variables declaration//GEN-END:variables
}
