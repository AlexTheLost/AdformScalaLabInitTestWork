package testwork.implementation

import java.io.{File, PrintWriter}

import testwork.tree.{Interval1D, IntervalST}

import scala.collection.JavaConversions.iterableAsScalaIterable
import scala.io.Source

/**
  * Created by Alex on 25/02/2016.
  */
object Main extends App {
  val LINE_TRANFORMATOR = """^(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})-(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})\s+([a-zA-Z0-9]+)$""".r
  val IP_TRANFORMATOR = """^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$""".r
  val USER_LINE_TRANFORMATOR = """^(-?\d+)\s+(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})$""".r

  //-----------
  val startTime = System.currentTimeMillis()
  doThis(args(0), args(1), args(2))
  println("Total time(ms): " + (System.currentTimeMillis() - startTime))

  //-----------

  private def doThis(rangesFile: String, transactionsFile: String, outputDirectory: String) = {
    val tree = new IntervalST[String]()
    populateIntervalTree(tree, rangesFile)
    findAndStoreAllIntervalForUser(tree, transactionsFile, outputDirectory)
  }

  private def populateIntervalTree(toPopulate: IntervalST[String], rangesFile: String) = {
    for (line <- getLines(rangesFile)) {
      line match {
        case LINE_TRANFORMATOR(ip1, ip2, name) => toPopulate.put(new Interval1D(toLong(ip1), toLong(ip2), name))
        case _ => throw new IllegalStateException("Invalid line " + line)
      }
    }
  }

  private def findAndStoreAllIntervalForUser(intervals: IntervalST[String], transactionsFile: String, outputDirectory: String) = {
    val writer = new PrintWriter(new File(outputDirectory + "\\output.tsv"))
    val withoutDuplicate = scala.collection.mutable.Set[String]()
    try {
      for (line <- getLines(transactionsFile)) {
        line match {
          case USER_LINE_TRANFORMATOR(id, ip) => {
            for (interval <- intervals.searchAll(new Interval1D(toLong(ip), toLong(ip), null))) {
              withoutDuplicate.add(interval.name)
            }
            withoutDuplicate.foreach({ name => writer.append(id).append('\t').append(name).append('\n') })
            writer.flush()
            withoutDuplicate.clear()
          }
          case _ => throw new IllegalStateException("Invalid line " + line)
        }
      }
    } finally {
      if (writer != null)
        writer.close()
    }
  }

  private def getLines(name: String) = Source.fromFile(name).getLines()

  private def toLong(decimal1: Int, decimal2: Int, decimal3: Int, decimal4: Int): Long = (decimal1 * 1000000000l
    + decimal2 * 1000000l
    + decimal3 * 1000l
    + decimal4)

  def toLong(ip: String): Long = {
    ip match {
      case IP_TRANFORMATOR(decimal1, decimal2, decimal3, decimal4) => toLong(decimal1.toInt, decimal2.toInt, decimal3.toInt, decimal4.toInt)
      case _ => throw new IllegalStateException("Not valid ip: " + ip)
    }
  }
}



