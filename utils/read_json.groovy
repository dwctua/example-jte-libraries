import groovy.json.JsonSlurper

def call(String filename)  {

  json = readFile(filename)
  def jsonSlurper = new JsonSlurper()
  data = new HashMap<>(jsonSlurper.parseText(json))
  println(data['test'][0])
  sh """
    pwd
    ls -lart config
    env | grep 
  """
}
