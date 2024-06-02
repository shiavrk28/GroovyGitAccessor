import groovy.json.JsonBuilder
import groovy.swing.SwingBuilder
import java.util.Timer
import javax.swing.SwingContainer
import javax.swing.WindowConstants as WC
import java.util.TimerTask
class GitAccessor extends TimerTask{


    def gitRequests() {
        def lines =[]
        getClass().getResource("/Input.txt").eachLine { lines << it.split(": ")[1] }
        println "${lines}"
        println lines[2];
        //def repoName=readln 'Input the git repo name?';
        def repo = lines[0]
        def owner = lines[1]
        def repo1 = "37signals"

        RestClientLocal rcl = new RestClientLocal();
        def endpoint1 = "/repos/" + repo1 + "/sub/issues"
        def endpoint2 = "/repos/" + owner + "/" + repo + "/pulls"
        def endpoint3 = "/repos/" + owner + "/" + repo + "/pulls/" + lines[6] + "/merge"
        def pull_req_body = new JsonBuilder([
                title: lines[2],
                head : lines[4],
                base : lines[3]
        ]).toPrettyString()

        def merge_req_body = new JsonBuilder([
                commit_title  : lines[2],
                commit_message: lines[4]
        ]).toPrettyString()
        println pull_req_body;

        //rcl.postRequest(endpoint1,"GET",null)
 rcl.postRequest(endpoint2,"POST",pull_req_body)
        //rcl.postRequest(endpoint3,"PUT",merge_req_body)
    }

    @Override
    void run() {
        gitRequests()
    }
}