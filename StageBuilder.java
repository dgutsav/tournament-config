import java.util.List;

public class StageBuilder {
    private String name;
    private StageType type;
    private boolean is_seeded;
    private int groups;
    private int rounds;
    private boolean is_final;
    private int pr1;
    private int pr2;
    private int pr3;
    private TeamCriteria criteria;

    // hidden variables
    private int advancing;

    public StageBuilder(String name, StageType type) {
        this.name = name;
        this.type = type;
        this.is_seeded = false;
        this.is_final = false;
        this.criteria = new TeamCriteria();
        this.advancing = 0;
    }

    public StageBuilder seeded() {
        this.is_seeded = true;
        return this;
    }

    public StageBuilder isFinal() {
        this.is_final = true;
        return this;
    }

    public StageBuilder groups(int groups) {
        this.groups = groups;
        return this;
    }

    public StageBuilder rounds(int rounds) {
        this.rounds = rounds;
        return this;
    }

    public BaseStage build(Tournament tournament) {
        if (this.advancing != 0) {
            setupAdvancingPrevious(tournament, this.advancing);
        }

        List<String> teams = this.criteria.apply(tournament);

        switch (type) {
            case KNOCKOUT:
                return new KnockoutStage(name, is_final, teams, is_seeded);
            case LEAGUE:
                return new LeagueStage(name, teams, rounds, pr1, pr2, pr3);
            case GROUP:
                return new GroupStage(name, teams, groups, rounds, pr1, pr2, pr3, is_seeded);
        }
        return new KnockoutStage(name, is_final, teams, is_seeded);
    }

    public StageBuilder teamsJoining(TeamFilter filter) {
        this.criteria.teamsJoining(filter);
        return this;
    }

    public StageBuilder teamsProgressing(TeamProgressingFilter filter) {
        this.criteria.teamsProgressing(filter);
        return this;
    }

    public StageBuilder teamsProgressing(String stage_name, int progressing_type) {
        return this.teamsProgressing(new TeamProgressingFilter(stage_name, progressing_type));
    }

    public StageBuilder teamsProgressing(String stage_name, int progressing_type1, int progressing_type2) {
        return this.teamsProgressing(stage_name, progressing_type1).teamsProgressing(stage_name, progressing_type2);
    }

    public StageBuilder teamsAdvancing() {
        this.advancing = 1;
        return this;
    }

    public StageBuilder teamsAdvancing(int adv) {
        this.advancing = adv;
        return this;
    }

    private void setupAdvancingPrevious(Tournament tmt, int adv) {
        BaseStage stage = tmt.getStages().getLast();
        this.criteria.teamsProgressing(new TeamProgressingFilter(stage.getName(), adv));
    }

    public StageBuilder primaryProgressing(int p) {
        this.pr1 = p;
        return this;
    }

    public StageBuilder secondaryProgressing(int p) {
        this.pr2 = p;
        return this;
    }

    public StageBuilder tertiaryProgressing(int p) {
        this.pr3 = p;
        return this;
    }

    public StageBuilder teamsQualifying(TeamQualifierFilter filter) {
        this.criteria.teamsQualifying(filter);
        return this;
    }

    public StageBuilder teamsQualifying(TeamQualifierFilter... filters) {
        for (TeamQualifierFilter filter : filters) {
            teamsQualifying(filter);
        }
        return this;
    }

    public StageBuilder progressing(int primary) {
        this.primaryProgressing(primary);
        return this;
    }

    public StageBuilder progressing(int primary, int secondary) {
        this.primaryProgressing(primary);
        this.secondaryProgressing(secondary);
        return this;
    }

    public StageBuilder progressing(int primary, int secondary, int tertiary) {
        this.primaryProgressing(primary);
        this.secondaryProgressing(secondary);
        this.tertiaryProgressing(tertiary);
        return this;
    }
}