import groovy.json.JsonSlurperClassic

@NonCPS
def call(String filename)  {
  sh """
    pwd
    ls -lart config
    ls -lart /home/jenkins/workspace/test-shared-lib_master/config/host.json
  """
  def jsonSlurper = new JsonSlurperClassic()
  json = readFile(filename)
  println(json)
  data = jsonSlurper.parseText(json)
  println(data)
}
