package testwork.generator

import java.io.{File, PrintWriter}

import testwork.implementation.Main

import scala.util.Random

/**
  * Created by Alex on 25/02/2016.
  */
object Generator {
  def main(args: Array[String]) = {
    val writer = new PrintWriter(new File(args(0)))

    try {
      for (i <- 0 until 1000000) {
        val first = nextIp();
        val second = nextIp();

        val firstIntForm = Main.toLong(first)
        val secondIntForm = Main.toLong(second)

        val range = if (firstIntForm < secondIntForm) first + "-" + second else second + "-" + first

        writer.write(range + "\t" + "Network" + i + "\n")
      }
    }
    finally {
      if (writer != null) {
        writer.close()
      }
    }
  }

  private def nextIp(): String = String.join(".", nextDecem, nextDecem, nextDecem, nextDecem)

  private def nextDecem(): String = Random.nextInt(256).toString
}
