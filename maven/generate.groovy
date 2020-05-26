import groovy.util.*
void call() {
    stage('pre_build') {
        println 'maven: generateSource()'
        parseXML()
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
    String base = 'apiproxy/proxies/*.xml'
    def files =  findFiles(glob: base)
    echo """${files[0].name} 
            ${files[0].path} 
            ${files[0].directory} 
            ${files[0].length} 
            ${files[0].lastModified}
    """

    for (def filename in files) {
        println filename
        def xmlfile = readFile filename.path
        Map m = [:]
        m.name =   extractFromXml(xmlfile) { proxyEndpoint -> proxyEndpoint.@name.text() }
        // m.name = proxyEndpoint.@name.text()
        // m.basePath = proxyEndpoint.HTTPProxyConnection.BasePath.text()
        // m.host = proxyEndpoint.HTTPProxyConnection.VirtualHost.text()
        println m
    }
}


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
