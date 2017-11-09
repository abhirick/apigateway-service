node {
        def mvn_version = 'MVN_HOME'
        stage ('SCM') {
                checkout([$class: 'GitSCM', branches: [[name: '*/apigateway-service-dev']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CleanBeforeCheckout']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'FTDGIT', url: 'https://s_fuser@del.tools.publicis.sapient.com/bitbucket/scm/ftd/apigateway-service.git']]])
        }
        stage('Build') {
                withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
                        sh "mvn clean package"
                }
        }
        stage('Quality') {
                withSonarQubeEnv('FTD_SONAR') {
                        withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
                        sh 'mvn sonar:sonar'
                }
        }
        }
        stage("Quality Gate"){
                timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
                    def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
                    if (qg.status != 'OK') {
                      error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    }
                }
        }
        stage("Docker") {
        		docker.build('apigatewayservice')
        }
}
