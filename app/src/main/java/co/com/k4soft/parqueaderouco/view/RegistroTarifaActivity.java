package co.com.k4soft.parqueaderouco.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.k4soft.parqueaderouco.R;
import co.com.k4soft.parqueaderouco.entidades.Tarifa;
import co.com.k4soft.parqueaderouco.persistencia.room.DataBaseHelper;
import co.com.k4soft.parqueaderouco.utilities.ActionBarUtil;

public class RegistroTarifaActivity extends AppCompatActivity {

    @BindView(R.id.txtNombreTarifa)
    public EditText txtNombreTarifa;
    @BindView(R.id.txtValorTarifa)
    public EditText txtValorTarifa;
    @BindView(R.id.txtIdTarifa)
    public EditText txtIdTarifa;
    private ActionBarUtil actionBarUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_tarifa);
        ButterKnife.bind(this);
        initComponents();
        recibirObjeto();
    }

    private void initComponents() {
        actionBarUtil = new ActionBarUtil(this);
        actionBarUtil.setToolBar(getString(R.string.configurar_tarifa));
    }

    private  void  recibirObjeto(){
        Bundle objetoEnviado = getIntent().getExtras();

        Tarifa tarifa = null;
        if(objetoEnviado != null){
            tarifa= (Tarifa) objetoEnviado.getSerializable("tarifa");

            txtNombreTarifa.setText(tarifa.getNombre());
            txtValorTarifa.setText( tarifa.getPrecio()+"");
            txtIdTarifa.setText(tarifa.getIdTarifa().toString());
        }

    }


    public void guardar(View view) {
        String nombreTarifa = txtNombreTarifa.getText().toString();
        Double valorTarifa = toDouble(txtValorTarifa.getText().toString());
        String idTarifa = txtIdTarifa.getText().toString();

        if(idTarifa.isEmpty()) {
            if (validarInformacion(nombreTarifa, valorTarifa)) {
                Tarifa tarifa = getTarifa(nombreTarifa, valorTarifa);
                new InsercionTarifa().execute(tarifa);
                finish();
            }
        }
        else{
            if (validarInformacion(nombreTarifa, valorTarifa)) {
                Tarifa tarifa = getTarifaActualizar(nombreTarifa, valorTarifa, Integer.parseInt(idTarifa));
                new ActualizarTarifa().execute(tarifa);
                finish();
            }
        }
    }


    private Tarifa getTarifa(String nombreTarifa, Double valorTarifa) {
        Tarifa tarifa = new Tarifa();
        tarifa.setNombre(nombreTarifa);
        tarifa.setPrecio(valorTarifa);
        return tarifa;
    }

    private Tarifa getTarifaActualizar(String nombreTarifa, Double valorTarifa, Integer idTarifa) {
        Tarifa tarifa = new Tarifa();
        tarifa.setIdTarifa(idTarifa);
        tarifa.setNombre(nombreTarifa);
        tarifa.setPrecio(valorTarifa);
        return tarifa;
    }

    private boolean validarInformacion(String nombreTarifa, Double valorTarifa) {
        boolean esValido = true;
        if ("".equals(nombreTarifa)) {
            txtNombreTarifa.setError(getString(R.string.requerido));
            esValido = false;
        }

        if (valorTarifa == 0) {
            txtValorTarifa.setError(getString(R.string.requerido));
            esValido = false;
        }
        return esValido;
    }

    private Double toDouble(String valor) {
        return "".equals(valor) ? 0 : Double.parseDouble(valor);
    }


    private class InsercionTarifa extends AsyncTask<Tarifa, Void, Void> {

        @Override
        protected Void doInBackground(Tarifa... tarifas) {
            DataBaseHelper.getSimpleDB(getApplicationContext()).getTarifaDAO().insert(tarifas[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), getString(R.string.successfully), Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }


    private class ActualizarTarifa extends AsyncTask<Tarifa, Void, Void> {

        @Override
        protected Void doInBackground(Tarifa... tarifas) {
            DataBaseHelper.getSimpleDB(getApplicationContext()).getTarifaDAO().update(tarifas[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), getString(R.string.Tarifa_actualizada_correctamente), Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}