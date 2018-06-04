package ch.fever.jsontranslate.translator

trait TranslatorComponent {
  def translator: Translator

  trait Translator {
    def translate(input: String, sourceLanguage: String, targetLanguage: String): String

    def langSupport: Seq[(String, String)]
  }

}