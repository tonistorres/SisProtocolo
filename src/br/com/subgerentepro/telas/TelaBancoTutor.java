package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.BancoTutorBO;
import br.com.subgerentepro.dao.BancoTutorDAO;
import br.com.subgerentepro.dao.ModeloTutorBancoDAO;
import br.com.subgerentepro.dto.BancoTutorDTO;
import br.com.subgerentepro.dto.FluxoTFDDTO;
import br.com.subgerentepro.dto.ModeloTutorBancoDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.exceptions.ValidacaoException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author DaTorres
 */
public class TelaBancoTutor extends javax.swing.JInternalFrame {

    ModeloTutorBancoDTO modeloTutorBancoDTO = new ModeloTutorBancoDTO();
    BancoTutorDTO bancoTutorDTO = new BancoTutorDTO();
    ModeloTutorBancoDAO modeloTutuorBancoDAO = new ModeloTutorBancoDAO();
    BancoTutorDAO bancoTutorDAO = new BancoTutorDAO();
    BancoTutorBO bancoTutorBO = new BancoTutorBO();

    Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 

    int flag = 0;
    int falgPasse = 0;

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

    public TelaBancoTutor() {
        initComponents();
        lblMaximoCaracterFavorecido.setVisible(false);//campo favorecido
        comportamentoAoCarregarFormUsuario();
        addRowJTable();
        cbNDigitosParaMascara.setSelectedItem("6");
        cbPerfilCliente.setEnabled(false);

        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tabela.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 

    }

    private void gerarCodigoGUI() {

        int codigo = bancoTutorDAO.gerarCodigo();
        int resultado = 0;
        // JOptionPane.showMessageDialog(null, "Codigo:"+ codigo);
        resultado = codigo + 1;
        txtCodigoBanco.setText(String.valueOf(resultado));

    }

    private void comportamentoAoCarregarFormUsuario() {

        //mask do campo conta corrente 
        acaoBotaoMascara();
        /**
         * comportamento campos
         */
        txtCodigoBanco.setEnabled(false);
        txtCodTutor.setEnabled(false);
        txtTutor.setEditable(false);
        txtAgencia.setEnabled(false);
        txtConta.setEnabled(false);
        txtFavorecido.setEnabled(false);
        cbBanco.setEnabled(false);
        cbTipoConta.setEnabled(false);
        cbNDigitosParaMascara.setEnabled(false);

        /**
         * comportamento botoes opções
         */
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnExcluir.setEnabled(false);

        btnBuscarTutor.setEnabled(false);
        btnBuscarPaciente.setEnabled(false);
        btnControMask.setEnabled(false);
        progressBarraPesquisa.setIndeterminate(true);
        progressBarraPesquisaNome.setIndeterminate(true);

        
    }

    private void desabailitarCampos() {
        /**
         * comportamento campos
         */
        txtCodigoBanco.setEnabled(false);
        txtCodTutor.setEnabled(false);
        txtTutor.setEditable(false);
        txtAgencia.setEnabled(false);
        txtConta.setEnabled(false);
        txtFavorecido.setEnabled(false);
        cbBanco.setEnabled(false);
        cbNDigitosParaMascara.setEnabled(false);
        cbTipoConta.setEnabled(false);
        btnControMask.setEnabled(false);
        txtBuscar.setEnabled(true);// esse campo sempre ficará habilitado para pesquisa 

    }

    private void acaoBotaoCancelar() {

        /**
         * Após salvar limpar os campos
         */
        limparCampos();

        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        desabailitarCampos();

        /**
         * ao clicar em btnAdicionar fazemos com que btnSalvar fique habilitado
         * para um eventual salvamento
         */
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnAdicionar.setEnabled(true);
        btnBuscarTutor.setEnabled(false);

        btnExcluir.setEnabled(false);

        JOptionPane.showMessageDialog(null, "Cadastro cancelado com sucesso!!");
    }

    private void acaoAdicionar() {
        gerarCodigoGUI();
        flag = 1;

        /**
         * Campos devem ser ativados
         */
        habilitaCampos();
        /**
         * Limpar os campos para cadastrar
         */
        limparCampos();
        /**
         * Botões que deverão ficar habilitados nesse evento para esse tipo de
         * Formulario
         */
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);

        /**
         * Botões que deverão ficar desabilitados nesse evento para esse tipo de
         * Formulario
         */
        txtBuscar.setEnabled(true);

        //txtProduto.requestFocus();//setar o campo nome Bairro após adicionar
        // txtProduto.setBackground(Color.CYAN);  // altera a cor do fundo
        btnBuscarTutor.requestFocus();
        //holders();
        btnAdicionar.setEnabled(false);

