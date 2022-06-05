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
    }};

}
