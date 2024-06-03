import java.util.Timer
class GitApiAccess {
    def GitApiAccess() {
        Timer timer = new Timer();
        def gitTask = new GitAccessor();
        timer.scheduleAtFixedRate(gitTask, 0, 1 * 60 * 1000)
    }
}
