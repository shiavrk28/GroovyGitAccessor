import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
class RestClientLocal {

    def postRequest(url, method, message, gitToken) {
       /* if (method.equals("GET")) {
            def get = url.openConnection();
            def getRC = get.getResponseCode();
            println(getRC);
            if (getRC.equals(200)) {
                println(get.getInputStream().getText());
            }
        } else {*/

        def uri=new URL(url)
            def conn = uri.openConnection();
            conn.setRequestMethod(method)
            conn.setRequestProperty("Authorization", gitToken);
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setDoOutput(true)
            conn.getOutputStream().write(message.getBytes("UTF-8"))

            def response = [:]

            response.code = conn.getResponseCode()
            response.message = conn.getResponseMessage()
            if (response.code >= 400) {
                try {
                    response.body = conn.getErrorStream()?.getText("UTF-8")
                } catch (e) {
                }
            } else {
                response.body = conn.getInputStream()?.getText("UTF-8")
            }
            def result=new JsonSlurper().parseText(response.body)
            println result;
            return result
      //  }

        }
}