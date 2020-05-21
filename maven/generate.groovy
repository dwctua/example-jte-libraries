void call(){
    stage("pre_build"){
        println "maven: generateSource()"
     
        withCredentials([sshUserPrivateKey(credentialsId: 'ssh_key', keyFileVariable: 'keyfile')]) {
          sh "ls -lart ${keyfile} "
          sh " echo ${keyfile} "
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
