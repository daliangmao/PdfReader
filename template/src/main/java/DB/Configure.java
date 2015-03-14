package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Configure implements OpenHelper.SqliteDelegate
{
    protected static Configure instance = null;

    OpenHelper openHelper;
    private SQLiteDatabase database;
    private Map<String, Table> tables;

    public static Configure getInstance(Context context, String name, int version) {
        if (instance == null) {
            instance = new Configure(context, name, version);
        }
        return instance;
    }

    public static Configure getInstance() {
        return instance;
    }

    public Configure(Context context, String name, int version) {
        tables = new HashMap<String, Table>();
        openHelper = new OpenHelper(context, name, version, this);
    }

    protected void initData(Table[] tbl) {
        for (int i=0; i<tbl.length; i++) {
            tables.put(tbl[i].getClass().getSimpleName(), tbl[i]);
        }
        database = openHelper.getWritableDatabase();
        for (Table table : tables.values()) {
            table.initdata(database);
        }
    }

    @Override
    public Collection<Table> OnTables() {
        return this.tables.values();
    }
}
