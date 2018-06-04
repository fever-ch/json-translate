package ch.fever.jsontranslate.translator

import org.scalatest.FunSuite

trait TranslatorComponentImplTest extends FunSuite {

  def translatorComponent: TranslatorComponent

  test("List languages") {
    val list = translatorComponent.translator.langSupport

    // let's consider that Google handles at least English, French and German
    Seq("en", "fr", "de").foreach(l => assert(list.exists(_._1 == l)))
  }

  test("Test simple translation") {
    assert(translatorComponent.translator.translate("Bonjour", "fr", "en") == "Hello")
  }

}
