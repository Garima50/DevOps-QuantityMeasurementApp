pipeline {
    agent any

    environment {
        IMAGE_NAME = "garima42/quantity-app-simple"
        IMAGE_TAG = "v1"
    }

    tools {
        jdk 'jdk23'
        maven 'maven'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .'
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-token',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    sh '''
                        echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                    '''
                }
            }
        }

        stage('Docker Push') {
            steps {
                sh 'docker push ${IMAGE_NAME}:${IMAGE_TAG}'
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([
                    string(credentialsId: 'google-client-id', variable: 'GOOGLE_CLIENT_ID'),
                    string(credentialsId: 'google-client-secret', variable: 'GOOGLE_CLIENT_SECRET')
                ]) {
                    sh '''
                        docker stop quantity-app-simple || true
                        docker rm quantity-app-simple || true

                        docker run -d \
                          --name quantity-app-simple \
                          -p 8081:8080 \
                          -e GOOGLE_CLIENT_ID="$GOOGLE_CLIENT_ID" \
                          -e GOOGLE_CLIENT_SECRET="$GOOGLE_CLIENT_SECRET" \
                          ${IMAGE_NAME}:${IMAGE_TAG}
                    '''
                }
            }
        }
           }
           }// closes pipeline