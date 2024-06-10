# pbook
> Requires java 8 or up installed

Simple and feature limited commandline utility for booklet style printing.

**pbook** is designed to be used in junction with some other printing solution. In short, it reorders pages to be in line with a booklet style print. To not mess up your print: **odd pages first, then even pages**

## Usage
`java -jar pbook.jar pbook [-hV] [-o=filename] filename`

* -h, --help             -  Show this help message and exit.
* -o, --output=filename  -  Specify custom path for the output pdf
* -V, --version          -  Print version information and exit.

## Example of usage
`java -jar pbook.jar some-pdf-file.pdf --output my-output.pdf`

# Installation
There is no need to clone or compile anything, one can just grab a **jar** from the releases. Alternatively
```bash
git clone gilab.com/Depermitto/pbook
cd pbook
sbt run [args...]
```

# License
The project is licensed under the MIT license. Feel free to modify and distribute however you so choose.