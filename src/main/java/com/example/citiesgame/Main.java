package com.example.citiesgame;

import javax.swing.*;

import com.example.citiesgame.gui.GameGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameGUI::new);  // Запуск графічного інтерфейсу гри, виклик
    }
}


/**  Головний клас Main, який містить метод main, є точкою входу в програму.
 * У методі main викликається метод invokeLater() з класу SwingUtilities.
 * Метод invokeLater() виконує передану йому задачу (у цьому випадку створення об'єкту GameGUI) у потоці розподілу подій Swing,
 * що дозволяє коректно запустити графічний інтерфейс гри.
 * Таким чином, програма починає виконання з створення графічного інтерфейсу та запускає гру.
 */




