pipeline {
    agent any

    stages {
        stage('Stage MedicareWeb') {
            steps {
               
               echo 'Getting code from github'
                  // Get some code from a GitHub repository
               git 'https://github.com/aditya2198/SimplilearnAutomationMedicare_Aditya.git'

                // Run Maven on a Unix agent.
                
               
            }
        }
        stage('Test MedicareWeb') {
            steps {
                echo 'Testing..'
                bat "mvn test"
            }
        }
    }
}
