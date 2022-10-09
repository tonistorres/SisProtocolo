package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.BairroBO;
import br.com.subgerentepro.bo.CidadeBO;
import br.com.subgerentepro.dao.BairroDAO;
import br.com.subgerentepro.dao.CidadeDAO;
import br.com.subgerentepro.dto.BairroDTO;
import br.com.subgerentepro.dto.CidadeDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import static br.com.subgerentepro.telas.TelaEmpresas.btnBairroBusca;
import static br.com.subgerentepro.telas.TelaEmpresas.txtBairro;
import static br.com.subgerentepro.telas.TelaEmpresas.txtCidade;
import static br.com.subgerentepro.telas.TelaEmpresas.txtCodCidade;
import static br.com.subgerentepro.telas.TelaEmpresas.txtEndereco;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaEmpresas.txtCelular;
import static br.com.subgerentepro.telas.TelaEmpresas.txtComplemento;
import static br.com.subgerentepro.telas.TelaEmpresas.txtContato;
import static br.com.subgerentepro.telas.TelaEmpresas.txtFoneComercial;
import static br.com.subgerentepro.telas.TelaEmpresas.txtNumero;
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
public class FrmListaBairros extends javax.swing.JInternalFrame {

    BairroDTO bairroDTO = new BairroDTO();
    BairroDAO bairroDAO = new BairroDAO();
    BairroBO bairroBO = new BairroBO();
    /**
     * Código Mestre Chimura
     */
    private static FrmListaBairros instance = null;

    public static FrmListaBairros getInstance() {

        if (instance == null) {

            instance = new FrmListaBairros();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public FrmListaBairros() {
        initComponents();
        txtBuscar.requestFocus();
        /**
         * Ao carregar o formulario buscar o valor que se encontra no
         * formulalrio TelaEmpresa do Campo txtCodEstado que deverá esta
         * definido como public e estatico e puxar para o campo no formulario em
         * uso no caso FrmListaCidades para o campo txtFiltroIDEstado
         */
        txtFiltroIDCidade.setText(TelaEmpresas.txtCodCidade.getText());
        txtFiltroIDCidade.setEnabled(false);

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
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<BairroDTO> list;

        //Dentro do metodo CampoPesquisar capturamos inicialmente dois parametros relacionados 
        //ao formulario(GUI) que servirão como filtro pra fazer a busca na tabela contida 
        //no banco de dados nas nuvens o txtBuscar(onde digitamos o nome da cidade)a ser 
        //encontrada no banco de dados o filtroCidade(como o proprio nome diz é onde iremos filtrar somente as cidades
        //pertencente ao estado identificado no filtro ) ou seja, em vez de pesquisar em todas as cidades pertecentes
        // a todos estados brasileiros iremos pesquisar numa lista que contem todoas as cidades pertencentes a um determinado
        //estado brasileiro 
        String pesquisar = txtBuscar.getText().toUpperCase();
        int filtroCiddade = Integer.parseInt(txtFiltroIDCidade.getText());

        try {

            list = (ArrayList<BairroDTO>) bairroDAO.filtrarBairrosPesqRapidaComFiltroCidade(pesquisar, filtroCiddade);

            Object rowData[] = new Object[3];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdBairroDto();
                rowData[1] = list.get(i).getNomeBairroDto().toString();
                rowData[2] = list.get(i).getNomeCidadeRecuperarDto().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(380);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FrmListaBairros\n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    /**
     * O método CampoPesquisar é um Método sem retorno
     */
    private void CampoPesquisar() {
        //Dentro do metodo CampoPesquisar capturamos inicialmente dois parametros relacionados 
        //ao formulario(GUI) que servirão como filtro pra fazer a busca na tabela contida 
        //no banco de dados nas nuvens o txtBuscar(onde digitamos o nome da cidade)a ser 
        //encontrada no banco de dados o filtroCidade(como o proprio nome diz é onde iremos filtrar somente as cidades
        //pertencente ao estado identificado no filtro ) ou seja, em vez de pesquisar em todas as cidades pertecentes
        // a todos estados brasileiros iremos pesquisar numa lista que contem todoas as cidades pertencentes a um determinado
        //estado brasileiro 
        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(txtBuscar.getText());
        int filtroCiddade = Integer.parseInt(txtFiltroIDCidade.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        //aqui instanciamos uma lista do tipo BairroDTO
        ArrayList<BairroDTO> list;

        try {

            // essa lista irá receber as informações a partir do método abaixo descrito método este contido ja na 
            //camada DAO de disparo da SQL
            list = (ArrayList<BairroDTO>) bairroDAO.filtrarBairrosPesqRapidaComFiltroCidade(pesquisar, filtroCiddade);

            Object rowData[] = new Object[3];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdBairroDto();
                rowData[1] = list.get(i).getNomeBairroDto().toString();
                rowData[2] = list.get(i).getNomeCidadeRecuperarDto().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(380);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormListaBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCabecalho = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        txtFiltroIDCidade = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        lblIDCidadeFiltro = new javax.swing.JLabel();
        lblLinhaInformativa = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
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

        txtFiltroIDCidade.setBackground(new java.awt.Color(255, 255, 153));
        txtFiltroIDCidade.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        txtFiltroIDCidade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFiltroIDCidade.setOpaque(false);
        painelCabecalho.add(txtFiltroIDCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 30, 30));

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

        lblIDCidadeFiltro.setText("Filtro-->>");
        painelCabecalho.add(lblIDCidadeFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, -1));

        lblLinhaInformativa.setText("Linha Informativa");
        painelCabecalho.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 270, -1));
        painelCabecalho.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 80, -1));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "BAIRROS", "CIDADE"
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
            tabela.getColumnModel().getColumn(0).setMinWidth(40);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabela.getColumnModel().getColumn(0).setMaxWidth(40);
            tabela.getColumnModel().getColumn(1).setMinWidth(200);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(1).setMaxWidth(200);
            tabela.getColumnModel().getColumn(2).setMinWidth(160);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(160);
            tabela.getColumnModel().getColumn(2).setMaxWidth(160);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(painelCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        int linha = tabela.getSelectedRow();

        txtBairro.setText(tabela.getValueAt(linha, 1).toString());
        txtEndereco.setEnabled(true);
        txtComplemento.setEnabled(true);
        txtNumero.setEnabled(true);
        txtFoneComercial.setEnabled(true);
        txtCelular.setEnabled(true);
        txtContato.setEnabled(true);
        txtEndereco.requestFocus();
        this.dispose();

    }//GEN-LAST:event_tabelaMouseClicked

    private void aoReceberFocoTxtBuscar() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Digite o Registro Procurado -->[ENTER]<--");
    }

