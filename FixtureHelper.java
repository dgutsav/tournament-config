import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixtureHelper {
    private static FixtureHelper instance;

    public static FixtureHelper getInstance() {
        if (instance == null) {
            instance = new FixtureHelper();
        }
        return instance;
    }

    private Util _util;

    public FixtureHelper() {
        _util = Util.getInstance();
    }

    public Map<Integer, List<Match>> getSingleRoundRobinFixtures(List<String> tList) {
        List<String> teamList = new ArrayList<>(tList);
        String dummy = "DUMMY";
        if (teamList.size() % 2 != 0)
            teamList.add(dummy);
        _util.shuffle(teamList);

        Map<Integer, List<Match>> matchMap = new HashMap<>();

        int num_matchdays = teamList.size() - 1;
        int matches_per_day = teamList.size() / 2;
        int mid = matches_per_day;
        for (int i = 1; i <= num_matchdays; i++) {
            List<Match> matches = new ArrayList<>();
            for (int j = 0; j < matches_per_day; j++) {
                if (!teamList.get(j).equals(dummy) && !teamList.get(mid + j).equals(dummy)) {
                    if (i % 2 == 0) {
                        matches.add(new Match(teamList.get(j), teamList.get(j + mid), ""));
                    } else {
                        matches.add(new Match(teamList.get(j), teamList.get(j + mid), ""));
                    }
                }
            }
            matchMap.put(i, matches);
            String temp = teamList.get(mid);
            String temp2 = teamList.get(mid - 1);
            for (int j = mid + 1; j < teamList.size(); j++) {
                teamList.set(j - 1, teamList.get(j));
            }
            teamList.set(teamList.size() - 1, temp2);
            for (int j = mid - 1; j >= 1; j--) {
                teamList.set(j, teamList.get(j - 1));
            }
            teamList.set(1, temp);
        }

        return matchMap;
    }

    public Map<Integer, List<Match>> getDoubleRoundRobinFixtures(List<String> teamList) {
        Map<Integer, List<Match>> matchMap = getSingleRoundRobinFixtures(teamList);
        List<Integer> matchdays = new ArrayList<>(matchMap.keySet());
        _util.shuffle(matchdays);
        for (int i = 0; i < matchdays.size(); i++) {
            List<Match> old_matches = matchMap.get(matchdays.get(i));
            int matchday = matchdays.size() + i + 1;
            List<Match> new_matches = new ArrayList<>();
            for (Match match : old_matches) {
                new_matches.add(new Match(match.team2, match.team1, ""));
            }
            matchMap.put(matchday, new_matches);
        }
        return matchMap;
    }

    public Map<Integer, List<Match>> getKnockoutFixtures(List<String> teamList, boolean seeded) {
        Map<Integer, List<Match>> matchMap = new HashMap<>();
        if (seeded) {
            teamList = _util.sortAccordingToRating(teamList, false);
        }

        List<Match> matches = new ArrayList<>();
        int mid = teamList.size() / 2;
        List<String> pot1 = teamList.subList(0, mid);
        List<String> pot2 = teamList.subList(mid, teamList.size());

        _util.shuffle(pot1);
        _util.shuffle(pot2);

        for (int i = 0; i < pot1.size(); i++) {
            matches.add(new Match(pot1.get(i), pot2.get(i), "", false));
        }
        matchMap.put(1, matches);
        return matchMap;
    }
}
