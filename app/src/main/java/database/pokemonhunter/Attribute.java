package database.pokemonhunter;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Attribute extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    private String pokemon_id;


//    void updatePage(int index, String jasonString){
//        PlaceholderFragment fragment = (PlaceholderFragment) mSectionsPagerAdapter.getRegisteredFragment(index);
//        fragment.setView(jasonString);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute);

        Intent intent = getIntent();
        pokemon_id = intent.getStringExtra("pokemon_id");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attribute, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private View rootView = null;
        private String pokemon_id;
        public PlaceholderFragment(){}


        public void setPokemon_id(String pokemon_id){
            this.pokemon_id = pokemon_id;
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (sectionNumber) {
                case 1:
                    rootView = inflater.inflate(R.layout.type, container, false);
                    new DatabaseConnector(this).execute("type.php", pokemon_id);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.attack, container, false);
                    new DatabaseConnector(this).execute("attack.php", pokemon_id);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.evolution, container, false);
                    new DatabaseConnector(this).execute("evolution.php", pokemon_id);
                    break;
                default:
                    Log.e("Attribute", "Section Number out of bound");
            }
            return rootView;
        }

        public void setView(String jsonString){
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (sectionNumber) {
                case 1:
                    try{
                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONArray types = jsonObject.getJSONArray("type");
                        for(int i=0; i<types.length(); i++){
                            JSONObject type = types.getJSONObject(i);
                            String type_name = type.getString("type_name");
                            TextView textView = (TextView) rootView.findViewById(getResources()
                                    .getIdentifier("type"+(i+1), "id", getContext()
                                            .getPackageName()));
                            textView.setText(type_name);
                        }
                        String advantage = jsonObject.getString("advantage");
                        TextView textView = (TextView) rootView.findViewById(R.id.advantage);
                        textView.setText(advantage);
                        String disadvantage = jsonObject.getString("disadvantage");
                        TextView textView1 = (TextView) rootView.findViewById(R.id.disadvantage);
                        textView1.setText(disadvantage);
                        TextView textView2 = (TextView) rootView.findViewById(R.id.strong);
                        textView2.setText("Strong against");
                        TextView textView3 = (TextView) rootView.findViewById(R.id.weak);
                        textView3.setText("Weak against");
                    } catch (JSONException e){
                        Log.e("JSON Parse", "Error parsing data"+ e.toString()) ;
                    } catch (Exception e){
                        Log.e("Exception", e.toString());
                    }
                    break;
                case 2:
                    try{
                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONArray fast_attacks = jsonObject.getJSONArray("fast_attack");
                        for(int i=0; i<fast_attacks.length(); i++){
                            JSONObject fast_attack = fast_attacks.getJSONObject(i);
                            String fast_attack_name = fast_attack.getString("attack_name");
                            TextView textView = (TextView) rootView.findViewById(getResources()
                                    .getIdentifier("fast"+(i+1), "id", getContext()
                                            .getPackageName()));
                            textView.setText(fast_attack_name);
                        }
                        JSONArray charge_attacks = jsonObject.getJSONArray("charge_attack");
                        for(int i=0; i<charge_attacks.length(); i++){
                            JSONObject charge_attack = charge_attacks.getJSONObject(i);
                            String charge_attack_name = charge_attack.getString("attack_name");
                            TextView textView = (TextView) rootView.findViewById(getResources()
                                    .getIdentifier("charge"+(i+1), "id", getContext()
                                            .getPackageName()));
                            textView.setText(charge_attack_name);
                        }
                    } catch (JSONException e){
                        Log.e("JSON Parse", "Error parsing data"+ e.toString()) ;
                    } catch (Exception e){
                        Log.e("Exception", e.toString());
                    }
                    break;
                case 3:
                    try{
                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONArray evolve_sequence = jsonObject.getJSONArray("evolve_sequence");
//                        ImageView image;
                        for(int i=0; i<evolve_sequence.length(); i++){
                            JSONObject pokemon = evolve_sequence.getJSONObject(i);
                            String pokemon_id = pokemon.getString("pokemon_id");
                            ImageView image = (ImageView) rootView.findViewById(getResources()
                                    .getIdentifier("poke"+(i+1), "id", getContext()
                                            .getPackageName()));
                            image.setImageResource(getResources().getIdentifier(String.format(Locale.US, "pokemon%03d", Integer.parseInt(pokemon_id))
                                    , "drawable", getContext().getPackageName()));

                        }
                    } catch (JSONException e){
                        Log.e("JSON Parse", "Error parsing data"+ e.toString()) ;
                    } catch (Exception e){
                        Log.e("Exception", e.toString());
                    }
                    break;
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        // Sparse array to keep track of registered fragments in memory
        private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            PlaceholderFragment fragment =  PlaceholderFragment.newInstance(position + 1);
            fragment.setPokemon_id(pokemon_id);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        // Returns the fragment for the position (if instantiated)
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Type";
                case 1:
                    return "Attacks";
                case 2:
                    return "Evolution Graph";
            }
            return null;
        }
    }
}
