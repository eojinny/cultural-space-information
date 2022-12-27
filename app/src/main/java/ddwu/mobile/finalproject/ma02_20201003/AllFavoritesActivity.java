package ddwu.mobile.finalproject.ma02_20201003;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AllFavoritesActivity extends Activity {
	
	private ListView lvContacts = null;

	private ArrayAdapter<CulturalSpaceInfoDTO> adapter;
	private FavoritesDBHelper helper;
	private ArrayList<CulturalSpaceInfoDTO> contactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_contacts);

		helper = new FavoritesDBHelper(this);
		contactList = new ArrayList<CulturalSpaceInfoDTO>();

		lvContacts = (ListView)findViewById(R.id.lvContacts);
		adapter = new ArrayAdapter<CulturalSpaceInfoDTO>(this, android.R.layout.simple_list_item_1, contactList);

		lvContacts.setAdapter(adapter);
	}



	@Override
	protected void onResume() {
		super.onResume();

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select*from " + FavoritesDBHelper.TABLE_NAME, null);

		contactList.clear();

		while (cursor.moveToNext())
		{
			CulturalSpaceInfoDTO dto = new CulturalSpaceInfoDTO();
			dto.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			dto.setFac_Name(cursor.getString(cursor.getColumnIndex(FavoritesDBHelper.COL_NAME)));
			dto.setAddr(cursor.getString(cursor.getColumnIndex(FavoritesDBHelper.COL_ADD)));
			contactList.add(dto);
		}
		adapter.notifyDataSetChanged();

		cursor.close();
		helper.close();
		

	}

}




