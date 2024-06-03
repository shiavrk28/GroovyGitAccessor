import groovy.json.JsonBuilder
import groovy.swing.SwingBuilder
import java.util.Timer
import javax.swing.SwingContainer
import javax.swing.WindowConstants as WC
import java.util.TimerTask
class GitAccessor extends TimerTask{


    def gitRequests() {
        def properties=new Properties();
        getClass().getResource("/res/applications.properties").withInputStream {
            properties.load(it)
        }
        def gitToken=properties.GIT_TOKEN.toString().replaceAll("\"","")
        def lines =[]
        def repo = ""
        def owner = ""
        def head=""
        def base=""
        def pull_no=""
        def title=""
        def commit_msg=""
        def request=""
        getClass().getResource("/Input.txt").eachLine {
            def var=it.split(":")[0].toString().toLowerCase().trim()
            switch(var){
                case "repo name":
                    repo=it.split(":")[1].toString().trim()
                case "owner":
                    owner=it.split(":")[1].toString().trim()
                case "head":
                    head=it.split(":")[1].toString().trim()
                case "base":
                    base=it.split(":")[1].toString().trim()
                case "title":
                    title=it.split(":")[1].toString().trim()
                case "pull number":
                    pull_no=it.split(":")[1].toString().trim()
                case "commit message":
                    commit_msg=it.split(":")[1].toString().trim()
            }
            lines << it.split(": ")[1]
        }

        RestClientLocal rcl = new RestClientLocal();
        def endpoint=""
        def reqBody=""
        def reqMethod="GET"
        def req=lines[5].toString()
        if(req.equalsIgnoreCase("PULL")) {
            endpoint = properties."PULL_REQ".replace("<owner>", owner).replace("<repo>", repo).replaceAll("\"","")
            reqBody=new JsonBuilder([
                    title: title,
                    head : head,
                    base : base
            ]).toPrettyString()
            reqMethod=properties.POST
        }
        else if(req.equalsIgnoreCase("MERGE")) {
            endpoint = properties."MERGE_REQ".replace("<owner>", owner).replace("<repo>", repo).replace("<pullno>", pull_no).replaceAll("\"","")
            reqBody=new JsonBuilder([
                    commit_title  : title,
                    commit_message: commit_msg
            ]).toPrettyString()
            reqMethod=properties.PUT
        }

        //rcl.postRequest(endpoint1,"GET",null)
def result1=rcl.postRequest(endpoint,reqMethod,reqBody,gitToken)
        if(result1.message!=null)
            println "Request Status : "+result1.message
        def errors=result1.errors
        errors.each{println("Error Message : "+ it.message)}
        //rcl.postRequest(endpoint3,"PUT",merge_req_body,properties."GIT_TOKEN".to)
    }

    @Override
    void run() {
        gitRequests()
    }
}