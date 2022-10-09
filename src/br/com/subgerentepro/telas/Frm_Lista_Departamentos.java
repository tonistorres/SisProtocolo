package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.DepartamentoBO;
import br.com.subgerentepro.dao.DepartamentoDAO;
import br.com.subgerentepro.dto.DepartamentoDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaSetorTramiteInterno.txtId;
import static br.com.subgerentepro.telas.TelaSetorTramiteInterno.txtNome;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class Frm_Lista_Departamentos extends javax.swing.JInternalFrame {

    DepartamentoDTO departamentoDTO = new DepartamentoDTO();
    DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    DepartamentoBO departamentoBO = new DepartamentoBO();

    public Frm_Lista_Departamentos() throws PersistenciaException {
        initComponents();

        addRowJTable();

    }

    public void addRowJTable() throws PersistenciaException {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            ArrayList<DepartamentoDTO> list;

            list = (ArrayList<DepartamentoDTO>) departamentoDAO.listarTodos();
            Object rowData[] = new Object[2];
            for (int i = 0; i < list.size(); i++) {

                System.out.println("lista:" + list.get(i).getNomeDto());
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();

                //  System.out.println("ID:" + list.get(i).getIdDto() + "NSUS:" + list.get(i).getNumeroCartaoSusDto() + "Paciente:" + list.get(i).getNomeDto() + "CPF:" + list.get(i).getCpfDto());
                model.addRow(rowData);
            }
            tabela.setModel(model);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(33);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(260);

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        txtPesquisar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelPrincipal.setBackground(java.awt.Color.white);
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DEPARTAMENTO"
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
            tabela.getColumnModel().getColumn(0).setMinWidth(40);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabela.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        painelPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 250, 200));
        painelPrincipal.add(txtPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 210, 30));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnBuscar.setPreferredSize(new java.awt.Dimension(32, 35));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        painelPrincipal.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 32, 35));

        getContentPane().add(painelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

        //com o método getSelectedRow capturamos a linha selecionada 
        int linha = tabela.getSelectedRow();
        /**
         * nas duas linhas subsequentes capturamos o valor que se encontra na
         * linha [zero- primeira posição do vetor ] e enviamos para o segundo
         * formulário para o campo txtidEstado em seguida repetimos o processo
         * com o que se encontra na posição [um]
         */
        //txtidEstado.setText(tabela.getValueAt(linha, 0).toString());
//        txtId.setText(tabela.getValueAt(linha, 0).toString());
        txtNome.setText(tabela.getValueAt(linha, 1).toString());

        

        
        this.dispose();

    }//GEN-LAST:event_tabelaMouseClicked

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            acaoPesquisar();
        } catch (PersistenciaException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada GUI:" + ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed
    private void pesquisarUsuario() {
        String pesquisarUsuario = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisar.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<DepartamentoDTO> list;

        try {

            list = (ArrayList<DepartamentoDTO>) departamentoDAO.filtrarUsuarioPesqRapida(pesquisarUsuario);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[3];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(45);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void acaoBotaoPesquisar() throws PersistenciaException {

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

                pesquisarUsuario();

            } else {
                addRowJTable();
            }

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }

    }

    private void acaoPesquisar() throws PersistenciaException {
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
