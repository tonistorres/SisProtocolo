/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.EstadoBO;

import br.com.subgerentepro.dao.EstadoDAO;
import br.com.subgerentepro.dto.EstadoDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author DaTorres
 */
public class TelaEstados extends javax.swing.JInternalFrame implements ActionListener {

    EstadoDTO estadoDTO = new EstadoDTO();
    EstadoDAO estadoDAO = new EstadoDAO();
    EstadoBO estadoBO = new EstadoBO();

    //https://www.youtube.com/watch?v=-ATbC-4rhc4
    //CRIANDO BOTAO ADICIONAR 
    JButton btnAdicionar = new JButton();
    JButton btnEditar = new JButton();
    JButton btnSalvar = new JButton();
    JButton btnExcluir = new JButton();
    JButton btnCancelar = new JButton();
    JButton btnPesquisar = new JButton();

    int flag = 0;

    // Chimura: A partir de agora, você usará esse método getInstance() toda vez que quiser criar um ViewEstado
    // Mesma coisa foi feita para ViewBairro
    // Também será necessário fazer para os outros
    private static TelaEstados instance = null;

    public static TelaEstados getInstance() {
        if (instance == null) {
            instance = new TelaEstados();// se instance igual a null então é criado uma instancia de ViewEstado
        }
        return instance;// em seguinda retornamos essa instancia qui por meio do padrão singleton
    }

    // Chimura: usado para checar se a ViewEstado já está aberta, no ato de abrí-la na ViewPrincipal
    public static boolean isOpen() {
        return instance != null;
    }

    public TelaEstados() {
        initComponents();
        aoCarregarForm();
        addRowJTable();
        personalizarBotoesInternosDoForm();
        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tblListarEstados.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tblListarEstados.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 

    }

