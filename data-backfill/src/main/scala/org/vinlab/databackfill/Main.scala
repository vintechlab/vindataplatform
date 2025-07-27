package org.vinlab.databackfill

import scopt.OptionParser

object Main {

  def main(args: Array[String]): Unit = {
    val parser = new OptionParser[Config]("Data Backfill") {
      head("Data Backfill", "0.1.0")

      opt[String]("db-url").required().action((x, c) => c.copy(dbUrl = x)).text("JDBC URL of the source database")
      opt[String]("source-table").required().action((x, c) => c.copy(sourceTable = x)).text("Source table name")
      opt[String]("target-table").required().action((x, c) => c.copy(targetTable = x)).text("Target BigQuery table name")
      opt[String]("target-dataset").required().action((x, c) => c.copy(targetDataset = x)).text("Target BigQuery dataset")
      opt[String]("partition-field").required().action((x, c) => c.copy(partitionField = x)).text("Partition field")
      opt[String]("gcs-bucket").required().action((x, c) => c.copy(gcsBucket = x)).text("Temporary GCS bucket")

      opt[String]("write-mode").optional().action((x, c) => c.copy(writeMode = x))
        .validate(x => if (Set("WRITE_TRUNCATE", "WRITE_APPEND", "WRITE_EMPTY").contains(x)) success else failure("Invalid write-mode"))
        .text("One of: WRITE_TRUNCATE, WRITE_APPEND, WRITE_EMPTY")

      opt[String]("create-mode").optional().action((x, c) => c.copy(createMode = x))
        .validate(x => if (Set("CREATE_IF_NEEDED", "CREATE_NEVER").contains(x)) success else failure("Invalid create-mode"))
        .text("One of: CREATE_IF_NEEDED, CREATE_NEVER")

      help("help").text("Show usage")
      version("version").text("Show version")
    }

    parser.parse(args, Config()) match {
      case Some(config) =>
        implicit val spark = SparkSessionProvider.create()
        BackfillJob.run(config)
        spark.stop()

      case None =>
        sys.exit(1)
    }
  }
}
