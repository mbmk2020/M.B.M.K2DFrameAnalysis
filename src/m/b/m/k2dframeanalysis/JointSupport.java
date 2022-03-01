/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.b.m.k2dframeanalysis;

/**
 *
 * @author Mohammad Bilal Al-khasawneh
 * @email mbmk2020@umd.edu
 */
public class JointSupport {
    
    private String supportType;
    private int d1;
    private int d2;
    private int d3;
    
    //Constructor
    public JointSupport(String supportType){
        this.supportType = supportType;
        
        if (this.supportType == "xyz"){d1=0 ; d2=0 ; d3=0;}
        else if (this.supportType == "xy"){d1=0 ; d2=0 ; d3=1;}
        else if (this.supportType == "xz"){d1=0 ; d2=1 ; d3=0;}
        else if (this.supportType == "yz"){d1=1 ; d2=0 ; d3=0;}
        else if (this.supportType == "y"){d1=1 ; d2=0 ; d3=1;}
        else if (this.supportType == "x"){d1=0 ; d2=1 ; d3=1;}
        else if (this.supportType == "z"){d1=1 ; d2=1 ; d3=0;}
        else if (this.supportType == "free"){d1=1 ; d2=1 ; d3=1;}
        else if (this.supportType == "ixyz"){d1=1 ; d2=1 ; d3=1;/*And that mean Internal fixed to consider it as xyz when doing the analysis and to consider it a free when calculate the D*/}
        else{d1=0 ; d2=0 ; d3=0;}
    }
    
    public void set_Support(String supportType){
         this.supportType = supportType;
        
        if (this.supportType == "xyz"){d1=0 ; d2=0 ; d3=0;}
        else if (this.supportType == "xy"){d1=0 ; d2=0 ; d3=1;}
        else if (this.supportType == "xz"){d1=0 ; d2=1 ; d3=0;}
        else if (this.supportType == "yz"){d1=1 ; d2=0 ; d3=0;}
        else if (this.supportType == "y"){d1=1 ; d2=0 ; d3=1;}
        else if (this.supportType == "x"){d1=0 ; d2=1 ; d3=1;}
        else if (this.supportType == "z"){d1=1 ; d2=1 ; d3=0;}
        else if (this.supportType == "free"){d1=1 ; d2=1 ; d3=1;}
        else if (this.supportType == "ixyz"){d1=1 ; d2=1 ; d3=1;}
        else{d1=0 ; d2=0 ; d3=0;}
    }
    
    public void deleteSupport(){
        this.supportType = "free";
        
        d1=1 ; 
        d2=1 ; 
        d3=1;
    }
    
    public String getSupportType(){
        return this.supportType;
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
