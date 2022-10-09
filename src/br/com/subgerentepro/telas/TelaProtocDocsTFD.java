package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.FluxoTFDBO;
import br.com.subgerentepro.bo.ProtocoloTFDBO;
import br.com.subgerentepro.dao.GeraCodigoCustomizadoDAO;
import br.com.subgerentepro.dao.ItemProtocoloDAO;
import br.com.subgerentepro.dao.ItensDoProtocoloTFDDAO;
import br.com.subgerentepro.dao.ItensFluxoMovimentoTFDDAO;
import br.com.subgerentepro.dao.PacienteDAO;
import br.com.subgerentepro.dto.FluxoTFDDTO;
import br.com.subgerentepro.dto.ItemProtocoloDTO;
import br.com.subgerentepro.dto.ItensDoProtocoloTFDDTO;
import br.com.subgerentepro.dto.ItensFluxoMovimentoTFDDTO;
import br.com.subgerentepro.dto.PacienteDTO;
import br.com.subgerentepro.dto.ProtocoloTFDDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import static br.com.subgerentepro.telas.TelaPrincipal.lblNomeCompletoUsuario;
import static br.com.subgerentepro.telas.TelaPrincipal.lblPerfil;
import static br.com.subgerentepro.telas.TelaPrincipal.lblRepositorioCPU;
import static br.com.subgerentepro.telas.TelaPrincipal.lblRepositorioHD;
import static br.com.subgerentepro.telas.TelaPrincipal.lblUsuarioLogado;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Dã Torres
 */
public class TelaProtocDocsTFD extends javax.swing.JInternalFrame {

    GeraCodigoCustomizadoDAO geraCodigoCustomizadoDAO = new GeraCodigoCustomizadoDAO();

    PacienteDAO pacienteDAO = new PacienteDAO();
    PacienteDTO pacienteDTO = new PacienteDTO();
    ItemProtocoloDTO itemProtocoloDTO = new ItemProtocoloDTO();
    ItemProtocoloDAO itemProtocoloDAO = new ItemProtocoloDAO();
    ProtocoloTFDDTO protocoloTFDDTO = new ProtocoloTFDDTO();
    ProtocoloTFDBO protocoloTFDBO = new ProtocoloTFDBO();
    FluxoTFDBO fluxoTFDBO = new FluxoTFDBO();
    FluxoTFDDTO fluxoTFDDTO = new FluxoTFDDTO();
    ItensFluxoMovimentoTFDDTO itensFluxoTFDDTO = new ItensFluxoMovimentoTFDDTO();
    ItensFluxoMovimentoTFDDAO itensFluxoTFDDAO = new ItensFluxoMovimentoTFDDAO();

    ArrayList<ItensDoProtocoloTFDDTO> listaItensDoProtocolo = new ArrayList<>();
    int flag = 0;

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

