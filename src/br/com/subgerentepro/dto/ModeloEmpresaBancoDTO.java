
package br.com.subgerentepro.dto;

import java.util.ArrayList;

/**
 *
 * @author DaTorres
 */
public class ModeloEmpresaBancoDTO {

    private BancoEmpresaDTO modeloBancoEmpresaDto;
    private EmpresaDTO modeloEmpresaDto;
    private ArrayList<ModeloEmpresaBancoDTO>listaEmpresaBancoDto;

    /**
     * @return the modeloBancoEmpresaDto
     */
    public BancoEmpresaDTO getModeloBancoEmpresaDto() {
        return modeloBancoEmpresaDto;
    }

    /**
     * @param modeloBancoEmpresaDto the modeloBancoEmpresaDto to set
     */
    public void setModeloBancoEmpresaDto(BancoEmpresaDTO modeloBancoEmpresaDto) {
        this.modeloBancoEmpresaDto = modeloBancoEmpresaDto;
    }

    /**
     * @return the modeloEmpresaDto
     */
    public EmpresaDTO getModeloEmpresaDto() {
        return modeloEmpresaDto;
    }

    /**
     * @param modeloEmpresaDto the modeloEmpresaDto to set
     */
    public void setModeloEmpresaDto(EmpresaDTO modeloEmpresaDto) {
        this.modeloEmpresaDto = modeloEmpresaDto;
    }

    /**
     * @return the listaEmpresaBancoDto
     */
    public ArrayList<ModeloEmpresaBancoDTO> getListaEmpresaBancoDto() {
        return listaEmpresaBancoDto;
    }

    /**
     * @param listaEmpresaBancoDto the listaEmpresaBancoDto to set
     */
    public void setListaEmpresaBancoDto(ArrayList<ModeloEmpresaBancoDTO> listaEmpresaBancoDto) {
        this.listaEmpresaBancoDto = listaEmpresaBancoDto;
    }


   
}
