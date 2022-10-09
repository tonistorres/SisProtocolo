package br.com.subgerentepro.tabelas;

import br.com.subgerentepro.dto.FluxoTFDDTO;
import br.com.subgerentepro.dao.FluxoTFDDAO;
import br.com.subgerentepro.exceptions.PersistenciaException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TabelaNuvem {

    FluxoTFDDTO fluxoDTO = new FluxoTFDDTO();
    FluxoTFDDAO fluxoDAO = new FluxoTFDDAO();

    public void ver_tabela(JTable tabela) throws PersistenciaException {

        tabela.setDefaultRenderer(Object.class, new RenderTabelaNuvem());

        

        ArrayList<FluxoTFDDTO> list;
        list = (ArrayList<FluxoTFDDTO>) fluxoDAO.listarTodos();

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        JButton btnReciboTFD = new JButton("Recibo");
        btnReciboTFD.setName("idReciboTFD");
        JButton btnCapaTFD = new JButton("capa");
        btnCapaTFD.setName("idCapaTFD");
        
        try {
            list = (ArrayList<FluxoTFDDTO>) fluxoDAO.listarTodos();

            Object rowData[] = new Object[5];//são 06 colunas 

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdCustomDto();
                rowData[1] = list.get(i).getInteressadoOrigemDto();
                rowData[2] = list.get(i).getCpfDto();

                /**
                 * Adicionamos mais dois campos na tabela tblListagem que serão
                 * responsável por assim dizer alocarem os espaços dos botões
                 * Editar e Deletar
                 */
                rowData[3] = btnReciboTFD;
                rowData[4] = btnCapaTFD;
                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(60);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(60);

            tabela.setPreferredScrollableViewportSize(tabela.getPreferredSize());

        } catch (PersistenciaException ex) {

            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }
    }
}
