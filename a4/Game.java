package a4;

import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

class Game extends JFrame implements ActionListener, MouseListener{
	   private String name = "Madeline Lewis-Whitfield";
	   private String course = "CSC 133- Object Oriented Computer Graphics";
	   private int assignmentNum = 2;
	   
	   private Scanner read = new Scanner(System.in);
	   private GameWorld gw = new GameWorld();; 
	   private int dogs;
	   private int cats;
	   public MapView mv;	//n
	   private ScoreView sv;	//n
	   private JFrame frame = new JFrame("Dog Catcher");	//n
	   protected JPanel mainFrame;	//n
	   private JButton moveRightButton, moveLeftButton, moveUpButton, moveDownButton, scoopButton, expandNetButton, contractNetButton, healButton, pauseButton;
	   private boolean playState = true;
	   private BorderLayout contentLayout = new BorderLayout(0,60);	//n
	   public int elapsedTime= 1;
	   private Timer timer = new Timer(1000, this);
	   
	   public Game(){ 

	      System.out.print("How many cats would you like?  ");
	      cats = read.nextInt();
	      System.out.print("How many dogs would you like?  ");
	      dogs = read.nextInt();
	      gw.initLayout(cats, dogs);

	      mv = new MapView(gw); // create an “Observer” for the map 
	      sv = new ScoreView(gw); // create an “Observer” for the game state data 
	      gw.addObserver(mv); // register the map observer 
	      gw.addObserver(sv); // register the score observer 
	      //System.out.print(mv.getSize().getHeight() + " " + mv.getSize().getWidth());
	      // code here to create menus, create Command objects for each command, 
	      // add commands to Command menu, create a control panel for the buttons, 
	      // add buttons to the control panel, add commands to the buttons, and 
	      // add control panel, MapView panel, and ScoreView panel to the frame 
	      //setVisible(true); 
	      // some Swing methods will only function when the frame is visible!
	      
	      mainFrame = (JPanel)frame.getContentPane();
	      mainFrame.setLayout(contentLayout);
	      mainFrame.setPreferredSize(new Dimension(900, 600));
	      frame.pack();
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setVisible(false);
	      mv.addMouseListener(this);
	      mainFrame.add(sv, BorderLayout.SOUTH);
	      mainFrame.add(mv, BorderLayout.CENTER);
	      //System.out.print(mv.getHeight() + " " + mv.preferredSize().getWidth());
	      
	      //panels and menus
	      JMenuBar menuBar = new JMenuBar();
	      JMenu fileMenu = new JMenu("File");
	      //JPanel scorePanel = new JPanel();
	      JMenu commandsMenu = new JMenu("Commands");
	      JPanel commandsButtons = new JPanel();
	      commandsButtons.setLayout(new GridLayout(10,1));
	      
	      //Command Buttons
	      moveRightButton = new JButton("Move Right");
	      moveLeftButton = new JButton("Move Left");
	      moveUpButton = new JButton("Move Up");
	      moveDownButton = new JButton("Move Down");
	      scoopButton = new JButton ("Scoop");
	      expandNetButton = new JButton ("Expand Net");
	      contractNetButton = new JButton ("Contract Net");
	      healButton = new JButton("Heal");
	   	  pauseButton = new JButton("Pause");
	   	  
	      //Command menu items
	      JMenuItem expandNetItem = new JMenuItem("e");
	      JMenuItem contractNetItem = new JMenuItem("c");
	      JMenuItem quitItem = new JMenuItem("q");
	      	      
	      //file menu items
	      
	      JMenuItem newItem = new JMenuItem("New");
	      JMenuItem saveItem = new JMenuItem("Save");
	      JMenuItem undoItem = new JMenuItem("Undo");
	      JMenu soundSubMenu = new JMenu("Sound");
	      JCheckBoxMenuItem soundStateItem = new JCheckBoxMenuItem("Sound On");
	      JMenuItem aboutItem = new JMenuItem("About");
	  	//adding menu items and buttons to their respective menus and panels
		  menuBar.add(fileMenu);
		  menuBar.add(commandsMenu);
		  
		  fileMenu.add(newItem);
		  fileMenu.add(saveItem);
		  fileMenu.add(undoItem);
		  soundSubMenu.add(soundStateItem);
		  fileMenu.add(soundSubMenu);
		  fileMenu.add(aboutItem);

		  commandsMenu.add(expandNetItem);
		  commandsMenu.add(contractNetItem);
		  commandsMenu.add(quitItem);
		  
		  commandsButtons.add(moveRightButton);
		  commandsButtons.add(moveLeftButton);
		  commandsButtons.add(moveUpButton);
		  commandsButtons.add(moveDownButton);
		  commandsButtons.add(scoopButton);
		  commandsButtons.add(expandNetButton);
		  commandsButtons.add(contractNetButton);
		  commandsButtons.add(healButton);
		  commandsButtons.add(pauseButton);
		  
		  mainFrame.add(menuBar, BorderLayout.NORTH);
		  mainFrame.add(commandsButtons, BorderLayout.WEST);
		  mainFrame.add(sv, BorderLayout.SOUTH);
	      mainFrame.add(mv, BorderLayout.CENTER);
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setVisible(true);
	      
	    //connect button and menu items to their command objects
	      int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
	      ActionMap amap = mainFrame.getActionMap();
	      InputMap imap = mainFrame.getInputMap(mapName);

	      AboutCommand aboutCommand = new AboutCommand("About");
	      aboutItem.setAction(aboutCommand);
	      
	      SoundCommand soundCommand = new SoundCommand("OFF", gw);
	      soundStateItem.setAction(soundCommand);
	      
	      MoveRightCommand moveRightCommand = new MoveRightCommand("Move Right", gw);
	      moveRightButton.setAction(moveRightCommand);
	      KeyStroke rightKey = KeyStroke.getKeyStroke("RIGHT");
	      imap.put(rightKey, "Move Right"); //hashtable(k,v)
	      amap.put("Move Right", moveRightCommand); //hashtable(k,v)
	      
	      MoveLeftCommand moveLeftCommand = new MoveLeftCommand("Move Left", gw);
	      moveLeftButton.setAction(moveLeftCommand);
	      KeyStroke leftKey = KeyStroke.getKeyStroke("LEFT");
	      imap.put(leftKey, "Move Left"); //hashtable(k,v)
	      amap.put("Move Left", moveLeftCommand); //hashtable(k,v)
	      
	      MoveUpCommand moveUpCommand = new MoveUpCommand("Move Up", gw);
	      moveUpButton.setAction(moveUpCommand);
	      KeyStroke upKey = KeyStroke.getKeyStroke("UP");
	      imap.put(upKey, "Move Up"); //hashtable(k,v)
	      amap.put("Move Up", moveUpCommand); //hashtable(k,v)
	      
	      MoveDownCommand moveDownCommand = new MoveDownCommand("Move Down", gw);
	      moveDownButton.setAction(moveDownCommand);
	      KeyStroke downKey = KeyStroke.getKeyStroke("DOWN");
	      imap.put(downKey, "Move Down"); //hashtable(k,v)
	      amap.put("Move Down", moveDownCommand); //hashtable(k,v)
	      
	      ScoopCommand scoopCommand = new ScoopCommand("Scoop", gw);
	      scoopButton.setAction(scoopCommand);
	      KeyStroke sKey = KeyStroke.getKeyStroke('s');
	      imap.put(sKey, "Scoop"); //hastable(k,v)
	      amap.put("Scoop", scoopCommand); //hashtable(k,v)
	      
	      ExpandNetCommand expandNetCommand = new ExpandNetCommand("Expand Net", gw);
	      expandNetButton.setAction(expandNetCommand);
	      expandNetItem.setAction(expandNetCommand);
	      KeyStroke eKey = KeyStroke.getKeyStroke('e');
	      imap.put(eKey, "Expand Net"); //hastable(k,v)
	      amap.put("Expand Net", expandNetCommand); //hashtable(k,v)
	      
	      ContractNetCommand contractNetCommand = new ContractNetCommand("Contract Net", gw);
	      contractNetButton.setAction(contractNetCommand);
	      contractNetItem.setAction(contractNetCommand);
	      KeyStroke cKey = KeyStroke.getKeyStroke('c');
	      imap.put(cKey, "Contract Net"); //hastable(k,v)
	      amap.put("Contract Net", contractNetCommand); //hashtable(k,v)
			
		  QuitCommand quitCommand = new QuitCommand("Quit");
		  quitItem.setAction(quitCommand);
		  KeyStroke qKey = KeyStroke.getKeyStroke('q');
		  imap.put(qKey, "quit"); //hashtable(k,v)
		  amap.put("quit", quitCommand); //hashtable(k,v)
		  
		  //NewCommand newCommand = new NewCommand("New");
		  //newItem.setAction(newCommand);
		  
		  HealCommand healCommand = new HealCommand("Heal");
		  healButton.setAction(healCommand);
		  
		  PauseCommand pauseCommand = new PauseCommand("Pause");
		  pauseButton.setAction(pauseCommand);
		  
		  mainFrame.requestFocus();
		  ActionListener actionListener= new ActionListener(){ 
	  			public void actionPerformed(ActionEvent e) {  
	  				if(playState()){
	  					healButton.setEnabled(false);
	  					gw.tick(elapsedTime);
	  					gw.windowInfo(Game.WIDTH, Game.HEIGHT, Game.LEFT_ALIGNMENT, Game.BOTTOM_ALIGNMENT);
	  					//if pause is not selected
	  				}
	  			}
		  };
		      timer = new Timer (20,actionListener);
		      timer.start();
	   }
	   public void mousePressed(MouseEvent e) {
		   if(!playState()){
			   int i = e.getModifiers();
			   Point p = e.getPoint();
			   gw.mouseEventHandler(p,i);
		   }
	   }
	   public void pauseEnabled(){
		   playState = false;
		   moveRightButton.setEnabled(false);
		   moveLeftButton.setEnabled(false);
		   moveUpButton.setEnabled(false);
		   moveDownButton.setEnabled(false);
		   scoopButton.setEnabled(false);
		   expandNetButton.setEnabled(false);
		   contractNetButton.setEnabled(false);
		   healButton.setEnabled(true);
		   gw.stopSound();
	   }
	   public void playEnabled(){
		   playState = true;
		   moveRightButton.setEnabled(true);
		   moveLeftButton.setEnabled(true);
		   moveUpButton.setEnabled(true);
		   moveDownButton.setEnabled(true);
		   scoopButton.setEnabled(true);
		   expandNetButton.setEnabled(true);
		   contractNetButton.setEnabled(true);
		   healButton.setEnabled(false);
		   gw.playSound();
	   }
	   public boolean playState(){
		   return playState;
	   }
	   public void changeGameState(){
		   if(pauseButton.getText()=="Pause"){
			   pauseEnabled();	
			   pauseButton.setText("Play");
		   }else{
			   playEnabled();
			   pauseButton.setText("Pause");
		   }
	   }
	      // code here to create menus, create Command objects for each command, 
	      // add commands to Command menu, create a control panel for the buttons, 
	      // add buttons to the control panel, add commands to the buttons, and 
	      // add control panel, MapView panel, and ScoreView panel to the frame 
	      // some Swing methods will only function when the frame is visible! 
	   
