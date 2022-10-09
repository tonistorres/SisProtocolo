package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.CidadeBO;
import br.com.subgerentepro.dao.CidadeDAO;
import br.com.subgerentepro.dao.EstadoDAO;
import br.com.subgerentepro.dto.CidadeDTO;
import br.com.subgerentepro.dto.EstadoDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteLayout;


public class TelaCidades extends javax.swing.JInternalFrame {
    
    CidadeDTO cidadeDTO = new CidadeDTO();
    CidadeDAO cidadeDAO = new CidadeDAO();
    CidadeBO cidadeBO = new CidadeBO();
    EstadoDAO estadoDAO = new EstadoDAO();
    EstadoDTO estadoDTO = new EstadoDTO();

    //https://www.youtube.com/watch?v=-ATbC-4rhc4
    //CRIANDO BOTAO ADICIONAR 
    JButton btnAdicionar = new JButton();
    JButton btnEditar = new JButton();
    JButton btnSalvar = new JButton();
    JButton btnExcluir = new JButton();
    JButton btnCancelar = new JButton();
    JButton btnPesquisar = new JButton();
    
    JTextField txtPesquisa = new JTextField();
    
    int flag = 0;

    // Chimura: A partir de agora, você usará esse método getInstance() toda vez que quiser criar um ViewEstado
    // Mesma coisa foi feita para ViewBairro
    // Também será necessário fazer para os outros
    private static TelaCidades instance = null;
    
    public static TelaCidades getInstance() {
        if (instance == null) {
            instance = new TelaCidades();// se instance igual a null então é criado uma instancia de ViewEstado
        }
        return instance;// em seguinda retornamos essa instancia qui por meio do padrão singleton
    }

    // Chimura: usado para checar se a ViewEstado já está aberta, no ato de abrí-la na ViewPrincipal
    public static boolean isOpen() {
        return instance != null;
    }
    
    public TelaCidades() {
        initComponents();
        addRowJTable();
        listarCombo();
        personalizarBotoesInternosDoForm();
        aoCarregarForm();

        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tblCidadesList.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tblCidadesList.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 

    }
    
    private void aoCarregarForm() {
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnAdicionar.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnPesquisar.setEnabled(true);
        
    }
    
    public void listarCombo() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        
        if (recebeConexao == true) {
            
            ArrayList<EstadoDTO> list;
            
            try {
                
                list = (ArrayList<EstadoDTO>) estadoDAO.listarTodos();
                
                ComboCidade.removeAllItems();
                for (int i = 0; i < list.size(); i++) {
                    
                    ComboCidade.addItem(list.get(i).getNomeEstadoDto());
                    
                }
                ComboCidade.setSelectedItem("MARANHAO");
            } catch (PersistenciaException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro Camada GUI:\n" + ex.getMessage());
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }
        
    }
    
    private void personalizarBotoesInternosDoForm() {
        //https://www.youtube.com/watch?v=-ATbC-4rhc4

        TheHandler th = new TheHandler();
        //ADICIONANDO AOS BOTOES AÇOES QUE SERAO TRATADAS PELO ACTIONLISLISTENER
        btnAdicionar.addActionListener(th);
        btnEditar.addActionListener(th);
        btnCancelar.addActionListener(th);
        btnExcluir.addActionListener(th);
        btnSalvar.addActionListener(th);
        btnPesquisar.addActionListener(th);
        
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
        btnPesquisar.setBounds(340, 130, 32, 35);
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        painelPrincipal.add(this.btnPesquisar);

        //CRIANDO CAMPO PESQUISA JTEXTFIELD
        txtPesquisa.setBounds(10, 135, 300, 30);
        painelPrincipal.add(txtPesquisa);
        
    }
    
    private void desabilitarTodosBotoes() {
        btnAdicionar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnPesquisar.setEnabled(false);
        btnSalvar.setEnabled(false);
    }
    
    private void desabilitarCampos() {
        txtCodigoCidade.setEnabled(false);
        txtNomeCidade.setEnabled(false);
        ComboCidade.setEnabled(false);
        txtPesquisa.setEnabled(false);
    }

