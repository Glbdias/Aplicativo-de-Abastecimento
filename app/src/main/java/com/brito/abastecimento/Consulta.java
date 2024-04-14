package com.brito.abastecimento;

import java.util.ArrayList;

public class Consulta {

    private static final ArrayList<Abastecimentos> lista = new ArrayList<Abastecimentos>();

    public static ArrayList<Abastecimentos> getLista(){
        return lista;
    }

    public static void addAbastecimento(Abastecimentos abastecimentos){
        lista.add(abastecimentos);
    }

    public static Abastecimentos getAbastecimento(int pos){
        return lista.get(pos);
    }
}
