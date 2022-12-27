package ddwu.mobile.finalproject.ma02_20201003;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    ListView lvList;
    String apiAddress;
    FavoritesDBHelper helper;

    ArrayAdapter<CulturalSpaceInfoDTO> adapter;
    List<CulturalSpaceInfoDTO> resultList;
    CulturalSpaceInfoDTO culturalSpaceInfoDTO = new CulturalSpaceInfoDTO();
    final int REQ_PERMISSION_CODE = 100;

    TextView tvText;

    FusedLocationProviderClient flpClient;
    Location mLastLocation;

    //    GoogleMap 객체 추가
    private GoogleMap mGoogleMap;
    private Marker centerMarker;

    private Button btn_open_bt_sheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_open_bt_sheet = findViewById(R.id.btn_open_bt_sheet);
        //txt_result = findViewById(R.id.txt_result);
        helper = new FavoritesDBHelper(this);
        btn_open_bt_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CulturalSpaceInfoGpsBottomSheetDialog bottomSheetDialog = new CulturalSpaceInfoGpsBottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(), "exampleBottomSheet");
                //txt_result.setText("바텀시트 보여짐");

            }
        });
        apiAddress = "http://openapi.seoul.go.kr:8088/6d55526c4f6a696e3839435071654c/xml/culturalSpaceInfo/1/820";

        //lvList = (ListView) findViewById(R.id.lvList);

        resultList = new ArrayList<CulturalSpaceInfoDTO>();
        adapter = new ArrayAdapter<CulturalSpaceInfoDTO>(this, android.R.layout.simple_list_item_1, resultList);

        //lvList.setAdapter(adapter);

        flpClient = LocationServices.getFusedLocationProviderClient(this);



        if(checkPermission()) {
            flpClient.requestLocationUpdates(
                    getLocationRequest(),
                    mLocCallback,
                    Looper.getMainLooper()
            );
        }


//        MapFragment 추가 및 지도 로딩 실행
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapReadyCallback);


