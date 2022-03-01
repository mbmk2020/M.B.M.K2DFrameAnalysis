/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.b.m.k2dframeanalysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Mohammad Bilal Al-khasawneh
 * @email mbmk2020@umd.edu
 */
public class MBMK2DFrameAnalysis {

    /**
     * @param args the command line arguments
     */
     
    //All Axial Loads..
    //| Index (Beam) | AxialLoads (Group) |      *Each Beam has it own table to describe his loads
    //|       0      |         L1         |=====> | Index | P   |   a  | That mean beam (1) has two axial loads @ 2.3 and @ 3.3
    //|       1      |         L2         |       |   0   | -50 | 2.3m |  
    //|       2      |         L3         |       |   1   | -20 | 3.3m |      

    //For The Fram without finite element
    public static ArrayList<JointSupport> JOINT_SUPPORTS = new ArrayList<>();
    public static ArrayList<JointLoad> JOINT_LOADS = new ArrayList<>();
    public static ArrayList<FramMember> FRAM_MEMBER = new ArrayList<>();
    public static ArrayList<AxialLoad> AXIAL_LOADS = new ArrayList<>();
    public static ArrayList<UniformLoad> UNIFORM_LOADS = new ArrayList<>();
    public static ArrayList<MomentLoad> MOMENT_LOADS = new ArrayList<>();
    public static ArrayList<Support> SUPPORTS = new ArrayList<>();
    
    //For The Fram finite element
    public static ArrayList<JointSupport> joints_supports = new ArrayList<>();
    public static ArrayList<JointLoad> joint_loads = new ArrayList<>();
    public static ArrayList<FramMember> fram_member = new ArrayList<>();
    public static ArrayList<AxialLoad> axial_loads = new ArrayList<>();
    public static ArrayList<UniformLoad> uniform_loads = new ArrayList<>();
    public static ArrayList<MomentLoad> moment_load = new ArrayList<>();
    
    
    //Members Data As Array List
    /*
     i | joint(1) number | joint(2) number
    --------------------------------------
     0         0                1 <--- This is the example down
     1         1                2
     2         2                3
    
    to retrieve the data --> MymembersData.get(0)[0] = that mean the first joint of  member 0 == 0
                             MymembersData.get(0)[1] = that mean the Second joint of member 0 == 1
    **Also the same with Joints Data but there we will find the coordinayes of the joint instead of joints of member
    */
    
    public static ArrayList<String[]> MyjointsData = new ArrayList<String[]>();
    public static ArrayList<String[]> MymembersData = new ArrayList<String[]>();
    
    //For the fram without finite element
    public static double[] x = new double[100000]; //x coordinates i.e. x[0] --> x-coordinates for joint (1)
    public static double[] y = new double[100000]; //y coordinates i.e. y[1] --> x-coordinates for joint (2)
    public static int[] j1 = new int[100000];//joint one number i.e. member (1) connect between a,b that mean j1(0) = a
    public static int[] j2 = new int[100000];//joint one number i.e. member (1) connect between a,b that mean j1(0) = b

     //For the fram with finite element
    public static ArrayList<Double> x_finite = new ArrayList<>(); //x coordinates i.e. x.get(0) --> x-coordinates for joint (1)
    public static ArrayList<Double> y_finite = new ArrayList<>(); //y coordinates i.e. y.get(1) --> x-coordinates for joint (2)
    public static ArrayList<Integer> j1_finite = new ArrayList<>();//joint one number i.e. member (1) connect between a,b that mean j1.get(0) = a
    public static ArrayList<Integer> j2_finite = new ArrayList<>();//joint one number i.e. member (1) connect between a,b that mean j1.get(0) = b
    
    //Define the screen Here And Open It once the application is open b the user....
    public static MainScreen m= new MainScreen();
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
       
        m.setVisible(true);
       
