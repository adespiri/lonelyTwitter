package ca.ualberta.cs.lonelytwitter;


import java.util.Date;

public class ImportantTweet extends Tweet {

    /**
     * Since this is of class ImportantTweet, it will return true
     * @return true if the tweet is important. False if the tweet is not
     */

    @Override
    public Boolean isImportant() {

        return true;
    }
}
