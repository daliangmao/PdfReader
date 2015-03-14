package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Collection;

/**
 * Created by Andy on 8/15/14 AD.
 */
public class OpenHelper extends SQLiteOpenHelper
{
    private SqliteDelegate delegate;

    public OpenHelper(Context context, String name, int version, SqliteDelegate delegate)
    {
        super(context, name, null, version);
        this.delegate = delegate;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        for (Table table : delegate.OnTables()) {
            db.execSQL(table.OnCreate());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        for (Table table : delegate.OnTables()) {
            db.execSQL(table.OnDrop());
        }
        onCreate(db);
    }

    public interface SqliteDelegate {
        public Collection<Table> OnTables();
    }
}

