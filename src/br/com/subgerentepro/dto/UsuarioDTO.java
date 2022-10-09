package br.com.subgerentepro.dto;

import br.com.subgerentepro.metodosstatics.MetodoStaticosUtil;

/**
 *
 * @author DaTorres
 */
public class UsuarioDTO {

    private Integer idUserDto;
    private String UsuarioDto;
    private String telefoneDto;
    private String loginDto;
    private String senhaDto;
    private String perfilDto;
    private String sexoDto;
    
    private String gmailDto;
  
    /**
     * @return the idUserDto
     */
    public Integer getIdUserDto() {
        return idUserDto;
    }

    /**
     * @param idUserDto the idUserDto to set
     */
    public void setIdUserDto(Integer idUserDto) {
        this.idUserDto = idUserDto;
    }

    /**
     * @return the UsuarioDto
     */
    public String getUsuarioDto() {
        return UsuarioDto;
    }

    /**
     * @param UsuarioDto the UsuarioDto to set
     */
    public void setUsuarioDto(String UsuarioDto) {
        this.UsuarioDto = UsuarioDto;
        this.UsuarioDto = MetodoStaticosUtil.removerAcentosCaixAlta(UsuarioDto.trim());
    }

    /**
     * @return the telefoneDto
     */
    public String getTelefoneDto() {
        return telefoneDto;
    }

    /**
     * @param telefoneDto the telefoneDto to set
     */
    public void setTelefoneDto(String telefoneDto) {
        this.telefoneDto = telefoneDto;
    }

    /**
     * @return the loginDto
     */
    public String getLoginDto() {
        return loginDto;
    }

    /**
     * @param loginDto the loginDto to set
     */
    public void setLoginDto(String loginDto) {
        this.loginDto = loginDto;
    }

    /**
     * @return the senhaDto
     */
    public String getSenhaDto() {
        return senhaDto;
    }

    /**
     * @param senhaDto the senhaDto to set
     */
    public void setSenhaDto(String senhaDto) {
        this.senhaDto = senhaDto;
    }

    /**
     * @return the perfilDto
     */
    public String getPerfilDto() {
        return perfilDto;
    }

    /**
     * @param perfilDto the perfilDto to set
     */
    public void setPerfilDto(String perfilDto) {
        this.perfilDto = perfilDto;
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
     * @return the gmailDto
     */
    public String getGmailDto() {
        return gmailDto;
    }

    /**
     * @param gmailDto the gmailDto to set
     */
    public void setGmailDto(String gmailDto) {
        this.gmailDto = gmailDto;
    }

}
