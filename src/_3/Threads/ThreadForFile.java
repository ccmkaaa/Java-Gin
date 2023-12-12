package _3.Threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ThreadForFile {
    public static class NamePrinter implements Runnable {
        private String fileName;
        private Semaphore currentSemaphore;
        private Semaphore nextSemaphore;
        private boolean fileComplete; // флаг для определения завершения файла

        public NamePrinter(String fileName, Semaphore currentSemaphore, Semaphore nextSemaphore) {
            this.fileName = fileName;
            this.currentSemaphore = currentSemaphore;
            this.nextSemaphore = nextSemaphore;
            this.fileComplete = false;
        }

        @Override
        public void run() {
            processFile(fileName);
        }

        private void processFile(String fileName) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    if (parts.length == 2) {
                        String firstName = parts[0];
                        String lastName = parts[1];

                        currentSemaphore.acquire(); // Ждем разрешения

                        if (!fileComplete) {
                            System.out.print(firstName + " ");
                            Thread.sleep(100);
                            System.out.println(lastName);

                            nextSemaphore.release(); // Разрешаем следующему потоку начать
                            currentSemaphore.acquire(); // Ждем, пока следующий поток напечатает
                            currentSemaphore.release(); // Освобождаем, чтобы следующий поток мог напечатать
                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                fileComplete = true; // Устанавливаем флаг завершения файла
                nextSemaphore.release(); // Сигнализируем, что файл обработан
            }
        }
    }
}