
package br.com.subgerentepro.dto;

import java.util.ArrayList;

/**
 *
 * @author DaTorres
 */
public class ModeloEmpresaUfCidadeBairroDTO {

    private EstadoDTO estadoModeloDTO;
    private CidadeDTO cidadeModeloDTO;
    private EmpresaDTO empresaModeloDTO;
    private ArrayList<ModeloEmpresaUfCidadeBairroDTO>listaComEmpresaUfCidadeBairroDTO;

    /**
     * @return the estadoModeloDTO
     */
    public EstadoDTO getEstadoModeloDTO() {
        return estadoModeloDTO;
    }

    /**
     * @param estadoModeloDTO the estadoModeloDTO to set
     */
    public void setEstadoModeloDTO(EstadoDTO estadoModeloDTO) {
        this.estadoModeloDTO = estadoModeloDTO;
    }

    /**
     * @return the cidadeModeloDTO
     */
    public CidadeDTO getCidadeModeloDTO() {
        return cidadeModeloDTO;
    }

    /**
     * @param cidadeModeloDTO the cidadeModeloDTO to set
     */
    public void setCidadeModeloDTO(CidadeDTO cidadeModeloDTO) {
        this.cidadeModeloDTO = cidadeModeloDTO;
    }

    /**
     * @return the listaComEmpresaUfCidadeBairroDTO
     */
    public ArrayList<ModeloEmpresaUfCidadeBairroDTO> getListaComEmpresaUfCidadeBairroDTO() {
        return listaComEmpresaUfCidadeBairroDTO;
    }

    /**
     * @param listaComEmpresaUfCidadeBairroDTO the listaComEmpresaUfCidadeBairroDTO to set
     */
    public void setListaComEmpresaUfCidadeBairroDTO(ArrayList<ModeloEmpresaUfCidadeBairroDTO> listaComEmpresaUfCidadeBairroDTO) {
        this.listaComEmpresaUfCidadeBairroDTO = listaComEmpresaUfCidadeBairroDTO;
    }

    /**
     * @return the empresaModeloDTO
     */
    public EmpresaDTO getEmpresaModeloDTO() {
        return empresaModeloDTO;
    }

    /**
     * @param empresaModeloDTO the empresaModeloDTO to set
     */
    public void setEmpresaModeloDTO(EmpresaDTO empresaModeloDTO) {
        this.empresaModeloDTO = empresaModeloDTO;
    }

}
