package utils

import groovy.json.JsonSlurper

class FileUtils implements Serializable{

 static def read_json {
if (args.size() < 1) {
    println("Missing filename")
    System.exit(1)
}
 
filename = args[0]
 
def jsonSlurper = new JsonSlurper()
data = jsonSlurper.parse(new File(filename))
 
println(data)
}
}
