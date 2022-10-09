/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.subgerentepro.telas;

import br.com.subgerentepro.dao.FuncionarioDAO;
import br.com.subgerentepro.dto.DepartamentoDTO;
import br.com.subgerentepro.dto.EstadoDTO;
import br.com.subgerentepro.dto.FuncionarioDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import static br.com.subgerentepro.telas.TelaPrincipal.lblNomeCompletoUsuario;
import static br.com.subgerentepro.telas.TelaPrincipal.lblPerfil;
//import static br.com.subgerentepro.telas.TelaPrincipal.lblRepositorioCPU;
//import static br.com.subgerentepro.telas.TelaPrincipal.lblRepositorioHD;
//import static br.com.subgerentepro.telas.TelaPrincipal.lblRepositorioPlacaMae;
import static br.com.subgerentepro.telas.TelaPrincipal.lblStatusData;
import static br.com.subgerentepro.telas.TelaPrincipal.lblUsuarioLogado;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dã Torres
 */
public class TelaProtocDocsEmpresas extends javax.swing.JInternalFrame {

    
    br.com.subgerentepro.telas.Frm_Lista_Setores_Tramite formSetorTramiteInterno;
    
    DepartamentoDTO departamentoDTO = new DepartamentoDTO();
    FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
//
    br.com.subgerentepro.telas.Frm_Protocolar_Lista_Empresas formListaEmpresa;
    br.com.subgerentepro.telas.Frm_Protocolar_Lista_Funcionarios_Filtro formListFuncionarioInternoProtocolo;

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

    public TelaProtocDocsEmpresas() {
        initComponents();
        personalizacaoFrontEnd();
        aoCarregarForm();
    }

    private void personalizacaoFrontEnd() {

        btnEnderecadoFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/pesquisar.png")));
        btnBuscarInteressado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/pesquisar.png")));
        btnItemDocumentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/pesquisar.png")));
        btnSetorDestino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/pesquisar.png")));
    }

    private void capturandoDadosNiveisMaquinaUsuario() {

        //MAQUINA
//        lblRepositorioHDForm.setText(lblRepositorioHD.getText());
//        lblRepositorioPlacaMaeForm.setText(lblRepositorioPlacaMae.getText());
//        repositorioCPUForm.setText(lblRepositorioCPU.getText());
        txtDataProtocoloForm.setText(lblStatusData.getText());
        //USUARIO
        lblUsuarioLogadoRepositorio.setText(lblUsuarioLogado.getText());
        lblUsuarioLogadoRepositorio.setText(lblUsuarioLogadoRepositorio.getText());
        lblPerfilForm.setText(lblPerfil.getText());
        lblNomeCompletoUsuarioForm.setText(lblNomeCompletoUsuario.getText());

    }

