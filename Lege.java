/*Dette er et program som lager en klasse "Lege" for representasjon av leger.*/

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
      throw new UlovligUtskrift(this, legemiddel, pas);
    }
    HvitResept h = new HvitResept(legemiddel, this, pasient, reit);
    utskrevendeResepter.leggTil(h);
  }
}
