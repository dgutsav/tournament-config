public class Match {
    String team1;
    String team2;
    String competition;
    int score1;
    int score2;
    boolean draw_allowed;

    boolean after_pk;
    int pk1;
    int pk2;

    enum MatchStatus {
        SCHEDULED,
        PK_PENDING,
        COMPLETED
    }

    enum MatchResult {
        TEAM1_WIN,
        TEAM2_WIN,
        DRAW
    }

    private MatchStatus status;
    private MatchResult result;

    public Match(String team1, String team2, String comp) {
        this.team1 = team1;
        this.team2 = team2;
        this.competition = comp;
        this.draw_allowed = true;
        this.initMatch();
    }

    public Match(String team1, String team2, String comp, boolean draw) {
        this.team1 = team1;
        this.team2 = team2;
        this.competition = comp;
        this.draw_allowed = draw;
        this.initMatch();
    }

    private void initMatch() {
        this.score1 = 0;
        this.score2 = 0;
        this.after_pk = false;
        this.pk1 = 0;
        this.pk2 = 0;
        this.status = MatchStatus.SCHEDULED;
    }

    public void simulated(int score1, int score2) {
        if (this.status == MatchStatus.SCHEDULED) {
            this.score1 = score1;
            this.score2 = score2;
            System.out.println(this.team1 + " " + this.score1 + "-" + this.score2 + " " + this.team2);
            if (!this.draw_allowed && this.score1 == this.score2)
                this.status = MatchStatus.PK_PENDING;
            else {
                this.status = MatchStatus.COMPLETED;
                this.setResult();
            }
        }
    }

    public void simulatePK(int score1, int score2) {
        if (this.status == MatchStatus.PK_PENDING) {
            this.pk1 = score1;
            this.pk2 = score2;
            System.out.println("Penalties: " + this.team1 + " " + this.pk1 + "-" + this.pk2 + " " + this.team2);
            this.status = MatchStatus.COMPLETED;
            this.setResult();
        }
    }

    public boolean hasEnded() {
        return this.status == MatchStatus.COMPLETED;
    }

    private void setResult() {
        if (this.status == MatchStatus.COMPLETED) {
            if (this.score1 > this.score2)
                this.result = MatchResult.TEAM1_WIN;
            else if (this.score1 < this.score2)
                this.result = MatchResult.TEAM2_WIN;
            else if (this.draw_allowed)
                this.result = MatchResult.DRAW;
            else if (this.pk1 > this.pk2)
                this.result = MatchResult.TEAM1_WIN;
            else
                this.result = MatchResult.TEAM2_WIN;
        }
    }

    public int getResult() {
        switch (this.result) {
            case TEAM1_WIN:
                return 1;
            case TEAM2_WIN:
                return 2;
            case DRAW:
                return 3;
        }
        return 3;
    }

    public String getWinner() {
        return this.result == MatchResult.TEAM1_WIN ? this.team1
                : this.result == MatchResult.TEAM2_WIN ? this.team2 : "DRAW";
    }

    public String getLoser() {
        return getWinner() == this.team1 ? this.team2 : this.team1;
    }
}
