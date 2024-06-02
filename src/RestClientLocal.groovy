import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
class RestClientLocal {

    def baseUrl = "https://api.github.com";
    //def gitToken="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"
    def gitToken="token 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"
    def postRequest(url, method, message) {
    def url2=new URL(baseUrl+url);
        println(">>$url2")
        if (method.equals("GET")) {
            def get = url2.openConnection();
            def getRC = get.getResponseCode();
            println(getRC);
            if (getRC.equals(200)) {
                println(get.getInputStream().getText());
            }
        } else {
            def post = url2.openConnection();
            post.setRequestMethod(method)
            post.setRequestProperty("Authorization", gitToken);
            post.setRequestProperty("Content-Type", "application/json")
            post.setDoOutput(true)
            post.getOutputStream().write(message.getBytes("UTF-8"))

            def response = [:]

            response.code = post.getResponseCode()
            response.message = post.getResponseMessage()
            if (response.code >= 400) {
                try {
                    response.body = post.getErrorStream()?.getText("UTF-8")
                } catch (e) {
                }
            } else {
                response.body = post.getInputStream()?.getText("UTF-8")
            }
            assert response.code in [200, 201]: "http call failure ${response.code}: ${response.body ?: response.message}"
            println response.message;
            return response
        }



        }


    def createPullRequest(String owner, String repo, String title, String head, String base){
        restClient.headers['Authorization'] = "token $token"
        def body = new JsonBuilder([
                title: title,
                head: head,
                base: base
        ])
        def response = restClient.post(path: "/repos/$owner/$repo/pulls", body: body)
        return new JsonSlurper().parseText(response.data)
    }

}