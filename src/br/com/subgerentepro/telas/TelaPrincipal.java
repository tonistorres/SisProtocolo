package br.com.subgerentepro.telas;

import br.com.subgerentepro.backup.SistemaBackup;
import br.com.subgerentepro.consultas.ViewGeradorDocsProtocolo;
import br.com.subgerentepro.dao.InfoControleConexaoDAO;

import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import static br.com.subgerentepro.telas.TelaBancoEmpresa.cbPerfilCliente;
import br.com.subgerentepro.telas.fluxo.FluxoSistemaGenericoTFD;
import br.com.subgerentepro.telas.fluxo.FluxoTesourariaTFD;
import br.com.subgerentepro.thread.MinhaThred;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Thread.sleep;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author DaTorres
 */
public class TelaPrincipal extends javax.swing.JFrame {

    br.com.subgerentepro.telas.TelaUsuario formUsuario;
    br.com.subgerentepro.telas.TelaEstados formEstados;
    br.com.subgerentepro.telas.TelaCidades formCidades;
    br.com.subgerentepro.telas.TelaBairro formBairros;
    br.com.subgerentepro.telas.TelaBancoEmpresa formBancoEmpresa;
    br.com.subgerentepro.telas.TelaEmpresas formEmpresas;

    br.com.subgerentepro.telas.TelaTutorTFD formTutor;
    br.com.subgerentepro.telas.TelaBancoTutor formBancoTutor;
    br.com.subgerentepro.telas.TelaDepartamento formDepartamento;
    br.com.subgerentepro.telas.TelaItensProtocolo formItemProtocolo;
    br.com.subgerentepro.backup.SistemaBackup formBackup;
    br.com.subgerentepro.telas.TelaCadFuncionarios formFuncionarios;
    br.com.subgerentepro.telas.TelaPessoasOutros formPessoasOutros;
    br.com.subgerentepro.telas.TelaProtocDocsDepSecretarias formProtocolarDepartamentosSecretarias;
    br.com.subgerentepro.telas.TelaProtocDocsTFD formProtDocsTFD;
    br.com.subgerentepro.telas.TelaProtocDocsEmpresas formProtDocsEmpresas;
    br.com.subgerentepro.telas.TelaProtocDocsPessoasOutros formProtDocsPessoasOutros;
    br.com.subgerentepro.telas.TelaSetorTramiteInterno formSetorTramiteInterno;
    br.com.subgerentepro.telas.TelaFluxograma formSobre;
    br.com.subgerentepro.telas.TelaTFD formTFD;

    //TESTE DE CONECTIVIDADE COM BANCO DE DADOS [[[[[PRIMERIO CRONOMETRO]]]]
    //Documentação:https://www.youtube.com/watch?v=LoKQvAQpL3w
    //************************************************************
    // inicializando variáveis do tipo int e estáticas
    static int milissegundos = 0;
    static int segundos = 0;
    static int minutos = 0;
    static int horas = 0;
    static boolean estado = true;

    InfoControleConexaoDAO controle = new InfoControleConexaoDAO();

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

    private void metodoStartTesteConexao() {

        estado = true;

        Thread t;
        t = new Thread() {
            public void run() {

                for (;;) {
                    if (estado == true) {

                        try {
                            sleep(1);

                            if (milissegundos > 1000) {
                                milissegundos = 0;
                                segundos++;

                            }

                            if (segundos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos++;

                                if (minutos == 10 || minutos == 30 || minutos == 50 || minutos == 59) {

                                    buscarInforConexao();

                                    int maxConexaoNoServidor = Integer.parseInt(lblMaxServidor.getText());
                                    int maxDeConexaoPorUsuario = Integer.parseInt(lblMaxUsuario.getText());
                                    int conexoesAbertasInstate = Integer.parseInt(lblConexoesAtivas.getText());

                                    if (conexoesAbertasInstate <= ((int) maxDeConexaoPorUsuario / 2)) {
                                        painelInfoComunic.setBackground(Color.WHITE);
                                        PanelInformacoesAdicionais.setBackground(Color.WHITE);
                                        //     MinhaThred tFecharOuReconectar = new MinhaThred("tarefa1", 1000);

                                    }
                                    if ((conexoesAbertasInstate > (int) maxDeConexaoPorUsuario / 2) && (conexoesAbertasInstate <= maxDeConexaoPorUsuario - 5)) {
                                        painelInfoComunic.setBackground(Color.YELLOW);
                                        PanelInformacoesAdicionais.setBackground(Color.YELLOW);
                                        //    MinhaThred tFecharOuReconectar = new MinhaThred("tarefa1", 1000);
                                    }
                                    if (maxConexaoNoServidor == 0) {
                                        painelInfoComunic.setBackground(Color.RED);
                                        PanelInformacoesAdicionais.setBackground(Color.RED);
                                        TelaPrincipal.lblSaindoSistemaPorFaltaConexao.setVisible(true);
                                        mnPrincipal.setBackground(Color.red);
                                        //criei uma instancia da minha Classe MinhaThread                                        
                                        MinhaThred tFecharOuReconectar = new MinhaThred("tarefa1", 1000);

                                    }

                                }

                            }

                            if (minutos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos = 0;
                                horas++;
                            }

                            lblMilissegundos.setText(" : " + milissegundos);
                            milissegundos++;
                            lblSegundos.setText(" : " + segundos);
                            lblMinuto.setText(" : " + minutos);
                            lblHora.setText(" : " + horas);

                        } catch (Exception e) {

                        }

                    } else {
                        break;
                    }
                }

            }

        };
        t.start();
    }

