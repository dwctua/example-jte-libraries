import groovy.json.JsonSlurper

def call(String filename)  {
  def jsonSlurper = new JsonSlurper()
  data = jsonSlurper.parse(new File(filename))
  println(data)
}
