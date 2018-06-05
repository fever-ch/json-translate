package ch.fever.jsontranslate

import ch.fever.jsontranslate.translator.{GoogleTranslatorComponentImpl, TranslatorComponent}

trait TranslatorContextComponent {
  lazy val context: JsonTranslatorComponent with TranslatorComponent = new JsonTranslatorComponentJson4sImpl with GoogleTranslatorComponentImpl

}
