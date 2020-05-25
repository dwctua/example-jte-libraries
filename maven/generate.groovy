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
    String base = 'example-jte-app-maven/apiproxy/proxies/*.xml'
    def files =  findFiles(glob: base)
    for (def file in files) {
        println file
        def proxyEndpoint = new XmlParser().parse(file)
        Map m = [:]
        m.name = proxyEndpoint.@name.text()
        m.basePath = proxyEndpoint.HTTPProxyConnection.BasePath.text()
        m.host = proxyEndpoint.HTTPProxyConnection.VirtualHost.text()
        println m
    }
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
