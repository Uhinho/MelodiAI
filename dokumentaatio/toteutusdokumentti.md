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

## Aika- ja tilavaativuus

Triehen tallennuksen aikavaativuus on O(n*m), missä n on tallennettavien alkioiden määrä ja m markovin aste.
Myös tilavaativuus on O(n*m).
Triestä haku on hyvin tehokasta aikavaativuudella O(n), jossa n on hakuavaimen pituus.

## Generoidut melodiat

Generoidut melodiat kuulostavat suhteellisen järkeviltä. Osumatarkkuus on heikko esim. satunnaisesti valitun alkusävelen vuoksi ja siksi generaattoria voi joutua pyörittämään useaan kertaan ihmiskorvalle järkevän kuuloisen melodian luomiseksi. 
Mikäli Markovin asteen nostaa liian korkealle, alkaa nuottikulku kuulostaa paremmalta
johtuen siitä, että se on suurella todennäköisyydellä suora kopio opetusmateriaalista.


## Jatkokehitys

Projekti on ollut erittäin mielenkiintoinen ja tulenkin varmasti jatkamaan tämän parissa. 
Opetusmateriaalista oli hankala parsia esim. päällekkäisiä nuotteja ja sointuja, vaikkakin niiden lisääminen generoituihin kappaleisiin toisi paljon väriä luotuun musiikkiin. 
Musiikissa mahdollisten kombinaatioiden määrä sävelten ja harmonioiden osalta on niin suuri, että heuristiikan kirjoittaminen tämän ohjaamiseksi on todella työlästä.
