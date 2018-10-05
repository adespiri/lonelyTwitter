package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private ListView oldTweetsList;
	private ArrayList<ImportantTweet> tweets = new ArrayList<ImportantTweet>();
	private ArrayAdapter adapter;

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.save);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);
		Button clearButton = (Button) findViewById(R.id.clear);

        clearButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                tweets.clear();
                saveInFile();
                adapter.notifyDataSetChanged();



            }
        });




		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
			    Date date = new Date(System.currentTimeMillis());
				setResult(RESULT_OK);
				ImportantTweet newTweet = new ImportantTweet();
				String text = bodyText.getText().toString();
				try {
					newTweet.setMessage(text);
				} catch (TweetTooLongException e) {
					e.printStackTrace();
				}
				tweets.add(newTweet);
				saveInFile();
				adapter.notifyDataSetChanged();
				finish();

			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		loadFromFile();
		adapter = new ArrayAdapter<ImportantTweet>(this,
				R.layout.list_item, tweets);
		oldTweetsList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	/**
	 * loads the old tweet list
	 */
	private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            Gson gson = new Gson();
            Type listTweetType = new TypeToken<ArrayList<ImportantTweet>>() {
            }.getType();
            tweets = gson.fromJson(reader, listTweetType);
            fis.close();


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            tweets = new ArrayList<ImportantTweet>();
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


	/**
	 * saves the tweet list
	 */
	private void saveInFile() {
		try {

		    FileOutputStream fos = openFileOutput(FILENAME, 0);
		    OutputStreamWriter osw = new OutputStreamWriter(fos);
		    BufferedWriter writer = new BufferedWriter(osw);
		    Gson gson = new Gson();
		    gson.toJson(tweets, writer);
		    writer.flush();

		    fos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

	}
}