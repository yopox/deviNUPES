package com.yopox.data;

import java.util.*;

public class Propositions {

    private static Random random = new Random();

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

    public static void updateSeed(int seed) {
        random = new Random(seed);
    }

    public static Vector<Data> getDailyPropositions() {
        final long time = System.currentTimeMillis();
        return getPropositions((int) Math.floor(time / (1000f * 60 * 60 * 24)));
    }

    public static Vector<Data> getPropositions(int seed) {
        final Vector<Data> propositions = new Vector<>();
        updateSeed(seed);

        while (propositions.size() < 5) {
            final int i = (int) Math.floor(random.nextDouble() * list.size());
            if (!propositions.contains(list.get(i))) propositions.add(list.get(i));
        }
        return propositions;
    }

    public static Data getNewProposition(HashSet<Data> old) {
        if (old.size() == list.size()) return list.get(0);
        Data data = null;
        while (data == null || old.contains(data)) {
            final int i = (int) Math.floor(random.nextDouble() * list.size());
            data = list.get(i);
        }

        old.add(data);
        return data;
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
                "Garantir l'accès à tous\ndes services publics essentiels",
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
                "et allonger les durées de garantie\nlégale des produits"
        ));
        add(new Data(
                "Arrêter les subventions",
                "aux énergies fossiles",
                "y compris à l'étranger"
        ));
        add(new Data(
                "Interdire la publicité",
                "des produits les plus émetteurs",
                "de gaz à effet de serre sur\ntous les supports publicitaires"
        ));
        add(new Data(
                "Lutter contre",
                "l'artificialisation des sols",
                "pour empêcher la disparition\nde surfaces agricoles utiles"
        ));
        add(new Data(
                "",
                "Développer les circuits courts",
                "pour réduire la circulation des\nmarchandises et l'utilisation\nd'emballages"
        ));
        add(new Data(
                "Intégrer",
                "l'éducation à la nutrition",
                "dans les programmes scolaires"
        ));
        add(new Data(
                "",
                "Rendre l'impôt sur le revenu",
                "et la c.s.g.\nvéritablement progressifs avec\nun barème à 14 tranches"
        ));
        add(new Data(
                "Constitutionnaliser le droit au",
                "chiffrement des données",
                "et des communications"
        ));
        add(new Data(
                "Reconnaître",
                "un droit de pétition numérique",
                "pour envoyer une loi à l'ordre\ndu jour des assemblées"
        ));
        add(new Data(
                "Instaurer une taxe significative",
                "sur les transactions financières",
                ""
        ));
        add(new Data(
                "Garantir",
                "la variété des langues vivantes",
                "enseignées et leur apprentissage\ndès le CP"
        ));
        add(new Data(
                "Interdire tout usage de la",
                "reconnaissance faciale",
                "dans les espaces et\nétablissements publics"
        ));

        add(new Data(
                "S'attaquer aux causes des addictions,\nplutôt que de continuer\nune politique de",
                "répression des consommateurs",
                ""
        ));
        add(new Data(
                "",
                "Former un pôle public bancaire",
                "qui réorientera le crédit vers\nla bifurcation écologique et\nsociale de la France"
        ));
        add(new Data(
                "Garantir l'accès à l'eau\ncourante potable à tous",
                "les habitants des Outre-mer",
                ""
        ));
        add(new Data(
                "Faire de la lutte contre",
                "la fraude et l'évasion fiscales",
                "une priorité"
        ));
        add(new Data(
                "",
                "Réaliser un audit citoyen",
                "de la dette publique pour\ndéterminer la part illégitime"
        ));
        add(new Data(
                "",
                "Redonner du pouvoir et des moyens",
                "aux associations citoyennes et aux\nacteurs de l'éducation populaire"
        ));
        add(new Data(
                "Intégrer ",
                "l'enjeu écologique",
                "de la maternelle au lycée"
        ));
        add(new Data(
                "Aller vers",
                "la gratuité des cantines scolaires",
                "en lien avec les\ncollectivités locales"
        ));
        add(new Data(
                "Combattre",
                "l'influence des lobbys",
                "dans le débat parlementaire"
        ));
        add(new Data(
                "Élire l'Assemblée nationale",
                "au scrutin proportionnel",
                ""
        ));
        add(new Data(
                "Supprimer",
                "la sélection à l'université",
                "aggravée par Parcoursup"
        ));
        add(new Data(
                "Instaurer le",
                "Référendum d'initiative citoyenne",
                "(RIC)"
        ));
        add(new Data(
                "Rembourser",
                "le traitement hormonal",
                "de la ménopause"
        ));
        add(new Data(
                "Respecter la dignité et les droits\ndes personnes privées de liberté,\nen finir avec",
                "la surpopulation carcérale",
                ""
        ));
        add(new Data(
                "",
                "Instaurer une tarification",
                "progressive et différenciée pour\nlutter contre les mésusages et\ngaspillages de l'eau"
        ));
        add(new Data(
                "Passer à la 6e République qui soit",
                "un régime parlementaire stable",
                "avec une nouvelle constitution adoptée\npar référendum"
        ));
        add(new Data(
                "",
                "Former l'ensemble des citoyens",
                "en matière sanitaire, nucléaire,\nd'inondation ou de\nfeux de forêt"
        ));
        add(new Data(
                "Créer",
                "un centre national du jeu vidéo",
                "et développer une filière publique\nde formation dans ce domaine"
        ));
    }};
}
