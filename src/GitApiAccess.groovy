import java.util.Timer
class GitApiAccess {

    def GitApiAccess() {
        def schedulerProps=new Properties();
        getClass().getResource("/res/scheduler.properties").withInputStream {
            schedulerProps.load(it)
        }
        def timeInterval=Long.parseLong(schedulerProps.GIT_ACCESSOR_SCHEDULE_INTERVAL.toString().trim())
        def timeUnit=schedulerProps.GIT_ACCESSOR_SCHEDULER_UNIT.toString().toLowerCase().trim()
        def millis=0l
        switch(timeUnit){
            case "minute":
            case "minutes":
                millis=60*1000
                break;
            case "hour":
            case "hours":
                millis=60*60*1000
                break;
            case "day":
            case "days":
                millis=24*60*60*1000

        }
        def period=timeInterval*millis
        Timer timer = new Timer();
        def gitTask = new GitAccessor();
        timer.scheduleAtFixedRate(gitTask, 0, period)
    }
}