    public TelaPrincipal() {
        initComponents();
        personalizarFrontEnd();
        metodoStartTesteConexao();
        buscarInforConexao();

        //alert estado critico sem conexao 
        lblAlertaCritical.setVisible(false);
        painelAlertaCritico.setVisible(false);
        lblSaindoSistemaPorFaltaConexao.setVisible(false);
       // this.setExtendedState(MAXIMIZED_BOTH);// propriedade para maximizar tela tanto na vertical quanto na horizontal Canal:https://www.youtube.com/watch?v=-Y2rpyIWj9c
        // Desabilitar o botão fechar de um JFrame
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);//desabilita o botão fechar da TelaPrincipal 
    }

    private void personalizarFrontEnd() {

        //MENU CADASTROS
        itmnCadastrarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/empresa.png")));
        itmnPessoasTFD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/paciente.png")));
        itmnFuncionarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/funcionario.png")));
        itmnOutros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/trump.png")));

        // botoesBarraFerramenta();
        itmnProtocEmpresas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/protocoloEmpresa.png")));
        itmnProtocTFD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/protocolarTFD.png")));
        itmnProtocDepartamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/protocolarFuncionarios.png")));
        itmnProtocOutros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/protocolarPessoasOutros.png")));
        painelInferior.setBackground(new Color(9, 81, 107));
        lblVerificacao.setForeground(Color.WHITE);

        //painel inferior 
        painelInferior.setVisible(false);
        lblVerificacao.setVisible(false);
        barraProgresso.setVisible(false);

        //barra status
        lblImagemData1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/status/data.png")));
        lblImagemHora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/paciente.png")));
        //capturandoData();
        criarDataHoraBarraStatus();

        //setando incone de usuario e data
        lblImagemData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/Date.png")));
        progressBarraPesquisa.setIndeterminate(true);

        //personalizando informações de Maquina 
        lblRepositorioCPU.setForeground(Color.WHITE);
        lblRepositorioHD.setForeground(Color.WHITE);
        lblCPU.setForeground(Color.WHITE);
        lblHD.setForeground(Color.WHITE);
        painelMiniMaquia.setBackground(new Color(9, 81, 107));
    }

    private void botoesBarraFerramenta() {
//
//        //CRIAR UMA CLASSE QUE SERÁ RESPONSAVEL POR EXCUTAR MEUS BOTOES
//        //EXECUTAR EVENTOS 
//        //https://www.youtube.com/watch?v=juVANshV2Eo
        Thehandler handler = new Thehandler();
//
//        //ADICIONANDO AOS BOTOES AÇOES QUE SERAO TRATADAS PELO ACTIONLISLISTENER
//        btnCadEmpresa.addActionListener(handler);
        btnCadFuncionario.addActionListener(handler);
//        btnFuncionario.addActionListener(handler);
//        btnPessoasOutros.addActionListener(handler);
//        btnProtocolarEmpresa.addActionListener(handler);
//        btnProtocolarTFD.addActionListener(handler);
//        btnProtocolarDepartamentos.addActionListener(handler);
//        btnProtocolarPessoasOutros.addActionListener(handler);
//        btnHelp.addActionListener(handler);
//        btnExit.addActionListener(handler);

    }

    private void criarDataHoraBarraStatus() {
        //https://www.youtube.com/watch?v=55CgbuWnmNc
        Date dataDoSistema = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        lblStatusData.setText(formato.format(dataDoSistema));

        Font f = new Font("Tahoma", Font.BOLD, 14);
        lblStatusData.setForeground(Color.RED);
        lblStatusData.setFont(f);

        Timer timer = new Timer(1000, new hora());
        timer.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DeskTop = new javax.swing.JDesktopPane();
        painelBarraFerramenta = new javax.swing.JPanel();
        BarraFerramentas = new javax.swing.JToolBar();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        lblEspaco1 = new javax.swing.JLabel();
        btnCadEmpresa = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnCadTFD = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnCadFuncionario = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnPessoasOutros = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        lblEspaco2 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JToolBar.Separator();
        btnProtocolarEmpresas = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        btnProtocolarTFD = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        btnProtocolarFuncionarios = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        btnProtocolarPessoas = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JToolBar.Separator();
        lblEspaco3 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JToolBar.Separator();
        btnHelp = new javax.swing.JButton();
        jSeparator14 = new javax.swing.JToolBar.Separator();
        btnExit = new javax.swing.JButton();
        jSeparator15 = new javax.swing.JToolBar.Separator();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        painelMiniMaquia = new javax.swing.JPanel();
        lblCPU = new javax.swing.JLabel();
        lblRepositorioCPU = new javax.swing.JLabel();
        lblHD = new javax.swing.JLabel();
        lblRepositorioHD = new javax.swing.JLabel();
        PainelPrincipal = new javax.swing.JPanel();
        carregador1 = new br.com.subgerentepro.telas.Carregador();
        painelInferior = new javax.swing.JPanel();
        barraProgresso = new javax.swing.JProgressBar();
        PanelInformacoesAdicionais = new javax.swing.JPanel();
        lblDataSistema = new javax.swing.JLabel();
        lblUsuarioLogado = new javax.swing.JLabel();
        lblImagemUser = new javax.swing.JLabel();
        lblImagemData = new javax.swing.JLabel();
        lblPerfil = new javax.swing.JLabel();
        lblNomeCompletoUsuario = new javax.swing.JLabel();
        painelInfoComunic = new javax.swing.JPanel();
        rtlMaximoConexaoServidor = new javax.swing.JLabel();
        lblMaxServidor = new javax.swing.JLabel();
        rtlUsuarioMaximoConexao = new javax.swing.JLabel();
        lblMaxUsuario = new javax.swing.JLabel();
        rtlNumeroConexoesAtivas = new javax.swing.JLabel();
        lblConexoesAtivas = new javax.swing.JLabel();
        painelCronometro = new javax.swing.JPanel();
        lblHora = new javax.swing.JLabel();
        lblMinuto = new javax.swing.JLabel();
        lblSegundos = new javax.swing.JLabel();
        lblMilissegundos = new javax.swing.JLabel();
        painelAlertaCritico = new javax.swing.JPanel();
        lblAlertaCritical = new javax.swing.JLabel();
        lblSaindoSistemaPorFaltaConexao = new javax.swing.JLabel();
        painelBarraStatus = new javax.swing.JPanel();
        lblStatusHora = new javax.swing.JLabel();
        lblImagemHora = new javax.swing.JLabel();
        lblStatusData = new javax.swing.JLabel();
        lblImagemData1 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        lblVerificacao = new javax.swing.JLabel();
        mnPrincipal = new javax.swing.JMenuBar();
        mnCadastros = new javax.swing.JMenu();
        itmnUsuarios = new javax.swing.JMenuItem();
        itmnEstados = new javax.swing.JMenuItem();
        itmnCidades = new javax.swing.JMenuItem();
        itmnBairros = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuEmpresas = new javax.swing.JMenu();
        itmnCadastrarEmpresa = new javax.swing.JMenuItem();
        itemBancoEmpresa = new javax.swing.JMenuItem();
        menuTFD = new javax.swing.JMenu();
        itmnPessoasTFD = new javax.swing.JMenuItem();
        itmnTutorTFD = new javax.swing.JMenuItem();
        menuBancoTFD = new javax.swing.JMenuItem();
        itmnFuncionarios = new javax.swing.JMenuItem();
        itmnOutros = new javax.swing.JMenuItem();
        itmnDepartamentos = new javax.swing.JMenuItem();
        itmnItensDoProtocolo = new javax.swing.JMenuItem();
        itmnSetorTramiteInterno = new javax.swing.JMenuItem();
        mnTesouraria = new javax.swing.JMenu();
        itmnAutenticarFluxo = new javax.swing.JMenuItem();
        mnProtocolar = new javax.swing.JMenu();
        itmnProtocEmpresas = new javax.swing.JMenuItem();
        itmnProtocTFD = new javax.swing.JMenuItem();
        itmnProtocDepartamentos = new javax.swing.JMenuItem();
        itmnProtocOutros = new javax.swing.JMenuItem();
        mnConsultas = new javax.swing.JMenu();
        itmnFluxoProcessoGeneric = new javax.swing.JMenuItem();
        itmRegistroProtocolados = new javax.swing.JMenuItem();
        mnRelarorios = new javax.swing.JMenu();
        itmnRelatorioEmpresasLicitadas = new javax.swing.JMenuItem();
        itmnRelatorioBairros = new javax.swing.JMenuItem();
        mnFerramentas = new javax.swing.JMenu();
        itmnFazerBackup = new javax.swing.JMenuItem();
        mnAjuda = new javax.swing.JMenu();
        itmnSobre = new javax.swing.JMenuItem();
        mnSair = new javax.swing.JMenu();
        itmnSairSistema = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SisTFD");
        setBackground(java.awt.Color.white);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        DeskTop.setBackground(new java.awt.Color(255, 255, 255));

        BarraFerramentas.setRollover(true);
        BarraFerramentas.add(jSeparator3);

        lblEspaco1.setText("                          ");
        BarraFerramentas.add(lblEspaco1);

        btnCadEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/empresa.png"))); // NOI18N
        btnCadEmpresa.setToolTipText("CADASTRAR EMPRESAS (LICITADAS E NÃO LICITADAS)");
        btnCadEmpresa.setFocusable(false);
        btnCadEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCadEmpresa.setPreferredSize(new java.awt.Dimension(25, 25));
        btnCadEmpresa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCadEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadEmpresaActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnCadEmpresa);
        BarraFerramentas.add(jSeparator2);

        btnCadTFD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/paciente.png"))); // NOI18N
        btnCadTFD.setToolTipText("CADASTRAR PACIENTES TFD");
        btnCadTFD.setFocusable(false);
        btnCadTFD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCadTFD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCadTFD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadTFDActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnCadTFD);
        BarraFerramentas.add(jSeparator4);

        btnCadFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/funcionario.png"))); // NOI18N
        btnCadFuncionario.setToolTipText("CADASTRAR FUNCIONARIOS (DEPARTAMENTOS INTERNOS )");
        btnCadFuncionario.setFocusable(false);
        btnCadFuncionario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCadFuncionario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCadFuncionario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnCadFuncionarioFocusGained(evt);
            }
        });
        btnCadFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadFuncionarioActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnCadFuncionario);
        BarraFerramentas.add(jSeparator6);

        btnPessoasOutros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/menuOutros.png"))); // NOI18N
        btnPessoasOutros.setToolTipText("CADASTRAR PESSOAS/OUTROS");
        btnPessoasOutros.setFocusable(false);
        btnPessoasOutros.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPessoasOutros.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPessoasOutros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPessoasOutrosActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnPessoasOutros);
        BarraFerramentas.add(jSeparator7);

        lblEspaco2.setText("                          ");
        BarraFerramentas.add(lblEspaco2);
        BarraFerramentas.add(jSeparator12);

        btnProtocolarEmpresas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/protocoloEmpresa.png"))); // NOI18N
        btnProtocolarEmpresas.setToolTipText("PROTOCOLAR EMPRESAS (DOCS INTERNOS E EXTERNOS)");
        btnProtocolarEmpresas.setFocusable(false);
        btnProtocolarEmpresas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProtocolarEmpresas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BarraFerramentas.add(btnProtocolarEmpresas);
        BarraFerramentas.add(jSeparator8);

        btnProtocolarTFD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/protocolarTFD.png"))); // NOI18N
        btnProtocolarTFD.setToolTipText("PROTOCOLAR TFD(DOCS INTERNOS)");
        btnProtocolarTFD.setFocusable(false);
        btnProtocolarTFD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProtocolarTFD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProtocolarTFD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProtocolarTFDActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnProtocolarTFD);
        BarraFerramentas.add(jSeparator9);

        btnProtocolarFuncionarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/protocolarFuncionarios.png"))); // NOI18N
        btnProtocolarFuncionarios.setToolTipText("PROTOCOLAR DOCS INTERNOS  ");
        btnProtocolarFuncionarios.setFocusable(false);
        btnProtocolarFuncionarios.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProtocolarFuncionarios.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BarraFerramentas.add(btnProtocolarFuncionarios);
        BarraFerramentas.add(jSeparator10);

        btnProtocolarPessoas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/protocolarPessoasOutros.png"))); // NOI18N
        btnProtocolarPessoas.setToolTipText("PROTOCOLAR PESSOAS/OUTROS(DOCS EXTERNOS)");
        btnProtocolarPessoas.setFocusable(false);
        btnProtocolarPessoas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProtocolarPessoas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BarraFerramentas.add(btnProtocolarPessoas);
        BarraFerramentas.add(jSeparator11);

        lblEspaco3.setText("                             ");
        BarraFerramentas.add(lblEspaco3);
        BarraFerramentas.add(jSeparator13);

        btnHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/menuhelp.png"))); // NOI18N
        btnHelp.setToolTipText("Abrir a Tela Sobre o Sistema");
        btnHelp.setFocusable(false);
        btnHelp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHelp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnHelp);
        BarraFerramentas.add(jSeparator14);

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/menuexit.png"))); // NOI18N
        btnExit.setToolTipText("Sair do Sistema");
        btnExit.setFocusable(false);
        btnExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnExit);
        BarraFerramentas.add(jSeparator15);

        progressBarraPesquisa.setPreferredSize(new java.awt.Dimension(25, 14));
        BarraFerramentas.add(progressBarraPesquisa);

        lblCPU.setText("CPU:");

        lblRepositorioCPU.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lblRepositorioCPU.setText("_____________");

        lblHD.setText("HD:");

        lblRepositorioHD.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        lblRepositorioHD.setText("____________");

        javax.swing.GroupLayout painelMiniMaquiaLayout = new javax.swing.GroupLayout(painelMiniMaquia);
        painelMiniMaquia.setLayout(painelMiniMaquiaLayout);
        painelMiniMaquiaLayout.setHorizontalGroup(
            painelMiniMaquiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMiniMaquiaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCPU)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRepositorioCPU)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblHD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRepositorioHD)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        painelMiniMaquiaLayout.setVerticalGroup(
            painelMiniMaquiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMiniMaquiaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelMiniMaquiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCPU)
                    .addComponent(lblRepositorioCPU)
                    .addComponent(lblHD)
                    .addComponent(lblRepositorioHD))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout painelBarraFerramentaLayout = new javax.swing.GroupLayout(painelBarraFerramenta);
        painelBarraFerramenta.setLayout(painelBarraFerramentaLayout);
        painelBarraFerramentaLayout.setHorizontalGroup(
            painelBarraFerramentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBarraFerramentaLayout.createSequentialGroup()
                .addComponent(BarraFerramentas, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelMiniMaquia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelBarraFerramentaLayout.setVerticalGroup(
            painelBarraFerramentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBarraFerramentaLayout.createSequentialGroup()
                .addGroup(painelBarraFerramentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(painelMiniMaquia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelBarraFerramentaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(BarraFerramentas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        PainelPrincipal.setBackground(java.awt.Color.white);

        carregador1.setPreferredSize(new java.awt.Dimension(1320, 480));
        carregador1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelInferior.setBackground(java.awt.Color.white);
        painelInferior.setForeground(java.awt.Color.white);
        painelInferior.setOpaque(false);
        painelInferior.setPreferredSize(new java.awt.Dimension(1280, 20));
        painelInferior.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        carregador1.add(painelInferior, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1530, 1000, -1));

        barraProgresso.setBackground(new java.awt.Color(255, 255, 255));
        carregador1.add(barraProgresso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 840, 20));

        PanelInformacoesAdicionais.setBackground(new java.awt.Color(255, 255, 255));
        PanelInformacoesAdicionais.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações:", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lblDataSistema.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblDataSistema.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDataSistema.setText("Data");

        lblUsuarioLogado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuarioLogado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuarioLogado.setText("Login");
        lblUsuarioLogado.setPreferredSize(new java.awt.Dimension(31, 15));

        lblImagemUser.setBackground(new java.awt.Color(255, 102, 102));
        lblImagemUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblImagemData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblPerfil.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPerfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPerfil.setText("Perfil");

        lblNomeCompletoUsuario.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lblNomeCompletoUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNomeCompletoUsuario.setText("Usuario");

        painelInfoComunic.setBackground(java.awt.Color.white);
        painelInfoComunic.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Info da Comunicação:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        rtlMaximoConexaoServidor.setText("Servidor Máx Conexao:");

        lblMaxServidor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        rtlUsuarioMaximoConexao.setText("Usuário Máx Conexão:");

        lblMaxUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        rtlNumeroConexoesAtivas.setText("Numero de Conexões Ativas:");

        lblConexoesAtivas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        painelCronometro.setBackground(java.awt.Color.white);
        painelCronometro.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controle Conexão:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10))); // NOI18N

        lblHora.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblHora.setForeground(new java.awt.Color(204, 153, 0));
        lblHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHora.setText("00:");

        lblMinuto.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblMinuto.setForeground(new java.awt.Color(204, 153, 0));
        lblMinuto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMinuto.setText("00:");

        lblSegundos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblSegundos.setForeground(new java.awt.Color(204, 153, 0));
        lblSegundos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSegundos.setText("00:");

        lblMilissegundos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblMilissegundos.setForeground(java.awt.Color.red);
        lblMilissegundos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMilissegundos.setText("00");

        javax.swing.GroupLayout painelCronometroLayout = new javax.swing.GroupLayout(painelCronometro);
        painelCronometro.setLayout(painelCronometroLayout);
        painelCronometroLayout.setHorizontalGroup(
            painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCronometroLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMilissegundos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelCronometroLayout.setVerticalGroup(
            painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblSegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblMilissegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout painelInfoComunicLayout = new javax.swing.GroupLayout(painelInfoComunic);
        painelInfoComunic.setLayout(painelInfoComunicLayout);
        painelInfoComunicLayout.setHorizontalGroup(
            painelInfoComunicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoComunicLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInfoComunicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelInfoComunicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(rtlMaximoConexaoServidor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMaxServidor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMaxUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rtlUsuarioMaximoConexao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rtlNumeroConexoesAtivas, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                    .addComponent(lblConexoesAtivas, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(painelCronometro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        painelInfoComunicLayout.setVerticalGroup(
            painelInfoComunicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoComunicLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rtlMaximoConexaoServidor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMaxServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(rtlUsuarioMaximoConexao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMaxUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rtlNumeroConexoesAtivas, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblConexoesAtivas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelCronometro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelInformacoesAdicionaisLayout = new javax.swing.GroupLayout(PanelInformacoesAdicionais);
        PanelInformacoesAdicionais.setLayout(PanelInformacoesAdicionaisLayout);
        PanelInformacoesAdicionaisLayout.setHorizontalGroup(
            PanelInformacoesAdicionaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInformacoesAdicionaisLayout.createSequentialGroup()
                .addGroup(PanelInformacoesAdicionaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelInformacoesAdicionaisLayout.createSequentialGroup()
                        .addGroup(PanelInformacoesAdicionaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImagemData, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImagemUser, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelInformacoesAdicionaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNomeCompletoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDataSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblUsuarioLogado, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)))
                    .addGroup(PanelInformacoesAdicionaisLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(painelInfoComunic, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        PanelInformacoesAdicionaisLayout.setVerticalGroup(
            PanelInformacoesAdicionaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInformacoesAdicionaisLayout.createSequentialGroup()
                .addGroup(PanelInformacoesAdicionaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagemUser, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelInformacoesAdicionaisLayout.createSequentialGroup()
                        .addComponent(lblUsuarioLogado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPerfil)
                        .addGap(0, 0, 0)
                        .addComponent(lblNomeCompletoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(PanelInformacoesAdicionaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagemData, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelInformacoesAdicionaisLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblDataSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelInfoComunic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 58, Short.MAX_VALUE))
        );

        carregador1.add(PanelInformacoesAdicionais, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 0, 220, 510));

        lblAlertaCritical.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/alert.png"))); // NOI18N

        javax.swing.GroupLayout painelAlertaCriticoLayout = new javax.swing.GroupLayout(painelAlertaCritico);
        painelAlertaCritico.setLayout(painelAlertaCriticoLayout);
        painelAlertaCriticoLayout.setHorizontalGroup(
            painelAlertaCriticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlertaCriticoLayout.createSequentialGroup()
                .addComponent(lblAlertaCritical, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        painelAlertaCriticoLayout.setVerticalGroup(
            painelAlertaCriticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelAlertaCriticoLayout.createSequentialGroup()
                .addComponent(lblAlertaCritical, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        carregador1.add(painelAlertaCritico, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 280, 340, 170));

        lblSaindoSistemaPorFaltaConexao.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        lblSaindoSistemaPorFaltaConexao.setForeground(java.awt.Color.white);
        lblSaindoSistemaPorFaltaConexao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        carregador1.add(lblSaindoSistemaPorFaltaConexao, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 280, 350, 40));

        painelBarraStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        painelBarraStatus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblStatusHora.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStatusHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusHora.setText("statusHora");
        painelBarraStatus.add(lblStatusHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, -1, 20));
        painelBarraStatus.add(lblImagemHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 20, 20));

        lblStatusData.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStatusData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusData.setText("statusData");
        painelBarraStatus.add(lblStatusData, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, -1, 20));
        painelBarraStatus.add(lblImagemData1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, 20, 20));

        carregador1.add(painelBarraStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 840, 23));
        carregador1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 310, -1, -1));

        lblVerificacao.setBackground(new java.awt.Color(204, 204, 204));
        lblVerificacao.setText("verificacao...");
        carregador1.add(lblVerificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 240, -1));

        javax.swing.GroupLayout PainelPrincipalLayout = new javax.swing.GroupLayout(PainelPrincipal);
        PainelPrincipal.setLayout(PainelPrincipalLayout);
        PainelPrincipalLayout.setHorizontalGroup(
            PainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(carregador1, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
        );
        PainelPrincipalLayout.setVerticalGroup(
            PainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(carregador1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout DeskTopLayout = new javax.swing.GroupLayout(DeskTop);
        DeskTop.setLayout(DeskTopLayout);
        DeskTopLayout.setHorizontalGroup(
            DeskTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(DeskTopLayout.createSequentialGroup()
                .addComponent(painelBarraFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        DeskTopLayout.setVerticalGroup(
            DeskTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeskTopLayout.createSequentialGroup()
                .addComponent(painelBarraFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(PainelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        DeskTop.setLayer(painelBarraFerramenta, javax.swing.JLayeredPane.DEFAULT_LAYER);
        DeskTop.setLayer(PainelPrincipal, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mnCadastros.setText("Cadastros");
        mnCadastros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCadastrosActionPerformed(evt);
            }
        });

        itmnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuUser.png"))); // NOI18N
        itmnUsuarios.setText("Usuários");
        itmnUsuarios.setEnabled(false);
        itmnUsuarios.setName(""); // NOI18N
        itmnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnUsuariosActionPerformed(evt);
            }
        });
        mnCadastros.add(itmnUsuarios);

        itmnEstados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuEstados.png"))); // NOI18N
        itmnEstados.setText("Estados");
        itmnEstados.setEnabled(false);
        itmnEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnEstadosActionPerformed(evt);
            }
        });
        mnCadastros.add(itmnEstados);

        itmnCidades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menucidades.png"))); // NOI18N
        itmnCidades.setText("Cidades");
        itmnCidades.setEnabled(false);
        itmnCidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnCidadesActionPerformed(evt);
            }
        });
        mnCadastros.add(itmnCidades);

        itmnBairros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuBairros.png"))); // NOI18N
        itmnBairros.setText("Bairros");
        itmnBairros.setEnabled(false);
        itmnBairros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnBairrosActionPerformed(evt);
            }
        });
        mnCadastros.add(itmnBairros);
        mnCadastros.add(jSeparator1);

        menuEmpresas.setText("Empresas (Licitadas / Não Licitadas)");
        menuEmpresas.setEnabled(false);

        itmnCadastrarEmpresa.setText("Cadastrar Empresa");
        itmnCadastrarEmpresa.setEnabled(false);
        itmnCadastrarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnCadastrarEmpresaActionPerformed(evt);
            }
        });
        menuEmpresas.add(itmnCadastrarEmpresa);

        itemBancoEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuBanco.png"))); // NOI18N
        itemBancoEmpresa.setText("Cadastrar Bancos");
        itemBancoEmpresa.setEnabled(false);
        itemBancoEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBancoEmpresaActionPerformed(evt);
            }
        });
        menuEmpresas.add(itemBancoEmpresa);

        mnCadastros.add(menuEmpresas);

        menuTFD.setText("TFD (Saúde/Específico)");
        menuTFD.setEnabled(false);

        itmnPessoasTFD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuTFD.png"))); // NOI18N
        itmnPessoasTFD.setText("Paciente TFD");
        itmnPessoasTFD.setEnabled(false);
        itmnPessoasTFD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnPessoasTFDActionPerformed(evt);
            }
        });
        menuTFD.add(itmnPessoasTFD);

        itmnTutorTFD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuFuncionario.png"))); // NOI18N
        itmnTutorTFD.setText("Tutor Paciente TFD");
        itmnTutorTFD.setEnabled(false);
        itmnTutorTFD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnTutorTFDActionPerformed(evt);
            }
        });
        menuTFD.add(itmnTutorTFD);

        menuBancoTFD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuBanco.png"))); // NOI18N
        menuBancoTFD.setText("Banco Tutor TFD");
        menuBancoTFD.setEnabled(false);
        menuBancoTFD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBancoTFDActionPerformed(evt);
            }
        });
        menuTFD.add(menuBancoTFD);

        mnCadastros.add(menuTFD);

        itmnFuncionarios.setText("Funcionários(Setores Internos)");
        itmnFuncionarios.setEnabled(false);
        itmnFuncionarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnFuncionariosActionPerformed(evt);
            }
        });
        mnCadastros.add(itmnFuncionarios);

        itmnOutros.setText("Pessoas/Outros(Setores Externos)");
        itmnOutros.setEnabled(false);
        itmnOutros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnOutrosActionPerformed(evt);
            }
        });
        mnCadastros.add(itmnOutros);

        itmnDepartamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuDepartamento.png"))); // NOI18N
        itmnDepartamentos.setText("Departamentos");
        itmnDepartamentos.setEnabled(false);
        itmnDepartamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnDepartamentosActionPerformed(evt);
            }
        });
        mnCadastros.add(itmnDepartamentos);

        itmnItensDoProtocolo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuItensProtocolo.png"))); // NOI18N
        itmnItensDoProtocolo.setText("Itens do Protocolo");
        itmnItensDoProtocolo.setEnabled(false);
        itmnItensDoProtocolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnItensDoProtocoloActionPerformed(evt);
            }
        });
        mnCadastros.add(itmnItensDoProtocolo);

        itmnSetorTramiteInterno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/setorTramiteInterno.png"))); // NOI18N
        itmnSetorTramiteInterno.setText("Setores de Tramite Interno Protocolo");
        itmnSetorTramiteInterno.setEnabled(false);
        itmnSetorTramiteInterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnSetorTramiteInternoActionPerformed(evt);
            }
        });
        mnCadastros.add(itmnSetorTramiteInterno);

        mnPrincipal.add(mnCadastros);

        mnTesouraria.setText("Tesouraria");

        itmnAutenticarFluxo.setText("Autenticar Fluxo");
        itmnAutenticarFluxo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnAutenticarFluxoActionPerformed(evt);
            }
        });
        mnTesouraria.add(itmnAutenticarFluxo);

        mnPrincipal.add(mnTesouraria);

        mnProtocolar.setText("Protocolar");

        itmnProtocEmpresas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuEmpresa.png"))); // NOI18N
        itmnProtocEmpresas.setText("Empresas(Licitadas/Não Licitadas/Externa a Estrutura Prefeitura)");
        itmnProtocEmpresas.setEnabled(false);
        itmnProtocEmpresas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnProtocEmpresasActionPerformed(evt);
            }
        });
        mnProtocolar.add(itmnProtocEmpresas);

        itmnProtocTFD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuTFD.png"))); // NOI18N
        itmnProtocTFD.setText("TFD(Paciente)");
        itmnProtocTFD.setEnabled(false);
        itmnProtocTFD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnProtocTFDActionPerformed(evt);
            }
        });
        mnProtocolar.add(itmnProtocTFD);

        itmnProtocDepartamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuSetorPublico.png"))); // NOI18N
        itmnProtocDepartamentos.setText("Departamentos/Secretarias(Pertencente a Estrutura Prefeitura)");
        itmnProtocDepartamentos.setEnabled(false);
        itmnProtocDepartamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnProtocDepartamentosActionPerformed(evt);
            }
        });
        mnProtocolar.add(itmnProtocDepartamentos);

        itmnProtocOutros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuOutros.png"))); // NOI18N
        itmnProtocOutros.setText("Pessoas/Outros(Externa a Estrutura Prefeitura)");
        itmnProtocOutros.setEnabled(false);
        itmnProtocOutros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnProtocOutrosActionPerformed(evt);
            }
        });
        mnProtocolar.add(itmnProtocOutros);

        mnPrincipal.add(mnProtocolar);

        mnConsultas.setText("Consultas");

        itmnFluxoProcessoGeneric.setText("Fluxo do Processo");
        itmnFluxoProcessoGeneric.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnFluxoProcessoGenericActionPerformed(evt);
            }
        });
        mnConsultas.add(itmnFluxoProcessoGeneric);

        itmRegistroProtocolados.setText("Registros Protocolados");
        itmRegistroProtocolados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmRegistroProtocoladosActionPerformed(evt);
            }
        });
        mnConsultas.add(itmRegistroProtocolados);

        mnPrincipal.add(mnConsultas);

        mnRelarorios.setText("Relatórios");

        itmnRelatorioEmpresasLicitadas.setText("Empresas Licitadas");
        itmnRelatorioEmpresasLicitadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnRelatorioEmpresasLicitadasActionPerformed(evt);
            }
        });
        mnRelarorios.add(itmnRelatorioEmpresasLicitadas);

        itmnRelatorioBairros.setText("Bairros Cadastrados");
        itmnRelatorioBairros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnRelatorioBairrosActionPerformed(evt);
            }
        });
        mnRelarorios.add(itmnRelatorioBairros);

        mnPrincipal.add(mnRelarorios);

        mnFerramentas.setText("Ferramentas");

        itmnFazerBackup.setText("Fazer Backup");
        itmnFazerBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnFazerBackupActionPerformed(evt);
            }
        });
        mnFerramentas.add(itmnFazerBackup);

        mnPrincipal.add(mnFerramentas);

        mnAjuda.setText("Ajuda");
        mnAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAjudaActionPerformed(evt);
            }
        });

        itmnSobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuHelp.png"))); // NOI18N
        itmnSobre.setText("Sobre");
        itmnSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnSobreActionPerformed(evt);
            }
        });
        mnAjuda.add(itmnSobre);

        mnPrincipal.add(mnAjuda);

        mnSair.setText("Sair");

        itmnSairSistema.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/menu/menuSairSistema.png"))); // NOI18N
        itmnSairSistema.setText("Sair do Sistema");
        itmnSairSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnSairSistemaActionPerformed(evt);
            }
        });
        mnSair.add(itmnSairSistema);

        mnPrincipal.add(mnSair);

        setJMenuBar(mnPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(DeskTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(DeskTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1101, 620));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buscarInforConexao() {

        try {
            //se o metodo recebe for igual a zero não houve conexão com o banco 
            int recebeServidor = controle.contarMaxConexaoServidor();
            int recebeUsuario = controle.contarMaxConexaoUsuario();

            if (recebeUsuario == 0) {
                recebeUsuario = 50;
                lblMaxUsuario.setText(String.valueOf(recebeUsuario));
            }

            if (recebeUsuario != 0) {
                lblMaxUsuario.setText(String.valueOf(recebeUsuario));
            }

            int recebeAtivasMomento = controle.contarNumeroConexaoAbertasNoMomento();
            lblMaxServidor.setText(String.valueOf(recebeServidor));

            lblConexoesAtivas.setText(String.valueOf(recebeAtivasMomento));
        } catch (PersistenciaException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI: Sem Conectividade,\n"
                    + "Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO,\n"
                    + "BANCO DE DADOS HOSPEDADO Sobrecarregado de Conexões Ativas\n"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
        }
    }


    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

        /**
         * Esse evento executa o codigo quando o formulário for aberto.
         *
         */
        Date data = new Date(); // criando um objeto do tipo Date para isso necessito instanciar a classe 
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);// capturar a data do sistema 
        lblDataSistema.setText(formatador.format(data));//adicionar a data capturada no meu label na área de trabalho 
        lblDataSistema.setForeground(Color.ORANGE);
    }//GEN-LAST:event_formWindowActivated

    private void acaoSairSistema() {

        /*Evento ao ser clicado executa código*/
        int sair = JOptionPane.showConfirmDialog(null, "Deseja Sair do Sistema?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (sair == JOptionPane.YES_OPTION) {

            painelInferior.setVisible(true);
            barraProgresso.setVisible(true);
            lblVerificacao.setVisible(true);

            new Thread() {

                public void run() {

                    for (int i = 0; i < 101; i++) {

                        try {

                            sleep(25);
                            barraProgresso.setValue(i);

                            if (barraProgresso.getValue() <= 5) {
                                lblVerificacao.setText("Inicializando barra de progresso");

                                painelInferior.setVisible(true);
                                barraProgresso.setVisible(true);
                                lblVerificacao.setVisible(true);

                            } else if (barraProgresso.getValue() <= 15) {
                                lblVerificacao.setText("15% Descarregado");

                            } else if (barraProgresso.getValue() <= 25) {

                                lblVerificacao.setText("25% Descarregado");

                            } else if (barraProgresso.getValue() <= 35) {
                                lblVerificacao.setText("35% Descarregado");

                            } else if (barraProgresso.getValue() <= 45) {
                                lblVerificacao.setText("45% Descarregado");

                            } else if (barraProgresso.getValue() <= 55) {
                                lblVerificacao.setText("55% Descarregado");

                            } else if (barraProgresso.getValue() <= 65) {
                                lblVerificacao.setText("65% Descarregado");

                            } else if (barraProgresso.getValue() <= 75) {
                                lblVerificacao.setText("75% Descarregado");

                            } else if (barraProgresso.getValue() <= 85) {
                                lblVerificacao.setText("85% Descarregado");

                            } else if (barraProgresso.getValue() <= 95) {
                                lblVerificacao.setText("95% Descarregado");

                            } else {
                                lblVerificacao.setText("Encerrado com sucesso!");
                                System.exit(0);//sair do sistema
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Desculpe! Erro no moduto de Descarregamento\n" + e.getMessage());

                        }

                    }
                }
            }.start();// iniciando a Thread

        }

    }
    private void itmnSairSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnSairSistemaActionPerformed
        acaoSairSistema();
    }//GEN-LAST:event_itmnSairSistemaActionPerformed

    public void acaoAbriTelaAjuda() {
        if (estaFechado(formSobre)) {
            formSobre = new TelaFluxograma();
            DeskTop.add(formSobre).setLocation(8, 40);
            formSobre.setTitle("Sobre");
            formSobre.show();
        } else {

            formSobre.toFront();
            formSobre.show();

        }

    }


    private void itmnSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnSobreActionPerformed
        acaoAbriTelaAjuda();

    }//GEN-LAST:event_itmnSobreActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        painelInferior.setVisible(false);
        barraProgresso.setVisible(false);
        lblVerificacao.setVisible(false);
    }//GEN-LAST:event_formWindowOpened

    private void itmnBairrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnBairrosActionPerformed
        if (estaFechado(formBairros)) {
            formBairros = new TelaBairro();
            DeskTop.add(formBairros).setLocation(8, 40);
            formBairros.setTitle("Bairros");
            formBairros.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formBairros.toFront();
            formBairros.show();

        }
    }//GEN-LAST:event_itmnBairrosActionPerformed

    private void itmnCidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnCidadesActionPerformed

        if (estaFechado(formCidades)) {
            formCidades = new TelaCidades();
            DeskTop.add(formCidades).setLocation(8, 40);
            formCidades.setTitle("Cidades");
            formCidades.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formCidades.toFront();
            formCidades.show();

        }

    }//GEN-LAST:event_itmnCidadesActionPerformed

    private void itmnEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnEstadosActionPerformed

        if (estaFechado(formEstados)) {
            formEstados = new TelaEstados();
            DeskTop.add(formEstados).setLocation(8, 40);
            formEstados.setTitle("Estados");
            formEstados.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formEstados.toFront();
            formEstados.show();

        }
    }//GEN-LAST:event_itmnEstadosActionPerformed

    private void itmnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnUsuariosActionPerformed
        if (estaFechado(formUsuario)) {
            formUsuario = new TelaUsuario();
            DeskTop.add(formUsuario).setLocation(8, 40);
            formUsuario.setTitle("Usuario");
            formUsuario.setVisible(true);
        } else {
            formUsuario.toFront();
            formUsuario.setVisible(true);

        }

    }//GEN-LAST:event_itmnUsuariosActionPerformed

    private void abrirBancoEmpresa() {

        if (estaFechado(formBancoEmpresa)) {
            formBancoEmpresa = new TelaBancoEmpresa();
            DeskTop.add(formBancoEmpresa).setLocation(1, 5);
            formBancoEmpresa.setTitle("Banco/Empresa");
            formBancoEmpresa.setVisible(true);
            cbPerfilCliente.setSelectedItem("EMPRESA");
        } else {
            formBancoEmpresa.toFront();
            formBancoEmpresa.setVisible(true);
            cbPerfilCliente.setSelectedItem("EMPRESA");

        }

    }
    private void itemBancoEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBancoEmpresaActionPerformed
        abrirBancoEmpresa();

    }//GEN-LAST:event_itemBancoEmpresaActionPerformed

    private void abrirEmpresa() {
        if (estaFechado(formEmpresas)) {
            formEmpresas = new TelaEmpresas();
            DeskTop.add(formEmpresas).setLocation(8, 40);
            formEmpresas.setTitle("Cadastrar/Empresas");
            formEmpresas.setVisible(true);
        } else {
            formEmpresas.toFront();
            formEmpresas.setVisible(true);

        }

    }
    private void itmnCadastrarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnCadastrarEmpresaActionPerformed

        abrirEmpresa();
    }//GEN-LAST:event_itmnCadastrarEmpresaActionPerformed

    private void menuBancoTFDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBancoTFDActionPerformed

        if (estaFechado(formBancoTutor)) {
            formBancoTutor = new TelaBancoTutor();
            DeskTop.add(formBancoTutor).setLocation(1, 5);
            formBancoTutor.setTitle("Banco/Tutor");
            formBancoTutor.setVisible(true);

        } else {
            formBancoTutor.toFront();
            formBancoTutor.setVisible(true);

        }
    }//GEN-LAST:event_menuBancoTFDActionPerformed

    private void itmnRelatorioEmpresasLicitadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnRelatorioEmpresasLicitadasActionPerformed

