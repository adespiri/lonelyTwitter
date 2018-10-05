package ca.ualberta.cs.lonelytwitter;

public class NormalTweet extends Tweet {

    /**
     * Returns false if it is a normal tweet. True if it is an important tweet
     * @return will return false since this is just a normal tweet
     */
    @Override
    public Boolean isImportant() {
        return false;
    }
}
