package holybible.religious.christianity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ScreenSlidePageFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        TextView text = (TextView) view.findViewById(R.id.chalisaheader);
        String message = getArguments().getString(EXTRA_MESSAGE);
        String header = getArguments().getString("heading");
        textView.setText(message);
        text.setText(header);
        return view;
    }


}