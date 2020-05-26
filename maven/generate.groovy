import groovy.util.*
void call() {
    stage('pre_build') {
        println 'maven: generateSource()'
        parseXML()
        parseJSON()
    }
}

private void sshTest() {
    withCredentials([sshUserPrivateKey(credentialsId: 'ssh', keyFileVariable: 'keyfile')]) {
        //  sh "ssh -i ${keyfile} git@github.com "
        //  sh " echo ${keyfile} "

        sshagent(['ssh']) {
            sh 'ssh -q git@github.com'
        }

        withCredentials([
            usernamePassword(credentialsId: 'test-up',
              usernameVariable: 'username',
              passwordVariable: 'password')
          ]) {
            print 'username=' + username + 'password=' + password

            print 'username.collect { it }=' + username.collect { it }
            print 'password.collect { it }=' + password.collect { it }
          }
    }
}

private void parseXML() {
    String baseDir = 'apiproxy/proxies/*.xml'
    def files =  findFiles(glob: baseDir)
    echo """${files[0].name}
            ${files[0].path}
            ${files[0].directory}
            ${files[0].length}
            ${files[0].lastModified}
    """

    List list = []
    for (def file in files) {
        println file
        def xml = readFile file.path
        def proxyEndpoint = new XmlSlurper().parseText(xml)
        Map m = [:]
        m.name = proxyEndpoint.@name.text()
        String base =   proxyEndpoint.HTTPProxyConnection.BasePath.text()
        m.basePath = (base) ? base : "/"
        url =  proxyEndpoint.HTTPProxyConnection.VirtualHost.text()

        // m.name =   extractFromXml(xmlfile) { proxyEndpoint -> proxyEndpoint.@name.text() }
        // String base =  extractFromXml(xmlfile) { proxyEndpoint -> proxyEndpoint.HTTPProxyConnection.BasePath.text() }
        // m.basePath = (base) ? base : "/"
        // m.url =   extractFromXml(xmlfile) { proxyEndpoint -> proxyEndpoint.HTTPProxyConnection.VirtualHost.text() }
       list.add(m)
    }
    println list
}

private void parseJSON() {
    String filePath = 'config/host.json'
    def json = readJson file: filePath
    print json
}

@NonCPS
String extractFromXml(String xml, Closure closure) {
    def node = new XmlSlurper().parseText(xml)
    return closure.call(node)?.text()
}

/*
@BeforeStep
void before(context){
    println "generateSource: running before the ${context.library} library's ${context.step} step"
}

@AfterStep
void after(context){
    println "generateSource: running after the ${context.library} library's ${context.step} step"
}
*/
