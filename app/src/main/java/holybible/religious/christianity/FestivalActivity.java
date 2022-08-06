package holybible.religious.christianity;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class FestivalActivity extends Activity implements OnItemSelectedListener {

    private static final String DEBUG_TAG = "Network";
    private TableLayout layLinearLayout;
    private Spinner yearSpinner;
    private ProgressBar progressBar;
    String[] yearList = {"2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022",
            "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033",
            "2034", "2035", "2036", "2037", "2038", "2039", "2040", "2041", "2042", "2043", "2044",
            "2045", "2046", "2047", "2048", "2049", "2050", "2051", "2052", "2053", "2054", "2055",
            "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066",
            "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2074", "2075", "2076", "2077",
            "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088",
            "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099",
            "2100"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival);
        // new DownloadWebpageTask().execute();
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        layLinearLayout = (TableLayout) findViewById(R.id.layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                R.layout.spinner_item, yearList);
        yearSpinner.setAdapter(adapter_state);
        yearSpinner.setOnItemSelectedListener(this);
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        String date = sd.format(new Date());
        String year = date.substring(date.lastIndexOf('/') + 1);

        int pos = 0;
        for (int i = 0; i < yearList.length; i++) {
            if (yearList[i].equals(year)) {
                pos = i;
                //Toast.makeText(this, pos+"", Toast.LENGTH_SHORT).show();
            }
        }
        yearSpinner.setSelection(pos);
    }


    @Override
    public void onItemSelected(AdapterView<?> adaperView, View view, int index, long arg3) {
        try {
        /*
	     * int year = Calendar.getInstance().get(Calendar.YEAR); if(year>2100 || year<2014) {
	     * year = 2014; }
	     */
            progressBar.setVisibility(View.VISIBLE);
            layLinearLayout.removeAllViews();
            InputStream is = getAssets().open("year/" + yearList[index] + ".txt");
            byte[] data = new byte[is.available()];
            is.read(data);

            try {
                JSONObject jsonObject = new JSONObject(new String(data));
                JSONArray rootArray = jsonObject.getJSONArray("root");
                for (int j = 0; j < rootArray.length(); j++) {

                    JSONObject monthObject = rootArray.getJSONObject(j);

                    JSONArray festivalArray = monthObject.getJSONArray("festival");

                    JSONArray festivalListArray = festivalArray.getJSONArray(0);
                    // Log.v("", "json " + festivalListArray.toString());
                    for (int i = 0; i < festivalListArray.length(); i++) {

                        JSONObject jsObject = festivalListArray.getJSONObject(i);
                        LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(
                                R.layout.table_row_item, null);

                        TextView date = (TextView) layout.findViewById(R.id.date);
                        date.setText(jsObject.getString("date"));

                        TextView weekday = (TextView) layout.findViewById(R.id.weekday);
                        weekday.setText(jsObject.getString("weekday"));
                        TextView festival = (TextView) layout.findViewById(R.id.festival);

                        festival.setText(jsObject.getString("festivalName"));

                        RelativeLayout titleLayout = (RelativeLayout) layout
                                .findViewById(R.id.monthLayout);
                        TextView month = (TextView) titleLayout.findViewById(R.id.month);
                        month.setText(monthObject.getString("month"));
                        if (i == 0) {
                            titleLayout.setVisibility(View.VISIBLE);
                        } else {
                            titleLayout.setVisibility(View.GONE);
                        }
                        layLinearLayout.addView(layout);
                    }
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
