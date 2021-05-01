# Määrittelydokumentti

**Opinto-ohjelma: Tietojenkäsittelytieteen kandiohjelma**

## Aihe

Harjoitustyön aiheena on luoda ohjelma, joka luo MIDI-tiedostoina syötettäviin harjoitusmateriaaleihin perustuvia melodioita. 
Ohjelman output on luodusta melodiasta tehty MIDI-tiedosto.

## Kieli

Java

## Algoritmit ja tietorakenteet

Melodioiden luomiseen käytetään Markovin ketjuun perustuvaa algoritmia. Syötteenä toimii MIDI-tiedostot.
Pääasiallisena tietorakenteena toimii trie-toteutus. Trien lisäksi sovellukseen on toteutettu tietorakenteeksi dynaamisesti skaalautuva listaobjekti. 

## Aika- ja tilavaativuus

Trieen tallentamisen ja triestä hakemisen aikavaativuus on O(m), jossa m on lisättävän/haettavan avaimen pituus.
Tilavaativuus on O(n*k), jossa n on solmujen määrä ja k on yksittäisen avaimen tilavaativuus. Avaimet tallennetaan kokonaislukuina, joten tilavaativuus on suhteellisen suuri. 

## Lähteet 

- https://en.wikipedia.org/wiki/Markov_chain
- [Using Markov chain to compose music pieces](https://www.math.utah.edu/~gustafso/s2016/2270/published-projects-2016/zhang-bopanna/zhangJie-bopannaPrathusha-MarkovChainMusicComposition.pdf)
