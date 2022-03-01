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
public class JointLoad {
    
    private int jointNumber;
    private double x_load; //X Axis
    private double y_load; //Y Axis
    private double m_load; //Moment
    private int load_type;
    
    //Constructor
    public JointLoad(int jointNumber, double x_load,double y_load,double m_load){
        this.jointNumber = jointNumber;
        this.x_load = x_load;
        this.y_load = y_load;
        this.m_load = m_load;
    }
    
    public void deleteForce(int index){
        if (index == 1){
            this.x_load = 0;
        }
         if (index == 2){
            this.y_load = 0;
        }
         if (index == 3){
            this.m_load = 0;
        }
    }
    
    public void addForce(int index, double value){
         if (index == 1){
            this.load_type = 1; 
            this.x_load += value;
        }
         if (index == 2){
            this.load_type = 2;
            this.y_load += value;
        }
         if (index == 3){
            this.load_type = 3;
            this.m_load += value;
        }
  
    }
    
    public void editForce(int index, double value){
         if (index == 1){
            this.load_type = 1;
            this.x_load = value;
            System.out.print("Joint Load Added Successfully...!");
        }
         if (index == 2){
            this.load_type = 2; 
            this.y_load = value;
            System.out.print("Joint Load Added Successfully...!");

        }
         if (index == 3){
            this.load_type = 3;
            this.m_load = value;
            System.out.print("Joint Load Added Successfully...!");
        }
    }
    
    public double get_Xload(){
        return this.x_load;
    }
    public double get_Yload(){
        return this.y_load;
    }
    public double get_Mload(){
        return this.m_load;
    }   
    public int get_JointNumber(){
        return this.jointNumber; 
    }
    public int get_LoadType(){
        return this.load_type;
    } 
}
