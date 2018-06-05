package ch.fever.jsontranslate

import java.io.{FileInputStream, InputStreamReader, Writer}

import ch.fever.jsontranslate.translator.TranslatorComponent

object Forecast {
  def apply(config: ForecastConfig): Unit = {

    val in = config.src.map(new FileInputStream(_)).getOrElse(System.in)


    val dumbWriter = new Writer {
      override def write(cbuf: Array[Char], off: Int, len: Int) {}

      override def flush() {}

      override def close() {}
    }

    var charactersToBeTranslated = 0

    val context = new JsonTranslatorComponentJson4sImpl with TranslatorComponent {
      override val translator: Translator = new Translator {

        override def translate(input: String, sourceLanguage: String, targetLanguage: String): String = {
          charactersToBeTranslated += input.length
          ""
        }

        override def langSupport: Seq[(String, String)] = Seq.empty
      }

    }
    context.jsonTranslator.translate(new InputStreamReader(in), dumbWriter, "", "", keepOriginal = false)

    println(
      f"""$charactersToBeTranslated characters to be translated, estimated cost: $$${charactersToBeTranslated / 1E6}%1.2f
         |
               |based on a cost of $$20 per million characters
         |(June 2018 - Google Pricing, https://cloud.google.com/translate/pricing)""".stripMargin)
  }
}
