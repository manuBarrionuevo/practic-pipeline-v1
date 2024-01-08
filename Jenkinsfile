pipeline {
    agent any
    environment {
        REGISTRY = 'manuelbarrionuevo'
        DOCKER_HUB_LOGIN = credentials('docker')
        CARPETA='jekins'
    }
    stages { // el principal donde se arman la tuberia CI
        stage('GitClone')
        {
                    steps {
                sh '''
                     rm $CARPETA
                     git clone https://github.com/manuBarrionuevo/jekins.git
                    '''
                    }
        }

        stage('Build') {
            parallel {
                stage('result')
                {
                    steps {
                            sh '''
                                cd result
                                docker build -t result:v1 .
                            '''
                    }
                }
                stage('vote')
                {
                        steps {
                                sh '''
                                    cd ..
                                    cd vote
                                    docker build -t vote:v1 .
                                '''
                            }
                }
                stage('worker')
                {
                    steps {
                        sh  '''
                        cd ..
                        cd worker
                        docker build -t worker:v1 .
                    '''
                    }
                }
            }
        }
        stage('deploy'){
            steps{
                sh  '''
                    docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW
                    docker docker push $REGISTRY/result:v1
                    docker docker push $REGISTRY/vote:v1
                    docker docker push $REGISTRY/worker:v1
                    '''
            }
        }
    } //fin stage principal
}
