package br.ufrn.imd.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.ufrn.imd.budget.R;
import br.ufrn.imd.domain.Item;

public class ItemAdapter extends ArrayAdapter<Item> {

    private final Context context;
    private final ArrayList<Item> items;

    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.item_row, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_row, parent, false);

        TextView textViewTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
        TextView textViewValue = (TextView) rowView.findViewById(R.id.textViewValue);
        TextView textViewDueDate = (TextView) rowView.findViewById(R.id.textViewDueDate);

        textViewTitle.setText(items.get(position).getTitle());
        textViewValue.setText(items.get(position).getValue());
        textViewDueDate.setText(items.get(position).getDueDate());

        textViewTitle.setTextColor(items.get(position).getColor());
        textViewValue.setTextColor(items.get(position).getColor());
        textViewDueDate.setTextColor(items.get(position).getColor());

        if (items.get(position).getDone()) {
        	textViewTitle.setPaintFlags(textViewTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        	textViewValue.setPaintFlags(textViewValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        	textViewDueDate.setPaintFlags(textViewDueDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        return rowView;
    }
}
