import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TeamFilter {
    private List<Predicate<Team>> predicates = new ArrayList<>();
    private Comparator<Team> comparator;
    private int limit;
    private int upper, lower;
    private boolean between = false;

    public TeamFilter byContinent(Continents continent) {
        if (continent == Continents.WORLD)
            this.predicates.add(team -> true);
        else
            this.predicates.add(team -> team.getNation().continent == continent);
        return this;
    }

    public TeamFilter sortByRatingDesc() {
        this.comparator = (a, b) -> b.getNation().points - a.getNation().points;
        return this;
    }

    public TeamFilter sortByRatingAsc() {
        this.comparator = (a, b) -> a.getNation().points - b.getNation().points;
        return this;
    }

    public TeamFilter limit(int results) {
        this.limit = results;
        return this;
    }

    public List<Team> apply(List<Team> teams) {
        var stream = teams.stream().filter(team -> predicates.stream().allMatch(p -> p.test(team)));
        stream = comparator != null ? stream.sorted(comparator) : stream;
        stream = limit > 0 ? stream.limit(limit) : stream;
        return stream.collect(Collectors.toList());
    }

    public List<String> applyFilter(List<Team> teams) {
        var stream = teams.stream().filter(team -> predicates.stream().allMatch(p -> p.test(team)));
        stream = comparator != null ? stream.sorted(comparator) : stream;
        if (between) {
            stream = stream.skip(lower).limit(upper - lower + 1);
        } else {
            stream = limit > 0 ? stream.limit(limit) : stream;
        }
        return stream.map(t -> t.getCode()).toList();
    }

    public TeamFilter best(int size) {
        this.limit = size;
        this.sortByRatingDesc();
        return this;
    }

    public TeamFilter worst(int size) {
        this.limit = size;
        this.sortByRatingAsc();
        return this;
    }

    public TeamFilter between(int upper, int lower) {
        this.upper = upper;
        this.lower = lower;
        this.sortByRatingAsc();
        this.between = true;
        return this;
    }
}