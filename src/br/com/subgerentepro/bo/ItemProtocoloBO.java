package br.com.subgerentepro.bo;

import br.com.subgerentepro.dao.ItemProtocoloDAO;
import br.com.subgerentepro.dto.ItemProtocoloDTO;
import br.com.subgerentepro.exceptions.NegocioException;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.exceptions.ValidacaoException;
import javax.swing.JOptionPane;

public class ItemProtocoloBO {

    public void atualizarBO(ItemProtocoloDTO itemProtocoloDTO) {

        try {

            ItemProtocoloDAO itemProtocoloDAO = new ItemProtocoloDAO();

            itemProtocoloDAO.atualizar(itemProtocoloDTO);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }

    public void cadastrarBO(ItemProtocoloDTO itemProtocoloDTO) throws NegocioException {

        try {

            ItemProtocoloDAO itemProtocoloDAO = new ItemProtocoloDAO();

            itemProtocoloDAO.inserir(itemProtocoloDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }

    }

    public boolean duplicidade(ItemProtocoloDTO itemProtocoloDTO) throws PersistenciaException {

        ItemProtocoloDAO itemProtocoloDAO = new ItemProtocoloDAO();

        //a verificação de Duplicidade será feita a partir do CPF do Paciente
        boolean verificaDuplicidade = itemProtocoloDAO.verificaDuplicidade(itemProtocoloDTO);

        //retorna o valor boleano com true ou false
        return verificaDuplicidade;
    }

    //VALIDAÇÃO INDEXADA
    public boolean validacaoBOdosCamposForm(ItemProtocoloDTO itemProtocoloDTO) throws ValidacaoException {

        boolean ehValido = true;

        
        if (itemProtocoloDTO.getNomeDto() == null
                || itemProtocoloDTO.getNomeDto().equals("")
                || itemProtocoloDTO.getNomeDto().toString().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo paciente Obrigatorio");

        }

        if (itemProtocoloDTO.getNomeDto().length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo paciente aceita no MAX 49 chars");

        }

       

        
        
        return ehValido;
    }

}
