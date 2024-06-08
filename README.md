# pbook
> Requires java installed, developed using java 21

Simple and feature limited commandline utility for booklet style printing. "Supports" duplex-capable, as well as non-duplex-capable printers.

Note: This software currently only produces correct printer sheets layout, e.g.
- 8 pages duplex
```
8,1,2,7,6,3,4,5
```
- 8 pages non-duplex
```
8,1,6,3
```
```
2,7,4,5
```

**pbook** is designed to be used in junction with some other printing solution, it does not reorganize pdfs 😢 (at least not yet).

## Usage
`java -jar pbook.jar <number-of-pages> [-d --duplex] [-q --quiet] [-h --help]`

- duplex  -   non-duplex mode. Formats pages for printers without duplex printing method.
- quiet   -   do not show tips for non-duplex mode.
- help    -   display this page.

## Example output
`java -jar pbook.jar 26`
```
26,1,24,3,22,5,20,7,18,9,16,11,14,13
```
```
2,25,4,23,6,21,8,19,10,17,12,15
```

# Installation
There is no need to clone or compile anything, one can just grab a **jar** from the releases. Alternatively
```bash
git clone gilab.com/Depermitto/pbook
cd pbook
sbt run ...
```

# License
The project is licensed under the MIT license. Feel free to modify and distribute however you so choose.