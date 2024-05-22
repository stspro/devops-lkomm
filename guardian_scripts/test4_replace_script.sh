#!/bin/bash

# Check if the correct number of arguments are provided
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <input_file>"
    exit 1
fi

# Input file
input_file="$1"

# Check if the input file exists
if [ ! -f "$input_file" ]; then
    echo "Input file '$input_file' not found."
    exit 1
fi

# Output file name
output_file="${input_file}.new"

# Replace "SEARCH" with "Replace" and save the output to a new file
sed 's/SEARCH/Replace/g' "$input_file" > "$output_file"

echo "Replacement completed. New file created: $output_file"
