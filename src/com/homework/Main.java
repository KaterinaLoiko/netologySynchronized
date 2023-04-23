package com.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

  public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
  public static final String LETTERS = "RLRFR";
  public static final int ROUT_LENGTH = 100;
  public static final int THREAD_COUNT = 1000;
  public static final char LETTER = 'R';

  public static void main(String[] args) {
    for (int i = 0; i < THREAD_COUNT; i++) {
      Thread thread = new Thread(() -> {
        String route = generateRoute(LETTERS, ROUT_LENGTH);
        int count = (int) route.chars().filter(chr -> chr == LETTER).count();
        System.out.println("Количество поворотов направо " + count);
        synchronized (sizeToFreq) {
          if (sizeToFreq.containsKey(count)) {
            sizeToFreq.put(count, sizeToFreq.get(count) + 1);
          } else {
            sizeToFreq.put(count, 1);
          }
        }
      });
      thread.start();
    }
    int max = sizeToFreq.values().stream().max(Integer::compare).get();
    sizeToFreq.keySet().forEach(key -> {
      if (sizeToFreq.get(key) == max) {
        System.out.printf("Самое частое количество повторений %s (встретилось %s раз)%n", key,
            max);
      }
    });
    System.out.println("Другие размеры:");
    sizeToFreq.keySet().forEach(key -> {
      if (sizeToFreq.get(key) != max) {
        System.out.printf("- %s (%s раз)%n", key, sizeToFreq.get(key));
      }
    });
  }

  public static String generateRoute(String letters, int length) {
    Random random = new Random();
    StringBuilder route = new StringBuilder();
    for (int i = 0; i < length; i++) {
      route.append(letters.charAt(random.nextInt(letters.length())));
    }
    return route.toString();
  }
}
