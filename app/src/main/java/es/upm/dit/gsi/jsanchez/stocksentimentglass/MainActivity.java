package es.upm.dit.gsi.jsanchez.stocksentimentglass;

import com.google.android.glass.app.Card;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.upm.dit.gsi.jsanchez.stocksentimentglass.adapters.CardAdapter;
import es.upm.dit.gsi.jsanchez.stocksentimentglass.model.EnterpriseModel;


public class MainActivity extends Activity {

    private CardScrollView mCardScroller;
    private View mView;
    private GestureDetector mGestureDetector;
    private List<Card> mCards;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mCardScroller = new CardScrollView(this);
        mCards = new ArrayList<>();
        getData();
        mCardScroller.setAdapter(new CardAdapter(mCards));



        setContentView(mCardScroller);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }


    private View buildView() {
        Card card = new Card(this);
        card.setText(R.string.app_name);
        card.setImageLayout(Card.ImageLayout.LEFT);
        card.addImage(R.drawable.logo_bbva);
        return card.getView();
    }

    private void getData(){


        String json = null;
        try {

            InputStream is = getAssets().open("gglass_demo.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

            JSONObject object = new JSONObject(json);

            Iterator<String> iter = object.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    JSONObject value = object.getJSONObject(key);
                    int photoId =getResources().getIdentifier(key.toLowerCase(), "drawable", getPackageName());
                    Card card = new Card(this);
                    card.setText(key + "\n\n" + "Sentiment index: " + value.getString("sentiment_index")+ "\n\n" + "Stock value: " + value.getString("price"));
                    card.setImageLayout(Card.ImageLayout.LEFT);
                    card.addImage(photoId);
                    mCards.add(card);
                    //EnterpriseModel enterpriseModel = new EnterpriseModel(key, value.getString("sentiment_index"),value.getString("price"),photoId);
                } catch (JSONException e) {
                    // Something went wrong!
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

       /* for (int i=1; i<=10; i++){
            CardBuilder card = new CardBuilder(this, CardBuilder.Layout.COLUMNS);
            card.setText(R.string.app_name);
            card.addImage(R.drawable.logo_bbva);
            mCards.add(card);
        }*/
        mCardScroller.setSelection(0);



    }





}
