/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.subgerentepro.componentes;

import static br.com.subgerentepro.telas.TelaUsuario.btnAdicionar;
import static br.com.subgerentepro.telas.TelaUsuario.btnCancelar;
import static br.com.subgerentepro.telas.TelaUsuario.btnExcluir;
import static br.com.subgerentepro.telas.TelaUsuario.btnSalvar;


import java.awt.Dimension;
import javax.swing.JButton;

/**
 *
 * @author Dã Torres
 */
public class ComponentesFormTelaUsuario {
    
    public void personalizaBotoesFormTelaUsuario(JButton btnAdicionar, JButton btnEditar,JButton btnSalvar,JButton btnExcluir,JButton btnPesquisar,JButton btnCancelar){
    
       //BOTAO ADICIONAR 
        btnAdicionar.setMinimumSize(new Dimension(45, 45));
        btnAdicionar.setPreferredSize(new Dimension(45, 45));
        btnAdicionar.setMaximumSize(new Dimension(45, 45));
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
      
        //BOTAO EDITAR 
        btnEditar.setMinimumSize(new Dimension(45, 45));
        btnEditar.setPreferredSize(new Dimension(45, 45));
        btnEditar.setMaximumSize(new Dimension(45, 45));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editar.png")));
        
           //BOTAO SALVAR 
        btnSalvar.setMinimumSize(new Dimension(45, 45));
        btnSalvar.setPreferredSize(new Dimension(45, 45));
        btnSalvar.setMaximumSize(new Dimension(45, 45));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));
        
        //BOTAO EXCLUIR 
        btnExcluir.setMinimumSize(new Dimension(45, 45));
        btnExcluir.setPreferredSize(new Dimension(45, 45));
        btnExcluir.setMaximumSize(new Dimension(45, 45));
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/excluir.png")));
        
        //BOTAO EXCLUIR 
        btnCancelar.setMinimumSize(new Dimension(45, 45));
        btnCancelar.setPreferredSize(new Dimension(45, 45));
        btnCancelar.setMaximumSize(new Dimension(45, 45));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        
        //BOTAO PESQUISAR 
        btnPesquisar.setMinimumSize(new Dimension(32, 35));
        btnPesquisar.setPreferredSize(new Dimension(32, 35));
        btnPesquisar.setMaximumSize(new Dimension(32, 35));
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        
        
        
    }
    
  
    
}
