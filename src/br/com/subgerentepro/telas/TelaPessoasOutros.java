package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.PessoaOutroBO;
import br.com.subgerentepro.dao.DepartamentoDAO;
import br.com.subgerentepro.dao.PessoaOutroDAO;
import br.com.subgerentepro.dto.DepartamentoDTO;
import br.com.subgerentepro.dto.PessoaOutroDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.lblPerfil;
import java.awt.Color;
import java.awt.Font;
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

/**
 *
 * @author DaTorres
 */
public class TelaPessoasOutros extends javax.swing.JInternalFrame {

    PessoaOutroDTO PessoaOutroDTO = new PessoaOutroDTO();
    PessoaOutroDAO pessoaOutroDAO = new PessoaOutroDAO();
    PessoaOutroBO pessoaOutroBO = new PessoaOutroBO();
    //para caixa de combinaçâo Departamente iremos instaciar DepartamentoDTO
    DepartamentoDTO departamentoDTO = new DepartamentoDTO();
    DepartamentoDAO departamentoDAO = new DepartamentoDAO();

    //https://www.youtube.com/watch?v=-ATbC-4rhc4
    //CRIANDO BOTAO ADICIONAR 
    JButton btnAdicionar = new JButton();
    JButton btnEditar = new JButton();
    JButton btnSalvar = new JButton();
    // JButton btnExcluir = new JButton();
    JButton btnCancelar = new JButton();
    JTextField txtPesquisa = new JTextField();

    int flag = 0;

    /**
     * Código Mestre Chimura
     */
    private static TelaPessoasOutros instance = null;

    public static TelaPessoasOutros getInstance() {

        if (instance == null) {

            instance = new TelaPessoasOutros();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public TelaPessoasOutros() {
        initComponents();
        aoCarregarForm();
        addRowJTable();
        componentesInternoJInternal();
        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tabela.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 

    }

    private void componentesInternoJInternal() {
        //https://www.youtube.com/watch?v=-ATbC-4rhc4

        TheHandler th = new TheHandler();
        //ADICIONANDO AOS BOTOES AÇOES QUE SERAO TRATADAS PELO ACTIONLISLISTENER
        this.btnAdicionar.addActionListener(th);
        this.btnEditar.addActionListener(th);
        this.btnCancelar.addActionListener(th);
        this.btnExcluir.addActionListener(th);
        this.btnSalvar.addActionListener(th);
        this.txtPesquisa.addActionListener(th);

        AbsoluteLayout layout = new AbsoluteLayout();
        //BOTAO ADICIONAR 
        this.btnAdicionar.setBounds(220, 1, 45, 45);
        this.btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
        this.PanelBotoesManipulacaoBancoDados.add(this.btnAdicionar);

        //BOTAO EDITAR
        this.btnEditar.setBounds(280, 1, 45, 45);
        this.btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editar.png")));
        this.PanelBotoesManipulacaoBancoDados.add(this.btnEditar);
        //BOTAO SALVAR
        this.btnSalvar.setBounds(340, 1, 45, 45);
        this.btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));
        this.PanelBotoesManipulacaoBancoDados.add(this.btnSalvar);
        //BOTAO CANCELAR
        this.btnCancelar.setBounds(400, 1, 45, 45);

        this.btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        this.PanelBotoesManipulacaoBancoDados.add(this.btnCancelar);
        //BOTAO EXCLUIR 
        this.btnExcluir.setBounds(20, 1, 45, 45);
        this.btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/excluir.png")));
        this.PanelBotoesManipulacaoBancoDados.add(this.btnExcluir);
        //BOTAO PESQUISAR 

        //CRIANDO CAMPO PESQUISA JTEXTFIELD
        this.txtPesquisa.setBounds(2, 4, 250, 30);
        this.painelPesquisa.add(txtPesquisa);

    }

    public void aoCarregarForm() {

        this.txtID.setEnabled(false);
        this.txtNome.setEnabled(false);
        this.cbSexo.setEnabled(false);
        this.txtAreaObservacao.setEnabled(false);
        this.txtCelular.setEnabled(false);
        this.cbDepartametoOutros.setEnabled(false);
        this.txtCPF.setEnabled(false);
        this.btnValidaCPF.setEnabled(false);
        this.txtPesquisa.setEnabled(true);
        try {
            listarCombo();
        } catch (PersistenciaException ex) {
            ex.printStackTrace();
        }

        this.btnExcluir.setEnabled(false);
        this.btnEditar.setEnabled(false);
        this.btnAdicionar.setEnabled(true);
        this.btnSalvar.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.btnPesquisar.setEnabled(true);
        progressBarraPesquisa.setIndeterminate(true);

    }

