package nid.myactivity;

/**
 * Created by Nid on 2/28/16.
 */
       import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
       import android.widget.Button;

public class BottomPanelFragment extends Fragment {


    Button b1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Button b1 = (Button) getView().findViewById(R.id.snooze);
        View view = inflater.inflate(R.layout.fragment_side_panel, container, false);


        return view;
    }
}