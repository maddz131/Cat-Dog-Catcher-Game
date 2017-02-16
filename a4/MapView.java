package a4;

import java.util. *;
import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;
import javax.swing.border.EtchedBorder;


class MapView extends JPanel implements Observer{
	
	GameWorld tw = new GameWorld();
	private AffineTransform worldToND, ndToScreen, vtm;
	
	public MapView(GameWorld gw){
		this.tw = gw;
		this.setBorder(new EtchedBorder());
	}
	
	public void update(Observable gw, Object arg){
		// code here to output current map information (based on 
		// the data in the Observable) to the console 
		this.setOpaque(true);
		this.repaint();
	}
	//this method changes the window boundaries to be smaller and causes a redraw
	/*public void zoomIn() {
	double h = windowTop - windowBottom;
	double w = windowRight - windowLeft;
	windowLeft += w*0.05;
	windowRight -= w*0.05;
	windowTop -= h*0.05;
	windowBottom += h*0.05;
	this.repaint();
	}*/
	public AffineTransform buildWorldToNDXform(int width, int height, float leftAlignment, float bottomAlignment){
		AffineTransform nD = new AffineTransform();
		int xnd = (int) (getX() - leftAlignment*(1/width-leftAlignment));
		int ynd = (int) (getY() - bottomAlignment*(1/height-bottomAlignment));
		nD.translate(xnd, ynd);
		return nD;
	}
	public AffineTransform buildNDToScreenXform(int windowWidth, int windowHeight){
		AffineTransform screen = new AffineTransform();
		int xs = (int) (worldToND.getTranslateX()*(-windowWidth))+windowWidth;
		int ys = (int) (worldToND.getTranslateY()*(-windowHeight))+windowHeight;
		screen.translate(xs, ys);
		return screen;
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform saveAT = g2d.getTransform();
		//vtm = new AffineTransform();
		//vtm.translate(0,this.getHeight());
		//vtm.scale(1, -1);
		worldToND = buildWorldToNDXform(MapView.WIDTH,MapView.HEIGHT,MapView.LEFT_ALIGNMENT,MapView.BOTTOM_ALIGNMENT);
		ndToScreen = buildNDToScreenXform(tw.windowWidth,tw.windowHeight);
		vtm = (AffineTransform) ndToScreen.clone(); 
		vtm.concatenate(worldToND);
		g2d.transform(vtm);
		for(int i = 0; i < tw.objectsNum; i++){
			((GameObject) tw.gameObjectsCollection.elementAt(i)).draw(g2d);
		}
		g2d.setTransform(saveAT);
	
	}
}
