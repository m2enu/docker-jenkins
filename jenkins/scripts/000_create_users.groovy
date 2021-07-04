import jenkins.model.*
import hudson.security.*
import hudson.tasks.*

/* FIXME: Define users here. */
final userDefs = [
    [id: "alice", pass: "jenkins123", name: "Alice", mail: "alice@example.com", isAdmin: false],
    [id: "bob"  , pass: "jenkins123", name: "Bob"  , mail: "bob@example.com"  , isAdmin: false],
    [id: "carol", pass: "jenkins123", name: "Carol", mail: "carol@example.com", isAdmin: false],
]

def jenkins  = Jenkins.getInstance()
def security = jenkins.getSecurityRealm()
def strategy = jenkins.getAuthorizationStrategy()

final allUsers = security.getAllUsers().each { it.getId() }

for (def userDef in userDefs) {
    def alreadyExists = allUsers.any { "${it}" == "${userDef.id}" }
    if (alreadyExists) {
        println "Skip adding ${userDef.id}: the user already exists."
        continue
    }

    def user = security.createAccount(userDef.id, userDef.pass)
    def prop = new Mailer.UserProperty(userDef.mail)
    user.setFullName(userDef.name)
    user.addProperty(prop)
    user.save()

    /* TODO: Add to administer is allowed when using matrix or role based auth. */
    /*
    if (userDef.isAdmin) {
        strategy.add(Jenkins.ADMINISTER, userDef.id)
    }
    */
}
jenkins.save()

security.getAllUsers().each { println "${it.getId()}" }