//        ConexaoUtil conecta = new ConexaoUtil();
//        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
//
//        if (recebeConexao == true) {
//            RelatoriosDAO relatorios = new RelatoriosDAO();
//            relatorios.gerarRelatorioListarTodasEmpresas();
//
//        } else {
//            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
//                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO\n"
//                    + "Erro Método Chama Relatório Empresa\n"
//                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
//
//        }

    }//GEN-LAST:event_itmnRelatorioEmpresasLicitadasActionPerformed

    private void itmnRelatorioBairrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnRelatorioBairrosActionPerformed
//        ConexaoUtil conecta = new ConexaoUtil();
//        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
//
//        if (recebeConexao == true) {
//            RelatoriosDAO relatorios = new RelatoriosDAO();
//            relatorios.gerarRelatorioListarTodasBairros();
//
//        } else {
//            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
//                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO\n"
//                    + "Erro Método Chama Relatório Empresa\n"
//                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
//
//        }
//

    }//GEN-LAST:event_itmnRelatorioBairrosActionPerformed

    private void itmnTutorTFDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnTutorTFDActionPerformed
        //formTutor
        if (estaFechado(formTutor)) {
            formTutor = new TelaTutorTFD();
            DeskTop.add(formTutor).setLocation(1, 5);
            formTutor.setTitle("Tutor");
            formTutor.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formTutor.toFront();
            formTutor.show();

        }


    }//GEN-LAST:event_itmnTutorTFDActionPerformed
    
    private void itmnPessoasTFDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnPessoasTFDActionPerformed
        if (estaFechado(formTFD)) {
            formTFD = new TelaTFD();
            DeskTop.add(formTFD).setLocation(8, 40);
            formTFD.setTitle("Paciente TFD");
            formTFD.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formTFD.toFront();
            formTFD.show();

        }

    }//GEN-LAST:event_itmnPessoasTFDActionPerformed

    private void itmnDepartamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnDepartamentosActionPerformed

        if (estaFechado(formDepartamento)) {
            formDepartamento = new TelaDepartamento();
            DeskTop.add(formDepartamento).setLocation(8, 40);
            formDepartamento.setTitle("Departamentos");
            formDepartamento.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formDepartamento.toFront();
            formDepartamento.show();

        }

    }//GEN-LAST:event_itmnDepartamentosActionPerformed
    private void abrirItensDoProtocolo() {
        if (estaFechado(formItemProtocolo)) {
            formItemProtocolo = new TelaItensProtocolo();
            DeskTop.add(formItemProtocolo).setLocation(8, 40);
            formItemProtocolo.setTitle("Itens de Protocolo");
            formItemProtocolo.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formItemProtocolo.toFront();
            formItemProtocolo.show();

        }
    }
    private void itmnItensDoProtocoloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnItensDoProtocoloActionPerformed
        abrirItensDoProtocolo();
    }//GEN-LAST:event_itmnItensDoProtocoloActionPerformed

    private void itmnFazerBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnFazerBackupActionPerformed
        if (estaFechado(formBackup)) {
            formBackup = new SistemaBackup();
            DeskTop.add(formBackup).setLocation(1, 5);
            formBackup.setTitle("Sub-Sistema de Backup");
            formBackup.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formBackup.toFront();
            formBackup.show();

        }
    }//GEN-LAST:event_itmnFazerBackupActionPerformed
