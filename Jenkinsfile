pipeline {
	agent any
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('yang5')
        DOCKER_IMAGE = 'eric050505/teedy_yyz'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }
    stages {
		stage('Build') {
			steps {
				checkout scmGit(
                    branches:[[name: '*/b-12212726']],
                    extensions: [],
                    userRemoteConfigs: [[url: 'https://github.com/Eric050505/Teedy']]
                )
                sh 'mvn -B -DskipTests clean package'
            }
        }
// Building Docker images
        stage('Building image') {
			steps {
				script {
                    docker.build("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}")
                }
            }
        }

        stage('Upload image') {
			steps {
				script {
                    docker.withRegistry('https://registry.hub.docker.com','yang5') {
                        docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push()
                        docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push('latest')
                    }
                }
            }
        }

        stage('Run containers') {
			steps {
				script {
                    sh 'docker stop teedy-container-8081 || true'
                    sh 'docker rm teedy-container-8081 || true'
                    docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").run('--name teedy-container-8081 -d -p 8081:8080')

                    sh 'docker ps --filter "name=teedy-container"'
                }
            }
        }
    }
}