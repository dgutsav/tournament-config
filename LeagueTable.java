import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LeagueTable {
    class TableRec {
        String name;
        int[] records;

        public TableRec(String name, int[] records) {
            this.name = name;
            this.records = records;
        }
    }

    private Map<String, int[]> records = new HashMap<>();
    private Map<String, Map<String, int[]>> h2h = new HashMap<>();

    private static final int POINTS_WIN = 3;
    private static final int POINTS_DRAW = 1;
    private static final int POINTS_LOSS = 0;

    private boolean ignore_h2h;

    public LeagueTable(List<String> list) {
        // int[6] - {pts, w,d,l, gf, ga}
        // int[3] - {pts, gf, ga}
        for (String x : list)
            records.put(x, new int[6]);
        for (String x : list) {
            Map<String, int[]> map = new HashMap<>();
            for (String y : list) {
                if (x.equals(y))
                    continue;
                map.put(y, new int[3]);
            }
            h2h.put(x, map);
        }
        this.ignore_h2h = true;
    }

    public LeagueTable(Map<String, int[]> t) {
        this.ignore_h2h = true;
        this.records = t;
    }

    public void update(Match m) {
        String t1 = m.team1, t2 = m.team2;
        int s1 = m.score1, s2 = m.score2;

        int hr[] = records.get(t1);
        int ar[] = records.get(t2);
        int hh[] = h2h.get(t1).get(t2);
        int ah[] = h2h.get(t2).get(t1);

        hr[4] += s1;
        hr[5] += s2;
        ar[4] += s2;
        ar[5] += s1;

        hh[1] += s1;
        hh[2] += s2;
        ah[1] += s2;
        ah[2] += s1;

        if (s1 > s2) {
            hr[0] += POINTS_WIN;
            hr[1]++;
            ar[3]++;
            ar[0] += POINTS_LOSS;
            hh[0] += POINTS_WIN;
            ah[0] += POINTS_LOSS;
        } else if (s2 > s1) {
            ar[0] += POINTS_WIN;
            ar[1]++;
            hr[3]++;
            hr[0] += POINTS_LOSS;
            ah[0] += POINTS_WIN;
            hh[0] += POINTS_LOSS;
        } else {
            hr[0] += POINTS_DRAW;
            ar[0] += POINTS_DRAW;
            hr[2]++;
            ar[2]++;
            hh[0] += POINTS_DRAW;
            ah[0] += POINTS_DRAW;
        }
    }

    public TableRec[] getTable() {
        List<String> teams = new ArrayList<>(records.keySet());
        teams.sort((a, b) -> {
            int[] ar = records.get(a), br = records.get(b);
            if (ar[0] != br[0])
                return br[0] - ar[0]; // pts
            // tiebreak: h2h pts -> h2h gd -> overall gd
            if (!ignore_h2h) {
                int[] ah = h2h.getOrDefault(a, Map.of()).getOrDefault(b, new int[3]);
                int[] bh = h2h.getOrDefault(b, Map.of()).getOrDefault(a, new int[3]);
                if (ah[0] != bh[0])
                    return bh[0] - ah[0];
                int hgd = (ah[1] - ah[2]) - (bh[1] - bh[2]);
                if (hgd != 0)
                    return -hgd;
            }
            return (br[3] - br[4]) - (ar[3] - ar[4]); // overall gd
        });
        TableRec[] recs = new TableRec[records.size()];
        for (int i = 0; i < teams.size(); i++) {
            recs[i] = new TableRec(teams.get(i), records.get(teams.get(i)));
        }
        return recs;
    }

    public void show() {
        System.out.printf("%-20s %3s %3s %3s %3s %5s%n", "Team", "Pts", "W", "D", "L", "GD");
        TableRec[] map = this.getTable();
        for (TableRec rec : map) {
            String t = rec.name;
            int[] r = rec.records;
            System.out.printf("%-20s %3d %3d %3d %3d %5d%n", t, r[0], r[1], r[2], r[3], r[4] - r[5]);
        }
    }

    public List<String> getSortedTeams() {
        List<String> sorted = new ArrayList<>();
        TableRec[] r = this.getTable();
        for (TableRec x : r)
            sorted.add(x.name);
        return sorted;
    }

    public Map<String, int[]> getTableMap() {
        return this.records;
    }
}