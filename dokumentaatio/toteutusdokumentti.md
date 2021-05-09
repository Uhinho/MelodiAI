# Toteutus

## Rakenne

## Tietorakenteet

### Trie

Opetusmateriaalina käytetystä MIDI-kokoelmasta parsitaan haluttu data(nuotti, pituus, voimakkuus) Trie-puuhun halutun Markovin asteen syvyydellä. Haku palauttaa annettua sekvenssiä seuraavat solmut.

### TrieNode

Triehen tallennettava solmuobjekti. Sisältää avaimen, lapsisolmut, kyseisen avaimen esiintymisten lukumäärän annetussa opetusmateriaalissa, sekä listaobjektin solmua seuraavista avainarvoista.

### DynamicList

Javan ArrayListiä mukaileva dynaamisesti kasvava lista.

## Markovin ketju

### ArraySequencer

Opetusdataan pohjautuvien datasekvenssien luomiseen tarkoitettu luokka.

- **generateSequence:** Palauttaa kokonaislukujonona Markovin ketjuna lasketun sekvenssin halutulla asteella. </p>

- **generateRhytmSequence:** Palauttaa listaobjektin joka sisältää opetusmateriaaliin pohjautuen nuottien pituuksia sovitettuna haluttuun tahtilajiin.

- **getNext:** Määrittää sekvenssin seuraavan nuotin

- **getWeightedRandom:** Arpoo arvon avaimien esiintyvyysmäärällä painotetetusti 

## Midi

### Note

Kuvastaa yksittäistä nuottia MIDI-tiedostossa. Sisältää nuotin pituuden, voimakkuuden ja taajuuden

### MidiParser

Parsii annetusta MIDI-muotoisesta aineistosta nuotit Note-objekteiksi.

### MidiBuilder

Luo uuden MIDI-tiedoston annettujen Markovin ketjujen mukaisesti.
