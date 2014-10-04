package br.ufrn.imd.tasks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import br.ufrn.imd.adapters.CurrencyQuotationAdapter;
import br.ufrn.imd.budget.CurrencyQuotationsActivity;
import br.ufrn.imd.domain.CurrencyQuotation;

public class CurrencyQuotationsFromYahooTask extends AsyncTask<Void, Void, List<CurrencyQuotation>> {

	Context context;

	public CurrencyQuotationsFromYahooTask(Context context) {
		this.context = context;
	}

	protected List<CurrencyQuotation> doInBackground(Void... params) {
		String[] convertions = new String[]{"EURBRL","USDBRL","CADBRL","AUDBRL","GBPBRL","CHFBRL","JPYBRL","CNYBRL","INRBRL","RUBBRL"};		

		DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22" +
        							  TextUtils.join("%2C", convertions) +
        							  "%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
        List<CurrencyQuotation> results = null;

        try {
	        HttpResponse response = httpClient.execute(request);
	        HttpEntity entity = response.getEntity();

	        if (entity != null) {
	        	InputStream stream = entity.getContent();	        	
	        	String json = toString(stream);	        	
	        	stream.close();
	        	results = getQuotations(json);
	        }
        } catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.i("Client Protocol Exception", e + "");
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IO Exception", e + "");
		}

        return results;
	}

	protected void onPostExecute(List<CurrencyQuotation> result) {
		Locale locale = ((ListActivity) context).getResources().getConfiguration().locale;
		CurrencyQuotationAdapter adapter = new CurrencyQuotationAdapter(context, generateData(result, locale));
		CurrencyQuotationsActivity activity = (CurrencyQuotationsActivity) context;
		activity.setListAdapter(adapter);
		activity.getProgressDialog().dismiss();
	}

	private List<CurrencyQuotation> getQuotations(String jsonStr) {
		List<CurrencyQuotation> result = new ArrayList<CurrencyQuotation>();

		try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			JSONArray rates = jsonObj.getJSONObject("query").getJSONObject("results").getJSONArray("rate");

			for (int i = 0; i < rates.length(); i++) {
				JSONObject rate = rates.getJSONObject(i);
				CurrencyQuotation pojo = new CurrencyQuotation(rate.getString("Name"), rate.getString("Rate"));
				result.add(pojo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("JSON Exception", e + "");
		}
		return result;
	}

	private String toString(InputStream is) throws IOException {
		byte[] bytes = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int lidos;
		while ((lidos = is.read(bytes)) > 0) {
			baos.write(bytes, 0, lidos);
		}
		return new String(baos.toByteArray());
	}

    private ArrayList<CurrencyQuotation> generateData(List<CurrencyQuotation> result, Locale locale){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

        ArrayList<CurrencyQuotation> currencyQuotations = new ArrayList<CurrencyQuotation>();

        for (int i = 0; i < result.size(); i++) {
			CurrencyQuotation currencyQuotation = (CurrencyQuotation) result.get(i);

			currencyQuotations.add(new CurrencyQuotation(
	        	currencyQuotation.getCurrency(),
	        	currencyFormat.format(Double.parseDouble(currencyQuotation.getRate()))
	        ));
		}

        return currencyQuotations;
    }
}
