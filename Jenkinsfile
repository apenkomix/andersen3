pipeline {
    agent any
    tools{
    maven 'jenkins_tomcat'
    }
    stages {
        stage("git") {
            steps {
                git credentialsId: 'jenkins_T', url: 'git@github.com:apenkomix/andersen3.git'
            }
        }
        stage("build") {
            steps {
                sh "mvn clean install"
            }
        }
        stage("deploy") {
            steps{
                sshagent(['jenkins_T']) {
                    sh 'scp -o StrictHostKeyChecking=yes /MyUser.war apenKO@192.168.0.108:8080:/opt/tomcat/webapps'
                }
            }

        }
    }
}