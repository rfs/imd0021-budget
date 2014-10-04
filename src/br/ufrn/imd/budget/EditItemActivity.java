package br.ufrn.imd.budget;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import br.ufrn.imd.budget.R;
import br.ufrn.imd.domain.Item;
import br.ufrn.imd.helpers.DatabaseHelper;

public class EditItemActivity extends ActionBarActivity {

	String type;
	Item item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		type = bundle.getString("type");
		item = (Item) bundle.getSerializable("item");

		setContentView(R.layout.activity_edit_item);
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (type.equals("incomes")) {
			if (item == null) {
				setTitle(getResources().getString(R.string.add_new_income));
			}
			else {
				setTitle(getResources().getString(R.string.edit_income));			
			}
			((CheckBox) findViewById(R.id.checkBoxDone)).setText(R.string.mark_as_received);
		}
		else {
			if (item == null) {
				setTitle(getResources().getString(R.string.add_new_expense));
			}
			else {
				setTitle(getResources().getString(R.string.edit_expense));			
			}
			((CheckBox) findViewById(R.id.checkBoxDone)).setText(R.string.mark_as_paid);
		}

		if (item != null) {
	    	Locale locale = getResources().getConfiguration().locale;
	        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
	        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);        

       		TextView editTextTitle = (TextView) findViewById(R.id.editTextTitle);
       		TextView editTextValue = (TextView) findViewById(R.id.editTextValue);
       		DatePicker datePickerDueDate = (DatePicker) findViewById(R.id.datePickerDueDate);
       		CheckBox checkBoxDone = (CheckBox) findViewById(R.id.checkBoxDone);

        	try {
       			Date dueDate = dateFormat.parse(item.getDueDate());
	       		Calendar calendar = Calendar.getInstance();
	       		calendar.setTime(dueDate);
	       		int year = calendar.get(Calendar.YEAR);
	       		int month = calendar.get(Calendar.MONTH);
	       		int day = calendar.get(Calendar.DAY_OF_MONTH);

				editTextTitle.setText(item.getTitle());
				editTextValue.setText(currencyFormat.parse(item.getValue()).toString());
				datePickerDueDate.updateDate(year, month, day);
				checkBoxDone.setChecked(item.getDone());
       		
        	} catch (ParseException e) {
	    	    Log.e("Error", e.getLocalizedMessage());
	    	}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);

    	menu.add("save_item").setIcon(R.drawable.ic_action_accept).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

    	return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
    	if (menuItem.getTitle().equals("save_item")) {
    		SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();

       		TextView editTextTitle = (TextView) findViewById(R.id.editTextTitle);
       		TextView editTextValue = (TextView) findViewById(R.id.editTextValue);
       		DatePicker datePickerDueDate = (DatePicker) findViewById(R.id.datePickerDueDate);
       		CheckBox checkBoxDone = (CheckBox) findViewById(R.id.checkBoxDone);
       		
       		Locale locale = getResources().getConfiguration().locale;
       		String year = String.format(locale, "%04d", datePickerDueDate.getYear());
       		String month = String.format(locale, "%02d", datePickerDueDate.getMonth()+1);
       		String day = String.format(locale, "%02d", datePickerDueDate.getDayOfMonth());
       		
       	    ContentValues values = new ContentValues();
       	    values.put("title", editTextTitle.getText().toString());
       	    values.put("value", editTextValue.getText().toString());
       	    values.put("dueDate", year + "-" + month + "-" + day);
       	    values.put("done", (checkBoxDone.isChecked()) ? 1 : 0);

       	    if (item == null) {
       	    	db.insert(type, null, values);
       	    }
       	    else {
       	    	db.update(type, values, "id = ?", new String[] {String.valueOf(item.getId())});
       	    }

       	    finish();
    	}
		return super.onOptionsItemSelected(menuItem);
	}
}
