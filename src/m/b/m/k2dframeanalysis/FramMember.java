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
public class FramMember {
    
    private int FJ; //First Joint
    private int SJ; //Second Joint
    private double x1,y1,x2,y2;
    private double Length;
    private double E, I, A; //User Input as Gpa, mm4, mm2
    private double lamda_y; // = y2-y1 / length
    private double lamda_x; // = x2-x1 / length
    
    //Constructor
    public FramMember(int firstJoint, int secondJoint, double x1, double y1, double x2, double y2, double ModulusOfElacticity, double MomentOfInertia,double crossSectionalArea){
        FJ = firstJoint;
        SJ = secondJoint;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        E = ModulusOfElacticity;
        I = MomentOfInertia;
        A = crossSectionalArea;
        
        //calculate
        Length = Math.sqrt(Math.pow((this.x2-this.x1), 2)+Math.pow((this.y2-this.y1), 2));
        lamda_y = (this.y2 - this.y1) / Length;
        lamda_x = (this.x2 - this.x1) / Length;

    }
    
    public void changeModulusOfElacticity(double e){
        E = e;
    }
    public void changeMomentOfInertia(double i){
        I = i;
    }
    public void changeArea(double a){
        A = a;
    }
    public double getLamda_y(){
        return lamda_y;
    }
     public double getLamda_x(){
        return lamda_x;
    }
    public double getE(){
        return E;
    }
    public double getI(){
        return I;
    }
    public double getA(){
        return A;
    }
    public double getLength(){
        return Length;
    }    
    public int getFirstJointNumber(){
        return FJ;
    }    
    public int getSecondJointNumber(){
        return SJ;
    }    
   
    
    
    
    
    
    
}
