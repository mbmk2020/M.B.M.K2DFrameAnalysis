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
public class test {
    
    public ArrayList<Double> gg;
    public Object[][] ggg;
    
    public test(){
        gg.add(33.33);
        
    }
    
    public void clearMatrix(Object[][] Array){
        for(int i=0 ; i<=Array.length-1 ; i++){
            for(int j=0 ; j<=Array[i].length-1 ; j++){
                   Array[i][j] = null;
            }
        }
        Array = null;
    }
    
}
