/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.b.m.k2dframeanalysis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Mohammad Bilal Al-khasawneh
 * @email mbmk2020@umd.edu
 */
public class Drawing {
    
    public static Color color = new Color(100,100,100); //R RED ; G GREEN ; B BLUE
    public Graphics g;
    public int PanelWidth;
    public int PanelHeight;
    public int Scale = 50;
    public int X_StartPoint;
    public int Y_StartPoint;
    public int HorizantalMovement = 0;
    public int VerticalMovement = 0;
    
    public static MbmkMatrix myMatrixSolver = new MbmkMatrix();

    public void setPanel(JPanel panel){
        this.g = panel.getGraphics();
        PanelWidth = panel.getWidth();
        PanelHeight = panel.getHeight();
        X_StartPoint = PanelWidth/2;
        Y_StartPoint = PanelHeight/2;

    }
    
    public void refreshThePanelSize(JPanel panel){
        PanelWidth = panel.getWidth();
        PanelHeight = panel.getHeight();
        X_StartPoint = PanelWidth/2;
        Y_StartPoint = PanelHeight/2;
    }
    
    public void IncreaseTheScale(int val){
        
        Scale += val;
        if(Scale >=500){
            Scale = 500;
        }
    }
    
    public void DecreaseTheScale(int val){
        
        Scale -= val;
        if(Scale <=5){
            Scale = 5;
        }
    }
    
    public void MoveRight(){
        this.HorizantalMovement +=10;
    }
    
    public void MoveLeft(){
        this.HorizantalMovement -=10;
    }
    
    public void MoveDown(){
        this.VerticalMovement -=10;
    }
    
    public void MoveUp(){
        this.VerticalMovement +=10;
    }
    
    public void DrawLine(double X1,double Y1,double X2,double Y2){
         //Convert to 2D to facilitate draw a thicker lines
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLUE);
        
        int x1 = (int) (X1*Scale) + HorizantalMovement;
        int y1 = (int) (Y1*Scale) + VerticalMovement;
        int x2 = (int) (X2*Scale) + HorizantalMovement;
        int y2 = (int) (Y2*Scale) + VerticalMovement;
        
