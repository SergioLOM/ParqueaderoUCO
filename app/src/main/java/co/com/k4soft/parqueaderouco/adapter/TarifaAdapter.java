package co.com.k4soft.parqueaderouco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.k4soft.parqueaderouco.R;
import co.com.k4soft.parqueaderouco.entidades.Tarifa;

public class TarifaAdapter extends BaseAdapter implements Filterable {
    private final Context context;
    private final LayoutInflater inflater;
    private List<Tarifa> listaTarifasOut;
    private List<Tarifa> listaTarifasIn;

    public TarifaAdapter(Context context, List<Tarifa> listaTarifas){
        this.context=context;
        listaTarifasOut = listaTarifas;
        listaTarifasIn = listaTarifas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaTarifasOut.size();
    }

    @Override
    public Tarifa getItem(int position) {
        return listaTarifasOut.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.tarifa_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.txtNombreTarifa.setText(listaTarifasOut.get(position).getNombre());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaTarifasOut = (List<Tarifa>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Tarifa> FilteredArrList = new ArrayList<>();
                if (listaTarifasIn == null) {
                    listaTarifasIn = new ArrayList<>(listaTarifasOut);
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = listaTarifasIn.size();
                    results.values = listaTarifasIn;
                } else {

                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < listaTarifasIn.size(); i++) {
                        String data = listaTarifasIn.get(i).getNombre();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(listaTarifasIn.get(i));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    class ViewHolder{
        @BindView(R.id.txtNombreTarifa)
        TextView txtNombreTarifa;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
