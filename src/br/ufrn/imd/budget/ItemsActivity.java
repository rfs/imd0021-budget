package br.ufrn.imd.budget;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.ufrn.imd.adapters.ItemAdapter;
import br.ufrn.imd.budget.R;
import br.ufrn.imd.domain.Item;
import br.ufrn.imd.helpers.DatabaseHelper;

public class ItemsActivity extends ListActivity {
	
	String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		type = bundle.getString("type");
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (type.equals("incomes")) {
			setTitle(getResources().getString(R.string.incomes));
		}
		else {
			setTitle(getResources().getString(R.string.expenses));
		}		

		ItemAdapter adapter = new ItemAdapter(this, generateData());
        setListAdapter(adapter);

        ListView listView = getListView();
        listView.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	Intent editItemIntent = new Intent(ItemsActivity.this, EditItemActivity.class);
        		Bundle bundle = new Bundle();
        		bundle.putString("type", type);
        		bundle.putSerializable("item", (Item) (parent.getAdapter().getItem(position)));
        		editItemIntent.putExtras(bundle);
        		startActivity(editItemIntent);
	        }
        });
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);

    	menu.add("edit_item").setIcon(R.drawable.ic_action_new).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getTitle().equals("edit_item")) {
    		Intent editItemIntent = new Intent(ItemsActivity.this, EditItemActivity.class);
    		Bundle bundle = new Bundle();
    		bundle.putString("type", type);
    		bundle.putSerializable("item", null);
    		editItemIntent.putExtras(bundle);    		
    		startActivity(editItemIntent);
        	return true;
    	}
		return super.onOptionsItemSelected(item);
    }

    private ArrayList<Item> generateData(){
    	Locale locale = getResources().getConfiguration().locale;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);        

    	SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
	    String sql = "SELECT  * FROM " + type + " ORDER BY dueDate";
	    Cursor c = db.rawQuery(sql, null);

        ArrayList<Item> items = new ArrayList<Item>();

	    if (c.moveToFirst()) {
	        do {
	        	try {
	        		Date dueDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c.getString(c.getColumnIndex("dueDate")) + " 23:59:59");
	        		Date today = new Date();
	        		Boolean done = (c.getInt(c.getColumnIndex("done")) == 1);
	        		int itemColor = (dueDate.before(today) && !done) ? Color.rgb(204, 0, 0) : Color.BLACK;

		        	Item item = new Item();
		        	item.setId(c.getInt(c.getColumnIndex("id")));
		        	item.setTitle(c.getString(c.getColumnIndex("title")));
		        	item.setValue(currencyFormat.format(c.getDouble(c.getColumnIndex("value"))));
		        	item.setDueDate(dateFormat.format(dueDate));
		        	item.setDone(done);
		        	item.setColor(itemColor);
				    items.add(item);
   
	        	} catch (ParseException e) {
	        	    Log.e("Error", e.getLocalizedMessage());
	        	}
	        } while (c.moveToNext());
	    }

        return items;
    }
}
