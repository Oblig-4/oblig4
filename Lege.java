/*Dette er et program som lager en klasse "Lege" for representasjon av leger.*/

class Lege implements Comparable<Lege> {
  protected Lenkeliste<Resept> utskrevendeResepter;
  protected String navn;

  // @param navn er navnet på legen.

  public Lege(String navn) {
    this.navn = navn;
    utskrevendeResepter = new Lenkeliste<Resept>();
  }



  public String hentNavn() {
    return navn;
  }

//Leger sorteres alfabetisk etter navn.
  public int compareTo(Lege annen){
    return navn.compareTo(annen.hentNavn());
  }

//Metoden gjør at det er mulig å hente ut en liste av resepter, som legen har skrevet.
  public Lenkeliste<Resept> hentUtResepter(){
    return utskrevendeResepter;
  }



  @Override
  public String toString() {
    return "Lege\n" + "Navn: " + navn;
  }

  public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{
    if (legemiddel instanceof Narkotisk){
      throw new UlovligUtskrift(this, legemiddel, pasient.hentPasientId());
    }
    HvitResept h = new HvitResept(legemiddel, this, pasient, reit);
    utskrevendeResepter.leggTil(h);
    return h;
  }

  public MillitaerResept skrivMillitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk){
      throw new UlovligUtskrift(this, legemiddel, pasient.hentPasientId());
    }

    MilitaerResept m = new MillitaerResept(legemiddel, this, pasient, reit);
    utskrevendeResepter.leggTil(m);
    return m;
  }

  public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk){
      throw new UlovligUtskrift(this, legemiddel, pasient.hentPasientId());
    }

    PResept p = new PResept(legemiddel, this, pasient);
    utskrevendeResepter.leggTil(p);
    return p;
  }

  public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk){
      throw new UlovligUtskrift(this, legemiddel, pasient.hentPasientId());
    }

    BlaaResept b = new BlaaResept(legemiddel, this, pasient, reit);
    utskrevendeResepter.leggTil(b);
    return b;
  }
}
