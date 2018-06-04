package ch.fever.jsontranslate

import ch.fever.jsontranslate.translator.TranslatorComponent
import org.scalatest.FunSuite


trait JsonTranslatorComponentTest extends FunSuite with TestTranslatorComponent {
  val input =
    """{
      | "labelForAString": "Value",
      | "labelForAnInt": 1,
      | "labelForBool": true,
      | "labelForAnObject": {
      |    "label": "AnotherValue"
      | },
      | "labelForAnArray": ["Red", "Blue", "Green"]
      |}
    """.stripMargin


  def translatorComponent: JsonTranslatorComponent with TestTranslatorComponent

  test("Json translation test") {
    testTranslation(testContent)
  }

  test("Json translation test - ordered") {
    testTranslation(testContent)
  }


  test("Json translation with originals test") {
    testTranslationWithOriginals(testContent)
  }

  test("Json translation with originals test - ordered") {
    testTranslationWithOriginals(testContent)
  }

  def testTranslation(f: (String, String) ⇒ Unit) {
    val expectedOutput =
      """{
        | "labelForAString": "VALUE",
        | "labelForAnInt": 1,
        | "labelForBool": true,
        | "labelForAnObject": {
        |    "label": "ANOTHERVALUE"
        | },
        | "labelForAnArray": ["RED", "BLUE", "GREEN"]
        |}
      """.stripMargin

    val output = translatorComponent.jsonTranslator.translateSrc(input, "any", "uc", keepOriginal = false)

    f(output, expectedOutput)
  }


  def testTranslationWithOriginals(f: (String, String) ⇒ Unit) {


    val expectedOutputWithOriginals =
      s"""{
         | "labelForAString${translatorComponent.origTag}": "Value",
         | "labelForAString": "VALUE",
         | "labelForAnInt": 1,
         | "labelForBool": true,
         | "labelForAnObject": {
         |    "label${translatorComponent.origTag}": "AnotherValue",
         |    "label": "ANOTHERVALUE"
         | },
         | "labelForAnArray${translatorComponent.origTag}": ["Red", "Blue", "Green"],
         | "labelForAnArray": ["RED", "BLUE", "GREEN"]
         |}
      """.stripMargin
    val outputWithOriginals = translatorComponent.jsonTranslator.translateSrc(input, "any", "uc", keepOriginal = true)

    f(outputWithOriginals, expectedOutputWithOriginals)
  }

  import org.json4s._
  import org.json4s.jackson.JsonMethods._


  def testContent(a: String, b: String): Unit = {
    assert(parse(a) == parse(b))
  }


  def testOrderedContent(a: String, b: String): Unit = {
    assert(render(parse(a)) == render(parse(b)))
  }
}


trait TestTranslatorComponent extends TranslatorComponent {
  override def translator = new Translator {
    override def translate(input: String, sourceLanguage: String, targetLanguage: String): String =
      targetLanguage match {
        case "lc" ⇒ input.toLowerCase()
        case "uc" ⇒ input.toUpperCase()
      }

    override def langSupport: Seq[(String, String)] = List(
      ("uc", "Upper case"),
      ("lc", "Lower case")
    )
  }
}