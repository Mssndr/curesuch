package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class TextQuest {

    private static final Logger logger = LogManager.getLogger(TextQuest.class);

    public static void main(String[] args) {

        logger.info("Starting the TextQuest game.");

        Scanner scanner = new Scanner(System.in);
        String currentScenario = "start.txt"; // Начальный сценарий

        try {
            while (currentScenario != null) {
                currentScenario = ScenarioManager.playScenario(currentScenario, scanner);
            }
        } catch (Exception e) {
            logger.error("An error occurred during the game: {}", e.getMessage());
            System.out.println("Спасибо за игру.");
        } finally {
            scanner.close();
        }

        logger.info("TextQuest game ended.");
    }
}