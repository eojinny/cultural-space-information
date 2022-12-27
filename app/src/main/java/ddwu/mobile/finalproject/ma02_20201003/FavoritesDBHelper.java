package ddwu.mobile.finalproject.ma02_20201003;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavoritesDBHelper extends SQLiteOpenHelper { //디비 편리하게 사용하기 위한, 상속후 생성자 및 oncreate, onupgrade 사용

	private final String TAG = "ContactDBHelper";

	private final static String DB_NAME = "Favorites_db";
	public final static String TABLE_NAME = "Favorites_table";
	public final static String COL_NAME = "name";
	public final static String COL_ADD = "description";

	public FavoritesDBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//테이블설계
		String createSql = " create table " + TABLE_NAME + " ( _id integer primary key autoincrement, " +
				COL_NAME + " TEXT," + COL_ADD + " TEXT);";
		Log.d(TAG, createSql);
		db.execSQL(createSql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}


