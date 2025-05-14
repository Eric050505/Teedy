pipeline {
	agent any
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('dockerhub_credentials')
        DOCKER_IMAGE = 'yang/teedy-yyz' // your Docker Hub user name and
        DOCKER_TAG = "${env.BUILD_NUMBER}" // use build number as tag
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
					// assume Dockerfile locate at root
                    docker.build("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}")
                }
            }
        }
// Uploading Docker images into Docker Hub
        stage('Upload image') {
			steps {
				script {
                    docker.withRegistry('https://registry.hub.docker.com','DOCKER_HUB_CREDENTIALS') {
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