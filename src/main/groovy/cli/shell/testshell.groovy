package cli.shell

import org.apache.groovy.groovysh.Groovysh

println("Hello World from groovy")

class Test {

}

sr = new Groovysh()
sr.run("a = 'julian'")
