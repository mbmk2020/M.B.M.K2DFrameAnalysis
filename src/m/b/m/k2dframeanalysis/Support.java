/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.b.m.k2dframeanalysis;

import java.util.ArrayList;

/**
 *
 * @author Mohammad Bilal Al-khasawneh
 * @email mbmk2020@umd.edu
 */
public class Support {
    
    
    //Type of support xyz xy xz yz y x z
    // xyz --> d1 = 0 d2 = 0 d3 = 0
    // xy --> d1 = 0 d2 = 0 d3 = d
    // xz --> d1 = 0 d2 = d d3 = 0
    //x=d1 y=d2   z=d3
    
    private String Left_Support;
    private String Right_Support;
    
    private int d1,d2,d3;

    public Support(String left_support, String right_support){
        Left_Support = left_support;
        Right_Support = right_support;
        
        if (Left_Support == "xyz"){d1=0 ; d2=0 ; d3=0;}
        else if (Left_Support == "xy"){d1=0 ; d2=0 ; d3=1;}
        else if (Left_Support == "xz"){d1=0 ; d2=1 ; d3=0;}
        else if (Left_Support == "yz"){d1=1 ; d2=0 ; d3=0;}
        else if (Left_Support == "y"){d1=1 ; d2=0 ; d3=1;}
        else if (Left_Support == "x"){d1=0 ; d2=1 ; d3=1;}
        else if (Left_Support == "z"){d1=1 ; d2=1 ; d3=0;}
        else if (Left_Support == "free"){d1=1 ; d2=1 ; d3=1;}
        else if (Left_Support == "ixyz"){d1=1 ; d2=1 ; d3=1;}
        else{d1=0 ; d2=0 ; d3=0;}
       
      }
    
    public void Set_Supports(String left_support, String right_support){
        Left_Support = left_support;
        Right_Support = right_support;
        
         
        if (Left_Support == "xyz"){d1=0 ; d2=0 ; d3=0;}
        else if (Left_Support == "xy"){d1=0 ; d2=0 ; d3=1;}
        else if (Left_Support == "xz"){d1=0 ; d2=1 ; d3=0;}
        else if (Left_Support == "yz"){d1=1 ; d2=0 ; d3=0;}
        else if (Left_Support == "y"){d1=1 ; d2=0 ; d3=1;}
        else if (Left_Support == "x"){d1=0 ; d2=1 ; d3=1;}
        else if (Left_Support == "z"){d1=1 ; d2=1 ; d3=0;}
        else if (Left_Support == "free"){d1=1 ; d2=1 ; d3=1;}
        else if (Left_Support == "ixyz"){d1=1 ; d2=1 ; d3=1;}
        else{d1=0 ; d2=0 ; d3=0;}
    }
    
    public String getLeftSupport(){
        return Left_Support;
    }
    public String getRightSupport(){
        return Right_Support;
    }
    public int get_d1(){
        return d1;
    }
     public int get_d2(){
        return d2;
    }
     public int get_d3(){
        return d3;
    }
    
    
}
