package org.imie.vocabularyapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class HelloController {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label scoreLabel;

    private final List<WordPair> wordPairs = new ArrayList<>();
    private final Map<WordPair, ComboBox<String>> comboBoxes = new HashMap<>();

    private int correctAnswers = 0;

    @FXML
    public void initialize() {
        try {
            loadVocabulary("/vocab.csv");
            displayWords();
        } catch (Exception e) {
            e.printStackTrace();
            scoreLabel.setText("Erreur de chargement : " + e.getMessage());
        }
    }

    private void loadVocabulary(String filePath) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filePath)));
        String header = reader.readLine(); // entête
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            if (parts.length == 2) {
                wordPairs.add(new WordPair(parts[0].trim(), parts[1].trim()));
            }
        }
        Collections.shuffle(wordPairs);
    }

    private void displayWords() {
        Set<String> allLang2Words = new HashSet<>();
        for (WordPair pair : wordPairs) {
            allLang2Words.add(pair.lang2());
        }

        gridPane.add(new Label("Anglais"), 0, 0);
        gridPane.add(new Label("Français"), 1, 0);

        int row = 1;
        for (WordPair pair : wordPairs) {
            Label wordLabel = new Label(pair.lang1());
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll(allLang2Words);
            comboBox.setPromptText("Choisir...");
            comboBoxes.put(pair, comboBox);

            gridPane.add(wordLabel, 0, row);
            gridPane.add(comboBox, 1, row);
            row++;
        }
    }

    @FXML
    protected void onValidateButtonClick() {
        correctAnswers = 0;
        for (WordPair pair : wordPairs) {
            String selected = comboBoxes.get(pair).getValue();
            if (pair.lang2().equals(selected)) {
                correctAnswers++;
            }
        }
        scoreLabel.setText("Score : " + correctAnswers + " / " + wordPairs.size());
    }

    private record WordPair(String lang1, String lang2) {}
}
