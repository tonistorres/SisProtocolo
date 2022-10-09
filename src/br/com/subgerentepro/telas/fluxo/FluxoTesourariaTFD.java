package br.com.subgerentepro.telas.fluxo;

import br.com.subgerentepro.telas.*;
import br.com.subgerentepro.bo.ProtocoloTFDBO;
import br.com.subgerentepro.dao.FluxoTFDDAO;
import br.com.subgerentepro.dao.ItensDoProtocoloTFDDAO;
import br.com.subgerentepro.dao.ItensFluxoMovimentoTFDDAO;
import br.com.subgerentepro.dao.SetorTramiteInternoDAO;
import br.com.subgerentepro.dao.UsuarioDAO;
import br.com.subgerentepro.dto.FluxoTFDDTO;
import br.com.subgerentepro.dto.ItensDoProtocoloTFDDTO;
import br.com.subgerentepro.dto.ItensFluxoMovimentoTFDDTO;
import br.com.subgerentepro.dto.SetorTramiteInternoDTO;
import br.com.subgerentepro.dto.UsuarioDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import static br.com.subgerentepro.telas.TelaPrincipal.lblNomeCompletoUsuario;
import static br.com.subgerentepro.telas.TelaPrincipal.lblPerfil;
import static br.com.subgerentepro.telas.TelaPrincipal.lblRepositorioCPU;
import static br.com.subgerentepro.telas.TelaPrincipal.lblRepositorioHD;
import static br.com.subgerentepro.telas.TelaPrincipal.lblStatusData;
import static br.com.subgerentepro.telas.TelaPrincipal.lblStatusHora;
import static br.com.subgerentepro.telas.TelaPrincipal.lblUsuarioLogado;
import br.com.subgerentepro.util.PINTAR_TABELA_FUNCIONARIO;
import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Dã Torres
 */
public class FluxoTesourariaTFD extends javax.swing.JInternalFrame {

    FluxoTFDDAO fluxoDAO = new FluxoTFDDAO();
    FluxoTFDDTO fluxoDTO = new FluxoTFDDTO();

    ItensFluxoMovimentoTFDDTO itensFluxoDTO = new ItensFluxoMovimentoTFDDTO();
    ItensFluxoMovimentoTFDDAO itensFluxoDAO = new ItensFluxoMovimentoTFDDAO();

    ItensDoProtocoloTFDDAO itensDoProtocolo = new ItensDoProtocoloTFDDAO();
    UsuarioDTO usuarioDTO = new UsuarioDTO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    SetorTramiteInternoDAO setorInternoDAO = new SetorTramiteInternoDAO();
    SetorTramiteInternoDTO setorInernoDTO = new SetorTramiteInternoDTO();

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

    public FluxoTesourariaTFD() {
        initComponents();
        aoCarregarForm();
        //*********************************************************************
        //OBSERVAÇÃO IMPORTANTE: As 3(três) Linhas de códigos abaixo devem  //
        //esta na seguinte sequência abaixo respectivamente                // 
        //Iremos criar dois métodos que colocará estilo em nossas tabelas //***************************
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0//
        tabela.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 
        //    t.ver_tabela(tabela);                                                                        //
        //********************************************************************************************
        //CANAL:MamaNs - Java Swing UI - Design jTable       
        //clicar e a tabela mudar a linha de cor indicando que a linha cliccada é aquela 
        //https://www.youtube.com/watch?v=RXhMdUPk12k
        //*******************************************************************************************
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(new Color(32, 136, 203));
        tabela.getTableHeader().setForeground(new Color(255, 255, 255));
        tabela.setRowHeight(25);

        //*************************************************************************
        //Java - Desktop - INSERINDO COR NA LINHA DO JTABLE ( Color jTable ) # 1
        //https://www.youtube.com/watch?v=ibR1-Sp4S1k
        //***************************************************************************
        // tabela.setDefaultRenderer(Object.class,new PINTAR_TABELA_FUNCIONARIO());
        //--------------------------------------------------------------------   
    }

    private void caixaCobinacaoFluxoProtocolo() {

        //se na caixa de fluxo estiver nulo adicionamos os dois itens no combo box
        //caso contrário deve have os itens adicionado e não precisa adicionar
        //mais nada 
        if (cbFluxo.getSelectedItem() == null) {
            cbFluxo.addItem("RECEBEMOS DEVOLUCAO");
            cbFluxo.addItem("REEINVIANDO CORRIGIDO");
            cbFluxo.addItem("ENCAMINHANDO PARA CORRECAO");
        }
    }

    private void caixaCobinacaoFluxoContabilidade() {
        //se na caixa de fluxo estiver nulo adicionamos os dois itens no combo box
        //caso contrário deve have os itens adicionado e não precisa adicionar
        //mais nada 
        if (cbFluxo.getSelectedItem() == null) {

            cbFluxo.addItem("RECEBIDO|ENCAMINHANDO");//SECRETARIA TESOURARIA
            cbFluxo.addItem("DEVOLVENDO CORRECAO");//MOTIVO - PROTOCOLO  
        }
    }

    private void caixaCobinacaoFluxoTesouraria() {
        //se na caixa de fluxo estiver nulo adicionamos os dois itens no combo box
        //caso contrário deve have os itens adicionado e não precisa adicionar
        //mais nada 
        if (cbFluxo.getSelectedItem() == null) {

            cbFluxo.addItem("RECEBIDO|ENCAMINHANDO");//SECRETARIA TESOURARIA
            cbFluxo.addItem("DEVOLVENDO CORRECAO");//MOTIVO - PROTOCOLO  
        }
    }

