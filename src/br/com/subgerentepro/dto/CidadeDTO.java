package br.com.subgerentepro.dto;

import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;

public class CidadeDTO {

private Integer codCidadeDto;
private String nomeCidadeDto;
private Integer chaveEstrangeiraIdEstadoDto;

private String siglaEstadoRecuperarTable;
private String pesquisaDto;


    /**
     * @return the codCidadeDto
     */
    public Integer getCodCidadeDto() {
        return codCidadeDto;
    }

    /**
     * @param codCidadeDto the codCidadeDto to set
     */
    public void setCodCidadeDto(Integer codCidadeDto) {
        this.codCidadeDto = codCidadeDto;
    }

    /**
     * @return the nomeCidadeDto
     */
    public String getNomeCidadeDto() {
        return nomeCidadeDto;
    }

    /**
     * @param nomeCidadeDto the nomeCidadeDto to set
     */
    public void setNomeCidadeDto(String nomeCidadeDto) {
        this.nomeCidadeDto = MetodoStaticosUtil.removerAcentosCaixAlta(nomeCidadeDto.toUpperCase().trim());
    }

    /**
     * @return the chaveEstrangeiraIdEstadoDto
     */
    public Integer getChaveEstrangeiraIdEstadoDto() {
        return chaveEstrangeiraIdEstadoDto;
    }

    /**
     * @param chaveEstrangeiraIdEstadoDto the chaveEstrangeiraIdEstadoDto to set
     */
    public void setChaveEstrangeiraIdEstadoDto(Integer chaveEstrangeiraIdEstadoDto) {
        this.chaveEstrangeiraIdEstadoDto = chaveEstrangeiraIdEstadoDto;
    }

    /**
     * @return the pesquisaDto
     */
    public String getPesquisaDto() {
        return pesquisaDto;
    }

    /**
     * @param pesquisaDto the pesquisaDto to set
     */
    public void setPesquisaDto(String pesquisaDto) {
        this.pesquisaDto = pesquisaDto;
    }

    /**
     * @return the siglaEstadoRecuperarTable
     */
    public String getSiglaEstadoRecuperarTable() {
        return siglaEstadoRecuperarTable;
    }

    /**
     * @param siglaEstadoRecuperarTable the siglaEstadoRecuperarTable to set
     */
    public void setSiglaEstadoRecuperarTable(String siglaEstadoRecuperarTable) {
        this.siglaEstadoRecuperarTable = siglaEstadoRecuperarTable;
    }

 
   
}