    public TelaProtocDocsTFD() {
        initComponents();
        aoCarregarForm();
        gerarCodigoGUIeDataSistema();
        buscarPersonalizarLoginMaquina();

        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tabela.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 
        //********************************************************************************************
        //CANAL:MamaNs - Java Swing UI - Design jTable       
        //clicar e a tabela mudar a linha de cor indicando que a linha cliccada é aquela 
        //https://www.youtube.com/watch?v=RXhMdUPk12k
        //*******************************************************************************************
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 9));
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(new Color(32, 136, 203));
        tabela.getTableHeader().setForeground(new Color(255, 255, 255));
        tabela.setRowHeight(25);

        //*************************************************************************
    }

    private void aoCarregarForm() {
        this.txtCodItemProtocolo.setEnabled(false);
        this.txtIdCodCustomizado.setEnabled(false);
        this.txtDtProtocolo.setEnabled(false);
        this.txtDepartamentoOrigem.setEnabled(false);
        this.txtInteressado.setEnabled(false);
        this.txtEnderecado.setEnabled(false);
        this.txtInteressadoSetorInterno.setEnabled(false);
        this.cbRelevancia.setEnabled(false);
        this.cbRelevancia.setSelectedItem(null);
        this.cbComoSeraViagem.setEnabled(true);
        this.txtDataTranferencia.setEnabled(false);
        this.txtItemDoProtocolo.setEnabled(false);
        this.txtQuantidade.setEnabled(false);
        this.txtAreaDescreve.setEnabled(false);
        this.txtCPF.setEnabled(false);

        //botao comportamento no carregamento
        this.btnValidaDataTransferencia.setEnabled(false);
        this.btnSalvar.setEnabled(false);
        this.btnAdicionar.setEnabled(true);
//        this.btnCancelar.setEnabled(false);
        this.btnValidaCPF.setEnabled(false);
        this.btnItensDoProtocolo.setEnabled(false);
        this.btnAdicionarItem.setEnabled(false);
        this.barProgressProcedimentosBackEnd.setIndeterminate(true);
        frontEnd();
    }

    private void buscarPersonalizarLoginMaquina() {

        painelDadosUsuarioMaquina.setBackground(new Color(9, 81, 107));

        Font f = new Font("Tahoma", Font.BOLD, 9);

        lblLogin.setFont(f);
        lblLogin.setForeground(Color.WHITE);

        lblUsuario.setFont(f);
        lblUsuario.setForeground(Color.WHITE);

        lblHD2.setFont(f);
        lblHD2.setForeground(Color.WHITE);

        lblCPU.setFont(f);
        lblCPU.setForeground(Color.WHITE);

        lblRepoCPU.setFont(f);
        lblRepoCPU.setForeground(Color.WHITE);
        lblRepoCPU.setText("");
        lblRepoCPU.setText(lblRepositorioCPU.getText());

        lblRepoHD.setFont(f);
        lblRepoHD.setForeground(Color.WHITE);
        lblRepoHD.setText("");
        lblRepoHD.setText(lblRepositorioHD.getText());

        lblRepoLogin.setFont(f);
        lblRepoLogin.setForeground(Color.WHITE);
        lblRepoLogin.setText("");
        lblRepoLogin.setText(lblUsuarioLogado.getText());

        lblPerfil2.setFont(f);
        lblPerfil2.setForeground(Color.WHITE);
        lblPerfil.setFont(f);
        lblPerfil.setForeground(Color.WHITE);

        lblRepoPerfil.setText("");
        lblRepoPerfil.setText(lblPerfil.getText());

        lblRepoUsuario.setFont(f);
        lblRepoUsuario.setForeground(Color.WHITE);
        lblRepoUsuario.setText("");
        lblRepoUsuario.setText(lblNomeCompletoUsuario.getText());

    }

    private void frontEnd() {
        Font fCPF = new Font("Tahoma", Font.BOLD, 12);
        txtCPF.setBackground(new Color(9, 81, 107));
        txtCPF.setForeground(Color.WHITE);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(new Color(9, 81, 107));

        lblLinhaInformativa.setFont(fCPF);

        //Painel Paciente 
        //cor de fundo painel paciente 
        painelInfoPaciente.setBackground(new Color(9, 81, 107));

        Font fPaciente = new Font("Tahoma", Font.BOLD, 9);

        lblPacientel.setFont(fPaciente);
        lblPacientel.setForeground(Color.WHITE);

        lblSexo.setFont(fPaciente);
        lblSexo.setForeground(Color.WHITE);

        lblCartaoSUS.setFont(fPaciente);
        lblCartaoSUS.setForeground(Color.WHITE);

        lblRua.setFont(fPaciente);
        lblRua.setForeground(Color.WHITE);

        lblRepoPaciente.setFont(fPaciente);
        lblRepoPaciente.setForeground(Color.WHITE);
        lblRepoPaciente.setText("");

        lblRepoSexo.setFont(fPaciente);
        lblRepoSexo.setForeground(Color.WHITE);
        lblRepoSexo.setText("");

        lblRepoRua.setFont(fPaciente);
        lblRepoRua.setForeground(Color.WHITE);
        lblRepoRua.setText("");

        lblRepoNCartaoSUS.setFont(fPaciente);
        lblRepoNCartaoSUS.setForeground(Color.WHITE);
        lblRepoNCartaoSUS.setText("");

        Font fCbComoSera = new Font("Tahoma", Font.BOLD, 8);
        cbComoSeraViagem.setFont(fCbComoSera);

    }

    private void gerarCodigoGUIeDataSistema() {

        /**
         * Setar uma Data pega no sistema e setar num JDateChooser
         * https://www.youtube.com/watch?v=GlBMniOwCnI
         */
        Date sistemaData = new Date();//import java.util.Date;
        SimpleDateFormat formatador = new SimpleDateFormat("y");
        SimpleDateFormat formatadorDataCompleta = new SimpleDateFormat("dd/MM/yyy");
        String dataCompleta = formatadorDataCompleta.format(sistemaData);
        Calendar now = Calendar.getInstance();
        String dataFormatada = formatador.format(sistemaData);
        txtDtProtocolo.setDate(sistemaData);

        int numeroMax = geraCodigoCustomizadoDAO.gerarCodigoTFD();
        int resultado = 0;
        // JOptionPane.showMessageDialog(null, "Numero"+ numeroDaVenda);
        resultado = numeroMax + 1;

        String codigoCustomizado = String.valueOf(resultado).concat("-TFD/").concat(dataFormatada);

        txtIdCodCustomizado.setText(String.valueOf(codigoCustomizado));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        txtIdCodCustomizado = new javax.swing.JTextField();
        lblIdCustomizado = new javax.swing.JLabel();
        lblDataProtocolo = new javax.swing.JLabel();
        txtCPF = new javax.swing.JFormattedTextField();
        lblCPFPaciente = new javax.swing.JLabel();
        painelInfoPaciente = new javax.swing.JPanel();
        lblPacientel = new javax.swing.JLabel();
        lblRepoPaciente = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        lblRepoSexo = new javax.swing.JLabel();
        lblRua = new javax.swing.JLabel();
        lblRepoRua = new javax.swing.JLabel();
        lblCartaoSUS = new javax.swing.JLabel();
        lblRepoNCartaoSUS = new javax.swing.JLabel();
        painelDadosUsuarioMaquina = new javax.swing.JPanel();
        lblLogin = new javax.swing.JLabel();
        lblRepoLogin = new javax.swing.JLabel();
        lblPerfil2 = new javax.swing.JLabel();
        lblRepoPerfil = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblRepoUsuario = new javax.swing.JLabel();
        lblHD2 = new javax.swing.JLabel();
        lblRepoHD = new javax.swing.JLabel();
        lblCPU = new javax.swing.JLabel();
        lblRepoCPU = new javax.swing.JLabel();
        txtDepartamentoOrigem = new javax.swing.JTextField();
        lblDepOrigem = new javax.swing.JLabel();
        txtInteressado = new javax.swing.JTextField();
        lblInteressado = new javax.swing.JLabel();
        txtEnderecado = new javax.swing.JTextField();
        lblEnderecado = new javax.swing.JLabel();
        txtInteressadoSetorInterno = new javax.swing.JTextField();
        lblInteressadoFuncionario = new javax.swing.JLabel();
        lblGrauRelevancia = new javax.swing.JLabel();
        btnValidaCPF = new javax.swing.JButton();
        lblLinhaInformativa = new javax.swing.JLabel();
        painelLancamento = new javax.swing.JPanel();
        txtItemDoProtocolo = new javax.swing.JTextField();
        btnItensDoProtocolo = new javax.swing.JButton();
        txtQuantidade = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaDescreve = new javax.swing.JTextArea();
        btnAdicionarItem = new javax.swing.JButton();
        txtCodItemProtocolo = new javax.swing.JTextField();
        lblID = new javax.swing.JLabel();
        lblInfoDataVenda = new javax.swing.JLabel();
        txtDataTranferencia = new com.toedter.calendar.JDateChooser();
        cbRelevancia = new javax.swing.JComboBox();
        btnValidaDataTransferencia = new javax.swing.JButton();
        txtDtProtocolo = new com.toedter.calendar.JDateChooser();
        cbComoSeraViagem = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        barProgressProcedimentosBackEnd = new javax.swing.JProgressBar();
        lblInfoProcessosBackEnd = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblValorDiaria = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JToggleButton();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelPrincipal.setBackground(java.awt.Color.white);
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabela.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ITENS DO PROTOCOLO", "QTDE", "DESCREVER", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setFocusable(false);
        tabela.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabela.setRowHeight(12);
        tabela.setSelectionBackground(new java.awt.Color(32, 136, 203));
        tabela.setShowVerticalLines(false);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(200);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(0).setMaxWidth(200);
            tabela.getColumnModel().getColumn(1).setMinWidth(70);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(1).setMaxWidth(70);
            tabela.getColumnModel().getColumn(2).setMinWidth(400);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(400);
            tabela.getColumnModel().getColumn(2).setMaxWidth(400);
        }

        painelPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 740, 110));
        painelPrincipal.add(txtIdCodCustomizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 90, 22));

        lblIdCustomizado.setText("ID Custom:");
        painelPrincipal.add(lblIdCustomizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 80, -1));

        lblDataProtocolo.setText("Data Protocolo:");
        painelPrincipal.add(lblDataProtocolo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, -1, -1));

        try {
            txtCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
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
        painelPrincipal.add(txtCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 100, 24));

        lblCPFPaciente.setText("CPF Paciente:");
        painelPrincipal.add(lblCPFPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 80, -1));

        painelInfoPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados Paciente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), java.awt.Color.white)); // NOI18N
        painelInfoPaciente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPacientel.setText("Paciente:");
        painelInfoPaciente.add(lblPacientel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        lblRepoPaciente.setText("____________________________________");
        painelInfoPaciente.add(lblRepoPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 250, -1));

        lblSexo.setText("Sexo:");
        painelInfoPaciente.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        lblRepoSexo.setText("________");
        painelInfoPaciente.add(lblRepoSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 80, -1));

        lblRua.setText("Rua:");
        painelInfoPaciente.add(lblRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

        lblRepoRua.setText("________________________");
        painelInfoPaciente.add(lblRepoRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 170, -1));

        lblCartaoSUS.setText("Número Cartão SUS:");
        painelInfoPaciente.add(lblCartaoSUS, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        lblRepoNCartaoSUS.setText("__________________________");
        painelInfoPaciente.add(lblRepoNCartaoSUS, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 190, -1));

        painelPrincipal.add(painelInfoPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 340, 90));

        painelDadosUsuarioMaquina.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados Usuário / Máquina:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), java.awt.Color.white)); // NOI18N
        painelDadosUsuarioMaquina.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogin.setText("Login:");
        painelDadosUsuarioMaquina.add(lblLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 40, -1));

        lblRepoLogin.setText("____________________");
        painelDadosUsuarioMaquina.add(lblRepoLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 130, -1));

        lblPerfil2.setText("Perfil:");
        painelDadosUsuarioMaquina.add(lblPerfil2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        lblRepoPerfil.setForeground(java.awt.Color.white);
        lblRepoPerfil.setText("____________________");
        painelDadosUsuarioMaquina.add(lblRepoPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 34, 130, 20));

        lblUsuario.setText("Usuario:");
        painelDadosUsuarioMaquina.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lblRepoUsuario.setText("__________________");
        painelDadosUsuarioMaquina.add(lblRepoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 140, -1));

        lblHD2.setText("HD:");
        painelDadosUsuarioMaquina.add(lblHD2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, -1, -1));

        lblRepoHD.setText("___________________");
        painelDadosUsuarioMaquina.add(lblRepoHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 140, -1));

        lblCPU.setText("CPU:");
        painelDadosUsuarioMaquina.add(lblCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, -1));

        lblRepoCPU.setText("___________________");
        painelDadosUsuarioMaquina.add(lblRepoCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 140, -1));

        painelPrincipal.add(painelDadosUsuarioMaquina, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 390, 90));

        txtDepartamentoOrigem.setPreferredSize(new java.awt.Dimension(6, 24));
        txtDepartamentoOrigem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDepartamentoOrigemActionPerformed(evt);
            }
        });
        painelPrincipal.add(txtDepartamentoOrigem, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 150, -1));

        lblDepOrigem.setText("Departamento de Origem:");
        painelPrincipal.add(lblDepOrigem, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, -1, -1));

        txtInteressado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInteressadoActionPerformed(evt);
            }
        });
        painelPrincipal.add(txtInteressado, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 160, 200, 24));

        lblInteressado.setText("Interessado:");
        painelPrincipal.add(lblInteressado, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, -1, -1));
        painelPrincipal.add(txtEnderecado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 120, 24));

        lblEnderecado.setText("Setor Interno:");
        painelPrincipal.add(lblEnderecado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        txtInteressadoSetorInterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInteressadoSetorInternoActionPerformed(evt);
            }
        });
        painelPrincipal.add(txtInteressadoSetorInterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 160, 24));

        lblInteressadoFuncionario.setText("Endereçado:");
        painelPrincipal.add(lblInteressadoFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, -1, 20));

        lblGrauRelevancia.setText("Relevância:");
        painelPrincipal.add(lblGrauRelevancia, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, -1, -1));

        btnValidaCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/validation.png"))); // NOI18N
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
        painelPrincipal.add(btnValidaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, 24, 24));

        lblLinhaInformativa.setText("Linha Informativa");
        painelPrincipal.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 610, 40));

        painelLancamento.setOpaque(false);
        painelLancamento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtItemDoProtocolo.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        txtItemDoProtocolo.setPreferredSize(new java.awt.Dimension(59, 22));
        painelLancamento.add(txtItemDoProtocolo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 240, 30));

        btnItensDoProtocolo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/search.png"))); // NOI18N
        btnItensDoProtocolo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnItensDoProtocoloFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnItensDoProtocoloFocusLost(evt);
            }
        });
        btnItensDoProtocolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItensDoProtocoloActionPerformed(evt);
            }
        });
        painelLancamento.add(btnItensDoProtocolo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 32, 32));

        txtQuantidade.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtQuantidade.setForeground(java.awt.Color.blue);
        txtQuantidade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelLancamento.add(txtQuantidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 30, 30));

        jLabel5.setText("Item do Protocolo");
        painelLancamento.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, -1));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("QTDE.");
        painelLancamento.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, -1, -1));

        txtAreaDescreve.setColumns(20);
        txtAreaDescreve.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        txtAreaDescreve.setRows(5);
        txtAreaDescreve.setOpaque(false);
        txtAreaDescreve.setPreferredSize(new java.awt.Dimension(224, 85));
        jScrollPane2.setViewportView(txtAreaDescreve);

        painelLancamento.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 310, 50));

        btnAdicionarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/AdicionarRegistro Rolover.png"))); // NOI18N
        btnAdicionarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarItemActionPerformed(evt);
            }
        });
        painelLancamento.add(btnAdicionarItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, 32, 32));

        txtCodItemProtocolo.setEnabled(false);
        painelLancamento.add(txtCodItemProtocolo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 30, 30));

        lblID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblID.setText("ID");
        painelLancamento.add(lblID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 30, -1));

        painelPrincipal.add(painelLancamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 740, 60));

        lblInfoDataVenda.setText("Data Transf R$ :");
        painelPrincipal.add(lblInfoDataVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 190, 120, -1));

        txtDataTranferencia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataTranferenciaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataTranferenciaFocusLost(evt);
            }
        });
        painelPrincipal.add(txtDataTranferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 210, 130, 24));

        cbRelevancia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "URGENTE", "MODERADO" }));
        cbRelevancia.setEnabled(false);
        cbRelevancia.setOpaque(false);
        cbRelevancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRelevanciaActionPerformed(evt);
            }
        });
        painelPrincipal.add(cbRelevancia, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 100, 24));

        btnValidaDataTransferencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/validation.png"))); // NOI18N
        btnValidaDataTransferencia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnValidaDataTransferenciaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnValidaDataTransferenciaFocusLost(evt);
            }
        });
        btnValidaDataTransferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidaDataTransferenciaActionPerformed(evt);
            }
        });
        painelPrincipal.add(btnValidaDataTransferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 210, 24, 24));
        painelPrincipal.add(txtDtProtocolo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 130, 24));

        cbComoSeraViagem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PACIENTE", "PACIENTE + ACOMPANHANTE", " " }));
        cbComoSeraViagem.setEnabled(false);
        painelPrincipal.add(cbComoSeraViagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 210, 150, 24));

        jLabel1.setText("Viagem?");
        painelPrincipal.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 190, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        painelPrincipal.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 0, 60, 40));
        painelPrincipal.add(barProgressProcedimentosBackEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 20, 60, -1));

        lblInfoProcessosBackEnd.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        lblInfoProcessosBackEnd.setText("Back-End");
        painelPrincipal.add(lblInfoProcessosBackEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 60, 20));

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png"))); // NOI18N
        btnAdicionar.setPreferredSize(new java.awt.Dimension(45, 45));
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
        btnAdicionar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAdicionarKeyPressed(evt);
            }
        });
        painelPrincipal.add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 430, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel7.setText("Descrever:");
        painelPrincipal.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(608, 190, 40, 20));

        lblValorDiaria.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lblValorDiaria.setText("R$");
        painelPrincipal.add(lblValorDiaria, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 190, 90, 20));

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvaRolover.png"))); // NOI18N
        btnSalvar.setEnabled(false);
        btnSalvar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        painelPrincipal.add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 430, 45, 45));

        getContentPane().add(painelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        //https://www.youtube.com/watch?v=jPfKFm2Yfow
        int coluna = tabela.getColumnModel().getColumnIndexAtX(evt.getX());
        int linha = evt.getY() / tabela.getRowHeight();

        if (linha < tabela.getRowCount() && linha >= 0 && coluna < tabela.getColumnCount() && coluna >= 0) {
            Object value = tabela.getValueAt(linha, coluna);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;

                /**
                 * Botão Exclusão Evento
                 */
                if (boton.getName().equals("Ex")) {

                    /*Evento ao ser clicado executa código*/
                    int excluir = JOptionPane.showConfirmDialog(null, "Deseja excluir registro?", "Informação", JOptionPane.YES_NO_OPTION);

                    if (excluir == JOptionPane.YES_OPTION) {

                        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

                        if (tabela.getSelectedRow() != -1) {
                            // tabelaVendas.removeRowSelectionInterval(linha, linha);
                            model.removeRow(tabela.getSelectedRow());

                        } else {
                            JOptionPane.showMessageDialog(this, "" + "\n Selecione um Registro na Tabela de Vendas", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/systorres/imagens/info.png")));
                        }
                    }
                }

            }

        }

    }//GEN-LAST:event_tabelaMouseClicked

    private void txtCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFActionPerformed

    private void txtInteressadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInteressadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInteressadoActionPerformed

    private void SalvarAdicoesEdicoes() {

        //inciaremos setando todos os valores queserão salvos na tbProtocoloTFD
        String DataProtocolo = ((JTextField) txtDtProtocolo.getDateEditor().getUiComponent()).getText();
        String DataTransferencia = ((JTextField) txtDataTranferencia.getDateEditor().getUiComponent()).getText();

        //setando campos para ser cadastrados na tabela de tbProtocoloTFD e btFluxoMovimento
        protocoloTFDDTO.setIdCustomDto(txtIdCodCustomizado.getText());
        fluxoTFDDTO.setIdCustomDto(txtIdCodCustomizado.getText());
        itensFluxoTFDDTO.setFkIDCustomDto(txtIdCodCustomizado.getText());

        protocoloTFDDTO.setDataProtocoloDto(DataProtocolo);
        fluxoTFDDTO.setDataProtocoloDto(DataProtocolo);

        protocoloTFDDTO.setDataTransferenciaDto(DataTransferencia);
        fluxoTFDDTO.setDataTransferenciaDto(DataTransferencia);

        protocoloTFDDTO.setCpfDto(txtCPF.getText());
        fluxoTFDDTO.setCpfDto(txtCPF.getText());

        protocoloTFDDTO.setDepOrigemDto(txtDepartamentoOrigem.getText());
        fluxoTFDDTO.setDepOrigemDto(txtDepartamentoOrigem.getText());

        protocoloTFDDTO.setInteressadoOrigemDto(txtInteressado.getText());
        fluxoTFDDTO.setInteressadoOrigemDto(txtInteressado.getText());

        protocoloTFDDTO.setDepDestinoDto(txtEnderecado.getText());
        fluxoTFDDTO.setDepDestinoDto(txtEnderecado.getText());
        itensFluxoTFDDTO.setDepartamentoDestinoDto(txtEnderecado.getText());

        protocoloTFDDTO.setInteressadoDestinoDto(txtInteressadoSetorInterno.getText());
        fluxoTFDDTO.setInteressadoDestinoDto(txtInteressadoSetorInterno.getText());
        itensFluxoTFDDTO.setInteressadoDestinoDto(txtInteressadoSetorInterno.getText());

        protocoloTFDDTO.setLoginDto(lblRepoLogin.getText());
        fluxoTFDDTO.setLoginDto(lblRepoLogin.getText());
        itensFluxoTFDDTO.setLoginUsuarioDto(lblRepoLogin.getText());

        protocoloTFDDTO.setPerfilDto(lblRepoPerfil.getText());
        fluxoTFDDTO.setPerfilDto(lblRepoPerfil.getText());
        itensFluxoTFDDTO.setPerfilDto(lblRepoPerfil.getText());

        protocoloTFDDTO.setUsuarioDto(lblRepoUsuario.getText());
        fluxoTFDDTO.setUsuarioDto(lblRepoUsuario.getText());

        protocoloTFDDTO.setHdDto(lblRepoHD.getText());
        fluxoTFDDTO.setHdDto(lblRepoHD.getText());
        itensFluxoTFDDTO.setRepositHDDto(lblRepoHD.getText());

        protocoloTFDDTO.setCpuDto(lblRepoCPU.getText());
        fluxoTFDDTO.setCpuDto(lblRepoCPU.getText());
        itensFluxoTFDDTO.setRepositCPUDto(lblRepoCPU.getText());

        //FAZENDO O SERVIÇO DOS RAPAZES DO PROTOCOLO
        itensFluxoTFDDTO.setStatusMovimentoDto("PROTOCOLADO|ENCAMINHANDO");

        //dados paciente 
        fluxoTFDDTO.setNome_pacienteDto(lblRepoPaciente.getText());
        fluxoTFDDTO.setSexo_pacienteDto(lblRepoSexo.getText());
        fluxoTFDDTO.setCpf_pacienteDto(txtCPF.getText());
        fluxoTFDDTO.setRua_pacienteDto(lblRepoRua.getText());
        fluxoTFDDTO.setnCartaoSus_pacienteDto(lblRepoNCartaoSUS.getText());

        /**
         * Observação:Essa é a forma de captuar do form gui um campo do tipo
         * senha para salva-lo num banco de dados como uma string
         */
        if (cbRelevancia.getSelectedItem() != null) {
            protocoloTFDDTO.setGrauRelevanciaDto(new String((String) cbRelevancia.getSelectedItem()));
            fluxoTFDDTO.setGrauRelevanciaDto(new String((String) cbRelevancia.getSelectedItem()));
        }

        if (cbComoSeraViagem.getSelectedItem() != null) {
            protocoloTFDDTO.setComoSeraViagemDto(new String((String) cbComoSeraViagem.getSelectedItem()));
            fluxoTFDDTO.setComoSeraViagemDto(new String((String) cbComoSeraViagem.getSelectedItem()));
        }

        try {

            if ((flag == 1)) {

                //cadastrando e retornando email tbProtocoloTFD
                boolean flagControleProtocolo = protocoloTFDBO.cadastrarRetorMsgEmail(protocoloTFDDTO);//salva cabeçalho do Protocolo

                //cadastrar e retornar email tbFluxoMovimento
                boolean flagControleFluxo = fluxoTFDBO.cadastrarRetorMsgEmail(fluxoTFDDTO);

                itensFluxoTFDDAO.inserir(itensFluxoTFDDTO);

                if (flagControleProtocolo && flagControleFluxo) {
                    //intanciando uma lista para salvar para salvar os itens adicionados
                    //na tabela (Camada GUI) na tabela tbItensProtocoloTFD contida no 
                    //banco de dados inovec87_infoq hospedado na nuvem hostgator
                    listaItensDoProtocolo = new ArrayList<>();

                    for (int i = 0; i < tabela.getRowCount(); i++) {

                        //aqui instacimaos a Estrutura DTO em Java que tem a mesma estrutura da 
                        //tabela contida no Banco de dados Hospedado na nuvem no nosso caso 
                        //tbItensProtocoloTFD
                        ItensDoProtocoloTFDDTO itensDoProtocoloTFD = new ItensDoProtocoloTFDDTO();

                        //agora iremos setar os dados que serão salvos na tabela[tbItensProtocoloTFD]
                        itensDoProtocoloTFD.setFkCustomDto(txtIdCodCustomizado.getText());//ID Custom || Banco->fk_id_com_data
                        itensDoProtocoloTFD.setItensDoProtocoloDto((String) tabela.getValueAt(i, 0));//ITENS DO PROTOCOLO||Banco->itensDoProtocolo
                        itensDoProtocoloTFD.setQtdeDto((int) tabela.getValueAt(i, 1)); //QTDE ||Banco->quantidade
                        itensDoProtocoloTFD.setDescreverDto((String) tabela.getValueAt(i, 2));//DESCREVER|| Banco->descreverProtocolo

                        /**
                         * a cada loop vamos adicionando valores a nossa lista
                         * que depois será salva no banoc de dados
                         */
                        listaItensDoProtocolo.add(itensDoProtocoloTFD);

                    }

                    /**
                     * Aqui passamos todos os valores adicionados em minha lista
                     * para um método na classe dao que irá fazer a inserção no
                     * banco de dados
                     */
                    ItensDoProtocoloTFDDAO itensTFD = new ItensDoProtocoloTFDDAO();

                    boolean flagControleItensDoProtocolo = itensTFD.salvarItensDoProtocoloTFD(listaItensDoProtocolo);

                    //Níveis de Segurança para envio de email
                    //* verificar se os dados do cabecalho Protocolo foi devidamente salvo na nuvem
                    //*verificar se os dados propicio da tabela de fluxo foi devidamente salvo na nuvem 
                    if (flagControleProtocolo && flagControleFluxo && flagControleItensDoProtocolo) {

                        //***************************************************************************
                        //VERIFICAR COM CALMA ESSA LINHA DE CODIGO O QUE ESTA HAVENDO 
                        // enviarEmailAnexo(DataProtocolo, DataTransferencia, txtInteressado.getText(), lblRepoSexo.getText(), lblRepoRua.getText(), lblRepoNCartaoSUS.getText(), lblValorDiaria.getText(), txtCPF.getText(), txtDepartamentoOrigem.getText(), txtEnderecado.getText(), txtInteressadoSetorInterno.getText(), lblRepoHD.getText(), lblRepoCPU.getText());
                        //****************************************************************************
                        vizualizandoCapaDeProcesso();
                        vizualizandoReciboProcesso();

                        Font font = new Font("Tahoma", Font.BOLD, 12);//label informativo    
                        lblLinhaInformativa.setText("Registro Salvo Com Sucesso.");
                        lblLinhaInformativa.setFont(font);
                        lblLinhaInformativa.setForeground(Color.BLUE);


                        /*Depois de Adicionados agora limpar os campos do formulario para que se possa fazer uma nova inserção*/
                        limparDadosPaciente();
                        limparDadosLogin();
                        limparDadosMaquina();
                        limparCampos();

                        int numeroLinhas = tabela.getRowCount();
                        if (numeroLinhas > 0) {
                            while (tabela.getModel().getRowCount() > 0) {
                                ((DefaultTableModel) tabela.getModel()).removeRow(0);
                            }

                        }

                        aoCarregarForm();
                    } else {

                        erroViaEmail("Erro Camada GUI:\n  if (flagControleProtocolo && flagControleFluxo && flagControleItensDoProtocolo){}", "NºPROT.:" + txtIdCodCustomizado.getText());
                        JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                                + "Método:SalvarAdicoesEdicoes() - Algumas dos Procedimentos de Salvamento \n"
                                + "não foi executado de forma correta\nCANCCELANDO O LANÇAMENTOS"
                                + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

                    }
                } else {
                    erroViaEmail("Erro Camada GUI:\nif (flagControleProtocolo && flagControleFluxo) {}", "N°PROT.:" + txtIdCodCustomizado.getText());
                }

            } else {
                erroViaEmail("Camada GUI:fla!=1", "NºPROT.:" + txtIdCodCustomizado.getText());
                JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                        + "Método:SalvarAdicoesEdicoes() - flag!=1\n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Camada GUI:" + e.getMessage());
            erroViaEmail("Camada GUI:erro ao acessar bloco try catch", "NºPROT.:" + txtIdCodCustomizado.getText() + "\n" + e.getMessage());
            e.printStackTrace();
            erroMsg(e.getMessage());
        }

    }

    private void erroMsg(String mensagemErro) {

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
            email.setSubject("Erro Camada GUI - Metodo:SalvarAdicoesEdicoes()");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Erro:" + mensagemErro + "\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            //tendo tudo configurado agora é enviar o email 
            email.send();

            //enviando mensagem de confirmação Usuário
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Todos Procedimentos concluídos com Sucesso."
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Erro ao Enviar Email." + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            e.printStackTrace();
        }

    }

    private void enviarEmail(String numeroProtocolo, String DtProtocolo, String DtTrasnf, String nomePaciente, String sexo, String rua, String nCartaoSUS, String diaria, String cpf, String fraseInfo) {

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.red);
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("Procedimentos Nuvem:Acessando Servidor google e gerando Procedimentos.");

        String meuEmail = "sisprotocoloj@gmail.com";
        String minhaSenha = "gerlande2111791020";
        String emailDestinatario = "prefaltoalegrema@gmail.com";
        String emailDestinatarioSecAdministracao = "jluzfreitas@gmail.com";
        String emailProcuradorGeral = "affonsobbatista@hotmail.com";

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
            email.setSubject("Nº Protocolo TFD: [" + numeroProtocolo + "]");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("--------------------------------------------------------------------------------\n"
                    + "Nº Protocolo TFD:" + numeroProtocolo + " Dt Protocolo:" + DtProtocolo + "Paciente:" + nomePaciente + "\n"
                    + "Sexo:" + sexo + " Rua: " + rua + "\n"
                    + "Número Cartão SUS: " + nCartaoSUS + "  Dt Trasferência: " + DtTrasnf + "  CPF: " + cpf + "\n"
                    + "------------------------------------------------------------------------------------\n"
                    + "Solicitação de pagamento referente ao TFD do Paciente " + nomePaciente + "\n"
                    + "no valor de" + diaria + "\n."
                    + fraseInfo + " \n"
                    + "-------------------------------------------------------------------------------------\n"
                    + "O temor do Senhor é o princípio da sabedoria, e o conhecimento do Santo a prudência. Provérbios 9:10  \n"
                    + "EM FASE DE TESTE (SISTEMA EM PRODUÇÃO)\n"
                    + "--------------------------------------------------------------------------------------\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            email.addTo(emailDestinatario);
            email.addTo(emailDestinatarioSecAdministracao);
            email.addTo(emailProcuradorGeral);
            //tendo tudo configurado agora é enviar o email 
            email.send();

            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Procedimentos de Nuvem Realizados com Sucesso!!!");
            //enviando mensagem de confirmação Usuário
//            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
//                    + "Todos Procedimentos concluídos com Sucesso."
//                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        } catch (Exception e) {

            erroViaEmail(e.getMessage(), "enviarEmail()-Esse método envia email para \n caixas de entrada de Email Pré-Determinadas\n PROTOCOLO Nº" + txtIdCodCustomizado.getText());
            e.printStackTrace();
        }

    }

    private void enviarEmailAnexo(String DtProtocolo, String DtTrasnf, String nomePaciente, String sexo, String rua, String nCartaoSUS, String diaria, String cpf, String Origem, String Destino, String aoCuidados, String hd, String cpu) {
      //----------------------------------------------------  
        //enviar Email com Anexo    
        //Dica Canal:https://www.youtube.com/watch?v=pBdaJhbI9I4
        //     

        //declaração de variáveis 
        String meuEmail = "sisprotocoloj@gmail.com";//meu email (Conta Principal)fará os lançamentos p/ demais
        String minhaSenha = "gerlande2111791020";//senha 
        String emailDestinatario = "prefaltoalegrema@gmail.com";//email para tesouraria 
        //  String emailDestinatarioSecAdministracao = "jluzfreitas@gmail.com";//segundo email destino 
        String emailSecretariaSaude = "setorprotocoloj@gmail.com";//email para secretaria de saude EMAIL:setorprotocoloj@gmail.com SENHA:setorpmaa1518
        //    String emailProcuradorGeral = "affonsobbatista@hotmail.com";//email para contabilidade

        //agora iremos utilizar os objetos das Bibliotecas referente Email
        //commons-email-1.5.jar e mail-1.4.7.jar
        //utilizamos o objeto email do tipo MultiPartEmail, pois, agora não 
        //será mais um email simples e sim um email com anexo.
        MultiPartEmail email = new MultiPartEmail();
        //Agora podemos utilizar o objeto email do Tipo SimplesEmail
        //agora iremo configurar o host abaixo estarei usando
        //o host do gmail para enviar as informações
        email.setHostName("smtp.gmail.com");
        //O proximo método abaixo é para configurar a porta desse host
        email.setSmtpPort(465);
        //esse método vai autenticar a conexão o envio desse email
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        //esse método vai ativar a conexao de forma segura (protocolo ssl)
        email.setSSLOnConnect(true);

        //agora um try porque os outro métodos podem gerar erros daí devem está dentro de um bloco de exceções
        try {
            //configura de quem é esse email de onde ele está vindo 
            email.setFrom(meuEmail);
            //configurar o assunto 
            email.setSubject("Data: [" + DtProtocolo + "] \n" + "[" + nomePaciente + "]");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("--------------------------------------------------------------------------------\n"
                    + "Nº Protocolo TFD:" + txtIdCodCustomizado.getText() + " Paciente:" + nomePaciente + "\n"
                    + "Sexo:" + sexo + " Rua: " + rua + "\n"
                    + "Número Cartão SUS: " + nCartaoSUS + "  Dt Trasferência: " + DtTrasnf + "  CPF: " + cpf + "\n"
                    + "--------------------------------------------------------------------------------------\n"
                    + "                             TRAMITAÇÃO MOVIMENTO INCIAL\n"
                    + "--------------------------------------------------------------------------------------\n"
                    + " Origem-->>[" + Origem + "]-->> [SETOR PROTOCOLO] protocolado em:" + DtProtocolo + ""
                    + "\n-->>Recebeu e Encaminhando para:[" + Destino + "] aos cuidados de \n"
                    + aoCuidados + "\nAutenticacao Maquina: HD: " + hd + "CPU: " + cpu + "\n"
                    + "------------------------------------------------------------------------------------\n"
                    + "O temor do Senhor é o princípio da sabedoria, e o conhecimento do Santo a prudência. \n"
                    + "Provérbios 9:10 - sisprotocoloj Analista:Tonis A. T. Ferreira  \n"
                    + "--------------------------------------------------------------------------------------\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            email.addTo(emailDestinatario);// TESOURARIA ADRIANO 
            //     email.addTo(emailProcuradorGeral);//CONTABILIDADE AFONSO
            email.addTo(emailSecretariaSaude);//SECRETARIA DE SAUDE SANDRA
            //Aqui criamos um vetor(arry)de um objeto do Tipo EmailAttachment
            EmailAttachment[] anexos = new EmailAttachment[2];

            //agora iremos pegar o anexos que é um vetor (array na) posição inicial 0(zero)
            //e intanciar um objeto do tipo EmailAttachment
            anexos[0] = new EmailAttachment();
            //agora iremos setar o primeiro documento na posição 0(zero) do array que irá 
            //anexar no gmail pré-configurado
            anexos[0].setPath("C:/ireport/reciboProcesso.pdf");
            //agora vamos renomear o arquivo anexaxo
            anexos[0].setName("digitalReciboProcesso.pdf");

            anexos[1] = new EmailAttachment();
            //agora iremos setar o primeiro documento na posição 0(zero) do array que irá 
            //anexar no gmail pré-configurado
            anexos[1].setPath("C:/ireport/capaProcesso.pdf");
            //agora vamos renomear o arquivo anexaxo
            anexos[1].setName("digitalCapaProcesso.pdf");

            //agora irei anexar os dois documento ao email 
            email.attach(anexos[0]);
            email.attach(anexos[1]);

            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            erroViaEmail(e.getMessage(), "enviarEmail()-Esse método envia email para \n caixas de entrada de Email Pré-Determinadas\n PROTOCOLO Nº" + txtIdCodCustomizado.getText());
            e.printStackTrace();
        }

    }

    private void limparCampos() {
        this.txtIdCodCustomizado.setText("");
        this.txtDtProtocolo.setDate(null);
        this.txtCPF.setText("");
        this.txtDepartamentoOrigem.setText("");
        this.txtInteressadoSetorInterno.setText("");
        this.txtEnderecado.setText("");
        this.txtInteressado.setText("");
        this.cbRelevancia.setSelectedItem(null);
        this.txtDataTranferencia.setDate(null);
        this.lblValorDiaria.setText("");

    }

    private void limparDadosMaquina() {
        this.lblRepoHD.setText("");
        this.lblRepoCPU.setText("");
    }

    private void limparDadosLogin() {
        this.lblRepoLogin.setText("");
        this.lblRepoPerfil.setText("");
        this.lblRepoUsuario.setText("");
    }

    private void limparDadosPaciente() {
        this.lblRepoPaciente.setText("");
        this.lblRepoSexo.setText("");
        this.lblRepoRua.setText("");
        this.lblRepoNCartaoSUS.setText("");

    }
    private void btnAdicionarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusGained
        btnAdicionar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnAdicionarFocusGained

    private void btnAdicionarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusLost
        btnAdicionar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnAdicionarFocusLost

    private void btnAdicionarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseEntered
        btnAdicionar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnAdicionarMouseEntered

    private void btnAdicionarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseExited
        btnAdicionar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnAdicionarMouseExited

    private void acaoAdicionar() {
        aoCarregarForm();
        gerarCodigoGUIeDataSistema();
        buscarPersonalizarLoginMaquina();
        flag = 1;
        txtCPF.setEnabled(true);
        txtCPF.requestFocus();
        txtCPF.setText("");
        btnAdicionar.setEnabled(false);

    }


    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnAdicionarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAdicionarKeyPressed

        //        acaoAdicionar();
        if (evt.getKeyCode() == evt.VK_ENTER) {
//            acaoAdicionar();

        }
    }//GEN-LAST:event_btnAdicionarKeyPressed

    private void validacoesObrigatoriasCPF() throws PersistenciaException {

        pacienteDTO.setCpfPacienteDto(this.txtCPF.getText());

        boolean retornoVerifcaDuplicidade = pacienteDAO.verificaDuplicidade(pacienteDTO);

        if (retornoVerifcaDuplicidade == true) {

            try {
                PacienteDTO pacienteDto = pacienteDAO.buscarPeloCPF(pacienteDTO);//verificar se já existe CNPJ

                if (pacienteDto.getCpfPacienteDto() != null) {

                    //cor de fundo painel paciente 
                    this.painelInfoPaciente.setBackground(new Color(9, 81, 107));

                    Font fPaciente = new Font("Tahoma", Font.BOLD, 10);

                    this.lblRepoPaciente.setFont(fPaciente);
                    this.lblRepoPaciente.setForeground(Color.WHITE);
                    this.lblRepoPaciente.setText("");
                    this.lblRepoPaciente.setText(pacienteDto.getNomePacienteDto());
                    //preenchendo campo Interessado
                    this.txtInteressado.setFont(fPaciente);
                    this.txtInteressado.setText(lblRepoPaciente.getText());

                    this.lblRepoSexo.setFont(fPaciente);
                    this.lblRepoSexo.setForeground(Color.WHITE);
                    this.lblRepoSexo.setText("");
                    this.lblRepoSexo.setText(pacienteDto.getSexoDto());

                    this.lblRepoRua.setFont(fPaciente);
                    this.lblRepoRua.setForeground(Color.WHITE);
                    this.lblRepoRua.setText("");
                    this.lblRepoRua.setText(pacienteDto.getRuaPacienteDto());

                    this.lblRepoNCartaoSUS.setFont(fPaciente);
                    this.lblRepoNCartaoSUS.setForeground(Color.WHITE);
                    this.lblRepoNCartaoSUS.setText("");
                    this.lblRepoNCartaoSUS.setText(pacienteDto.getNumeroCartaoSusDto());

                    this.btnValidaCPF.setEnabled(true);
//                    this.btnCancelar.setEnabled(true);
                    this.btnValidaCPF.requestFocus();

                    this.txtDepartamentoOrigem.setFont(fPaciente);
                    this.txtDepartamentoOrigem.setText("SECRETARIA DE SAUDE");
                    this.cbRelevancia.setFont(fPaciente);

                    this.txtInteressadoSetorInterno.setFont(fPaciente);
                    this.txtInteressadoSetorInterno.setText("AFONSO BARROS BATISTA");

                    Font fCbComoSera = new Font("Tahoma", Font.BOLD, 8);
                    this.cbComoSeraViagem.setFont(fCbComoSera);

                    this.txtDepartamentoOrigem.setEnabled(true);
                    this.txtDepartamentoOrigem.setEditable(false);
                    this.txtDepartamentoOrigem.setBackground(new Color(9, 81, 107));
                    this.txtDepartamentoOrigem.setForeground(Color.WHITE);

                    this.txtInteressado.setEnabled(true);
                    this.txtInteressado.setEditable(false);
                    this.txtInteressado.setBackground(new Color(9, 81, 107));
                    this.txtInteressado.setForeground(Color.WHITE);

                    this.txtEnderecado.setEnabled(true);
                    this.txtEnderecado.setEditable(false);
                    this.txtEnderecado.setBackground(new Color(9, 81, 107));
                    this.txtEnderecado.setForeground(Color.WHITE);

                    this.txtEnderecado.setFont(fPaciente);
                    this.txtEnderecado.setText("CONTABILIDADE");

                    this.txtInteressadoSetorInterno.setEnabled(true);
                    this.txtInteressadoSetorInterno.setEditable(false);
                    this.txtInteressadoSetorInterno.setBackground(new Color(9, 81, 107));
                    this.txtInteressadoSetorInterno.setForeground(Color.WHITE);

                    this.txtDataTranferencia.setEnabled(true);
                    this.btnValidaDataTransferencia.setEnabled(true);
                    this.txtDataTranferencia.requestFocus();

                }

            } catch (PersistenciaException ex) {
                erroViaEmail(ex.getMessage(), "validacoesObrigatoriasCPF()-Camada GUI-TelaProtocDocsTFS");
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {

            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.red);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("CPF não foi reconhecido em nossa Base de Dados");

            JOptionPane.showMessageDialog(this, "" + "\n O CPF Digitado não se encontra Registrado \nem nossa Base de Dados.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            txtCPF.setText("");
            txtCPF.requestFocus();
            btnValidaCPF.setEnabled(false);
        }

    }


    private void txtDepartamentoOrigemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDepartamentoOrigemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepartamentoOrigemActionPerformed

    private void acaoBuscarPacientePeloCPF() throws PersistenciaException {
        validacoesObrigatoriasCPF();
    }


    private void btnValidaCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidaCPFActionPerformed


    }//GEN-LAST:event_btnValidaCPFActionPerformed


    private void txtCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFKeyPressed

        if (!txtCPF.getText().equals("   .   .   -  ")) {

            if (evt.getKeyCode() == evt.VK_ENTER) {
                btnValidaCPF.requestFocus();
                btnValidaCPF.setEnabled(true);
            }
        } else {
            if (evt.getKeyCode() == evt.VK_ENTER) {
                btnValidaCPF.setEnabled(false);
                txtCPF.requestFocus();
                Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 

                lblLinhaInformativa.setFont(f);
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setText("OBRIGATÓRIO! CPF do paciente.");
                lblLinhaInformativa.setForeground(Color.red);
                txtCPF.setBackground(Color.red);
                txtCPF.setForeground(Color.WHITE);
            }
        }
    }//GEN-LAST:event_txtCPFKeyPressed

    private void btnValidaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusGained
        btnValidaCPF.setBackground(Color.YELLOW);

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            try {
                acaoBuscarPacientePeloCPF();
            } catch (PersistenciaException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
        }
    }//GEN-LAST:event_btnValidaCPFFocusGained

    private void btnValidaCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusLost
        btnValidaCPF.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnValidaCPFFocusLost

    private void txtCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusGained

        lblLinhaInformativa.setText("OBRIGATÓRIO! Digite o CPF do Paciente, Pressione [ENTER] em Seguida faça Validação CPF.");
        lblLinhaInformativa.setForeground(Color.BLUE);
    }//GEN-LAST:event_txtCPFFocusGained

    private void txtDataTranferenciaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataTranferenciaFocusGained
        txtDataTranferencia.setBackground(Color.YELLOW);
        lblLinhaInformativa.setText("Digite a Data da Transferência R$");
    }//GEN-LAST:event_txtDataTranferenciaFocusGained

    private void txtDataTranferenciaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataTranferenciaFocusLost
        txtDataTranferencia.setBackground(Color.white);
        lblLinhaInformativa.setText("");
    }//GEN-LAST:event_txtDataTranferenciaFocusLost

    private void btnValidaDataTransferenciaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaDataTransferenciaFocusGained
        btnValidaDataTransferencia.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnValidaDataTransferenciaFocusGained

    private void btnValidaDataTransferenciaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaDataTransferenciaFocusLost
        btnValidaDataTransferencia.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnValidaDataTransferenciaFocusLost

    private void verificandoCampoDataTransf() throws ParseException {

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        Date DataProtocolo = formatador.parse(txtDtProtocolo.getDateFormatString());
        //agroa pegar uma data do componente do tipo JDateChooser e transformar numa String
        String DataTransferenciaCapturada = formatador.format(txtDataTranferencia.getDate());
        Date DataTransferencia = formatador.parse(DataTransferenciaCapturada);

        //aqui teremos a diferença em milissegundo entre as duas datas 
        //o metodo Math.abs() pega o valor absoluto
        long diffEmMil = Math.abs(DataTransferencia.getTime() - DataProtocolo.getTime());

        //o resultado de Milessegundo irei converter para dias 
        long diff = TimeUnit.DAYS.convert(diffEmMil, TimeUnit.MILLISECONDS);

        if (txtDataTranferencia.getDate() != null) {
            //se o numro de dias maior que zero  
            if (diff > 0) {
                Font fonte = new Font("Tahoma", Font.BOLD, 12);
                lblLinhaInformativa.setFont(fonte);
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setForeground(Color.BLUE);
                lblLinhaInformativa.setText("Temos " + diff + " dia(s)" + " até o Processo de Transferência(R$)");

            }

            if (diff < 0) {

                Font fonte = new Font("Tahoma", Font.BOLD, 12);
                lblLinhaInformativa.setFont(fonte);
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setForeground(Color.RED);
                lblLinhaInformativa.setText("Temos " + diff + " dia(s)" + " a Data de Transferência deve ser Superior a Data Protocolada.");

            }

            if (diff == 0) {

                Font fonte = new Font("Tahoma", Font.BOLD, 12);
                lblLinhaInformativa.setFont(fonte);
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setForeground(Color.RED);
                lblLinhaInformativa.setText("Temos " + diff + " dia" + " para efetuar a Transferência, a uma forte possibilidade de Recusa devido o Tramite do Processo.");

            }

        }

    }

    //metodo para checar se a data agendada para transferencia vai cair
    //no sábado ou domingo e metodo para recusar esse agendamento
    //https://pt.stackoverflow.com/questions/73910/verificar-se-o-dia-x-cai-em-um-sabado-ou-domingo
    private boolean checarFinaisDeSemana(Calendar data) throws ParseException {
// se for domingo

        boolean flagCalendar = true;

        if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Font f = new Font("Tahoma", Font.BOLD, 12);
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.red);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Erro na tentativa de agendar uma Data de Transferência para DOMINGO");
            txtDataTranferencia.requestFocus();
            txtDataTranferencia.setBackground(Color.red);

            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

            //bem nesse ponto o codigo dectou que a dataDeTransferencia setada pelo usuário 
            //eh domingo logo recebendo mais um dia ele vai para segundo 
            //o código abaixo vai pegar a data atual neste caso domigo e adicionar mais 1
            //dia e setar no campo liberando o procedimento 
            flagCalendar = false;
        }

        if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Font f = new Font("Tahoma", Font.BOLD, 12);
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.red);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Erro na tentativa de agendar uma Data de Transferência para SÁBADO");
            txtDataTranferencia.requestFocus();
            txtDataTranferencia.setBackground(Color.red);

            flagCalendar = false;
        }

        return flagCalendar;
    }

    private void btnValidaDataTransferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidaDataTransferenciaActionPerformed

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        try {
            String nomePaciente = txtInteressado.getText();
            String DataProtocolo = ((JTextField) txtDtProtocolo.getDateEditor().getUiComponent()).getText();
            String DataTransferencia = ((JTextField) txtDataTranferencia.getDateEditor().getUiComponent()).getText();

            //Convertendo as Datas de String para Date |String|--->>|Date|
            Date primeiraDt = formatador.parse(DataProtocolo);
            Date segundaDt = formatador.parse(DataTransferencia);

            //converter uma String para Calendar
            Calendar dataNoFormatoCalendar = formatador.getCalendar();
            dataNoFormatoCalendar.setTime(segundaDt);//date
            boolean resultadoCritica = checarFinaisDeSemana(dataNoFormatoCalendar);

            //se DataTransferencia diferente de vazio -1ª Condição
            if (!DataTransferencia.isEmpty()) {

                //se resultado critica for true quer dizer que não é nem sabado nem domingo então prosseguir com 
                //o programa
                if (resultadoCritica) {
                    // se Segunda Data(Data de Transferência) for maior que Primeria Data(Data de Protocolo)
                    if (segundaDt.getTime() >= primeiraDt.getTime()) {

                        //aqui teremos a diferença em milissegundo entre as duas datas 
                        //o metodo Math.abs() pega o valor absoluto
                        long diffEmMil = Math.abs(segundaDt.getTime() - primeiraDt.getTime());
                        //pegando a diferença entre dias utilizado a classe Time.Unit
                        long diff = TimeUnit.DAYS.convert(diffEmMil, TimeUnit.MILLISECONDS);

                        //Aqui instanciamos um String recebendo inicialmente um valor nulo 
                        String DiariaTFD = "";

                        String situacaoViagem = ((String) cbComoSeraViagem.getSelectedItem());

                        //colocamos nesse ponto uma condicional que irá definir o valor do TFD 
                        //de acordo com critérios pré definidos - FUTURAMENTE DEVERÁ PEGAR 
                        //esse valores diretamente de uma tabela tornando essa parte do programa 
                        //mais dinâmica
                        if (cbComoSeraViagem.getSelectedItem().equals("PACIENTE")) {

                            DiariaTFD = "R$ 150,00";
                            lblValorDiaria.setText(DiariaTFD);

                        }

                        if (cbComoSeraViagem.getSelectedItem().equals("PACIENTE + ACOMPANHANTE")) {
                            DiariaTFD = "R$ 300,00";
                            lblValorDiaria.setText(DiariaTFD);
                        }

                        //se o numro de dias maior que zero  
                        if (diff > 0) {
                            Font fonte = new Font("Tahoma", Font.BOLD, 12);
                            lblLinhaInformativa.setFont(fonte);
                            lblLinhaInformativa.setText("");
                            lblLinhaInformativa.setForeground(Color.BLUE);
                            lblLinhaInformativa.setText("Temos " + diff + " dia(s)" + " até o Processo de Transferência(R$)");
                            btnItensDoProtocolo.setEnabled(true);
                            btnItensDoProtocolo.requestFocus();
                            //
                            txtCodItemProtocolo.setText("41");
                            txtItemDoProtocolo.setText("TFD(TRATAMENTO FORA DO DOMICILIO)");
                            txtQuantidade.setText("1");

                            Font fTxtDescreve = new Font("Tahoma", Font.BOLD, 9);
                            txtAreaDescreve.setFont(fTxtDescreve);
                            txtAreaDescreve.setForeground(Color.BLUE);

                            if (cbComoSeraViagem.getSelectedItem().equals("PACIENTE")) {
                                txtAreaDescreve.setText("Solicitação de pagamento referente ao TFD do Paciente \n" + nomePaciente + "\n no valor de " + DiariaTFD + " .\n"
                                        + "Como será viagem?" + situacaoViagem + "\n Data Transferências:" + DataTransferencia
                                        + "\nDias Transferencia:" + diff + "dia(s)");
                            }

                            if (cbComoSeraViagem.getSelectedItem().equals("PACIENTE + ACOMPANHANTE")) {
                                txtAreaDescreve.setText("Solicitação de pagamento referente ao TFD do Paciente\n " + nomePaciente + "\n com acompanhante no valor de " + DiariaTFD + ".\n"
                                        + "Como será viagem?" + situacaoViagem + "\nDias Transferencia:" + diff + "dia(s)");

                            }

                            txtQuantidade.setEnabled(true);
                            txtAreaDescreve.setEnabled(true);
                            this.btnAdicionarItem.setEnabled(true);
                        }

                        //Nessa parte do código colocamos uma condicional que irá verificar o grau de 
                        //Urgência de acordo com critérios pré definidos pelo programador podemos também 
                        //neste ponto deixar esse critério mais dinâmico para FUTURA versão 
                        if (diff > 8) {
                            cbRelevancia.setSelectedItem("MODERADO");
                        }

                        if (diff <= 8) {
                            cbRelevancia.setSelectedItem("URGENTE");
                        }

                        if (diff == 0) {

                            Font fonte = new Font("Tahoma", Font.BOLD, 12);
                            lblLinhaInformativa.setFont(fonte);
                            lblLinhaInformativa.setText("");
                            lblLinhaInformativa.setForeground(Color.RED);
                            lblLinhaInformativa.setText("Temos " + diff + " dia" + " para efetuar a Transferência, a uma forte possibilidade de Recusa devido o Tramite do Processo.");
                            btnItensDoProtocolo.setEnabled(true);
                            btnItensDoProtocolo.requestFocus();

                            txtCodItemProtocolo.setText("41");
                            txtItemDoProtocolo.setText("TFD(TRATAMENTO FORA DO DOMICILIO)");
                            txtQuantidade.setText("1");

                            Font fTxtDescreve = new Font("Tahoma", Font.BOLD, 9);
                            txtAreaDescreve.setFont(fTxtDescreve);
                            txtAreaDescreve.setForeground(Color.BLUE);

                            if (cbComoSeraViagem.getSelectedItem().equals("PACIENTE")) {
                                txtAreaDescreve.setText("Solicitação de pagamento referente ao TFD do Paciente \n" + nomePaciente + "\n no valor de " + DiariaTFD + " .\n"
                                        + "Como será viagem?" + situacaoViagem + "\nDias Transferencia:" + diff + "dia(s)");
                            }

                            if (cbComoSeraViagem.getSelectedItem().equals("PACIENTE + ACOMPANHANTE")) {
                                txtAreaDescreve.setText("Solicitação de pagamento referente ao TFD do Paciente\n " + nomePaciente + "\n com acompanhante no valor de " + DiariaTFD + ".\n"
                                        + "Como será viagem?" + situacaoViagem + "\nDias Transferencia:" + diff + "dia(s)");

                            }

                            txtQuantidade.setEnabled(true);
                            txtAreaDescreve.setEnabled(true);
                            this.btnAdicionarItem.setEnabled(true);

                            //grau de relevancia 
                            cbRelevancia.setSelectedItem("URGENTE");
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                                + "A Data de Transferência Deve ser Maior ou Igual a Data Protocolada"
                                + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                        txtDataTranferencia.setBackground(Color.red);
                        txtDataTranferencia.requestFocus();
                        //Font
                    }

                } else {

                    if (dataNoFormatoCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {

                        Calendar c = Calendar.getInstance();
                        //capiturou a data no formato date 
                        c.setTime(segundaDt);
                        //adicionamos mais um dia a data capturada 
                        c.add(Calendar.DATE, +1);
                        //agora vamos receber a data alterada no formato date 
                        Date dataAlterada = c.getTime();
                        txtDataTranferencia.setDate(dataAlterada);

                        JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                                + "Domingo não é uma data para efetuar pagamento de TFD.\n"
                                + "O sistema irá adiantar em 1(UM) dia a Data do pagamento\n"
                                + "Logo o pagamento será efetuado numa SEGUNDA-FEIRA.\n"
                                + "Observação Importante: Perceba se a SEGUNDA-FEIRA setada \n"
                                + "no campo Data da Transferência´é um dia útil, caso seja \n"
                                + "um feriado sete manualmente a próxima data Util"
                                + "", "Info:Impossivel Pagamento TFD", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                        txtDataTranferencia.requestFocus();
                    }

                    if (dataNoFormatoCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {

                        Calendar c = Calendar.getInstance();
                        //capiturou a data no formato date 
                        c.setTime(segundaDt);
                        //adicionamos mais um dia a data capturada 
                        c.add(Calendar.DATE, +2);
                        //agora vamos receber a data alterada no formato date 
                        Date dataAlterada = c.getTime();
                        txtDataTranferencia.setDate(dataAlterada);

                        JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                                + "Sábado não é uma data para efetuar pagamento de TFD.\n"
                                + "O sistema irá adiantar em 2(DOIS) dias a Data do pagamento\n"
                                + "Logo o pagamento será efetuado numa SEGUNDA-FEIRA.\n"
                                + "Observação Importante: Perceba se a SEGUNDA-FEIRA setada \n"
                                + "no campo Data da Transferência´é um dia útil, caso seja \n"
                                + "um feriado sete manualmente a próxima data Util"
                                + "", "Info:Impossivel Pagamento TFD", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    }

                    txtDataTranferencia.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "O campo Data Transf. R$ Deve Conter\n uma Data Superior Data Protocolo "
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }

    }//GEN-LAST:event_btnValidaDataTransferenciaActionPerformed

    private void btnItensDoProtocoloFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnItensDoProtocoloFocusGained
        btnItensDoProtocolo.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnItensDoProtocoloFocusGained

    private void btnItensDoProtocoloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnItensDoProtocoloFocusLost
        btnItensDoProtocolo.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnItensDoProtocoloFocusLost

    br.com.subgerentepro.telas.Frm_Lista_Itens_Protocolo_TFD formListaItensProtocoloTFD;
    private void btnItensDoProtocoloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItensDoProtocoloActionPerformed
        if (estaFechado(formListaItensProtocoloTFD)) {
            formListaItensProtocoloTFD = new Frm_Lista_Itens_Protocolo_TFD();
            DeskTop.add(formListaItensProtocoloTFD).setLocation(200, 40);
            formListaItensProtocoloTFD.setTitle("Listar Itens do Protocolo");
            formListaItensProtocoloTFD.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formListaItensProtocoloTFD.toFront();
            formListaItensProtocoloTFD.show();

        }


    }//GEN-LAST:event_btnItensDoProtocoloActionPerformed

    private void txtCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusLost
        lblLinhaInformativa.setText("");
    }//GEN-LAST:event_txtCPFFocusLost

    Font f = new Font("Tahoma", Font.BOLD, 12);

    private void adicionarItensTabelaItensProtocolo() {

        if (txtItemDoProtocolo.getText().equals("") || (txtItemDoProtocolo.getText() == null)) {
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("É necessário Definir um Item de Protocolo");
            lblLinhaInformativa.setForeground(Color.red);
            txtItemDoProtocolo.setBackground(Color.yellow);
            txtItemDoProtocolo.requestFocus();
        } else {

            if ((txtQuantidade.getText().equals("") || txtQuantidade.getText() == null)) {

                lblLinhaInformativa.setFont(f);
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setText("O campo Quantidade não pode conter valor Nulo.");
                lblLinhaInformativa.setForeground(Color.red);
                txtQuantidade.setBackground(Color.yellow);
                txtQuantidade.requestFocus();

            } else {

                //--------------------------------------------------------------------       
                //https://www.youtube.com/watch?v=jPfKFm2Yfow
                tabela.setDefaultRenderer(Object.class, new Render());

                JButton btnExcluir = new JButton("Excluir");

                btnExcluir.setName(
                        "Ex");

                int codigo = Integer.parseInt(txtCodItemProtocolo.getText());
                DefaultTableModel model = (DefaultTableModel) tabela.getModel();
                ArrayList<ItemProtocoloDTO> list;

                try {

                    list = (ArrayList<ItemProtocoloDTO>) itemProtocoloDAO.listaCodigo(codigo);

                    Object rowData[] = new Object[4];//são 04 colunas 

                    for (int i = 0; i < list.size(); i++) {

                        rowData[0] = list.get(i).getNomeDto();
                        rowData[1] = Integer.parseInt(txtQuantidade.getText());
                        rowData[2] = txtAreaDescreve.getText();
                        rowData[3] = btnExcluir;
                        model.addRow(rowData);

                    }

                    tabela.setModel(model);

                    /**
                     * Coluna ID posição[0] vetor
                     */
                    tabela.getColumnModel().getColumn(0).setPreferredWidth(200);
                    tabela.getColumnModel().getColumn(1).setPreferredWidth(70);
                    tabela.getColumnModel().getColumn(2).setPreferredWidth(400);
                    tabela.getColumnModel().getColumn(3).setPreferredWidth(100);

                    txtCodItemProtocolo.setText("");
                    txtItemDoProtocolo.setText("");
                    txtQuantidade.setText("");
                    txtAreaDescreve.setText("");
                    btnSalvar.setEnabled(true);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() AQUI pOREQUE \n"
                            + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                            + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
                }
            }

        }

    }
    private void btnAdicionarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarItemActionPerformed
//    

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            adicionarItensTabelaItensProtocolo();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
        }


    }//GEN-LAST:event_btnAdicionarItemActionPerformed

    private void cbRelevanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRelevanciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbRelevanciaActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            SalvarAdicoesEdicoes();
        } else {
            erroViaEmail("Erro na Camada GUI:btnSalvar\n", "Evento:btnSalvarActionPerformed()");
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
        }


    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtInteressadoSetorInternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInteressadoSetorInternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInteressadoSetorInternoActionPerformed

    private void vizualizandoReciboProcesso() {
        //**********************************************************************************
        //CURSO:Como Desenvolver Relatórios em Java com o JasperReports e o JasperStudio
        //apartir daqui começa de fato a parte pratica em relatório
        //https://www.udemy.com/course/relatorios-em-java-para-iniciantes-como-desenvolver/learn/lecture/18733998#overview
        //https://www.udemy.com/course/relatorios-em-java-para-iniciantes-como-desenvolver/learn/lecture/18734818#overview
        //***********************************************************************************************

        //criando um objeto de conexão do tipo ConexãoUtil
        ConexaoUtil conecta = new ConexaoUtil();

        try {//tratando os metódos abaixo dentro de um bloco try chatch

            //iremos setar o caminho de onde se encontra o arquivo xml por meio de um InputStream
            InputStream jrxmlStream = TelaProtocDocsTFD.class.getResourceAsStream("/ireport/reciboprocesso.xml");
            //compilamos o arquivo xml capiturado acima pelo objeto do Tipo InputStream
            //e fazemos sua conpilaçao por meio do JasperCompilerManger
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            //Esse relatório tem parâmetros daí criamos um HashMap 
            Map<String, Object> parametros = new HashMap<>();
            //passamos o parâmetro pro relatório ja compilado para fazer o filtro dos dados 
            parametros.put("CONDICAO_ID", txtIdCodCustomizado.getText());

            //aqui preenchemos o relatório e passamos a conexao objeto conecta.getConnection()
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conecta.getConnection());

            //neste ponto já tenho meu relatório preenchido agora vamos salvar o arquivo JasperPrint 
            File file = new File("C:/ireport/reciboprocesso.jrprint");

            //agora iremos fazer uma verificação para ve se o arquivo existe ou não na pasta
            if (!file.exists()) {

                //iremos entao criá-lo
                file.createNewFile();
                JRSaver.saveObject(jasperPrint, file);

                //vamos exportar o arquivo no formato pdf
                JRPdfExporter exporterPdf = new JRPdfExporter();
                //aqui é o input que irá alimentar o arquivo pdf que no caso arqui jasperPrint
                //esse método abaixo é uma interface que encapsula o objeto jasperprint
                //preparando para exportação
                ExporterInput input = new SimpleExporterInput(jasperPrint);
                exporterPdf.setExporterInput(input);
                //agora iremos definir o caminho onde ficará o arquivo gerado
                //esse caminho é encapsulado pela classe SimpleOutputStreamExporterOutput 
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("C:/ireport/reciboProcesso.pdf")));

                //agora iremos chamar o método que gera o relatório propriamente dito 
                exporterPdf.exportReport();

            } else {//caso contrário, ou seja, se não  existir 
                //vamos exportar o arquivo no formato pdf
                JRPdfExporter exporterPdf = new JRPdfExporter();
                //aqui é o input que irá alimentar o arquivo pdf que no caso arqui jasperPrint
                //esse método abaixo é uma interface que encapsula o objeto jasperprint
                //preparando para exportação
                ExporterInput input = new SimpleExporterInput(jasperPrint);
                exporterPdf.setExporterInput(input);
                //agora iremos definir o caminho onde ficará o arquivo gerado
                //esse caminho é encapsulado pela classe SimpleOutputStreamExporterOutput 
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("C:/ireport/reciboProcesso.pdf")));

                //agora iremos chamar o método que gera o relatório propriamente dito 
                exporterPdf.exportReport();
            }
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);

        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "vizualizandoReciboProcesso()");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tratamento Erro Camada GUI: " + ex.getMessage());
        }

    }

    private void vizualizandoCapaDeProcesso() {

        //**********************************************************************************
        //CURSO:Como Desenvolver Relatórios em Java com o JasperReports e o JasperStudio
        //apartir daqui começa de fato a parte pratica em relatório
        //https://www.udemy.com/course/relatorios-em-java-para-iniciantes-como-desenvolver/learn/lecture/18733998#overview
        //https://www.udemy.com/course/relatorios-em-java-para-iniciantes-como-desenvolver/learn/lecture/18734818#overview
        //***********************************************************************************************
        
        ConexaoUtil conecta = new ConexaoUtil();
        try {

            InputStream jrxmlStream = TelaProtocDocsTFD.class.getResourceAsStream("/ireport/capaDeProcesso.xml");

            //compilamos o arquivo 
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            Map<String, Object> parametros = new HashMap<>();
            //preencher o Map de Parametros 
            parametros.put("CONDICAO_ID", txtIdCodCustomizado.getText());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conecta.getConnection());

            //neste ponto já tenho meu relatório preenchido agora vamos salvar o arquivo JasperPrint 
            File file = new File("C:/ireport/capaprocesso.jrprint");

            //agora iremos fazer uma verificação para ve se o arquivo existe ou não na pasta
            if (!file.exists()) {

                //iremos entao criá-lo
                file.createNewFile();
                JRSaver.saveObject(jasperPrint, file);

                //vamos exportar o arquivo no formato pdf
                JRPdfExporter exporterPdf = new JRPdfExporter();
                //aqui é o input que irá alimentar o arquivo pdf que no caso arqui jasperPrint
                //esse método abaixo é uma interface que encapsula o objeto jasperprint
                //preparando para exportação
                ExporterInput input = new SimpleExporterInput(jasperPrint);
                exporterPdf.setExporterInput(input);
                //agora iremos definir o caminho onde ficará o arquivo gerado
                //esse caminho é encapsulado pela classe SimpleOutputStreamExporterOutput 
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("C:/ireport/capaProcesso.pdf")));

                //agora iremos chamar o método que gera o relatório propriamente dito 
                exporterPdf.exportReport();

            } else {//caso contrário, ou seja, se não  existir 
                //vamos exportar o arquivo no formato pdf
                JRPdfExporter exporterPdf = new JRPdfExporter();
                //aqui é o input que irá alimentar o arquivo pdf que no caso arqui jasperPrint
                //esse método abaixo é uma interface que encapsula o objeto jasperprint
                //preparando para exportação
                ExporterInput input = new SimpleExporterInput(jasperPrint);
                exporterPdf.setExporterInput(input);
                //agora iremos definir o caminho onde ficará o arquivo gerado
                //esse caminho é encapsulado pela classe SimpleOutputStreamExporterOutput 
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("C:/ireport/capaProcesso.pdf")));

                //agora iremos chamar o método que gera o relatório propriamente dito 
                exporterPdf.exportReport();
            }
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);

        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "VizualizandoCapaDeProcesso()");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tratamento Erro Camada GUI: " + ex.getMessage());
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barProgressProcedimentosBackEnd;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAdicionarItem;
    private javax.swing.JButton btnItensDoProtocolo;
    private javax.swing.JToggleButton btnSalvar;
    private javax.swing.JButton btnValidaCPF;
    private javax.swing.JButton btnValidaDataTransferencia;
    public static javax.swing.JComboBox cbComoSeraViagem;
    private javax.swing.JComboBox cbRelevancia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCPFPaciente;
    private javax.swing.JLabel lblCPU;
    private javax.swing.JLabel lblCartaoSUS;
    private javax.swing.JLabel lblDataProtocolo;
    private javax.swing.JLabel lblDepOrigem;
    private javax.swing.JLabel lblEnderecado;
    private javax.swing.JLabel lblGrauRelevancia;
    private javax.swing.JLabel lblHD2;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIdCustomizado;
    private javax.swing.JLabel lblInfoDataVenda;
    private javax.swing.JLabel lblInfoProcessosBackEnd;
    private javax.swing.JLabel lblInteressado;
    private javax.swing.JLabel lblInteressadoFuncionario;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblPacientel;
    private javax.swing.JLabel lblPerfil2;
    private javax.swing.JLabel lblRepoCPU;
    private javax.swing.JLabel lblRepoHD;
    private javax.swing.JLabel lblRepoLogin;
    private javax.swing.JLabel lblRepoNCartaoSUS;
    private javax.swing.JLabel lblRepoPaciente;
    private javax.swing.JLabel lblRepoPerfil;
    private javax.swing.JLabel lblRepoRua;
    private javax.swing.JLabel lblRepoSexo;
    private javax.swing.JLabel lblRepoUsuario;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblValorDiaria;
    private javax.swing.JPanel painelDadosUsuarioMaquina;
    private javax.swing.JPanel painelInfoPaciente;
    private javax.swing.JPanel painelLancamento;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JTable tabela;
    public static javax.swing.JTextArea txtAreaDescreve;
    private javax.swing.JFormattedTextField txtCPF;
    public static javax.swing.JTextField txtCodItemProtocolo;
    private com.toedter.calendar.JDateChooser txtDataTranferencia;
    private javax.swing.JTextField txtDepartamentoOrigem;
    private com.toedter.calendar.JDateChooser txtDtProtocolo;
    private javax.swing.JTextField txtEnderecado;
    private javax.swing.JTextField txtIdCodCustomizado;
    public static javax.swing.JTextField txtInteressado;
    private javax.swing.JTextField txtInteressadoSetorInterno;
    public static javax.swing.JTextField txtItemDoProtocolo;
    public static javax.swing.JTextField txtQuantidade;
    // End of variables declaration//GEN-END:variables
}
