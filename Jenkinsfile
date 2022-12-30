pipeline {
    agent any
    tools{
    maven 'jenkins_tomcat'
    }
    stages {
        stage("git") {
            steps {
                git credentialsId: '12345', url: 'git@github.com:apenkomix/andersen3.git'
            }
        }
        stage("build") {
            steps {
                sh "mvn clean install"
            }
        }
        stage("deploy") {
            steps{
               deploy adapters: [tomcat9(credentialsId: '987654321', path: '', url: 'http://192.168.0.108:8080/')], contextPath: 'war', war: '**/*.war'

            }

        }
    }
}