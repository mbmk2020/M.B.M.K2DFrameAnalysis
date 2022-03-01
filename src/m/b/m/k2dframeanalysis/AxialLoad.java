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
public class AxialLoad {
    
    
    //Variables
    private ArrayList<Double> p = new ArrayList<>();
    private ArrayList<Double> a = new ArrayList<>();
    private double Length;

    //Constructor
    public AxialLoad(double x1,double y1,double x2,double y2){
        Length = Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2));
    }
   
    //Add Load
    public void AddLoad(double load,double location){
        if(location <=Length && location >=0){
            p.add(load);
            a.add(location);
            System.out.println("Load Added Successfully");
        }else{
          System.out.println("Check your Load Inputs");
        }
    }
    
    //Delete Load
    public void DeleteLoad(int loadIndex){
        p.remove(loadIndex);
        a.remove(loadIndex);

        System.out.println("Load removed Successfully");

    }
    
    public int getSize(){
        return p.size();
    }
    
    public double getLoads(int Load_index){
        return p.get(Load_index);
    }
    
    public double getLocation(int location_index){
        return a.get(location_index);
    }
}
