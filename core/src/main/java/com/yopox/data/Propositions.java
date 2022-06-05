package com.yopox.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Propositions {

    public static class Data {
        final public String before;
        final public String guess;
        final public String after;

        public Data(String before, String guess, String after) {
            this.before = before;
            this.guess = guess;
            this.after = after;
        }
    }

    public static Vector<Data> getRandomPropositions() {
        final Vector<Data> propositions = new Vector<>();
        final Random r = new Random();
        while (propositions.size() < 5) {
            final int i = (int) Math.floor(r.nextDouble() * list.size());
            if (!propositions.contains(list.get(i))) propositions.add(list.get(i));
        }
        return propositions;
    }

    public final static List<Data> list = new ArrayList<Data>() {{
        add(new Data(
                "Inscrire dans la Constitution\nla règle verte, qui impose de",
                "ne pas prendre plus à la nature",
                "que ce qu'elle peut reconstituer"
        ));
        add(new Data(
                "Rendre le droit à l'eau\net à l'assainissement par",
                "la gratuité des mètres cubes",
                "indispensables à la vie digne"
        ));
        add(new Data(
                "",
                "Créer 300 000 emplois agricoles",
                "pour instaurer une agriculture\nrelocalisée, diversifiée\net écologique"
        ));
        add(new Data(
                "Porter immédiatement le",
                "SMIC mensuel à 1 500 euros net",
                "et accompagner les TPE/PME"
        ));
        add(new Data(
                "Restaurer le droit à la",
                "retraite à 60 ans avec 40 annuités",
                "avec une attention particulière\npour les métiers pénibles"
        ));
        add(new Data(
                "Reconnaître",
                "le burn-out",
                "comme maladie professionnelle"
        ));
        add(new Data(
                "Rouvrir les lignes ferroviaires\ndu quotidien et",
                "augmenter le nombre de trains",
                ""
        ));
        add(new Data(
                "Relever l'ambition climatique avec",
                "une baisse de 65% des émissions",
                "d'ici à 2030 au lieu de 40% et\nrendre public un bilan annuel"
        ));
        add(new Data(
                "Indexer",
                "le montant des retraites",
                "sur les salaires"
        ));
        add(new Data(
                "Lutter contre l'artificialisation\ndes sols et la pêche illégale",
                "pour protéger la biodiversité",
                ""
        ));
        add(new Data(
                "Garantir l'accès à tous\nles services publics essentiels",
                "à moins de 15 à 30 min",
                "de tout lieu d'habitation"
        ));
        add(new Data(
                "Soutenir l'associatif local",
                "en maintenant les subventions",
                "et en sortant de la logique\ndes appels à projets"
        ));
        add(new Data(
                "Engager un plan national\nde soutien massif à l'essor",
                "des transports collectifs",
                "dans les grandes agglomérations"
        ));
        add(new Data(
                "Garantir des tarifs accessibles et\ndes mesures de gratuité ciblée pour",
                "les transports en commun",
                ""
        ));
        add(new Data(
                "",
                "Supprimer les lignes aériennes",
                "quand l'alternative en train\nest inférieure à trois heures"
        ));
        add(new Data(
                "Planifier le passage à",
                "100% d'énergies renouvelables",
                "et la sortie du nucléaire"
        ));
        add(new Data(
                "Interdire",
                "l'obsolescence programmée",
                "et allonger les durées de garantie légale\ndes produits"
        ));
    }};

}