//        res/values/strings.xml 의 server_url 값을 읽어옴
//        apiAddress = getResources().getString(R.string.server_url);


        Button Button_Search = findViewById(R.id.Button_Search);
        Button_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NaverBlogSearchActivity.class);
                startActivity(intent);
            }
        });
        Button Button_favorite = findViewById(R.id.Button_favorite);
        Button_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AllFavoritesActivity.class);
                startActivity(intent);
            }
        });

    }


    class NetworkAsyncTask extends AsyncTask<String, Void, String> {

        final static String NETWORK_ERR_MSG = "Server Error!";
        public final static String TAG = "NetworkAsyncTask";
        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(MainActivity.this, "Wait", "Downloading...");     // 진행상황 다이얼로그 출력
        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String result = downloadContents(address);
            if (result == null) {
                cancel(true);
                return NETWORK_ERR_MSG;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDlg.dismiss();  // 진행상황 다이얼로그 종료
            adapter.clear();        // 어댑터에 남아있는 이전 내용이 있다면 클리어

//          parser 생성 및 OpenAPI 수신 결과를 사용하여 parsing 수행
            CultureSapceInfoXmlParser parser = new CultureSapceInfoXmlParser();

            resultList = parser.parse(result);

            if (resultList == null) {       // 올바른 결과를 수신하지 못하였을 경우 안내
                Toast.makeText(MainActivity.this, "날짜를 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
            } else if (!resultList.isEmpty()) {
                adapter.addAll(resultList);
                for (CulturalSpaceInfoDTO data : resultList) {
                    LatLng latLng = new LatLng(data.X_Coord, data.Y_Coord);
                    MarkerOptions options = new MarkerOptions()
                            .title(data.Fac_Name)
                            .position(latLng)
                            .snippet(data.Addr+ "\n" + data.Fac_Desc)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    centerMarker = mGoogleMap.addMarker(options);

                    // centerMarker.showInfoWindow();
                }// 리스트뷰에 연결되어 있는 어댑터에 parsing 결과 ArrayList 를 추가
                mGoogleMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
                    @Override
                    public void onInfoWindowLongClick(@NonNull Marker marker) {
                        SQLiteDatabase db = helper.getWritableDatabase();

                        ContentValues row = new ContentValues();

                        row.put(FavoritesDBHelper.COL_NAME, marker.getTitle());
                        row.put(FavoritesDBHelper.COL_ADD, marker.getSnippet());

                        //오류 확인 가능 result
                        db.insert(FavoritesDBHelper.TABLE_NAME, null, row);

                        //db.execSQL("INSERT INTO "+ FavoritesDBHelper.TABLE_NAME +
                        //     " VALUES (NULL, '" + etName.getText().toString() + "','" +  etPhone.getText().toString() + "','" + etCategory.getText().toString() + "');");

                        helper.close();
                        Toast.makeText(MainActivity.this, marker.getTitle() + "즐겨찾기에 저장했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            Log.d(TAG,"왜 안뜨니"+ resultList);

        }


        @Override
        protected void onCancelled(String msg) {
            super.onCancelled();
            progressDlg.dismiss();
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }
    /* 주소(apiAddress)에 접속하여 문자열 데이터를 수신한 후 반환 */
    protected String downloadContents(String address) {
        HttpURLConnection conn = null;
        InputStream stream = null;
        String result = null;

        try {
            URL url = new URL(address);
            conn = (HttpURLConnection)url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToString(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }

        return result;
    }
    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
    private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {
        conn.setReadTimeout(3000);
        conn.setConnectTimeout(3000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + conn.getResponseCode());
        }

        return conn.getInputStream();
    }


    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    protected String readStreamToString(InputStream stream){
        StringBuilder result = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String readLine = bufferedReader.readLine();

            while (readLine != null) {
                result.append(readLine + "\n");
                readLine = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }


    OnMapReadyCallback mapReadyCallback=new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {

            mGoogleMap = googleMap; //구글맵 가져오면 멤버변수에 담기

            //코드로 구글맵 제어가능
            if(checkPermission()) {
                mGoogleMap.setMyLocationEnabled(true);
            }
            new NetworkAsyncTask().execute(apiAddress);

            resultList = new ArrayList<CulturalSpaceInfoDTO>();
            Log.d(TAG,"왜 안뜨니"+ resultList);
           // mGoogleMap.setOnMyLocationButtonClickListener(locationButtonClickListener);
            // mGoogleMap.setOnMyLocationClickListener(locationClickListener);
            //ArrayList<Marker> markers = new ArrayList<Marker>();
            //for(int i=0; i<resultList.size(); i++) {
           // Log.d(TAG,"왜 안뜨니"+ String.valueOf(culturalSpaceInfoDTO.X_Coord + culturalSpaceInfoDTO.Y_Coord));
            for (CulturalSpaceInfoDTO data : resultList) {
                LatLng latLng = new LatLng(data.X_Coord, data.Y_Coord);
                MarkerOptions options = new MarkerOptions()
                            .title(data.Fac_Name)
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                centerMarker = mGoogleMap.addMarker(options);

                   // centerMarker.showInfoWindow();
                }


            //}
            //Log.d(TAG,  String.valueOf(culturalSpaceInfoDTO.getX_Coord())+ String.valueOf(culturalSpaceInfoDTO.getX_Coord()));
            //LatLng latLng = new LatLng(culturalSpaceInfoDTO.getX_Coord(),culturalSpaceInfoDTO.getY_Coord());
            //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17)); // 그자리로 옮겨줌
            //mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17)); // 이동과정을 보여줌
            //-> 위치를 수신하는 콜백 함수에서 해주면 바로바로 위치 정보를 알 수 있음
            // 위치 정보를 마커에 찍는 역할은 onMap Ready에서 싫ㅇ함
            //MarkerOptions options = new MarkerOptions();
           // options.position(latLng)
           //         .title("현재위치")
           //         .snippet("이동중")
            //        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            //centerMarker = mGoogleMap.addMarker(options); // 마커를 찍기 마커라는 객체로 반환하도록 ->  마커 위치 변경 , 삭제도 해줌
            // 마커를 띄워줌
            Log.i(TAG, "되는거니 onMapReady");
        }

    };


    LocationCallback mLocCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            for (Location loc : locationResult.getLocations()) {
                double lat = loc.getLatitude();
                double lng = loc.getLongitude();
                //setTvText(String.format("(%.6f, %.6f)", lat, lng));
                mLastLocation = loc;

//                현재 수신 위치로 GoogleMap 위치 설정
                LatLng currentLoc = new LatLng(lat,lng);
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc,17));
                //centerMarker.setPosition(currentLoc);
                Log.i(TAG, "정답"+ String.valueOf(currentLoc));
            }
        }
    }; // 마커 객체가 여러개면 리스트에 보관
    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "위치권한 획득 완료", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "위치권한 미획득", Toast.LENGTH_SHORT).show();
                }
        }
    }
    private void getLastLocation() {

        flpClient.getLastLocation().addOnSuccessListener(
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            //setTvText( String.format("최종위치: (%.6f, %.6f)", latitude, longitude) );
                            mLastLocation = location;
                        } else {
                            Toast.makeText(MainActivity.this, "No location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        flpClient.getLastLocation().addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.e(TAG, "Unknown");
                    }
                }
        );

    }

    private boolean checkPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            // 권한이 있을 경우 수행할 동작
            Toast.makeText(this,"Permissions Granted", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            // 권한 요청
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION_CODE);
            return false;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        flpClient.removeLocationUpdates(mLocCallback);
    }

}