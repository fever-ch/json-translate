package ch.fever.jsontranslate.translator

import com.google.cloud.translate.Translate.TranslateOption
import com.google.cloud.translate.TranslateOptions

import scala.collection.JavaConverters._

trait GoogleTranslatorComponentImpl extends TranslatorComponent {
  override def translator = new GoogleTranslator

  class GoogleTranslator extends Translator {

    lazy val translateService = TranslateOptions.newBuilder.build.getService

    override def translate(input: String, sourceLang: String, targetLang: String): String = {
      val srcLang = TranslateOption.sourceLanguage(sourceLang)
      val tgtLang = TranslateOption.targetLanguage(targetLang)

      translateService.translate(input, srcLang, tgtLang).getTranslatedText
    }

    override def langSupport: Seq[(String, String)] =
      translateService.listSupportedLanguages()
        .asScala.map(l ⇒ (l.getCode, l.getName))
  }

}