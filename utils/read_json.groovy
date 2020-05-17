import groovy.json.JsonSlurper

def call(String filename)  {
  sh """
    pwd
    ls -lart
  """
  def jsonSlurper = new JsonSlurper()
  data = jsonSlurper.parse(new File(filename))
  println(data)
}
