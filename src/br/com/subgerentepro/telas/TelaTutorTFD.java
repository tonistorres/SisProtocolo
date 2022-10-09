package br.com.subgerentepro.telas;
//imports padrão das minhas aplicações 

import br.com.subgerentepro.bo.TutorBO;
import br.com.subgerentepro.dao.TutorDAO;
import br.com.subgerentepro.dto.TutorDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.exceptions.ValidacaoException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import static br.com.subgerentepro.telas.TelaPrincipal.lblPerfil;
import br.com.subgerentepro.util.PINTAR_TABELA_TUTOR;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaTutorTFD extends javax.swing.JInternalFrame {

    TutorDTO tutorDTO = new TutorDTO();
    TutorDAO tutorDAO = new TutorDAO();
    TutorBO tutorBO = new TutorBO();
    Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 

    int flag = 0;

    //Chimura: A partir de agora, você usará esse método getInstance() toda vez que quiser criar um ViewEstado
    // Mesma coisa foi feita para ViewBairro
    // Também será necessário fazer para os outros
    private static TelaTutorTFD instance = null;

    public static TelaTutorTFD getInstance() {
        if (instance == null) {
            instance = new TelaTutorTFD();
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

    public TelaTutorTFD() {
        initComponents();
        aoCarregarForm();
        addRowJTable();
        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tabela.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 
//https://www.youtube.com/watch?v=jPfKFm2Yfow
        tabela.setDefaultRenderer(Object.class, new PINTAR_TABELA_TUTOR());

    }

    private void desabilitarCampos() {
        this.txtId.setEnabled(false);
        this.txtTutor.setEnabled(false);
        this.txtCPF.setEnabled(false);
        this.txtidEstadoTutor.setEnabled(false);
        this.txtEstadoTutor.setEnabled(false);
        this.txtidCidadeTutor.setEnabled(false);
        this.txtCidadeTutor.setEnabled(false);
        this.txtIdBairroTutor.setEnabled(false);
        this.txtBairroTutor.setEnabled(false);
        this.txtRua.setEnabled(false);
    }

    private void limparCampos() {
        this.txtId.setText("");
        this.txtTutor.setText("");
        this.txtCPF.setText("");
        this.txtidEstadoTutor.setText("");
        this.txtEstadoTutor.setText("");
        this.txtidCidadeTutor.setText("");
        this.txtCidadeTutor.setText("");
        this.txtIdBairroTutor.setText("");
        this.txtBairroTutor.setText("");
        this.txtRua.setText("");

    }

    private void habilitarCampos() {

        this.txtTutor.setEnabled(true);
        this.txtCPF.setEnabled(true);
        this.txtEstadoTutor.setEnabled(true);
        this.txtCidadeTutor.setEnabled(true);
        this.txtBairroTutor.setEnabled(true);
        this.txtRua.setEnabled(true);
    }

    public void addRowJTable() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            ArrayList<TutorDTO> list;

            try {

                list = (ArrayList<TutorDTO>) tutorDAO.listarTodos();

                Object rowData[] = new Object[4];

                for (int i = 0; i < list.size(); i++) {

                    rowData[0] = list.get(i).getIdDto();
                    rowData[1] = list.get(i).getCpfDto();
                    rowData[2] = list.get(i).getNomeDto();

                    //  System.out.println("ID:" + list.get(i).getIdDto() + "NSUS:" + list.get(i).getNumeroCartaoSusDto() + "Paciente:" + list.get(i).getNomeDto() + "CPF:" + list.get(i).getCpfDto());
                    model.addRow(rowData);
                }

                tabela.setModel(model);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tabela.getColumnModel().getColumn(0).setPreferredWidth(33);
                tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
                tabela.getColumnModel().getColumn(2).setPreferredWidth(260);

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

    public void aoCarregarForm() {

        //campos ids
        this.txtId.setEnabled(false);
        this.txtIdBairroTutor.setEnabled(false);
        this.txtidEstadoTutor.setEnabled(false);
        this.txtidCidadeTutor.setEnabled(false);
        //campos diversos
        this.txtTutor.setEnabled(false);
        this.txtCPF.setEnabled(false);
        this.txtEstadoTutor.setEnabled(false);
        this.txtCidadeTutor.setEnabled(false);
        this.txtBairroTutor.setEnabled(false);
        this.txtRua.setEnabled(false);

        //botões no momento do carregamento
        this.btnExcluir.setEnabled(false);
        this.btnSalvar.setEnabled(false);
        this.btnValidaCPF.setEnabled(false);
        this.btnEstadoBusca.setEnabled(false);
        this.btnCidadeBusca.setEnabled(false);
        this.btnBairroBusca.setEnabled(false);
        this.progressBarraPesquisa.setIndeterminate(true);
        this.progressoBarCPF.setIndeterminate(true);
        this.progressBarEstado.setIndeterminate(true);
        this.progressBarCidade.setIndeterminate(true);
        this.progressBarBairro.setIndeterminate(true);

        //pintar alguns componentes do form em tempo de execução 
        txtBuscar.setBackground(new Color(0, 103, 139));
        txtBuscar.setForeground(Color.WHITE);
        txtBuscar.setEditable(true);
        txtBuscar.setEnabled(true);
    }

    private void desabilitarBotoesComplementaresTFD() {
        this.btnValidaCPF.setEnabled(false);
        this.btnEstadoBusca.setEnabled(false);
        this.btnCidadeBusca.setEnabled(false);
        this.btnBairroBusca.setEnabled(false);

    }

    private void habilitarBotoesComplementaresForTFD() {
        this.btnValidaCPF.setEnabled(true);
        this.btnEstadoBusca.setEnabled(true);
        this.btnCidadeBusca.setEnabled(true);
        this.btnBairroBusca.setEnabled(true);

    }

    private void acaoBotaoAdicionar() {

        flag = 1;

        /**
         * Como esse campo tem uma validação de cpf que deve ser muito bem
         * validada com vários tratamentos e testes antes de salvar no banco a
         * liberação dos demais campos e açõs que ficariam por conta desse botão
         * ficarãoa cargo do botão btnvalidaCPF
         */
        txtTutor.setEnabled(true);
        txtCPF.setEnabled(true);

        txtEstadoTutor.setEnabled(true);
        txtCidadeTutor.setEnabled(true);
        txtBairroTutor.setEnabled(true);
        txtEstadoTutor.setEditable(false);
        txtCidadeTutor.setEditable(false);
        txtBairroTutor.setEditable(false);
        txtRua.setEnabled(true);

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

        txtTutor.requestFocus();//setar o campo nome Bairro após adicionar
        txtTutor.setBackground(Color.CYAN);  // altera a cor do fundo

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelSuperior = new javax.swing.JPanel();
        btnExcluir = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        painelDadosPaciente = new javax.swing.JPanel();
        txtId = new javax.swing.JTextField();
        lblId = new javax.swing.JLabel();
        lblPaciente = new javax.swing.JLabel();
        txtTutor = new javax.swing.JTextField();
        txtidEstadoTutor = new javax.swing.JTextField();
        txtEstadoTutor = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        btnEstadoBusca = new javax.swing.JButton();
        txtidCidadeTutor = new javax.swing.JTextField();
        txtCidadeTutor = new javax.swing.JTextField();
        lblCidade = new javax.swing.JLabel();
        btnCidadeBusca = new javax.swing.JButton();
        txtBairroTutor = new javax.swing.JTextField();
        lblBairro = new javax.swing.JLabel();
        btnBairroBusca = new javax.swing.JButton();
        txtIdBairroTutor = new javax.swing.JTextField();
        lblCPF = new javax.swing.JLabel();
        btnValidaCPF = new javax.swing.JButton();
        txtCPF = new javax.swing.JFormattedTextField();
        txtRua = new javax.swing.JTextField();
        lblRuaDoPaciente = new javax.swing.JLabel();
        lblIdBairro = new javax.swing.JLabel();
        lblIdCidade = new javax.swing.JLabel();
        lblIdEstado = new javax.swing.JLabel();
        progressoBarCPF = new javax.swing.JProgressBar();
        jLabel4 = new javax.swing.JLabel();
        progressBarEstado = new javax.swing.JProgressBar();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        progressBarCidade = new javax.swing.JProgressBar();
        jLabel7 = new javax.swing.JLabel();
        progressBarBairro = new javax.swing.JProgressBar();
        painelPesquisa = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        painelInformativo = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelSuperior.setBackground(java.awt.Color.white);
        painelSuperior.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Botões:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 13))); // NOI18N

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/excluir.png"))); // NOI18N
        btnExcluir.setToolTipText("Excluir Registro");
        btnExcluir.setEnabled(false);
        btnExcluir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnExcluirFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnExcluirFocusLost(evt);
            }
        });
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

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png"))); // NOI18N
        btnAdicionar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAdicionarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnAdicionarFocusLost(evt);
            }
        });
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

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png"))); // NOI18N
        btnSalvar.setToolTipText("Salvar Registro");
        btnSalvar.setEnabled(false);
        btnSalvar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnSalvarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnSalvarFocusLost(evt);
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

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png"))); // NOI18N
        btnCancelar.setToolTipText("Cancelar Registro");
        btnCancelar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnCancelarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnCancelarFocusLost(evt);
            }
        });
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

        javax.swing.GroupLayout painelSuperiorLayout = new javax.swing.GroupLayout(painelSuperior);
        painelSuperior.setLayout(painelSuperiorLayout);
        painelSuperiorLayout.setHorizontalGroup(
            painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelSuperiorLayout.createSequentialGroup()
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painelSuperiorLayout.setVerticalGroup(
            painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelSuperiorLayout.createSequentialGroup()
                .addGroup(painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(painelSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290, -1));

        painelDadosPaciente.setBackground(java.awt.Color.white);
        painelDadosPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Paciente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 13))); // NOI18N
        painelDadosPaciente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        painelDadosPaciente.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 39, 30, 31));

        lblId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblId.setText("ID:");
        painelDadosPaciente.add(lblId, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 18, 36, -1));

        lblPaciente.setText("Tutor:");
        painelDadosPaciente.add(lblPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 18, -1, -1));

        txtTutor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTutorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTutorFocusLost(evt);
            }
        });
        txtTutor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTutorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTutorKeyReleased(evt);
            }
        });
        painelDadosPaciente.add(txtTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 39, 240, 31));
        painelDadosPaciente.add(txtidEstadoTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 30, 31));
        painelDadosPaciente.add(txtEstadoTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 190, 30));

        lblEstado.setText("Estado:");
        painelDadosPaciente.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        btnEstadoBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/busca32x32.png"))); // NOI18N
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
        painelDadosPaciente.add(btnEstadoBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 32, 32));

        txtidCidadeTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidCidadeTutorActionPerformed(evt);
            }
        });
        painelDadosPaciente.add(txtidCidadeTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 30, 30));

        txtCidadeTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCidadeTutorActionPerformed(evt);
            }
        });
        painelDadosPaciente.add(txtCidadeTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 190, 30));

        lblCidade.setText("Cidade:");
        painelDadosPaciente.add(lblCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        btnCidadeBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/busca32x32.png"))); // NOI18N
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
        painelDadosPaciente.add(btnCidadeBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, 32, 30));

        txtBairroTutor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBairroTutorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBairroTutorKeyTyped(evt);
            }
        });
        painelDadosPaciente.add(txtBairroTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 190, 30));

        lblBairro.setText("Bairro:");
        painelDadosPaciente.add(lblBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        btnBairroBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/busca32x32.png"))); // NOI18N
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
        painelDadosPaciente.add(btnBairroBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, 32, 32));
        painelDadosPaciente.add(txtIdBairroTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 30, 31));

        lblCPF.setText("CPF:");
        painelDadosPaciente.add(lblCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        btnValidaCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/botaoValidacao.png"))); // NOI18N
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
        painelDadosPaciente.add(btnValidaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 32, 32));

        try {
            txtCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCPF.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
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
        painelDadosPaciente.add(txtCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 220, 30));

        txtRua.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRuaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRuaFocusLost(evt);
            }
        });
        txtRua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRuaKeyPressed(evt);
            }
        });
        painelDadosPaciente.add(txtRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 270, 32));

        lblRuaDoPaciente.setText("Rua:");
        painelDadosPaciente.add(lblRuaDoPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        lblIdBairro.setText("ID:");
        painelDadosPaciente.add(lblIdBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        lblIdCidade.setText("ID:");
        painelDadosPaciente.add(lblIdCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        lblIdEstado.setText("ID:");
        painelDadosPaciente.add(lblIdEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));
        painelDadosPaciente.add(progressoBarCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 50, 13));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        painelDadosPaciente.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 50, 30));
        painelDadosPaciente.add(progressBarEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 50, 13));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        painelDadosPaciente.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 50, 30));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        painelDadosPaciente.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 50, 30));
        painelDadosPaciente.add(progressBarCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, 50, 13));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        painelDadosPaciente.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 50, 30));
        painelDadosPaciente.add(progressBarBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 50, 13));

        getContentPane().add(painelDadosPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 290, 400));

        painelPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa:"));

        txtBuscar.setEditable(false);
        txtBuscar.setToolTipText("Pesquisar Registro (Contas das Empresas)");
        txtBuscar.setEnabled(false);
        txtBuscar.setPreferredSize(new java.awt.Dimension(100, 20));
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

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CPF", "TUTOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
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
            tabela.getColumnModel().getColumn(0).setResizable(false);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
        }

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N

        javax.swing.GroupLayout painelPesquisaLayout = new javax.swing.GroupLayout(painelPesquisa);
        painelPesquisa.setLayout(painelPesquisaLayout);
        painelPesquisaLayout.setHorizontalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBarraPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
        );
        painelPesquisaLayout.setVerticalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelPesquisaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(progressBarraPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(painelPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 370, 480));

        painelInformativo.setBackground(java.awt.Color.white);

        lblLinhaInformativa.setText("Linha Informativa");

        javax.swing.GroupLayout painelInformativoLayout = new javax.swing.GroupLayout(painelInformativo);
        painelInformativo.setLayout(painelInformativoLayout);
        painelInformativoLayout.setHorizontalGroup(
            painelInformativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLinhaInformativa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
        );
        painelInformativoLayout.setVerticalGroup(
            painelInformativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLinhaInformativa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(painelInformativo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 290, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        acaoAoSelecionarRegistroTabela();
    }//GEN-LAST:event_tabelaMouseClicked

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed

    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Iniciando Pesquisa no Banco de Dados...");
            btnPesquisar.requestFocus();

        }

    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFActionPerformed

    private void txtCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusGained
        txtCPF.setBackground(Color.YELLOW);
    }//GEN-LAST:event_txtCPFFocusGained

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

    private void btnBairroBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBairroBuscaActionPerformed
        buscarBairro();
    }//GEN-LAST:event_btnBairroBuscaActionPerformed

    private void btnBairroBuscaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBairroBuscaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBairroBuscaMouseEntered

    private void btnBairroBuscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBairroBuscaFocusLost
        btnBairroBusca.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnBairroBuscaFocusLost

    private void btnBairroBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBairroBuscaFocusGained
        btnBairroBusca.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnBairroBuscaFocusGained

    private void txtBairroTutorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBairroTutorKeyTyped

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

        if (txtBairroTutor.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute
            JOptionPane.showMessageDialog(null, "Máxmimo 50 caracter");// colocamos uma mensagem alertar usuário
            txtBairroTutor.setBackground(Color.YELLOW);//modificamos a cor do campo para visualmente indicar ao usuário o erro
            //lblLinhaInformativa.setText("Campo Nome: Máximo 50 caracteres");

        }
    }//GEN-LAST:event_txtBairroTutorKeyTyped

    private void txtBairroTutorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBairroTutorKeyReleased
        txtBairroTutor.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtBairroTutor.getText()));
    }//GEN-LAST:event_txtBairroTutorKeyReleased

    private void btnCidadeBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCidadeBuscaActionPerformed

        buscarCidade();
    }//GEN-LAST:event_btnCidadeBuscaActionPerformed

    private void btnCidadeBuscaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCidadeBuscaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCidadeBuscaMouseEntered

    private void btnCidadeBuscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCidadeBuscaFocusLost
        btnCidadeBusca.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnCidadeBuscaFocusLost

    private void btnCidadeBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCidadeBuscaFocusGained
        btnCidadeBusca.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnCidadeBuscaFocusGained

    private void txtCidadeTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCidadeTutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCidadeTutorActionPerformed

    private void btnEstadoBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoBuscaActionPerformed
        buscarEstado();
    }//GEN-LAST:event_btnEstadoBuscaActionPerformed

    private void btnEstadoBuscaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstadoBuscaMouseEntered

    }//GEN-LAST:event_btnEstadoBuscaMouseEntered

    private void btnEstadoBuscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEstadoBuscaFocusLost
        btnEstadoBusca.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnEstadoBuscaFocusLost

    private void btnEstadoBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEstadoBuscaFocusGained
        btnEstadoBusca.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnEstadoBuscaFocusGained

    private void txtTutorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTutorKeyReleased

    }//GEN-LAST:event_txtTutorKeyReleased

    private void txtTutorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTutorKeyPressed
        if (txtTutor.getText().equals("")) {
            if ((evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT)) {
                JOptionPane.showMessageDialog(this, "" + "\n Alerta!\n"
                        + "Campo TUTOR  não pode conter valor nulo. "
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                txtTutor.requestFocus();
                txtTutor.setBackground(Color.red);
            }

        } else {
            if ((evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT)) {

                txtCPF.requestFocus();
                txtTutor.setBackground(Color.red);
            }
        }


    }//GEN-LAST:event_txtTutorKeyPressed

    private void txtTutorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTutorFocusLost
        txtTutor.setBackground(Color.WHITE);

    }//GEN-LAST:event_txtTutorFocusLost

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        acaoBotaoCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
        //   SalvarAdicoesEdicoes();
    }//GEN-LAST:event_btnSalvarKeyPressed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            try {
                SalvarAdicoesEdicoes();
            } catch (PersistenciaException ex) {
                JOptionPane.showMessageDialog(null, "CAMADA GUI:\n" + ex.getMessage());
            } catch (ValidacaoException ex) {
                Logger.getLogger(TelaTutorTFD.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            //    desabilitarCampos();
            //     desabilitaBotoes();
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseExited
        btnSalvar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnSalvarMouseExited

    private void btnSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseEntered
        btnSalvar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnSalvarMouseEntered

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoBotaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnAdicionarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseExited
        btnAdicionar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnAdicionarMouseExited

    private void btnAdicionarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseEntered
        btnAdicionar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnAdicionarMouseEntered

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            acaoExcluir();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            //  desabilitarCampos();
            //  desabilitaBotoes();
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void txtidCidadeTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidCidadeTutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidCidadeTutorActionPerformed

    private void txtTutorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTutorFocusGained

        //JOptionPane.showMessageDialog(null,"Tutor:"+txtTutor.getText());
        if (txtTutor.getText().equalsIgnoreCase("titular")) {
            JOptionPane.showMessageDialog(this, "" + "\n Alerta!:\n"
                    + "Registro Protegido contra \n EXCLUSÃO ou ALTERAÇÃO "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            btnCancelar.requestFocus();
            txtTutor.setEnabled(false);
            txtCPF.setEnabled(false);
            txtRua.setEnabled(false);
            btnSalvar.setEnabled(false);
            btnExcluir.setEnabled(false);
            btnValidaCPF.setEnabled(false);
            btnEstadoBusca.setEnabled(false);
            btnCidadeBusca.setEnabled(false);
            btnBairroBusca.setEnabled(false);

        } else {

            txtTutor.setEnabled(true);
            txtCPF.setEnabled(true);
            txtRua.setEnabled(true);
            btnSalvar.setEnabled(true);
            btnExcluir.setEnabled(true);
            btnValidaCPF.setEnabled(true);
            btnEstadoBusca.setEnabled(true);
            btnCidadeBusca.setEnabled(true);
            btnBairroBusca.setEnabled(true);

        }


    }//GEN-LAST:event_txtTutorFocusGained

    private void btnCancelarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCancelarFocusGained
        btnCancelar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnCancelarFocusGained

    private void btnCancelarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCancelarFocusLost
        btnCancelar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnCancelarFocusLost

    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained
        btnSalvar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnSalvarFocusGained

    private void btnSalvarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusLost
        btnSalvar.setBackground(Color.WHITE);
        lblLinhaInformativa.setText("");
    }//GEN-LAST:event_btnSalvarFocusLost

    private void btnAdicionarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusGained
        btnCancelar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnAdicionarFocusGained

    private void btnAdicionarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusLost
        btnCancelar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnAdicionarFocusLost

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained
        btnCancelar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnExcluirFocusGained

    private void btnExcluirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusLost
        btnCancelar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnExcluirFocusLost

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        btnPesquisar.setBackground(Color.YELLOW);

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
    }//GEN-LAST:event_btnPesquisarFocusGained

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        txtBuscar.setBackground(Color.YELLOW);
        txtBuscar.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        txtBuscar.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFKeyPressed
        if ((evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT)) {

            btnValidaCPF.requestFocus();

        }

    }//GEN-LAST:event_txtCPFKeyPressed

    private void btnValidaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusGained
        btnValidaCPF.setBackground(Color.YELLOW);


    }//GEN-LAST:event_btnValidaCPFFocusGained

    private void btnValidaCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusLost
        btnValidaCPF.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnValidaCPFFocusLost

    private void txtCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusLost

        txtCPF.setBackground(Color.WHITE);

    }//GEN-LAST:event_txtCPFFocusLost

    private void txtRuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRuaKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Salvar Registro");
            btnSalvar.requestFocus();

        }
    }//GEN-LAST:event_txtRuaKeyPressed

    private void txtRuaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRuaFocusGained
        txtRua.setBackground(Color.YELLOW);
    }//GEN-LAST:event_txtRuaFocusGained

    private void txtRuaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRuaFocusLost
        txtRua.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtRuaFocusLost

    private void acaoExcluir() {

        if (txtTutor.getText().equalsIgnoreCase("titular") && lblPerfil.getText().equalsIgnoreCase("PESQUISA TECNOLOGICA DA INFORMACAO")) {

            try {

                /*Evento ao ser clicado executa código*/
                int excluir = JOptionPane.showConfirmDialog(null, "Deseja excluir registro?", "Informação", JOptionPane.YES_NO_OPTION);

                if (excluir == JOptionPane.YES_OPTION) {

                    tutorDTO.setIdDto(Integer.parseInt(txtId.getText()));

                    /**
                     * Chamando o método que irá executar a Edição dos Dados em
                     * nosso Banco de Dados
                     */
                    tutorDAO.deletar(tutorDTO);

                    //ações após exclusão
                    comportamentoAposExclusao();

                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Camada GUI" + e.getMessage());
            }
        }

        if (!txtTutor.getText().equalsIgnoreCase("titular") && lblPerfil.getText().equalsIgnoreCase("SECRETARIA DE SAUDE")) {

            try {

                /*Evento ao ser clicado executa código*/
                int excluir = JOptionPane.showConfirmDialog(null, "Deseja excluir registro?", "Informação", JOptionPane.YES_NO_OPTION);

                if (excluir == JOptionPane.YES_OPTION) {

                    tutorDTO.setIdDto(Integer.parseInt(txtId.getText()));

                    /**
                     * Chamando o método que irá executar a Edição dos Dados em
                     * nosso Banco de Dados
                     */
                    tutorDAO.deletar(tutorDTO);

                    //ações após exclusão
                    comportamentoAposExclusao();

                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Camada GUI" + e.getMessage());
            }
        }

        if (txtTutor.getText().equalsIgnoreCase("titular") && lblPerfil.getText().equalsIgnoreCase("SECRETARIA DE SAUDE")) {
            JOptionPane.showMessageDialog(null, "" + "\n Alerta!:\n"
                    + "Dado protegido contra exclusão  \n"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
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

    private void SalvarAdicoesEdicoes() throws PersistenciaException, ValidacaoException {

        /**
         * 1 - Faz o tratamento com a Condicional if 2 - seta o valor do campo
         * tratado
         */
        if (!this.txtidEstadoTutor.getText().equals("")
                && !this.txtidCidadeTutor.getText().equals("")
                && !this.txtIdBairroTutor.getText().equals("")) {

            tutorDTO.setFkEstadoDto(Integer.parseInt(this.txtidEstadoTutor.getText()));
            tutorDTO.setFkCidadeDto(Integer.parseInt(this.txtidCidadeTutor.getText()));
            tutorDTO.setFkBairroDto(Integer.parseInt(this.txtIdBairroTutor.getText()));
        }

        tutorDTO.setNomeDto(txtTutor.getText());
        tutorDTO.setEstadoDto(txtEstadoTutor.getText());
        tutorDTO.setCidadeDto(txtCidadeTutor.getText());
        tutorDTO.setBairroDto(txtBairroTutor.getText());
        tutorDTO.setCpfDto(txtCPF.getText());
        tutorDTO.setRuaDto(txtRua.getText());

        try {

            //TRABALHAR AS VALIDAÇÕES NA HORA DO SALVAMENTOS 
            boolean recebeRetornoDuplicidade = tutorBO.duplicidade(tutorDTO);//verificar se já existe CPF
            boolean recebeRetornoDuplicidadeTitular = tutorDAO.verificaSeTitularCadastrado(tutorDTO);
            boolean recebeRetornoIndexado = tutorBO.validacaoBOdosCamposForm(tutorDTO);

            if ((flag == 1)) {

                //  JOptionPane.showMessageDialog(null, "retorno Paciente:"+recebeRetornoDuplicidade);
                if (recebeRetornoDuplicidade == false && recebeRetornoDuplicidadeTitular == false) {

                    tutorBO.cadastrarBO(tutorDTO);
                    /**
                     * Após salvar limpar os campos
                     */
                    limparCampos();
                    /**
                     * Bloquear campos e Botões
                     */
                    desabilitarCampos();
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

                    JOptionPane.showMessageDialog(this, "" + "\n Cadastrado com Sucesso.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

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

                    JOptionPane.showMessageDialog(this, "" + "\n CAMADA GUI : Resgistro já cadastrado.\nSistema Impossibilitou \n a Duplicidade", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    txtCPF.requestFocus();
                    txtCPF.setBackground(Color.RED);

                }

            } else {

                //tratado com condicional if 
                if (!this.txtId.getText().equals("")) {
                    tutorDTO.setIdDto(Integer.parseInt(txtId.getText()));
                }

                tutorBO.atualizarBO(tutorDTO);
                /**
                 * Após salvar limpar os campos
                 */
                limparCampos();
                /**
                 * Bloquear campos e Botões
                 */
                desabilitarCampos();
                /**
                 * Liberar campos necessário para operações após salvamento
                 */
                btnAdicionar.setEnabled(true);
                btnCancelar.setEnabled(false);
                btnSalvar.setEnabled(false);
                //  holders();
                JOptionPane.showMessageDialog(this, "" + "\n Edição Salva com Sucesso ", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

                int numeroLinhas = tabela.getRowCount();

                if (numeroLinhas > 0) {

                    while (tabela.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tabela.getModel()).removeRow(0);

                    }

                    addRowJTable();

                } else {
                    addRowJTable();

                }

            }//fecha blco else 
        } catch (Exception e) {

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo paciente Obrigatorio")) {
                txtTutor.requestFocus();
                txtTutor.setBackground(Color.YELLOW);

                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo paciente aceita no MAX 49 chars")) {
                txtTutor.requestFocus();
                txtTutor.setBackground(Color.YELLOW);

            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo estado Obrigatorio")) {
                txtEstadoTutor.requestFocus();
                txtEstadoTutor.setBackground(Color.YELLOW);
            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo estado aceita no MAX 40 chars")) {

                txtEstadoTutor.requestFocus();
                txtEstadoTutor.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }
            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo cidade Obrigatorio")) {
                txtCidadeTutor.requestFocus();
                txtCidadeTutor.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (BO-Busines Object) [Endereco] obrigatorio
            if (e.getMessage().equals("Campo cidade aceita no MAX 49 chars")) {
                txtCidadeTutor.requestFocus();
                txtCidadeTutor.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }
            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo bairro Obrigatorio")) {
                txtBairroTutor.requestFocus();
                txtBairroTutor.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo bairro aceita no MAX 49 chars")) {
                txtBairroTutor.requestFocus();
                txtBairroTutor.setBackground(Color.YELLOW);
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

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo rua Obrigatorio")) {
                txtRua.requestFocus();
                txtRua.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo rua aceita no MAX 49 chars")) {
                txtRua.requestFocus();
                txtRua.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

        }
    }

    br.com.subgerentepro.telas.FrmListaEstadosTutor formListaEstadoTutor;

    private void buscarEstado() {

        if (estaFechado(formListaEstadoTutor)) {
            formListaEstadoTutor = new FrmListaEstadosTutor();
            DeskTop.add(formListaEstadoTutor).setLocation(580, 5);
            formListaEstadoTutor.setTitle("Lista Estados");

//clicou nessa opção cinco campos automaticamente dever ser limpos 
            txtidCidadeTutor.setText("");
            txtCidadeTutor.setText("");
            txtIdBairroTutor.setText("");
            txtBairroTutor.setText("");
            txtRua.setText("");

            formListaEstadoTutor.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formListaEstadoTutor.toFront();
            formListaEstadoTutor.show();

        }

    }

    br.com.subgerentepro.telas.FrmListaCidadesTutor formListaCidadesTutor;

    private void buscarCidade() {

        if (estaFechado(formListaCidadesTutor)) {
            formListaCidadesTutor = new FrmListaCidadesTutor();
            DeskTop.add(formListaCidadesTutor).setLocation(480, 5);
            formListaCidadesTutor.setTitle("Lista Estados Cadastradas");
            //neste campo 3(tres) campos devem ser limpos automaticamente 
            txtIdBairroTutor.setText("");
            txtBairroTutor.setText("");
            txtRua.setText("");

            formListaCidadesTutor.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            txtBairroTutor.setEditable(true);
            formListaCidadesTutor.toFront();
            formListaCidadesTutor.show();

        }

    }

    br.com.subgerentepro.telas.FrmListaBairrosTutor formListaBairros;

    private void buscarBairro() {

        if (estaFechado(formListaBairros)) {
            formListaBairros = new FrmListaBairrosTutor();
            DeskTop.add(formListaBairros).setLocation(460, 5);
            formListaBairros.setTitle("Lista Bairros Cadastrados");
            formListaBairros.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formListaBairros.toFront();
            formListaBairros.show();

        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    public static javax.swing.JButton btnBairroBusca;
    private javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnCidadeBusca;
    public static javax.swing.JButton btnEstadoBusca;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnValidaCPF;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblIdBairro;
    private javax.swing.JLabel lblIdCidade;
    private javax.swing.JLabel lblIdEstado;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblPaciente;
    private javax.swing.JLabel lblRuaDoPaciente;
    private javax.swing.JPanel painelDadosPaciente;
    private javax.swing.JPanel painelInformativo;
    private javax.swing.JPanel painelPesquisa;
    private javax.swing.JPanel painelSuperior;
    private javax.swing.JProgressBar progressBarBairro;
    private javax.swing.JProgressBar progressBarCidade;
    private javax.swing.JProgressBar progressBarEstado;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JProgressBar progressoBarCPF;
    private javax.swing.JTable tabela;
    public static javax.swing.JTextField txtBairroTutor;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JFormattedTextField txtCPF;
    public static javax.swing.JTextField txtCidadeTutor;
    public static javax.swing.JTextField txtEstadoTutor;
    private javax.swing.JTextField txtId;
    public static javax.swing.JTextField txtIdBairroTutor;
    private javax.swing.JTextField txtRua;
    private javax.swing.JTextField txtTutor;
    public static javax.swing.JTextField txtidCidadeTutor;
    public static javax.swing.JTextField txtidEstadoTutor;
    // End of variables declaration//GEN-END:variables

    private void acaoBotaoCancelar() {
        /**
         * Após salvar limpar os campos
         */
        limparCampos();

        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        desabilitarCampos();

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
        if (txtTutor.equals("")) {

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
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    private void pesquisarUsuario() {
        String pesquisarUsuario = MetodoStaticosUtil.removerAcentosCaixAlta(txtBuscar.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<TutorDTO> list;

        try {

            list = (ArrayList<TutorDTO>) tutorDAO.filtrarUsuarioPesqRapida(pesquisarUsuario);

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
                rowData[2] = list.get(i).getCpfDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(45);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(260);

            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);

            lblLinhaInformativa.setText("Pesquisa Terminada com Sucesso.");
            lblLinhaInformativa.setForeground(Color.ORANGE);
            txtBuscar.requestFocus();

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void acaoBotaoPesquisar() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            int numeroLinhas = tabela.getRowCount();

            if (numeroLinhas > 0) {

                while (tabela.getModel().getRowCount() > 0) {
                    ((DefaultTableModel) tabela.getModel()).removeRow(0);

                }

                pesquisarUsuario();

            } else {
                addRowJTable();
            }

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }

    }

    private void acaoNoClickRegistroTabela() {
        limparCampos();

        flag = 2;
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

            TutorDTO retorno = tutorDAO.buscarPorIdTblConsultaList(codigo);

            if (retorno.getNomeDto() != null || !retorno.getNomeDto().equals("")) {

                /**
                 * SETAR CAMPOS com dados retornados do método buscar por id no
                 * banco
                 */
                this.txtId.setText(String.valueOf(retorno.getIdDto()));
                this.txtIdBairroTutor.setText(String.valueOf(retorno.getFkBairroDto()));
                this.txtidEstadoTutor.setText(String.valueOf(retorno.getFkEstadoDto()));
                this.txtidCidadeTutor.setText(String.valueOf(retorno.getFkCidadeDto()));
                this.txtTutor.setText(retorno.getNomeDto());
                this.txtCPF.setText(retorno.getCpfDto());
                this.txtEstadoTutor.setText(retorno.getEstadoDto());
                this.txtCidadeTutor.setText(retorno.getCidadeDto());
                this.txtBairroTutor.setText(retorno.getBairroDto());
                this.txtRua.setText(retorno.getRuaDto());

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

                /**
                 * Também irá habilitar nossos campos para que possamos digitar
                 * os dados no formulario medicos
                 */
                habilitarCampos();

                //habilitar botoes complementares 
                habilitarBotoesComplementaresForTFD();

                /**
                 * Desabilitar campos especificos
                 */
                txtCidadeTutor.setEnabled(false);
                txtEstadoTutor.setEnabled(false);
                txtBairroTutor.setEnabled(false);

                txtTutor.requestFocus();

            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
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
        tutorDTO.setCpfDto(this.txtCPF.getText());

        try {
            boolean retornoVerifcaDuplicidade = tutorDAO.verificaDuplicidade(tutorDTO);//verificar se já existe CNPJ

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
                for (int i = 0; i < tutorDTO.getCpfDto().length(); i++) {

                    cont += 1;//Aqui o contador começa a ser incrementado em mais um a cada passada 

                    //Quando o contador estiver na posicao 3 execute o codigo abaixo 
                    if (cont == 4) {

                        /**
                         * Se na posição 3 do campo txtCNPJ estiver um ponto
                         * substitua em todos os lugares da String que estiver
                         * com ponto por um estaço em branco
                         */
                        if (tutorDTO.getCpfDto().charAt(i) == '.') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             *
                             */
                            tutorDTO.setCpfDto(tutorDTO.getCpfDto().replace(tutorDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                    //Quando o contador estiver na posicao 5 execute o codigo abaixo 
                    if (cont == 12) {

                        /**
                         * Se na posição 16 do campo txtCNPJ estiver um traço
                         * [-] substitua em todos os lugares da String que
                         * estiver com ponto por um estaço em branco
                         */
                        if (tutorDTO.getCpfDto().charAt(i) == '-') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             */
                            tutorDTO.setCpfDto(tutorDTO.getCpfDto().replace(tutorDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                }
                /**
                 * Neste ponto criamos uma String nova chamada cnpjTratado e
                 * capturamos a string ja tratada e fazemos o último tratamento
                 * que é tirar todos os espaços embrancos da string tratada
                 */
                String cpfTratado = tutorDTO.getCpfDto().replace(" ", "");

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

    private void acaoCNPJTrue() {
        habilitarBotoesComplementaresForTFD();
        buscarEstado();
        txtBairroTutor.setEditable(false);
        txtRua.setEnabled(true);
    }
}
