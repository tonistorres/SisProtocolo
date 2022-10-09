package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.EmpresaBO;
import br.com.subgerentepro.bo.EstadoBO;
import br.com.subgerentepro.dao.EmpresaDAO;
import br.com.subgerentepro.dao.EstadoDAO;
import br.com.subgerentepro.dto.EmpresaDTO;
import br.com.subgerentepro.dto.EstadoDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import static br.com.subgerentepro.telas.TelaBancoEmpresa.cbBanco;
import static br.com.subgerentepro.telas.TelaBancoEmpresa.txtCodEmpresa;
import static br.com.subgerentepro.telas.TelaBancoEmpresa.txtEmpresa;
import static br.com.subgerentepro.telas.TelaEmpresas.btnCidadeBusca;
import static br.com.subgerentepro.telas.TelaEmpresas.txtCodEstado;
import static br.com.subgerentepro.telas.TelaEmpresas.txtEstado;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DaTorres
 */
public class FrmListaEstados extends javax.swing.JInternalFrame {
    
    EstadoDTO estadoDTO = new EstadoDTO();
    EstadoDAO estadoDAO = new EstadoDAO();
    EstadoBO estadoBO = new EstadoBO();
    /**
     * Código Mestre Chimura
     */
    private static FrmListaEstados instance = null;
    
    public static FrmListaEstados getInstance() {
        
        if (instance == null) {
            
            instance = new FrmListaEstados();
            
        }
        
        return instance;
    }
    
    public static boolean isOpen() {
        
        return instance != null;
    }
    
    public FrmListaEstados() {
        initComponents();
        buscarEstados();
        txtBuscar.requestFocus();
        
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
            
            ArrayList<EstadoDTO> list;
            
            try {
                
                list = (ArrayList<EstadoDTO>) estadoDAO.listarTodos();
                
                Object rowData[] = new Object[2];//são 04 colunas 
                for (int i = 0; i < list.size(); i++) {
                    rowData[0] = list.get(i).getIdEtadoDto();
                    rowData[1] = list.get(i).getNomeEstadoDto().toString();
                    model.addRow(rowData);
                }
                
                tabela.setModel(model);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
                tabela.getColumnModel().getColumn(1).setPreferredWidth(380);
                
                progressBarraPesquisa.setIndeterminate(false);
                progressBarraPesquisa.setIndeterminate(closable);
            } catch (PersistenciaException ex) {
                JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FrmListaEstados \n"
                        + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                        + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
                
                progressBarraPesquisa.setIndeterminate(false);
                progressBarraPesquisa.setIndeterminate(closable);
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
        
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        
        ArrayList<EstadoDTO> list;
        
        try {
            
            list = (ArrayList<EstadoDTO>) estadoDAO.filtrarEstadosPesqRapida(pesquisar);
            
            Object rowData[] = new Object[2];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdEtadoDto();
                rowData[1] = list.get(i).getNomeEstadoDto();
                model.addRow(rowData);
            }
            
            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);
            
            progressBarraPesquisa.setIndeterminate(false);
            progressBarraPesquisa.setIndeterminate(closable);
            
            personalizandoBarraInfoPesquisaTermino();
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormListaEstados \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
            progressBarraPesquisa.setIndeterminate(false);
            progressBarraPesquisa.setIndeterminate(closable);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCabecalho = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        lblLinhaInformativa = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

        setClosable(true);

        painelCabecalho.setBackground(new java.awt.Color(255, 255, 255));
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
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        painelCabecalho.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 220, 30));

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesquisarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnPesquisarFocusLost(evt);
            }
        });
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });
        btnPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPesquisarKeyPressed(evt);
            }
        });
        painelCabecalho.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 32, 30));
        painelCabecalho.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 90, -1));

        lblLinhaInformativa.setText("Linha Informativa");
        painelCabecalho.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 260, -1));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "ESTADO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
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
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        int linha = tabela.getSelectedRow();
        
        txtCodEstado.setText(tabela.getValueAt(linha, 0).toString());
        txtEstado.setText(tabela.getValueAt(linha, 1).toString());
        btnCidadeBusca.setEnabled(true);
        btnCidadeBusca.requestFocus();
           
        this.dispose();

    }//GEN-LAST:event_tabelaMouseClicked
   private void aoReceberFocoBtnPesquisar(){
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Pressione -->[ENTER]<-- Procurar -->[MYSQL]<--");
    
    }
    
    private void aoReceberFocoTxtBuscar() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Digite o Registro Procurado -->[ENTER]<--");
    }
    
    
    
    private void personalizandoBarraInfoPesquisaTermino() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setForeground(Color.ORANGE);
        lblLinhaInformativa.setText("Pesquisa Terminada.");
        txtBuscar.requestFocus();
        txtBuscar.setBackground(Color.CYAN);
    }
    
    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        txtBuscar.setBackground(Color.YELLOW);
        txtBuscar.setToolTipText("Digite o Registro Procurado");
        aoReceberFocoTxtBuscar();
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        txtBuscar.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtBuscarFocusLost
    
    private void acaoBotaoPesquisar() {
        
        progressBarraPesquisa.setIndeterminate(true);
        
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
        
    }
    
    private void buscarEstados() {
        txtBuscar.requestFocus();
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {
            
            acaoBotaoPesquisar();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            
        }
        
    }

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        
        buscarEstados();

    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        
        if (evt.getKeyCode() == evt.VK_ENTER) {
            
            txtBuscar.setBackground(Color.YELLOW);
           
            btnPesquisar.requestFocus();
            btnPesquisar.setBackground(Color.YELLOW);
            txtBuscar.setBackground(Color.WHITE);
        }
        

    }//GEN-LAST:event_txtBuscarKeyPressed

    private void btnPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPesquisarKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            buscarEstados();
        }        
    }//GEN-LAST:event_btnPesquisarKeyPressed

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
aoReceberFocoBtnPesquisar();
    }//GEN-LAST:event_btnPesquisarFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
