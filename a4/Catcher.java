package a4;

import java.awt.Point;
import java.io.File;

abstract class Catcher extends GameObject implements IGuidable{
	   
	   protected int dogsScooped, catsScooped, size;    //assuming will be used for every catcher
	   private Sound1 scoopSound;
	   public Catcher(){
	   
	      size = 6000;
	      dogsScooped = 0;
	      catsScooped = 0;
	      setLocation();
	      mySound();
	      
	  }
	   public void mySound(){
		   String soundDir = "." + File.separator + "sounds" + File.separator;
		   String scoopSoundFile = "scoopSound.wav";
		   String scoopSoundPath = soundDir + scoopSoundFile;
		   scoopSound = new Sound1(scoopSoundPath);
	   }
	   public void setCatsScooped(){
	      catsScooped++;
	   }
	   
	   public int getCatsScooped(){
		      return catsScooped;
	   }
	   
	   public void setDogsScooped(){
		   dogsScooped++;
	   }
	   
	   public int getDogsScooped(){
	      return dogsScooped;
	   }
	   public void scoopSound(){ //setting these 2 equal triggers change in dir
		   scoopSound.play();
	   }
	   public void expand(){
		   size+=400;
	   }
	   public void contract(){
		   if(size>400){
			   size-=400;
		   }
	   }
	   public int getSize(){
	      return size;
	   }
	   
	   public Point getLocation(){
	      return location;
	   }
	   
	   public double getX(){
	      return location.getX();
	   }
	   
	   public double getY(){
	      return location.getY();
	   }
	   
	   public Point moveUp(){
	      y = (int)location.getY()-20;
	      location = new Point(x,y);
	      return location;
	   }
	   
	   public Point moveDown(){
	      y = (int)location.getY()+20;
	      location = new Point(x,y);
	      return location;
	   }
	   
	   public Point moveLeft(){
	      x = (int)location.getX()-20;
	      location = new Point(x,y);
	      return location;
	   }
	   
	   public Point moveRight(){
	      x = (int)location.getX()+20;
	      location = new Point(x,y);
	      return location;
	   }

	public boolean contains(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

}
