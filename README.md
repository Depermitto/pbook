# pbook

> Requires java 8 or up installed

Simple commandline utility for creating booklet-style PDFs.

## Usage

```shell
java -jar pbook.jar [-hV] [--duplex] file
      file
  -h, --help      Show this help message and exit.
      --split     Split the booklet into two (front and back) PDF files.
  -V, --version   Print version information and exit.
```

## Example of usage

`java -jar pbook.jar --split some-pdf-file.pdf`
pbook will produce two independent files for printing. One for front one for back. For double-sided printers omit the `--split` option.

# Installation

Either grab a **jar** from the releases or build from source:

```shell
git clone gilab.com/Depermitto/pbook
cd pbook
sbt test assembly
```

# License

The project is licensed under the MIT license.
