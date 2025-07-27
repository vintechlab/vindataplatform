
package org.vinlab.databackfill

case class Config(
  dbUrl: String = "",
  sourceTable: String = "",
  targetTable: String = "",
  targetDataset: String = "",
  partitionField: String = "",
  gcsBucket: String = "",
  writeMode: String = "WRITE_TRUNCATE",
  createMode: String = "CREATE_IF_NEEDED"
)
