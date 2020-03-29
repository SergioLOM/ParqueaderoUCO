package co.com.k4soft.parqueaderouco.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.k4soft.parqueaderouco.R;
import co.com.k4soft.parqueaderouco.adapter.TarifaAdapter;
import co.com.k4soft.parqueaderouco.entidades.Tarifa;
import co.com.k4soft.parqueaderouco.persistencia.room.DataBaseHelper;
import co.com.k4soft.parqueaderouco.utilities.ActionBarUtil;

public class TarifaActivity extends AppCompatActivity {

    private ActionBarUtil actionBarUtil;
    @BindView(R.id.listViewTarifas)
    public ListView listViewTarifas;
    public List<Tarifa> listaTarifas;
    private TarifaAdapter tarifaAdapter;
    DataBaseHelper db;

    public void goToRegistroTarifa(View view) {
        Intent intent = new Intent(this, RegistroTarifaActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarifa);
        ButterKnife.bind(this);
        initComponents();
        loadTarifas();
        onClickItem();
    }

    private void loadTarifas() {
        listaTarifas = db.getTarifaDAO().listar();
        if (listaTarifas.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.sin_tarifas, Toast.LENGTH_SHORT).show();
        } else {
            tarifaAdapter = new TarifaAdapter(this,listaTarifas);
            listViewTarifas.setAdapter(tarifaAdapter);
        }

    }

    private void onClickItem() {
        listViewTarifas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              ObtenerObjeto(position);
            }
        });
    }

    public void ObtenerObjeto(int position){
        Intent cambio = new Intent(getApplicationContext(), RegistroTarifaActivity.class);
        Tarifa tarifa = listaTarifas.get(position);
        Bundle bundle= new Bundle();
        bundle.putSerializable("tarifa", tarifa);
        cambio.putExtras(bundle);
        startActivity(cambio);
    }

    private void initComponents() {
        db = DataBaseHelper.getDBMainThread(this);
        actionBarUtil = new ActionBarUtil(this);
        actionBarUtil.setToolBar(getString(R.string.tarifas));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRestart() {
        super.onRestart();
        loadTarifas();
    }

}
