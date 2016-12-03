package database.pokemonhunter;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by gc on 12/1/16.
 */

public class DatabaseConnector extends AsyncTask<String, Void, String> {

    private static final String TAG = "DatabaseUtils";
    private static final String BASE_URL = "http://172.27.157.75/";

    Object context;
    String action = null;
    DatabaseConnector(Object ctx) {
        context = ctx;
    }


    @Override
    protected String doInBackground(String... params) {
        if(params.length == 0) return null;
        action = params[0];
        try {
            String queryURL = BASE_URL + action;
            URL url = new URL(queryURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            if(params.length >= 2) {
                String pokemon_id = params[1];
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("pokemon_id", "UTF-8") + "=" + URLEncoder.encode(pokemon_id, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine())!=null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException");
        } catch (IOException e) {
            Log.e(TAG, "IO Exception");
        } catch (Exception e){
            Log.e(TAG, "Unknown Exception");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String rawJson){
        switch(action){
            case "location.php":
                ((MapsActivity)context).showPokemon(rawJson);
                break;
            case "type.php":
                ((Attribute.PlaceholderFragment) context).setView(rawJson);
                break;
            case "attack.php":
                ((Attribute.PlaceholderFragment) context).setView(rawJson);
                 break;
            case "evolve.php":
                ((Attribute.PlaceholderFragment) context).setView(rawJson);
                break;
            default:
                Log.e(TAG, "Action not supported");
        }
    }


}
