package br.ufrn.imd.budget;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import br.ufrn.imd.tasks.CurrencyQuotationsFromYahooTask;

public class CurrencyQuotationsActivity extends ListActivity {

	ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();

		progressDialog = ProgressDialog.show(this, "", "Carregando cotações. Aguarde...", true);

        new CurrencyQuotationsFromYahooTask(this).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.currency_quotations, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}
}
