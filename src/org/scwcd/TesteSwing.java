package org.scwcd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * User: grinvon
 * Date: 10/05/2006
 * Time: 02:18:50
 *https://www.guj.com.br/t/limpando-todos-os-campos-jtextfield/31678/11
 * @author Inocêncio
 */
public class TesteSwing extends JFrame {

    //criou 3 objetos do tipo JTextField e em seu construtor ja passou valores do tipo 
    //texto campo1,2 e 3
    private JTextField text1 = new JTextField("texto campo 1");
    private JTextField text2 = new JTextField("texto campo 2");
    private JTextField text3 = new JTextField("texto campo 3");

    //criou tambm um obejto do tipo JButton  e nomeou como btnOK
    private JButton btnOK = new JButton();

    public TesteSwing() {

        super("Janela Teste");

        btnOK.setAction(acaoBotao());

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(text1);
        getContentPane().add(text2);
        getContentPane().add(text3);
        getContentPane().add(btnOK);

        setSize(new Dimension(300,300));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public Action acaoBotao() {

        Action acao = new AbstractAction() {

            public void actionPerformed(ActionEvent event) {

                //limpa os campos

                for (int i=0; i < getContentPane().getComponentCount(); i++) {
                    //varre todos os componentes

                    Component c = getContentPane().getComponent(i);

                    if (c instanceof JTextField) {
                        //apaga os valores
                        JTextField field = (JTextField) c;
                        field.setText("");
                        System.out.println("apagando campo " + i);
                    }
                }
            }
        };

        acao.putValue(Action.NAME, "Limpa Campos");

        return acao;
    }



    public static void main(String[] args) {
        new TesteSwing();
    }
}