        buscarTutor();

    }

    private void acaoEditar() {
        if (txtCodigoBanco.equals("")) {

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
            habilitaCampos();
            txtCodigoBanco.setEnabled(false);

            txtCodTutor.setEnabled(false);
            txtTutor.setEditable(false);
            btnBuscarTutor.setEnabled(true);
            txtTutor.requestFocus();

            txtConta.setEnabled(true);
            txtAgencia.setEnabled(true);
            txtFavorecido.setEnabled(false);

            cbBanco.setEnabled(true); // o problema não é esse 
            cbTipoConta.setEnabled(true);
            cbNDigitosParaMascara.setEnabled(true);// o problema não é esse 
            btnControMask.setEnabled(true);// o problema não é essa 

            /**
             * ao clicar em btnAdicionar fazemos com que btnSalvar fique
             * habilitado para um eventual salvamento
             */
            btnSalvar.setEnabled(true);
            btnCancelar.setEnabled(true);

            btnAdicionar.setEnabled(false);
            btnExcluir.setEnabled(false);
        }
    }

    private void acaoExcluir() {

        try {

            /*Evento ao ser clicado executa código*/
            int excluir = JOptionPane.showConfirmDialog(null, "Deseja excluir registro?", "Informação", JOptionPane.YES_NO_OPTION);

            if (excluir == JOptionPane.YES_OPTION) {

                //   JOptionPane.showMessageDialog(null, "txtCodigoBanco:" + txtCodigoBanco.getText());
                bancoTutorDTO.setIdBancoDto(Integer.parseInt(txtCodigoBanco.getText()));
                /**
                 * Chamando o método que irá executar a Edição dos Dados em
                 * nosso Banco de Dados
                 */
                bancoTutorBO.ExcluirBO(bancoTutorDTO);

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

    private String formataAgBanco(String pString) {

        String retorno = new String();
        int tamanhoString = pString.length();//calcula o tamanho da String digitada pelo usuário 
        int contarTraco = 0;

        try {

            for (int i = 0; i < tamanhoString; i++) {//um for que irá percorrer toda String 
                if (pString.charAt(i) == ' ') {

                    contarTraco = contarTraco + 1;
                    if (contarTraco == 1) {
                        retorno += '-';
                    } else {
                        throw new ValidacaoException("Valor invalido!");
                    }

                } else {
                    retorno += pString.charAt(i);
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        return retorno;
    }

    private void SalvarAdicoesEdicoes() {

// 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO 
        bancoTutorDTO.setIdBancoDto(Integer.parseInt(txtCodigoBanco.getText()));

        if (!txtCodTutor.getText().equals("")) {
            bancoTutorDTO.setFk_id_tutor(Integer.parseInt(txtCodTutor.getText()));
        }

        if (!txtCodTFD.getText().equals("")) {
            bancoTutorDTO.setFk_id_tfd(Integer.parseInt(txtCodTFD.getText()));
        }

        bancoTutorDTO.setBancoDto((String) cbBanco.getSelectedItem());
        bancoTutorDTO.setAgenciaDto(txtAgencia.getText());
        bancoTutorDTO.setContaCorrenteDto(txtConta.getText());
        bancoTutorDTO.setFavorecidoDto(txtFavorecido.getText());
        bancoTutorDTO.setPerfilClienteDto((String) cbPerfilCliente.getSelectedItem());
        bancoTutorDTO.setTipoContaDto((String) cbTipoConta.getSelectedItem());
        bancoTutorDTO.setPacienteDto(txtPaciente.getText());
        bancoTutorDTO.setTutorDto(txtTutor.getText());
        bancoTutorDTO.setCpfPacienteDto(lblCPFPacienteRepositorio.getText());

        try {

            if ((flag == 1)) {

                boolean retornoVerifcaDuplicidade = bancoTutorDAO.verificaDuplicidade(bancoTutorDTO);//valida se conta corrente já existe
                boolean retornoIndexado = bancoTutorBO.validacaoIndexada(bancoTutorDTO, flag);
                boolean retornoContaCorrente = bancoTutorBO.validaContaCorrente(bancoTutorDTO);

                if ((retornoIndexado == true)
                        && (retornoContaCorrente == true)) {

                    bancoTutorBO.cadastrarBO(bancoTutorDTO);
                    /**
                     * Após salvar limpar os campos
                     */
                    limparCampos();
                    /**
                     * Bloquear campos e Botões
                     */
                    desabailitarCampos();
                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    this.btnSalvar.setEnabled(false);
                    this.btnAdicionar.setEnabled(true);
                    this.btnCancelar.setEnabled(false);

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

                    this.txtCodTFD.setBackground(Color.white);
                    this.txtPaciente.setBackground(Color.white);
                    this.txtCodTutor.setBackground(Color.white);
                    this.txtTutor.setBackground(Color.white);
                    this.btnBuscarTutor.setEnabled(false);
                    this.btnBuscarPaciente.setEnabled(false);

                } else {

                    JOptionPane.showMessageDialog(this, "" + "\n Resgistro já cadastrado.\nSistema Impossibilitou \n a Duplicidade", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    this.txtConta.requestFocus();
                    this.txtConta.setBackground(Color.RED);

                }

            } else {

                boolean retornoVerifcaDuplicidade = bancoTutorDAO.verificaDuplicidade(bancoTutorDTO);//valida se conta corrente já existe
                boolean retornoIndexado = bancoTutorBO.validacaoIndexada(bancoTutorDTO, flag);
                boolean retornoContaCorrente = bancoTutorBO.validaContaCorrente(bancoTutorDTO);

                bancoTutorDTO.setIdBancoDto(Integer.parseInt(txtCodigoBanco.getText()));

                bancoTutorBO.EditarBO(bancoTutorDTO);
                /**
                 * Após salvar limpar os campos
                 */
                limparCampos();
                /**
                 * Bloquear campos e Botões
                 */
                desabailitarCampos();
                /**
                 * Liberar campos necessário para operações após salvamento
                 */
                this.btnAdicionar.setEnabled(true);
                this.btnCancelar.setEnabled(false);
                this.btnSalvar.setEnabled(false);
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

            if (e.getMessage().equals("Campo Codigo Banco e Obrigatorio")) {
                this.txtCodigoBanco.requestFocus();
                this.txtCodigoBanco.setBackground(Color.RED);

                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            }

            br.com.subgerentepro.telas.FrmListaTutor frmListTutor = null;
            if (e.getMessage().equals("Campo Codigo Tutor e Obrigatorio")) {
                this.txtCodTutor.setEnabled(true);
                this.txtCodTutor.setEditable(false);
                this.txtCodTutor.setBackground(Color.red);
                setDefaultCloseOperation(frmListTutor.DISPOSE_ON_CLOSE);

                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            if (e.getMessage().equals("Campo Codigo Paciente e Obrigatorio")) {
                this.txtCodTFD.setEnabled(true);
                this.txtCodTFD.setEditable(false);
                this.txtCodTFD.setBackground(Color.RED);
                buscarPaciente();
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            if (e.getMessage().equals("Campo Agencia e Obrigatorio")) {
                this.txtAgencia.requestFocus();
                this.txtAgencia.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            if (e.getMessage().equals("Campo Conta e Obrigatorio")) {

                this.txtConta.setEditable(true);
                this.txtConta.setEnabled(true);
                this.btnControMask.setEnabled(true);
                this.txtConta.requestFocus();
                this.txtConta.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            if (e.getMessage().equals("Campo Favorecido e Obrigatorio")) {
                this.txtFavorecido.requestFocus();
                this.txtFavorecido.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

        }
    }

    private void tratandoCampoAgencia() {

        //USAR UM OBJETO DO TIPO [JFormattedTextField] 
        //MASCARA: ####-A essa é a mascara usada para Agencia 
        //************************************************************//
        //        EXCELENTE ARTIGO SOBRE FORMATAÇÃO DE MÁSCARAS       //
        //http://preclog.blogspot.com/2007/09/jformattedtextfield.html//
        // ***********************************************************//       
        /**
         * Primeiro criamos uma String com o nome de [mensagem] e capturamos o
         * valor digitado neste campo por meio do método getText() onde ficará
         * armazenado o que foi digitado no campo txtAgencia.getText().
         */
        String mensagem = txtAgencia.getText();

        if (mensagem.equals("    - ")) {
            txtAgencia.setValue(null);
        }

        //dispositivo para liberar a mask Conta Corrente setada pelo botão select após digitação da agencia 
        if (!mensagem.equals("    - ")) {
            //        acaoBotaoMascara();
            txtTutor.requestFocus();
        }

        /**
         * Criamos um contador que será incrementado a medida que o for estiver
         * sendo executado na string passando por cada caracter da mesma e nos
         * dando a posição exata onde se encontra para que possamos fazer uma
         * tomada de decisão o for está
         */
        int cont = 0;

        //inicia-se o for que irá percorrer o tamanho total da variável mensagem que guarda o valor capturado do campo txtAgencia
        for (int i = 0; i < mensagem.length(); i++) {

            cont += 1;//Aqui o contador começa a ser incrementado em mais um a cada passada 

            //Quando o contador estiver na posicao 5 execute o codigo abaixo 
            if (cont == 5) {

                /**
                 * Se na posição 5 do campo txtAgencia não estiver um traço
                 * coloque o traço nesse ponto pois é necessario a existencia do
                 * mesmo
                 */
                if (mensagem.charAt(i) != '-') {

                    /**
                     * o método replace efetua essa mudança de comportamento
                     * nesta String
                     */
                    mensagem = mensagem.replace(mensagem.charAt(i), '-');
                    //em seguida seta a substituição no campo txtAgencia
                    txtAgencia.setText(mensagem);

                } else {
                    txtAgencia.setText(mensagem);
                }

            }

            if (cont == 6) {

                if (mensagem.charAt(i) != 'X'
                        && mensagem.charAt(i) != '0'
                        && mensagem.charAt(i) != '1'
                        && mensagem.charAt(i) != '2'
                        && mensagem.charAt(i) != '3'
                        && mensagem.charAt(i) != '4'
                        && mensagem.charAt(i) != '5'
                        && mensagem.charAt(i) != '6'
                        && mensagem.charAt(i) != '7'
                        && mensagem.charAt(i) != '8'
                        && mensagem.charAt(i) != '9') {

                    mensagem = mensagem.replace(mensagem.charAt(i), 'X');
                    txtAgencia.setText(mensagem);

                } else {

                    txtAgencia.setText(mensagem);
                }

            }
        }
    }

    private void analisarDigitoVerificador() {

        String mensagem = txtConta.getText();
        int numeroDGCombo = Integer.parseInt((String) cbNDigitosParaMascara.getSelectedItem());

        int tamanhoMask = mensagem.length();

        int cont = 0;

        for (int i = 0; i < tamanhoMask; i++) {

            cont += 1;

            // JOptionPane.showMessageDialog(this, "cont: " + cont);
            if (numeroDGCombo == 2) {
                if (cont == 3) { // no combo box ==2

                    //    JOptionPane.showMessageDialog(null, "Caracter Capturado: [" + mensagem.charAt(i) + "]");
                    if (mensagem.charAt(i) != 'X'
                            && mensagem.charAt(i) != '0'
                            && mensagem.charAt(i) != '1'
                            && mensagem.charAt(i) != '2'
                            && mensagem.charAt(i) != '3'
                            && mensagem.charAt(i) != '4'
                            && mensagem.charAt(i) != '5'
                            && mensagem.charAt(i) != '6'
                            && mensagem.charAt(i) != '7'
                            && mensagem.charAt(i) != '8'
                            && mensagem.charAt(i) != '9') {

                        mensagem = mensagem.replace(mensagem.charAt(i), 'X');
                        txtConta.setText(mensagem);

                    } else {

                        txtConta.setText(mensagem);
                    }
                }

            }

            if (numeroDGCombo == 3) {
                if (cont == 4) { // no combo box ==2

                    //      JOptionPane.showMessageDialog(null, "Caracter Capturado: [" + mensagem.charAt(i) + "]");
                    if (mensagem.charAt(i) != 'X'
                            && mensagem.charAt(i) != '0'
                            && mensagem.charAt(i) != '1'
                            && mensagem.charAt(i) != '2'
                            && mensagem.charAt(i) != '3'
                            && mensagem.charAt(i) != '4'
                            && mensagem.charAt(i) != '5'
                            && mensagem.charAt(i) != '6'
                            && mensagem.charAt(i) != '7'
                            && mensagem.charAt(i) != '8'
                            && mensagem.charAt(i) != '9') {

                        mensagem = mensagem.replace(mensagem.charAt(i), 'X');
                        txtConta.setText(mensagem);

                    } else {

                        txtConta.setText(mensagem);
                    }
                }

            }

            if (numeroDGCombo == 4) {
                if (cont == 5) { // no combo box ==2

                    //        JOptionPane.showMessageDialog(null, "Caracter Capturado: [" + mensagem.charAt(i) + "]");
                    if (mensagem.charAt(i) != 'X'
                            && mensagem.charAt(i) != '0'
                            && mensagem.charAt(i) != '1'
                            && mensagem.charAt(i) != '2'
                            && mensagem.charAt(i) != '3'
                            && mensagem.charAt(i) != '4'
                            && mensagem.charAt(i) != '5'
                            && mensagem.charAt(i) != '6'
                            && mensagem.charAt(i) != '7'
                            && mensagem.charAt(i) != '8'
                            && mensagem.charAt(i) != '9') {

                        mensagem = mensagem.replace(mensagem.charAt(i), 'X');
                        txtConta.setText(mensagem);

                    } else {

                        txtConta.setText(mensagem);
                    }
                }

            }

            if (numeroDGCombo == 5) {
                if (cont == 6) { // no combo box ==2

                    //          JOptionPane.showMessageDialog(null, "Caracter Capturado: [" + mensagem.charAt(i) + "]");
                    if (mensagem.charAt(i) != 'X'
                            && mensagem.charAt(i) != '0'
                            && mensagem.charAt(i) != '1'
                            && mensagem.charAt(i) != '2'
                            && mensagem.charAt(i) != '3'
                            && mensagem.charAt(i) != '4'
                            && mensagem.charAt(i) != '5'
                            && mensagem.charAt(i) != '6'
                            && mensagem.charAt(i) != '7'
                            && mensagem.charAt(i) != '8'
                            && mensagem.charAt(i) != '9') {

                        mensagem = mensagem.replace(mensagem.charAt(i), 'X');
                        txtConta.setText(mensagem);

                    } else {

                        txtConta.setText(mensagem);
                    }
                }

            }

            if (numeroDGCombo == 6) {
                if (cont == 7) { // no combo box ==2

                    //            JOptionPane.showMessageDialog(null, "Caracter Capturado: [" + mensagem.charAt(i) + "]");
                    if (mensagem.charAt(i) != 'X'
                            && mensagem.charAt(i) != '0'
                            && mensagem.charAt(i) != '1'
                            && mensagem.charAt(i) != '2'
                            && mensagem.charAt(i) != '3'
                            && mensagem.charAt(i) != '4'
                            && mensagem.charAt(i) != '5'
                            && mensagem.charAt(i) != '6'
                            && mensagem.charAt(i) != '7'
                            && mensagem.charAt(i) != '8'
                            && mensagem.charAt(i) != '9') {

                        mensagem = mensagem.replace(mensagem.charAt(i), 'X');
                        txtConta.setText(mensagem);

                    } else {

                        txtConta.setText(mensagem);
                    }
                }

            }

            if (numeroDGCombo == 7) {
                if (cont == 8) { // no combo box ==2

                    //              JOptionPane.showMessageDialog(null, "Caracter Capturado: [" + mensagem.charAt(i) + "]");
                    if (mensagem.charAt(i) != 'X'
                            && mensagem.charAt(i) != '0'
                            && mensagem.charAt(i) != '1'
                            && mensagem.charAt(i) != '2'
                            && mensagem.charAt(i) != '3'
                            && mensagem.charAt(i) != '4'
                            && mensagem.charAt(i) != '5'
                            && mensagem.charAt(i) != '6'
                            && mensagem.charAt(i) != '7'
                            && mensagem.charAt(i) != '8'
                            && mensagem.charAt(i) != '9') {

                        mensagem = mensagem.replace(mensagem.charAt(i), 'X');
                        txtConta.setText(mensagem);

                    } else {

                        txtConta.setText(mensagem);
                    }
                }

            }

            if (numeroDGCombo == 8) {
                if (cont == 9) { // no combo box ==2

                    //                JOptionPane.showMessageDialog(null, "Caracter Capturado: [" + mensagem.charAt(i) + "]");
                    if (mensagem.charAt(i) != 'X'
                            && mensagem.charAt(i) != '0'
                            && mensagem.charAt(i) != '1'
                            && mensagem.charAt(i) != '2'
                            && mensagem.charAt(i) != '3'
                            && mensagem.charAt(i) != '4'
                            && mensagem.charAt(i) != '5'
                            && mensagem.charAt(i) != '6'
                            && mensagem.charAt(i) != '7'
                            && mensagem.charAt(i) != '8'
                            && mensagem.charAt(i) != '9') {

                        mensagem = mensagem.replace(mensagem.charAt(i), 'X');
                        txtConta.setText(mensagem);

                    } else {

                        txtConta.setText(mensagem);
                    }
                }

            }

            if (numeroDGCombo == 9) {
                if (cont == 10) { // no combo box ==2

//                    JOptionPane.showMessageDialog(null, "Caracter Capturado: [" + mensagem.charAt(i) + "]");
                    if (mensagem.charAt(i) != 'X'
                            && mensagem.charAt(i) != '0'
                            && mensagem.charAt(i) != '1'
                            && mensagem.charAt(i) != '2'
                            && mensagem.charAt(i) != '3'
                            && mensagem.charAt(i) != '4'
                            && mensagem.charAt(i) != '5'
                            && mensagem.charAt(i) != '6'
                            && mensagem.charAt(i) != '7'
                            && mensagem.charAt(i) != '8'
                            && mensagem.charAt(i) != '9') {

                        mensagem = mensagem.replace(mensagem.charAt(i), 'X');
                        txtConta.setText(mensagem);

                    } else {

                        txtConta.setText(mensagem);
                    }
                }

            }

            if (numeroDGCombo == 10) {
                if (cont == 11) { // no combo box ==2

                    //                  JOptionPane.showMessageDialog(null, "Caracter Capturado: [" + mensagem.charAt(i) + "]");
                    if (mensagem.charAt(i) != 'X'
                            && mensagem.charAt(i) != '0'
                            && mensagem.charAt(i) != '1'
                            && mensagem.charAt(i) != '2'
                            && mensagem.charAt(i) != '3'
                            && mensagem.charAt(i) != '4'
                            && mensagem.charAt(i) != '5'
                            && mensagem.charAt(i) != '6'
                            && mensagem.charAt(i) != '7'
                            && mensagem.charAt(i) != '8'
                            && mensagem.charAt(i) != '9') {

                        mensagem = mensagem.replace(mensagem.charAt(i), 'X');
                        txtConta.setText(mensagem);

                    } else {

                        txtConta.setText(mensagem);
                    }
                }

            }

        }
    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<BancoTutorDTO> list;

        try {

            list = (ArrayList<BancoTutorDTO>) bancoTutorDAO.listarTodos();

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[7];//são 06 colunas 

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdBancoDto();
                rowData[1] = list.get(i).getPacienteDto().toString();
                rowData[2] = list.get(i).getCpfPacienteDto().toString();
                rowData[3] = list.get(i).getTutorDto().toString();
                rowData[4] = list.get(i).getBancoDto().toString();
                rowData[5] = list.get(i).getAgenciaDto().toString();
                rowData[6] = list.get(i).getContaCorrenteDto().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(55);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(70);
            //tabela.getColumnModel().getColumn(5).setPreferredWidth(280);
            //INFORMAÇÃO BARRA INFORMATIVA
            lblLinhaInformativa.setText("Pesquisa Efetuada Com Sucesso!");
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setFont(f);
            txtBuscar.requestFocus();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }
    }

    private void aoClicarTabelaSelecionarParaEditar() {
        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/

        int codigoCapturado = (int) tabela.getValueAt(tabela.getSelectedRow(), 0);

        /**
         * Logo abaixo temos um ArrayList que junta atributos da classe Empresa
         * e Banco em um objeto do tipo list que será usada para capturar tais
         * valores vindo de um método chamado filtrarPorCodigo existente na
         * camada DAO dessa aplicação dentro de modeloempresaBancoDAO essee
         * método retorna uma lista com atributos das duas classe Empresa e
         * Banco
         */
        ArrayList<ModeloTutorBancoDTO> list;

        try {
            /**
             * nesse ponto temos o objeto list recebendo os atributos setados
             * pelo metodo filtrarPorCodigo o interessante aqui é que é passado
             * um parâmetro codigo(int) onde a lista em questão só retornará um
             * único elemento pertencente ao banco pesquisado o do proprio
             * código capturado
             */
            list = (ArrayList<ModeloTutorBancoDTO>) modeloTutuorBancoDAO.filtrarPorCodigo(codigoCapturado);

            //aqui fazemos uma pequena verificaçao antes de prosseguirmos onde 
            //verificamos se a list não for nula, ou seja, conter algo entao
            //ele efetua tudo que se encontra dentro do if caso contrário dentro
            //do else tem um JOPANE com uma mensagem dentro dizendo que não encontrou 
            //nenhum registro isso que será executado no caso da list ser null 
            if (list != null) {

                /**
                 * String.valueOf() pega um Valor Inteiro e transforma em String
                 * e seta em txtIDMedico que é um campo do tipo texto
                 */
                for (int i = 0; i < list.size(); i++) {

                    int conta = (list.get(i).getModeloBancoTutorDto().getContaCorrenteDto().length() - 1);
                    // JOptionPane.showMessageDialog(null, "Conta" + conta);
                    cbNDigitosParaMascara.setSelectedItem(Integer.toString(conta));

                    acaoBotaoMascara();

                    txtCodigoBanco.setText(String.valueOf(list.get(i).getModeloBancoTutorDto().getIdBancoDto()));

                    //Tutor
                    txtCodTutor.setText(String.valueOf(list.get(i).getModeloTutorDto().getIdDto()));
                    txtTutor.setText(list.get(i).getModeloTutorDto().getNomeDto());
                    //paciente
                    txtCodTFD.setText(String.valueOf(list.get(i).getModeloPacienteDTO().getIdPacienteDto()));
                    txtPaciente.setText(list.get(i).getModeloPacienteDTO().getNomePacienteDto());

                    cbBanco.setSelectedItem((String) list.get(i).getModeloBancoTutorDto().getBancoDto());
                    cbTipoConta.setSelectedItem((String) list.get(i).getModeloBancoTutorDto().getTipoContaDto());
                    txtAgencia.setText(list.get(i).getModeloBancoTutorDto().getAgenciaDto());
                    txtConta.setText(list.get(i).getModeloBancoTutorDto().getContaCorrenteDto());//contar os caraceteres 
                    txtFavorecido.setText(list.get(i).getModeloBancoTutorDto().getFavorecidoDto());

                }
                /**
                 * Liberar os botões abaixo
                 */

                btnExcluir.setEnabled(true);
                btnSalvar.setEnabled(true);
                btnAdicionar.setEnabled(false);
                btnCancelar.setEnabled(true);
                btnBuscarPaciente.setEnabled(true);

                txtAgencia.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar()\n" + ex.getMessage());
        }

    }

    private void pesquisarUsuario() {

        String pesquisarUsuario = txtBuscar.getText();

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<BancoTutorDTO> list;

        try {

            list = (ArrayList<BancoTutorDTO>) bancoTutorDAO.filtrarUsuarioPesqRapida(pesquisarUsuario);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[7];//são 06 colunas 

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdBancoDto();
                rowData[1] = list.get(i).getPacienteDto().toString();
                rowData[2] = list.get(i).getCpfPacienteDto().toString();
                rowData[3] = list.get(i).getTutorDto().toString();
                rowData[4] = list.get(i).getBancoDto().toString();
                rowData[5] = list.get(i).getAgenciaDto().toString();
                rowData[6] = list.get(i).getContaCorrenteDto().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(55);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(70);
            //tabela.getColumnModel().getColumn(5).setPreferredWidth(280);
            //INFORMAÇÃO BARRA INFORMATIVA
            lblLinhaInformativa.setText("Pesquisa Efetuada Com Sucesso!");
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setFont(f);
            txtBuscar.requestFocus();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void limparCampos() {
        //    txtCodigoBanco.setText("");
        txtCodTutor.setText("");
        txtTutor.setText("");
        txtAgencia.setText("");
        txtConta.setText("");
        txtFavorecido.setText("");
        txtBuscar.setText("");
        txtCodTFD.setText("");
        txtPaciente.setText("");
    }

    private void habilitaCampos() {

        txtCodigoBanco.setEnabled(false);

        txtCodTutor.setEnabled(false);
        txtTutor.setEditable(false);

        txtCodTFD.setEnabled(false);
        txtPaciente.setEditable(false);

        txtConta.setEnabled(false);
        txtAgencia.setEnabled(true);

        //o campo favorecido é falso pois é auto completado no momento da escolha
        //do paciente 
        txtFavorecido.setEnabled(false);

        btnBuscarTutor.setEnabled(true);
        cbBanco.setEnabled(true);
        cbTipoConta.setEnabled(true);
        cbNDigitosParaMascara.setEnabled(true);
        btnControMask.setEnabled(true);

    }

    private void acaoMascaraEventoEdicao() {

        //    txtConta.setEnabled(true);
        //******************************************************************************************
        //Contribuição: RenataFA Fórum:GUJ                                                          *
        //http://www.guj.com.br/t/mudar-formato-de-jformattedtextfield-em-tempo-de-execucao/35713/15 *
        //********************************************************************************************* 
        //  txtConta.setValue(null); // aqui está o famoso "pulo do gato"
        int numeroEscolido = Integer.parseInt((String) cbNDigitosParaMascara.getSelectedItem());

        switch (numeroEscolido) {
            case 2:

                MaskFormatter maskFormatter2;
                try {
                    maskFormatter2 = new MaskFormatter("#-A");
                    //  txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter2));

                } catch (ParseException ex) {

                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 3:
                MaskFormatter maskFormatter3;
                try {
                    maskFormatter3 = new MaskFormatter("##-A");
                    //txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter3));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 4:
                MaskFormatter maskFormatter4;
                try {
                    maskFormatter4 = new MaskFormatter("###-A");
                    //txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter4));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 5:
                MaskFormatter maskFormatter5;
                try {
                    maskFormatter5 = new MaskFormatter("####-A");
                    //txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter5));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 6:
                MaskFormatter maskFormatter6;
                try {
                    maskFormatter6 = new MaskFormatter("#####-A");
                    //txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter6));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;
            case 7:
                MaskFormatter maskFormatter7;
                try {
                    maskFormatter7 = new MaskFormatter("######-A");
                    // txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter7));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 8:

                MaskFormatter maskFormatter8;
                try {
                    maskFormatter8 = new MaskFormatter("#######-A");
                    //txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter8));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 9:

                MaskFormatter maskFormatter9;
                try {
                    maskFormatter9 = new MaskFormatter("########-A");
                    //txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter9));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 10:
                MaskFormatter maskFormatter10;
                try {
                    maskFormatter10 = new MaskFormatter("#########-A");
                    //txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter10));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

        }

    }

    private void acaoBotaoMascara() {

        // JOptionPane.showMessageDialog(this, "Flag Principal:" + flag);
        falgPasse = flag;
        flag = 1;
        if (flag == 1) {

            txtConta.setEnabled(true);

        }

        flag = falgPasse;
        //******************************************************************************************
        //Contribuição: RenataFA Fórum:GUJ                                                          *
        //http://www.guj.com.br/t/mudar-formato-de-jformattedtextfield-em-tempo-de-execucao/35713/15 *
        //********************************************************************************************* 
        txtConta.setValue(null); // aqui está o famoso "pulo do gato"

        int numeroEscolido = Integer.parseInt((String) cbNDigitosParaMascara.getSelectedItem());

        switch (numeroEscolido) {
            case 2:

                MaskFormatter maskFormatter2;
                try {
                    maskFormatter2 = new MaskFormatter("#-A");
                    txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter2));

                } catch (ParseException ex) {

                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 3:
                MaskFormatter maskFormatter3;
                try {
                    maskFormatter3 = new MaskFormatter("##-A");
                    txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter3));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 4:
                MaskFormatter maskFormatter4;
                try {
                    maskFormatter4 = new MaskFormatter("###-A");
                    txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter4));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 5:
                MaskFormatter maskFormatter5;
                try {
                    maskFormatter5 = new MaskFormatter("####-A");
                    txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter5));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 6:
                MaskFormatter maskFormatter6;
                try {
                    maskFormatter6 = new MaskFormatter("#####-A");
                    txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter6));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;
            case 7:
                MaskFormatter maskFormatter7;
                try {
                    maskFormatter7 = new MaskFormatter("######-A");
                    txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter7));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 8:

                MaskFormatter maskFormatter8;
                try {
                    maskFormatter8 = new MaskFormatter("#######-A");
                    txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter8));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 9:

                MaskFormatter maskFormatter9;
                try {
                    maskFormatter9 = new MaskFormatter("########-A");
                    txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter9));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

            case 10:
                MaskFormatter maskFormatter10;
                try {
                    maskFormatter10 = new MaskFormatter("#########-A");
                    txtConta.setFormatterFactory(new DefaultFormatterFactory(maskFormatter10));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "" + "\n Erro!", ex.getMessage(), 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                }
                break;

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        btnExcluir = new javax.swing.JButton();
        btnBuscarTutor = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        scrollTable = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        txtCodigoBanco = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        txtTutor = new javax.swing.JTextField();
        txtCodTutor = new javax.swing.JTextField();
        lblTutor = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblBancos = new javax.swing.JLabel();
        cbBanco = new javax.swing.JComboBox<String>();
        lblAgencia = new javax.swing.JLabel();
        txtAgencia = new javax.swing.JFormattedTextField();
        lblNDigito = new javax.swing.JLabel();
        cbNDigitosParaMascara = new javax.swing.JComboBox<String>();
        lblTipoConta = new javax.swing.JLabel();
        cbTipoConta = new javax.swing.JComboBox();
        btnControMask = new javax.swing.JButton();
        lblConta = new javax.swing.JLabel();
        txtConta = new javax.swing.JFormattedTextField();
        cbPerfilCliente = new javax.swing.JComboBox();
        lblPerfilCliente = new javax.swing.JLabel();
        lblFavorecido = new javax.swing.JLabel();
        lblContador = new javax.swing.JLabel();
        lblMaximoCaracterFavorecido = new javax.swing.JLabel();
        txtFavorecido = new javax.swing.JTextField();
        lblImagemNuvemBackEnd = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        txtCodTFD = new javax.swing.JTextField();
        lblTutor1 = new javax.swing.JLabel();
        txtPaciente = new javax.swing.JTextField();
        lblCpfPacienteTFD = new javax.swing.JLabel();
        btnBuscarPaciente = new javax.swing.JButton();
        lblCPFPacienteRepositorio = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtCPFBusca = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        btnBuscarCPF = new javax.swing.JButton();
        lblImagemNuvemBackEndNome = new javax.swing.JLabel();
        progressBarraPesquisaNome = new javax.swing.JProgressBar();
        btnPesquisarPorNome = new javax.swing.JButton();
        lblLinhaInformativa = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Banco Empresas");
        setName("formBancoEmpresa"); // NOI18N
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        painelPrincipal.setForeground(new java.awt.Color(255, 0, 0));
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/lixeira.png"))); // NOI18N
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
        painelPrincipal.add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 45, 45));

        btnBuscarTutor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/busca32x32.png"))); // NOI18N
        btnBuscarTutor.setToolTipText("Buscar Empresas ");
        btnBuscarTutor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnBuscarTutorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnBuscarTutorFocusLost(evt);
            }
        });
        btnBuscarTutor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarTutorMouseEntered(evt);
            }
        });
        btnBuscarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTutorActionPerformed(evt);
            }
        });
        painelPrincipal.add(btnBuscarTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 32, 32));

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
        painelPrincipal.add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 45, 45));

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
        painelPrincipal.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 45, 45));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Paciente(TFD)", "CPF(TFD)", "Tutor", "Banco", "Ag", "Conta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
        scrollTable.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setResizable(false);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(55);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(5).setResizable(false);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(6).setResizable(false);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(70);
        }

        painelPrincipal.add(scrollTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, 600, 330));

        txtCodigoBanco.setEditable(false);
        txtCodigoBanco.setBackground(new java.awt.Color(255, 255, 204));
        txtCodigoBanco.setEnabled(false);
        painelPrincipal.add(txtCodigoBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 30, 30));

        lblCodigo.setBackground(new java.awt.Color(255, 255, 210));
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodigo.setText("ID:");
        painelPrincipal.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 30, 30));

        txtTutor.setBackground(new java.awt.Color(255, 255, 204));
        txtTutor.setToolTipText("Nome da Empresa a qual será vinculada a Conta ");
        txtTutor.setPreferredSize(new java.awt.Dimension(69, 30));
        txtTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTutorActionPerformed(evt);
            }
        });
        painelPrincipal.add(txtTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 240, 30));

        txtCodTutor.setBackground(new java.awt.Color(255, 255, 204));
        painelPrincipal.add(txtCodTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 30, 30));

        lblTutor.setText("Tutor:");
        painelPrincipal.add(lblTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

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
        painelPrincipal.add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 45, 45));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados Bancários:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 13))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBancos.setText("Lista de Bancos:");
        jPanel1.add(lblBancos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 20));

        cbBanco.setBackground(new java.awt.Color(255, 255, 204));
        cbBanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Banco do Brasil S.A.", "Banco Itaú S.A.", "Banco Santander (Brasil) S.A.", "Banco Real S.A. (antigo)", "Itaú Unibanco Holding S.A.", "Banco Bradesco S.A.", "Banco Citibank S.A.", "HSBC Bank Brasil S.A. – Banco Múltiplo", "Caixa Econômica Federal", "Banco Mercantil do Brasil S.A.", "Banco Rural S.A.", "Banco Safra S.A.", "Banco Rendimento S.A.", "Banco do Nordeste" }));
        cbBanco.setToolTipText("Escolha o Banco ");
        cbBanco.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbBancoItemStateChanged(evt);
            }
        });
        cbBanco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbBancoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbBancoFocusLost(evt);
            }
        });
        cbBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBancoActionPerformed(evt);
            }
        });
        jPanel1.add(cbBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 220, 30));

        lblAgencia.setText("Agencia:");
        jPanel1.add(lblAgencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, -1));

        txtAgencia.setBackground(new java.awt.Color(255, 255, 204));
        try {
            txtAgencia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-A")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtAgencia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAgenciaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAgenciaFocusLost(evt);
            }
        });
        txtAgencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAgenciaActionPerformed(evt);
            }
        });
        txtAgencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAgenciaKeyPressed(evt);
            }
        });
        jPanel1.add(txtAgencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 80, 30));

        lblNDigito.setText("Nº DG Conta");
        jPanel1.add(lblNDigito, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        cbNDigitosParaMascara.setBackground(new java.awt.Color(255, 255, 204));
        cbNDigitosParaMascara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cbNDigitosParaMascara.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbNDigitosParaMascaraItemStateChanged(evt);
            }
        });
        cbNDigitosParaMascara.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbNDigitosParaMascaraFocusGained(evt);
            }
        });
        jPanel1.add(cbNDigitosParaMascara, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 70, 30));

        lblTipoConta.setText("Tipo de Conta:");
        jPanel1.add(lblTipoConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, -1, -1));

        cbTipoConta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Corrente", "Poupanca" }));
        jPanel1.add(cbTipoConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 100, 30));

        btnControMask.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/mascaraBanco.png"))); // NOI18N
        btnControMask.setPreferredSize(new java.awt.Dimension(32, 32));
        btnControMask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnControMaskActionPerformed(evt);
            }
        });
        jPanel1.add(btnControMask, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, -1, 40));

        lblConta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConta.setText("Conta:");
        jPanel1.add(lblConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 50, 20));

        txtConta.setBackground(new java.awt.Color(255, 255, 204));
        txtConta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtContaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtContaFocusLost(evt);
            }
        });
        txtConta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContaActionPerformed(evt);
            }
        });
        txtConta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContaKeyPressed(evt);
            }
        });
        jPanel1.add(txtConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 80, 30));

        cbPerfilCliente.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cbPerfilCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TUTOR" }));
        cbPerfilCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPerfilClienteActionPerformed(evt);
            }
        });
        jPanel1.add(cbPerfilCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 80, 30));

        lblPerfilCliente.setText("Perfil Cliente:");
        jPanel1.add(lblPerfilCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        lblFavorecido.setText("Favorecido:");
        jPanel1.add(lblFavorecido, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, -1, -1));

        lblContador.setForeground(new java.awt.Color(51, 153, 255));
        jPanel1.add(lblContador, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 170, 20));

        lblMaximoCaracterFavorecido.setForeground(java.awt.Color.red);
        lblMaximoCaracterFavorecido.setText("Atingiu Máxmio Caracter Permitido");
        jPanel1.add(lblMaximoCaracterFavorecido, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 170, -1));

        txtFavorecido.setBackground(new java.awt.Color(255, 255, 204));
        txtFavorecido.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        txtFavorecido.setToolTipText("Digite o nome do favorecido e abreviações se for caso ");
        txtFavorecido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFavorecidoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFavorecidoFocusLost(evt);
            }
        });
        txtFavorecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFavorecidoActionPerformed(evt);
            }
        });
        txtFavorecido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFavorecidoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFavorecidoKeyReleased(evt);
            }
        });
        jPanel1.add(txtFavorecido, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 230, 30));

        painelPrincipal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 350, 200));

        lblImagemNuvemBackEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        painelPrincipal.add(lblImagemNuvemBackEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 50, 50, 40));
        painelPrincipal.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 90, 50, -1));

        txtCodTFD.setBackground(new java.awt.Color(255, 255, 204));
        painelPrincipal.add(txtCodTFD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 30, 30));

        lblTutor1.setText("Paciente");
        painelPrincipal.add(lblTutor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        txtPaciente.setBackground(new java.awt.Color(255, 255, 204));
        txtPaciente.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        txtPaciente.setToolTipText("Nome da Empresa a qual será vinculada a Conta ");
        txtPaciente.setPreferredSize(new java.awt.Dimension(69, 30));
        txtPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPacienteActionPerformed(evt);
            }
        });
        painelPrincipal.add(txtPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 200, -1));

        lblCpfPacienteTFD.setText("CPF:");
        painelPrincipal.add(lblCpfPacienteTFD, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, -1, -1));

        btnBuscarPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/busca32x32.png"))); // NOI18N
        btnBuscarPaciente.setToolTipText("Buscar Empresas ");
        btnBuscarPaciente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnBuscarPacienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnBuscarPacienteFocusLost(evt);
            }
        });
        btnBuscarPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarPacienteMouseEntered(evt);
            }
        });
        btnBuscarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPacienteActionPerformed(evt);
            }
        });
        painelPrincipal.add(btnBuscarPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 32, 32));

        lblCPFPacienteRepositorio.setBackground(java.awt.Color.pink);
        lblCPFPacienteRepositorio.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblCPFPacienteRepositorio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelPrincipal.add(lblCPFPacienteRepositorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, 70, 30));

        txtBuscar.setBackground(new java.awt.Color(102, 204, 255));
        txtBuscar.setToolTipText("Pesquisar Registro (Contas das Empresas)");
        txtBuscar.setPreferredSize(new java.awt.Dimension(100, 30));
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
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
        painelPrincipal.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 220, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Pesquisar Paciente (Nome):");
        painelPrincipal.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, -1, -1));

        txtCPFBusca.setBackground(new java.awt.Color(102, 204, 255));
        try {
            txtCPFBusca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCPFBusca.setPreferredSize(new java.awt.Dimension(51, 30));
        txtCPFBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCPFBuscaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCPFBuscaFocusLost(evt);
            }
        });
        txtCPFBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPFBuscaActionPerformed(evt);
            }
        });
        txtCPFBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCPFBuscaKeyPressed(evt);
            }
        });
        painelPrincipal.add(txtCPFBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 70, 145, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("CPF:");
        painelPrincipal.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 50, -1, -1));

        btnBuscarCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnBuscarCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnBuscarCPFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnBuscarCPFFocusLost(evt);
            }
        });
        btnBuscarCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCPFActionPerformed(evt);
            }
        });
        painelPrincipal.add(btnBuscarCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 70, 32, 33));

        lblImagemNuvemBackEndNome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemConectada.png"))); // NOI18N
        painelPrincipal.add(lblImagemNuvemBackEndNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, 50, 40));
        painelPrincipal.add(progressBarraPesquisaNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, 50, -1));

        btnPesquisarPorNome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesquisarPorNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesquisarPorNomeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnPesquisarPorNomeFocusLost(evt);
            }
        });
        painelPrincipal.add(btnPesquisarPorNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(605, 70, 32, 32));

        lblLinhaInformativa.setText("Barra Informativa");
        painelPrincipal.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 14, 580, 20));

        getContentPane().add(painelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        acaoExcluir();

    }//GEN-LAST:event_btnExcluirActionPerformed
    br.com.subgerentepro.telas.FrmListaTutor formLista;

    private void buscarTutor() {

        if (estaFechado(formLista)) {
            formLista = new FrmListaTutor();
            DeskTop.add(formLista).setLocation(530, 5);
            formLista.setTitle("Lista Tutores Cadastrados");
            formLista.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formLista.toFront();
            formLista.show();

        }

    }

    br.com.subgerentepro.telas.FrmListaPaciente formListaPaciente;

    private void buscarPaciente() {

        if (estaFechado(formListaPaciente)) {
            formListaPaciente = new FrmListaPaciente();
            DeskTop.add(formListaPaciente).setLocation(480, 5);
            formListaPaciente.setTitle("Lista Pacientes Cadastrados");
            formListaPaciente.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formListaPaciente.toFront();
            formListaPaciente.show();

        }

    }


    private void btnBuscarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTutorActionPerformed
        buscarTutor();
    }//GEN-LAST:event_btnBuscarTutorActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        SalvarAdicoesEdicoes();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        acaoBotaoCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTutorActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Iniciando Pesquisa no Banco de Dados...");
            btnPesquisarPorNome.requestFocus();

        }

    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnBuscarTutorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarTutorMouseEntered

    }//GEN-LAST:event_btnBuscarTutorMouseEntered

    private void btnBuscarTutorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarTutorFocusGained
        btnBuscarTutor.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnBuscarTutorFocusGained

    private void btnBuscarTutorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarTutorFocusLost
        btnBuscarTutor.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnBuscarTutorFocusLost

    private void cbBancoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbBancoFocusGained
        cbBanco.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_cbBancoFocusGained

    private void cbBancoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbBancoFocusLost
        cbBanco.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_cbBancoFocusLost

    private void txtFavorecidoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFavorecidoFocusGained

        cbBanco.setBackground(Color.WHITE);
        txtTutor.setBackground(Color.WHITE);
        txtCodTutor.setBackground(Color.WHITE);
        txtCodigoBanco.setBackground(Color.WHITE);
        txtAgencia.setBackground(Color.WHITE);
        txtConta.setBackground(Color.WHITE);
        txtFavorecido.setBackground(Color.yellow);
    }//GEN-LAST:event_txtFavorecidoFocusGained

    private void txtFavorecidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFavorecidoFocusLost
        txtFavorecido.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_txtFavorecidoFocusLost

    private void btnSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseEntered
