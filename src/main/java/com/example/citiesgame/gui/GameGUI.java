package com.example.citiesgame.gui;

import com.example.citiesgame.model.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI {
    private JFrame frame;
    private JTextField userInputField;
    private JTextArea conversationTextArea;
    private JButton makeMoveButton;
    private JLabel scoreLabel;
    private GameLogic gameLogic;

    public GameGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Гра \"Міста\"");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel welcomePanel = createWelcomePanel();
        frame.add(welcomePanel, BorderLayout.CENTER);

        frame.setSize(400, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel welcomeLabel = new JLabel("Ласкаво просимо до гри Міста\n (росії не існує)");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        welcomePanel.add(welcomeLabel, gbc);

        JButton startButton = new JButton("ОК");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 15, 5, 5);
        startButton.setPreferredSize(new Dimension(100, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                createGamePanel();
                frame.revalidate();
                gameLogic.startGame();
            }
        });

        welcomePanel.add(startButton, gbc);

        return welcomePanel;
    }

    private void createGamePanel() {
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new BorderLayout());
        userInputField = new JTextField();
        inputPanel.add(userInputField, BorderLayout.CENTER);

        makeMoveButton = new JButton("Зробити хід");
        makeMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = userInputField.getText().trim();
                gameLogic.processUserInput(userInput);
                userInputField.setText("");
            }
        });
        inputPanel.add(makeMoveButton, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.NORTH);

        conversationTextArea = new JTextArea();
        conversationTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(conversationTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        scoreLabel = new JLabel("Ваш рахунок: 0 | Рахунок комп'ютера: 0");
        frame.add(scoreLabel, BorderLayout.SOUTH);

        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        gameLogic = new GameLogic(this);
    }

    public void appendConversation(String userResponse, String computerResponse) {
        conversationTextArea.append("Користувач: " + userResponse + "\n");
        conversationTextArea.append("Комп'ютер: " + computerResponse + "\n");
        conversationTextArea.setCaretPosition(conversationTextArea.getDocument().getLength());
    }

    public void updateScoreLabel(int userScore, int computerScore) {
        scoreLabel.setText("Ваш рахунок: " + userScore + " | Рахунок комп'ютера: " + computerScore);
    }
}



