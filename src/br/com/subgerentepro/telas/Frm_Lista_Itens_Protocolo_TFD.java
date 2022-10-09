package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.ItemProtocoloBO;
import br.com.subgerentepro.bo.ItemProtocoloBO;
import br.com.subgerentepro.dao.ItemProtocoloDAO;
import br.com.subgerentepro.dao.ItemProtocoloDAO;
import br.com.subgerentepro.dto.ItemProtocoloDTO;
import br.com.subgerentepro.dto.ItemProtocoloDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import static br.com.subgerentepro.telas.TelaBancoEmpresa.cbBanco;
import static br.com.subgerentepro.telas.TelaBancoEmpresa.txtCodEmpresa;
import static br.com.subgerentepro.telas.TelaBancoEmpresa.txtEmpresa;
import static br.com.subgerentepro.telas.TelaProtocDocsEmpresas.btnSetorDestino;
import static br.com.subgerentepro.telas.TelaProtocDocsEmpresas.txtInteressado;
import static br.com.subgerentepro.telas.TelaProtocDocsTFD.cbComoSeraViagem;
import static br.com.subgerentepro.telas.TelaProtocDocsTFD.txtAreaDescreve;
import static br.com.subgerentepro.telas.TelaProtocDocsTFD.txtCodItemProtocolo;
import static br.com.subgerentepro.telas.TelaProtocDocsTFD.txtItemDoProtocolo;
import static br.com.subgerentepro.telas.TelaProtocDocsTFD.txtQuantidade;
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
public class Frm_Lista_Itens_Protocolo_TFD extends javax.swing.JInternalFrame {

    ItemProtocoloDTO itemProtocoloDTO = new ItemProtocoloDTO();
    ItemProtocoloDAO itemProtocoloDAO = new ItemProtocoloDAO();
    ItemProtocoloBO itemProtocoloBO = new ItemProtocoloBO();

    /**
     * Código Mestre Chimura
     */
    private static Frm_Lista_Itens_Protocolo_TFD instance = null;

    public static Frm_Lista_Itens_Protocolo_TFD getInstance() {

        if (instance == null) {

            instance = new Frm_Lista_Itens_Protocolo_TFD();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public Frm_Lista_Itens_Protocolo_TFD() {
        initComponents();
        pesquisar();

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<ItemProtocoloDTO> list;

        try {

            list = (ArrayList<ItemProtocoloDTO>) itemProtocoloDAO.listarTodos();

            Object rowData[] = new Object[2];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void CampoPesquisar() {

        String pesquisar = txtBuscar.getText().toUpperCase();

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<ItemProtocoloDTO> list;

        try {

            list = (ArrayList<ItemProtocoloDTO>) itemProtocoloDAO.filtrarUsuarioPesqRapida(pesquisar);

            Object rowData[] = new Object[2];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);

            Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.ORANGE);
            lblLinhaInformativa.setText("Pesquisa Terminada.");
            txtBuscar.requestFocus();
            txtBuscar.setBackground(Color.CYAN);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
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
        lblLinhaInformativa = new javax.swing.JLabel();
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
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        painelCabecalho.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 270, 30));

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesquisar.setPreferredSize(new java.awt.Dimension(32, 32));
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
        painelCabecalho.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 32, 32));

        lblLinhaInformativa.setText("Linha Info:");
        painelCabecalho.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 340, 20));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ITEM PROTOCOLO"
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
            tabela.getColumnModel().getColumn(0).setMinWidth(50);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(0).setMaxWidth(50);
            tabela.getColumnModel().getColumn(1).setMinWidth(300);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(300);
            tabela.getColumnModel().getColumn(1).setMaxWidth(300);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void pesquisar() {
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
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        int linha = tabela.getSelectedRow();

       // String nomePaciente = txtInteressado.getText();

        String DiariaTFD = "";
        if (cbComoSeraViagem.getSelectedItem().equals("PACIENTE")) {

            DiariaTFD = "R$ 150,00";
        }

        if (cbComoSeraViagem.getSelectedItem().equals("PACIENTE + ACOMPANHANTE")) {
            DiariaTFD = "R$ 300,00";
        }

        txtItemDoProtocolo.setText(tabela.getValueAt(linha, 1).toString());
        txtCodItemProtocolo.setText("41");
        txtItemDoProtocolo.setText("TFD(TRATAMENTO FORA DO DOMICILIO)");
        txtQuantidade.setText("1");

        Font fTxtDescreve = new Font("Tahoma", Font.BOLD, 9);
        txtAreaDescreve.setFont(fTxtDescreve);
        txtAreaDescreve.setForeground(Color.BLUE);
        txtAreaDescreve.setText("UM TFD NO VALOR DE " + DiariaTFD + " DO(A) SERVIDOR(A)\n ______________PARA O(A)  \nPARCIENTE " + "___________________.");

        txtQuantidade.setEnabled(true);
        txtAreaDescreve.setEnabled(true);

        this.dispose();

    }//GEN-LAST:event_tabelaMouseClicked

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        txtBuscar.setBackground(Color.YELLOW);


    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        txtBuscar.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtBuscarFocusLost

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            pesquisar();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void acaoPesquisar() {

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            pesquisar();

        } else {
            addRowJTable();
        }

    }


    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained

        btnPesquisar.setBackground(Color.YELLOW);

        if (txtBuscar.getText().toString().trim().equals("")) {
            //        JOptionPane.showMessageDialog(this, "" + "\n Campo Pesquisa NULO", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Campo de Busca não Pode Conter Valor Nulo.");

            txtBuscar.setBackground(Color.CYAN);
            txtBuscar.setText("Funcionário?  :~( ");
            txtBuscar.requestFocus();
        }

        if (!txtBuscar.getText().toString().trim().equals("")) {
            acaoPesquisar();
        }


    }//GEN-LAST:event_btnPesquisarFocusGained

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {
            Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Pesquisando: Espere Comunicando MySqL.");
            btnPesquisar.requestFocus();

        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void btnPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPesquisarKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
            //  JOptionPane.showMessageDialog(this, "flag: " + flag);
            ConexaoUtil conecta = new ConexaoUtil();
            boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
            if (recebeConexao == true) {

                pesquisar();

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            }

        }
    }//GEN-LAST:event_btnPesquisarKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