    private void aoCarregarForm(){
    btnExcluir.setEnabled(false);
    btnEditar.setEnabled(false);
    btnSalvar.setEnabled(false);
    btnCancelar.setEnabled(false);
    
    
    }
    
    
    private void acaoBtnEditar() {
        if (txtNomeEstado.equals("")) {

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
            txtNomeEstado.setEnabled(true);
            txtSiglaEstado.setEnabled(true);
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

    private void acaoBtnAdicionar() {
        flag = 1;

        /**
         * Campos devem ser ativados
         */
        txtNomeEstado.setEnabled(true);
        txtSiglaEstado.setEnabled(false);
        /**
         * Limpar os campos para cadastrar
         */
        txtNomeEstado.setText("");
        txtSiglaEstado.setText("");
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

        txtNomeEstado.requestFocus();//colocar foco no campo do estado
        txtNomeEstado.setBackground(Color.CYAN);  // altera a cor do fundo

    }

    private void acaoBtnSalvar() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            acaoBotaoSalvarAdicaoEdicao();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    private void acaoBtnExcluir() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            acaoBotaoExcluir();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    public void actionPerformed(ActionEvent e) {
        //https://www.youtube.com/watch?v=-ATbC-4rhc4  
        //agora vamos contratar varios tipos de ações num mesmo método actionPerformed
        if (e.getSource() == btnAdicionar) {
            acaoBtnAdicionar();
        }

        if (e.getSource() == btnEditar) {
            acaoBtnEditar();
        }

        if (e.getSource() == btnCancelar) {
            acaoBtnCancelar();
        }

        if (e.getSource() == btnExcluir) {
            acaoBtnExcluir();
        }

        if (e.getSource() == btnSalvar) {
            acaoBtnSalvar();
        }

        if (e.getSource() == btnPesquisar) {
            acaoBotaoPesquisar();
        }

    }

    private void personalizarBotoesInternosDoForm() {
    //https://www.youtube.com/watch?v=-ATbC-4rhc4

        //ADICIONANDO AOS BOTOES AÇOES QUE SERAO TRATADAS PELO ACTIONLISLISTENER
        btnAdicionar.addActionListener(this);
        btnEditar.addActionListener(this);
        btnCancelar.addActionListener(this);
        btnExcluir.addActionListener(this);
        btnSalvar.addActionListener(this);
        btnPesquisar.addActionListener(this);

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
        btnSalvar.setBounds(340, 1, 45, 45);
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));
        PanelBotoesManipulacaoBancoDados.add(this.btnSalvar);
        //BOTAO CANCELAR
        btnCancelar.setBounds(400, 1, 45, 45);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        PanelBotoesManipulacaoBancoDados.add(this.btnCancelar);
        //BOTAO EXCLUIR 
        btnExcluir.setBounds(20, 1, 45, 45);
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/excluir.png")));
        PanelBotoesManipulacaoBancoDados.add(this.btnExcluir);
        //BOTAO PESQUISAR 
        btnPesquisar.setBounds(390, 118, 32, 35);
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        PanelCadEstados.add(this.btnPesquisar);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelCadEstados = new javax.swing.JPanel();
        lblCodigoEstado = new javax.swing.JLabel();
        lblNomeEstado = new javax.swing.JLabel();
        lblSiglaEstado = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNomeEstado = new javax.swing.JTextField();
        txtSiglaEstado = new javax.swing.JTextField();
        scrTblEstado = new javax.swing.JScrollPane();
        tblListarEstados = new javax.swing.JTable();
        txtPesquisa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        PanelBotoesManipulacaoBancoDados = new javax.swing.JPanel();

        setClosable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        PanelCadEstados.setBackground(java.awt.Color.white);
        PanelCadEstados.setLayout(null);

        lblCodigoEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodigoEstado.setText("ID");
        PanelCadEstados.add(lblCodigoEstado);
        lblCodigoEstado.setBounds(10, 60, 40, 14);

        lblNomeEstado.setText("UF:");
        PanelCadEstados.add(lblNomeEstado);
        lblNomeEstado.setBounds(70, 60, 90, 14);

        lblSiglaEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSiglaEstado.setText("Sigla:");
        PanelCadEstados.add(lblSiglaEstado);
        lblSiglaEstado.setBounds(390, 60, 50, 14);

        txtCodigo.setDisabledTextColor(new java.awt.Color(51, 51, 255));
        txtCodigo.setEnabled(false);
        PanelCadEstados.add(txtCodigo);
        txtCodigo.setBounds(10, 80, 44, 31);

        txtNomeEstado.setDisabledTextColor(new java.awt.Color(51, 51, 255));
        txtNomeEstado.setEnabled(false);
        txtNomeEstado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomeEstadoFocusLost(evt);
            }
        });
        txtNomeEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeEstadoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeEstadoKeyTyped(evt);
            }
        });
        PanelCadEstados.add(txtNomeEstado);
        txtNomeEstado.setBounds(70, 80, 310, 31);

        txtSiglaEstado.setDisabledTextColor(new java.awt.Color(51, 51, 255));
        txtSiglaEstado.setEnabled(false);
        txtSiglaEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSiglaEstadoKeyPressed(evt);
            }
        });
        PanelCadEstados.add(txtSiglaEstado);
        txtSiglaEstado.setBounds(390, 80, 50, 31);

        tblListarEstados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ESTADO", "SIGLAS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListarEstados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblListarEstadosFocusGained(evt);
            }
        });
        tblListarEstados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListarEstadosMouseClicked(evt);
            }
        });
        scrTblEstado.setViewportView(tblListarEstados);

        PanelCadEstados.add(scrTblEstado);
        scrTblEstado.setBounds(10, 160, 440, 300);

        txtPesquisa.setBackground(new java.awt.Color(51, 153, 255));
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyPressed(evt);
            }
        });
        PanelCadEstados.add(txtPesquisa);
        txtPesquisa.setBounds(70, 120, 310, 30);

        jLabel2.setText("Pesquisar:");
        PanelCadEstados.add(jLabel2);
        jLabel2.setBounds(10, 130, 60, 14);

        javax.swing.GroupLayout PanelBotoesManipulacaoBancoDadosLayout = new javax.swing.GroupLayout(PanelBotoesManipulacaoBancoDados);
        PanelBotoesManipulacaoBancoDados.setLayout(PanelBotoesManipulacaoBancoDadosLayout);
        PanelBotoesManipulacaoBancoDadosLayout.setHorizontalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );
        PanelBotoesManipulacaoBancoDadosLayout.setVerticalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
        );

        PanelCadEstados.add(PanelBotoesManipulacaoBancoDados);
        PanelBotoesManipulacaoBancoDados.setBounds(0, 0, 460, 47);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelCadEstados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelCadEstados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
        );

        setBounds(600, 150, 475, 504);
    }// </editor-fold>//GEN-END:initComponents

    private void desabilitarCampos() {

        txtCodigo.setEnabled(false);
        txtNomeEstado.setEnabled(false);
        txtPesquisa.setEnabled(false);
        txtSiglaEstado.setEnabled(false);

    }

    private void desabilitarTodosBotoes() {
        btnAdicionar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnPesquisar.setEnabled(false);
        btnSalvar.setEnabled(false);
    }

    /**
     * Criando um método para adicionar linhas no JTable
     */
    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tblListarEstados.getModel();

        ArrayList<EstadoDTO> list;

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            try {

                list = (ArrayList<EstadoDTO>) estadoDAO.listarTodos();

                Object rowData[] = new Object[3];

                for (int i = 0; i < list.size(); i++) {
                    rowData[0] = list.get(i).getIdEtadoDto();
                    rowData[1] = list.get(i).getNomeEstadoDto();
                    rowData[2] = list.get(i).getSiglaEstadoDto();

                    model.addRow(rowData);
                }

                tblListarEstados.setModel(model);
                //posição que se encontra o estado do maranhão dentro do banco de dados para ser selecionado 
                tblListarEstados.setRowSelectionInterval(9, 9);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tblListarEstados.getColumnModel().getColumn(0).setPreferredWidth(33);
                tblListarEstados.getColumnModel().getColumn(1).setPreferredWidth(260);
                tblListarEstados.getColumnModel().getColumn(2).setPreferredWidth(33);

            } catch (PersistenciaException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() \n"
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

    private void acaoBtnCancelar() {

        /**
         * O procedimento Aqui é CAPTURAR os valores digitados pelo usuário para
         * só então salvar no Banco de Dados
         */
        String nomeEstado = txtNomeEstado.getText();
        String siglaEstado = txtNomeEstado.getText();

        /**
         * Após salvar limpar os campos
         */
        txtCodigo.setText("");
        txtNomeEstado.setText("");
        txtSiglaEstado.setText("");
        txtPesquisa.setText("");
        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        txtNomeEstado.setEnabled(false);
        txtSiglaEstado.setEnabled(false);
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

    private void acaoBotaoSalvarAdicaoEdicao() {

        String nomeEstado = txtNomeEstado.getText();
        String ufEstado = txtSiglaEstado.getText();

        try {
            estadoBO = new EstadoBO();

            /**
             * Trabalhando com os retornos das validações
             */
            if ((estadoBO.validaNome(nomeEstado)) == false) {
                txtNomeEstado.setText("");
            } else {

                if ((flag == 1) && (MetodoStaticosUtil.verificarDigitacaoUF(nomeEstado) == true)) {

                    estadoDTO.setNomeEstadoDto(nomeEstado);

                    boolean retornoVerifcaDuplicidade = estadoDAO.verificaDuplicidade(estadoDTO);

                    if (retornoVerifcaDuplicidade == false) {

                        estadoDTO.setNomeEstadoDto(nomeEstado);
                        estadoDTO.setSiglaEstadoDto(ufEstado);

                        estadoBO.cadastrarBO(estadoDTO);
                        /**
                         * Após salvar limpar os campos
                         */
                        txtNomeEstado.setText("");
                        txtSiglaEstado.setText("");
                        txtPesquisa.setText("");
                        /**
                         * Bloquear campos e Botões
                         */
                        txtNomeEstado.setEnabled(false);
                        txtSiglaEstado.setEnabled(false);
                        txtPesquisa.setEnabled(true);
                        /**
                         * Liberar campos necessário para operações após
                         * salvamento
                         */
                        btnSalvar.setEnabled(false);

                        btnAdicionar.setEnabled(true);

                        MetodoStaticosUtil.addMensagem(TelaEstados.this, "Registro Cadastrado com Sucesso!");
                        int numeroLinhas = tblListarEstados.getRowCount();

                        if (numeroLinhas > 0) {

                            while (tblListarEstados.getModel().getRowCount() > 0) {
                                ((DefaultTableModel) tblListarEstados.getModel()).removeRow(0);

                            }

                            addRowJTable();

                        } else {
                            addRowJTable();
                        }

                    } else {
                        JOptionPane.showMessageDialog(TelaEstados.this, "Estado já cadastrado");
                        txtNomeEstado.setText("");
                        txtSiglaEstado.setText("");
                        txtPesquisa.setText("");
                    }

                } else if ((flag == 1) && (MetodoStaticosUtil.verificarDigitacaoUF(nomeEstado) == false)) {

                    txtNomeEstado.setText("");
                    txtSiglaEstado.setText("");
                    JOptionPane.showMessageDialog(TelaEstados.this, "Não pertence a um Estado Brasileiro");
                } else {

                    if (MetodoStaticosUtil.verificarDigitacaoUF(nomeEstado) == true) {

                        /**
                         * caso contrário em vez de ser adicionar é salvar
                         * edição
                         */
                        estadoDTO.setIdEtadoDto(Integer.parseInt(txtCodigo.getText()));
                        estadoDTO.setNomeEstadoDto(txtNomeEstado.getText());
                        estadoDTO.setSiglaEstadoDto(txtSiglaEstado.getText());
                        /**
                         * Chamando o método que irá executar a Edição dos Dados
                         * em nosso Banco de Dados
                         */
                        estadoBO.EditarBO(estadoDTO);

                        /**
                         * Após salvar limpar os campos
                         */
                        /**
                         * Após salvar limpar os campos
                         */
                        txtNomeEstado.setText("");
                        txtSiglaEstado.setText("");
                        txtPesquisa.setText("");

                        /**
                         * Bloquear campos e Botões
                         */
                        txtNomeEstado.setEnabled(false);
                        txtSiglaEstado.setEnabled(false);
                        txtPesquisa.setEnabled(true);

                        /**
                         * Liberar campos necessário para operações após
                         * salvamento
                         */
                        btnAdicionar.setEnabled(true);
                        btnCancelar.setEnabled(false);
                        btnSalvar.setEnabled(false);

                        MetodoStaticosUtil.addMensagem(TelaEstados.this, "Edição Salva com Sucesso!");
                    } else {

                        txtNomeEstado.setText("");
                        txtSiglaEstado.setText("");
                        JOptionPane.showMessageDialog(TelaEstados.this, "Após edição feita, verificou-se:\n 1-Que o estado Editado NÃO faz parte dos estados Brasileiros");
                    }
                }
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "yyErro Capturado:\n" + e.getMessage() + "\nPorfavor tire um Print e Entrar em Contado com o Desenvolvedor do Sistema\nAnalista:Tonis A. Torres Ferreira\nsisvenda2011@gmail.com");

        }
    }

    private void acaoBotaoExcluir() {
        int resposta = 0;
        resposta = JOptionPane.showConfirmDialog(rootPane, "Deseja Excluir Resgistro?");

        if (resposta == JOptionPane.YES_OPTION) {
            estadoDTO.setIdEtadoDto(Integer.parseInt(txtCodigo.getText()));
            /**
             * Chamando o método que irá executar a Edição dos Dados em nosso
             * Banco de Dados
             */
            estadoBO.ExcluirBO(estadoDTO);
            /**
             * Após salvar limpar os campos
             */
            txtNomeEstado.setText("");
            txtSiglaEstado.setText("");
            txtPesquisa.setText("");
            btnExcluir.setEnabled(false);
            btnEditar.setEnabled(false);
            try {
                /**
                 * Conta o Número de linhas na minha tabela e armazena na
                 * variável numeroLinas
                 * https://www.youtube.com/watch?v=1fKwn-Vd0uc
                 */
                int numeroLinhas = tblListarEstados.getRowCount();
                if (numeroLinhas > 0) {

                    //http://andersonneto.blogspot.com.br/2015/05/tutorial-remover-todas-as-linhas-de-um.html
                    while (tblListarEstados.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tblListarEstados.getModel()).removeRow(0);
                    }
                    addRowJTable();
                } else {
                    addRowJTable();
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();

            }
        }

    }


    private void txtNomeEstadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeEstadoFocusLost

        String nomeDoEstado = txtNomeEstado.getText();

        if (nomeDoEstado.equalsIgnoreCase("acre")) {
            txtSiglaEstado.setText("AC");
        } else if (nomeDoEstado.equalsIgnoreCase("alagoas")) {
            txtSiglaEstado.setText("AL");
        } else if (nomeDoEstado.equalsIgnoreCase("amapa")) {
            txtSiglaEstado.setText("AP");
        } else if (nomeDoEstado.equalsIgnoreCase("amazonas")) {
            txtSiglaEstado.setText("AM");
        } else if (nomeDoEstado.equalsIgnoreCase("bahia")) {
            txtSiglaEstado.setText("BA");
        } else if (nomeDoEstado.equalsIgnoreCase("distrito federal")) {
            txtSiglaEstado.setText("DF");
        } else if (nomeDoEstado.equalsIgnoreCase("ceara")) {
            txtSiglaEstado.setText("CE");
        } else if (nomeDoEstado.equalsIgnoreCase("espirito santo")) {
            txtSiglaEstado.setText("ES");
        } else if (nomeDoEstado.equalsIgnoreCase("goias")) {
            txtSiglaEstado.setText("GO");
        } else if (nomeDoEstado.equalsIgnoreCase("maranhao")) {
            txtSiglaEstado.setText("MA");
        } else if (nomeDoEstado.equalsIgnoreCase("mato grosso")) {
            txtSiglaEstado.setText("MT");
        } else if (nomeDoEstado.equalsIgnoreCase("mato grosso do sul")) {
            txtSiglaEstado.setText("MS");
        } else if (nomeDoEstado.equalsIgnoreCase("minas gerais")) {
            txtSiglaEstado.setText("MG");
        } else if (nomeDoEstado.equalsIgnoreCase("para")) {
            txtSiglaEstado.setText("PA");
        } else if (nomeDoEstado.equalsIgnoreCase("paraiba")) {
            txtSiglaEstado.setText("PB");
        } else if (nomeDoEstado.equalsIgnoreCase("parana")) {
            txtSiglaEstado.setText("PR");
        } else if (nomeDoEstado.equalsIgnoreCase("pernambuco")) {
            txtSiglaEstado.setText("PE");
        } else if (nomeDoEstado.equalsIgnoreCase("piaui")) {
            txtSiglaEstado.setText("PI");
        } else if (nomeDoEstado.equalsIgnoreCase("rio de janeiro")) {
            txtSiglaEstado.setText("RJ");
        } else if (nomeDoEstado.equalsIgnoreCase("rio grande do norte")) {
            txtSiglaEstado.setText("RN");
        } else if (nomeDoEstado.equalsIgnoreCase("rio grande do sul")) {
            txtSiglaEstado.setText("RS");
        } else if (nomeDoEstado.equalsIgnoreCase("rondonia")) {
            txtSiglaEstado.setText("RO");
        } else if (nomeDoEstado.equalsIgnoreCase("roraima")) {
            txtSiglaEstado.setText("RR");
        } else if (nomeDoEstado.equalsIgnoreCase("santa catarina")) {
            txtSiglaEstado.setText("SC");
        } else if (nomeDoEstado.equalsIgnoreCase("sao paulo")) {
            txtSiglaEstado.setText("SP");
        } else if (nomeDoEstado.equalsIgnoreCase("sergipe")) {
            txtSiglaEstado.setText("SE");
        } else if (nomeDoEstado.equalsIgnoreCase("tocantins")) {
            txtSiglaEstado.setText("TO");
        }
    }//GEN-LAST:event_txtNomeEstadoFocusLost

    private void txtNomeEstadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeEstadoKeyTyped

        String caracteres = "0987654321";// lista de caracters que não devem ser aceitos
        if (caracteres.contains(evt.getKeyChar() + "")) {// se o character que gerou o evento estiver na lista
            evt.consume();//aciona esse propriedade para eliminar a ação do evento
        }

    }//GEN-LAST:event_txtNomeEstadoKeyTyped

    private void tblListarEstadosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblListarEstadosFocusGained

    }//GEN-LAST:event_tblListarEstadosFocusGained
    private void acaoMouseClicked() {
        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/
        estadoDTO.setNomeEstadoDto("" + tblListarEstados.getValueAt(tblListarEstados.getSelectedRow(), 1));
        EstadoDTO modelo;
        try {
            modelo = estadoDAO.filtrarAoClicar(estadoDTO);

            if (modelo != null) {
                /**
                 * String.valueOf() pega um Valor Inteiro e transforma em String
                 * e seta em txtIDMedico que é um campo do tipo texto
                 */
                txtCodigo.setText(String.valueOf(modelo.getIdEtadoDto()));
                txtNomeEstado.setText(modelo.getNomeEstadoDto());
                txtSiglaEstado.setText(modelo.getSiglaEstadoDto());
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
                txtNomeEstado.setEnabled(false);
                txtSiglaEstado.setEnabled(false);

                //APAGAR APARTIR DAQUI
            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar()\n" + ex.getMessage());
        }

    }
    private void tblListarEstadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListarEstadosMouseClicked

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


    }//GEN-LAST:event_tblListarEstadosMouseClicked

    private void pesquisaEstados() {

        String pesquisarEstados = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisa.getText());

        DefaultTableModel model = (DefaultTableModel) tblListarEstados.getModel();

        ArrayList<EstadoDTO> list;

        try {

            list = (ArrayList<EstadoDTO>) estadoDAO.filtrarEstadosPesqRapida(pesquisarEstados);

            Object rowData[] = new Object[3];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdEtadoDto();
                rowData[1] = list.get(i).getNomeEstadoDto();
                rowData[2] = list.get(i).getSiglaEstadoDto();

                model.addRow(rowData);
            }
            tblListarEstados.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tblListarEstados.getColumnModel().getColumn(0).setPreferredWidth(33);
            tblListarEstados.getColumnModel().getColumn(1).setPreferredWidth(260);
            tblListarEstados.getColumnModel().getColumn(2).setPreferredWidth(33);
        } catch (Exception e) {
            e.printStackTrace();

            MetodoStaticosUtil.addMensagem(TelaEstados.this, e.getMessage());
        }
    }


    private void txtNomeEstadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeEstadoKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            txtSiglaEstado.setEnabled(true);
            txtSiglaEstado.setEditable(false);
            txtSiglaEstado.requestFocus();
            txtSiglaEstado.setBackground(Color.cyan);
        }
    }//GEN-LAST:event_txtNomeEstadoKeyPressed

    private void txtSiglaEstadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSiglaEstadoKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            String nomeEstado = txtNomeEstado.getText();
            String ufEstado = txtSiglaEstado.getText();

            try {
                estadoBO = new EstadoBO();

                /**
                 * Trabalhando com os retornos das validações
                 */
                if ((estadoBO.validaNome(nomeEstado)) == false) {
                    txtNomeEstado.setText("");
                } else {

                    if ((flag == 1) && (MetodoStaticosUtil.verificarDigitacaoUF(nomeEstado) == true)) {

                        estadoDTO.setNomeEstadoDto(nomeEstado);

                        boolean retornoVerifcaDuplicidade = estadoDAO.verificaDuplicidade(estadoDTO);

                        if (retornoVerifcaDuplicidade == false) {

                            estadoDTO.setNomeEstadoDto(nomeEstado);
                            estadoDTO.setSiglaEstadoDto(ufEstado);

                            estadoBO.cadastrarBO(estadoDTO);
                            /**
                             * Após salvar limpar os campos
                             */
                            txtNomeEstado.setText("");
                            txtSiglaEstado.setText("");
                            txtPesquisa.setText("");
                            /**
                             * Bloquear campos e Botões
                             */
                            txtNomeEstado.setEnabled(false);
                            txtSiglaEstado.setEnabled(false);
                            txtPesquisa.setEnabled(true);
                            /**
                             * Liberar campos necessário para operações após
                             * salvamento
                             */
                            btnSalvar.setEnabled(false);

                            btnAdicionar.setEnabled(true);

                            MetodoStaticosUtil.addMensagem(TelaEstados.this, "Registro Cadastrado com Sucesso!");
                            int numeroLinhas = tblListarEstados.getRowCount();

                            if (numeroLinhas > 0) {

                                while (tblListarEstados.getModel().getRowCount() > 0) {
                                    ((DefaultTableModel) tblListarEstados.getModel()).removeRow(0);

                                }

                                addRowJTable();

                            } else {
                                addRowJTable();
                            }

                        } else {
                            JOptionPane.showMessageDialog(TelaEstados.this, "Estado já cadastrado");
                            txtNomeEstado.setText("");
                            txtSiglaEstado.setText("");
                            txtPesquisa.setText("");
                        }

                    } else if ((flag == 1) && (MetodoStaticosUtil.verificarDigitacaoUF(nomeEstado) == false)) {

                        txtNomeEstado.setText("");
                        txtSiglaEstado.setText("");
                        JOptionPane.showMessageDialog(TelaEstados.this, "Não pertence a um Estado Brasileiro");
                    } else {

                        if (MetodoStaticosUtil.verificarDigitacaoUF(nomeEstado) == true) {

                            /**
                             * caso contrário em vez de ser adicionar é salvar
                             * edição
                             */
                            estadoDTO.setIdEtadoDto(Integer.parseInt(txtCodigo.getText()));
                            estadoDTO.setNomeEstadoDto(txtNomeEstado.getText());
                            estadoDTO.setSiglaEstadoDto(txtSiglaEstado.getText());
                            /**
                             * Chamando o método que irá executar a Edição dos
                             * Dados em nosso Banco de Dados
                             */
                            estadoBO.EditarBO(estadoDTO);

                            /**
                             * Após salvar limpar os campos
                             */
                            /**
                             * Após salvar limpar os campos
                             */
                            txtNomeEstado.setText("");
                            txtSiglaEstado.setText("");
                            txtPesquisa.setText("");

                            /**
                             * Bloquear campos e Botões
                             */
                            txtNomeEstado.setEnabled(false);
                            txtSiglaEstado.setEnabled(false);
                            txtPesquisa.setEnabled(true);

                            /**
                             * Liberar campos necessário para operações após
                             * salvamento
                             */
                            btnAdicionar.setEnabled(true);
                            btnCancelar.setEnabled(false);
                            btnSalvar.setEnabled(false);

                            MetodoStaticosUtil.addMensagem(TelaEstados.this, "Edição Salva com Sucesso!");
                        } else {

                            txtNomeEstado.setText("");
                            txtSiglaEstado.setText("");
                            JOptionPane.showMessageDialog(TelaEstados.this, "Após edição feita, verificou-se:\n 1-Que o estado Editado NÃO faz parte dos estados Brasileiros");
                        }
                    }
                }
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "yyErro Capturado:\n" + e.getMessage() + "\nPorfavor tire um Print e Entrar em Contado com o Desenvolvedor do Sistema\nAnalista:Tonis A. Torres Ferreira\nsisvenda2011@gmail.com");

            }

        }
    }//GEN-LAST:event_txtSiglaEstadoKeyPressed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instance = null;
    }//GEN-LAST:event_formInternalFrameClosed
    private void acaoBotaoPesquisar() {

        int numeroLinhas = tblListarEstados.getRowCount();

        if (numeroLinhas > 0) {

            while (tblListarEstados.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tblListarEstados.getModel()).removeRow(0);

            }

            pesquisaEstados();

        } else {
            addRowJTable();
        }

    }
    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed

    }//GEN-LAST:event_txtPesquisaKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBotoesManipulacaoBancoDados;
    private javax.swing.JPanel PanelCadEstados;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblCodigoEstado;
    private javax.swing.JLabel lblNomeEstado;
    private javax.swing.JLabel lblSiglaEstado;
    private javax.swing.JScrollPane scrTblEstado;
    private javax.swing.JTable tblListarEstados;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNomeEstado;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JTextField txtSiglaEstado;
    // End of variables declaration//GEN-END:variables
}
