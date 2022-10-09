
package br.com.subgerentepro.telas;


public class TelaDevolucaoCorrecaoTFD extends javax.swing.JInternalFrame {
    public TelaDevolucaoCorrecaoTFD() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idCustoMotivoCorrecao = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaDecreveMotivo = new javax.swing.JTextArea();
        txtIDChaveIdentificacao = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(idCustoMotivoCorrecao, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 134, 30));

        txtAreaDecreveMotivo.setColumns(20);
        txtAreaDecreveMotivo.setRows(5);
        jScrollPane1.setViewportView(txtAreaDecreveMotivo);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 370, 120));

        txtIDChaveIdentificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDChaveIdentificacaoActionPerformed(evt);
            }
        });
        getContentPane().add(txtIDChaveIdentificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 50, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Descrever o Motivo Devolução TFD :");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Nº Protocolo:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Chave ID:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIDChaveIdentificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDChaveIdentificacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDChaveIdentificacaoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField idCustoMotivoCorrecao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtAreaDecreveMotivo;
    private javax.swing.JTextField txtIDChaveIdentificacao;
    // End of variables declaration//GEN-END:variables
}
