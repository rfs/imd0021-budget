package br.ufrn.imd.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.ufrn.imd.budget.R;
import br.ufrn.imd.domain.ItemType;

public class ItemTypeAdapter extends ArrayAdapter<ItemType> {

    private final Context context;
    private final ArrayList<ItemType> itemTypes;

    public ItemTypeAdapter(Context context, ArrayList<ItemType> itemTypes) {
        super(context, R.layout.item_type_row, itemTypes);
        this.context = context;
        this.itemTypes = itemTypes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_type_row, parent, false);

        TextView textViewTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
        TextView textViewTotal = (TextView) rowView.findViewById(R.id.textViewTotal);

        textViewTitle.setText(itemTypes.get(position).getTitle());
        textViewTotal.setText(itemTypes.get(position).getTotal());
        textViewTotal.setTextColor(itemTypes.get(position).getColor());

        return rowView;
    }
}