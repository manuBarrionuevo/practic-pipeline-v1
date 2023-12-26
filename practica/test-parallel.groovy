pipeline {
    agent any      
         environment{
                CARPETA="node-app-devops"
                REPO_URL="https://github.com/roxsross/node-app-devops.git"
                DOCKER_HUB_LOGIN=credentials('docker')
                REGISTRY='manuelbarrionuevo'
            }
    stages {
        stage('Init') { 
            steps {
                sh """
                if [ -d "\$CARPETA" ]; then
                    echo "La carpeta ya está creada"
                else
                    git clone "\$REPO_URL"
                    if [ \$? -eq 0 ]; then
                        echo "Se clonó la carpeta"
                    else
                        echo "Error al clonar la carpeta"
                    fi
                fi
                """
                }
        }
        stage('build') { 
            steps {
                sh 'cd node-app-devops && docker build -t prueba-node:v1 .' 
            }
        }
        stage('parallel') {                
            parallel{
                stage('deploy')
                {
                    steps{
                            sh ''' 
                            docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW
                            docker tag prueba-node:v1 $REGISTRY/rueba-node:v1
                            docker push $REGISTRY/rueba-node:v1
                            '''
                    }
                }
                stage('uni test')
                {
                
                        steps{
                            echo 'test unitario'
                        }
                    }
                stage('uni test 2')
                {
                
                        steps{
                            echo 'test unitario 2'
                        }
                    }
                }
            
            
        }
    }
}
