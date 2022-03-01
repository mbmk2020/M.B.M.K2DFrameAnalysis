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
public class MomentLoad {
     //Variables
    private ArrayList<Double> m = new ArrayList<>();
    private ArrayList<Double> a = new ArrayList<>();
    private double Length;

    //Constructor
    public MomentLoad(double x1,double y1,double x2,double y2){
        Length = Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2));
    }
    
      //Add Moment
    public void AddMoment(double moment,double location){
        if(location <=Length && location >=0){
            m.add(moment);
            a.add(location);
            System.out.println("Moment Added Successfully");
        }else{
          System.out.println("Check your Moment Inputs");
        }
    }
    
    //Delete Moment
    public void DeleteMoment(int momentIndex){
        m.remove(momentIndex);
        a.remove(momentIndex);

        System.out.println("Moment removed Successfully");

    }
    
    public int getSize(){
        return m.size();
    }
    
    public double getMoments(int Moment_index){
        return m.get(Moment_index);
    }
    
    public double getLocation(int location_index){
        return a.get(location_index);
    }
}
