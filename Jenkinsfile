pipeline {
    agent any
    stages {
        stage ('Build Backend'){
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Unit Tests'){
            steps {
                sh 'mvn test'
            }
        }
        stage ('Sonar Analysis'){
            
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            
            steps {
                withSonarQubeEnv('SONAR_LOCAL'){
                    sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=d8316423832c2aa968ba603e04a1d53eccf74629 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
            }
        }
        stage ('Quality Gate'){
            steps{
                sleep(5)
                timeout(time:1, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage ('Deploy Backend') {
            steps{
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage ('API Test'){
            steps {
                dir('api-test'){                 
                    git credentialsId: 'GitHubLogin', url: 'https://github.com/rodrigues374/tasks-api-test'                    
                }
            }
        }
        stage ('Deploy front-end'){
            steps {
                dir('front-end'){                    
                    git credentialsId: 'GitHubLogin', url: 'https://github.com/rodrigues374/tasks-frontend'
                    sh 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }
        stage ('Deploy Prod'){
            steps {
                sh 'docker-compose build'
                sh 'docker-compose up -d'
            }
        }
    }
    post {
        always {
            junit skipMarkingBuildUnstable: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/tasks-backend.war, front-end/target/tasks.war', onlyIfSuccessful: true
        }
        unsuccessful {
            emailext attachLog: true, body: 'asasasa', subject: ' $BUILD_NUMBER FALHA', to: 'rodolfo.rodrigues+jenkins374@gmail.com'
        }
        fixed {
            emailext attachLog: true, body: 'asasasa', subject: '$BUILD_NUMBER OK', to: 'rodolfo.rodrigues+jenkins374@gmail.com'
        }
    }
}

