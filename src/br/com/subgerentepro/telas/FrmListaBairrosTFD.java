package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.BairroBO;
import br.com.subgerentepro.bo.CidadeBO;
import br.com.subgerentepro.dao.BairroDAO;
import br.com.subgerentepro.dao.CidadeDAO;
import br.com.subgerentepro.dto.BairroDTO;
import br.com.subgerentepro.dto.CidadeDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;

import static br.com.subgerentepro.telas.TelaTFD.txtBairro;
import static br.com.subgerentepro.telas.TelaTFD.txtIdBairro;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import javax.swing.JInternalFrame;

/**
 *
 * @author DaTorres
 */
public class FrmListaBairrosTFD extends javax.swing.JInternalFrame {

    BairroDTO bairroDTO = new BairroDTO();
    BairroDAO bairroDAO = new BairroDAO();
    BairroBO bairroBO = new BairroBO();
    /**
     * Código Mestre Chimura
     */
    private static FrmListaBairrosTFD instance = null;

    public static FrmListaBairrosTFD getInstance() {

        if (instance == null) {

            instance = new FrmListaBairrosTFD();

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

    public FrmListaBairrosTFD() {
        initComponents();
        aoCarregarForm();

        /**
         * Ao carregar o formulario buscar o valor que se encontra no
         * formulalrio TelaEmpresa do Campo txtCodEstado que deverá esta
         * definido como public e estatico e puxar para o campo no formulario em
         * uso no caso FrmListaCidades para o campo txtFiltroIDEstado
         */
        txtFiltroIDCidade.setText(TelaTFD.txtidCidade.getText());
        txtFiltroIDCidade.setEnabled(false);

        refresh();

    }

    private void aoCarregarForm() {
        progressBarraPesquisa.setIndeterminate(true);

    }

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
        btnCadastrarBairros = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
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
        painelCabecalho.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 200, 30));

        txtFiltroIDCidade.setBackground(new java.awt.Color(51, 153, 255));
        txtFiltroIDCidade.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtFiltroIDCidade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelCabecalho.add(txtFiltroIDCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 32, 32));

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });
        painelCabecalho.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 32, 35));

        lblIDCidadeFiltro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIDCidadeFiltro.setText("ID");
        painelCabecalho.add(lblIDCidadeFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 30, -1));

        btnCadastrarBairros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/AdicionarRegistro Rolover.png"))); // NOI18N
        btnCadastrarBairros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarBairrosActionPerformed(evt);
            }
        });
        painelCabecalho.add(btnCadastrarBairros, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 32, 32));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bairros");
        painelCabecalho.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 50, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/refresh.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        painelCabecalho.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 32, 32));

        jLabel2.setText("Atualizar");
        painelCabecalho.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, -1, -1));
        painelCabecalho.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 200, -1));

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
            tabela.getColumnModel().getColumn(0).setMinWidth(50);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(0).setMaxWidth(50);
            tabela.getColumnModel().getColumn(1).setMinWidth(190);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(190);
            tabela.getColumnModel().getColumn(1).setMaxWidth(190);
            tabela.getColumnModel().getColumn(2).setMinWidth(200);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(2).setMaxWidth(200);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        int linha = tabela.getSelectedRow();

        txtIdBairro.setText(tabela.getValueAt(linha, 0).toString());
        txtBairro.setText(tabela.getValueAt(linha, 1).toString());
        progressBarraPesquisa.setIndeterminate(closable);
        this.dispose();

    }//GEN-LAST:event_tabelaMouseClicked

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        txtBuscar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        txtBuscar.setBackground(new Color(240, 240, 240));
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
    br.com.subgerentepro.telas.TelaBairro formBairros;
    private void btnCadastrarBairrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarBairrosActionPerformed
        if (estaFechado(formBairros)) {
            formBairros = new TelaBairro();
            DeskTop.add(formBairros).setLocation(1, 5);
            formBairros.setTitle("Bairros");
            formBairros.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formBairros.toFront();
            formBairros.show();

        }
    }//GEN-LAST:event_btnCadastrarBairrosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        refresh();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrarBairros;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIDCidadeFiltro;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtFiltroIDCidade;
    // End of variables declaration//GEN-END:variables
}