        /*
        // TODO code application logic here
        MyjointsData = Text_Reader("joints.txt");
        MymembersData = Text_Reader("members.txt");
        //Retrieve XY Coordinates data and fill out in the arrays..
        for (int i = 0; i <= MyjointsData.size() - 1; i++){
            x[i] = Double.valueOf(MyjointsData.get(i)[0]);
            y[i] = Double.valueOf(MyjointsData.get(i)[1]);
            //System.out.println(x[i]+" , "+y[i]);
        }
        //Retrieve memebrs joints composed "which are these joints that connect of this beam??"
        for (int i = 0; i <= MymembersData.size() - 1; i++){
            j1[i] = Integer.valueOf(MymembersData.get(i)[0]); 
            j2[i] = Integer.valueOf(MymembersData.get(i)[1]);
            //System.out.println(j1[i]+" , "+j2[i]);
        }
        //Here Initialize for all data....
        //Initilize the AxialLoads Table, Supports Table and then later will fill out by user....
        for (int i=0 ; i<= MymembersData.size()-1; i++){
            FRAM_MEMBER.add(new FramMember(j1[i],j2[i],x[j1[i]],y[j1[i]],x[j2[i]],y[j2[i]],200,60E6,600)); //Gpa, mm4, mm2
            AXIAL_LOADS.add(new AxialLoad(x[j1[i]],y[j1[i]],x[j2[i]],y[j2[i]]));//Make Axial Loads Table
            UNIFORM_LOADS.add(new UniformLoad(x[j1[i]],y[j1[i]],x[j2[i]],y[j2[i]])); //Make Uniform Loads Table
            MOMENT_LOADS.add(new MomentLoad(x[j1[i]],y[j1[i]],x[j2[i]],y[j2[i]]));//Make Moment Loads Table
            SUPPORTS.add(new Support("ixyz","ixyz"));//Make Supports Table the table default case fixed-fixed
        }
        //Initilize the joints Load [Q] Matrix....
        for (int i=0 ; i<= MyjointsData.size()-1 ; i++){
            JOINT_LOADS.add(new JointLoad(i,0,0,0));
            JOINT_SUPPORTS.add(new JointSupport("free"));
            //Now we have a[Q] Matrix all zero 
        }
       
      
        
        //--1-- Set Support Type
        JOINT_SUPPORTS.get(0).set_Support("xyz");
        JOINT_SUPPORTS.get(1).set_Support("xy");
      
        
        //--2--Set The Loads
        AXIAL_LOADS.get(0).AddLoad(-75, 2);
        AXIAL_LOADS.get(0).AddLoad(20, 4.3);
        
       

        //--3--Set the Joints Load
        //JOINT_LOADS.get(5).addForce(2, -10);
        
        //--4--Edit the properties
    
        //AnalysisANDprint();     
       
        //--5--Finite Element...
        //FEM(200);
        
        //AnalysisANDprint_FINITE();
       
      
          
          
       
        */
       
       
    }
        
        
    public static ArrayList<String[]> Text_Reader(String text_file_name) throws FileNotFoundException, IOException{
        int NumberOfPoints = 0;
        ArrayList<String[]> data = new ArrayList<String[]>();
        String[] XY = new String[2];
         try (BufferedReader br = new BufferedReader(new FileReader(text_file_name))) {
         String line;
         while ((line = br.readLine()) != null) {
           List<String> joints = Arrays.asList(line.split(","));
           //System.out.println("joint1: "+ joints.get(0)+"  joint2:"+joints.get(1));
           XY[0] = joints.get(0);
           XY[1] = joints.get(1);
           
           data.add(new String[]{XY[0],XY[1]});
           NumberOfPoints +=1;
        }

      }
       return data;
       
       //Note that this code return the data as arraylist x,y
       //                                                 x2,y2
       //                                                 x3,y3
       //each raw has Double[] XY = new Double[2] insid an ArrayList...
    }
    
    public static double[][] MultiplayMatrix(double[][] M1, double[][] M2){
        int ar1_raw,ar1_col,ar2_raw,ar2_col,x,count,start;
        double[] sum = new double[1000];
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
    public static double[][] Matrix1PlusMatrix2(double[][] M1, double[][] M2){
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
    public static double[][] Matrix1MinusMatrix2(double[][] M1, double[][] M2){
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
    public static double[][] MatrixInverse(double[][] M){
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
    public static double[][] BuildUnitMatrix(int SizeOfMatrix){
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
    public static void PrintMatrix(double[][] M,String decimals){
        for (int i = 0; i <= M.length - 1;i++){
            for(int j=0; j <= M[i].length - 1;j++){
                System.out.print(" "+String.valueOf(Format(M[i][j],decimals)));
            }
            System.out.println("");
        }
       System.out.println("");
    }
    public static double[][] reduceMatrix(double[][] M, ArrayList<Integer> Index, String direction){
        
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
    public static double Format(double value, String decimals){
        DecimalFormat df2 = new DecimalFormat( decimals );
         double newValue = new Double(df2.format(value)).doubleValue();
         return newValue;
    }
    public static void clearMatrix(Object M[][]){
        for(int i=0 ; i<= M.length; i++){
            for(int j=0 ; j<=M[i].length; j++){
                M[i][j] = null;
            }
        }
        M = null;
    }
    
    
   
    public static double[][] QF() throws IOException    {
        //axial load variables
        double b;
        double a;
        double p;
        
        //Unifrom load variables
        double bb;
        double aa;
        double cc;
        double w;
        double aaa; //aaa, bbb --> for fixed -fixed calculation
        double bbb;
        
        //Momnet Variables
        double m;
        double am;
        double bm;

        
        
        //Variables..........
        ArrayList<String[]> jointsData = new ArrayList<String[]>();
        ArrayList<String[]> membersData = new ArrayList<String[]>();
        int number_of_joints;
        int number_of_members;
        double x1,y1,z1,x2,y2,z2;
        double[][] local_loads = new double[6][1];//size 1x6 but the indixes will be 5x1
        double[][] major_loads = new double[6][1];
        double[][] TraM = new double[6][6]; //Transformation Matrix
        double cx1,cy1,cx2,cy2;
        double length,sinTheta,cosTheta;
        String TR1,TR2; //Type of Support 1 and 2
        
        jointsData = Text_Reader("joints.txt");
        membersData = Text_Reader("members.txt");

        number_of_joints = jointsData.size();
        number_of_members = membersData.size();
        double[][] QF = new double[3*number_of_joints][1];
        //******************************************************************************//
        for (int i=0; i<=number_of_members - 1 ; i++){
            
            double x1_calculations = 0; //These all next variables are for calculate @ each point then use it for accumulative
            double y1_calculations = 0;
            double z1_calculations = 0;
            double x2_calculations = 0;
            double y2_calculations = 0;
            double z2_calculations = 0;

            cx1 = x[j1[i]];
            cy1 = y[j1[i]];
            cx2 = x[j2[i]];
            cy2 = y[j2[i]];
            
            x1 = 0;
            y1 = 0;
            z1 = 0;
            x2 = 0;
            y2 = 0;
            z2 = 0;
            
            length = Math.pow(Math.pow(cx2-cx1, 2)+Math.pow(cy2-cy1, 2), 0.5);
            
            //Using Member Method
            //TR1 = SUPPORTS.get(i).getLeftSupport();
            //TR2 = SUPPORTS.get(i).getRightSupport();
            
            //Using Joint Method
            TR1 = JOINT_SUPPORTS.get(FRAM_MEMBER.get(i).getFirstJointNumber()).getSupportType();
            TR2 = JOINT_SUPPORTS.get(FRAM_MEMBER.get(i).getSecondJointNumber()).getSupportType();
            
            
            sinTheta = (cy2 - cy1)/length;
            cosTheta = (cx2 - cx1)/length;
            
           
                //1) Fixed - Hinge
                if((TR1 == "xyz" && TR2 == "xy") || (TR1 == "xyz" && TR2 == "y") || (TR1 == "ixyz" && TR2 == "xy") ||(TR1 == "ixyz" && TR2 == "y")){
                //xyz = fixed , xy = hinge , y = roller
                    //Axial Loads Anylysis
                    for (int j = 0 ; j<= AXIAL_LOADS.get(i).getSize() - 1; j++){
                
                         a = AXIAL_LOADS.get(i).getLocation(j);
                         b = length - a;
                         p = AXIAL_LOADS.get(i).getLoads(j);
               
                         z1_calculations= (-p*a*b*(length+b))/(2*Math.pow(length, 2));
                         y1_calculations = (z1_calculations - p*b)/length;
                         y2_calculations = -y1_calculations - p;
                         
                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;

                    }
                      //Uniform Loads Anylysis
                      for (int j = 0 ; j<= UNIFORM_LOADS.get(i).getSize() - 1; j++){
                         aa = UNIFORM_LOADS.get(i).getStartLocation(j);
                         bb = UNIFORM_LOADS.get(i).getUniformLoadLength(j);
                         w = UNIFORM_LOADS.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                         /////////////////////////////Here ................................................................
                         y2_calculations = (-w/(8*Math.pow(length, 3)))*(7*Math.pow(aa, 4) - 24*Math.pow(aa, 3)*bb - 4*Math.pow(aa, 3)*length + 12*Math.pow(aa, 2)*Math.pow(bb, 2) + 24*Math.pow(aa, 2)*bb*length - 4*aa*Math.pow(bb, 3) - 1*Math.pow(bb, 4) + 4*Math.pow(bb, 3)*length);
                         y1_calculations = -y2_calculations - w*bb;
                         z1_calculations = y1_calculations*length + w*bb*(bb/2 + cc);

                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= MOMENT_LOADS.get(i).getSize() - 1; j++){
                         
                          m =MOMENT_LOADS.get(i).getMoments(j);
                          am = MOMENT_LOADS.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                         z1_calculations = -m*(1 - (3*am/length) + (1.5*Math.pow(am, 2)/Math.pow(length, 2)));
                         y1_calculations = (z1_calculations + m) /length ;
                         y2_calculations = -y1_calculations;
                        
                         x1_calculations = 0;
                         x2_calculations = 0;
                         z2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }
                }
                
                //2) Hinge - Fixed
                if((TR1 == "xy" && TR2 == "xyz") || (TR1 == "y" && TR2 == "xyz") || (TR1 == "xy" && TR2 == "ixyz") || (TR1 == "y" && TR2 == "ixyz")){
                //xyz = fixed , xy = hinge , y = roller
                //Axial Loads Anylysis
                    for (int j = 0 ; j<= AXIAL_LOADS.get(i).getSize() - 1; j++){
                
                         a = AXIAL_LOADS.get(i).getLocation(j);
                         b = length - a;
                         p = AXIAL_LOADS.get(i).getLoads(j);
               
                         z2_calculations= (p*a*b*(length+a))/(2*Math.pow(length, 2));
                         y1_calculations = (z2_calculations - p*b)/length;
                         y2_calculations = -y1_calculations - p;
                         
                         z1_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= UNIFORM_LOADS.get(i).getSize() - 1; j++){
                
                         aa = UNIFORM_LOADS.get(i).getStartLocation(j);
                         bb = UNIFORM_LOADS.get(i).getUniformLoadLength(j);
                         w = UNIFORM_LOADS.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                         /////////////////////////////Here ................................................................
                         y1_calculations = (-w/(8*Math.pow(length, 3)))*(7*Math.pow(cc, 4) - 24*Math.pow(cc, 3)*bb - 4*Math.pow(cc, 3)*length + 12*Math.pow(cc, 2)*Math.pow(bb, 2) + 24*Math.pow(cc, 2)*bb*length - 4*cc*Math.pow(bb, 3) - 1*Math.pow(bb, 4) + 4*Math.pow(bb, 3)*length);
                         y2_calculations = -y1_calculations - w*bb;
                         z2_calculations = y1_calculations*length + w*bb*(bb/2 + cc);

                         z1_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                      
                     //Moment Loads Anylysis
                      for (int j = 0 ; j<= MOMENT_LOADS.get(i).getSize() - 1; j++){
                         
                          m =MOMENT_LOADS.get(i).getMoments(j);
                          am = MOMENT_LOADS.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                         z2_calculations = -m*(1 - (3*bm/length) + (1.5*Math.pow(bm, 2)/Math.pow(length, 2)));
                         y1_calculations = (z2_calculations + m) /length ;
                         y2_calculations = -y1_calculations;
                        
                         x1_calculations = 0;
                         x2_calculations = 0;
                         z1_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                //3) Fixed - Fixed
                if((TR1 == "xyz" && TR2 == "xyz") || (TR1 == "ixyz" && TR2 == "ixyz") || (TR1 == "ixyz" && TR2 == "xyz") || (TR1 == "xyz" && TR2 == "ixyz")){
                //xyz = fixed , xy = hinge , y = roller
                    for (int j = 0 ; j<= AXIAL_LOADS.get(i).getSize() - 1; j++){
                
                         a = AXIAL_LOADS.get(i).getLocation(j);
                         b = length - a;
                         p = AXIAL_LOADS.get(i).getLoads(j);
               
                         z1_calculations= (-p*a*Math.pow(b, 2))/(Math.pow(length, 2));
                         z2_calculations= (p*b*Math.pow(a, 2))/(Math.pow(length, 2));
                         y1_calculations = (z1_calculations+z2_calculations-(p*b))/length;
                         y2_calculations = -y1_calculations-p;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                      //Uniform Loads Anylysis
                      for (int j = 0 ; j<= UNIFORM_LOADS.get(i).getSize() - 1; j++){
                         aa = UNIFORM_LOADS.get(i).getStartLocation(j);
                         bb = UNIFORM_LOADS.get(i).getUniformLoadLength(j);
                         w = UNIFORM_LOADS.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                         aaa = aa + bb/2;
                         bbb = cc + bb/2;
                         /////////////////////////////Here ................................................................
                         z1_calculations = ((-w*bb)/Math.pow(length, 2))*(aaa*Math.pow(bbb, 2) + (((aaa-2*bbb)*Math.pow(bb, 2))/12));
                         z2_calculations = ((w*bb)/Math.pow(length, 2))*(bbb*Math.pow(aaa, 2) + (((bbb-2*aaa)*Math.pow(bb, 2))/12));
                         y1_calculations = (-w*bb/Math.pow(length, 3))*(((2*aaa+length)*Math.pow(bbb, 2))+(((aaa-bbb)/4)*Math.pow(bb, 2)));
                         y2_calculations = (-w*bb/Math.pow(length, 3))*(((2*bbb+length)*Math.pow(aaa, 2))+(((aaa-bbb)/4)*Math.pow(bb, 2)));
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                        //Moment Loads Anylysis
                      for (int j = 0 ; j<= MOMENT_LOADS.get(i).getSize() - 1; j++){
                         
                          m =MOMENT_LOADS.get(i).getMoments(j);
                          am = MOMENT_LOADS.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                         z1_calculations = -m*bm*(bm - 2*am) / Math.pow(length, 2);
                         z2_calculations =  m*bm*(2*bm - am) / Math.pow(length, 2);
                         y1_calculations = (z1_calculations+z2_calculations+m) / length;
                         y2_calculations = -y1_calculations;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                //4) Hinge - Roller or Roller - Hinge or Hinge - Hinge
                if((TR1 == "xy" && TR2 == "y") || (TR1 == "y" && TR2 == "xy") || (TR1 == "xy" && TR2 == "xy")){
                //xyz = fixed , xy = hinge , y = roller
                    for (int j = 0 ; j<= AXIAL_LOADS.get(i).getSize() - 1; j++){
                
                         a = AXIAL_LOADS.get(i).getLocation(j);
                         b = length - a;
                         p = AXIAL_LOADS.get(i).getLoads(j);
               
                         
                         y1_calculations = -p*b/length;
                         y2_calculations = -p*a/length;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         z1_calculations = 0;
                         z2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= UNIFORM_LOADS.get(i).getSize() - 1; j++){
                         aa = UNIFORM_LOADS.get(i).getStartLocation(j);
                         bb = UNIFORM_LOADS.get(i).getUniformLoadLength(j);
                         w = UNIFORM_LOADS.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                        
                         /////////////////////////////Here ................................................................
                        
                         y1_calculations = -w*bb*(bb/2+cc)/length;
                         y2_calculations = -y1_calculations - w*bb;
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         z1_calculations = 0;
                         z2_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= MOMENT_LOADS.get(i).getSize() - 1; j++){
                         
                          m =MOMENT_LOADS.get(i).getMoments(j);
                          am = MOMENT_LOADS.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                        
                         y1_calculations = m / length;
                         y2_calculations = -y1_calculations;
                         
                         z1_calculations = 0;
                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }   
                
                  //5) Fixed - Free
                if((TR1 == "xyz" && TR2 == "free") || (TR1 == "ixyz" && TR2 == "free")){
                //xyz = fixed , xy = hinge , y = roller, free = free
                    for (int j = 0 ; j<= AXIAL_LOADS.get(i).getSize() - 1; j++){
                
                     
                         a = AXIAL_LOADS.get(i).getLocation(j);
                         b = length - a;
                         p = AXIAL_LOADS.get(i).getLoads(j);
               
                         
                         y1_calculations = -p;
                         z1_calculations = -p*a;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         y2_calculations = 0;
                         z2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= UNIFORM_LOADS.get(i).getSize() - 1; j++){
                         aa = UNIFORM_LOADS.get(i).getStartLocation(j);
                         bb = UNIFORM_LOADS.get(i).getUniformLoadLength(j);
                         w = UNIFORM_LOADS.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                        
                         /////////////////////////////Here ................................................................
                        
                         y1_calculations = -w*bb;
                         z1_calculations = -w*bb*(bb/2+aa);
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         y2_calculations = 0;
                         z2_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= MOMENT_LOADS.get(i).getSize() - 1; j++){
                         
                          m =MOMENT_LOADS.get(i).getMoments(j);
                          am = MOMENT_LOADS.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                        
                         z1_calculations = -m;
                         
                         y1_calculations = 0;
                         y2_calculations = 0;
                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                     //5) Free - Fixed
                if((TR1 == "free" && TR2 == "xyz") || (TR1 == "free" && TR2 == "ixyz")){
                //xyz = fixed , xy = hinge , y = roller, free = free
                    for (int j = 0 ; j<= AXIAL_LOADS.get(i).getSize() - 1; j++){
                
                     
                         a = AXIAL_LOADS.get(i).getLocation(j);
                         b = length - a;
                         p = AXIAL_LOADS.get(i).getLoads(j);
               
                         
                         y2_calculations = -p;
                         z2_calculations = p*a;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         y1_calculations = 0;
                         z1_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= UNIFORM_LOADS.get(i).getSize() - 1; j++){
                         aa = UNIFORM_LOADS.get(i).getStartLocation(j);
                         bb = UNIFORM_LOADS.get(i).getUniformLoadLength(j);
                         w = UNIFORM_LOADS.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                        
                         /////////////////////////////Here ................................................................
                        
                         y2_calculations = -w*bb;
                         z2_calculations = w*bb*(bb/2+cc);
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         y1_calculations = 0;
                         z1_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= MOMENT_LOADS.get(i).getSize() - 1; j++){
                         
                          m =MOMENT_LOADS.get(i).getMoments(j);
                          am = MOMENT_LOADS.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                        
                         z2_calculations = -m;
                         
                         y1_calculations = 0;
                         y2_calculations = 0;
                         z1_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                //System.out.println("x1="+x1+"  y1="+y1+"  z1="+z1+"  x2="+x2+"  y2="+y2+"  z2="+z2);

                //After we calculated the local reactions, now we need to convert it to Major X-Y Coordinates....
                //#1--Make the Transformation Matrix
                TraM[0][0] = cosTheta ; TraM[0][1]= -sinTheta ; TraM[0][2] = 0 ; TraM[0][3] =    0     ; TraM[0][4] =     0      ; TraM[0][5] = 0 ;
                TraM[1][0] = sinTheta ; TraM[1][1]=  cosTheta ; TraM[1][2] = 0 ; TraM[1][3] =    0     ; TraM[1][4] =     0      ; TraM[1][5] = 0 ; 
                TraM[2][0] =    0     ; TraM[2][1]=      0    ; TraM[2][2] = 1 ; TraM[2][3] =    0     ; TraM[2][4] =     0      ; TraM[2][5] = 0 ; 
                TraM[3][0] =    0     ; TraM[3][1]=      0    ; TraM[3][2] = 0 ; TraM[3][3] = cosTheta ; TraM[3][4] = -sinTheta  ; TraM[3][5] = 0 ;
                TraM[4][0] =    0     ; TraM[4][1]=      0    ; TraM[4][2] = 0 ; TraM[4][3] = sinTheta ; TraM[4][4] = cosTheta   ; TraM[4][5] = 0 ; 
                TraM[5][0] =    0     ; TraM[5][1]=      0    ; TraM[5][2] = 0 ; TraM[5][3] =   0      ; TraM[5][4] =     0      ; TraM[5][5] = 1 ; 

                //#2--Make the local reactions matrix
                local_loads[0][0] = x1;
                local_loads[1][0] = y1;
                local_loads[2][0] = z1;
                local_loads[3][0] = x2;
                local_loads[4][0] = y2;
                local_loads[5][0] = z2;
                
                //#3--Multiply transformation matrix by local to convert the local to major
                major_loads = MultiplayMatrix(TraM,local_loads);
                
                //#4--Combine the QF Materix that we should need to solve the frame stifness Matrix...
                QF[3*(j1[i]+1)-3][0] += major_loads[0][0];
                QF[3*(j1[i]+1)-2][0] += major_loads[1][0];
                QF[3*(j1[i]+1)-1][0] += major_loads[2][0];
                QF[3*(j2[i]+1)-3][0] += major_loads[3][0];
                QF[3*(j2[i]+1)-2][0] += major_loads[4][0];
                QF[3*(j2[i]+1)-1][0] += major_loads[5][0];

                //PrintMatrix(QF);
              }
           return QF;     
    }
    public static double[][] K() throws IOException{
    // Retrieve All data and calculate the size of K
        ArrayList<String[]> jointsData = new ArrayList<>();
        ArrayList<String[]> membersData = new ArrayList<>();
        
        jointsData = Text_Reader("joints.txt");
        membersData = Text_Reader("members.txt");
        
        int numberOfJoint = jointsData.size();
        int numberOfMember = membersData.size();
        
        double[][] K = new double[3*numberOfJoint][3*numberOfJoint];
       
        for (int i = 0; i<= numberOfMember - 1; i++){
          
            double E = FRAM_MEMBER.get(i).getE() * 1E+6;
            double I = FRAM_MEMBER.get(i).getI() * 1E-12;
            double A = FRAM_MEMBER.get(i).getA() * 1E-6;
            double ly = FRAM_MEMBER.get(i).getLamda_y();
            double lx = FRAM_MEMBER.get(i).getLamda_x();
            double L = FRAM_MEMBER.get(i).getLength();
            int Ui = 3*(j1[i]+1)-3; //0
            int Vi = 3*(j1[i]+1)-2; //1
            int Mi = 3*(j1[i]+1)-1; //2
            
            int Uj = 3*(j2[i]+1)-3; //3
            int Vj = 3*(j2[i]+1)-2; //4
            int Mj = 3*(j2[i]+1)-1; //5 
                       
            //System.out.println(Ui+""+Vi+""+Mi+""+Uj+""+Vj+""+Mj);

            //Build the K Matrix/....
            K[Ui][Ui] +=  (A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2);
            K[Ui][Vi] +=  ((A*E/L)-(12*E*I/Math.pow(L, 3))) * lx * ly;
            K[Ui][Mi] +=  (-6*E*I/Math.pow(L, 2))*ly;
            K[Ui][Uj] += -((A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2));
            K[Ui][Vj] += -((A*E/L)*lx*ly - (12*E*I/Math.pow(L, 3))*ly*lx);
            K[Ui][Mj] +=  (-6*E*I/Math.pow(L, 2))*ly;
           
            K[Vi][Ui] +=  ((A*E/L) - (12*E*I/Math.pow(L, 3)))*lx*ly;
            K[Vi][Vi] +=  (A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2);
            K[Vi][Mi] +=  (6*E*I/Math.pow(L, 2))*lx;
            K[Vi][Uj] += -((A*E/L)*ly*lx - (12*E*I/Math.pow(L, 3))*lx*ly);
            K[Vi][Vj] += -((A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2));
            K[Vi][Mj] +=  (6*E*I/Math.pow(L, 2))*lx;
           
            K[Mi][Ui] += (-6*E*I/Math.pow(L, 2))*ly;
            K[Mi][Vi] += (6*E*I/Math.pow(L, 2))*lx;
            K[Mi][Mi] += (4*E*I)/L;
            K[Mi][Uj] += (6*E*I/Math.pow(L, 2))*ly;
            K[Mi][Vj] += (-6*E*I/Math.pow(L, 2))*lx;
            K[Mi][Mj] += (2*E*I)/L;
            
            K[Uj][Ui] += -((A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2));
            K[Uj][Vi] += -((A*E/L)*lx*ly - (12*E*I/Math.pow(L, 3))*ly*lx);
            K[Uj][Mi] +=  (6*E*I/Math.pow(L, 2))*ly;
            K[Uj][Uj] +=  (A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2);
            K[Uj][Vj] +=  ((A*E/L)-(12*E*I/Math.pow(L, 3))) * lx * ly;
            K[Uj][Mj] +=  (6*E*I/Math.pow(L, 2))*ly;
            
            K[Vj][Ui] += -((A*E/L)*lx*ly - (12*E*I/Math.pow(L, 3))*ly*lx);
            K[Vj][Vi] += -((A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2));
            K[Vj][Mi] +=  (-6*E*I/Math.pow(L, 2))*lx;
            K[Vj][Uj] +=  ((A*E/L)-(12*E*I/Math.pow(L, 3))) * lx * ly;
            K[Vj][Vj] +=  (A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2);
            K[Vj][Mj] +=  (-6*E*I/Math.pow(L, 2))*lx;
            
            K[Mj][Ui] +=  (-6*E*I/Math.pow(L, 2))*ly;
            K[Mj][Vi] +=  (6*E*I/Math.pow(L, 2))*lx;
            K[Mj][Mi] +=  (2*E*I)/L;
            K[Mj][Uj] +=  (6*E*I/Math.pow(L, 2))*ly;
            K[Mj][Vj] +=  (-6*E*I/Math.pow(L, 2))*lx;
            K[Mj][Mj] +=  (4*E*I)/L;
            
        }   
       
        return K;
    } 
    public static double[][] Q() throws IOException{
        ArrayList<String[]> jointsData = new ArrayList<>();
        ArrayList<String[]> membersData = new ArrayList<>();
        
        jointsData = Text_Reader("joints.txt");
        membersData = Text_Reader("members.txt");
        
        int numberOfJoint = jointsData.size();
        int numberOfMember = membersData.size();
        
        double[][] Q = new double[3*numberOfJoint][1];
        
        for(int i=0 ; i<= numberOfJoint-1 ; i++){
           Q[3*(i+1)-3][0] =JOINT_LOADS.get(i).get_Xload();
           Q[3*(i+1)-2][0] = JOINT_LOADS.get(i).get_Yload();
           Q[3*(i+1)-1][0] = JOINT_LOADS.get(i).get_Mload();
        }
        
        return Q;
        
    }
    public static double[][] D() throws IOException{
        ArrayList<String[]> jointsData = new ArrayList<>();
        ArrayList<String[]> membersData = new ArrayList<>();
        
        jointsData = Text_Reader("joints.txt");
        membersData = Text_Reader("members.txt");
        
        int numberOfJoint = jointsData.size();
        int numberOfMember = membersData.size();
        
        double[][] D = new double[3*numberOfJoint][1];
        
       for (int i =0 ; i<= JOINT_SUPPORTS.size()-1 ; i++){
           D[3*(i+1)-3][0] = JOINT_SUPPORTS.get(i).get_d1();
           D[3*(i+1)-2][0] = JOINT_SUPPORTS.get(i).get_d2();
           D[3*(i+1)-1][0] = JOINT_SUPPORTS.get(i).get_d3();
       }
        
        return D;
    }  
    public static double[][] D_Solved(double[][] deflictionMatrix) throws IOException{
        ArrayList<String[]> jointsData = new ArrayList<>();
        ArrayList<String[]> membersData = new ArrayList<>();
        
        jointsData = Text_Reader("joints.txt");
        membersData = Text_Reader("members.txt");
        
        int numberOfJoint = jointsData.size();
        int numberOfMember = membersData.size();
        
        double[][] D_Solved = new double[3*numberOfJoint][1];
        
        int index = 0;
        for (int i =0 ; i<= JOINT_SUPPORTS.size()-1 ; i++){
            if(JOINT_SUPPORTS.get(i).get_d1() == 0){
                D_Solved[3*(i+1)-3][0] = 0;
            }else{
                D_Solved[3*(i+1)-3][0] = deflictionMatrix[index][0];
                index +=1;
            }
            
             if(JOINT_SUPPORTS.get(i).get_d2() == 0){
                D_Solved[3*(i+1)-2][0] = 0;
            }else{
                D_Solved[3*(i+1)-2][0] = deflictionMatrix[index][0];
                index +=1;
            }
             
              if(JOINT_SUPPORTS.get(i).get_d3() == 0){
                D_Solved[3*(i+1)-1][0] = 0;
            }else{
                D_Solved[3*(i+1)-1][0] = deflictionMatrix[index][0];
                index +=1;
            }
          
        }
        
        return D_Solved;
    } //D_Solved: D Matrix after find the unkown values so this will return a full Defliction matrix without unkowns     
    public static double[][] d(double[][] deflictionMatrix, int Beam_Index){
            int J1 = j1[Beam_Index];
            int J2 = j2[Beam_Index];
            
            double[][] d = new double[6][1];
            
            d[0][0] = deflictionMatrix[3*(J1+1)-3][0];
            d[1][0] = deflictionMatrix[3*(J1+1)-2][0];
            d[2][0] = deflictionMatrix[3*(J1+1)-1][0];

            d[3][0] = deflictionMatrix[3*(J2+1)-3][0];
            d[4][0] = deflictionMatrix[3*(J2+1)-2][0];
            d[5][0] = deflictionMatrix[3*(J2+1)-1][0];
            
            return d;
    }
    public static double[][] qf(int beam_Index) throws IOException    {
        //axial load variables
        double b;
        double a;
        double p;
        
        //Unifrom load variables
        double bb;
        double aa;
        double cc;
        double w;
        double aaa; //aaa, bbb --> for fixed -fixed calculation
        double bbb;
        
        //Momnet Variables
        double m;
        double am;
        double bm;

        
        
        //Variables..........
        ArrayList<String[]> jointsData = new ArrayList<String[]>();
        ArrayList<String[]> membersData = new ArrayList<String[]>();
        int number_of_joints;
        int number_of_members;
        double x1,y1,z1,x2,y2,z2;
        double[][] local_loads = new double[6][1];//size 1x6 but the indixes will be 5x1
        double[][] major_loads = new double[6][1];
        double[][] TraM = new double[6][6]; //Transformation Matrix
        double cx1,cy1,cx2,cy2;
        double length,sinTheta,cosTheta;
        String TR1,TR2; //Type of Support 1 and 2
        
        jointsData = Text_Reader("joints.txt");
        membersData = Text_Reader("members.txt");

        number_of_joints = jointsData.size();
        number_of_members = membersData.size();
        double[][] QF = new double[6][1];
        //******************************************************************************//
        //for (int i=0; i<=number_of_members - 1 ; i++){
            
            double x1_calculations = 0; //These all next variables are for calculate @ each point then use it for accumulative
            double y1_calculations = 0;
            double z1_calculations = 0;
            double x2_calculations = 0;
            double y2_calculations = 0;
            double z2_calculations = 0;

            int i = beam_Index;
            cx1 = x[j1[i]];
            cy1 = y[j1[i]];
            cx2 = x[j2[i]];
            cy2 = y[j2[i]];
            
            x1 = 0;
            y1 = 0;
            z1 = 0;
            x2 = 0;
            y2 = 0;
            z2 = 0;
            
            length = Math.pow(Math.pow(cx2-cx1, 2)+Math.pow(cy2-cy1, 2), 0.5);
            
            //Using Member Method
            //TR1 = SUPPORTS.get(i).getLeftSupport();
            //TR2 = SUPPORTS.get(i).getRightSupport();
            
            //Using Joint Method
            TR1 = JOINT_SUPPORTS.get(FRAM_MEMBER.get(i).getFirstJointNumber()).getSupportType();
            TR2 = JOINT_SUPPORTS.get(FRAM_MEMBER.get(i).getSecondJointNumber()).getSupportType();
            
            
            sinTheta = (cy2 - cy1)/length;
            cosTheta = (cx2 - cx1)/length;
            
           
                //1) Fixed - Hinge
                if((TR1 == "xyz" && TR2 == "xy") || (TR1 == "xyz" && TR2 == "y") || (TR1 == "ixyz" && TR2 == "xy") ||(TR1 == "ixyz" && TR2 == "y")){
                //xyz = fixed , xy = hinge , y = roller
                    //Axial Loads Anylysis
                    for (int j = 0 ; j<= AXIAL_LOADS.get(i).getSize() - 1; j++){
                
                         a = AXIAL_LOADS.get(i).getLocation(j);
                         b = length - a;
                         p = AXIAL_LOADS.get(i).getLoads(j);
               
                         z1_calculations= (-p*a*b*(length+b))/(2*Math.pow(length, 2));
                         y1_calculations = (z1_calculations - p*b)/length;
                         y2_calculations = -y1_calculations - p;
                         
                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;

                    }
                      //Uniform Loads Anylysis
                      for (int j = 0 ; j<= UNIFORM_LOADS.get(i).getSize() - 1; j++){
                         aa = UNIFORM_LOADS.get(i).getStartLocation(j);
                         bb = UNIFORM_LOADS.get(i).getUniformLoadLength(j);
                         w = UNIFORM_LOADS.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                         /////////////////////////////Here ................................................................
                         y2_calculations = (-w/(8*Math.pow(length, 3)))*(7*Math.pow(aa, 4) - 24*Math.pow(aa, 3)*bb - 4*Math.pow(aa, 3)*length + 12*Math.pow(aa, 2)*Math.pow(bb, 2) + 24*Math.pow(aa, 2)*bb*length - 4*aa*Math.pow(bb, 3) - 1*Math.pow(bb, 4) + 4*Math.pow(bb, 3)*length);
                         y1_calculations = -y2_calculations - w*bb;
                         z1_calculations = y1_calculations*length + w*bb*(bb/2 + cc);

                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= MOMENT_LOADS.get(i).getSize() - 1; j++){
                         
                          m =MOMENT_LOADS.get(i).getMoments(j);
                          am = MOMENT_LOADS.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                         z1_calculations = -m*(1 - (3*am/length) + (1.5*Math.pow(am, 2)/Math.pow(length, 2)));
                         y1_calculations = (z1_calculations + m) /length ;
                         y2_calculations = -y1_calculations;
                        
                         x1_calculations = 0;
                         x2_calculations = 0;
                         z2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }
                }
                
                //2) Hinge - Fixed
                if((TR1 == "xy" && TR2 == "xyz") || (TR1 == "y" && TR2 == "xyz") || (TR1 == "xy" && TR2 == "ixyz") || (TR1 == "y" && TR2 == "ixyz")){
                //xyz = fixed , xy = hinge , y = roller
                //Axial Loads Anylysis
                    for (int j = 0 ; j<= AXIAL_LOADS.get(i).getSize() - 1; j++){
                
                         a = AXIAL_LOADS.get(i).getLocation(j);
                         b = length - a;
                         p = AXIAL_LOADS.get(i).getLoads(j);
               
                         z2_calculations= (p*a*b*(length+a))/(2*Math.pow(length, 2));
                         y1_calculations = (z2_calculations - p*b)/length;
                         y2_calculations = -y1_calculations - p;
                         
                         z1_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= UNIFORM_LOADS.get(i).getSize() - 1; j++){
                
                         aa = UNIFORM_LOADS.get(i).getStartLocation(j);
                         bb = UNIFORM_LOADS.get(i).getUniformLoadLength(j);
                         w = UNIFORM_LOADS.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                         /////////////////////////////Here ................................................................
                         y1_calculations = (-w/(8*Math.pow(length, 3)))*(7*Math.pow(cc, 4) - 24*Math.pow(cc, 3)*bb - 4*Math.pow(cc, 3)*length + 12*Math.pow(cc, 2)*Math.pow(bb, 2) + 24*Math.pow(cc, 2)*bb*length - 4*cc*Math.pow(bb, 3) - 1*Math.pow(bb, 4) + 4*Math.pow(bb, 3)*length);
                         y2_calculations = -y1_calculations - w*bb;
                         z2_calculations = y1_calculations*length + w*bb*(bb/2 + cc);

                         z1_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                      
                     //Moment Loads Anylysis
                      for (int j = 0 ; j<= MOMENT_LOADS.get(i).getSize() - 1; j++){
                         
                          m =MOMENT_LOADS.get(i).getMoments(j);
                          am = MOMENT_LOADS.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                         z2_calculations = -m*(1 - (3*bm/length) + (1.5*Math.pow(bm, 2)/Math.pow(length, 2)));
                         y1_calculations = (z2_calculations + m) /length ;
                         y2_calculations = -y1_calculations;
                        
                         x1_calculations = 0;
                         x2_calculations = 0;
                         z1_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                //3) Fixed - Fixed
                if((TR1 == "xyz" && TR2 == "xyz") || (TR1 == "ixyz" && TR2 == "ixyz") || (TR1 == "ixyz" && TR2 == "xyz") || (TR1 == "xyz" && TR2 == "ixyz")){
                //xyz = fixed , xy = hinge , y = roller
                    for (int j = 0 ; j<= AXIAL_LOADS.get(i).getSize() - 1; j++){
                
                         a = AXIAL_LOADS.get(i).getLocation(j);
                         b = length - a;
                         p = AXIAL_LOADS.get(i).getLoads(j);
               
                         z1_calculations= (-p*a*Math.pow(b, 2))/(Math.pow(length, 2));
                         z2_calculations= (p*b*Math.pow(a, 2))/(Math.pow(length, 2));
                         y1_calculations = (z1_calculations+z2_calculations-(p*b))/length;
                         y2_calculations = -y1_calculations-p;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                      //Uniform Loads Anylysis
                      for (int j = 0 ; j<= UNIFORM_LOADS.get(i).getSize() - 1; j++){
                         aa = UNIFORM_LOADS.get(i).getStartLocation(j);
                         bb = UNIFORM_LOADS.get(i).getUniformLoadLength(j);
                         w = UNIFORM_LOADS.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                         aaa = aa + bb/2;
                         bbb = cc + bb/2;
                         /////////////////////////////Here ................................................................
                         z1_calculations = ((-w*bb)/Math.pow(length, 2))*(aaa*Math.pow(bbb, 2) + (((aaa-2*bbb)*Math.pow(bb, 2))/12));
                         z2_calculations = ((w*bb)/Math.pow(length, 2))*(bbb*Math.pow(aaa, 2) + (((bbb-2*aaa)*Math.pow(bb, 2))/12));
                         y1_calculations = (-w*bb/Math.pow(length, 3))*(((2*aaa+length)*Math.pow(bbb, 2))+(((aaa-bbb)/4)*Math.pow(bb, 2)));
                         y2_calculations = (-w*bb/Math.pow(length, 3))*(((2*bbb+length)*Math.pow(aaa, 2))+(((aaa-bbb)/4)*Math.pow(bb, 2)));
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                        //Moment Loads Anylysis
                      for (int j = 0 ; j<= MOMENT_LOADS.get(i).getSize() - 1; j++){
                         
                          m =MOMENT_LOADS.get(i).getMoments(j);
                          am = MOMENT_LOADS.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                         z1_calculations = -m*bm*(bm - 2*am) / Math.pow(length, 2);
                         z2_calculations =  m*bm*(2*bm - am) / Math.pow(length, 2);
                         y1_calculations = (z1_calculations+z2_calculations+m) / length;
                         y2_calculations = -y1_calculations;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                //4) Hinge - Roller or Roller - Hinge or Hinge - Hinge
                if((TR1 == "xy" && TR2 == "y") || (TR1 == "y" && TR2 == "xy") || (TR1 == "xy" && TR2 == "xy")){
                //xyz = fixed , xy = hinge , y = roller
                    for (int j = 0 ; j<= AXIAL_LOADS.get(i).getSize() - 1; j++){
                
                         a = AXIAL_LOADS.get(i).getLocation(j);
                         b = length - a;
                         p = AXIAL_LOADS.get(i).getLoads(j);
               
                         
                         y1_calculations = -p*b/length;
                         y2_calculations = -p*a/length;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         z1_calculations = 0;
                         z2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= UNIFORM_LOADS.get(i).getSize() - 1; j++){
                         aa = UNIFORM_LOADS.get(i).getStartLocation(j);
                         bb = UNIFORM_LOADS.get(i).getUniformLoadLength(j);
                         w = UNIFORM_LOADS.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                        
                         /////////////////////////////Here ................................................................
                        
                         y1_calculations = -w*bb*(bb/2+cc)/length;
                         y2_calculations = -y1_calculations - w*bb;
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         z1_calculations = 0;
                         z2_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= MOMENT_LOADS.get(i).getSize() - 1; j++){
                         
                          m =MOMENT_LOADS.get(i).getMoments(j);
                          am = MOMENT_LOADS.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                        
                         y1_calculations = m / length;
                         y2_calculations = -y1_calculations;
                         
                         z1_calculations = 0;
                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }   
                
                //5) Fixed - Free
                if((TR1 == "xyz" && TR2 == "free") || (TR1 == "ixyz" && TR2 == "free")){
                //xyz = fixed , xy = hinge , y = roller, free = free
                    for (int j = 0 ; j<= AXIAL_LOADS.get(i).getSize() - 1; j++){
                
                     
                         a = AXIAL_LOADS.get(i).getLocation(j);
                         b = length - a;
                         p = AXIAL_LOADS.get(i).getLoads(j);
               
                         
                         y1_calculations = -p;
                         z1_calculations = -p*a;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         y2_calculations = 0;
                         z2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= UNIFORM_LOADS.get(i).getSize() - 1; j++){
                         aa = UNIFORM_LOADS.get(i).getStartLocation(j);
                         bb = UNIFORM_LOADS.get(i).getUniformLoadLength(j);
                         w = UNIFORM_LOADS.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                        
                         /////////////////////////////Here ................................................................
                        
                         y1_calculations = -w*bb;
                         z1_calculations = -w*bb*(bb/2+aa);
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         y2_calculations = 0;
                         z2_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= MOMENT_LOADS.get(i).getSize() - 1; j++){
                         
                          m =MOMENT_LOADS.get(i).getMoments(j);
                          am = MOMENT_LOADS.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                        
                         z1_calculations = -m;
                         
                         y1_calculations = 0;
                         y2_calculations = 0;
                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                //6) Free - Fixed
                if((TR1 == "free" && TR2 == "xyz") || (TR1 == "free" && TR2 == "ixyz")){
                //xyz = fixed , xy = hinge , y = roller, free = free
                    for (int j = 0 ; j<= AXIAL_LOADS.get(i).getSize() - 1; j++){
                
                     
                         a = AXIAL_LOADS.get(i).getLocation(j);
                         b = length - a;
                         p = AXIAL_LOADS.get(i).getLoads(j);
               
                         
                         y2_calculations = -p;
                         z2_calculations = p*a;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         y1_calculations = 0;
                         z1_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= UNIFORM_LOADS.get(i).getSize() - 1; j++){
                         aa = UNIFORM_LOADS.get(i).getStartLocation(j);
                         bb = UNIFORM_LOADS.get(i).getUniformLoadLength(j);
                         w = UNIFORM_LOADS.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                        
                         /////////////////////////////Here ................................................................
                        
                         y2_calculations = -w*bb;
                         z2_calculations = w*bb*(bb/2+cc);
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         y1_calculations = 0;
                         z1_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= MOMENT_LOADS.get(i).getSize() - 1; j++){
                         
                          m =MOMENT_LOADS.get(i).getMoments(j);
                          am = MOMENT_LOADS.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                        
                         z2_calculations = -m;
                         
                         y1_calculations = 0;
                         y2_calculations = 0;
                         z1_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                //System.out.println("x1="+x1+"  y1="+y1+"  z1="+z1+"  x2="+x2+"  y2="+y2+"  z2="+z2);

                //After we calculated the local reactions, now we need to convert it to Major X-Y Coordinates....
                //#1--Make the Transformation Matrix
                TraM[0][0] = cosTheta ; TraM[0][1]= -sinTheta ; TraM[0][2] = 0 ; TraM[0][3] =    0     ; TraM[0][4] =     0      ; TraM[0][5] = 0 ;
                TraM[1][0] = sinTheta ; TraM[1][1]=  cosTheta ; TraM[1][2] = 0 ; TraM[1][3] =    0     ; TraM[1][4] =     0      ; TraM[1][5] = 0 ; 
                TraM[2][0] =    0     ; TraM[2][1]=      0    ; TraM[2][2] = 1 ; TraM[2][3] =    0     ; TraM[2][4] =     0      ; TraM[2][5] = 0 ; 
                TraM[3][0] =    0     ; TraM[3][1]=      0    ; TraM[3][2] = 0 ; TraM[3][3] = cosTheta ; TraM[3][4] = -sinTheta  ; TraM[3][5] = 0 ;
                TraM[4][0] =    0     ; TraM[4][1]=      0    ; TraM[4][2] = 0 ; TraM[4][3] = sinTheta ; TraM[4][4] = cosTheta   ; TraM[4][5] = 0 ; 
                TraM[5][0] =    0     ; TraM[5][1]=      0    ; TraM[5][2] = 0 ; TraM[5][3] =   0      ; TraM[5][4] =     0      ; TraM[5][5] = 1 ; 

                //#2--Make the local reactions matrix
                local_loads[0][0] = x1;
                local_loads[1][0] = y1;
                local_loads[2][0] = z1;
                local_loads[3][0] = x2;
                local_loads[4][0] = y2;
                local_loads[5][0] = z2;
                
                //#3--Multiply transformation matrix by local to convert the local to major
                major_loads = MultiplayMatrix(TraM,local_loads);
                
                //#4--Combine the QF Materix that we should need to solve the frame stifness Matrix...
                QF[0][0] += major_loads[0][0];
                QF[1][0] += major_loads[1][0];
                QF[2][0] += major_loads[2][0];
                QF[3][0] += major_loads[3][0];
                QF[4][0] += major_loads[4][0];
                QF[5][0] += major_loads[5][0];

                //PrintMatrix(QF);
              
           return QF;     
    }
    public static double[][] k_local(int beam_Index) throws IOException{
    // Retrieve All data and calculate the size of K
        ArrayList<String[]> jointsData = new ArrayList<>();
        ArrayList<String[]> membersData = new ArrayList<>();
        
        jointsData = Text_Reader("joints.txt");
        membersData = Text_Reader("members.txt");
        
       
        
        double[][] K = new double[6][6];
       
        int i = beam_Index;
          
            double E = FRAM_MEMBER.get(i).getE() * 1E+6;
            double I = FRAM_MEMBER.get(i).getI() * 1E-12;
            double A = FRAM_MEMBER.get(i).getA() * 1E-6;
            double ly = FRAM_MEMBER.get(i).getLamda_y();
            double lx = FRAM_MEMBER.get(i).getLamda_x();
            double L = FRAM_MEMBER.get(i).getLength();
            
            int Ui = 0; //0
            int Vi = 1; //1
            int Mi = 2; //2
            
            int Uj = 3; //3
            int Vj = 4; //4
            int Mj = 5; //5 
                       
            //System.out.println(Ui+""+Vi+""+Mi+""+Uj+""+Vj+""+Mj);

            //Build the K Matrix/....
            K[Ui][Ui] +=  (A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2);
            K[Ui][Vi] +=  ((A*E/L)-(12*E*I/Math.pow(L, 3))) * lx * ly;
            K[Ui][Mi] +=  (-6*E*I/Math.pow(L, 2))*ly;
            K[Ui][Uj] += -((A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2));
            K[Ui][Vj] += -((A*E/L)*lx*ly - (12*E*I/Math.pow(L, 3))*ly*lx);
            K[Ui][Mj] +=  (-6*E*I/Math.pow(L, 2))*ly;
           
            K[Vi][Ui] +=  ((A*E/L) - (12*E*I/Math.pow(L, 3)))*lx*ly;
            K[Vi][Vi] +=  (A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2);
            K[Vi][Mi] +=  (6*E*I/Math.pow(L, 2))*lx;
            K[Vi][Uj] += -((A*E/L)*ly*lx - (12*E*I/Math.pow(L, 3))*lx*ly);
            K[Vi][Vj] += -((A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2));
            K[Vi][Mj] +=  (6*E*I/Math.pow(L, 2))*lx;
           
            K[Mi][Ui] += (-6*E*I/Math.pow(L, 2))*ly;
            K[Mi][Vi] += (6*E*I/Math.pow(L, 2))*lx;
            K[Mi][Mi] += (4*E*I)/L;
            K[Mi][Uj] += (6*E*I/Math.pow(L, 2))*ly;
            K[Mi][Vj] += (-6*E*I/Math.pow(L, 2))*lx;
            K[Mi][Mj] += (2*E*I)/L;
            
            K[Uj][Ui] += -((A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2));
            K[Uj][Vi] += -((A*E/L)*lx*ly - (12*E*I/Math.pow(L, 3))*ly*lx);
            K[Uj][Mi] +=  (6*E*I/Math.pow(L, 2))*ly;
            K[Uj][Uj] +=  (A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2);
            K[Uj][Vj] +=  ((A*E/L)-(12*E*I/Math.pow(L, 3))) * lx * ly;
            K[Uj][Mj] +=  (6*E*I/Math.pow(L, 2))*ly;
            
            K[Vj][Ui] += -((A*E/L)*lx*ly - (12*E*I/Math.pow(L, 3))*ly*lx);
            K[Vj][Vi] += -((A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2));
            K[Vj][Mi] +=  (-6*E*I/Math.pow(L, 2))*lx;
            K[Vj][Uj] +=  ((A*E/L)-(12*E*I/Math.pow(L, 3))) * lx * ly;
            K[Vj][Vj] +=  (A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2);
            K[Vj][Mj] +=  (-6*E*I/Math.pow(L, 2))*lx;
            
            K[Mj][Ui] +=  (-6*E*I/Math.pow(L, 2))*ly;
            K[Mj][Vi] +=  (6*E*I/Math.pow(L, 2))*lx;
            K[Mj][Mi] +=  (2*E*I)/L;
            K[Mj][Uj] +=  (6*E*I/Math.pow(L, 2))*ly;
            K[Mj][Vj] +=  (-6*E*I/Math.pow(L, 2))*lx;
            K[Mj][Mj] +=  (4*E*I)/L;
            
          
       
        return K;
    }   
    public static ArrayList<Integer> BoundryConition() throws IOException{
        ArrayList<String[]> jointsData = new ArrayList<>();
        ArrayList<String[]> membersData = new ArrayList<>();
        
        jointsData = Text_Reader("joints.txt");
        membersData = Text_Reader("members.txt");
        
        int numberOfJoint = jointsData.size();
        int numberOfMember = membersData.size();
        
       ArrayList<Integer> boundryConition = new ArrayList<>();
        
       for (int i =0 ; i<= JOINT_SUPPORTS.size()-1 ; i++){
           if(JOINT_SUPPORTS.get(i).get_d1() == 0 ){
               boundryConition.add(3*(i+1)-3);
           }
           if(JOINT_SUPPORTS.get(i).get_d2() == 0){
               boundryConition.add(3*(i+1)-2);
           }
           if(JOINT_SUPPORTS.get(i).get_d3() == 0){
               boundryConition.add(3*(i+1)-1);
           }
       }
        
        return boundryConition;
    }  
    public static void AnalysisANDprint() throws IOException{
            //--4--Boubdry Condition
        ArrayList<Integer> BC = new ArrayList<>();
        BC = BoundryConition();
        
        
        //Print Matrices and after that we can do the calculations
        PrintMatrix(Q(),"0.00");
        PrintMatrix(K(),"0.00");
        PrintMatrix(QF(),"0.00"); 
        PrintMatrix(D(),"0.00"); 

        //Print Matrix After eliminate and applied the boundry condition
        PrintMatrix(reduceMatrix(Q(),BC,"raw"),"0.00");  
        PrintMatrix(reduceMatrix(K(),BC,"both"),"0.00"); 
        PrintMatrix(reduceMatrix(D(),BC,"raw"),"0.00");
        PrintMatrix(reduceMatrix(QF(),BC,"raw"),"0.00"); 


        //---------Anylysis and solve--------------------
        double[][] Q  = reduceMatrix(Q(),BC,"raw");
        double[][] K  = reduceMatrix(K(),BC,"both");
        double[][] D  = reduceMatrix(D(),BC,"raw");
        double[][] QF = reduceMatrix(QF(),BC,"raw");
       
       // [Q] = [K][D]+[QF]
       // [K]^-1*[Q]-[QF] = [D]
       //--1--Solve For Defliction
       double[][] defliction =MultiplayMatrix(MatrixInverse(K),Matrix1MinusMatrix2(Q,QF)) ;
       PrintMatrix(defliction,"0.000000000"); 
      
        
       //--2--Combine the deflictions and get a full matrix of defliction wihout unkowns
       PrintMatrix(D_Solved(defliction),"0.000000000"); 
       double[][] D_Solved = D_Solved(defliction); //Get the full Matrix of defliction without unkowns
      
       
       //--3-- find the reaction of beams
       double[][] Beam_0_defliction = d(D_Solved,0);//Get the defliction Matrix of beam (0) first beam
       PrintMatrix(Beam_0_defliction,"0.000000000"); 
       
       double[][] Beam_0_qf = qf(0);//Get the qf Matrix of beam (0) first beam
       PrintMatrix(Beam_0_qf,"0.000000000"); 
 
       double[][] Beam_0_localK = k_local(0);//Get the qf Matrix of beam (0) first beam
       PrintMatrix(Beam_0_localK,"0.00"); 
       
       double[][] Beam_0_Reactions = Matrix1PlusMatrix2(MultiplayMatrix(Beam_0_localK,Beam_0_defliction),Beam_0_qf);//Get the qf Matrix of beam (0) first beam
       PrintMatrix(Beam_0_Reactions,"0.00"); 
               
    }
    
       
    
    public static double[][] QF_FINITE() throws IOException    {
        //axial load variables
        double b;
        double a;
        double p;
        
        //Unifrom load variables
        double bb;
        double aa;
        double cc;
        double w;
        double aaa; //aaa, bbb --> for fixed -fixed calculation
        double bbb;
        
        //Momnet Variables
        double m;
        double am;
        double bm;

        
        
        //Variables..........
                int number_of_joints;
        int number_of_members;
        double x1,y1,z1,x2,y2,z2;
        double[][] local_loads = new double[6][1];//size 1x6 but the indixes will be 5x1
        double[][] major_loads = new double[6][1];
        double[][] TraM = new double[6][6]; //Transformation Matrix
        double cx1,cy1,cx2,cy2;
        double length,sinTheta,cosTheta;
        String TR1,TR2; //Type of Support 1 and 2
        
      
        number_of_joints =  x_finite.size();
        number_of_members = j1_finite.size();
        double[][] QF = new double[3*number_of_joints][1];
        //******************************************************************************//
        for (int i=0; i<=number_of_members - 1 ; i++){
            
            double x1_calculations = 0; //These all next variables are for calculate @ each point then use it for accumulative
            double y1_calculations = 0;
            double z1_calculations = 0;
            double x2_calculations = 0;
            double y2_calculations = 0;
            double z2_calculations = 0;

            
            cx1 = x_finite.get(j1_finite.get(i));
            cy1 = y_finite.get(j1_finite.get(i));
            cx2 = x_finite.get(j2_finite.get(i));
            cy2 = y_finite.get(j2_finite.get(i));
            
            x1 = 0;
            y1 = 0;
            z1 = 0;
            x2 = 0;
            y2 = 0;
            z2 = 0;
            
            length = Math.pow(Math.pow(cx2-cx1, 2)+Math.pow(cy2-cy1, 2), 0.5);
            
            //Using Member Method
            //TR1 = SUPPORTS.get(i).getLeftSupport();
            //TR2 = SUPPORTS.get(i).getRightSupport();
            
            //Using Joint Method
            TR1 = joints_supports.get(fram_member.get(i).getFirstJointNumber()).getSupportType();
            TR2 = joints_supports.get(fram_member.get(i).getSecondJointNumber()).getSupportType();
            
            
            sinTheta = (cy2 - cy1)/length;
            cosTheta = (cx2 - cx1)/length;
            
           
                //1) Fixed - Hinge
                if((TR1 == "xyz" && TR2 == "xy") || (TR1 == "xyz" && TR2 == "y") || (TR1 == "ixyz" && TR2 == "xy") ||(TR1 == "ixyz" && TR2 == "y")){
                //xyz = fixed , xy = hinge , y = roller
                    //Axial Loads Anylysis
                    for (int j = 0 ; j<= axial_loads.get(i).getSize() - 1; j++){
                
                         a = axial_loads.get(i).getLocation(j);
                         b = length - a;
                         p = axial_loads.get(i).getLoads(j);
               
                         z1_calculations= (-p*a*b*(length+b))/(2*Math.pow(length, 2));
                         y1_calculations = (z1_calculations - p*b)/length;
                         y2_calculations = -y1_calculations - p;
                         
                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;

                    }
                      //Uniform Loads Anylysis
                      for (int j = 0 ; j<= uniform_loads.get(i).getSize() - 1; j++){
                         aa = uniform_loads.get(i).getStartLocation(j);
                         bb = uniform_loads.get(i).getUniformLoadLength(j);
                         w = uniform_loads.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                         /////////////////////////////Here ................................................................
                         y2_calculations = (-w/(8*Math.pow(length, 3)))*(7*Math.pow(aa, 4) - 24*Math.pow(aa, 3)*bb - 4*Math.pow(aa, 3)*length + 12*Math.pow(aa, 2)*Math.pow(bb, 2) + 24*Math.pow(aa, 2)*bb*length - 4*aa*Math.pow(bb, 3) - 1*Math.pow(bb, 4) + 4*Math.pow(bb, 3)*length);
                         y1_calculations = -y2_calculations - w*bb;
                         z1_calculations = y1_calculations*length + w*bb*(bb/2 + cc);

                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= moment_load.get(i).getSize() - 1; j++){
                         
                          m =moment_load.get(i).getMoments(j);
                          am = moment_load.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                         z1_calculations = -m*(1 - (3*am/length) + (1.5*Math.pow(am, 2)/Math.pow(length, 2)));
                         y1_calculations = (z1_calculations + m) /length ;
                         y2_calculations = -y1_calculations;
                        
                         x1_calculations = 0;
                         x2_calculations = 0;
                         z2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }
                }
                
                //2) Hinge - Fixed
                if((TR1 == "xy" && TR2 == "xyz") || (TR1 == "y" && TR2 == "xyz") || (TR1 == "xy" && TR2 == "ixyz") || (TR1 == "y" && TR2 == "ixyz")){
                //xyz = fixed , xy = hinge , y = roller
                //Axial Loads Anylysis
                    for (int j = 0 ; j<= axial_loads.get(i).getSize() - 1; j++){
                
                         a = axial_loads.get(i).getLocation(j);
                         b = length - a;
                         p = axial_loads.get(i).getLoads(j);
               
                         z2_calculations= (p*a*b*(length+a))/(2*Math.pow(length, 2));
                         y1_calculations = (z2_calculations - p*b)/length;
                         y2_calculations = -y1_calculations - p;
                         
                         z1_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= uniform_loads.get(i).getSize() - 1; j++){
                
                         aa = uniform_loads.get(i).getStartLocation(j);
                         bb = uniform_loads.get(i).getUniformLoadLength(j);
                         w = uniform_loads.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                         /////////////////////////////Here ................................................................
                         y1_calculations = (-w/(8*Math.pow(length, 3)))*(7*Math.pow(cc, 4) - 24*Math.pow(cc, 3)*bb - 4*Math.pow(cc, 3)*length + 12*Math.pow(cc, 2)*Math.pow(bb, 2) + 24*Math.pow(cc, 2)*bb*length - 4*cc*Math.pow(bb, 3) - 1*Math.pow(bb, 4) + 4*Math.pow(bb, 3)*length);
                         y2_calculations = -y1_calculations - w*bb;
                         z2_calculations = y1_calculations*length + w*bb*(bb/2 + cc);

                         z1_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                      
                     //Moment Loads Anylysis
                      for (int j = 0 ; j<= moment_load.get(i).getSize() - 1; j++){
                         
                          m =moment_load.get(i).getMoments(j);
                          am = moment_load.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                         z2_calculations = -m*(1 - (3*bm/length) + (1.5*Math.pow(bm, 2)/Math.pow(length, 2)));
                         y1_calculations = (z2_calculations + m) /length ;
                         y2_calculations = -y1_calculations;
                        
                         x1_calculations = 0;
                         x2_calculations = 0;
                         z1_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                //3) Fixed - Fixed
                if((TR1 == "xyz" && TR2 == "xyz") || (TR1 == "ixyz" && TR2 == "ixyz") || (TR1 == "ixyz" && TR2 == "xyz") || (TR1 == "xyz" && TR2 == "ixyz")){
                //xyz = fixed , xy = hinge , y = roller
                    for (int j = 0 ; j<= axial_loads.get(i).getSize() - 1; j++){
                
                         a = axial_loads.get(i).getLocation(j);
                         b = length - a;
                         p = axial_loads.get(i).getLoads(j);
               
                         z1_calculations= (-p*a*Math.pow(b, 2))/(Math.pow(length, 2));
                         z2_calculations= (p*b*Math.pow(a, 2))/(Math.pow(length, 2));
                         y1_calculations = (z1_calculations+z2_calculations-(p*b))/length;
                         y2_calculations = -y1_calculations-p;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                      //Uniform Loads Anylysis
                      for (int j = 0 ; j<= uniform_loads.get(i).getSize() - 1; j++){
                         aa = uniform_loads.get(i).getStartLocation(j);
                         bb = uniform_loads.get(i).getUniformLoadLength(j);
                         w = uniform_loads.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                         aaa = aa + bb/2;
                         bbb = cc + bb/2;
                         /////////////////////////////Here ................................................................
                         z1_calculations = ((-w*bb)/Math.pow(length, 2))*(aaa*Math.pow(bbb, 2) + (((aaa-2*bbb)*Math.pow(bb, 2))/12));
                         z2_calculations = ((w*bb)/Math.pow(length, 2))*(bbb*Math.pow(aaa, 2) + (((bbb-2*aaa)*Math.pow(bb, 2))/12));
                         y1_calculations = (-w*bb/Math.pow(length, 3))*(((2*aaa+length)*Math.pow(bbb, 2))+(((aaa-bbb)/4)*Math.pow(bb, 2)));
                         y2_calculations = (-w*bb/Math.pow(length, 3))*(((2*bbb+length)*Math.pow(aaa, 2))+(((aaa-bbb)/4)*Math.pow(bb, 2)));
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                        //Moment Loads Anylysis
                      for (int j = 0 ; j<= moment_load.get(i).getSize() - 1; j++){
                         
                          m =moment_load.get(i).getMoments(j);
                          am = moment_load.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                         z1_calculations = -m*bm*(bm - 2*am) / Math.pow(length, 2);
                         z2_calculations =  m*bm*(2*bm - am) / Math.pow(length, 2);
                         y1_calculations = (z1_calculations+z2_calculations+m) / length;
                         y2_calculations = -y1_calculations;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                     //4) Hinge - Roller or Roller - Hinge or Hinge - Hinge
                if((TR1 == "xy" && TR2 == "y") || (TR1 == "y" && TR2 == "xy") || (TR1 == "xy" && TR2 == "xy")){
                //xyz = fixed , xy = hinge , y = roller
                    for (int j = 0 ; j<= axial_loads.get(i).getSize() - 1; j++){
                
                         a = axial_loads.get(i).getLocation(j);
                         b = length - a;
                         p = axial_loads.get(i).getLoads(j);
               
                         
                         y1_calculations = -p*b/length;
                         y2_calculations = -p*a/length;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         z1_calculations = 0;
                         z2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= uniform_loads.get(i).getSize() - 1; j++){
                         aa = uniform_loads.get(i).getStartLocation(j);
                         bb = uniform_loads.get(i).getUniformLoadLength(j);
                         w = uniform_loads.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                        
                         /////////////////////////////Here ................................................................
                        
                         y1_calculations = -w*bb*(bb/2+cc)/length;
                         y2_calculations = -y1_calculations - w*bb;
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         z1_calculations = 0;
                         z2_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= moment_load.get(i).getSize() - 1; j++){
                         
                          m =moment_load.get(i).getMoments(j);
                          am = moment_load.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                        
                         y1_calculations = m / length;
                         y2_calculations = -y1_calculations;
                         
                         z1_calculations = 0;
                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                //5) Fixed - Free
                if((TR1 == "xyz" && TR2 == "free") || (TR1 == "ixyz" && TR2 == "free")){
                //xyz = fixed , xy = hinge , y = roller, free = free
                    for (int j = 0 ; j<= axial_loads.get(i).getSize() - 1; j++){
                
                     
                         a = axial_loads.get(i).getLocation(j);
                         b = length - a;
                         p = axial_loads.get(i).getLoads(j);
               
                         
                         y1_calculations = -p;
                         z1_calculations = -p*a;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         y2_calculations = 0;
                         z2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= uniform_loads.get(i).getSize() - 1; j++){
                         aa = uniform_loads.get(i).getStartLocation(j);
                         bb = uniform_loads.get(i).getUniformLoadLength(j);
                         w = uniform_loads.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                        
                         /////////////////////////////Here ................................................................
                        
                         y1_calculations = -w*bb;
                         z1_calculations = -w*bb*(bb/2+aa);
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         y2_calculations = 0;
                         z2_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= moment_load.get(i).getSize() - 1; j++){
                         
                          m =moment_load.get(i).getMoments(j);
                          am = moment_load.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                        
                         z1_calculations = -m;
                         
                         y1_calculations = 0;
                         y2_calculations = 0;
                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                     //6) Free - Fixed
                if((TR1 == "free" && TR2 == "xyz") || (TR1 == "free" && TR2 == "ixyz")){
                //xyz = fixed , xy = hinge , y = roller, free = free
                    for (int j = 0 ; j<= axial_loads.get(i).getSize() - 1; j++){
                
                     
                         a = axial_loads.get(i).getLocation(j);
                         b = length - a;
                         p = axial_loads.get(i).getLoads(j);
               
                         
                         y2_calculations = -p;
                         z2_calculations = p*a;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         y1_calculations = 0;
                         z1_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= uniform_loads.get(i).getSize() - 1; j++){
                         aa = uniform_loads.get(i).getStartLocation(j);
                         bb = uniform_loads.get(i).getUniformLoadLength(j);
                         w = uniform_loads.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                        
                         /////////////////////////////Here ................................................................
                        
                         y2_calculations = -w*bb;
                         z2_calculations = w*bb*(bb/2+cc);
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         y1_calculations = 0;
                         z1_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= moment_load.get(i).getSize() - 1; j++){
                         
                          m =moment_load.get(i).getMoments(j);
                          am = moment_load.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                        
                         z2_calculations = -m;
                         
                         y1_calculations = 0;
                         y2_calculations = 0;
                         z1_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                //System.out.println("x1="+x1+"  y1="+y1+"  z1="+z1+"  x2="+x2+"  y2="+y2+"  z2="+z2);

                //After we calculated the local reactions, now we need to convert it to Major X-Y Coordinates....
                //#1--Make the Transformation Matrix
                TraM[0][0] = cosTheta ; TraM[0][1]= -sinTheta ; TraM[0][2] = 0 ; TraM[0][3] =    0     ; TraM[0][4] =     0      ; TraM[0][5] = 0 ;
                TraM[1][0] = sinTheta ; TraM[1][1]=  cosTheta ; TraM[1][2] = 0 ; TraM[1][3] =    0     ; TraM[1][4] =     0      ; TraM[1][5] = 0 ; 
                TraM[2][0] =    0     ; TraM[2][1]=      0    ; TraM[2][2] = 1 ; TraM[2][3] =    0     ; TraM[2][4] =     0      ; TraM[2][5] = 0 ; 
                TraM[3][0] =    0     ; TraM[3][1]=      0    ; TraM[3][2] = 0 ; TraM[3][3] = cosTheta ; TraM[3][4] = -sinTheta  ; TraM[3][5] = 0 ;
                TraM[4][0] =    0     ; TraM[4][1]=      0    ; TraM[4][2] = 0 ; TraM[4][3] = sinTheta ; TraM[4][4] = cosTheta   ; TraM[4][5] = 0 ; 
                TraM[5][0] =    0     ; TraM[5][1]=      0    ; TraM[5][2] = 0 ; TraM[5][3] =   0      ; TraM[5][4] =     0      ; TraM[5][5] = 1 ; 

                //#2--Make the local reactions matrix
                local_loads[0][0] = x1;
                local_loads[1][0] = y1;
                local_loads[2][0] = z1;
                local_loads[3][0] = x2;
                local_loads[4][0] = y2;
                local_loads[5][0] = z2;
                
                //#3--Multiply transformation matrix by local to convert the local to major
                major_loads = MultiplayMatrix(TraM,local_loads);
                
                //#4--Combine the QF Materix that we should need to solve the frame stifness Matrix...
                QF[3*(j1_finite.get(i)+1)-3][0] += major_loads[0][0];
                QF[3*(j1_finite.get(i)+1)-2][0] += major_loads[1][0];
                QF[3*(j1_finite.get(i)+1)-1][0] += major_loads[2][0];
                QF[3*(j2_finite.get(i)+1)-3][0] += major_loads[3][0];
                QF[3*(j2_finite.get(i)+1)-2][0] += major_loads[4][0];
                QF[3*(j2_finite.get(i)+1)-1][0] += major_loads[5][0];

                //PrintMatrix(QF);
              }
           return QF;     
    } 
    public static double[][] K_FINITE() throws IOException{
    // Retrieve All data and calculate the size of K
        int numberOfJoint = x_finite.size();
        int numberOfMember = j1_finite.size();
        
        double[][] K = new double[3*numberOfJoint][3*numberOfJoint];
       
        for (int i = 0; i<= numberOfMember - 1; i++){
          
            double E = fram_member.get(i).getE() * 1E+6;
            double I = fram_member.get(i).getI() * 1E-12;
            double A = fram_member.get(i).getA() * 1E-6;
            double ly = fram_member.get(i).getLamda_y();
            double lx = fram_member.get(i).getLamda_x();
            double L = fram_member.get(i).getLength();
            int Ui = 3*(j1_finite.get(i)+1)-3; //0 
            int Vi = 3*(j1_finite.get(i)+1)-2; //1
            int Mi = 3*(j1_finite.get(i)+1)-1; //2
            
            int Uj = 3*(j2_finite.get(i)+1)-3; //3
            int Vj = 3*(j2_finite.get(i)+1)-2; //4
            int Mj = 3*(j2_finite.get(i)+1)-1; //5 
                       
            //System.out.println(Ui+""+Vi+""+Mi+""+Uj+""+Vj+""+Mj);

            //Build the K Matrix/....
            K[Ui][Ui] +=  (A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2);
            K[Ui][Vi] +=  ((A*E/L)-(12*E*I/Math.pow(L, 3))) * lx * ly;
            K[Ui][Mi] +=  (-6*E*I/Math.pow(L, 2))*ly;
            K[Ui][Uj] += -((A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2));
            K[Ui][Vj] += -((A*E/L)*lx*ly - (12*E*I/Math.pow(L, 3))*ly*lx);
            K[Ui][Mj] +=  (-6*E*I/Math.pow(L, 2))*ly;
           
            K[Vi][Ui] +=  ((A*E/L) - (12*E*I/Math.pow(L, 3)))*lx*ly;
            K[Vi][Vi] +=  (A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2);
            K[Vi][Mi] +=  (6*E*I/Math.pow(L, 2))*lx;
            K[Vi][Uj] += -((A*E/L)*ly*lx - (12*E*I/Math.pow(L, 3))*lx*ly);
            K[Vi][Vj] += -((A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2));
            K[Vi][Mj] +=  (6*E*I/Math.pow(L, 2))*lx;
           
            K[Mi][Ui] += (-6*E*I/Math.pow(L, 2))*ly;
            K[Mi][Vi] += (6*E*I/Math.pow(L, 2))*lx;
            K[Mi][Mi] += (4*E*I)/L;
            K[Mi][Uj] += (6*E*I/Math.pow(L, 2))*ly;
            K[Mi][Vj] += (-6*E*I/Math.pow(L, 2))*lx;
            K[Mi][Mj] += (2*E*I)/L;
            
            K[Uj][Ui] += -((A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2));
            K[Uj][Vi] += -((A*E/L)*lx*ly - (12*E*I/Math.pow(L, 3))*ly*lx);
            K[Uj][Mi] +=  (6*E*I/Math.pow(L, 2))*ly;
            K[Uj][Uj] +=  (A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2);
            K[Uj][Vj] +=  ((A*E/L)-(12*E*I/Math.pow(L, 3))) * lx * ly;
            K[Uj][Mj] +=  (6*E*I/Math.pow(L, 2))*ly;
            
            K[Vj][Ui] += -((A*E/L)*lx*ly - (12*E*I/Math.pow(L, 3))*ly*lx);
            K[Vj][Vi] += -((A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2));
            K[Vj][Mi] +=  (-6*E*I/Math.pow(L, 2))*lx;
            K[Vj][Uj] +=  ((A*E/L)-(12*E*I/Math.pow(L, 3))) * lx * ly;
            K[Vj][Vj] +=  (A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2);
            K[Vj][Mj] +=  (-6*E*I/Math.pow(L, 2))*lx;
            
            K[Mj][Ui] +=  (-6*E*I/Math.pow(L, 2))*ly;
            K[Mj][Vi] +=  (6*E*I/Math.pow(L, 2))*lx;
            K[Mj][Mi] +=  (2*E*I)/L;
            K[Mj][Uj] +=  (6*E*I/Math.pow(L, 2))*ly;
            K[Mj][Vj] +=  (-6*E*I/Math.pow(L, 2))*lx;
            K[Mj][Mj] +=  (4*E*I)/L;
            
        }   
       
        return K;
    }   
    public static double[][] Q_FINITE() throws IOException{
              
        int numberOfJoint = x_finite.size();
        int numberOfMember = j1_finite.size();
        
        double[][] Q = new double[3*numberOfJoint][1];
        
        for(int i=0 ; i<= numberOfJoint-1 ; i++){
           Q[3*(i+1)-3][0] =joint_loads.get(i).get_Xload();
           Q[3*(i+1)-2][0] = joint_loads.get(i).get_Yload();
           Q[3*(i+1)-1][0] = joint_loads.get(i).get_Mload();
        }
        
        return Q;
        
    }    
    public static double[][] D_FINITE() throws IOException{
              
        int numberOfJoint = x_finite.size();
        int numberOfMember = j1_finite.size();
        
        double[][] D = new double[3*numberOfJoint][1];
        
       for (int i =0 ; i<= joints_supports.size()-1 ; i++){
           D[3*(i+1)-3][0] = joints_supports.get(i).get_d1();
           D[3*(i+1)-2][0] = joints_supports.get(i).get_d2();
           D[3*(i+1)-1][0] = joints_supports.get(i).get_d3();
       }
        
        return D;
    }  
    public static double[][] D_Solved_FINITE(double[][] deflictionMatrix) throws IOException{
        int numberOfJoint = x_finite.size();
        int numberOfMember = j1_finite.size();
        
        double[][] D_Solved = new double[3*numberOfJoint][1];
        
        int index = 0;
        for (int i =0 ; i<= joints_supports.size()-1 ; i++){
            if(joints_supports.get(i).get_d1() == 0){
                D_Solved[3*(i+1)-3][0] = 0;
            }else{
                D_Solved[3*(i+1)-3][0] = deflictionMatrix[index][0];
                index +=1;
            }
            
             if(joints_supports.get(i).get_d2() == 0){
                D_Solved[3*(i+1)-2][0] = 0;
            }else{
                D_Solved[3*(i+1)-2][0] = deflictionMatrix[index][0];
                index +=1;
            }
             
              if(joints_supports.get(i).get_d3() == 0){
                D_Solved[3*(i+1)-1][0] = 0;
            }else{
                D_Solved[3*(i+1)-1][0] = deflictionMatrix[index][0];
                index +=1;
            }
          
        }
        
        return D_Solved;
    } //D_Solved: D Matrix after find the unkown values so this will return a full Defliction matrix without unkowns   
    public static double[][] d_FINITE(double[][] deflictionMatrix, int Beam_Index){
            int J1 = j1_finite.get(Beam_Index);
            int J2 = j2_finite.get(Beam_Index);
            
            double[][] d = new double[6][1];
            
            d[0][0] = deflictionMatrix[3*(J1+1)-3][0];
            d[1][0] = deflictionMatrix[3*(J1+1)-2][0];
            d[2][0] = deflictionMatrix[3*(J1+1)-1][0];

            d[3][0] = deflictionMatrix[3*(J2+1)-3][0];
            d[4][0] = deflictionMatrix[3*(J2+1)-2][0];
            d[5][0] = deflictionMatrix[3*(J2+1)-1][0];
            
            return d;
    }
    public static double[][] qf_FINITE(int beam_Index) throws IOException    {
        //axial load variables
        double b;
        double a;
        double p;
        
        //Unifrom load variables
        double bb;
        double aa;
        double cc;
        double w;
        double aaa; //aaa, bbb --> for fixed -fixed calculation
        double bbb;
        
        //Momnet Variables
        double m;
        double am;
        double bm;

        
        
        //Variables..........
      
        double x1,y1,z1,x2,y2,z2;
        double[][] local_loads = new double[6][1];//size 1x6 but the indixes will be 5x1
        double[][] major_loads = new double[6][1];
        double[][] TraM = new double[6][6]; //Transformation Matrix
        double cx1,cy1,cx2,cy2;
        double length,sinTheta,cosTheta;
        String TR1,TR2; //Type of Support 1 and 2
        
        int number_of_joints = x_finite.size();
        int number_of_members = j1_finite.size();
        double[][] QF = new double[6][1];
        //******************************************************************************//
        //for (int i=0; i<=number_of_members - 1 ; i++){
            
            double x1_calculations = 0; //These all next variables are for calculate @ each point then use it for accumulative
            double y1_calculations = 0;
            double z1_calculations = 0;
            double x2_calculations = 0;
            double y2_calculations = 0;
            double z2_calculations = 0;

            int i = beam_Index;
            cx1 = x_finite.get(j1_finite.get(i));
            cy1 = y_finite.get(j1_finite.get(i));
            cx2 = x_finite.get(j2_finite.get(i));
            cy2 = y_finite.get(j2_finite.get(i));
            
            x1 = 0;
            y1 = 0;
            z1 = 0;
            x2 = 0;
            y2 = 0;
            z2 = 0;
            
            length = Math.pow(Math.pow(cx2-cx1, 2)+Math.pow(cy2-cy1, 2), 0.5);
            
            //Using Member Method
            //TR1 = SUPPORTS.get(i).getLeftSupport();
            //TR2 = SUPPORTS.get(i).getRightSupport();
            
            //Using Joint Method
            TR1 = joints_supports.get(fram_member.get(i).getFirstJointNumber()).getSupportType();
            TR2 = joints_supports.get(fram_member.get(i).getSecondJointNumber()).getSupportType();
            
            
            sinTheta = (cy2 - cy1)/length;
            cosTheta = (cx2 - cx1)/length;
            
           
                //1) Fixed - Hinge
                if((TR1 == "xyz" && TR2 == "xy") || (TR1 == "xyz" && TR2 == "y") || (TR1 == "ixyz" && TR2 == "xy") ||(TR1 == "ixyz" && TR2 == "y")){
                //xyz = fixed , xy = hinge , y = roller
                    //Axial Loads Anylysis
                    for (int j = 0 ; j<= axial_loads.get(i).getSize() - 1; j++){
                
                         a = axial_loads.get(i).getLocation(j);
                         b = length - a;
                         p = axial_loads.get(i).getLoads(j);
               
                         z1_calculations= (-p*a*b*(length+b))/(2*Math.pow(length, 2));
                         y1_calculations = (z1_calculations - p*b)/length;
                         y2_calculations = -y1_calculations - p;
                         
                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;

                    }
                      //Uniform Loads Anylysis
                      for (int j = 0 ; j<= uniform_loads.get(i).getSize() - 1; j++){
                         aa = uniform_loads.get(i).getStartLocation(j);
                         bb = uniform_loads.get(i).getUniformLoadLength(j);
                         w = uniform_loads.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                         /////////////////////////////Here ................................................................
                         y2_calculations = (-w/(8*Math.pow(length, 3)))*(7*Math.pow(aa, 4) - 24*Math.pow(aa, 3)*bb - 4*Math.pow(aa, 3)*length + 12*Math.pow(aa, 2)*Math.pow(bb, 2) + 24*Math.pow(aa, 2)*bb*length - 4*aa*Math.pow(bb, 3) - 1*Math.pow(bb, 4) + 4*Math.pow(bb, 3)*length);
                         y1_calculations = -y2_calculations - w*bb;
                         z1_calculations = y1_calculations*length + w*bb*(bb/2 + cc);

                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= moment_load.get(i).getSize() - 1; j++){
                         
                          m =moment_load.get(i).getMoments(j);
                          am = moment_load.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                         z1_calculations = -m*(1 - (3*am/length) + (1.5*Math.pow(am, 2)/Math.pow(length, 2)));
                         y1_calculations = (z1_calculations + m) /length ;
                         y2_calculations = -y1_calculations;
                        
                         x1_calculations = 0;
                         x2_calculations = 0;
                         z2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }
                }
                
                //2) Hinge - Fixed
                if((TR1 == "xy" && TR2 == "xyz") || (TR1 == "y" && TR2 == "xyz") || (TR1 == "xy" && TR2 == "ixyz") || (TR1 == "y" && TR2 == "ixyz")){
                //xyz = fixed , xy = hinge , y = roller
                //Axial Loads Anylysis
                    for (int j = 0 ; j<= axial_loads.get(i).getSize() - 1; j++){
                
                         a = axial_loads.get(i).getLocation(j);
                         b = length - a;
                         p = axial_loads.get(i).getLoads(j);
               
                         z2_calculations= (p*a*b*(length+a))/(2*Math.pow(length, 2));
                         y1_calculations = (z2_calculations - p*b)/length;
                         y2_calculations = -y1_calculations - p;
                         
                         z1_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= uniform_loads.get(i).getSize() - 1; j++){
                
                         aa = uniform_loads.get(i).getStartLocation(j);
                         bb = uniform_loads.get(i).getUniformLoadLength(j);
                         w = uniform_loads.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                         /////////////////////////////Here ................................................................
                         y1_calculations = (-w/(8*Math.pow(length, 3)))*(7*Math.pow(cc, 4) - 24*Math.pow(cc, 3)*bb - 4*Math.pow(cc, 3)*length + 12*Math.pow(cc, 2)*Math.pow(bb, 2) + 24*Math.pow(cc, 2)*bb*length - 4*cc*Math.pow(bb, 3) - 1*Math.pow(bb, 4) + 4*Math.pow(bb, 3)*length);
                         y2_calculations = -y1_calculations - w*bb;
                         z2_calculations = y1_calculations*length + w*bb*(bb/2 + cc);

                         z1_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                      
                     //Moment Loads Anylysis
                      for (int j = 0 ; j<= moment_load.get(i).getSize() - 1; j++){
                         
                          m =moment_load.get(i).getMoments(j);
                          am = moment_load.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                         z2_calculations = -m*(1 - (3*bm/length) + (1.5*Math.pow(bm, 2)/Math.pow(length, 2)));
                         y1_calculations = (z2_calculations + m) /length ;
                         y2_calculations = -y1_calculations;
                        
                         x1_calculations = 0;
                         x2_calculations = 0;
                         z1_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                //3) Fixed - Fixed
                if((TR1 == "xyz" && TR2 == "xyz") || (TR1 == "ixyz" && TR2 == "ixyz") || (TR1 == "ixyz" && TR2 == "xyz") || (TR1 == "xyz" && TR2 == "ixyz")){
                //xyz = fixed , xy = hinge , y = roller
                    for (int j = 0 ; j<= axial_loads.get(i).getSize() - 1; j++){
                
                         a = axial_loads.get(i).getLocation(j);
                         b = length - a;
                         p = axial_loads.get(i).getLoads(j);
               
                         z1_calculations= (-p*a*Math.pow(b, 2))/(Math.pow(length, 2));
                         z2_calculations= (p*b*Math.pow(a, 2))/(Math.pow(length, 2));
                         y1_calculations = (z1_calculations+z2_calculations-(p*b))/length;
                         y2_calculations = -y1_calculations-p;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                      //Uniform Loads Anylysis
                      for (int j = 0 ; j<= uniform_loads.get(i).getSize() - 1; j++){
                         aa = uniform_loads.get(i).getStartLocation(j);
                         bb = uniform_loads.get(i).getUniformLoadLength(j);
                         w = uniform_loads.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                         aaa = aa + bb/2;
                         bbb = cc + bb/2;
                         /////////////////////////////Here ................................................................
                         z1_calculations = ((-w*bb)/Math.pow(length, 2))*(aaa*Math.pow(bbb, 2) + (((aaa-2*bbb)*Math.pow(bb, 2))/12));
                         z2_calculations = ((w*bb)/Math.pow(length, 2))*(bbb*Math.pow(aaa, 2) + (((bbb-2*aaa)*Math.pow(bb, 2))/12));
                         y1_calculations = (-w*bb/Math.pow(length, 3))*(((2*aaa+length)*Math.pow(bbb, 2))+(((aaa-bbb)/4)*Math.pow(bb, 2)));
                         y2_calculations = (-w*bb/Math.pow(length, 3))*(((2*bbb+length)*Math.pow(aaa, 2))+(((aaa-bbb)/4)*Math.pow(bb, 2)));
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                        //Moment Loads Anylysis
                      for (int j = 0 ; j<= moment_load.get(i).getSize() - 1; j++){
                         
                          m =moment_load.get(i).getMoments(j);
                          am = moment_load.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                         z1_calculations = -m*bm*(bm - 2*am) / Math.pow(length, 2);
                         z2_calculations =  m*bm*(2*bm - am) / Math.pow(length, 2);
                         y1_calculations = (z1_calculations+z2_calculations+m) / length;
                         y2_calculations = -y1_calculations;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                     //4) Hinge - Roller or Roller - Hinge or Hinge - Hinge
                if((TR1 == "xy" && TR2 == "y") || (TR1 == "y" && TR2 == "xy") || (TR1 == "xy" && TR2 == "xy")){
                //xyz = fixed , xy = hinge , y = roller
                    for (int j = 0 ; j<= axial_loads.get(i).getSize() - 1; j++){
                
                         a = axial_loads.get(i).getLocation(j);
                         b = length - a;
                         p = axial_loads.get(i).getLoads(j);
               
                         
                         y1_calculations = -p*b/length;
                         y2_calculations = -p*a/length;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         z1_calculations = 0;
                         z2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= uniform_loads.get(i).getSize() - 1; j++){
                         aa = uniform_loads.get(i).getStartLocation(j);
                         bb = uniform_loads.get(i).getUniformLoadLength(j);
                         w = uniform_loads.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                        
                         /////////////////////////////Here ................................................................
                        
                         y1_calculations = -w*bb*(bb/2+cc)/length;
                         y2_calculations = -y1_calculations - w*bb;
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         z1_calculations = 0;
                         z2_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= moment_load.get(i).getSize() - 1; j++){
                         
                          m =moment_load.get(i).getMoments(j);
                          am = moment_load.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                        
                         y1_calculations = m / length;
                         y2_calculations = -y1_calculations;
                         
                         z1_calculations = 0;
                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                //5) Fixed - Free
                if((TR1 == "xyz" && TR2 == "free") || (TR1 == "ixyz" && TR2 == "free")){
                //xyz = fixed , xy = hinge , y = roller, free = free
                    for (int j = 0 ; j<= axial_loads.get(i).getSize() - 1; j++){
                
                     
                         a = axial_loads.get(i).getLocation(j);
                         b = length - a;
                         p = axial_loads.get(i).getLoads(j);
               
                         
                         y1_calculations = -p;
                         z1_calculations = -p*a;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         y2_calculations = 0;
                         z2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= uniform_loads.get(i).getSize() - 1; j++){
                         aa = uniform_loads.get(i).getStartLocation(j);
                         bb = uniform_loads.get(i).getUniformLoadLength(j);
                         w = uniform_loads.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                        
                         /////////////////////////////Here ................................................................
                        
                         y1_calculations = -w*bb;
                         z1_calculations = -w*bb*(bb/2+aa);
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         y2_calculations = 0;
                         z2_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= moment_load.get(i).getSize() - 1; j++){
                         
                          m =moment_load.get(i).getMoments(j);
                          am = moment_load.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                        
                         z1_calculations = -m;
                         
                         y1_calculations = 0;
                         y2_calculations = 0;
                         z2_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                
                     //6) Free - Fixed
                if((TR1 == "free" && TR2 == "xyz") || (TR1 == "free" && TR2 == "ixyz")){
                //xyz = fixed , xy = hinge , y = roller, free = free
                    for (int j = 0 ; j<= axial_loads.get(i).getSize() - 1; j++){
                
                     
                         a = axial_loads.get(i).getLocation(j);
                         b = length - a;
                         p = axial_loads.get(i).getLoads(j);
               
                         
                         y2_calculations = -p;
                         z2_calculations = p*a;
                         
                         x1_calculations = 0;
                         x2_calculations = 0;
                         y1_calculations = 0;
                         z1_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                         
                    }
                    
                    //Uniform Loads Anylysis
                      for (int j = 0 ; j<= uniform_loads.get(i).getSize() - 1; j++){
                         aa = uniform_loads.get(i).getStartLocation(j);
                         bb = uniform_loads.get(i).getUniformLoadLength(j);
                         w = uniform_loads.get(i).getUniformLoadValue(j);
                         cc = length - (aa+bb);
                        
                         /////////////////////////////Here ................................................................
                        
                         y2_calculations = -w*bb;
                         z2_calculations = w*bb*(bb/2+cc);
                         
                         x1_calculations = 0;
                         x2_calculations = 0; 
                         y1_calculations = 0;
                         z1_calculations = 0; 
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                    }
                      
                      //Moment Loads Anylysis
                      for (int j = 0 ; j<= moment_load.get(i).getSize() - 1; j++){
                         
                          m =moment_load.get(i).getMoments(j);
                          am = moment_load.get(i).getLocation(j);
                          bm = length - am;
                          
                         /////////////////////////////Here ................................................................
                        
                         z2_calculations = -m;
                         
                         y1_calculations = 0;
                         y2_calculations = 0;
                         z1_calculations = 0;
                         x1_calculations = 0;
                         x2_calculations = 0;
                         
                         x1 += x1_calculations;
                         y1 += y1_calculations;
                         z1 += z1_calculations;
                         x2 += x2_calculations;
                         y2 += y2_calculations;
                         z2 += z2_calculations;
                        }  
                }
                //System.out.println("x1="+x1+"  y1="+y1+"  z1="+z1+"  x2="+x2+"  y2="+y2+"  z2="+z2);

                //After we calculated the local reactions, now we need to convert it to Major X-Y Coordinates....
                //#1--Make the Transformation Matrix
                TraM[0][0] = cosTheta ; TraM[0][1]= -sinTheta ; TraM[0][2] = 0 ; TraM[0][3] =    0     ; TraM[0][4] =     0      ; TraM[0][5] = 0 ;
                TraM[1][0] = sinTheta ; TraM[1][1]=  cosTheta ; TraM[1][2] = 0 ; TraM[1][3] =    0     ; TraM[1][4] =     0      ; TraM[1][5] = 0 ; 
                TraM[2][0] =    0     ; TraM[2][1]=      0    ; TraM[2][2] = 1 ; TraM[2][3] =    0     ; TraM[2][4] =     0      ; TraM[2][5] = 0 ; 
                TraM[3][0] =    0     ; TraM[3][1]=      0    ; TraM[3][2] = 0 ; TraM[3][3] = cosTheta ; TraM[3][4] = -sinTheta  ; TraM[3][5] = 0 ;
                TraM[4][0] =    0     ; TraM[4][1]=      0    ; TraM[4][2] = 0 ; TraM[4][3] = sinTheta ; TraM[4][4] = cosTheta   ; TraM[4][5] = 0 ; 
                TraM[5][0] =    0     ; TraM[5][1]=      0    ; TraM[5][2] = 0 ; TraM[5][3] =   0      ; TraM[5][4] =     0      ; TraM[5][5] = 1 ; 

                //#2--Make the local reactions matrix
                local_loads[0][0] = x1;
                local_loads[1][0] = y1;
                local_loads[2][0] = z1;
                local_loads[3][0] = x2;
                local_loads[4][0] = y2;
                local_loads[5][0] = z2;
                
                //#3--Multiply transformation matrix by local to convert the local to major
                major_loads = MultiplayMatrix(TraM,local_loads);
                
                //#4--Combine the QF Materix that we should need to solve the frame stifness Matrix...
                QF[0][0] += major_loads[0][0];
                QF[1][0] += major_loads[1][0];
                QF[2][0] += major_loads[2][0];
                QF[3][0] += major_loads[3][0];
                QF[4][0] += major_loads[4][0];
                QF[5][0] += major_loads[5][0];

                //PrintMatrix(QF);
              
           return QF;     
    }
    public static double[][] k_local_FINITE(int beam_Index) throws IOException{
    // Retrieve All data and calculate the size of K
     
        double[][] K = new double[6][6];
       
        int i = beam_Index;
          
            double E = fram_member.get(i).getE() * 1E+6;
            double I = fram_member.get(i).getI() * 1E-12;
            double A = fram_member.get(i).getA() * 1E-6;
            double ly = fram_member.get(i).getLamda_y();
            double lx = fram_member.get(i).getLamda_x();
            double L = fram_member.get(i).getLength();
            
            int Ui = 0; //0
            int Vi = 1; //1
            int Mi = 2; //2
            
            int Uj = 3; //3
            int Vj = 4; //4
            int Mj = 5; //5 
                       
            //System.out.println(Ui+""+Vi+""+Mi+""+Uj+""+Vj+""+Mj);

            //Build the K Matrix/....
            K[Ui][Ui] +=  (A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2);
            K[Ui][Vi] +=  ((A*E/L)-(12*E*I/Math.pow(L, 3))) * lx * ly;
            K[Ui][Mi] +=  (-6*E*I/Math.pow(L, 2))*ly;
            K[Ui][Uj] += -((A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2));
            K[Ui][Vj] += -((A*E/L)*lx*ly - (12*E*I/Math.pow(L, 3))*ly*lx);
            K[Ui][Mj] +=  (-6*E*I/Math.pow(L, 2))*ly;
           
            K[Vi][Ui] +=  ((A*E/L) - (12*E*I/Math.pow(L, 3)))*lx*ly;
            K[Vi][Vi] +=  (A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2);
            K[Vi][Mi] +=  (6*E*I/Math.pow(L, 2))*lx;
            K[Vi][Uj] += -((A*E/L)*ly*lx - (12*E*I/Math.pow(L, 3))*lx*ly);
            K[Vi][Vj] += -((A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2));
            K[Vi][Mj] +=  (6*E*I/Math.pow(L, 2))*lx;
           
            K[Mi][Ui] += (-6*E*I/Math.pow(L, 2))*ly;
            K[Mi][Vi] += (6*E*I/Math.pow(L, 2))*lx;
            K[Mi][Mi] += (4*E*I)/L;
            K[Mi][Uj] += (6*E*I/Math.pow(L, 2))*ly;
            K[Mi][Vj] += (-6*E*I/Math.pow(L, 2))*lx;
            K[Mi][Mj] += (2*E*I)/L;
            
            K[Uj][Ui] += -((A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2));
            K[Uj][Vi] += -((A*E/L)*lx*ly - (12*E*I/Math.pow(L, 3))*ly*lx);
            K[Uj][Mi] +=  (6*E*I/Math.pow(L, 2))*ly;
            K[Uj][Uj] +=  (A*E/L)*Math.pow(lx, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(ly, 2);
            K[Uj][Vj] +=  ((A*E/L)-(12*E*I/Math.pow(L, 3))) * lx * ly;
            K[Uj][Mj] +=  (6*E*I/Math.pow(L, 2))*ly;
            
            K[Vj][Ui] += -((A*E/L)*lx*ly - (12*E*I/Math.pow(L, 3))*ly*lx);
            K[Vj][Vi] += -((A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2));
            K[Vj][Mi] +=  (-6*E*I/Math.pow(L, 2))*lx;
            K[Vj][Uj] +=  ((A*E/L)-(12*E*I/Math.pow(L, 3))) * lx * ly;
            K[Vj][Vj] +=  (A*E/L)*Math.pow(ly, 2) + (12*E*I/Math.pow(L, 3))*Math.pow(lx, 2);
            K[Vj][Mj] +=  (-6*E*I/Math.pow(L, 2))*lx;
            
            K[Mj][Ui] +=  (-6*E*I/Math.pow(L, 2))*ly;
            K[Mj][Vi] +=  (6*E*I/Math.pow(L, 2))*lx;
            K[Mj][Mi] +=  (2*E*I)/L;
            K[Mj][Uj] +=  (6*E*I/Math.pow(L, 2))*ly;
            K[Mj][Vj] +=  (-6*E*I/Math.pow(L, 2))*lx;
            K[Mj][Mj] +=  (4*E*I)/L;
            
          
       
        return K;
    }
    public static void FEM(int NumberOfElement) throws IOException{
        // Retrieve All data and calculate the size of K
        ArrayList<String[]> jointsData = new ArrayList<>();
        ArrayList<String[]> membersData = new ArrayList<>();
        
        jointsData = Text_Reader("joints.txt");
        membersData = Text_Reader("members.txt");
        
        int numberOfJoint = jointsData.size();
        int numberOfMember = membersData.size();
        int numberOfMemebr_after_FEM = numberOfMember*NumberOfElement;
        
        double x1,y1,x2,y2;
        double step, L;
        double cos_theta, sin_theta;
        
        int count=0; // to fill the j1 and j2 help in dividing
        
        //Initilize the joints....
        for (int i=0 ; i<= numberOfMemebr_after_FEM ; i++){
            joints_supports.add(new JointSupport("free"));
        }
        
        for(int i=0 ; i<=numberOfMember-1 ; i++){
             x1 = x[j1[i]];
             y1 = y[j1[i]];
             x2 = x[j2[i]];
             y2 = y[j2[i]];
            
             L = Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2));
             
             cos_theta = (x2-x1)/L;
             sin_theta = (y2-y1)/L;

             step = L / NumberOfElement;
             
             String LeftSupport = JOINT_SUPPORTS.get(j1[i]).getSupportType();
             String RightSupport = JOINT_SUPPORTS.get(j2[i]).getSupportType();
             
             double E = FRAM_MEMBER.get(i).getE();
             double I = FRAM_MEMBER.get(i).getI();
             double A = FRAM_MEMBER.get(i).getA();

             
             int NumberOfAxialLoadOnThisMember = AXIAL_LOADS.get(i).getSize();
             int NumberOfUniformLoadOnThisMember = UNIFORM_LOADS.get(i).getSize();
             int NumberOfMomentLoadOnThisMember = MOMENT_LOADS.get(i).getSize();
             
             //Loads at joints...
             double X_Load_leftJoint = JOINT_LOADS.get(j1[i]).get_Xload();
             double Y_Load_leftJoint = JOINT_LOADS.get(j1[i]).get_Yload();
             double M_Load_leftJoint = JOINT_LOADS.get(j1[i]).get_Mload();
             
             double X_Load_RightJoint = JOINT_LOADS.get(j2[i]).get_Xload();
             double Y_Load_RightJoint = JOINT_LOADS.get(j2[i]).get_Yload();
             double M_Load_RightJoint = JOINT_LOADS.get(j2[i]).get_Mload();

             //----1------Make joints (x,y)
             double x = x1;
             double y = y1;
             for(int j=0 ; j<= NumberOfElement;j++){
                 if((x_finite.size() != 0) && (y_finite.size() !=0)){
                     if((x_finite.get(x_finite.size()-1) == x) && (y_finite.get(y_finite.size()-1) == y)){
                         //System.out.println("similir");
                         
                     }else{
                         //System.out.println("nonsimilir");
                         x_finite.add(x);
                         y_finite.add(y);
                     }
                 }else{
                     x_finite.add(x);
                     y_finite.add(y);
                 }
                 
                 
                 x += step*cos_theta;
                 y += step*sin_theta;
             }
             
             //----2-------Make members (j1,j2)
             for(int j=count ; j<=(count+NumberOfElement-1); j++){
               j1_finite.add(j);
               j2_finite.add(j+1);
               
               fram_member.add(new FramMember(j,j+1,x_finite.get(j),y_finite.get(j),x_finite.get(j+1),y_finite.get(j+1),E,I,A));
               axial_loads.add(new AxialLoad(x_finite.get(j),y_finite.get(j),x_finite.get(j+1),y_finite.get(j+1)));
               uniform_loads.add(new UniformLoad(x_finite.get(j),y_finite.get(j),x_finite.get(j+1),y_finite.get(j+1)));
               moment_load.add(new MomentLoad(x_finite.get(j),y_finite.get(j),x_finite.get(j+1),y_finite.get(j+1)));              
             }
              //----2-------Make Joints load (X,Y,M)
             for(int j=count ; j<=(count+NumberOfElement); j++){
                 joint_loads.add(new JointLoad(j,0,0,0));
                 
                 if(j == count){
                  joint_loads.get(j).editForce(1, X_Load_leftJoint);
                  joint_loads.get(j).editForce(2, Y_Load_leftJoint);
                  joint_loads.get(j).editForce(3, M_Load_leftJoint);
                 }else if(j == count+NumberOfElement){
                  joint_loads.get(j).editForce(1, X_Load_RightJoint);
                  joint_loads.get(j).editForce(2, Y_Load_RightJoint);
                  joint_loads.get(j).editForce(3, M_Load_RightJoint);  
                 }
                 
             }
             //----22-------Make Loads
             for(int j=count ; j<=(count+NumberOfElement-1); j++){
               
               int MemberIndex = j;
               //Fill the axial_loads
               for(int k = 0 ; k<=NumberOfAxialLoadOnThisMember-1 ; k++){
                   double load_coordinate = AXIAL_LOADS.get(i).getLocation(k);
                   double load_value = AXIAL_LOADS.get(i).getLoads(k);
                   
                   double XcoordinateOfFirstJoint = x1;
                   double XcoordinateOfleftoffinitemember  = x_finite.get(j);
                   double XcoordinateOfRightoffinitemember = x_finite.get(j+1);
                   
                   double YcoordinateOfFirstJoint = y1;
                   double YcoordinateOfleftoffinitemember  = y_finite.get(j);
                   double YcoordinateOfRightoffinitemember = y_finite.get(j+1);
                   
                   double LeftJointDistance = Math.sqrt(Math.pow((x_finite.get(j) - x1), 2)+Math.pow((y_finite.get(j) - y1), 2));
                   double RightJointDistance = Math.sqrt(Math.pow((x_finite.get(j+1) - x1), 2)+Math.pow((y_finite.get(j+1) - y1), 2));

                   //Test if the load within the member and if(true) Add it....
                   if((load_coordinate > (LeftJointDistance)) && (load_coordinate < (RightJointDistance)) ){
                       axial_loads.get(j).AddLoad(load_value, (load_coordinate-LeftJointDistance));
                       System.out.println("value= "+load_value+" member: "+j+" @ "+(load_coordinate-LeftJointDistance));
                   }else if((load_coordinate == (LeftJointDistance))){
                       axial_loads.get(j).AddLoad(load_value, (load_coordinate-LeftJointDistance));
                       System.out.println("value= "+load_value+" member: "+j+" @ "+(load_coordinate-LeftJointDistance));
                   } 
                   
               }
               //Fill the Moment load...
               for(int k = 0 ; k<=NumberOfMomentLoadOnThisMember-1 ; k++){
                   double load_coordinate = MOMENT_LOADS.get(i).getLocation(k);
                   double load_value = MOMENT_LOADS.get(i).getMoments(k);
                   
                   double XcoordinateOfFirstJoint = x1;
                   double XcoordinateOfleftoffinitemember  = x_finite.get(j);
                   double XcoordinateOfRightoffinitemember = x_finite.get(j+1);
                   
                   double YcoordinateOfFirstJoint = y1;
                   double YcoordinateOfleftoffinitemember  = y_finite.get(j);
                   double YcoordinateOfRightoffinitemember = y_finite.get(j+1);
                   
                   double LeftJointDistance = Math.sqrt(Math.pow((x_finite.get(j) - x1), 2)+Math.pow((y_finite.get(j) - y1), 2));
                   double RightJointDistance = Math.sqrt(Math.pow((x_finite.get(j+1) - x1), 2)+Math.pow((y_finite.get(j+1) - y1), 2));

                   //Test if the load within the member and if(true) Add it....
                   if((load_coordinate > (LeftJointDistance)) && (load_coordinate < (RightJointDistance)) ){
                       moment_load.get(j).AddMoment(load_value, (load_coordinate-LeftJointDistance));
                   }else if((load_coordinate == (LeftJointDistance))){
                       moment_load.get(j).AddMoment(load_value, (load_coordinate-LeftJointDistance));
                   } 
               }
                //Fill the uniformloads
               for(int k = 0 ; k<=NumberOfUniformLoadOnThisMember-1 ; k++){
                   double uniformLoadValue = UNIFORM_LOADS.get(i).getUniformLoadValue(k);
                   double a = UNIFORM_LOADS.get(i).getStartLocation(k); //start location of the unifrom load
                   double b = a + UNIFORM_LOADS.get(i).getUniformLoadLength(k);//location of the ends of the uniform load
                   
                   double XcoordinateOfFirstJoint = x1;
                   double XcoordinateOfleftoffinitemember  = x_finite.get(j);
                   double XcoordinateOfRightoffinitemember = x_finite.get(j+1);
                   
                   double YcoordinateOfFirstJoint = y1;
                   double YcoordinateOfleftoffinitemember  = y_finite.get(j);
                   double YcoordinateOfRightoffinitemember = y_finite.get(j+1);
                   
                   double LeftJointDistance = Math.sqrt(Math.pow((x_finite.get(j) - x1), 2)+Math.pow((y_finite.get(j) - y1), 2));
                   double RightJointDistance = Math.sqrt(Math.pow((x_finite.get(j+1) - x1), 2)+Math.pow((y_finite.get(j+1) - y1), 2));

                   System.out.println("Here  "+LeftJointDistance+" "+RightJointDistance+",,"+a+","+b+"membr "+j+"length="+uniform_loads.get(j).getMemberLength());
                   //Test if the uniform_load within the member and if(true) Add it....
                   //left point out and riht point is in
                   if(((LeftJointDistance<=a && LeftJointDistance<=b) || (LeftJointDistance>=a && LeftJointDistance>=b)) && (RightJointDistance>=a && RightJointDistance<=b)){
                       uniform_loads.get(j).AddUniformLoad(uniformLoadValue, a - LeftJointDistance, RightJointDistance - a,false);
                   }
                   /*Both In*/
                   else if((LeftJointDistance>=a && LeftJointDistance<=b) && (RightJointDistance>=a && RightJointDistance<=b) ){
                       System.out.println("both");
                       uniform_loads.get(j).AddUniformLoad(uniformLoadValue, 0, RightJointDistance - LeftJointDistance,false);
                   }
                   /*Left in and riht is out*/
                   else if(((RightJointDistance<=a && RightJointDistance<=b) || (RightJointDistance>=a && RightJointDistance>=b)) && (LeftJointDistance>=a && LeftJointDistance<=b)){
                       uniform_loads.get(j).AddUniformLoad(uniformLoadValue, 0, b - LeftJointDistance,false);
                   }
                   /*Both are out*/
                   else{
                        
                   }
               }
             }
             
             //----3-----Make Supports
             for (int j=count ; j<=(count+NumberOfElement) ; j++){
                 if(j == count)
                 {
                   joints_supports.get(j).set_Support(LeftSupport);
                 }else if(j==count+NumberOfElement){
                   joints_supports.get(j).set_Support(RightSupport);
                 }else{
                   joints_supports.get(j).set_Support("ixyz");
                 }
             }
             
             //---4----Make the loads
             count = count + NumberOfElement;
        }
       
    }  
    public static ArrayList<Integer> BoundryConition_FINITE() throws IOException{
        int numberOfJoint = x_finite.size();
        int numberOfMember = j1_finite.size();
        
       ArrayList<Integer> boundryConition = new ArrayList<>();
        
       for (int i =0 ; i<= joints_supports.size()-1 ; i++){
           if(joints_supports.get(i).get_d1() == 0 ){
               boundryConition.add(3*(i+1)-3);
           }
           if(joints_supports.get(i).get_d2() == 0){
               boundryConition.add(3*(i+1)-2);
           }
           if(joints_supports.get(i).get_d3() == 0){
               boundryConition.add(3*(i+1)-1);
           }
       }
        
        return boundryConition;
    }
    public static void AnalysisANDprint_FINITE() throws IOException{
            //--4--Boubdry Condition
        ArrayList<Integer> BC = new ArrayList<>();
        BC = BoundryConition_FINITE();
        
        
        //Print Matrices and after that we can do the calculations
        //PrintMatrix(Q_FINITE(),"0.00");
        //PrintMatrix(K_FINITE(),"0.00");
        //PrintMatrix(QF_FINITE(),"0.00"); 
        //PrintMatrix(D_FINITE(),"0.00"); 

        //Print Matrix After eliminate and applied the boundry condition
        //PrintMatrix(reduceMatrix(Q_FINITE(),BC,"raw"),"0.00");  
        //PrintMatrix(reduceMatrix(K_FINITE(),BC,"both"),"0.00"); 
        //PrintMatrix(reduceMatrix(D_FINITE(),BC,"raw"),"0.00");
        //PrintMatrix(reduceMatrix(QF_FINITE(),BC,"raw"),"0.00"); 


        //---------Anylysis and solve--------------------
        double[][] Q_FINITE  = reduceMatrix(Q_FINITE(),BC,"raw");
        double[][] K_FINITE  = reduceMatrix(K_FINITE(),BC,"both");
        double[][] D_FINITE  = reduceMatrix(D_FINITE(),BC,"raw");
        double[][] QF_FINITE = reduceMatrix(QF_FINITE(),BC,"raw");
       
       // [Q] = [K][D]+[QF]
       // [K]^-1*[Q]-[QF] = [D]
       //--1--Solve For Defliction
       double[][] defliction =MultiplayMatrix(MatrixInverse(K_FINITE),Matrix1MinusMatrix2(Q_FINITE,QF_FINITE)) ;
       //PrintMatrix(defliction,"0.000000000"); 
      
        
       //--2--Combine the deflictions and get a full matrix of defliction wihout unkowns
       PrintMatrix(D_Solved_FINITE(defliction),"0.000000000"); 
       double[][] D_Solved_FINITE = D_Solved_FINITE(defliction); //Get the full Matrix of defliction without unkowns
      
       
       //--3-- find the reaction of beams
       double[][] Beam_0_defliction_FINITE = d_FINITE(D_Solved_FINITE,0);//Get the defliction Matrix of beam (0) first beam
       //PrintMatrix(Beam_0_defliction_FINITE,"0.000000000"); 
       
       double[][] Beam_0_qf_FINITE = qf_FINITE(0);//Get the qf Matrix of beam (0) first beam
       //PrintMatrix(Beam_0_qf_FINITE,"0.000000000"); 
 
       double[][] Beam_0_localK_FINITE = k_local_FINITE(0);//Get the qf Matrix of beam (0) first beam
       //PrintMatrix(Beam_0_localK_FINITE,"0.00"); 
       
       double[][] Beam_0_Reactions_FINITE = Matrix1PlusMatrix2(MultiplayMatrix(Beam_0_localK_FINITE,Beam_0_defliction_FINITE),Beam_0_qf_FINITE);//Get the qf Matrix of beam (0) first beam
       //PrintMatrix(Beam_0_Reactions_FINITE,"0.00"); 
               
    } 
    public static void FEMAnalysisANDprint() throws IOException{
            //--4--Boubdry Condition
        ArrayList<Integer> BC = new ArrayList<>();
        BC = BoundryConition();
        
        
        //Print Matrices and after that we can do the calculations
        PrintMatrix(Q(),"0.00");
        PrintMatrix(K(),"0.00");
        PrintMatrix(QF(),"0.00"); 
        PrintMatrix(D(),"0.00"); 

        //Print Matrix After eliminate and applied the boundry condition
        PrintMatrix(reduceMatrix(Q(),BC,"raw"),"0.00");  
        PrintMatrix(reduceMatrix(K(),BC,"both"),"0.00"); 
        PrintMatrix(reduceMatrix(D(),BC,"raw"),"0.00");
        PrintMatrix(reduceMatrix(QF(),BC,"raw"),"0.00"); 


        //---------Anylysis and solve--------------------
        double[][] Q  = reduceMatrix(Q(),BC,"raw");
        double[][] K  = reduceMatrix(K(),BC,"both");
        double[][] D  = reduceMatrix(D(),BC,"raw");
        double[][] QF = reduceMatrix(QF(),BC,"raw");
       
       // [Q] = [K][D]+[QF]
       // [K]^-1*[Q]-[QF] = [D]
       //--1--Solve For Defliction
       double[][] defliction =MultiplayMatrix(MatrixInverse(K),Matrix1MinusMatrix2(Q,QF)) ;
       PrintMatrix(defliction,"0.000000000"); 
      
        
       //--2--Combine the deflictions and get a full matrix of defliction wihout unkowns
       PrintMatrix(D_Solved(defliction),"0.000000000"); 
       double[][] D_Solved = D_Solved(defliction); //Get the full Matrix of defliction without unkowns
      
       
       //--3-- find the reaction of beams
       double[][] Beam_0_defliction = d(D_Solved,0);//Get the defliction Matrix of beam (0) first beam
       PrintMatrix(Beam_0_defliction,"0.000000000"); 
       
       double[][] Beam_0_qf = qf(0);//Get the qf Matrix of beam (0) first beam
       PrintMatrix(Beam_0_qf,"0.000000000"); 
 
       double[][] Beam_0_localK = k_local(0);//Get the qf Matrix of beam (0) first beam
       PrintMatrix(Beam_0_localK,"0.00"); 
       
       double[][] Beam_0_Reactions = Matrix1PlusMatrix2(MultiplayMatrix(Beam_0_localK,Beam_0_defliction),Beam_0_qf);//Get the qf Matrix of beam (0) first beam
       PrintMatrix(Beam_0_Reactions,"0.00"); 
               
    }  
    public static void PrintFEM() throws IOException{
        for(int i=0 ; i<=x_finite.size()-1; i++){
           System.out.println(x_finite.get(i)+","+y_finite.get(i));
       }
       System.out.println("");
       for(int i=0 ; i<=j1_finite.size()-1; i++){
           System.out.println(j1_finite.get(i)+","+j2_finite.get(i));
       }
       System.out.println("");
       for(int i=0 ; i<=joints_supports.size()-1; i++){
           System.out.println(joints_supports.get(i).getSupportType());
       }     
       System.out.println("");
       for(int i=0 ; i<=fram_member.size()-1; i++){
           System.out.println(fram_member.get(i).getFirstJointNumber()+","+fram_member.get(i).getSecondJointNumber()+" "+fram_member.get(i).getE());
       }   
        System.out.println("");
       for(int i=0 ; i<=axial_loads.size()-1; i++){
           for(int j=0; j<= axial_loads.get(i).getSize()-1 ; j++){
              System.out.println(axial_loads.get(i).getLoads(j)+" @ "+axial_loads.get(i).getLocation(j)+"@ Memebr "+i);
           }
       }  
       System.out.println("");
       for(int i=0 ; i<=uniform_loads.size()-1; i++){
           for(int j=0; j<= uniform_loads.get(i).getSize()-1 ; j++){
              System.out.println(uniform_loads.get(i).getUniformLoadValue(j)+" from "+uniform_loads.get(i).getStartLocation(j)+" LENGTH "+uniform_loads.get(i).getUniformLoadLength(j)+"@ Memebr "+i);
           }
       }
       System.out.println("");
       for(int i=0 ; i<=moment_load.size()-1; i++){
           for(int j=0; j<= moment_load.get(i).getSize()-1 ; j++){
              System.out.println(moment_load.get(i).getMoments(j)+" @ "+moment_load.get(i).getLocation(j)+" at member "+i);
           }
       } 
       System.out.println("");
       for(int i=0 ; i<=joint_loads.size()-1; i++){
          
              System.out.println("x= "+joint_loads.get(i).get_Xload()+" y= "+joint_loads.get(i).get_Yload()+" m= "+joint_loads.get(i).get_Mload());
         
       } 
       
       PrintMatrix(D_FINITE(),"0.00000");
    }
   
    
    
  
    
    
}

