![GitHub](https://img.shields.io/github/license/studio42gmbh/dle)
![GitHub top language](https://img.shields.io/github/languages/top/studio42gmbh/dle)
![GitHub last commit](https://img.shields.io/github/last-commit/studio42gmbh/dle)
![GitHub issues](https://img.shields.io/github/issues/studio42gmbh/dle)

# Data Language Examples

![DL Logo](https://github.com/studio42gmbh/dl/blob/master/resources/images/logo/dl-logo-200.png)

***ATTENTION:** This is the ALPHA release. Still many changes to come!*

This is the example project to Data Language (See: https://github.com/studio42gmbh/dl)

Have a great day!

Benjamin

> "Look up to the stars not down on your feet. Be curious!" _Stephen Hawking 1942 - 2018_


## Get started

* Download project
* Download Base 42 https://github.com/studio42gmbh/base42
* Download Log 42 https://github.com/studio42gmbh/log42
* Download DL https://github.com/studio42gmbh/dl
* Explore examples

Find the Javadoc here: https://studio42gmbh.github.io/dle/javadoc/


## Simple Configuration Example

Shows using DL for your configuration.

See https://github.com/studio42gmbh/dle/tree/master/src/main/java/de/s42/dl/examples/simpleconfiguration


## Load And Store Example

Shows using DL for loading and storing your complex configuration.

See https://github.com/studio42gmbh/dle/tree/master/src/main/java/de/s42/dl/examples/loadandstore


## Binary Example

Shows you how to write and read DL in different default formats - especially BIN and BIN_COMPRESSED.

See https://github.com/studio42gmbh/dle/tree/master/src/main/java/de/s42/dl/examples/binary


## GUI Example

Shows creating a very simple data driven GUI framework with DL.

See https://github.com/studio42gmbh/dle/tree/master/src/main/java/de/s42/dl/examples/gui


## DL Only Example

Showcases the possibilites of DL only usage. 

* Require other modules
* Define extern types, annotations, pragmas
* Define complex types with extends und contains
* Use pragmas
* Define instances
* Reference instances
* Export instances
* Introspect DL within Java

See https://github.com/studio42gmbh/dle/tree/master/src/main/java/de/s42/dl/examples/dlonly


## HTML Example

Showcases creating a html file from a dl dialect.

From
```
html {
  body {
    div main {
      classes : alert, alert-info;
      div {
        classes : jumbotron;
        h1 heading { text : "The Heading"; }
        p headingText { text : "The multiline\ntext under the heading";	}
      }
    }
  }
}
```
To
```
<html>
  <body>
    <div id="main" class="alert, alert-info">
      <div class="jumbotron">
        <h1 id="heading">The Heading</h1>
        <p id="headingText">The multiline<br/>text under the heading</p>
      </div>
     </div>
  </body>
</html>
```

See https://github.com/studio42gmbh/dle/tree/master/src/main/java/de/s42/dl/examples/html

