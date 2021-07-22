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
            steps {
                sh 'mvn sonar:sonar -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=d8316423832c2aa968ba603e04a1d53eccf74629 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java '
            }
        }
        stage ('Quality Gate'){
            steps{
                timeout(time:1, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}

