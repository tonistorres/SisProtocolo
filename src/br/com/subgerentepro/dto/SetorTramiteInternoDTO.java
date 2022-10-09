
package br.com.subgerentepro.dto;

import java.util.Date;


public class SetorTramiteInternoDTO {
    
    private int idDto;//codtutor
    private String nomeDto;//nometutor
    private Date cadastroTimeStampDto;//cadastro

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
        this.nomeDto = nomeDto;
    }

   
    
}
