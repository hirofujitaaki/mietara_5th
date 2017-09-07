// https://stackoverflow.com/questions/26292073/whats-the-difference-between-maven-plugins-and-dependencies
// https://stackoverflow.com/questions/33771269/what-is-the-difference-between-plugins-dependencies-modules-and-sub-projects-i
// https://stackoverflow.com/questions/11881663/what-is-the-difference-in-maven-between-dependency-and-plugin-tags-in-pom-xml
/* Dependencies are libraries, more strictly, they are jar files(a fancy name of a zip file
 * that contains java classes) and will be added to the classpath while executing the task.
 * A plugin however is an artifact that you can configure in your project to have it actually
 * do something during the build of your project.
 */

// Comment to get more information during initialization
logLevel := Level.Warn

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.16")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

// web plugins

addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.1.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.4")

addSbtPlugin("com.typesafe.sbt" % "sbt-rjs" % "1.0.8")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-mocha" % "1.1.0")

addSbtPlugin("org.irundaia.sbt" % "sbt-sassify" % "1.4.6")
