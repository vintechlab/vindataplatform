package org.vinlab.databackfill

import org.apache.spark.sql.{SaveMode, SparkSession}

object BackfillJob {
  def run(config: Config)(implicit spark: SparkSession): Unit = {
    val df = spark.read
      .format("jdbc")
      .option("url", config.dbUrl)
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("user", sys.env("MYSQL_USER"))
      .option("password", sys.env("MYSQL_PASSWORD"))
      .option("dbtable", config.sourceTable)
      .load()

    val sparkWriteMode = config.writeMode match {
      case "WRITE_TRUNCATE" => SaveMode.Overwrite
      case "WRITE_APPEND"   => SaveMode.Append
      case "WRITE_EMPTY"    => SaveMode.ErrorIfExists
      case other            => throw new IllegalArgumentException(s"Unsupported writeMode: $other")
    }

    df.write
      .mode(sparkWriteMode)
      .option("partitionField", config.partitionField)
      .option("createDisposition", config.createMode)
      .option("writeDisposition", config.writeMode)
      .option("temporaryGcsBucket", config.gcsBucket)
      .option("table", s"${config.targetDataset}.${config.targetTable}")
      .format("bigquery")
      .save()
  }
}

