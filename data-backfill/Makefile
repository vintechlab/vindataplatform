SHELL := /bin/bash

-include .env
export

assembly:
	sbt assembly

submit:
	spark-submit \
		--conf spark.driver.bindAddress=0.0.0.0 \
		--conf spark.hadoop.fs.gs.impl=com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem \
		--conf spark.hadoop.fs.AbstractFileSystem.gs.impl=com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS \
		--class org.vinlab.databackfill.Main \
		--jars $(JARS_PATH)/mysql-connector-java-8.0.29.jar,$(JARS_PATH)/spark-bigquery-with-dependencies_2.12-0.32.0.jar,$(JARS_PATH)/gcs-connector-hadoop3-2.2.5-shaded.jar \
		target/scala-2.12/data-backfill-assembly-0.1.0.jar \
		--db-url=$(MYSQL_URL) \
		--source-table=products \
		--target-dataset=de_test_pim \
		--target-table=products \
		--partition-field=created_at \
		--gcs-bucket=de_test_data_backfill
