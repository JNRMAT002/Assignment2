//package skeletonCodeAssgnmt2;

import javax.swing.*;

// import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;


import java.util.Scanner;
// import java.util.concurrent.*;
//model is separate from the view.

public class WordApp {
//shared variables
	static int noWords=4;
	static int totalWords;

   	static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

	static WordRecord[] words;
	static volatile boolean done;  //must be volatile
	static 	Score score = new Score();

	static WordPanel w;
	// Instantiating score and message objects
	static TrackScore s;
	static DisplayMessage m;
	
	// Instantiating buttons at start
	static JButton startB, endB, quitB;
	
	public static void start()
	{
		done = false;
	}	
	
	public static void stop()
	{
		done = true;
	}
	
	public static void setupGUI(int frameX,int frameY,int yLimit)
	{
		// Frame init and dimensions
		JFrame frame = new JFrame("WordGame"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameX, frameY);
		JPanel g = new JPanel();
		g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
		g.setSize(frameX,frameY);
    	
		w = new WordPanel(words,yLimit);
		w.setSize(frameX,yLimit+100);
		
		// Creating new DisplayMessage() object to display final score after game completion
		m = new DisplayMessage();
		m.setSize(frameX, yLimit);
		
		g.add(w); 
		
		// DisplayMessage object added to graphics, not visible
		m.setVisible(false);
		g.add(m);
	    
		JPanel txt = new JPanel();
		txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS)); 
		JLabel caught =new JLabel("Caught: " + score.getCaught() + "    ");
		JLabel missed =new JLabel("Missed:" + score.getMissed()+ "    ");
		JLabel scr =new JLabel("Score:" + score.getScore()+ "    ");    
		txt.add(caught);
		txt.add(missed);
	   	txt.add(scr);
	   	
	   	
	    //[snip]
	    	s = new TrackScore(scr, caught, missed);
  
	    	final JTextField textEntry = new JTextField("",20);
	    	textEntry.addActionListener(new ActionListener()
	  	{
	  		public void actionPerformed(ActionEvent evt)
	  		{
	  			String text = textEntry.getText();
	  			//[snip]
	  			// Checking for correctness of the entered word
	  			for (int i=0; i<words.length; i++)
	  			{
	  				if (words[i].matchWord(text))
	  				{
	  					score.caughtWord(text.length());
	  					w.repaint();
	  					break;
	  				}
	  			}
	  			textEntry.setText("");
	  			textEntry.requestFocus();
			}
		});
	   
	   txt.add(textEntry);
	   txt.setMaximumSize( txt.getPreferredSize() );
	   g.add(txt);
	    
	   JPanel b = new JPanel();
	   b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS)); 
	   startB = new JButton("Start");
	   endB = new JButton("End");
	   quitB = new JButton("Quit");
		
		// add the listener to the jbutton to handle the "pressed" event
        	startB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
		      //[snip]
		      // Score reset at start of new game
		      score.resetScore();
		      
		      // WordPanel w visible, all buttons visible, DisplayMessage m not displayed as game is in progress
		      WordApp.w.setVisible(true);
		      WordApp.m.setVisible(false);   
		      startB.setVisible(true);
		      endB.setVisible(true);
		      quitB.setVisible(true);
		      
		      // Fetching words from dict, placing on screen
		      int x_inc = (int)frameX/noWords;
		      for (int i=0;i<noWords;i++)
		      {
			words[i]=new WordRecord(dict.getNewWord(),i*x_inc,yLimit);
		      }
		      
		      w.repaint();
		      
		      // Starting WordPanel w thread
		      start();
		      Thread wordView = new Thread(w);
		      wordView.start();
		      
		      // Starting thread to handle scores
		      Thread trackScore = new Thread(s);
		      trackScore.start();
		      
		      // Clear text field on start of new game
		      textEntry.setText("");
		      textEntry.requestFocus();  //return focus to the text entry field
		      
		   }
		});
			
		// add the listener to the jbutton to handle the "pressed" event
		endB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
		      //[snip]
		      // End threads - words stop falling, scores no longer tracked
		      stop();
		      
		      // Clears words from screen
		      for (int i=0; i<noWords; i++)
		      {
			words[i] = new WordRecord();
		      }
		      
		      w.repaint();
		      
		      endB.setVisible(false);
		      startB.setVisible(true);
		      scr.setVisible(false);
		      caught.setVisible(false);
		      missed.setVisible(false);
		   }
		});
		
		//JButton quitB = new JButton("Quit");
		
		quitB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
		   	// Stops all threads, exits program
		   	stop();
		   	System.exit(0);
		   }
		});
		
		endB.setVisible(false);
		
		b.add(startB);
		b.add(endB);
		b.add(quitB);
		g.add(b);
    	
		frame.setLocationRelativeTo(null);  // Center window on screen.
		frame.add(g); //add contents to window
		frame.setContentPane(g);     
		//frame.pack();  // don't do this - packs it into small space
       		frame.setVisible(true);
	}

   public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();
			//System.out.println("read '" + dictLength+"'");

			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]=new String(dictReader.next());
				//System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictReader.close();
		} catch (IOException e) {
	        System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
		return dictStr;
	}

	public static void main(String[] args) {
    	
		//deal with command line arguments
		totalWords=Integer.parseInt(args[0]);  //total words to fall
		noWords=Integer.parseInt(args[1]); // total words falling at any point
		assert(totalWords>=noWords); // this could be done more neatly
		String[] tmpDict=getDictFromFile(args[2]); //file of words
		if (tmpDict!=null)
			dict= new WordDictionary(tmpDict);
		
		WordRecord.dict=dict; //set the class dictionary for the words.
		
		words = new WordRecord[noWords];  //shared array of current words
		
		//[snip]
		
		setupGUI(frameX, frameY, yLimit);  
		//Start WordPanel thread - for redrawing animation
    		
    		
		int x_inc=(int)frameX/noWords;
	  	//initialize shared array of current words

		for (int i=0;i<noWords;i++) {
			words[i]=new WordRecord(dict.getNewWord(),i*x_inc,yLimit);
		}
	}
}