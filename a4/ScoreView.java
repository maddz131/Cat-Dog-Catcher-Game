package a4;

import java.util. *;

import javax.swing.*;

class ScoreView extends JPanel implements Observer{
	
	//int points, dogsCaptured, catsCaptured, remainingAnimals;
	GameWorld tw = new GameWorld();
	//JPanel scorePanel = new JPanel();
	public JLabel currentScoreLabel, dogsCapturedLabel, catsCapturedLabel,
	remainingDogsLabel, remainingCatsLabel, soundLabel;
	
	public ScoreView(GameWorld gw){
		this.tw = gw;
		currentScoreLabel = new JLabel("Current Score: " + tw.getPoints());
		dogsCapturedLabel = new JLabel("Dogs Captured: " + tw.getDogsCaptured());
		catsCapturedLabel = new JLabel("Cats Captured: " + tw.getCatsCaptured());
		remainingDogsLabel = new JLabel("Remaining Dogs: " + tw.getDogsNum());
		remainingCatsLabel = new JLabel("Remaining Cats: " + tw.getCatsNum());
		soundLabel = new JLabel("Sound: " + tw.getSoundStateString());
		this.add(currentScoreLabel);
		this.add(dogsCapturedLabel);
		this.add(catsCapturedLabel);
		this.add(remainingDogsLabel);
		this.add(remainingCatsLabel);
		this.add(soundLabel);
	}
	public void update(Observable gw, Object arg){
		// code here to update JLabels from data in the Observable
		
		currentScoreLabel.setText("Current Score: " + tw.getPoints());
		dogsCapturedLabel.setText("Dogs Captured: " + tw.getDogsCaptured());
		catsCapturedLabel.setText("Cats Captured: " + tw.getCatsCaptured());
		remainingDogsLabel.setText("Remaining Dogs: " + tw.getDogsNum());
		remainingCatsLabel.setText("Remaining Cats: " + tw.getCatsNum());
		soundLabel.setText("Sound: " + tw.getSoundStateString());
		
	}
	
}
