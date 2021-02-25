
If you don't use an editor and such, you should be using `latexmk` to compile. Specifically;
```
latexmk -pdf [filename]   # fully compiles file by running as many times as necessary.
                          # if a file name is omitted, it'll compile all files in directory.

latexmk -c                # clean up all the "junk" files and be left with only bib/pdf/tex
```
