import groovy.json.JsonSlurper

def call(String filename)  {
  sh """
    pwd
    ls -lart config
    ls -lart /home/jenkins/workspace/test-shared-lib_master/config/host.json
  """
  json = readFile(filename)
  println(json)
  def jsonSlurper = new JsonSlurper()
  data = new HashMap<>(jsonSlurper.parseText(json))
  println(data)
}
