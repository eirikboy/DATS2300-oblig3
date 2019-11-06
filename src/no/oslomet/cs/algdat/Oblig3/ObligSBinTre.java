package no.oslomet.cs.algdat.Oblig3;

////////////////// ObligSBinTre /////////////////////////////////

import java.util.*;

public class ObligSBinTre<T> implements Beholder<T>
{
  private static final class Node<T>   // en indre nodeklasse
  {
    private T verdi;                   // nodens verdi
    private Node<T> venstre, høyre;    // venstre og høyre barn
    private Node<T> forelder;          // forelder

    // konstruktør
    private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder)
    {
      this.verdi = verdi;
      venstre = v; høyre = h;
      this.forelder = forelder;
    }

    private Node(T verdi, Node<T> forelder)  // konstruktør
    {
      this(verdi, null, null, forelder);
    }

    @Override
    public String toString(){ return "" + verdi;}

  } // class Node

  private Node<T> rot;                            // peker til rotnoden
  private int antall;                             // antall noder
  private int endringer;                          // antall endringer

  private final Comparator<? super T> comp;       // komparator

  public ObligSBinTre(Comparator<? super T> c)    // konstruktør
  {
    rot = null;
    antall = 0;
    comp = c;
  }
  
  @Override
  public boolean leggInn(T verdi) {
    Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

    Node<T> p = rot, q = null;               // p starter i roten
    int cmp = 0;                             // hjelpevariabel

    while (p != null)       // fortsetter til p er ute av treet
    {
      q = p;                                 // q er forelder til p
      cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
      p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
    }

    // p er nå null, dvs. ute av treet, q er den siste vi passerte

    p = new Node<T>(verdi, q);               // oppretter en ny node med verdi og foreldrenode

    if (q == null) rot = p;                  // p blir rotnode
    else if (cmp < 0) q.venstre = p;         // venstre barn til q
    else q.høyre = p;                        // høyre barn til q

    antall++;                                // én verdi mer i treet
    return true;                             // vellykket innlegging
  }
  
  @Override
  public boolean inneholder(T verdi)
  {
    if (verdi == null) return false;

    Node<T> p = rot;

    while (p != null)
    {
      int cmp = comp.compare(verdi, p.verdi);
      if (cmp < 0) p = p.venstre;
      else if (cmp > 0) p = p.høyre;
      else return true;
    }

    return false;
  }
  
  @Override
  public boolean fjern(T verdi) {
    if (verdi == null) return false;  // treet har ingen nullverdier

    Node<T> p = rot, q = null;   // q skal være forelder til p

    while (p != null){ //leter etter en verdi
      int cmp = comp.compare(verdi,p.verdi);      // sammenligner
      if (cmp < 0) { q = p; p = p.venstre; }      // går til venstre
      else if (cmp > 0) { q = p; p = p.høyre; }   // går til høyre
      else break;    // den søkte verdien ligger i p
    }
    if (p == null) return false;   // finner ikke verdi

    if (p.venstre == null || p.høyre == null){  // Tilfelle 1) og 2)
      Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
      if (p == rot){
        rot = b;
        if (b != null) b.forelder = q;
      } else if (p == q.venstre){
        q.venstre = b;
        if (b != null) b.forelder = q;
      } else{
        q.høyre = b;
        if (b != null) b.forelder = q;
      }
    }
    else  // Tilfelle 3)
    {
      Node<T> s = p, r = p.høyre;   // finner neste i inorden
      while (r.venstre != null) {
        s = r;    // s er forelder til r
        r = r.venstre;
      }

      p.verdi = r.verdi;   // kopierer verdien i r til p

      if (s != p){
        s.venstre = r.høyre;
        if (r.høyre != null) r.høyre.forelder = s;
      } else {
        s.høyre = r.høyre;
        if (r.høyre != null) r.høyre.forelder = s;
      }
    }

    antall--;   // det er nå én node mindre i treet
    return true;
  }
  
  public int fjernAlle(T verdi) {
    if (tom()){
      return 0;
    }

    Node<T> p = rot, q = null;   // q skal være forelder til p
    int funnet = 0;

    while (p != null){ //leter etter en verdi
      int cmp = comp.compare(verdi,p.verdi);      // sammenligner
      if (cmp < 0) { q = p; p = p.venstre; }      // går til venstre
      else if (cmp > 0) { q = p; p = p.høyre; }   // går til høyre
      else {
        funnet++;
        p = p.høyre; //Finner samme verdi til høyre
        fjern(verdi);
      }
    }

    return funnet;

  }
  
  @Override
  public int antall()
  {
    return antall;
  }
  
  public int antall(T verdi) {
    if (verdi == null){
      return antall;
    }

    Node<T> p = rot;
    int funnet = 0; //Teller opp hvor mange forekomster av verdi

    while (p != null) {
      int cmp = comp.compare(verdi, p.verdi);
      if (cmp < 0) p = p.venstre;
      else if (cmp > 0) p = p.høyre;
      else {
        funnet++;
        p = p.høyre; //Finner samme verdi til høyre
      }
    }

    return funnet;
  }
  
  @Override
  public boolean tom()
  {
    return antall == 0;
  }
  
  @Override
  public void nullstill() {
    if(!tom()){
      nullstill(rot);
      rot = null;
      antall = 0;
    }
  }

  public void nullstill(Node<T> p ){
    if (p.venstre != null){
      nullstill(p.venstre);
      p.venstre = null;
      p.forelder = null;
    }

    if (p.høyre != null){
      nullstill(p.høyre);
      p.høyre = null;
      p.forelder = null;
    }
    p.verdi = null;
  }
  
  private static <T> Node<T> nesteInorden(Node<T> p) {
    if (p.høyre != null){
      p = p.høyre;
      while (p.venstre != null){
        p = p.venstre;
      }
    } else {
      while (p.forelder != null && p == p.forelder.høyre){
        p = p.forelder;
      }
      return p.forelder;
    }
    return p;
  }
  
  @Override
  public String toString() {
    if (tom()){
      return "[]";
    }

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");

    Node<T> p = rot;
    while (p.venstre != null){
      p = p.venstre;
    }
    stringBuilder.append(p.verdi);

    while (nesteInorden(p) != null){
      stringBuilder.append(", ");
      p = nesteInorden(p);
      stringBuilder.append(p.verdi);
    }
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
  
  public String omvendtString() {
    if (tom()){
      return "[]";
    }

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");

    Stakk<Node<T>> stakk = new TabellStakk<>();
    Node<T> p = rot;   // starter i roten og går til venstre
    while (p.venstre != null){
      p = p.venstre;
    }
    stakk.leggInn(p);

    while (nesteInorden(p) != null){
      p = nesteInorden(p);
      stakk.leggInn(p);
    }

    while (!stakk.tom()){
      p = stakk.taUt();
      stringBuilder.append(p.verdi);
      if (!stakk.tom()){
        stringBuilder.append(", ");
      }
    }
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
  
  public String høyreGren() {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String lengstGren()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String[] grener()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String bladnodeverdier()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String postString()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  @Override
  public Iterator<T> iterator()
  {
    return new BladnodeIterator();
  }
  
  private class BladnodeIterator implements Iterator<T>
  {
    private Node<T> p = rot, q = null;
    private boolean removeOK = false;
    private int iteratorendringer = endringer;
    
    private BladnodeIterator()  // konstruktør
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }
    
    @Override
    public boolean hasNext()
    {
      return p != null;  // Denne skal ikke endres!
    }
    
    @Override
    public T next()
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }
    
    @Override
    public void remove()
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

  } // BladnodeIterator

} // ObligSBinTre
