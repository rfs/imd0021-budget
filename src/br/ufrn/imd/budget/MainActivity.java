package br.ufrn.imd.budget;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.ufrn.imd.adapters.ItemTypeAdapter;
import br.ufrn.imd.budget.R;
import br.ufrn.imd.domain.ItemType;
import br.ufrn.imd.helpers.DatabaseHelper;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.currency_quotations) {
    		Intent currencyQuotationsIntent = new Intent(MainActivity.this, CurrencyQuotationsActivity.class);
    		startActivity(currencyQuotationsIntent);
    		return true;
		}
		return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onStart() {
		super.onStart();

		ItemTypeAdapter adapter = new ItemTypeAdapter(this, generateData());
        setListAdapter(adapter);

        ListView listView = getListView();
        listView.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	Intent itemsIntent;
	        	Bundle bundle;
	        	
	        	switch (position) {
	        	case 0:
	        		itemsIntent = new Intent(MainActivity.this, ItemsActivity.class);
	        		bundle = new Bundle();
	        		bundle.putString("type", "incomes");
	        		itemsIntent.putExtras(bundle);
	        		startActivity(itemsIntent);
	        		break;
	        	case 1:
	        		itemsIntent = new Intent(MainActivity.this, ItemsActivity.class);
	        		bundle = new Bundle();
	        		bundle.putString("type", "expenses");
	        		itemsIntent.putExtras(bundle);
	        		startActivity(itemsIntent);
	        		break;
	        	}
	        }
        });
	}

    private ArrayList<ItemType> generateData(){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(getResources().getConfiguration().locale);
        
    	SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
    	String sql;
    	Cursor c;

    	sql = "SELECT SUM(value) AS value FROM incomes WHERE done = 0";
	    c = db.rawQuery(sql, null);
	    if (c != null) { c.moveToFirst(); }
	    Double incomesTotal = (c != null) ? c.getDouble(c.getColumnIndex("value")) : 0.00;

    	sql = "SELECT SUM(value) AS value FROM expenses WHERE done = 0";
	    c = db.rawQuery(sql, null);
	    if (c != null) { c.moveToFirst(); }
	    Double expensesTotal = (c != null) ? c.getDouble(c.getColumnIndex("value")) : 0.00;

    	Double balanceTotal = incomesTotal - expensesTotal;

    	String incomes = currencyFormat.format(incomesTotal);
    	String expenses = currencyFormat.format(expensesTotal);
    	String balance = currencyFormat.format(balanceTotal);
    	int balanceColor = (balanceTotal > 0.0) ? Color.rgb(0, 102, 204) : Color.rgb(204, 0, 0);

        ArrayList<ItemType> itemTypes = new ArrayList<ItemType>();
        itemTypes.add(new ItemType(getResources().getString(R.string.incomes), incomes, Color.BLACK));
        itemTypes.add(new ItemType(getResources().getString(R.string.expenses), expenses, Color.BLACK)); 
        itemTypes.add(new ItemType(getResources().getString(R.string.balance), balance, balanceColor)); 

        return itemTypes;
    }
}
