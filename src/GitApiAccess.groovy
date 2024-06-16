import java.util.Timer
class GitApiAccess {

    def GitApiAccess() {
        def timeUnits = [] as ArrayList;
        timeUnits.add("minute")
        timeUnits.add("minutes")
        timeUnits.add("hour")
        timeUnits.add("hours")
        timeUnits.add("day")
        timeUnits.add("days")
        def correctInput=false
        def schedulerProps=new Properties();
        def errMsg="";
        getClass().getResource("/res/scheduler.properties").withInputStream {
            schedulerProps.load(it)
        }
        def timeInterval=0l
        try {
            timeInterval = Long.parseLong(schedulerProps.GIT_ACCESSOR_SCHEDULE_INTERVAL.toString().trim())

            def timeUnit = schedulerProps.GIT_ACCESSOR_SCHEDULER_UNIT.toString().toLowerCase().trim()
            if (timeUnits.contains(timeUnit) && timeInterval!=0l) {
                correctInput = true;
                def millis = 0l
                switch (timeUnit) {
                    case "minute":
                    case "minutes":
                        millis = 60 * 1000
                        break;
                    case "hour":
                    case "hours":
                        millis = 60 * 60 * 1000
                        break;
                    case "day":
                    case "days":
                        millis = 24 * 60 * 60 * 1000

                }
                def period = timeInterval * millis
                Timer timer = new Timer();
                def gitTask = new GitAccessor();
                timer.scheduleAtFixedRate(gitTask, 0, period)
            }
            else{
                errMsg="value for key TIME_UNIT is incorrect, The only accepted values are Minute,Minutes, Hour,Hours ,Day and Days "
                throw new IncorrectInputException(errMsg)
            }
        }

        catch(Exception ex){
            if(timeInterval==0l){
                errMsg="value for key GIT_ACCESSOR_SCHEDULE_INTERVAL is incorrect, Only numeric values are accepted "
                throw new IncorrectInputException(errMsg,ex)
            }

            println("Exception occoured "+ex.printStackTrace());
        }
    }
}
