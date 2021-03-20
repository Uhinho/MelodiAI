# Määrittelydokumentti

**Opinto-ohjelma: Tietojenkäsittelytieteen kandiohjelma**

## Aihe

Harjoitustyön aiheena on luoda ohjelma, joka luo MIDI-tiedostoina syötettäviin harjoitusmateriaaleihin perustuvia melodioita. 
Ohjelman output on luodusta melodiasta tehty MIDI-tiedosto, joka on mahdollista jatkojalostaa esim. studiotyöskentelyyn tarkoitetuilla mallinnusohjelmilla (Studio One, Cubase, jne.)
MIDI-tiedostojen prosessointiin löytynee hyviä valmiita Java-kirjastoja, joita voidaan käyttää hyväksi datan muuttamiseksi käytettävään muotoon. 

## Kieli

Ohjelma tehdään Javalla.

## Algoritmit ja tietorakenteet

Melodioiden luomiseen käytetään Markovin ketjuun perustuvaa algoritmia. Syötteenä toimii MIDI-tiedostot, joista saadaan sävelten järjestys helposti selville. MIDI on yleinen tiedostomuoto digitaaliselle musiikille ja näin helposti saatavilla. 
MIDI-tiedostoista puretaan sävelten järjestys ja näin saadaan laskettua nuotin n+1 todennäköisyydet matriiseihin. 

## Lähteet 

- https://en.wikipedia.org/wiki/Markov_chain
- [Using Markov chain to compose music pieces](https://www.math.utah.edu/~gustafso/s2016/2270/published-projects-2016/zhang-bopanna/zhangJie-bopannaPrathusha-MarkovChainMusicComposition.pdf)
