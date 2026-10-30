import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupStage extends BaseStage {
    private Util _util;

    private int num_groups;
    private int num_rounds;

    private int progressing1;
    private int progressing2;
    private int progressing3;

    private boolean seeded;

    private LeagueStage[] groups;

    public GroupStage(String name, List<String> teams, int groups, int rounds, int pr1, int pr2, int pr3,
            boolean seeded) {
        super(name, StageType.GROUP, false, teams);
        this.num_groups = groups;
        this.num_rounds = rounds;
        this.seeded = seeded;
        this.progressing1 = pr1;
        this.progressing2 = pr2;
        this.progressing3 = pr3;
        _util = Util.getInstance();
        this.groups = new LeagueStage[groups];
    }

    private String[][] getGroups() {
        String dummy = "DUMMY";
        List<String> teams = this.getTeams();
        if (seeded) {
            teams = _util.sortAccordingToRating(teams, false);
        }
        int num_pots = (int) (Math.ceil(teams.size() / num_groups));
        if (num_pots * num_groups < teams.size())
            num_pots++;
        String[][] pots = new String[num_pots][num_groups];
        for (int i = 0; i < teams.size(); i++) {
            int pot = i / num_groups;
            int index = i % num_groups;
            pots[pot][index] = teams.get(i);
        }
        // pad dummies
        for (int i = teams.size(); i < num_pots * num_groups; i++) {
            pots[i / num_groups][i % num_groups] = dummy;
        }
        for (int i = 0; i < num_pots; i++) {
            _util.shuffle(pots[i]);
        }
        String group_arr[][] = new String[num_groups][num_pots];
        for (int i = 0; i < num_groups; i++) {
            for (int j = 0; j < num_pots; j++) {
                group_arr[i][j] = pots[j][i];
            }
        }
        return group_arr;
    }

    private String getGroupName(int count) {
        char ch = (char) (count + 65);
        return this.getName() + " Group " + ch;
    }

    @Override
    public void scheduleFixtures() {
        int prim_prog = this.progressing1 / this.num_groups;
        int sec_prog = (int) (Math.ceil(this.progressing2 / this.num_groups));
        int ter_prog = (int) (Math.ceil(this.progressing3 / this.num_groups));
        if (this.progressing2 > 0 && sec_prog == 0)
            sec_prog = 1;
        if (this.progressing3 > 0 && ter_prog == 0)
            ter_prog = 1;
        String group_arr[][] = this.getGroups();
        for (int i = 0; i < this.num_groups; i++) {
            List<String> teams = Arrays.asList(group_arr[i]);
            if (teams.contains("DUMMY")) {
                List<String> fixedTeams = new ArrayList<>();
                for (int j = 0; j < teams.size(); j++) {
                    if (!teams.get(j).equals("DUMMY")) {
                        fixedTeams.add(teams.get(j));
                    }
                }
                teams = fixedTeams;
            }
            LeagueStage ls = new LeagueStage(this.getGroupName(i), teams, this.num_rounds, prim_prog, sec_prog,
                    ter_prog);
            ls.scheduleFixtures();
            System.out.println(this.getGroupName(i) + " " + teams.toString());
            this.groups[i] = ls;
        }
    }

    @Override
    public void simulate() {
        System.out.println(this.getName());
        for (int i = 0; i < this.num_groups; i++) {
            this.groups[i].simulate();
        }
    }

    public List<String> getPrimaryProgressingTeams() {
        List<String> progressing = new ArrayList<>();
        for (int i = 0; i < this.num_groups; i++) {
            progressing.addAll(this.groups[i].getPrimaryProgressingTeams());
        }
        return progressing;
    }

    public List<String> getSecondaryProgressingTeams() {
        if (this.progressing2 % this.num_groups == 0) {
            List<String> progressing = new ArrayList<>();
            for (int i = 0; i < this.num_groups; i++) {
                progressing.addAll(this.groups[i].getSecondaryProgressingTeams());
            }
            return progressing;
        } else {
            Map<String, int[]> rec = new HashMap<>();
            for (int i = 0; i < this.num_groups; i++) {
                List<String> rx = this.groups[i].getSecondaryProgressingTeams();
                for (String r : rx) {
                    rec.put(r, this.groups[i].getTable().get(r));
                }
            }
            LeagueTable lt = new LeagueTable(rec);
            System.out.println(
                    "Ranking of " + _util.getOrdinal((this.progressing1 / this.num_groups) + 1) + " Place teams");
            lt.show();
            return lt.getSortedTeams().subList(0, this.progressing2);
        }
    }

    public List<String> getTertiaryProgressingTeams() {
        if (this.progressing3 % this.num_groups == 0) {
            List<String> progressing = new ArrayList<>();
            for (int i = 0; i < this.num_groups; i++) {
                progressing.addAll(this.groups[i].getTertiaryProgressingTeams());
            }
            return progressing;
        } else {
            Map<String, int[]> rec = new HashMap<>();
            for (int i = 0; i < this.num_groups; i++) {
                List<String> rx = this.groups[i].getTertiaryProgressingTeams();
                for (String r : rx) {
                    rec.put(r, this.groups[i].getTable().get(r));
                }
            }
            LeagueTable lt = new LeagueTable(rec);
            return lt.getSortedTeams().subList(0, this.progressing3);
        }
    }
}