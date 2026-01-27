pipeline {
  agent {
    kubernetes {
      label 'microservicios-jdk21-sas-201-agent'
    }
  }

  parameters {
    string(name: 'IMAGE_NAME', defaultValue: 'azudirayacont02.azurecr.io/COMPONENTE/A_RELLENAR', description: 'Nombre de la imagen para ACR')
    string(name: 'HELM_REPO_URL', defaultValue: 'A_RELLENAR', description: 'Repositrio Helm')
    string(name: 'HELM_REPO_BRANCH', defaultValue: 'develop', description: 'Rama del repo HELM donde está el proyecto')
    string(name: 'HELM_VALUES_PATH', defaultValue: 'injectValues_DES.yaml', description: 'Fichero values.yaml donde están los valores para AZURE')
    string(name: 'HELM_CONFIG_KEY', defaultValue: 'A_RELLENAR.yaml', description: 'Proyecto en repo HELM')
  }

  environment {
    TAG = ''
  }

  stages {

    stage('Build') {
      steps {
        container('jdk21-mvn-sas201') {
          sh 'mvn clean compile'
        }
      }
    }

    stage('Unit Test') {
      steps {
        container('jdk21-mvn-sas201') {
          sh 'mvn test'
        }
      }
    }

    stage('Package') {
      steps {
        container('jdk21-mvn-sas201') {
          sh 'mvn package -DskipTests'
        }
      }
    }

    stage('Set Version') {
      steps {
        container('jdk21-mvn-sas201') {
          script {
            def version = sh(
              script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout',
              returnStdout: true
            ).trim()

            def buildNum = currentBuild.getNumber()
            def timestamp = new Date().format('yyyyMMddHHmmss')
            def tag = "${version}-${buildNum}-${timestamp}"

            writeFile file: 'tag.txt', text: tag
            env.TAG = tag

            echo "Versión generada para la imagen: ${tag}"
          }
        }
      }
    }

    stage('Build Docker Image') {
      steps {
        container('kaniko') {
          script {
            def tag = readFile('tag.txt').trim() // <-- Lee desde el archivo
            env.TAG = tag
            sh """
              echo 'Construyendo imagen: $IMAGE_NAME:${tag}'
              /kaniko/executor \
                --context `pwd` \
                --cache=true \
                --cache-dir=/cache \
                --dockerfile `pwd`/Dockerfile \
                --destination $IMAGE_NAME:${tag} \
                --verbosity info
            """
          }
        }
      }
    }


    stage('Update Helm Chart') {
      steps {
        container('jdk21-mvn-sas201') {
          withCredentials([usernamePassword(credentialsId: 'ecu-credentials', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
            script {
              def tag = readFile('tag.txt').trim()
              def repoUrl = "https://${GIT_USER}:${GIT_PASS}@${params.HELM_REPO_URL.replace('https://', '')}"
              def repoDir = currentBuild.getNumber()
              def branch = params.HELM_REPO_BRANCH
              def yamlPath = "${repoDir}/${params.HELM_VALUES_PATH}"
              def configFileKey = params.HELM_CONFIG_KEY


              sh """
                rm -rf ${repoDir}
                git clone --branch ${branch} ${repoUrl} ${repoDir}

                echo "Actualizando tag de imagen en values.yaml..."
                sed -i '/configFile: projects\\/${configFileKey}/,/imageTag:/s/imageTag: .*/imageTag: "${tag}"/' ${yamlPath}

                echo "Contenido actualizado:"
                cat ${yamlPath}

                echo "Commit y push..."
                cd ${repoDir}
                git config user.name "Jenkins Bot"
                git config user.email "nomail@nttdata.com"
                git add ${params.HELM_VALUES_PATH}
                git commit -m "ci: bump version ${tag} ${configFileKey}" || echo "Sin cambios que commitear"
                git push origin ${branch}
              """
            }
          }
        }
      }
    }
  }
}
