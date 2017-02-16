package a4;

import java.util.*;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;

abstract class Animal extends GameObject implements IMoveable, ICollider{
	   
	   protected int dir, catSize, dogSize, radius, x2, y2, x3, y3, speed, collision=0, waitCount=0;
	   protected Point leftPoint, rightPoint, tip;
	   private Random random;
	   Color color;
	   protected AffineTransform myRotation, myTranslation;
	   
	   public Animal(){
	         
	      random = new Random();
	      dir = random.nextInt(359);    //sets random direction, size, and location within specified constraints
	      dogSize = 50;
	      catSize = 25;
	      myRotation = new AffineTransform();
	      myTranslation = new AffineTransform();
	      setLocation();
	      
	   }
	   public void rotate (double radians) {
		   myRotation.rotate (radians);
		   }
	   public void translate (double dx, double dy) {
		   myTranslation.translate(dx, dy);
	   }
	   public void resetTransform() {
		   myRotation.setToIdentity();
		   myTranslation.setToIdentity();
	   }
	   public Point getLocation(){
		   //Point location = new Point(x,y);//((int)myTranslation.getTranslateX(), (int)myTranslation.getTranslateY());
		   return location;//new Point(x,y);
	   }
	public double getX(){
	      return myTranslation.getTranslateX();
	   }
	   public double getY(){
		   return myTranslation.getTranslateY();
	   }

	   public void setSize(int i){   //initial size is set in dog and cat classes
	      dogSize = i;
	   }
	   
	   public int getSize(){
	      return dogSize;
	   }
	   
	   public int getDirection(){
	      return dir;
	   }
	   public void collisionHandler(int b){
		   changeDirection();
		   setWait(b);
	   }
	   public void setWait(int w){ //setting these 2 equal triggers change in dir
		   waitCount = w;
	   }
	   public int getWait(){
		   return waitCount;
	   }
	   public void changeDirection(){
		   if(dir>180){
				  dir-=180;
			  }else{
				  dir+=179;
			  }
	   }
	   public void move(int elapsedTime){       //moves cat and dog positions after "tick"
		  if(outOfBounds()){
			  changeDirection();
		  }
		  int theta = dir;//Math.abs(90- dir);
	      double deltaX = Math.cos(theta)*speed;
	      double deltaY = Math.sin(theta)*speed;
	      x += deltaX;
	      y += deltaY;
	      location = new Point(x,y);
	      if(waitCount>0)waitCount--;
	      //collisionControl(1);
	  }
	   public boolean outOfBounds(){
		   boolean state = false;
		   if(x <= 0 || x>=800 || y <= 100 || y >= 500){
			   state = true;
		   }
		   return state;
	   }

}
