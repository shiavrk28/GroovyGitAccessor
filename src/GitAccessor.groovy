import groovy.json.JsonBuilder
import groovy.swing.SwingBuilder
import java.util.Timer
import javax.swing.SwingContainer
import javax.swing.WindowConstants as WC
import java.util.TimerTask
class GitAccessor extends TimerTask {


    def gitRequests() {
        def properties = new Properties();
        getClass().getResource("/res/applications.properties").withInputStream {
            properties.load(it)
        }


        def gitToken = properties.GIT_TOKEN.toString().replaceAll("\"", "")
        def lines = []
        def request_type = System.console().readLine 'What Git operation you want to perform?'
        def repo = System.console().readLine 'Please enter your repository name '
        def owner = System.console().readLine 'Please enter the owner of '+repo+' '
        def head = System.console().readLine 'Please enter the head branch '
        def base = System.console().readLine 'Please enter the base branch '
        def title = System.console().readLine 'Please enter the title of the request '
        def pull_no=""
        def commit_msg=""
        if (request_type.equalsIgnoreCase("merge")) {
         pull_no = System.console().readLine 'Please enter the pull number '
         commit_msg = System.console().readLine 'Please enter the commit message '
    }


        RestClientLocal rcl = new RestClientLocal();
        def endpoint = ""
        def reqBody = ""
        def reqMethod = ""
        //def req = lines[5].toString()
        def acceptedmethods = [] as ArrayList;
        acceptedmethods.add(properties.PULL.toString())
        acceptedmethods.add(properties.MERGE.toString())
       // println(">>"+acceptedmethods)
        if (!acceptedmethods.contains(request_type.toUpperCase()))
            println(request_type + " is an un-supported command")
         else{
        if (request_type.equalsIgnoreCase(properties.PULL)) {
            endpoint = properties.PULL_REQ.replace("<owner>", owner).replace("<repo>", repo).replaceAll("\"", "")
            reqBody = new JsonBuilder([
                    owner: owner,
                    repo: repo,
                    title: title,
                    head : head,
                    base : base
            ]).toPrettyString()
            reqMethod = properties.POST
        } else if (request_type.equalsIgnoreCase(properties.MERGE)) {
            endpoint = properties."MERGE_REQ".replace("<owner>", owner).replace("<repo>", repo).replace("<pullno>", pull_no).replaceAll("\"", "")
            reqBody = new JsonBuilder([
                    commit_title  : title,
                    commit_message: commit_msg
            ]).toPrettyString()
            reqMethod = properties.PUT
        }

        //rcl.postRequest(endpoint1,"GET",null)
            println(">>>>>>"+reqBody)
        def result1 = rcl.postRequest(endpoint, reqMethod, reqBody, gitToken)
        if (result1.message != null)
            println "Request Status : " + result1.message
        def errors = result1.errors
        errors.each { println("Error Message : " + it.message) }
        //rcl.postRequest(endpoint3,"PUT",merge_req_body,properties."GIT_TOKEN".to)
    }
}
    @Override
    void run() {
        gitRequests()
    }
}