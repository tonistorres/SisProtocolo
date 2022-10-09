
package br.com.subgerentepro.dto;

import java.util.ArrayList;

/**
 *
 * @author DaTorres
 */
public class ModeloTutorBancoDTO {

    private BancoTutorDTO modeloBancoTutorDto;
    private TutorDTO modeloTutorDto;
    private PacienteDTO modeloPacienteDTO;
    private ArrayList<ModeloTutorBancoDTO>listaTutorBancoDto;

    /**
     * @return the modeloBancoTutorDto
     */
    public BancoTutorDTO getModeloBancoTutorDto() {
        return modeloBancoTutorDto;
    }

    /**
     * @param modeloBancoTutorDto the modeloBancoTutorDto to set
     */
    public void setModeloBancoTutorDto(BancoTutorDTO modeloBancoTutorDto) {
        this.modeloBancoTutorDto = modeloBancoTutorDto;
    }

    /**
     * @return the modeloTutorDto
     */
    public TutorDTO getModeloTutorDto() {
        return modeloTutorDto;
    }

    /**
     * @param modeloTutorDto the modeloTutorDto to set
     */
    public void setModeloTutorDto(TutorDTO modeloTutorDto) {
        this.modeloTutorDto = modeloTutorDto;
    }

    /**
     * @return the listaTutorBancoDto
     */
    public ArrayList<ModeloTutorBancoDTO> getListaTutorBancoDto() {
        return listaTutorBancoDto;
    }

    /**
     * @param listaTutorBancoDto the listaTutorBancoDto to set
     */
    public void setListaTutorBancoDto(ArrayList<ModeloTutorBancoDTO> listaTutorBancoDto) {
        this.listaTutorBancoDto = listaTutorBancoDto;
    }

    /**
     * @return the modeloPacienteDTO
     */
    public PacienteDTO getModeloPacienteDTO() {
        return modeloPacienteDTO;
    }

    /**
     * @param modeloPacienteDTO the modeloPacienteDTO to set
     */
    public void setModeloPacienteDTO(PacienteDTO modeloPacienteDTO) {
        this.modeloPacienteDTO = modeloPacienteDTO;
    }

  

   
  

   
}
