import groovy.json.JsonSlurper

def call(String filename)  {
  sh """
    pwd
    ls -lart config
    ls -lart /home/jenkins/workspace/test-shared-lib_master/config/host.json
  """
  def jsonSlurper = new JsonSlurper()
  data = jsonSlurper.parse(new File(filename))
  println(data)
}
