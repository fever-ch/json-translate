# JSON Translate

*a Simple tool to translate JSON files*

[![Build Status](https://travis-ci.org/fever-ch/json-translate.svg?branch=master)](https://travis-ci.org/fever-ch/json-translate)
[![License](http://img.shields.io/:license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)


Nowadays, it's quite common to store text content of a website in JSON format. This little piece of software aims to help within translation process.

Currently, this piece of software works only using the Google Translate API. This API isn't free, but it works pretty well, and it is relatively cheap.
 
About the "cheapness" of Google Translate, the current pricing is $20 for 1'000'000 characters.
- about $66 to translate Emile Zola's masterpiece "Les Misérables" 
- about $9 to translate J. K. Rowling's best-seller "Harry Potter and the Sorcerer's Stone")
.

*Hence, if you plan to write a best-seller/masterpiece, it might be valuable/recommended to contact a renowned translation agency ;-)*

# Use it

Consider a file named `content.json` containing text written in English that we'd like to translate French in a new file named `content-fr.json`

You can use and build this software directly or use it with the Docker containment mechanism. In both cases, you'll need a Google Translate API key.

In both examples, you'll see an environment variable named `NO_GCE_CHECK`, setting it to true will prevent you from some warnings when running this software outside Google Cloud (i.e., directly on your computer).
### Use it with SBT 

Requirements:
- [Java](https://www.java.com/en/) 
- [Sbt](https://www.scala-sbt.org/) 

Build the program:

    sbt jsontranslate/assembly 

Estimate the cost of translating `content.json`:
    
    java -jar app/target/jsontranslate*assembly*.jar forecast -i content.json

List availables languages available for translations:

    GOOGLE_API_KEY=<YOUR_GOOGLE_API_KEY> NO_GCE_CHECK=True java -jar app/target/jsontranslate*assembly*.jar list 

    
Translate `content.json` from English to French:

    GOOGLE_API_KEY=<YOUR_GOOGLE_API_KEY> NO_GCE_CHECK=True java -jar app/target/jsontranslate*assembly*.jar translate -i content.json -o content-fr.json -m en -n fr 

Get help:

    java -jar app/target/jsontranslate*assembly*.jar help


### Use it with Docker

Requirements: 
- [Docker](https://www.docker.com/get-docker)
 
  
Estimate the cost of translating `content.json`:

    docker run -i --rm feverch/json-translate forecast < content.json

List availables languages available for translations:
    
    docker run -i --rm -e NO_GCE_CHECK=True -e GOOGLE_API_KEY=<YOUR_GOOGLE_API_KEY> feverch/json-translate forecast < content.json

Translate `content.json` from English to French:

    docker run -i --rm -e NO_GCE_CHECK=True -e GOOGLE_API_KEY=<YOUR_GOOGLE_API_KEY> feverch/json-translate translate -m en -n fr < content.json > content-fr.json

Get help:

    docker run -i --rm feverch/json-translate help
    
## License
 
This software is licensed under the Apache 2 license, quoted below.

Copyright 2018 Raphaël P. Barazzutti

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.