    public void addRowJTable() {

        String filtrarPorIdCustomizado = txtIdCustomBuscar.getText();
        //https://www.youtube.com/watch?v=jPfKFm2Yfow
        tabela.setDefaultRenderer(Object.class, new RenderClasseBotaoTabela());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setName("idCancelar");

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<ItensFluxoMovimentoTFDDTO> list;
        try {

            list = (ArrayList<ItensFluxoMovimentoTFDDTO>) itensFluxoDAO.listarTodosParametro(filtrarPorIdCustomizado);

            Object rowData[] = new Object[8];//são 04 colunas 

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getStatusMovimentoDto();
                rowData[2] = list.get(i).getDataRegistradaDto().toString();
                rowData[3] = list.get(i).getPerfilDto();
                rowData[4] = list.get(i).getDepartamentoDestinoDto();
                rowData[5] = list.get(i).getInteressadoDestinoDto();
                rowData[6] = list.get(i).getLancamentoDto();
                rowData[7] = btnCancelar;

                model.addRow(rowData);

            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(90);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(85);
            tabela.getColumnModel().getColumn(7).setPreferredWidth(85);

        } catch (PersistenciaException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void aoCarregarForm() {
        //botao comportamento no carregamento
        this.btnSalvar.setEnabled(false);
        this.barProgressProcedimentosBackEnd.setIndeterminate(true);
        frontEnd();
        buscarPersonalizarLoginMaquina();
        cboMascaraCustomizada.requestFocus();

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

        lblCPF.setFont(f);
        lblCPF.setForeground(Color.WHITE);

    }

    private void frontEnd() {

        //Painel Paciente 
        //cor de fundo painel paciente 
        Font fPaciente = new Font("Tahoma", Font.BOLD, 9);

        painelPrincipal.setBackground(new Color(255, 255, 255));

        painelInfoPaciente.setBackground(new Color(255, 255, 255));
        //labels
        lblPacientel.setForeground(Color.BLACK);
        lblPacientel.setFont(fPaciente);

        lblRepoPaciente.setForeground(Color.BLACK);
        lblRepoPaciente.setFont(fPaciente);

        lblCartaoSUS.setForeground(Color.BLACK);
        lblCartaoSUS.setFont(fPaciente);

        lblRepoNCartaoSUS.setForeground(Color.BLACK);
        lblRepoNCartaoSUS.setFont(fPaciente);

        lblCPF.setForeground(Color.BLACK);
        lblCPF.setFont(fPaciente);

        lblRepositCPF.setForeground(Color.BLACK);
        lblRepositCPF.setFont(fPaciente);

        painelDadosUsuarioMaquina.setBackground(new Color(255, 255, 255));
        //labels
        lblLogin.setForeground(Color.BLACK);
        lblLogin.setFont(fPaciente);

        lblRepoLogin.setForeground(Color.BLACK);
        lblRepoLogin.setFont(fPaciente);

        lblHD2.setForeground(Color.BLACK);
        lblHD2.setFont(fPaciente);

        lblRepoHD.setForeground(Color.BLACK);
        lblRepoHD.setFont(fPaciente);

        lblPerfil2.setForeground(Color.BLACK);
        lblPerfil2.setFont(fPaciente);

        lblRepoPerfil.setForeground(Color.BLACK);
        lblRepoPerfil.setFont(fPaciente);

        lblCPU.setForeground(Color.BLACK);
        lblCPU.setFont(fPaciente);

        lblRepoCPU.setForeground(Color.BLACK);
        lblRepoCPU.setFont(fPaciente);

        lblUsuario.setForeground(Color.BLACK);
        lblUsuario.setFont(fPaciente);

        lblRepoUsuario.setForeground(Color.BLACK);
        lblRepoUsuario.setFont(fPaciente);

        painelDadosFluxo.setBackground(new Color(255, 255, 255));
        //labels
        lblNProtocolo.setForeground(Color.BLACK);
        lblNProtocolo.setFont(fPaciente);

        lblDadosProtocoloRepositIDCustomizado.setForeground(Color.BLACK);
        lblDadosProtocoloRepositIDCustomizado.setFont(fPaciente);

        lblDataProtocolada.setForeground(Color.BLACK);
        lblDataProtocolada.setFont(fPaciente);

        lblDadosProtocoloRepositDtProtocolada.setForeground(Color.BLACK);
        lblDadosProtocoloRepositDtProtocolada.setFont(fPaciente);

        lblDtTransf.setForeground(Color.BLACK);
        lblDtTransf.setFont(fPaciente);

        lblDadosProtocoloRepositDtTransf.setForeground(Color.BLACK);
        lblDadosProtocoloRepositDtTransf.setFont(fPaciente);

        lblDepOrigem.setForeground(Color.BLACK);
        lblDepOrigem.setFont(fPaciente);

        lblDadosProcessoTFDRepositDepOrigem.setForeground(Color.BLACK);
        lblDadosProcessoTFDRepositDepOrigem.setFont(fPaciente);

        lblInteressadoDepOrigem.setForeground(Color.BLACK);
        lblInteressadoDepOrigem.setFont(fPaciente);

        lblDadosProtocoloIntressadoOrigem.setForeground(Color.BLACK);
        lblDadosProtocoloIntressadoOrigem.setFont(fPaciente);

        lblSetorInterno.setForeground(Color.BLACK);
        lblSetorInterno.setFont(fPaciente);

        lblDadoProtocoloRepositSetorInterno.setForeground(Color.BLACK);
        lblDadoProtocoloRepositSetorInterno.setFont(fPaciente);

        lblDadosProtocoloRepositEnderecado.setForeground(Color.BLACK);
        lblDadosProtocoloRepositEnderecado.setFont(fPaciente);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        painelInfoPaciente = new javax.swing.JPanel();
        lblPacientel = new javax.swing.JLabel();
        lblRepoPaciente = new javax.swing.JLabel();
        lblCartaoSUS = new javax.swing.JLabel();
        lblRepoNCartaoSUS = new javax.swing.JLabel();
        lblCPF = new javax.swing.JLabel();
        lblRepositCPF = new javax.swing.JLabel();
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
        lblLinhaInformativa = new javax.swing.JLabel();
        lblNuvemBackEnd = new javax.swing.JLabel();
        barProgressProcedimentosBackEnd = new javax.swing.JProgressBar();
        btnSalvar = new javax.swing.JButton();
        lblConsultarProtocolo = new javax.swing.JLabel();
        txtIdCustomBuscar = new javax.swing.JFormattedTextField();
        btnBuscarIdCustom = new javax.swing.JButton();
        painelDadosFluxo = new javax.swing.JPanel();
        lblNProtocolo = new javax.swing.JLabel();
        lblDadosProtocoloRepositIDCustomizado = new javax.swing.JLabel();
        lblDataProtocolada = new javax.swing.JLabel();
        lblDadosProtocoloRepositDtProtocolada = new javax.swing.JLabel();
        lblDepOrigem = new javax.swing.JLabel();
        lblDadosProcessoTFDRepositDepOrigem = new javax.swing.JLabel();
        lblInteressadoDepOrigem = new javax.swing.JLabel();
        lblDadosProtocoloInteressadoDepOrigem = new javax.swing.JLabel();
        lblDadosProtocoloIntressadoOrigem = new javax.swing.JLabel();
        lblDtTransf = new javax.swing.JLabel();
        lblDadosProtocoloRepositDtTransf = new javax.swing.JLabel();
        lblDadosProtocoloRepositEnderecado = new javax.swing.JLabel();
        lblSetorInterno = new javax.swing.JLabel();
        lblDadoProtocoloRepositSetorInterno = new javax.swing.JLabel();
        cboMascaraCustomizada = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        cbFluxo = new javax.swing.JComboBox();
        lblFluxo = new javax.swing.JLabel();
        cbDepVinculadosFluxoTFD = new javax.swing.JComboBox();
        lblDepartamento = new javax.swing.JLabel();
        cbInteressado = new javax.swing.JComboBox();
        lblAosCuidados = new javax.swing.JLabel();
        lblBackEnd = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelPrincipal.setBackground(java.awt.Color.white);
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelInfoPaciente.setBackground(new java.awt.Color(51, 51, 255));
        painelInfoPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Paciente:"));
        painelInfoPaciente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPacientel.setText("Paciente:");
        painelInfoPaciente.add(lblPacientel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        lblRepoPaciente.setText("_______________________________________");
        painelInfoPaciente.add(lblRepoPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 240, -1));

        lblCartaoSUS.setText("NºSUS:");
        painelInfoPaciente.add(lblCartaoSUS, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        lblRepoNCartaoSUS.setText("__________________");
        painelInfoPaciente.add(lblRepoNCartaoSUS, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 120, -1));

        lblCPF.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        lblCPF.setText("CPF:");
        painelInfoPaciente.add(lblCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, -1));

        lblRepositCPF.setForeground(java.awt.Color.white);
        lblRepositCPF.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        painelInfoPaciente.add(lblRepositCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 100, 20));

        painelPrincipal.add(painelInfoPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 340, 90));