//        btnSalvar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnSalvarMouseEntered

    private void btnSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseExited
//        btnSalvar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnSalvarMouseExited

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed

        SalvarAdicoesEdicoes();
    }//GEN-LAST:event_btnSalvarKeyPressed

    private void txtAgenciaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAgenciaFocusLost
        tratandoCampoAgencia();
    }//GEN-LAST:event_txtAgenciaFocusLost

    private void txtContaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContaFocusGained

        cbBanco.setBackground(Color.WHITE);
        txtTutor.setBackground(Color.WHITE);
        txtCodTutor.setBackground(Color.WHITE);
        txtCodigoBanco.setBackground(Color.WHITE);
        txtAgencia.setBackground(Color.WHITE);
        txtConta.setBackground(Color.yellow);

    }//GEN-LAST:event_txtContaFocusGained

    private void txtContaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContaFocusLost
        txtConta.setBackground(new Color(240, 240, 240));
        txtConta.setEnabled(false);

        //analizador mascara
        //   analisarDigitoVerificador();
        //https://forum.scriptcase.com.br/index.php?topic=14020.0
    }//GEN-LAST:event_txtContaFocusLost

    private void cbNDigitosParaMascaraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbNDigitosParaMascaraItemStateChanged
        txtConta.setEnabled(false);

    }//GEN-LAST:event_cbNDigitosParaMascaraItemStateChanged

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void cbBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBancoActionPerformed

    private void txtAgenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAgenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAgenciaActionPerformed

    private void btnControMaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnControMaskActionPerformed

        acaoBotaoMascara();
        // JOptionPane.showMessageDialog(this, "flag saida" + flag);

    }//GEN-LAST:event_btnControMaskActionPerformed

    private void txtFavorecidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFavorecidoKeyReleased

        txtFavorecido.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtFavorecido.getText()));

        //https://www.youtube.com/watch?v=DbuqV7Pq_nQ
        int d = txtFavorecido.getText().length();
        lblContador.setText("Número Caracter Digitado: " + String.valueOf(d));
        String k = txtFavorecido.getText();
        if (d > 49) {
            String stringNova = k.substring(0, k.length() - 1);
            txtFavorecido.setText(stringNova);
            lblMaximoCaracterFavorecido.setVisible(true);
        } else {
            lblMaximoCaracterFavorecido.setVisible(false);

        }

    }//GEN-LAST:event_txtFavorecidoKeyReleased

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

        acaoEditar();

        //   flag=1;
        aoClicarTabelaSelecionarParaEditar();

        flag = flag;
        //  JOptionPane.showMessageDialog(this, "Flag Rotornada Saida:"+flag);

    }//GEN-LAST:event_tabelaMouseClicked

    private void txtFavorecidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFavorecidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFavorecidoActionPerformed

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

    private void cbBancoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbBancoItemStateChanged
        txtAgencia.requestFocus();
        txtAgencia.setBackground(Color.yellow);
    }//GEN-LAST:event_cbBancoItemStateChanged


    private void txtAgenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAgenciaKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            txtConta.requestFocus();
        }
    }//GEN-LAST:event_txtAgenciaKeyPressed

    private void txtAgenciaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAgenciaFocusGained

        cbBanco.setBackground(Color.WHITE);
        txtTutor.setBackground(Color.WHITE);
        txtCodTutor.setBackground(Color.WHITE);
        txtCodigoBanco.setBackground(Color.WHITE);
        txtAgencia.setBackground(Color.YELLOW);


    }//GEN-LAST:event_txtAgenciaFocusGained

    private void txtContaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContaKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {
            txtFavorecido.requestFocus();
        }
    }//GEN-LAST:event_txtContaKeyPressed

    private void txtFavorecidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFavorecidoKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            btnSalvar.requestFocus();
        }
    }//GEN-LAST:event_txtFavorecidoKeyPressed

    private void cbPerfilClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPerfilClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPerfilClienteActionPerformed

    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained
        btnSalvar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnSalvarFocusGained

    private void cbNDigitosParaMascaraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbNDigitosParaMascaraFocusGained

        txtConta.setEnabled(false);

    }//GEN-LAST:event_cbNDigitosParaMascaraFocusGained

    private void txtPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPacienteActionPerformed

    private void btnBuscarPacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarPacienteFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarPacienteFocusGained

    private void btnBuscarPacienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarPacienteFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarPacienteFocusLost

    private void btnBuscarPacienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarPacienteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarPacienteMouseEntered

    private void btnBuscarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPacienteActionPerformed
        buscarPaciente();
    }//GEN-LAST:event_btnBuscarPacienteActionPerformed

    private void txtContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContaActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        lblLinhaInformativa.setText("Digite o nome a ser Pesquisado.");
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.BLUE);
        txtBuscar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        txtBuscar.setBackground(Color.WHITE);
        lblLinhaInformativa.setText("");


    }//GEN-LAST:event_txtBuscarFocusLost

    private void btnAdicionarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusGained
        btnAdicionar.setBackground(Color.YELLOW);
        lblLinhaInformativa.setText("");
    }//GEN-LAST:event_btnAdicionarFocusGained

    private void btnAdicionarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusLost
        btnAdicionar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnAdicionarFocusLost

    private void btnSalvarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusLost
        btnSalvar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnSalvarFocusLost

    private void btnCancelarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCancelarFocusGained
        btnCancelar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnCancelarFocusGained

    private void btnCancelarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCancelarFocusLost
        btnSalvar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnCancelarFocusLost

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained
        btnExcluir.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnExcluirFocusGained

    private void btnExcluirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusLost
        btnExcluir.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnExcluirFocusLost

    private void txtCPFBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFBuscaFocusGained
        txtCPFBusca.setBackground(Color.YELLOW);
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Informe o Nº do CPF a Ser Pesquisado.");
        lblLinhaInformativa.setForeground(Color.BLUE);
    }//GEN-LAST:event_txtCPFBuscaFocusGained

    private void txtCPFBuscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFBuscaFocusLost
        txtCPFBusca.setBackground(Color.WHITE);
        lblLinhaInformativa.setText("");
    }//GEN-LAST:event_txtCPFBuscaFocusLost

    private void txtCPFBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFBuscaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFBuscaActionPerformed

    private void txtCPFBuscaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFBuscaKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            if (!txtCPFBusca.getText().equals("   .   .   -  ")) {
                
                lblLinhaInformativa.setForeground(Color.BLUE);
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setText("Iniciando Pesquisa no Banco de Dados...");

                btnBuscarCPF.requestFocus();

            } else {
                JOptionPane.showMessageDialog(null, "Você Digitou um Valor Nulo.");
            }

        }

    }//GEN-LAST:event_txtCPFBuscaKeyPressed

    private void acaoBotaoPesquisarCPF() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {
            acaoPesquisarCPF();

        } else {

            erroViaEmail("Sem Conexão com a Internet", "acaoBotaoPesquisar()- Camada GUI:\n"
                    + "ViewGeradorDocsProtocolo");
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }
    }

    private void acaoPesquisarCPF() {

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

                pesquisarCPF();

            } else {
                addRowJTable();
            }

        } else {

            erroViaEmail("Sem Conexão com a Internet", "acaoBTNPesquisar()-Camada GUI\n"
                    + "ViewGeradorDocsProtocolo");

            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }

    }

    private void pesquisarCPF() {

        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(txtCPFBusca.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<BancoTutorDTO> list;
        try {

            list = (ArrayList<BancoTutorDTO>) bancoTutorDAO.filtrarPesqRapidaCPF(pesquisar);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[7];//são 06 colunas 

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdBancoDto();
                rowData[1] = list.get(i).getPacienteDto().toString();
                rowData[2] = list.get(i).getCpfPacienteDto().toString();
                rowData[3] = list.get(i).getTutorDto().toString();
                rowData[4] = list.get(i).getBancoDto().toString();
                rowData[5] = list.get(i).getAgenciaDto().toString();
                rowData[6] = list.get(i).getContaCorrenteDto().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(55);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(70);
            //tabela.getColumnModel().getColumn(5).setPreferredWidth(280);
            //INFORMAÇÃO BARRA INFORMATIVA
            lblLinhaInformativa.setText("Pesquisa Efetuada Com Sucesso!");
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setFont(f);
            txtCPFBusca.requestFocus();
            txtCPFBusca.setText("");

        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "pesquisar()-Camada GUI- ViewGeradorDocsProtocolo.java\n"
                    + "pesquisar pelo idCustomizado para gerar o Recibo de Processo ");
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


    private void btnBuscarCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarCPFFocusGained

        btnBuscarCPF.setBackground(Color.YELLOW);

        if (!txtCPFBusca.getText().equals("   .   .   -  ")) {
            acaoBotaoPesquisarCPF();
        } else {
            JOptionPane.showMessageDialog(null, "Campo Vazio");
            txtCPFBusca.requestFocus();
        }

    }//GEN-LAST:event_btnBuscarCPFFocusGained

    private void btnBuscarCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarCPFFocusLost
        btnBuscarCPF.setBackground(Color.WHITE);        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarCPFFocusLost

    private void btnBuscarCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarCPFActionPerformed

    private void btnPesquisarPorNomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarPorNomeFocusGained

        btnPesquisarPorNome.setBackground(Color.YELLOW);

        try {
            int numeroLinhas = tabela.getRowCount();

            if (numeroLinhas > 0) {

                while (tabela.getModel().getRowCount() > 0) {
                    ((DefaultTableModel) tabela.getModel()).removeRow(0);

                }

                pesquisarUsuario();

            } else {
                addRowJTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

    }//GEN-LAST:event_btnPesquisarPorNomeFocusGained

    private void btnPesquisarPorNomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarPorNomeFocusLost
        btnPesquisarPorNome.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarPorNomeFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnBuscarCPF;
    public static javax.swing.JButton btnBuscarPaciente;
    private javax.swing.JButton btnBuscarTutor;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnControMask;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisarPorNome;
    private javax.swing.JButton btnSalvar;
    public static javax.swing.JComboBox<String> cbBanco;
    private javax.swing.JComboBox<String> cbNDigitosParaMascara;
    public static javax.swing.JComboBox cbPerfilCliente;
    private javax.swing.JComboBox cbTipoConta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAgencia;
    private javax.swing.JLabel lblBancos;
    public static javax.swing.JLabel lblCPFPacienteRepositorio;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblConta;
    private javax.swing.JLabel lblContador;
    private javax.swing.JLabel lblCpfPacienteTFD;
    private javax.swing.JLabel lblFavorecido;
    private javax.swing.JLabel lblImagemNuvemBackEnd;
    private javax.swing.JLabel lblImagemNuvemBackEndNome;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblMaximoCaracterFavorecido;
    private javax.swing.JLabel lblNDigito;
    private javax.swing.JLabel lblPerfilCliente;
    private javax.swing.JLabel lblTipoConta;
    private javax.swing.JLabel lblTutor;
    private javax.swing.JLabel lblTutor1;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JProgressBar progressBarraPesquisaNome;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTable tabela;
    private javax.swing.JFormattedTextField txtAgencia;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JFormattedTextField txtCPFBusca;
    public static javax.swing.JTextField txtCodTFD;
    public static javax.swing.JTextField txtCodTutor;
    private javax.swing.JTextField txtCodigoBanco;
    private javax.swing.JFormattedTextField txtConta;
    public static javax.swing.JTextField txtFavorecido;
    public static javax.swing.JTextField txtPaciente;
    public static javax.swing.JTextField txtTutor;
    // End of variables declaration//GEN-END:variables

}
