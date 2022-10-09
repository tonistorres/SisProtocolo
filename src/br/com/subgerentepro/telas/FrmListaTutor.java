/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.TutorBO;
import br.com.subgerentepro.bo.TutorBO;
import br.com.subgerentepro.dao.TutorDAO;
import br.com.subgerentepro.dao.TutorDAO;
import br.com.subgerentepro.dto.TutorDTO;
import br.com.subgerentepro.dto.TutorDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import static br.com.subgerentepro.telas.TelaBancoEmpresa.cbBanco;
import static br.com.subgerentepro.telas.TelaBancoTutor.btnBuscarPaciente;
import static br.com.subgerentepro.telas.TelaBancoTutor.txtCodTutor;
import static br.com.subgerentepro.telas.TelaBancoTutor.txtFavorecido;
import static br.com.subgerentepro.telas.TelaBancoTutor.txtTutor;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import br.com.subgerentepro.util.PINTAR_TABELA_LISTA_DE_TUTORES;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DaTorres
 */
public class FrmListaTutor extends javax.swing.JInternalFrame {

    TutorDTO tutorDTO = new TutorDTO();
    TutorDAO tutorDAO = new TutorDAO();
    TutorBO tutorBO = new TutorBO();

    Font f = new Font("Tahoma", Font.BOLD, 12);
    /**
     * Código Mestre Chimura
     */
    private static FrmListaTutor instance = null;

    public static FrmListaTutor getInstance() {

        if (instance == null) {

            instance = new FrmListaTutor();

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

    public FrmListaTutor() {
        initComponents();
        addRowJTable();
        aoCarregarForm();
          //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tabela.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 
        //https://www.youtube.com/watch?v=jPfKFm2Yfow
        tabela.setDefaultRenderer(Object.class, new PINTAR_TABELA_LISTA_DE_TUTORES());
    }

    public void aoCarregarForm() {
        progressBarraPesquisa.setIndeterminate(true);
        lblLinhaInformativa.setText("Digite o Tutor em Seguida pressione ENTER");
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.BLUE);

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<TutorDTO> list;

        try {

            list = (ArrayList<TutorDTO>) tutorDAO.listarTodos();

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

        ArrayList<TutorDTO> list;

        try {

            list = (ArrayList<TutorDTO>) tutorDAO.filtrarUsuarioPesqRapida(pesquisar);

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

            //INFORMAÇÃO BARRA INFORMATIVA
            lblLinhaInformativa.setText("Pesquisa Efetuada Com Sucesso!");
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setFont(f);
            txtBuscar.requestFocus();

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
        btnCadastrarTutor = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        lblLinhaInformativa = new javax.swing.JLabel();
        lblImagemNuvemBackEnd = new javax.swing.JLabel();
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
        painelCabecalho.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 160, 30));

        btnCadastrarTutor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuFuncionario.png"))); // NOI18N
        btnCadastrarTutor.setToolTipText("Cadastrar Tutor");
        btnCadastrarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarTutorActionPerformed(evt);
            }
        });
        painelCabecalho.add(btnCadastrarTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 32, 32));

        btnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/refresh.png"))); // NOI18N
        btnAtualizar.setToolTipText("Atualizar Lista Cadastro Tutor");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });
        painelCabecalho.add(btnAtualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 32, 32));

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesquisarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnPesquisarFocusLost(evt);
            }
        });
        btnPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPesquisarKeyPressed(evt);
            }
        });
        painelCabecalho.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 32, 32));

        lblLinhaInformativa.setText("Linha Informativa");
        painelCabecalho.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 280, 20));

        lblImagemNuvemBackEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        painelCabecalho.add(lblImagemNuvemBackEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 60, 40));
        painelCabecalho.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 220, -1));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "TUTOR"
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

        if (evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_ENTER) {
            btnPesquisar.requestFocus();
            lblLinhaInformativa.setText("Iniciando a Pesquisa Banco Dados");
            lblLinhaInformativa.setForeground(Color.ORANGE);
            lblLinhaInformativa.setFont(f);
        }


    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        int linha = tabela.getSelectedRow();

        txtCodTutor.setText(tabela.getValueAt(linha, 0).toString());
        txtTutor.setText(tabela.getValueAt(linha, 1).toString());
        txtFavorecido.setText(tabela.getValueAt(linha, 1).toString());
        btnBuscarPaciente.setEnabled(true);

        this.dispose();

    }//GEN-LAST:event_tabelaMouseClicked

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        txtBuscar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        txtBuscar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_txtBuscarFocusLost
    br.com.subgerentepro.telas.TelaTutorTFD formTutor;
    private void btnCadastrarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarTutorActionPerformed
        if (estaFechado(formTutor)) {
            formTutor = new TelaTutorTFD();
            DeskTop.add(formTutor).setLocation(1, 5);
            formTutor.setTitle("Cadastro de Tutores");
            formTutor.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formTutor.toFront();
            formTutor.show();

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

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        refresh();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        btnPesquisar.setBackground(Color.YELLOW);

           
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


    }//GEN-LAST:event_btnPesquisarFocusGained

    private void btnPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPesquisarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPesquisarKeyPressed

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnCadastrarTutor;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImagemNuvemBackEnd;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
