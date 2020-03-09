/*
Dette er et program som lager en abstract klasse "Resept" for representasjon av resepter på legemidler.
Siden klassen er abstract, er det ikke mulig å opprette en instans av klassen (kun av subklassene).
*/

abstract class Resept {
  protected Legemiddel legemiddel;
  protected Lege utskrivendeLege;
  protected int pasientId;
  protected int reit;
  protected int id;
  private static int teller = 0; // deklarerer en static variabel "teller" som initialiseres med verdien 0.

  // @param legemiddel er en referanse til et objekt av klassen "Legemiddel".
  // @param utskrivendeLege er en referanse til et objekt av klassen "Lege" som har skrevet ut resepten.
  // @param pasientId er ID-en til pasienten som eier resepten.
  // @param reit er antall ganger som er igjen på resepten.
  // "id" settes til å bli lik "teller", som deretter økes med 1. Siden "teller" er static vil verdien øke for hver instans av klassen, slik at hver resept får en unik id.
  public Resept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
    this.legemiddel = legemiddel;
    this.utskrivendeLege = utskrivendeLege;
    this.pasient = pasient;
    this.reit = reit;
    id = teller;
    teller++;
  }

  public int hentId() {
    return id;
  }

  public Legemiddel hentLegemiddel() {
    return legemiddel;
  }

  public Lege hentLege() {
    return utskrivendeLege;
  }

  public int hentReit() {
    return reit;
  }

  // Forsøker å bruke resepten én gang, returnerer false om resepten er oppbrukt (altså at reit < 0), ellers returneres true.
  public boolean bruk() {
    reit -= 1;
    if (reit < 0) {
      return false;
    }
    return true;
  }

  // Abstract metode som må implementeres i subklassene.
  abstract public String farge();

  // Abstract metode som må implementeres i subklassene.
  abstract public double prisAaBetale();

  // Overskriver toString() metoden fra klassen "Object".
  // Abstract metode som må implementeres i subklassene.
  @Override
  public abstract String toString();


}
