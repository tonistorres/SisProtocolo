package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.CidadeBO;
import br.com.subgerentepro.dao.CidadeDAO;
import br.com.subgerentepro.dto.CidadeDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;

import static br.com.subgerentepro.telas.TelaTutorTFD.txtidEstadoTutor;


import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaTutorTFD.txtCidadeTutor;
import static br.com.subgerentepro.telas.TelaTutorTFD.txtidCidadeTutor;


import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DaTorres
 */
public class FrmListaCidadesTutor extends javax.swing.JInternalFrame {

    CidadeDTO cidadeDTO = new CidadeDTO();
    CidadeDAO cidadeDAO = new CidadeDAO();
    CidadeBO cidadeBO = new CidadeBO();
    /**
     * Código Mestre Chimura
     */
    private static FrmListaCidadesTutor instance = null;

    public static FrmListaCidadesTutor getInstance() {

        if (instance == null) {

            instance = new FrmListaCidadesTutor();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public FrmListaCidadesTutor() {
        initComponents();
         progressBarraPesquisa.setIndeterminate(true);
        /**
         * Ao carregar o formulario buscar o valor que se encontra no
         * formulalrio TelaEmpresa do Campo txtCodEstado que deverá esta
         * definido como public e estatico e puxar para o campo no formulario em
         * uso no caso FrmListaCidades para o campo txtFiltroIDEstado
         */
        txtFiltroIDEstado.setText(txtidEstadoTutor.getText());
        txtFiltroIDEstado.setEnabled(false);

        
        buscarCidade();
        txtBuscar.requestFocus();
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            try {

                int numeroLinhas = tabela.getRowCount();

                if (numeroLinhas > 0) {

                    while (tabela.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tabela.getModel()).removeRow(0);

                    }

                    CampoPesquisar();

                } else {
                    addRowJTable();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }
        
        //setar 
        setarCidade();
        

    }

    private void setarCidade(){
    
          ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            this.txtBuscar.setText("ALTO ALEGRE DO MARANHAO");
            CampoPesquisar();
            
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }

    }
    
    
    private void desabilitarCampos() {
        txtBuscar.setEnabled(false);
    }

    private void desabilitarTodosBotoes() {
        btnPesquisar.setEnabled(false);
    }

    public void addRowJTable() {

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            ArrayList<CidadeDTO> list;

            try {

                int filtro = Integer.parseInt(txtFiltroIDEstado.getText());
                String pesquisa = txtBuscar.getText();

                list = (ArrayList<CidadeDTO>) cidadeDAO.filtrarCidadesPesqRapida(pesquisa, filtro);

                Object rowData[] = new Object[3];//são 03 colunas 
                for (int i = 0; i < list.size(); i++) {
                    rowData[0] = list.get(i).getCodCidadeDto();
                    rowData[1] = list.get(i).getNomeCidadeDto();
                    rowData[2] = list.get(i).getSiglaEstadoRecuperarTable();
                    model.addRow(rowData);
                }

                tabela.setModel(model);

                //posição que se encontra o estado do maranhão dentro do banco de dados para ser selecionado 
                tabela.setRowSelectionInterval(6, 6);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
                tabela.getColumnModel().getColumn(1).setPreferredWidth(380);
                tabela.getColumnModel().getColumn(2).setPreferredWidth(100);

            } catch (PersistenciaException ex) {
                JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FrmListaCidades\n"
                        + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                        + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    private void CampoPesquisar() {

        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(txtBuscar.getText());
        int filtroPesquisa = Integer.parseInt(txtFiltroIDEstado.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<CidadeDTO> list;

        try {

            list = (ArrayList<CidadeDTO>) cidadeDAO.filtrarCidadesPesqRapida(pesquisar, filtroPesquisa);

            Object rowData[] = new Object[3];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getCodCidadeDto();
                rowData[1] = list.get(i).getNomeCidadeDto().toString();
                rowData[2] = list.get(i).getSiglaEstadoRecuperarTable().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(100);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormListaCidades \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCabecalho = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        lblIDEstadoFiltro = new javax.swing.JLabel();
        txtFiltroIDEstado = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

        setClosable(true);

        painelCabecalho.setBackground(new java.awt.Color(255, 255, 255));
        painelCabecalho.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "OPÇÕES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        painelCabecalho.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscar.setBackground(new java.awt.Color(255, 255, 204));
        txtBuscar.setOpaque(false);
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        painelCabecalho.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 180, 30));

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });
        painelCabecalho.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 32, 32));

        lblIDEstadoFiltro.setText("FiltroID");
        painelCabecalho.add(lblIDEstadoFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        txtFiltroIDEstado.setBackground(new java.awt.Color(255, 255, 153));
        txtFiltroIDEstado.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        txtFiltroIDEstado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelCabecalho.add(txtFiltroIDEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 30, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        painelCabecalho.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 60, 40));
        painelCabecalho.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 60, -1));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CIDADES", "UF"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(80);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(0).setMaxWidth(80);
            tabela.getColumnModel().getColumn(1).setMinWidth(280);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(1).setMaxWidth(280);
            tabela.getColumnModel().getColumn(2).setMinWidth(100);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(painelCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased


    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        int linha = tabela.getSelectedRow();

        txtidCidadeTutor.setText(tabela.getValueAt(linha, 0).toString());
        txtCidadeTutor.setText(tabela.getValueAt(linha, 1).toString());

        this.dispose();

    }//GEN-LAST:event_tabelaMouseClicked

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        txtBuscar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        txtBuscar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_txtBuscarFocusLost

    private void buscarCidade() {

        txtBuscar.requestFocus();
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            try {

                int numeroLinhas = tabela.getRowCount();

                if (numeroLinhas > 0) {

                    while (tabela.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tabela.getModel()).removeRow(0);

                    }

                    CampoPesquisar();

                } else {
                    addRowJTable();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }
    }

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        buscarCidade();
    }//GEN-LAST:event_btnPesquisarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIDEstadoFiltro;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtFiltroIDEstado;
    // End of variables declaration//GEN-END:variables
}
