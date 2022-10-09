package br.com.subgerentepro.dao;

import br.com.subgerentepro.dto.BancoTutorDTO;
import br.com.subgerentepro.dto.TutorDTO;
import br.com.subgerentepro.dto.ModeloTutorBancoDTO;
import br.com.subgerentepro.dto.PacienteDTO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import br.com.subgerentepro.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author DaTorres
 */
public class ModeloTutorBancoDAO {

   
    public List<ModeloTutorBancoDTO> filtrarPorCodigo(int codigo) throws PersistenciaException {

        List<ModeloTutorBancoDTO> listar = new ArrayList<>();

        BancoTutorDTO bancoTutorDTO = new BancoTutorDTO();
        PacienteDTO pacienteDTO = new PacienteDTO();
        TutorDTO tutorDTO = new TutorDTO();
        ModeloTutorBancoDTO modeloTutorBancoDTO = new ModeloTutorBancoDTO();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            //inner join entre 3 tabelas 
            //https://pt.stackoverflow.com/questions/72710/inner-join-com-3-tabelas
            String sql = "SELECT * FROM bancotutortfd\n"
                    + "INNER JOIN tbtfd ON tbtfd.idpaciente=bancotutortfd.fk_id_tfd\n"
                    + "INNER JOIN tbtutortfd ON tbtutortfd.codtutor=bancotutortfd.fk_id_tutor WHERE bancotutortfd.idBanco=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                bancoTutorDTO = new BancoTutorDTO();
                tutorDTO = new TutorDTO();
                pacienteDTO = new PacienteDTO();

                modeloTutorBancoDTO = new ModeloTutorBancoDTO();

                //Banconometutor
                bancoTutorDTO.setIdBancoDto(resultSet.getInt("idBanco"));
                bancoTutorDTO.setFk_id_tfd(resultSet.getInt("fk_id_tfd"));
                bancoTutorDTO.setFk_id_tutor(resultSet.getInt("fk_id_tutor"));
                bancoTutorDTO.setBancoDto(resultSet.getString("Banco"));
                bancoTutorDTO.setAgenciaDto(resultSet.getString("Agencia"));
                bancoTutorDTO.setContaCorrenteDto(resultSet.getString("Conta"));
                bancoTutorDTO.setFavorecidoDto(resultSet.getString("favorecido"));
                bancoTutorDTO.setTipoContaDto(resultSet.getString("TipoConta"));

                //TutorDTO
                tutorDTO.setIdDto(resultSet.getInt("codtutor"));
                tutorDTO.setNomeDto(resultSet.getString("nometutor"));

                //PacienteDTO
                pacienteDTO.setIdPacienteDto(resultSet.getInt("idpaciente"));
                pacienteDTO.setNomePacienteDto(resultSet.getString("nomepaciente"));

                modeloTutorBancoDTO.setModeloBancoTutorDto(bancoTutorDTO);
                modeloTutorBancoDTO.setModeloTutorDto(tutorDTO);
                modeloTutorBancoDTO.setModeloPacienteDTO(pacienteDTO);
                listar.add(modeloTutorBancoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listar;

    }

}
