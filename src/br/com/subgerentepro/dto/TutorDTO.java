
package br.com.subgerentepro.dto;

import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;
import java.util.Date;


public class TutorDTO {
    
    private int idDto;//codtutor
    private String nomeDto;//nometutor
    private String cpfDto;//cpftutor
    private int fkEstadoDto;//fk_estado
    private String estadoDto;//inner join
    private String cidadeDto;//inner join 
    private String bairroDto;//inner join 
    private int fkCidadeDto;//fk_cidade 
    private int fkBairroDto;//fk_bairro 
    private String ruaDto;//ruatutor
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
        this.nomeDto =MetodoStaticosUtil.removerAcentosCaixAlta(nomeDto) ;
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
     * @return the ruaDto
     */
    public String getRuaDto() {
        return ruaDto;
    }

    /**
     * @param ruaDto the ruaDto to set
     */
    public void setRuaDto(String ruaDto) {
        this.ruaDto = ruaDto;
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

    
}
