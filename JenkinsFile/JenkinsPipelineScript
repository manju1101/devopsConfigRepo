def extcode

node{
    def appProp
        stage('Pull Config files'){
            git branch: 'master',
                credentialsId: 'cf6edfe6-5dc7-4bbf-b4f5-491e8e83b22b', 
                url: 'https://github.com/manju1101/devopsConfigRepo.git'
                
                println "Hello"
                extcode = readFile('./javaConfig.properties')
                println extcode
                
                appProp = readProperties  file: './javaConfig.properties'
                println appProp['GIT_APP_REPO']
        }
        
        stage('GitClone') { // for display purposes
      // Get some code from a GitHub repository
              git branch: appProp['GIT_APP_BRANCH'],
              credentialsId: 'cf6edfe6-5dc7-4bbf-b4f5-491e8e83b22b', 
              url: appProp['GIT_APP_REPO']
            }
            
            
        stage('Building Sonar....') {
            def project_path="."
            dir(project_path){
            //some block
                withSonarQubeEnv('SonarNameInJenkins') {
                    // sh "${scannerHome}/bin/sonar-scanner"
                    sh 'mvn clean package sonar:sonar'
                    // sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
                    // sh 'mvn clean package org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
                }
    //   archiveArtifacts 'target/*.jar'
            }
        }
    }