    /**
     * Criando um método para adicionar linhas no JTable
     */
    public void addRowJTable() {
        
        DefaultTableModel model = (DefaultTableModel) tblCidadesList.getModel();
        
        ArrayList<CidadeDTO> list;

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        
        if (recebeConexao == true) {
            
            try {
                
                list = (ArrayList<CidadeDTO>) cidadeDAO.listarTodos();
                
                Object rowData[] = new Object[3];
                
                for (int i = 0; i < list.size(); i++) {
                    rowData[0] = list.get(i).getCodCidadeDto();
                    rowData[1] = list.get(i).getNomeCidadeDto();
                    rowData[2] = list.get(i).getSiglaEstadoRecuperarTable();
                    
                    model.addRow(rowData);
                }
                
                tblCidadesList.setModel(model);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tblCidadesList.getColumnModel().getColumn(0).setPreferredWidth(33);
                tblCidadesList.getColumnModel().getColumn(1).setPreferredWidth(260);
                tblCidadesList.getColumnModel().getColumn(2).setPreferredWidth(33);
                
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCidadesList = new javax.swing.JTable();
        lblCodigo = new javax.swing.JLabel();
        txtCodigoCidade = new javax.swing.JTextField();
        txtNomeCidade = new javax.swing.JTextField();
        lblCidadeNome = new javax.swing.JLabel();
        ComboCidade = new javax.swing.JComboBox<String>();
        lblEstados = new javax.swing.JLabel();
        PanelBotoesManipulacaoBancoDados = new javax.swing.JPanel();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(475, 504));
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

        painelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        painelPrincipal.setLayout(null);

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        tblCidadesList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CIDADE", "UF"
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
        tblCidadesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCidadesListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCidadesList);

        painelPrincipal.add(jScrollPane1);
        jScrollPane1.setBounds(10, 170, 440, 290);

        lblCodigo.setText("Codigo:");
        painelPrincipal.add(lblCodigo);
        lblCodigo.setBounds(10, 70, 50, 14);

        txtCodigoCidade.setBackground(new java.awt.Color(255, 255, 204));
        txtCodigoCidade.setDisabledTextColor(new java.awt.Color(51, 51, 255));
        txtCodigoCidade.setEnabled(false);
        painelPrincipal.add(txtCodigoCidade);
        txtCodigoCidade.setBounds(10, 90, 40, 30);

        txtNomeCidade.setBackground(new java.awt.Color(255, 255, 204));
        txtNomeCidade.setDisabledTextColor(new java.awt.Color(51, 51, 255));
        txtNomeCidade.setEnabled(false);
        txtNomeCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeCidadeActionPerformed(evt);
            }
        });
        txtNomeCidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeCidadeKeyPressed(evt);
            }
        });
        painelPrincipal.add(txtNomeCidade);
        txtNomeCidade.setBounds(70, 90, 250, 30);

        lblCidadeNome.setText("Cidade:");
        painelPrincipal.add(lblCidadeNome);
        lblCidadeNome.setBounds(80, 70, 60, 14);

        ComboCidade.setBackground(new java.awt.Color(255, 255, 204));
        ComboCidade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboCidade.setEnabled(false);
        ComboCidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComboCidadeKeyPressed(evt);
            }
        });
        painelPrincipal.add(ComboCidade);
        ComboCidade.setBounds(330, 90, 110, 30);

        lblEstados.setText("Estados:");
        painelPrincipal.add(lblEstados);
        lblEstados.setBounds(330, 70, 60, 14);

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

        painelPrincipal.add(PanelBotoesManipulacaoBancoDados);
        PanelBotoesManipulacaoBancoDados.setBounds(0, 0, 460, 47);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                .addContainerGap())
        );

        setBounds(600, 150, 472, 510);
    }// </editor-fold>//GEN-END:initComponents

    private void acaoBotaoPesquisar() {
        try {
            
            int numeroLinhas = tblCidadesList.getRowCount();
            
            if (numeroLinhas > 0) {
                
                while (tblCidadesList.getModel().getRowCount() > 0) {
                    ((DefaultTableModel) tblCidadesList.getModel()).removeRow(0);
                    
                }
                
                pesquisaCidades();
            } else {
                addRowJTable();
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void pesquisaCidades() {
        
        String pesquisarCidades = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisa.getText());
        
        DefaultTableModel model = (DefaultTableModel) tblCidadesList.getModel();
        
        ArrayList<CidadeDTO> list;
        
        try {
            
            list = (ArrayList<CidadeDTO>) cidadeDAO.filtrarCidadesPesqRapidaUmFiltroApenas(pesquisarCidades);
            
            Object rowData[] = new Object[3];
            
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getCodCidadeDto();
                rowData[1] = list.get(i).getNomeCidadeDto();
                rowData[2] = list.get(i).getSiglaEstadoRecuperarTable();
                
                model.addRow(rowData);
            }
            
            tblCidadesList.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tblCidadesList.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblCidadesList.getColumnModel().getColumn(1).setPreferredWidth(260);
            tblCidadesList.getColumnModel().getColumn(2).setPreferredWidth(40);
            
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }
    }
    
    private void acaoMouseClicked() {

        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/
        int codigo = Integer.parseInt("" + tblCidadesList.getValueAt(tblCidadesList.getSelectedRow(), 0));

        /**
         * Esse código está comentado só para ficar o exemplo de como pegaria o
         * valor de nome da tabela ou seja coluna 1 sendo que falando neses caso
         * trabalhamos como vetor que inicial do zero(0)
         */
        /*   cidadeDTO.setNomeCidadeDto("" + tblCidadesList.getValueAt(tblCidadesList.getSelectedRow(), 1));*/
        try {
            
            CidadeDTO retorno = cidadeDAO.buscarPorIdTblConsultaList(codigo);
            
            if (retorno.getCodCidadeDto() != null || !retorno.getCodCidadeDto().equals("")) {
                /**
                 * String.valueOf() pega um Valor Inteiro e transforma em String
                 * e seta em txtIDMedico que é um campo do tipo texto
                 */
                txtCodigoCidade.setText(String.valueOf(retorno.getCodCidadeDto()));
                txtNomeCidade.setText(retorno.getNomeCidadeDto());
                txtPesquisa.setText(retorno.getSiglaEstadoRecuperarTable());
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
                txtNomeCidade.setEnabled(false);
                ComboCidade.setEnabled(false);

                //APAGAR APARTIR DAQUI
            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar()\n" + ex.getMessage());
        }
        
    }

    private void tblCidadesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCidadesListMouseClicked
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
        

    }//GEN-LAST:event_tblCidadesListMouseClicked

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
    }//GEN-LAST:event_jScrollPane1MouseClicked
    
    private void acaoBotaoCancelar() {

        /**
         * Após salvar limpar os campos
         */
        txtCodigoCidade.setText("");
        txtNomeCidade.setText("");
        ComboCidade.setSelectedItem("");
        txtPesquisa.setText("");
        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        txtCodigoCidade.setEnabled(false);
        txtNomeCidade.setEnabled(false);
        ComboCidade.setEnabled(false);
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
    
    private void salvar() {
        
        String nomeCidade = txtNomeCidade.getText();
        String ufEstado = (String) ComboCidade.getSelectedItem();
        
        try {
            cidadeBO = new CidadeBO();

            /**
             * Trabalhando com os retornos das validações
             */
            if ((cidadeBO.validaNome(nomeCidade)) == false) {
                txtNomeCidade.setText("");
            } else {
                
                if ((flag == 1)) {
                    
                    cidadeDTO.setNomeCidadeDto(nomeCidade);
                    
                    boolean retornoVerifcaDuplicidade = cidadeDAO.verificaDuplicidade(cidadeDTO);
                    
                    if (retornoVerifcaDuplicidade == false) {
                        
                        cidadeDTO.setNomeCidadeDto(nomeCidade);

                        // JOptionPane.showMessageDialog(null, "Camada gui" + ufEstado);
                        estadoDTO.setNomeEstadoDto(ufEstado);
                        
                        EstadoDTO retorno = estadoDAO.ComboCidadeSetarParaEviarBanco(estadoDTO);

                        //   JOptionPane.showMessageDialog(null, "Retornado ok Método:" + retorno.getIdEtadoDto());
                        cidadeDTO.setChaveEstrangeiraIdEstadoDto(retorno.getIdEtadoDto());
                        
                        cidadeBO.cadastrarBO(cidadeDTO);
                        /**
                         * Após salvar limpar os campos
                         */
                        txtNomeCidade.setText("");
                        txtCodigoCidade.setText("");
                        ComboCidade.setSelectedItem("");
                        /**
                         * Bloquear campos e Botões
                         */
                        txtCodigoCidade.setEnabled(false);
                        txtNomeCidade.setEnabled(false);
                        ComboCidade.setEnabled(false);
                        txtPesquisa.setEnabled(true);
                        /**
                         * Liberar campos necessário para operações após
                         * salvamento
                         */
                        btnSalvar.setEnabled(false);
                        
                        btnAdicionar.setEnabled(true);
                        
                        MetodoStaticosUtil.addMensagem(TelaCidades.this, "Registro Cadastrado com Sucesso!");
                        int numeroLinhas = tblCidadesList.getRowCount();
                        
                        if (numeroLinhas > 0) {
                            
                            while (tblCidadesList.getModel().getRowCount() > 0) {
                                ((DefaultTableModel) tblCidadesList.getModel()).removeRow(0);
                                
                            }
                            
                            addRowJTable();
                            
                        } else {
                            addRowJTable();
                            
                        }
                        
                    } else {
                        JOptionPane.showMessageDialog(TelaCidades.this, "Resgistro já cadastrado.\n"
                                + "o Sistema Impossibilitou \n a Duplicidade");
                        /**
                         * Limpando campos após o evento de travamento da
                         * duplicidade
                         */
                        
                        txtCodigoCidade.setText("");
                        txtNomeCidade.setText("");
                        txtPesquisa.setText("");

                        /**
                         * Disabilitando Campos
                         */
                        txtNomeCidade.setEnabled(false);
                        btnSalvar.setEnabled(false);
                        btnExcluir.setEnabled(false);
                        ComboCidade.setEnabled(false);
                        
                        txtPesquisa.setEnabled(true);
                        
                    }
                    
                } else if ((flag == 1)) {
                    
                    txtCodigoCidade.setText("");
                    txtNomeCidade.setText("");
                    ComboCidade.setSelectedItem("");
                    JOptionPane.showMessageDialog(TelaCidades.this, "Não pertence a um Estado Brasileiro");
                } else {
                    
                    cidadeDTO.setCodCidadeDto(Integer.parseInt(txtCodigoCidade.getText()));
                    cidadeDTO.setNomeCidadeDto(txtNomeCidade.getText());
                    
                    estadoDTO.setNomeEstadoDto(ufEstado);
                    EstadoDTO retorno = estadoDAO.ComboCidadeSetarParaEviarBanco(estadoDTO);
                    
                    cidadeDTO.setChaveEstrangeiraIdEstadoDto(retorno.getIdEtadoDto());
                    /**
                     * Chamando o método que irá executar a Edição dos Dados em
                     * nosso Banco de Dados
                     */
                    cidadeBO.EditarBO(cidadeDTO);

                    /**
                     * Após salvar limpar os campos
                     */
                    txtCodigoCidade.setText("");
                    txtNomeCidade.setText("");
                    ComboCidade.setSelectedItem("");
                    txtPesquisa.setText("");

                    /**
                     * Bloquear campos e Botões
                     */
                    txtCodigoCidade.setEnabled(false);
                    txtNomeCidade.setEnabled(false);
                    ComboCidade.setEnabled(false);
                    txtPesquisa.setEnabled(true);

                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    btnAdicionar.setEnabled(true);
                    btnCancelar.setEnabled(false);
                    btnSalvar.setEnabled(false);
                    
                    MetodoStaticosUtil.addMensagem(TelaCidades.this, "Edição Salva com Sucesso!");
                    int numeroLinhas = tblCidadesList.getRowCount();
                    
                    if (numeroLinhas > 0) {
                        
                        while (tblCidadesList.getModel().getRowCount() > 0) {
                            ((DefaultTableModel) tblCidadesList.getModel()).removeRow(0);
                            
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
            salvar();
            
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }
        
    }
    
    private void acaoBotadoEditar() {
        if (txtNomeCidade.equals("")) {
            
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
            txtNomeCidade.setEnabled(true);
            ComboCidade.setEnabled(true);
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
    
    private void acaoBotaoAdicionar() {
        flag = 1;

        /**
         * Campos devem ser ativados
         */
        txtNomeCidade.setEnabled(true);
        ComboCidade.setEnabled(true);
        /**
         * Limpar os campos para cadastrar
         */
        txtNomeCidade.setText("");
        ComboCidade.setSelectedItem("");
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
        
        txtNomeCidade.requestFocus();//colocar foco no campo Nome da Cidade
        txtNomeCidade.setBackground(Color.CYAN);  // altera a cor do fundo

    }
    
    private void acaoBotaoExcluir() {
        int resposta = 0;
        resposta = JOptionPane.showConfirmDialog(rootPane, "Deseja Excluir Resgistro?");
        
        try {
            if (resposta == JOptionPane.YES_OPTION) {
                cidadeDTO.setCodCidadeDto(Integer.parseInt(txtCodigoCidade.getText()));
                /**
                 * Chamando o método que irá executar a Edição dos Dados em
                 * nosso Banco de Dados
                 */
                cidadeBO.ExcluirBO(cidadeDTO);
                /**
                 * Após salvar limpar os campos
                 */
                txtNomeCidade.setText("");
                ComboCidade.setSelectedItem("");
                txtPesquisa.setText("");
                btnExcluir.setEnabled(false);
                btnEditar.setEnabled(false);
                
                int numeroLinhas = tblCidadesList.getRowCount();
                
                if (numeroLinhas > 0) {
                    
                    while (tblCidadesList.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tblCidadesList.getModel()).removeRow(0);
                        
                    }
                    
                    addRowJTable();
                    
                } else {
                    addRowJTable();
                    
                }
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
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

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instance = null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void txtNomeCidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeCidadeKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            
            ComboCidade.setEnabled(true);
            ComboCidade.setEditable(false);
            ComboCidade.requestFocus();
            ComboCidade.setBackground(Color.CYAN);
        }
    }//GEN-LAST:event_txtNomeCidadeKeyPressed

    private void ComboCidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboCidadeKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            salvar();
        }
    }//GEN-LAST:event_ComboCidadeKeyPressed

    private void txtNomeCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeCidadeActionPerformed
    
    private class TheHandler implements ActionListener {
        
        public void actionPerformed(ActionEvent evt) {
            
            if (evt.getSource() == btnExcluir) {
                acaoBotaoExcluir();
            }
            if (evt.getSource() == btnAdicionar) {
                acaoBotaoAdicionar();
            }
            
            if (evt.getSource() == btnEditar) {
                acaoBotadoEditar();
            }
            
            if (evt.getSource() == btnSalvar) {
                acaoBotaoSalvar();
            }
            
            if (evt.getSource() == btnCancelar) {
                acaoBotaoCancelar();
            }
            
            if (evt.getSource() == btnPesquisar) {
                acaoBotaoPesquisar();
            }
            
        }
        
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboCidade;
    private javax.swing.JPanel PanelBotoesManipulacaoBancoDados;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCidadeNome;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblEstados;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JTable tblCidadesList;
    private javax.swing.JTextField txtCodigoCidade;
    private javax.swing.JTextField txtNomeCidade;
    // End of variables declaration//GEN-END:variables
}
