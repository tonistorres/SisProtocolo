package br.com.subgerentepro.bo;

import br.com.subgerentepro.dao.TutorDAO;
import br.com.subgerentepro.dto.TutorDTO;
import br.com.subgerentepro.exceptions.NegocioException;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.exceptions.ValidacaoException;
import javax.swing.JOptionPane;

public class TutorBO {

    public void atualizarBO(TutorDTO tutorDTO) {

        try {

            TutorDAO tutorDAO = new TutorDAO();

            tutorDAO.atualizar(tutorDTO);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }

    public void cadastrarBO(TutorDTO tutorDTO) throws NegocioException {

        try {

            TutorDAO tutorDAO = new TutorDAO();

            tutorDAO.inserir(tutorDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }

    }

    public boolean duplicidade(TutorDTO tutorDTO) throws PersistenciaException {

        TutorDAO tutorDAO = new TutorDAO();

        //a verificação de Duplicidade será feita a partir do CPF do Paciente
        boolean verificaDuplicidade = tutorDAO.verificaDuplicidade(tutorDTO);

        //retorna o valor boleano com true ou false
        return verificaDuplicidade;
    }

    //VALIDAÇÃO INDEXADA
    public boolean validacaoBOdosCamposForm(TutorDTO tutorDTO) throws ValidacaoException {

        boolean ehValido = true;

        
        if (tutorDTO.getNomeDto() == null
                || tutorDTO.getNomeDto().equals("")
                || tutorDTO.getNomeDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo paciente Obrigatorio");

        }

        if (tutorDTO.getNomeDto().length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo paciente aceita no MAX 49 chars");

        }

        //campo cpf
       
        if (tutorDTO.getCpfDto().equals("   .   .   -  ")) {

            ehValido = false;
            throw new ValidacaoException("Campo cpf Obrigatorio");

        }

        if (tutorDTO.getCpfDto().length() > 14) {

            ehValido = false;
            throw new ValidacaoException("Campo cpf aceita no MAX 14 chars");

        }

        //campo estado 
        if (tutorDTO.getEstadoDto() == null
                || tutorDTO.getEstadoDto().equals("")
                || tutorDTO.getEstadoDto().toString().isEmpty()) {

            ehValido = false;

            throw new ValidacaoException("Campo estado Obrigatorio");

        }

        if (tutorDTO.getEstadoDto().length() > 40) {

            ehValido = false;
            throw new ValidacaoException("Campo estado aceita no MAX 40 chars");

        }

        //campo cidade 
         if (
                 tutorDTO.getCidadeDto() == null
                 || tutorDTO.getCidadeDto().equals("")
                 || tutorDTO.getCidadeDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo cidade Obrigatorio");

        }

        if (tutorDTO.getCidadeDto().length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo cidade aceita no MAX 49 chars");

        }
        
        //campo bairro
          if (
                  tutorDTO.getBairroDto() == null || 
                  tutorDTO.getBairroDto().equals("") 
                  || tutorDTO.getBairroDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo bairro Obrigatorio");

        }

        if (tutorDTO.getBairroDto().length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo bairro aceita no MAX 49 chars");

        }
        
        //campo rua 
        if (tutorDTO.getRuaDto() == null
                || tutorDTO.getRuaDto().equals("")
                || tutorDTO.getRuaDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo rua Obrigatorio");

        }

        if (tutorDTO.getRuaDto().length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo rua aceita no MAX 49 chars");

        }

        
        
        
        return ehValido;
    }

}
