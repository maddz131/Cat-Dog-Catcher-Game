package a4;

import java.io.File;
import java.util.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.*;
import javax.imageio.ImageIO;

import java.io.IOException;

class Dog extends Animal implements IDrawable, ISelectable{
	   
	   private Color color;
	   private int scratches, radius, size;
	   private Sound1 dogCollisionSound ;
	   private BufferedImage dogImage;
	   
	   public Dog() {       
	   
	            color = Color.red;
	            scratches = 0;
	            speed = 5;
	            radius = dogSize/2;
	            size = dogSize;
	            mySound();
	            //setImage();
	            
	   }
	   /*private void setImage() throws IOException{
		   dogImage = ImageIO.read(new File("C:\\Users\\Madeline\\workspace\\csc133\\src\\a4\\dog2.jpe"));
	   }*/
	   public void setLocation(){
		   Random random = new Random();
		      x = random.nextInt(750);//gets location between 0.0 and 1024.0
		      y = random.nextInt(400);
	      
	    }
	   public void mySound(){
		   String soundDir = "." + File.separator + "sounds" + File.separator ;
		   String collisionSoundFile = "dogBark.wav" ;
		   String collisionSoundPath = soundDir + collisionSoundFile ;
		   dogCollisionSound = new Sound1(collisionSoundPath) ;
	   }
	   public void collisionHandler(boolean sound){
		   if(speed>0){
			   decreaseSpeed();
			   decreaseSize();
			   setColor();
			   setScratches();
			   if(sound) dogBark();
		   }
	   }
	   public void setColor(){    //used when dog has been scratched to change color
		  switch(speed){
		   case 4: color= Color.magenta.darker(); break;
		   case 3: color= Color.magenta; break;
		   case 2: color= Color.pink; break;
		   case 1: color= Color.gray; break;
		   default: color = Color.gray; break;
		  }
	   }
	   
	   public Color getColor(){
		   return color;
	   }
	   public void decreaseSize(){   //initial size is set in dog and cat classes
		   if(size>20){
			   size-=(size/8);
		   }
	   }
	   public void setSize(int s){
		   size = s;
	   }
	   public int getSize(){
		   return size;
	   }
	   public void decreaseSpeed(){    //used when dog has been scrathed to decrease speed
		   speed--;
	   }
	   public void setSpeed(int s){
		   speed = s;
	   }
	   public int getSpeed(){
		   return speed;
	   }
	   public void setScratches(){
		   scratches++;
	   }
	   public int getScratches(){
	      return scratches;
	   }
	   public void heal(){
		   scratches = 0;
		   color = Color.red;
		   speed = 5;
		   size = dogSize;
	   }
	   
