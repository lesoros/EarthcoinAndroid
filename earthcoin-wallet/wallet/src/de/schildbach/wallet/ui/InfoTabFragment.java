package de.schildbach.wallet.ui;
 
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Map;
import android.net.Uri;
import java.util.List;
import java.util.ArrayList;
import android.content.Intent;


import de.schildbach.wallet.earthcoin.R;
 
public class InfoTabFragment extends ListFragment {
    int fragNum;
	List<Map<String, String>> infoList = new ArrayList<Map<String,String>>();
    String title[] = { "Earthcoin Offical Site", "News", "Twitter", "Facebook", "Forums", "Charts","Reddit" };
	String url[] = { "http://getearthcoin.com", "http://earthcoin.eu", "http://twitter.com/getearthcoin", "http://facebook.com/earthcoin.official", "https://earthcointalk.org", "http://www.cryptocoincharts.info/v2/coins/show/eac","http://reddit.com/r/earthcoin" };
 
    static InfoTabFragment init(int val) {
        InfoTabFragment infoTab = new InfoTabFragment();
 
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        infoTab.setArguments(args);
 
        return infoTab;
    }
 
    /**
     * Retrieving this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
    }
 
    /**
     * The Fragment's UI is a simple text view showing its instance number and
     * an associated list.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_pager_list, container, false);
        //View tv = layoutView.findViewById(R.id.text);
		
        //((TextView) tv).setText("Earthcoin Related Sites");
		
        return layoutView;
    }
 
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.infotab, title));
    }
 
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
	
		// get string with position as index
		String infoUrl = url[position];

		// create intent that opens in browser
		Intent intent = new Intent(Intent.ACTION_VIEW);

		// convert url to uri
		intent.setData(Uri.parse(infoUrl));
		startActivity(intent);
				
    }

}