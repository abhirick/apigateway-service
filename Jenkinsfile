node {
        def image='apigatewayservice'
        stage ('SCM') {
            checkout([$class: 'GitSCM', branches: [[name: '*/apigateway-service-dev']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CleanBeforeCheckout']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'FTDGIT', url: 'https://s_fuser@del.tools.publicis.sapient.com/bitbucket/scm/ftd/apigateway-service.git']]])
        }
        stage('Build') {
        		sh "mvn clean package"
        }
        stage('Quality') {
            withSonarQubeEnv('FTD_SONAR') {
            		sh 'mvn sonar:sonar'
        		}
        }
        stage("Quality Gate"){
            timeout(time: 5, unit: 'MINUTES') { // Just in case something goes wrong, pipeline will be killed after a timeout
                def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
                if (qg.status != 'OK') {
                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
            }
        }
        stage("Build Docker Image") {
        		docker.build("${image}")
        		sh "docker save -o ${image}.tar ${image}"
        }
        stage("Remote Copy") {
			sh "scp ${image}.tar ${DOCKER_SERVICES_TARGET_USER}@${DOCKER_SERVICES_TARGET_HOST}:${REMOTE_TEMP_DIR}"
        }
 		stage("Load Image Remotely") {
			sh "ssh ${DOCKER_SERVICES_TARGET_USER}@${DOCKER_SERVICES_TARGET_HOST} \"docker load < ${REMOTE_TEMP_DIR}/${image}.tar\""
        }
        stage("Cleanup") {
            sh "rm -rf ${image}.tar"
            sh "ssh ${DOCKER_SERVICES_TARGET_USER}@${DOCKER_SERVICES_TARGET_HOST} \"rm -rf ${REMOTE_TEMP_DIR}/${image}.tar\""
			sh "docker image rm -f ${image}"
			sh "ssh ${DOCKER_SERVICES_TARGET_USER}@${DOCKER_SERVICES_TARGET_HOST} \"docker images -aq --filter dangling=true | xargs -r docker rmi -f\""
        }
}
