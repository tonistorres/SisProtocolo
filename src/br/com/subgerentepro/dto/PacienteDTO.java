
package br.com.subgerentepro.dto;

import java.util.Date;


public class PacienteDTO {
    
    private int idPacienteDto;//idpaciente
    private String nomePacienteDto;//nomepaciente
    private String cpfPacienteDto;//cpfpaciente
    private String sexoDto;
    private int fkEstadoDto;//fk_estado
    private String estadoDto;//inner join
    private String cidadeDto;//inner join 
    private String bairroDto;//inner join 
    private int fkCidadeDto;//fk_cidade 
    private int fkBairroDto;//fk_bairro 
    private String ruaPacienteDto;//ruapaciente
    private String numeroCartaoSusDto;//numerocartaosus
    
    private String vinculoDto;//vincular a um departamento TFD(Tratamento Fora Domicilio)
    private Date cadastroTimeStampDto;//numerocartaosus

    /**
     * @return the idPacienteDto
     */
    public int getIdPacienteDto() {
        return idPacienteDto;
    }

    /**
     * @param idPacienteDto the idPacienteDto to set
     */
    public void setIdPacienteDto(int idPacienteDto) {
        this.idPacienteDto = idPacienteDto;
    }

    /**
     * @return the nomePacienteDto
     */
    public String getNomePacienteDto() {
        return nomePacienteDto;
    }

    /**
     * @param nomePacienteDto the nomePacienteDto to set
     */
    public void setNomePacienteDto(String nomePacienteDto) {
        this.nomePacienteDto = nomePacienteDto;
    }

    /**
     * @return the cpfPacienteDto
     */
    public String getCpfPacienteDto() {
        return cpfPacienteDto;
    }

    /**
     * @param cpfPacienteDto the cpfPacienteDto to set
     */
    public void setCpfPacienteDto(String cpfPacienteDto) {
        this.cpfPacienteDto = cpfPacienteDto;
    }

    /**
     * @return the fkEstadoDto
     */
    public int getFkEstadoDto() {
        return fkEstadoDto;
    }

    /**
     * @param fkEstadoDto the fkEstadoDto to set
     */
    public void setFkEstadoDto(int fkEstadoDto) {
        this.fkEstadoDto = fkEstadoDto;
    }

    /**
     * @return the fkCidadeDto
     */
    public int getFkCidadeDto() {
        return fkCidadeDto;
    }

    /**
     * @param fkCidadeDto the fkCidadeDto to set
     */
    public void setFkCidadeDto(int fkCidadeDto) {
        this.fkCidadeDto = fkCidadeDto;
    }

    /**
     * @return the fkBairroDto
     */
    public int getFkBairroDto() {
        return fkBairroDto;
    }

    /**
     * @param fkBairroDto the fkBairroDto to set
     */
    public void setFkBairroDto(int fkBairroDto) {
        this.fkBairroDto = fkBairroDto;
    }

  
    /**
     * @return the numeroCartaoSusDto
     */
    public String getNumeroCartaoSusDto() {
        return numeroCartaoSusDto;
    }

    /**
     * @param numeroCartaoSusDto the numeroCartaoSusDto to set
     */
    public void setNumeroCartaoSusDto(String numeroCartaoSusDto) {
        this.numeroCartaoSusDto = numeroCartaoSusDto;
    }

    /**
     * @return the cadastroTimeStampDto
     */
    public Date getCadastroTimeStampDto() {
        return cadastroTimeStampDto;
    }

    /**
     * @param cadastroTimeStampDto the cadastroTimeStampDto to set
     */
    public void setCadastroTimeStampDto(Date cadastroTimeStampDto) {
        this.cadastroTimeStampDto = cadastroTimeStampDto;
    }

    /**
     * @return the ruaPacienteDto
     */
    public String getRuaPacienteDto() {
        return ruaPacienteDto;
    }

    /**
     * @param ruaPacienteDto the ruaPacienteDto to set
     */
    public void setRuaPacienteDto(String ruaPacienteDto) {
        this.ruaPacienteDto = ruaPacienteDto;
    }

    /**
     * @return the estadoDto
     */
    public String getEstadoDto() {
        return estadoDto;
    }

    /**
     * @param estadoDto the estadoDto to set
     */
    public void setEstadoDto(String estadoDto) {
        this.estadoDto = estadoDto;
    }

    /**
     * @return the cidadeDto
     */
    public String getCidadeDto() {
        return cidadeDto;
    }

    /**
     * @param cidadeDto the cidadeDto to set
     */
    public void setCidadeDto(String cidadeDto) {
        this.cidadeDto = cidadeDto;
    }

    /**
     * @return the bairroDto
     */
    public String getBairroDto() {
        return bairroDto;
    }

    /**
     * @param bairroDto the bairroDto to set
     */
    public void setBairroDto(String bairroDto) {
        this.bairroDto = bairroDto;
    }

    /**
     * @return the vinculoDto
     */
    public String getVinculoDto() {
        return vinculoDto;
    }

    /**
     * @param vinculoDto the vinculoDto to set
     */
    public void setVinculoDto(String vinculoDto) {
        this.vinculoDto = vinculoDto;
    }

    /**
     * @return the sexoDto
     */
    public String getSexoDto() {
        return sexoDto;
    }

    /**
     * @param sexoDto the sexoDto to set
     */
    public void setSexoDto(String sexoDto) {
        this.sexoDto = sexoDto;
    }
    
    
}
