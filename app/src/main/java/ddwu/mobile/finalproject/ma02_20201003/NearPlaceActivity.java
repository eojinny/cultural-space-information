package ddwu.mobile.finalproject.ma02_20201003;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

public class NearPlaceActivity extends AppCompatActivity {

    public static final String TAG = "near";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_places);

        Intent intent = getIntent();
        Location loc = (Location) intent.getSerializableExtra("object"); // 직렬화된 객체를 받는 방법
       LatLng latLng = new LatLng(loc.getLatitude(),loc.getLongitude());
       Log.d(TAG, "나와라 제발" + latLng);
    }
}
