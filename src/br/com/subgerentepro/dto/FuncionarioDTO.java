
package br.com.subgerentepro.dto;

import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import java.util.Date;


public class FuncionarioDTO {
    
    private int idDto;//idpaciente
    private String nomeDto;//nomepaciente
    private String departamentoDto;//inner join
    private String sexoDto;//inner join 
    private String celularDto;//inner join 
    private String cpfDto;//cpf
    private Date cadastroTimeStampDto;//numerocartaosus

    /**
     * @return the idDto
     */
    public int getIdDto() {
        return idDto;
    }

    /**
     * @param idDto the idDto to set
     */
    public void setIdDto(int idDto) {
        this.idDto = idDto;
    }

    /**
     * @return the nomeDto
     */
    public String getNomeDto() {
        return nomeDto;
    }

    /**
     * @param nomeDto the nomeDto to set
     */
    public void setNomeDto(String nomeDto) {
        this.nomeDto = MetodoStaticosUtil.removerAcentosCaixAlta(nomeDto.toUpperCase().trim());
    }

    /**
     * @return the departamentoDto
     */
    public String getDepartamentoDto() {
        return departamentoDto;
    }

    /**
     * @param departamentoDto the departamentoDto to set
     */
    public void setDepartamentoDto(String departamentoDto) {
        this.departamentoDto = departamentoDto;
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

    /**
     * @return the celularDto
     */
    public String getCelularDto() {
        return celularDto;
    }

    /**
     * @param celularDto the celularDto to set
     */
    public void setCelularDto(String celularDto) {
        this.celularDto = celularDto;
    }

    /**
     * @return the cadastroTimeStampDto
     */
    public Date getCadastroTimeStampDto() {
        return cadastroTimeStampDto;
    }

    /**
     * @return the cpfDto
     */
    public String getCpfDto() {
        return cpfDto;
    }

    /**
     * @param cpfDto the cpfDto to set
     */
    public void setCpfDto(String cpfDto) {
        this.cpfDto = cpfDto;
    }
  
  
}
