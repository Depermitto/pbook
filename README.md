# pbook
> Requires java 8 or up installed

Simple and feature limited commandline utility for booklet style printing.

**pbook** is designed to be used in junction with some other printing solution. In short, it reorders pages to be in line with a booklet style print. To not mess up your print: **print xyz_booklet_front.pdf first -> flip -> then print xyz_booklet_back.pdf**

## Usage
`java -jar pbook.jar [-dhV] [-n=n] filename`
*      filename
*  -d, --duplex            Reorder pages into a single pdf file for duplex-equipped printers
*  -h, --help              Show this help message and exit.
*  -n, --name-preserve=n   How much of original filename to preserve
*  -V, --version           Print version information and exit.


## Example of usage
`java -jar pbook.jar some-pdf-file.pdf`
pbook will produce two independent files for printing. One for front one for back. For duplex-equipped printers use the `--duplex` option.

# Installation
There is no need to clone or compile anything, one can just grab a **jar** from the releases. Alternatively
```bash
git clone gilab.com/Depermitto/pbook
cd pbook
sbt run [args...]
```

# License
The project is licensed under the MIT license. Feel free to modify and distribute however you so choose.
