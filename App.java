package dennisMohle.myZoo.com;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    private static Map<String, Queue<String>> loadAnimalNames(String fileName) throws IOException {
        Map<String, Queue<String>> namesMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        String currentSpecies = null;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            // Check for header lines like "Hyena Names:" etc.
            if (line.toLowerCase().endsWith("names:")) {
                // Extract species name from header (e.g., "Hyena Names:" becomes "hyena")
                currentSpecies = line.split(" ")[0].toLowerCase();
            } else if (!line.isEmpty() && currentSpecies != null) {
                // Split the comma-separated list of names
                String[] names = line.split(",");
                Queue<String> queue = new LinkedList<>();
                for (String name : names) {
                    queue.add(name.trim());
                }
                namesMap.put(currentSpecies, queue);
                // Reset currentSpecies for next section
                currentSpecies = null;
            }
        }
        reader.close();
        return namesMap;
    }

    private static List<Animal> loadArrivingAnimals(String fileName, Map<String, Queue<String>> namesMap, Map<String, Integer> speciesCount) throws IOException {
        List<Animal> animals = new ArrayList<>();  // Using ArrayList to store Animal objects
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        Pattern pattern = Pattern.compile("(\\d+)\\s+year\\s+old\\s+(\\w+)\\s+(\\w+)", Pattern.CASE_INSENSITIVE);

        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                int age = Integer.parseInt(matcher.group(1));
                // String gender = matcher.group(2); // Gender is not used in this example
                String species = matcher.group(3).toLowerCase();

                // Retrieve an available name from the names map for the species
                String name = "Unnamed";
                if (namesMap.containsKey(species)) {
                    Queue<String> queue = namesMap.get(species);
                    if (!queue.isEmpty()) {
                        name = queue.poll();
                    }
                }

                Animal animal;
                // Instantiate the appropriate subclass based on species
                switch (species) {
                    case "lion":
                        animal = new Lion(name, age);
                        break;
                    case "tiger":
                        animal = new Tiger(name, age);
                        break;
                    case "bear":
                        animal = new Bear(name, age);
                        break;
                    case "hyena":
                        animal = new Hyena(name, age);
                        break;
                    default:
                        animal = new Animal(name, age, species);
                        break;
                }

                animals.add(animal);
                speciesCount.put(species, speciesCount.getOrDefault(species, 0) + 1);
            }
        }
        reader.close();
        return animals;
    }

    public static void main(String[] args) {
        Map<String, Queue<String>> namesMap = new HashMap<>();
        Map<String, Integer> speciesCount = new HashMap<>();
        List<Animal> animals = new ArrayList<>();

        try {
            // Load names from animalNames.txt
            namesMap = loadAnimalNames("animalNames.txt");
            // Load arriving animals from arrivingAnimals.txt and assign names accordingly
            animals = loadArrivingAnimals("arrivingAnimals.txt", namesMap, speciesCount);

            // Write the report to newAnimals.txt
            PrintWriter writer = new PrintWriter(new FileWriter("newAnimals.txt"));
            writer.println("Zoo Animals Report");
            writer.println("==================\n");

            // Group animals by species and print the report
            for (String species : speciesCount.keySet()) {
                // Capitalize the species name for display
                writer.println("Species: " + species.substring(0, 1).toUpperCase() + species.substring(1));
                writer.println("Total Count: " + speciesCount.get(species));
                writer.println("Animals:");
                for (Animal animal : animals) {
                    if (animal.getSpecies().equalsIgnoreCase(species)) {
                        writer.println(" - " + animal.toString());
                    }
                }
                writer.println();
            }
            writer.close();
            System.out.println("Report generated successfully in newAnimals.txt.");
        } catch (IOException e) {
            System.err.println("An error occurred during file I/O operations.");
            e.printStackTrace();
        }
    }
}
