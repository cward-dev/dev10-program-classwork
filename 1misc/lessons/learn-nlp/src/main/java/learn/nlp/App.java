package learn.nlp;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.Token;

public class App {

    public static void main(String[] args) {

        String text = "The first human in space was Yuri Gagarin, who flew the Vostok 1 spacecraft, " +
                "launched by the Soviet Union on 12 April 1961 as part of the Vostok program.";

        Document doc = new Document(text);

        for (Sentence sentence : doc.sentences()) {
            for (Token token : sentence.tokens()) {
                System.out.printf("%-10s: %-3s: %s%n",
                        token.word(),
                        token.posTag(),
                        token.nerTag()
                );
            }
        }
    }
}