    public void listarCombo() throws PersistenciaException {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            ArrayList<DepartamentoDTO> list;

            list = (ArrayList<DepartamentoDTO>) departamentoDAO.listarTodos();
            cbDepartametoOutros.removeAllItems();
            for (int i = 0; i < list.size(); i++) {

                cbDepartametoOutros.addItem(list.get(i).getNomeDto());

            }
            //setar um valor especifico vindo banco de dados 
            cbDepartametoOutros.setSelectedItem("OUTROS (ESTRUTURA EXTERNA)");

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    private void desabilitarCampos() {

        this.txtID.setEnabled(false);
        this.txtNome.setEnabled(false);
        this.cbSexo.setEnabled(false);
        this.txtAreaObservacao.setEnabled(false);
        this.txtCelular.setEnabled(false);
        this.txtCPF.setEnabled(false);

    }

    private void habilitarCampos() {

        txtNome.setEnabled(true);
        txtAreaObservacao.setEnabled(true);
        cbSexo.setEnabled(true);
        txtCelular.setEnabled(true);
        txtCPF.setEnabled(true);

    }

    private void limparCampos() {

        txtID.setText("");
        txtNome.setText("");
        // cbSexo.setSelectedItem(null);
        txtAreaObservacao.setText("");
        txtCelular.setText("");
        txtCPF.setText("");

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<PessoaOutroDTO> list;

        try {

            list = (ArrayList<PessoaOutroDTO>) pessoaOutroDAO.listarTodos();

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                rowData[2] = list.get(i).getCelularDto();
                rowData[3] = list.get(i).getCpfDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(220);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(140);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void pesquisar() {
        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisa.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<PessoaOutroDTO> list;

        try {

            list = (ArrayList<PessoaOutroDTO>) pessoaOutroDAO.filtrarPesquisaRapida(pesquisar);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                rowData[2] = list.get(i).getCelularDto();
                rowData[3] = list.get(i).getCpfDto();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(220);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(140);

            //----------------------------------//
            //informe sobre Fim da pesquisa //
            //--------------------------------//
            personalizandoBarraInfoPesquisaTermino();

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void personalizandoBarraInfoPesquisaTermino() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setForeground(Color.ORANGE);
        lblLinhaInformativa.setText("Pesquisa Terminada.");
        txtPesquisa.requestFocus();
        txtPesquisa.setBackground(Color.CYAN);
    }

    private void salvar() {

        /**
         * capturando os campos do Form na Camada Gui e em vez de adicionar ha
         * uma variável encapsulamos e setamos como o método set
         */
        PessoaOutroDTO.setNomeDto(txtNome.getText());

        /**
         * Observação:Essa é a forma de captuar do form gui um campo do tipo
         * senha para salva-lo num banco de dados como uma string
         */
        PessoaOutroDTO.setObservacaoDto(txtAreaObservacao.getText());

        if (cbSexo.getSelectedItem() != null) {
            PessoaOutroDTO.setSexoDto(new String((String) cbSexo.getSelectedItem()));
        }
        if (cbDepartametoOutros.getSelectedItem() != null) {
            PessoaOutroDTO.setDepartamentoDto(new String((String) cbDepartametoOutros.getSelectedItem()));
        }
        PessoaOutroDTO.setCelularDto(txtCelular.getText());
        PessoaOutroDTO.setCpfDto(txtCPF.getText());

        try {
            /**
             * Depois de capturados e atribuídos seus respectivos valores
             * capturados nas variáveis acimas descrita. Iremos criar um objeto
             * do tipo PessoaOutroBO
             */
            pessoaOutroBO = new PessoaOutroBO();

            //VALIDAÇÕES
            pessoaOutroBO.validacaoIndexada(PessoaOutroDTO);

            /**
             * Trabalhando com os retornos das validações
             */
            if ((flag == 1)) {

                PessoaOutroDTO.setNomeDto(txtNome.getText());

                boolean retornoVerifcaDuplicidade = pessoaOutroDAO.verificaDuplicidade(PessoaOutroDTO);

                if (retornoVerifcaDuplicidade == false) {

                    /**
                     * capturando os campos do Form na Camada Gui e em vez de
                     * adicionar ha uma variável encapsulamos e setamos como o
                     * método set
                     */
                    PessoaOutroDTO.setNomeDto(txtNome.getText());
                    PessoaOutroDTO.setObservacaoDto(txtAreaObservacao.getText());

                    if (cbSexo.getSelectedItem() != null) {
                        PessoaOutroDTO.setSexoDto(new String((String) cbSexo.getSelectedItem()));
                    }

                    if (cbDepartametoOutros.getSelectedItem() != null) {
                        PessoaOutroDTO.setDepartamentoDto(new String((String) cbDepartametoOutros.getSelectedItem()));
                    }
                    PessoaOutroDTO.setCpfDto(txtCPF.getText());
                    PessoaOutroDTO.setCelularDto(txtCelular.getText());

                    pessoaOutroBO.cadastrar(PessoaOutroDTO);
                    /**
                     * Após salvar limpar os campos
                     */
                    limparCampos();

                    /**
                     * Bloquear campos e Botões
                     */
                    desabilitarCampos();
                    txtPesquisa.setEnabled(true);
                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    btnSalvar.setEnabled(false);
                    btnValidaCPF.setEnabled(false);
                    btnAdicionar.setEnabled(true);

                    listarCombo();
                    //  JOptionPane.showMessageDialog(null, "Registro Cadastrado com Sucesso!");
                    /**
                     * Zera a Linha informativa criada para esse Sistema
                     */
                    Font f = new Font("Tahoma", Font.BOLD, 18);//label informativo 
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setForeground(Color.BLUE);
                    lblLinhaInformativa.setText("Resgistro Salvo");

                    int numeroLinhas = tabela.getRowCount();

                    if (numeroLinhas > 0) {

                        while (tabela.getModel().getRowCount() > 0) {
                            ((DefaultTableModel) tabela.getModel()).removeRow(0);

                        }

                        addRowJTable();

                    } else {
                        addRowJTable();

                    }

                } else {
                    //JOptionPane.showMessageDialog(TelaUsuarios.this, "Login já cadastrado.\no Sistema Impossibilitou \n a Duplicidade");

                    txtNome.requestFocus();
                    txtNome.setBackground(Color.YELLOW);
                    lblLinhaInformativa.setText("Verificação efetuada, Registro já cadastrado no Sistema");
                    lblLinhaInformativa.setForeground(Color.RED);

                }

            } else {

                /**
                 * Caso não seja um novo registro equivale dizer que é uma
                 * edição então executará esse código flag será !=1
                 */
                if (txtID.getText() != null) {
                    PessoaOutroDTO.setIdDto(Integer.parseInt(txtID.getText()));
                }

                /**
                 * Chamando o método que irá executar a Edição dos Dados em
                 * nosso Banco de Dados
                 */
                pessoaOutroBO.atualizarBO(PessoaOutroDTO);

                /**
                 * Após salvar limpar os campos
                 */
                /**
                 * Após salvar limpar os campos
                 */
                limparCampos();
                txtPesquisa.setText("");

                /**
                 * Bloquear campos e Botões
                 */
                desabilitarCampos();
                txtPesquisa.setEnabled(true);

                /**
                 * Liberar campos necessário para operações após salvamento
                 */
                btnAdicionar.setEnabled(true);
                btnCancelar.setEnabled(false);
                btnSalvar.setEnabled(false);

//                    MensagensUtil.add(TelaUsuarios.this, "Edição Salva com Sucesso!");
                JOptionPane.showMessageDialog(null, "Edição salva  com Sucesso!");
                int numeroLinhas = tabela.getRowCount();

                if (numeroLinhas > 0) {

                    while (tabela.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tabela.getModel()).removeRow(0);

                    }

                    addRowJTable();

                } else {
                    addRowJTable();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada GUI: " + e.getMessage());

            if (e.getMessage().equals("Campo nome Obrigatório")) {
                txtNome.requestFocus();
                txtNome.setBackground(Color.RED);
            }

            if (e.getMessage().equals("Campo nome aceita no MAX 50 chars")) {
                txtNome.requestFocus();
                txtNome.setBackground(Color.RED);
                lblLinhaInformativa.setText("Campo aceita no Máximo a digitação de 50 caracteres");

            }

            if (e.getMessage().equals("Campo cpf Obrigatorio")) {
                txtCPF.requestFocus();
                txtCPF.setBackground(Color.red);
            }

            if (e.getMessage().equals("Campo cpf aceita no MAX 14 chars")) {
                txtCPF.requestFocus();
                txtCPF.setBackground(Color.red);

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

        jDayChooser1 = new com.toedter.calendar.JDayChooser();
        PanelDadosUsuario = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        cbSexo = new javax.swing.JComboBox<String>();
        lblCelular = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        PanelJTable = new javax.swing.JPanel();
        lblID = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        lblLinhaInformativa = new javax.swing.JLabel();
        txtCelular = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaObservacao = new javax.swing.JTextArea();
        lblObservacao = new javax.swing.JLabel();
        cbDepartametoOutros = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        lblCPF = new javax.swing.JLabel();
        txtCPF = new javax.swing.JFormattedTextField();
        btnValidaCPF = new javax.swing.JButton();
        painelPesquisa = new javax.swing.JPanel();
        btnPesquisar = new javax.swing.JButton();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        PanelBotoesManipulacaoBancoDados = new javax.swing.JPanel();
        btnExcluir = new javax.swing.JButton();

        setClosable(true);
        setTitle("Pessoa/Outros");

        PanelDadosUsuario.setBackground(java.awt.Color.white);
        PanelDadosUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Pessoa/Outro:"));
        PanelDadosUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNome.setText("Nome:");
        PanelDadosUsuario.add(lblNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, -1, -1));

        lblSexo.setText("Sexo:");
        PanelDadosUsuario.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, -1, -1));

        cbSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MASCULINO", "FEMININO", "OUTROS" }));
        cbSexo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbSexoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbSexoFocusLost(evt);
            }
        });
        cbSexo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbSexoKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(cbSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 110, 30));

        lblCelular.setText("Celular:");
        PanelDadosUsuario.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        txtNome.setBackground(new java.awt.Color(255, 255, 204));
        txtNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomeFocusLost(evt);
            }
        });
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 350, 30));

        PanelJTable.setLayout(new java.awt.GridLayout(1, 0));
        PanelDadosUsuario.add(PanelJTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 276, 481, -1));

        lblID.setText("ID:");
        PanelDadosUsuario.add(lblID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtID.setBackground(new java.awt.Color(255, 255, 204));
        txtID.setEnabled(false);
        PanelDadosUsuario.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 35, 30));

        lblLinhaInformativa.setForeground(new java.awt.Color(51, 153, 255));
        lblLinhaInformativa.setText("Linha Informativa");
        PanelDadosUsuario.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 27, 460, -1));

        txtCelular.setBackground(new java.awt.Color(255, 255, 204));
        try {
            txtCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCelular.setPreferredSize(new java.awt.Dimension(6, 30));
        txtCelular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCelularFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCelularFocusLost(evt);
            }
        });
        txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCelularKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelularKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 122, -1));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOME", "CELULAR", "CPF"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
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
            tabela.getColumnModel().getColumn(1).setMinWidth(210);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(210);
            tabela.getColumnModel().getColumn(1).setMaxWidth(210);
            tabela.getColumnModel().getColumn(2).setMinWidth(70);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(2).setMaxWidth(70);
            tabela.getColumnModel().getColumn(3).setMinWidth(120);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        PanelDadosUsuario.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 470, 140));

        txtAreaObservacao.setColumns(20);
        txtAreaObservacao.setRows(5);
        txtAreaObservacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAreaObservacaoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAreaObservacaoFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(txtAreaObservacao);

        PanelDadosUsuario.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 380, 50));

        lblObservacao.setText("Observação:");
        PanelDadosUsuario.add(lblObservacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        cbDepartametoOutros.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cbDepartametoOutros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDepartametoOutrosActionPerformed(evt);
            }
        });
        PanelDadosUsuario.add(cbDepartametoOutros, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 230, 30));

        jLabel1.setText("Vincular:");
        PanelDadosUsuario.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, -1, -1));

        lblCPF.setText("CPF:");
        PanelDadosUsuario.add(lblCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        try {
            txtCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCPF.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCPFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCPFFocusLost(evt);
            }
        });
        txtCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPFActionPerformed(evt);
            }
        });
        txtCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCPFKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(txtCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 190, 30));

        btnValidaCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/botaoValidacao.png"))); // NOI18N
        btnValidaCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnValidaCPFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnValidaCPFFocusLost(evt);
            }
        });
        btnValidaCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidaCPFActionPerformed(evt);
            }
        });
        btnValidaCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnValidaCPFKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(btnValidaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 35, 30));

        painelPesquisa.setOpaque(false);

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

        javax.swing.GroupLayout painelPesquisaLayout = new javax.swing.GroupLayout(painelPesquisa);
        painelPesquisa.setLayout(painelPesquisaLayout);
        painelPesquisaLayout.setHorizontalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPesquisaLayout.createSequentialGroup()
                .addContainerGap(273, Short.MAX_VALUE)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(progressBarraPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painelPesquisaLayout.setVerticalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressBarraPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelDadosUsuario.add(painelPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 470, 40));

        PanelBotoesManipulacaoBancoDados.setBackground(java.awt.Color.white);
        PanelBotoesManipulacaoBancoDados.setPreferredSize(new java.awt.Dimension(475, 47));

        btnExcluir.setText("excluir");
        btnExcluir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnExcluirFocusGained(evt);
            }
        });

        javax.swing.GroupLayout PanelBotoesManipulacaoBancoDadosLayout = new javax.swing.GroupLayout(PanelBotoesManipulacaoBancoDados);
        PanelBotoesManipulacaoBancoDados.setLayout(PanelBotoesManipulacaoBancoDadosLayout);
        PanelBotoesManipulacaoBancoDadosLayout.setHorizontalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExcluir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelBotoesManipulacaoBancoDadosLayout.setVerticalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExcluir)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelDadosUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelBotoesManipulacaoBancoDados, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(PanelBotoesManipulacaoBancoDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelDadosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbSexoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbSexoKeyPressed

        if (evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_ENTER) {

            txtCelular.requestFocus();

        }

        if (evt.getKeyCode() == evt.VK_LEFT) {

            btnValidaCPF.requestFocus();

        }
    }//GEN-LAST:event_cbSexoKeyPressed

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

            txtCPF.requestFocus();

        }
    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyTyped
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

        if (txtNome.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute
            JOptionPane.showMessageDialog(null, "Máxmimo 50 caracter");// colocamos uma mensagem alertar usuário
            txtNome.setBackground(Color.CYAN);//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Nome: Máximo 50 caracteres");

        }
    }//GEN-LAST:event_txtNomeKeyTyped

    private void txtCelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            btnSalvar.requestFocus();
            btnSalvar.setBackground(Color.YELLOW);
            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Usando o Mouse Click no Botão SALVAR.");
        }

        if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {

            cbSexo.requestFocus();

        }
    }//GEN-LAST:event_txtCelularKeyPressed

    private void txtCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyTyped

    }//GEN-LAST:event_txtCelularKeyTyped

    private void acaoBotaoCancelar() {

        /**
         * Após salvar limpar os campos
         */
        /**
         * Após salvar limpar os campos
         */
        limparCampos();
        txtPesquisa.setText("");
        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        desabilitarCampos();

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

    private void desabilitarTodosBotoes() {
        btnAdicionar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnSalvar.setEnabled(false);
    }

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

    private void acaoBotaoEditar() {
        if (txtNome.equals("")) {

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
            habilitarCampos();
            /**
             * ao clicar em btnAdicionar fazemos com que btnSalvar fique
             * habilitado para um eventual salvamento
             */
            btnSalvar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnEditar.setEnabled(false);
            btnAdicionar.setEnabled(false);
            btnValidaCPF.setEnabled(true);
            btnExcluir.setEnabled(false);

        }

    }

    private void acaoBotaoAdicionar() {
        flag = 1;

        /**
         * Campos devem ser ativados
         */
        habilitarCampos();

        /**
         * Limpar os campos para cadastrar
         */
        txtID.setVisible(false);
        limparCampos();

        /**
         * Botões que deverão ficar habilitados nesse evento para esse tipo de
         * Formulario
         */
        btnAdicionar.setEnabled(false);
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnValidaCPF.setEnabled(true);

        /**
         * Botões que deverão ficar desabilitados nesse evento para esse tipo de
         * Formulario
         */
        txtPesquisa.setEnabled(false);
        btnEditar.setEnabled(false);

        txtNome.requestFocus();//setar o campo nome Bairro após adicionar
        txtNome.setBackground(Color.CYAN);  // altera a cor do fundo
    }

    private void acaoExcluirRegistro() {

        int resposta = 0;
        resposta = JOptionPane.showConfirmDialog(rootPane, "Deseja Excluir Resgistro?");

        if (resposta == JOptionPane.YES_OPTION) {
            PessoaOutroDTO.setIdDto(Integer.parseInt(txtID.getText()));
            /**
             * Chamando o método que irá executar a Edição dos Dados em nosso
             * Banco de Dados
             */
            pessoaOutroBO.ExcluirBO(PessoaOutroDTO);
            /**
             * Após salvar limpar os campos
             */
            limparCampos();
            btnAdicionar.setEnabled(true);
            btnExcluir.setEnabled(false);
            btnEditar.setEnabled(false);
            
            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Registro Excluído com SUCESSO.");
            try {
                /**
                 * Conta o Número de linhas na minha tabela e armazena na
                 * variável numeroLinas
                 * https://www.youtube.com/watch?v=1fKwn-Vd0uc
                 */
                int numeroLinhas = tabela.getRowCount();
                if (numeroLinhas > 0) {

                    //http://andersonneto.blogspot.com.br/2015/05/tutorial-remover-todas-as-linhas-de-um.html
                    while (tabela.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tabela.getModel()).removeRow(0);
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

    private void acaoMouseClicked() {

        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/
        int codigo = Integer.parseInt("" + tabela.getValueAt(tabela.getSelectedRow(), 0));
        /**
         * Esse código está comentado só para ficar o exemplo de como pegaria o
         * valor de nome da tabela ou seja coluna 1 sendo que falando neses caso
         * trabalhamos como vetor que inicial do zero(0)
         */
        /*   cidadeDTO.setNomeCidadeDto("" + tblCidadesList.getValueAt(tblCidadesList.getSelectedRow(), 1));*/

        try {

            PessoaOutroDTO PessoaOutroDTO = pessoaOutroDAO.buscarPorIdTblConsultaList(codigo);

            if (!PessoaOutroDTO.getNomeDto().equals("") || PessoaOutroDTO.getNomeDto() != null) {
                /**
                 * String.valueOf() pega um Valor Inteiro e transforma em String
                 * e seta em txtIDMedico que é um campo do tipo texto
                 */
                txtID.setText(String.valueOf(PessoaOutroDTO.getIdDto()));
                txtNome.setText(PessoaOutroDTO.getNomeDto());
                /**
                 * setSelectedItem para setar uma String que está no Banco de
                 * Dados em um Campo de Combinação em um Form Java
                 */
                txtAreaObservacao.setText(PessoaOutroDTO.getObservacaoDto());
                cbSexo.setSelectedItem(PessoaOutroDTO.getSexoDto());
                txtCelular.setText(PessoaOutroDTO.getCelularDto());
                cbDepartametoOutros.setSelectedItem(PessoaOutroDTO.getDepartamentoDto());
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
                desabilitarCampos();

            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar()\n" + ex.getMessage());
        }
    }


    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

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


    }//GEN-LAST:event_tabelaMouseClicked

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


    private void txtNomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusLost
        txtNome.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtNomeFocusLost

    private void txtNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyReleased

    }//GEN-LAST:event_txtNomeKeyReleased

    private void cbSexoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbSexoFocusGained
        txtNome.setBackground(Color.YELLOW);
        MetodoStaticosUtil.removerAcentosCaixAlta(txtNome.getText());
    }//GEN-LAST:event_cbSexoFocusGained

    private void cbDepartametoOutrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDepartametoOutrosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbDepartametoOutrosActionPerformed

    private void txtCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusGained
        txtCPF.setBackground(Color.YELLOW);
        if (this.txtNome.getText().toString().isEmpty()) {
            this.txtNome.requestFocus();
            this.txtNome.setBackground(Color.YELLOW);

            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.RED);
            txtNome.setBackground(Color.red);
            lblLinhaInformativa.setText("Digite o NOME  e pressione [ENTER]");

        } else {
            txtNome.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtNome.getText()));
            this.txtNome.setBackground(Color.white);

            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Digite o CPF em Sequida valide no Botao ao Lado [ENTER]");
        }
    }//GEN-LAST:event_txtCPFFocusGained

    private void txtCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFActionPerformed
    private void acaoValidaCPFPolindoDados() {

        /**
         * Primeiro criamos uma String com o nome de [CNPJ] e capturamos o valor
         * digitado no campo txtCNPJ por meio do método getText() onde ficará
         * armazenado na variável CNPJ criado para receber o valor capturado
         * pelou usuário.
         */
        PessoaOutroDTO.setCpfDto(this.txtCPF.getText());

        try {
            boolean retornoVerifcaDuplicidade = pessoaOutroDAO.verificaDuplicidadeCPF(PessoaOutroDTO);//verificar se já existe CNPJ

            if (retornoVerifcaDuplicidade == false) {

                /**
                 * Criamos um contador que será incrementado a medida que
                 * estiver sendo executado na string passando por cada caracter
                 * da mesma e nos dando a posição exata onde se encontra para
                 * que possamos fazer uma intervenção exata.
                 */
                int cont = 0;

                /**
                 * Inicia-se o for que irá percorrer o tamanho total da variável
                 * CNPJ que guarda o valor capturado do campo txtCNPJ
                 */
                for (int i = 0; i < PessoaOutroDTO.getCpfDto().length(); i++) {

                    cont += 1;//Aqui o contador começa a ser incrementado em mais um a cada passada 

                    //Quando o contador estiver na posicao 3 execute o codigo abaixo 
                    if (cont == 4) {

                        /**
                         * Se na posição 3 do campo txtCNPJ estiver um ponto
                         * substitua em todos os lugares da String que estiver
                         * com ponto por um estaço em branco
                         */
                        if (PessoaOutroDTO.getCpfDto().charAt(i) == '.') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             *
                             */
                            PessoaOutroDTO.setCpfDto(PessoaOutroDTO.getCpfDto().replace(PessoaOutroDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                    //Quando o contador estiver na posicao 5 execute o codigo abaixo 
                    if (cont == 12) {

                        /**
                         * Se na posição 16 do campo txtCNPJ estiver um traço
                         * [-] substitua em todos os lugares da String que
                         * estiver com ponto por um estaço em branco
                         */
                        if (PessoaOutroDTO.getCpfDto().charAt(i) == '-') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             */
                            PessoaOutroDTO.setCpfDto(PessoaOutroDTO.getCpfDto().replace(PessoaOutroDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                }
                /**
                 * Neste ponto criamos uma String nova chamada cnpjTratado e
                 * capturamos a string ja tratada e fazemos o último tratamento
                 * que é tirar todos os espaços embrancos da string tratada
                 */
                String cpfTratado = PessoaOutroDTO.getCpfDto().replace(" ", "");

                /**
                 * A baixo fazemos a aplicação da função que irá validar se o
                 * cnpj é válido ou não isCNPJ
                 */
                boolean recebeCPF = MetodoStaticosUtil.isCPF(cpfTratado);
                /**
                 * se o retorno for verdadeiro CNPJ válido caso contrário CNPJ
                 * Inválido
                 */
                if (recebeCPF == true) {
                    //  JOptionPane.showMessageDialog(this, "" + "\n Validado com Sucesso.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    cbSexo.requestFocus();
                    txtCPF.setEditable(true);
                    txtCPF.setBackground(Color.WHITE);
                    Font f = new Font("Tahoma", Font.BOLD, 14);
                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setForeground(Color.GREEN);
                    lblLinhaInformativa.setText("Registro Validado com SUCESSO.");

                    cbSexo.requestFocus();

                } else {
                    JOptionPane.showMessageDialog(this, "" + "\n CPF Inválido.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    txtCPF.setBackground(Color.YELLOW);
                    txtCPF.requestFocus();
                }

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Registro Duplicado.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                txtCPF.requestFocus();
                txtCPF.setBackground(Color.RED);

            }
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }
    private void btnValidaCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidaCPFActionPerformed


    }//GEN-LAST:event_btnValidaCPFActionPerformed
    private void acaoBotaoPesquisar() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            acaoPesquisar();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }
    }


    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        acaoBotaoPesquisar();

    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtNomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusGained
        txtNome.setBackground(Color.YELLOW);
        txtCPF.setBackground(Color.WHITE);

        Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Digite o NOME  e pressione [ENTER]");
    }//GEN-LAST:event_txtNomeFocusGained

    private void txtCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusLost
        txtNome.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtCPFFocusLost

    private void btnValidaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusGained

        btnValidaCPF.setBackground(Color.YELLOW);
        txtCPF.setBackground(Color.WHITE);

        if (txtCPF.getText().equals("   .   .   -  ")) {
            txtCPF.requestFocus();
            btnValidaCPF.setBackground(Color.WHITE);
        }

        if (!txtCPF.getText().equals("   .   .   -  ")) {
            ConexaoUtil conecta = new ConexaoUtil();
            boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

            if (recebeConexao == true) {
                acaoValidaCPFPolindoDados();
            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }
        }
    }//GEN-LAST:event_btnValidaCPFFocusGained

    private void btnValidaCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusLost
        btnValidaCPF.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnValidaCPFFocusLost

    private void cbSexoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbSexoFocusLost
        txtNome.setBackground(Color.WHITE);
    }//GEN-LAST:event_cbSexoFocusLost

    private void txtCelularFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCelularFocusGained
        txtNome.setBackground(Color.YELLOW);
    }//GEN-LAST:event_txtCelularFocusGained

    private void txtCelularFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCelularFocusLost
        txtNome.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtCelularFocusLost

    private void txtAreaObservacaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaObservacaoFocusGained
        txtNome.setBackground(Color.YELLOW);
    }//GEN-LAST:event_txtAreaObservacaoFocusGained

    private void txtAreaObservacaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaObservacaoFocusLost
        txtNome.setBackground(Color.white);
    }//GEN-LAST:event_txtAreaObservacaoFocusLost

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        txtNome.setBackground(Color.YELLOW);

        if (txtPesquisa.getText().toString().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "" + "\n Campo Pesquisa NULO", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            txtPesquisa.setBackground(Color.CYAN);
            txtPesquisa.setText("Pessoa/Outros?  :~( ");
            txtPesquisa.requestFocus();
        }

        if (!txtPesquisa.getText().toString().trim().equals("")) {
            acaoPesquisar();
        }

    }//GEN-LAST:event_btnPesquisarFocusGained

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        txtNome.setBackground(Color.white);
    }//GEN-LAST:event_btnPesquisarFocusLost

    private void txtCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFKeyPressed

        if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {

            txtNome.requestFocus();

        }

        if (evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_ENTER) {

            btnValidaCPF.requestFocus();

        }


    }//GEN-LAST:event_txtCPFKeyPressed

    private void personalizandoBarraInfoPesquisaInicio() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Pesquisando: Espere Comunicando MySqL.");
    }


    private void btnValidaCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnValidaCPFKeyPressed

        if (evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_ENTER) {

            cbSexo.requestFocus();

        }

        if (evt.getKeyCode() == evt.VK_LEFT) {

            txtCPF.requestFocus();

        }

    }//GEN-LAST:event_btnValidaCPFKeyPressed

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained
        //codigo de proteçao para setor de protocolo 
        if (lblPerfil.getText().equalsIgnoreCase("protocolo")) {
            btnExcluir.setEnabled(false);
            
            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Registros Protegidos Proibido Exclusão.");

        }
        if (!lblPerfil.getText().equalsIgnoreCase("protocolo")) {
        acaoBotaoExcluir();
        }
    }//GEN-LAST:event_btnExcluirFocusGained
    private class TheHandler implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

           
            if (evt.getSource() == btnAdicionar) {
                acaoBotaoAdicionar();
            }

            if (evt.getSource() == btnEditar) {
                acaoBotaoEditar();
            }

            if (evt.getSource() == btnSalvar) {

                acaoBotaoSalvar();
            }

            if (evt.getSource() == btnCancelar) {
                acaoBotaoCancelar();
            }

            if (evt.getSource() == btnPesquisar) {

                acaoPesquisar();
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
    private javax.swing.JPanel PanelDadosUsuario;
    private javax.swing.JPanel PanelJTable;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnValidaCPF;
    private javax.swing.JComboBox cbDepartametoOutros;
    private javax.swing.JComboBox<String> cbSexo;
    private com.toedter.calendar.JDayChooser jDayChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblObservacao;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JPanel painelPesquisa;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JTextArea txtAreaObservacao;
    private javax.swing.JFormattedTextField txtCPF;
    private javax.swing.JFormattedTextField txtCelular;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
