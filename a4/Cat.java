package a4;

import java.util.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;

class Cat extends Animal implements IDrawable{
	 
	   private Color color;
	   private Sound1 dogCollisionSound, catCollisionSound;
	   private int radius;
	        
	   public Cat(){   
	   
	            color = Color.black;
	            speed = 5;
	            mySound();
	            radius = catSize/2;
	   }
	   public void mySound(){
		   String soundDir = "." + File.separator + "sounds" + File.separator;
		   String collisionSoundFile = "angryMeow.wav";
		   String collisionSoundFile2 = "cuteMeow.wav";
		   String collisionSoundPath = soundDir + collisionSoundFile;
		   String collisionSoundPath2 = soundDir + collisionSoundFile2;
		   dogCollisionSound = new Sound1(collisionSoundPath);
		   catCollisionSound = new Sound1(collisionSoundPath2);
	   }
	   public void setLocation(){
		   Random random = new Random();
		      x = random.nextInt(750);
		      y = random.nextInt(400);
		      translate(x,y);
		      	      
	   } 
	   
	   public void setKittenLocation(Point parent){
		  x = (int)parent.getX();	//add five so it isn't created right on it's parents, causing a collision
		  y = (int)parent.getY();
		  location = new Point(x+40,y+40);
	   }
	   public void setColor(){
		   color = new Color(5,98,130);
	   }
	   
	   public Color getColor(){
	      return color;
	   } 
	   public int getSize(){
		      return catSize;
	   }
	   public void setSpeed(){
	   
	   }
	   
	   public int getSpeed(){
	      return speed;
	   }
	   
	   public void angryMeow(){ //setting these 2 equal triggers change in dir
		   dogCollisionSound.play();
	   }
	   
	   public void cuteMeow(){ //setting these 2 equal triggers change in dir
		   catCollisionSound.play();
	   }
	   
	   public boolean contains(Point p){
		   if(Math.pow((p.getX() - x), 2) + Math.pow((p.getY() - y), 2) <= Math.pow(radius+2, 2)){
			   //System.out.println("x1" + p.getX() + " x2" + x + " y1" + p.getY() + " y2" + y);
			   return true;
		   }else {
			   return false;
		   }
	   }
	   
	   public void draw(Graphics2D g2d){
		   //draws color and catSize
		   
		   AffineTransform save = g2d.getTransform();
		   this.translate(x,y);
		   this.rotate(dir+145);
		   g2d.transform(myTranslation);
		   g2d.transform(myRotation);
		   leftPoint =  new Point(- catSize/2,-(int)(((catSize)/2)*Math.sqrt(3))/2);
		   rightPoint = new Point(catSize/2, leftPoint.y);
		   tip = new Point(0,(int)(((catSize)/2)*Math.sqrt(3))/2);
		   if(!getSelected()){
			   g2d.setColor(Color.green);
			   g2d.fillPolygon(new int[] {leftPoint.x, 0, rightPoint.x}, new int[] {leftPoint.y, tip.y, rightPoint.y}, 3);
		   }else{
			   g2d.setColor(Color.blue);
			   g2d.fillPolygon(new int[] {leftPoint.x, 0, rightPoint.x}, new int[] {leftPoint.y, tip.y, rightPoint.y}, 3);
		   }
		   resetTransform();
		   g2d.setTransform(save);
	   }
	   
	   public String toString(){
	      String s = "Cat: loc=(" + getX() + "," + getY() + ") color=" + color + " catSize=" + getSize() + " speed=" + speed + " dir= " + getDirection();
	      return s;
	   }

	   
}
