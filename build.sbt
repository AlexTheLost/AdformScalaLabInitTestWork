name := "AdformScalaLabInitTestWork"

version := "1.0"

scalaVersion := "2.11.7"

// example for run[see: testwork.Main object]: sbt run D:\\resources\\ranges.tsv D:\\resources\\transactions.tsv D:\\out
mainClass in(Compile, run) := Some("testwork.implementation.Main")