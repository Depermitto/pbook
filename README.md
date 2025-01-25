# pbook
> Requires java 8 or up installed

Simple commandline utility for creating booklet-style PDFs. In short, **pbook** copies a PDF file and reorders the pages into a booklet-style.

## Usage
```
java -jar pbook.jar [-hV] [--duplex] file
      file
      --duplex    Reorder pages into a single pdf file for double-sided printers
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
```

## Example of usage
`java -jar pbook.jar some-pdf-file.pdf`
pbook will produce two independent files for printing. One for front one for back. For duplex-equipped printers use the `--duplex` option.

# Installation
There is no need to clone or compile anything, one can just grab a **jar** from the releases. Alternatively
```bash
git clone gilab.com/Depermitto/pbook
cd pbook
sbt test assembly
```

# License
The project is licensed under the MIT license. Feel free to modify and distribute however you so choose.
