package a4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class GameWorld extends Observable {

	// code here to hold and manipulate world objects, handle
	// observer registration, invoke observer callbacks, etc.
	
	private Sound1 gameSound;
	int objectsNum;
	private int dogsNum, catsNum, points, animalObjs, count = 0;
	public GameObjectsCollection gameObjectsCollection;
	public Catcher net;
	private Random r;
	private boolean soundState = true;
	public String soundStateString = "ON";
	public JLabel currentScoreLabel, dogsCapturedLabel, catsCapturedLabel, remainingAnimalsLabel, soundLabel;
	protected int windowHeight, windowWidth;
	protected float windowLeft, windowBottom;

	public GameWorld() {

		points = 0;
		gameSound();

	}

	public void initLayout(int c, int d){ // dogs and cats, and one net, placed
											// in random positions
		playSound();
		dogsNum = d;
		catsNum = c;
		gameObjectsCollection = new GameObjectsCollection();
		objectsNum = catsNum + dogsNum + 1; // total # cats and dogs// used
											// later in toString method
		animalObjs = catsNum + dogsNum;
		net = new NetObject(); // creates net,the catcher
		gameObjectsCollection.add(net);
		for (int i = 0; i < catsNum; i++) {
			Cat cats = new Cat();
			gameObjectsCollection.add(cats); // fills cat vector with cat
												// objects
		}
		for (int i = 0; i < dogsNum; i++) {
			Dog dogs = new Dog();
			gameObjectsCollection.add(dogs); // fills dog vector with dog
												// objects

		}

	}

	// additional methods here to
	// manipulate world objects and
	// related game state data
	public void gameSound(){
		   String soundDir = "." + File.separator + "sounds" + File.separator;
		   String gameSoundFile = "congas.wav";
		   String gameSoundPath = soundDir + gameSoundFile;
		   gameSound = new Sound1(gameSoundPath);
	}
	
	public void playSound(){
		gameSound.play();
	}
	public void stopSound(){
		gameSound.stop();
	}
	public int getPoints() {
		return points;
	}

	public int getDogsCaptured() {
		return net.getDogsScooped();
	}

	public int getCatsCaptured() {
		return net.getCatsScooped();
	}

	public int getRemainingAnimals() {
		return dogsNum + catsNum;
	}
	public int getDogsNum(){
		return dogsNum;
	}
	public int getCatsNum(){
		return catsNum;
	}
	public void heal(){
		Iterator gameElements = gameObjectsCollection.getIterator();
		while (gameElements.hasNext()) {
			Object obj = gameElements.next();
			if(((ISelectable)obj).getSelected() && obj instanceof Dog){
				((Dog)obj).heal();
			}
		}
	}
	public void setSound(boolean on) {
		if (on) {
			soundState = true;
			soundStateString = "ON";
			setChanged();
			notifyObservers();
		} else {
			soundState = false;
			soundStateString = "OFF";
			setChanged();
			notifyObservers();
		}
	}

	public boolean getSoundState() {
		return soundState;
	}

	public String getSoundStateString() {
		return soundStateString;
	}

	public void expandNet() {
		net.expand(); // even # in function expands net
		setChanged();
		notifyObservers();
	}

	public void contractNet() {
		net.contract(); // odd # contracts net
	}
	
	public void moveRight() {
		net.moveRight();
	}

	public void moveLeft() {
		net.moveLeft();
	}

	public void moveUp() {
		net.moveUp();
	}

	public void moveDown() {
		net.moveDown();
	}
	public void scoop() { // scooper

		Iterator gameElements = gameObjectsCollection.getIterator();
		if(soundState) net.scoopSound();
		while (gameElements.hasNext()) {
			Object obj = gameElements.next();
			if (obj instanceof Dog) {
				Dog dog = (Dog) obj;
				Point p = dog.getLocation();
				if (net.contains(p)){
						dogsNum--;
						objectsNum--;
						net.setDogsScooped(); // adds to scoop count
						points += 10 - dog.getScratches(); // subtracts # of scratches from points awarded
						((Dog) obj).dogBark();
						gameElements.remove(); // deletes scooped dog from game
				}
			} else if (obj instanceof Cat) {
				Cat cat = (Cat) obj;
				Point p = cat.getLocation();
				if (net.contains(p)){
						catsNum--;
						objectsNum--;
						net.setCatsScooped();
						points -= 10; // subtracts 10 points from total score
						((Cat) obj).angryMeow();
						gameElements.remove(); // scoops and removes cat
				}
			}

		}
	}

	public void collisionDetector() {
		int wait = 100; //number of ticks before another collision for this object can be detected
		for (int k = 0; k < objectsNum; k++) {
			if (!(gameObjectsCollection.elementAt(k) instanceof Catcher)) {
				if (((Animal) gameObjectsCollection.elementAt(k)).getWait() == 0) {
					for (int i = k + 1; i < objectsNum; i++) {
						if (((Animal) gameObjectsCollection.elementAt(k)).contains(((Animal)gameObjectsCollection.elementAt(i)).getLocation())) {
							if (gameObjectsCollection.elementAt(k) instanceof Cat) {
								if (gameObjectsCollection.elementAt(i) instanceof Cat) {
									if (catsNum < 30) {
										Cat kitten = new Cat();
										objectsNum++;
										catsNum++;
										kitten.changeDirection();
										Point newLocation = ((Cat) gameObjectsCollection.elementAt(k)).getLocation();
										kitten.setKittenLocation(newLocation);
										((Cat) gameObjectsCollection.elementAt(k)).collisionHandler(wait);
										((Cat) gameObjectsCollection.elementAt(i)).collisionHandler(wait);
										if(soundState)((Cat) gameObjectsCollection.elementAt(i)).cuteMeow();
										gameObjectsCollection.add(kitten);
									}
								} else if (gameObjectsCollection.elementAt(i) instanceof Dog) {
									((Dog) gameObjectsCollection.elementAt(i)).collisionHandler(soundState);
									((Cat) gameObjectsCollection.elementAt(k)).collisionHandler(wait);
									if(soundState)((Cat) gameObjectsCollection.elementAt(k)).cuteMeow();
								}

							} else if (gameObjectsCollection.elementAt(k) instanceof Dog) {
								if (gameObjectsCollection.elementAt(i) instanceof Cat && ((Animal) gameObjectsCollection.elementAt(i)).getWait()==0) {
									((Dog) gameObjectsCollection.elementAt(k)).collisionHandler(soundState);
									((Cat) gameObjectsCollection.elementAt(i)).collisionHandler(wait);
									if(soundState)((Cat) gameObjectsCollection.elementAt(i)).cuteMeow();
								}
							}
						}
					}
				}
			}
		}
	}
	public void mouseEventHandler(Point p, int modifier){
		Iterator gameElements = gameObjectsCollection.getIterator();
		int count=0;
		while(gameElements.hasNext()){
			GameObject obj = (GameObject) gameElements.next();
			Point z = new Point((int)p.getX(), (int)p.getY());
			if(obj instanceof Dog){ //if this is taken out, select is applicable to all objects
				if(modifier == 18){//means control is held
					if(obj.contains(z) && obj.getSelected()==false){
						
						((ISelectable)obj).setSelected(true);
						
					}else if(obj.contains(z) && obj.getSelected()){
						
						((ISelectable)obj).setSelected(false);
						
					}else if(!obj.contains(z)&& count==dogsNum-1){//change dogsNum to objectsNum or catsNum depending on what is allowed to be selected
						for(int k=0; k<objectsNum; k++){
							((GameObject) gameObjectsCollection.elementAt(k)).setSelected(false);
						}
						count = 0;
					}else{
						count++;
					}
				}else{
					if(obj.contains(z) && obj.getSelected()==false){
						((ISelectable)obj).setSelected(true);
					}else{
						((ISelectable)obj).setSelected(false);
					}
				}
			}
			setChanged();
			notifyObservers();
		}
	}
	public void tick(int elapsedTime) {// animals move on tick
		Iterator gameElements = gameObjectsCollection.getIterator();
		int counter = -1;
		while (gameElements.hasNext()) {
			counter++;
			Object obj = gameElements.next();
			((ISelectable) obj).setSelected(false);
			if (obj instanceof Cat) {
				((Cat) gameObjectsCollection.elementAt(counter))
						.move(elapsedTime);
			} else if (obj instanceof Dog) {
				if (((Dog) gameObjectsCollection.elementAt(counter))
						.getScratches() < 5) {
					((Dog) gameObjectsCollection.elementAt(counter))
							.move(elapsedTime);
				}
			}
		}
		collisionDetector();
		setChanged();
		notifyObservers();

	}

	public String toString() { // when m is pressed, prints objects attribute
								// data
		Iterator gameElements = gameObjectsCollection.getIterator();
		// Animal animal = new Animal();
		int counter = -1;
		String s = "";
		while (gameElements.hasNext()) {
			counter++;
			gameElements.next();
			s += gameObjectsCollection.elementAt(counter).toString() + "\n";
		}
		return s;
	}

	public void windowInfo(int w, int h, float l, float b) {
		windowWidth = w;
		windowHeight = h;
		windowLeft = l;
		windowBottom = b;
		
	}

}
