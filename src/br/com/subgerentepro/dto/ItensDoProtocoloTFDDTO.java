package br.com.subgerentepro.dto;

public class ItensDoProtocoloTFDDTO {

    private String fkCustomDto;
    private String itensDoProtocoloDto;
    private int qtdeDto;
    private String descreverDto;

    /**
     * @return the fkCustomDto
     */
    public String getFkCustomDto() {
        return fkCustomDto;
    }

    /**
     * @param fkCustomDto the fkCustomDto to set
     */
    public void setFkCustomDto(String fkCustomDto) {
        this.fkCustomDto = fkCustomDto;
    }

    /**
     * @return the itensDoProtocoloDto
     */
    public String getItensDoProtocoloDto() {
        return itensDoProtocoloDto;
    }

    /**
     * @param itensDoProtocoloDto the itensDoProtocoloDto to set
     */
    public void setItensDoProtocoloDto(String itensDoProtocoloDto) {
        this.itensDoProtocoloDto = itensDoProtocoloDto;
    }

    /**
     * @return the qtdeDto
     */
    public int getQtdeDto() {
        return qtdeDto;
    }

    /**
     * @param qtdeDto the qtdeDto to set
     */
    public void setQtdeDto(int qtdeDto) {
        this.qtdeDto = qtdeDto;
    }

    /**
     * @return the descreverDto
     */
    public String getDescreverDto() {
        return descreverDto;
    }

    /**
     * @param descreverDto the descreverDto to set
     */
    public void setDescreverDto(String descreverDto) {
        this.descreverDto = descreverDto;
    }

}
