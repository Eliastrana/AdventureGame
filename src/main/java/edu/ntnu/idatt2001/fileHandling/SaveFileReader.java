package edu.ntnu.idatt2001.fileHandling;

import edu.ntnu.idatt2001.GUI.PaneGenerator;
import edu.ntnu.idatt2001.Passage;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;


public class SaveFileReader {




    public static String getImage(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String image = br.readLine();
        br.close();

        return image;
    }

    public static String getName(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));

        br.readLine(); // Skip image line
        String name = br.readLine();
        br.close();

        return name;
    }

    public static HashMap<String, LinkedList<String>> backOnePassage(String filePath) {
        HashMap<String, LinkedList<String>> map = new HashMap<>();
        map.put("P:", new LinkedList<>());
        map.put("N:", new LinkedList<>());
        map.put("H:", new LinkedList<>());
        map.put("G:", new LinkedList<>());
        map.put("S:", new LinkedList<>());
        map.put("I:", new LinkedList<>());

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String key : map.keySet()) {
                    if (line.startsWith(key)) {
                        if (map.get(key).size() == 2) {
                            map.get(key).removeFirst();
                        }
                        map.get(key).addLast(line.substring(2));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    public static int getHealth(String filePath) {
        int health = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("H:")) {
                    health = Integer.parseInt(line.substring(2));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return health;
    }
    public static int getScore(String filePath) {
        int strength = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("S:")) {
                    strength = Integer.parseInt(line.substring(2));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return strength;
    }
    public static int getGold(String filePath) {
        int gold = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("G:")) {
                    gold = Integer.parseInt(line.substring(2));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gold;
    }


    public static String getPath(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));

        br.readLine(); // Skip image line
        br.readLine(); // Skip name line
        String path = br.readLine();
        br.close();

        return path;
    }

    public static String getLastSeenPassage(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));

        br.readLine(); // Skip image line
        br.readLine(); // Skip name line
        br.readLine(); // Skip path line

        String lastSeenPassage = "";
        String line;
        while ((line = br.readLine()) != null) {
            lastSeenPassage = line;
        }

        br.close();

        return lastSeenPassage;
    }


    public static String findWordBeforePng(String filename, String path) {
        String filePath = path + "/" + filename;
        String line = null;
        String word = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                if (line.contains(filename)) {
                    int index = line.indexOf(filename);
                    if (index > 0) {
                        int startIndex = index - 1;
                        while (startIndex >= 0 && Character.isLetter(line.charAt(startIndex))) {
                            startIndex--;
                        }
                        word = line.substring(startIndex + 1, index);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return word;
    }


    public static String getPassageNameFromCounter(String filePath, int counter) {
        String passageName = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("C:" + counter)) {
                    //System.out.println("Found counter");
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("P:")) {
                            passageName = line.substring(2);
                            break;
                        }
                    }

                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return passageName;
    }

    public static ArrayList<String> getInventoryFromCounter(String filePath, int counter) {
        ArrayList<String> passageInventory = new ArrayList<>();
        boolean isMatchingCounter = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("C:" + counter)) {
                    isMatchingCounter = true;
                } else if (line.startsWith("C:") && isMatchingCounter) {
                    break; // Reached the next counter, stop reading
                }

                if (isMatchingCounter && line.startsWith("I:")) {
                    String[] items = line.substring(2).replaceAll("\\[|\\]", "").split(", ");
                    passageInventory.addAll(Arrays.asList(items));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return passageInventory;
    }






    public static HashMap<String, Object> getPassageParameters(String filePath, int counter) {
        HashMap<String, Object> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isMatchingCounter = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("C:" + counter)) {
                    isMatchingCounter = true;
                } else if (line.startsWith("C:") && isMatchingCounter) {
                    break; // Reached the next counter, stop reading
                }

                if (isMatchingCounter) {
                    if (line.startsWith("H:")) {
                        map.put("health", Integer.parseInt(line.substring(2))); // Parse the string to an int
                    } else if (line.startsWith("G:")) {
                        map.put("gold", Integer.parseInt(line.substring(2))); // Parse the string to an int
                    } else if (line.startsWith("S:")) {
                        map.put("score", Integer.parseInt(line.substring(2))); // Parse the string to an int
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

















}
