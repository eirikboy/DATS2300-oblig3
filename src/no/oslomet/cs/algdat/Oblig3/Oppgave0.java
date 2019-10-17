package no.oslomet.cs.algdat.Oblig3;

import java.util.Comparator;

public class Oppgave0 {
    public static void main(String[] args) {
        ObligSBinTre<Integer> treInt = new ObligSBinTre<>(Comparator.naturalOrder());
        ObligSBinTre<Character> treChar = new ObligSBinTre<>(Comparator.naturalOrder());
        ObligSBinTre<String> treString = new ObligSBinTre<>(Comparator.naturalOrder());

        System.out.println(treInt.antall());//Gir 0 som utskrift
        System.out.println(treChar.antall());//Gir 0 som utskrift
        System.out.println(treString.antall());//Gir 0 som utskrift
    }
}
