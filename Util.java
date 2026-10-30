import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Util {
    private static Util instance;

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    private DataHelper _dataHelper;

    public Util() {
        _dataHelper = DataHelper.getInstance();
    }

    public int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public int getRandomInt(int max) {
        return getRandomInt(0, max);
    }

    public <T> void shuffle(List<T> list) {
        Collections.shuffle(list);
    }

    public <T> void shuffle(T[] array) {
        Collections.shuffle(Arrays.asList(array));
    }

    public List<String> sortAccordingToRating(List<String> teams, boolean ascending) {
        teams.sort((x, y) -> (ascending) ? _dataHelper.getTeam(x).getRating() - _dataHelper.getTeam(y).getRating()
                : _dataHelper.getTeam(y).getRating() - _dataHelper.getTeam(x).getRating());
        return teams;
    }

    public String getOrdinal(int x) {
        switch (x) {
            case 1:
                return "First";
            case 2:
                return "Second";
            case 3:
                return "Third";
            case 4:
                return "Fourth";
            case 5:
                return "Fifth";
            case 6:
                return "Sixth";
            case 7:
                return "Seventh";
            case 8:
                return "Eighth";
            case 9:
                return "Nineth";
            case 10:
                return "Tenth";
            default:
                return "Nth";
        }
    }
}
