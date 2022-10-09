package br.com.subgerentepro.telas;

import br.com.subgerentepro.bo.EmpresaBO;
import br.com.subgerentepro.dao.EmpresaDAO;
import br.com.subgerentepro.dto.EmpresaDTO;
import br.com.subgerentepro.dto.ModeloEmpresaUfCidadeBairroDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.DeskTop;
import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import static br.com.subgerentepro.telas.TelaPrincipal.lblPerfil;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaEmpresas extends javax.swing.JInternalFrame {

    EmpresaDTO empresaDTO = new EmpresaDTO();
    EmpresaDAO empresaDAO = new EmpresaDAO();
    EmpresaBO empresaBO = new EmpresaBO();

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

    public TelaEmpresas() {
        initComponents();
        comportamentoAoCarregarFormUsuario();
        addRowJTable();
        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tabela.getTableHeader().setDefaultRenderer(new br.com.subgerentepro.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new br.com.subgerentepro.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 

    }

    private void gerarCodigoGUI() {

        int codigo = empresaDAO.gerarCodigo();
        int resultado = 0;
        //     JOptionPane.showMessageDialog(null, "Codigo:"+ codigo);
        resultado = codigo + 1;
        txtIdEmpresa.setText(String.valueOf(resultado));

    }

    private void frontEnd() {

        Font fCamposTxt = new Font("Tahoma", Font.BOLD, 9);

        txtIdEmpresa.setFont(fCamposTxt);
        txtCodEstado.setFont(fCamposTxt);
        txtCodCidade.setFont(fCamposTxt);
        txtCNPJ.setFont(fCamposTxt);
        txtEmpresa.setFont(fCamposTxt);
        txtEstado.setFont(fCamposTxt);
        txtCidade.setFont(fCamposTxt);
        txtBairro.setFont(fCamposTxt);
        txtEndereco.setFont(fCamposTxt);
        txtComplemento.setFont(fCamposTxt);
        txtNumero.setFont(fCamposTxt);
        txtFoneComercial.setFont(fCamposTxt);
        txtCelular.setFont(fCamposTxt);
        txtContato.setFont(fCamposTxt);
        cbVinculo.setFont(fCamposTxt);
        txtBuscar.setFont(fCamposTxt);

        txtBairro.setEnabled(false);

    }

    private void personalizandoBarraInfoPesquisaInicio() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Presione ENTER  iniciar a Pesquisa MYSQL");
    }

    private void personalizandoBarraInfoPesquisaTermino() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setForeground(Color.ORANGE);
        lblLinhaInformativa.setText("Pesquisa Terminada.");
        txtBuscar.requestFocus();
        txtBuscar.setBackground(Color.CYAN);
    }

    private void comportamentoAoCarregarFormUsuario() {

        /**
         * comportamento campos
         */
        this.txtIdEmpresa.setEnabled(false);
        this.txtCNPJ.setEnabled(false);

        /*Propriedade Enabled*/
        this.txtEmpresa.setEnabled(false);
        this.txtEstado.setEnabled(false);
        this.txtCidade.setEnabled(false);
        //this.txtBairro.setEnabled(false);
        this.txtCodEstado.setEnabled(false);
        this.txtCodCidade.setEnabled(false);
        this.btnValidaCNPJ.setEnabled(false);

        this.txtEndereco.setEnabled(false);
        this.txtComplemento.setEnabled(false);
        this.txtNumero.setEnabled(false);
        this.txtFoneComercial.setEnabled(false);
        this.txtCelular.setEnabled(false);
        this.txtContato.setEnabled(false);

        /**
         * comportamento botoes Principais(genericos)
         */
        this.btnSalvar.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.btnExcluir.setEnabled(false);
        this.btnEditar.setEnabled(false);

        /**
         * Botões próprios do formulario
         */
        this.btnEstadoBusca.setEnabled(false);
        this.btnBairroBusca.setEnabled(false);
        this.btnCidadeBusca.setEnabled(false);

        //barras de progressos evento load
        // progressBarValidaCnpj.setVisible(false);
        progressBarFrontBack.setIndeterminate(true);
        frontEnd();

    }

    private void habilitaCampos() {
        this.txtCNPJ.setEnabled(true);
        this.txtEmpresa.setEnabled(true);
        this.txtEmpresa.setEditable(true);
        this.btnEstadoBusca.setEnabled(true);
        this.btnCidadeBusca.setEnabled(true);
        this.btnBairroBusca.setEnabled(true);
        this.txtEndereco.setEnabled(true);
        this.txtComplemento.setEnabled(true);
        this.txtNumero.setEnabled(true);
        this.txtFoneComercial.setEnabled(true);
        this.txtCelular.setEnabled(true);
        this.txtContato.setEnabled(true);
        this.txtBuscar.setEnabled(true);
    }

    private void desabailitarCampos() {
        /**
         * comportamento campos
         */
        /**
         * comportamento campos
         */
        this.txtIdEmpresa.setEnabled(false);
        this.txtCNPJ.setEnabled(false);

        /*Propriedade Editable */
        this.txtEmpresa.setEnabled(false);
        this.txtEstado.setEnabled(false);
        this.txtCidade.setEnabled(false);
        this.txtBairro.setEnabled(false);

        this.txtCodEstado.setEnabled(false);
        this.txtCodCidade.setEditable(false);

        this.txtEndereco.setEnabled(false);
        this.txtComplemento.setEnabled(false);
        this.txtNumero.setEnabled(false);
        this.txtFoneComercial.setEnabled(false);
        this.txtCelular.setEnabled(false);
        this.txtContato.setEnabled(false);

        /**
         * Botões próprios do formulario
         */
        this.btnEstadoBusca.setEnabled(false);
        this.btnBairroBusca.setEnabled(false);
        this.btnCidadeBusca.setEnabled(false);

        this.txtBuscar.setEnabled(true);// esse campo sempre ficará habilitado para pesquisa 

    }

    private void acaoAdicionar() {

        gerarCodigoGUI();

        flag = 1;

        limparCampos();

        this.btnValidaCNPJ.setEnabled(true);
        this.btnAdicionar.setEnabled(false);
        this.btnEditar.setEnabled(false);
        this.btnPesquisar.setEnabled(false);
        this.btnSalvar.setEnabled(true);
        this.btnCancelar.setEnabled(true);
        this.btnEstadoBusca.setEnabled(false);
        this.btnCidadeBusca.setEnabled(false);
        this.btnBairroBusca.setEnabled(false);

        this.txtCNPJ.setEnabled(true);
        this.txtCNPJ.requestFocus();
        this.txtBuscar.setEnabled(false);
        this.txtBairro.setEnabled(false);

    }

    private void acaoEditar() {
        if (txtCNPJ.equals("")) {

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

            /**
             * ao clicar em btnAdicionar fazemos com que btnSalvar fique
             * habilitado para um eventual salvamento
             */
            btnSalvar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnValidaCNPJ.setEnabled(true);
            btnEditar.setEnabled(false);
            btnAdicionar.setEnabled(false);
            btnExcluir.setEnabled(false);

        }

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
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);

        JOptionPane.showMessageDialog(null, "Cadastro cancelado com sucesso!!");
    }

    private void acaoExcluir() {

        try {

            /*Evento ao ser clicado executa código*/
            int excluir = JOptionPane.showConfirmDialog(null, "Deseja excluir registro?", "Informação", JOptionPane.YES_NO_OPTION);

            if (excluir == JOptionPane.YES_OPTION) {
                empresaDTO.setIdEmpresaDto(Integer.parseInt(txtIdEmpresa.getText()));
                /**
                 * Chamando o método que irá executar a Edição dos Dados em
                 * nosso Banco de Dados
                 */
                empresaBO.ExcluirBO(empresaDTO);

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

    private void limparCampos() {

        // txtIdEmpresa.setText("");
        txtCNPJ.setValue(null);
        txtEmpresa.setText("");
        txtEstado.setText("");
        txtCidade.setText("");
        txtBairro.setText("");
        txtEndereco.setText("");
        txtComplemento.setText("");
        txtNumero.setText("");
        txtFoneComercial.setText("");
        txtCelular.setText("");
        txtContato.setText("");
        txtCodEstado.setText("");
        txtCodCidade.setText("");

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<EmpresaDTO> list;

        try {

            list = (ArrayList<EmpresaDTO>) empresaDAO.listarTodos();

            Object rowData[] = new Object[4];//são 04 colunas 

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdEmpresaDto();
                rowData[1] = list.get(i).getEmpresaDto();
                rowData[2] = list.get(i).getCnpjDto();
                rowData[3] = list.get(i).getContatoDto();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(55);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(150);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void SalvarAdicoesEdicoes() {
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO 

        empresaDTO.setIdEmpresaDto(Integer.parseInt(txtIdEmpresa.getText()));

        /**
         * Fazer o tratamento dos dois campos no formulario que representa o
         * codigo que receberá valores do sub formulários frmLisa, necessário
         * para evitar erros na hora do salvamentos no banco de dados
         */
        if (!txtCodEstado.getText().equals("")) {
            /**
             * Em Dados do tipo Integer devemos fazer sempre esse tratamento
             * antes de setá-os e um retorno de validação paa saber se o dado
             * foi setado
             */

            empresaDTO.setFkEstadoDto(Integer.parseInt(txtCodEstado.getText()));
        }

        if (!txtCodCidade.getText().equals("")) {
            /**
             * Em Dados do tipo Integer devemos fazer sempre esse tratamento
             * antes de setá-os e um retorno de validação paa saber se o dado
             * foi setado
             */
            empresaDTO.setFkCidadeDto(Integer.parseInt(txtCodCidade.getText()));
        }
        if (cbVinculo.getSelectedItem() != null) {
            empresaDTO.setVinculoDto(new String((String) cbVinculo.getSelectedItem()));
        }
        empresaDTO.setCnpjDto(txtCNPJ.getText());
        empresaDTO.setEmpresaDto(txtEmpresa.getText());
        empresaDTO.setBairroDto(txtBairro.getText());
        empresaDTO.setEnderecoDto(txtEndereco.getText());
        empresaDTO.setComplementoDto(txtComplemento.getText());
        empresaDTO.setNumeroDto(txtNumero.getText());
        empresaDTO.setFoneComercialDto(txtFoneComercial.getText());
        empresaDTO.setCelularDto(txtCelular.getText());
        empresaDTO.setContatoDto(txtContato.getText());

        try {

            //TRABALHAR AS VALIDAÇÕES NA HORA DO SALVAMENTOS 
            if ((flag == 1)) {

                boolean retornoVerifcaDuplicidade = empresaDAO.verificaDuplicidade(empresaDTO);//verificar se já existe CNPJ
                boolean retornoValidCodEmpresa = empresaBO.validaCodigoEmpresa(empresaDTO);//valida numero se diferente de zero  
                boolean retornoValidFkEstado = empresaBO.validaCodigoEstado(empresaDTO);//valida numero se diferente de zero
                boolean retornoValidaFkCidade = empresaBO.validaCodigoCidade(empresaDTO);//valida numero se diferente de zero
                boolean retornoValidNomeEmpresa = empresaBO.validaNomeEmpresa(empresaDTO);//validade se empresa diferente de nulo
                boolean retornoValidEndereco = empresaBO.validaEndereco(empresaDTO);//validade se endereco diferente de nulo
                boolean retornoValidContato = empresaBO.validaContato(empresaDTO);//validade se Contato diferente de nulo

                if ((retornoValidCodEmpresa == true)
                        && (retornoVerifcaDuplicidade == false)
                        && (retornoValidFkEstado == true)
                        && (retornoValidaFkCidade == true)
                        && (retornoValidNomeEmpresa == true)
                        && retornoValidEndereco == true
                        && retornoValidContato == true) {

                    empresaBO.cadastrarBO(empresaDTO);
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

                    //   JOptionPane.showMessageDialog(this, "" + "\n CAMADA GUI ANALISE 1 PARA DICIONAR: Resgistro já cadastrado.\nSistema Impossibilitou \n a Duplicidade", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    txtCNPJ.requestFocus();
                    txtCNPJ.setBackground(Color.RED);

                }

            } else {

                empresaDTO.setIdEmpresaDto(Integer.parseInt(txtIdEmpresa.getText()));
                boolean retornoVerifcaDuplicidade = empresaDAO.verificaDuplicidade(empresaDTO);//verificar se já existe CNPJ
                boolean retornoValidCodEmpresa = empresaBO.validaCodigoEmpresa(empresaDTO);//valida numero se diferente de zero  
                boolean retornoValidFkEstado = empresaBO.validaCodigoEstado(empresaDTO);//valida numero se diferente de zero
                boolean retornoValidaFkCidade = empresaBO.validaCodigoCidade(empresaDTO);//valida numero se diferente de zero
                boolean retornoValidNomeEmpresa = empresaBO.validaNomeEmpresa(empresaDTO);//validade se empresa diferente de nulo
                boolean retornoValidEndereco = empresaBO.validaEndereco(empresaDTO);//validade se endereco diferente de nulo
                boolean retornoValidContato = empresaBO.validaContato(empresaDTO);//validade se Contato diferente de nulo

                empresaBO.EditarBO(empresaDTO);
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

            //tratando validação da camada de negocio (BO-Busines Object) [codigo empresa] 
            if (e.getMessage().equals("Campo Codigo Empresa e Obrigatorio")) {
                txtIdEmpresa.requestFocus();
                txtIdEmpresa.setBackground(Color.RED);

                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            }

            //tratando validação da camada de negocio (BO-Busines Object) [codigo estado]
            if (e.getMessage().equals("Campo Codigo Estado e Obrigatorio")) {
                txtCodEstado.requestFocus();
                txtCodEstado.setBackground(Color.RED);

            }

            //tratando validação da camada de negocio (BO-Busines Object) [codigo cidade]
            if (e.getMessage().equals("Campo Codigo Cidade e Obrigatorio")) {
                txtCodCidade.requestFocus();
                txtCodCidade.setBackground(Color.RED);

            }

            //tratando validação da camada de negocio (BO-Busines Object) [nome Empresa]
            if (e.getMessage().equals("Campo Codigo Empresa e Obrigatorio")) {

                txtEmpresa.requestFocus();
                txtEmpresa.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }
            //tratando validação da camada de negocio (BO-Busines Object) [nome Empresa] excedeu 50chs
            if (e.getMessage().equals("O campo Empresa Excedeu os 50 Caracteres")) {
                txtEmpresa.requestFocus();
                txtEmpresa.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (BO-Busines Object) [Endereco] obrigatorio
            if (e.getMessage().equals("Preenchimento campo Endereco e obrigatorio.")) {
                txtEndereco.requestFocus();
                txtEndereco.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }
            //tratando validação da camada de negocio (BO-Busines Object) [Endereco] excedeu 50chs
            if (e.getMessage().equals("O Campo Endereco Excedeu os 50 Caracteres")) {
                txtEndereco.requestFocus();
                txtEndereco.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (BO-Busines Object) [Contato] obrigatorio
            if (e.getMessage().equals("Preenchimento campo Contato é obrigatorio.")) {
                txtContato.requestFocus();
                txtContato.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

            //tratando validação da camada de negocio (BO-Busines Object) [Contato] excedeu 50chs
            if (e.getMessage().equals("O campo Contato Excedeu os 50 Caracteres")) {
                txtContato.requestFocus();
                txtContato.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "" + "\n Exception: " + e.getMessage(), "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            }

        }
    }

    /**
     * O método CampoPesquisar é um Método sem retorno
     */
    private void pesquisaEmpresa() {

        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(txtBuscar.getText().toUpperCase());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        //aqui instanciamos uma lista do tipo BairroDTO
        ArrayList<EmpresaDTO> list;

        try {

            // essa lista irá receber as informações a partir do método abaixo descrito método este contido ja na 
            //camada DAO de disparo da SQL
            list = (ArrayList<EmpresaDTO>) empresaDAO.filtrarPesquisaRapida(pesquisar);

            Object rowData[] = new Object[4];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdEmpresaDto();
                rowData[1] = list.get(i).getEmpresaDto().toString();
                rowData[2] = list.get(i).getCnpjDto().toString();
                rowData[3] = list.get(i).getContatoDto().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

//            /**
//             * Coluna ID posição[0] vetor
//             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(380);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(380);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(380);

            personalizandoBarraInfoPesquisaTermino();
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormListaBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        painelSuperior = new javax.swing.JPanel();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        painelCentral = new javax.swing.JPanel();
        txtIdEmpresa = new javax.swing.JTextField();
        lblIdEmpreda = new javax.swing.JLabel();
        txtCNPJ = new javax.swing.JFormattedTextField();
        lblCNPJ = new javax.swing.JLabel();
        txtEmpresa = new javax.swing.JTextField();
        lblEmpresa = new javax.swing.JLabel();
        btnEstadoBusca = new javax.swing.JButton();
        txtCodEstado = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        txtCodCidade = new javax.swing.JTextField();
        txtCidade = new javax.swing.JTextField();
        btnCidadeBusca = new javax.swing.JButton();
        txtBairro = new javax.swing.JTextField();
        btnBairroBusca = new javax.swing.JButton();
        lblEstado = new javax.swing.JLabel();
        lblCidade = new javax.swing.JLabel();
        lblBairro = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        txtComplemento = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        lblEndereco = new javax.swing.JLabel();
        lblComplemento = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtFoneComercial = new javax.swing.JFormattedTextField();
        lblFoneComercial = new javax.swing.JLabel();
        txtCelular = new javax.swing.JFormattedTextField();
        lblCelular = new javax.swing.JLabel();
        txtContato = new javax.swing.JTextField();
        lblContato = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        btnValidaCNPJ = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        progressBarValidaCnpj = new javax.swing.JProgressBar();
        cbVinculo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        lblPesquisar = new javax.swing.JLabel();
        lblLinhaInformativa = new javax.swing.JLabel();
        progressBarFrontBack = new javax.swing.JProgressBar();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        painelPrincipal.setPreferredSize(new java.awt.Dimension(576, 800));

        painelSuperior.setBackground(java.awt.Color.white);

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
        btnAdicionar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAdicionarKeyPressed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editar.png"))); // NOI18N
        btnEditar.setToolTipText("Alterar Registro");
        btnEditar.setEnabled(false);
        btnEditar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEditarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnEditarFocusLost(evt);
            }
        });
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

        javax.swing.GroupLayout painelSuperiorLayout = new javax.swing.GroupLayout(painelSuperior);
        painelSuperior.setLayout(painelSuperiorLayout);
        painelSuperiorLayout.setHorizontalGroup(
            painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelSuperiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
            .addGroup(painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelSuperiorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 511, Short.MAX_VALUE)))
        );
        painelSuperiorLayout.setVerticalGroup(
            painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelSuperiorLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelSuperiorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        painelCentral.setBackground(java.awt.Color.white);
        painelCentral.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados Empresa:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 13))); // NOI18N
        painelCentral.setPreferredSize(new java.awt.Dimension(564, 400));
        painelCentral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        painelCentral.add(txtIdEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 30, 30));

        lblIdEmpreda.setText("ID:");
        painelCentral.add(lblIdEmpreda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        try {
            txtCNPJ.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCNPJ.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCNPJFocusGained(evt);
            }
        });
        txtCNPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCNPJKeyPressed(evt);
            }
        });
        painelCentral.add(txtCNPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 150, 30));

        lblCNPJ.setText("CNPJ:");
        painelCentral.add(lblCNPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        txtEmpresa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmpresaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmpresaFocusLost(evt);
            }
        });
        txtEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpresaActionPerformed(evt);
            }
        });
        txtEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmpresaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmpresaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmpresaKeyTyped(evt);
            }
        });
        painelCentral.add(txtEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 310, 30));

        lblEmpresa.setText("Empresa:");
        painelCentral.add(lblEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

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
        painelCentral.add(btnEstadoBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 32, 32));
        painelCentral.add(txtCodEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 30, 30));
        painelCentral.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 100, 30));
        painelCentral.add(txtCodCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 30, 30));
        painelCentral.add(txtCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 130, 30));

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
        painelCentral.add(btnCidadeBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 32, 32));

        txtBairro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBairroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBairroFocusLost(evt);
            }
        });
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
        painelCentral.add(txtBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, 120, 30));

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
        painelCentral.add(btnBairroBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 32, 32));

        lblEstado.setText("Estado:");
        painelCentral.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        lblCidade.setText("Cidade:");
        painelCentral.add(lblCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, -1, -1));

        lblBairro.setText("Bairro:");
        painelCentral.add(lblBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, -1, -1));

        txtEndereco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEnderecoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEnderecoFocusLost(evt);
            }
        });
        txtEndereco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEnderecoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEnderecoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEnderecoKeyTyped(evt);
            }
        });
        painelCentral.add(txtEndereco, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 160, 30));

        txtComplemento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtComplementoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComplementoFocusLost(evt);
            }
        });
        txtComplemento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComplementoActionPerformed(evt);
            }
        });
        txtComplemento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtComplementoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtComplementoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtComplementoKeyTyped(evt);
            }
        });
        painelCentral.add(txtComplemento, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 120, 30));

        txtNumero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumeroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumeroFocusLost(evt);
            }
        });
        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumeroKeyPressed(evt);
            }
        });
        painelCentral.add(txtNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 150, 40, 30));

        lblEndereco.setText("Endereço:");
        painelCentral.add(lblEndereco, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        lblComplemento.setText("Complemento:");
        painelCentral.add(lblComplemento, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, -1, -1));

        jLabel1.setText("Número:");
        painelCentral.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 50, -1));

        try {
            txtFoneComercial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFoneComercial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFoneComercialFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFoneComercialFocusLost(evt);
            }
        });
        txtFoneComercial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFoneComercialKeyPressed(evt);
            }
        });
        painelCentral.add(txtFoneComercial, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, 90, 30));

        lblFoneComercial.setText("Fone:");
        painelCentral.add(lblFoneComercial, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, -1, -1));

        try {
            txtCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)9####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCelular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCelularFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCelularFocusLost(evt);
            }
        });
        txtCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelularActionPerformed(evt);
            }
        });
        txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCelularKeyPressed(evt);
            }
        });
        painelCentral.add(txtCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 150, 90, 30));

        lblCelular.setText("Celular:");
        painelCentral.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, -1, -1));

        txtContato.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtContatoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtContatoFocusLost(evt);
            }
        });
        txtContato.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContatoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContatoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContatoKeyTyped(evt);
            }
        });
        painelCentral.add(txtContato, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 140, 30));

        lblContato.setText("Contato:");
        painelCentral.add(lblContato, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Empresa", "CNPJ", "Contato"
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
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(60);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(60);
            tabela.getColumnModel().getColumn(0).setMaxWidth(60);
            tabela.getColumnModel().getColumn(1).setMinWidth(200);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(1).setMaxWidth(200);
            tabela.getColumnModel().getColumn(2).setMinWidth(120);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(2).setMaxWidth(120);
            tabela.getColumnModel().getColumn(3).setMinWidth(200);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        painelCentral.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 550, 150));

        btnValidaCNPJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/validation.png"))); // NOI18N
        btnValidaCNPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidaCNPJActionPerformed(evt);
            }
        });
        btnValidaCNPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnValidaCNPJKeyPressed(evt);
            }
        });
        painelCentral.add(btnValidaCNPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 30, 30));

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnPesquisarFocusLost(evt);
            }
        });
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });
        btnPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPesquisarKeyPressed(evt);
            }
        });
        painelCentral.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 210, 32, 32));
        painelCentral.add(progressBarValidaCnpj, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 110, -1));

        cbVinculo.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        cbVinculo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EMPRESAS(INTERNAS/EXTERNAS)" }));
        cbVinculo.setEnabled(false);
        painelCentral.add(cbVinculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 160, 30));

        jLabel2.setText("Vinculo:");
        painelCentral.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 60, -1));

        txtBuscar.setPreferredSize(new java.awt.Dimension(6, 30));
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });
        painelCentral.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, 190, -1));

        lblPesquisar.setText("Pesquisar:");
        painelCentral.add(lblPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 190, -1, -1));

        lblLinhaInformativa.setText("Linha Informativa");
        painelCentral.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 380, -1));
        painelCentral.add(progressBarFrontBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 250, 150, -1));

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

        javax.swing.GroupLayout painelPrincipalLayout = new javax.swing.GroupLayout(painelPrincipal);
        painelPrincipal.setLayout(painelPrincipalLayout);
        painelPrincipalLayout.setHorizontalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelPrincipalLayout.createSequentialGroup()
                        .addComponent(painelSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addComponent(painelCentral, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelPrincipalLayout.setVerticalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelCentral, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(painelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 690));

        setBounds(0, 0, 583, 520);
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
            acaoExcluir();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }


    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAdicionarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseEntered
        btnAdicionar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnAdicionarMouseEntered

    private void btnAdicionarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseExited
        btnAdicionar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnAdicionarMouseExited

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        acaoEditar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseEntered
        btnSalvar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnSalvarMouseEntered

    private void btnSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseExited
        btnSalvar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnSalvarMouseExited

    private void desabilitarCampos() {
        txtIdEmpresa.setEnabled(false);
        txtCNPJ.setEnabled(false);
        txtEmpresa.setEnabled(false);
        txtCodEstado.setEnabled(false);
        txtEstado.setEnabled(false);
        txtCodCidade.setEnabled(false);
        txtCidade.setEnabled(false);
        txtBairro.setEnabled(false);
        txtEndereco.setEnabled(false);
        txtComplemento.setEnabled(false);
        txtNumero.setEnabled(false);
        txtFoneComercial.setEnabled(false);
        txtCelular.setEnabled(false);
        txtContato.setEnabled(false);
        txtBuscar.setEnabled(false);

    }

    private void desabilitarTodosBotoes() {

        btnAdicionar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnPesquisar.setEnabled(false);
        btnSalvar.setEnabled(false);

    }


    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            SalvarAdicoesEdicoes();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            ConexaoUtil conecta = new ConexaoUtil();
            boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

            if (recebeConexao == true) {

                SalvarAdicoesEdicoes();

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                desabilitarCampos();
                desabilitarTodosBotoes();
            }

        }

        if (evt.getKeyCode() == evt.VK_F7) {

            ConexaoUtil conecta = new ConexaoUtil();
            boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

            if (recebeConexao == true) {

                SalvarAdicoesEdicoes();

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                desabilitarCampos();
                desabilitarTodosBotoes();
            }

        }

    }//GEN-LAST:event_btnSalvarKeyPressed

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        acaoBotaoCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEstadoBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEstadoBuscaFocusGained
        btnEstadoBusca.setBackground(Color.YELLOW);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.ORANGE);
        lblLinhaInformativa.setText("Pesquisa Terminada Lista Estados Aberta...");
    }//GEN-LAST:event_btnEstadoBuscaFocusGained

    private void btnEstadoBuscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEstadoBuscaFocusLost
        btnEstadoBusca.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnEstadoBuscaFocusLost

    private void btnEstadoBuscaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstadoBuscaMouseEntered

    }//GEN-LAST:event_btnEstadoBuscaMouseEntered

    br.com.subgerentepro.telas.FrmListaBairros formListaBairros;

    private void buscarBairro() {

        if (estaFechado(formListaBairros)) {
            formListaBairros = new FrmListaBairros();
            DeskTop.add(formListaBairros).setLocation(560, 40);
            formListaBairros.setTitle("Lista Bairros Cadastrados");
            formListaBairros.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formListaBairros.toFront();
            formListaBairros.show();

        }

    }

    br.com.subgerentepro.telas.FrmListaCidades formListaCidades;

    private void buscarCidade() {

        if (estaFechado(formListaCidades)) {
            formListaCidades = new FrmListaCidades();
            DeskTop.add(formListaCidades).setLocation(560, 40);
            formListaCidades.setTitle("Lista Cidades");
            txtBairro.setEditable(true);
            formListaCidades.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            txtBairro.setEditable(true);
            formListaCidades.toFront();
            formListaCidades.show();

        }

    }

    br.com.subgerentepro.telas.FrmListaEstados formListaEstado;

    private void buscarEstado() {

        if (estaFechado(formListaEstado)) {
            formListaEstado = new FrmListaEstados();
            DeskTop.add(formListaEstado).setLocation(560, 40);
            formListaEstado.setTitle("Lista Estados");
            formListaEstado.show();
        } else {
            //JOptionPane.showMessageDialog(this, "Formulário em Uso", "Informação", 0, new ImageIcon(getClass().getResource("/br/com/pontovenda/imagens/usuarios/info.png")));
            formListaEstado.toFront();
            formListaEstado.show();

        }

    }
    private void btnEstadoBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoBuscaActionPerformed
        buscarEstado();
    }//GEN-LAST:event_btnEstadoBuscaActionPerformed

    private void btnCidadeBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCidadeBuscaFocusGained
        btnCidadeBusca.setBackground(Color.YELLOW);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Pesquisa ENTER abrir Lista Cidades...");
    }//GEN-LAST:event_btnCidadeBuscaFocusGained

    private void btnCidadeBuscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCidadeBuscaFocusLost
        btnCidadeBusca.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnCidadeBuscaFocusLost

    private void btnCidadeBuscaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCidadeBuscaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCidadeBuscaMouseEntered

    private void btnCidadeBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCidadeBuscaActionPerformed

        buscarCidade();


    }//GEN-LAST:event_btnCidadeBuscaActionPerformed

    private void btnBairroBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBairroBuscaFocusGained
        btnBairroBusca.setBackground(Color.YELLOW);

        //----------------------------------//
        //informe sobre inicio da pesquisa //
        //--------------------------------//
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Presione ENTER  Abrir Lista Bairros");


    }//GEN-LAST:event_btnBairroBuscaFocusGained

    private void btnBairroBuscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBairroBuscaFocusLost
        btnBairroBusca.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnBairroBuscaFocusLost

    private void btnBairroBuscaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBairroBuscaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBairroBuscaMouseEntered

    private void btnBairroBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBairroBuscaActionPerformed
        buscarBairro();
    }//GEN-LAST:event_btnBairroBuscaActionPerformed
    private void aoClicarItemDaTabela() {
        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/

        int codigoCapturado = (int) tabela.getValueAt(tabela.getSelectedRow(), 0);

        ArrayList<ModeloEmpresaUfCidadeBairroDTO> list;

        try {
            list = (ArrayList<ModeloEmpresaUfCidadeBairroDTO>) empresaDAO.filtrarId(codigoCapturado);

            if (list != null) {

                /**
                 * String.valueOf() pega um Valor Inteiro e transforma em String
                 * e seta em txtIDMedico que é um campo do tipo texto
                 */
                for (int i = 0; i < list.size(); i++) {

                    txtIdEmpresa.setText(String.valueOf(list.get(i).getEmpresaModeloDTO().getIdEmpresaDto()));
                    txtCNPJ.setText(String.valueOf(list.get(i).getEmpresaModeloDTO().getCnpjDto()));
                    txtEmpresa.setText(list.get(i).getEmpresaModeloDTO().getEmpresaDto());
                    txtCodEstado.setText(String.valueOf(list.get(i).getEmpresaModeloDTO().getFkEstadoDto()));
                    txtEstado.setText(String.valueOf(list.get(i).getEstadoModeloDTO().getNomeEstadoDto()));
                    txtCodCidade.setText(String.valueOf(list.get(i).getEmpresaModeloDTO().getFkCidadeDto()));
                    txtCidade.setText(String.valueOf(list.get(i).getCidadeModeloDTO().getNomeCidadeDto()));
                    txtBairro.setText(String.valueOf(list.get(i).getEmpresaModeloDTO().getBairroDto()));
                    txtEndereco.setText(String.valueOf(list.get(i).getEmpresaModeloDTO().getEnderecoDto()));
                    txtComplemento.setText(String.valueOf(list.get(i).getEmpresaModeloDTO().getComplementoDto()));
                    txtNumero.setText(String.valueOf(list.get(i).getEmpresaModeloDTO().getNumeroDto()));
                    txtFoneComercial.setText(String.valueOf(list.get(i).getEmpresaModeloDTO().getFoneComercialDto()));
                    txtCelular.setText(String.valueOf(list.get(i).getEmpresaModeloDTO().getCelularDto()));
                    txtContato.setText(String.valueOf(list.get(i).getEmpresaModeloDTO().getContatoDto()));

                }
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
                txtBuscar.setEnabled(true);

                /**
                 * Desabilitar campos
                 */
                desabailitarCampos();

            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar()\n" + ex.getMessage());
        }

    }
    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        aoClicarItemDaTabela();
    }//GEN-LAST:event_tabelaMouseClicked

    private void acaoCNPJTrue() {

        //CAMPOS HABILITAR    
        this.txtEmpresa.setEnabled(true);
        //CAMPOS DESABILITAR
        this.txtCNPJ.setEnabled(false);
        this.btnValidaCNPJ.setEnabled(false);
    }

    private void acaoBotaoValidaCNPJ() {

        //Ativamos a propriedade Indeterminate para criar um evento load de 
        // espera e definimo com true a sua inicialização
        progressBarValidaCnpj.setIndeterminate(true);

        /**
         * Primeiro criamos uma String com o nome de [CNPJ] e capturamos o valor
         * digitado no campo txtCNPJ por meio do método getText() onde ficará
         * armazenado na variável CNPJ criado para receber o valor capturado
         * pelou usuário.
         */
        String CNPJ = this.txtCNPJ.getText();

        try {
            boolean retornoVerifcaDuplicidade = empresaDAO.verificaDuplicidadeString(CNPJ);//verificar se já existe CNPJ

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
                for (int i = 0; i < CNPJ.length(); i++) {

                    cont += 1;//Aqui o contador começa a ser incrementado em mais um a cada passada 

                    //Quando o contador estiver na posicao 3 execute o codigo abaixo 
                    if (cont == 3) {

                        /**
                         * Se na posição 3 do campo txtCNPJ estiver um ponto
                         * substitua em todos os lugares da String que estiver
                         * com ponto por um estaço em branco
                         */
                        if (CNPJ.charAt(i) == '.') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             */
                            CNPJ = CNPJ.replace(CNPJ.charAt(i), ' ');

                        }

                    }

                    //Quando o contador estiver na posicao 11 execute o codigo abaixo 
                    if (cont == 11) {

                        /**
                         * Se na posição 11 do campo txtCNPJ estiver um traço[/]
                         * substitua em todos os lugares da String que estiver
                         * com ponto por um estaço em branco
                         */
                        if (CNPJ.charAt(i) == '/') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             */
                            CNPJ = CNPJ.replace(CNPJ.charAt(i), ' ');

                        }

                    }

                    //Quando o contador estiver na posicao 5 execute o codigo abaixo 
                    if (cont == 16) {

                        /**
                         * Se na posição 16 do campo txtCNPJ estiver um traço
                         * [-] substitua em todos os lugares da String que
                         * estiver com ponto por um estaço em branco
                         */
                        if (CNPJ.charAt(i) == '-') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             */
                            CNPJ = CNPJ.replace(CNPJ.charAt(i), ' ');

                        }

                    }

                }
                /**
                 * Neste ponto criamos uma String nova chamada cnpjTratado e
                 * capturamos a string ja tratada e fazemos o último tratamento
                 * que é tirar todos os espaços embrancos da string tratada
                 */
                String cnpjTratado = CNPJ.replace(" ", "");

                /**
                 * A baixo fazemos a aplicação da função que irá validar se o
                 * cnpj é válido ou não isCNPJ
                 */
                boolean recebeCNPJ = MetodoStaticosUtil.isCNPJ(cnpjTratado);
                /**
                 * se o retorno for verdadeiro CNPJ válido caso contrário CNPJ
                 * Inválido
                 */
                if (recebeCNPJ == true) {
                    //      JOptionPane.showMessageDialog(this, "" + "\n Validado com Sucesso.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    acaoCNPJTrue();
                    txtEmpresa.setEnabled(true);
                    txtEmpresa.setEditable(true);

                    progressBarValidaCnpj.setIndeterminate(false);

                    //----------------------------------//
                    //informe sobre inicio da pesquisa //
                    //--------------------------------//
                    Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
                    lblLinhaInformativa.setForeground(Color.BLUE);
                    lblLinhaInformativa.setText("Validado Com Sucesso");

                } else {
                    JOptionPane.showMessageDialog(this, "" + "\n CNPJ Inválido.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                    progressBarValidaCnpj.setIndeterminate(false);

                }

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n CNPJ Duplicado.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
                txtCNPJ.requestFocus();
                txtCNPJ.setBackground(Color.RED);
                //terminou a ação de carreamento pois o cnpj é duplicado e o metodo foi encerrado
                progressBarValidaCnpj.setIndeterminate(false);
                progressBarValidaCnpj.setVisible(false);
            }
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }


    private void btnValidaCNPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidaCNPJActionPerformed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            acaoBotaoValidaCNPJ();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }


    }//GEN-LAST:event_btnValidaCNPJActionPerformed

    private void txtEmpresaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpresaKeyReleased


    }//GEN-LAST:event_txtEmpresaKeyReleased

    private void txtEmpresaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpresaKeyTyped

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
        int numeroDeCaracter = 99;

        if (txtEmpresa.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute 
            JOptionPane.showMessageDialog(null, "Máxmimo 100 caracter");// colocamos uma mensagem alertar usuário
            txtEmpresa.setBackground(Color.YELLOW);//modificamos a cor do campo para visualmente indicar ao usuário o erro 
            //lblLinhaInformativa.setText("Campo Nome: Máximo 50 caracteres");

        }
    }//GEN-LAST:event_txtEmpresaKeyTyped

    private void txtEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmpresaActionPerformed

    private void txtEnderecoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEnderecoKeyReleased
        txtEndereco.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtEndereco.getText()));

    }//GEN-LAST:event_txtEnderecoKeyReleased

    private void txtEnderecoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEnderecoKeyTyped

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

        if (txtEndereco.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute 
            JOptionPane.showMessageDialog(null, "Máxmimo 50 caracter");// colocamos uma mensagem alertar usuário
            txtEndereco.setBackground(Color.YELLOW);//modificamos a cor do campo para visualmente indicar ao usuário o erro 
            //lblLinhaInformativa.setText("Campo Nome: Máximo 50 caracteres");

        }

    }//GEN-LAST:event_txtEnderecoKeyTyped

    private void txtComplementoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComplementoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComplementoActionPerformed

    private void txtComplementoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComplementoKeyReleased
        txtComplemento.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtComplemento.getText()));
    }//GEN-LAST:event_txtComplementoKeyReleased

    private void txtComplementoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComplementoKeyTyped
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

        if (txtComplemento.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute 
            JOptionPane.showMessageDialog(null, "Máxmimo 50 caracter");// colocamos uma mensagem alertar usuário
            txtComplemento.setBackground(Color.YELLOW);//modificamos a cor do campo para visualmente indicar ao usuário o erro 
            //lblLinhaInformativa.setText("Campo Nome: Máximo 50 caracteres");

        }

    }//GEN-LAST:event_txtComplementoKeyTyped

    private void txtContatoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContatoKeyReleased
        txtContato.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtContato.getText()));
    }//GEN-LAST:event_txtContatoKeyReleased

    private void txtContatoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContatoKeyTyped

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

        if (txtContato.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute 
            JOptionPane.showMessageDialog(null, "Máxmimo 50 caracter");// colocamos uma mensagem alertar usuário
            txtContato.setBackground(Color.YELLOW);//modificamos a cor do campo para visualmente indicar ao usuário o erro 

        }

    }//GEN-LAST:event_txtContatoKeyTyped

    private void txtCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularActionPerformed

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

    private void acaoBotaoPesquisar() {

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            pesquisaEmpresa();

        } else {
            addRowJTable();
        }
    }


    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed

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
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtEmpresaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmpresaFocusLost
        txtEmpresa.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtEmpresa.getText()));
        txtEmpresa.setBackground(Color.WHITE);

    }//GEN-LAST:event_txtEmpresaFocusLost

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        txtBuscar.setBackground(Color.YELLOW);

        Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Digite o Registro Pesquisado Pressione-->[ENTER]");

    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            txtBuscar.setToolTipText("Digite o Registro Procurado");
            txtBuscar.setBackground(Color.YELLOW);
            btnPesquisar.setEnabled(true);
            //----------------------------------//
            //informe sobre inicio da pesquisa //
            //--------------------------------//
            personalizandoBarraInfoPesquisaInicio();

            btnPesquisar.requestFocus();
            btnPesquisar.setBackground(Color.YELLOW);
            txtBuscar.setBackground(Color.WHITE);
        }


    }//GEN-LAST:event_txtBuscarKeyPressed

    private void btnPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPesquisarKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

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

        }
    }//GEN-LAST:event_btnPesquisarKeyPressed

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost

    private void txtCNPJFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCNPJFocusGained
        this.txtCNPJ.setBackground(Color.YELLOW);
    }//GEN-LAST:event_txtCNPJFocusGained

    private void txtCNPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCNPJKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            btnValidaCNPJ.setBackground(Color.YELLOW);
            //----------------------------------//
            //informe sobre inicio da pesquisa //
            //--------------------------------//
            Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Presione ENTER  iniciar validação");

            btnValidaCNPJ.requestFocus();
            txtCNPJ.setBackground(Color.WHITE);
        }


    }//GEN-LAST:event_txtCNPJKeyPressed

    private void btnValidaCNPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnValidaCNPJKeyPressed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            acaoBotaoValidaCNPJ();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }//GEN-LAST:event_btnValidaCNPJKeyPressed

    private void txtEmpresaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmpresaFocusGained
        txtEmpresa.setBackground(Color.YELLOW);

        Font f = new Font("Tahoma", Font.BOLD, 18);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Digite a Empresa e Pressione -->[ENTER]");

    }//GEN-LAST:event_txtEmpresaFocusGained

    private void txtEmpresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpresaKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            if (!txtEmpresa.getText().equals("")) {
                btnEstadoBusca.setEnabled(true);
                btnEstadoBusca.requestFocus();
                btnEstadoBusca.setBackground(Color.YELLOW);

                buscarEstado();

                //  JOptionPane.showMessageDialog(null, "entrei no primeiro ");
            }

            if (txtEmpresa.getText().equals("")) {
                //   JOptionPane.showMessageDialog(null, "entrei na segunda ");
                Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setFont(f);
                lblLinhaInformativa.setForeground(Color.RED);
                lblLinhaInformativa.setText("Digite a Empresa e Pressione -->[ENTER]");
                txtEmpresa.setBackground(Color.red);
            }

        }


    }//GEN-LAST:event_txtEmpresaKeyPressed

    private void btnCidadeBuscaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCidadeBuscaKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {
            buscarCidade();
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Pesquisa Terminada Lista Cidades Aberta");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnCidadeBuscaKeyPressed

    private void btnBairroBuscaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBairroBuscaKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            buscarBairro();
        }

        //se SETA PARA CIMA 
        if (evt.getKeyCode() == evt.VK_UP) {

            txtBairro.requestFocus();

        }

        //se SETA PARA ESQUERDA
        if (evt.getKeyCode() == evt.VK_LEFT) {

            txtBairro.requestFocus();

        }

        //se SETA PARA DIREITA
        if (evt.getKeyCode() == evt.VK_RIGHT) {

            txtEndereco.requestFocus();

        }

        //se SETA PARA BAIXO
        if (evt.getKeyCode() == evt.VK_LEFT) {

            txtEndereco.requestFocus();

        }

    }//GEN-LAST:event_btnBairroBuscaKeyPressed

    private void txtBairroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBairroFocusGained
        txtBairro.setBackground(Color.YELLOW);

    }//GEN-LAST:event_txtBairroFocusGained

    private void txtBairroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBairroFocusLost
        txtBairro.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtBairroFocusLost

    private void txtEnderecoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEnderecoFocusGained
        txtEndereco.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Digite Endereço Pressione -->[ENTER]");

    }//GEN-LAST:event_txtEnderecoFocusGained

    private void txtEnderecoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEnderecoFocusLost
        txtEndereco.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtEnderecoFocusLost

    private void txtEnderecoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEnderecoKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
            txtComplemento.requestFocus();
        }


    }//GEN-LAST:event_txtEnderecoKeyPressed

    private void txtComplementoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComplementoFocusGained
        txtComplemento.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Caso haja complemento Digite e Pressione -->[ENTER]");
    }//GEN-LAST:event_txtComplementoFocusGained

    private void txtComplementoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComplementoFocusLost
        txtComplemento.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtComplementoFocusLost

    private void txtComplementoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComplementoKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
            txtNumero.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtEndereco.requestFocus();
        }
    }//GEN-LAST:event_txtComplementoKeyPressed

    private void txtNumeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
            txtFoneComercial.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtComplemento.requestFocus();
        }
    }//GEN-LAST:event_txtNumeroKeyPressed

    private void txtNumeroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroFocusGained
        txtNumero.setBackground(Color.YELLOW);
    }//GEN-LAST:event_txtNumeroFocusGained

    private void txtNumeroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroFocusLost
        txtNumero.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtNumeroFocusLost

    private void txtFoneComercialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFoneComercialFocusGained
        txtFoneComercial.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Digite Telefone Comercial Fixo-->[ENTER]");


    }//GEN-LAST:event_txtFoneComercialFocusGained

    private void txtFoneComercialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFoneComercialFocusLost
        txtFoneComercial.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtFoneComercialFocusLost

    private void txtFoneComercialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoneComercialKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
            txtCelular.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtNumero.requestFocus();
        }
    }//GEN-LAST:event_txtFoneComercialKeyPressed

    private void txtCelularFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCelularFocusGained
        txtCelular.setBackground(Color.YELLOW);
    }//GEN-LAST:event_txtCelularFocusGained

    private void txtCelularFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCelularFocusLost
        txtCelular.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtCelularFocusLost

    private void txtCelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
            txtContato.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtFoneComercial.requestFocus();
        }

    }//GEN-LAST:event_txtCelularKeyPressed

    private void txtContatoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContatoFocusGained
        txtContato.setBackground(Color.YELLOW);
        Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Digite contado da Empresa->[ENTER]");
    }//GEN-LAST:event_txtContatoFocusGained

    private void txtContatoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContatoFocusLost
        txtContato.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtContatoFocusLost

    private void txtContatoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContatoKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
            btnSalvar.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtCelular.requestFocus();
        }

    }//GEN-LAST:event_txtContatoKeyPressed

    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained
        btnSalvar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnSalvarFocusGained

    private void btnSalvarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusLost
        btnSalvar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnSalvarFocusLost

    private void btnCancelarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCancelarFocusGained
        btnCancelar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnCancelarFocusGained

    private void btnCancelarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCancelarFocusLost
        btnCancelar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnCancelarFocusLost

    private void btnAdicionarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusGained
        btnAdicionar.setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnAdicionarFocusGained

    private void btnAdicionarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusLost
        btnAdicionar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnAdicionarFocusLost

    private void btnEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusGained
        btnEditar.setBackground(Color.YELLOW);
        //codigo de proteçao para setor de protocolo 
        if (lblPerfil.getText().equalsIgnoreCase("protocolo")) {
            btnEditar.setEnabled(false);

            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Registros Protegidos Proibido Alteração/Exclusão.");

        }
    }//GEN-LAST:event_btnEditarFocusGained

    private void btnEditarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusLost
        btnEditar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnEditarFocusLost

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained
        btnExcluir.setBackground(Color.YELLOW);
        //codigo de proteçao para setor de protocolo 
        if (lblPerfil.getText().equalsIgnoreCase("protocolo")) {
            btnExcluir.setEnabled(false);

            Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.BLUE);
            lblLinhaInformativa.setText("Registros Protegidos Proibido Alteração/Exclusão.");

        }


    }//GEN-LAST:event_btnExcluirFocusGained

    private void btnExcluirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusLost
        btnExcluir.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnExcluirFocusLost

    private void txtBairroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBairroKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
            if (txtBairro.getText() != null) {
                txtEndereco.setEnabled(true);
                txtComplemento.setEnabled(true);
                txtNumero.setEnabled(true);
                txtFoneComercial.setEnabled(true);
                txtCelular.setEnabled(true);
                txtContato.setEnabled(true);
                txtEndereco.requestFocus();
                txtEndereco.requestFocus();
            }

            if (txtBairro.getText() == null) {
                buscarBairro();
            }
        }
    }//GEN-LAST:event_txtBairroKeyPressed

    private void btnAdicionarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAdicionarKeyPressed

        //        acaoAdicionar();
        if (evt.getKeyCode() == evt.VK_ENTER) {
            acaoAdicionar();

        }

    }//GEN-LAST:event_btnAdicionarKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    public static javax.swing.JButton btnBairroBusca;
    private javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnCidadeBusca;
    private javax.swing.JButton btnEditar;
    public static javax.swing.JButton btnEstadoBusca;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnValidaCNPJ;
    private javax.swing.JComboBox cbVinculo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCNPJ;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JLabel lblContato;
    private javax.swing.JLabel lblEmpresa;
    private javax.swing.JLabel lblEndereco;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFoneComercial;
    private javax.swing.JLabel lblIdEmpreda;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblPesquisar;
    private javax.swing.JPanel painelCentral;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JPanel painelSuperior;
    private javax.swing.JProgressBar progressBarFrontBack;
    private javax.swing.JProgressBar progressBarValidaCnpj;
    private javax.swing.JTable tabela;
    public static javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JFormattedTextField txtCNPJ;
    public static javax.swing.JFormattedTextField txtCelular;
    public static javax.swing.JTextField txtCidade;
    public static javax.swing.JTextField txtCodCidade;
    public static javax.swing.JTextField txtCodEstado;
    public static javax.swing.JTextField txtComplemento;
    public static javax.swing.JTextField txtContato;
    private javax.swing.JTextField txtEmpresa;
    public static javax.swing.JTextField txtEndereco;
    public static javax.swing.JTextField txtEstado;
    public static javax.swing.JFormattedTextField txtFoneComercial;
    private javax.swing.JTextField txtIdEmpresa;
    public static javax.swing.JTextField txtNumero;
    // End of variables declaration//GEN-END:variables
}
