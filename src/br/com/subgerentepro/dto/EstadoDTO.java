package br.com.subgerentepro.dto;

import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;

/**
 * AULA 12 2ª CAMADA DTO(Data Transfer Object)Padrão de Projeto foi criado para
 * AUXILIAR na transferência de DADOS de um lado para outro dentro de um
 * projeto.sem lógica de negócios em seus objetos e comumente associado à
 * transferência de dados entre uma camada de visão (view layer) e outra de
 * persistência dos dados (model layer) Muito frequentemente você verá esse
 * padrão sendo usado em conjunto com um DAO. *
 * http://www.devmedia.com.br/diferenca-entre-os-patterns-po-pojo-bo-dto-e-vo/28162
 */
public class EstadoDTO {

    
    private Integer idEtadoDto;
    private String nomeEstadoDto;
    private String siglaEstadoDto;
   
    private String PesquisaDto;
    private String PesquisaParaComboCidade;

    
    public Integer getIdEtadoDto() {
        return idEtadoDto;
    }

    public void setIdEtadoDto(Integer idEtadoDto) {
        this.idEtadoDto = idEtadoDto;
    }

    public String getNomeEstadoDto() {
        return nomeEstadoDto;
    }

    public void setNomeEstadoDto(String nomeEstadoDto) {
        this.nomeEstadoDto = br.com.subgerentepro.metodosstatics.MetodoStaticosUtil.removerAcentosCaixAlta(nomeEstadoDto.trim());
    }

    public String getSiglaEstadoDto() {
        return  siglaEstadoDto;
    }

    public void setSiglaEstadoDto(String siglaEstadoDto) {
        this.siglaEstadoDto = MetodoStaticosUtil.removerAcentosCaixAlta(siglaEstadoDto.trim());
    }

    /**
     * @return the PesquisaDto
     */
    public String getPesquisaDto() {
        return PesquisaDto;
    }

    /**
     * @param PesquisaDto the PesquisaDto to set
     */
    public void setPesquisaDto(String PesquisaDto) {
        this.PesquisaDto = PesquisaDto;
    }

    /**
     * @return the PesquisaParaComboCidade
     */
    public String getPesquisaParaComboCidade() {
        return PesquisaParaComboCidade;
    }

    /**
     * @param PesquisaParaComboCidade the PesquisaParaComboCidade to set
     */
    public void setPesquisaParaComboCidade(String PesquisaParaComboCidade) {
        this.PesquisaParaComboCidade = PesquisaParaComboCidade;
    }

   

}
