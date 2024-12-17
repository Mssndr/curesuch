package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ScenarioManager {

    private static final Logger logger = LogManager.getLogger(ScenarioManager.class);

    public static String playScenario(String scenarioFile, Scanner scanner) {
        try (InputStream inputStream = ScenarioManager.class.getClassLoader().getResourceAsStream(scenarioFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            StringBuilder text = new StringBuilder();
            Map<Integer, String> choices = new HashMap<>();
            Map<Integer, String> branches = new HashMap<>();

            // Флаги для управления чтением
            boolean readingText = false;
            boolean readingChoices = false;
            boolean readingBranches = false;

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[START]")) {
                    readingText = true;
                    readingChoices = false;
                    readingBranches = false;
                } else if (line.startsWith("[CHOICE]")) {
                    readingText = false;
                    readingChoices = true;
                    readingBranches = false;
                } else if (line.startsWith("[BRANCH]")) {
                    readingText = false;
                    readingChoices = false;
                    readingBranches = true;
                } else if (readingText) {
                    // Читаем текст сценария
                    text.append(line).append("\n");
                } else if (readingChoices) {
                    // Читаем и сохраняем варианты выбора
                    String[] parts = line.split("\\.");
                    if (parts.length == 2) {
                        int choiceNumber = Integer.parseInt(parts[0].trim());
                        String choiceText = parts[1].trim();
                        choices.put(choiceNumber, choiceText);
                    }
                } else if (readingBranches) {
                    // Читаем ветки сценариев
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        branches.put(Integer.parseInt(parts[0].trim()), parts[1].trim());
                    }
                }
            }

            // Выводим текст сценария
            System.out.println(text.toString());

            // Выводим варианты выбора
            if (!choices.isEmpty()) {
                System.out.println("Варианты выбора:");
                for (Map.Entry<Integer, String> entry : choices.entrySet()) {
                    System.out.println(entry.getKey() + ". " + entry.getValue());
                }
            }

            // Получаем выбор пользователя
            if (!branches.isEmpty()) {
                int choice = -1;
                boolean validChoice = false;

                while (!validChoice) {
                    System.out.print("Ваш выбор: ");
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Очищаем буфер

                        // Проверяем, что выбор существует в доступных ветках
                        if (branches.containsKey(choice)) {
                            validChoice = true;
                        } else {
                            System.out.println("Такого варианта нет. Выберите одно из предложенных чисел.");
                        }
                    } catch (java.util.InputMismatchException e) {
                        // Обрабатываем случай, если пользователь ввел не число
                        scanner.nextLine(); // Очищаем буфер
                        System.out.println("Введено не число. Выберите одно из предложенных чисел");
                    }
                }

                // Возвращаем следующий сценарий
                logger.info("User chose option: {}", choice);
                return branches.get(choice);
            } else {
                // Если нет веток, завершаем игру
                logger.info("No branches available, ending game.");
                return null;
            }
        } catch (IOException e) {
            logger.error("Error reading scenario file: {}", e.getMessage(), e);
            System.out.println("Спасибо за игру");
            return null;
        }
    }
}