    private void aoCarregarForm() {

        //CAMPOS 
        txtID.setEnabled(false);
        txtDataProtocoloForm.setEnabled(false);

        txtSetorDestinoRopositorio.setEnabled(false);
        btnSetorDestino.setEnabled(false);
        btnEnderecadoFuncionario.setEnabled(false);
        txtItemDoProtocolo.setEnabled(false);
        btnItemDocumentos.setEnabled(false);
        txtNumero.setEnabled(false);
        txtAreaDescricao.setEnabled(false);
        
        //SEGURANÇA A NIVEL DE MAQUINA E USUARIO
        capturandoDadosNiveisMaquinaUsuario();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        painelSuperior = new javax.swing.JPanel();
        btnExcluir = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblGrauRelevancia = new javax.swing.JLabel();
        cbGrauRelevancia = new javax.swing.JComboBox();
        painelSegurancaNivelMaquina1 = new javax.swing.JPanel();
        paineSegurancaNivelUsuario = new javax.swing.JPanel();
        lblUsuarioLogadoRepositorio = new javax.swing.JLabel();
        lblPerfilForm = new javax.swing.JLabel();
        lblNomeCompletoUsuarioForm = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblHD1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblRepositorioHDForm = new javax.swing.JLabel();
        lblRepositorioPlacaMaeForm = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        repositorioCPUForm = new javax.swing.JLabel();
        painelIdentificacoesIniciais = new javax.swing.JPanel();
        txtID = new javax.swing.JTextField();
        txtIDAno = new javax.swing.JTextField();
        txtDataProtocoloForm = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        painelFluxoDeOrigem = new javax.swing.JPanel();
        cbOrigem = new javax.swing.JComboBox();
        txtInteressado = new javax.swing.JTextField();
        llbOrigem = new javax.swing.JLabel();
        lblEmpresaInteressada = new javax.swing.JLabel();
        btnBuscarInteressado = new javax.swing.JButton();
        painelFluxoDestino = new javax.swing.JPanel();
        lblSetorDestino = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtEnderecadoFuncionario = new javax.swing.JTextField();
        btnEnderecadoFuncionario = new javax.swing.JButton();
        txtSetorDestinoRopositorio = new javax.swing.JTextField();
        btnSetorDestino = new javax.swing.JButton();
        ScroolTabela = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        painelLancamento = new javax.swing.JPanel();
        txtNumero = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtItemDoProtocolo = new javax.swing.JTextField();
        btnItemDocumentos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaDescricao = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Empresas ");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelPrincipal.setBackground(java.awt.Color.white);
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelSuperior.setBackground(new java.awt.Color(255, 255, 255));
        painelSuperior.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Botões Superior Protocolo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 13))); // NOI18N
        painelSuperior.setOpaque(false);

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/lixeira.png"))); // NOI18N
        btnExcluir.setToolTipText("Excluir Registro");
        btnExcluir.setEnabled(false);
        btnExcluir.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/lixeiraRollover.png"))); // NOI18N
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

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/AdicionarRegistro (1).png"))); // NOI18N
        btnAdicionar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/AdicionarRegistro Rolover.png"))); // NOI18N
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

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/editRegistro.png"))); // NOI18N
        btnEditar.setToolTipText("Alterar Registro");
        btnEditar.setEnabled(false);
        btnEditar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/editRegistroRolover.png"))); // NOI18N
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

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/salvar.png"))); // NOI18N
        btnSalvar.setToolTipText("Salvar Registro");
        btnSalvar.setEnabled(false);
        btnSalvar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/salvaRolover.png"))); // NOI18N
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

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/cancelar.png"))); // NOI18N
        btnCancelar.setToolTipText("Cancelar Registro");
        btnCancelar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/cancelarRolover.png"))); // NOI18N
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

        lblGrauRelevancia.setText("Grau de relevancia :");

        cbGrauRelevancia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MODERADO", "ALTO", "BAIXO" }));

        javax.swing.GroupLayout painelSuperiorLayout = new javax.swing.GroupLayout(painelSuperior);
        painelSuperior.setLayout(painelSuperiorLayout);
        painelSuperiorLayout.setHorizontalGroup(
            painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelSuperiorLayout.createSequentialGroup()
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addGroup(painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGrauRelevancia)
                    .addComponent(cbGrauRelevancia, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painelSuperiorLayout.setVerticalGroup(
            painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelSuperiorLayout.createSequentialGroup()
                .addGroup(painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelSuperiorLayout.createSequentialGroup()
                        .addComponent(lblGrauRelevancia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbGrauRelevancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelPrincipal.add(painelSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 790, 100));

        painelSegurancaNivelMaquina1.setBackground(new java.awt.Color(204, 204, 204));
        painelSegurancaNivelMaquina1.setBorder(javax.swing.BorderFactory.createTitledBorder("DETECÇÃO LÓGICA E FÍSICA:"));
        painelSegurancaNivelMaquina1.setOpaque(false);

        paineSegurancaNivelUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("Segurança Nível Usuário:"));
        paineSegurancaNivelUsuario.setOpaque(false);

        lblUsuarioLogadoRepositorio.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        lblUsuarioLogadoRepositorio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblUsuarioLogadoRepositorio.setText("Login");

        lblPerfilForm.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        lblPerfilForm.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPerfilForm.setText("Perfil");

        lblNomeCompletoUsuarioForm.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lblNomeCompletoUsuarioForm.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNomeCompletoUsuarioForm.setText("Usuario");

        lblUsuario.setText("Login");

        jLabel3.setText("Setor:");

        jLabel4.setText("Nome Abreviado:");

        javax.swing.GroupLayout paineSegurancaNivelUsuarioLayout = new javax.swing.GroupLayout(paineSegurancaNivelUsuario);
        paineSegurancaNivelUsuario.setLayout(paineSegurancaNivelUsuarioLayout);
        paineSegurancaNivelUsuarioLayout.setHorizontalGroup(
            paineSegurancaNivelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paineSegurancaNivelUsuarioLayout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(paineSegurancaNivelUsuarioLayout.createSequentialGroup()
                .addGroup(paineSegurancaNivelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paineSegurancaNivelUsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paineSegurancaNivelUsuarioLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paineSegurancaNivelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUsuarioLogadoRepositorio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPerfilForm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNomeCompletoUsuarioForm, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                .addGap(46, 46, 46))
        );
        paineSegurancaNivelUsuarioLayout.setVerticalGroup(
            paineSegurancaNivelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paineSegurancaNivelUsuarioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(paineSegurancaNivelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(lblUsuarioLogadoRepositorio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paineSegurancaNivelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paineSegurancaNivelUsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addGroup(paineSegurancaNivelUsuarioLayout.createSequentialGroup()
                        .addComponent(lblPerfilForm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNomeCompletoUsuarioForm))))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Segurança a Nível de Máquina:"));
        jPanel1.setOpaque(false);

        lblHD1.setText("HD:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Placa Mãe:");

        lblRepositorioHDForm.setBackground(java.awt.Color.black);
        lblRepositorioHDForm.setText("----------------------------");

        lblRepositorioPlacaMaeForm.setBackground(java.awt.Color.black);
        lblRepositorioPlacaMaeForm.setText("----------------------------");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("CPU:");

        repositorioCPUForm.setText("----------------------------");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblHD1)
                        .addGap(49, 49, 49)
                        .addComponent(lblRepositorioHDForm))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(42, 42, 42)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblRepositorioPlacaMaeForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(repositorioCPUForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHD1)
                    .addComponent(lblRepositorioHDForm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblRepositorioPlacaMaeForm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 9, Short.MAX_VALUE)
                        .addComponent(repositorioCPUForm))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout painelSegurancaNivelMaquina1Layout = new javax.swing.GroupLayout(painelSegurancaNivelMaquina1);
        painelSegurancaNivelMaquina1.setLayout(painelSegurancaNivelMaquina1Layout);
        painelSegurancaNivelMaquina1Layout.setHorizontalGroup(
            painelSegurancaNivelMaquina1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelSegurancaNivelMaquina1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(paineSegurancaNivelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        painelSegurancaNivelMaquina1Layout.setVerticalGroup(
            painelSegurancaNivelMaquina1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paineSegurancaNivelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        painelPrincipal.add(painelSegurancaNivelMaquina1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 570, 140));

        painelIdentificacoesIniciais.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados de Identificação :"));
        painelIdentificacoesIniciais.setOpaque(false);

        txtIDAno.setEnabled(false);

        txtDataProtocoloForm.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel6.setText("ID:");

        jLabel7.setText("Data Doc. Protocolado:");

        javax.swing.GroupLayout painelIdentificacoesIniciaisLayout = new javax.swing.GroupLayout(painelIdentificacoesIniciais);
        painelIdentificacoesIniciais.setLayout(painelIdentificacoesIniciaisLayout);
        painelIdentificacoesIniciaisLayout.setHorizontalGroup(
            painelIdentificacoesIniciaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelIdentificacoesIniciaisLayout.createSequentialGroup()
                .addGroup(painelIdentificacoesIniciaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelIdentificacoesIniciaisLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIDAno))
                    .addGroup(painelIdentificacoesIniciaisLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(painelIdentificacoesIniciaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDataProtocoloForm, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        painelIdentificacoesIniciaisLayout.setVerticalGroup(
            painelIdentificacoesIniciaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelIdentificacoesIniciaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelIdentificacoesIniciaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtIDAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataProtocoloForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelPrincipal.add(painelIdentificacoesIniciais, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 210, 130));

        painelFluxoDeOrigem.setBorder(javax.swing.BorderFactory.createTitledBorder("Fluxo de Origem (Interna/Externa):"));
        painelFluxoDeOrigem.setOpaque(false);

        cbOrigem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EMPRESAS(INTERNAS/EXTERNAS)" }));
        cbOrigem.setEnabled(false);
        cbOrigem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbOrigemItemStateChanged(evt);
            }
        });

        txtInteressado.setEnabled(false);

        llbOrigem.setText("Origem:");

        lblEmpresaInteressada.setText("Empresa (Interessada):");

        btnBuscarInteressado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarInteressadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelFluxoDeOrigemLayout = new javax.swing.GroupLayout(painelFluxoDeOrigem);
        painelFluxoDeOrigem.setLayout(painelFluxoDeOrigemLayout);
        painelFluxoDeOrigemLayout.setHorizontalGroup(
            painelFluxoDeOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFluxoDeOrigemLayout.createSequentialGroup()
                .addGroup(painelFluxoDeOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(llbOrigem))
                .addGap(24, 24, 24)
                .addGroup(painelFluxoDeOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFluxoDeOrigemLayout.createSequentialGroup()
                        .addComponent(lblEmpresaInteressada)
                        .addGap(0, 320, Short.MAX_VALUE))
                    .addComponent(txtInteressado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarInteressado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        painelFluxoDeOrigemLayout.setVerticalGroup(
            painelFluxoDeOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFluxoDeOrigemLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(painelFluxoDeOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(llbOrigem)
                    .addComponent(lblEmpresaInteressada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelFluxoDeOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFluxoDeOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtInteressado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscarInteressado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelPrincipal.add(painelFluxoDeOrigem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 790, 100));

        painelFluxoDestino.setBorder(javax.swing.BorderFactory.createTitledBorder("Fluxo de Destino(Interna):"));
        painelFluxoDestino.setOpaque(false);

        lblSetorDestino.setText("Setor de Destino:");

        jLabel8.setText("Endereçado ao Funcionario:");

        txtEnderecadoFuncionario.setEnabled(false);

        btnEnderecadoFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnderecadoFuncionarioActionPerformed(evt);
            }
        });

        btnSetorDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetorDestinoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelFluxoDestinoLayout = new javax.swing.GroupLayout(painelFluxoDestino);
        painelFluxoDestino.setLayout(painelFluxoDestinoLayout);
        painelFluxoDestinoLayout.setHorizontalGroup(
            painelFluxoDestinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFluxoDestinoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelFluxoDestinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSetorDestino)
                    .addGroup(painelFluxoDestinoLayout.createSequentialGroup()
                        .addComponent(txtSetorDestinoRopositorio, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSetorDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(painelFluxoDestinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(painelFluxoDestinoLayout.createSequentialGroup()
                        .addComponent(txtEnderecadoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnderecadoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        painelFluxoDestinoLayout.setVerticalGroup(
            painelFluxoDestinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFluxoDestinoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelFluxoDestinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEnderecadoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelFluxoDestinoLayout.createSequentialGroup()
                        .addGroup(painelFluxoDestinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSetorDestino)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelFluxoDestinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSetorDestino, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(painelFluxoDestinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtEnderecadoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSetorDestinoRopositorio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelPrincipal.add(painelFluxoDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 790, 100));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DOCUMENTO(ITEM PROTOCOLO)", "NUMERO", "DESCREVER ASSUNTO", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        ScroolTabela.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(50);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(0).setMaxWidth(50);
            tabela.getColumnModel().getColumn(1).setMinWidth(250);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(250);
            tabela.getColumnModel().getColumn(1).setMaxWidth(250);
            tabela.getColumnModel().getColumn(2).setMinWidth(80);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(2).setMaxWidth(80);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(400);
            tabela.getColumnModel().getColumn(4).setMinWidth(80);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(4).setMaxWidth(80);
        }

        painelPrincipal.add(ScroolTabela, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 790, 120));

        painelLancamento.setBackground(java.awt.SystemColor.activeCaption);
        painelLancamento.setBorder(javax.swing.BorderFactory.createTitledBorder("Lançamento:"));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Qtde.");

        btnItemDocumentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemDocumentosActionPerformed(evt);
            }
        });

        txtAreaDescricao.setColumns(20);
        txtAreaDescricao.setRows(5);
        jScrollPane1.setViewportView(txtAreaDescricao);

        jLabel10.setText("Item Protocolo(Documentos)");

        jLabel11.setText("Descrever Assunto:");

        javax.swing.GroupLayout painelLancamentoLayout = new javax.swing.GroupLayout(painelLancamento);
        painelLancamento.setLayout(painelLancamentoLayout);
        painelLancamentoLayout.setHorizontalGroup(
            painelLancamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelLancamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelLancamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(painelLancamentoLayout.createSequentialGroup()
                        .addComponent(txtItemDoProtocolo, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnItemDocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(painelLancamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(painelLancamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        painelLancamentoLayout.setVerticalGroup(
            painelLancamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLancamentoLayout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelLancamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelLancamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnItemDocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(painelLancamentoLayout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtItemDoProtocolo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(painelLancamentoLayout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        painelPrincipal.add(painelLancamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 790, 140));

        getContentPane().add(painelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 740));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            //         acaoExcluir();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            //     desabilitarCampos();
            //     desabilitarTodosBotoes();
        }

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAdicionarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseEntered
        btnAdicionar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnAdicionarMouseEntered

    private void btnAdicionarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseExited
        btnAdicionar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnAdicionarMouseExited

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
//        acaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        //      acaoEditar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseEntered
        btnSalvar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnSalvarMouseEntered

    private void btnSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseExited
        btnSalvar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnSalvarMouseExited

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            //        SalvarAdicoesEdicoes();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            //      desabilitarCampos();
            //    desabilitarTodosBotoes();
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
        //   SalvarAdicoesEdicoes();
    }//GEN-LAST:event_btnSalvarKeyPressed

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // acaoBotaoCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
                            //         somarValorTotalVenda();
                        } else {
                            JOptionPane.showMessageDialog(this, "" + "\n Selecione um Registro na Tabela de Vendas", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/systorres/imagens/info.png")));
                        }
                    }
                }

            }

        }

    }//GEN-LAST:event_tabelaMouseClicked

    private void btnItemDocumentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemDocumentosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnItemDocumentosActionPerformed

    private void btnEnderecadoFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnderecadoFuncionarioActionPerformed

        //formListFuncionarioInternoProtocolo
        if (estaFechado(formListFuncionarioInternoProtocolo)) {
            formListFuncionarioInternoProtocolo = new Frm_Protocolar_Lista_Funcionarios_Filtro();
            DeskTop.add(formListFuncionarioInternoProtocolo).setLocation(530, 5);
            formListFuncionarioInternoProtocolo.setTitle("Lista Funcionarios Protocolo");
            formListFuncionarioInternoProtocolo.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formListFuncionarioInternoProtocolo.toFront();
            formListFuncionarioInternoProtocolo.show();

        }


    }//GEN-LAST:event_btnEnderecadoFuncionarioActionPerformed

    private void cbOrigemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbOrigemItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbOrigemItemStateChanged

    private void btnBuscarInteressadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarInteressadoActionPerformed
        if (estaFechado(formListaEmpresa)) {
            formListaEmpresa = new Frm_Protocolar_Lista_Empresas();
            DeskTop.add(formListaEmpresa).setLocation(530, 5);
            formListaEmpresa.setTitle("Lista Empresas Cadastradas");
            formListaEmpresa.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formListaEmpresa.toFront();
            formListaEmpresa.show();

        }
    }//GEN-LAST:event_btnBuscarInteressadoActionPerformed

    private void buscarSetoresTramiteInterno(){
    
              //BOTAO DEPARTAMENTO 
        if (estaFechado(formSetorTramiteInterno)) {
            formSetorTramiteInterno = new Frm_Lista_Setores_Tramite();
            DeskTop.add(formSetorTramiteInterno).setLocation(1, 5);
            formSetorTramiteInterno.setTitle("Cadastro de Setores Tramite Interno");
            formSetorTramiteInterno.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formSetorTramiteInterno.toFront();
            formSetorTramiteInterno.show();

        }

    }
    
    
    private void btnSetorDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetorDestinoActionPerformed
     
        
        
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

        buscarSetoresTramiteInterno();
        
          
                 
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            
        }
        
    }//GEN-LAST:event_btnSetorDestinoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScroolTabela;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnBuscarInteressado;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEnderecadoFuncionario;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnItemDocumentos;
    private javax.swing.JButton btnSalvar;
    public static javax.swing.JButton btnSetorDestino;
    private javax.swing.JComboBox cbGrauRelevancia;
    private javax.swing.JComboBox cbOrigem;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmpresaInteressada;
    private javax.swing.JLabel lblGrauRelevancia;
    private javax.swing.JLabel lblHD1;
    public static javax.swing.JLabel lblNomeCompletoUsuarioForm;
    public static javax.swing.JLabel lblPerfilForm;
    private javax.swing.JLabel lblRepositorioHDForm;
    private javax.swing.JLabel lblRepositorioPlacaMaeForm;
    private javax.swing.JLabel lblSetorDestino;
    private javax.swing.JLabel lblUsuario;
    public static javax.swing.JLabel lblUsuarioLogadoRepositorio;
    private javax.swing.JLabel llbOrigem;
    private javax.swing.JPanel paineSegurancaNivelUsuario;
    private javax.swing.JPanel painelFluxoDeOrigem;
    private javax.swing.JPanel painelFluxoDestino;
    private javax.swing.JPanel painelIdentificacoesIniciais;
    private javax.swing.JPanel painelLancamento;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JPanel painelSegurancaNivelMaquina1;
    private javax.swing.JPanel painelSuperior;
    private javax.swing.JLabel repositorioCPUForm;
    private javax.swing.JTable tabela;
    private javax.swing.JTextArea txtAreaDescricao;
    public static javax.swing.JTextField txtDataProtocoloForm;
    public static javax.swing.JTextField txtEnderecadoFuncionario;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDAno;
    public static javax.swing.JTextField txtInteressado;
    private javax.swing.JTextField txtItemDoProtocolo;
    private javax.swing.JTextField txtNumero;
    public static javax.swing.JTextField txtSetorDestinoRopositorio;
    // End of variables declaration//GEN-END:variables
}