	   public void dogBark(){ //setting these 2 equal triggers change in dir
		   dogCollisionSound.play();
	   }
	   public boolean contains(Point p){
		   int px = p.x;
		   int py = p.y;
		   if(Math.pow((px - x), 2) + Math.pow((py - y), 2) <= Math.pow(radius+2, 2)){
			   return true;
		   }else {
			   return false;
		   }
		   
	   }
	   public void draw(Graphics2D g2d){
		   //draws color and size
		   radius=size/2;
		   AffineTransform save = g2d.getTransform();
		   this.translate(x,y);
		   g2d.transform(myTranslation);
		   if(!getSelected()){
			   g2d.setColor(color);
			   Point boxCorner = new Point (-radius, -radius);
			   g2d.rotate(dir+135);
			   g2d.fillOval(boxCorner.x, boxCorner.y, size, 2*size/3);
			   Feet f0 = new Feet(); f0.translate((size/5)*2,0); f0.scale (1,1);
			   f0.setColor(color); f0.setSize(size);
			   f0.draw(g2d);
			   Feet f1 = new Feet(); f1.translate((size/5)*2,0); f1.scale (-1,1);
			   f1.setColor(color); f1.setSize(size);
			   f1.draw(g2d);
			   Feet f2 = new Feet(); f2.translate((size/5)*2,size/3); f2.scale (1,-1);
			   f2.setColor(color); f2.setSize(size);
			   f2.draw(g2d);
			   Feet f3 = new Feet(); f3.translate((size/5)*2,size/3); f3.scale (-1,-1);
			   f3.setColor(color); f3.setSize(size);
			   f3.draw(g2d);
			   Head h = new Head(); h.translate(size/8,size/3*2); h.scale (-1.5,1);
			   h.setColor(color);  h.rotate(90);
			   h.draw(g2d);
			   
		   }else{
			   color = Color.blue;
			   g2d.setColor(color);
			   Point boxCorner = new Point (-radius, -radius);
			   g2d.rotate(dir+135);
			   g2d.fillOval(boxCorner.x, boxCorner.y, size, 2*size/3);
			   Feet f0 = new Feet(); f0.translate((size/5)*2,0); f0.scale (1,1);
			   f0.setColor(color); f0.setSize(size);
			   f0.draw(g2d);
			   Feet f1 = new Feet(); f1.translate((size/5)*2,0); f1.scale (-1,1);
			   f1.setColor(color); f1.setSize(size);
			   f1.draw(g2d);
			   Feet f2 = new Feet(); f2.translate((size/5)*2,size/3); f2.scale (1,-1);
			   f2.setColor(color); f2.setSize(size);
			   f2.draw(g2d);
			   Feet f3 = new Feet(); f3.translate((size/5)*2,size/3); f3.scale (-1,-1);
			   f3.setColor(color); f3.setSize(size);
			   f3.draw(g2d);
			   Head h = new Head(); h.translate(size/8,size/3*2); h.scale (-1.5,1);
			   h.setColor(color);  h.rotate(90);
			   h.draw(g2d);
			   color = Color.red;
		   }
		   //g.drawImage(dogImage, x, y, size, size, null); 
		   resetTransform();
		   g2d.setTransform(save);
	   }
	   public void setSelected(boolean yesno){
			selected = yesno;
		}
		public boolean getSelected(){
			return selected;
		}
	   public String toString(){
	      String s = "Dog: loc=(" + getX() + "," + getY() + ") color= " + color + " size= " +  getSize() + " speed= " + speed + " dir= " + getDirection() + " scratches: " + scratches;
	      return s;
	   }

}
class Feet{
	
	private Point top, bottomLeft, bottomRight;
	int size;
	private Color myColor ;
	private AffineTransform myTranslation ;
	private AffineTransform myRotation ;
	private AffineTransform myScale ;
	
	public Feet(){
		
		myTranslation = new AffineTransform();
		myRotation = new AffineTransform();
		myScale = new AffineTransform();
	}
	public void setColor(Color c){
		myColor = c;
	}
	public void setSize(int s){
		size = s;
	}
	public void rotate (double degrees) {
		myRotation.rotate (Math.toRadians(degrees));
		}
		public void scale (double sx, double sy) {
		myScale.scale (sx, sy);
		}
		public void translate (double dx, double dy) {
		myTranslation.translate (dx, dy);
		}
		public void draw (Graphics2D g2d) {
			
		// save the current graphics transform for later restoration
		AffineTransform saveAT = g2d.getTransform() ;
		g2d.transform(myRotation);
		g2d.transform(myScale);
		g2d.transform(myTranslation);
		g2d.setColor(myColor);
		g2d.fillOval (0, 0, size/4, size/4) ;
		// restore the old graphics transform (remove this shape's transform)
		g2d.setTransform (saveAT) ;
	} 
}
class Head{
	
	private Point top, bottomLeft, bottomRight ;
	private Color myColor ;
	private AffineTransform myTranslation ;
	private AffineTransform myRotation ;
	private AffineTransform myScale ;
	
	public Head(){
		
		top = new Point (0, 10);
		bottomLeft = new Point (-5, -10);
		bottomRight = new Point (5, -10);
		myTranslation = new AffineTransform();
		myRotation = new AffineTransform();
		myScale = new AffineTransform();
	}
	
	public void rotate (double degrees) {
		myRotation.rotate (Math.toRadians(degrees));
	}
	public void scale (double sx, double sy) {
		myScale.scale (sx, sy);
	}
	public void translate (double dx, double dy) {
		myTranslation.translate (dx, dy);
	}
	public void setColor(Color c){
		myColor = c;
	}
	public void draw (Graphics2D g2d) {
		
		// save the current graphics transform for later restoration
		AffineTransform saveAT = g2d.getTransform();
		g2d.transform(myRotation);
		g2d.transform(myScale);
		g2d.transform(myTranslation);
		g2d.setColor(myColor);
		g2d.fillPolygon (new int[] {bottomLeft.x, top.x, bottomRight.x}, new int[] {bottomLeft.y, top.y, bottomRight.y}, 3);
		// restore the old graphics transform (remove this shape's transform)
		g2d.setTransform (saveAT) ;
		
	}
}