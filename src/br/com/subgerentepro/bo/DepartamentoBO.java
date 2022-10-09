package br.com.subgerentepro.bo;

import br.com.subgerentepro.dao.DepartamentoDAO;
import br.com.subgerentepro.dto.DepartamentoDTO;
import br.com.subgerentepro.exceptions.NegocioException;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.exceptions.ValidacaoException;
import javax.swing.JOptionPane;

public class DepartamentoBO {

    public void atualizarBO(DepartamentoDTO departamentoDTO) {

        try {

            DepartamentoDAO departamentoDAO = new DepartamentoDAO();

            departamentoDAO.atualizar(departamentoDTO);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }

    public void cadastrarBO(DepartamentoDTO departamentoDTO) throws NegocioException {

        try {

            DepartamentoDAO departamentoDAO = new DepartamentoDAO();

            departamentoDAO.inserir(departamentoDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }

    }

    public boolean duplicidade(DepartamentoDTO departamentoDTO) throws PersistenciaException {

        DepartamentoDAO departamentoDAO = new DepartamentoDAO();

        //a verificação de Duplicidade será feita a partir do CPF do Paciente
        boolean verificaDuplicidade = departamentoDAO.verificaDuplicidade(departamentoDTO);

        //retorna o valor boleano com true ou false
        return verificaDuplicidade;
    }

    //VALIDAÇÃO INDEXADA
    public boolean validacaoBOdosCamposForm(DepartamentoDTO departamentoDTO) throws ValidacaoException {

        boolean ehValido = true;

        
        if (departamentoDTO.getNomeDto() == null
                || departamentoDTO.getNomeDto().equals("")
                || departamentoDTO.getNomeDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo paciente Obrigatorio");

        }

        if (departamentoDTO.getNomeDto().length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo paciente aceita no MAX 49 chars");

        }

       

        
        
        return ehValido;
    }

}
