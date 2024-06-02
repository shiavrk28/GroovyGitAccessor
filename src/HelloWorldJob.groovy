import java.util.Timer
import java.util.TimerTask

class HelloWorldJob extends TimerTask{
    @Override
    void run() {
        println "hello"
    }
}
