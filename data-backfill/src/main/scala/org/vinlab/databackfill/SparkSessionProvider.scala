package org.vinlab.databackfill

import org.apache.spark.sql.SparkSession

object SparkSessionProvider {
  def create(): SparkSession = {
    SparkSession.builder()
      .appName("MySQL to BigQuery Backfill")
      .getOrCreate()
  }
}

