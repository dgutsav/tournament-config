import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHelper {
    private static final String data_file = "data.csv";

    private static DataHelper instance;

    public static DataHelper getInstance() {
        if (instance == null)
            instance = new DataHelper();
        return instance;
    }

    private Map<String, Team> teams = new HashMap<>();
    private Map<String, Tournament> tournaments = new HashMap<>();

    private FileHelper _fileHelper;

    private DataHelper() {
        _fileHelper = FileHelper.getInstance();
        loadTeams();
    }

    public void loadTeams() {
        List<String[]> nations = _fileHelper.readFile(data_file);
        for (String[] arr : nations) {
            teams.put(arr[1], new Team(new Nation(arr[0], arr[1],
                    Integer.parseInt(arr[3]), Continents.fromConfed(arr[2]))));
        }
        System.out.println(teams.size() + " Teams Loaded");
    }

    public Map<String, Team> getTeams() {
        return this.teams;
    }

    public Team getTeam(String id) {
        return this.teams.getOrDefault(id, null);
    }

    public void addTournament(Tournament tmt) {
        tournaments.put(tmt.getName(), tmt);
    }

    public Tournament getTournament(String name) {
        return tournaments.getOrDefault(name,
                new Tournament(name, TournamentType.REGIONAL_TOURNAMENT, new StageBuilds()));
    }
}
