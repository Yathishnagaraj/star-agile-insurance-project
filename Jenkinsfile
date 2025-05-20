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
                sh 'mvn clean install'
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE .'
                withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh "docker tag $IMAGE $DOCKER_HUB:latest"
                    sh "docker push $DOCKER_HUB:latest"
                }
            }
        }

        stage('Deploy to Test') {
            steps {
                sshagent(['k8s-master-ssh']) {
                    sh '''
                    ssh ubuntu@10.0.0.20 << EOF
                    kubectl set image deployment/insure-me-deployment insure-me-container=$DOCKER_HUB:latest -n test || \
                    kubectl apply -f k8s/test-deployment.yaml
                    EOF
                    '''
                }
            }
        }

        stage('Selenium Test') {
            steps {
                sh './scripts/run-selenium-tests.sh'
            }
        }

        stage('Deploy to Prod') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                sshagent(['k8s-master-ssh']) {
                    sh '''
                    ssh ubuntu@10.0.0.20 << EOF
                    kubectl set image deployment/insure-me-deployment insure-me-container=$DOCKER_HUB:latest -n prod || \
                    kubectl apply -f k8s/prod-deployment.yaml
                    EOF
                    '''
                }
            }
        }
    }
}
