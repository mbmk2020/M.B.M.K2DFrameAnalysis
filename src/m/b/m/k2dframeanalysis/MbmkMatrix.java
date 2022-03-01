/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.b.m.k2dframeanalysis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Mohammad Bilal Al-khasawneh
 * @email mbmk2020@umd.edu
 */
public class MbmkMatrix {
    
   
    
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
        //Best results 0.000000000001
        for (int i=1 ; i<=n-1 ; i++){
           for (int j=0 ; j<= i-1 ; j++){
               if (Format(a[j][j], "0.00000000000000") == 0){a[j][j] = 0.000000000001;}
               f = a[i][j] / a[j][j];
               for(int m=0 ; m<=n-1 ; m++){
                   a[i][m] = a[i][m] - f*a[j][m];
                   b[i][m] = b[i][m] - f*b[j][m];
               }
           }
        }
        
        for (int i=n-2 ; i>=0 ; i--){
            for( int j=(n-1) ; j>=(i+1) ; j--){
                if(Format(a[j][j],"0.00000000000000") == 0){a[j][j] = 0.000000000001;}
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
                if(Format(a[i][i],"0.00000000000000") == 0){a[i][i] = 0.000000000001;}
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
    public  double Format(double value, String decimals){
        DecimalFormat df2 = new DecimalFormat( decimals );
         double newValue = new Double(df2.format(value)).doubleValue();
         return newValue;
    }
    
}
