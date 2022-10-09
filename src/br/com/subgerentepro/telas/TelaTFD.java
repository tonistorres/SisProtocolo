package br.com.subgerentepro.telas;
//imports padrão das minhas aplicações 

import br.com.subgerentepro.bo.PacienteBO;
import br.com.subgerentepro.dao.PacienteDAO;
import br.com.subgerentepro.dto.PacienteDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.exceptions.ValidacaoException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import static br.com.subgerentepro.telas.TelaPrincipal.lblPerfil;
import static br.com.subgerentepro.telas.TelaUsuario.btnEditar;
import br.com.subgerentepro.util.ValidacaoCNS;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class TelaTFD extends javax.swing.JInternalFrame {

    PacienteDTO pacienteDTO = new PacienteDTO();
    PacienteDAO pacienteDAO = new PacienteDAO();
    PacienteBO pacienteBO = new PacienteBO();

    //https://www.youtube.com/watch?v=-ATbC-4rhc4
    //CRIANDO BOTAO ADICIONAR 
    JButton btnAdicionar = new JButton();
    JButton btnCancelar = new JButton();
   public static JTextField txtBuscar = new JTextField();

    int flag = 0;

    //Chimura: A partir de agora, você usará esse método getInstance() toda vez que quiser criar um ViewEstado
    // Mesma coisa foi feita para ViewBairro
    // Também será necessário fazer para os outros
    private static TelaTFD instance = null;

    public static TelaTFD getInstance() {
        if (instance == null) {
            instance = new TelaTFD();
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

    public TelaTFD() {
        initComponents();
        aoCarregarForm();
        componentesInternoJInternal();
        addRowJTable();
        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tbPaciente.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tbPaciente.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 

    }

    private void componentesInternoJInternal() {
        //https://www.youtube.com/watch?v=-ATbC-4rhc4

        TheHandler th = new TheHandler();
        //ADICIONANDO AOS BOTOES AÇOES QUE SERAO TRATADAS PELO ACTIONLISLISTENER
        this.btnAdicionar.addActionListener(th);
        //   this.btnEditar.addActionListener(th);
        this.btnCancelar.addActionListener(th);
        this.btnExcluir.addActionListener(th);
        this.btnSalvar.addActionListener(th);
        txtBuscar.addActionListener(th);
        AbsoluteLayout layout = new AbsoluteLayout();
        //BOTAO ADICIONAR 
        this.btnAdicionar.setBounds(360, 1, 45, 45);
        this.btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
        this.PanelBotoesManipulacaoBancoDados.add(this.btnAdicionar);

        //BOTAO EDITAR
//        this.btnEditar.setBounds(280, 1, 45, 45);
//        this.btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editar.png")));
//        this.PanelBotoesManipulacaoBancoDados.add(this.btnEditar);
        //BOTAO SALVAR
        this.btnSalvar.setBounds(420, 1, 45, 45);
        this.btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));
        this.PanelBotoesManipulacaoBancoDados.add(this.btnSalvar);
        //BOTAO CANCELAR
        this.btnCancelar.setBounds(480, 1, 45, 45);

        this.btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        this.PanelBotoesManipulacaoBancoDados.add(this.btnCancelar);
        //BOTAO EXCLUIR 
        this.btnExcluir.setBounds(10, 1, 45, 45);
        this.btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/excluir.png")));
        this.PanelBotoesManipulacaoBancoDados.add(this.btnExcluir);
        //BOTAO PESQUISAR 

        //CRIANDO CAMPO PESQUISA JTEXTFIELD
        this.txtBuscar.setBounds(2, 4, 250, 30);
        this.painelPesquisa.add(txtBuscar);

        //SETANDO CAMPOS DE TEXTO DO FORMULARIO A FONTE PADRAO 
        Font fontePesonalizaTxT = new Font("Tahoma", Font.BOLD, 9);
        txtId.setFont(fontePesonalizaTxT);
        txtIdBairro.setFont(fontePesonalizaTxT);
        txtidCidade.setFont(fontePesonalizaTxT);
        txtidEstado.setFont(fontePesonalizaTxT);
        txtPaciente.setFont(fontePesonalizaTxT);
        txtCPF.setFont(fontePesonalizaTxT);
        txtEstado.setFont(fontePesonalizaTxT);
        txtCidade.setFont(fontePesonalizaTxT);
        txtBairro.setFont(fontePesonalizaTxT);
        Font fonteCampoNCartaoSUS = new Font("Tahoma", Font.BOLD, 18);
        txtCartaoSUS.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCartaoSUS.setFont(fonteCampoNCartaoSUS);

    }

    private void desabilitarCampos() {
        this.txtId.setEnabled(false);
        this.txtPaciente.setEnabled(false);
        this.txtCPF.setEnabled(false);
        this.txtidEstado.setEnabled(false);
        this.txtEstado.setEnabled(false);
        this.txtidCidade.setEnabled(false);
        this.txtCidade.setEnabled(false);
        this.txtIdBairro.setEnabled(false);
        this.txtBairro.setEnabled(false);
        this.txtRuaPaciente.setEnabled(false);
        this.txtCartaoSUS.setEnabled(false);

    }

    private void limparCampos() {
        this.txtId.setText("");
        this.txtPaciente.setText("");
        this.txtCPF.setText("");
        this.txtidEstado.setText("");
        this.txtEstado.setText("");
        this.txtidCidade.setText("");
        this.txtCidade.setText("");
        this.txtIdBairro.setText("");
        this.txtBairro.setText("");
        this.txtRuaPaciente.setText("");
        this.txtCartaoSUS.setText("");

    }

    private void habilitarCampos() {

        this.txtPaciente.setEnabled(true);
        this.txtCPF.setEnabled(true);
        this.txtEstado.setEnabled(true);
        this.txtCidade.setEnabled(true);
        this.txtBairro.setEnabled(true);
        this.txtRuaPaciente.setEnabled(true);
        this.txtCartaoSUS.setEnabled(true);

    }

    public void addRowJTable() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            DefaultTableModel model = (DefaultTableModel) tbPaciente.getModel();

            ArrayList<PacienteDTO> list;

            try {
                //robo conectado servidor google 
                list = (ArrayList<PacienteDTO>) pacienteDAO.listarTodos();

                Object rowData[] = new Object[4];

                for (int i = 0; i < list.size(); i++) {

                    rowData[0] = list.get(i).getIdPacienteDto();
                    rowData[1] = list.get(i).getNumeroCartaoSusDto();
                    rowData[2] = list.get(i).getNomePacienteDto();
                    rowData[3] = list.get(i).getCpfPacienteDto();

                    //  System.out.println("ID:" + list.get(i).getIdPacienteDto() + "NSUS:" + list.get(i).getNumeroCartaoSusDto() + "Paciente:" + list.get(i).getNomePacienteDto() + "CPF:" + list.get(i).getCpfPacienteDto());
                    model.addRow(rowData);
                }

                tbPaciente.setModel(model);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tbPaciente.getColumnModel().getColumn(0).setPreferredWidth(33);
                tbPaciente.getColumnModel().getColumn(1).setPreferredWidth(180);
                tbPaciente.getColumnModel().getColumn(2).setPreferredWidth(260);
                tbPaciente.getColumnModel().getColumn(3).setPreferredWidth(80);
            } catch (PersistenciaException ex) {

                //robo conectado ao servidor google 
                erroViaEmail(ex.getMessage(), "Erro:Método addRowTable() FormBairro \n"
                        + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                        + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n");

                JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
                        + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                        + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
            }

        } else {
            //robo conectado ao servidor google 
            erroViaEmail("Erro Conectividade", "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO");

            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    public void aoCarregarForm() {

        //campos ids
        this.txtId.setEnabled(false);
        this.txtIdBairro.setEnabled(false);
        this.txtidEstado.setEnabled(false);
        this.txtidCidade.setEnabled(false);
        //campos diversos
        this.txtPaciente.setEnabled(false);
        this.txtCPF.setEnabled(false);
        this.txtEstado.setEnabled(false);
        this.txtCidade.setEnabled(false);
        this.txtBairro.setEnabled(false);
        this.txtCartaoSUS.setEnabled(false);
        this.txtRuaPaciente.setEnabled(false);

        //botões no momento do carregamento
        this.btnExcluir.setEnabled(false);
        this.btnSalvar.setEnabled(false);
        this.btnValidaCPF.setEnabled(false);
        this.btnEstadoBusca.setEnabled(false);
        this.btnCidadeBusca.setEnabled(false);
        this.btnBairroBusca.setEnabled(false);
        this.btnValidaNCartaoSUS.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.cbSexo.setEnabled(false);
        // this.cbSexo.setSelectedItem(null);
        progressBarraPesquisa.setIndeterminate(true);
    }

    private void desabilitarBotoesComplementaresTFD() {
        this.btnValidaCPF.setEnabled(false);
        this.btnEstadoBusca.setEnabled(false);
        this.btnCidadeBusca.setEnabled(false);
        this.btnBairroBusca.setEnabled(false);
        this.btnValidaNCartaoSUS.setEnabled(false);

    }

    private void habilitarBotoesComplementaresForTFD() {
        this.btnValidaCPF.setEnabled(true);
        this.btnEstadoBusca.setEnabled(true);
        this.btnCidadeBusca.setEnabled(true);
        this.btnBairroBusca.setEnabled(true);
        this.btnValidaNCartaoSUS.setEnabled(true);

    }

    private void acaoBotaoAdicionar() {

        flag = 1;

        /**
         * Como esse campo tem uma validação de cpf que deve ser muito bem
         * validada com vários tratamentos e testes antes de salvar no banco a
         * liberação dos demais campos e açõs que ficariam por conta desse botão
         * ficarãoa cargo do botão btnvalidaCPF
         */
        txtPaciente.setEnabled(true);
        txtCPF.setEnabled(true);

        txtEstado.setEnabled(true);
        txtCidade.setEnabled(true);
        txtBairro.setEnabled(true);
        txtEstado.setEditable(false);
        txtCidade.setEditable(false);
        txtBairro.setEditable(false);
        cbSexo.setEnabled(false);
        //  cbSexo.setSelectedItem(null);

        /**
         * Campos devem ser ativados
         */
        //     habilitarCampos();
        /**
         * Limpar os campos para cadastrar
         */
        limparCampos();

        /**
         * comportamento dos principais botões quando adicionar acionado
         */
        btnAdicionar.setEnabled(false);
        btnPesquisar.setEnabled(false);
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnValidaCPF.setEnabled(true);

        //botoes complementares faz parte do formulario tfd
        //   habilitarBotoesComplementaresForTFD();
        /**
         * Botões que deverão ficar desabilitados nesse evento para esse tipo de
         * Formulario
         */
        txtBuscar.setEnabled(false);

        txtPaciente.requestFocus();//setar o campo nome Bairro após adicionar
        txtPaciente.setBackground(Color.CYAN);  // altera a cor do fundo

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtCartaoSUS = new javax.swing.JFormattedTextField();
        btnValidaNCartaoSUS = new javax.swing.JButton();
        painelDadosPaciente = new javax.swing.JPanel();
        txtId = new javax.swing.JTextField();
        lblId = new javax.swing.JLabel();
        lblPaciente = new javax.swing.JLabel();
        txtPaciente = new javax.swing.JTextField();
        txtidEstado = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        btnEstadoBusca = new javax.swing.JButton();
        txtidCidade = new javax.swing.JTextField();
        txtCidade = new javax.swing.JTextField();
        lblCidade = new javax.swing.JLabel();
        btnCidadeBusca = new javax.swing.JButton();
        txtBairro = new javax.swing.JTextField();
        lblBairro = new javax.swing.JLabel();
        btnBairroBusca = new javax.swing.JButton();
        txtIdBairro = new javax.swing.JTextField();
        lblCPF = new javax.swing.JLabel();
        btnValidaCPF = new javax.swing.JButton();
        txtCPF = new javax.swing.JFormattedTextField();
        txtRuaPaciente = new javax.swing.JTextField();
        lblRuaDoPaciente = new javax.swing.JLabel();
        cbSexo = new javax.swing.JComboBox();
        lblSexo = new javax.swing.JLabel();
        PanelBotoesManipulacaoBancoDados = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        painelPesquisa = new javax.swing.JPanel();
        btnPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPaciente = new javax.swing.JTable();
        lblLinhaInformativa = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbVinculoTFD = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nº Cartão SUS:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCartaoSUS.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCartaoSUS.setForeground(new java.awt.Color(0, 153, 153));
        try {
            txtCartaoSUS.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.####.####.####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCartaoSUS.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCartaoSUS.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtCartaoSUS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCartaoSUSFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCartaoSUSFocusLost(evt);
            }
        });
        txtCartaoSUS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCartaoSUSKeyPressed(evt);
            }
        });
        jPanel1.add(txtCartaoSUS, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 230, 30));

        btnValidaNCartaoSUS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/validation.png"))); // NOI18N
        btnValidaNCartaoSUS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnValidaNCartaoSUSFocusGained(evt);
            }
        });
        btnValidaNCartaoSUS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidaNCartaoSUSActionPerformed(evt);
            }
        });
        btnValidaNCartaoSUS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnValidaNCartaoSUSKeyPressed(evt);
            }
        });
        jPanel1.add(btnValidaNCartaoSUS, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 32, 32));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 290, 60));

        painelDadosPaciente.setBackground(new java.awt.Color(255, 255, 255));
        painelDadosPaciente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        painelDadosPaciente.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 30, 31));

        lblId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblId.setText("ID:");
        painelDadosPaciente.add(lblId, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 36, -1));

        lblPaciente.setText("Paciente:");
        painelDadosPaciente.add(lblPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        txtPaciente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPacienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPacienteFocusLost(evt);
            }
        });
        txtPaciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPacienteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPacienteKeyReleased(evt);
            }
        });
        painelDadosPaciente.add(txtPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 210, 30));
        painelDadosPaciente.add(txtidEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 28, 30));
        painelDadosPaciente.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 160, 30));

        lblEstado.setText("Estado:");
        painelDadosPaciente.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        btnEstadoBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/search.png"))); // NOI18N
        btnEstadoBusca.setToolTipText("Buscar Empresas ");
        btnEstadoBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEstadoBuscaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnEstadoBuscaFocusLost(evt);
            }
        });
        btnEstadoBusca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEstadoBuscaMouseEntered(evt);
            }
        });
        btnEstadoBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoBuscaActionPerformed(evt);
            }
        });
        btnEstadoBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEstadoBuscaKeyPressed(evt);
            }
        });
        painelDadosPaciente.add(btnEstadoBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 32, 32));
        painelDadosPaciente.add(txtidCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 30, 30));

        txtCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCidadeActionPerformed(evt);
            }
        });
        painelDadosPaciente.add(txtCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 220, 30));

        lblCidade.setText("Cidade:");
        painelDadosPaciente.add(lblCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, -1));

        btnCidadeBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/search.png"))); // NOI18N
        btnCidadeBusca.setToolTipText("Buscar Empresas ");
        btnCidadeBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnCidadeBuscaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnCidadeBuscaFocusLost(evt);
            }
        });
        btnCidadeBusca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCidadeBuscaMouseEntered(evt);
            }
        });
        btnCidadeBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCidadeBuscaActionPerformed(evt);
            }
        });
        btnCidadeBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCidadeBuscaKeyPressed(evt);
            }
        });
        painelDadosPaciente.add(btnCidadeBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, 32, 32));

        txtBairro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBairroKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBairroKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBairroKeyTyped(evt);
            }
        });
        painelDadosPaciente.add(txtBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 150, 30));

        lblBairro.setText("Bairro:");
        painelDadosPaciente.add(lblBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        btnBairroBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/search.png"))); // NOI18N
        btnBairroBusca.setToolTipText("Buscar Empresas ");
        btnBairroBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnBairroBuscaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnBairroBuscaFocusLost(evt);
            }
        });
        btnBairroBusca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBairroBuscaMouseEntered(evt);
            }
        });
        btnBairroBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBairroBuscaActionPerformed(evt);
            }
        });
        btnBairroBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBairroBuscaKeyPressed(evt);
            }
        });
        painelDadosPaciente.add(btnBairroBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 32, 32));
        painelDadosPaciente.add(txtIdBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 30, 30));

        lblCPF.setText("CPF:");
        painelDadosPaciente.add(lblCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        btnValidaCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/validation.png"))); // NOI18N
        btnValidaCPF.setPreferredSize(new java.awt.Dimension(32, 32));
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
        painelDadosPaciente.add(btnValidaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 32, 32));

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
        painelDadosPaciente.add(txtCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 110, 30));

        txtRuaPaciente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRuaPacienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRuaPacienteFocusLost(evt);
            }
        });
        txtRuaPaciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRuaPacienteKeyPressed(evt);
            }
        });
        painelDadosPaciente.add(txtRuaPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 280, 32));

        lblRuaDoPaciente.setText("Rua:");
        painelDadosPaciente.add(lblRuaDoPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, -1, -1));

        cbSexo.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cbSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MASCULINO", "FEMININO", "OUTROS" }));
        cbSexo.setPreferredSize(new java.awt.Dimension(84, 30));
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
        painelDadosPaciente.add(cbSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 100, -1));

        lblSexo.setText("Sexo:");
        painelDadosPaciente.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 30, -1));

        getContentPane().add(painelDadosPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 530, 190));

        PanelBotoesManipulacaoBancoDados.setBackground(new java.awt.Color(255, 255, 255));

        btnSalvar.setText("Salvar");
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
                .addGap(19, 19, 19)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 311, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
        );
        PanelBotoesManipulacaoBancoDadosLayout.setVerticalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        getContentPane().add(PanelBotoesManipulacaoBancoDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, 50));

        painelPesquisa.setBackground(java.awt.Color.white);

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesquisarFocusGained(evt);
            }
        });
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        tbPaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "N CARTAO SUS", "PACIENTE", "CPF"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPacienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPaciente);
        if (tbPaciente.getColumnModel().getColumnCount() > 0) {
            tbPaciente.getColumnModel().getColumn(0).setMinWidth(45);
            tbPaciente.getColumnModel().getColumn(0).setPreferredWidth(45);
            tbPaciente.getColumnModel().getColumn(0).setMaxWidth(45);
            tbPaciente.getColumnModel().getColumn(1).setMinWidth(120);
            tbPaciente.getColumnModel().getColumn(1).setPreferredWidth(120);
            tbPaciente.getColumnModel().getColumn(1).setMaxWidth(120);
            tbPaciente.getColumnModel().getColumn(2).setMinWidth(230);
            tbPaciente.getColumnModel().getColumn(2).setPreferredWidth(230);
            tbPaciente.getColumnModel().getColumn(2).setMaxWidth(230);
            tbPaciente.getColumnModel().getColumn(3).setMinWidth(100);
            tbPaciente.getColumnModel().getColumn(3).setPreferredWidth(100);
            tbPaciente.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        javax.swing.GroupLayout painelPesquisaLayout = new javax.swing.GroupLayout(painelPesquisa);
        painelPesquisa.setLayout(painelPesquisaLayout);
        painelPesquisaLayout.setHorizontalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        painelPesquisaLayout.setVerticalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(painelPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 530, 160));

        lblLinhaInformativa.setBackground(new java.awt.Color(51, 255, 51));
        lblLinhaInformativa.setText("Linha Info");
        getContentPane().add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 54, 370, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Vinculado Setor Saude:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 160, 10));

        cbVinculoTFD.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cbVinculoTFD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TFD (SECRETARIA DE SAUDE)" }));
        cbVinculoTFD.setEnabled(false);
        cbVinculoTFD.setPreferredSize(new java.awt.Dimension(64, 30));
        getContentPane().add(cbVinculoTFD, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 290, 220, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 60, 40));
        getContentPane().add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 60, 90, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void acaoExcluir() {

        try {

            /*Evento ao ser clicado executa código*/
            int excluir = JOptionPane.showConfirmDialog(null, "Deseja excluir registro?", "Informação", JOptionPane.YES_NO_OPTION);

            if (excluir == JOptionPane.YES_OPTION) {

                pacienteDTO.setIdPacienteDto(Integer.parseInt(txtId.getText()));

                /**
                 * Chamando o método que irá executar a Edição dos Dados em
                 * nosso Banco de Dados
                 */
                //robo conectado servidor google 
                pacienteDAO.deletar(pacienteDTO);

                //ações após exclusão
                comportamentoAposExclusao();

            }
        } catch (Exception e) {
            //rbo conectado ao servidor google
            erroViaEmail(e.getMessage(), "acaoExcluir - Camada GUI\n"
                    + "erro ao tentar deletar um registro ");
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Camada GUI" + e.getMessage());
        }

    }

    private void comportamentoAposExclusao() {
        /**
         * Ações de Botões
         */

        btnExcluir.setEnabled(false);
        btnAdicionar.setEnabled(true);
        /**
         * Após salvar limpar os campos
         */
        limparCampos();
        desabilitarCampos();
        /**
         * Atualiza a tabela
         */
        int numeroLinhas = tbPaciente.getRowCount();

        if (numeroLinhas > 0) {

            while (tbPaciente.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tbPaciente.getModel()).removeRow(0);

            }
//robo conectado servidor google 
            addRowJTable();

        } else {
//robo conectado servidor google 
            addRowJTable();

        }

    }

    private void acaoBotaoExcluir() {
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            //robo conectado servidor google 
            acaoExcluir();

        } else {
            
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            //  desabilitarCampos();
            //  desabilitaBotoes();
        }
    }

    private void SalvarAdicoesEdicoes() throws PersistenciaException, ValidacaoException {

        /**
         * 1 - Faz o tratamento com a Condicional if 2 - seta o valor do campo
         * tratado
         */
        if (!this.txtidEstado.getText().equals("")
                && !this.txtidCidade.getText().equals("")
                && !this.txtIdBairro.getText().equals("")) {

            pacienteDTO.setFkEstadoDto(Integer.parseInt(this.txtidEstado.getText()));
            pacienteDTO.setFkCidadeDto(Integer.parseInt(this.txtidCidade.getText()));
            pacienteDTO.setFkBairroDto(Integer.parseInt(this.txtIdBairro.getText()));
        }

        pacienteDTO.setNomePacienteDto(txtPaciente.getText());
        pacienteDTO.setEstadoDto(txtEstado.getText());
        pacienteDTO.setCidadeDto(txtCidade.getText());
        pacienteDTO.setBairroDto(txtBairro.getText());
        pacienteDTO.setCpfPacienteDto(txtCPF.getText());
        pacienteDTO.setRuaPacienteDto(txtRuaPaciente.getText());
        pacienteDTO.setNumeroCartaoSusDto(txtCartaoSUS.getText());

        if (cbVinculoTFD.getSelectedItem() != null) {
            pacienteDTO.setVinculoDto(new String((String) cbVinculoTFD.getSelectedItem()));
        }
        if (cbSexo.getSelectedItem() != null) {
            pacienteDTO.setSexoDto(new String((String) cbSexo.getSelectedItem()));
        }
        try {

            //TRABALHAR AS VALIDAÇÕES NA HORA DO SALVAMENTOS 
            boolean recebeRetornoDuplicidade = pacienteBO.duplicidade(pacienteDTO);//verificar se já existe CPF
            boolean recebeRetornoIndexado = pacienteBO.validacaoBOdosCamposForm(pacienteDTO);

            if ((flag == 1)) {

                //  JOptionPane.showMessageDialog(null, "retorno Paciente:"+recebeRetornoCampoPaciente);
                if (recebeRetornoDuplicidade == false) {

                    //robo conectado servidor google 
                    pacienteBO.cadastrarBO(pacienteDTO);
                    /**
                     * Após salvar limpar os campos
                     */
                    limparCampos();

                    /**
                     * Bloquear campos e Botões
                     */
                    desabilitarCampos();
                    cbSexo.setEnabled(false);
                    /**
                     * Liberar campos necessário para operações após salvamento
                     */

                    //desabilitar botoes complementares
                    desabilitarBotoesComplementaresTFD();

                    btnSalvar.setEnabled(false);
                    btnAdicionar.setEnabled(true);
                    btnCancelar.setEnabled(true);
                    btnPesquisar.setEnabled(true);
                    txtBuscar.setEnabled(true);
                    cbSexo.setSelectedItem(null);
                    JOptionPane.showMessageDialog(this, "" + "\n Cadastrado com Sucesso.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

                    int numeroLinhas = tbPaciente.getRowCount();

                    if (numeroLinhas > 0) {

                        while (tbPaciente.getModel().getRowCount() > 0) {
                            ((DefaultTableModel) tbPaciente.getModel()).removeRow(0);

                        }
                        //robo conectado servidor google     
                        addRowJTable();

                    } else {
                        //robo conectado servidor google 
                        addRowJTable();

                    }

                } else {

                    //   JOptionPane.showMessageDialog(this, "" + "\n CAMADA GUI ANALISE 1 PARA DICIONAR: Resgistro já cadastrado.\nSistema Impossibilitou \n a Duplicidade", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    txtCPF.requestFocus();
                    txtCPF.setBackground(Color.RED);

                }

            } else {

                //tratado com condicional if 
                if (!this.txtId.getText().equals("")) {
                    pacienteDTO.setIdPacienteDto(Integer.parseInt(txtId.getText()));
                }
                //robo conectado servidor google 
                pacienteBO.atualizarBO(pacienteDTO);
                /**
                 * Após salvar limpar os campos
                 */
                limparCampos();
                cbSexo.setSelectedItem(null);
                /**
                 * Bloquear campos e Botões
                 */
                desabilitarCampos();
                cbSexo.setEnabled(false);
                /**
                 * Liberar campos necessário para operações após salvamento
                 */
                btnAdicionar.setEnabled(true);
                btnCancelar.setEnabled(false);
                btnSalvar.setEnabled(false);
                //  holders();
                JOptionPane.showMessageDialog(this, "" + "\n Edição Salva com Sucesso ", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

                int numeroLinhas = tbPaciente.getRowCount();

                if (numeroLinhas > 0) {

                    while (tbPaciente.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tbPaciente.getModel()).removeRow(0);

                    }
                    //robo conectado servidor google 
                    addRowJTable();

                } else {
                    //robo conectado servidor google 
                    addRowJTable();

                }

            }//fecha blco else 
        } catch (Exception e) {

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo paciente Obrigatorio")) {
                txtPaciente.requestFocus();
                txtPaciente.setBackground(Color.YELLOW);

                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo paciente aceita no MAX 49 chars")) {
                txtPaciente.requestFocus();
                txtPaciente.setBackground(Color.YELLOW);

            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo estado Obrigatorio")) {
                txtEstado.requestFocus();
                txtEstado.setBackground(Color.YELLOW);
            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo estado aceita no MAX 40 chars")) {

                txtEstado.requestFocus();
                txtEstado.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }
            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo cidade Obrigatorio")) {
                txtCidade.requestFocus();
                txtCidade.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (BO-Busines Object) [Endereco] obrigatorio
            if (e.getMessage().equals("Campo cidade aceita no MAX 49 chars")) {
                txtCidade.requestFocus();
                txtCidade.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }
            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo bairro Obrigatorio")) {
                txtBairro.requestFocus();
                txtBairro.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo bairro aceita no MAX 49 chars")) {
                txtBairro.requestFocus();
                txtBairro.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo cpf Obrigatorio")) {
                txtCPF.requestFocus();
                txtCPF.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo cpf aceita no MAX 14 chars")) {
                txtCPF.requestFocus();
                txtCPF.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (B)-Busines Object)
            if (e.getMessage().equals("Campo Numero Cartao SUS  Obrigatorio")) {
                txtCartaoSUS.requestFocus();
                txtCartaoSUS.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo rua Obrigatorio")) {
                txtRuaPaciente.requestFocus();
                txtRuaPaciente.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo rua aceita no MAX 49 chars")) {
                txtRuaPaciente.requestFocus();
                txtRuaPaciente.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo Numero Cartao SUS Obrigatorio")) {
                txtCartaoSUS.requestFocus();
                txtCartaoSUS.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

        }
    }

    private void acaoBotaoSalvar() {
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            try {
                SalvarAdicoesEdicoes();
            } catch (PersistenciaException ex) {
                JOptionPane.showMessageDialog(null, "CAMADA GUI:\n" + ex.getMessage());
            } catch (ValidacaoException ex) {
                erroViaEmail(ex.getMessage(), "acaoBotaoSalvar()- Camada GUI - TelaTFD.java");
                ex.printStackTrace();
            }
        } else {
            //robo conectado ao servidor da google 
            erroViaEmail("Erro Conectividade", "acaoBotaoSalvar()-Camada GUI\n"
                    + "erro ao tentar salvar registro sem conectividade com o banco de dados ");

            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            //    desabilitarCampos();
            //     desabilitaBotoes();
        }
    }

    private void acaoBotaoPesquisar() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {
            acaoBTNPesquisar();

        } else {
            //robo conectado servidor google 
            erroViaEmail("erro de conectividade", "acaoBotaoPesquisar()\n"
                    + "camada GUI - TelaTFD - ao tentar executar o botão de pesquisa\n"
                    + "do formulario em questãio");
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }
    }
    private void btnEstadoBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEstadoBuscaFocusGained
        btnEstadoBusca.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 14);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Click ou Pressione [ENTER] e Selecione a UF");
    }//GEN-LAST:event_btnEstadoBuscaFocusGained

    private void btnEstadoBuscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEstadoBuscaFocusLost
        btnEstadoBusca.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnEstadoBuscaFocusLost

    private void btnEstadoBuscaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstadoBuscaMouseEntered

    }//GEN-LAST:event_btnEstadoBuscaMouseEntered

    br.com.subgerentepro.telas.FrmListaEstadosTFD formListaEstadoTFD;

    private void buscarEstado() {

        if (estaFechado(formListaEstadoTFD)) {
            formListaEstadoTFD = new FrmListaEstadosTFD();
            DeskTop.add(formListaEstadoTFD).setLocation(480, 40);
            formListaEstadoTFD.setTitle("Lista Estados");

//clicou nessa opção cinco campos automaticamente dever ser limpos 
            txtidCidade.setText("");
            txtCidade.setText("");
            txtIdBairro.setText("");
            txtBairro.setText("");
            txtRuaPaciente.setText("");

            formListaEstadoTFD.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formListaEstadoTFD.toFront();
            formListaEstadoTFD.show();

        }

    }


    private void btnEstadoBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoBuscaActionPerformed
        buscarEstado();
    }//GEN-LAST:event_btnEstadoBuscaActionPerformed

    private void btnCidadeBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCidadeBuscaFocusGained
        btnCidadeBusca.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 14);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Click ou Pressione [ENTER] e Selecione a Cidade");
    }//GEN-LAST:event_btnCidadeBuscaFocusGained

    private void btnCidadeBuscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCidadeBuscaFocusLost
        btnCidadeBusca.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnCidadeBuscaFocusLost

    private void btnCidadeBuscaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCidadeBuscaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCidadeBuscaMouseEntered

    br.com.subgerentepro.telas.FrmListaCidadesTFD formListaCidades;

    private void buscarCidade() {

        if (estaFechado(formListaCidades)) {
            formListaCidades = new FrmListaCidadesTFD();
            DeskTop.add(formListaCidades).setLocation(500, 40);
            formListaCidades.setTitle("Lista Cidaddes");
            //neste campo 3(tres) campos devem ser limpos automaticamente 
            txtIdBairro.setText("");
            txtBairro.setText("");
            txtRuaPaciente.setText("");

            formListaCidades.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            txtBairro.setEditable(true);
            formListaCidades.toFront();
            formListaCidades.show();

        }

    }


    private void btnCidadeBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCidadeBuscaActionPerformed
        if (!txtEstado.getText().equals("")) {
            buscarCidade();
        }

        if (txtEstado.getText().equals("")) {
            Font f = new Font("Tahoma", Font.BOLD, 14);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("É necessário haver um Estado Selecionado");
            buscarEstado();
        }

    }//GEN-LAST:event_btnCidadeBuscaActionPerformed

    private void txtBairroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBairroKeyReleased
        txtBairro.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtBairro.getText()));
    }//GEN-LAST:event_txtBairroKeyReleased

    private void txtBairroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBairroKeyTyped

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
        int numeroDeCaracter = 49;

        if (txtBairro.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute
            JOptionPane.showMessageDialog(null, "Máxmimo 50 caracter");// colocamos uma mensagem alertar usuário
            txtBairro.setBackground(Color.YELLOW);//modificamos a cor do campo para visualmente indicar ao usuário o erro
            //lblLinhaInformativa.setText("Campo Nome: Máximo 50 caracteres");

        }
    }//GEN-LAST:event_txtBairroKeyTyped

    private void btnBairroBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBairroBuscaFocusGained
        btnBairroBusca.setBackground(Color.YELLOW);

        Font f = new Font("Tahoma", Font.BOLD, 14);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Click ou Pressione [ENTER] e Selecione o Bairro");
    }//GEN-LAST:event_btnBairroBuscaFocusGained

    private void btnBairroBuscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBairroBuscaFocusLost
        btnBairroBusca.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnBairroBuscaFocusLost

    private void btnBairroBuscaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBairroBuscaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBairroBuscaMouseEntered

    br.com.subgerentepro.telas.FrmListaBairrosTFD formListaBairros;

    private void buscarBairro() {

        if (estaFechado(formListaBairros)) {
            formListaBairros = new FrmListaBairrosTFD();
            DeskTop.add(formListaBairros).setLocation(510, 40);
            formListaBairros.setTitle("Lista Bairros Cadastrados");
            formListaBairros.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formListaBairros.toFront();
            formListaBairros.show();

        }

    }


    private void btnBairroBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBairroBuscaActionPerformed
        if (!txtCidade.getText().equals("")) {
            buscarBairro();
        }

        if (txtCidade.getText().equals("")) {
            Font f = new Font("Tahoma", Font.BOLD, 14);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("É necessário haver uma Cidade Selecionado");
            buscarCidade();
        }
    }//GEN-LAST:event_btnBairroBuscaActionPerformed

    private void btnValidaCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidaCPFActionPerformed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            acaoValidaCPFPolindoDados();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
        }
    }//GEN-LAST:event_btnValidaCPFActionPerformed

    private void tbPacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPacienteMouseClicked

        acaoAoSelecionarRegistroTabela();


    }//GEN-LAST:event_tbPacienteMouseClicked

    private void txtCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCidadeActionPerformed

    private void txtCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFActionPerformed

    private void txtPacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPacienteKeyReleased


    }//GEN-LAST:event_txtPacienteKeyReleased

    private void txtPacienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPacienteFocusLost
        txtPaciente.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtPacienteFocusLost

    private void txtPacienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPacienteKeyPressed
        if (txtPaciente.getText().equals("")) {
            txtPaciente.requestFocus();
            Font f = new Font("Tahoma", Font.BOLD, 14);
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Obrigatório Digitar o NOME do Paciente");

        }
        if (!txtPaciente.getText().equals("")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                txtCPF.requestFocus();
            }
        }
    }//GEN-LAST:event_txtPacienteKeyPressed

    private void txtCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusGained
        txtCPF.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 14);
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite o CPF");

        if (this.txtPaciente.getText().toString().isEmpty()) {
            this.txtPaciente.requestFocus();
            this.txtPaciente.setBackground(Color.YELLOW);
        } else {
            txtPaciente.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtPaciente.getText()));
            this.txtPaciente.setBackground(Color.white);
        }

    }//GEN-LAST:event_txtCPFFocusGained

    private void acaoValidaCartaoSUSPolindoDados() {

        pacienteDTO.setNumeroCartaoSusDto(txtCartaoSUS.getText());

        try {
            //robo servidor google
            boolean retornoVerifcaDuplicidade = pacienteDAO.verificaDuplicidadeCartaoSUS(pacienteDTO);//verificar se já existe CNPJ

            if (retornoVerifcaDuplicidade == false) {

                int cont = 0;
                for (int i = 0; i < pacienteDTO.getNumeroCartaoSusDto().length(); i++) {

                    cont += 1;//Aqui o contador começa a ser incrementado em mais um a cada passada 

                    //Quando o contador estiver na posicao 3 execute o codigo abaixo 
                    if (cont == 4) {

                        if (pacienteDTO.getNumeroCartaoSusDto().charAt(i) == '.') {

                            pacienteDTO.setNumeroCartaoSusDto(pacienteDTO.getNumeroCartaoSusDto().replace(pacienteDTO.getNumeroCartaoSusDto().charAt(i), ' '));

                        }

                    }

                }

                String cartaoSusTratado = pacienteDTO.getNumeroCartaoSusDto().replace(" ", "");

                ValidacaoCNS validaCNS = new ValidacaoCNS();

                boolean recebeCartaSUS = validaCNS.isValid(cartaoSusTratado);

                if (recebeCartaSUS == true) {
                    btnSalvar.requestFocus();
                    try {
                        SalvarAdicoesEdicoes();
                    } catch (PersistenciaException ex) {
                        
                        //robo conectado servidor google 
                        erroViaEmail(ex.getMessage(), "acaoValidaCartaoSUSPolindoDados()- camada GUI");
                        ex.printStackTrace();
                    } catch (ValidacaoException ex) {
                        
                        //robo conectado servidor google 
                        erroViaEmail(ex.getMessage(), "acaoValidaCartaoSUSPolindoDados()- camada GUI");
                        ex.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "" + "\n Cartao Invalido.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    txtCartaoSUS.setBackground(Color.YELLOW);
                    txtCartaoSUS.requestFocus();
                }

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Registro Duplicado.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                txtCartaoSUS.requestFocus();
                txtCartaoSUS.setBackground(Color.RED);

            }
        } catch (PersistenciaException ex) {
           //robo conectado servidor google 
            erroViaEmail(ex.getMessage(), "acaoValidaCartaoSUSPolindoDados() - camada GUI\n"
                    + "erro ao tentar validar ncartaoSUS");
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    private void acaoCNPJTrue() {
        habilitarBotoesComplementaresForTFD();
        txtBairro.setEditable(false);
        txtCartaoSUS.setEnabled(true);
        txtRuaPaciente.setEnabled(true);
        cbSexo.setEnabled(true);
        btnSalvar.requestFocus();
    }


    private void btnValidaNCartaoSUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidaNCartaoSUSActionPerformed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            acaoValidaCartaoSUSPolindoDados();
        } else {
           //robo conectado servidor google 
            erroViaEmail("Erro de conectividade", "Camada GUI - TelaTFD\n"
                    + "ao clicar no btnValidaNCartaoSUS -\n"
                    + "sem conectividade com a internet");
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
        }

    }//GEN-LAST:event_btnValidaNCartaoSUSActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        acaoBotaoPesquisar();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtPacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPacienteFocusGained
        txtPaciente.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 14);
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite o NOME do Paciente");
    }//GEN-LAST:event_txtPacienteFocusGained

    private void txtCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusLost
        txtCPF.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtCPFFocusLost

    private void btnValidaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusGained
        btnValidaCPF.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 14);
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Click sobre o Botao em Foco ou Pressione [ENTER] validar CPF");
    }//GEN-LAST:event_btnValidaCPFFocusGained

    private void btnValidaCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusLost
        btnValidaCPF.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnValidaCPFFocusLost

    private void cbSexoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbSexoFocusGained
        cbSexo.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 14);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Selecione o Sexo do Paciente");
    }//GEN-LAST:event_cbSexoFocusGained

    private void cbSexoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbSexoFocusLost
        cbSexo.setBackground(Color.WHITE);
    }//GEN-LAST:event_cbSexoFocusLost

    private void txtRuaPacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRuaPacienteFocusGained
        txtRuaPaciente.setBackground(Color.YELLOW);
    }//GEN-LAST:event_txtRuaPacienteFocusGained

    private void txtRuaPacienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRuaPacienteFocusLost
        txtRuaPaciente.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtRuaPacienteFocusLost

    private void txtCartaoSUSFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCartaoSUSFocusGained
        txtCartaoSUS.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 14);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Digite o Número do Cartão SUS");
    }//GEN-LAST:event_txtCartaoSUSFocusGained

    private void txtCartaoSUSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCartaoSUSFocusLost
        txtCartaoSUS.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtCartaoSUSFocusLost

    private void txtCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFKeyPressed
        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtPaciente.requestFocus();
        }

        if (!txtCPF.getText().equals("   .   .   -  ")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                btnValidaCPF.requestFocus();
            }
        }
        if (txtCPF.getText().equals("   .   .   -  ")) {

            txtCPF.requestFocus();

            Font f = new Font("Tahoma", Font.BOLD, 14);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Obrigatório Digitar um CPF e Validá-lo");
        }


    }//GEN-LAST:event_txtCPFKeyPressed

    private void btnValidaCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnValidaCPFKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

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

        if (evt.getKeyCode() == evt.VK_RIGHT) {
            cbSexo.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtCPF.requestFocus();
        }
    }//GEN-LAST:event_btnValidaCPFKeyPressed

    private void cbSexoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbSexoKeyPressed
        if (evt.getKeyCode() == evt.VK_LEFT) {
            btnValidaCPF.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_ENTER) {
            btnEstadoBusca.requestFocus();
        }
    }//GEN-LAST:event_cbSexoKeyPressed

    private void btnEstadoBuscaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEstadoBuscaKeyPressed
        if (evt.getKeyCode() == evt.VK_LEFT) {
            cbSexo.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_ENTER) {
            btnCidadeBusca.requestFocus();
        }

    }//GEN-LAST:event_btnEstadoBuscaKeyPressed

    private void btnCidadeBuscaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCidadeBuscaKeyPressed
        if (evt.getKeyCode() == evt.VK_LEFT) {
            btnEstadoBusca.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            txtBairro.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_ENTER) {
            if (!txtEstado.getText().equals("")) {
                buscarCidade();
            }

            if (txtEstado.getText().equals("")) {
                Font f = new Font("Tahoma", Font.BOLD, 14);
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setForeground(Color.BLUE);
                lblLinhaInformativa.setText("É necessário haver um Estado Selecionado");
                buscarEstado();
            }

        }

    }//GEN-LAST:event_btnCidadeBuscaKeyPressed

    private void txtBairroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBairroKeyPressed
        if (evt.getKeyCode() == evt.VK_LEFT) {
            btnCidadeBusca.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_ENTER) {
            btnBairroBusca.requestFocus();
        }
    }//GEN-LAST:event_txtBairroKeyPressed

    private void txtRuaPacienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRuaPacienteKeyPressed
        if (evt.getKeyCode() == evt.VK_LEFT) {
            btnBairroBusca.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_ENTER) {
            txtCartaoSUS.requestFocus();
        }
    }//GEN-LAST:event_txtRuaPacienteKeyPressed

    private void txtCartaoSUSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCartaoSUSKeyPressed
        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtRuaPaciente.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_ENTER) {
            btnValidaNCartaoSUS.requestFocus();
        }
    }//GEN-LAST:event_txtCartaoSUSKeyPressed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        acaoBotaoSalvar();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained
        btnSalvar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnSalvarFocusGained

    private void btnSalvarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusLost
        btnSalvar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnSalvarFocusLost

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_F7) {
            acaoBotaoSalvar();
        }

    }//GEN-LAST:event_btnSalvarKeyPressed

    private void btnValidaNCartaoSUSFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaNCartaoSUSFocusGained
        Font f = new Font("Tahoma", Font.BOLD, 14);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Click ou Pressione [ENTER] para Validar Cartão SUS");
    }//GEN-LAST:event_btnValidaNCartaoSUSFocusGained

    private void btnValidaNCartaoSUSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnValidaNCartaoSUSKeyPressed
        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtCartaoSUS.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_ENTER) {

            ConexaoUtil conecta = new ConexaoUtil();
            boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

            if (recebeConexao == true) {
                //robo conectado google 
                acaoValidaCartaoSUSPolindoDados();
            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

        }
    }//GEN-LAST:event_btnValidaNCartaoSUSKeyPressed

    private void btnBairroBuscaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBairroBuscaKeyPressed
        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtBairro.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            txtRuaPaciente.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_ENTER) {
            if (!txtCidade.getText().equals("")) {
                buscarBairro();
            }

            if (txtEstado.getText().equals("")) {
                Font f = new Font("Tahoma", Font.BOLD, 14);
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setForeground(Color.BLUE);
                lblLinhaInformativa.setText("É necessário haver uma Cidade Selecionado");
                buscarCidade();
            }
        }
    }//GEN-LAST:event_btnBairroBuscaKeyPressed

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        if (txtBuscar.getText().toString().trim().equals("")) {
            //JOptionPane.showMessageDialog(this, "" + "\n Campo Pesquisa NULO", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            Font f = new Font("Tahoma", Font.BOLD, 14);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setForeground(Color.RED);
            lblLinhaInformativa.setText("Campo de Pesquisa NULO");
            txtBuscar.setBackground(Color.CYAN);
            txtBuscar.requestFocus();
        }

        if (!txtBuscar.getText().toString().trim().equals("")) {
            acaoBotaoPesquisar();
        }
    }//GEN-LAST:event_btnPesquisarFocusGained

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (!lblPerfil.getText().equalsIgnoreCase("protocolo")) {

            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Acesso LIBERADO para Exclusão");

            acaoBotaoExcluir();
        }

        if (lblPerfil.getText().equalsIgnoreCase("protocolo")) {
            btnExcluir.setEnabled(false);
            btnEditar.setEnabled(false);

            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Registros Protegidos Proibido Exclusão.");

        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained
        btnExcluir.setBackground(Color.YELLOW);
        if (lblPerfil.getText().equalsIgnoreCase("protocolo")) {
            btnExcluir.setEnabled(false);
//            btnEditar.setEnabled(false);

            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Registros Protegidos Proibido Exclusão.");

        }


    }//GEN-LAST:event_btnExcluirFocusGained

    private void btnExcluirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusLost
        btnExcluir.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnExcluirFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBotoesManipulacaoBancoDados;
    public static javax.swing.JButton btnBairroBusca;
    public static javax.swing.JButton btnCidadeBusca;
    public static javax.swing.JButton btnEstadoBusca;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnValidaCPF;
    private javax.swing.JButton btnValidaNCartaoSUS;
    private javax.swing.JComboBox cbSexo;
    private javax.swing.JComboBox cbVinculoTFD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblPaciente;
    private javax.swing.JLabel lblRuaDoPaciente;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JPanel painelDadosPaciente;
    private javax.swing.JPanel painelPesquisa;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tbPaciente;
    public static javax.swing.JTextField txtBairro;
    private javax.swing.JFormattedTextField txtCPF;
    private javax.swing.JFormattedTextField txtCartaoSUS;
    public static javax.swing.JTextField txtCidade;
    public static javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtId;
    public static javax.swing.JTextField txtIdBairro;
    private javax.swing.JTextField txtPaciente;
    private javax.swing.JTextField txtRuaPaciente;
    public static javax.swing.JTextField txtidCidade;
    public static javax.swing.JTextField txtidEstado;
    // End of variables declaration//GEN-END:variables

    private void acaoBotaoCancelar() {
        /**
         * Após salvar limpar os campos
         */
        limparCampos();
        cbSexo.setSelectedItem(null);
        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        desabilitarCampos();
        cbSexo.setEnabled(false);

        //desabilitar botoes complementares tfd
        desabilitarBotoesComplementaresTFD();

        /**
         * ao clicar em btnAdicionar fazemos com que btnSalvar fique habilitado
         * para um eventual salvamento
         */
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
//        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnPesquisar.setEnabled(true);
        txtBuscar.setEnabled(true);
        btnAdicionar.setEnabled(true);

        JOptionPane.showMessageDialog(null, "Cadastro cancelado com sucesso!!");
    }

    private void desabilitarTodosBotoes() {

        btnAdicionar.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);

        //   btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }

    private void acaoEditar() {
        if (txtPaciente.equals("")) {

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
//            btnEditar.setEnabled(false);
            btnAdicionar.setEnabled(false);
            btnExcluir.setEnabled(false);

        }

    }

    private void acaoAoSelecionarRegistroTabela() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            acaoNoClickRegistroTabela();

        } else {
            erroViaEmail("Sem Conectividade: \n"
                    + " Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO", "acaoAoSelecionarRegistroTabela()\n"
                    + "-Camada GUI - TelaTFD");

            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    private void erroViaEmail(String mensagemErro, String metodo) {

        String meuEmail = "sisprotocoloj@gmail.com";
        String minhaSenha = "gerlande2111791020";

        //agora iremos utilizar os objetos das Bibliotecas referente Email
        //commons-email-1.5.jar e mail-1.4.7.jar
        SimpleEmail email = new SimpleEmail();
        //Agora podemos utilizar o objeto email do Tipo SimplesEmail
        //a primeira cois a fazer é acessar o host abaixo estarei usando
        //o host do google para enviar as informações
        email.setHostName("smtp.gmail.com");
        //O proximo método abaixo é para configurar a porta desse host
        email.setSmtpPort(465);
        //esse método vai autenticar a conexão o envio desse email
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        //esse método vai ativar a conexao de forma segura 
        email.setSSLOnConnect(true);

        //agora um try porque os outro métodos podem gerar erros daí devem está dentro de um bloco de exceções
        try {
            //configura de quem é esse email de onde ele está vindo 
            email.setFrom(meuEmail);
            //configurar o assunto 
            email.setSubject("Metodo:" + metodo);
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Mensagem:" + mensagemErro + "\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            erroViaEmail(mensagemErro, metodo);

            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Erro:" + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            e.printStackTrace();
        }

    }

    private void pesquisarUsuario() {
        String pesquisarUsuario = MetodoStaticosUtil.removerAcentosCaixAlta(txtBuscar.getText());

        DefaultTableModel model = (DefaultTableModel) tbPaciente.getModel();

        ArrayList<PacienteDTO> list;

        try {

            list = (ArrayList<PacienteDTO>) pacienteDAO.filtrarUsuarioPesqRapida(pesquisarUsuario);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdPacienteDto();
                rowData[1] = list.get(i).getNumeroCartaoSusDto();
                rowData[2] = list.get(i).getNomePacienteDto();
                rowData[3] = list.get(i).getCpfPacienteDto();

                model.addRow(rowData);
            }

            tbPaciente.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tbPaciente.getColumnModel().getColumn(0).setPreferredWidth(45);
            tbPaciente.getColumnModel().getColumn(1).setPreferredWidth(520);
            tbPaciente.getColumnModel().getColumn(2).setPreferredWidth(380);
            tbPaciente.getColumnModel().getColumn(3).setPreferredWidth(260);
            personalizandoBarraInfoPesquisaTermino();
        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "pesquisarUsuario() \n"
                    + "Camada GUI - TelaTFD");
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void acaoBTNPesquisar() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            int numeroLinhas = tbPaciente.getRowCount();

            if (numeroLinhas > 0) {

                while (tbPaciente.getModel().getRowCount() > 0) {
                    ((DefaultTableModel) tbPaciente.getModel()).removeRow(0);

                }

                pesquisarUsuario();

            } else {
                addRowJTable();
            }

        } else {

            erroViaEmail("erro Conectividade", "acaoBTNPesquisar()- erro ao tentar\n"
                    + " pesquisar por usuarios");
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }

    }

    private void acaoNoClickRegistroTabela() {

        flag = 2;

        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/
        int codigo = Integer.parseInt("" + tbPaciente.getValueAt(tbPaciente.getSelectedRow(), 0));
        /**
         * Esse código está comentado só para ficar o exemplo de como pegaria o
         * valor de nome da tabela ou seja coluna 1 sendo que falando neses caso
         * trabalhamos como vetor que inicial do zero(0)
         */
        /*   cidadeDTO.setNomeCidadeDto("" + tblCidadesList.getValueAt(tblCidadesList.getSelectedRow(), 1));*/

        try {
            //robo conectado servidor google 
            PacienteDTO retorno = pacienteDAO.buscarPorIdTblConsultaList(codigo);

            if (retorno.getNomePacienteDto() != null || !retorno.getNomePacienteDto().equals("")) {

                if (lblPerfil.getText().equalsIgnoreCase("protocolo")
                        && retorno.getCpfPacienteDto().isEmpty()
                        && retorno.getNumeroCartaoSusDto().isEmpty()) {

                    Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 
                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setForeground(Color.BLUE);
                    lblLinhaInformativa.setText("Liberado p/ Alteração prioridade [CPF/Cartao SUS].");
                    txtCPF.setBackground(Color.YELLOW);
                    txtCartaoSUS.setBackground(Color.YELLOW);

                    /**
                     * SETAR CAMPOS com dados retornados do método buscar por id
                     * no banco
                     */
                    this.txtId.setText(String.valueOf(retorno.getIdPacienteDto()));
                    this.txtIdBairro.setText(String.valueOf(retorno.getFkBairroDto()));
                    this.txtidEstado.setText(String.valueOf(retorno.getFkEstadoDto()));
                    this.txtidCidade.setText(String.valueOf(retorno.getFkCidadeDto()));
                    this.txtPaciente.setText(retorno.getNomePacienteDto());
                    this.txtCPF.setText(retorno.getCpfPacienteDto());
                    this.cbSexo.setSelectedItem(retorno.getSexoDto());
                    this.txtEstado.setText(retorno.getEstadoDto());
                    this.txtCidade.setText(retorno.getCidadeDto());
                    this.txtBairro.setText(retorno.getBairroDto());
                    this.txtRuaPaciente.setText(retorno.getRuaPacienteDto());
                    this.txtCartaoSUS.setText(retorno.getNumeroCartaoSusDto());

                    /**
                     * Liberar os botões abaixo
                     */
//                btnEditar.setEnabled(true);
                    btnExcluir.setEnabled(true);
                    btnAdicionar.setEnabled(false);
                    btnCancelar.setEnabled(true);
                    btnSalvar.setEnabled(true);
                    /**
                     * Habilitar Campos
                     */
                    txtBuscar.setEnabled(true);
                    cbSexo.setEnabled(true);

                    /**
                     * Também irá habilitar nossos campos para que possamos
                     * digitar os dados no formulario medicos
                     */
                    habilitarCampos();

                    //habilitar botoes complementares 
                    habilitarBotoesComplementaresForTFD();

                    /**
                     * Desabilitar campos especificos
                     */
                    txtCidade.setEnabled(false);
                    txtEstado.setEnabled(false);
                    txtBairro.setEnabled(false);
                }

                if (lblPerfil.getText().equalsIgnoreCase("protocolo")
                        && !retorno.getCpfPacienteDto().isEmpty()
                        && !retorno.getNumeroCartaoSusDto().isEmpty()) {

                    btnExcluir.setEnabled(false);

                    Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setForeground(Color.RED);
                    lblLinhaInformativa.setText("Registro Protegido Principais Dados Preenchidos");

                    /**
                     * SETAR CAMPOS com dados retornados do método buscar por id
                     * no banco
                     */
                    this.txtId.setText(String.valueOf(retorno.getIdPacienteDto()));
                    this.txtIdBairro.setText(String.valueOf(retorno.getFkBairroDto()));
                    this.txtidEstado.setText(String.valueOf(retorno.getFkEstadoDto()));
                    this.txtidCidade.setText(String.valueOf(retorno.getFkCidadeDto()));
                    this.txtPaciente.setText(retorno.getNomePacienteDto());
                    this.txtCPF.setText(retorno.getCpfPacienteDto());
                    this.cbSexo.setSelectedItem(retorno.getSexoDto());
                    this.txtEstado.setText(retorno.getEstadoDto());
                    this.txtCidade.setText(retorno.getCidadeDto());
                    this.txtBairro.setText(retorno.getBairroDto());
                    this.txtRuaPaciente.setText(retorno.getRuaPacienteDto());
                    this.txtCartaoSUS.setText(retorno.getNumeroCartaoSusDto());

                    desabilitarCampos();
                    desabilitarBotoesComplementaresTFD();
                    btnSalvar.setEnabled(false);

                }

                if (!lblPerfil.getText().equalsIgnoreCase("protocolo")) {

                    Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 
                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setForeground(Color.BLUE);
                    lblLinhaInformativa.setText("Acesso LIBERADO para ALTERAÇÃO.");

                    /**
                     * SETAR CAMPOS com dados retornados do método buscar por id
                     * no banco
                     */
                    this.txtId.setText(String.valueOf(retorno.getIdPacienteDto()));
                    this.txtIdBairro.setText(String.valueOf(retorno.getFkBairroDto()));
                    this.txtidEstado.setText(String.valueOf(retorno.getFkEstadoDto()));
                    this.txtidCidade.setText(String.valueOf(retorno.getFkCidadeDto()));
                    this.txtPaciente.setText(retorno.getNomePacienteDto());
                    this.txtCPF.setText(retorno.getCpfPacienteDto());
                    this.cbSexo.setSelectedItem(retorno.getSexoDto());
                    this.txtEstado.setText(retorno.getEstadoDto());
                    this.txtCidade.setText(retorno.getCidadeDto());
                    this.txtBairro.setText(retorno.getBairroDto());
                    this.txtRuaPaciente.setText(retorno.getRuaPacienteDto());
                    this.txtCartaoSUS.setText(retorno.getNumeroCartaoSusDto());

                    /**
                     * Liberar os botões abaixo
                     */
//                btnEditar.setEnabled(true);
                    btnExcluir.setEnabled(true);
                    btnAdicionar.setEnabled(false);
                    btnCancelar.setEnabled(true);
                    btnSalvar.setEnabled(true);
                    /**
                     * Habilitar Campos
                     */
                    txtBuscar.setEnabled(true);
                    cbSexo.setEnabled(true);

                    /**
                     * Também irá habilitar nossos campos para que possamos
                     * digitar os dados no formulario medicos
                     */
                    habilitarCampos();

                    //habilitar botoes complementares 
                    habilitarBotoesComplementaresForTFD();

                    /**
                     * Desabilitar campos especificos
                     */
                    txtCidade.setEnabled(false);
                    txtEstado.setEnabled(false);
                    txtBairro.setEnabled(false);

                }

            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
            //robo conectado ao servidor google 
            erroViaEmail(ex.getMessage(), "acaoNoClickRegistroTabela()\n"
                    + "camada GUI- TelaTFD.java- Erro ao clicar na tabela\n"
                    + "para gerar um evento de setar todos valores nos campos\n"
                    + "onde ocorrera alterações que posterior mente serão \n"
                    + "salvas no banco de dados  ");
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar()\n" + ex.getMessage());
        }
    }

    private void acaoValidaCPFPolindoDados() {

        /**
         * Primeiro criamos uma String com o nome de [CNPJ] e capturamos o valor
         * digitado no campo txtCNPJ por meio do método getText() onde ficará
         * armazenado na variável CNPJ criado para receber o valor capturado
         * pelou usuário.
         */
        pacienteDTO.setCpfPacienteDto(this.txtCPF.getText());

        try {
            //robo conectado ao servidor google 
            boolean retornoVerifcaDuplicidade = pacienteDAO.verificaDuplicidade(pacienteDTO);//verificar se já existe CNPJ

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
                for (int i = 0; i < pacienteDTO.getCpfPacienteDto().length(); i++) {

                    cont += 1;//Aqui o contador começa a ser incrementado em mais um a cada passada 

                    //Quando o contador estiver na posicao 3 execute o codigo abaixo 
                    if (cont == 4) {

                        /**
                         * Se na posição 3 do campo txtCNPJ estiver um ponto
                         * substitua em todos os lugares da String que estiver
                         * com ponto por um estaço em branco
                         */
                        if (pacienteDTO.getCpfPacienteDto().charAt(i) == '.') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             *
                             */
                            pacienteDTO.setCpfPacienteDto(pacienteDTO.getCpfPacienteDto().replace(pacienteDTO.getCpfPacienteDto().charAt(i), ' '));

                        }

                    }

                    //Quando o contador estiver na posicao 5 execute o codigo abaixo 
                    if (cont == 12) {

                        /**
                         * Se na posição 16 do campo txtCNPJ estiver um traço
                         * [-] substitua em todos os lugares da String que
                         * estiver com ponto por um estaço em branco
                         */
                        if (pacienteDTO.getCpfPacienteDto().charAt(i) == '-') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             */
                            pacienteDTO.setCpfPacienteDto(pacienteDTO.getCpfPacienteDto().replace(pacienteDTO.getCpfPacienteDto().charAt(i), ' '));

                        }

                    }

                }
                /**
                 * Neste ponto criamos uma String nova chamada cnpjTratado e
                 * capturamos a string ja tratada e fazemos o último tratamento
                 * que é tirar todos os espaços embrancos da string tratada
                 */
                String cpfTratado = pacienteDTO.getCpfPacienteDto().replace(" ", "");

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
                    //   JOptionPane.showMessageDialog(this, "" + "\n Validado com Sucesso.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    acaoCNPJTrue();
                    txtCPF.setEditable(true);
                    txtCPF.setBackground(Color.WHITE);
                    Font f = new Font("Tahoma", Font.BOLD, 14);
                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setText("Validado com Sucesso.");
                    lblLinhaInformativa.setForeground(Color.BLUE);
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
            //robo conectado servidor google 
            erroViaEmail(ex.getMessage(), "acaoValidaCPFPolindoDados()\n"
                    + "Camada GUI - validando cpf e polindo dados ");
            JOptionPane.showMessageDialog(null, ex.getMessage());
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
        txtBuscar.requestFocus();
        txtBuscar.setBackground(Color.CYAN);
    }

    private class TheHandler implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            if (evt.getSource() == btnAdicionar) {
                acaoBotaoAdicionar();
            }

            if (evt.getSource() == btnCancelar) {
                acaoBotaoCancelar();
            }

            if (evt.getSource() == btnPesquisar) {

                acaoBTNPesquisar();
            }

            if (evt.getSource() == txtBuscar) {
                txtBuscar.setToolTipText("Digite Funcionario a Ser Pesquisado");
                txtBuscar.setBackground(Color.YELLOW);

                //----------------------------------//
                //informe sobre inicio da pesquisa //
                //--------------------------------//
                personalizandoBarraInfoPesquisaInicio();
                btnPesquisar.requestFocus();

            }

        }

    }
}
