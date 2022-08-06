package holybible.religious.christianity.BabyNames;

public class NameType {

    String name, meaning, feast;


    public NameType(String name, String meaning, String feast) {
        this.name = name;
        this.meaning = meaning;
        this.feast = feast;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getFeast() {
        return feast;
    }
}
