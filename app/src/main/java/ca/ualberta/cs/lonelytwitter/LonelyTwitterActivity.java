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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private ListView oldTweetsList;
	private ArrayList<String> tweets = new ArrayList<String>();
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
                String text = bodyText.getText().toString();
                saveInFile();
                adapter.notifyDataSetChanged();
                finish();

            }
        });




		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
			    Date date = new Date(System.currentTimeMillis());
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				saveInFile();
				tweets.add(text);
				adapter.notifyDataSetChanged();
				finish();

			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		loadFromFile();
		adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, tweets);
		oldTweetsList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            Gson gson = new Gson();
            Type listTweetType = new TypeToken<ArrayList<Tweet>>() {
            }.getType();
            tweets = gson.fromJson(reader, listTweetType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            tweets = new ArrayList<String>();
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


	
	private void saveInFile() {
		try {

		    FileOutputStream fos = openFileOutput(FILENAME, 0);
		    OutputStreamWriter osw = new OutputStreamWriter(fos);
		    BufferedWriter writer = new BufferedWriter(osw);
		    Gson gson = new Gson();
		    gson.toJson(tweets, writer);
		    writer.flush();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			tweets = new ArrayList<String>();
			e.printStackTrace();
		}

	}
}