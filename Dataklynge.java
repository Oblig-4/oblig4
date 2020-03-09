/*
Dette er et program som lager en klasse "Dataklynge" som modellerer en dataklynge med racks og noder.
Klassen skal holde rede på en liste med racks, og plassere noder i disse.
*/

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

class Dataklynge {

  private int noderPerRack;
  private ArrayList<Rack> racks;

  // Oppretter en dataklynge en tom array list av racks, med oppgitt maks antall noder per rack.
  // Oppretter et rack som legges til i den tomme dataklyngen.
  // @param noderPerRack er maks antall noder per rack.
  public Dataklynge(int noderPerRack) {
    this.noderPerRack = noderPerRack;
    racks = new ArrayList<Rack>();
    racks.add(new Rack(noderPerRack));
  }

  // Alternativ konstruktør som leser data om dataklynge fra fil, og oppretter en dataklynge med racks og noder.
  // Oppretter først et File objekt med filnavnet, som deretter brukes til å opprette et Scanner objekt som leser filen.
  // Leser den første linjen, som konverteres fra en String til en int, som tilordnes variabelen "noderPerRack",
  // som videre brukes til å opprette et rack som legges til i den tomme dataklyngen.
  // Videre bruker vi en while-løkke til å lese hver linje i filen, som splittes til en String-array som vi kaller "data".
  // Antall noder vil være på indeks 0 i "data", og angis derfor som data[0] og konverteres til int.
  // Minnestørrelse vil være på indeks 1 i "data", og angis derfor som data[1] og konverteres til int.
  // Antall prosessorer vil være på indeks 2, og angis derfor som data[2] og konverteres til int.
  // Dermed bruker vi enda while-løkke som setter inn et gitt antall noder med minnestørrelse og prosessorantall.
  // @param filnavn er navnet på filen med data om dataklyngen-
  public Dataklynge(String filnavn) throws FileNotFoundException {
    racks = new ArrayList<Rack>();
    File fil = new File(filnavn);
    Scanner innfil = new Scanner(fil);
    noderPerRack = Integer.parseInt(innfil.nextLine());
    racks.add(new Rack(noderPerRack));

    String linje = "";
    while (innfil.hasNextLine()) {
      linje = innfil.nextLine();
      String[] data = linje.split(" ");
      int antNoder = Integer.parseInt(data[0]);
      int minne = Integer.parseInt(data[1]);
      int antPros = Integer.parseInt(data[2]);
      int i = 0;
      while (i < antNoder) {
        this.settInnNode(new Node(minne, antPros));
        i++;
      }
    }

    innfil.close();
  }

  // Setter inn en ny node i et rack med ledig plass, eller i et nytt rack.
  // Bruker en for-løkke til å iterere gjennom array listen "racks", og gjør deretter en if-sjekk på om hver rack-objekt
  // har mindre plass (ved å kalle på metoden "hentantNoder" på rack-objektene) enn maks antall noder per Rack, og dermed ledig plass til en ny node.
  // Dersom det ikke er ledig plass, lages det et nytt rack-objekt i dataklyngen hvor den nye noden settes inn i.
  // @param nyNode er noden som skal settes inn i racket.
  public void settInnNode(Node nyNode) {
    for (Rack rack : racks) {
      if (rack.hentAntNoder() < noderPerRack) {
        rack.settInn(nyNode);
        return;
      }
    }
    Rack r = new Rack(noderPerRack);
    r.settInn(nyNode);
    racks.add(r);

  }

  // Beregner og henter totalt antall prosessorer i dataklyngen.
  // Initialiserer variabelen "antPros" med startverdien 0.
  // Bruker en for-løkke til å iterere gjennom array listen "racks", og kaller på metoden "antProsessorer" på hvert rack-objekt.
  // Summerer antall prosessorer i hvert rack, som blir tilordnet variabelen "antPros".
  // @return antall prosessorer i dataklyngen.
  public int antProsessorer() {
    int antPros = 0;
    for (Rack rack : racks) {
      antPros = antPros + rack.antProsessorer();
    }
    return antPros;
  }

  // Beregner antall noder i dataklyngen med minne over en gitt grense.
  // Initialiserer variabelen "antNoderNokMinne" med startverdien 0.
  // Bruker en for-løkke til å iterere gjennom array listen "racks", og kaller på metoden "noderMedNokMinne" på hvert rack.objekt.
  // Summerer antall noder med nok minne i hvert rack, som blir tilordnet variabelen "antNoderNokMinne".
  // @param paakrevdMinne er GB minne som kreves.
  // @return antall noder i dataklyngen med nok minne.
  public int noderMedNokMinne(int paakrevdMinne) {
    int antNoderNokMinne = 0;
    for (Rack rack : racks) {
      antNoderNokMinne = antNoderNokMinne + rack.noderMedNokMinne(paakrevdMinne);
    }
    return antNoderNokMinne;
  }

  // Henter antall racks i dataklyngen.
  // @return antall racks. 
  public int antRacks() {
    return racks.size();
  }
 }
