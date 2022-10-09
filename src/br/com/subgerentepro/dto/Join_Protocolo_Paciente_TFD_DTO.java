package br.com.subgerentepro.dto;

import java.util.ArrayList;


public class Join_Protocolo_Paciente_TFD_DTO {

    private ProtocoloTFDDTO itensDoProtocolo;
    private PacienteDTO itensPaciente;
     private ArrayList<Join_Protocolo_Paciente_TFD_DTO>listaModeloVendaClientes;

    /**
     * @return the itensDoProtocolo
     */
    public ProtocoloTFDDTO getItensDoProtocolo() {
        return itensDoProtocolo;
    }

    /**
     * @param itensDoProtocolo the itensDoProtocolo to set
     */
    public void setItensDoProtocolo(ProtocoloTFDDTO itensDoProtocolo) {
        this.itensDoProtocolo = itensDoProtocolo;
    }

    /**
     * @return the itensPaciente
     */
    public PacienteDTO getItensPaciente() {
        return itensPaciente;
    }

    /**
     * @param itensPaciente the itensPaciente to set
     */
    public void setItensPaciente(PacienteDTO itensPaciente) {
        this.itensPaciente = itensPaciente;
    }

    /**
     * @return the listaModeloVendaClientes
     */
    public ArrayList<Join_Protocolo_Paciente_TFD_DTO> getListaModeloVendaClientes() {
        return listaModeloVendaClientes;
    }

    /**
     * @param listaModeloVendaClientes the listaModeloVendaClientes to set
     */
    public void setListaModeloVendaClientes(ArrayList<Join_Protocolo_Paciente_TFD_DTO> listaModeloVendaClientes) {
        this.listaModeloVendaClientes = listaModeloVendaClientes;
    }
    
    
    
}
