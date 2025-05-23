pipeline {
    agent any

    environment {
        IMAGE = "insure-me"
        DOCKER_HUB = "yathishnag/insure-me"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Yathishnagaraj/star-agile-insurance-project.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Prepare Jar') {
            steps {
                sh 'cp selenium-insure-me-runnable.jar ./selenium-insure-me-runnable.jar || cp target/selenium-insure-me-runnable.jar ./selenium-insure-me-runnable.jar'
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE} ."
                withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    sh "docker tag ${IMAGE} ${DOCKER_HUB}:latest"
                    sh "docker push ${DOCKER_HUB}:latest"
                }
            }
        }

        stage('Deploy to Test') {
            steps {
                sshagent(['k8s-master-ssh']) {
                    sh """
                    ssh ubuntu@10.0.0.20 << EOF
                    kubectl set image deployment/insure-me-deployment insure-me-container=${DOCKER_HUB}:latest -n test || \\
                    kubectl apply -f k8s/test-deployment.yaml
                    EOF
                    """
                }
            }
        }

        stage('Selenium Test') {
            steps {
                sh './scripts/run-selenium-tests.sh'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'selenium-results/**/*.xml', allowEmptyArchive: true
                }
            }
        }

        stage('Deploy to Prod') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                sshagent(['k8s-master-ssh']) {
                    sh """
                    ssh ubuntu@10.0.0.20 << EOF
                    kubectl set image deployment/insure-me-deployment insure-me-container=${DOCKER_HUB}:latest -n prod || \\
                    kubectl apply -f k8s/prod-deployment.yaml
                    EOF
                    """
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        failure {
            mail to: 'team@example.com', subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}", body: "Check Jenkins for details."
        }
    }
}
