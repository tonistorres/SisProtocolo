
package br.com.subgerentepro.dto;

/**
 *
 * @author DaTorres
 */
public class BancoEmpresaDTO {
    
    //idBanco,fk_id_empresa,Banco,Agencia,ContaCorrente,favorecido
    
  private int idBancoDto;//idBanco
  private int fkEmpresaDto;//fk_id_empresa
  private String bancoDto;//Banco
  private String agenciaDto;//Agencia
  private String tipoContaDto;
  private String contaCorrenteDto;//ContaCorrente
  private String favorecidoDto;//favorecido
  private String perfilClienteDto;//perfilCliente

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
     * @return the fkEmpresaDto
     */
    public int getFkEmpresaDto() {
        return fkEmpresaDto;
    }

    /**
     * @param fkEmpresaDto the fkEmpresaDto to set
     */
    public void setFkEmpresaDto(int fkEmpresaDto) {
        this.fkEmpresaDto = fkEmpresaDto;
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
  
}
