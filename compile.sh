#!/bin/bash

find src/client src/commun -type f -name "*.java" > sourcesCli.list
find src/serveur src/commun -type f -name "*.java" > sourcesSer.list

javac @sourcesCli.list @paramsCli.list
javac @sourcesSer.list @paramsSer.list
