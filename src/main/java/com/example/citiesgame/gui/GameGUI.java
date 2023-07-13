package com.example.citiesgame.gui;

import com.example.citiesgame.model.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI {
    private JFrame frame;  //приватний об'єкт вікна для графічного інтерфейсу гри
    private JTextField userInputField;  //поле для введення даних користувачем
    private JTextArea conversationTextArea; //поле для тексту
    private JButton makeMoveButton;  //кнопка "Зробити хід"
    private JLabel scoreLabel; //мітка для відображення рахунку гри
    private GameLogic gameLogic; //об'єкт классу GameLogic

    public GameGUI() {  // Конструктор, який ініціалізує графічний інтерфейс гри
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Гра \"Міста\""); // Створення вітального вікна з назвою "Гра "Міста"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //дія закривання вікна
        frame.setLayout(new BorderLayout()); // розташування вікна

        JPanel welcomePanel = createWelcomePanel(); //вікно привітання
        frame.add(welcomePanel, BorderLayout.CENTER); //розташування вікна привітання

        frame.setSize(400, 100);  //розмір вікна
        frame.setLocationRelativeTo(null);  //розташування вікна
        frame.setVisible(true); //видимість вікна
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout()); // Створення панелі привітання з менеджером розташування GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Створення об'єкта для налаштування розташування компонентів

        JLabel welcomeLabel = new JLabel("Ласкаво просимо до гри Міста\n (росії не існує)"); //вікно з текстом привітання
        gbc.gridx = 0;  // Встановлення позиції мітки по горизонталі
        gbc.gridy = 0;  // Встановлення позиції мітки по вертикалі
        gbc.insets = new Insets(5, 5, 5, 5);  // Встановлення відступів від країв панелі
        welcomePanel.add(welcomeLabel, gbc);  // Додавання мітки на панель привітання

        JButton startButton = new JButton("ОК");  // Створення кнопки "ОК"
        gbc.gridx = 1;  // Встановлення позиції кнопки по горизонталі
        gbc.gridy = 0;  // Встановлення позиції кнопки по вертикалі
        gbc.insets = new Insets(5, 15, 5, 5);  // Встановлення відступів від країв панелі
        startButton.setPreferredSize(new Dimension(100, 50));  // Встановлення розміру кнопки
        startButton.addActionListener(new ActionListener() {  // Додавання респонсу до кнопки
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();  // Видалення всіх компонентів з вмісту вікна
                createGamePanel(); // Створення панелі гри
                frame.revalidate();  //Відмальовування нового вікна
                gameLogic.startGame();  // Виклик старту гри через класс GameLogic
            }
        });

        welcomePanel.add(startButton, gbc);  // Додавання кнопки на панель привітання

        return welcomePanel;  // Повернення панелі привітання
    }

    private void createGamePanel() {
        frame.setLayout(new BorderLayout()); // Встановлення розташування для вікна

        JPanel inputPanel = new JPanel(new BorderLayout()); // Створення панелі вводу з менеджером розташування BorderLayout
        userInputField = new JTextField(); // Текстове поле
        inputPanel.add(userInputField, BorderLayout.CENTER);  // Додавання текстового поля на панель вводу у центр

        makeMoveButton = new JButton("Зробити хід");  // Створення кнопки "Зробити хід"
        userInputField.addActionListener(new ActionListener() {  // Додавання респонсу до кнопки
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = userInputField.getText().trim();   // Отримання введеного користувачем тексту
                gameLogic.processUserInput(userInput);  // Обробка введеного користувачем тексту в логіці гри
                userInputField.setText("");    // Очищення текстового поля після вводу для респонсу комп'ютера
            }
        });
        inputPanel.add(makeMoveButton, BorderLayout.EAST); // Додавання кнопки на панель вводу

        frame.add(inputPanel, BorderLayout.NORTH);  // Додавання панелі вводу в верхню частину вікна

        conversationTextArea = new JTextArea();  // Створення текстової області для введеня
        conversationTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(conversationTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        scoreLabel = new JLabel("Ваш рахунок: 0 | Рахунок комп'ютера: 0"); // Відображення рахунку
        frame.add(scoreLabel, BorderLayout.SOUTH); // Розташування вікна з рахунком

        frame.setSize(400, 500); // Розмір вікна
        frame.setLocationRelativeTo(null); // Розташування вікна по центру

        gameLogic = new GameLogic(this); // Ініціалізація логіки гри
    }

    public void appendConversation(String userResponse, String computerResponse) {
        conversationTextArea.append("Користувач: " + userResponse + "\n");
        conversationTextArea.append("Комп'ютер: " + computerResponse + "\n");
        conversationTextArea.setCaretPosition(conversationTextArea.getDocument().getLength());
    }

    public void updateScoreLabel(int userScore, int computerScore) {
        scoreLabel.setText("Ваш рахунок: " + userScore + " | Рахунок комп'ютера: " + computerScore); // Панель оновлення рахунку після кожної відповіді
    }
}



