pipeline {
    agent any

    stages {
        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Check Maven') {
            steps {
                // Check if mvn is accessible and print version
                sh 'mvn --version'
            }
        }

        stage('Build') {
            steps {
                // Add Maven to PATH explicitly in case Jenkins environment doesn't have it
                withEnv(["PATH+MAVEN=/usr/share/maven/bin:/usr/bin"]) {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }

        stage('Prepare Jar') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo 'Prepare Jar step placeholder'
                // Add your actual jar preparation steps here
            }
        }

        stage('Unit Test') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo 'Unit Test step placeholder'
                // Add your actual unit test commands here
            }
        }

        stage('Build Docker Image') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo 'Build Docker Image step placeholder'
                // Add docker build commands here
            }
        }

        stage('Deploy to Test') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo 'Deploy to Test step placeholder'
                // Add deployment commands here
            }
        }

        stage('Selenium Test') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo 'Selenium Test step placeholder'
                // Add Selenium tests here
            }
        }

        stage('Deploy to Prod') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo 'Deploy to Prod step placeholder'
                // Add production deployment commands here
            }
        }
    }

    post {
        failure {
            echo 'Build failed!'
            // If you want to send email here, make sure SMTP server is configured properly
            // mail to: 'you@example.com', subject: "Build failed: ${env.JOB_NAME}", body: "Check Jenkins logs."
        }
        cleanup {
            cleanWs()
        }
    }
}
