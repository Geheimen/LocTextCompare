# LocTextCompare
This code reads an excel file and compares two columns. If a cell in the first column has at least one asterisk, all lines in the second column's corresponding cell must have an asterisk. In case any line lacks an asterisk, that cell is highlighted with a red color.

Valid inputs for the first column are: single cell, single-column range or entire column. For the second column you need only specify the column letter itself.

The program can either create a new excel file with the modifications made or overwrite the file used.
