#!/bin/bash

full_path=$(realpath "$0")
data=$(dirname "$full_path")/import_files

cd ./import_files || exit

files=$(find ".")

for entry in $files
do
  if [ "$entry" != "." ]; then
        fileName=$(echo "$entry" | cut -c3- | cut -f 1 -d '.')
        sudo mongosh adderbot --eval "db.$fileName.drop()"
        sudo mongoimport -d adderbot -c "$fileName" < "$data/$fileName.json" --jsonArray
  fi
done

exit 0