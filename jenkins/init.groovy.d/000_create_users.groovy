import jenkins.model.*
import hudson.security.*

final env = System.getenv()
final defaultPass = env.JENKINS_DEFAULT_PASSWORD ?: "jenkins123"

/* TODO: Define users here. */
final userDefs = [
    [id: "alice", pass: "${defaultPass}", name: "Alice"      , isAdmin: false],
    [id: "bob"  , pass: "${defaultPass}", name: "Bob"        , isAdmin: false],
    [id: "carol", pass: "${defaultPass}", name: "Carol"      , isAdmin: false],
]

def jenkins  = Jenkins.getInstance()
def security = jenkins.getSecurityRealm()
def strategy = jenkins.getAuthorizationStrategy()

final allUsers = security.getAllUsers().each { it.getId() }

for (def userDef in userDefs) {
    if (allUsers.contains(userDef.id)) {
        continue
    }
    def user = security.createAccount(userDef.id, userDef.pass)
    user.setFullName(userDef.name)
    user.save()

    /* TODO: Add to administer is allowed when using matrix or role based auth. */
    /*
    if (userDef.isAdmin) {
        strategy.add(Jenkins.ADMINISTER, userDef.id)
    }
    */
}

jenkins.save()

