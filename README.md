# Huffman_File_Compressor

A file compressing software which utilizes the Huffman encoding/decoding algorithms. The Huffman Tree is displayed with animation when compressing an item. The GUI window is made to fit the Mac OS, although it is still usable with Windows (with a few elements overlapping). This happens because I made this GUI when I was not very knowledgeable about building GUIs with Java.

When a file is compressed, it will be split into two smaller (in most cases) files. Both files are required to decompress the file. I was too lazy to implement a way that is more convenient for use.

### The code takes in specific arguments in the form of:
`--encode <file_to_encode> <name_of_encoded_file> <name_of_frequency_table_file>` to compress a file.
OR
`--decode <file_to_decode> <name_of_decoded_file> <frequency_table_file>` to decompress a file.

Example:
`--encode alice30.txt alice30.enc freq.txt`
`--decode alice30.en alice30.txt freq.txt`
