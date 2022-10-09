
package br.com.subgerentepro.dto;

import java.util.Date;

/**
 *
 * @author DaTorres
 */
public class BancoTutorDTO {
    
    //idBanco,fk_id_empresa,Banco,Agencia,ContaCorrente,favorecido
    
  private int idBancoDto;//idBanco
  private int fk_id_tfd;//fk_id_tfd
  private String pacienteDto;//Paciente
  private String cpfPacienteDto;//cpfPaciente
  private int fk_id_tutor;//fk_id_tutor
  private String tutorDto;//Tutor
  private String bancoDto;//Banco
  private String agenciaDto;//Agencia
  private String tipoContaDto;//TipoConta
  private String contaCorrenteDto;//Conta
  private String favorecidoDto;//favorecido
  private String perfilClienteDto;//perfilCliente
  private Date cadastroDto; //cadastro

    /**
     * @return the idBancoDto
     */
    public int getIdBancoDto() {
        return idBancoDto;
    }

    /**
     * @param idBancoDto the idBancoDto to set
     */
    public void setIdBancoDto(int idBancoDto) {
        this.idBancoDto = idBancoDto;
    }

    /**
     * @return the fk_id_tfd
     */
    public int getFk_id_tfd() {
        return fk_id_tfd;
    }

    /**
     * @param fk_id_tfd the fk_id_tfd to set
     */
    public void setFk_id_tfd(int fk_id_tfd) {
        this.fk_id_tfd = fk_id_tfd;
    }

    /**
     * @return the fk_id_tutor
     */
    public int getFk_id_tutor() {
        return fk_id_tutor;
    }

    /**
     * @param fk_id_tutor the fk_id_tutor to set
     */
    public void setFk_id_tutor(int fk_id_tutor) {
        this.fk_id_tutor = fk_id_tutor;
    }

    /**
     * @return the bancoDto
     */
    public String getBancoDto() {
        return bancoDto;
    }

    /**
     * @param bancoDto the bancoDto to set
     */
    public void setBancoDto(String bancoDto) {
        this.bancoDto = bancoDto;
    }

    /**
     * @return the agenciaDto
     */
    public String getAgenciaDto() {
        return agenciaDto;
    }

    /**
     * @param agenciaDto the agenciaDto to set
     */
    public void setAgenciaDto(String agenciaDto) {
        this.agenciaDto = agenciaDto;
    }

    /**
     * @return the tipoContaDto
     */
    public String getTipoContaDto() {
        return tipoContaDto;
    }

    /**
     * @param tipoContaDto the tipoContaDto to set
     */
    public void setTipoContaDto(String tipoContaDto) {
        this.tipoContaDto = tipoContaDto;
    }

    /**
     * @return the contaCorrenteDto
     */
    public String getContaCorrenteDto() {
        return contaCorrenteDto;
    }

    /**
     * @param contaCorrenteDto the contaCorrenteDto to set
     */
    public void setContaCorrenteDto(String contaCorrenteDto) {
        this.contaCorrenteDto = contaCorrenteDto;
    }

    /**
     * @return the favorecidoDto
     */
    public String getFavorecidoDto() {
        return favorecidoDto;
    }

    /**
     * @param favorecidoDto the favorecidoDto to set
     */
    public void setFavorecidoDto(String favorecidoDto) {
        this.favorecidoDto = favorecidoDto;
    }

    /**
     * @return the perfilClienteDto
     */
    public String getPerfilClienteDto() {
        return perfilClienteDto;
    }

    /**
     * @param perfilClienteDto the perfilClienteDto to set
     */
    public void setPerfilClienteDto(String perfilClienteDto) {
        this.perfilClienteDto = perfilClienteDto;
    }

    /**
     * @return the cadastroDto
     */
    public Date getCadastroDto() {
        return cadastroDto;
    }

    /**
     * @param cadastroDto the cadastroDto to set
     */
    public void setCadastroDto(Date cadastroDto) {
        this.cadastroDto = cadastroDto;
    }

    /**
     * @return the pacienteDto
     */
    public String getPacienteDto() {
        return pacienteDto;
    }

    /**
     * @return the cpfPacienteDto
     */
    public String getCpfPacienteDto() {
        return cpfPacienteDto;
    }

    /**
     * @return the tutorDto
     */
    public String getTutorDto() {
        return tutorDto;
    }

    /**
     * @param pacienteDto the pacienteDto to set
     */
    public void setPacienteDto(String pacienteDto) {
        this.pacienteDto = pacienteDto;
    }

    /**
     * @param cpfPacienteDto the cpfPacienteDto to set
     */
    public void setCpfPacienteDto(String cpfPacienteDto) {
        this.cpfPacienteDto = cpfPacienteDto;
    }

    /**
     * @param tutorDto the tutorDto to set
     */
    public void setTutorDto(String tutorDto) {
        this.tutorDto = tutorDto;
    }

   
  
}