//formFuncionarios
    private void itmnFuncionariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnFuncionariosActionPerformed
        if (estaFechado(formFuncionarios)) {
            try {
                formFuncionarios = new TelaCadFuncionarios();
                DeskTop.add(formFuncionarios).setLocation(8, 40);
                formFuncionarios.setTitle("Funcionários");
                formFuncionarios.show();
            } catch (PersistenciaException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Camda  GUI:\n" + ex.getMessage());
            }

        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formFuncionarios.toFront();
            formFuncionarios.show();

        }
    }//GEN-LAST:event_itmnFuncionariosActionPerformed

    private void itmnOutrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnOutrosActionPerformed
        if (estaFechado(formPessoasOutros)) {
            formPessoasOutros = new TelaPessoasOutros();
            DeskTop.add(formPessoasOutros).setLocation(8, 40);
            formPessoasOutros.setTitle("Pessoas/Outros");
            formPessoasOutros.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formPessoasOutros.toFront();
            formPessoasOutros.show();

        }
    }//GEN-LAST:event_itmnOutrosActionPerformed

    private void sairAplicacao() {

        /*Evento ao ser clicado executa código*/
        int sair = JOptionPane.showConfirmDialog(null, "Deseja Sair do Sistema?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (sair == JOptionPane.YES_OPTION) {

            painelInferior.setVisible(true);
            barraProgresso.setVisible(true);
            lblVerificacao.setVisible(true);

            new Thread() {

                public void run() {

                    for (int i = 0; i < 101; i++) {

                        try {

                            sleep(25);
                            barraProgresso.setValue(i);

                            if (barraProgresso.getValue() <= 5) {
                                lblVerificacao.setText("Inicializando barra de progresso");

                                painelInferior.setVisible(true);
                                barraProgresso.setVisible(true);
                                lblVerificacao.setVisible(true);

                            } else if (barraProgresso.getValue() <= 15) {
                                lblVerificacao.setText("15% Descarregado");

                            } else if (barraProgresso.getValue() <= 25) {

                                lblVerificacao.setText("25% Descarregado");

                            } else if (barraProgresso.getValue() <= 35) {
                                lblVerificacao.setText("35% Descarregado");

                            } else if (barraProgresso.getValue() <= 45) {
                                lblVerificacao.setText("45% Descarregado");

                            } else if (barraProgresso.getValue() <= 55) {
                                lblVerificacao.setText("55% Descarregado");

                            } else if (barraProgresso.getValue() <= 65) {
                                lblVerificacao.setText("65% Descarregado");

                            } else if (barraProgresso.getValue() <= 75) {
                                lblVerificacao.setText("75% Descarregado");

                            } else if (barraProgresso.getValue() <= 85) {
                                lblVerificacao.setText("85% Descarregado");

                            } else if (barraProgresso.getValue() <= 95) {
                                lblVerificacao.setText("95% Descarregado");

                            } else {
                                lblVerificacao.setText("Encerrado com sucesso!");
                                System.exit(0);//sair do sistema
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Desculpe! Erro no moduto de Descarregamento\n" + e.getMessage());

                        }

                    }
                }
            }.start();// iniciando a Thread

        }

    }

    private void itmnProtocOutrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnProtocOutrosActionPerformed
        if (estaFechado(formProtDocsPessoasOutros)) {
            formProtDocsPessoasOutros = new TelaProtocDocsPessoasOutros();
            DeskTop.add(formProtDocsPessoasOutros).setLocation(1, 5);
            formProtDocsPessoasOutros.setTitle("Protocolar Docs Externo (Presidentes de Associações, Vereadores, Pastores, Camara Municipal)");
            formProtDocsPessoasOutros.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formProtDocsPessoasOutros.toFront();
            formProtDocsPessoasOutros.show();

        }
    }//GEN-LAST:event_itmnProtocOutrosActionPerformed

    private void itmnProtocEmpresasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnProtocEmpresasActionPerformed

        //MENU EMPRESAS 
        if (estaFechado(formProtDocsEmpresas)) {
            formProtDocsEmpresas = new TelaProtocDocsEmpresas();
            DeskTop.add(formProtDocsEmpresas).setLocation(1, 5);
            formProtDocsEmpresas.setTitle("Empresas Licitadas / Não Licitadas (Protocolar Docs Interno ou Externo a Prefeitura(Empresas))");
            formProtDocsEmpresas.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formProtDocsEmpresas.toFront();
            formProtDocsEmpresas.show();

        }
    }//GEN-LAST:event_itmnProtocEmpresasActionPerformed

    private void itmnProtocTFDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnProtocTFDActionPerformed
        //MENU TFD 

        if (estaFechado(formProtDocsTFD)) {
            formProtDocsTFD = new TelaProtocDocsTFD();
            DeskTop.add(formProtDocsTFD).setLocation(1, 40);
            formProtDocsTFD.setTitle("Protocolar TFD");
            formProtDocsTFD.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formProtDocsTFD.toFront();
            formProtDocsTFD.show();

        }
    }//GEN-LAST:event_itmnProtocTFDActionPerformed

    private void itmnProtocDepartamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnProtocDepartamentosActionPerformed

        //MENU DEPARTAMENTO E SECRETARIAS 
        if (estaFechado(formProtocolarDepartamentosSecretarias)) {
            formProtocolarDepartamentosSecretarias = new TelaProtocDocsDepSecretarias();
            DeskTop.add(formProtocolarDepartamentosSecretarias).setLocation(1, 5);
            formProtocolarDepartamentosSecretarias.setTitle("Departamento e Secretarias (Protocolos de Documentos Internos Prefeitura)");
            formProtocolarDepartamentosSecretarias.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formProtocolarDepartamentosSecretarias.toFront();
            formProtocolarDepartamentosSecretarias.show();

        }
    }//GEN-LAST:event_itmnProtocDepartamentosActionPerformed

    private void itmnSetorTramiteInternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnSetorTramiteInternoActionPerformed
//formSetorTramiteInterno

        //BOTAO DEPARTAMENTO 
        if (estaFechado(formSetorTramiteInterno)) {
            formSetorTramiteInterno = new TelaSetorTramiteInterno();
            DeskTop.add(formSetorTramiteInterno).setLocation(8, 40);
            formSetorTramiteInterno.setTitle("Cadastro de Setores Tramite Interno");
            formSetorTramiteInterno.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formSetorTramiteInterno.toFront();
            formSetorTramiteInterno.show();

        }


    }//GEN-LAST:event_itmnSetorTramiteInternoActionPerformed

    private void mnAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAjudaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnAjudaActionPerformed

    private void mnCadastrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCadastrosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnCadastrosActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        acaoSairSistema();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        acaoAbriTelaAjuda();
    }//GEN-LAST:event_btnHelpActionPerformed

    private void btnCadTFDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadTFDActionPerformed
        if (estaFechado(formTFD)) {
            formTFD = new TelaTFD();
            DeskTop.add(formTFD).setLocation(8, 40);
            formTFD.setTitle("Paciente TFD");
            formTFD.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formTFD.toFront();
            formTFD.show();

        }

    }//GEN-LAST:event_btnCadTFDActionPerformed

    private void btnCadFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadFuncionarioActionPerformed

        if (estaFechado(formFuncionarios)) {

            try {

                formFuncionarios = new TelaCadFuncionarios();
                DeskTop.add(formFuncionarios).setLocation(8, 40);
                formFuncionarios.setTitle("Funcionários");
                formFuncionarios.show();
            } catch (PersistenciaException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Camda  GUI:\n" + ex.getMessage());
            }

        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formFuncionarios.toFront();
            formFuncionarios.show();

        }
    }//GEN-LAST:event_btnCadFuncionarioActionPerformed

    private void btnPessoasOutrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPessoasOutrosActionPerformed
        if (estaFechado(formPessoasOutros)) {
            formPessoasOutros = new TelaPessoasOutros();
            DeskTop.add(formPessoasOutros).setLocation(8, 40);
            formPessoasOutros.setTitle("Pessoas/Outros");
            formPessoasOutros.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formPessoasOutros.toFront();
            formPessoasOutros.show();

        }
    }//GEN-LAST:event_btnPessoasOutrosActionPerformed

    private void btnCadFuncionarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCadFuncionarioFocusGained

    }//GEN-LAST:event_btnCadFuncionarioFocusGained

    private void btnCadEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadEmpresaActionPerformed
        abrirEmpresa();
    }//GEN-LAST:event_btnCadEmpresaActionPerformed

    private void btnProtocolarTFDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProtocolarTFDActionPerformed
        if (estaFechado(formProtDocsTFD)) {
            formProtDocsTFD = new TelaProtocDocsTFD();
            DeskTop.add(formProtDocsTFD).setLocation(1, 40);
            formProtDocsTFD.setTitle("Protocolar TFD");
            formProtDocsTFD.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formProtDocsTFD.toFront();
            formProtDocsTFD.show();

        }
    }//GEN-LAST:event_btnProtocolarTFDActionPerformed
    br.com.subgerentepro.consultas.ViewGeradorDocsProtocolo formCnsRegistrosProtocolados;
    private void itmRegistroProtocoladosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmRegistroProtocoladosActionPerformed
        if (estaFechado(formCnsRegistrosProtocolados)) {
            try {
                formCnsRegistrosProtocolados = new ViewGeradorDocsProtocolo();
            } catch (PersistenciaException ex) {
                ex.printStackTrace();
            }
            DeskTop.add(formCnsRegistrosProtocolados).setLocation(1, 40);
            formCnsRegistrosProtocolados.setTitle("Gerar Recibo e Capa do Processo");
            formCnsRegistrosProtocolados.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formCnsRegistrosProtocolados.toFront();
            formCnsRegistrosProtocolados.show();

        }
    }//GEN-LAST:event_itmRegistroProtocoladosActionPerformed
    br.com.subgerentepro.telas.fluxo.FluxoSistemaGenericoTFD formFluxoSistemaGenerioTFD;
    private void itmnFluxoProcessoGenericActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnFluxoProcessoGenericActionPerformed
        if (estaFechado(formFluxoSistemaGenerioTFD)) {
            formFluxoSistemaGenerioTFD = new FluxoSistemaGenericoTFD();
            DeskTop.add(formFluxoSistemaGenerioTFD).setLocation(1, 40);
            formFluxoSistemaGenerioTFD.setTitle("Fluxo do Processo TFD");
            formFluxoSistemaGenerioTFD.show();

        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formFluxoSistemaGenerioTFD.toFront();
            formFluxoSistemaGenerioTFD.show();

        }
    }//GEN-LAST:event_itmnFluxoProcessoGenericActionPerformed
    
    br.com.subgerentepro.telas.fluxo.FluxoTesourariaTFD formFluxoTesourariaTFD;
    private void itmnAutenticarFluxoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnAutenticarFluxoActionPerformed
        if (estaFechado(formFluxoTesourariaTFD)) {
            formFluxoTesourariaTFD = new FluxoTesourariaTFD();
            DeskTop.add(formFluxoTesourariaTFD).setLocation(1, 40);
            formFluxoTesourariaTFD.setTitle("Autenticação Fluxlo Tesouraria");
            formFluxoTesourariaTFD.show();

        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formFluxoTesourariaTFD.toFront();
            formFluxoTesourariaTFD.show();

        }

    }//GEN-LAST:event_itmnAutenticarFluxoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    class hora implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Calendar now = Calendar.getInstance();
            lblStatusHora.setText(String.format("%1$tH:%1$tM:%1$tS", now));

            Font f = new Font("Tahoma", Font.BOLD, 14);
            lblStatusHora.setForeground(Color.RED);
            lblStatusHora.setFont(f);

        }

    }

