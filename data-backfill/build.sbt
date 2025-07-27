name := "data-backfill"
version := "0.1.0"
scalaVersion := "2.12.18"
semanticdbEnabled := true
semanticdbVersion := "4.8.11"

val SparkVersion = "3.3.2"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % SparkVersion % Provided,
  "org.apache.spark" %% "spark-sql" % SparkVersion % Provided,
  "com.github.scopt" %% "scopt" % "4.1.0"
)

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _ @_*) => MergeStrategy.discard
  case _                           => MergeStrategy.first
}
