package br.com.subgerentepro.bo;

import br.com.subgerentepro.dao.SetorTramiteInternoDAO;
import br.com.subgerentepro.dto.SetorTramiteInternoDTO;
import br.com.subgerentepro.exceptions.NegocioException;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.exceptions.ValidacaoException;
import javax.swing.JOptionPane;

public class SetorTramiteInternoBO {

    public void atualizarBO(SetorTramiteInternoDTO setorTramiteInternoDTO) {

        try {

            SetorTramiteInternoDAO setorTramiteInternoDAO = new SetorTramiteInternoDAO();

            setorTramiteInternoDAO.atualizar(setorTramiteInternoDTO);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }

    public void cadastrarBO(SetorTramiteInternoDTO setorTramiteInternoDTO) throws NegocioException {

        try {

            SetorTramiteInternoDAO setorTramiteInternoDAO = new SetorTramiteInternoDAO();

            setorTramiteInternoDAO.inserir(setorTramiteInternoDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }

    }

    public boolean duplicidade(SetorTramiteInternoDTO setorTramiteInternoDTO) throws PersistenciaException {

        SetorTramiteInternoDAO setorTramiteInternoDAO = new SetorTramiteInternoDAO();

        //a verificação de Duplicidade será feita a partir do CPF do Paciente
        boolean verificaDuplicidade = setorTramiteInternoDAO.verificaDuplicidade(setorTramiteInternoDTO);

        //retorna o valor boleano com true ou false
        return verificaDuplicidade;
    }

    //VALIDAÇÃO INDEXADA
    public boolean validacaoBOdosCamposForm(SetorTramiteInternoDTO setorTramiteInternoDTO) throws ValidacaoException {

        boolean ehValido = true;

        
        if (setorTramiteInternoDTO.getNomeDto() == null
                || setorTramiteInternoDTO.getNomeDto().equals("")
                || setorTramiteInternoDTO.getNomeDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo paciente Obrigatorio");

        }

        if (setorTramiteInternoDTO.getNomeDto().length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo paciente aceita no MAX 49 chars");

        }

       

        
        
        return ehValido;
    }

}
