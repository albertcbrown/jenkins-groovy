#!groovy

import groovy.json.JsonOutput

// Functions

// Docker functions
def dockerlogin() {
    retry (3) {
        timeout(60) {
                sh "aws ecr get-login --region us-west-2 | bash"
                sh "aws ecr get-login --region us-west-1 | bash"
        }
    }
}

def dockerbuild(label) {
    sh "docker build -t ${label} ."
}

def dockerrmi(vm) {
    sh "docker -H tcp://10.1.10.210:5001 rmi -f registry.1for.one:5000/${vm} || echo RMI Failed"
}

def dockertag(label_old, label_new) {
    sh "docker tag -f ${label_old} ${env.ECR_USWEST2_REGISTRY}/${label_new}"
}

def dockerpush(image) {
    sh "dockerpush ${env.ECR_USWEST2_REGISTRY}/${image}"
}