	   class PauseCommand extends AbstractAction{
		   
		   public PauseCommand(String title){
			   super(title);
		   }
		   
		   public void actionPerformed(ActionEvent e) {
			   
			   changeGameState();		
			}
	   }
	   
	   class HealCommand extends AbstractAction{
		   public HealCommand(String title){
			   super(title);
		   }
		   public void actionPerformed(ActionEvent e) {
			   
			   gw.heal();		
			}
	   }
	   class AboutCommand extends AbstractAction{
		   
		   public AboutCommand(String title){
			   super(title);
		   }
		   
		   public void actionPerformed(ActionEvent e){
			   JOptionPane.showMessageDialog(mainFrame, "Name: " + name + "\nCourse: " + course + "\nAssinment: " + assignmentNum);
			   
		   }
	   }
	   class SoundCommand extends AbstractAction{
			
		   public SoundCommand(String title, GameWorld gw){
				super(title);
			}
		   public void actionPerformed(ActionEvent e) {
			   if(gw.getSoundState()){
				   gw.setSound(false);
			   }
			   else{
				   gw.setSound(true);
			   }
				
			}
			
		}
	   class MoveRightCommand extends AbstractAction{
	
			public MoveRightCommand(String title, GameWorld gw){
				super(title);
			}
			public void actionPerformed(ActionEvent e) {	
				gw.moveRight();
			}
		}
	   
