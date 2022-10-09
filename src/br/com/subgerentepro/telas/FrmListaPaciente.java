package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.PacienteBO;
import br.com.subgerentepro.dao.PacienteDAO;
import br.com.subgerentepro.dto.PacienteDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import static br.com.subgerentepro.telas.TelaBancoTutor.lblCPFPacienteRepositorio;
import static br.com.subgerentepro.telas.TelaBancoTutor.txtCodTFD;
import static br.com.subgerentepro.telas.TelaBancoTutor.txtPaciente;
import static br.com.subgerentepro.telas.TelaTFD.txtBuscar;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DaTorres
 */
public class FrmListaPaciente extends javax.swing.JInternalFrame {

    PacienteDTO pacienteDTO = new PacienteDTO();
    PacienteDAO pacienteDAO = new PacienteDAO();
    PacienteBO pacienteBO = new PacienteBO();

    /**
     * Código Mestre Chimura
     */
    private static FrmListaPaciente instance = null;

    public static FrmListaPaciente getInstance() {

        if (instance == null) {

            instance = new FrmListaPaciente();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    //Hugo vasconcelos Aula 25 - verificando Janelas Abertas
    //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555436?start=0
    //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555438?start=0
    //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555444?start=0
    //agroa criamos o método que irá fazer a verificaçao e passamos como parâmetros um obj do tipo Object 
    public boolean estaFechado(Object obj) {

        /**
         * Criei um objeto chamado ativo do tipo JInternalFrame que está
         * inicializado como um array (array é um objeto que guarda vários
         * elementos detro) Pergunta-se: quais são os objetos todas as janelas
         * que são carregadas pelo objeto carregador. Bem o objeto ativo irá
         * guardar todas as janelas que estão sendo abertas no sistema
         */
        JInternalFrame[] ativo = DeskTop.getAllFrames();
        boolean fechado = true;
        int cont = 0;

        while (cont < ativo.length && fechado) {
            if (ativo[cont] == obj) {
                fechado = false;

            }
            cont++;
        }
        return fechado;
    }

    public FrmListaPaciente() {
        initComponents();
        addRowJTable();
        progressBarraPesquisa.setIndeterminate(true);
    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<PacienteDTO> list;

        try {

            list = (ArrayList<PacienteDTO>) pacienteDAO.listarTodos();

            Object rowData[] = new Object[3];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdPacienteDto();
                rowData[1] = list.get(i).getNomePacienteDto().toString();
                rowData[2] = list.get(i).getCpfPacienteDto().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void CampoPesquisar() {

        String pesquisar = txtBuscarFrmLista.getText().toUpperCase();

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<PacienteDTO> list;

        try {

            list = (ArrayList<PacienteDTO>) pacienteDAO.filtrarUsuarioPesqRapida(pesquisar);

            Object rowData[] = new Object[3];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdPacienteDto();
                rowData[1] = list.get(i).getNomePacienteDto().toString();
                rowData[2] = list.get(i).getCpfPacienteDto().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(120);

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
        txtBuscarFrmLista = new javax.swing.JTextField();
        codigoL1 = new javax.swing.JLabel();
        btnCadastrarTutor = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

        setClosable(true);

        painelCabecalho.setBackground(new java.awt.Color(255, 255, 255));
        painelCabecalho.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "OPÇÕES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        painelCabecalho.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscarFrmLista.setBackground(new java.awt.Color(255, 255, 204));
        txtBuscarFrmLista.setOpaque(false);
        txtBuscarFrmLista.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFrmListaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFrmListaFocusLost(evt);
            }
        });
        txtBuscarFrmLista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarFrmListaKeyReleased(evt);
            }
        });
        painelCabecalho.add(txtBuscarFrmLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 180, 30));

        codigoL1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        codigoL1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/fundo/buscarL.png"))); // NOI18N
        painelCabecalho.add(codigoL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 250, 52));

        btnCadastrarTutor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png"))); // NOI18N
        btnCadastrarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarTutorActionPerformed(evt);
            }
        });
        painelCabecalho.add(btnCadastrarTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 45, 45));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Paciente");
        painelCabecalho.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 50, -1));

        jLabel2.setText("Atualizar");
        painelCabecalho.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/refresh.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        painelCabecalho.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 45, 45));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        painelCabecalho.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 60, 40));
        painelCabecalho.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 60, -1));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "TUTOR", "CPF"
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
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarFrmListaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarFrmListaKeyReleased
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
    }//GEN-LAST:event_txtBuscarFrmListaKeyReleased

    br.com.subgerentepro.telas.TelaTFD formTFD;

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        int linha = tabela.getSelectedRow();

        if (!tabela.getValueAt(linha, 2).toString().isEmpty()) {
            txtCodTFD.setText(tabela.getValueAt(linha, 0).toString());
            txtPaciente.setText(tabela.getValueAt(linha, 1).toString());
            lblCPFPacienteRepositorio.setText(tabela.getValueAt(linha, 2).toString());
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Paciente CPF Nulo!\n "
                    + "É obrigatório Acrescentar o CPF\n"
                    + "no Cadastro de Paciente. ");
            if (estaFechado(formTFD)) {
                formTFD = new TelaTFD();
                DeskTop.add(formTFD).setLocation(8, 40);
                formTFD.setTitle("Paciente TFD");
                formTFD.show();

                txtBuscar.requestFocus();
                txtBuscar.setBackground(Color.YELLOW);
                txtBuscar.setText(tabela.getValueAt(linha, 1).toString());

            } else {
                //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
                formTFD.toFront();
                formTFD.show();

            }

        }
    }//GEN-LAST:event_tabelaMouseClicked

    private void txtBuscarFrmListaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFrmListaFocusGained
        txtBuscarFrmLista.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_txtBuscarFrmListaFocusGained

    private void txtBuscarFrmListaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFrmListaFocusLost
        txtBuscarFrmLista.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_txtBuscarFrmListaFocusLost
    br.com.subgerentepro.telas.TelaTFD formPaciente;
    private void btnCadastrarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarTutorActionPerformed
        if (estaFechado(formPaciente)) {
            formPaciente = new TelaTFD();
            DeskTop.add(formPaciente).setLocation(1, 5);
            formPaciente.setTitle("Bairros");
            formPaciente.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formPaciente.toFront();
            formPaciente.show();

        }
    }//GEN-LAST:event_btnCadastrarTutorActionPerformed
    private void refresh() {

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            try {

                //aqui temos uma variável que contará por meio do método nativo getRowCount o número 
                // de linhas contidos na tabela inicialmente 
                int numeroLinhas = tabela.getRowCount();

                // se número de linhas maior que zero 
                if (numeroLinhas > 0) {

                    while (tabela.getModel().getRowCount() > 0) {

                        //se o número de linhas for maior que zero será removidas todas as linhas 
                        // do DefaultTableModel, ou seja, do modelo da tabela inicial por meio do 
                        //método nativo removeRow(0)a partir da primeira posição do vetor no caso zero
                        ((DefaultTableModel) tabela.getModel()).removeRow(0);

                    }

                    //depois de limpa a tabela caso hara algo contido nelas então entraremos no método CampoPesquisa()
                    CampoPesquisar();

                } else {
                    addRowJTable();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            // desabilitarCampos();
            // desabilitarTodosBotoes();
        }

    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        refresh();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrarTutor;
    private javax.swing.JLabel codigoL1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBuscarFrmLista;
    // End of variables declaration//GEN-END:variables
}
