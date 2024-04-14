package com.brito.abastecimento;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.brito.abastecimento.databinding.ActivityAbastecimentosBinding;

public class AbastecimentosActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAbastecimentosBinding binding;

    //MONTA DROP DOWN
    private Spinner spinner;
    private static final  String[] paths = {"Gasolina", "Álcool", "Gás Veicular", "Diesel"};
    // FIM DROP DOWN

    EditText txtData;
    EditText txtOdometro;

    EditText txtLitros;

    Button btnCadastrar;

    Button btnVoltar;

    Abastecimentos abastecimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAbastecimentosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        // PEGA OS ELEMENTOS PELO ID
        txtData = (EditText) findViewById(R.id.txtData);
        txtOdometro = (EditText) findViewById(R.id.txtOdometro);
        txtLitros = (EditText) findViewById(R.id.txtLitros);
        spinner = (Spinner) findViewById(R.id.spinnerFiltro);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        //MONTA DROP DOWN
        spinner = (Spinner)findViewById(R.id.spinnerFiltro);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(AbastecimentosActivity.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // FIM DROP DOWN

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtData.getText().toString().isEmpty() ||
                        txtOdometro.getText().toString().isEmpty()
                || txtLitros.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "⚠\uFE0F ⚠\uFE0F Informar data odômetro, litros e combustível", Toast.LENGTH_SHORT).show();
                } else {


//                abastecimento = new Abastecimentos(txtData.getText().toString(),
//                        Float.parseFloat(String.valueOf(txtOdometro.getText())),
//                        Float.parseFloat(String.valueOf(txtLitros.getText())),
//                        spinner.getSelectedItem().toString());
//
//                Consulta.addAbastecimento(abastecimento);


                    abastecimento = new Abastecimentos("01/01/2024",
                            Float.parseFloat("14576"),
                            Float.parseFloat("45"),
                            "Gasolina");

                    Consulta.addAbastecimento(abastecimento);

                    abastecimento = new Abastecimentos("17/01/2024",
                            Float.parseFloat("14786"),
                            Float.parseFloat("40"),
                            "Álcool");

                    Consulta.addAbastecimento(abastecimento);

                    abastecimento = new Abastecimentos("03/02/2024",
                            Float.parseFloat("14966"),
                            Float.parseFloat("48"),
                            "Álcool");

                    Consulta.addAbastecimento(abastecimento);

                    abastecimento = new Abastecimentos("15/02/2024",
                            Float.parseFloat("15178"),
                            Float.parseFloat("46"),
                            "Gasolina");

                    Consulta.addAbastecimento(abastecimento);

                    abastecimento = new Abastecimentos("01/03/2024",
                            Float.parseFloat("15428"),
                            Float.parseFloat("49"),
                            "Gasolina");

                    Consulta.addAbastecimento(abastecimento);
                    finish();
                }
            }
        });


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                }
            }
        );


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }
}