package com.brito.abastecimento;

public class Abastecimentos {

    private String data;

    private Float odometro;

    private Float litros;

    private String combustivel;

    public Abastecimentos(String data, Float odometro ,Float litros, String combustivel){
        this.data = data;
        this.odometro = odometro;
        this.litros = litros;
        this.combustivel = combustivel;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public Float getOdometro() {
        return odometro;
    }
    public void setOdometro(Float odometro) {
        this.odometro = odometro;
    }

    public Float getLitros() {
        return litros;
    }
    public void setLitros(Float litros) {
        this.litros = litros;
    }

    public String getCombustivel() {
        return combustivel;
    }
    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    @Override
    public String toString() {
        return "Combustivel:" + combustivel;
    }
}
