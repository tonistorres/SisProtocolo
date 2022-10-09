package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.FuncionarioBO;
import br.com.subgerentepro.dao.DepartamentoDAO;
import br.com.subgerentepro.dao.FuncionarioDAO;
import br.com.subgerentepro.dto.DepartamentoDTO;
import br.com.subgerentepro.dto.FuncionarioDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;

import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import static br.com.subgerentepro.telas.TelaPrincipal.lblPerfil;
import br.com.subgerentepro.util.PINTAR_TABELA_FUNCIONARIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class TelaCadFuncionarios extends javax.swing.JInternalFrame {

    FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    FuncionarioBO funcionarioBO = new FuncionarioBO();
    //para caixa de combinaçâo Departamente iremos instaciar DepartamentoDTO
    DepartamentoDTO departamentoDTO = new DepartamentoDTO();
    DepartamentoDAO departamentoDAO = new DepartamentoDAO();

    //https://www.youtube.com/watch?v=-ATbC-4rhc4
    //CRIANDO BOTAO ADICIONAR 
    //BUTONS
    JButton btnAdicionar = new JButton();
    // JButton btnEditar = new JButton();
    // JButton btnSalvar = new JButton();
    //  JButton btnExcluir = new JButton();
    JButton btnCancelar = new JButton();
    //  JButton btnPesquisar = new JButton();
    //JButton btnValidarCPF = new JButton();

    //LABEL
    JLabel lblLinhaInformativa = new JLabel();
    JLabel lblId = new JLabel();
    JLabel lblnome = new JLabel();
    JLabel lblsexo = new JLabel();
    JLabel lblvinculandoDep = new JLabel();
    JLabel lblCelular = new JLabel();
    JLabel lblPesquisar = new JLabel();
    JLabel lblCPF = new JLabel();

    //COMBO 
    //  JComboBox cbSexo = new JComboBox();
    private void criandoListaCbSexo() {
        cbSexo.removeAllItems();
        cbSexo.addItem("MASCULINO");
        cbSexo.addItem("FEMININO");
    }

    //   JComboBox cbDepartamento = new JComboBox();
    //TEXTFILD
    JTextField txtPesquisa = new JTextField();
    JTextField txtID = new JTextField();
   // JTextField txtNome = new JTextField();

    // JFormattedTextField txtCelular = new JFormattedTextField();
    //JFormattedTextField txtCPF = new JFormattedTextField();
    int flag = 0;

    //Chimura: A partir de agora, você usará esse método getInstance() toda vez que quiser criar um ViewEstado
    // Mesma coisa foi feita para ViewBairro
    // Também será necessário fazer para os outros
    private static TelaCadFuncionarios instance = null;

    public static TelaCadFuncionarios getInstance() {
        if (instance == null) {
            try {
                instance = new TelaCadFuncionarios();
            } catch (PersistenciaException ex) {
                ex.printStackTrace();
            }
        }
        return instance;
    }

    // Chimura: usado para checar se a ViewBairro já está aberta, no ato de abrí-la na ViewPrincipal
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

    public TelaCadFuncionarios() throws PersistenciaException {
        initComponents();
        componentesInternoJInternal();
        aoCarregarForm();
        addRowJTable();

        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tabela.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 
        //https://www.youtube.com/watch?v=jPfKFm2Yfow
        tabela.setDefaultRenderer(Object.class, new PINTAR_TABELA_FUNCIONARIO());

    }

    public void aoCarregarForm() {

        this.txtID.setEnabled(false);
        this.txtNome.setEnabled(false);
        this.cbSexo.setEnabled(false);
        this.cbDepartamento.setEnabled(false);
        this.txtCelular.setEnabled(false);
        this.txtCPF.setEnabled(false);

        btnExcluir.setEnabled(false);
        btnAdicionar.setEnabled(true);
        btnEditar.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnValidarCPF.setEnabled(false);

        cbSexo.setSelectedItem(null);
        cbDepartamento.setSelectedItem(null);
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
            cbDepartamento.removeAllItems();
            for (int i = 0; i < list.size(); i++) {

                cbDepartamento.addItem(list.get(i).getNomeDto());

            }

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    private void componentesInternoJInternal() throws PersistenciaException {
        //https://www.youtube.com/watch?v=-ATbC-4rhc4

        TheHandler th = new TheHandler();
        //ADICIONANDO AOS BOTOES AÇOES QUE SERAO TRATADAS PELO ACTIONLISLISTENER
        btnAdicionar.addActionListener(th);
        btnEditar.addActionListener(th);
        btnCancelar.addActionListener(th);
        btnExcluir.addActionListener(th);
        btnSalvar.addActionListener(th);
        btnValidarCPF.addActionListener(th);
        txtPesquisa.addActionListener(th);
        cbSexo.addActionListener(th);

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

        //CRIANDO LABELS 
        Font fonteLabelCampos = new Font("Tahoma", Font.BOLD, 10);
        lblLinhaInformativa.setBounds(11, 1, 400, 30);
        lblLinhaInformativa.setText("Linha Informativa");
        painelDados.add(lblLinhaInformativa);

        //BARRA DE PROGRESSO
        progressBarraPesquisa.setBounds(350, 1, 100, 20);
        painelDados.add(progressBarraPesquisa);

        //label ID
        lblId.setBounds(12, 30, 30, 20);
        lblId.setText("ID:");
        lblId.setFont(fonteLabelCampos);
        lblId.setVerticalTextPosition(SwingConstants.CENTER);
        painelDados.add(this.lblId);

        //txt ID
        txtID.setBounds(11, 50, 35, 30);
        painelDados.add(txtID);

        //label NOME
        lblnome.setBounds(57, 30, 50, 20);
        lblnome.setText("NOME:");
        lblnome.setFont(fonteLabelCampos);
        painelDados.add(this.lblnome);
        //txt NOME
        txtNome.setBounds(55, 50, 240, 30);
        painelDados.add(txtNome);

        //lable CPF
        lblCPF.setBounds(318, 30, 48, 30);
        lblCPF.setText("CPF:");
        lblCPF.setFont(fonteLabelCampos);
        painelDados.add(this.lblCPF);
        //txt CPF
        txtCPF.setBounds(315, 50, 100, 30);

        try {
            txtCPF.setFormatterFactory(new DefaultFormatterFactory(
                    new MaskFormatter("###.###.###-##")));

            painelDados.add(txtCPF);

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Camada GUI:" + ex.getMessage());
        }

        //btn CPF
        btnValidarCPF.setBounds(415, 48, 32, 32);
        btnValidarCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/validation.png")));
        painelDados.add(this.btnValidarCPF);

        //label SEXO
        lblsexo.setBounds(13, 80, 50, 20);
        lblsexo.setText("SEXO:");
        lblsexo.setFont(fonteLabelCampos);
        painelDados.add(this.lblsexo);

        cbSexo.setBounds(11, 100, 110, 30);
        painelDados.add(cbSexo);
        criandoListaCbSexo();

        //label VINCULAR DEPARTAMENTO
        lblvinculandoDep.setBounds(133, 80, 150, 20);
        lblvinculandoDep.setText("VINCULAR DEPARTAMENTO:");
        lblvinculandoDep.setFont(fonteLabelCampos);
        painelDados.add(this.lblvinculandoDep);

        cbDepartamento.setBounds(131, 100, 190, 30);
        painelDados.add(cbDepartamento);
        listarCombo();

        //label CELULAR
        lblCelular.setBounds(333, 80, 50, 20);
        lblCelular.setText("CELULAR:");
        lblCelular.setFont(fonteLabelCampos);
        painelDados.add(this.lblCelular);

        //JFormattedTextField CELULAR
        txtCelular.setBounds(331, 100, 105, 30);
        try {
            txtCelular.setFormatterFactory(new DefaultFormatterFactory(
                    new MaskFormatter("(##) 9####-####")));
            painelDados.add(txtCelular);
        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Camada GUI:" + ex.getMessage());
        }

        //BOTAO PESQUISAR 
//        btnPesquisar.setBounds(290, 145, 32, 35);
        //      btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        //    painelDados.add(this.btnPesquisar);
        //Label pesquisar
        lblPesquisar.setBounds(12, 132, 100, 20);
        lblPesquisar.setText("PESQUISAR:");
        lblPesquisar.setFont(fonteLabelCampos);
        painelDados.add(this.lblPesquisar);
        txtPesquisa.setBounds(11, 150, 271, 30);
        painelDados.add(txtPesquisa);
        txtPesquisa.requestFocus();
        txtPesquisa.setBackground(Color.YELLOW);

        //SETANDO CAMPOS DE TEXTO DO FORMULARIO A FONTE PADRAO 
        Font fontePesonalizaTxT = new Font("Tahoma", Font.BOLD, 9);
        txtPesquisa.setFont(fontePesonalizaTxT);
        txtID.setFont(fontePesonalizaTxT);
        txtNome.setFont(fontePesonalizaTxT);
        txtCelular.setFont(fontePesonalizaTxT);
        txtCPF.setFont(fontePesonalizaTxT);
        cbSexo.setFont(fontePesonalizaTxT);

        Font fonteCampoCbDepartamento = new Font("Tahoma", Font.BOLD, 7);
        cbDepartamento.setFont(fontePesonalizaTxT);
        cbDepartamento.setForeground(Color.BLUE);

    }

    private void acaoValidaCPFPolindoDados() {

        /**
         * Primeiro criamos uma String com o nome de [CNPJ] e capturamos o valor
         * digitado no campo txtCNPJ por meio do método getText() onde ficará
         * armazenado na variável CNPJ criado para receber o valor capturado
         * pelou usuário.
         */
        funcionarioDTO.setCpfDto(this.txtCPF.getText());

        try {
            boolean retornoVerifcaDuplicidade = funcionarioDAO.verificaDuplicidadeCPF(funcionarioDTO);//verificar se já existe CPF

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
                for (int i = 0; i < funcionarioDTO.getCpfDto().length(); i++) {

                    cont += 1;//Aqui o contador começa a ser incrementado em mais um a cada passada 

                    //Quando o contador estiver na posicao 3 execute o codigo abaixo 
                    if (cont == 4) {

                        /**
                         * Se na posição 3 do campo txtCNPJ estiver um ponto
                         * substitua em todos os lugares da String que estiver
                         * com ponto por um estaço em branco
                         */
                        if (funcionarioDTO.getCpfDto().charAt(i) == '.') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             *
                             */
                            funcionarioDTO.setCpfDto(funcionarioDTO.getCpfDto().replace(funcionarioDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                    //Quando o contador estiver na posicao 5 execute o codigo abaixo 
                    if (cont == 12) {

                        /**
                         * Se na posição 16 do campo txtCNPJ estiver um traço
                         * [-] substitua em todos os lugares da String que
                         * estiver com ponto por um estaço em branco
                         */
                        if (funcionarioDTO.getCpfDto().charAt(i) == '-') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             */
                            funcionarioDTO.setCpfDto(funcionarioDTO.getCpfDto().replace(funcionarioDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                }
                /**
                 * Neste ponto criamos uma String nova chamada cnpjTratado e
                 * capturamos a string ja tratada e fazemos o último tratamento
                 * que é tirar todos os espaços embrancos da string tratada
                 */
                String cpfTratado = funcionarioDTO.getCpfDto().replace(" ", "");

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
                    JOptionPane.showMessageDialog(this, "" + "\n Validado com Sucesso.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

                    txtCPF.setEditable(true);
                    txtCPF.setBackground(Color.WHITE);
                    cbSexo.setBackground(Color.YELLOW);
                    btnValidarCPF.setBackground(Color.WHITE);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        painelPrincipal = new javax.swing.JPanel();
        PanelBotoesManipulacaoBancoDados = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        painelDados = new javax.swing.JPanel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        btnPesquisar = new javax.swing.JButton();
        txtNome = new javax.swing.JTextField();
        txtCPF = new javax.swing.JFormattedTextField();
        txtCelular = new javax.swing.JFormattedTextField();
        cbDepartamento = new javax.swing.JComboBox();
        cbSexo = new javax.swing.JComboBox();
        btnValidarCPF = new javax.swing.JButton();

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "LOGIN", "PERFIL", "CELULAR", "SENHA"
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

        jTextField1.setText("jTextField1");

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelPrincipal.setBackground(java.awt.Color.white);
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalvar.setText("salvar");
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

        btnEditar.setText("editar");
        btnEditar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEditarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnEditarFocusLost(evt);
            }
        });
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setText("excluir");
        btnExcluir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnExcluirFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnExcluirFocusLost(evt);
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
                .addGap(37, 37, 37)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addComponent(btnEditar)
                .addGap(49, 49, 49)
                .addComponent(btnSalvar)
                .addGap(70, 70, 70))
        );
        PanelBotoesManipulacaoBancoDadosLayout.setVerticalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar)
                    .addComponent(btnExcluir))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        painelPrincipal.add(PanelBotoesManipulacaoBancoDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 50));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOME", "DEPARTAMENTO", "CELULAR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        tabela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(40);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabela.getColumnModel().getColumn(0).setMaxWidth(40);
            tabela.getColumnModel().getColumn(1).setMinWidth(180);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
            tabela.getColumnModel().getColumn(1).setMaxWidth(180);
            tabela.getColumnModel().getColumn(2).setMinWidth(150);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        painelPrincipal.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, -1, 200));

        painelDados.setBackground(java.awt.Color.white);

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesquisarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnPesquisarFocusLost(evt);
            }
        });

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
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

        txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCelularKeyPressed(evt);
            }
        });

        cbDepartamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbDepartamentoKeyPressed(evt);
            }
        });

        cbSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSexoActionPerformed(evt);
            }
        });
        cbSexo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbSexoKeyPressed(evt);
            }
        });

        btnValidarCPF.setText("jButton1");
        btnValidarCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnValidarCPFFocusGained(evt);
            }
        });

        javax.swing.GroupLayout painelDadosLayout = new javax.swing.GroupLayout(painelDados);
        painelDados.setLayout(painelDadosLayout);
        painelDadosLayout.setHorizontalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelDadosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(progressBarraPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(cbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnValidarCPF)
                            .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(104, 104, 104))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        painelDadosLayout.setVerticalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addComponent(progressBarraPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btnValidarCPF)))
                .addGap(18, 18, 18)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        painelPrincipal.add(painelDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 460, 180));

        getContentPane().add(painelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked


    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

        acaoAoClicarNumRegistro();

    }//GEN-LAST:event_tabelaMouseClicked

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        if (txtPesquisa.getText().toString().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "" + "\n Campo Pesquisa NULO", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            txtPesquisa.setBackground(Color.CYAN);
            txtPesquisa.setText("Funcionário?  :~( ");
            txtPesquisa.requestFocus();
        }

        if (!txtPesquisa.getText().toString().trim().equals("")) {
            acaoPesquisar();
        }


    }//GEN-LAST:event_btnPesquisarFocusGained

    private void tabelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyPressed
        personalizandoBarraInfoPesquisaInicio();
    }//GEN-LAST:event_tabelaKeyPressed

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed

    }//GEN-LAST:event_txtNomeActionPerformed

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            txtCPF.requestFocus();
            txtCPF.setBackground(Color.YELLOW);
            txtNome.setBackground(Color.WHITE);
        }

        //se seta para baixo 
        if (evt.getKeyCode() == evt.VK_DOWN) {
            txtCPF.requestFocus();
            txtCPF.setBackground(Color.YELLOW);
            txtNome.setBackground(Color.WHITE);
        }

        //se seta para direita 
        //se seta para baixo 
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            txtCPF.requestFocus();
            txtCPF.setBackground(Color.YELLOW);
            txtNome.setBackground(Color.WHITE);
        }
    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFKeyPressed

        //aperta ENTER  e vai pra o campo de cpf 
        if (evt.getKeyCode() == evt.VK_ENTER) {

            if (!txtCPF.getText().equals("   .   .   -  ")) {

                btnValidarCPF.requestFocus();
                btnValidarCPF.setBackground(Color.YELLOW);
                txtCPF.setBackground(Color.WHITE);
            } else {

                cbSexo.setBackground(Color.YELLOW);
                btnValidarCPF.setBackground(Color.WHITE);
                txtCPF.setBackground(Color.WHITE);
                cbSexo.requestFocus();
            }

        }

        //seta para cima retorna para o campo txtNome
        if (evt.getKeyCode() == evt.VK_UP) {
            //    JOptionPane.showMessageDialog(null, "Foi precionado seta para cima ");
            txtNome.requestFocus();
            txtNome.setBackground(Color.YELLOW);
            txtCPF.setBackground(Color.WHITE);
        }
        //seta para esquerda retorna para o campo txtNome
        if (evt.getKeyCode() == evt.VK_LEFT) {
            //    JOptionPane.showMessageDialog(null, "Foi precionado seta para cima ");
            txtNome.requestFocus();
            txtNome.setBackground(Color.YELLOW);
            txtCPF.setBackground(Color.WHITE);
        }
        //seta para BAIXO retorna para o campo cbSexo
        if (evt.getKeyCode() == evt.VK_DOWN) {

            cbSexo.requestFocus();
            cbSexo.setBackground(Color.YELLOW);
            txtCPF.setBackground(Color.WHITE);
        }

        //seta para DIREITA retorna para o campo cbSexo
        if (evt.getKeyCode() == evt.VK_RIGHT) {

            cbSexo.requestFocus();
            cbSexo.setBackground(Color.YELLOW);
            txtCPF.setBackground(Color.WHITE);
        }


    }//GEN-LAST:event_txtCPFKeyPressed

    private void btnValidarCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidarCPFFocusGained
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            cbSexo.requestFocus();

            acaoValidaCPFPolindoDados();

        } else {
            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
        }
    }//GEN-LAST:event_btnValidarCPFFocusGained

    private void cbDepartamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbDepartamentoKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            txtCelular.requestFocus();
            txtCelular.setBackground(Color.YELLOW);
            cbDepartamento.setBackground(Color.WHITE);
        }

        //seta para ESQUERD retorna para o campo cbSexo
        if (evt.getKeyCode() == evt.VK_LEFT) {

            cbSexo.requestFocus();
            cbSexo.setBackground(Color.YELLOW);
            cbDepartamento.setBackground(Color.WHITE);
        }

        //SETA DIREITA 
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            txtCelular.requestFocus();
            txtCelular.setBackground(Color.YELLOW);
            cbDepartamento.setBackground(Color.WHITE);
        }


    }//GEN-LAST:event_cbDepartamentoKeyPressed

    private void cbSexoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbSexoKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {
            cbDepartamento.requestFocus();
            cbDepartamento.setBackground(Color.YELLOW);
            cbSexo.setBackground(Color.WHITE);
        }

        //seta para ESQUERDA retorna para o campo CPF
        if (evt.getKeyCode() == evt.VK_LEFT) {

            txtCPF.requestFocus();
            txtCPF.setBackground(Color.YELLOW);
            cbSexo.setBackground(Color.WHITE);

        }

        //seta para DIREITA vai campo cbDepartamento
        if (evt.getKeyCode() == evt.VK_RIGHT) {

            cbDepartamento.requestFocus();
            cbDepartamento.setBackground(Color.YELLOW);
            cbSexo.setBackground(Color.WHITE);

        }

    }//GEN-LAST:event_cbSexoKeyPressed

    private void txtCelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            btnSalvar.requestFocus();
            btnSalvar.setBackground(Color.YELLOW);
            txtCelular.setBackground(Color.WHITE);
        }

        //seta para ESQUERD retorna para o campo cbSexo
        if (evt.getKeyCode() == evt.VK_LEFT) {

            cbDepartamento.requestFocus();
            cbDepartamento.setBackground(Color.YELLOW);
            txtCelular.setBackground(Color.WHITE);
        }

        //seta para CIMA (UP)Retorna para o campo cbSexo
        if (evt.getKeyCode() == evt.VK_UP) {

            cbDepartamento.requestFocus();
            cbDepartamento.setBackground(Color.YELLOW);
            txtCelular.setBackground(Color.WHITE);
        }
    }//GEN-LAST:event_txtCelularKeyPressed

    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained
        btnSalvar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnSalvarFocusGained

    private void cbSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSexoActionPerformed

    }//GEN-LAST:event_cbSexoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        flag = 2;
        btnEditar.setBackground(Color.YELLOW);

        //SE CPF NULL E FONE NULL (TUDO CERTO) REGISTRO ANDRE CONCEICAO
        if (lblPerfil.getText().equalsIgnoreCase("protocolo") && txtCPF.getText().equals("   .   .   -  ") && !txtID.getText().equals("") && txtCelular.getText().equals("(  ) 9    -    ")) {

            btnEditar.setEnabled(true);

            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Liberado a Inclusão CPF/Celular Funcionário.");
            txtID.setEnabled(false);
            txtNome.setEnabled(false);
            txtCPF.setEnabled(true);
            btnValidarCPF.setEnabled(true);
            cbSexo.setEnabled(false);
            cbDepartamento.setEnabled(false);
            txtCelular.setEnabled(true);
            txtCelular.setBackground(Color.YELLOW);
            txtCPF.setBackground(Color.YELLOW);
            btnSalvar.setEnabled(true);

        }

        //se CPF CADASTRADO E FONE NULL(TUDO CERTRO ) REGISTRO DEMETRIO
        if (lblPerfil.getText().equalsIgnoreCase("protocolo") && !txtCPF.getText().equals("   .   .   -  ") && !txtID.getText().equals("") && txtCelular.getText().equals("(  ) 9    -    ")) {

            txtCelular.setEnabled(true);
            txtCelular.setBackground(Color.YELLOW);
            txtCelular.requestFocus();
            txtCPF.setEnabled(false);
            btnSalvar.setEnabled(true);

            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Liberado a para Inclusão Celular Funcionário.");
        }

        //se CPF CADASTRADO E FONE CADASTRADO (TUDO CERTO)ANDRE LICITACAO
        if (lblPerfil.getText().equalsIgnoreCase("protocolo") && !txtCPF.getText().equals("   .   .   -  ") && !txtID.getText().equals("") && !txtCelular.getText().equals("(  ) 9    -    ")) {

            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Registro completo Alteração Impossibilitada.");
        }
        //se CPF NULL E FONE CADASTRADO
        if (lblPerfil.getText().equalsIgnoreCase("protocolo") && txtCPF.getText().equals("   .   .   -  ") && !txtID.getText().equals("") && !txtCelular.getText().equals("(  ) 9    -    ")) {
            txtCelular.setEnabled(false);
            txtCPF.setEnabled(true);
            txtCPF.setBackground(Color.YELLOW);
            txtCPF.requestFocus();
            btnValidarCPF.setEnabled(true);
            btnSalvar.setEnabled(true);

            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("CPF Liberado para INCLUSAO.");
        }

        if (!lblPerfil.getText().equalsIgnoreCase("protocolo")) {
            acaoBotaoEditar();
        }

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        acaoBotaoSalvar();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusLost
        btnSalvar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnSalvarFocusLost

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed

        if (lblPerfil.getText().equalsIgnoreCase("PESQUISA TECNOLOGICA DA INFORMACAO")) {
            acaoBotaoExcluir();
        } else {
            Font font = new Font("Tahoma", Font.BOLD, 12);//label informativo    
            lblLinhaInformativa.setText("Registro Protegido contra Exclusão");
            lblLinhaInformativa.setFont(font);
            lblLinhaInformativa.setForeground(Color.red);

        }

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained
        btnExcluir.setBackground(Color.YELLOW);

