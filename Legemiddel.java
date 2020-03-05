/*
Dette er et program som lager en abstract klasse "Legemiddel" for representasjon av legemidler.
Siden klassen er abstract, er det ikke mulig å opprette en instans av klassen (kun av subklassene).
*/

abstract class Legemiddel {
  protected String navn;
  protected double pris;
  protected double virkestoff;
  protected int id;
  private static int teller = 0; // deklarerer en static variabel "teller" som initialiseres med verdien 0.

  // @param navn er navnet på legemiddelet.
  // @param pris er prisen på legemiddelet.
  // @param virkestoff er mg virkestoff i legemiddelet.
  // "id" settes til å bli lik "teller", som deretter økes med 1. Siden "teller" er static vil verdien øke for hver instans av klassen, slik at hvert legemiddel får en unik id.
  public Legemiddel(String navn, double pris, double virkestoff) {
    this.navn = navn;
    this.pris = pris;
    this.virkestoff = virkestoff;
    id = teller;
    teller++;
  }

  public int hentId() {
    return id;
  }

  public String hentNavn() {
    return navn;
  }

  public double hentPris() {
    return pris;
  }

  public double hentVirkestoff() {
    return virkestoff;
  }

  public void settNyPris(double nyPris) {
    pris = nyPris;
  }

  // Overskriver toString() metoden fra klassen "Object".
  // Metoden er abstract for å tvinge subklassene til å overskrive metoden.
  @Override
  public abstract String toString();

}