    private void aoReceberFocoBtnPesquisar(){
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Pressione -->[ENTER]<-- Procurar -->[MYSQL]<--");
    
    }

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        txtBuscar.setBackground(Color.YELLOW);
        aoReceberFocoTxtBuscar();
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        txtBuscar.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtBuscarFocusLost

    private void desabilitarCampos() {
        txtBuscar.setEnabled(false);

    }

    private void desabilitarTodosBotoes() {

        btnPesquisar.setEnabled(false);
    }

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed

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
            desabilitarCampos();
            desabilitarTodosBotoes();
        }


    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        btnPesquisar.setBackground(Color.YELLOW);
        aoReceberFocoBtnPesquisar();
    }//GEN-LAST:event_btnPesquisarFocusGained

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            btnPesquisar.requestFocus();

        }

        //se seta para DIREITA
        if (evt.getKeyCode() == evt.VK_RIGHT) {

            btnPesquisar.requestFocus();
            Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Pesquisar Presione ENTER");

        }


    }//GEN-LAST:event_txtBuscarKeyPressed

    private void btnPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPesquisarKeyPressed
     
        
        if(evt.getKeyCode()==evt.VK_ENTER){
        
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
            desabilitarCampos();
            desabilitarTodosBotoes();
        }


        }
        
        
        if (evt.getKeyCode() == evt.VK_LEFT) {

            txtBuscar.requestFocus();

        }
    }//GEN-LAST:event_btnPesquisarKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIDCidadeFiltro;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtFiltroIDCidade;
    // End of variables declaration//GEN-END:variables
}
