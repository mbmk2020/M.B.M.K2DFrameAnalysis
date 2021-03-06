/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.b.m.k2dframeanalysis;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import static m.b.m.k2dframeanalysis.DeflectionsFram.Deflections;
import static m.b.m.k2dframeanalysis.DeflectionsFram.jTable_Deflections;

/**
 *
 * @author ma739918
 */
public class ReactionsFram extends javax.swing.JFrame {
    
    public MainScreen mainscreen;
    public static double[][] D;
    public static int NumberOfMembers;
    public ReactionsFram(double[][] D,int NumberOfMembers){
        initComponents();
        this.D = D;
        this.NumberOfMembers = NumberOfMembers;
    }

    /**
     * Creates new form ReactionsFram
     */
    public ReactionsFram() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Reactions = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTable_Reactions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Member", "X1", "Y1", "Z1", "X2", "Y2", "Z2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable_Reactions);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        System.out.println("You Are Here");
        /*try{
           double[][] q = mainscreen.get_q(this.D, 0);
        }catch(Exception e){
            
        }
                
        */
        
        DefaultTableModel model = (DefaultTableModel) jTable_Reactions.getModel();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jTable_Reactions.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        jTable_Reactions.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        jTable_Reactions.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        jTable_Reactions.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        jTable_Reactions.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        jTable_Reactions.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        jTable_Reactions.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
        
        try {
            for(int i = 0 ; i<=this.NumberOfMembers-1 ; i++){
                double[][] q = mainscreen.get_q(this.D, i);
                
                
                //double x1 = q[0][0];
                model.addRow(new Object[]{String.valueOf(i),Format(q[0][0],"0.0000"),Format(q[1][0],"0.0000"),Format(q[2][0],"0.0000"),Format(q[3][0],"0.0000"),Format(q[4][0],"0.0000"),Format(q[5][0],"0.0000")});
            }
              
        } catch (IOException ex) {
            Logger.getLogger(ReactionsFram.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
    }
     public  double Format(double value, String decimals){
        DecimalFormat df2 = new DecimalFormat( decimals );
         double newValue = new Double(df2.format(value)).doubleValue();
         return newValue;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Reactions;
    // End of variables declaration//GEN-END:variables
}
