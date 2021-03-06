package dividedespesa.presentation;


import dividedespesa.business.DivideDespesaFacade;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class RegistoII extends javax.swing.JDialog implements Observer {

    private static DivideDespesaFacade facade;
    private static String  nomeSenhorio, usernameSenhorio, passSenhorio,
                           usernameAdmin, passAdmin;
    
    /**
     * Creates new form RegistoII
     */
    public RegistoII(java.awt.Frame Registo, boolean modal,
                     DivideDespesaFacade facade, String nomeSenhorio,
                     String usernameSenhorio, String passSenhorio,
                     String usernameAdmin, String passAdmin) {
        super(Registo, modal);
        initComponents();
        this.facade = facade;
        this.usernameAdmin = usernameAdmin;
        this.usernameSenhorio = usernameSenhorio;
        this.nomeSenhorio = nomeSenhorio;
        this.passSenhorio = passSenhorio;
        this.passAdmin = passAdmin;
        this.facade.addObserver(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        numQuartos = new javax.swing.JLabel();
        terminaQuartos = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        adicionaQuarto = new javax.swing.JButton();
        rendaField = new javax.swing.JTextField();
        removeQuarto = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Adicionar Quartos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Nº Quartos");

        numQuartos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        numQuartos.setForeground(new java.awt.Color(102, 0, 0));
        numQuartos.setText("        0");
        numQuartos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                numQuartosComponentShown(evt);
            }
        });

        terminaQuartos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        terminaQuartos.setText("Terminar");
        terminaQuartos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                terminaQuartosActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton2.setText("Retroceder");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        adicionaQuarto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        adicionaQuarto.setText("Adicionar");
        adicionaQuarto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionaQuartoActionPerformed(evt);
            }
        });

        removeQuarto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        removeQuarto.setText("Remover");
        removeQuarto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeQuartoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Preço:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(numQuartos, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rendaField, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(removeQuarto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(adicionaQuarto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(terminaQuartos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(numQuartos))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adicionaQuarto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rendaField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeQuarto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(terminaQuartos))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int opcao = JOptionPane.showConfirmDialog(this,"Deseja retroceder?","Confirmaçao",JOptionPane.YES_NO_OPTION);
        
        if(opcao == 0){
            this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void terminaQuartosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminaQuartosActionPerformed
        
        int opcao = JOptionPane.showConfirmDialog(this,"Deseja Terminar?", 
                                                  "Confirmaçao", 
                                                  JOptionPane.YES_NO_OPTION);
        
        if(opcao == 0){
            
            boolean registaApt = facade.registaApartamento(nomeSenhorio, usernameSenhorio,
                                                           passSenhorio, usernameAdmin,
                                                           passAdmin);

            if (registaApt) {
                UserSenhorio dialog = new UserSenhorio(new javax.swing.JFrame(),
                                                       true, facade);

                System.gc();
                java.awt.Window win[] = java.awt.Window.getWindows();  
                win[1].dispose(); 
                win[1]=null;
                this.dispose();
                dialog.setVisible(true);
                
            } else {
                String msg = "Não foi possível registar o apartamento.";
                JOptionPane.showMessageDialog(this, msg);
                this.dispose();
            }
        }
    }//GEN-LAST:event_terminaQuartosActionPerformed

    private void adicionaQuartoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionaQuartoActionPerformed
        String rendaStr = rendaField.getText();
        
        if (!facade.addToPrecos(rendaStr)) {
            String msg = "Introduza um valor de renda válido.";
            JOptionPane.showMessageDialog(this, msg);
        }
    }//GEN-LAST:event_adicionaQuartoActionPerformed

    private void numQuartosComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_numQuartosComponentShown

    }//GEN-LAST:event_numQuartosComponentShown

    private void removeQuartoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeQuartoActionPerformed

        if(!facade.removeQuarto()) {
            String msg = "Não há quartos para remover.";
            JOptionPane.showMessageDialog(this, msg);
        }
    }//GEN-LAST:event_removeQuartoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionaQuarto;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel numQuartos;
    private javax.swing.JButton removeQuarto;
    private javax.swing.JTextField rendaField;
    private javax.swing.JButton terminaQuartos;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        this.numQuartos.setText(Integer.toString(facade.getNumQuartos()));
    }
}
