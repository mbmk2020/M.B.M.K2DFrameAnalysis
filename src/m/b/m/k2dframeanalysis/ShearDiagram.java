/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.b.m.k2dframeanalysis;

import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static m.b.m.k2dframeanalysis.MainScreen.AXIAL_LOADS;
import static m.b.m.k2dframeanalysis.MainScreen.JOINT_LOADS;
import static m.b.m.k2dframeanalysis.MainScreen.JOINT_SUPPORTS;
import static m.b.m.k2dframeanalysis.MainScreen.JointNumbers;
import static m.b.m.k2dframeanalysis.MainScreen.MOMENT_LOADS;
import static m.b.m.k2dframeanalysis.MainScreen.MemberNumbers;
import static m.b.m.k2dframeanalysis.MainScreen.MultiplayMatrix;
import static m.b.m.k2dframeanalysis.MainScreen.MyjointsData;
import static m.b.m.k2dframeanalysis.MainScreen.MymembersData;
import static m.b.m.k2dframeanalysis.MainScreen.UNIFORM_LOADS;
import static m.b.m.k2dframeanalysis.MainScreen.j1;
import static m.b.m.k2dframeanalysis.MainScreen.j2;
import static m.b.m.k2dframeanalysis.MainScreen.jPanel1;
import static m.b.m.k2dframeanalysis.MainScreen.myDrawing;
import static m.b.m.k2dframeanalysis.MainScreen.x;
import static m.b.m.k2dframeanalysis.MainScreen.y;

/**
 *
 * @author ma739918
 */
public class ShearDiagram extends javax.swing.JFrame {

    /**
     * Creates new form ShearDiagram
     */
    public MainScreen mainscreen;
    public double[][] D;
    public int NumberOfMembers;
    public static Drawing myDrawing = new Drawing();
   
    public ArrayList<Double> SHEAR = new ArrayList<>();
    public ArrayList<Double> MOMENT = new ArrayList<>();
    public double MAXSHEAR_Value;
    public double MAXMOMENT_Value;
    
    public static ArrayList<FramMember> FRAM_MEMBER = new ArrayList<>();
    public static ArrayList<AxialLoad> AXIAL_LOADS = new ArrayList<>();
    public static ArrayList<UniformLoad> UNIFORM_LOADS = new ArrayList<>();
    public static ArrayList<MomentLoad> MOMENT_LOADS = new ArrayList<>();
    public static double[] x = new double[100000]; //x coordinates i.e. x[0] --> x-coordinates for joint (1)
    public static double[] y = new double[100000]; //y coordinates i.e. y[1] --> x-coordinates for joint (2)
  
    public ShearDiagram(double[][] D, int NumberOfMembers){
        initComponents();
        this.D = D;
        this.NumberOfMembers = NumberOfMembers;
    }
    