//    //https://www.youtube.com/watch?v=juVANshV2Eo   
//    //criando uma classe dentro de outra para implementar as 
//    //ações dos meus botões da Barra de ferramentasa 
//    //Como é uma classe dentro da outra ela herda tudo que a
//    //classe mae tem ...
    private class Thehandler implements ActionListener {
//
//        //dentro dessa classe iremo implementar um método nativo do java 
//        //na verdade é a unica assinatura dessa classe que devemos implementar

        public void actionPerformed(ActionEvent evt) {
//            //BOTAO CADASTRO EMPRESA 

        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JToolBar BarraFerramentas;
    public static javax.swing.JDesktopPane DeskTop;
    public static javax.swing.JPanel PainelPrincipal;
    public static javax.swing.JPanel PanelInformacoesAdicionais;
    public static javax.swing.JProgressBar barraProgresso;
    public static javax.swing.JButton btnCadEmpresa;
    public static javax.swing.JButton btnCadFuncionario;
    public static javax.swing.JButton btnCadTFD;
    public static javax.swing.JButton btnExit;
    public static javax.swing.JButton btnHelp;
    public static javax.swing.JButton btnPessoasOutros;
    public static javax.swing.JButton btnProtocolarEmpresas;
    public static javax.swing.JButton btnProtocolarFuncionarios;
    public static javax.swing.JButton btnProtocolarPessoas;
    public static javax.swing.JButton btnProtocolarTFD;
    public static br.com.subgerentepro.telas.Carregador carregador1;
    public static javax.swing.JMenuItem itemBancoEmpresa;
    public static javax.swing.JMenuItem itmRegistroProtocolados;
    public static javax.swing.JMenuItem itmnAutenticarFluxo;
    public static javax.swing.JMenuItem itmnBairros;
    public static javax.swing.JMenuItem itmnCadastrarEmpresa;
    public static javax.swing.JMenuItem itmnCidades;
    public static javax.swing.JMenuItem itmnDepartamentos;
    public static javax.swing.JMenuItem itmnEstados;
    public static javax.swing.JMenuItem itmnFazerBackup;
    public static javax.swing.JMenuItem itmnFluxoProcessoGeneric;
    public static javax.swing.JMenuItem itmnFuncionarios;
    public static javax.swing.JMenuItem itmnItensDoProtocolo;
    public static javax.swing.JMenuItem itmnOutros;
    public static javax.swing.JMenuItem itmnPessoasTFD;
    public static javax.swing.JMenuItem itmnProtocDepartamentos;
    public static javax.swing.JMenuItem itmnProtocEmpresas;
    public static javax.swing.JMenuItem itmnProtocOutros;
    public static javax.swing.JMenuItem itmnProtocTFD;
    public static javax.swing.JMenuItem itmnRelatorioBairros;
    public static javax.swing.JMenuItem itmnRelatorioEmpresasLicitadas;
    public static javax.swing.JMenuItem itmnSairSistema;
    public static javax.swing.JMenuItem itmnSetorTramiteInterno;
    public static javax.swing.JMenuItem itmnSobre;
    public static javax.swing.JMenuItem itmnTutorTFD;
    public static javax.swing.JMenuItem itmnUsuarios;
    public static javax.swing.JPopupMenu.Separator jSeparator1;
    public static javax.swing.JToolBar.Separator jSeparator10;
    public static javax.swing.JToolBar.Separator jSeparator11;
    public static javax.swing.JToolBar.Separator jSeparator12;
    public static javax.swing.JToolBar.Separator jSeparator13;
    public static javax.swing.JToolBar.Separator jSeparator14;
    public static javax.swing.JToolBar.Separator jSeparator15;
    public static javax.swing.JToolBar.Separator jSeparator2;
    public static javax.swing.JToolBar.Separator jSeparator3;
    public static javax.swing.JToolBar.Separator jSeparator4;
    public static javax.swing.JSeparator jSeparator5;
    public static javax.swing.JToolBar.Separator jSeparator6;
    public static javax.swing.JToolBar.Separator jSeparator7;
    public static javax.swing.JToolBar.Separator jSeparator8;
    public static javax.swing.JToolBar.Separator jSeparator9;
    public static javax.swing.JLabel lblAlertaCritical;
    public static javax.swing.JLabel lblCPU;
    public static javax.swing.JLabel lblConexoesAtivas;
    public static javax.swing.JLabel lblDataSistema;
    public static javax.swing.JLabel lblEspaco1;
    public static javax.swing.JLabel lblEspaco2;
    public static javax.swing.JLabel lblEspaco3;
    public static javax.swing.JLabel lblHD;
    public static javax.swing.JLabel lblHora;
    public static javax.swing.JLabel lblImagemData;
    public static javax.swing.JLabel lblImagemData1;
    public static javax.swing.JLabel lblImagemHora;
    public static javax.swing.JLabel lblImagemUser;
    public static javax.swing.JLabel lblMaxServidor;
    public static javax.swing.JLabel lblMaxUsuario;
    public static javax.swing.JLabel lblMilissegundos;
    public static javax.swing.JLabel lblMinuto;
    public static javax.swing.JLabel lblNomeCompletoUsuario;
    public static javax.swing.JLabel lblPerfil;
    public static javax.swing.JLabel lblRepositorioCPU;
    public static javax.swing.JLabel lblRepositorioHD;
    public static javax.swing.JLabel lblSaindoSistemaPorFaltaConexao;
    public static javax.swing.JLabel lblSegundos;
    public static javax.swing.JLabel lblStatusData;
    public static javax.swing.JLabel lblStatusHora;
    public static javax.swing.JLabel lblUsuarioLogado;
    public static javax.swing.JLabel lblVerificacao;
    public static javax.swing.JMenuItem menuBancoTFD;
    public static javax.swing.JMenu menuEmpresas;
    public static javax.swing.JMenu menuTFD;
    public static javax.swing.JMenu mnAjuda;
    public static javax.swing.JMenu mnCadastros;
    public static javax.swing.JMenu mnConsultas;
    public static javax.swing.JMenu mnFerramentas;
    public static javax.swing.JMenuBar mnPrincipal;
    public static javax.swing.JMenu mnProtocolar;
    public static javax.swing.JMenu mnRelarorios;
    public static javax.swing.JMenu mnSair;
    public static javax.swing.JMenu mnTesouraria;
    public static javax.swing.JPanel painelAlertaCritico;
    public static javax.swing.JPanel painelBarraFerramenta;
    public static javax.swing.JPanel painelBarraStatus;
    public static javax.swing.JPanel painelCronometro;
    public static javax.swing.JPanel painelInferior;
    public static javax.swing.JPanel painelInfoComunic;
    public static javax.swing.JPanel painelMiniMaquia;
    public static javax.swing.JProgressBar progressBarraPesquisa;
    public static javax.swing.JLabel rtlMaximoConexaoServidor;
    public static javax.swing.JLabel rtlNumeroConexoesAtivas;
    public static javax.swing.JLabel rtlUsuarioMaximoConexao;
    // End of variables declaration//GEN-END:variables
}
