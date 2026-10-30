public class Simulator {
    private static Simulator instance;

    public static Simulator getInstance() {
        if (instance == null)
            instance = new Simulator();
        return instance;
    }

    private DataHelper _dataHelper;
    private Util _util;

    public Simulator() {
        _util = Util.getInstance();
        _dataHelper = DataHelper.getInstance();
    }

    public void simulateMatch(Match match) {
        int rating1 = _dataHelper.getTeam(match.team1).getRating();
        int rating2 = _dataHelper.getTeam(match.team2).getRating();

        int diff = Math.abs(rating1 - rating2);
        int score_win = 0, score_loss = 0;
        if (diff > 4) {
            score_win = _util.getRandomInt(3, 7);
            score_loss = _util.getRandomInt(0, 4);
        } else if (diff > 3) {
            score_win = _util.getRandomInt(2, 5);
            score_loss = _util.getRandomInt(0, 3);
        } else if (diff > 2) {
            score_win = _util.getRandomInt(1, 4);
            score_loss = _util.getRandomInt(0, 2);
        } else if (diff > 1) {
            score_win = _util.getRandomInt(0, 2);
            score_loss = _util.getRandomInt(0, 1);
        } else {
            score_win = _util.getRandomInt(0, 1);
            score_loss = _util.getRandomInt(0, 1);
        }
        if (rating1 > rating2) {
            match.simulated(score_win, score_loss);
        } else if (rating1 < rating2) {
            match.simulated(score_loss, score_win);
        } else {
            match.simulated(score_win, score_loss);
        }

        if (score_win == score_loss && !match.draw_allowed) {
            int score1 = _util.getRandomInt(5);
            int score2 = _util.getRandomInt(5);
            if (score1 != score2)
                match.simulatePK(score1, score2);
            else {
                if (_util.getRandomInt(1) == 1) {
                    score1 += _util.getRandomInt(3);
                    score2 = score1 - 1;
                    match.simulatePK(score1, score2);
                } else {
                    score2 += _util.getRandomInt(3);
                    score1 = score2 - 1;
                    match.simulatePK(score1, score2);
                }
            }
        }
    }
}
