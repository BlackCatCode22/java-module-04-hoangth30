package dennisMohle.myZoo.com;

public class Animal {
    private String name;
    private int age;
    private String species;

    public Animal(String name, int age, String species) {
        this.name = name;
        this.age = age;
        this.species = species;
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getSpecies() {
        return species;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age;
    }
}

// Subclass representing a Lion
class Lion extends Animal {
    public Lion(String name, int age) {
        super(name, age, "lion");
    }
}

// Subclass representing a Tiger
class Tiger extends Animal {
    public Tiger(String name, int age) {
        super(name, age, "tiger");
    }
}

// Subclass representing a Bear
class Bear extends Animal {
    public Bear(String name, int age) {
        super(name, age, "bear");
    }
}

// Subclass representing a Hyena
class Hyena extends Animal {
    public Hyena(String name, int age) {
        super(name, age, "hyena");
    }
}
