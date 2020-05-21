void call(){
      stage("pre_build"){
        println "maven: generateSource()"
             withCredentials([sshUserPrivateKey(credentialsId: "mim1206", keyFileVariable: 'keyfile')]) {
        sh "ls -lart ${keyfile} "
                   sh " cat ${keyfile} "
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
