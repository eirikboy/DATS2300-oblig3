package no.oslomet.cs.algdat.Oblig3;

import java.util.Comparator;

public class Oppgave0 {
    public static void main(String[] args) {
//        ObligSBinTre<Integer> treInt = new ObligSBinTre<>(Comparator.naturalOrder());
//        ObligSBinTre<Character> treChar = new ObligSBinTre<>(Comparator.naturalOrder());
//        ObligSBinTre<String> treString = new ObligSBinTre<>(Comparator.naturalOrder());
//
//        System.out.println(treInt.antall());//Gir 0 som utskrift
//        System.out.println(treChar.antall());//Gir 0 som utskrift
//        System.out.println(treString.antall());//Gir 0 som utskrift
//
//
//        Integer[] a = {4,7,2,9,5,10,8,1,3,6};
//        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
//        for (int verdi : a) tre.leggInn(verdi);
//        System.out.println(tre.antall()); // Utskrift: 10

//        Integer[] a = {4,7,2,9,4,10,8,7,4,6};
//        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
//        for (int verdi : a) tre.leggInn(verdi);
//        System.out.println(tre.antall()); // Utskrift: 10
//        System.out.println(tre.antall(5)); // Utskrift: 0
//        System.out.println(tre.antall(4)); // Utskrift: 3
//        System.out.println(tre.antall(7)); // Utskrift: 2
//        System.out.println(tre.antall(10)); // Utskrift: 1
//        int[] a = {4,7,2,9,4,10,8,7,4,6,1};
//        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
//        for (int verdi : a) tre.leggInn(verdi);
//        System.out.println(tre); // [1, 2, 4, 4, 4, 6, 7, 7, 8, 9, 10]
//
//        int[] a = {4,7,2,9,4,10,8,7,4,6,1};
//        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
//        for (int verdi : a) tre.leggInn(verdi);
//        System.out.println(tre.omvendtString()); // [10, 9, 8, 7, 7, 6, 4, 4, 4, 2, 1]
        int[] a = {4,7,2,9,4,10,8,7,4,6,1};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder()); for (int verdi : a) tre.leggInn(verdi);
        System.out.println(tre.fjernAlle(4));// 3 tre.fjernAlle(7); tre.fjern(8);
        System.out.println(tre.antall());// 5
        System.out.println(tre + " " + tre.omvendtString());
// [1, 2, 6, 9, 10] [10, 9, 6, 2, 1]
// OBS: Hvis du ikke har gjort oppgave 4 kan du her bruke toString()
    }
}
