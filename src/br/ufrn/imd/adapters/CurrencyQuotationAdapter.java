package br.ufrn.imd.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.ufrn.imd.budget.R;
import br.ufrn.imd.domain.CurrencyQuotation;

public class CurrencyQuotationAdapter extends ArrayAdapter<CurrencyQuotation> {

    private final Context context;
    private final ArrayList<CurrencyQuotation> currencyQuotations;

    public CurrencyQuotationAdapter(Context context, ArrayList<CurrencyQuotation> currencyQuotations) {
        super(context, R.layout.currency_quotation_row, currencyQuotations);
        this.context = context;
        this.currencyQuotations = currencyQuotations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.currency_quotation_row, parent, false);

        TextView textViewCurrency = (TextView) rowView.findViewById(R.id.textViewCurrency);
        TextView textViewRate = (TextView) rowView.findViewById(R.id.textViewRate);

        textViewCurrency.setText(currencyQuotations.get(position).getCurrency());
        textViewRate.setText(currencyQuotations.get(position).getRate());

        return rowView;
    }
}