        painelDadosUsuarioMaquina.setBackground(new java.awt.Color(51, 51, 255));
        painelDadosUsuarioMaquina.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Usuário / Máquina:"));
        painelDadosUsuarioMaquina.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogin.setText("Login:");
        painelDadosUsuarioMaquina.add(lblLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 40, -1));

        lblRepoLogin.setText("____________________");
        painelDadosUsuarioMaquina.add(lblRepoLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 130, -1));

        lblPerfil2.setText("Perfil:");
        painelDadosUsuarioMaquina.add(lblPerfil2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        lblRepoPerfil.setForeground(java.awt.Color.white);
        lblRepoPerfil.setText("____________________");
        painelDadosUsuarioMaquina.add(lblRepoPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 130, 20));

        lblUsuario.setText("Usuario:");
        painelDadosUsuarioMaquina.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        lblRepoUsuario.setText("__________________");
        painelDadosUsuarioMaquina.add(lblRepoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 140, -1));

        lblHD2.setText("HD:");
        painelDadosUsuarioMaquina.add(lblHD2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        lblRepoHD.setText("___________________");
        painelDadosUsuarioMaquina.add(lblRepoHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 140, -1));

        lblCPU.setText("CPU:");
        painelDadosUsuarioMaquina.add(lblCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, -1));

        lblRepoCPU.setText("___________________");
        painelDadosUsuarioMaquina.add(lblRepoCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 140, -1));

        painelPrincipal.add(painelDadosUsuarioMaquina, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 400, 100));

        lblLinhaInformativa.setBackground(new java.awt.Color(51, 0, 255));
        lblLinhaInformativa.setBorder(javax.swing.BorderFactory.createTitledBorder("Linha Informativa:"));
        painelPrincipal.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 520, 53));

        lblNuvemBackEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        painelPrincipal.add(lblNuvemBackEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 50, 30));
        painelPrincipal.add(barProgressProcedimentosBackEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 50, 20));

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
        painelPrincipal.add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 260, 45, 45));

        lblConsultarProtocolo.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblConsultarProtocolo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/mascaraBanco.png"))); // NOI18N
        painelPrincipal.add(lblConsultarProtocolo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 7, 40, 25));

        try {
            txtIdCustomBuscar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtIdCustomBuscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIdCustomBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdCustomBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdCustomBuscarFocusLost(evt);
            }
        });
        txtIdCustomBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdCustomBuscarActionPerformed(evt);
            }
        });
        txtIdCustomBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdCustomBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdCustomBuscarKeyReleased(evt);
            }
        });
        painelPrincipal.add(txtIdCustomBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 170, 30));

        btnBuscarIdCustom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnBuscarIdCustom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnBuscarIdCustomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnBuscarIdCustomFocusLost(evt);
            }
        });
        btnBuscarIdCustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarIdCustomActionPerformed(evt);
            }
        });
        painelPrincipal.add(btnBuscarIdCustom, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 35, 32, 32));

        painelDadosFluxo.setBackground(new java.awt.Color(51, 51, 255));
        painelDadosFluxo.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Fluxo:"));
        painelDadosFluxo.setForeground(new java.awt.Color(255, 255, 255));
        painelDadosFluxo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNProtocolo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNProtocolo.setForeground(new java.awt.Color(204, 204, 204));
        lblNProtocolo.setText("N°:");
        painelDadosFluxo.add(lblNProtocolo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 25, -1, -1));

        lblDadosProtocoloRepositIDCustomizado.setForeground(java.awt.Color.white);
        lblDadosProtocoloRepositIDCustomizado.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        painelDadosFluxo.add(lblDadosProtocoloRepositIDCustomizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 80, 20));

        lblDataProtocolada.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDataProtocolada.setForeground(new java.awt.Color(204, 204, 204));
        lblDataProtocolada.setText("Dt.Prot.:");
        painelDadosFluxo.add(lblDataProtocolada, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 25, -1, -1));

        lblDadosProtocoloRepositDtProtocolada.setForeground(java.awt.Color.white);
        lblDadosProtocoloRepositDtProtocolada.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        painelDadosFluxo.add(lblDadosProtocoloRepositDtProtocolada, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 80, 20));

        lblDepOrigem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDepOrigem.setForeground(new java.awt.Color(204, 204, 204));
        lblDepOrigem.setText("Origem:");
        painelDadosFluxo.add(lblDepOrigem, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 25, -1, -1));

        lblDadosProcessoTFDRepositDepOrigem.setForeground(java.awt.Color.white);
        lblDadosProcessoTFDRepositDepOrigem.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        painelDadosFluxo.add(lblDadosProcessoTFDRepositDepOrigem, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 250, 20));

        lblInteressadoDepOrigem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblInteressadoDepOrigem.setForeground(new java.awt.Color(204, 204, 204));
        lblInteressadoDepOrigem.setText("Interessado:");
        painelDadosFluxo.add(lblInteressadoDepOrigem, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, -1, -1));
        painelDadosFluxo.add(lblDadosProtocoloInteressadoDepOrigem, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, -1, -1));

        lblDadosProtocoloIntressadoOrigem.setForeground(java.awt.Color.white);
        lblDadosProtocoloIntressadoOrigem.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        painelDadosFluxo.add(lblDadosProtocoloIntressadoOrigem, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 210, 20));

        lblDtTransf.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDtTransf.setForeground(new java.awt.Color(204, 204, 204));
        lblDtTransf.setText("Dt.Transf.:");
        painelDadosFluxo.add(lblDtTransf, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 25, -1, -1));

        lblDadosProtocoloRepositDtTransf.setForeground(java.awt.Color.white);
        lblDadosProtocoloRepositDtTransf.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        painelDadosFluxo.add(lblDadosProtocoloRepositDtTransf, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 70, 20));

        lblDadosProtocoloRepositEnderecado.setForeground(java.awt.Color.white);
        lblDadosProtocoloRepositEnderecado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDadosProtocoloRepositEnderecado.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        painelDadosFluxo.add(lblDadosProtocoloRepositEnderecado, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 50, 160, 20));

        lblSetorInterno.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSetorInterno.setForeground(new java.awt.Color(204, 204, 204));
        lblSetorInterno.setText("Interno:");
        painelDadosFluxo.add(lblSetorInterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 55, 50, -1));

        lblDadoProtocoloRepositSetorInterno.setForeground(java.awt.Color.white);
        lblDadoProtocoloRepositSetorInterno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDadoProtocoloRepositSetorInterno.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white));
        painelDadosFluxo.add(lblDadoProtocoloRepositSetorInterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 180, 20));

        painelPrincipal.add(painelDadosFluxo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 740, 80));

        cboMascaraCustomizada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", " " }));
        cboMascaraCustomizada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMascaraCustomizadaActionPerformed(evt);
            }
        });
        painelPrincipal.add(cboMascaraCustomizada, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 7, 40, 25));

        tabela.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "STATUS", "DT.REGISTRO", "PERFIL", "DESTINO", "ENDEREÇADO", "LANÇAMENTO", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabela.setRowHeight(25);
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
            tabela.getColumnModel().getColumn(0).setResizable(false);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(5).setResizable(false);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(90);
            tabela.getColumnModel().getColumn(7).setResizable(false);
            tabela.getColumnModel().getColumn(7).setPreferredWidth(85);
        }

        painelPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 760, 140));

        cbFluxo.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cbFluxo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFluxoActionPerformed(evt);
            }
        });
        painelPrincipal.add(cbFluxo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 190, 28));

        lblFluxo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblFluxo.setText("Fluxo:");
        painelPrincipal.add(lblFluxo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 50, -1));

        cbDepVinculadosFluxoTFD.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cbDepVinculadosFluxoTFD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDepVinculadosFluxoTFDActionPerformed(evt);
            }
        });
        painelPrincipal.add(cbDepVinculadosFluxoTFD, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 240, 28));

        lblDepartamento.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDepartamento.setText("Departamento/Secretaria:");
        painelPrincipal.add(lblDepartamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, -1, -1));

        cbInteressado.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        painelPrincipal.add(cbInteressado, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 250, 30));

        lblAosCuidados.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAosCuidados.setText("Aos Cuidados:");
        painelPrincipal.add(lblAosCuidados, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, 100, -1));

        lblBackEnd.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        lblBackEnd.setText("Back-End");
        painelPrincipal.add(lblBackEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, -1, -1));

        getContentPane().add(painelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarIdCustomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarIdCustomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarIdCustomActionPerformed

    private void btnBuscarIdCustomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarIdCustomFocusLost
        btnBuscarIdCustom.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnBuscarIdCustomFocusLost

    private void buscarDadosNaTbFluxoTFDOutraFonte() {
        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(lblDadosProtocoloRepositIDCustomizado.getText());

        try {

            FluxoTFDDTO retornoFluxo = fluxoDAO.buscarIdCustomizado(pesquisar);
            lblDadosProtocoloRepositIDCustomizado.setText(retornoFluxo.getIdCustomDto());//idCustomizado
            lblDadosProtocoloRepositDtProtocolada.setText(retornoFluxo.getDataProtocoloDto());//dataProtocolo
            lblDadosProcessoTFDRepositDepOrigem.setText(retornoFluxo.getDepOrigemDto());//Departamento de Origem
            lblDadosProtocoloIntressadoOrigem.setText(retornoFluxo.getInteressadoOrigemDto());// Paciente do TFD
            lblDadosProtocoloRepositEnderecado.setText(retornoFluxo.getInteressadoDestinoDto());//departamento de destino
            lblDadoProtocoloRepositSetorInterno.setText(retornoFluxo.getDepDestinoDto());//funcionario do departamento interno 
            lblDadosProtocoloRepositDtTransf.setText(retornoFluxo.getDataTransferenciaDto());//data transferencia 
            //dados paciente 
            lblRepoPaciente.setText(retornoFluxo.getNome_pacienteDto());
            //   lblRepoSexo.setText(retornoFluxo.getSexo_pacienteDto());
            //    lblRepoRua.setText(retornoFluxo.getRua_pacienteDto());
            lblRepoNCartaoSUS.setText(retornoFluxo.getnCartaoSus_pacienteDto());
            lblRepositCPF.setText(retornoFluxo.getCpf_pacienteDto());
            txtIdCustomBuscar.setText("");
            txtIdCustomBuscar.requestFocus();

            addRowTratandoDadosEnviadosTabela();
            //
        } catch (Exception e) {
            //colocar métod robo Informar erro - robo gmail    
            erroViaEmail(e.getMessage(), "buscarDadosNaTbFluxoTFD() - Camada GUI\n"
                    + "Formulario:FluxoSistemaGenerico.java");
            e.printStackTrace();
        }

    }

    private void buscarDadosNaTbFluxoTFD() {

        String pesquisar = txtIdCustomBuscar.getText();

        try {

            FluxoTFDDTO retornoFluxo = fluxoDAO.buscarIdCustomizado(pesquisar);
            lblDadosProtocoloRepositIDCustomizado.setText(retornoFluxo.getIdCustomDto());//idCustomizado
            lblDadosProtocoloRepositDtProtocolada.setText(retornoFluxo.getDataProtocoloDto());//dataProtocolo
            lblDadosProcessoTFDRepositDepOrigem.setText(retornoFluxo.getDepOrigemDto());//Departamento de Origem
            lblDadosProtocoloIntressadoOrigem.setText(retornoFluxo.getInteressadoOrigemDto());// Paciente do TFD
            lblDadosProtocoloRepositEnderecado.setText(retornoFluxo.getInteressadoDestinoDto());//departamento de destino
            lblDadoProtocoloRepositSetorInterno.setText(retornoFluxo.getDepDestinoDto());//funcionario do departamento interno 
            lblDadosProtocoloRepositDtTransf.setText(retornoFluxo.getDataTransferenciaDto());//data transferencia 
            //dados paciente 
            lblRepoPaciente.setText(retornoFluxo.getNome_pacienteDto());
            //   lblRepoSexo.setText(retornoFluxo.getSexo_pacienteDto());
            //    lblRepoRua.setText(retornoFluxo.getRua_pacienteDto());
            lblRepoNCartaoSUS.setText(retornoFluxo.getnCartaoSus_pacienteDto());
            lblRepositCPF.setText(retornoFluxo.getCpf_pacienteDto());
            //        txtIdCustomBuscar.setText("");
            txtIdCustomBuscar.requestFocus();
            //
        } catch (Exception e) {
            //colocar métod robo Informar erro - robo gmail    
            erroViaEmail(e.getMessage(), "buscarDadosNaTbFluxoTFD() - Camada GUI\n"
                    + "Formulario:FluxoSistemaGenerico.java");
            e.printStackTrace();
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

    private void addRowTratandoDadosEnviadosTabela() {

        try {

            int numeroLinhas = tabela.getRowCount();

            if (numeroLinhas > 0) {

                while (tabela.getModel().getRowCount() > 0) {
                    ((DefaultTableModel) tabela.getModel()).removeRow(0);

                }
                addRowJTable();

            } else {
                addRowJTable();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void buscarStatusTransacao() throws PersistenciaException {
        FluxoTFDDTO retorno = fluxoDAO.buscarStatusTransacao(txtIdCustomBuscar.getText());

        Font f = new Font("Tahoma", Font.BOLD, 16);
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText(retorno.getStatutsTrasacaoDto() + " DE PAGAMENTO");
        lblLinhaInformativa.setForeground(Color.RED);
        if (retorno.getStatutsTrasacaoDto().equals("PENDENTE")) {
            painelPrincipal.setBackground(new Color(255, 255, 255));

            painelInfoPaciente.setBackground(new Color(255, 255, 255));
            //labels
            lblPacientel.setForeground(Color.BLACK);
            lblRepoPaciente.setForeground(Color.RED);

            lblCartaoSUS.setForeground(Color.BLACK);
            lblRepoNCartaoSUS.setForeground(Color.RED);
            lblCPF.setForeground(Color.BLACK);
            lblRepositCPF.setForeground(Color.RED);

            painelDadosUsuarioMaquina.setBackground(new Color(255, 255, 255));
            //labels
            lblLogin.setForeground(Color.BLACK);
            lblRepoLogin.setForeground(Color.RED);
            lblHD2.setForeground(Color.BLACK);
            lblRepoHD.setForeground(Color.RED);
            lblPerfil2.setForeground(Color.BLACK);
            lblRepoPerfil.setForeground(Color.RED);
            lblCPU.setForeground(Color.BLACK);
            lblRepoCPU.setForeground(Color.RED);
            lblUsuario.setForeground(Color.BLACK);
            lblRepoUsuario.setForeground(Color.RED);

            painelDadosFluxo.setBackground(new Color(255, 255, 255));
            //labels
            lblNProtocolo.setForeground(Color.BLACK);
            lblDadosProtocoloRepositIDCustomizado.setForeground(Color.RED);
            lblDataProtocolada.setForeground(Color.BLACK);
            lblDadosProtocoloRepositDtProtocolada.setForeground(Color.RED);
            lblDtTransf.setForeground(Color.BLACK);
            lblDadosProtocoloRepositDtTransf.setForeground(Color.RED);
            lblDepOrigem.setForeground(Color.BLACK);
            lblDadosProcessoTFDRepositDepOrigem.setForeground(Color.RED);
            lblInteressadoDepOrigem.setForeground(Color.BLACK);
            lblDadosProtocoloIntressadoOrigem.setForeground(Color.RED);
            lblSetorInterno.setForeground(Color.BLACK);
             lblDadoProtocoloRepositSetorInterno.setForeground(Color.RED);
            lblDadosProtocoloRepositEnderecado.setForeground(Color.RED);
            cbFluxo.setBackground(Color.RED);
            cbDepVinculadosFluxoTFD.setBackground(Color.RED);
            cbInteressado.setBackground(Color.RED);

        } else {
            painelPrincipal.setBackground(new Color(255, 255, 255));

            painelInfoPaciente.setBackground(new Color(255, 255, 255));
            //labels
            lblPacientel.setForeground(Color.BLACK);
            lblRepoPaciente.setForeground(new Color(204, 172, 0));

            lblCartaoSUS.setForeground(Color.BLACK);
            lblRepoNCartaoSUS.setForeground(new Color(204, 172, 0));
            lblCPF.setForeground(Color.BLACK);
            lblRepositCPF.setForeground(new Color(204, 172, 0));

            painelDadosUsuarioMaquina.setBackground(new Color(255, 255, 255));
            //labels
            lblLogin.setForeground(Color.BLACK);
            lblRepoLogin.setForeground(new Color(204, 172, 0));
            lblHD2.setForeground(Color.BLACK);
            lblRepoHD.setForeground(new Color(204, 172, 0));
            lblPerfil2.setForeground(Color.BLACK);
            lblRepoPerfil.setForeground(new Color(204, 172, 0));
            lblCPU.setForeground(Color.BLACK);
            lblRepoCPU.setForeground(new Color(204, 172, 0));
            lblUsuario.setForeground(Color.BLACK);
            lblRepoUsuario.setForeground(new Color(204, 172, 0));

            painelDadosFluxo.setBackground(new Color(255, 255, 255));
            //labels
            lblNProtocolo.setForeground(Color.BLACK);
            lblDadosProtocoloRepositIDCustomizado.setForeground(new Color(204, 172, 0));
            lblDataProtocolada.setForeground(Color.BLACK);
            lblDadosProtocoloRepositDtProtocolada.setForeground(new Color(204, 172, 0));
            lblDtTransf.setForeground(Color.BLACK);
            lblDadosProtocoloRepositDtTransf.setForeground(new Color(204, 172, 0));
            lblDepOrigem.setForeground(Color.BLACK);
            lblDadosProcessoTFDRepositDepOrigem.setForeground(new Color(204, 172, 0));
            lblInteressadoDepOrigem.setForeground(Color.BLACK);
            lblDadosProtocoloIntressadoOrigem.setForeground(new Color(204, 172, 0));
            lblSetorInterno.setForeground(Color.BLACK);
            lblDadoProtocoloRepositSetorInterno.setForeground(new Color(204, 172, 0));
            lblDadosProtocoloRepositEnderecado.setForeground(new Color(204, 172, 0));
            cbFluxo.setBackground(new Color(204, 172, 0));
            cbDepVinculadosFluxoTFD.setBackground(new Color(204, 172, 0));
            cbInteressado.setBackground(new Color(204, 172, 0));

            Font f2 = new Font("Tahoma", Font.BOLD, 16);
            lblLinhaInformativa.setFont(f2);
            lblLinhaInformativa.setText(retorno.getStatutsTrasacaoDto() + " COM SUCESSO.");
            lblLinhaInformativa.setForeground(new Color(204, 172, 0));
        }
    }

    private void btnBuscarIdCustomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarIdCustomFocusGained

        btnBuscarIdCustom.setBackground(Color.YELLOW);

        fluxoDTO.setIdCustomDto(this.txtIdCustomBuscar.getText());

        try {
            //verificar se o processo encontra-se cadastrado

            boolean retornoVerifcaDuplicidade = fluxoDAO.verificaDuplicidade(fluxoDTO);

            if (retornoVerifcaDuplicidade) {
                if (!txtIdCustomBuscar.getText().equals("  -TFD/    ")) {

                    buscarDadosNaTbFluxoTFD();
                    //primeiro limpa a tabela caso exita registros ja setados 
                    //só entao coloca os registros novos 
                    addRowTratandoDadosEnviadosTabela();

                    //status da transação
                    buscarStatusTransacao();

                    cbDepVinculadosFluxoTFD.setEnabled(true);
                    cbFluxo.setEnabled(true);
                    cbInteressado.setEnabled(true);
                    btnSalvar.setEnabled(true);

                    if (lblRepoPerfil.getText().equalsIgnoreCase("protocolo")) {

                        cbDepVinculadosFluxoTFD.setEnabled(true);
                        cbFluxo.setEnabled(true);
                        caixaCobinacaoFluxoProtocolo();
                        cbInteressado.setEnabled(false);
                    }

                    if (lblRepoPerfil.getText().equalsIgnoreCase("contabilidade")) {
                        cbDepVinculadosFluxoTFD.setEnabled(true);
                        cbFluxo.setEnabled(true);
                        caixaCobinacaoFluxoContabilidade();
                        cbInteressado.setEnabled(false);
                    }

                    if (lblRepoPerfil.getText().equalsIgnoreCase("tesouraria")) {
                        cbDepVinculadosFluxoTFD.setEnabled(true);
                        cbFluxo.setEnabled(true);
                        caixaCobinacaoFluxoTesouraria();
                        cbInteressado.setEnabled(false);
                    }

                } else {
                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setText("Digite um Número de Protocolo válido");
                    //txtIdCustomBuscar.requestFocus();
                    cboMascaraCustomizada.requestFocus();
                }
            } else {
                //  JOptionPane.showMessageDialog(null, "Nº deste Protocolo não consta \nem nossa Banco de Dados");
                //   txtIdCustomBuscar.setText("");
                txtIdCustomBuscar.setBackground(Color.red);
                txtIdCustomBuscar.requestFocus();
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setText("Protocolo Inexistente.");
            }
        } catch (PersistenciaException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
            erroViaEmail(ex.getMessage(), " btnBuscarIdCustomFocusGained()- camada GUI\n"
                    + "ao Clicar no botão pesquisar do \n"
                    + "form FluxoSistemaGenericoTFD");
        }


    }//GEN-LAST:event_btnBuscarIdCustomFocusGained

    private void txtIdCustomBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdCustomBuscarKeyReleased

    }//GEN-LAST:event_txtIdCustomBuscarKeyReleased

    private void txtIdCustomBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdCustomBuscarKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Iniciando Pesquisa no Banco de Dados");

            btnBuscarIdCustom.requestFocus();

        }
    }//GEN-LAST:event_txtIdCustomBuscarKeyPressed

    private void txtIdCustomBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdCustomBuscarFocusLost
        txtIdCustomBuscar.setBackground(Color.WHITE);
        lblLinhaInformativa.setText("");
    }//GEN-LAST:event_txtIdCustomBuscarFocusLost

    private void txtIdCustomBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdCustomBuscarFocusGained
        txtIdCustomBuscar.setBackground(Color.YELLOW);
        txtIdCustomBuscar.setToolTipText("Informe o Nº do Processo a Ser Pesquisado.");
//        Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo
//        lblLinhaInformativa.setText("");
//        lblLinhaInformativa.setText("Informe o Nº do Processo a Ser Pesquisado.");
//        lblLinhaInformativa.setForeground(Color.BLUE);

    }//GEN-LAST:event_txtIdCustomBuscarFocusGained

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed

    }//GEN-LAST:event_btnSalvarKeyPressed

    private void limparCampos() {
        lblDadosProtocoloRepositIDCustomizado.setText("");
        lblDadosProtocoloRepositDtProtocolada.setText("");
        lblDadosProtocoloRepositDtTransf.setText("");
        lblDadosProcessoTFDRepositDepOrigem.setText("");
        lblDadosProtocoloIntressadoOrigem.setText("");
        lblDadoProtocoloRepositSetorInterno.setText("");
        lblDadosProtocoloRepositEnderecado.setText("");
        lblRepositCPF.setText("");

    }


    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        /*Evento ao ser clicado executa código*/
        int lancar_pagar = JOptionPane.showConfirmDialog(null, "Confirma o Lançamento \n "
                + "e Pagamento do\n"
                + " Protocolo Nº [ " + txtIdCustomBuscar.getText() + " ] ?\n", "Informação", JOptionPane.YES_NO_OPTION);

        if (lancar_pagar == JOptionPane.YES_OPTION) {

            //setando os valores que serão necessário par ao preenchimento da tbItensFluxoMoviemento
            itensFluxoDTO.setFkIDCustomDto(txtIdCustomBuscar.getText());//fk_id_com_data
            itensFluxoDTO.setDepartamentoDestinoDto((String) cbDepVinculadosFluxoTFD.getSelectedItem());//destino
            itensFluxoDTO.setInteressadoDestinoDto((String) cbInteressado.getSelectedItem());//interessado_destino
            itensFluxoDTO.setLoginUsuarioDto(lblRepoLogin.getText());//login_usuario
            itensFluxoDTO.setPerfilDto(lblRepoPerfil.getText());//Perfil
            itensFluxoDTO.setRepositHDDto(lblRepoHD.getText());//hd
            itensFluxoDTO.setRepositCPUDto(lblRepoCPU.getText());//cpu
            itensFluxoDTO.setStatusMovimentoDto((String) cbFluxo.getSelectedItem());//statusMovimento     

            //efetuando o pagamento 
            fluxoDTO.setStatutsTrasacaoDto("PAGO");
            fluxoDTO.setDataTransacaoDto(lblStatusData.getText());

            //uma verificação antes do lançamento forma de evitar dois lançamento de forma indevida no 
            //fluxo sem que antes você cancele 1 para lançar outro só pode ter um único 
            int contadorControleLancaTesouraria = 1;
            boolean flagPrimeiroCadastro = false;

            for (int linha = 0; linha < tabela.getRowCount(); linha++) {
                String colunaStatus = (String) tabela.getModel().getValueAt(linha, 1); //1=statusMovimento;
                String colunaPerfil = (String) tabela.getModel().getValueAt(linha, 3); //3=perfil;
                String colunaLancamento = (String) tabela.getModel().getValueAt(linha, 6); //6=Lançamento;

                /**
                 * USAR UMA TECNICA NOVA USAR UM FUNIL PARA FILTRAR INFORMAÇÕES
                 * QUANDO O FLUXXO FOR NORMAL
                 */
                System.out.println("Linha:" + linha + " Nº De Linhas: " + tabela.getRowCount() + "Perfil: " + colunaPerfil + "Status: " + colunaStatus + "Lancamento: " + colunaLancamento);

                if ( //se numero de linhas é 2  
                        //se linha analisada no momento é UM que representa CONTABILIDADE
                        //se nesta linha analisada na posição 1 o perfil é CONTABILIDADE
                        // se o status do movimento é RECEDIO / ENCAMINHADO
                        // se CANCELADO o lançamento
                        tabela.getRowCount() >= 2
                        && colunaPerfil.equalsIgnoreCase("CONTABILIDADE")
                        && colunaStatus.equalsIgnoreCase("RECEBIDO|ENCAMINHANDO")
                        && colunaLancamento.equalsIgnoreCase("AUTENTICADO") //SE TRUE
                        ) {

                    System.out.println("Contador:" + contadorControleLancaTesouraria);

                    if (contadorControleLancaTesouraria == 1) {

                     //  JOptionPane.showMessageDialog(null, "entrou para inserir linha e pagamento");
                        //fazer uma pesquisa 
                        boolean retorno = itensDoProtocolo.buscarConfirmacaoTesourariaInsere(txtIdCustomBuscar.getText(), "Tesouraria","AUTENTICADO");

                        if (retorno == false) {

                            try {

                                //irá inserir quando as duas condições forem TRUE
                                itensFluxoDAO.inserir(itensFluxoDTO);

                                //primeiro limpa a tabela caso exita registros ja setados 
                                //só entao coloca os registros novos 
                                //addRowTratandoDadosEnviadosTabela();
                                buscarDadosNaTbFluxoTFD();
                                addRowTratandoDadosEnviadosTabela();

                                //ENVIAS PACOTES DE EMAIL INFORMANDO O FLUXO DO TFD
                                enviarEmailFluxoContabilidadeTesouraria();

                                //   limparCampos();
                                lblLinhaInformativa.setText("");
                                lblLinhaInformativa.setText("Registro Cadastrado com Sucesso!");
                                JOptionPane.showMessageDialog(this, "Registro Cadastrado com Sucesso!" + "\n ", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                                cbDepVinculadosFluxoTFD.setEnabled(false);
                                cbFluxo.setEnabled(false);
                                cbInteressado.setEnabled(false);
                                btnSalvar.setEnabled(false);

                                try {
                                    fluxoDAO.atualizarPagamentoStatusTransacao(fluxoDTO);
                                    //fazer uma atualização no status transação em tempo de execução 
                                    buscarStatusTransacao();
                                    //envia email para a secretaria de saude
                                    enviarConfirmaPagamentoTesouraria();
                                } catch (PersistenciaException ex) {
                                    ex.printStackTrace();
                                    ex.getMessage();
                                    erroViaEmail(ex.getMessage(), "Camada GUI: btnSalvarActionPerformed\n Tesouraria erro ao \n enviar pagamento na \n tbFluxoMovimento");
                                }
                                
                                flagPrimeiroCadastro = true;

                            } catch (PersistenciaException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                                erroViaEmail(ex.getMessage(), "Inserir ao Salvar o Fluxo\n"
                                        + " na tbItensFluxoMovimento- Camada GUI\n"
                                        + "inserir()");
                                ex.printStackTrace();
                            }
                        }

                    }

                    System.out.println("Contador Antes if Tesouraria==2:" + contadorControleLancaTesouraria);

//                JOptionPane.showMessageDialog(null, "DISPARA MENSAGEM\n"
//                        + "[TESOURARIA]LINHA: " +linha +"CONTADOR"+ contadorControleLancaTesouraria);
                    if (contadorControleLancaTesouraria == 2) {

                        JOptionPane.showMessageDialog(this, "IMPORTANTE: o setor de [CONTABILIDADE ] \n"
                                + "registrou 2(dois) ou mais Fluxo(s) de Encaminhamento\n"
                                + "para este protocolo[" + txtIdCustomBuscar.getText() + "]\n"
                                + "neste caso há 2(dois) ou mais fluxo(s) (AUTENTICADO)\n"
                                + " para esse Registro, onde o correto seria\n"
                                + "ter apenas 1(UM) fluxo da [CONTABILIDADE]-->>[TESOURARIA]" + "\n ", "ALERTA!!!!", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    }

                    //fazer uma pesquisa se ja um lançamento feito no setor de Tesouraria AUTENTICADO
                    boolean retorno = itensDoProtocolo.buscarConfirmacaoTesourariaInsere(txtIdCustomBuscar.getText(), "Tesouraria","AUTENTICADO");
//              
//                JOptionPane.showMessageDialog(null, "DISPARA A MENSAGEM DE BLOQUEIO \n"
//                        +"Existe Autenticado"+ retorno);

                    if (retorno == true && contadorControleLancaTesouraria == 1 && flagPrimeiroCadastro == false && colunaLancamento.equalsIgnoreCase("AUTENTICADO")) {

                        JOptionPane.showMessageDialog(this, "IMPORTANTE: já existe uma [AUTENTICAÇÃO] \n"
                                + "da [TESOURARIA] encaminhando [ARQUIVAMENTO]\n"
                                + "para este protocolo[" + txtIdCustomBuscar.getText() + "]\n"
                                + "o ALGORITMO do Sistema Impossibilita outro Lançamento,\n"
                                + "pois, neste caso gera DUPLICIDADE\n"
                                + "\n ", "ALERTA!!!!", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

                        btnSalvar.setEnabled(false);
                    }

                    contadorControleLancaTesouraria = contadorControleLancaTesouraria + 1;
                }

            }

            boolean retorno = itensDoProtocolo.buscarConfirmacaoTesourariaInsere(txtIdCustomBuscar.getText(), "Tesouraria","AUTENTICADO");

            if (retorno == false) {

                //tem que criar uma condicional aqui 
                JOptionPane.showMessageDialog(this, "ALGORITMO DE ANALISE:\n"
                        + "o setor de [CONTABILIDADE ] deverá  \n"
                        + "AUTENTICAR  um Fluxo válido para o Protocolo\n"
                        + "Nº [ " + txtIdCustomBuscar.getText() + " ]  \n"
                        + "\n ", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseExited
        btnSalvar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnSalvarMouseExited

    private void btnSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseEntered
        btnSalvar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnSalvarMouseEntered

    private void btnSalvarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusLost
        btnSalvar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnSalvarFocusLost

    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained
        btnSalvar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnSalvarFocusGained

    private void cbFluxoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFluxoActionPerformed

        //capturando itens selecionado caixa combinação fluxo
        String itemSelecionado = (String) cbFluxo.getSelectedItem();

        if (lblRepoPerfil.getText().equalsIgnoreCase("TESOURARIA") && itemSelecionado.equalsIgnoreCase("RECEBIDO|ENCAMINHANDO")) {

            //Adicionar uma lista em um como com todos os Internos do Banco de dados 
            // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
            //  JOptionPane.showMessageDialog(this, "flag: " + flag);
            ConexaoUtil conecta = new ConexaoUtil();
            boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

            if (recebeConexao == true) {

                ArrayList<SetorTramiteInternoDTO> list;

                try {

                    list = (ArrayList<SetorTramiteInternoDTO>) setorInternoDAO.listarTodos();

                    cbDepVinculadosFluxoTFD.removeAllItems();
                    for (int i = 0; i < list.size(); i++) {

                        //fazer filtro dos setores internos vindos do banco de dados 
                        // e já setando automaticamente o fluxo para quenão ocorra erro 
                        //por parte do usuário 
                        if (list.get(i).getNomeDto().equalsIgnoreCase("ARQUIVAMENTO DE PROCESSO")) {
                            cbDepVinculadosFluxoTFD.addItem(list.get(i).getNomeDto());
                        }
                    }

                } catch (PersistenciaException ex) {
                    erroViaEmail(ex.getMessage(), "Erro ao Prencher o combo\n"
                            + "cbDepVinculadosFluxoTFD - Camda DAO \n"
                            + "FluxoTesouraria");
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro Camada GUI:\n" + ex.getMessage());
                }

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            }

        }

        if (lblRepoPerfil.getText().equalsIgnoreCase("TESOURARIA") && itemSelecionado.equalsIgnoreCase("DEVOLVENDO CORRECAO")) {

            //Adicionar uma lista em um como com todos os Internos do Banco de dados 
            // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
            //  JOptionPane.showMessageDialog(this, "flag: " + flag);
            ConexaoUtil conecta = new ConexaoUtil();
            boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

            if (recebeConexao == true) {

                ArrayList<SetorTramiteInternoDTO> list;

                try {

                    list = (ArrayList<SetorTramiteInternoDTO>) setorInternoDAO.listarTodos();

                    cbDepVinculadosFluxoTFD.removeAllItems();
                    for (int i = 0; i < list.size(); i++) {

                        //fazer filtro dos setores internos vindos do banco de dados 
                        // e já setando automaticamente o fluxo para quenão ocorra erro 
                        //por parte do usuário 
                        if (list.get(i).getNomeDto().equalsIgnoreCase("CONTABILIDADE")) {
                            cbDepVinculadosFluxoTFD.addItem(list.get(i).getNomeDto());
                        }
                    }

                } catch (PersistenciaException ex) {
                    erroViaEmail(ex.getMessage(), "Erro ao Prencher o combo\n"
                            + "cbDepVinculadosFluxoTFD - Camda DAO \n"
                            + "FLUXOTESOURARIA");
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro Camada GUI:\n" + ex.getMessage());
                }

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            }

        }
        System.out.println(itemSelecionado);
    }//GEN-LAST:event_cbFluxoActionPerformed

    private void cbDepVinculadosFluxoTFDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDepVinculadosFluxoTFDActionPerformed

        //capturando itens selecionado caixa combinação Departamento(Setores Internos )
        String itemSelecionado = (String) cbDepVinculadosFluxoTFD.getSelectedItem();
        cbInteressado.setEnabled(true);
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            ArrayList<UsuarioDTO> list;

            try {

                list = (ArrayList<UsuarioDTO>) usuarioDAO.buscarPerfilRetornarList(itemSelecionado);

                cbInteressado.removeAllItems();
                for (int i = 0; i < list.size(); i++) {

                    cbInteressado.addItem(list.get(i).getUsuarioDto());

                }

                btnSalvar.setEnabled(true);
            } catch (PersistenciaException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro Camada GUI:\n" + ex.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }

    }//GEN-LAST:event_cbDepVinculadosFluxoTFDActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        //https://www.youtube.com/watch?v=jPfKFm2Yfow
        int coluna = tabela.getColumnModel().getColumnIndexAtX(evt.getX());
        int linha = evt.getY() / tabela.getRowHeight();

        //trabalhando o controle de segurança do usuario 
        int linhaControle = tabela.getSelectedRow();
        //se na tabela na linha selecionada e coluna dois (perfil for igual ao perfil do usuário logado  li
        //liberar o botaoa excluir para que se execute o procedimento 

        Font fonteConfirma = new Font("Tahoma", Font.BOLD, 12);
        lblLinhaInformativa.setFont(fonteConfirma);
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Linha Gerada No " + tabela.getValueAt(linhaControle, 3).toString() + " Permitido Cancelar");

        if (tabela.getValueAt(linhaControle, 3).toString().equals(lblRepoPerfil.getText())) {

            if (linha < tabela.getRowCount() && linha >= 0 && coluna < tabela.getColumnCount() && coluna >= 0) {
                Object value = tabela.getValueAt(linha, coluna);
                if (value instanceof JButton) {
                    ((JButton) value).doClick();
                    JButton boton = (JButton) value;

                    /**
                     * Botão Exclusão Evento
                     */
                    if (boton.getName().equals("idCancelar")) {

                        /*Evento ao ser clicado executa código*/
                        int cancelar = JOptionPane.showConfirmDialog(null, "Deseja cancelar lançamento?", "Informação", JOptionPane.YES_NO_OPTION);

                        if (cancelar == JOptionPane.YES_OPTION) {

                            //capiturado linha e coluna na posição 0(zero) do array 
                            itensFluxoDTO.setIdDto(Integer.parseInt(tabela.getValueAt(linhaControle, 0).toString()));
                            //setando o valor que será lançado
                            itensFluxoDTO.setLancamentoDto("CANCELADO");
                            try {
                                itensFluxoDAO.atualizar(itensFluxoDTO);

                                buscarDadosNaTbFluxoTFD();
                                addRowTratandoDadosEnviadosTabela();

                                String id = tabela.getValueAt(linhaControle, 0).toString();
                                String dtRegistro = tabela.getValueAt(linhaControle, 2).toString();
                                String perfil = tabela.getValueAt(linhaControle, 3).toString();
                                String destino = tabela.getValueAt(linhaControle, 4).toString();
                                String enderecado = tabela.getValueAt(linhaControle, 5).toString();
                                String lancamento = tabela.getValueAt(linhaControle, 6).toString();

                                enviarEmailCancelamentoFluxoNaTesouraria(id, dtRegistro, perfil, destino, enderecado, lancamento);

                            } catch (PersistenciaException ex) {

                                erroViaEmail(ex.getMessage(), "Camada Gui:"
                                        + "\ntabelaMouseClicked(java.awt.event.MouseEvent evt)\n"
                                        + "ao clicar no botão para cancelar lançamento no fluxo ");
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Camada GUI:" + ex.getMessage());
                            }

                        }

                    }

                }//fim condicional botton ex

            }
        } else {

            Font fonte = new Font("Tahoma", Font.BOLD, 12);
            lblLinhaInformativa.setFont(fonte);
            lblLinhaInformativa.setForeground(Color.red);

            lblLinhaInformativa.setText("Linha Gerada No " + tabela.getValueAt(linhaControle, 3).toString() + " Sem Permissão/Cancelar ");

            //JOptionPane.showMessageDialog(this, "" + "\n Login não tem permissão \n excluir o fluxo em questão \n mais uma tentativa e enviaremos\n um email de notificação\n TENTATIVA BURLAR O SISTEMA", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
        }                  //aqui o if 
    }//GEN-LAST:event_tabelaMouseClicked

    private void cboMascaraCustomizadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMascaraCustomizadaActionPerformed

        int numeroEscolido = Integer.parseInt((String) cboMascaraCustomizada.getSelectedItem());

        switch (numeroEscolido) {
            case 1:

                MaskFormatter maskFormatter1;
                try {
                    //*******************************************************************************************
                    //contribuição de RenataFA Forum GUJ
                    //https://www.guj.com.br/t/mudar-formato-de-jformattedtextfield-em-tempo-de-execucao/35713/16
                    //********************************************************************************************
                    txtIdCustomBuscar.setValue(null);//famoso pulo do gato 
                    txtIdCustomBuscar.setEnabled(true);
                    maskFormatter1 = new MaskFormatter("#-TFD/20##");
                    txtIdCustomBuscar.setFormatterFactory(new DefaultFormatterFactory(maskFormatter1));

                } catch (ParseException ex) {
                    erroViaEmail(ex.getMessage(), "GUI:FluxoSistemaGenericoTFD\n"
                            + "erro ao tentar setar a mascara \n"
                            + "na posição de 1 algarismo");
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 2:
                MaskFormatter maskFormatter2;
                try {
                    //*******************************************************************************************
                    //contribuição de RenataFA Forum GUJ
                    //https://www.guj.com.br/t/mudar-formato-de-jformattedtextfield-em-tempo-de-execucao/35713/16
                    //********************************************************************************************
                    txtIdCustomBuscar.setValue(null);//famoso pulo do gato 
                    txtIdCustomBuscar.setEnabled(true);
                    maskFormatter2 = new MaskFormatter("##-TFD/20##");
                    txtIdCustomBuscar.setFormatterFactory(new DefaultFormatterFactory(maskFormatter2));
                } catch (ParseException ex) {

                    erroViaEmail(ex.getMessage(), "GUI:FluxoSistemaGenericoTFD\n"
                            + "erro ao tentar setar a mascara \n"
                            + "na posição de 2 algarismo");

                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 3:
                MaskFormatter maskFormatter3;
                try {
                    //*******************************************************************************************
                    //contribuição de RenataFA Forum GUJ
                    //https://www.guj.com.br/t/mudar-formato-de-jformattedtextfield-em-tempo-de-execucao/35713/16
                    //********************************************************************************************
                    txtIdCustomBuscar.setValue(null);//famoso pulo do gato 
                    txtIdCustomBuscar.setEnabled(true);
                    maskFormatter3 = new MaskFormatter("###-TFD/20##");
                    txtIdCustomBuscar.setFormatterFactory(new DefaultFormatterFactory(maskFormatter3));
                } catch (ParseException ex) {

                    erroViaEmail(ex.getMessage(), "GUI:FluxoSistemaGenericoTFD\n"
                            + "erro ao tentar setar a mascara \n"
                            + "na posição de 3 algarismo");
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 4:
                MaskFormatter maskFormatter4;
                try {
                    //*******************************************************************************************
                    //contribuição de RenataFA Forum GUJ
                    //https://www.guj.com.br/t/mudar-formato-de-jformattedtextfield-em-tempo-de-execucao/35713/16
                    //********************************************************************************************
                    txtIdCustomBuscar.setValue(null);//famoso pulo do gato 
                    txtIdCustomBuscar.setEnabled(true);
                    maskFormatter4 = new MaskFormatter("####-TFD/20##");
                    txtIdCustomBuscar.setFormatterFactory(new DefaultFormatterFactory(maskFormatter4));
                } catch (ParseException ex) {

                    erroViaEmail(ex.getMessage(), "GUI:FluxoSistemaGenericoTFD\n"
                            + "erro ao tentar setar a mascara \n"
                            + "na posição de 4 algarismo");
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 5:
                MaskFormatter maskFormatter5;
                try {
                    //*******************************************************************************************
                    //contribuição de RenataFA Forum GUJ
                    //https://www.guj.com.br/t/mudar-formato-de-jformattedtextfield-em-tempo-de-execucao/35713/16
                    //********************************************************************************************
                    txtIdCustomBuscar.setValue(null);//famoso pulo do gato 
                    txtIdCustomBuscar.setEnabled(true);
                    maskFormatter5 = new MaskFormatter("#####-TFD/20##");
                    txtIdCustomBuscar.setFormatterFactory(new DefaultFormatterFactory(maskFormatter5));
                } catch (ParseException ex) {

                    erroViaEmail(ex.getMessage(), "GUI:FluxoSistemaGenericoTFD\n"
                            + "erro ao tentar setar a mascara \n"
                            + "na posição de 5 algarismo");
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;
            case 6:
                MaskFormatter maskFormatter6;
                try {
                    //*******************************************************************************************
                    //contribuição de RenataFA Forum GUJ
                    //https://www.guj.com.br/t/mudar-formato-de-jformattedtextfield-em-tempo-de-execucao/35713/16
                    //********************************************************************************************
                    txtIdCustomBuscar.setValue(null);//famoso pulo do gato 
                    txtIdCustomBuscar.setEnabled(true);
                    maskFormatter6 = new MaskFormatter("######-TFD/20##");
                    txtIdCustomBuscar.setFormatterFactory(new DefaultFormatterFactory(maskFormatter6));

                } catch (ParseException ex) {

                    erroViaEmail(ex.getMessage(), "GUI:FluxoSistemaGenericoTFD\n"
                            + "erro ao tentar setar a mascara \n"
                            + "na posição de 6 algarismo");
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

        }

        System.out.println("Numero Esclido: " + numeroEscolido);
    }//GEN-LAST:event_cboMascaraCustomizadaActionPerformed

    private void txtIdCustomBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdCustomBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCustomBuscarActionPerformed

    private void acaoBuscarPacientePeloCPF() throws PersistenciaException {

    }

    private void verificandoCampoDataTransf() throws ParseException {

    }

    br.com.subgerentepro.telas.Frm_Lista_Itens_Protocolo_TFD formListaItensProtocoloTFD;

    private void adicionarItensTabelaItensProtocolo() {

    }

    
    private void enviarEmailCancelamentoFluxoNaTesouraria(
            String tabelaID,
            String tabelaDtRegistro,
            String tabelaPerfil,
            String tabelaDestino,
            String tabelaEnderecado,
            String tabelaLancamento
    
    ) {
  //----------------------------------------------------  
        //enviar Email com Anexo    
        //Dica Canal:https://www.youtube.com/watch?v=pBdaJhbI9I4
        //     

        //declaração de variáveis 
        String meuEmail = "sisprotocoloj@gmail.com";//meu email (Conta Principal)fará os lançamentos p/ demais
        String minhaSenha = "gerlande2111791020";//senha 

        //EMAIL DE ENVIO 
       // String emailDestinatario = "prefaltoalegrema@gmail.com";//email para tesouraria TEM SISTEMA PARA CONSULTAR 
        String emailSecretariaSaude = "setorprotocoloj@gmail.com";//email para secretaria de saude EMAIL:setorprotocoloj@gmail.com SENHA:setorpmaa1518
     //   String emailProcuradorGeral = "affonsobbatista@hotmail.com";//email para contabilidade TEM SISTEMA PARA CONSULTAR
        //  String emailSetorProtocolo = "prefeituraprotocolo0@gmail.com";//setor de protocolo TEM SISTEMA PARA CONSULTAR

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
            email.setSubject("Data: [" + lblDadosProtocoloRepositDtProtocolada.getText() + "] \n" +"["+lblRepoPaciente.getText()+"]"+ "\nFLUXO FOI CANCELADO [TESOURARIA]");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("--------------------------------------------------------------------------------\n"
                    + "Nº Protocolo TFD:" + lblDadosProtocoloRepositIDCustomizado.getText() + " Paciente:" + lblRepoPaciente.getText() + "\n"
                    + "Número Cartão SUS: " + lblRepoNCartaoSUS.getText() + "  Dt Trasferência: " + lblDadosProtocoloRepositDtTransf.getText() + "  CPF: " + lblRepositCPF.getText() + "\n"
                    + "--------------------------------------------------------------------------------------\n"
                    + "TRAMITAÇÃO MOVIMENTO - UM LANÇAMENTO FOI CANCELADO  [TESOURARIA] \n"
                    + "--------------------------------------------------------------------------------------\n"
                    + " Origem-->>[SECRETARIA SAUDE]-->> [SETOR PROTOCOLO] protocolado em:" + lblDadosProtocoloRepositDtProtocolada.getText() + "\n"
                    + "Recebeu e Encaminhando para:[" + lblDadoProtocoloRepositSetorInterno.getText() + "] aos cuidados de " + lblDadosProtocoloRepositEnderecado.getText() + "\n"
                    + "que na Data de :" + lblStatusData.getText() + "e horas:" + lblStatusHora.getText() + "\n"
                    + "Autentica no Sistem " + cbFluxo.getSelectedItem().toString() + " para " + cbDepVinculadosFluxoTFD.getSelectedItem().toString() + " aos cuidados de " + cbInteressado.getSelectedItem() + "\n"
                    +"--------------------------------------------------------------------------------------\n"
                    + " O MESMO FOI CANCELADO<<--->>[ CONFORME ESTÁ EXPLICITO NA LINHA ABAIXO]<<-->> \n"
                    +"--------------------------------------------------------------------------------------\n"
                    + "ID: ["+tabelaID+"] DATA PROTOCOLADA :[ "+tabelaDtRegistro+"]\n"
                    + " Setor Responsável pelo Cancelamento: ["+tabelaPerfil+"] Era destinado ao Setor:["+tabelaDestino+"\n"
                    + " Aos Cuidados de:["+tabelaEnderecado+"] a Situação Atual foi:["+tabelaLancamento+"\n"
                    + "Data Cancelamento: [ "+lblStatusData.getText()+" Hora Cancelamento:"+lblStatusHora.getText() +"\n"
                    + "Autenticacao Maquina: HD: " + lblRepoHD.getText() + "CPU: " + lblRepoCPU.getText() + "\n"
                    + "------------------------------------------------------------------------------------\n"
                    + "João 11:35: “Jesus chorou”. Analista:Tonis A. T. Ferreira\n"
                    + "--------------------------------------------------------------------------------------\n"
            );
            email.addTo(meuEmail);
            email.addTo(emailSecretariaSaude);// SANDRA SECRETARIA DE SAUDE 
            email.send();

        } catch (Exception e) {

            erroViaEmail(e.getMessage(), "enviarEmail()-Esse método envia email para \n "
                    + "caixas de entrada de Email da Pessoas responsável TFD \n"
                    + "Secretaria de Saude informando sobre o fluxo Contabilidade-->>Tesouraria\n PROTOCOLO Nº" + lblDadosProtocoloRepositIDCustomizado.getText());
            e.printStackTrace();
        }

    }

    
    
    
    
    
    
    private void enviarEmailFluxoContabilidadeTesouraria() {
      //----------------------------------------------------  
        //enviar Email com Anexo    
        //Dica Canal:https://www.youtube.com/watch?v=pBdaJhbI9I4
        //     

        //declaração de variáveis 
        String meuEmail = "sisprotocoloj@gmail.com";//meu email (Conta Principal)fará os lançamentos p/ demais
        String minhaSenha = "gerlande2111791020";//senha 

        //EMAIL DE ENVIO 
        //  String emailDestinatario = "prefaltoalegrema@gmail.com";//email para tesouraria TEM SISTEMA PARA CONSULTAR 
        String emailSecretariaSaude = "setorprotocoloj@gmail.com";//email para secretaria de saude EMAIL:setorprotocoloj@gmail.com SENHA:setorpmaa1518
        //    String emailProcuradorGeral = "affonsobbatista@hotmail.com";//email para contabilidade TEM SISTEMA PARA CONSULTAR

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
            email.setSubject("[" + lblDadosProtocoloRepositIDCustomizado.getText() + "]" + " FLUXO CONFIRMADO CONTABILIDADE");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("--------------------------------------------------------------------------------\n"
                    + "Nº Protocolo TFD:" + lblDadosProtocoloRepositIDCustomizado.getText() + " Paciente:" + lblRepoPaciente.getText() + "\n"
                    + "Número Cartão SUS: " + lblRepoNCartaoSUS.getText() + "  Dt Trasferência: " + lblDadosProtocoloRepositDtTransf.getText() + "  CPF: " + lblRepositCPF.getText() + "\n"
                    + "--------------------------------------------------------------------------------------\n"
                    + "TRAMITAÇÃO MOVIMENTO CONTABILIDADE-->> TESOURARIA\n"
                    + "--------------------------------------------------------------------------------------\n"
                    + " Origem-->>[SECRETARIA SAUDE]-->> [SETOR PROTOCOLO] protocolado em:" + lblDadosProtocoloRepositDtProtocolada.getText() + "\n"
                    + "Recebeu e Encaminhando para:[" + lblDadoProtocoloRepositSetorInterno.getText() + "] aos cuidados de " + lblDadosProtocoloRepositEnderecado.getText() + "\n"
                    + "que na Data de :" + lblStatusData.getText() + "e horas:" + lblStatusHora.getText() + "\n"
                    + "Autentica no Sistem " + cbFluxo.getSelectedItem().toString() + " para " + cbDepVinculadosFluxoTFD.getSelectedItem().toString() + " aos cuidados de " + cbInteressado.getSelectedItem() + "\n"
                    + "Autenticacao Maquina: HD: " + lblRepoHD.getText() + "CPU: " + lblRepoCPU.getText() + "\n"
                    + "------------------------------------------------------------------------------------\n"
                    + "Quando você se assentar para uma refeição com alguma autoridade observe com atenção \n"
                    + "quem está diante de você e encoste a faca à sua própria garganta se estiver com grande\n"
                    + "apetite.Não deseje as iguarias que lhe oferece,pois podem ser enganosas.Não esgote suas \n"
                    + "forças tentando ficar rico, tenha bom senso!Provérbios 9:10 - Analista:Tonis A. T. Ferreira\n"
                    + "--------------------------------------------------------------------------------------\n"
            );
            email.addTo(meuEmail);
            email.addTo(emailSecretariaSaude);//secretaria de saude SANDRA
            email.send();

        } catch (Exception e) {

            erroViaEmail(e.getMessage(), "enviarEmail()-Esse método envia email para \n "
                    + "caixas de entrada de Email da Pessoas responsável TFD \n"
                    + "Secretaria de Saude informando sobre o fluxo Contabilidade-->>Tesouraria\n PROTOCOLO Nº" + lblDadosProtocoloRepositIDCustomizado.getText());
            e.printStackTrace();
        }

    }

    private void enviarConfirmaPagamentoTesouraria() {
      //----------------------------------------------------  
        //enviar Email com Anexo    
        //Dica Canal:https://www.youtube.com/watch?v=pBdaJhbI9I4
        //     

        //declaração de variáveis 
        String meuEmail = "sisprotocoloj@gmail.com";//meu email (Conta Principal)fará os lançamentos p/ demais
        String minhaSenha = "gerlande2111791020";//senha 

        //EMAIL DE ENVIO 
         String emailDestinatario = "prefaltoalegrema@gmail.com";//email para tesouraria TEM SISTEMA PARA CONSULTAR 
        String emailSecretariaSaude = "setorprotocoloj@gmail.com";//email para secretaria de saude EMAIL:setorprotocoloj@gmail.com SENHA:setorpmaa1518
        //    String emailProcuradorGeral = "affonsobbatista@hotmail.com";//email para contabilidade TEM SISTEMA PARA CONSULTAR

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
            email.setSubject("R$ (^_^) Data: [" + lblDadosProtocoloRepositDtProtocolada.getText() + "] \n" +"["+lblRepoPaciente.getText()+"]" + " CONFIRMAÇÃO DE TRANSFERÊNCIA\n [TESOURARIA]");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("--------------------------------------------------------------------------------\n"
                    + "Número Cartão SUS: " + lblRepoNCartaoSUS.getText() + "  Dt Trasferência: " + lblDadosProtocoloRepositDtTransf.getText() + "  CPF: " + lblRepositCPF.getText() + "\n"
                    + "--------------------------------------------------------------------------------------\n"
                    + "Confirmo a transferência do TFD Nº[" + lblDadosProtocoloRepositIDCustomizado.getText() + "] do Paciente: " + lblRepoPaciente.getText() + "\n"
                    + "--------------------------------------------------------------------------------------\n"
                    + " Origem-->>[SECRETARIA SAUDE]-->> [SETOR PROTOCOLO] protocolado em:" + lblDadosProtocoloRepositDtProtocolada.getText() + "\n"
                    + "Recebeu e Encaminhando para:[" + lblDadoProtocoloRepositSetorInterno.getText() + "] aos cuidados de " + lblDadosProtocoloRepositEnderecado.getText() + "\n"
                    + "que na Data de :" + lblStatusData.getText() + "e horas:" + lblStatusHora.getText() + "\n"
                    + "Autentica no Sistem " + cbFluxo.getSelectedItem().toString() + " para " + cbDepVinculadosFluxoTFD.getSelectedItem().toString() + " aos cuidados de " + cbInteressado.getSelectedItem() + "\n"
                    + "Autenticacao Maquina: HD: " + lblRepoHD.getText() + "CPU: " + lblRepoCPU.getText() + "\n"
                    + "------------------------------------------------------------------------------------\n"
                    + "João 11:35: “Jesus chorou”.. Analista:Tonis A. T. Ferreira \n"
                    + "--------------------------------------------------------------------------------------\n"
            );
            email.addTo(meuEmail);
            email.addTo(emailDestinatario);
            email.addTo(emailSecretariaSaude);//secretaria de saude SANDRA
            email.send();

        } catch (Exception e) {

            erroViaEmail(e.getMessage(), "enviarEmail()-Esse método envia email para \n "
                    + "caixas de entrada de Email da Pessoas responsável TFD \n"
                    + "Secretaria de Saude informando sobre o fluxo Contabilidade-->>Tesouraria\n PROTOCOLO Nº" + lblDadosProtocoloRepositIDCustomizado.getText());
            e.printStackTrace();
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barProgressProcedimentosBackEnd;
    private javax.swing.JButton btnBuscarIdCustom;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox cbDepVinculadosFluxoTFD;
    private javax.swing.JComboBox cbFluxo;
    private javax.swing.JComboBox cbInteressado;
    private javax.swing.JComboBox cboMascaraCustomizada;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAosCuidados;
    private javax.swing.JLabel lblBackEnd;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblCPU;
    private javax.swing.JLabel lblCartaoSUS;
    private javax.swing.JLabel lblConsultarProtocolo;
    private javax.swing.JLabel lblDadoProtocoloRepositSetorInterno;
    private javax.swing.JLabel lblDadosProcessoTFDRepositDepOrigem;
    private javax.swing.JLabel lblDadosProtocoloInteressadoDepOrigem;
    private javax.swing.JLabel lblDadosProtocoloIntressadoOrigem;
    private javax.swing.JLabel lblDadosProtocoloRepositDtProtocolada;
    private javax.swing.JLabel lblDadosProtocoloRepositDtTransf;
    private javax.swing.JLabel lblDadosProtocoloRepositEnderecado;
    private javax.swing.JLabel lblDadosProtocoloRepositIDCustomizado;
    private javax.swing.JLabel lblDataProtocolada;
    private javax.swing.JLabel lblDepOrigem;
    private javax.swing.JLabel lblDepartamento;
    private javax.swing.JLabel lblDtTransf;
    private javax.swing.JLabel lblFluxo;
    private javax.swing.JLabel lblHD2;
    private javax.swing.JLabel lblInteressadoDepOrigem;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblNProtocolo;
    private javax.swing.JLabel lblNuvemBackEnd;
    private javax.swing.JLabel lblPacientel;
    private javax.swing.JLabel lblPerfil2;
    private javax.swing.JLabel lblRepoCPU;
    private javax.swing.JLabel lblRepoHD;
    private javax.swing.JLabel lblRepoLogin;
    private javax.swing.JLabel lblRepoNCartaoSUS;
    private javax.swing.JLabel lblRepoPaciente;
    private javax.swing.JLabel lblRepoPerfil;
    private javax.swing.JLabel lblRepoUsuario;
    private javax.swing.JLabel lblRepositCPF;
    private javax.swing.JLabel lblSetorInterno;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel painelDadosFluxo;
    private javax.swing.JPanel painelDadosUsuarioMaquina;
    private javax.swing.JPanel painelInfoPaciente;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JTable tabela;
    private javax.swing.JFormattedTextField txtIdCustomBuscar;
    // End of variables declaration//GEN-END:variables
}
