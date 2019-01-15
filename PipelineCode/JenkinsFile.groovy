def extcode

node{
    def pipelineProp
    def utilProp
        stage('Pull Pipeline Config'){
            git branch: 'master',
                credentialsId: 'cf6edfe6-5dc7-4bbf-b4f5-491e8e83b22b', 
                url: 'https://github.com/manju1101/devopsConfigRepo.git'

                pipelineProp = readProperties  file: './Properties/JenkinsFile.properties'
                println pipelineProp['GIT_APP_REPO']
        }
        
        stage('Pull Utility Config'){
            git branch: 'master',
                credentialsId: 'cf6edfe6-5dc7-4bbf-b4f5-491e8e83b22b', 
                url: 'https://github.com/manju1101/DevopsUtilRepo.git'

                utilProp = readProperties  file: './LinuxSystem/shellCommands.groovy'
                println utilProp['MVN_CLEAR_PACKAGE']
                println utilProp['MVN_CLEAR_PACKAGE'] +" "+pipelineProp['SOANR_BUILD']
        }
        
        stage('GitClone') { // for display purposes
      // Get some code from a GitHub repository
              git branch: pipelineProp['GIT_APP_BRANCH'],
              credentialsId: 'cf6edfe6-5dc7-4bbf-b4f5-491e8e83b22b', 
              url: pipelineProp['GIT_APP_REPO']
            }
            
            
        stage('Building Sonar....') {
            def project_path=pipelineProp['GIT_APP_POMFILE_PATH']
            dir(project_path){
            //some block
                withSonarQubeEnv('SonarNameInJenkins') {
                    // sh "${scannerHome}/bin/sonar-scanner"
                    sh utilProp['MVN_CLEAR_PACKAGE']+" "+pipelineProp['SOANR_BUILD']
                    // sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
                    // sh 'mvn clean package org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
                }
    //   archiveArtifacts 'target/*.jar'
            }
        }
        
        stage('deploy tomcat') { 
            sh 'sudo cp /var/lib/jenkins/workspace/EndToEndPipeline/target/*.war /opt/tomcat/webapps/'
            }
    }
