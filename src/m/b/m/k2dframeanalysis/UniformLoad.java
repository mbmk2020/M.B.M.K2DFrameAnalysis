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
 */public class UniformLoad {
    //Variables
    private ArrayList<Double> w = new ArrayList<>();
    private ArrayList<Double> a = new ArrayList<>();
    private ArrayList<Double> b = new ArrayList<>();

    private double Length;
    
    
    //Constructor
    public UniformLoad(double x1,double y1,double x2,double y2){
        Length = Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2));
    }
    
    //Add Load
    public void AddUniformLoad(double uniformload,double start_location, double uniform_load_length,boolean CheckAboutLenght){
        if(CheckAboutLenght){
            if(start_location <=Length && start_location >=0 && (start_location+uniform_load_length) <=Length && (start_location+uniform_load_length) >=0 ){
            w.add(uniformload);
            a.add(start_location);
            b.add(uniform_load_length);
            
            System.out.println("UniformLoad Added Successfully");
        }else{
          System.out.println("Check your UniformLoad Inputs");
        }
        }else{
            w.add(uniformload);
            a.add(start_location);
            b.add(uniform_load_length);
            
            System.out.println("UniformLoad Added Successfully");
        }
        
    }
    
    //Delete Load
    public void DeleteUnifromLoad(int UniformloadIndex){
           w.remove(UniformloadIndex);
           a.remove(UniformloadIndex);
           b.remove(UniformloadIndex);
        System.out.println("UniformLoad removed Successfully");

    }
    
    public int getSize(){
        return w.size();
    }
    
    public double getUniformLoadValue(int UniformLoad_index){
        return w.get(UniformLoad_index);
    }
    
    public double getStartLocation(int UniformLoad_index){
        return a.get(UniformLoad_index);
    }
    
    public double getUniformLoadLength(int UniformLoad_index){
        return b.get(UniformLoad_index);
    }
    
     public double getEndLocation(int UniformLoad_index){
        return (a.get(UniformLoad_index)+b.get(UniformLoad_index));
    }
    
    public double getMemberLength(){
        return Length;
    }
}
