public class WordDrop implements Runnable
{
    WordRecord word;
        
    WordDrop(WordRecord word)
    {
        this.word = word;
    }
    
    @Override
    public void run()
    {
        // Retrieving fallingspeed from WordRecord, drop the word by that value
        float dropVal = word.getSpeed()/(float)WordApp.yLimit;
        word.drop(dropVal);
        
        // If a word is missed, reset it and add to the missed count
        if (word.getY() >= WordApp.yLimit-25)
        {
            word.resetWord();
            WordApp.score.missedWord();
        }
    }
}