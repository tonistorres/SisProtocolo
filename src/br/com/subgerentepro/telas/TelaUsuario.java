package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.UsuarioBO;
import br.com.subgerentepro.componentes.ComponentesFormTelaUsuario;
import br.com.subgerentepro.dao.SetorTramiteInternoDAO;
import br.com.subgerentepro.dao.UsuarioDAO;
import br.com.subgerentepro.dto.ItensDoProtocoloTFDDTO;
import br.com.subgerentepro.dto.SetorTramiteInternoDTO;
import br.com.subgerentepro.dto.UsuarioDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author DaTorres
 */
public class TelaUsuario extends javax.swing.JInternalFrame {

    UsuarioDTO usuarioDTO = new UsuarioDTO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    UsuarioBO usuarioBO = new UsuarioBO();
    ComponentesFormTelaUsuario componente = new ComponentesFormTelaUsuario();
    SetorTramiteInternoDTO setorInternoDTO = new SetorTramiteInternoDTO();
    SetorTramiteInternoDAO setorInternoDAO = new SetorTramiteInternoDAO();

    int flag = 0;

    /**
     * Código Mestre Chimura
     */
    private static TelaUsuario instance = null;

    public static TelaUsuario getInstance() {

        if (instance == null) {

            instance = new TelaUsuario();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public TelaUsuario() {
        initComponents();
        aoCarregarForm();
        addRowJTable();
        personalizarBotoesInternosDoForm();
        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tblUsuarios.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tblUsuarios.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 

    }

    public void listarCombo() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            ArrayList<SetorTramiteInternoDTO> list;

            try {

                list = (ArrayList<SetorTramiteInternoDTO>) setorInternoDAO.listarTodos();

                cbPerfilUsuarios.removeAllItems();
                for (int i = 0; i < list.size(); i++) {

                    cbPerfilUsuarios.addItem(list.get(i).getNomeDto());

                }
                cbPerfilUsuarios.setSelectedItem(null);
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

    public void aoCarregarForm() {

        this.txtUsuario.setEnabled(false);
        this.txtLogin.setEnabled(false);
        this.txtSenhaPS.setEnabled(false);
        this.txtCelular.setEnabled(false);
        this.cbPerfilUsuarios.setEnabled(false);
        this.cbPerfilUsuarios.setEnabled(false);
        this.txtGmail.setEnabled(false);
        this.cbSexo.setEnabled(false);
        this.cbSexo.setSelectedItem(null);

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tblUsuarios.getModel();

        ArrayList<UsuarioDTO> list;

        try {

            list = (ArrayList<UsuarioDTO>) usuarioDAO.listarTodos();

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[5];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdUserDto();
                rowData[1] = list.get(i).getLoginDto();
                rowData[2] = list.get(i).getTelefoneDto();
                rowData[3] = list.get(i).getPerfilDto();
                rowData[4] = list.get(i).getSenhaDto();

                model.addRow(rowData);
            }

            tblUsuarios.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(75);
            tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(70);
            tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(450);
            tblUsuarios.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblUsuarios.getColumnModel().getColumn(4).setPreferredWidth(70);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void personalizarBotoesInternosDoForm() {

        componente.personalizaBotoesFormTelaUsuario(btnAdicionar, btnEditar, btnSalvar, btnExcluir, btnPesquisar, btnCancelar);
    }

    private void pesquisarUsuario() {
        String pesquisarUsuario = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisa.getText());

        DefaultTableModel model = (DefaultTableModel) tblUsuarios.getModel();

        ArrayList<UsuarioDTO> list;

        try {

            list = (ArrayList<UsuarioDTO>) usuarioDAO.filtrarUsuarioPesqRapida(pesquisarUsuario);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[5];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdUserDto();
                rowData[1] = list.get(i).getLoginDto();
                rowData[2] = list.get(i).getTelefoneDto();
                rowData[3] = list.get(i).getPerfilDto();
                rowData[4] = list.get(i).getSenhaDto();

                model.addRow(rowData);
            }

            tblUsuarios.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(75);
            tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(70);
            tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(450);
            tblUsuarios.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblUsuarios.getColumnModel().getColumn(4).setPreferredWidth(70);

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void salvar() {

        /**
         * capturando os campos do Form na Camada Gui e em vez de adicionar ha
         * uma variável encapsulamos e setamos como o método set
         */
        usuarioDTO.setUsuarioDto(txtUsuario.getText());
        usuarioDTO.setLoginDto(txtLogin.getText());
        /**
         * Observação:Essa é a forma de captuar do form gui um campo do tipo
         * senha para salva-lo num banco de dados como uma string
         */
        usuarioDTO.setSenhaDto(new String(txtSenhaPS.getPassword()));

        // Observação: essa é a forma de capturar um campo do tipo Caixa de combinaçao 
        if (cbPerfilUsuarios.getSelectedItem() != null) {
            usuarioDTO.setPerfilDto((String) cbPerfilUsuarios.getSelectedItem());
         
            
        }
        if (cbSexo.getSelectedItem() != null) {
            usuarioDTO.setSexoDto((String) cbSexo.getSelectedItem());
        }

        usuarioDTO.setTelefoneDto(txtCelular.getText());
        usuarioDTO.setGmailDto(txtGmail.getText());

        try {
            /**
             * Depois de capturados e atribuídos seus respectivos valores
             * capturados nas variáveis acimas descrita. Iremos criar um objeto
             * do tipo UsuarioBO
             */
            usuarioBO = new UsuarioBO();

            /**
             * Trabalhando com os retornos das validações
             */
            if ((usuarioBO.validaNome(usuarioDTO)) == false) {
                txtUsuario.setText("");

            } else {

                if ((flag == 1)) {

                    usuarioDTO.setUsuarioDto(txtUsuario.getText());

                    boolean retornoVerifcaDuplicidade = usuarioDAO.verificaDuplicidade(usuarioDTO);

                    if (retornoVerifcaDuplicidade == false) {

                        usuarioBO.cadastrar(usuarioDTO);
                        /**
                         * Após salvar limpar os campos
                         */
                        txtUsuario.setText("");
                        txtLogin.setText("");
                        txtSenhaPS.setText("");
                        txtCelular.setText("");
                        cbPerfilUsuarios.setSelectedItem("");
                        /**
                         * Bloquear campos e Botões
                         */
                        txtIDUsuario.setEnabled(false);
                        txtUsuario.setEnabled(false);
                        txtLogin.setEnabled(false);
                        txtCelular.setEnabled(false);
                        cbPerfilUsuarios.setEnabled(false);
                        txtSenhaPS.setEnabled(false);
                        txtPesquisa.setEnabled(true);
                        /**
                         * Liberar campos necessário para operações após
                         * salvamento
                         */
                        btnSalvar.setEnabled(false);

                        btnAdicionar.setEnabled(true);

                        
                        lblLinhaInformativa.setText("Resgistro Cadastrado com Sucesso");
                        Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 
                        lblLinhaInformativa.setFont(f);
                        lblLinhaInformativa.setForeground(Color.getHSBColor(51, 153, 255));
                        
                        txtGmail.setText("");
                        txtGmail.setEnabled(false);
                        cbSexo.setEnabled(false);
                        cbSexo.setSelectedItem(null);
                        
                        
                                
                        //MensagensUtil.add(TelaUsuarios.this, "Registro Cadastrado com Sucesso!");
                        JOptionPane.showMessageDialog(null, "Registro Cadastrado com Sucesso!");
                        /**
                         * Zera a Linha informativa criada para esse Sistema
                         */
                        
                                
                        
                        
                        int numeroLinhas = tblUsuarios.getRowCount();

                        if (numeroLinhas > 0) {

                            while (tblUsuarios.getModel().getRowCount() > 0) {
                                ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);

                            }

                            addRowJTable();

                        } else {
                            addRowJTable();

                        }

                    } else {

                        txtLogin.requestFocus();
                        txtLogin.setBackground(Color.YELLOW);
                        lblLinhaInformativa.setText("Verificação efetuada, Login já cadastrado no Sistema");
                        lblLinhaInformativa.setForeground(Color.RED);

                    }

                } else {

                    /**
                     * Caso não seja um novo registro equivale dizer que é uma
                     * edição então executará esse código flag será !=1
                     */
                    usuarioDTO.setIdUserDto(Integer.parseInt(txtIDUsuario.getText()));
                    /**
                     * capturando os campos do Form na Camada Gui e em vez de
                     * adicionar ha uma variável encapsulamos e setamos como o
                     * método set
                     */
                    usuarioDTO.setUsuarioDto(txtUsuario.getText());
                    usuarioDTO.setLoginDto(txtLogin.getText());
                    /**
                     * Observação:Essa é a forma de captuar do form gui um campo
                     * do tipo senha para salva-lo num banco de dados como uma
                     * string
                     */
                    usuarioDTO.setSenhaDto(new String(txtSenhaPS.getPassword()));

                    // Observação: essa é a forma de capturar um campo do tipo Caixa de combinaçao 
                    if (cbPerfilUsuarios.getSelectedItem() != null) {
                        usuarioDTO.setPerfilDto((String) cbPerfilUsuarios.getSelectedItem());
                       
                    }
                    if (cbSexo.getSelectedItem() != null) {
                        usuarioDTO.setSexoDto((String) cbSexo.getSelectedItem());
                    }

                    usuarioDTO.setTelefoneDto(txtCelular.getText());
                    usuarioDTO.setGmailDto(txtGmail.getText());

                    /**
                     * Chamando o método que irá executar a Edição dos Dados em
                     * nosso Banco de Dados
                     */
                    usuarioBO.atualizarBO(usuarioDTO);

                    /**
                     * Após salvar limpar os campos
                     */
                    txtIDUsuario.setText("");
                    txtUsuario.setText("");
                    txtLogin.setText("");
                    txtCelular.setText("");
                    cbPerfilUsuarios.setSelectedItem(null);
                    txtSenhaPS.setText("");
                    txtPesquisa.setText("");
                    cbSexo.setSelectedItem(null);
                    txtGmail.setText("");
                    /**
                     * Bloquear campos e Botões
                     */
                    txtIDUsuario.setEnabled(false);
                    txtUsuario.setEnabled(false);
                    txtLogin.setEnabled(false);
                    txtCelular.setEnabled(false);
                    cbPerfilUsuarios.setEnabled(false);
                    txtSenhaPS.setEnabled(false);
                    txtPesquisa.setEnabled(true);
                    txtGmail.setEnabled(false);
                    cbSexo.setEnabled(false);

                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    btnAdicionar.setEnabled(true);
                    btnCancelar.setEnabled(false);
                    btnSalvar.setEnabled(false);

//                    MensagensUtil.add(TelaUsuarios.this, "Edição Salva com Sucesso!");
                    JOptionPane.showMessageDialog(null, "Edição salva  com Sucesso!");
                    int numeroLinhas = tblUsuarios.getRowCount();

                    if (numeroLinhas > 0) {

                        while (tblUsuarios.getModel().getRowCount() > 0) {
                            ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);

                        }

                        addRowJTable();

                    } else {
                        addRowJTable();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada GUI: " + e.getMessage());

            if (e.getMessage().equals("Campo nome Obrigatório")) {
                txtUsuario.requestFocus();
                txtUsuario.setBackground(Color.RED);
            }

            if (e.getMessage().equals("Campo nome aceita no MAX 50 chars")) {
                txtUsuario.requestFocus();
                txtUsuario.setBackground(Color.RED);
                lblLinhaInformativa.setText("Campo aceita no Máximo a digitação de 50 caracteres");

            }

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelDadosUsuario = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        txtSenhaPS = new javax.swing.JPasswordField();
        lblPerfil = new javax.swing.JLabel();
        cbPerfilUsuarios = new javax.swing.JComboBox<String>();
        lblCelular = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        PanelJTable = new javax.swing.JPanel();
        lblID = new javax.swing.JLabel();
        txtIDUsuario = new javax.swing.JTextField();
        lblLinhaInformativa = new javax.swing.JLabel();
        txtCelular = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnPesquisar = new javax.swing.JButton();
        cbSexo = new javax.swing.JComboBox();
        lblSexo = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        lblPesquisaRapida = new javax.swing.JLabel();
        txtGmail = new javax.swing.JTextField();
        lblImgGmail = new javax.swing.JLabel();
        PanelBotoesManipulacaoBancoDados = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();

        setClosable(true);
        setTitle("Usuário");

        PanelDadosUsuario.setBackground(java.awt.Color.white);
        PanelDadosUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Usuário:"));
        PanelDadosUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUsuario.setText("Nome:");
        PanelDadosUsuario.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        lblLogin.setText("Login:");
        PanelDadosUsuario.add(lblLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        txtLogin.setBackground(new java.awt.Color(255, 255, 204));
        txtLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLoginKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLoginKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 100, 30));

        lblSenha.setText("Senha:");
        PanelDadosUsuario.add(lblSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 50, -1));

        txtSenhaPS.setBackground(new java.awt.Color(255, 255, 204));
        txtSenhaPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaPSKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSenhaPSKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtSenhaPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 112, 30));

        lblPerfil.setText("Perfil:");
        PanelDadosUsuario.add(lblPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, -1, -1));

        cbPerfilUsuarios.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cbPerfilUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPerfilUsuariosActionPerformed(evt);
            }
        });
        cbPerfilUsuarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbPerfilUsuariosKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(cbPerfilUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, 180, 30));

        lblCelular.setText("Celular:");
        PanelDadosUsuario.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        txtUsuario.setBackground(new java.awt.Color(255, 255, 204));
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 390, 30));

        PanelJTable.setLayout(new java.awt.GridLayout(1, 0));
        PanelDadosUsuario.add(PanelJTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 164, 481, -1));

        lblID.setText("ID:");
        PanelDadosUsuario.add(lblID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        txtIDUsuario.setBackground(new java.awt.Color(255, 255, 204));
        txtIDUsuario.setEnabled(false);
        PanelDadosUsuario.add(txtIDUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 35, 30));

        lblLinhaInformativa.setForeground(new java.awt.Color(51, 153, 255));
        lblLinhaInformativa.setText("Linha Informativa");
        PanelDadosUsuario.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 440, -1));

        txtCelular.setBackground(new java.awt.Color(255, 255, 204));
        try {
            txtCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCelular.setPreferredSize(new java.awt.Dimension(6, 30));
        txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCelularKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelularKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 100, -1));

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "LOGIN", "CELULAR", "PERFIL", "SENHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);
        if (tblUsuarios.getColumnModel().getColumnCount() > 0) {
            tblUsuarios.getColumnModel().getColumn(0).setMinWidth(40);
            tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblUsuarios.getColumnModel().getColumn(0).setMaxWidth(40);
            tblUsuarios.getColumnModel().getColumn(1).setMinWidth(80);
            tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(80);
            tblUsuarios.getColumnModel().getColumn(1).setMaxWidth(80);
            tblUsuarios.getColumnModel().getColumn(2).setMinWidth(180);
            tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(180);
            tblUsuarios.getColumnModel().getColumn(2).setMaxWidth(180);
            tblUsuarios.getColumnModel().getColumn(3).setMinWidth(80);
            tblUsuarios.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblUsuarios.getColumnModel().getColumn(3).setMaxWidth(80);
            tblUsuarios.getColumnModel().getColumn(4).setMinWidth(70);
            tblUsuarios.getColumnModel().getColumn(4).setPreferredWidth(70);
            tblUsuarios.getColumnModel().getColumn(4).setMaxWidth(70);
        }

        PanelDadosUsuario.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 450, 140));

        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });
        PanelDadosUsuario.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 209, -1, -1));

        cbSexo.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cbSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MASCULINO", "FEMININO" }));
        PanelDadosUsuario.add(cbSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 100, 30));

        lblSexo.setText("Sexo:");
        PanelDadosUsuario.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, -1, -1));

        txtPesquisa.setBackground(new java.awt.Color(51, 153, 255));
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(txtPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 370, 35));

        lblPesquisaRapida.setText("Pesquisa Rápida:");
        PanelDadosUsuario.add(lblPesquisaRapida, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        txtGmail.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        PanelDadosUsuario.add(txtGmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 170, 30));

        lblImgGmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/gmail.png"))); // NOI18N
        PanelDadosUsuario.add(lblImgGmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, -1, -1));

        PanelBotoesManipulacaoBancoDados.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.disabledShadow"));

        btnCancelar.setToolTipText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalvar.setToolTipText("Gravar Registro");
        btnSalvar.setEnabled(false);
        btnSalvar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnSalvarFocusGained(evt);
            }
        });
        btnSalvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalvarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalvarMouseExited(evt);
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

        btnEditar.setToolTipText("Editar Registro");
        btnEditar.setEnabled(false);
        btnEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarMouseExited(evt);
            }
        });
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnAdicionar.setToolTipText("Adicionar Registro");
        btnAdicionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAdicionarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAdicionarMouseExited(evt);
            }
        });
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnExcluir.setToolTipText("Excluir Registro");
        btnExcluir.setEnabled(false);
        btnExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcluirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcluirMouseExited(evt);
            }
        });
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelBotoesManipulacaoBancoDadosLayout = new javax.swing.GroupLayout(PanelBotoesManipulacaoBancoDados);
        PanelBotoesManipulacaoBancoDados.setLayout(PanelBotoesManipulacaoBancoDadosLayout);
        PanelBotoesManipulacaoBancoDadosLayout.setHorizontalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdicionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar)
                .addContainerGap())
        );
        PanelBotoesManipulacaoBancoDadosLayout.setVerticalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdicionar)
                    .addComponent(btnEditar)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar)
                    .addComponent(btnExcluir))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelBotoesManipulacaoBancoDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelDadosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 22, Short.MAX_VALUE)
                .addComponent(PanelBotoesManipulacaoBancoDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE)
                .addComponent(PanelDadosUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            txtSenhaPS.requestFocus();

        }
    }//GEN-LAST:event_txtLoginKeyPressed

    private void txtLoginKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyTyped
        /**
         * Analista de Sistemas: Tonis Alberto Torres Ferreira
         * Contribuição:https://www.youtube.com/watch?v=nZCC8fnNfyI Erro:
         * Criando rotinas para controle de erros de usuários mensagem para
         * controlar o máximo de caracter que dever ser lançado para o Banco de
         * Dados. Deste modo evitamos a possibilidade de erro quando o usuário
         * tentar gravar no banco de dados um dado com um numero de caracter
         * maior que o permitido no campo do Banco de Dados evitando o disparo
         * de uma throw exceptions
         */

        /**
         * criamos uma variável de controle do tipo primitivo int como o númeo
         * de caracter de acordo com o especificado no Banco de Dados. Em sguida
         * colocamos uma estrutura de controle onde mostramos o campo que
         * recebera o codigo e acionamos a propriedade length (tamanho) e
         * indicamos o numero de caracter máximo aceito
         */
        int numeroDeCaracter = 10;

        if (txtLogin.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute
            JOptionPane.showMessageDialog(null, "Máximo 10 caracteres");// colocamos uma mensagem alertar usuário
            txtLogin.setBackground(Color.CYAN);//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Login: Máximo 10 caracteres");
        }
    }//GEN-LAST:event_txtLoginKeyTyped

    private void txtSenhaPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaPSKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            cbPerfilUsuarios.requestFocus();

        }
    }//GEN-LAST:event_txtSenhaPSKeyPressed

    private void txtSenhaPSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaPSKeyTyped
        /**
         * Analista de Sistemas: Tonis Alberto Torres Ferreira
         * Contribuição:https://www.youtube.com/watch?v=nZCC8fnNfyI Erro:
         * Criando rotinas para controle de erros de usuários mensagem para
         * controlar o máximo de caracter que dever ser lançado para o Banco de
         * Dados. Deste modo evitamos a possibilidade de erro quando o usuário
         * tentar gravar no banco de dados um dado com um numero de caracter
         * maior que o permitido no campo do Banco de Dados evitando o disparo
         * de uma throw exceptions
         */

        /**
         * criamos uma variável de controle do tipo primitivo int como o númeo
         * de caracter de acordo com o especificado no Banco de Dados. Em sguida
         * colocamos uma estrutura de controle onde mostramos o campo que
         * recebera o codigo e acionamos a propriedade length (tamanho) e
         * indicamos o numero de caracter máximo aceito
         */
        int numeroDeCaracter = 8;

        if (txtSenhaPS.getPassword().length > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute
            JOptionPane.showMessageDialog(null, "Máxmimo 8 caracteres");// colocamos uma mensagem alertar usuário
            txtSenhaPS.setBackground(Color.CYAN);//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Senha: Máximo 8 caracteres");
        }
    }//GEN-LAST:event_txtSenhaPSKeyTyped

    private void cbPerfilUsuariosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPerfilUsuariosKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            txtCelular.requestFocus();

        }
    }//GEN-LAST:event_cbPerfilUsuariosKeyPressed

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            txtLogin.requestFocus();

        }
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped
        /**
         * Analista de Sistemas: Tonis Alberto Torres Ferreira
         * Contribuição:https://www.youtube.com/watch?v=nZCC8fnNfyI Erro:
         * Criando rotinas para controle de erros de usuários mensagem para
         * controlar o máximo de caracter que dever ser lançado para o Banco de
         * Dados. Deste modo evitamos a possibilidade de erro quando o usuário
         * tentar gravar no banco de dados um dado com um numero de caracter
         * maior que o permitido no campo do Banco de Dados evitando o disparo
         * de uma throw exceptions
         */

        /**
         * criamos uma variável de controle do tipo primitivo int como o númeo
         * de caracter de acordo com o especificado no Banco de Dados. Em sguida
         * colocamos uma estrutura de controle onde mostramos o campo que
         * recebera o codigo e acionamos a propriedade length (tamanho) e
         * indicamos o numero de caracter máximo aceito
         */
        int numeroDeCaracter = 50;

        if (txtUsuario.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute
            JOptionPane.showMessageDialog(null, "Máxmimo 50 caracter");// colocamos uma mensagem alertar usuário
            txtUsuario.setBackground(Color.CYAN);//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Nome: Máximo 50 caracteres");

        }
    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void txtCelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            btnSalvar.requestFocus();

        }
    }//GEN-LAST:event_txtCelularKeyPressed

    private void txtCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyTyped

    }//GEN-LAST:event_txtCelularKeyTyped

    private void acaoBtnCancelar() {

        /**
         * Após salvar limpar os campos
         */
        /**
         * Após salvar limpar os campos
         */
        txtIDUsuario.setText("");
        txtUsuario.setText("");
        txtLogin.setText("");
        txtSenhaPS.setText("");
        txtCelular.setText("");
        cbPerfilUsuarios.setSelectedItem(null);
        cbSexo.setSelectedItem(null);
        txtGmail.setText("");
        txtPesquisa.setText("");
        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        txtIDUsuario.setEnabled(false);
        txtUsuario.setEnabled(false);
        cbPerfilUsuarios.setEnabled(false);
        txtLogin.setEnabled(false);
        txtSenhaPS.setEnabled(false);
        txtCelular.setEnabled(false);
         txtGmail.setEnabled(false);
         cbSexo.setEnabled(false);

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


    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        acaoBtnCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void desabilitarCampos() {

        txtCelular.setEnabled(false);
        txtIDUsuario.setEnabled(false);
        txtLogin.setEnabled(false);
        txtPesquisa.setEnabled(false);
        txtSenhaPS.setEnabled(false);
        txtUsuario.setEnabled(false);
        cbPerfilUsuarios.setEnabled(false);
    }

    private void desabilitarTodosBotoes() {
        btnAdicionar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnSalvar.setEnabled(false);
    }


    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained

    }//GEN-LAST:event_btnSalvarFocusGained

    private void acaoBotaoSalvar() {

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

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        acaoBotaoSalvar();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
        acaoBotaoSalvar();
    }//GEN-LAST:event_btnSalvarKeyPressed

    private void acaoBotaoEditar() {
        if (txtUsuario.equals("")) {

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
            txtUsuario.setEnabled(true);
            txtLogin.setEnabled(true);
            cbPerfilUsuarios.setEnabled(true);
            txtSenhaPS.setEnabled(true);
            txtCelular.setEnabled(true);
            cbSexo.setEnabled(true);
            txtGmail.setEnabled(true);
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

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        acaoBotaoEditar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void acaoBotaoAdicionar() {

        flag = 1;

        /**
         * Campos devem ser ativados
         */
        txtUsuario.setEnabled(true);
        txtLogin.setEnabled(true);
        cbPerfilUsuarios.setEnabled(true);
        txtSenhaPS.setEnabled(true);
        txtCelular.setEnabled(true);
        cbSexo.setEnabled(true);
        txtGmail.setEnabled(true);

        /**
         * Limpar os campos para cadastrar
         */
        txtIDUsuario.setVisible(false);
        txtUsuario.setText("");
        txtSenhaPS.setText("");
        cbPerfilUsuarios.setSelectedItem(null);
        txtLogin.setText("");
        txtCelular.setText("");

        /**
         * Botões que deverão ficar habilitados nesse evento para esse tipo de
         * Formulario
         */
        btnAdicionar.setEnabled(false);
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);

        /**
         * Botões que deverão ficar desabilitados nesse evento para esse tipo de
         * Formulario
         */
        txtPesquisa.setEnabled(false);
        btnEditar.setEnabled(false);

        txtUsuario.requestFocus();//setar o campo nome Bairro após adicionar
        txtUsuario.setBackground(Color.CYAN);  // altera a cor do fundo
        listarCombo();

    }
    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoBotaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void acaoExcluirRegistro() {

        int resposta = 0;
        resposta = JOptionPane.showConfirmDialog(rootPane, "Deseja Excluir Resgistro?");

        if (resposta == JOptionPane.YES_OPTION) {
            usuarioDTO.setIdUserDto(Integer.parseInt(txtIDUsuario.getText()));
            /**
             * Chamando o método que irá executar a Edição dos Dados em nosso
             * Banco de Dados
             */
            usuarioBO.ExcluirBO(usuarioDTO);
            /**
             * Após salvar limpar os campos
             */
            txtIDUsuario.setText("");
            txtUsuario.setText("");
            txtLogin.setText("");
            txtSenhaPS.setText("");
            txtCelular.setText("");
            cbPerfilUsuarios.setSelectedItem(null);
            btnAdicionar.setEnabled(true);
            btnExcluir.setEnabled(false);
            btnEditar.setEnabled(false);
            try {
                /**
                 * Conta o Número de linhas na minha tabela e armazena na
                 * variável numeroLinas
                 * https://www.youtube.com/watch?v=1fKwn-Vd0uc
                 */
                int numeroLinhas = tblUsuarios.getRowCount();
                if (numeroLinhas > 0) {

                    //http://andersonneto.blogspot.com.br/2015/05/tutorial-remover-todas-as-linhas-de-um.html
                    while (tblUsuarios.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);
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

    private void acaoBotaoExcluir() {

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            acaoExcluirRegistro();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        acaoBotaoExcluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed

    }//GEN-LAST:event_txtPesquisaKeyPressed
    private void acaoMouseClicked() {

        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/
        int codigo = Integer.parseInt("" + tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 0));
        /**
         * Esse código está comentado só para ficar o exemplo de como pegaria o
         * valor de nome da tabela ou seja coluna 1 sendo que falando neses caso
         * trabalhamos como vetor que inicial do zero(0)
         */
        /*   cidadeDTO.setNomeCidadeDto("" + tblCidadesList.getValueAt(tblCidadesList.getSelectedRow(), 1));*/

        try {

            UsuarioDTO retorno = usuarioDAO.buscarPorIdTblConsultaList(codigo);

            if (retorno.getIdUserDto() != null || !retorno.getIdUserDto().equals("")) {
                /**
                 * String.valueOf() pega um Valor Inteiro e transforma em String
                 * e seta em txtIDMedico que é um campo do tipo texto
                 */
                txtIDUsuario.setText(String.valueOf(retorno.getIdUserDto()));
                txtUsuario.setText(retorno.getUsuarioDto());
                txtLogin.setText(retorno.getLoginDto());
                txtSenhaPS.setText(retorno.getSenhaDto());
                txtCelular.setText(retorno.getTelefoneDto());

                /**
                 * setSelectedItem para setar uma String que está no Banco de
                 * Dados em um Campo de Combinação em um Form Java
                 */
                //   JOptionPane.showMessageDialog(null, "perfil:" + retorno.getPerfilDto());
             //   cbPerfilUsuarios.setSelectedItem(retorno.getPerfilDto());

                if (cbPerfilUsuarios.equals("")) {
                    cbPerfilUsuarios.addItem(retorno.getPerfilDto());

                }

                if (!cbPerfilUsuarios.equals("")) {
                    cbPerfilUsuarios.removeAllItems();
                    cbPerfilUsuarios.addItem(retorno.getPerfilDto());

                }
                cbSexo.setSelectedItem(retorno.getSexoDto());
                txtGmail.setText(retorno.getGmailDto());

                /**
                 * Liberar os botões abaixo
                 */
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnAdicionar.setEnabled(false);
                btnCancelar.setEnabled(true);
                txtGmail.setEnabled(true);
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
                this.txtUsuario.setEnabled(false);
                this.cbPerfilUsuarios.setEnabled(false);
                this.cbSexo.setEnabled(false);
                this.txtLogin.setEnabled(false);
                this.txtSenhaPS.setEnabled(false);
                this.txtCelular.setEnabled(false);
                this.txtGmail.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar()\n" + ex.getMessage());
        }
    }


    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked

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


    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void btnAdicionarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseEntered
        btnAdicionar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnAdicionarMouseEntered

    private void btnAdicionarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseExited
        btnAdicionar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnAdicionarMouseExited

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseEntered
        btnSalvar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnSalvarMouseEntered

    private void btnSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseExited
        btnSalvar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnSalvarMouseExited

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void acaoBotaoPesquisar() {

        int numeroLinhas = tblUsuarios.getRowCount();

        if (numeroLinhas > 0) {

            while (tblUsuarios.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);

            }

            pesquisarUsuario();

        } else {
            addRowJTable();
        }

    }


    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed

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
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void cbPerfilUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPerfilUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPerfilUsuariosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBotoesManipulacaoBancoDados;
    private javax.swing.JPanel PanelDadosUsuario;
    private javax.swing.JPanel PanelJTable;
    public static javax.swing.JButton btnAdicionar;
    public static javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnEditar;
    public static javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    public static javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbPerfilUsuarios;
    private javax.swing.JComboBox cbSexo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblImgGmail;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblPerfil;
    private javax.swing.JLabel lblPesquisaRapida;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JFormattedTextField txtCelular;
    private javax.swing.JTextField txtGmail;
    private javax.swing.JTextField txtIDUsuario;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JPasswordField txtSenhaPS;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
