package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.CidadeBO;
import br.com.subgerentepro.dao.CidadeDAO;
import br.com.subgerentepro.dao.FuncionarioDAO;
import br.com.subgerentepro.dto.CidadeDTO;
import br.com.subgerentepro.dto.FuncionarioDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import static br.com.subgerentepro.telas.TelaEmpresas.btnBairroBusca;
import static br.com.subgerentepro.telas.TelaEmpresas.txtCidade;
import static br.com.subgerentepro.telas.TelaEmpresas.txtCodCidade;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaProtocDocsEmpresas.txtSetorDestinoRopositorio;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DaTorres
 */
public class Frm_Protocolar_Lista_Funcionarios_Filtro extends javax.swing.JInternalFrame {
    
    FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    
    private static Frm_Protocolar_Lista_Funcionarios_Filtro instance = null;
    
    public static Frm_Protocolar_Lista_Funcionarios_Filtro getInstance() {
        
        if (instance == null) {
            
            instance = new Frm_Protocolar_Lista_Funcionarios_Filtro();
            
        }
        
        return instance;
    }
    
    public static boolean isOpen() {
        
        return instance != null;
    }
    
    public Frm_Protocolar_Lista_Funcionarios_Filtro() {
        initComponents();
        personalizacaoFrontEnd();
        txtBuscar.requestFocus();
        txtFiltroBuscaCombo.setText(txtSetorDestinoRopositorio.getText());
        pesquisaFuncionariosComFiltro();
        
    }
    
    private void personalizacaoFrontEnd() {
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/pesquisar.png")));
        txtFiltroBuscaCombo.setEnabled(false);
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
            
            ArrayList<FuncionarioDTO> list;
            
            try {
                
                String pesquisa = MetodoStaticosUtil.removerAcentosCaixAlta(txtBuscar.getText());
                String filtroDaPesquisa = MetodoStaticosUtil.removerAcentosCaixAlta(txtFiltroBuscaCombo.getText());
                
                list = (ArrayList<FuncionarioDTO>) funcionarioDAO.filtrarPesqRapidaComParametro(pesquisa, filtroDaPesquisa);
                
                Object rowData[] = new Object[3];//são 03 colunas 
                for (int i = 0; i < list.size(); i++) {
                    rowData[0] = list.get(i).getNomeDto();
                    model.addRow(rowData);
                }
                
                tabela.setModel(model);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tabela.getColumnModel().getColumn(0).setPreferredWidth(400);
                
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
        
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        
        if (recebeConexao == true) {
            
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            
            ArrayList<FuncionarioDTO> list;
            
            try {
                
                String pesquisa = MetodoStaticosUtil.removerAcentosCaixAlta(txtBuscar.getText());
                String filtroDaPesquisa = MetodoStaticosUtil.removerAcentosCaixAlta(txtFiltroBuscaCombo.getText());
                
                list = (ArrayList<FuncionarioDTO>) funcionarioDAO.filtrarPesqRapidaComParametro(pesquisa, filtroDaPesquisa);
                
                Object rowData[] = new Object[3];//são 03 colunas 
                for (int i = 0; i < list.size(); i++) {
                    
                    rowData[0] = list.get(i).getNomeDto();
                    model.addRow(rowData);
                }
                
                tabela.setModel(model);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tabela.getColumnModel().getColumn(0).setPreferredWidth(400);
                
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCabecalho = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        lblIDEstadoFiltro = new javax.swing.JLabel();
        txtFiltroBuscaCombo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
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
        painelCabecalho.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 180, 30));

        btnPesquisar.setPreferredSize(new java.awt.Dimension(32, 32));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });
        painelCabecalho.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 32, 32));

        lblIDEstadoFiltro.setText("Setor de Tramite");
        painelCabecalho.add(lblIDEstadoFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 170, 30));

        txtFiltroBuscaCombo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtFiltroBuscaCombo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFiltroBuscaCombo.setPreferredSize(new java.awt.Dimension(6, 30));
        txtFiltroBuscaCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroBuscaComboActionPerformed(evt);
            }
        });
        painelCabecalho.add(txtFiltroBuscaCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 170, 30));

        jLabel1.setText("Pesquisar Funcionários:");
        painelCabecalho.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FUNCIONÁRIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
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
            tabela.getColumnModel().getColumn(0).setResizable(false);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(280);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                    .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        int linha = tabela.getSelectedRow();
        
        txtCodCidade.setText(tabela.getValueAt(linha, 0).toString());
        txtCidade.setText(tabela.getValueAt(linha, 1).toString());
        btnBairroBusca.requestFocus();
        this.dispose();

    }//GEN-LAST:event_tabelaMouseClicked

    private void txtFiltroBuscaComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroBuscaComboActionPerformed
        

    }//GEN-LAST:event_txtFiltroBuscaComboActionPerformed
    private void pesquisaFuncionariosComFiltro() {

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

        
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

        
        pesquisaFuncionariosComFiltro();
        
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            
        }
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        txtBuscar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        txtBuscar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_txtBuscarFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIDEstadoFiltro;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtFiltroBuscaCombo;
    // End of variables declaration//GEN-END:variables

}
