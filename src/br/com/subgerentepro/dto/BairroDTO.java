package br.com.subgerentepro.dto;

import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;

public class BairroDTO {
    
    
    private Integer idBairroDto;
    private String nomeBairroDto;
    private Integer chaveEstrangeiraIdCidadeDto;
    
    private String nomeCidadeRecuperarDto;

    /**
     * @return the idBairroDto
     */
    public Integer getIdBairroDto() {
        return idBairroDto;
    }

    /**
     * @param idBairroDto the idBairroDto to set
     */
    public void setIdBairroDto(Integer idBairroDto) {
        this.idBairroDto = idBairroDto;
    }

    /**
     * @return the nomeBairroDto
     */
    public String getNomeBairroDto() {
        return nomeBairroDto;
    }

    /**
     * @param nomeBairroDto the nomeBairroDto to set
     */
    public void setNomeBairroDto(String nomeBairroDto) {
        this.nomeBairroDto = MetodoStaticosUtil.removerAcentosCaixAlta(nomeBairroDto.trim());
    }

    /**
     * @return the chaveEstrangeiraIdCidadeDto
     */
    public Integer getChaveEstrangeiraIdCidadeDto() {
        return chaveEstrangeiraIdCidadeDto;
    }

    /**
     * @param chaveEstrangeiraIdCidadeDto the chaveEstrangeiraIdCidadeDto to set
     */
    public void setChaveEstrangeiraIdCidadeDto(Integer chaveEstrangeiraIdCidadeDto) {
        this.chaveEstrangeiraIdCidadeDto = chaveEstrangeiraIdCidadeDto;
    }

    /**
     * @return the nomeCidadeRecuperarDto
     */
    public String getNomeCidadeRecuperarDto() {
        return nomeCidadeRecuperarDto;
    }

    /**
     * @param nomeCidadeRecuperarDto the nomeCidadeRecuperarDto to set
     */
    public void setNomeCidadeRecuperarDto(String nomeCidadeRecuperarDto) {
        this.nomeCidadeRecuperarDto = nomeCidadeRecuperarDto;
    }
    
}
