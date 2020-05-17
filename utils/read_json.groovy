import groovy.json.JsonSlurper

def call(String filename)  {
  sh """
    pwd
    ls -lart config
    ls -lart /home/jenkins/workspace/test-shared-lib_master/config/host.json
  """
  def jsonSlurper = new JsonSlurper()
  json = readFile(filename)
  println(json)
  data = new HashMap<>(jsonSlurper.parseText(json))
  println(data)
}
