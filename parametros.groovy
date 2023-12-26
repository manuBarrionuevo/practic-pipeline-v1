pipeline {
    agent any
    parameters{
        string(name: 'PERSONA' defaultValue:'maria', description:'nombre persona prueba')
    }

    stages {
        stage('Hello') {
            steps {
                echo "Hola soy ${params.PERSONA}"
            }
        }
    }
    post {
        failure {
            echo 'esta ejecución ha fallado'
        }
    }
}
