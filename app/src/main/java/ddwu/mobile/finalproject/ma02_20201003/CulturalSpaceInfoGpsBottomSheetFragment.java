package ddwu.mobile.finalproject.ma02_20201003;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CulturalSpaceInfoGpsBottomSheetFragment extends BottomSheetDialogFragment {
    private final String TAG = "CulturalSpaceInfoGpsBottomSheetFragment";
    // 초기변수 설정
    private View view;
    final int REQ_PERMISSION_CODE = 100;
    // 인터페이스 변수
    private BottomSheetListener mListener;
    // 바텀시트 숨기기 버튼
    private Button btn_hide_bt_sheet;
    String str;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

       // mListener = (BottomSheetListener) getContext();
        Bundle bundle = getArguments();

        if (bundle != null) {
            str = bundle.getString("key");
        }

        btn_hide_bt_sheet = view.findViewById(R.id.btn_hide_bt_sheet);


        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        if(str == null){
        Log.d(TAG, "0");}
        btn_hide_bt_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mListener.onButtonClicked("바텀 시트 숨겨짐");
                dismiss();
            }
        });
        return view;
    }


    // 부모 액티비티와 연결하기위한 인터페이스
    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }



}