    public ShearDiagram() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_ShearDiagram = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox_MemberIndex = new javax.swing.JComboBox<>();
        jButton_DrawShearDiagram = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField_MaxShear = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_MaxShearAt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_MaxMoment = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField_MaxMomentAt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField_MinShear = new javax.swing.JTextField();
        jTextField_MinMoment = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField_MinShearAt = new javax.swing.JTextField();
        jTextField_MinMomentAt = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jTextField_ValueAt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField_ShearValue = new javax.swing.JTextField();
        jTextField_MomentValue = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        MemberHeightText = new javax.swing.JTextField();
        MemberWidthText = new javax.swing.JTextField();
        CoverText = new javax.swing.JTextField();
        MomentText = new javax.swing.JTextField();
        FyText = new javax.swing.JTextField();
        FcText = new javax.swing.JTextField();
        BarDiameterText = new javax.swing.JTextField();
        StirrupsText = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        DesignButton = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        ConcreteCostText = new javax.swing.JTextField();
        SteelCostPrice = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        DensityFinalText = new javax.swing.JTextField();
        MomentCapacityText = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        DensityText = new javax.swing.JTextField();
        BetaText = new javax.swing.JTextField();
        DText = new javax.swing.JTextField();
        NumberOfBarsText = new javax.swing.JTextField();
        AText = new javax.swing.JTextField();
        PhiText = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        MinWidthText = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        SmaxText = new javax.swing.JTextField();
        ResultsLabel = new javax.swing.JLabel();
        DesnityFinalCheckLabel = new javax.swing.JLabel();
        MomentCapacityCheckLabel = new javax.swing.JLabel();
        WidthCheckCapacityLabel = new javax.swing.JLabel();
        SmaxCheckLabel = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        costLabel = new javax.swing.JLabel();
        FinalSection = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel_ShearDiagram.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel_ShearDiagramLayout = new javax.swing.GroupLayout(jPanel_ShearDiagram);
        jPanel_ShearDiagram.setLayout(jPanel_ShearDiagramLayout);
        jPanel_ShearDiagramLayout.setHorizontalGroup(
            jPanel_ShearDiagramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 614, Short.MAX_VALUE)
        );
        jPanel_ShearDiagramLayout.setVerticalGroup(
            jPanel_ShearDiagramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Member");

        jComboBox_MemberIndex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton_DrawShearDiagram.setText("Draw Shear / Moment");
        jButton_DrawShearDiagram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DrawShearDiagramActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Max Shear");

        jTextField_MaxShear.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 0));
        jLabel5.setText("Results");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(java.awt.Color.red);
        jLabel6.setText("@");

        jTextField_MaxShearAt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("Max Moment");

        jTextField_MaxMoment.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(java.awt.Color.red);
        jLabel8.setText("@");

        jTextField_MaxMomentAt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("Min Shear");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("Min Moment");

        jTextField_MinShear.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField_MinMoment.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 255));
        jLabel10.setText("@");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 255));
        jLabel11.setText("@");

        jTextField_MinShearAt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField_MinMomentAt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 0));
        jLabel4.setText("Shear/Moment @");

        jTextField_ValueAt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ValueAt.setText("0.0");

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setText("Get");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Shear");

        jLabel14.setText("Moment");

        jTextField_ShearValue.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField_MomentValue.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 0));
        jLabel13.setText("RC Design Member");

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 102));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Cover(mm)");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 102, 102));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Mu(Kn.m)");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 102, 102));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Fy(Mpa)");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 102, 102));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("F'c(Mpa)");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 102, 102));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Bar Diameter(mm)");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 102, 102));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Stirrups (mm)");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 102, 102));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Member Height(mm)");

        MemberHeightText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MemberHeightText.setText("600");
        MemberHeightText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MemberHeightTextActionPerformed(evt);
            }
        });

        MemberWidthText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MemberWidthText.setText("300");
        MemberWidthText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MemberWidthTextActionPerformed(evt);
            }
        });

        CoverText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        CoverText.setText("40");
        CoverText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CoverTextActionPerformed(evt);
            }
        });

        MomentText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MomentText.setText("0");

        FyText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        FyText.setText("420");

        FcText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        FcText.setText("28");

        BarDiameterText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        BarDiameterText.setText("16");

        StirrupsText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        StirrupsText.setText("8");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 102, 102));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Member Width(mm)");

        DesignButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DesignButton.setText("Design / Check");
        DesignButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DesignButtonActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Concrete Cost $/m3");

        ConcreteCostText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ConcreteCostText.setText("100");
        ConcreteCostText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConcreteCostTextActionPerformed(evt);
            }
        });

        SteelCostPrice.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SteelCostPrice.setText("657");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Steel Cost $/Ton.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DesignButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(8, 8, 8))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(1, 1, 1)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SteelCostPrice)
                            .addComponent(ConcreteCostText)
                            .addComponent(StirrupsText)
                            .addComponent(BarDiameterText)
                            .addComponent(FcText)
                            .addComponent(FyText, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                            .addComponent(MomentText)
                            .addComponent(MemberWidthText)
                            .addComponent(MemberHeightText)
                            .addComponent(CoverText))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(MemberHeightText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MemberWidthText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CoverText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MomentText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FyText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FcText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BarDiameterText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(StirrupsText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(ConcreteCostText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(SteelCostPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DesignButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(187, 253, 204));

        DensityFinalText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DensityFinalText.setText("0");

        MomentCapacityText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MomentCapacityText.setText("0");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(153, 51, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("β");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(153, 51, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("d(mm)");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(153, 51, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("#Of Bars");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(153, 51, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("a");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(153, 51, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Ф");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(153, 51, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("ρ");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(153, 51, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Mu(Mpa)");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(153, 51, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("ρ");

        DensityText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DensityText.setText("0");
        DensityText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DensityTextActionPerformed(evt);
            }
        });

        BetaText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        BetaText.setText("0");

        DText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DText.setText("0");
        DText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DTextActionPerformed(evt);
            }
        });

        NumberOfBarsText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NumberOfBarsText.setText("0");

        AText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AText.setText("0");

        PhiText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PhiText.setText("0");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 102, 102));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Min Width(mm)");

        MinWidthText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MinWidthText.setText("0");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 102, 102));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Smax");

        SmaxText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SmaxText.setText("0");

        ResultsLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ResultsLabel.setForeground(new java.awt.Color(255, 0, 0));
        ResultsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ResultsLabel.setText("Results");

        DesnityFinalCheckLabel.setText("Ok");

        MomentCapacityCheckLabel.setText("Ok");

        WidthCheckCapacityLabel.setText("Ok");

        SmaxCheckLabel.setText("Ok");

        costLabel.setBackground(new java.awt.Color(0, 0, 204));
        costLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        costLabel.setForeground(new java.awt.Color(0, 51, 255));
        costLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        costLabel.setText("Cost $");

        FinalSection.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        FinalSection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FinalSection.setText("jLabel35");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(costLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ResultsLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SmaxText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(MinWidthText, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(MomentCapacityText)
                            .addComponent(DensityFinalText)
                            .addComponent(PhiText)
                            .addComponent(AText)
                            .addComponent(NumberOfBarsText)
                            .addComponent(BetaText)
                            .addComponent(DensityText)
                            .addComponent(DText, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(SmaxCheckLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(DesnityFinalCheckLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MomentCapacityCheckLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(WidthCheckCapacityLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(FinalSection, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(DensityText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BetaText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NumberOfBarsText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PhiText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DensityFinalText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(DesnityFinalCheckLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(MomentCapacityText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MomentCapacityCheckLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MinWidthText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(WidthCheckCapacityLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(SmaxText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SmaxCheckLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FinalSection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ResultsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(costLabel))
        );

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_ShearDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(151, 151, 151)
                                .addComponent(jLabel13)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator1)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(3, 3, 3)
                                                    .addComponent(jLabel9)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jTextField_MinMoment, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(4, 4, 4)
                                                    .addComponent(jLabel11)
                                                    .addGap(4, 4, 4)
                                                    .addComponent(jTextField_MinMomentAt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(9, 9, 9)
                                                    .addComponent(jLabel3)
                                                    .addGap(26, 26, 26)
                                                    .addComponent(jTextField_MinShear, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(4, 4, 4)
                                                    .addComponent(jLabel10)
                                                    .addGap(4, 4, 4)
                                                    .addComponent(jTextField_MinShearAt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(101, 101, 101)
                                                    .addComponent(jLabel5))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel7)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jTextField_MaxMoment, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(4, 4, 4)
                                                    .addComponent(jLabel8)
                                                    .addGap(4, 4, 4)
                                                    .addComponent(jTextField_MaxMomentAt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(6, 6, 6)
                                                    .addComponent(jLabel2)
                                                    .addGap(26, 26, 26)
                                                    .addComponent(jTextField_MaxShear, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(4, 4, 4)
                                                    .addComponent(jLabel6)
                                                    .addGap(4, 4, 4)
                                                    .addComponent(jTextField_MaxShearAt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(12, 12, 12)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addComponent(jLabel4)
                                                    .addGap(20, 20, 20))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addGap(2, 2, 2)
                                                            .addComponent(jLabel14)))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jTextField_ShearValue)
                                                        .addComponent(jTextField_MomentValue, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addComponent(jTextField_ValueAt))
                                            .addGap(0, 11, Short.MAX_VALUE))))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_MemberIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_DrawShearDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_ShearDiagram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(11, 11, 11)
                        .addComponent(jComboBox_MemberIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jButton_DrawShearDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField_MaxShear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField_MaxShearAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(2, 2, 2)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel2)
                                                            .addComponent(jLabel6))))
                                                .addGap(6, 6, 6))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField_ValueAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTextField_ShearValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(jTextField_MomentValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(80, 80, 80)
                                                .addComponent(jTextField_MaxMoment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(80, 80, 80)
                                                .addComponent(jTextField_MaxMomentAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(79, 79, 79)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel8))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField_MinShear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField_MinShearAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel3)
                                                    .addComponent(jLabel10))))))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_MinMoment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_MinMomentAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel11)))))
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        jTextField_ShearValue.setEnabled(false);
        jTextField_MomentValue.setEnabled(false);
        
        jTextField_MaxShear.setEnabled(false);
        jTextField_MaxShearAt.setEnabled(false);
        jTextField_MaxMoment.setEnabled(false);
        jTextField_MaxMomentAt.setEnabled(false);
        
         jTextField_MinShear.setEnabled(false);
        jTextField_MinShearAt.setEnabled(false);
        jTextField_MinMoment.setEnabled(false);
        jTextField_MinMomentAt.setEnabled(false);
        
        System.out.println("You Are Here");
        //Retrieve the data from the main screen.........
 
          FRAM_MEMBER = mainscreen.get_FRAM_MEMBER();
          AXIAL_LOADS = mainscreen.get_AXIAL_LOADS();
          UNIFORM_LOADS = mainscreen.get_UNIFORM_LOADS();
          MOMENT_LOADS = mainscreen.get_MOMENT_LOADS();
          x = mainscreen.get_X();
          y = mainscreen.get_Y();
        
          jComboBox_MemberIndex.removeAllItems();
          for(int i=0 ; i<=FRAM_MEMBER.size()-1 ; i++){
              jComboBox_MemberIndex.addItem(String.valueOf(i));
          }  
        
        
        
        
    }//GEN-LAST:event_formWindowOpened

    private void jButton_DrawShearDiagramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DrawShearDiagramActionPerformed
        /* TODO add your handling code here:
           Solve Shear and Moment for Distributed Load
           Force N and Length Meter
        */
        DrawShearMoment(jComboBox_MemberIndex.getSelectedIndex());
    }//GEN-LAST:event_jButton_DrawShearDiagramActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        myDrawing.setPanel(jPanel_ShearDiagram);
        myDrawing.CleanThePanel(jPanel_ShearDiagram);
        DrawShearMoment(jComboBox_MemberIndex.getSelectedIndex());
        
        double x_start,y_start,x_end,y_end; 
        
        double upper_x_start = jPanel_ShearDiagram.getWidth()/4;
        double upper_y_start = jPanel_ShearDiagram.getHeight()/5;
        double upper_x_end = 3*jPanel_ShearDiagram.getWidth()/4;
        double upper_y_end = jPanel_ShearDiagram.getHeight()/5;
        
        double lower_x_start = jPanel_ShearDiagram.getWidth()/4;
        double lower_y_start = jPanel_ShearDiagram.getHeight()/1.2;
        double lower_x_end = 3*jPanel_ShearDiagram.getWidth()/4;
        double lower_y_end = jPanel_ShearDiagram.getHeight()/1.2;
        
        double line_length = Math.sqrt(Math.pow((upper_x_end-upper_x_start), 2)+Math.pow((upper_y_end-upper_y_start), 2)); 
        
        int l = (int)(Format(FRAM_MEMBER.get(jComboBox_MemberIndex.getSelectedIndex()).getLength(),"0.000")*100);
        try{
            int point = (int)(Double.valueOf(jTextField_ValueAt.getText())*100);
            jTextField_ShearValue.setText(String.valueOf(Format(SHEAR.get(point)/1000,"0.00")));
            jTextField_MomentValue.setText(String.valueOf(Format(MOMENT.get(point)/100000,"0.00")));
            //Line
            double line_x1,line_y1,line_x2,line_y2;
            line_x1 = ((double)point/(double)l)*line_length + upper_x_start;
            line_y1 = upper_y_start - 50;
            line_x2 = ((double)point/(double)l)*line_length + upper_x_start;
            line_y2 = lower_y_start + 50;
            myDrawing.DrawFreeLine(line_x1, line_y1, line_x2, line_y2, Color.orange, 2);
            
            //Shear Circle
            //The X_coordiantes will be the same so I will use line_x1 as x_coordinates
            x_start = jPanel_ShearDiagram.getWidth()/4;
            y_start = jPanel_ShearDiagram.getHeight()/2;
            x_end = 3*jPanel_ShearDiagram.getWidth()/4;
            y_end = jPanel_ShearDiagram.getHeight()/2;
            double shear_circle_y = y_start - ((SHEAR.get(point)/MAXSHEAR_Value)*(40.0));
            myDrawing.DrawFreePoints(line_x1, shear_circle_y, Color.black, 2);
            
            //Moment Circle
            //The X_coordiantes will be the same so I will use line_x1 as x_coordinates
             x_start = jPanel_ShearDiagram.getWidth()/4;
             y_start = jPanel_ShearDiagram.getHeight()/1.2;
             x_end = 3*jPanel_ShearDiagram.getWidth()/4;
             y_end = jPanel_ShearDiagram.getHeight()/1.2;
            double moment_circle_y = y_start + ((MOMENT.get(point)/MAXMOMENT_Value)*(40.0));
            myDrawing.DrawFreePoints(line_x1, moment_circle_y, Color.black, 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Check about your Inputs..!");  
        }        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void MemberHeightTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MemberHeightTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MemberHeightTextActionPerformed

    private void CoverTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CoverTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CoverTextActionPerformed

    private void DensityTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DensityTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DensityTextActionPerformed

    private void DTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTextActionPerformed

    private void DesignButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DesignButtonActionPerformed
        // TODO add your handling code here:
        int NumberOfBars = 0;
        
        double A,B,C,P1,P2,Pmin,Beta,Pmax,P,d,a,Phi,Pfinal,Mu_Capacity,WidthMin,Smax,TotalCost;
        
        boolean densityCheck, MomentCheck, WidthCheck, SmaxCheck;
        try{
        double Height = Double.valueOf(MemberHeightText.getText());
        double Width = Double.valueOf(MemberWidthText.getText());
        double Cover = Double.valueOf(CoverText.getText());
        double Mu = Math.abs(Double.valueOf(MomentText.getText()));
        double Fy = Double.valueOf(FyText.getText());
        double Fc = Double.valueOf(FcText.getText());
        double BarDiameter = Double.valueOf(BarDiameterText.getText());
        double Stirrups =  Double.valueOf(StirrupsText.getText());
        double ConcreteCost = Double.valueOf(ConcreteCostText.getText());
        double SteelCost = Double.valueOf(SteelCostPrice.getText());
        
        d = Height-Cover-Stirrups-(BarDiameter/2);
        
        A = (0.59*0.9*Math.pow(Fy, 2)*Width*Math.pow(d, 2))/Fc;
        B = -0.9*Fy*Width*Math.pow(d, 2);
        C = Mu*Math.pow(10, 6);
        
        P1 = (-B+Math.sqrt(Math.pow(B, 2)-4*A*C))/(2*A);
        P2 = (-B-Math.sqrt(Math.pow(B, 2)-4*A*C))/(2*A);
        
        
        Pmin = Math.max((1.4/Fy), ((Math.sqrt(Fc))/(4*Fy)));
        
        Beta = 0.85 - 0.05*((Fc-28)/7);
        if(Beta >= 0.85){Beta = 0.85;}
        if(Beta <= 0.65){Beta = 0.65;}
        
        Pmax =0.85*Beta*(Fc/Fy)*(0.003/(0.003+0.004));
        
        P = Math.min(P1, P2);
        if(P >= Pmax){P = Pmax;}
        if(P <= Pmin){P = Pmin;}
        //-------------------------------------------------------------
        
        NumberOfBars = 1 + (int)((P*Width*d)/(0.25*Math.PI*Math.pow(BarDiameter, 2)));
        a =((NumberOfBars*(0.25*Math.PI*Math.pow(BarDiameter, 2)))*(Fy))/(0.85*Fc*Width);
        Phi =0.65+0.25*(1/((a/Beta)/d) - (5/3));
        if(Phi <= 0.65){Phi = 0.65;}
        if(Phi >= 0.90){Phi = 0.90;}
        Pfinal =(NumberOfBars*(0.25*Math.PI*Math.pow(BarDiameter, 2)))/(Width*d);
        Mu_Capacity =(Phi*(NumberOfBars*(0.25*Math.PI*Math.pow(BarDiameter, 2)))*(Fy)*(d-a/2))/Math.pow(10, 6);
        WidthMin =NumberOfBars*BarDiameter+(NumberOfBars-1)*25.0+2.0*(Cover+Stirrups);
        Smax =380.0*(280.0/(2.0/3.0*Fy)) - 2*Cover;
        
        
        if(Pfinal <= Pmax){densityCheck = true;}else{densityCheck = false;}
        if(Mu_Capacity >= Mu){MomentCheck = true;}else{MomentCheck = false;}
        if(WidthMin <= Width){WidthCheck = true;}else{WidthCheck = false;}
        if(((Width-2*Cover-2*Stirrups-BarDiameter)/(NumberOfBars-1)) <= Smax){SmaxCheck = true;}else{SmaxCheck = false;}
        
        
        DensityText.setText(String.valueOf(Format(P,"0.0000")));
        BetaText.setText(String.valueOf(Format(Beta,"0.0000")));
        DText.setText(String.valueOf(Format(d,"0.0000")));
        NumberOfBarsText.setText(String.valueOf(NumberOfBars));
        AText.setText(String.valueOf(Format(a,"0.0000")));
        PhiText.setText(String.valueOf(Format(Phi,"0.0000")));
        DensityFinalText.setText(String.valueOf(Format(Pfinal,"0.0000")));
        MomentCapacityText.setText(String.valueOf(Format(Mu_Capacity,"0.0000")));
        MinWidthText.setText(String.valueOf(Format(WidthMin,"0.0000")));
        SmaxText.setText(String.valueOf(Format(Smax,"0.0000")));
        
        if(densityCheck){DesnityFinalCheckLabel.setText("OK");}else{DesnityFinalCheckLabel.setText("Not OK");}
        if(MomentCheck){MomentCapacityCheckLabel.setText("OK");}else{MomentCapacityCheckLabel.setText("Not OK");}
        if(WidthCheck){WidthCheckCapacityLabel.setText("OK");}else{WidthCheckCapacityLabel.setText("Not OK");}
        if(SmaxCheck){SmaxCheckLabel.setText("OK");}else{SmaxCheckLabel.setText("Not OK");}
        
        FinalSection.setText(String.valueOf(Width)+"mm"+" x "+String.valueOf(Height)+"mm"+" , "+String.valueOf(NumberOfBars)+"Ф"+String.valueOf(BarDiameter)+" Is:");
        if(densityCheck && MomentCheck && WidthCheck && SmaxCheck){ResultsLabel.setText("Safe");}else{ResultsLabel.setText("Not Safe");}
      
        
        double SteelDensity = 8.0; //Tonne/m3
        double Length = FRAM_MEMBER.get(jComboBox_MemberIndex.getSelectedIndex()).getLength();
        TotalCost = (((Height/1000.0)*(Width/1000.0)*(Length))*ConcreteCost) + ((Length*(NumberOfBars*(0.25*Math.PI*Math.pow((BarDiameter/1000), 2))))*SteelCost*SteelDensity);
        
        if(densityCheck && MomentCheck && WidthCheck && SmaxCheck){costLabel.setText("Cost: $ "+String.valueOf(Format(TotalCost,"0.00")));}else{costLabel.setText("$");}
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Check your Inputs...!!");  
        }
        
        
        
    }//GEN-LAST:event_DesignButtonActionPerformed

    private void MemberWidthTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MemberWidthTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MemberWidthTextActionPerformed

    private void ConcreteCostTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConcreteCostTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConcreteCostTextActionPerformed

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
    public static double[][] TransformationMatrix(double x1,double y1,double x2,double y2){
        double L,cosTheta,sinTheta;
        
        double[][] TraM = new double[6][6];
        
        L = Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2)); 
        cosTheta = (x2-x1)/L;
        sinTheta = (y2-y1)/L;
        
        TraM[0][0] =  cosTheta ; TraM[0][1]=   sinTheta ; TraM[0][2] = 0 ; TraM[0][3] =    0     ; TraM[0][4] =     0      ; TraM[0][5] = 0 ;
        TraM[1][0] = -sinTheta ; TraM[1][1]=   cosTheta ; TraM[1][2] = 0 ; TraM[1][3] =    0     ; TraM[1][4] =     0      ; TraM[1][5] = 0 ; 
        TraM[2][0] =     0     ; TraM[2][1]=       0    ; TraM[2][2] = 1 ; TraM[2][3] =    0     ; TraM[2][4] =     0      ; TraM[2][5] = 0 ; 
        TraM[3][0] =     0     ; TraM[3][1]=       0    ; TraM[3][2] = 0 ; TraM[3][3] = cosTheta ; TraM[3][4] = sinTheta  ; TraM[3][5] = 0 ;
        TraM[4][0] =     0     ; TraM[4][1]=       0    ; TraM[4][2] = 0 ; TraM[4][3] = -sinTheta ; TraM[4][4] = cosTheta   ; TraM[4][5] = 0 ; 
        TraM[5][0] =     0     ; TraM[5][1]=       0    ; TraM[5][2] = 0 ; TraM[5][3] =   0      ; TraM[5][4] =     0      ; TraM[5][5] = 1 ; 
        
        return TraM;
    }
    public  double[][] MultiplayMatrix(double[][] M1, double[][] M2){
        int ar1_raw,ar1_col,ar2_raw,ar2_col,x,count,start;
        double[] sum = new double[100000];
        ar1_raw = M1.length; ar1_col = M1[0].length;
        ar2_raw = M2.length; ar2_col = M2[0].length;
        
        
        
        double ResultAStwoMultibleMatrix[][] = new double[ar1_raw][ar2_col];
        if (ar1_col == ar2_raw){
            count = 0;
            //Find the Answers as vector....
            for (int i = 0;i <= ar1_raw - 1;i++){
              for (int y = 0;y <= ar2_col - 1;y++){
                for (int j = 0;j <= ar1_col - 1;j++){
                  x = j;
                  sum[count] +=M1[i][j] * M2[x][y];
                }
                count +=1;
              } 
            }
            //Find the Answers as vector....
            start = 0;
            for (int iraw =0; iraw <= ar1_raw -1 ; iraw++){
                for(int icol =0; icol<= ar2_col -1 ; icol++){
                    ResultAStwoMultibleMatrix[iraw][icol] = sum[start];
                    start +=1;
                }
            }
        }
        return ResultAStwoMultibleMatrix;
    }
    public  double[][] Matrix1PlusMatrix2(double[][] M1, double[][] M2){
        int ar1_raw = M1.length; int ar1_col = M1[0].length;
        int ar2_raw = M2.length; int ar2_col = M2[0].length;
        
        double[][] MatrixResults = new double[ar1_raw][ar1_col];
        if(ar1_col ==ar2_col && ar1_raw == ar2_raw){
            for(int iraw=0 ; iraw<=ar1_raw-1 ; iraw++){
                for(int icol=0 ; icol<=ar1_col-1; icol++){
                    MatrixResults[iraw][icol] = M1[iraw][icol] + M2[iraw][icol];
                }
            }
        }
        return MatrixResults; 
    }
    public  double[][] Matrix1MinusMatrix2(double[][] M1, double[][] M2){
        //M1 - M2
        int ar1_raw = M1.length; int ar1_col = M1[0].length;
        int ar2_raw = M2.length; int ar2_col = M2[0].length;
        
        double[][] MatrixResults = new double[ar1_raw][ar1_col];
        if(ar1_col ==ar2_col && ar1_raw == ar2_raw){
            for(int iraw=0 ; iraw<=ar1_raw-1 ; iraw++){
                for(int icol=0 ; icol<=ar1_col-1; icol++){
                    MatrixResults[iraw][icol] = M1[iraw][icol] - M2[iraw][icol];
                }
            }
        }
        return MatrixResults; 
    }
    public  double[][] MatrixInverse(double[][] M){
        int ar1_raw; int ar1_col;
        double[][] MatrixResultsAsInverse = new double[M.length][M[0].length];
        ar1_raw = M.length; ar1_col = M[0].length;
        
        if(ar1_col != ar1_raw){
           JOptionPane.showMessageDialog(null, "Matrix Error (Inverse Problem): Matrix is not a square Matrix");
           return MatrixResultsAsInverse;
        }
        
        double[][] a = new double[ar1_raw][ar1_col];
        // This code is copy from my Visual Basic Code, therefore I will do this next forloop for a where we don't need that while weare using Java and Java has pointer
        for (int ii=0 ; ii<= ar1_raw-1 ; ii++){
            for (int jj=0 ; jj<=ar1_col-1 ; jj++){
                a[ii][jj] = M[ii][jj];
            }
        }
        ///////////////////////////////////////Pointer Ends//////////////////////////////////////////////
        double[][] b = BuildUnitMatrix(a.length);
        ///////////////Now The following code to start calculate the inverse Matrix using Gausin Method....
        int n;
        double f;
        n = a.length;
        for (int i=1 ; i<=n-1 ; i++){
           for (int j=0 ; j<= i-1 ; j++){
               if (Format(a[j][j], "0.00000000000000") == 0){a[j][j] = 0.000000000000001;}
               f = a[i][j] / a[j][j];
               for(int m=0 ; m<=n-1 ; m++){
                   a[i][m] = a[i][m] - f*a[j][m];
                   b[i][m] = b[i][m] - f*b[j][m];
               }
           }
        }
        
        for (int i=n-2 ; i>=0 ; i--){
            for( int j=(n-1) ; j>=(i+1) ; j--){
                if(Format(a[j][j],"0.00000000000000") == 0){a[j][j] = 0.000000000000001;}
                f = a[i][j] / a[j][j];
                for(int m=(n-1) ; m>=0 ; m--){
                    a[i][m] = a[i][m] - f*a[j][m];
                    b[i][m] = b[i][m] - f*b[j][m];
                }
            }
        }
        //Here Print the Matrix
        for (int i=0 ; i<=(n-1) ; i++){
            for (int j=0 ; j<=(n-1) ; j++){
                if(Format(a[i][i],"0.00000000000000") == 0){a[i][i] = 0.000000000000001;}
                b[i][j] = Format(b[i][j],"0.000000000000000") / Format(a[i][i],"0.000000000000000") ;
            }
        }
        ////////////////////////////////////////
        for (int i=0 ; i<=(n-1) ; i++){
            for (int j=0 ; j<=(n-1) ; j++){
               MatrixResultsAsInverse[i][j] = b[i][j];
            }
        }
                
        return MatrixResultsAsInverse;
    }    
    public  double[][] BuildUnitMatrix(int SizeOfMatrix){
        double[][] UnitMatrix = new double[SizeOfMatrix][SizeOfMatrix];
        for( int iraw=0 ; iraw<=SizeOfMatrix-1 ; iraw++){
            for( int icol=0 ; icol<= SizeOfMatrix-1 ; icol++){
                if (iraw == icol){
                    UnitMatrix[iraw][icol] = 1;
                }else{
                    UnitMatrix[iraw][icol] = 0;
                }
            }
        }
        return UnitMatrix;
    }
    public  void PrintMatrix(double[][] M,String decimals){
        for (int i = 0; i <= M.length - 1;i++){
            for(int j=0; j <= M[i].length - 1;j++){
                System.out.print(" "+String.valueOf(Format(M[i][j],decimals)));
            }
            System.out.println("");
        }
       System.out.println("");
    }
    public  double[][] reduceMatrix(double[][] M, ArrayList<Integer> Index, String direction){
        
         //Direction its mean the reduction in matrix will be using coloumn and raw or only raw
         // both ==> mean delete raw and column that meet the numbers
         // raw ==> mean delete only the raw that meet the numbers
        int raw = 0;
        int col = 0;
        
        ArrayList<Integer> rawNeeded = new ArrayList<>();
        ArrayList<Integer> colNeeded = new ArrayList<>();

        boolean check = true;
        
        //Raw Needed
        for (int i = 0 ; i<= M.length-1 ; i++){
            check = true;
           for (int k = 0 ; k<=Index.size()-1 ; k++){
              if(i == Index.get(k)){
                  check = false;
              }
           }
           if(direction == "col"){check = true;}
           if(check){
              rawNeeded.add(i);
          
           }
        }
        
         //Col Needed
        for (int j = 0 ; j<= M[0].length-1 ; j++){
            check = true;
           for (int k = 0 ; k<=Index.size()-1 ; k++){
              if(j == Index.get(k)){
                  check = false;
              }
           }
           if(direction == "raw"){check = true;}
           if(check){
              colNeeded.add(j);           
           }
        }
      
          try{
               if(direction == "both"){
                double [][] newMatrix_both = new double[M.length - Index.size()][M[0].length - Index.size()];
                for (int i = 0 ; i<= newMatrix_both.length-1; i++){
                for (int j = 0 ; j<= newMatrix_both[0].length-1 ; j++){
                 newMatrix_both[i][j] = M[rawNeeded.get(i)][colNeeded.get(j)];
             }
          }
            return newMatrix_both;
            
            
            
        }else if(direction == "raw"){
              double [][] newMatrix_raw = new double[M.length - Index.size()][M[0].length];
            for (int i = 0 ; i<= newMatrix_raw.length-1; i++){
             for (int j = 0 ; j<= newMatrix_raw[0].length-1 ; j++){
                 newMatrix_raw[i][j] = M[rawNeeded.get(i)][colNeeded.get(j)];
             }
          }
            return newMatrix_raw;
        }
        else if(direction == "col"){
               double [][] newMatrix_col = new double[M.length][M[0].length - Index.size()];
               for (int i = 0 ; i<= newMatrix_col.length-1; i++){
                 for (int j = 0 ; j<= newMatrix_col[0].length-1 ; j++){
                  newMatrix_col[i][j] = M[rawNeeded.get(i)][colNeeded.get(j)];
                 }
                }
        return newMatrix_col;
        }
        else{
            double [][] newMatrix = new double[M.length - Index.size()][M[0].length - Index.size()];
            return newMatrix;
        }
        }catch(NegativeArraySizeException e){
            double [][] newMatrix = new double[M.length ][M[0].length];
            return newMatrix;
        }    
    }
    
    public  void DrawShearMoment(int MemberIndex){
        SHEAR.clear();
        MOMENT.clear();
        
        double Min = 0;
        
        double[] Moment = new double[50000];
        double[] Shear = new double[50000];
        double x1,x2,y1,y2;
        double Axial_LeftReaction = 0;
        double Axial_RightReaction = 0;
        double Moment_LeftReaction = 0;
        double Moment_RightReaction = 0;
        int memberIndex = MemberIndex;
        
        int NumberOfDistributedLoads = UNIFORM_LOADS.get(memberIndex).getSize();
        int NumberOfAxialLoads = AXIAL_LOADS.get(memberIndex).getSize();
        int NumberOfMomentLoads = MOMENT_LOADS.get(memberIndex).getSize();
        
        int l = (int)(Format(FRAM_MEMBER.get(memberIndex).getLength(),"0.000")*100);
        
        x1 = x[FRAM_MEMBER.get(memberIndex).getFirstJointNumber()];
        y1 = y[FRAM_MEMBER.get(memberIndex).getFirstJointNumber()];
        x2 = x[FRAM_MEMBER.get(memberIndex).getSecondJointNumber()];
        y2 = y[FRAM_MEMBER.get(memberIndex).getSecondJointNumber()];
        double[][] q = new double[6][6];
        double [][] TransFormationMatrix = TransformationMatrix(x1,y1,x2,y2);
        try {
            q = mainscreen.get_q(this.D, memberIndex);
            q = MultiplayMatrix(TransFormationMatrix,q);
            Axial_LeftReaction   =  Format(q[1][0],"0.000");
            Axial_RightReaction  =  Format(q[4][0],"0.000");
            Moment_LeftReaction  =  Format(q[2][0],"0.000");
            Moment_RightReaction =  Format(q[5][0],"0.000");
        } catch (IOException ex) {
            Logger.getLogger(ShearDiagram.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
       
        //Distributed Loads.............(((1)))...................
        double w1,w2,p1,p2; //In Newton
        for(int i = 0 ; i<=NumberOfDistributedLoads-1 ; i++){
            w1 = 1000*UNIFORM_LOADS.get(memberIndex).getUniformLoadValue(i); //*1000 to convert to Newton
            w2 = 1000*UNIFORM_LOADS.get(memberIndex).getUniformLoadValue(i); //*1000 to convert to Newton
            p1 = UNIFORM_LOADS.get(memberIndex).getStartLocation(i);
            p2 = UNIFORM_LOADS.get(memberIndex).getStartLocation(i)+UNIFORM_LOADS.get(memberIndex).getUniformLoadLength(i);
            
            if(Math.abs(w1) >= Min){Min = Math.abs(w1);}
            if(Math.abs(w2) >= Min){Min = Math.abs(w2);}
            
            w1=w1/100; w2=w2/100; p1=p1*100; p2=p2*100;
            
            //-------m1------------
            for(int i1=0 ; i1<=p1 ; i1++){
                Moment[i1] = 0 + Moment[i1];
                Shear[i1] = 0 + Shear[i1];
            }
            //-------m2------------
            for(double i1=p1+1 ; i1<=p2-1 ; i1++){
                int i2 = (int)i1;
                
                double wx = (((w2 - w1) / (p2 - p1)) * (i2 - p1)) + w1;
                double a1 = w1 * (i2 - p1);
                double a2 = 0.5 * (wx - w1) * (i2 - p1);
                double u1 = (i2 - p1) / 2;
                double u2 = (2 / 3) * (i2 - p1);
                double p3 = ((u1 * a1 + u2 * a2) / (a1 + a2)) + p1;
                
                Moment[i2] = (((w1 + wx) / 2) * (i2 - p1)) * (i2 - p3) + Moment[i2];
                Shear[i2] = (((wx + w1) / 2) * (i2 - p1)) + Shear[i2];
            }
            //-------m3------------
            for(double i1=p2 ; i1<=l ; i1++){
                int i3 = (int)i1;
                
                double a1 = w1 * (p2 - p1);
                double a2 = 0.5 * (w2 - w1) * (p2 - p1);
                double u1 = (p2 - p1) / 2;
                double u2 = (2 / 3) * (p2 - p1);
                double p3 = ((u1 * a1 + u2 * a2) / (a1 + a2)) + p1;
                
                Moment[i3] = (((w1 + w2) / 2) * (p2 - p1)) * (i3 - p3) + Moment[i3];
                Shear[i3] = (((w1 + w2) / 2) * (p2 - p1)) + Shear[i3];
            }
        }
        
        //Axial Loads.............(((2)))...................
        double p,p4;
        for(int i=0 ; i<=NumberOfAxialLoads-1 ; i++){
            p = 1000*AXIAL_LOADS.get(memberIndex).getLoads(i);
            p4 = AXIAL_LOADS.get(memberIndex).getLocation(i);
            p4 = p4*100;
            for(int i1=0 ; i1<=p4 ; i1++){
                Moment[i1] = 0 + Moment[i1];
                Shear[i1] = 0 + Shear[i1];
            }
            for(double i1=p4 ; i1<=l ; i1++){
                int i2 = (int)i1;
                
                Moment[i2] = (p * (i2 - p4)) + Moment[i2];
                Shear[i2] = p + Shear[i2];
            }
        }
        //Moment Loads.............(((4)))...................
        double m,p5;
        for(int i=0 ; i<=NumberOfMomentLoads-1 ; i++){
            m = 1000*MOMENT_LOADS.get(memberIndex).getMoments(i); //Convert to Newton
            p5 = MOMENT_LOADS.get(memberIndex).getLocation(i);
            
            m = -m*100; p5 = p5*100;
            
            for(int i1=0 ; i1<=p5 ; i1++){
                Moment[i1] = 0 + Moment[i1];
                Shear[i1] = 0 + Shear[i1];
            }
            for(double i1=p5 ; i1<=l ; i1++){
                int i2 = (int)i1;
                
                Moment[i2] = m + Moment[i2];
                Shear[i2] = 0 + Shear[i2];
            }
        }
        //Left Support Axial Loads.............(((5)))...................
            p = 1000*Axial_LeftReaction; //Convert to Newton
            p4 = 0;
            p4 = p4*100;
            for(int i1=0 ; i1<=p4 ; i1++){
                Moment[i1] = 0 + Moment[i1];
                Shear[i1] = 0 + Shear[i1];
            }
            for(double i1=p4 ; i1<=l ; i1++){
                int i2 = (int)i1;
                
                Moment[i2] = (p * (i2 - p4)) + Moment[i2];
                Shear[i2] = p + Shear[i2];
            }
        //Right Support Axial Loads.............(((6)))...................
            p = 1000*Axial_RightReaction; //Convert to Newton
            p4 = FRAM_MEMBER.get(memberIndex).getLength();
            p4 = p4*100;
            for(int i1=0 ; i1<=p4 ; i1++){
                Moment[i1] = 0 + Moment[i1];
                Shear[i1] = 0 + Shear[i1];
            }
            for(double i1=p4 ; i1<=l ; i1++){
                int i2 = (int)i1;
                
                Moment[i2] = (p * (i2 - p4)) + Moment[i2];
                Shear[i2] = p + Shear[i2];
            }
        //Left Support Moment Loads.............(((7)))...................
            m = 1000*Moment_LeftReaction; //Convert to Newton
            p5 = 0;
            
            m = -m*100; p5 = p5*100;
            
            for(int i1=0 ; i1<=p5 ; i1++){
                Moment[i1] = 0 + Moment[i1];
                Shear[i1] = 0 + Shear[i1];
            }
            for(double i1=p5 ; i1<=l ; i1++){
                int i2 = (int)i1;
                
                Moment[i2] = m + Moment[i2];
                Shear[i2] = 0 + Shear[i2];
            }
        //Right Support Moment Loads.............(((8)))...................
            m = 1000*Moment_RightReaction; //Convert to Newton
            p5 = FRAM_MEMBER.get(memberIndex).getLength();;
            
            m = -m*100; p5 = p5*100;
            
            for(int i1=0 ; i1<=p5 ; i1++){
                Moment[i1] = 0 + Moment[i1];
                Shear[i1] = 0 + Shear[i1];
            }
            for(double i1=p5 ; i1<=l ; i1++){
                int i2 = (int)i1;
                
                Moment[i2] = m + Moment[i2];
                Shear[i2] = 0 + Shear[i2];
            }
        
                
        System.out.println("SHERE SHERE SHERE SHERE SHERE SHERE SHERE SHERE SHERE SHERE SHERE SHERE ");
        double MaxShear = 0;
        double MinShear = 1E100;
        double MaxMoment = 0;
        double MinMoment = 1E100;
        double MaxShear_Value = 0;
        double MaxMoment_Value = 0;
        double MaxShearAt=0,MinShearAt=0,MaxMomentAt=0,MinMomentAt=0;
        for(int i=0 ; i<=l ; i++){
            //Shear[i] = Format(Shear[i]/1000,"0.000");
            //Moment[i] = Format(Moment[i]/100000,"0.000");
            if(Math.abs(Shear[i]) > Math.abs(MaxShear)){MaxShear = Shear[i];MaxShearAt=i;}
            if(Math.abs(Shear[i]) < Math.abs(MinShear)){MinShear = Shear[i];MinShearAt=i;}
            if(Math.abs(Moment[i]) > Math.abs(MaxMoment)){MaxMoment = Moment[i];MaxMomentAt=i;}
            if(Math.abs(Moment[i]) < Math.abs(MinMoment)){MinMoment = Moment[i];MinMomentAt=i;}
          
            //System.out.println(Shear[i]+" , "+Moment[i]);
        }
            MaxShear = Format(MaxShear,"0.000");
            MinShear = Format(MinShear,"0.000");
            MaxMoment = Format(MaxMoment,"0.000");
            MinMoment = Format(MinMoment,"0.000");
            if(MaxShear  == 0){MaxShear  = 1;}
            if(MaxMoment == 0){MaxMoment = 1;}
            if(Math.abs(MaxShear)>Math.abs(MinShear)){MaxShear_Value = Math.abs(MaxShear);}else{MaxShear_Value = Math.abs(MinShear);}
            if(Math.abs(MaxMoment)>Math.abs(MinMoment)){MaxMoment_Value = Math.abs(MaxMoment);}else{MaxMoment_Value = Math.abs(MinMoment);}
        
        //jPanel_ShearDiagram--------------------------------Drawing--------------------------------------------------------
        myDrawing.setPanel(jPanel_ShearDiagram);
        myDrawing.CleanThePanel(jPanel_ShearDiagram);
        //Beam+Loads........................
        double SX1,SY1,SX2,SY2,x_start,y_start,x_end,y_end,line_length;
        myDrawing.setPanel(jPanel_ShearDiagram);
         x_start = jPanel_ShearDiagram.getWidth()/4;
         y_start = jPanel_ShearDiagram.getHeight()/5;
         x_end = 3*jPanel_ShearDiagram.getWidth()/4;
         y_end = jPanel_ShearDiagram.getHeight()/5;
        
         line_length = Math.sqrt(Math.pow((x_end-x_start), 2)+Math.pow((y_end-y_start), 2)); 
         myDrawing.DrawFreeLine(x_start,y_start,x_end,y_end,Color.BLUE,3);
         myDrawing.DrawFreePoints(x_start,y_start,Color.RED,5);
         myDrawing.DrawFreePoints(x_end,y_end,Color.RED,5);
         myDrawing.DrawFreeJointsNumber(x_start,y_start,FRAM_MEMBER.get(memberIndex).getFirstJointNumber());
         myDrawing.DrawFreeJointsNumber(x_end,y_end,FRAM_MEMBER.get(memberIndex).getSecondJointNumber());
         if(Axial_LeftReaction != 0){myDrawing.DrawFreeTheJointLoads(x_start, y_start, 2, Axial_LeftReaction);}
         if(Axial_RightReaction !=0){myDrawing.DrawFreeTheJointLoads(x_end, y_end, 2, Axial_RightReaction);}
         if(Moment_LeftReaction != 0){myDrawing.DrawFreeTheJointLoads(x_start, y_start, 3, Moment_LeftReaction);}
         if(Moment_RightReaction != 0){myDrawing.DrawFreeTheJointLoads(x_end, y_end, 3, Moment_RightReaction);}
         //Axial Loads
            for(int j=0 ; j<= AXIAL_LOADS.get(memberIndex).getSize()-1 ; j++){
                myDrawing.DrawFreeTheAxialLoads(x_start,y_start,x_end,y_end, (100*AXIAL_LOADS.get(memberIndex).getLocation(j)/l*line_length),AXIAL_LOADS.get(memberIndex).getLoads(j));
            }
        //Uniform_Loads
            for(int j=0 ; j<= UNIFORM_LOADS.get(memberIndex).getSize()-1 ; j++){
                myDrawing.DrawFreeTheUniformLoads(x_start,y_start,x_end,y_end, ((100.0*UNIFORM_LOADS.get(memberIndex).getStartLocation(j))/l*line_length),((100.0*UNIFORM_LOADS.get(memberIndex).getEndLocation(j))/l*line_length), UNIFORM_LOADS.get(memberIndex).getUniformLoadValue(j));
            }
        //Moment Loads
            for(int j=0 ; j<= MOMENT_LOADS.get(memberIndex).getSize()-1 ; j++){
                myDrawing.DrawFreeTheMomentLoads(x_start,y_start,x_end,y_end, (100*MOMENT_LOADS.get(memberIndex).getLocation(j)/l*line_length),MOMENT_LOADS.get(memberIndex).getMoments(j));
            }
       
        
        //Shear Diagram........................
        myDrawing.setPanel(jPanel_ShearDiagram);
         x_start = jPanel_ShearDiagram.getWidth()/4;
         y_start = jPanel_ShearDiagram.getHeight()/2;
         x_end = 3*jPanel_ShearDiagram.getWidth()/4;
         y_end = jPanel_ShearDiagram.getHeight()/2;
        
         line_length = Math.sqrt(Math.pow((x_end-x_start), 2)+Math.pow((y_end-y_start), 2)); 
        myDrawing.DrawFreeLine(x_start,y_start,x_end,y_end,Color.BLUE,3);
        for(int i=0 ; i<=l-1 ; i++){
            double ii = (double)i;
            double ll = (double)l;
            SX1 = ((ii/ll)*line_length) + x_start;
            SY1 = y_start - ((Shear[i]/MaxShear_Value)*(40.0));
            SX2 = ((ii+1.0)/ll)*line_length + x_start;
            SY2 = y_start - ((Shear[i+1]/MaxShear_Value)*(40.0));
            System.out.println(SX1);
            if(i==0){myDrawing.DrawFreeLine(x_start,y_start,SX1,SY1,Color.MAGENTA,2);}
            if(i==(l-1)){myDrawing.DrawFreeLine(SX2,SY2,x_end,y_end,Color.MAGENTA,2);}
            myDrawing.DrawFreeLine(SX1,SY1,SX2,SY2,Color.MAGENTA,2);
        }
        
        
        //Moment Diagram........................
        myDrawing.setPanel(jPanel_ShearDiagram);
         x_start = jPanel_ShearDiagram.getWidth()/4;
         y_start = jPanel_ShearDiagram.getHeight()/1.2;
         x_end = 3*jPanel_ShearDiagram.getWidth()/4;
         y_end = jPanel_ShearDiagram.getHeight()/1.2;
        
         line_length = Math.sqrt(Math.pow((x_end-x_start), 2)+Math.pow((y_end-y_start), 2)); 
        myDrawing.DrawFreeLine(x_start,y_start,x_end,y_end,Color.BLUE,3);
        for(int i=0 ; i<=l-1 ; i++){
            double ii = (double)i;
            double ll = (double)l;
            SX1 = ((ii/ll)*line_length) + x_start;
            SY1 = y_start + ((Moment[i]/MaxMoment_Value)*(40.0));
            SX2 = ((ii+1.0)/ll)*line_length + x_start;
            SY2 = y_start + ((Moment[i+1]/MaxMoment_Value)*(40.0));
            System.out.println(SX1+","+SY1+","+SX2+","+SY2);
            if(i==0){myDrawing.DrawFreeLine(x_start,y_start,SX1,SY1,Color.MAGENTA,2);}
            if(i==(l-1)){myDrawing.DrawFreeLine(SX2,SY2,x_end,y_end,Color.MAGENTA,2);}
            myDrawing.DrawFreeLine(SX1,SY1,SX2,SY2,Color.MAGENTA,2);
        }
        
       
        
        jTextField_MaxShear.setText(String.valueOf(Format(MaxShear/1000,"0.00")));
        jTextField_MaxShearAt.setText(String.valueOf(Format(MaxShearAt/100,"0.00")));
        jTextField_MaxMoment.setText(String.valueOf(Format(MaxMoment/100000,"0.00")));
        jTextField_MaxMomentAt.setText(String.valueOf(Format(MaxMomentAt/100,"0.00")));
        
        jTextField_MinShear.setText(String.valueOf(Format(MinShear/1000,"0.00")));
        jTextField_MinShearAt.setText(String.valueOf(Format(MinShearAt/100,"0.00")));
        jTextField_MinMoment.setText(String.valueOf(Format(MinMoment/100000,"0.00")));
        jTextField_MinMomentAt.setText(String.valueOf(Format(MinMomentAt/100,"0.00")));
       
        MomentText.setText(String.valueOf(Format(MaxMoment/100000,"0.00")));
        //Share the values of shear and moment with SHEAR MOMENT TO use them in GET BUTON
        for(int i=0 ; i<=l ;i++){
            SHEAR.add(Shear[i]);
            MOMENT.add(Moment[i]);
        }
        MAXSHEAR_Value = MaxShear_Value;
        MAXMOMENT_Value = MaxMoment_Value;
       
    }
   

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AText;
    private javax.swing.JTextField BarDiameterText;
    private javax.swing.JTextField BetaText;
    private javax.swing.JTextField ConcreteCostText;
    private javax.swing.JTextField CoverText;
    private javax.swing.JTextField DText;
    private javax.swing.JTextField DensityFinalText;
    private javax.swing.JTextField DensityText;
    private javax.swing.JButton DesignButton;
    private javax.swing.JLabel DesnityFinalCheckLabel;
    private javax.swing.JTextField FcText;
    private javax.swing.JLabel FinalSection;
    private javax.swing.JTextField FyText;
    private javax.swing.JTextField MemberHeightText;
    private javax.swing.JTextField MemberWidthText;
    private javax.swing.JTextField MinWidthText;
    private javax.swing.JLabel MomentCapacityCheckLabel;
    private javax.swing.JTextField MomentCapacityText;
    private javax.swing.JTextField MomentText;
    private javax.swing.JTextField NumberOfBarsText;
    private javax.swing.JTextField PhiText;
    private javax.swing.JLabel ResultsLabel;
    private javax.swing.JLabel SmaxCheckLabel;
    private javax.swing.JTextField SmaxText;
    private javax.swing.JTextField SteelCostPrice;
    private javax.swing.JTextField StirrupsText;
    private javax.swing.JLabel WidthCheckCapacityLabel;
    private javax.swing.JLabel costLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_DrawShearDiagram;
    private javax.swing.JComboBox<String> jComboBox_MemberIndex;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JPanel jPanel_ShearDiagram;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextField jTextField_MaxMoment;
    private javax.swing.JTextField jTextField_MaxMomentAt;
    private javax.swing.JTextField jTextField_MaxShear;
    private javax.swing.JTextField jTextField_MaxShearAt;
    private javax.swing.JTextField jTextField_MinMoment;
    private javax.swing.JTextField jTextField_MinMomentAt;
    private javax.swing.JTextField jTextField_MinShear;
    private javax.swing.JTextField jTextField_MinShearAt;
    private javax.swing.JTextField jTextField_MomentValue;
    private javax.swing.JTextField jTextField_ShearValue;
    private javax.swing.JTextField jTextField_ValueAt;
    // End of variables declaration//GEN-END:variables
}
