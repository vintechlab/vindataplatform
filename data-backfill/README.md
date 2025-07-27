# Data Backfill

## Requirements

Using `conda` to setup development enviroment.

To create or update an environment based on this file, use the following command:

```sh
conda env create -f environment.yml
```

Or, to update an existing environment:

```sh
conda env update -f environment.yml
```

- python 3.12.11
- scala 2.12.18
- java 1.8.0
- pyspark 3.3.2

## Metals

```sh
sbt compile
sbt bloopInstall
```