//codigo de proteçao para setor de protocolo 
        if (lblPerfil.getText().equalsIgnoreCase("protocolo")) {
            btnExcluir.setEnabled(false);
            btnEditar.setEnabled(false);

            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Registros Protegidos Proibido Exclusão.");

        }
    }//GEN-LAST:event_btnExcluirFocusGained

    private void btnEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusGained
        //   

    }//GEN-LAST:event_btnEditarFocusGained

    private void btnEditarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusLost
        btnEditar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnEditarFocusLost

    private void btnExcluirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusLost
        btnExcluir.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnExcluirFocusLost

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost

    private void txtCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFActionPerformed

    private void desabilitarCampos() {

        this.txtID.setEnabled(false);
        this.txtNome.setEnabled(false);
        this.cbSexo.setEnabled(false);
        this.cbDepartamento.setEnabled(false);
        this.txtCelular.setEnabled(false);

    }

    private void habilitarCampos() {

        txtNome.setEnabled(true);
        cbDepartamento.setEnabled(true);
        cbSexo.setEnabled(true);
        txtCelular.setEnabled(true);

    }

    private void limparCampos() {

        txtID.setText("");
        txtNome.setText("");
        cbSexo.setSelectedItem(null);
        cbDepartamento.setSelectedItem(null);
        txtCelular.setText("");

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

    private void desabilitarTodosBotoes() {
        btnAdicionar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnSalvar.setEnabled(false);
    }

    private void acaoExcluirRegistro() {
        /*Evento ao ser clicado executa código*/
        int excluir = JOptionPane.showConfirmDialog(null, "Deseja Excluir Registro?", "Informação", JOptionPane.YES_NO_OPTION);

        if (excluir == JOptionPane.YES_OPTION) {
            funcionarioDTO.setIdDto(Integer.parseInt(txtID.getText()));
            /**
             * Chamando o método que irá executar a Edição dos Dados em nosso
             * Banco de Dados
             */
            funcionarioBO.ExcluirBO(funcionarioDTO);
            /**
             * Após salvar limpar os campos
             */
            limparCampos();
            btnAdicionar.setEnabled(true);
            btnExcluir.setEnabled(false);
            btnEditar.setEnabled(false);

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

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<FuncionarioDTO> list;

        try {

            list = (ArrayList<FuncionarioDTO>) funcionarioDAO.listarTodos();

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
                rowData[2] = list.get(i).getDepartamentoDto();
                rowData[3] = list.get(i).getCelularDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(220);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(100);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
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

    private void pesquisar() {

        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisa.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<FuncionarioDTO> list;

        try {

            list = (ArrayList<FuncionarioDTO>) funcionarioDAO.filtrarPesquisaRapida(pesquisar);

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
                rowData[2] = list.get(i).getDepartamentoDto();
                rowData[3] = list.get(i).getCelularDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(220);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(100);

            //----------------------------------//
            //informe sobre Fim da pesquisa //
            //--------------------------------//
            personalizandoBarraInfoPesquisaTermino();

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
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
        btnValidarCPF.setEnabled(true);

        /**
         * Botões que deverão ficar desabilitados nesse evento para esse tipo de
         * Formulario
         */
        txtPesquisa.setEnabled(false);
        txtCPF.setEnabled(true);
        btnEditar.setEnabled(false);

        txtNome.requestFocus();//setar o campo nome Bairro após adicionar
        txtNome.setBackground(Color.CYAN);  // altera a cor do fundo
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
            txtCPF.setEnabled(true);
            /**
             * ao clicar em btnAdicionar fazemos com que btnSalvar fique
             * habilitado para um eventual salvamento
             */
            btnSalvar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnValidarCPF.setEnabled(true);
            btnEditar.setEnabled(false);
            btnAdicionar.setEnabled(false);
            btnExcluir.setEnabled(false);

        }
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

    private void salvar() {

        /**
         * capturando os campos do Form na Camada Gui e em vez de adicionar ha
         * uma variável encapsulamos e setamos como o método set
         */
        funcionarioDTO.setNomeDto(txtNome.getText());

        /**
         * Observação:Essa é a forma de captuar do form gui um campo do tipo
         * senha para salva-lo num banco de dados como uma string
         */
        if (cbSexo.getSelectedItem() != null) {
            funcionarioDTO.setSexoDto(new String((String) cbSexo.getSelectedItem()));
        }

        if (cbDepartamento.getSelectedItem() != null) {
            funcionarioDTO.setDepartamentoDto(new String((String) cbDepartamento.getSelectedItem()));
        }

        funcionarioDTO.setCelularDto(txtCelular.getText());
        funcionarioDTO.setCpfDto(txtCPF.getText());

        try {

            boolean recebeRetornoIndexado = funcionarioBO.validacaoBOdosCamposForm(funcionarioDTO);
            /**
             * Depois de capturados e atribuídos seus respectivos valores
             * capturados nas variáveis acimas descrita. Iremos criar um objeto
             * do tipo FuncionarioBO
             */
            funcionarioBO = new FuncionarioBO();

            /**
             * Trabalhando com os retornos das validações
             */
            if ((flag == 1)) {

                funcionarioDTO.setNomeDto(txtNome.getText());

                boolean retornoVerifcaDuplicidade = funcionarioDAO.verificaDuplicidade(funcionarioDTO);

                if (retornoVerifcaDuplicidade == false) {

                    /**
                     * capturando os campos do Form na Camada Gui e em vez de
                     * adicionar ha uma variável encapsulamos e setamos como o
                     * método set
                     */
                    funcionarioDTO.setNomeDto(txtNome.getText());
                    funcionarioDTO.setDepartamentoDto(new String((String) cbDepartamento.getSelectedItem()));
                    funcionarioDTO.setSexoDto(new String((String) cbSexo.getSelectedItem()));
                    funcionarioDTO.setCelularDto(txtCelular.getText());
                    funcionarioDTO.setCpfDto(txtCPF.getText());

                    funcionarioBO.cadastrar(funcionarioDTO);
                    /**
                     * Após salvar limpar os campos
                     */
                    txtID.setText("");
                    txtNome.setText("");
                    cbSexo.setSelectedItem(null);
                    cbDepartamento.setSelectedItem(null);
                    txtCelular.setText("");

                    txtCPF.setEnabled(false);
                    btnValidarCPF.setEnabled(false);
                    txtCPF.setText("");
                    /**
                     * Bloquear campos e Botões
                     */
                    txtID.setEnabled(false);
                    txtNome.setEnabled(false);
                    cbDepartamento.setEnabled(false);
                    txtCelular.setEnabled(false);
                    cbSexo.setEnabled(false);
                    txtPesquisa.setEnabled(true);
                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    btnSalvar.setEnabled(false);

                    btnAdicionar.setEnabled(true);

                    btnSalvar.setBackground(Color.WHITE);
                    JOptionPane.showMessageDialog(null, "Registro Cadastrado com Sucesso!");
                    /**
                     * Zera a Linha informativa criada para esse Sistema
                     */
                    lblLinhaInformativa.setText("Linha Informativa");
                    lblLinhaInformativa.setForeground(Color.getHSBColor(51, 153, 255));

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
                    funcionarioDTO.setIdDto(Integer.parseInt(txtID.getText()));
                }

                /**
                 * Chamando o método que irá executar a Edição dos Dados em
                 * nosso Banco de Dados
                 */
                funcionarioBO.atualizarBO(funcionarioDTO);

                /**
                 * Após salvar limpar os campos
                 */
                /**
                 * Após salvar limpar os campos
                 */
                limparCampos();
                // txtPesquisa.setText("");

                txtCPF.setEnabled(false);
                txtCPF.setText("");
                btnValidarCPF.setEnabled(false);

                /**
                 * Bloquear campos e Botões
                 */
                desabilitarCampos();
                txtPesquisa.setEnabled(true);

                /**
                 * Liberar campos necessário para operações após salvamento
                 */
                btnAdicionar.setEnabled(true);
                btnAdicionar.requestFocus();
                btnCancelar.setEnabled(false);
                btnSalvar.setEnabled(false);
                btnSalvar.setBackground(Color.WHITE);
                btnEditar.setEnabled(false);
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

            if (e.getMessage().equals("Campo NOME Obrigatorio")) {
                txtNome.requestFocus();
                txtNome.setBackground(Color.RED);
                lblLinhaInformativa.setText("Campo NOME Obrigatorio");
            }

            if (e.getMessage().equals("Campo NOME aceita no MAX 49 chars")) {
                txtNome.requestFocus();
                txtNome.setBackground(Color.RED);
                lblLinhaInformativa.setText("Campo aceita no Máximo a digitação de 50 caracteres");

            }

            if (e.getMessage().equals("Campo cpf Obrigatorio")) {
                txtCPF.requestFocus();
                txtCPF.setBackground(Color.RED);
                lblLinhaInformativa.setText("Campo cpf Obrigatorio");

            }

            if (e.getMessage().equals("Campo cpf aceita no MAX 14 chars")) {
                txtCPF.requestFocus();
                txtCPF.setBackground(Color.RED);
                lblLinhaInformativa.setText("Campo cpf aceita no MAX 14 chars");

            }

            if (e.getMessage().equals("Campo SEXO Obrigatorio")) {
                cbSexo.requestFocus();
                cbSexo.setBackground(Color.RED);
                lblLinhaInformativa.setText("Campo SEXO Obrigatorio");

            }

            if (e.getMessage().equals("DEPARTAMENTO OBRIATORIO")) {
                cbDepartamento.requestFocus();
                cbDepartamento.setBackground(Color.RED);

                lblLinhaInformativa.setText("DEPARTAMENTO OBRIATORIO");

            }

        }

    }

    private void acaoBotaoCancelar() {

        /**
         * Após salvar limpar os campos
         */
        /**
         * Após salvar limpar os campos
         */
        limparCampos();
        txtPesquisa.setText("");
        txtCPF.setText("");

        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        desabilitarCampos();

        txtPesquisa.setEnabled(true);
        txtCPF.setEnabled(false);

        /**
         * ao clicar em btnAdicionar fazemos com que btnSalvar fique habilitado
         * para um eventual salvamento
         */
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnAdicionar.setEnabled(true);
        btnValidarCPF.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        txtPesquisa.requestFocus();
        JOptionPane.showMessageDialog(null, "Cadastro cancelado com sucesso!!");

    }

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

            FuncionarioDTO funcionarioDTO = funcionarioDAO.buscarPorIdTblConsultaList(codigo);

            if (!funcionarioDTO.getNomeDto().equals("") || funcionarioDTO.getNomeDto() != null) {
                /**
                 * String.valueOf() pega um Valor Inteiro e transforma em String
                 * e seta em txtIDMedico que é um campo do tipo texto
                 */
                txtID.setText(String.valueOf(funcionarioDTO.getIdDto()));
                txtNome.setText(funcionarioDTO.getNomeDto());
                /**
                 * setSelectedItem para setar uma String que está no Banco de
                 * Dados em um Campo de Combinação em um Form Java
                 */
                cbDepartamento.setSelectedItem(funcionarioDTO.getDepartamentoDto());
                cbSexo.setSelectedItem(funcionarioDTO.getSexoDto());

                String celularSetaCampo = funcionarioDTO.getCelularDto();

                if (celularSetaCampo.equals("(  ) 9    -    ")) {
                    txtCelular.setText(null);
                }

                if (!celularSetaCampo.equals("(  ) 9    -    ")) {
                    txtCelular.setText(celularSetaCampo);
                }
                String cpfSetar = funcionarioDTO.getCpfDto();

                txtCPF.setText(cpfSetar);

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
                txtCPF.setEnabled(false);
                btnValidarCPF.setEnabled(false);
                btnSalvar.setEnabled(false);

                personalizandoBarraInfoPesquisaTermino();

            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar()\n" + ex.getMessage());
        }
    }

    private void acaoAoClicarNumRegistro() {

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

    }

    public void focusLost(java.awt.event.FocusEvent evt) {
        System.out.println("Antes do JOptionPane");
        JOptionPane.showMessageDialog(null, "teste");
        System.out.println("Após do JOptionPane");
    }

    private class TheHandler implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            if (evt.getSource() == btnAdicionar) {
                acaoBotaoAdicionar();
            }

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

            if (evt.getSource() == txtNome) {
                txtNome.setToolTipText("Digite Funcionário");
                txtNome.setBackground(Color.YELLOW);
                txtCPF.requestFocus();
            }

        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBotoesManipulacaoBancoDados;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnValidarCPF;
    private javax.swing.JComboBox cbDepartamento;
    private javax.swing.JComboBox cbSexo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    public static javax.swing.JPanel painelDados;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JFormattedTextField txtCPF;
    private javax.swing.JFormattedTextField txtCelular;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
