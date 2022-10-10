package br.com.subgerentepro.jbdc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexaoUtil {

    private static ConexaoUtil connexaoUtil;

    public static ConexaoUtil getInstance() {

        if (connexaoUtil == null) {

            connexaoUtil = new ConexaoUtil();
        }
        return connexaoUtil;
    }

    //TRABALHANDO DE FORMA LOCAL HOST TESTE 
//    public Connection getConnection() throws ClassNotFoundException, SQLException {
//
//        Class.forName("com.mysql.jdbc.Driver");
//
//        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/inovec87_infoq", "root", "");
//
//    }

    
    

 //  TRABALHANDO COM CONEXAO NUVEM HOSTGATOR 
    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");


        return DriverManager.getConnection("jdbc:mysql://localhost:3306/infoq", "root", "1020");

    }
    
    
    
    
    //TRABALHANDO DE FORMA LOCAL HOST NUVEM HOSTGATOR 
//    public boolean ConexaoVerificaEstado() {
//
//        try {
//
//            Class.forName("com.mysql.jdbc.Driver");
//
//            Connection conexao = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/inovec87_infoq", "root", "");
//
//            conexao.close();
//
//            return true;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            //JOptionPane.showMesssageDialog(null,"Erro ao se Conectar"+ e.getMessage());
//        }
//
//        return false;
//
//    }

    
    
   
    /**
     * TRABALHANDO COM A CONEXAO NUVEM HOSTGATOR 
     */
    public boolean ConexaoVerificaEstado() {

        try {

            Class.forName("com.mysql.jdbc.Driver");



            Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/infoq", "root", "1020");

            conexao.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }

        return false;

    }

    
    
    
    
////VERIFICANDO A SEGURANÇA COM CLASSE NAS NUVENS UTILIZO TANTO PARA VERIFICAÇÃO LOCAL HOST COMO NUVEM 
//
    public Connection getConnectionSisSegHospeda() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("conectado sisHospeda");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/infoq", "root", "1020");

    }

}
