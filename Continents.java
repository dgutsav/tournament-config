import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Continents {
    EUROPE("Europe", "UEFA"),
    ASIA("Asia", "AFC"),
    AFRICA("Africa", "CAF"),
    NORTH_AMERICA("North America", "CONCACAF"),
    SOUTH_AMERICA("South America", "CONMEBOL"),
    OCEANIA("Oceania", "OFC"),
    WORLD("World", "FIFA");

    private final String name;
    private final String confed;

    Continents(String name, String confed) {
        this.name = name;
        this.confed = confed;
    }

    public String getName() {
        return this.name;
    }

    public String getConfederation() {
        return this.confed;
    }

    private static Map<String, Continents> lookupMap = Arrays.stream(values())
            .collect(Collectors.toMap(c -> c.confed, c -> c));

    public static Continents fromConfed(String confed) {
        return lookupMap.get(confed);
    }
}
