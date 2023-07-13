package com.example.citiesgame.model;

import com.example.citiesgame.gui.GameGUI;
import com.example.citiesgame.util.CityFileReader;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GameLogic {
    private GameGUI gameGUI;  // Графічний інтерфейс гри
    private List<String> citiesList;  // Список міст
    private Set<String> usedCities;   // Міста, які вже використано
    private int userScore = 0;
    private int computerScore = 0;
    private String lastComputerCity = "";  // Останнє місто, яке назвав комп'ютер

    public GameLogic(GameGUI gameGUI) {  // Конструктор класу, в якому посилання на графічний інтерфейс гри
        this.gameGUI = gameGUI;
    }

    public void startGame() {
        CityFileReader fileReader = new CityFileReader(); // Створення об'єкту для читання файлу з містами
        citiesList = fileReader.readCitiesFromFile("src/main/java/com/example/citiesgame/recources/cities.txt");
        usedCities = new HashSet<>();  // Ініціалізація списку використаних міст
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

        String adjustedUserInput = adjustCityName(userInputInLowerCase); // Змінна з відкорегованою назвою міста де прибранні останні букви ь,и,ї, і т.д.

        if (!checkFirstLetter(adjustedUserInput)) {
            JOptionPane.showMessageDialog(null, "Перша літера введеного міста не відповідає останній літері міста, яке назвав комп'ютер. Спробуйте ще раз.");
            return;
        }

        usedCities.add(userInputInLowerCase);  // Додавання введеного міста до списку використаних міст

        String computerMove = findNextCity(adjustedUserInput);  // Знаходження наступного міста, яке назве комп'ютер
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
        lastComputerCity = computerMove.toLowerCase().replaceAll("[ьиЬИйЙ]", "").trim();
    }

    private boolean checkFirstLetter(String city) {
        if (lastComputerCity.isEmpty()) {
            return true; // Якщо комп'ютер ще не назвав жодного міста, перевірка не потрібна
        }

        char firstChar = city.charAt(0); // Перша літера введеного міста
        char lastChar = lastComputerCity.charAt(lastComputerCity.length() - 1); // Остання літера міста, яке назвав комп'ютер
        return Character.toLowerCase(firstChar) == lastChar; // Перевірка, чи відповідає перша літера останній літері
    }

    private String findNextCity(String previousCity) {
        char lastChar = previousCity.charAt(previousCity.length() - 1);  // Остання літера попереднього міста
        String cityWithLastChar = null; // Місто з останньою літерою

        for (String city : citiesList) {
            if (!usedCities.contains(city.toLowerCase()) && city.charAt(0) == lastChar) { // Шукаємо місто ще не використане і починається з останньої літери попереднього міста
                cityWithLastChar = city; // Збереження міста
                break; // Виходимо з циклу
            }
        }

        if (cityWithLastChar != null) {
            return cityWithLastChar;  // Якщо місто у списку знайдемо, то повертаємо його
        }
        return null;
    }

    private boolean isValidCity(String city) {
        return citiesList.contains(city);  // Перевірка, чи місто є в списку міст
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1); // Повертаємо рядок приводячи першу літеру у верхній регістр
    }

    private String adjustCityName(String cityName) {
        return cityName.replaceAll("[ьиЬИйЙ]", "").trim(); // Видалення останніх літер "ьиЬИйЙ" у назві міста
    }
}




