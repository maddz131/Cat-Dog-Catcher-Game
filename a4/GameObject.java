package a4;

import java.awt.Point;
import java.awt.Color;
import java.awt.*;

abstract class GameObject implements IDrawable, ISelectable, ICollider{
		protected int x, y;        //variables used in every class
		protected Point location; 
	    protected boolean selected=false;
		public GameObject(){
		
		}
		public void setSelected(boolean yesno){
			selected = yesno;
		}
		public boolean getSelected(){
			return selected;
		}
		public abstract void setColor();
		public abstract Color getColor();
		public abstract void setLocation();
		public abstract Point getLocation();
		public abstract boolean contains(Point p);
		public abstract double getX();
		public abstract double getY();
		//public abstract void setSize(int i);
		public abstract int getSize();

}
