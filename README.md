

#### **nugensol-lkomm**
###### This Repository aims to provide basic Continuous Integration / Continuous Delivery Pipelines for Java Applications .
CI/CD Tools Integrated:
 * CloudBees Jenkins  : **Jenkins 2.375.1**
 * SonarQube          : **sonarqube-9.7.1.62043**
 * Docker             : **version 20.10.20**
 * Ubuntu             : **20.04.3 LTS**

Typical usage  : 
  1) Fork this Repo.
  2) Goto the folder and update 'dev-ci-cd/dev1/config.yml'
  3) Create a new Jenkins Pipeline 
  4) Set the following pipeline properties:
   * 	ENVIRONMENT           = 'dev1'
   * 	CONFIG_REPO           = 'https://github.com/stspro/nugensol.git'
   * 	CONFIG_REPO_BRANCH    = 'main'
   * 	CONFIG_YAML_LOC       = 'dev-ci-cd/dev1/config.yml'
  5) Build the pipeline.
  6) Verify the Docker Container deployed on local DockerHub.
