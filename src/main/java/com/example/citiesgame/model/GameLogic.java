package com.example.citiesgame.model;

import com.example.citiesgame.gui.GameGUI;
import com.example.citiesgame.util.CityFileReader;

import java.util.*;
import javax.swing.JOptionPane;


import com.example.citiesgame.gui.GameGUI;
import com.example.citiesgame.util.CityFileReader;

import java.util.*;
import javax.swing.JOptionPane;


public class GameLogic {
    private GameGUI gameGUI;
    private List<String> citiesList;
    private Set<String> usedCities;
    private int userScore = 0;
    private int computerScore = 0;
    private Random random;
    private String lastComputerCity = "";

    public GameLogic(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
    }

    public void startGame() {
        CityFileReader fileReader = new CityFileReader();
        citiesList = fileReader.readCitiesFromFile("src/main/java/com/example/citiesgame/recources/cities.txt");
        usedCities = new HashSet<>();
        random = new Random();
    }

    public void processUserInput(String userInput) {
        String userInputInLowerCase = userInput.toLowerCase();

        if (userInputInLowerCase.equals("здаюсь")) {
            String scoreMessage = "Ви здалися. Комп'ютер переміг!\n";
            scoreMessage += "Ваш рахунок: " + userScore + "\n";
            scoreMessage += "Рахунок комп'ютера: " + computerScore;

            JOptionPane.showMessageDialog(null, scoreMessage);
            computerScore++;
            gameGUI.updateScoreLabel(userScore, computerScore);
            System.exit(0);
        }

        if (usedCities.contains(userInputInLowerCase)) {
            JOptionPane.showMessageDialog(null, "Це місто вже використано. Введіть інше місто.");
            return;
        }

        if (!isValidCity(userInputInLowerCase)) {
            JOptionPane.showMessageDialog(null, "Введене значення не є містом. Спробуйте ще раз.");
            return;
        }

        String adjustedUserInput = adjustCityName(userInputInLowerCase);

        if (!checkFirstLetter(adjustedUserInput)) {
            JOptionPane.showMessageDialog(null, "Перша літера введеного міста не відповідає останній літері міста, яке назвав комп'ютер. Спробуйте ще раз.");
            return;
        }

        usedCities.add(userInputInLowerCase);

        String computerMove = findNextCity(adjustedUserInput);
        if (computerMove == null) {
            JOptionPane.showMessageDialog(null, "Ком'ютер не може знайти місто для вас. Ви перемогли!");
            userScore++;
            gameGUI.updateScoreLabel(userScore, computerScore);
            System.exit(0);
            return;
        }

        usedCities.add(userInputInLowerCase);
        usedCities.add(computerMove.toLowerCase());
        userScore++;
        computerScore++;
        gameGUI.updateScoreLabel(userScore, computerScore);
        gameGUI.appendConversation(userInput, capitalizeFirstLetter(computerMove));
        lastComputerCity = computerMove.toLowerCase().replaceAll("[ьиЬИйЙ]","").trim();
    }

    private boolean checkFirstLetter(String city) {
        if (lastComputerCity.isEmpty()) {
            return true; // Якщо комп'ютер ще не назвав жодного міста, перевірка не потрібна
        }

        char firstChar = city.charAt(0);
        char lastChar = lastComputerCity.charAt(lastComputerCity.length() - 1);
        return Character.toLowerCase(firstChar) == lastChar;
    }

    private String findNextCity(String previousCity) {
        char lastChar = previousCity.charAt(previousCity.length() - 1);
        String cityWithLastChar = null;

        for (String city : citiesList) {
            if (!usedCities.contains(city.toLowerCase()) && city.charAt(0) == lastChar) {
                cityWithLastChar = city;
                break;
            }
        }

        if (cityWithLastChar != null) {
            return cityWithLastChar;
        }

        char lastCharWithoutSoftSign = previousCity.charAt(previousCity.length() - 2);

        for (String city : citiesList) {
            if (!usedCities.contains(city.toLowerCase()) && city.charAt(0) == lastCharWithoutSoftSign) {
                return city;
            }
        }
        return null;
    }

    private boolean isValidCity(String city) {
        return citiesList.contains(city);
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private String adjustCityName(String cityName) {
        return cityName.replaceAll("[ьиЬИйЙ]", "").trim();
    }
}




