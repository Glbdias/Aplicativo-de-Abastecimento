package com.brito.abastecimento;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import com.brito.abastecimento.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    //MONTA DROP DOWN
    Spinner spinnerFiltro;
    private static final  String[] paths = {"Nenhum","Gasolina", "Álcool", "Gás Veicular", "Diesel"};
    // FIM DROP DOWN

    ArrayAdapter<Abastecimentos> adapter;

    Button btnBuscar;

    Button btnLimpar;

    EditText txtDataInicial;

    EditText txtDataFinal;

    TextView txtReultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setImageResource(R.drawable.add);

        // PEGA OS ELEMENTOS PELO ID
        spinnerFiltro = (Spinner) findViewById(R.id.spinnerFiltro);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnLimpar = (Button) findViewById(R.id.btnLimpar);
        txtDataInicial = (EditText) findViewById(R.id.txtDataInicial);
        txtDataFinal = (EditText) findViewById(R.id.txtDataFinal);
        txtReultado = findViewById(R.id.txtResultado);

        //MONTA DROP DOWN
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltro.setAdapter(adapter);
        // FIM DROP DOWN


        // Responsável por abrir uma nova activity
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AbastecimentosActivity.class);
                startActivity(i);
            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    if (!(txtDataInicial.getText().toString().isEmpty() ||
                            txtDataFinal.getText().toString().isEmpty()) && spinnerFiltro.getSelectedItem().toString() != "Nenhum") {
                        consultaCombustivel(spinnerFiltro.getSelectedItem().toString(),
                                txtDataInicial.getText().toString(), txtDataFinal.getText().toString());
                    } else if (!(txtDataInicial.getText().toString().isEmpty() ||
                            txtDataFinal.getText().toString().isEmpty())) {
                        consultaPeriodo(txtDataInicial.getText().toString(), txtDataFinal.getText().toString());
                    } else {
                        chamadaAlerta("Obrigatório informar período ou tipo de combustível!");
                    }
                }catch (Exception e){
                    chamadaAlerta("Atenção! Verifique o formato da data informada");
                }
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                limpaCampos();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        limpaCampos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Função responsável por realizar a consulta por período
    public void consultaPeriodo(String periodoInicial, String periodoFinal){

//        Formata a String do tipo data para yyyymmdd
        String s1 = periodoInicial.substring(6,10) + periodoInicial.substring(3,5) + periodoInicial.substring(0,2);
        String s2 = periodoFinal.substring(6,10) + periodoFinal.substring(3,5) + periodoFinal.substring(0,2);

        int tamanhoArray = Consulta.getLista().size(); // Pega tamanho do array para usar no for
        float ant = 0.0f;
        float total_km = 0.0f;
        float total_combustivel = 0.0f;
        float desempenho = 0;
        int aux = 0;
        String result;

        if(tamanhoArray > 0){
            for(int i=0; i < Consulta.getLista().size() ; i++){
                aux = i;
                String s3 = Consulta.getLista().get(i).getData();
                s3 = s3.substring(6,10) + s3.substring(3,5) + s3.substring(0,2);
                if(s3.compareTo(s1) >= 0 && s3.compareTo(s2) <= 0) {
                    if (ant > 0) {
                        total_km += Consulta.getLista().get(i).getOdometro() - ant;
                        total_combustivel += Consulta.getLista().get(i).getLitros();
                        ant = Consulta.getLista().get(i).getOdometro();

                    } else {
                        ant = Consulta.getLista().get(i).getOdometro();
                        total_combustivel += Consulta.getLista().get(i).getLitros();
                    }
                }
            }
            total_combustivel = (total_combustivel - Consulta.getLista().get(aux).getLitros());

            desempenho = total_km/total_combustivel;

            result = "No período total, o veículo percorreu um total de " + total_km +
                    " quilômetros, utilizando " + total_combustivel + " litros de combustível com o desempenho de " +
                    String.format("%.2f", desempenho)  + " Km/l";

            txtReultado.setText(result);

        }else{
            chamadaAlerta("Não possui nenhum abastecimento para esse período");
        }
    }


//    Função responsável por realizar consulta por combustível
    public void consultaCombustivel(String combustivel, String periodoInicial, String periodoFinal){

        //        Formata a String do tipo data para yyyymmdd
        String s1 = periodoInicial.substring(6,10) + periodoInicial.substring(3,5) + periodoInicial.substring(0,2);
        String s2 = periodoFinal.substring(6,10) + periodoFinal.substring(3,5) + periodoFinal.substring(0,2);
        float ant = 0.0f;
        float total_km = 0.0f;
        float total_combustivel = 0.0f;
        String antCombustivel = combustivel;
        int tamanhoArray = Consulta.getLista().size();
        float desempenho = 0.0f;
        String result;
        if(tamanhoArray > 0){
            for(int i=0; i < Consulta.getLista().size() ; i++){
                String combustivel_aux = Consulta.getLista().get(i).getCombustivel();

                String s3 = Consulta.getLista().get(i).getData();
                s3 = s3.substring(6,10) + s3.substring(3,5) + s3.substring(0,2);
                if(s3.compareTo(s1) >= 0 && s3.compareTo(s2) <= 0) {
                    if (combustivel == combustivel_aux) {
                        if (ant > 0) {
                            if(antCombustivel == combustivel){
                                total_combustivel += Consulta.getLista().get(i).getLitros();
                                total_km += Consulta.getLista().get(i).getOdometro() - ant;
                            }
                            ant = Consulta.getLista().get(i).getOdometro();
                        } else {
                            ant = Consulta.getLista().get(i).getOdometro();
                        }
                    } else {
                        if (antCombustivel == combustivel && combustivel_aux != combustivel
                                && ant>0) {
                            total_combustivel += Consulta.getLista().get(i).getLitros();
                            total_km += (Consulta.getLista().get(i).getOdometro() - ant);
                        }
                        ant = Consulta.getLista().get(i).getOdometro();
                    }

                    antCombustivel = Consulta.getLista().get(i).getCombustivel();
                }
            }
            desempenho =  total_km / total_combustivel;
            result = "No período total e utilizando o combustível " + combustivel
                    +", o veículo percorreu um total de " + total_km +
                    " quilômetros, utilizando " + total_combustivel + " litros de combustível com o desempenho de " +
                    String.format("%.2f", desempenho)  + " Km/l";

            txtReultado.setText(result);
        }else{
            chamadaAlerta("Não possui nenhum abastecimento para essa data e combustível");
        }


    }

    public void chamadaAlerta(String msg){
        // CRIA ALERT PARA INFORMAR O FILTROS NECESSÁRIOS
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Atenção")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Este método é chamado quando o botão OK é clicado
                        dialogInterface.dismiss(); // Aqui fechamos o AlertDialog
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void limpaCampos(){
        txtDataInicial.setText("");
        txtDataFinal.setText("");
        txtReultado.setText("");
        spinnerFiltro.setSelection(0);
    }
}