	   class MoveLeftCommand extends AbstractAction{	
			
			public MoveLeftCommand(String title, GameWorld gw){
				super(title);
			}
			public void actionPerformed(ActionEvent e) {	
				gw.moveLeft();
			}
		}
	   
	   class MoveUpCommand extends AbstractAction{	
			
			public MoveUpCommand(String title, GameWorld gw){
				super(title);
			}
			public void actionPerformed(ActionEvent e) {	
				gw.moveUp();
			}
		}
	   
	   class MoveDownCommand extends AbstractAction{	
			
			public MoveDownCommand(String title, GameWorld gw){
				super(title);
			}
			public void actionPerformed(ActionEvent e) {	
				gw.moveDown();
			}
		}
	   
	   class ScoopCommand extends AbstractAction{	
			
			public ScoopCommand(String title, GameWorld gw){
				super(title);
			}
			public void actionPerformed(ActionEvent e) {	
				gw.scoop();
			}
		}
	   
	   class ExpandNetCommand extends AbstractAction{	
			
			public ExpandNetCommand(String title, GameWorld gw){
				super(title);
			}
			
			public void actionPerformed(ActionEvent e) {	
				gw.expandNet(); //passing an even # as argument expands net
			}
		}
	   
	   class ContractNetCommand extends AbstractAction{	
		   
		   public ContractNetCommand(String title, GameWorld gw){
				super(title);
			}
			
		   public void actionPerformed(ActionEvent e) {	
			      gw.contractNet();
			}
		}
	   
	   /*class NewCommand extends AbstractAction{
		   public NewCommand(String title){
				super(title);
			}
		   public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, "Are you sure you want to restart?");
				//Window w = SwingUtilities.getWindowAncestor(mainFrame);
				//w.dispatchEvent(new WindowEvent(w, WindowEvent.WINDOW_CLOSING));
				Game g = new Game();
				
			}
	   }*/
	   
	   class QuitCommand extends AbstractAction{
			
		   public QuitCommand(String title){
				super(title);
			}
		   public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?");
				System.exit(0);
				
			}
			
		}
	   

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		/*if(!playState()){
			   Point p = e.getPoint();
			   gw.mouseEventHandler(p);
		}*/
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.print("in");
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
