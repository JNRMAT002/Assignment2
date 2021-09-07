import javax.swing.*;

public class TrackScore implements Runnable
{
    JLabel scr, caught, missed;
    
    TrackScore(JLabel scr, JLabel caught, JLabel missed)
    {
        this.scr = scr;
        this.caught = caught;
        this.missed = missed;
    }
    
    @Override
    public void run()
    {
        scr.setVisible(true);
        caught.setVisible(true);
        missed.setVisible(true);
        
        scr.setText("Score: " + WordApp.score.getScore() + "     ");
        caught.setText("Caught: " + WordApp.score.getCaught() + "     ");
        missed.setText("Missed: " + WordApp.score.getMissed() + "     ");
        
        while(WordApp.score.getTotal() < WordApp.totalWords)
        {
            if (WordApp.w.done)
            {
                break;
            }
            
            scr.setText("Score: " + WordApp.score.getScore() + "     ");
            caught.setText("Caught: " + WordApp.score.getCaught() + "     ");
            missed.setText("Missed: " + WordApp.score.getMissed() + "     ");
        }
        
        WordApp.endB.setVisible(false);
        WordApp.startB.setVisible(true);        
        scr.setVisible(false);
        caught.setVisible(false);
        missed.setVisible(false);
        WordApp.stop();
    }
}