public class Team {

    private Nation nation;
    private int rating;

    public Team(Nation nation) {
        this.nation = nation;
        this.rating = nation.points / 20;
    }

    public String getName() {
        return this.nation.name;
    }

    public int getRating() {
        return this.rating;
    }

    public Nation getNation() {
        return this.nation;
    }

    public String getCode() {
        return this.nation.code;
    }
}
