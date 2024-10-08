//package skeletonCodeAssgnmt2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.concurrent.CountDownLatch;

// import javax.swing.JButton;
import javax.swing.JPanel;

public class WordPanel extends JPanel implements Runnable {
		public static volatile boolean done;
		private WordRecord[] words;
		private int noWords;
		private int maxY;

		
		public void paintComponent(Graphics g) {
		    int width = getWidth();
		    int height = getHeight();
		    g.clearRect(0,0,width,height);
		    g.setColor(Color.red);
		    g.fillRect(0,maxY-10,width,height);
		    g.setColor(Color.black);
		    g.setFont(new Font("Helvetica", Font.PLAIN, 26));
		   //draw the words
		   //animation must be added 
		    for (int i=0;i<noWords;i++){	    	
		    	//g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());	
		    	g.drawString(words[i].getWord(),words[i].getX(), (int)words[i].getY()+20);  //y-offset for skeleton so that you can see the words	
		    }
		   
		  }
		
		WordPanel(WordRecord[] words, int maxY) {
			this.words=words;
			noWords = words.length;
			done=false;
			this.maxY=maxY;		
		}
		
		public void run() {
			//add in code to animate this
			while (WordApp.done == false)
			{
				// Create a thread for the falling of each word
				for (int i=0; i<words.length; i++)
				{
					WordDrop worddrop = new WordDrop(words[i]);
					Thread drop = new Thread(worddrop);
					drop.start();
				}
				
				repaint();
				
				// Number of words determines how long thread sleeps for
				// More total words = longer sleep = slower fall rate
				int sleep = noWords*5;
				try
				{
					Thread.sleep(sleep);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			
			for (int i=0; i<words.length; i++)
			{
				words[i].resetMaxMinWait();
			}
			
			WordApp.w.setVisible(false);
			WordApp.m.setVisible(true);
		}

	}


