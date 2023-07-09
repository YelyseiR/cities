package com.example.citiesgame;

import javax.swing.*;
import com.example.citiesgame.gui.GameGUI;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameGUI::new);
    }
}




