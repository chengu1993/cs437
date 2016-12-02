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
    private static final String USERNAME = "root";
    private static final String PASSWORD = "guchenji";

    MapsActivity context;
    DatabaseConnector(MapsActivity ctx) {
        context = ctx;
    }


    @Override
    protected String doInBackground(String... params) {
        String query = params[0];
        try {
            String queryURL = BASE_URL + query;
            URL url = new URL(queryURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(USERNAME, "UTF-8") + "&"
                    + URLEncoder.encode("password", "UTF-8")+"="+ URLEncoder.encode(PASSWORD, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
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
        context.showPokemon(rawJson);
    }


}
