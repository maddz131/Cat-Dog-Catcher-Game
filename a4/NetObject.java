package a4;

import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;

class NetObject extends Catcher{
	
	   private Color color;
	   private int sideLength=0;
	   
	   public NetObject(){
	      
	      color = Color.gray;
	      sideLength();
	   }
	   
	   public void setLocation(){
	      Random random = new Random();
	      x = random.nextInt(750);//*500.0;//between 0.0 and 1024.0
	      y = random.nextInt(400);//still need to account for objects being contained w/in world
	      location = new Point(x,y);
	   }
	   
	   public void setColor(){
	      
	   }
	   
	   public Color getColor(){
	      return color;
	   }
	   
	   public int sideLength(){
		   sideLength = (int)Math.sqrt(size);
		   return sideLength;
	   }
	   
	   public boolean contains(Point p){
		   if(p.getX() < x +sideLength/2 && p.getX() > x - sideLength/2 && p.getY() < y + sideLength/2 && p.getY()> y - sideLength/2){ // checks if it's within net bounds
				return true;
		   }else{
			   return false;
		   }
	   }
	   public void draw(Graphics2D g2d){
		   //draws color and size
		   sideLength();
		   g2d.drawRect(x-(sideLength/2),y-(sideLength/2), sideLength, sideLength);
	   }
	   
	   public String toString(){
	   
	      String s = "Net: loc=(" + getX() + "," + getY() + ") color=" + color + " size=" + getSize();
	      return s;
	   
	   }


}
