const scanner = require('@sonar/scan').default;
// Replace with your project name key and token and 
// use the command 'node ./sonarqube/sonarscan.js' 
// on the frontend folder to analyze
// Don´t forget to define de environment variable SONAR_TOKEN (and restart the shell!)
console.log("USING AS TOKEN: ",process.env.SONAR_TOKEN)
scanner(
    {
        serverUrl: 'https://oitilo.us.es/sonar',
        token: 'sqp_5b9545b9242d3b90d76650dcfa3daba3a0620946',
        options: {
            'sonar.projectName': 'psg2-2526-g6-63-frontend',
            'sonar.projectDescription': 'Here I can add a description of my project',
            'sonar.projectKey': 'psg2-2526-g6-63-frontend',
            'sonar.projectVersion': '1.0.0',
            'sonar.token': 'sqp_5b9545b9242d3b90d76650dcfa3daba3a0620946',
            'sonar.exclusions': '',
            'sonar.sourceEncoding': 'UTF-8',
            'sonar.javascript.lcov.reportPaths': './coverage/lcov.info'
        }
    },
    () => process.exit()
)