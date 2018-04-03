#!/bin/bash

find src/client -type f -name "*.java" > sourcesCli.list
find src/serveur src/client -type f -name "*.java" > sourcesSer.list

javac @sourcesCli.list @paramsCli.list
javac @sourcesSer.list @paramsSer.list
