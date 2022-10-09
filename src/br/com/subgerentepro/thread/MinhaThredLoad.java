package br.com.subgerentepro.thread;

import br.com.subgerentepro.jbdc.ConexaoUtil;
import static br.com.subgerentepro.telas.Login.lblLogin;
import br.com.subgerentepro.telas.Splash;
import static br.com.subgerentepro.telas.Splash.lbl;
import br.com.subgerentepro.telas.TelaPrincipal;
import static br.com.subgerentepro.telas.TelaPrincipal.barraProgresso;
import static br.com.subgerentepro.telas.TelaPrincipal.lblAlertaCritical;
import static br.com.subgerentepro.telas.TelaPrincipal.lblVerificacao;
import static br.com.subgerentepro.telas.TelaPrincipal.painelAlertaCritico;
import static br.com.subgerentepro.telas.TelaPrincipal.painelInferior;
import java.awt.Color;
import java.awt.Frame;
import static java.lang.Thread.sleep;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Dã Torres Primeria coisa a fazer é extender a classe Thread do pacote
 * java lang
 */
public class MinhaThredLoad extends Thread {

    //***********************************************//
    //AULA LOIANE GRONNER                           //
    //https://www.youtube.com/watch?v=v5l30QMKv6c  //
    //********************************************//
    private String nomeThread;
    private int tempo;

    //criar um construtor que irá receber o nome dessa Thread 
    public MinhaThredLoad(String nomeTarefa, int tempo) {

        this.nomeThread = nomeTarefa;
        this.tempo = tempo;

        //para não ficarmos chamando o método start toda vez que criarmos uma thread 
        //colocamos ele dentro do meu construror 
        start();

    }

    //para uma Thread ser executada devemos sobrescrever o método run que tem
    // a seguinte assinatura 
    public void run() {

        Splash ss = new Splash();
        ss.setState (Frame.NORMAL);
      //  ss.toFront();
        ss.setVisible(true);

        int i = 0;

        while (i < tempo) {

            i++;
            
            try {
                lbl.setVisible(true);
                lbl.setText(String.valueOf(i) + "%");
                sleep(1000);
            } catch (Exception e) {
            }
            if(i==tempo-1){
            ss.dispose();
            }
        }

    }

    //Dica interessante chamar outro form
    //new Frm().setVisible(true);  
}