       g2.drawLine((X_StartPoint+x1), (Y_StartPoint-y1), (X_StartPoint+x2), (Y_StartPoint-y2));       
    }
    
    public void DrawPoints(double X,double Y){
         //Convert to 2D to facilitate draw a thicker lines
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.RED);
        
        int radias = (int) (0.1*Scale);
        int x1 = (int) (X*Scale) +HorizantalMovement;
        int y1 = (int) (Y*Scale) + VerticalMovement;
        
        x1 = x1 - radias / 2;
        y1 = y1 + radias / 2;
        
       
        
       g2.drawOval((X_StartPoint+x1), (Y_StartPoint-y1), radias,radias);       
    }
    
    public void CleanThePanel(JPanel Panel){
        this.g.setColor(Color.LIGHT_GRAY);
        this.g.fillRect(0, 0,PanelWidth , PanelHeight);
    }
    
    public void DrawTheSupport(double X,double Y, String TypeofTheSupport){
        
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.BLACK);
        
        int x = (int) (X*Scale) + HorizantalMovement;
        int y = (int) (Y*Scale) + VerticalMovement;
        
        if(TypeofTheSupport == "xyz"){
            g2.drawLine((X_StartPoint+x), (Y_StartPoint-y), (X_StartPoint+(x)), (Y_StartPoint-(y-20)));    
            g2.drawLine((X_StartPoint+(x-40)), (Y_StartPoint-(y-20)), (X_StartPoint+(x+40)), (Y_StartPoint-(y-20)));    
        }
        
        if(TypeofTheSupport == "xy"){
            g2.drawLine((X_StartPoint+x), (Y_StartPoint-y), (X_StartPoint+(x-25)), (Y_StartPoint-(y-20)));   
            g2.drawLine((X_StartPoint+x), (Y_StartPoint-y), (X_StartPoint+(x+25)), (Y_StartPoint-(y-20)));  
            g2.drawLine((X_StartPoint+(x-40)), (Y_StartPoint-(y-20)), (X_StartPoint+(x+40)), (Y_StartPoint-(y-20)));    
        }
        
        if(TypeofTheSupport == "y"){
            g2.drawLine((X_StartPoint+x), (Y_StartPoint-y), (X_StartPoint+(x-20)), (Y_StartPoint-(y-15)));   
            g2.drawLine((X_StartPoint+x), (Y_StartPoint-y), (X_StartPoint+(x+20)), (Y_StartPoint-(y-20)));  
            g2.drawLine((X_StartPoint+(x-40)), (Y_StartPoint-(y-20)), (X_StartPoint+(x+40)), (Y_StartPoint-(y-20)));  
            
            //Left wheel....
            g2.setStroke(new BasicStroke(6));
            g2.setColor(Color.DARK_GRAY);
             int radias = 13;
             int x1 = (x-20) - radias / 2;
             int y1 = (y-18) + radias / 2;
             g2.drawOval((X_StartPoint+x1), (Y_StartPoint-y1), radias,radias);      
             
             //Rigth wheel....
             radias = 13;
             x1 = (x+20) - radias / 2;
             y1 = (y-18) + radias / 2;
             g2.drawOval((X_StartPoint+x1), (Y_StartPoint-y1), radias,radias);       
        }
    }
    
    public void DrawTheAxialLoads(double X1,double Y1,double X2,double Y2,double position, double load_value){
        
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setStroke(new BasicStroke(2));
        Color myColor = new Color(0,102,51);
        g2.setColor(myColor);
        
        double L = Math.sqrt(Math.pow((X2-X1), 2)+Math.pow((Y2-Y1), 2));
        double cosTheta = (X2-X1) / L;
        double sinTheta = (Y2-Y1) / L;
        
        double ArrowLength = 60;
        double rightShift = 6;
        double UpShift = 20;
        double UpShift2 = 40;
       
        if(load_value > 0){
           //Point(1)
            int x1 = (int) ((X1+ position*cosTheta)*Scale)  + HorizantalMovement;
            int y1 = (int) ((Y1+ position*sinTheta)*Scale)  + VerticalMovement;
        
            //Point(2)
            int x2 = (int) (x1 - ArrowLength*sinTheta);
            int y2 = (int) (y1 + ArrowLength*cosTheta); 
        
            //Point(3)
            int x3 = (int) (x1 + rightShift*cosTheta - UpShift2*sinTheta);
            int y3 = (int) (y1 + rightShift*sinTheta + UpShift2*cosTheta);
        
            //Point(4)
            int x4 = (int) (x1 - rightShift*cosTheta - UpShift2*sinTheta) ;
            int y4 = (int) (y1 - rightShift*sinTheta + UpShift2*cosTheta) ;
            
           
            g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x2), Y_StartPoint-(y2));    
            g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x3), Y_StartPoint-(y3));    
            g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x4), Y_StartPoint-(y4));  
            
            g2.drawString(String.valueOf(load_value), X_StartPoint+(x2),Y_StartPoint-(y2+10));
        }else if(load_value < 0){
            //Point(1)
            int x1 = (int) ((X1+ position*cosTheta)*Scale)  + HorizantalMovement;
            int y1 = (int) ((Y1+ position*sinTheta)*Scale)  + VerticalMovement;
        
            //Point(2)
            int x2 = (int) (x1 - ArrowLength*sinTheta);
            int y2 = (int) (y1 + ArrowLength*cosTheta); 
        
            //Point(3)
            int x3 = (int) (x1 + rightShift*cosTheta - UpShift*sinTheta);
            int y3 = (int) (y1 + rightShift*sinTheta + UpShift*cosTheta);
        
            //Point(4)
            int x4 = (int) (x1 - rightShift*cosTheta - UpShift*sinTheta) ;
            int y4 = (int) (y1 - rightShift*sinTheta + UpShift*cosTheta) ;
            
              
            
            
            
            g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x2), Y_StartPoint-(y2));    
            g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x3), Y_StartPoint-(y3));    
            g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x4), Y_StartPoint-(y4));    
            
            g2.drawString(String.valueOf(load_value), X_StartPoint+(x2),Y_StartPoint-(y2+10));
            
            
        }else{
            //Don't doing any thing....
        }
    }
    
    public void DrawTheUniformLoads(double X1,double Y1,double X2,double Y2,double Startposition, double Endposition, double load_value){
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setStroke(new BasicStroke(2));
        Color myColor = new Color(0,102,51);
        g2.setColor(myColor);
        
        double L = Math.sqrt(Math.pow((X2-X1), 2)+Math.pow((Y2-Y1), 2));
        double cosTheta = (X2-X1) / L;
        double sinTheta = (Y2-Y1) / L;
        
       
        
        double ArrowLength = 35;
        double rightShift = 4;
        double UpShift = 13;
        double UpShift2 = 26;
        
        double ArrowSpace = 0.3;
      
        
        double ArrowSpace2 = (Endposition - Startposition)/((int)((Endposition - Startposition)/ArrowSpace));
        
        int x1,x2,x3,x4;
        int y1,y2,y3,y4;
        if(load_value > 0){
            for(double i=Startposition ; i<=Endposition ; i+= ArrowSpace2){
             //Point(1)
             x1 = (int) ((X1+ i*cosTheta)*Scale)  + HorizantalMovement;
             y1 = (int) ((Y1+ i*sinTheta)*Scale)  + VerticalMovement;
        
            //Point(2)
             x2 = (int) (x1 - ArrowLength*sinTheta);
             y2 = (int) (y1 + ArrowLength*cosTheta); 
        
            //Point(3)
             x3 = (int) (x1 + rightShift*cosTheta - UpShift2*sinTheta);
             y3 = (int) (y1 + rightShift*sinTheta + UpShift2*cosTheta);
        
            //Point(4)
             x4 = (int) (x1 - rightShift*cosTheta - UpShift2*sinTheta) ;
             y4 = (int) (y1 - rightShift*sinTheta + UpShift2*cosTheta) ;
            
           
            
            g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x2), Y_StartPoint-(y2));    
            g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x3), Y_StartPoint-(y3));    
            g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x4), Y_StartPoint-(y4));  

            }
            
            //Draw LastArrow because sometimes the drawing will be missing last one where the error refer to rounds in math...
            //Point(1)
             x1 = (int) ((X1+ Endposition*cosTheta)*Scale)  + HorizantalMovement;
             y1 = (int) ((Y1+ Endposition*sinTheta)*Scale)  + VerticalMovement;
        
            //Point(2)
             x2 = (int) (x1 - ArrowLength*sinTheta);
             y2 = (int) (y1 + ArrowLength*cosTheta); 
        
            //Point(3)
             x3 = (int) (x1 + rightShift*cosTheta - UpShift2*sinTheta);
             y3 = (int) (y1 + rightShift*sinTheta + UpShift2*cosTheta);
        
            //Point(4)
             x4 = (int) (x1 - rightShift*cosTheta - UpShift2*sinTheta) ;
             y4 = (int) (y1 - rightShift*sinTheta + UpShift2*cosTheta) ;
            
           
            
            g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x2), Y_StartPoint-(y2));    
            g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x3), Y_StartPoint-(y3));    
            g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x4), Y_StartPoint-(y4));  

            //Draw The Cover Line that close the shape.....
             //Point(1)
             x1 = (int) ((X1+ (Startposition)*cosTheta)*Scale)  + HorizantalMovement;
             y1 = (int) ((Y1+ (Startposition)*sinTheta)*Scale)  + VerticalMovement;
        
            //Point(2)
             x2 = (int) (x1 - (ArrowLength+0)*sinTheta);
             y2 = (int) (y1 + (ArrowLength+0)*cosTheta); 
             
             //Point(3)
             x3 = (int) ((X1+ (Endposition)*cosTheta)*Scale)  + HorizantalMovement;
             y3 = (int) ((Y1+ (Endposition)*sinTheta)*Scale)  + VerticalMovement;
        
            //Point(4)
             x4 = (int) (x3 - (ArrowLength+0)*sinTheta);
             y4 = (int) (y3 + (ArrowLength+0)*cosTheta); 
             
             //Point(5)
             int x5 = (int) (((x2+x4)/2-15) - (ArrowLength+0)*sinTheta);
             int y5 = (int) (((y2+y4)/2-15) + (ArrowLength+0)*cosTheta); 
             
             g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x4), Y_StartPoint-(y4));    
             g2.drawString(String.valueOf(load_value), X_StartPoint+((x5) - 0),Y_StartPoint-((y5)+0));
           
        }else if(load_value < 0){
            for(double i=Startposition ; i<= Endposition ; i+= ArrowSpace2){
                 //Point(1)
             x1 = (int) ((X1+ i*cosTheta)*Scale)  + HorizantalMovement;
             y1 = (int) ((Y1+ i*sinTheta)*Scale)  + VerticalMovement;
        
            //Point(2)
             x2 = (int) (x1 - ArrowLength*sinTheta);
             y2 = (int) (y1 + ArrowLength*cosTheta); 
        
            //Point(3)
             x3 = (int) (x1 + rightShift*cosTheta - UpShift*sinTheta);
             y3 = (int) (y1 + rightShift*sinTheta + UpShift*cosTheta);
        
            //Point(4)
             x4 = (int) (x1 - rightShift*cosTheta - UpShift*sinTheta) ;
             y4 = (int) (y1 - rightShift*sinTheta + UpShift*cosTheta) ;
            
            g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x2), Y_StartPoint-(y2));    
            g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x3), Y_StartPoint-(y3));    
            g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x4), Y_StartPoint-(y4));    
            }
            

             //Draw LastArrow because sometimes the drawing will be missing last one where the error refer to rounds in math...
             //Point(1)
             x1 = (int) ((X1+ Endposition*cosTheta)*Scale)  + HorizantalMovement;
             y1 = (int) ((Y1+ Endposition*sinTheta)*Scale)  + VerticalMovement;
        
            //Point(2)
             x2 = (int) (x1 - ArrowLength*sinTheta);
             y2 = (int) (y1 + ArrowLength*cosTheta); 
        
            //Point(3)
             x3 = (int) (x1 + rightShift*cosTheta - UpShift*sinTheta);
             y3 = (int) (y1 + rightShift*sinTheta + UpShift*cosTheta);
        
            //Point(4)
             x4 = (int) (x1 - rightShift*cosTheta - UpShift*sinTheta) ;
             y4 = (int) (y1 - rightShift*sinTheta + UpShift*cosTheta) ;
            
            g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x2), Y_StartPoint-(y2));    
            g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x3), Y_StartPoint-(y3));    
            g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x4), Y_StartPoint-(y4));    
            
            
            
            
           
            //Draw The Cover Line that close the shape.....
             //Point(1)
             x1 = (int) ((X1+ (Startposition)*cosTheta)*Scale)  + HorizantalMovement;
             y1 = (int) ((Y1+ (Startposition)*sinTheta)*Scale)  + VerticalMovement;
        
            //Point(2)
             x2 = (int) (x1 - (ArrowLength+0)*sinTheta);
             y2 = (int) (y1 + (ArrowLength+0)*cosTheta); 
             
             //Point(3)
             x3 = (int) ((X1+ (Endposition)*cosTheta)*Scale)  + HorizantalMovement;
             y3 = (int) ((Y1+ (Endposition)*sinTheta)*Scale)  + VerticalMovement;
        
            //Point(4)
             x4 = (int) (x3 - (ArrowLength+0)*sinTheta);
             y4 = (int) (y3 + (ArrowLength+0)*cosTheta); 
             
             //Point(5)
             int x5 = (int) (((x2+x4)/2-15) - (ArrowLength+0)*sinTheta);
             int y5 = (int) (((y2+y4)/2-15) + (ArrowLength+0)*cosTheta); 
             
             g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x4), Y_StartPoint-(y4));    
             g2.drawString(String.valueOf(load_value), X_StartPoint+((x5) - 0),Y_StartPoint-((y5)+0));
             
             
        }else{
            //Don't doing any thing....
        }
        
    }
    
    public void DrawTheMomentLoads(double X1,double Y1,double X2,double Y2,double position, double load_value){
             
        Graphics2D g2 = (Graphics2D) this.g;
        //g2.setStroke(new BasicStroke());
        Color myColor = new Color(0,102,51);
        g2.setColor(myColor);
        
        double L = Math.sqrt(Math.pow((X2-X1), 2)+Math.pow((Y2-Y1), 2));
        double cosTheta = (X2-X1) / L;
        double sinTheta = (Y2-Y1) / L;
        
        int radias = (int) (2);
        
        int MomentRadias = (int)(25);
        
        int x1 = (int) ((X1+ position*cosTheta)*Scale)  + HorizantalMovement;
        int y1 = (int) ((Y1+ position*sinTheta)*Scale)  + VerticalMovement;
        
        
        
        for(double i=0 ; i<=MomentRadias ; i=i+0.1){
            int x2 = (int) (x1 + i);
            
            
            int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(i, 2), 0.5);
            int y2 = y1 - y_Shift;
            int y3 = y1 + y_Shift;
            
            g2.drawOval(X_StartPoint+(x2-radias/2), Y_StartPoint-(y2+radias/2), radias, radias);
            g2.drawOval(X_StartPoint+(x2-radias/2), Y_StartPoint-(y3+radias/2), radias, radias);
                
        }
            g2.drawOval(X_StartPoint+((x1+MomentRadias)-radias/2), Y_StartPoint-(y1+radias/2), radias, radias);    
            //Draw the center of the moment.....
            Color myColor2 = Color.BLACK;
            g2.setColor(myColor2);
            g2.drawOval(X_StartPoint+((x1)-2*radias/2), Y_StartPoint-(y1+2*radias/2), (2*radias), (2*radias));       
            
            
            //Draw The Arrow....
            g2.setStroke(new BasicStroke(2));
            if(load_value < 0){
                int x2 = (int) (x1 + 0);
                int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(0, 2), 0.5);
                int y2 = y1 - y_Shift;
               
                int x3 =x2+10;
                int y3 =y2+10;
                int x4 =x2+10;
                int y4 =y2-10;
                
                g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x3), Y_StartPoint-(y3));    
                g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x4), Y_StartPoint-(y4));   
                
            }else if(load_value > 0){
                int x2 = (int) (x1 + 0);
                int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(0, 2), 0.5);
                int y2 = y1 + y_Shift;
               
                int x3 =x2+10;
                int y3 =y2+10;
                int x4 =x2+10;
                int y4 =y2-10;
                
                g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x3), Y_StartPoint-(y3));    
                g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x4), Y_StartPoint-(y4));   
            }
            
            //Draw the String...
              int x2 = (int) (x1 + 0);
              int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(0, 2), 0.5);
              int y2 = y1 - y_Shift;
              
              int x3 =x2+10;
              int y3 =y2+60;   
              
              g2.drawString(String.valueOf(load_value), X_StartPoint+(x3),Y_StartPoint-(y3));
    }
    
    public void DrawTheJointLoads(double X1, double Y1, int loadType, double load_value){
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setStroke(new BasicStroke(4));
        //Color myColor = new Color(340,100,60);
        g2.setColor(Color.BLACK);
        
        int x1 = (int) (X1*Scale) + HorizantalMovement;
        int y1 = (int) (Y1*Scale) + VerticalMovement;
        
        //Herel...................................
        if(loadType == 1){
            if(load_value > 0){
                int x2 = (int) x1+60;
                int y2 = (int) y1;
                
                int x3 = (int) x2-18;
                int y3 = (int) y1+6;
                
                int x4 = (int) x2-18;
                int y4 = (int) y1-6;
                g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x2), Y_StartPoint-(y2));   
                g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x3), Y_StartPoint-(y3));    
                g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x4), Y_StartPoint-(y4)); 
                
               
                g2.drawString(String.valueOf(load_value), X_StartPoint+(x2),Y_StartPoint-(y2+10));
            
            }else if(load_value < 0){
                int x2 = (int) x1+60;
                int y2 = (int) y1;
                
                int x3 = (int) x1+18;
                int y3 = (int) y1+6;
                
                int x4 = (int) x1+18;
                int y4 = (int) y1-6;
                g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x2), Y_StartPoint-(y2));   
                g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x3), Y_StartPoint-(y3));    
                g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x4), Y_StartPoint-(y4));  
                
                g2.drawString(String.valueOf(load_value), X_StartPoint+(x2),Y_StartPoint-(y2+10));

            }
        }
    if(loadType == 2){
            if(load_value > 0){
                int x2 = (int) x1;
                int y2 = (int) y1+60;
                
                int x3 = (int) x2+6;
                int y3 = (int) y2-18;
                
                int x4 = (int) x2-6;
                int y4 = (int) y2-18;
                g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x2), Y_StartPoint-(y2));   
                g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x3), Y_StartPoint-(y3));    
                g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x4), Y_StartPoint-(y4));    
                
                g2.drawString(String.valueOf(load_value), X_StartPoint+(x2),Y_StartPoint-(y2+10));
            }else if(load_value < 0){
                int x2 = (int) x1;
                int y2 = (int) y1+60;
                
                int x3 = (int) x1+6;
                int y3 = (int) y1+18;
                
                int x4 = (int) x1-6;
                int y4 = (int) y1+18;
                g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x2), Y_StartPoint-(y2));   
                g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x3), Y_StartPoint-(y3));    
                g2.drawLine(X_StartPoint+(x1), Y_StartPoint-(y1), X_StartPoint+(x4), Y_StartPoint-(y4));    
               
                g2.drawString(String.valueOf(load_value), X_StartPoint+(x2),Y_StartPoint-(y2+10));
            }
        }        
    
    if(loadType == 3){
            g2.setStroke(new BasicStroke(0));

            int radias = (int) (2);
            int MomentRadias = (int)(25);
        
            //Draw the Moment....
            if(load_value != 0){
                for(double i=0 ; i<=MomentRadias ; i=i+0.1){
                    int x2 = (int) (x1 + i);
           
                    int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(i, 2), 0.5);
                    int y2 = y1 - y_Shift;
                    int y3 = y1 + y_Shift;
            
                    g2.drawOval(X_StartPoint+(x2-radias/2), Y_StartPoint-(y2+radias/2), radias, radias);
                    g2.drawOval(X_StartPoint+(x2-radias/2), Y_StartPoint-(y3+radias/2), radias, radias);
            }
            g2.drawOval(X_StartPoint+((x1+MomentRadias)-radias/2), Y_StartPoint-(y1+radias/2), radias, radias);    
            //Draw the center of the moment.....
            Color myColor2 = Color.BLACK;
            g2.setColor(myColor2);
            g2.drawOval(X_StartPoint+((x1)-2*radias/2), Y_StartPoint-(y1+2*radias/2), (2*radias), (2*radias));    
            
                
            //Draw The Arrow....
            g2.setStroke(new BasicStroke(2));
            if(load_value < 0){
                int x2 = (int) (x1 + 0);
                int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(0, 2), 0.5);
                int y2 = y1 - y_Shift;
               
                int x3 =x2+10;
                int y3 =y2+10;
                int x4 =x2+10;
                int y4 =y2-10;
                
                g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x3), Y_StartPoint-(y3));    
                g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x4), Y_StartPoint-(y4));   
                
            }else if(load_value > 0){
                int x2 = (int) (x1 + 0);
                int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(0, 2), 0.5);
                int y2 = y1 + y_Shift;
               
                int x3 =x2+10;
                int y3 =y2+10;
                int x4 =x2+10;
                int y4 =y2-10;
                
                g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x3), Y_StartPoint-(y3));    
                g2.drawLine(X_StartPoint+(x2), Y_StartPoint-(y2), X_StartPoint+(x4), Y_StartPoint-(y4));   
            }
            
            //Draw the String...
              int x2 = (int) (x1 + 0);
              int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(0, 2), 0.5);
              int y2 = y1 - y_Shift;
              
              int x3 =x2+10;
              int y3 =y2+60;   
              
              g2.drawString(String.valueOf(load_value), X_StartPoint+(x3),Y_StartPoint-(y3));
            
            }
           
            
        
        }        
        
    }
    
    public void DrawJointsNumber(double X, double Y, int JointNumber){
        
        int x = (int) (X*Scale) + HorizantalMovement;
        int y = (int) (Y*Scale) + VerticalMovement;
        
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("default", Font.BOLD, 16));
        g2.drawString(String.valueOf(JointNumber), X_StartPoint+(x-15),Y_StartPoint-(y+5));
    }
    
    public void DrawMemberNumber(double X1, double Y1, double X2, double Y2, int MemberNumber){
        
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setStroke(new BasicStroke(4));
        g2.setFont(new Font("default", Font.BOLD, 16));
        g2.setColor(Color.BLACK);
        
        double L = Math.sqrt(Math.pow((X2-X1), 2)+Math.pow((Y2-Y1), 2));
        double cosTheta = (X2-X1) / L;
        double sinTheta = (Y2-Y1) / L;
        
        double MemberNumber_Position = L/2;
        
        int x = (int) ((X1+ (MemberNumber_Position)*cosTheta)*Scale)  + HorizantalMovement;
        int y = (int) ((Y1+ (MemberNumber_Position)*sinTheta)*Scale)  + VerticalMovement;
        
        g2.drawString(String.valueOf(MemberNumber), X_StartPoint+(x-15),Y_StartPoint-(y+5));
        
    }
    
    
    
    public void DrawFreeLine(double X1,double Y1,double X2,double Y2,Color color,int LineThick){
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setStroke(new BasicStroke(LineThick));
        g2.setColor(color);
        
        int x1 = (int) (X1);
        int y1 = (int) (Y1);
        int x2 = (int) (X2);
        int y2 = (int) (Y2);
       
        
       g2.drawLine(x1,y1,x2,y2);
    }
    public void DrawFreePoints(double X,double Y,Color color, int CircleThick){
         //Convert to 2D to facilitate draw a thicker lines
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(color);
        
        int radias = (int) (5);
        int x1 = (int) (X);
        int y1 = (int) (Y);
        
        x1 = x1 - radias / 2;
        y1 = y1 - radias / 2;
        
       g2.drawOval(x1,y1, radias,radias);       
    }
    public void DrawFreeJointsNumber(double X, double Y, int JointNumber){
        
        int x = (int) (X);
        int y = (int) (Y);
        
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("default", Font.BOLD, 16));
        g2.drawString(String.valueOf(JointNumber), (x-15),(y-5));
    }
    public void DrawFreeTheJointLoads(double X1, double Y1, int loadType, double load_value){
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setStroke(new BasicStroke(2));
        //Color myColor = new Color(340,100,60);
        g2.setColor(Color.BLACK);
        
        int x1 = (int) (X1);
        int y1 = (int) (Y1);
        
        //Herel...................................
        if(loadType == 1){
            if(load_value > 0){
                int x2 = (int) x1+60;
                int y2 = (int) y1;
                
                int x3 = (int) x2-18;
                int y3 = (int) y1-6;
                
                int x4 = (int) x2-18;
                int y4 = (int) y1+6;
                g2.drawLine((x1), (y1), (x2), (y2));   
                g2.drawLine((x2), (y2), (x3), (y3));    
                g2.drawLine((x2), (y2), (x4), (y4)); 
                
               
                g2.drawString(String.valueOf(load_value), (x2),(y2+10));
            
            }else if(load_value < 0){
                int x2 = (int) x1+60;
                int y2 = (int) y1;
                
                int x3 = (int) x1+18;
                int y3 = (int) y1-6;
                
                int x4 = (int) x1+18;
                int y4 = (int) y1+6;
                g2.drawLine((x1), (y1), (x2),(y2));   
                g2.drawLine((x1), (y1), (x3), (y3));    
                g2.drawLine((x1), (y1), (x4), (y4));  
                
                g2.drawString(String.valueOf(load_value), (x2),(y2-10));

            }
        }
    if(loadType == 2){
            if(load_value > 0){
                int x2 = (int) x1;
                int y2 = (int) y1-60;
                
                int x3 = (int) x2+6;
                int y3 = (int) y2+18;
                
                int x4 = (int) x2-6;
                int y4 = (int) y2+18;
                g2.drawLine((x1), (y1), (x2), (y2));   
                g2.drawLine((x2), (y2), (x3), (y3));    
                g2.drawLine((x2), (y2), (x4), (y4));    
                
                g2.drawString(String.valueOf(load_value), (x2),(y2-10));
            }else if(load_value < 0){
                int x2 = (int) x1;
                int y2 = (int) y1-60;
                
                int x3 = (int) x1+6;
                int y3 = (int) y1-18;
                
                int x4 = (int) x1-6;
                int y4 = (int) y1-18;
                g2.drawLine((x1), (y1), (x2), (y2));   
                g2.drawLine((x1), (y1), (x3), (y3));    
                g2.drawLine((x1), (y1), (x4), (y4));    
               
                g2.drawString(String.valueOf(load_value), (x2),(y2-10));
            }
        }        
    
    if(loadType == 3){
            g2.setStroke(new BasicStroke(0));

            int radias = (int) (2);
            int MomentRadias = (int)(25);
        
            //Draw the Moment....
            if(load_value != 0){
                for(double i=0 ; i<=MomentRadias ; i=i+0.1){
                    int x2 = (int) (x1 + i);
           
                    int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(i, 2), 0.5);
                    int y2 = y1 - y_Shift;
                    int y3 = y1 + y_Shift;
            
                    g2.drawOval((x2-radias/2), (y2+radias/2), radias, radias);
                    g2.drawOval((x2-radias/2), (y3+radias/2), radias, radias);
            }
            g2.drawOval(((x1+MomentRadias)-radias/2), (y1+radias/2), radias, radias);    
            //Draw the center of the moment.....
            Color myColor2 = Color.BLACK;
            g2.setColor(myColor2);
            g2.drawOval(((x1)-2*radias/2), (y1-2*radias/2), (2*radias), (2*radias));    
            
                
            //Draw The Arrow....
            g2.setStroke(new BasicStroke(2));
            if(load_value < 0){
                int x2 = (int) (x1 + 0);
                int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(0, 2), 0.5);
                int y2 = y1 + y_Shift;
               
                int x3 =x2+10;
                int y3 =y2-10;
                int x4 =x2+10;
                int y4 =y2+10;
                
                g2.drawLine((x2), (y2), (x3), (y3));    
                g2.drawLine((x2), (y2), (x4), (y4));   
                
            }else if(load_value > 0){
                int x2 = (int) (x1 + 0);
                int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(0, 2), 0.5);
                int y2 = y1 - y_Shift;
               
                int x3 =x2+10;
                int y3 =y2-10;
                int x4 =x2+10;
                int y4 =y2+10;
                
                g2.drawLine((x2), (y2), (x3), (y3));    
                g2.drawLine((x2), (y2), (x4), (y4));   
            }
            
            //Draw the String...
              int x2 = (int) (x1 + 0);
              int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(0, 2), 0.5);
              int y2 = y1 + y_Shift;
              
              int x3 =x2+10;
              int y3 =y2-60;   
              
              g2.drawString(String.valueOf(load_value), (x3),(y3));
            
            }
           
            
        
        }        
        
    }
    public void DrawFreeTheAxialLoads(double X1,double Y1,double X2,double Y2,double position, double load_value){
        
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setStroke(new BasicStroke(2));
        Color myColor = new Color(0,102,51);
        g2.setColor(myColor);
        
        double L = Math.sqrt(Math.pow((X2-X1), 2)+Math.pow((Y2-Y1), 2));
        double cosTheta = (X2-X1) / L;
        double sinTheta = (Y2-Y1) / L;
        
        double ArrowLength = 60;
        double rightShift = 6;
        double UpShift = 20;
        double UpShift2 = 40;
       
        if(load_value > 0){
           //Point(1)
            int x1 = (int) ((X1+ position*cosTheta));
            int y1 = (int) ((Y1- position*sinTheta));
        
            //Point(2)
            int x2 = (int) (x1 - ArrowLength*sinTheta);
            int y2 = (int) (y1 - ArrowLength*cosTheta); 
        
            //Point(3)
            int x3 = (int) (x1 + rightShift*cosTheta - UpShift2*sinTheta);
            int y3 = (int) (y1 - rightShift*sinTheta - UpShift2*cosTheta);
        
            //Point(4)
            int x4 = (int) (x1 - rightShift*cosTheta - UpShift2*sinTheta) ;
            int y4 = (int) (y1 + rightShift*sinTheta - UpShift2*cosTheta) ;
            
           
            g2.drawLine((x1), (y1), (x2), (y2));    
            g2.drawLine((x2), (y2), (x3), (y3));    
            g2.drawLine((x2), (y2), (x4), (y4));  
            
            g2.drawString(String.valueOf(load_value), (x2),(y2-10));
        }else if(load_value < 0){
            //Point(1)
            int x1 = (int) ((X1+ position*cosTheta));
            int y1 = (int) ((Y1- position*sinTheta));
        
            //Point(2)
            int x2 = (int) (x1 - ArrowLength*sinTheta);
            int y2 = (int) (y1 - ArrowLength*cosTheta); 
        
            //Point(3)
            int x3 = (int) (x1 + rightShift*cosTheta - UpShift*sinTheta);
            int y3 = (int) (y1 - rightShift*sinTheta - UpShift*cosTheta);
        
            //Point(4)
            int x4 = (int) (x1 - rightShift*cosTheta - UpShift*sinTheta) ;
            int y4 = (int) (y1 + rightShift*sinTheta - UpShift*cosTheta) ;
            
              
            
            
            
            g2.drawLine((x1), (y1), (x2), (y2));    
            g2.drawLine((x1), (y1), (x3), (y3));    
            g2.drawLine((x1), (y1), (x4), (y4));    
            
            g2.drawString(String.valueOf(load_value), (x2),(y2-10));
            
            
        }else{
            //Don't doing any thing....
        }
    }
    public void DrawFreeTheUniformLoads(double X1,double Y1,double X2,double Y2,double Startposition, double Endposition, double load_value){
        Graphics2D g2 = (Graphics2D) this.g;
        g2.setStroke(new BasicStroke(2));
        Color myColor = new Color(0,102,51);
        g2.setColor(myColor);
        
        double L = Math.sqrt(Math.pow((X2-X1), 2)+Math.pow((Y2-Y1), 2));
        double cosTheta = (X2-X1) / L;
        double sinTheta = (Y2-Y1) / L;
        
       
        
        double ArrowLength = 35;
        double rightShift = 4;
        double UpShift = 13;
        double UpShift2 = 26;
        
        double ArrowSpace = 20;
      
        
        double ArrowSpace2 = (Endposition - Startposition)/((int)((Endposition - Startposition)/ArrowSpace));
        
        int x1,x2,x3,x4;
        int y1,y2,y3,y4;
        if(load_value > 0){
            for(double i=Startposition ; i<=Endposition ; i+= ArrowSpace2){
             //Point(1)
             x1 = (int) ((X1+ i*cosTheta));
             y1 = (int) ((Y1- i*sinTheta));
        
            //Point(2)
             x2 = (int) (x1 - ArrowLength*sinTheta);
             y2 = (int) (y1 - ArrowLength*cosTheta); 
        
            //Point(3)
             x3 = (int) (x1 + rightShift*cosTheta - UpShift2*sinTheta);
             y3 = (int) (y1 - rightShift*sinTheta - UpShift2*cosTheta);
        
            //Point(4)
             x4 = (int) (x1 - rightShift*cosTheta - UpShift2*sinTheta) ;
             y4 = (int) (y1 + rightShift*sinTheta - UpShift2*cosTheta) ;
            
           
            
            g2.drawLine((x1), (y1), (x2), (y2));    
            g2.drawLine((x2), (y2), (x3), (y3));    
            g2.drawLine((x2), (y2), (x4), (y4));  

            }
            
            //Draw LastArrow because sometimes the drawing will be missing last one where the error refer to rounds in math...
            //Point(1)
             x1 = (int) ((X1+ Endposition*cosTheta));
             y1 = (int) ((Y1- Endposition*sinTheta));
        
            //Point(2)
             x2 = (int) (x1 - ArrowLength*sinTheta);
             y2 = (int) (y1 - ArrowLength*cosTheta); 
        
            //Point(3)
             x3 = (int) (x1 + rightShift*cosTheta - UpShift2*sinTheta);
             y3 = (int) (y1 - rightShift*sinTheta - UpShift2*cosTheta);
        
            //Point(4)
             x4 = (int) (x1 - rightShift*cosTheta - UpShift2*sinTheta) ;
             y4 = (int) (y1 + rightShift*sinTheta - UpShift2*cosTheta) ;
            
           
            
            g2.drawLine((x1), (y1), (x2), (y2));    
            g2.drawLine((x2), (y2), (x3), (y3));    
            g2.drawLine((x2), (y2), (x4), (y4));  

            //Draw The Cover Line that close the shape.....
             //Point(1)
             x1 = (int) ((X1+ (Startposition)*cosTheta));
             y1 = (int) ((Y1- (Startposition)*sinTheta));
        
            //Point(2)
             x2 = (int) (x1 - (ArrowLength+0)*sinTheta);
             y2 = (int) (y1 - (ArrowLength+0)*cosTheta); 
             
             //Point(3)
             x3 = (int) ((X1+ (Endposition)*cosTheta));
             y3 = (int) ((Y1- (Endposition)*sinTheta));
        
            //Point(4)
             x4 = (int) (x3 - (ArrowLength+0)*sinTheta);
             y4 = (int) (y3 - (ArrowLength+0)*cosTheta); 
             
             //Point(5)
             int x5 = (int) (((x2+x4)/2-15) - (ArrowLength+0)*sinTheta);
             int y5 = (int) (((y2+y4)/2+15) - (ArrowLength+0)*cosTheta); 
             
             g2.drawLine((x2), (y2), (x4), (y4));    
             g2.drawString(String.valueOf(load_value), ((x5) - 0),((y5)+0));
             
           
        }else if(load_value < 0){
            for(double i=Startposition ; i<= Endposition ; i+= ArrowSpace2){
                 //Point(1)
             x1 = (int) ((X1+ i*cosTheta));
             y1 = (int) ((Y1- i*sinTheta));
        
            //Point(2)
             x2 = (int) (x1 - ArrowLength*sinTheta);
             y2 = (int) (y1 - ArrowLength*cosTheta); 
        
            //Point(3)
             x3 = (int) (x1 + rightShift*cosTheta - UpShift*sinTheta);
             y3 = (int) (y1 - rightShift*sinTheta - UpShift*cosTheta);
        
            //Point(4)
             x4 = (int) (x1 - rightShift*cosTheta - UpShift*sinTheta) ;
             y4 = (int) (y1 + rightShift*sinTheta - UpShift*cosTheta) ;
            
            g2.drawLine((x1), (y1), (x2), (y2));    
            g2.drawLine((x1), (y1), (x3), (y3));    
            g2.drawLine((x1), (y1), (x4), (y4));    
            }
            

             //Draw LastArrow because sometimes the drawing will be missing last one where the error refer to rounds in math...
             //Point(1)
             x1 = (int) ((X1+ Endposition*cosTheta));
             y1 = (int) ((Y1- Endposition*sinTheta));
        
            //Point(2)
             x2 = (int) (x1 - ArrowLength*sinTheta);
             y2 = (int) (y1 - ArrowLength*cosTheta); 
        
            //Point(3)
             x3 = (int) (x1 + rightShift*cosTheta - UpShift*sinTheta);
             y3 = (int) (y1 - rightShift*sinTheta - UpShift*cosTheta);
        
            //Point(4)
             x4 = (int) (x1 - rightShift*cosTheta - UpShift*sinTheta) ;
             y4 = (int) (y1 + rightShift*sinTheta - UpShift*cosTheta) ;
            
            g2.drawLine((x1), (y1), (x2), (y2));    
            g2.drawLine((x1), (y1), (x3), (y3));    
            g2.drawLine((x1), (y1), (x4), (y4));    
            
            
            
            
           
            //Draw The Cover Line that close the shape.....
             //Point(1)
             x1 = (int) ((X1+ (Startposition)*cosTheta));
             y1 = (int) ((Y1- (Startposition)*sinTheta));
        
            //Point(2)
             x2 = (int) (x1 - (ArrowLength+0)*sinTheta);
             y2 = (int) (y1 - (ArrowLength+0)*cosTheta); 
             
             //Point(3)
             x3 = (int) ((X1+ (Endposition)*cosTheta));
             y3 = (int) ((Y1- (Endposition)*sinTheta));
        
            //Point(4)
             x4 = (int) (x3 - (ArrowLength+0)*sinTheta);
             y4 = (int) (y3 - (ArrowLength+0)*cosTheta); 
             
             //Point(5)
             int x5 = (int) (((x2+x4)/2-15) - (ArrowLength+0)*sinTheta);
             int y5 = (int) (((y2+y4)/2+15) - (ArrowLength+0)*cosTheta); 
             
             g2.drawLine((x2), (y2), (x4), (y4));    
             g2.drawString(String.valueOf(load_value), ((x5) - 0),((y5)+0));
             
             
        }else{
            //Don't doing any thing....
        }
        
    }
    public void DrawFreeTheMomentLoads(double X1,double Y1,double X2,double Y2,double position, double load_value){
             
        Graphics2D g2 = (Graphics2D) this.g;
        //g2.setStroke(new BasicStroke());
        Color myColor = new Color(0,102,51);
        g2.setColor(myColor);
        
        double L = Math.sqrt(Math.pow((X2-X1), 2)+Math.pow((Y2-Y1), 2));
        double cosTheta = (X2-X1) / L;
        double sinTheta = (Y2-Y1) / L;
        
        int radias = (int) (2);
        
        int MomentRadias = (int)(25);
        
        int x1 = (int) ((X1+ position*cosTheta));
        int y1 = (int) ((Y1- position*sinTheta));
        
        
        
        for(double i=0 ; i<=MomentRadias ; i=i+0.1){
            int x2 = (int) (x1 + i);
            
            
            int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) - Math.pow(i, 2), 0.5);
            int y2 = y1 - y_Shift;
            int y3 = y1 + y_Shift;
            
            g2.drawOval((x2-radias/2), (y2-radias/2), radias, radias);
            g2.drawOval((x2-radias/2), (y3-radias/2), radias, radias);
                
        }
            g2.drawOval(((x1+MomentRadias)-radias/2), (y1-radias/2), radias, radias);    
            //Draw the center of the moment.....
            Color myColor2 = Color.BLACK;
            g2.setColor(myColor2);
            g2.drawOval(((x1)-2*radias/2), (y1-2*radias/2), (2*radias), (2*radias));       
            
            
            //Draw The Arrow....
            g2.setStroke(new BasicStroke(2));
            if(load_value < 0){
                int x2 = (int) (x1 + 0);
                int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) + Math.pow(0, 2), 0.5);
                int y2 = y1 + y_Shift;
               
                int x3 =x2+10;
                int y3 =y2-10;
                int x4 =x2+10;
                int y4 =y2+10;
                
                g2.drawLine((x2), (y2), (x3), (y3));    
                g2.drawLine((x2), (y2), (x4), (y4));   
                
            }else if(load_value > 0){
                int x2 = (int) (x1 + 0);
                int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) + Math.pow(0, 2), 0.5);
                int y2 = y1 - y_Shift;
               
                int x3 =x2+10;
                int y3 =y2-10;
                int x4 =x2+10;
                int y4 =y2+10;
                
                g2.drawLine((x2), (y2), (x3), (y3));    
                g2.drawLine((x2), (y2), (x4), (y4));   
            }
            
            //Draw the String...
              int x2 = (int) (x1 + 0);
              int y_Shift = (int) Math.pow(Math.pow(MomentRadias, 2) + Math.pow(0, 2), 0.5);
              int y2 = y1 + y_Shift;
              
              int x3 =x2-10;
              int y3 =y2-60;   
              
              g2.drawString(String.valueOf(load_value), (x3),(y3));
    }
}
