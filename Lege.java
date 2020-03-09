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



  public String hentLegensNavn() {
    return navn;
  }

//Leger sorteres alfabetisk etter navn.
  public int compareTo(Lege annen){
    return navn.compareTo(annen.hentLegensNavn());
  }

//Metoden gjør at det er mulig å hente ut en liste av resepter, som legen har skrevet.
  public Lenkeliste<Resept> hentUtResepter(){
    return utskrevendeResepter;
  }



  @Override
  public String toString() {
    return "Lege\n" + "Navn: " + navn;
  }

  public Hvit skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{
    if (legemiddel instanceof Narkotisk){
      throw new UlovligUtskrift(this, legemiddel, pasient.hentPasientId());
    }
    Hvit h = new Hvit(legemiddel, this, pasient, reit);
    utskrevendeResepter.leggTil(h);
    return h;
  }

  public Millitaer skrivMillitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk){
      throw new UlovligUtskrift(this, legemiddel, pasient.hentPasientId());
    }

    Militaer m = new Millitaer()legemiddel, this, pasient.hentPasientId(), reit);
    utskrevendeResepter.leggTil(m);
    return m;
  }

  public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift;

  public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift;


  
}
