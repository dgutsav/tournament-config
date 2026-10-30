public class TournamentConfig {

    private String name;
    private StageBuilds stage_builds = new StageBuilds();
    private TournamentType type;

    private DataHelper _dataHelper;

    public TournamentConfig(String name, TournamentType type, StageBuilds builds) {
        this.name = name;
        this.type = type;
        this.stage_builds = builds;
        _dataHelper = DataHelper.getInstance();
    }

    public Tournament configureTournament() {
        Tournament tmt = new Tournament(this.name, this.type, this.stage_builds);
        _dataHelper.addTournament(tmt);
        return tmt;
    }
}