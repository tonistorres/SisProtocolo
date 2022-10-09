package br.com.subgerentepro.telas;
//imports padrão das minhas aplicações 

import br.com.subgerentepro.bo.SetorTramiteInternoBO;
import br.com.subgerentepro.dao.SetorTramiteInternoDAO;
import br.com.subgerentepro.dto.SetorTramiteInternoDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.exceptions.ValidacaoException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import java.awt.Color;
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
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class TelaSetorTramiteInterno extends javax.swing.JInternalFrame {

    SetorTramiteInternoDTO setorTramiteInternoDTO = new SetorTramiteInternoDTO();
    SetorTramiteInternoDAO setorTramiteInternoDAO = new SetorTramiteInternoDAO();
    SetorTramiteInternoBO setorTramiteInternoBO = new SetorTramiteInternoBO();

    //https://www.youtube.com/watch?v=-ATbC-4rhc4
    //CRIANDO BOTAO ADICIONAR 
    JButton btnAdicionar = new JButton();

    JButton btnSalvar = new JButton();
    JButton btnExcluir = new JButton();
    JButton btnCancelar = new JButton();
    JButton btnPesquisar = new JButton();
    JButton btnBuscaDep = new JButton();
    JTextField txtBuscar = new JTextField();

    int flag = 0;

    //Chimura: A partir de agora, você usará esse método getInstance() toda vez que quiser criar um ViewEstado
    // Mesma coisa foi feita para ViewBairro
    // Também será necessário fazer para os outros
    private static TelaSetorTramiteInterno instance = null;

    public static TelaSetorTramiteInterno getInstance() {
        if (instance == null) {
            instance = new TelaSetorTramiteInterno();
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

    public TelaSetorTramiteInterno() {
        initComponents();
        componentesInternoJInternal();
        aoCarregarForm();
        addRowJTable();
        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tabela.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 

    }

    private void componentesInternoJInternal() {
        //https://www.youtube.com/watch?v=-ATbC-4rhc4

        TheHandler th = new TheHandler();
        //ADICIONANDO AOS BOTOES AÇOES QUE SERAO TRATADAS PELO ACTIONLISLISTENER
        btnAdicionar.addActionListener(th);

        btnCancelar.addActionListener(th);
        btnExcluir.addActionListener(th);
        btnSalvar.addActionListener(th);
        btnPesquisar.addActionListener(th);
        btnBuscaDep.addActionListener(th);

        AbsoluteLayout layout = new AbsoluteLayout();
        //BOTAO ADICIONAR 
        btnAdicionar.setBounds(265, 1, 45, 45);
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
        PanelBotoesManipulacaoBancoDados.add(this.btnAdicionar);

        //BOTAO SALVAR
        btnSalvar.setBounds(320, 1, 45, 45);
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));
        PanelBotoesManipulacaoBancoDados.add(this.btnSalvar);
        //BOTAO CANCELAR
        btnCancelar.setBounds(380, 1, 45, 45);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        PanelBotoesManipulacaoBancoDados.add(this.btnCancelar);
        //BOTAO EXCLUIR 
        btnExcluir.setBounds(20, 1, 45, 45);
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/excluir.png")));
        PanelBotoesManipulacaoBancoDados.add(this.btnExcluir);

        //BOTAO BUSCAR DEPARTAMENTOS
        btnBuscaDep.setBounds(384, 52, 32, 35);
        btnBuscaDep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        PainelCentral.add(this.btnBuscaDep);

        //BOTAO PESQUISAR 
        btnPesquisar.setBounds(321, 90, 32, 35);
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        PainelCentral.add(this.btnPesquisar);

        //CRIANDO CAMPO PESQUISA JTEXTFIELD
        txtBuscar.setBounds(17, 95, 300, 30);
        PainelCentral.add(txtBuscar);

    }

    private void desabilitarCampos() {
        this.txtId.setEnabled(false);
        this.txtNome.setEnabled(false);

    }

    private void limparCampos() {
        this.txtId.setText("");
        this.txtNome.setText("");

    }

    private void habilitarCampos() {

        this.txtNome.setEnabled(true);

    }

    public void addRowJTable() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            ArrayList<SetorTramiteInternoDTO> list;

            try {

                list = (ArrayList<SetorTramiteInternoDTO>) setorTramiteInternoDAO.listarTodos();

                Object rowData[] = new Object[2];

                for (int i = 0; i < list.size(); i++) {

                    rowData[0] = list.get(i).getIdDto();
                    rowData[1] = list.get(i).getNomeDto();

                    //  System.out.println("ID:" + list.get(i).getIdDto() + "NSUS:" + list.get(i).getNumeroCartaoSusDto() + "Paciente:" + list.get(i).getNomeDto() + "CPF:" + list.get(i).getCpfDto());
                    model.addRow(rowData);
                }

                tabela.setModel(model);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tabela.getColumnModel().getColumn(0).setPreferredWidth(33);
                tabela.getColumnModel().getColumn(1).setPreferredWidth(260);

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
        //campos diversos
        this.txtNome.setEnabled(false);

        //botões no momento do carregamento
        this.btnExcluir.setEnabled(false);
        this.btnAdicionar.setEnabled(true);
        this.btnSalvar.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.btnBuscaDep.setEnabled(false);
        this.btnPesquisar.setEnabled(true);
        this.txtBuscar.setEnabled(true);

    }

    private void acaoBotaoAdicionar() {

        flag = 1;

        /**
         * Como esse campo tem uma validação de cpf que deve ser muito bem
         * validada com vários tratamentos e testes antes de salvar no banco a
         * liberação dos demais campos e açõs que ficariam por conta desse botão
         * ficarãoa cargo do botão btnvalidaCPF
         */
        txtNome.setEnabled(true);

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

        //botoes complementares faz parte do formulario tfd
        //   habilitarBotoesComplementaresForTFD();
        /**
         * Botões que deverão ficar desabilitados nesse evento para esse tipo de
         * Formulario
         */
        txtBuscar.setEnabled(false);

        txtNome.requestFocus();//setar o campo nome Bairro após adicionar
        txtNome.setBackground(Color.CYAN);  // altera a cor do fundo
        txtNome.setEnabled(false);
        btnBuscaDep.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PainelCentral = new javax.swing.JPanel();
        txtId = new javax.swing.JTextField();
        lblId = new javax.swing.JLabel();
        lblPaciente = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        PanelBotoesManipulacaoBancoDados = new javax.swing.JPanel();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PainelCentral.setBackground(java.awt.Color.white);
        PainelCentral.setBorder(javax.swing.BorderFactory.createTitledBorder("Coleta de Dados:"));

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        lblId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblId.setText("ID:");

        lblPaciente.setText("Setores Tramite Inerno Sistema de Protocolo");

        txtNome.setEnabled(false);
        txtNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomeFocusLost(evt);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomeKeyReleased(evt);
            }
        });

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "SETORES TRAMITE INTERNO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
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
            tabela.getColumnModel().getColumn(0).setMinWidth(44);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(44);
            tabela.getColumnModel().getColumn(0).setMaxWidth(44);
        }

        javax.swing.GroupLayout PainelCentralLayout = new javax.swing.GroupLayout(PainelCentral);
        PainelCentral.setLayout(PainelCentralLayout);
        PainelCentralLayout.setHorizontalGroup(
            PainelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelCentralLayout.createSequentialGroup()
                .addGroup(PainelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PainelCentralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(PainelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPaciente)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
            .addGroup(PainelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        PainelCentralLayout.setVerticalGroup(
            PainelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(lblPaciente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(PainelCentral, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 450, 410));

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

        getContentPane().add(PanelBotoesManipulacaoBancoDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void acaoExcluir() {

        try {

            /*Evento ao ser clicado executa código*/
            int excluir = JOptionPane.showConfirmDialog(null, "Deseja excluir registro?", "Informação", JOptionPane.YES_NO_OPTION);

            if (excluir == JOptionPane.YES_OPTION) {

                setorTramiteInternoDTO.setIdDto(Integer.parseInt(txtId.getText()));

                /**
                 * Chamando o método que irá executar a Edição dos Dados em
                 * nosso Banco de Dados
                 */
                setorTramiteInternoDAO.deletar(setorTramiteInternoDTO);

                //ações após exclusão
                comportamentoAposExclusao();

            }
        } catch (Exception e) {
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

    private void acaoBotaoExcluir() {
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
    }

    private void SalvarAdicoesEdicoes() throws PersistenciaException, ValidacaoException {

        /**
         * 1 - Faz o tratamento com a Condicional if 2 - seta o valor do campo
         * tratado
         */
        if (!this.txtId.getText().equals("")) {

            setorTramiteInternoDTO.setIdDto(Integer.parseInt(this.txtId.getText()));

        }

        setorTramiteInternoDTO.setNomeDto(MetodoStaticosUtil.removerAcentosCaixAlta(txtNome.getText()));

        try {

            //TRABALHAR AS VALIDAÇÕES NA HORA DO SALVAMENTOS 
            boolean recebeRetornoDuplicidade = setorTramiteInternoBO.duplicidade(setorTramiteInternoDTO);//verificar se já existe CPF

            boolean recebeRetornoIndexado = setorTramiteInternoBO.validacaoBOdosCamposForm(setorTramiteInternoDTO);

            if ((flag == 1)) {

                //  JOptionPane.showMessageDialog(null, "retorno Paciente:"+recebeRetornoDuplicidade);
                if (recebeRetornoDuplicidade == false) {

                    setorTramiteInternoBO.cadastrarBO(setorTramiteInternoDTO);
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

                }

            } else {

                //tratado com condicional if 
                if (!this.txtId.getText().equals("")) {
                    setorTramiteInternoDTO.setIdDto(Integer.parseInt(txtId.getText()));
                }

                setorTramiteInternoBO.atualizarBO(setorTramiteInternoDTO);
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
            if (e.getMessage().equals("Campo Setor Obrigatorio")) {
                txtNome.requestFocus();
                txtNome.setBackground(Color.YELLOW);

                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            }

            //tratando validação da camada de negocio (BO-Busines Object) 
            if (e.getMessage().equals("Campo Setor aceita no MAX 49 chars")) {
                txtNome.requestFocus();
                txtNome.setBackground(Color.YELLOW);

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
                Logger.getLogger(TelaSetorTramiteInterno.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
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

            acaoPesquisar();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }

    }


    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        acaoAoSelecionarRegistroTabela();
    }//GEN-LAST:event_tabelaMouseClicked

    private void txtNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyReleased


    }//GEN-LAST:event_txtNomeKeyReleased

    private void txtNomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusLost

    }//GEN-LAST:event_txtNomeFocusLost

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed

    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelCentral;
    private javax.swing.JPanel PanelBotoesManipulacaoBancoDados;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblPaciente;
    private javax.swing.JTable tabela;
    public static javax.swing.JTextField txtId;
    public static javax.swing.JTextField txtNome;
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

        ArrayList<SetorTramiteInternoDTO> list;

        try {

            list = (ArrayList<SetorTramiteInternoDTO>) setorTramiteInternoDAO.filtrarUsuarioPesqRapida(pesquisarUsuario);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[3];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(45);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void acaoPesquisar() {

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

            SetorTramiteInternoDTO retorno = setorTramiteInternoDAO.buscarPorIdTblConsultaList(codigo);

            if (retorno.getNomeDto() != null || !retorno.getNomeDto().equals("")) {

                /**
                 * SETAR CAMPOS com dados retornados do método buscar por id no
                 * banco
                 */
                this.txtId.setText(String.valueOf(retorno.getIdDto()));
                this.txtNome.setText(retorno.getNomeDto());

                /**
                 * Liberar os botões abaixo
                 */
//                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnAdicionar.setEnabled(false);
                btnCancelar.setEnabled(true);
                btnSalvar.setEnabled(true);
                btnBuscaDep.setEnabled(false);
                /**
                 * Habilitar Campos
                 */
                txtBuscar.setEnabled(true);

                /**
                 * Também irá habilitar nossos campos para que possamos digitar
                 * os dados no formulario medicos
                 */
                habilitarCampos();
                txtNome.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar()\n" + ex.getMessage());
        }
    }

    br.com.subgerentepro.telas.Frm_Lista_Departamentos formListaDepartamentos;

    private void acaoBotaoBuscarDepartamentos() throws PersistenciaException {

        if (estaFechado(formListaDepartamentos)) {
            formListaDepartamentos = new Frm_Lista_Departamentos();
            DeskTop.add(formListaDepartamentos).setLocation(330, 40);
            formListaDepartamentos.setTitle("Lista Departametnos");
            formListaDepartamentos.show();
        } else {
            formListaDepartamentos.toFront();
            formListaDepartamentos.show();

        }
    }

    private class TheHandler implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            if (evt.getSource() == btnExcluir) {
                acaoBotaoExcluir();
            }
            if (evt.getSource() == btnAdicionar) {
                acaoBotaoAdicionar();
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

            if (evt.getSource() == btnBuscaDep) {
                try {
                    acaoBotaoBuscarDepartamentos();
                } catch (PersistenciaException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Camada GUI:" + ex.getMessage());
                }
            }

        }

    }
}
