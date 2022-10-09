package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.BancoEmpresaBO;
import br.com.subgerentepro.dao.BancoEmpresaDAO;
import br.com.subgerentepro.dao.ModeloEmpresaBancoDAO;
import br.com.subgerentepro.dto.BancoEmpresaDTO;
import br.com.subgerentepro.dto.ModeloEmpresaBancoDTO;
import br.com.subgerentepro.exceptions.ValidacaoException;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;

import java.awt.Color;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author DaTorres
 */
public class TelaBancoEmpresa extends javax.swing.JInternalFrame {

    ModeloEmpresaBancoDTO modeloempresaBancoDTO = new ModeloEmpresaBancoDTO();
    ModeloEmpresaBancoDAO modeloempresaBancoDAO = new ModeloEmpresaBancoDAO();
    BancoEmpresaDTO bancoEmpresaDTO = new BancoEmpresaDTO();
    BancoEmpresaDAO bancoEmpresaDAO = new BancoEmpresaDAO();
    BancoEmpresaBO bancoEmpresaBO = new BancoEmpresaBO();
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

    public TelaBancoEmpresa() {
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

        int codigo = bancoEmpresaDAO.gerarCodigo();
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
        txtCodEmpresa.setEnabled(false);
        txtEmpresa.setEditable(false);
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
        btnEditar.setEnabled(false);
        btnBuscarEmpresa.setEnabled(false);
        btnControMask.setEnabled(false);

    }

    private void desabailitarCampos() {
        /**
         * comportamento campos
         */
        txtCodigoBanco.setEnabled(false);
        txtCodEmpresa.setEnabled(false);
        txtEmpresa.setEditable(false);
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
        btnBuscarEmpresa.setEnabled(false);
        btnEditar.setEnabled(false);
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
        btnEditar.setEnabled(false);

        //txtProduto.requestFocus();//setar o campo nome Bairro após adicionar
        // txtProduto.setBackground(Color.CYAN);  // altera a cor do fundo
        btnBuscarEmpresa.requestFocus();
        //holders();
        btnAdicionar.setEnabled(false);

        buscarEmpresa();

    }

    private void acaoEditar() {
        if (txtEmpresa.equals("")) {

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
            txtCodEmpresa.setEnabled(false);
            txtEmpresa.setEditable(false);
            txtConta.setEnabled(true);
            txtAgencia.setEnabled(true);
            txtFavorecido.setEnabled(true);
            btnBuscarEmpresa.setEnabled(true);
            cbBanco.setEnabled(true); // o problema não é esse 
            cbTipoConta.setEnabled(true);
            cbNDigitosParaMascara.setEnabled(true);// o problema não é esse 
            btnControMask.setEnabled(true);// o problema não é essa 
            txtEmpresa.requestFocus();

            /**
             * ao clicar em btnAdicionar fazemos com que btnSalvar fique
             * habilitado para um eventual salvamento
             */
            btnSalvar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnEditar.setEnabled(false);
            btnAdicionar.setEnabled(false);
            btnExcluir.setEnabled(false);
        }
    }

