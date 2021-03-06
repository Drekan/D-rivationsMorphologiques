
# HMIN230 - Dérivations morphologiques
**M1** DECOL
**SEGURA** Bastien

## Sommaire

* 1. Architecture
* 2. Exemple d'utilisation

## 1.  Architecture
Nous avons des schémas de transformation du type :

	*er ⇒ *age		//monter   ->   montage
	*ger ⇒ *geoire     	//nager    ->   nageoire
	*uire ⇒ *uction        //instruire ->  instruction
Ces schémas sont essentiellement des règles, avec un ensemble de prémices et un ensemble de conclusions.

Prenons une règle **R1** un peu plus complète : 
	
	R1 = *er & pos == V ⇒ *e & pos = N  
**R1** se décompose comme tel :
* *Prémices(R1)* =  {(*er),(pos == V)}
*  *Conclusions(R1)* = {(*er),(pos = N)}

Les règles peuvent donc s'écrire comme deux ensembles de clauses. On peut voir qu'il existe plusieurs types de clauses, mais dans un premier temps nous considèrerons les type plus élémentaires : les clause "suffixes" (*er, *ger, *uire, *quer ). 
Dans un deuxième temps, seront prisent en compte les clauses nous renseignant sur la nature du mot (Nom, Verbe, Adjectif, etc... )

Voici le diagramme UML représentant les règles et les clauses:
![Derivation-Morphologique-noyau](https://i.imgur.com/WLM8SCa.png)

Ci-dessous, est donnée une explication des comportements importants des deux classes.

-----

### -- Clause --

**Attribut**

*_value* : contient la valeur de la clause (par exemple, "*er")

**Méthodes**

*isSuffixe()* : renvoie vrai si la clause est de type suffixe.

*isNatureConstraint()* : renvoie vrai si la clause est de type "contrainte sur la nature grammaticale". Exemple : r_pos == V signifie que le terme doit être un verbe.

*doesMatch(string)* : étant donné un mot, renvoie vrai si ce mot peut satisfaire la clause. 
Par exemple, 
"*er".doesMatch("manger") -> *true*
 "*eur".doesMatch("manger") -> *false*

------------

### -- Rule --
**Attributs**

*_leftClauseSet* : ensemble des prémices 

*_rightClauseSet* : ensemble des conclusions

**Méthodes**

*Rule(string)*  : Constructeur. Il prend en paramètre une règle du type "*er => *age" et initialise les ensembles de clauses correspondant.

*doesMatch(string)* : prend un mot en paramètre, et renvoie true si la règle matche. Pour qu'un match se produise, il faut que chaque clause des prémices matche avec le mot.	

*apply(string)* : prend un mot en paramètre, et renvoie le mot correspondant à l'application de la règle (si la règle matche avec le mot) . Par exemple, soit la règle **r1** = " *er => *age", alors **r1**.apply("trier") renvoie "triage".


--------

Les règles ont maintenant une représentation, il ne nous manque plus qu'à définir un "Moteur de Dérivation" qui sera capable d'aller charger des règles dans un fichier texte et qui générera toutes les dérivations possible d'un mot en fonction de son ensemble de règle. 

Voici l'UML de cette classe : 

![Derivation-Morphologique-engine](https://i.imgur.com/6XY5WX4.png)


-----

### -- DerivationEngine --
**Attributs**

*_ruleSet* : ensemble de règles qui seront chargées

*_path* : le chemin d'accès vers le fichier contenant les règles au format txt

*_dérivations* : Table de Hashage<mot,dérivations> 
Par exemple, *dérivations[nager]* = <nageur,nageuse,nageoire, ...>

**Méthodes**

*loadRules(path : string)* : Cette méthode charge le *_ruleSet* à partir du fichier spécifié en paramètre.

*exists(word: string)* : Cette méthode utilise RequeterRezo afin de vérifier que le mot donné en argument existe bien. Elle renvoie true si le mot existe, false sinon.

*run(word : string)* : Calcule les dérivations du mot spécifié. Les dérivations sont stoquées dans *_dérivations*



------------

## 2. Exemple d'utilisation

Le fonctionnement interne de l'engine est totalement opaque pour l'utilisateur : il n'a pas besoin de le connaître.

Lorsque le programme est executé, les mots à dériver sont demandés à l'utilisateur (en bleu dans la capture ci-dessous). Ensuite, l'engine s'occupe de générer les dérivations en s'appuyant sur JDM pour savoir si la dérivation issue d'une règle correspond bien à un mot de la langue française.

![ExempleUtilisation](https://i.imgur.com/t85IKnK.png)

Toutes les dérivations sont ensuite affichées dans la console.

