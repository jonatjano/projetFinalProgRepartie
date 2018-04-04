# Protocol de communication

## Lors de la connection d'un client
Le client se connecte en UDP à un serveur dont l'adresse est connue.
Le serveur lui répond en lui donnant l’adresse et le port du réseau multicast auquel le client se connecte immédiatement.
Par la suite le seul réseau utilisé par le client est le réseau multicast.
Au même moment le serveur qui possède un client fictif informe tous les clients, par l'intermédiaire du réseau multicast, qu'il va leur re-envoyer tout le dessin depuis le début.

## Lors du dessin
Le client envoie un message sur le réseau multicast indiquant les paramètres de la forme à dessiner.

> e.g. DRAW:CIRCLE:#000000:2 :553 :734 :100 :50

### analyse de la tram

La formation de la trame se fait ainsi :

> TYPE:FORME:COULEUR:REMPLISSAGE:X:Y:PARAMS...

où :
- TYPE peux être :
	- DRAW si on dessine,
	- DEL si on supprime,
	- CLEAR si on supprime tout
- FORME correspond à la forme à dessiner (CIRCLE, SQUARE)
- COULEUR correspond au code hexadecimal de la couleur de la forme
- REMPLISSAGE est un entier indiquant si la forme est pleine ou vide
- X et Y sont les coordonnées de la forme
- PARAMS... correspond à une suite de paramètres séparés pas des ":", il dépendent de la forme à dessiner (la taille de la forme en fait partie)