    private void acaoExcluir() {

        try {

            /*Evento ao ser clicado executa código*/
            int excluir = JOptionPane.showConfirmDialog(null, "Deseja excluir registro?", "Informação", JOptionPane.YES_NO_OPTION);

            if (excluir == JOptionPane.YES_OPTION) {
                bancoEmpresaDTO.setIdBancoDto(Integer.parseInt(txtCodigoBanco.getText()));
                /**
                 * Chamando o método que irá executar a Edição dos Dados em
                 * nosso Banco de Dados
                 */
                bancoEmpresaBO.ExcluirBO(bancoEmpresaDTO);

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
        btnEditar.setEnabled(false);
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

      //  JOptionPane.showMessageDialog(this, "valor da flag na hora de salvar -->>"+flag);

// 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO 
        bancoEmpresaDTO.setIdBancoDto(Integer.parseInt(txtCodigoBanco.getText()));

        if (!txtCodEmpresa.getText().equals("")) {
            /**
             * Em Dados do tipo Integer devemos fazer sempre esse tratamento
             * antes de setá-os e um retorno de validação paa saber se o dado
             * foi setado
             */
            bancoEmpresaDTO.setFkEmpresaDto(Integer.parseInt(txtCodEmpresa.getText()));
        }
        bancoEmpresaDTO.setBancoDto((String) cbBanco.getSelectedItem());
        bancoEmpresaDTO.setAgenciaDto(txtAgencia.getText());
        bancoEmpresaDTO.setContaCorrenteDto(txtConta.getText());
        bancoEmpresaDTO.setFavorecidoDto(txtFavorecido.getText());
        bancoEmpresaDTO.setPerfilClienteDto((String) cbPerfilCliente.getSelectedItem());
        bancoEmpresaDTO.setTipoContaDto((String) cbTipoConta.getSelectedItem());

        try {

            if ((flag == 1)) {

                boolean retornoVerifcaDuplicidade = bancoEmpresaDAO.verificaDuplicidade(bancoEmpresaDTO);//valida se conta corrente já existe
                boolean retornoCampoCodigoBanco = bancoEmpresaBO.validaCodigoBanco(bancoEmpresaDTO);//valida numero se diferente de zero  
                boolean retornoCampoFkCodigoEmpresa = bancoEmpresaBO.validaCodigoEmpresa(bancoEmpresaDTO);//valida numero se diferente de zero
                boolean retornoAgencia = bancoEmpresaBO.validaAgencia(bancoEmpresaDTO);
                boolean retornContaCorrente = bancoEmpresaBO.validaContaCorrente(bancoEmpresaDTO);
                boolean retornoFavorecido = bancoEmpresaBO.valavoidaFrecido(bancoEmpresaDTO);

                if ((retornoCampoCodigoBanco == true)
                        && (retornoVerifcaDuplicidade == false)
                        && (retornoCampoFkCodigoEmpresa == true)
                        && (retornoAgencia == true)
                        && (retornContaCorrente == true)
                        && retornoFavorecido) {

                    bancoEmpresaBO.cadastrarBO(bancoEmpresaDTO);
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
                    btnSalvar.setEnabled(false);
                    btnAdicionar.setEnabled(true);
                    btnCancelar.setEnabled(false);

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

                    JOptionPane.showMessageDialog(this, "" + "\n Resgistro já cadastrado.\nSistema Impossibilitou \n a Duplicidade", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    txtConta.requestFocus();
                    txtConta.setBackground(Color.RED);

                }

            } else {

                bancoEmpresaDTO.setIdBancoDto(Integer.parseInt(txtCodigoBanco.getText()));
                boolean retornoVerifcaDuplicidade = bancoEmpresaDAO.verificaDuplicidade(bancoEmpresaDTO);//valida se conta corrente já existe
                boolean retornoCampoCodigoBanco = bancoEmpresaBO.validaCodigoBanco(bancoEmpresaDTO);//valida numero se diferente de zero  
                boolean retornoCampoFkCodigoEmpresa = bancoEmpresaBO.validaCodigoEmpresa(bancoEmpresaDTO);//valida numero se diferente de zero
                boolean retornoAgencia = bancoEmpresaBO.validaAgencia(bancoEmpresaDTO);
                boolean retornContaCorrente = bancoEmpresaBO.validaContaCorrente(bancoEmpresaDTO);
                boolean retornoFavorecido = bancoEmpresaBO.valavoidaFrecido(bancoEmpresaDTO);

                bancoEmpresaBO.EditarBO(bancoEmpresaDTO);
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

            if (e.getMessage().equals("Campo Codigo Banco e Obrigatorio")) {
                txtCodigoBanco.requestFocus();
                txtCodigoBanco.setBackground(Color.RED);

                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            }

            if (e.getMessage().equals("Campo Codigo Empresa e Obrigatorio")) {
                txtCodEmpresa.setEnabled(true);
                txtCodEmpresa.setEditable(false);
                txtCodEmpresa.setBackground(Color.RED);
                buscarEmpresa();
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            if (e.getMessage().equals("Campo Agencia e Obrigatorio")) {
                txtAgencia.requestFocus();
                txtAgencia.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            if (e.getMessage().equals("Campo Conta e Obrigatorio")) {
                txtConta.requestFocus();
                txtConta.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            if (e.getMessage().equals("Campo Favorecido e Obrigatorio")) {
                txtFavorecido.requestFocus();
                txtFavorecido.setBackground(Color.RED);
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
            txtEmpresa.requestFocus();
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

        ArrayList<ModeloEmpresaBancoDTO> list;

        try {

            list = (ArrayList<ModeloEmpresaBancoDTO>) modeloempresaBancoDAO.listarTodos();

            Object rowData[] = new Object[5];//são 06 colunas 

            for (int i = 0; i < list.size(); i++) {
                //JOptionPane.showMessageDialog(this,"Empresa"+list.get(i).getModeloEmpresaDto().getEmpresaDto());
                rowData[0] = list.get(i).getModeloBancoEmpresaDto().getIdBancoDto();
                rowData[1] = list.get(i).getModeloEmpresaDto().getEmpresaDto().toString();
                rowData[2] = list.get(i).getModeloBancoEmpresaDto().getBancoDto().toString();
                rowData[3] = list.get(i).getModeloBancoEmpresaDto().getAgenciaDto().toString();
                rowData[4] = list.get(i).getModeloBancoEmpresaDto().getContaCorrenteDto().toString();
                //  rowData[5] = list.get(i).getModeloBancoEmpresaDto().getFavorecidoDto().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(55);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            //redimensionando tamanho da coluna do botões Editar e Deletar 
            tabela.getColumnModel().getColumn(3).setPreferredWidth(60);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(70);
            //   tabela.getColumnModel().getColumn(5).setPreferredWidth(280);
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
        ArrayList<ModeloEmpresaBancoDTO> list;

        try {
            /**
             * nesse ponto temos o objeto list recebendo os atributos setados
             * pelo metodo filtrarPorCodigo o interessante aqui é que é passado
             * um parâmetro codigo(int) onde a lista em questão só retornará um
             * único elemento pertencente ao banco pesquisado o do proprio
             * código capturado
             */
            list = (ArrayList<ModeloEmpresaBancoDTO>) modeloempresaBancoDAO.filtrarPorCodigo(codigoCapturado);

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

                    int conta = (list.get(i).getModeloBancoEmpresaDto().getContaCorrenteDto().length() - 1);
                    // JOptionPane.showMessageDialog(null, "Conta" + conta);
                    cbNDigitosParaMascara.setSelectedItem(Integer.toString(conta));

                    acaoBotaoMascara();

                    txtCodigoBanco.setText(String.valueOf(list.get(i).getModeloBancoEmpresaDto().getIdBancoDto()));
                    txtCodEmpresa.setText(String.valueOf(list.get(i).getModeloEmpresaDto().getIdEmpresaDto()));
                    txtEmpresa.setText(list.get(i).getModeloEmpresaDto().getEmpresaDto());
                    cbBanco.setSelectedItem((String) list.get(i).getModeloBancoEmpresaDto().getBancoDto());
                    cbTipoConta.setSelectedItem((String) list.get(i).getModeloBancoEmpresaDto().getTipoContaDto());
                    txtAgencia.setText(list.get(i).getModeloBancoEmpresaDto().getAgenciaDto());
                    txtConta.setText(list.get(i).getModeloBancoEmpresaDto().getContaCorrenteDto());//contar os caraceteres 
                    txtFavorecido.setText(list.get(i).getModeloBancoEmpresaDto().getFavorecidoDto());

                }
                /**
                 * Liberar os botões abaixo
                 */
                btnEditar.setEnabled(false);
                btnExcluir.setEnabled(true);
                btnSalvar.setEnabled(true);
                btnAdicionar.setEnabled(false);
                btnCancelar.setEnabled(true);

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

        ArrayList<ModeloEmpresaBancoDTO> list;

        try {

            list = (ArrayList<ModeloEmpresaBancoDTO>) modeloempresaBancoDAO.filtrarUsuarioPesqRapida(pesquisarUsuario);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[5];//são 06 colunas 

            for (int i = 0; i < list.size(); i++) {
                //JOptionPane.showMessageDialog(this,"Empresa"+list.get(i).getModeloEmpresaDto().getEmpresaDto());
                rowData[0] = list.get(i).getModeloBancoEmpresaDto().getIdBancoDto();
                rowData[1] = list.get(i).getModeloEmpresaDto().getEmpresaDto().toString();
                rowData[2] = list.get(i).getModeloBancoEmpresaDto().getBancoDto().toString();
                rowData[3] = list.get(i).getModeloBancoEmpresaDto().getAgenciaDto().toString();
                rowData[4] = list.get(i).getModeloBancoEmpresaDto().getContaCorrenteDto().toString();
                // rowData[5] = list.get(i).getModeloBancoEmpresaDto().getFavorecidoDto().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(55);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            //redimensionando tamanho da coluna do botões Editar e Deletar 
            tabela.getColumnModel().getColumn(3).setPreferredWidth(60);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(70);
            //tabela.getColumnModel().getColumn(5).setPreferredWidth(280);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void limparCampos() {
        //    txtCodigoBanco.setText("");
        txtCodEmpresa.setText("");
        txtEmpresa.setText("");
        txtAgencia.setText("");
        txtConta.setText("");
        txtFavorecido.setText("");
        txtBuscar.setText("");
    }

    private void habilitaCampos() {
        txtCodigoBanco.setEnabled(false);
        txtCodEmpresa.setEnabled(false);
        txtEmpresa.setEditable(false);
        txtConta.setEnabled(false);
        txtAgencia.setEnabled(true);
        txtFavorecido.setEnabled(true);
        btnBuscarEmpresa.setEnabled(true);
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
        btnBuscarEmpresa = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        scrollTable = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        txtCodigoBanco = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        txtEmpresa = new javax.swing.JTextField();
        txtCodEmpresa = new javax.swing.JTextField();
        lblEmpresa = new javax.swing.JLabel();
        lblAgencia = new javax.swing.JLabel();
        lblConta = new javax.swing.JLabel();
        cbBanco = new javax.swing.JComboBox<String>();
        lblBancos = new javax.swing.JLabel();
        txtFavorecido = new javax.swing.JTextField();
        lblFavorecido = new javax.swing.JLabel();
        painelPesquisaRapida = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        txtAgencia = new javax.swing.JFormattedTextField();
        cbNDigitosParaMascara = new javax.swing.JComboBox<String>();
        lblNDigito = new javax.swing.JLabel();
        txtConta = new javax.swing.JFormattedTextField();
        btnAdicionar = new javax.swing.JButton();
        btnControMask = new javax.swing.JButton();
        lblMaximoCaracterFavorecido = new javax.swing.JLabel();
        lblContador = new javax.swing.JLabel();
        cbPerfilCliente = new javax.swing.JComboBox();
        lblPerfilCliente = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        cbTipoConta = new javax.swing.JComboBox();
        lblTipoConta = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Banco Empresas");
        setName("formBancoEmpresa"); // NOI18N
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        painelPrincipal.setForeground(new java.awt.Color(255, 0, 0));
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        painelPrincipal.add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 64, 64));

        btnBuscarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/busca32x32.png"))); // NOI18N
        btnBuscarEmpresa.setToolTipText("Buscar Empresas ");
        btnBuscarEmpresa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnBuscarEmpresaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnBuscarEmpresaFocusLost(evt);
            }
        });
        btnBuscarEmpresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarEmpresaMouseEntered(evt);
            }
        });
        btnBuscarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarEmpresaActionPerformed(evt);
            }
        });
        painelPrincipal.add(btnBuscarEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 32, 32));

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/salvar.png"))); // NOI18N
        btnSalvar.setToolTipText("Salvar Registro");
        btnSalvar.setEnabled(false);
        btnSalvar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/botoes/salvaRolover.png"))); // NOI18N
        btnSalvar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnSalvarFocusGained(evt);
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
        painelPrincipal.add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 64, 64));

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
        painelPrincipal.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 64, 64));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Empresa", "Banco", "Ag", "Conta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            tabela.getColumnModel().getColumn(0).setMinWidth(34);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(0).setMaxWidth(34);
            tabela.getColumnModel().getColumn(1).setMinWidth(200);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(1).setMaxWidth(200);
            tabela.getColumnModel().getColumn(2).setMinWidth(120);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(2).setMaxWidth(120);
            tabela.getColumnModel().getColumn(3).setMinWidth(60);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(60);
            tabela.getColumnModel().getColumn(3).setMaxWidth(60);
            tabela.getColumnModel().getColumn(4).setMinWidth(120);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(4).setMaxWidth(120);
        }

        painelPrincipal.add(scrollTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 520, 250));

        txtCodigoBanco.setEditable(false);
        txtCodigoBanco.setBackground(new java.awt.Color(255, 255, 204));
        painelPrincipal.add(txtCodigoBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 70, 30));

        lblCodigo.setText("Código:");
        painelPrincipal.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 70, 30));

        txtEmpresa.setBackground(new java.awt.Color(255, 255, 204));
        txtEmpresa.setToolTipText("Nome da Empresa a qual será vinculada a Conta ");
        txtEmpresa.setPreferredSize(new java.awt.Dimension(69, 30));
        txtEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpresaActionPerformed(evt);
            }
        });
        painelPrincipal.add(txtEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 340, 30));

        txtCodEmpresa.setBackground(new java.awt.Color(255, 255, 204));
        painelPrincipal.add(txtCodEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 40, 30));

        lblEmpresa.setText("Empresa:");
        painelPrincipal.add(lblEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, -1, -1));

        lblAgencia.setText("Agencia:");
        painelPrincipal.add(lblAgencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, -1, -1));

        lblConta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConta.setText("Conta:");
        painelPrincipal.add(lblConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 50, 30));

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
        painelPrincipal.add(cbBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 180, 30));

        lblBancos.setText("Lista de Bancos:");
        painelPrincipal.add(lblBancos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 20));

        txtFavorecido.setBackground(new java.awt.Color(255, 255, 204));
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
        painelPrincipal.add(txtFavorecido, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 480, 40));

        lblFavorecido.setText("Favorecido:");
        painelPrincipal.add(lblFavorecido, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        painelPesquisaRapida.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisa Rápida:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 13), new java.awt.Color(0, 153, 204))); // NOI18N
        painelPesquisaRapida.setLayout(new java.awt.BorderLayout());

        txtBuscar.setBackground(new java.awt.Color(102, 204, 255));
        txtBuscar.setToolTipText("Pesquisar Registro (Contas das Empresas)");
        txtBuscar.setPreferredSize(new java.awt.Dimension(100, 30));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        painelPesquisaRapida.add(txtBuscar, java.awt.BorderLayout.PAGE_END);

        painelPrincipal.add(painelPesquisaRapida, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 480, 70));

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
        painelPrincipal.add(txtAgencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 80, 30));

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
        painelPrincipal.add(cbNDigitosParaMascara, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 70, 30));

        lblNDigito.setText("Nº DG Conta");
        painelPrincipal.add(lblNDigito, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, -1, -1));

        txtConta.setBackground(new java.awt.Color(255, 255, 204));
        txtConta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtContaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtContaFocusLost(evt);
            }
        });
        txtConta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContaKeyPressed(evt);
            }
        });
        painelPrincipal.add(txtConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 110, 30));

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
        painelPrincipal.add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 64, 64));

        btnControMask.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/mascaraBanco.png"))); // NOI18N
        btnControMask.setPreferredSize(new java.awt.Dimension(32, 32));
        btnControMask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnControMaskActionPerformed(evt);
            }
        });
        painelPrincipal.add(btnControMask, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 32, 40));

        lblMaximoCaracterFavorecido.setForeground(java.awt.Color.red);
        lblMaximoCaracterFavorecido.setText("Atingiu Máxmio Caracter Permitido");
        painelPrincipal.add(lblMaximoCaracterFavorecido, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, 200, -1));

        lblContador.setForeground(new java.awt.Color(51, 153, 255));
        painelPrincipal.add(lblContador, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 210, 20));

        cbPerfilCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EMPRESA", "TFD" }));
        cbPerfilCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPerfilClienteActionPerformed(evt);
            }
        });
        painelPrincipal.add(cbPerfilCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, 130, 30));

        lblPerfilCliente.setText("Perfil Cliente:");
        painelPrincipal.add(lblPerfilCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 250, -1, -1));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/Editar.jpeg"))); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        painelPrincipal.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 64, 64));

        cbTipoConta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Corrente", "Poupanca" }));
        painelPrincipal.add(cbTipoConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 190, 100, 30));

        lblTipoConta.setText("Tipo de Conta:");
        painelPrincipal.add(lblTipoConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, -1, -1));

        getContentPane().add(painelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        acaoExcluir();

    }//GEN-LAST:event_btnExcluirActionPerformed
    br.com.subgerentepro.telas.FrmListaEmpresa formListaEmpresa;

    private void buscarEmpresa() {

        if (estaFechado(formListaEmpresa)) {
            formListaEmpresa = new FrmListaEmpresa();
            DeskTop.add(formListaEmpresa).setLocation(530, 5);
            formListaEmpresa.setTitle("Lista Empresas Cadastradas");
            formListaEmpresa.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formListaEmpresa.toFront();
            formListaEmpresa.show();

        }

    }


    private void btnBuscarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarEmpresaActionPerformed
        buscarEmpresa();
    }//GEN-LAST:event_btnBuscarEmpresaActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        SalvarAdicoesEdicoes();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        acaoBotaoCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmpresaActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed


    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            pesquisarUsuario();

        } else {
            addRowJTable();
        }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnBuscarEmpresaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarEmpresaMouseEntered

    }//GEN-LAST:event_btnBuscarEmpresaMouseEntered

    private void btnBuscarEmpresaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarEmpresaFocusGained
        btnBuscarEmpresa.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnBuscarEmpresaFocusGained

    private void btnBuscarEmpresaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBuscarEmpresaFocusLost
        btnBuscarEmpresa.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnBuscarEmpresaFocusLost

    private void cbBancoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbBancoFocusGained
        cbBanco.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_cbBancoFocusGained

    private void cbBancoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbBancoFocusLost
        cbBanco.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_cbBancoFocusLost

    private void txtFavorecidoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFavorecidoFocusGained

        cbBanco.setBackground(Color.WHITE);
        txtEmpresa.setBackground(Color.WHITE);
        txtCodEmpresa.setBackground(Color.WHITE);
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
        txtEmpresa.setBackground(Color.WHITE);
        txtCodEmpresa.setBackground(Color.WHITE);
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

        desabailitarCampos();
        acaoEditar();

     //   flag=1;
        aoClicarTabelaSelecionarParaEditar();
        btnEditar.setEnabled(true);
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
        txtEmpresa.setBackground(Color.WHITE);
        txtCodEmpresa.setBackground(Color.WHITE);
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

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        acaoEditar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained

    }//GEN-LAST:event_btnSalvarFocusGained

    private void cbNDigitosParaMascaraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbNDigitosParaMascaraFocusGained

        txtConta.setEnabled(false);

    }//GEN-LAST:event_cbNDigitosParaMascaraFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnBuscarEmpresa;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnControMask;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnSalvar;
    public static javax.swing.JComboBox<String> cbBanco;
    private javax.swing.JComboBox<String> cbNDigitosParaMascara;
    public static javax.swing.JComboBox cbPerfilCliente;
    private javax.swing.JComboBox cbTipoConta;
    private javax.swing.JLabel lblAgencia;
    private javax.swing.JLabel lblBancos;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblConta;
    private javax.swing.JLabel lblContador;
    private javax.swing.JLabel lblEmpresa;
    private javax.swing.JLabel lblFavorecido;
    private javax.swing.JLabel lblMaximoCaracterFavorecido;
    private javax.swing.JLabel lblNDigito;
    private javax.swing.JLabel lblPerfilCliente;
    private javax.swing.JLabel lblTipoConta;
    private javax.swing.JPanel painelPesquisaRapida;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTable tabela;
    private javax.swing.JFormattedTextField txtAgencia;
    private javax.swing.JTextField txtBuscar;
    public static javax.swing.JTextField txtCodEmpresa;
    private javax.swing.JTextField txtCodigoBanco;
    private javax.swing.JFormattedTextField txtConta;
    public static javax.swing.JTextField txtEmpresa;
    private javax.swing.JTextField txtFavorecido;
    // End of variables declaration//GEN-END:variables

}
