package bomberman;

public class Welcome extends javax.swing.JPanel {

    public Welcome() {
        initComponents();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jEditorPane1 = new javax.swing.JEditorPane();

        setMaximumSize(new java.awt.Dimension(700, 500));
        setMinimumSize(new java.awt.Dimension(700, 500));

        jLabel1.setText("");
        jPanel1.add(jLabel1);

        jEditorPane1.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        jEditorPane1.setContentType("text/html"); // NOI18N
        jEditorPane1.setEditable(false);
        jEditorPane1.setText("<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      \r<center>\n<b>j B O M B E R</b>\n<br>\n\n<br><br><br><br>\nPremi il tasto spazio per iniziare\n</center>\n    </p>\r\n  </body>\r\n</html>\r\n");
        jEditorPane1.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .addComponent(jEditorPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jEditorPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
