package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.BairroBO;
import br.com.subgerentepro.dao.BairroDAO;
import br.com.subgerentepro.dao.CidadeDAO;
import br.com.subgerentepro.dto.BairroDTO;
import br.com.subgerentepro.dto.CidadeDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;

import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.lblPerfil;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class TelaBairro extends javax.swing.JInternalFrame {

    //https://www.youtube.com/watch?v=-ATbC-4rhc4
    //CRIANDO BOTAO ADICIONAR 
    JButton btnAdicionar = new JButton();
    JButton btnEditar = new JButton();
    //   JButton btnSalvar = new JButton();
    JButton btnExcluir = new JButton();
    JButton btnCancelar = new JButton();
    // JButton btnPesquisar = new JButton();

    JTextField txtPesquisa = new JTextField();
    JTextField txtCodigo = new JTextField();
    //JTextField txtBairro = new JTextField();
    JComboBox comboCidades = new JComboBox();

    BairroDAO bairroDAO = new BairroDAO();
    BairroDTO bairroDTO = new BairroDTO();
    BairroBO bairroBO = new BairroBO();
    CidadeDTO cidadeDTO = new CidadeDTO();
    CidadeDAO cidadeDAO = new CidadeDAO();

    int flag = 0;

    //Chimura: A partir de agora, você usará esse método getInstance() toda vez que quiser criar um ViewEstado
    // Mesma coisa foi feita para ViewBairro
    // Também será necessário fazer para os outros
    private static TelaBairro instance = null;

    public static TelaBairro getInstance() {
        if (instance == null) {
            instance = new TelaBairro();
        }
        return instance;
    }

    // Chimura: usado para checar se a ViewBairro já está aberta, no ato de abrí-la na ViewPrincipal
    public static boolean isOpen() {
        return instance != null;
    }

    public TelaBairro() {
        initComponents();
        componentesInternoJInternal();
        aoCarregarForm();
        listarCombo();
        addRowJTable();
        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tblBairro.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tblBairro.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 

    }

    private void aoCarregarForm() {

        progressBarraPesquisa.setIndeterminate(true);
        btnExcluir.setEnabled(false);
        btnEditar.setEnabled(false);
        btnAdicionar.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnPesquisar.setEnabled(true);
        txtPesquisa.setEnabled(true);
        comboCidades.setEnabled(false);
        txtCodigo.setEnabled(false);
        txtBairro.setEnabled(false);
        txtPesquisa.requestFocus();

    }

    public void listarCombo() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            ArrayList<CidadeDTO> list;

            try {

                list = (ArrayList<CidadeDTO>) bairroDAO.listarComboCidades();

                comboCidades.removeAllItems();
                for (int i = 0; i < list.size(); i++) {

                    comboCidades.addItem(list.get(i).getNomeCidadeDto());

                }
                comboCidades.setSelectedItem("ALTO ALEGRE DO MARANHAO");
            } catch (PersistenciaException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro Camada GUI Listar Cidades :\n" + ex.getMessage());
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

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            DefaultTableModel model = (DefaultTableModel) tblBairro.getModel();

            ArrayList<BairroDTO> list;

            try {

                list = (ArrayList<BairroDTO>) bairroDAO.listarTodos();

                Object rowData[] = new Object[3];

                for (int i = 0; i < list.size(); i++) {
                    rowData[0] = list.get(i).getIdBairroDto();
                    rowData[1] = list.get(i).getNomeBairroDto();
                    rowData[2] = list.get(i).getNomeCidadeRecuperarDto();

                    model.addRow(rowData);
                }

                tblBairro.setModel(model);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tblBairro.getColumnModel().getColumn(0).setPreferredWidth(33);
                tblBairro.getColumnModel().getColumn(1).setPreferredWidth(260);
                tblBairro.getColumnModel().getColumn(2).setPreferredWidth(260);

            } catch (PersistenciaException ex) {
                JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
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

    private void acaoMouseClicked() {
        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/
        bairroDTO.setNomeBairroDto("" + tblBairro.getValueAt(tblBairro.getSelectedRow(), 1));
        BairroDTO modelo;
        try {
            modelo = bairroDAO.filtrarAoClicar(bairroDTO);

            if (modelo != null) {
                /**
                 * String.valueOf() pega um Valor Inteiro e transforma em String
                 * e seta em txtIDMedico que é um campo do tipo texto
                 */
                txtCodigo.setText(String.valueOf(modelo.getIdBairroDto()));
                txtBairro.setText(modelo.getNomeBairroDto());
                comboCidades.setSelectedItem(modelo.getChaveEstrangeiraIdCidadeDto());
                /**
                 * Liberar os botões abaixo
                 */
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnAdicionar.setEnabled(false);
                btnCancelar.setEnabled(true);

                /**
                 * Habilitar Campos
                 */
                txtPesquisa.setEnabled(true);

                /**
                 * Desabilitar campos
                 */
                /**
                 * Também irá habilitar nossos campos para que possamos digitar
                 * os dados no formulario medicos
                 */
                txtCodigo.setEnabled(false);
                txtBairro.setEnabled(false);
                comboCidades.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar()\n" + ex.getMessage());
        }
    }

    private void desabilitarCampos() {
        txtCodigo.setEnabled(false);
        txtBairro.setEnabled(false);
        txtPesquisa.setEnabled(false);
        comboCidades.setEnabled(false);
    }

    private void desabilitarTodosBotoes() {
        btnAdicionar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnPesquisar.setEnabled(false);
        btnSalvar.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        PanelBotoesManipulacaoBancoDados = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBairro = new javax.swing.JTable();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        btnPesquisar = new javax.swing.JButton();
        lblLinhaInformativa = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(475, 504));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        painelPrincipal.setLayout(null);

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png"))); // NOI18N
        btnSalvar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnSalvarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnSalvarFocusLost(evt);
            }
        });
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        btnSalvar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalvarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout PanelBotoesManipulacaoBancoDadosLayout = new javax.swing.GroupLayout(PanelBotoesManipulacaoBancoDados);
        PanelBotoesManipulacaoBancoDados.setLayout(PanelBotoesManipulacaoBancoDadosLayout);
        PanelBotoesManipulacaoBancoDadosLayout.setHorizontalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                .addContainerGap(339, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        PanelBotoesManipulacaoBancoDadosLayout.setVerticalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, Short.MAX_VALUE)
        );

        painelPrincipal.add(PanelBotoesManipulacaoBancoDados);
        PanelBotoesManipulacaoBancoDados.setBounds(0, 0, 460, 47);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblBairro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "BAIRRO", "CIDADE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBairro.setSelectionForeground(new java.awt.Color(51, 153, 255));
        tblBairro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBairroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBairro);

        painelPrincipal.add(jScrollPane1);
        jScrollPane1.setBounds(0, 172, 452, 300);
        painelPrincipal.add(progressBarraPesquisa);
        progressBarraPesquisa.setBounds(360, 60, 80, 14);

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesquisar.setPreferredSize(new java.awt.Dimension(32, 35));
        btnPesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesquisarFocusGained(evt);
            }
        });
        painelPrincipal.add(btnPesquisar);
        btnPesquisar.setBounds(300, 120, 32, 35);

        lblLinhaInformativa.setText("Linha Informativa");
        painelPrincipal.add(lblLinhaInformativa);
        lblLinhaInformativa.setBounds(10, 60, 330, 14);

        txtBairro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBairroKeyPressed(evt);
            }
        });
        painelPrincipal.add(txtBairro);
        txtBairro.setBounds(30, 90, 180, 20);

        getContentPane().add(painelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 470));

        setBounds(0, 0, 475, 504);
    }// </editor-fold>//GEN-END:initComponents


    private void tblBairroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBairroMouseClicked

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            acaoMouseClicked();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }//GEN-LAST:event_tblBairroMouseClicked

    private void acaoPesquisar() {
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            acaoBotaoPesquisar();
        } else {
            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }

    }


    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        if (txtPesquisa.getText().toString().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "" + "\n Campo Pesquisa NULO", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            txtPesquisa.setBackground(Color.CYAN);
            txtPesquisa.setText("Bairro?  :~( ");
            txtPesquisa.requestFocus();
        } else {
            acaoPesquisar();
        }
    }//GEN-LAST:event_btnPesquisarFocusGained

    private void txtBairroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBairroKeyPressed

        //aperta ENTER  e vai pra o campo de cpf 
        if (evt.getKeyCode() == evt.VK_ENTER) {
            btnSalvar.requestFocus();
            btnSalvar.setBackground(Color.YELLOW);
            txtBairro.setBackground(Color.WHITE);
        }

    }//GEN-LAST:event_txtBairroKeyPressed

    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained

    }//GEN-LAST:event_btnSalvarFocusGained

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        acaoBotaoSalvar();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {
            acaoBotaoSalvar();
        }

        if (evt.getKeyCode() == evt.VK_F7) {
            acaoBotaoSalvar();
        }

    }//GEN-LAST:event_btnSalvarKeyPressed

    private void btnSalvarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusLost
        btnSalvar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnSalvarFocusLost
    private void componentesInternoJInternal() {
        //https://www.youtube.com/watch?v=-ATbC-4rhc4

        TheHandler th = new TheHandler();
        //ADICIONANDO AOS BOTOES AÇOES QUE SERAO TRATADAS PELO ACTIONLISLISTENER
        btnAdicionar.addActionListener(th);
        btnEditar.addActionListener(th);
        btnCancelar.addActionListener(th);
        btnExcluir.addActionListener(th);
        btnSalvar.addActionListener(th);
        btnPesquisar.addActionListener(th);
        txtPesquisa.addActionListener(th);
        AbsoluteLayout layout = new AbsoluteLayout();
        //BOTAO ADICIONAR 
        btnAdicionar.setBounds(220, 1, 45, 45);
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
        PanelBotoesManipulacaoBancoDados.add(this.btnAdicionar);

        //BOTAO EDITAR
        btnEditar.setBounds(280, 1, 45, 45);
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editar.png")));
        PanelBotoesManipulacaoBancoDados.add(this.btnEditar);
        //BOTAO SALVAR
        //  btnSalvar.setBounds(340, 1, 45, 45);
        //btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));
        // PanelBotoesManipulacaoBancoDados.add(this.btnSalvar);
        //BOTAO CANCELAR
        btnCancelar.setBounds(400, 1, 45, 45);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        PanelBotoesManipulacaoBancoDados.add(this.btnCancelar);
        //BOTAO EXCLUIR 
        btnExcluir.setBounds(20, 1, 45, 45);
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/excluir.png")));
        PanelBotoesManipulacaoBancoDados.add(this.btnExcluir);
        //BOTAO PESQUISAR 
        btnPesquisar.setBounds(320, 130, 32, 35);
//        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        painelPrincipal.add(this.btnPesquisar);

        //CRIANDO CAMPO PESQUISA JTEXTFIELD
        txtPesquisa.setBounds(10, 135, 300, 30);
        painelPrincipal.add(txtPesquisa);

        comboCidades.setBounds(290, 80, 148, 30);
        painelPrincipal.add(comboCidades);
        txtCodigo.setBounds(10, 80, 50, 30);
        painelPrincipal.add(txtCodigo);
        txtBairro.setBounds(70, 80, 200, 30);
        painelPrincipal.add(txtBairro);

    }

    private void acaoBotaoExcluir() {

        int resposta = 0;
        resposta = JOptionPane.showConfirmDialog(rootPane, "Deseja Excluir Resgistro?");

        if (resposta == JOptionPane.YES_OPTION) {
            bairroDTO.setIdBairroDto(Integer.parseInt(txtCodigo.getText()));
            /**
             * Chamando o método que irá executar a Edição dos Dados em nosso
             * Banco de Dados
             */
            bairroBO.ExcluirBO(bairroDTO);
            /**
             * Após salvar limpar os campos
             */
            txtBairro.setText("");
            comboCidades.setSelectedItem("");
            txtPesquisa.setText("");
            btnExcluir.setEnabled(false);
            btnEditar.setEnabled(false);

            /* Esse método fecha a janela atual */
            //  ViewBairro.this.dispose();
            //ViewBairro formularioBairro = ViewBairro.getInstance();
            //formularioBairro.setVisible(true);
        }

    }

    private void acaoBotaoAdicionar() {
        flag = 1;

        /**
         * Campos devem ser ativados
         */
        txtBairro.setEnabled(true);
        txtBairro.setEditable(true);
        comboCidades.setEnabled(false);
        /**
         * Limpar os campos para cadastrar
         */
        txtBairro.setText("");
        comboCidades.setSelectedItem("");
        txtPesquisa.setText("");

        /**
         * Botões que deverão ficar habilitados nesse evento para esse tipo de
         * Formulario
         */
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);

        /**
         * Botões que deverão ficar desabilitados nesse evento para esse tipo de
         * Formulario
         */
        txtPesquisa.setEnabled(false);
        btnEditar.setEnabled(false);

        txtBairro.requestFocus();//setar o campo nome Bairro após adicionar
        txtBairro.setBackground(Color.CYAN);  // altera a cor do fundo

    }

    private void acaoBotaoEditar() {

        if (txtBairro.equals("")) {

            JOptionPane.showMessageDialog(null, "Informação:\n"
                    + "Para que se possa EDITAR é necessário \n"
                    + "que haja um registro selecionado");

        } else {
            /**
             * Quando clicar em Editar essa flag recebe o valor de 2
             */

            flag = 2;

            /**
             * Também irá habilitar nossos campos para que possamos digitar os
             * dados no formulario medicos
             */
            txtBairro.setEnabled(true);
            comboCidades.setEnabled(true);
            /**
             * ao clicar em btnAdicionar fazemos com que btnSalvar fique
             * habilitado para um eventual salvamento
             */
            btnSalvar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnEditar.setEnabled(false);
            btnAdicionar.setEnabled(false);
            btnExcluir.setEnabled(false);

        }
    }

    private void salvarAdicoesAteracoesBairros() {
        String nomeBairro = txtBairro.getText();
        String cidadeDoBairro = (String) comboCidades.getSelectedItem();

        // JOptionPane.showMessageDialog(this, "nomeBairro:"+nomeBairro);
        //JOptionPane.showMessageDialog(this, "cidadeDoBairro:"+cidadeDoBairro);
        try {
            bairroBO = new BairroBO();

            /**
             * Trabalhando com os retornos das validações
             */
            if ((bairroBO.validaNome(nomeBairro)) == false) {
                txtBairro.setText("");
            } else {

                if ((flag == 1)) {

                    bairroDTO.setNomeBairroDto(nomeBairro);

                    boolean retornoVerifcaDuplicidade = bairroDAO.verificaDuplicidade(bairroDTO);

                    if (retornoVerifcaDuplicidade == false) {

                        bairroDTO.setNomeBairroDto(nomeBairro);

                        // JOptionPane.showMessageDialog(null, "Camada gui" + ufEstado);
                        cidadeDTO.setNomeCidadeDto(cidadeDoBairro);

                        CidadeDTO retorno = cidadeDAO.ComboCidadeSetarParaEviarBanco(cidadeDTO);

                        //   JOptionPane.showMessageDialog(null, "Retornado ok Método:" + retorno.getIdEtadoDto());
                        bairroDTO.setChaveEstrangeiraIdCidadeDto(retorno.getCodCidadeDto());

                        bairroBO.cadastrarBO(bairroDTO);//DANDO ERRO AQUI
                        /**
                         * Após salvar limpar os campos
                         */
                        txtBairro.setText("");
                        txtCodigo.setText("");
                        comboCidades.setSelectedItem("");
                        /**
                         * Bloquear campos e Botões
                         */
                        txtCodigo.setEnabled(false);
                        txtBairro.setEnabled(false);
                        comboCidades.setEnabled(false);
                        txtPesquisa.setEnabled(true);
                        /**
                         * Liberar campos necessário para operações após
                         * salvamento
                         */
                        btnSalvar.setEnabled(false);

                        btnAdicionar.setEnabled(true);

                        JOptionPane.showMessageDialog(this, "" + "\n Registro Salvo", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                        int numeroLinhas = tblBairro.getRowCount();

                        if (numeroLinhas > 0) {

                            while (tblBairro.getModel().getRowCount() > 0) {
                                ((DefaultTableModel) tblBairro.getModel()).removeRow(0);

                            }

                            addRowJTable();

                        } else {
                            addRowJTable();

                        }

                    } else {
                        JOptionPane.showMessageDialog(TelaBairro.this, "Resgistro já cadastrado.\no Sistema Impossibilitou \n a Duplicidade");
                        /**
                         * Limpando campos após o evento de travamento da
                         * duplicidade
                         */

                        txtCodigo.setText("");
                        txtBairro.setText("");
                        txtPesquisa.setText("");

                        /**
                         * Disabilitando Campos
                         */
                        txtBairro.setEnabled(false);
                        btnSalvar.setEnabled(false);
                        btnExcluir.setEnabled(false);
                        comboCidades.setEnabled(false);

                        txtPesquisa.setEnabled(true);

                    }

                } else {

                    /**
                     * Caso não seja um novo registro equivale dizer que é uma
                     * edição então executará esse código flag será !=1
                     */
                    bairroDTO.setIdBairroDto(Integer.parseInt(txtCodigo.getText()));
                    bairroDTO.setNomeBairroDto(txtBairro.getText());

                    cidadeDTO.setNomeCidadeDto(cidadeDoBairro);
                    CidadeDTO retorno = cidadeDAO.ComboCidadeSetarParaEviarBanco(cidadeDTO);

                    bairroDTO.setChaveEstrangeiraIdCidadeDto(retorno.getCodCidadeDto());
                    /**
                     * Chamando o método que irá executar a Edição dos Dados em
                     * nosso Banco de Dados
                     */
                    bairroBO.EditarBO(bairroDTO);

                    /**
                     * Após salvar limpar os campos
                     */
                    txtCodigo.setText("");
                    txtBairro.setText("");
                    comboCidades.setSelectedItem("");
                    txtPesquisa.setText("");

                    /**
                     * Bloquear campos e Botões
                     */
                    txtCodigo.setEnabled(false);
                    txtBairro.setEnabled(false);
                    comboCidades.setEnabled(false);
                    txtPesquisa.setEnabled(true);

                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    btnAdicionar.setEnabled(true);
                    btnCancelar.setEnabled(false);
                    btnSalvar.setEnabled(false);

                    MetodoStaticosUtil.addMensagem(TelaBairro.this, "Edição Salva com Sucesso!");

                    int numeroLinhas = tblBairro.getRowCount();

                    if (numeroLinhas > 0) {

                        while (tblBairro.getModel().getRowCount() > 0) {
                            ((DefaultTableModel) tblBairro.getModel()).removeRow(0);

                        }

                        addRowJTable();

                    } else {
                        addRowJTable();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "yyErro Capturado:\n" + e.getMessage() + "\nPorfavor tire um Print e Entrar em Contado com o Desenvolvedor do Sistema\nAnalista:Tonis A. Torres Ferreira\nsisvenda2011@gmail.com");

        }

    }

    private void acaoBotaoSalvar() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            salvarAdicoesAteracoesBairros();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    private void acaoBotaoCancelar() {
        /**
         * Após salvar limpar os campos
         */
        txtCodigo.setText("");
        txtBairro.setText("");
        comboCidades.setSelectedItem("");
        txtPesquisa.setText("");
        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        txtCodigo.setEnabled(false);
        txtBairro.setEnabled(false);
        comboCidades.setEnabled(false);
        txtPesquisa.setEnabled(true);

        /**
         * ao clicar em btnAdicionar fazemos com que btnSalvar fique habilitado
         * para um eventual salvamento
         */
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnAdicionar.setEnabled(true);

        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        JOptionPane.showMessageDialog(null, "Cadastro cancelado com sucesso!!");

    }

    private void acaoBotaoPesquisar() {

        try {

            int numeroLinhas = tblBairro.getRowCount();

            if (numeroLinhas > 0) {

                while (tblBairro.getModel().getRowCount() > 0) {
                    ((DefaultTableModel) tblBairro.getModel()).removeRow(0);

                }

                pesquisaBairros();

            } else {
                addRowJTable();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void personalizandoBarraInfoPesquisaInicio() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Pesquisando: Espere Comunicando MySqL.");
    }

    private void personalizandoBarraInfoPesquisaTermino() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setForeground(Color.ORANGE);
        lblLinhaInformativa.setText("Pesquisa Terminada.");
        txtPesquisa.requestFocus();
        txtPesquisa.setBackground(Color.CYAN);
    }

    private void pesquisaBairros() {

        String pesquisarBairros = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisa.getText());

        DefaultTableModel model = (DefaultTableModel) tblBairro.getModel();

        ArrayList<BairroDTO> list;

        try {

            list = (ArrayList<BairroDTO>) bairroDAO.filtrarBairrosPesqRapida(pesquisarBairros);

            Object rowData[] = new Object[3];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdBairroDto();
                rowData[1] = list.get(i).getNomeBairroDto();
                rowData[2] = list.get(i).getNomeCidadeRecuperarDto();

                model.addRow(rowData);
            }

            tblBairro.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tblBairro.getColumnModel().getColumn(0).setPreferredWidth(33);
            tblBairro.getColumnModel().getColumn(1).setPreferredWidth(260);
            tblBairro.getColumnModel().getColumn(2).setPreferredWidth(260);

            //----------------------------------//
            //informe sobre Fim da pesquisa //
            //--------------------------------//
            personalizandoBarraInfoPesquisaTermino();

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }
    }

    private class TheHandler implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            if (evt.getSource() == btnExcluir) {
                if (!lblPerfil.getText().equalsIgnoreCase("protocolo")) {
                    acaoBotaoExcluir();
                }

                if (lblPerfil.getText().equalsIgnoreCase("protocolo")) {

                    Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setForeground(Color.BLUE);
                    lblLinhaInformativa.setText("Proibido a Exclusão.");
                }

            }
            if (evt.getSource() == btnAdicionar) {
                acaoBotaoAdicionar();
            }

            if (evt.getSource() == btnEditar) {
                if (lblPerfil.getText().equalsIgnoreCase("protocolo")) {

                    Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setForeground(Color.BLUE);
                    lblLinhaInformativa.setText("Proibido a Edição.");
                }
                if (!lblPerfil.getText().equalsIgnoreCase("protocolo")) {
                    acaoBotaoEditar();
                }
            }

//            if (evt.getSource() == btnSalvar) {
//                acaoBotaoSalvar();
//            }
            if (evt.getSource() == btnCancelar) {

                acaoBotaoCancelar();

            }

            if (evt.getSource() == txtPesquisa) {
                txtPesquisa.setToolTipText("Digite Funcionario a Ser Pesquisado");
                txtPesquisa.setBackground(Color.YELLOW);

                //----------------------------------//
                //informe sobre inicio da pesquisa //
                //--------------------------------//
                personalizandoBarraInfoPesquisaInicio();
                btnPesquisar.requestFocus();

            }
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBotoesManipulacaoBancoDados;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tblBairro;
    private javax.swing.JTextField txtBairro;
    // End of variables declaration//GEN-END:variables
}
